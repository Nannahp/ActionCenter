
<script setup lang="ts">
import { ref } from 'vue';
import CreateActivityForm from "@/components/CreateActivityForm.vue";
import TestHeader from ".././components/TestHeader.vue";

// Reactive reference for form visibility
const formVisible = ref(false); // Initially set to false

// Handle dropdown selection
const handleDropdownSelect = (value: string) => {
  if (value === 'add-activity') {
    formVisible.value = true;  
  }
};

// Close the form
const closeForm = () => {
  formVisible.value = false;  
};
</script>

<template>
      <h2> HIIIII</h2>
  <header>
    <TestHeader @dropdown-select="handleDropdownSelect" />
  </header>
  <div class="test-view">
    <CreateActivityForm v-if="formVisible" @exitForm="closeForm" />
  </div>

</template>


<style scoped>
.test-view {
  display: flex;
  justify-content: center;
  align-items: center;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
}

template {
  display: flex;
  flex-direction: column;
}
</style>
