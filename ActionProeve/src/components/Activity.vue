<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
import {computed, onMounted, ref, type Ref} from 'vue'
import axios from "axios";

// eslint-disable-next-line vue/no-dupe-keys
const activityName = ref('')

//Henter data fra backend, når komponentet er monteret.
onMounted(() => {
  axios.get('http://localhost:8080/activity')
      .then(response => {
        activityName.value = response.data.name
      })
      .catch(error =>{
        console.error('Fejl desværre: ', error)
      })
})

defineProps<{
  activityName: string
}>()

</script>

<template>
  <h1>{{ activityName }}</h1>

</template>

<style scoped>

</style>
