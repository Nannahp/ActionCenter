import { createRouter, createWebHistory } from 'vue-router'
import HomePageView from '@/views/HomePageView.vue'
import Testview from '@/views/test-view.vue'
import path from 'path'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePageView
    },
    {
    path: '/add-activity',
      name: 'add-activity',
      component: Testview
    }
  ]
})

export default router
