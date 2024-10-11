<script setup lang="ts">

import { defineComponent } from 'vue'
import { Carousel, Navigation, Pagination, Slide } from 'vue3-carousel'
import WelcomeImage from '../assets/Welcome.png'

import 'vue3-carousel/dist/carousel.css'

// Array af billed-URL'er
const slides = [
  { id: 1, image: WelcomeImage, text: 'Welcome!', subtitle: 'Enjoy your time with Go Karts and more fun activities!' },
  { id: 2, image: 'https://img.freepik.com/premium-photo/gokart-racing-scenic-outdoor-track-daylight_922936-41831.jpg?w=1800', text: 'Real life Mario Kart!', subtitle: '' },
  { id: 3, image: 'https://img.freepik.com/premium-photo/capture-chaos-closequarters-paintball-battle-indoor-arena_1308549-18552.jpg?w=1380', text: 'Shoot your Boss!', subtitle: '' },
  { id: 4, image: 'https://via.placeholder.com/800x400?text=Image+3', text: 'Tackle your boyfriend!', subtitle: '' },
  { id: 5, image: 'https://via.placeholder.com/800x400?text=Image+4', text: 'Fourth Slide Text', subtitle: '' },
];

</script>

<template>
  <Carousel>
    <Slide v-for="slide in slides" :key="slide.id">
      <div class="carousel_item">
        <img :src="slide.image" class="carousel_image" />
        <div class="carousel_text">
          <!-- Brug text som title -->
          <h1>{{ slide.text }}</h1>
          <p v-if="slide.subtitle" class="carousel_subtitle">{{ slide.subtitle }}</p>
        </div>
      </div>
    </Slide>

    <template #addons>
      <Navigation />
      <Pagination />
    </template>
  </Carousel>
</template>

<style scoped>
.carousel_item {
  position: relative;
  min-height: 400px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.carousel_image {
  width: 100%;
  height: 600px;
  border-radius: 8px;
  object-fit: cover;
}

h1 {
  font-family: "Galada", cursive;
  font-weight: 400;
  font-style: italic;
  letter-spacing: .10em;
  font-size: 6rem;
  color: var(--action-yellow);
  padding:0;
  margin:0;
}

.carousel_text {
  position: absolute;
  text-align: center;
  background-color: rgba(0, 0, 0, 0.7); /* Halvgennemsigtig baggrund */
 /* padding: 10px;
  border-radius: 5px;

  */
  width: 100%;
  transform: translateY(-50%);
}

.carousel_subtitle {
  font-family: "Andale Mono";
  font-size: 1.7rem;
  font-weight: bolder;
  color: var(--action-red);
  margin-bottom: 2rem;
}


.carousel_slide {
  padding: 10px;
}

.carousel__prev,
.carousel__next {
  width: 50px;
  height: 50px;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  font-size: 30px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.carousel__prev {
  left: 20px; /* Placering og mørk kant på venstre side */
}

.carousel__next {
  right: 20px; /* Placering og mørk kant på højre side */
}

/* Når man hover på pilene */
.carousel__prev:hover,
.carousel__next:hover {
  background-color: rgba(0, 0, 0, 0.8); /* Gør baggrunden mørkere ved hover */
}

/* Stil for pagination (prikkerne) */
.carousel__pagination {
  position: absolute;
  bottom: 10px; /* Placer pagination i bunden af billedet */
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 10px;
  color: #f9f9f9;
}

.carousel__pagination-item {
  width: 15px;
  height: 15px;
  background-color: rgba(255, 255, 255, 0.8); /* Hvid farve */
  border-radius: 50%;
  cursor: pointer;
}

.carousel__pagination-dot-active {
  background-color: white; /* Gør den aktive dot mere tydelig */
}

button {
  color: #f9f9f9;
}

</style>