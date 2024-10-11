<script lang="ts">
import {computed, ref} from 'vue';
import {defineComponent} from "vue";
import BaseNavigation from "@/components/BaseNavigation.vue";
import BaseButton from "@/components/BaseButton.vue";
import {useRoute, useRouter} from "vue-router";
import axios from "axios";

export default defineComponent({
  name: 'TestHeader',
  components: {
    BaseButton,
    BaseNavigation
  },
  setup(_,  { emit }) {
    const router = useRouter();
    const route = useRoute();
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

    const buttonText = computed(() => {
      return route.name === 'schedule' ? 'Calendar' : 'Schedule';
    });

    const handleButtonClick = () => {
      if (route.name === 'schedule') {
        router.push({ name: 'calender' });
      } else {
        router.push({ name: 'schedule' });
      }
    };

    const handleLogout = async () => {
      try {
        await axios.post('http://localhost:8080/api/login/logout', {}, {
          withCredentials: true
        });
        localStorage.removeItem('isLoggedIn');
        localStorage.removeItem('isAdmin');
        await router.push('/');
      } catch (error) {
        console.error('Logout failed:', error);
      }
    };

    //This can work as there is no redirection to any pages
    const isAdmin = computed(() => {
      return localStorage.getItem('isAdmin') === 'true';
    });

    return {
      navItems,
      dropdownItems,
      handleDropdownSelect,
      formVisible,
      buttonText,
      handleButtonClick,
      handleLogout,
      isAdmin
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
          v-if="isAdmin"
          :navItems="navItems"
          dropdownLabel="Activities"
          :dropdownItems="dropdownItems"
          @dropdown-select="handleDropdownSelect"
      />

      <BaseButton
          class="schedule-button"
          :text="buttonText"
          type="button"
          @click="handleButtonClick"
      />

      <BaseButton
          class="logout-button"
          text="Logout"
          type="button"
          @click="handleLogout"
      />
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
  width: 100%;
  background-color: #b3b3b3;
}


.logo img {
  height: 60px;
  margin-left: 10px;
}

.nav-section {
  display: flex;
  align-items: center;
  gap: 15px;
}

.logout-button {
  background-color: #0056b3;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  width: 90px;
  height: 45px;
}

.schedule-button {
  background-color: #0056b3;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  width: 90px;
  height: 45px;
}

</style>