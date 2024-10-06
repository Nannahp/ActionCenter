<script setup lang="ts">
import BaseButton from "@/components/BaseButton.vue";
import TimetableColumn from "@/components/TimetableColumn.vue";
import ActivityColumn from "@/components/ActivityColumn.vue";
import { ref, onMounted } from 'vue';
import axios from 'axios';

const startHour = 10;
const endHour = 20;

const weekendStartHour = 12;
const weekendEndHour = 20;

const bookings = ref<any[]>([]);
const uniqueActivities = ref<string[]>([]);

const props = defineProps<{
  day: Date | null;
  isVisible: boolean;
}>();

const emit = defineEmits(['close-day-view']);

const activityColors: Record<string, string> = {
  "Yoga": "rgba(255, 182, 193, 0.7)",
  "Bowling": "rgba(173, 216, 230, 0.7)",
  "Laser Tag": "rgba(144, 238, 144, 0.7)"
};

function isWeekend(day: Date | null): boolean {
  if (day === null) {
    return false;
  }
  const dayOfWeek = day.getDay();
  return dayOfWeek === 0 || dayOfWeek === 6; //Sunday = 0, Saturday = 6
}

onMounted(async () => {
  if (props.day !== null) {
    const dayYear = props.day.getFullYear();
    const dayMonth = props.day.getMonth() + 1; //+1 to convert to 1-based month
    const dayDate = props.day.getDate();

    const formattedDate = `${dayYear}-${String(dayMonth).padStart(2, '0')}-${String(dayDate).padStart(2, '0')}`;

    try {
      const { data } = await axios.get(`http://localhost:8080/api/bookings/day`, {
        params: { date: formattedDate }
      });

      console.log('Fetched bookings:', data); // Log the fetched bookings

      // Store the retrieved bookings in the ref
      bookings.value = data;

      // Get unique activity names
      uniqueActivities.value = [...new Set(bookings.value.map(b => b.activityName))];
    } catch (error) {
      console.error('Error fetching bookings:', error);
    }
  }
});


function closeDayView() {
  emit('close-day-view');
}

function filteredBookings(activity: string) {
  return bookings.value.filter(booking => booking.activityName === activity);
}
</script>

<template>
  <transition name="slide">
    <div v-if="isVisible" class="day-view">
      <BaseButton text="✖" type="button" class="close-button" @click="closeDayView" />
      <h2 class="centered-date">{{ day?.toDateString() }}</h2>
      <div class="activity-header">
        <div
            v-for="activity in uniqueActivities"
            :key="activity"
            class="activity-name"
        >
          <h3>{{ activity }}</h3>
        </div>
      </div>
      <div class="day-grid">
        <!-- Timetable Column -->
        <TimetableColumn
            :startHour="isWeekend(day) ? weekendStartHour : startHour"
            :endHour="isWeekend(day) ? weekendEndHour : endHour"
        />
        <!-- Activity Columns -->
        <div class="activity-columns">
          <ActivityColumn
              v-for="activity in uniqueActivities"
              :key="activity"
              :activityName="activity"
              :bookings="filteredBookings(activity)"
              :activityColor="activityColors[activity]"
          />
        </div>
      </div>
      <BaseButton text="Create Booking" type="button" class="create-booking-btn" @click="closeDayView" />
    </div>
  </transition>
</template>

<style scoped>
.day-view {
  width: 60%;
  padding: 20px;
  background: #f0f0f0;
  border-left: 1px solid #ccc;
  border-radius: 10px;
  position: absolute;
  right: 0;
  top: 0;
  height: 100%;
  z-index: 10;
  display: flex;
  flex-direction: column;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
}

.create-booking-btn {
  background: none;
  border: 1px solid #b3b3b3;
  font-size: 20px;
  cursor: pointer;
  margin: 0 auto;
  display: block;
  border-radius: 10px;
  padding: 10px;
}

.centered-date {
  text-align: center;
  font-size: 35px;
  margin-bottom: 20px;
  color: #333333;
}

.activity-header {
  display: flex;
  justify-content: space-around;
  margin-bottom: 10px;
}

.activity-name {
  font-weight: bold;
  color: #333;
}

.day-grid {
  display: grid;
  grid-template-columns: 50px 1fr;
  gap: 10px;
  flex-grow: 1;
}

.activity-columns {
  display: flex;
  flex-grow: 1;
}
</style>