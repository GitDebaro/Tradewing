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
    {path: '/', name: 'home', component: HomePage, meta: { title: "TradeWing - Home" }},
    {path: '/login', name: 'login', component: LoginPage, meta: { title: "TradeWing - Login" }},
    {path: '/register', name: 'register', component: RegisterPage, meta: { title: "TradeWing - Register" }},
    //Guarded pages
    {path: '/guarded', redirect: '/dashboard',component: () => import('../components/layouts/GuardedLayout.vue'), beforeEnter: [isAuthenticated],children: [
      {path: '/dashboard', name: 'dashboard', component: () => import ('../components/DashboardPage.vue'), meta: { title: "TradeWing - Dashboard" }},
      {path: '/profile', name: 'profile', component: () => import ('../components/ProfilePage.vue'), meta: { title: "TradeWing - Profile" }},
      {path: '/product/:id', component: () => import('../components/products/ProductDetails.vue'), meta: { title: "TradeWing - Product Page" }},
      {path: '/success/:id', component: () => import('../components/products/SellSuccess.vue'), meta: { title: "TradeWing - Payment Success" }},
      {path: '/cancel', component: () => import('../components/products/SellCancel.vue'), meta: { title: "TradeWing - Payment Cancelled" }}
    ]},

    //404
    {path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound, meta: { title: "TradeWing - NotFound" }}
  ]
});

router.beforeEach((to, from, next) => {
     document.title = to.meta.title || "TradeWing";
     next();
});

export default router;