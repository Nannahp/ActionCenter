<script setup lang="ts">
import { ref } from 'vue';
import BaseInput from '@/components/BaseInput.vue'; 
import BaseButton from './BaseButton.vue';
import axios from 'axios';

const emit = defineEmits(['exitForm']);
const currentSlide = ref(1);
const activityName = ref('');
const durations = ref('');
const information = ref('');
const image = ref('');
const submitted = ref(false);
const minPeople = ref('');
const maxPeople = ref ('');
const price = ref('');

function nextSlide() {
  currentSlide.value += 1;
}

async function handleSubmit() {
  const timesArray = durations.value.split(',')
    .map(time => parseFloat(time.trim()))
    .filter(time => !isNaN(time));

  const activity = {
    activityName: activityName.value,
    durations: timesArray,
    information: information.value,
    image: image.value,
    minPeople: minPeople.value,
    maxPeople: maxPeople.value,
    price: price.value,
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
  information.value = '';
  durations.value = '';
  image.value = ''; 
  minPeople.value = ('');
  maxPeople.value = ('');
  price.value = ('');
  submitted.value = false; 
}
</script>

<template>
  <form @submit.prevent="currentSlide === 3 ? handleSubmit() : nextSlide()">
    <BaseButton text="✖" type="button" class="close-button" @click="exitForm" />
    <h2>Add New Activity</h2>
    <div v-if="currentSlide === 1">
      <div class="input-wrapper">
        <div class="input">

          <BaseInput
            id="activityName"
            name="activityName"
            v-model="activityName"
            placeholder="fx: Badminton"
            required
            class="small-input"
            labelText="Enter title of activity:"
            labelFor="activityName"
          />

          <label for="activityInformation" class="input-label">Enter activity information:</label>
          <textarea
            id="information"
            name="information"
            v-model="information"
            placeholder="fx: Our badminton court can fit up to 4 people..."
            required
            class="big-input"
             labelFor="information"
          ></textarea>
        </div>
        <div class="button-wrapper">
          <BaseButton text="Next" type="submit" />
        </div>
      </div>
    </div>

    <div v-if="currentSlide === 2">
      <div class="input-wrapper">
        <div class="input">
    
          <BaseInput
            id="durations"
            name="durations"
            v-model="durations"
            placeholder="fx: 30, 45, 60"
            required
            class="small-input"
            labelText="Enter time in minutes (comma separated):"
            labelFor="durations"
            
          />

        
          <BaseInput
            id="image"
            name="image"
            v-model="image"
            placeholder="fx: https://example.com/image.jpg"
            required
            class="small-input"
            labelText="Enter an image URL:"
            labelFor="image"
            type="URL"
          />
        </div>
        <div class="button-wrapper">
          <BaseButton text="Submit" type="submit" />
        </div>
      </div>
    </div>

    <div v-if="currentSlide === 3">
      <div class="input-wrapper">
        <div class="input">
    
          <BaseInput
            id="minPeople"
            name="minPeople"
            v-model="minPeople"
            placeholder="fx: 6"
            required
            class="small-input"
            labelText="Enter minimum reqiured people:"
            labelFor="minPeople"
            type="number"
          />
        
          <BaseInput
            id="maxPeople"
            name="maxPeople"
            v-model="maxPeople"
            placeholder="fx: 20"
            required
            class="small-input"
            labelText="Enter maximum number of people:"
            labelFor="maxPeople"
            type="number"
          />
          <BaseInput
            id="price"
            name="price"
            v-model="price"
            placeholder="fx: 200"
            required
            class="small-input"
            labelText="Enter the price per person:"
            labelFor="price"
            type="number"
          />
        </div>
        <div class="button-wrapper">
          <BaseButton text="Submit" type="submit" />
        </div>
      </div>
    </div>

    <div v-if="currentSlide === 4">
      <div class="input-wrapper">
        <div class="success-message">
          <h3 v-if="submitted">Activity added!</h3>
          <p>You can close this form now</p>
        </div>
        <div class="button-wrapper">
        <BaseButton text="Close" type="button" @click="exitForm" />
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
  height: 27em;
  margin: 0 auto;
  padding: 2rem;
  text-align: left;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  position: relative; /* Add this to position the close button inside the form */
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