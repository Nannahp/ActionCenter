<script setup lang="ts">
import {computed, ref} from 'vue';
import BaseButton from './BaseButton.vue';
import axios from 'axios';
import SelectDates from "@/components/SelectDates.vue";

const props = defineProps<{
  currentSelectedDay: Date | null; // Add this prop to receive the day
}>();

// Emit event to parent component
const emit = defineEmits(['close', 'duty-schedule-added']);

// Reactive variables
const currentSlide = ref(1);
const selectedEmployee = ref('');
const startTime = ref('');
const endTime = ref('');
const selectedDays = ref([]); // Array to hold selected days as Date objects
const employees = ref([]); // This will be populated with employee data
const submitted = ref(false);

async function fetchEmployees() {
  try {
    const response = await axios.get('http://localhost:8080/api/employee');
    employees.value = response.data;
  } catch (error) {
    console.error("Error fetching employees: ", error);
  }
}

// Call fetchEmployees on component mount
fetchEmployees();

function nextSlide() {
  currentSlide.value += 1;
}

const formStartTime = ref('10:00'); // Default start time
const formEndTime = ref('20:00');   // Default end time

// Function to determine if the selected day is a weekend
const isWeekend = (day: Date) => {
  const dayOfWeek = day.getDay();
  return dayOfWeek === 0 || dayOfWeek === 6; // 0 = Sunday, 6 = Saturday
};

// Computed property for allowed start times based on the selected day
const allowedHours = computed(() => {
  if (!props.currentSelectedDay) return [];
  return isWeekend(props.currentSelectedDay)
      ? ['12', '13', '14', '15', '16', '17', '18', '19', '20']
      : ['10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20'];
});

const allowedMinutes = ['00'];

async function handleSubmit() {
  console.log("Submitting duty schedule for: ", selectedEmployee.value);

  // Prepare an array to hold the duty schedules
  const dutySchedulesToSubmit = [];

  try {
    // Iterate over the selected days and create an object for each
    for (const day of selectedDays.value) {
      const dutyScheduleToSubmit = {
        employeeId: selectedEmployee.value, // Use employee ID directly
        startTime: startTime.value,
        endTime: endTime.value,
        date: new Date(Date.UTC(day.getFullYear(), day.getMonth(), day.getDate())).toISOString().split('T')[0],
      };

      console.log(dutyScheduleToSubmit)
      // Add the schedule object to the array
      dutySchedulesToSubmit.push(dutyScheduleToSubmit);
    }

    // Make a single POST request with the array of duty schedules
    const response = await axios.post('http://localhost:8080/api/duty-schedules/add-duty', dutySchedulesToSubmit);
    console.log("Response from server: ", response);
    submitted.value = true;
    currentSlide.value += 1; // Move to success message slide
    emit('duty-schedule-added');
  } catch (error) {
    console.error("Error submitting duty schedule: ", error);
  }
}

function handleClose() {
  resetForm();
  emit('close');
}

function resetForm() {
  currentSlide.value = 1;
  selectedEmployee.value = '';
  startTime.value = '';
  endTime.value = '';
  selectedDays.value = [];
  submitted.value = false;
}

// Handle selected days from the calendar
function handleDaySelected(day: Date) {
  formStartTime.value = ''; // Reset time selection when day changes
  formEndTime.value = '';

  // Reset start time based on day selected
  startTime.value = isWeekend(day) ? '12:00' : '10:00'; // Automatically set start time based on selected day
  console.log("Selected day: ", day);
  const dayString = day.toDateString();

  // Check if the selected day is already in the list
  const dayIndex = selectedDays.value.findIndex(d => d.toDateString() === dayString);

  if (dayIndex === -1) {
    // Add day if not already selected
    selectedDays.value.push(day);
  } else {
    // Remove day if already selected
    selectedDays.value.splice(dayIndex, 1);
  }
}

</script>

<template>
  <form @submit.prevent="currentSlide === 2 ? handleSubmit() : nextSlide()">
    <BaseButton text="✖" type="button" class="close-button" @click="handleClose" />
    <h2>Add New Duty Schedule</h2>

    <div v-if="currentSlide === 1">
      <div class="input-wrapper">
        <div class="input">
          <label for="employeeSelect" class="input-label">Select Employee:</label>
          <select v-model="selectedEmployee" required>
            <option value="" disabled>Select employee</option>
            <option v-for="employee in employees" :key="employee.id" :value="employee.id">
              {{ employee.name }}
            </option>
          </select>
          <!-- Start time select -->
          <label for="formStartTime">Start Time:</label>
          <select v-model="formStartTime" required>
            <option value="" disabled>Select Start Time</option>
            <template v-for="hour in allowedHours" :key="hour">
              <option v-for="minute in allowedMinutes" :key="`${hour}:${minute}`" :value="`${hour}:${minute}`">
                {{ hour }}:{{ minute }}
              </option>
            </template>
          </select>

          <!-- End time select -->
          <label for="formEndTime">End Time:</label>
          <select v-model="formEndTime" required>
            <option value="" disabled>Select End Time</option>
            <template v-for="hour in allowedHours" :key="hour">
              <option v-for="minute in allowedMinutes" :key="`${hour}:${minute}`" :value="`${hour}:${minute}`">
                {{ hour }}:{{ minute }}
              </option>
            </template>
          </select>
        </div>
        <div class="button-wrapper">
          <BaseButton text="Next" type="submit" />
        </div>
      </div>
    </div>

    <div v-if="currentSlide === 2">
      <div class="input-wrapper">
        <label class="input-label">Select Days:</label>
        <SelectDates @day-selected="handleDaySelected" />

        <div class="button-wrapper">
          <BaseButton text="Submit" type="submit" />
        </div>
      </div>
    </div>

    <div v-if="currentSlide === 3">
      <div class="input-wrapper">
        <div class="success-message">
          <h3 v-if="submitted">Duty Schedule added!</h3>
          <p>You can close this form now</p>
        </div>
        <div class="button-wrapper">
          <BaseButton text="Close" type="button" @click="handleClose" />
        </div>
      </div>
    </div>
  </form>
</template>

<style scoped>
@import '@/assets/base.css';
/* Center the form on the page and set a fixed height */
form {
  width: 40em;
  height: 30em;
  margin: 0 auto;
  padding: 2rem;
  text-align: left;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  position: fixed; /* Keep it fixed to overlay */
  top: 50%; /* Position from the top */
  left: 50%; /* Position from the left */
  transform: translate(-50%, -50%); /* Center the form */
  z-index: 20; /* Ensure it appears above other content */
}

select {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  transition: border-color 0.3s ease;
  width: 100%;
  margin-bottom: 1.5rem;
  color: grey;
}


.input {
  height: 18em;
  align-content: center;
  width: 95%;
  align-self: center;
}

/* Styling for headers */
h2 {
  text-align: center;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  z-index: 10; /* Optional: Ensure it appears on top of the form content */
  color: #030003;
}

/* Wrapper for form inputs */
.input-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex-grow: 1; /* Allows content to fill available space, keeping centered */
  width: 100%;
  margin-bottom: 1.5rem;
}


/* Align labels to the left of the input */
.input-label {
  font-weight: bold;
  margin-bottom: 0.5rem;
  display: block;
}

/* Styling for inputs */
input, textarea {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  transition: border-color 0.3s ease;
  font-size: 1rem;
  margin-bottom: 1rem;
}

input:focus,
textarea:focus {
  border-color: #007bff;
  outline: none;
}

/* Ensure textarea has more height and wraps text */
textarea {
  height: 8rem;
  resize: vertical;
  line-height: 1.5;
  word-wrap: break-word;
}

/* Button wrapper styling */
.button-wrapper {
  margin-top: auto; /* Pushes the button to the bottom */
  text-align: center;
}

/* Button styling */
button {
  padding: 0.75rem 1.5rem;
  background-color: #0056b3;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 18px;
}
.success-message {
  height: 18em;
  align-content: center;
  width: auto;
  align-self: center;
  text-align: center;
  color: green;
}
</style>
