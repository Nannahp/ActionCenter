<script setup lang="ts">
import { ref } from 'vue'
import BaseInput from './BaseInput.vue'
import BaseButton from './BaseButton.vue'
import axios from "axios";

defineProps<{}>()

// baseInput med type option hehe
const password = ref('');
const username = ref('')
const errorMessage = ref('');


async function handleSubmit() {
  errorMessage.value = ''; // Clear previous error message
  try {
    const response = await axios.post('http://localhost:8080/api/login/auth', {
      username: username.value,
      password: password.value
    }, {
      withCredentials: true //allows to send session cookies to the backend
    });

    // Store the username and admin status in localStorage
    localStorage.setItem('username', response.data.username);
    localStorage.setItem('isAdmin', String(response.data.isAdmin));

    //Reload or redirect to the main page
    window.location.reload();
  } catch (error) {
    console.log('Login failed:', error);
    errorMessage.value = 'Username or password incorrect';
  }
}

</script>

<template>
  <div class="form-container">
    <form @submit.prevent="handleSubmit">
      <BaseInput
          id="username"
          name="username"
          labelFor="username"
          labelText="Username"
          placeholder="marioMe"
          v-model="username"
          required
      />

      <BaseInput
          id="password"
          name="password"
          type="password"
          labelFor="password"
          labelText="Password"
          placeholder="passWord"
          v-model="password"
          required
      />

      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

      <BaseButton type="submit" text="Submit" @click="handleSubmit"/>
    </form>
  </div>
</template>


<style scoped>
.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

form {
  display: flex;
  flex-direction: column;
  max-width: 350px;
  width: 100%;
  padding: 2rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}

button {
  background-color: #0056b3;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  width: 90px;
  height: 45px;
  align-self: center;
  margin-top: 10px;
}

.error-message {
  color: red; /* Style for the error message */
  text-align: center; /* Center the error message */
  margin-top: 10px;
  margin-bottom: 10px;
  font-size: 16px;
}

</style>
