<script setup lang="ts">
import type { CSSProperties } from 'vue';
import {ref} from "vue";
import type {DutySchedule} from "../models/DutySchedule";
import ScheduleDetails from "@/components/ScheduleDetails.vue";

const props = defineProps<{
  employeeName: string;
  duties: DutySchedule[];
  employeeColor: string;
  day: Date | null;
  isAdmin: boolean;
}>();

const selectedDuty = ref<DutySchedule | null>(null);
const isModalVisible = ref(false);

function isWeekend(date: Date): boolean {
  const dayOfWeek = date.getDay();
  return dayOfWeek === 0 || dayOfWeek === 6; // Sunday = 0, Saturday = 6
}

function getEventStyle(duty: DutySchedule): CSSProperties {
  //So TS can read it
  const startDateTime = new Date(`${duty.date}T${duty.startTime}`);
  const endDateTime = new Date(`${duty.date}T${duty.endTime}`);

  const startHour = startDateTime.getHours();
  const startMinutes = startDateTime.getMinutes();
  const endHour = endDateTime.getHours();
  const endMinutes = endDateTime.getMinutes();

  //Check if booking date is weekend
  const isBookingOnWeekend = isWeekend(startDateTime);
  const openingHour = isBookingOnWeekend ? 12 : 10;
  const closingHour = 20;
  const totalHours = closingHour - openingHour;

  const top = Math.round(((startHour - openingHour) * 60 + startMinutes) * 100 / (totalHours * 60));
  const height = Math.round(((endHour - startHour) * 60 + (endMinutes - startMinutes)) * 100 / (totalHours * 60));

  return {
    top: `${top}%`,
    height: `${height}%`,
    backgroundColor: props.employeeColor || 'darkgray',
    position: 'absolute',
    width: '100%',
  };
}

function formatTime(date: string, time: string) {
  const dateTimeString = `${date}T${time}`;
  return new Date(dateTimeString).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

function openDutyDetails(duty: DutySchedule) {
  selectedDuty.value = duty;
  isModalVisible.value = true;
}

function closeModal() {
  isModalVisible.value = false;
}
</script>

<template>
  <div class="activity-column">
    <div class="event-grid">
      <div
          v-for="duty in duties"
          :key="duty.id"
          :style="getEventStyle(duty)"
          class="event-block"
          @click="openDutyDetails(duty)"
      >
        {{ formatTime(duty.date, duty.startTime) }} -
        {{ formatTime(duty.date, duty.endTime) }}
        <br />
        {{ employeeName || 'Unknown Employee'}}
      </div>
    </div>

    <ScheduleDetails
        :isVisible="isModalVisible"
        :duty-schedule="selectedDuty"
        :day="props.day"
        :is-admin="isAdmin"
        @close="closeModal"
    />
  </div>
</template>

<style scoped>
.activity-column {
  flex: 1;
  position: relative;
  margin-right: 5px;
}

.event-grid {
  position: relative;
  height: 80%;
}

.event-block {
  background-color: #b3b3b3;
  border-radius: 5px;
  padding-top: 5px;
  padding-left: 5px;
  padding-right: 5px;
  color: #1f2023;
  font-size: 12px;
  font-weight: bold;
  margin-bottom: 1px;
}
</style>