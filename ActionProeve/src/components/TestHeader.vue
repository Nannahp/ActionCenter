<script lang="ts">
import { defineComponent, ref } from 'vue';
import BaseNavigation from './BaseNavigation.vue';


export default defineComponent({
  name: 'TestHeader',
  components: {
    BaseNavigation
  },
  setup(_,  { emit }) {
    const formVisible = ref(false);
    const navItems = [
   {label: "Activities", link: "/"}
    ];

    const dropdownItems = [
    {label: 'Add Activity', value: 'add-activity'},
    {label: 'Remove Activity', value: 'remove-activity'}
    ];

    //Scroller til det valgte element på siden.
    const handleDropdownSelect = (value: string) => {
     if (value === 'add-activity') {
        formVisible.value = true;
        emit('dropdown-select', value)
     }
    };

    return {
      navItems,
      dropdownItems,
      handleDropdownSelect,
      formVisible
    };
  },
});
</script>


<template>
  <header class="header">
    <div class="logo">
      <img src="@/assets/logo.png" alt="Logo" />
    </div>
    <BaseNavigation
        :navItems="navItems"
        dropdownLabel="Activities"
        :dropdownItems="dropdownItems"
        @dropdown-select="handleDropdownSelect"
    />
  </header>
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
}

nav {
  display: flex;
  align-items: center;
}

</style>
