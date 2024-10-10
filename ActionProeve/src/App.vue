<script setup lang="ts">
import { onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

// Helper function to load and replace stylesheets
const loadStylesheet = (styleUrl: string) => {
  // Remove any existing dynamically added stylesheet
  const existingLink = document.getElementById('dynamic-stylesheet');
  if (existingLink) {
    existingLink.remove();  // This removes the previous stylesheet
  }

  // Create and add the new stylesheet
  const linkElement = document.createElement('link');
  linkElement.rel = 'stylesheet';
  linkElement.href = styleUrl;
  linkElement.id = 'dynamic-stylesheet';  // Add an ID to easily identify the element
  document.head.appendChild(linkElement);
};

// Function to apply the correct stylesheet based on the current route
const applyStyles = () => {
  const isHomePage = route.path === '/';

  // Choose the correct stylesheet based on the route
  if (isHomePage) {
    loadStylesheet('/src/assets/indexMain.css');  // Ensure the path is correct
  } else {
    loadStylesheet('/src/assets/base.css');  // Ensure the path is correct
  }
};

// Load the stylesheet on component mount
onMounted(() => {
  applyStyles();
});

// Watch for route changes and apply the correct stylesheet dynamically
watch(() => route.path, () => {
  applyStyles();
});
</script>

<template>
  <div>
    <router-view />
  </div>
</template>
