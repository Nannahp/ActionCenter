<script setup lang="ts">
import { ref, defineEmits } from 'vue';
import BaseInput from '@/components/BaseInput.vue'; 
import BaseButton from './BaseButton.vue';
import axios from 'axios';

const emit = defineEmits(['exitForm']);
const currentSlide = ref(1);
const activityName = ref('');
const activityTimes = ref('');
const activityDescription = ref('');
const activityImage = ref('');
const submitted = ref(false);

function nextSlide() {
  currentSlide.value += 1;
}

async function handleSubmit() {
  const timesArray = activityTimes.value.split(',')
    .map(time => parseFloat(time.trim()))
    .filter(time => !isNaN(time));

  const activity = {
    name: activityName.value,
    times: timesArray,
    description: activityDescription.value,
    imageUrl: activityImage.value,
  };

  console.log("Submitting activity: ", activity);

  try {
    const response = await axios.post('http://localhost:8080/add-activity', activity);
    console.log("Response from server: ", response);
    submitted.value = true;
    currentSlide.value += 1; 
  } catch (error) {
    console.error("Error submitting activity: ", error);
  }
}

function exitForm() {
  resetForm();
  emit('exitForm');
}

function resetForm() {
  currentSlide.value = 1;
  activityName.value = '';
  activityDescription.value = '';
  activityTimes.value = '';
  activityImage.value = ''; 
  submitted.value = false; 
}
</script>

<template>
  <form @submit.prevent="currentSlide === 2 ? handleSubmit() : nextSlide()">
  
      <h2>Add New Activity</h2>
      <div class="input-wrapper">
        <div v-if="currentSlide === 1">
          <label for="activityName" class="input-label">Enter title of activity:</label>
          <input
            id="activityName"
            name="activityName"
            v-model="activityName"
            placeholder="fx: Badminton"
            required
            class="small-input"
          />

          <label for="activityDescription" class="input-label">Enter activity description:</label>
          <textarea
            id="activityDescription"
            name="activityDescription"
            v-model="activityDescription"
            placeholder="fx: Our badminton court can fit up to 4 people..."
            required
            class="big-input"
          ></textarea>

          <div class="button-wrapper">
            <BaseButton 
              text="Next"
              type="submit" 
            />
          </div>
        </div>
      </div>

      <div class="input-wrapper">
        <div v-if="currentSlide === 2">
          <label for="activityTimes" class="input-label">Enter time in minutes (comma separated):</label>
          <input
            id="activityTimes"
            name="activityTimes"
            v-model="activityTimes"
            placeholder="fx: 30, 45, 60"
            required
            class="small-input"
          />

          <label for="activityImage" class="input-label">Enter an image-url:</label>
          <input
            id="activityImage"
            name="activityImage"
            v-model="activityImage"
            placeholder="fx: https://example.com/image.jpg"
            required
            class="small-input"
          />

          <div class="button-wrapper">
            <BaseButton 
              text="Submit"
              type="submit" 
            />
          </div>
        </div>
      </div>

      <div class="input-wrapper">
        <div v-if="currentSlide === 3">
          <h3 v-if="submitted">Activity added!</h3>
          <p>You can close this form now</p>

          <BaseButton
            text="Close"
            type="button"
            @click="exitForm"
          />
        </div>
      </div>
  </form>
</template>

<style scoped>
/* Center the form on the page */
form {
  width: 40em;
  margin: 0 auto;
  padding: 2rem;
  text-align: left;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}

/* Styling for headers */
h2 {
  color: black;
  text-align: center;
}

/* Wrapper for form inputs */
.input-wrapper {
  margin-bottom: 1.5rem;
  width: 100%;
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
  text-align: center;
  margin-top: 1rem;
}

/* Button styling */
button {
  padding: 0.75rem 1.5rem;
  background-color: #0056b3;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
