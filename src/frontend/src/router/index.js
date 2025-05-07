import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../components/HomePage.vue'
import LoginPage from '../components/LoginPage.vue'
import RegisterPage from '../components/RegisterPage.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {path: '/', name: 'home', component: HomePage},
    {path: '/login', name: 'login', component: LoginPage},
    {path: '/register', name: 'register', component: RegisterPage},
    {path: '/dashboard', name: 'dashboard', component: () => import ('../components/DashboardPage.vue')},
    {path: '/profile', name: 'profile', component: () => import ('../components/ProfilePage.vue')}
  ]
});

export default router;