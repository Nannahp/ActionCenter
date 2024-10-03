<template>
  <div>
    <h2>New Booking</h2>
    <form @submit.prevent="submitBooking">
      <div>
        <label for="activityName">Activity Name</label>
        <input type="text" id="activityName" v-model="booking.activityName" required />
      </div>

      <div>
        <label for="customerName">Customer Name</label>
        <input type="text" id="customerName" v-model="booking.customerName" required />
      </div>

      <div>
        <label for="phoneNumber">Phone Number</label>
        <input type="tel" id="phoneNumber" v-model="booking.phoneNumber" required />
      </div>

      <div>
        <label for="email">Email</label>
        <input type="email" id="email" v-model="booking.email" required />
      </div>

      <div>
        <input type="hidden" id="employee" v-model="booking.employee" required />
      </div>

      <div>
        <label for="date">Date</label>
        <input type="date" id="date" v-model="booking.date" required />
      </div>

      <div>
        <label for="startTime">Start Time</label>
        <input type="time" id="startTime" v-model="booking.startTime" required />
      </div>

      <div>
        <label for="endTime">End Time</label>
        <input type="time" id="endTime" v-model="booking.endTime" required />
      </div>

      <button type="submit">Submit Booking</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      booking: {
        date: '',
        startDateTime: '',
        endTime: '',
        activityName: '',
        customerName: '',
        phoneNumber: '',
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
