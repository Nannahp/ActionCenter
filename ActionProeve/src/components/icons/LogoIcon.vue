<script setup>
import BaseImage from '../BaseImage.vue'
import logo from '../../assets/logo.png'
</script>

<template>
  <div class="logo">
    <BaseImage :src="logo" />
  </div>
</template>

<style scoped>
img {
  width: 100%;
  height: 100%;
}

.logo {
  width: 150px;
}
</style>
