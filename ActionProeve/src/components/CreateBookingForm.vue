<template>
  <div class="create-booking-form-backdrop">
    <div class="create-booking-form">
      <h2>New Booking</h2>
      <form @submit.prevent="submitBooking">
        <BaseButton text="✖" type="button" class="close-button" @click="closeForm" />

        <BaseSelectInput
            id="activityName"
            name="activityName"
            labelText="Activity:"
            placeholder="Please select an activity"
            :options="activities"
            v-model="booking.activityName"
            required
        />


<!--        <label for="activityName">Activity:</label>
        <select id="activityName" v-model="booking.activityName" required>
          <option disabled value="">Please select an activity</option>
          <option v-for="activity in activities" :key="activity.activityName" :value="activity.activityName">
            {{ activity.activityName }}
          </option>
        </select>-->

        <BaseInput
          id="customerName"
          name="customerName"
          labelFor="customerName"
          labelText="Name:"
          placeholder="John Doe"
          v-model="booking.customerName"
          required
      />

        <BaseInput
          id="email"
          name="email"
          labelFor="email"
          labelText="Email:"
          placeholder="Johndoe@gmail.com"
          v-model="booking.email"
          required
      />

        <div>
        <input type="hidden" id="employee" v-model="booking.employee" required />
        </div>

        <BaseInput
          id="date"
          name="date"
          labelFor="date"
          labelText="Date:"
          placeholder=""
          type="date"
          v-model="booking.date"
          required
      />

        <BaseInput
          id="startTime"
          name="startTime"
          labelFor="startTime"
          labelText="Start time:"
          placeholder="12:00"
          type="time"
          v-model="booking.startTime"
          required
      />

        <BaseInput
          id="endTime"
          name="endTime"
          labelFor="endTime"
          labelText="end time:"
          placeholder="18:00"
          type="time"
          v-model="booking.endTime"
          required
      />

        <p>{{ submissionStatus }}</p>

        <BaseButton
          type="submit"
          text="Save"/>
      </form>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import BaseInput from "@/components/BaseInput.vue";
import BaseButton from "@/components/BaseButton.vue";
import axios from "axios";
import BaseSelectInput from "@/components/BaseSelectInput.vue";

let activities = ref([]);

onMounted(async () => {
  try {
    const { data } = await axios.get('http://localhost:8080/activities');
    activities.value = data.map(activity => ({ value: activity.activityName, text: activity.activityName }));
  } catch (error) {
    console.error('Error fetching activities:', error);
  }
});

const emit = defineEmits(['close-form']);

let booking = ref({
  date: '',
  startTime: '',
  endTime: '',
  activityName: '',
  customerName: '',
  email: '',
  employeeId: 1, //just for testing
});

async function submitBooking() {
  console.log("Booking submitted:", booking.value);
  try {
    const response = await axios.post('http://localhost:8080/api/bookings/createBooking', booking.value);
    console.log("Response:", response.data);

    // If the submission is successful, update status and clear the form
    booking.value = resetBookingForm(); // Reset the form after successful submission

    closeForm(); // Close the form after successful submission
  } catch (error) {
    console.error("Error submitting booking:", error);
  }
}

function resetBookingForm() {
  // Reset the form fields after successful submission
  return {
    date: '',
    startTime: '',
    endTime: '',
    activityName: '',
    customerName: '',
    email: '',
    employeeId: '',
  };
}

function closeForm() {
  emit('close-form');
}
</script>

<style scoped>
.create-booking-form {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
  background: white;
  padding: 20px;
  border-radius: 10px;
  width: 80%;
  max-width: 500px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
}

.create-booking-form .close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
}

.create-booking-form-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 999;
}
</style>
