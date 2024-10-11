<script setup lang="ts">
import {onMounted, ref} from 'vue';
import MonthCalender from "@/components/MonthCalender.vue";
import CreateActivityForm from "@/components/CreateActivityForm.vue";
import TestHeader from ".././components/TestHeader.vue";
import DaySchedule from "@/components/DaySchedule.vue";
import type {DutySchedule} from "../models/DutySchedule";
import axios from "axios";
import LoginForm from "@/components/LoginForm.vue";

interface LoginSessionData {
  isAdmin: boolean;
  sessionValid: boolean;
}

//Define refs for the selected day and events
const selectedDay = ref<Date | null>(null);
const selectedDayEvents = ref<DutySchedule[]>([]);
const isDayViewVisible = ref(false);
const isLoggedIn = ref(false);
const isAdmin = ref(false);
const allDuties = ref<any[]>([]);
const duties = ref<DutySchedule[]>([]);




//Login stuff - the whole part is needed so it doesnt redirect to login form
onMounted(async () => {
  //Needed so the site doesnt lag when checking from backend
  const savedLoginState = localStorage.getItem('isLoggedIn');
  if (savedLoginState === 'true') {
    isLoggedIn.value = true;

    const adminState = localStorage.getItem('isAdmin');
    isAdmin.value = adminState === 'true';
  }

  //Confirms the check and makes sure the session is valid and isAdmin is set
  try {
    const response = await axios.get('http://localhost:8080/api/login/check-session', {
      withCredentials: true
    });

    handleLoginSuccess(response.data);
  } catch (error) {
    isLoggedIn.value = false;
  }

  // Fetch only the duties for the logged-in user
  try {
    const { data } = await axios.get('http://localhost:8080/api/duty-schedules/all'); // Updated endpoint
    allDuties.value = data; // Store all duties

    // Filter duties for the logged-in user
    const username = localStorage.getItem('username');
    console.log('All Duties:', allDuties.value);
    console.log('Username:', username);
    duties.value = allDuties.value.filter(duty => duty.employeeUsername === username);
  } catch (error) {
    console.error('Error fetching duty schedules:', error);
  }

});

function handleLoginSuccess(data: LoginSessionData) {
  localStorage.setItem('isLoggedIn', 'true');
  localStorage.setItem('isAdmin', String(data.isAdmin));
  isLoggedIn.value = true;
  isAdmin.value = Boolean(data.isAdmin);
}

//Methods to show and close the day view
function showDayView(day: Date) {
  selectedDay.value = day;
  selectedDayEvents.value = []; // Fetch schedule for the selected day if needed
  isDayViewVisible.value = true;
  (document.querySelector('.month-view') as HTMLElement).classList.add('blurred');
}

function closeDayView() {
  isDayViewVisible.value = false;
  (document.querySelector('.month-view') as HTMLElement).classList.remove('blurred');
}

//Reactive reference for form visibility
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
  <!-- login -->
  <div v-if="!isLoggedIn" class="login">
    <LoginForm />
  </div>

  <div v-else>
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
        <MonthCalender @day-selected="showDayView" :duty-days="duties.map(duty => new Date(duty.date))" />
      </div>

      <!-- Day Calendar -->
      <DaySchedule
          v-if="isDayViewVisible"
          :day="selectedDay"
          :events="selectedDayEvents"
          :isVisible="isDayViewVisible"
          :is-admin="isAdmin"
          @close-day-view="closeDayView"
      />

      <!-- Overlay for Day View -->
      <div v-if="isDayViewVisible" class="overlay" @click="closeDayView"></div>
      <div v-if="formVisible" class="overlay" @click="closeForm"></div>
    </div>
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
  height: 100%;
  background-color: white;
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
