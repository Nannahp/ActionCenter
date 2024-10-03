<script setup lang="ts">
defineProps<{
  text: string
  type: string
}>()
</script>

<template>
  <button :type="type">{{ text }}</button>
</template>
