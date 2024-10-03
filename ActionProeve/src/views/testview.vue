<!--TEMPLATE-->
<template>
    <div class="test-view">
      <button v-if="!formVisible" @click="formVisible = true">Add New Activity</button>
      <!-- Conditionally display the AddActivity component when the button is clicked -->
      <AddActivityForm v-if="formVisible" @exitForm="closeForm" />
    </div>
  </template>
  
  <!--SCRIPT -->
  <script lang="ts">
  import { defineComponent, ref } from 'vue'
  import AddActivityForm from "@/components/AddActivityForm.vue"
    
  export default defineComponent({
    name: 'TestView',
    data() {
      return {
        formVisible: false
      }
    },
    components: {
      AddActivityForm
    },
    methods: {
      closeForm() {
        this.formVisible = false
      }
    }
  })
  </script>
  
  <style scoped>
  /* Add basic styles if needed */
  .test-view {
    text-align: center;
    margin-top: 20px;
  }
  </style>
  