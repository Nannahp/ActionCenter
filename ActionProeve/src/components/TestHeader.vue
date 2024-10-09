<script lang="ts">
import {ref, computed, watch, onMounted} from 'vue';
import { useRoute } from 'vue-router';
import {defineComponent} from "vue";
import BaseNavigation from "@/components/BaseNavigation.vue";
import BaseButton from "@/components/BaseButton.vue";

export default defineComponent({
  name: 'TestHeader',
  components: {
    BaseButton,
    BaseNavigation
  },
  setup(_,  { emit }) {
    //const formVisible = ref(false);
    const navItems = [
      {label: "Activities", link: "/"}
    ];

    const dropdownItems = [
      {label: 'Add Activity', value: 'add-activity'},
      {label: 'Remove Activity', value: 'remove-activity'}
    ];

    const handleDropdownSelect = (value: string) => {
      emit("dropdown-select",value);
    };
    /*
    //Scroller til det valgte element på siden.
    const handleDropdownSelect = (value: string) => {
      if (value === 'add-activity') {
        formVisible.value = true;
        emit('dropdown-select', value)
      }
    };
    */

    return {
      navItems,
      dropdownItems,
      handleDropdownSelect,
      
    };
  },
});
</script>


<template>
  <header class="header">
    <div class="logo">
      <img src="@/assets/logo.png" alt="Logo" />
    </div>
    <div class="nav-section">
      <BaseNavigation
          :navItems="navItems"
          dropdownLabel="Activities"
          :dropdownItems="dropdownItems"
          @dropdown-select="handleDropdownSelect"
      />

      <BaseButton text="Logout" type="button" />
    </div>
  </header>
</template>

<style scoped>
/* Styles for other pages */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  width: 100%; /* Full width */
  top: 0; /* Stays at the top */
  left: 0; /* Aligns to the left edge */
  background-color: #b3b3b3; /* Your background color */
}


.logo img {
  height: 50px;
}

.nav-section {
  display: flex;
  align-items: center;
  gap: 15px;
}

button {
  padding: 0.75rem 1.5rem;
  background-color: #0056b3;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
}

</style>