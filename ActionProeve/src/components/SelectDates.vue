<script setup lang="ts">
import { ref, computed } from 'vue';
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue';
import RightArrowIcon from '@/components/icons/RightArrowIcon.vue';

// For emitting the selected day
const emit = defineEmits(['day-selected']);

// Current date and month setup
const currentDate = ref(new Date());
const currentMonth = ref(currentDate.value.getMonth());
const currentYear = ref(currentDate.value.getFullYear());

// Current month name
const currentMonthName = computed(() => {
  const monthName = new Date(currentYear.value, currentMonth.value).toLocaleString('en-US', {
    month: 'long',
  });

  return monthName.charAt(0).toUpperCase() + monthName.slice(1).toLowerCase();
});

// Day names
const dayNames = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];

// Days in the current month
const daysInMonth = computed(() => {
  const firstDayOfMonth = new Date(currentYear.value, currentMonth.value, 1);
  const lastDayOfMonth = new Date(currentYear.value, currentMonth.value + 1, 0);

  const firstDayIndex = (firstDayOfMonth.getDay() + 6) % 7; // Shift so Monday is index 0
  const days = [];

  for (let i = 0; i < firstDayIndex; i++) {
    days.push(null);
  }

  for (let i = 1; i <= lastDayOfMonth.getDate(); i++) {
    days.push(new Date(currentYear.value, currentMonth.value, i));
  }

  while (days.length < 42) {
    days.push(null);
  }

  return days;
});

// Week numbers calculation
const weekNumbers = computed(() => {
  const weeks = [];
  let currentWeekNumber = getWeekNumber(
      daysInMonth.value[daysInMonth.value.findIndex((day) => day !== null)]
  );

  for (let i = 0; i < Math.ceil(daysInMonth.value.length / 7); i++) {
    const weekDays = daysInMonth.value.slice(i * 7, (i + 1) * 7);
    if (weekDays.some((day) => day !== null)) {
      weeks.push(currentWeekNumber++);
    } else {
      weeks.push(null);
    }
  }

  return weeks;
});

// Calculate week number
function getWeekNumber(date: Date | null): number {
  if (!date) return 0;
  const startDate = new Date(date.getFullYear(), 0, 1);
  const daysDifference = Math.floor((date.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000));
  return Math.ceil((daysDifference + startDate.getDay() + 1) / 7);
}

// Check if today
function isToday(day: Date | null) {
  if (!day) return false;
  const today = new Date();
  return (
      today.getDate() === day.getDate() &&
      today.getMonth() === day.getMonth() &&
      today.getFullYear() === day.getFullYear()
  );
}

// Month navigation functions
function prevMonth() {
  currentMonth.value--;
  if (currentMonth.value < 0) {
    currentMonth.value = 11;
    currentYear.value--;
  }
}

function nextMonth() {
  currentMonth.value++;
  if (currentMonth.value > 11) {
    currentMonth.value = 0;
    currentYear.value++;
  }
}

// Selected days logic
const selectedDays = ref<Date[]>([]);

function selectDay(day: Date) {
  const today = new Date();
  today.setHours(0, 0, 0, 0);

  // Prevent selecting past days
  if (day < today) {
    return;
  }

  if (selectedDays.value.some(selectedDay => selectedDay.toDateString() === day.toDateString())) {
    selectedDays.value = selectedDays.value.filter(selectedDay => selectedDay.toDateString() !== day.toDateString());
  } else {
    selectedDays.value.push(day);
  }

  // Emit the selected days to the parent component
  emit('day-selected', day);
}


</script>

<template>
  <div class="month-view">
    <div class="calender-header">
      <LeftArrowIcon @click="prevMonth"/>
      <h3>{{ currentMonthName }} {{ currentYear }}</h3>
      <RightArrowIcon @click="nextMonth"/>
    </div>

    <div class="calender-day-names">
      <div v-for="(day, index) in dayNames" :key="index" class="calender-day-name">{{ day }}</div>
    </div>

    <div class="calender-grid">
      <div v-for="(weekNumber, weekIndex) in weekNumbers" :key="weekIndex" class="week-row">
        <div v-if="weekNumber !== null" class="week-number">{{ weekNumber }}</div>

        <div
            v-for="(day, dayIndex) in daysInMonth.slice(weekIndex * 7, (weekIndex + 1) * 7)"
            :key="dayIndex"
            class="calender-day"
            :class="{ 'current-day': isToday(day), 'empty-day': day === null, 'selected-day': selectedDays.some(selectedDay => selectedDay.toDateString() === day?.toDateString()) }"
            @click="day && selectDay(day)"
        >
          {{ day ? day.getDate() : '' }}
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.month-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  box-sizing: border-box;
}

h3 {
  color: #1f2023;
}

.calender-header {
  display: flex;
  justify-content: space-between;
  width: 80%;
  margin-bottom: 20px;
  font-size: x-large;
  color: #1f2023;
}

.calender-day-names {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  width: 75%;
  text-align: center;
  font-weight: bold;
  margin-bottom: 20px;
  margin-left: 50px;
}

.calender-grid {
  display: grid;
  grid-template-columns: auto repeat(7, 1fr);
  width: 80%;
  gap: 10px;
}

.week-row {
  display: contents;
}

.week-number {
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  width: 40px;
}

.calender-day {
  width: 100%;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #ccc;
  cursor: pointer;
}

.empty-day {
  border: none;
  cursor: default;
}

.current-day {
  border: 2px solid #0056b3;
}

.selected-day {
  background-color: #d1e7dd; /* Change this color to fit your design */
  border: 2px solid #0056b3; /* Optional: highlight selected days */
}

.selected-days {
  margin-bottom: 10px;
  text-align: center;
  font-weight: bold;
  width: 30em;
}

.selected-day {
  margin: 0 5px;
}
</style>
