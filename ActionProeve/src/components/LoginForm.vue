<script setup lang="ts">
import { ref } from 'vue'
import BaseInput from './BaseInput.vue'
import BaseButton from './BaseButton.vue'

defineProps<{}>()

// Vi har ikke brug for gender i denne component men det var for at teste
// baseInput med type option hehe

const username = ref('')
const gender = ref('')

function handleSubmit() {
  console.log('Username:', username.value)
  console.log('Gender:', gender.value)
}
</script>

<template>
  <form @submit.prevent="handleSubmit">
    <BaseInput
      id="username"
      name="username"
      labelFor="username"
      labelText="Username"
      placeholder="JohnDie"
      v-model="username"
      required
    />

    <BaseInput
      id="password"
      name="password"
      type="password"
      labelFor="password"
      labelText="Password"
      placeholder="pissword"
      v-model="password"
      required
    />

    <BaseInput
      id="gender"
      name="gender"
      labelFor="gender"
      labelText="Gender"
      type="select"
      v-model="gender"
      :options="[
        { label: 'Male', value: 'male' },
        { label: 'Female', value: 'female' },
        { label: 'Other', value: 'other' }
      ]"
      required
    />
    <BaseButton type="submit" text="Submit" />
  </form>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  max-width: 300px;
  margin: 0 auto;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}

button {
  padding: 0.75rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
