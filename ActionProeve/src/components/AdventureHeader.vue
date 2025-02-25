<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'AdventureHeader',
  setup() {
    // Navigation and dropdown items
    const navItems = [
      { label: 'Activity', link: '/' }
    ];

    const dropdownItems = [
      { label: 'GoCart', value: 'Go-kart'},
      { label: 'Paintball', value: 'Paintball' },
      { label: 'Sumo Wrestling', value: 'Sumo Wrestling' },
      { label: 'Sumo Football', value: 'Sumo Football' }
    ];
    const isOpen = ref(false);

    const openDropdown = () => {
      isOpen.value = true;
    };

    const closeDropdown = () => {
      isOpen.value = false;
    };

    const handleDropdownSelect = (value: string) => {
      const element = document.getElementById(value);
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
      isOpen.value = false; // Close the dropdown after selection
    };

    return {
      navItems,
      dropdownItems,
      isOpen,
      openDropdown,
      closeDropdown,
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
    <nav class="navigation">
      <ul class="nav-list">
        <!-- Dropdown Navigation -->
        <li v-if="dropdownItems" class="nav-item dropdown"
            @mouseover="openDropdown"
            @mouseleave="closeDropdown">
          <a href="#" class="dropdown-button">{{ 'Activities' }}</a>
          <ul v-if="isOpen" class="dropdown-menu">
            <li v-for="dropdownItem in dropdownItems" :key="dropdownItem.label">
              <a
                  :href="'#' + dropdownItem.value"
                  @click.prevent="handleDropdownSelect(dropdownItem.value)"
              >
                {{ dropdownItem.label }}
              </a>
            </li>
          </ul>
        </li>
      </ul>
    </nav>
  </div>
</template>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 20px 40px;
  color: var(--action-black);
  box-shadow: 0px 4px 8px var(--action-black);
}

.logo img {
  height: 110px;
  margin-left: 10%;
  margin-bottom: 10px;
}

.nav-list {
  list-style-type: none;
  display: flex;
  gap: 16px;
}

.nav-item {
  position: relative;
}

.dropdown-menu {
  position: absolute;
  background-color: #2b2d30;
  list-style-type: none;
  padding: 20px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
  display: block;
  z-index: 1;
}

.dropdown-menu li {
  padding: 8px 12px;
}

.dropdown-menu a {
  color: white;
  text-decoration: none;
}

.dropdown-menu a:hover {
  color: var(--action-blue);
}

.dropdown-button {
  padding: 15px;
  color: #f9f9f9;
  background-color: #e83a00;
  cursor: pointer;
  border-radius: 8px;
  font-size: 25px;
  font-weight: bold;
  text-decoration: none;
  display: inline-block;
}
</style>