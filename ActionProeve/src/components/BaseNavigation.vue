<script lang="ts">
import { defineComponent, ref } from 'vue';
import type { PropType} from "vue";

export interface NavItem {
  label: string;
  link: string;
}

export interface DropdownItem {
  label: string;
  value: string;
}

export default defineComponent({
  name: 'BaseNavigation',
  props: {
    navItems: {
      type: Array as PropType<NavItem[]>, // TypeScript typing via PropType
      required: true,
    },
    dropdownLabel: {
      type: String,
      required: false,
      default: 'Dropdown',
    },
    dropdownItems: {
      type: Array as PropType<DropdownItem[]>, // TypeScript typing via PropType
      required: false,
    },
  },
  setup(props, { emit }) {
    const isOpen = ref(false);

    const openDropdown = () =>{
      isOpen.value = true;
    };

    const closeDropdown = () => {
      isOpen.value = false;
    };

    const handleClick = (item: DropdownItem) => {
      emit('dropdown-select', item.value);
      isOpen.value = false;
    };

    return {
      isOpen,
      openDropdown,
      closeDropdown,
      handleClick,
    };
  },
});
</script>


<template>
  <nav class="navigation">
    <ul class="nav-list">
      <!-- <li v-for="item in navItems" :key="item.label" class="nav-item">
        <router-link :to="item.link">{{ item.label }}</router-link>
      </li> -->
      <li v-if="dropdownItems" class="nav-item dropdown"
          @mouseover="openDropdown"
          @mouseleave="closeDropdown">
        <!-- når musen er over knappen, åbner dropdown menuen -->
        <a href="#" class="dropdown-button">{{ dropdownLabel }}</a>

       <!-- <span class="dropdown-button">{{ dropdownLabel }}</span>-->
        <ul v-if="isOpen" class="dropdown-menu">
          <li v-for="dropdownItem in dropdownItems" :key="dropdownItem.label">
            <a href="#" @click.prevent="handleClick(dropdownItem)">{{ dropdownItem.label }}</a>
          </li>
        </ul>
      </li>
    </ul>
  </nav>
</template>


<style scoped>
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
  background-color: #ffffff;
  list-style-type: none;
  padding: 5px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
  display: block;
  z-index: 1;
}

.dropdown-menu li {
  padding: 8px 12px;
  color: #f0f0f0;
}

.dropdown-menu a:hover {
  color: #00aeff;
}

.dropdown-menu a {
  color: #1f2023;
  text-decoration: none;
}

.dropdown-button {
  padding: 14px;
  color: #f9f9f9;
  background-color: #2b2d30;
  cursor: pointer;
  border-radius: 8px;
  font-size: 16px;
  text-decoration: none;
  display: inline-block;
  font-family: sans-serif;
}
</style>