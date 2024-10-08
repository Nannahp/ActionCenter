<script setup lang="ts">
import { ref } from 'vue';
import DayCalender from "@/components/DayCalender.vue";
import MonthCalender from "@/components/MonthCalender.vue";
import CreateActivityForm from "@/components/CreateActivityForm.vue";
import TestHeader from ".././components/TestHeader.vue";



//Placeholder for bookings
interface Event {
  title: string;
  startTime: string;
  endTime: string;
}

//Define refs for the selected day and events
const selectedDay = ref<Date | null>(null);
const selectedDayEvents = ref<Event[]>([]);
const isDayViewVisible = ref(false);

//Methods to show and close the day view
function showDayView(day: Date) {
  selectedDay.value = day;
  selectedDayEvents.value = []; // Fetch events for the selected day if needed
  isDayViewVisible.value = true;
  (document.querySelector('.month-view') as HTMLElement).classList.add('blurred');
}

function closeDayView() {
  isDayViewVisible.value = false;
  (document.querySelector('.month-view') as HTMLElement).classList.remove('blurred');
}

//Add-activities things: 
// Reactive reference for form visibility
const formVisible = ref(false); // Initially set to false

// Handle dropdown selection
const handleDropdownSelect = (value: string) => {
  if (value === 'add-activity') {
    formVisible.value = true;
    (document.querySelector('.month-view') as HTMLElement).classList.add('blurred');
  }
};

// Close the form
const closeForm = () => {
  formVisible.value = false;
  (document.querySelector('.month-view') as HTMLElement).classList.remove('blurred');
};
</script>

<template>
  <header>
    <TestHeader @dropdown-select="handleDropdownSelect" />
  </header>

  <!-- Activity Form -->
  <div v-if="formVisible" class="activity-form">
    <CreateActivityForm @exitForm="closeForm" />
  </div>

  <!-- Calendar Container -->
  <div class="calendar-container">
    <!-- Month Calendar -->
    <div>
      <MonthCalender @day-selected="showDayView" />
    </div>

    <!-- Day Calendar -->
    <DayCalender
        v-if="isDayViewVisible"
        :day="selectedDay"
        :events="selectedDayEvents"
        :isVisible="isDayViewVisible"
        @close-day-view="closeDayView"
    />

    <!-- Overlay for Day View -->
    <div v-if="isDayViewVisible" class="overlay" @click="closeDayView"></div>
    <div v-if="formVisible" class="overlay" @click="closeForm"></div>
  </div>
</template>

<style scoped>
/* Calendar container styles */
.calendar-container {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  width: 100%;
  height: calc(100% - 75px); /* Adjust this value based on your header's height */
  margin-top: 10px;
}

/* Form styling when visible */
.activity-form {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 200;
}

/* Overlay for day view */
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

header {
  left: 0;
  top: 0;
  height: 75px;
}

template {
  display: flex;
  flex-direction: column;
}
</style>
