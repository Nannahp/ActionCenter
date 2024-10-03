<template>
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
      />
      <div class="button-wrapper">
        <button class="button" @click="nextSlide">Next</button>
      </div>
    </div>

    <div v-if="currentSlide === 2">
      <BaseInput
        labelText="Enter activity description:"
        labelFor="activityDescription"
        placeholder="fx: Our badminton court can fit up to 4 people..."
        id="activityDescription"
        name="activityDescription"
        type="textarea"
        v-model="activityDescription"
        
      />
      <div class="button-wrapper">
        <button class="button" @click="nextSlide">Next</button>
      </div>
    </div>

    <div v-if="currentSlide === 3">
        <BaseInput
          labelText="Enter time in minutes (comma seperated):"
          labelFor="activityTime"
          placeholder="fx: 30, 45, 60"
          id="activityTime"
          name="activityTime"
          v-model="activityTimes"
        />
      <div class="button-wrapper">
        <button class="button" @click="submitActivity">Submit</button>
      </div>
    </div>

    <div v-if="currentSlide === 4">
      <div class="input">
        <h3 v-if="submitted">Activity added!</h3>
        <p>You can close this form now</p>
        <button @click="exitForm">Close</button>
      </div>
    </div>
  </div>
</template>

<script>
import BaseInput from '@/components/BaseInput.vue'; // Adjust the import path as needed
import axios from "axios";
export default {
  components: {
    BaseInput,
  },
  data() {
    return {
      currentSlide: 1,
      activityName: '',
      activityTimes:'',
      activityDescription: '',
      submitted: false,
    };
  },
  methods: {
    nextSlide() {
      this.currentSlide += 1;
    },
    async submitActivity() {
      const timesArray = this.activityTimes.split(',')
      .map(time => parseFloat(time.trim()))
      .filter(time => !isNaN(time)); // filter out anything that is not a number

      const activity = {
        name: this.activityName,
        times: timesArray,
        description: this.activityDescription,
      };
      try {
        await axios.post('http://localhost:8080/add-activity', activity);
        this.submitted = true;
        this.currentSlide += 1;
        this.$emit('activitySubmitted', activity); 
      }
      catch (error) {
        console.error("error submitting actitivy: ", error)
      }
  
    },
    exitForm() {
      this.resetForm();
      this.$emit('exitForm');
    },
    resetForm() {
      this.currentSlide = 1;
      this.activityName = '';
      this.activityDescription = '';
      this.activityTimes = ''; 
      this.submitted = false; 
    },
  },
};
</script>

<style scoped>
.form {
  border: solid black;
  border-radius: 1rem;
  width: 50%;
  margin: auto;
  padding: 2%;
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
</style>
