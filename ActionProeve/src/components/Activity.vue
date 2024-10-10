<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
import missingImage from '../assets/missingpicture.png'

const props = defineProps<{
  activity: {
    activityName: string
    durations: string[]
    information: string
    price: number
    minPeople: number
    maxPeople: number
    image: string
  }
  isReversed: boolean
}>()

// //Tjekker om der er et image i jsonfilen, og ellers bruger den det lokale billede.
// const imageMissing = props.activity.image || missingImage
</script>

<template>
  <div class="activity" :class="{ reverse: props.isReversed }">
    <div class="activity-details">
      <h2>{{ props.activity.activityName }}</h2>

      <p class="info-line">Choose between:</p>
      <p>{{ props.activity.durations.join(', ') }}</p>

      <p class="info-line">Info:</p>
      <p>{{ props.activity.information }}</p>

      <p class="info-line">Price:</p>
      <p>{{ props.activity.price }} DKK</p>

      <p class="info-line">Participants:</p>
      <p>Min: {{ props.activity.minPeople }} | Max: {{ props.activity.maxPeople }}</p>
    </div>

    <div class="activity-image image" :style="{ backgroundImage: `url(${props.activity.image})` }">
      <!-- <div class="image" :alt="props.activity.activityName + ' Image'"></div> -->
    </div>
  </div>
</template>

<style scoped>
.activity {
  display: flex;
  justify-content: space-around;
  align-items: flex-start;
  margin-bottom: 150px;
  margin-top: 40px;
  height: 100%;
  padding: 0 50px;
}

.activity.reverse {
  flex-direction: row-reverse;
}

.activity-details {
  flex: 1;
  max-width: 40vw;
}

.activity.reverse activity-details {
  margin-left: 0px;
  padding-right: 150px;
}

.activity-image {
  flex: 1;
  text-align: center;
}

.activity-image {
  flex: 1;
  text-align: center;
  height: 400px;
  width: 40vw;
  background-size: cover;
  background-position: center;
}
.activity.reverse .activity-image {
  margin-right: 0;
}

.image {
  max-width: 45vw;
  max-height: 400px;
  transition: transform 0.3s ease;
}

.image:hover {
  transform: scale(1.1);
}

.info-line {
  font-weight: bold;
  font-size: 1.3rem;
  margin-bottom: 0;
  color: var(--action-yellow);
}

p {
  margin-top: 0;
  line-height: 1.6;
}
</style>
