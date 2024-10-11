<script setup lang="ts">
import BaseButton from "@/components/BaseButton.vue";
import TimetableColumn from "@/components/TimetableColumn.vue";
import ActivityColumn from "@/components/ActivityColumn.vue";
import {ref, onMounted, computed} from 'vue';
import axios from 'axios';
import CreateBookingForm from "@/components/CreateBookingForm.vue";

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

// Fetch isAdmin from localStorage
const isAdmin = computed(() => {
  return localStorage.getItem('isAdmin') === 'true';
});

const emit = defineEmits(['close-day-view']);

const activityColors: Record<string, string> = {
  "Yoga": "rgba(255, 182, 193, 0.7)",
  "Bowling": "rgba(173, 216, 230, 0.7)",
  "Laser Tag": "rgba(144, 238, 144, 0.7)"
};

// Function to get activity color based on the booking's activity name
/*function getActivityColor(activityName: string): string {
  let activityColor = 'darkgray'; // Default color

  for (const activity of uniqueActivities.value) {
    if (activity === activityName) {
      activityColor = activity.color; // Set the color if a match is found
      break; // Exit the loop once the activity is found
    }
  }

  return activityColor;
}*/


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

      bookings.value = data;

      uniqueActivities.value = [...new Set(bookings.value.map(b => b.activityName))];
    } catch (error) {
      console.error('Error fetching bookings:', error);
    }
  }
});

let showCreateBookingForm = ref(false);

function openCreateBookingForm() {
  showCreateBookingForm.value = true;
}

function closeDayView() {
  emit('close-day-view');
}

function filteredBookings(activity: string) {
  return bookings.value.filter(booking => booking.activityName === activity);
}

function isFutureOrToday(day: Date | null) {
  if (!day) return false;

  const today = new Date();
  today.setHours(0, 0, 0, 0);
  return day.getTime() >= today.getTime();
}
</script>

<template>
  <div v-if="isVisible" class="day-view">
    <BaseButton text="✖" type="button" class="close-button" @click="closeDayView" />
    <h2 class="centered-date">{{ day?.toDateString() }}</h2>
    <div class="activity-header">
      <div
          v-for="activity in uniqueActivities"
          :key="activity"
          class="activity-name"
      >
        <h3 class="activity-name-text">{{ activity }}</h3>
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
            :day="day"
            :is-admin="isAdmin"
        />
      </div>
    </div>
    <CreateBookingForm v-if="showCreateBookingForm" @close-form="showCreateBookingForm = false" />
    <BaseButton
        v-if="isFutureOrToday(day) && isAdmin"
        text="Create Booking"
        type="button"
        class="create-booking-btn"
        @click="openCreateBookingForm"
    />
  </div>
</template>

<style scoped>
.day-view {
  width: 70%;
  padding: 20px;
  background: #f0f0f0;
  border-left: 1px solid #ccc;
  border-radius: 10px;
  right: 0;
  top: 0;
  z-index: 10;
  display: flex;
  flex-direction: column;
  position: fixed;
  height: 100vh;
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
  font-size: 18px;
  margin: 0 auto;
  display: block;
  padding: 0.75rem 1.5rem;
  background-color: #0056b3;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
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
  align-items: center;
  margin-bottom: 10px;
  margin-left: 50px;
}

.activity-name {
  display: flex;
  justify-content: center;
  align-items: center;
  flex: 1; /* Equal space for each column */
}

.activity-name-text {
  overflow: hidden;
  text-overflow: ellipsis; /* Show ellipsis if text overflows */
  white-space: normal;
  display: block;
  max-width: 100px;
  text-align: center;
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