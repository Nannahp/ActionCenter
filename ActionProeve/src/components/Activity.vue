<!-- eslint-disable vue/multi-word-component-names -->
 <!--
<script setup lang="ts">
import {computed, onMounted, ref, type Ref} from 'vue'
import axios from "axios";

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
  <div>
    <h2>{{ props.activity.activityName }}</h2>
    <p>Times: {{ props.activity.durations.join(', ') }}</p>
    <p>{{ props.activity.information }}</p>
    <img src="props.activity.image" :alt="props.activity.activityName" style="max-width: 200px; height: auto;" />
  </div>
</template>

<style scoped>

</style>
-->