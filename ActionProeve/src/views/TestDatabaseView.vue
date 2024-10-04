<template>
    <div class="container">
      <div class="left-side">
        <h1>Employee Management</h1>
        <button @click="registerEmployee">Register Hardcoded Employee</button>
        <div v-if="message">{{ message }}</div>
  
        <h2>All Employees</h2>
        <ul>
          <li v-for="employee in employees" :key="employee.id">
            {{ employee.name }} ({{ employee.username }})
          </li>
        </ul>
      </div>
  
      <div class="right-side">
        <h1>Booking Management</h1>
        <button @click="createHardcodedBooking">Create Hardcoded Booking</button>
        <div v-if="bookingMessage">{{ bookingMessage }}</div>
  
        <h2>All Bookings</h2>
        <ul>
          <li v-for="booking in bookings" :key="booking.id">
            {{ booking.activityName }} for {{ booking.customerName }} on {{ booking.date }} at {{ booking.startTime }}
          </li>
        </ul>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue';
  import EmployeeService from '../services/EmployeeServices'; // Correct path to your service
  import BookingService from '.././services/BookingServices'; // Path to your BookingService
  import type { Employee } from '../models/Employee'; // Import the Employee interface
  import type { Booking } from '../models/Booking'; // Import the Booking interface
  
  const employees = ref<Employee[]>([]); // Ref to store employees
  const bookings = ref<Booking[]>([]); // Ref to store bookings
  const message = ref<string>(''); // Ref to store response message for employees
  const bookingMessage = ref<string>(''); // Ref to store response message for bookings
  
  // Function to fetch all employees
  const fetchEmployees = async () => {
    try {
      const response = await EmployeeService.getAllEmployees();
      console.log("Fetched employees:", response.data);
      employees.value = response.data; // Set the employees from response
    } catch (error) {
      console.error("Error fetching employees:", error);
    }
  };
  
  // Function to register a hardcoded employee
  const registerEmployee = async () => {
    try {
      const response = await EmployeeService.registerHardcodedEmployee();
      message.value = response.data; // Set the response message
      fetchEmployees(); // Refresh the employee list after registering
    } catch (error) {
      console.error("Error registering employee:", error);
    }
  };
  
  // Function to create a hardcoded booking
  const createHardcodedBooking = async () => {
    try {
      const response = await BookingService.createHardcodedBooking();
      bookingMessage.value = response.data; // Set the response message for bookings
      fetchBookings(); // Refresh the booking list after creating
    } catch (error) {
      console.error("Error creating booking:", error);
    }
  };
  
  // Function to fetch all bookings
  const fetchBookings = async () => {
  try {
    const bookingsList = await BookingService.getAllBookings(); // Get the list of bookings
    bookings.value = bookingsList; // Directly set the bookings from the response
  } catch (error) {
    console.error("Error fetching bookings:", error);
  }
};
  
  // Fetch employees and bookings when the component is mounted
  onMounted(() => {
    fetchEmployees();
    fetchBookings();
  });
  </script>
  
  <style scoped>
  
  .container {
    display: flex;
    justify-content: space-between;
    margin: 20px;
  }
  
  .left-side,
  .right-side {
    width: 48%; /* Adjust width for each side */
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: white;
    color: black;
  }
  
  h1 {
    color: #333;
  }
  
  button {
    margin: 10px 0;
    padding: 10px;
  }
  
  ul {
    list-style: none;
    padding: 0;
  }
  
  li {
    padding: 5px 0;
  }
  </style>
  