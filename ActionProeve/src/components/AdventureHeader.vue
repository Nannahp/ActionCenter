<script lang="ts">
import { defineComponent } from 'vue';
import BaseNavigation from './BaseNavigation.vue';


export default defineComponent({
  name: 'AdventureHeader',
  components: {
    BaseNavigation
  },
  setup() {
    const navItems = [
      {label: 'Activity', link: '/'}
    ];

    const dropdownItems = [
      {label: 'GoCart', value: 'gocart'},
      {label: 'Sumo Wrestling', value: 'sumo'}
    ];


    //Scroller til det valgte element på siden.
    const handleDropdownSelect = (value: string) => {
      const element = document.getElementById(value);
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
    };

    return {
      navItems,
      dropdownItems,
      handleDropdownSelect
    };
  },
});
</script>


<template>
  <div class="header">
    <div class="logo">
      <img src="@/assets/logo.png" alt="Logo" />
    </div>
    <BaseNavigation class="menu-button"
                    :navItems="navItems"
                    dropdownLabel="Activities"
                    :dropdownItems="dropdownItems"
                    @dropdown-select="handleDropdownSelect"
    />
  </div>
</template>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 10px 20px;
  color: var(--bg-color-dark);
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  grid-column: 1 / -1;
}


.logo img {
  height: 120px;
  margin-left: 10%;
}


</style>
