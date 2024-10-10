<script setup lang="ts">
import type { Booking } from "../models/Booking";
import BaseButton from "@/components/BaseButton.vue";

const props = defineProps<{
  isVisible: boolean;
  booking: Booking | null;
  day: Date | null;
}>()

const emit = defineEmits(['close']);

function close() {
  emit('close');
}

function formatTime(date: string, time: string) {
  const dateTimeString = `${date}T${time}`;
  return new Date(dateTimeString).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

function isFutureOrToday(day: Date | null) {
  if (!day) return false;

  const today = new Date();
  today.setHours(0, 0, 0, 0); // Remove time for accurate comparison
  return day.getTime() >= today.getTime(); // Returns true if the day is today or in the future
}
</script>

<template>
<!-- TODO -->
  <!-- denne så typescript ikke brokker sig aka docker buld, mht. if booking is null -->
  <div v-if="isVisible" class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <BaseButton text="✖" type="button" class="close-button" @click="close" />
      <h2>Booking Details</h2>
      <p v-if="booking"><strong>Activity:</strong> {{ booking.activityName }}</p>
      <p v-if="booking"><strong>Customer:</strong> {{ booking.customerName }}</p>
      <p v-if="booking"><strong>Date:</strong> {{ booking.date }}</p>
      <p v-if="booking"><strong>Start Time:</strong> {{ formatTime(booking.date, booking.startTime) }}</p>
      <p v-if="booking"><strong>End Time:</strong> {{ formatTime(booking.date, booking.endTime) }}</p>
      <p v-if="booking"><strong>Email:</strong> {{ booking.email }}</p>
      <BaseButton
          v-if="isFutureOrToday(day)"
          text="Delete Booking"
          type="button"
          class="delete-booking-btn"
          @click="close"
      />
 <!-- <div v-if="isVisible" class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <BaseButton text="✖" type="button" class="close-button" @click="close" />
      <h2>Booking Details</h2>
      <p><strong>Activity:</strong> {{ booking.activityName }}</p>
      <p><strong>Customer:</strong> {{ booking.customerName }}</p>
      <p><strong>Date:</strong> {{ booking.date }}</p>
      <p><strong>Start Time:</strong> {{ formatTime(booking.date, booking.startTime) }}</p>
      <p><strong>End Time:</strong> {{ formatTime(booking.date, booking.endTime) }}</p>
      <p><strong>Email:</strong> {{ booking.email }}</p>
      <BaseButton
          v-if="isFutureOrToday(day)"
          text="Delete Booking"
          type="button"
          class="delete-booking-btn"
          @click="close"
      /> -->
    </div>
  </div>
</template>


<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(8px); /* Blur effect for background */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  max-width: 400px;
  width: 100%;
  position: relative;
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

.delete-booking-btn {
  background: #0056b3;
  border: 1px solid #b3b3b3;
  font-size: 15px;
  cursor: pointer;
  margin: 0 auto;
  display: block;
  border-radius: 4px;
  padding: 10px;
}

h2 {
  text-align: center;
  color: #333333;
}

p {
  color: #333333;
}

strong {
  color: var(--text-color-light);
}
</style>
