<script setup lang="ts">
import type { Booking } from "../models/Booking";
import BaseButton from "@/components/BaseButton.vue";

const props = defineProps<{
  isVisible: boolean;
  booking: Booking | null;
}>()

const emit = defineEmits(['close']);

function close() {
  emit('close');
}

function formatTime(date: string, time: string) {
  const dateTimeString = `${date}T${time}`;
  return new Date(dateTimeString).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}
</script>

<template>
  <div v-if="isVisible" class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <BaseButton text="✖" type="button" class="close-button" @click="close" />
      <h2>Booking Details</h2>
      <p><strong>Activity:</strong> {{ booking.activityName }}</p>
      <p><strong>Customer:</strong> {{ booking.customerName }}</p>
      <p><strong>Date:</strong> {{ booking.date }}</p>
      <p><strong>Start Time:</strong> {{ formatTime(booking.date, booking.startTime) }}</p>
      <p><strong>End Time:</strong> {{ formatTime(booking.date, booking.endTime) }}</p>
      <p><strong>Email:</strong> {{ booking.email }}</p>
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
