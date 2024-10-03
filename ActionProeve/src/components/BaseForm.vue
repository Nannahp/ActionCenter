<script setup lang="ts">
import InputText from './InputText.vue'
import InputPassword from './InputPassword.vue'
import ButtonSubmit from './ButtonSubmit.vue'

defineProps<{}>()
</script>

<template>
  <form action="">
    <InputText labelText="Username" :required="true" placeholder="JohnDoe" />
    <InputPassword labelText="Password" />
    <ButtonSubmit text="Login" />
  </form>
</template>

<style>
/* 
bio agtigt lol i know its the wrong one
form {
  display: flex;
  flex-direction: column;
  width: 20vw;
  gap: 2rem;
  background-color: beige;
  padding: 2rem 3rem;
  border: double rgb(107, 103, 28) 4px;
} */

form {
  display: flex;
  flex-direction: column;
  padding: 1rem;
  border: solid black 1px;
  color: black;
}
</style>
