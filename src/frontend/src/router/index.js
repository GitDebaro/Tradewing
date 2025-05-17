import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../components/HomePage.vue'
import LoginPage from '../components/LoginPage.vue'
import RegisterPage from '../components/RegisterPage.vue'
import NotFound from '../components/NotFound.vue'
import isAuthenticated from '@/components/guards/is-authenticated'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    //Landing module
    {path: '/', name: 'home', component: HomePage},
    {path: '/login', name: 'login', component: LoginPage},
    {path: '/register', name: 'register', component: RegisterPage},
    //Guarded pages
    {path: '/guarded', redirect: '/dashboard',component: () => import('../components/layouts/GuardedLayout.vue'), beforeEnter: [isAuthenticated],children: [
      {path: '/dashboard', name: 'dashboard', component: () => import ('../components/DashboardPage.vue')},
      {path: '/profile', name: 'profile', component: () => import ('../components/ProfilePage.vue')},
      {path: '/product/:id', component: () => import('../components/products/ProductDetails.vue')}
    ]},

    //404
    {path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound}
  ]
});

export default router;