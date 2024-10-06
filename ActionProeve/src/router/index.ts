import { createRouter, createWebHistory } from 'vue-router'
import HomePageView from '@/views/HomePageView.vue'
import TestDatabaseView from '@/views/TestDatabaseView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePageView
    },
    {
      path: '/test-database',
      name: 'database',
      component: TestDatabaseView
    }
  ]
})

export default router
