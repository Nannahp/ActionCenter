<script setup lang="ts">
import BaseButton from "@/components/BaseButton.vue";
import TimetableColumn from "@/components/TimetableColumn.vue";
import { ref, onMounted } from 'vue';
import axios from 'axios';
import ScheduleColumn from "@/components/ScheduleColumn.vue";
import CreateDutyForm from "@/components/CreateDutyForm.vue";

const startHour = 10;
const endHour = 20;

const weekendStartHour = 12;
const weekendEndHour = 20;

const duties = ref<any[]>([]);
const employeeNames = ref<string[]>([]);
const isCreateDutyFormVisible = ref(false);


const props = defineProps<{
  day: Date | null;
  isVisible: boolean;
}>();

const emit = defineEmits(['close-day-view']);

const employeeColors: Record<string, string> = {
  "Princess Peach": "rgba(255, 182, 193, 0.7)",
  "Luigi Mario": "rgba(173, 216, 230, 0.7)",
  "Toad Toadsworth": "rgba(144, 238, 144, 0.7)",
  "Mario Mario": "rgba(255,146,127,0.7)",
  "Yoshi Yoshi": "rgba(103,175,153,0.7)",
  "Bowser Koopa": "rgba(255,191,104,0.7)",
  "Donkey Kong": "rgba(161,124,99,0.7)",
  "Princess Daisy": "rgba(134,150,215,0.7)",
  "Wario Wario": "rgba(255,239,121,0.7)",
  "Waluigi Waluigi": "rgba(218,140,218,0.7)",

};

//Function to get activity color based on the booking's activity name
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
  await fetchDutySchedules();
});

function closeDayView() {
  isCreateDutyFormVisible.value = false; // Hide the CreateDutyForm when closing the day view
  emit('close-day-view');
}

function showCreateDutyForm() {
  isCreateDutyFormVisible.value = true; // Show the CreateDutyForm
}

function handleDutyScheduleAdded() {
  // Re-fetch the duty schedules for the specific day
  fetchDutySchedules();
}

async function fetchDutySchedules() {
  if (props.day !== null) {
    const dayYear = props.day.getFullYear();
    const dayMonth = props.day.getMonth() + 1; // +1 to convert to 1-based month
    const dayDate = props.day.getDate();

    const formattedDate = `${dayYear}-${String(dayMonth).padStart(2, '0')}-${String(dayDate).padStart(2, '0')}`;

    try {
      const { data } = await axios.get(`http://localhost:8080/api/duty-schedules/day`, {
        params: { date: formattedDate }
      });

      duties.value = data;

      employeeNames.value = [...new Set(duties.value.map(d => d.employeeName))];
    } catch (error) {
      console.error('Error fetching duty schedules:', error);
    }
  }
}


function filteredDuties(employee: string) {
  return duties.value.filter(duty => duty.employeeName === employee);
}

function isFutureOrToday(day: Date | null) {
  if (!day) return false;

  const today = new Date();
  today.setHours(0, 0, 0, 0); // Remove time for accurate comparison
  return day.getTime() >= today.getTime(); // Returns true if the day is today or in the future
}

const formVisible = ref(false); // Initially set to false

const openForm = (value: string) => {
  showCreateDutyForm();
  formVisible.value = true;
  (document.querySelector('.month-view') as HTMLElement).classList.add('blurred');
};

const closeForm = () => {
  formVisible.value = false;
  (document.querySelector('.month-view') as HTMLElement).classList.remove('blurred');
};
</script>

<template>
  <transition name="slide">
    <div v-if="isVisible" class="day-view">
      <BaseButton text="✖" type="button" class="close-button" @click="closeDayView" />
      <h2 class="centered-date">{{ day?.toDateString() }}</h2>
      <div class="schedule-header">
        <div
            v-for="employee in employeeNames"
            :key="employee"
            class="schedule-name"
        >
          <h3 class="schedule-name-text">{{ employee }}</h3>
        </div>
      </div>
      <div class="day-grid">
        <!-- Timetable Column -->
        <TimetableColumn
            :startHour="isWeekend(day) ? weekendStartHour : startHour"
            :endHour="isWeekend(day) ? weekendEndHour : endHour"
        />
        <!-- Activity Columns -->
        <div class="schedule-columns">
          <ScheduleColumn
            v-for="employee in employeeNames"
            :key="employee"
            :employee-name="employee"
            :employee-color="employeeColors[employee] || 'light-grey'"
            :day="day"
            :duties="filteredDuties(employee)"
          />
        </div>
      </div>
      <BaseButton
          v-if="isFutureOrToday(day)"
          text="Add duty"
          type="button"
          class="create-booking-btn"
          @click="openForm"
      />
      <CreateDutyForm
          v-if="isCreateDutyFormVisible"
          @close="isCreateDutyFormVisible = false"
          @duty-schedule-added="handleDutyScheduleAdded"
      />
      <div v-if="isCreateDutyFormVisible" class="overlay" @close="closeForm"></div>
    </div>
  </transition>
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

.schedule-header {
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-bottom: 10px;
  margin-left: 50px;
}

.schedule-name {
  display: flex;
  justify-content: center;
  align-items: center;
  flex: 1;
}

.schedule-name-text {
  overflow: hidden;
  text-overflow: ellipsis;
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

.schedule-columns {
  display: flex;
  flex-grow: 1;
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(3px);
  z-index: 9;
}
</style>