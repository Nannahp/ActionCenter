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
  overskrift: string
}>()

const questions = ref([
  'Får du energi af at være sammen med mange mennesker?',
  'Foretrækker du sociale arrangementer frem for en aften alene?',
  'Kan du godt lide at tale med fremmede?',
  'Bliver du hurtigt rastløs, når du er alene i længere tid?',
  'Trives du i store grupper af mennesker?',
  'Kan du lide at tage initiativ til sociale sammenkomster?',
  'Føler du dig ofte fyldt med energi efter sociale aktiviteter?',
  'Bliver du hurtigt træt af at være alene?',
  'Kan du lide at tage styring i samtaler?',
  'Nyder du at netværke og møde nye mennesker?',
  'Foretrækker du at have mange forskellige sociale aktiviteter i løbet af ugen?',
  'Bliver du rastløs, hvis du har en stille weekend uden planer?',
  'Nyder du at være centrum for opmærksomheden?',
  'Har du brug for hyppig kontakt med andre mennesker for at føle dig glad?',
  'Føler du, at du får energi af at tale med mange forskellige mennesker i løbet af en dag?'
])

// opretter answers, som er en tom array af typen boolean, som derfor
//kan indeholder true/false. ref gør den reaktiv.
const answers: Ref<boolean[]> = ref([])

//Tjekker at answers er lige så lang som spørgsmål, og opdaterer answers array.
//true hvis de er færdig med teste, false hvis de ikke er.
const isDone = computed(() => answers.value.length >= questions.value.length)

const nextQuestion = computed(() => {
  const nextQuestionIndex = answers.value.length
  const nextQuestion = questions.value[nextQuestionIndex]
  return nextQuestion
})

const introExtroStr = computed(() => {
  const introvertCount = answers.value.filter((value) => value === false).length
  const extrovertCount = answers.value.filter((value) => value === true).length
  return introvertCount > extrovertCount ? 'introvert' : 'ekstrovert'
})

const isIntrovert = computed(() => {
  const introvertCount = answers.value.filter((value) => value === false).length
  const extrovertCount = answers.value.filter((value) => value === true).length
  return introvertCount > extrovertCount
})
</script>

<template>
  <h1>{{ overskrift }}</h1>
  <p>{{ activityName }}</p>

  <div v-if="!isDone">
    <p class="progress">
      Process: Du er i gang med at svare på spørgsmål {{ answers.length + 1 }} of
      {{ questions.length }}.
    </p>
    <p class="question">{{ nextQuestion }}</p>

    <button @click="answers.push(true)">Jep, det er mig!</button>
    <button @click="answers.push(false)">Nope, genkender jeg ikke!</button>
  </div>

  <div v-else-if="isIntrovert">Du er ... 🥁 ... {{ introExtroStr }}!! Herligt!! Total hygge!</div>
  <div v-else>Du er ... 🥁 ... {{ introExtroStr }}!! Juhhuu!! Tillykke!!</div>
</template>

<style scoped>
.question {
  border: 2px solid black;
  padding: 1em;
}
button {
  padding: 1em;
  margin: 1em;
}
.progress {
  padding: 1em;
}
</style>
