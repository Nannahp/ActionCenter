<script setup lang="ts">
import { ref, computed } from 'vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import RightArrowIcon from '@/components/icons/RightArrowIcon.vue'

//For emitting the selected day
const emit = defineEmits(['day-selected'])

const currentDate = ref(new Date())
const currentMonth = ref(currentDate.value.getMonth())
const currentYear = ref(currentDate.value.getFullYear())

const currentMonthName = computed(() => {
  const monthName = new Date(currentYear.value, currentMonth.value).toLocaleString('en-US', {
    month: 'long'
  });

  // Capitalize the first letter and ensure the rest of the letters are lowercase
  return monthName.charAt(0).toUpperCase() + monthName.slice(1).toLowerCase();
});

const dayNames = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']

const daysInMonth = computed(() => {
  const firstDayOfMonth = new Date(currentYear.value, currentMonth.value, 1)
  const lastDayOfMonth = new Date(currentYear.value, currentMonth.value + 1, 0)

  //Get the index of the first day of the month (0 = Sunday, 6 = Saturday)
  const firstDayIndex = (firstDayOfMonth.getDay() + 6) % 7 //Shift so Monday is index 0

  const days = []

  //Fills empty days first
  for (let i = 0; i < firstDayIndex; i++) {
    days.push(null)
  }

  //Actual days
  for (let i = 1; i <= lastDayOfMonth.getDate(); i++) {
    days.push(new Date(currentYear.value, currentMonth.value, i))
  }

  //Fill rest of the calendar to ensure it always has 42 days
  while (days.length < 42) {
    days.push(null);
  }

  return days
})

//To calculate the correct week numbers in the year
const weekNumbers = computed(() => {
  const weeks = []
  let currentWeekNumber = getWeekNumber(
    daysInMonth.value[daysInMonth.value.findIndex((day) => day !== null)]
  )

  // Iterate over each week and check if the week contains any valid (non-null) days
  for (let i = 0; i < Math.ceil(daysInMonth.value.length / 7); i++) {
    const weekDays = daysInMonth.value.slice(i * 7, (i + 1) * 7);

    // Only push the week number if there is at least one valid day in the week
    if (weekDays.some(day => day !== null)) {
      weeks.push(currentWeekNumber++);
    } else {
      weeks.push(null); // Push null for weeks that are entirely empty
    }
  }

  return weeks
})

//Calculate the week number of the year for a given date
function getWeekNumber(date: Date | null): number {
  if (!date) return 0
  const startDate = new Date(date.getFullYear(), 0, 1)
  const daysDifference = Math.floor((date.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000))
  return Math.ceil((daysDifference + startDate.getDay() + 1) / 7)
}

function isToday(day: Date | null) {
  if (!day) return false

  const today = new Date()
  return (
    today.getDate() === day.getDate() &&
    today.getMonth() === day.getMonth() &&
    today.getFullYear() === day.getFullYear()
  )
}

function prevMonth() {
  currentMonth.value--
  if (currentMonth.value < 0) {
    currentMonth.value = 11
    currentYear.value--
  }
}

function nextMonth() {
  currentMonth.value++
  if (currentMonth.value > 11) {
    currentMonth.value = 0
    currentYear.value++
  }
}

//Emit the selected day to the parent component
function selectDay(day: Date) {
  emit('day-selected', day)
}
</script>

<template>
  <div class="month-view">
    <div class="calender-header">
      <LeftArrowIcon @click="prevMonth"/>
      <h2>{{ currentMonthName }} {{ currentYear }}</h2>
      <RightArrowIcon @click="nextMonth"/>
    </div>

    <div class="calender-day-names">
      <div v-for="(day, index) in dayNames" :key="index" class="calender-day-name">{{ day }}</div>
    </div>

    <div class="calender-grid">
      <div v-for="(weekNumber, weekIndex) in weekNumbers" :key="weekIndex" class="week-row">
        <!-- Only show the week number if it's not null -->
        <div v-if="weekNumber !== null" class="week-number">{{ weekNumber }}</div>


        <!-- Days in week (including empty placeholders for filler days) -->
        <div
          v-for="(day, dayIndex) in daysInMonth.slice(weekIndex * 7, (weekIndex + 1) * 7)"
          :key="dayIndex"
          class="calender-day"
          :class="{ 'current-day': isToday(day), 'empty-day': day === null }"
          @click="day && selectDay(day)"
        >
          <!-- Shows day number only if day is not null -->
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
  width: 80vw;
  height: 80vh;
  padding: 20px;
  box-sizing: border-box;
}

.calender-header {
  display: flex;
  justify-content: space-between;
  width: 80%;
  margin-bottom: 20px;
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
  grid-template-columns: auto repeat(7, 1fr); /* Week number + 7 days */
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
  height: 60px;
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

button {
  background: none;
  color: #f0f0f0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: xx-large;
}

button:hover {
  color: #0056b3;
}
</style>
