import { createRouter, createWebHistory } from 'vue-router'
import HomePageView from '@/views/HomePageView.vue'
import TestDatabaseView from '@/views/TestDatabaseView.vue'
import CalenderView from "@/views/CalenderView.vue";
import Testview from '@/views/test-view.vue'
import ScheduleView from "@/views/ScheduleView.vue";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePageView
    },
    {
      path: '/calender',
      name: 'calender',
      component: CalenderView
    },
    {
      path: '/schedule',
      name: 'schedule',
      component: ScheduleView
    }
  ]
})

export default router
