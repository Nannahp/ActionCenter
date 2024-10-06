<template>
  <div>
    <h2>New Booking</h2>
    <form @submit.prevent="submitBooking">

      <BaseInput
          id="activityName"
          name="activityName"
          labelFor="activityName"
          labelText="Activity name:"
          placeholder="eg. Laser tag"
          v-model="booking.activityName"
          required
      />

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
      <BaseButton
          type="submit"
          text="Save"/>
    </form>
  </div>
</template>

<script>
import BaseInput from "@/components/BaseInput.vue";
import BaseButton from "@/components/BaseButton.vue";

export default {
  components: {BaseButton, BaseInput},
  data() {
    return {
      booking: {
        date: '',
        startTime: '',
        endTime: '',
        activityName: '',
        customerName: '',
        email: '',
        employeeId: '',
      },
    };
  },
  methods: {
    submitBooking() {
      console.log("Booking submitted:", this.booking);
      // You can use axios or fetch to post data to the backend
      // axios.post('/api/bookings', this.booking).then(response => { ... })
    },
  },
};
</script>

<style scoped>

</style>
