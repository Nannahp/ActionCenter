import { createRouter, createWebHistory } from 'vue-router'
import Activity from "@/components/Activity.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/activity',
      name: 'activity',
      component: Activity
    }
  ]
})

export default router
