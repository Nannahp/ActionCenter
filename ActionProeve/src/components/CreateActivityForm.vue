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
    <div class="form">
      <h2>Add New Activity</h2>

      <div v-if="currentSlide === 1">
        <BaseInput
          labelText="Enter title of activity:"
          labelFor="activityName"
          placeholder="fx: Badminton"
          id="activityName"
          name="activityName"
          v-model="activityName"
          required
        />
        <BaseInput
          labelText="Enter activity description:"
          labelFor="activityDescription"
          placeholder="fx: Our badminton court can fit up to 4 people..."
          id="activityDescription"
          name="activityDescription"
          type="textarea"
          v-model="activityDescription"
          required
        />
        <div class="button-wrapper">
          <BaseButton 
            text="Next"
            type="submit" 
          />
        </div>
      </div>

      <div v-if="currentSlide === 2">
        <BaseInput
          labelText="Enter time in minutes (comma separated):"
          labelFor="activityTimes"
          placeholder="fx: 30, 45, 60"
          id="activityTimes"
          name="activityTimes"
          v-model="activityTimes"
          required
        />
        <BaseInput
          labelText="Enter an image-url: "
          labelFor="activityImage"
          placeholder="fx: https://example.com/image.jpg"
          id="activityImage"
          name="activityImage"
          v-model="activityImage"
          required
        />
        <div class="button-wrapper">
          <BaseButton 
            text="Submit"
            type="submit" 
          />
        </div>
      </div>

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
.form {
  border: solid black;
  border-radius: 1rem;
  width: 80%;
  max-width: 600px; 
  padding: 2rem;
  text-align: center;
}

.button-wrapper {
  padding: 4%;
}

.button {
  width: 5em;
  margin: auto;
  padding: 2%;
  font-size: 1em;
}
.input {
  width: 50%;
}
</style>
