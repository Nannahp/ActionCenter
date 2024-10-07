<script setup>
import BaseImage from '../BaseImage.vue'
import arrow from '../../assets/left-arrow.png'
</script>

<template>
  <div class="arrow">
    <BaseImage :src="arrow" />
  </div>
</template>

<style scoped>
img {
  width: 100%;
  height: 100%;
}

.arrow {
  width: 150px;
}
</style>
