<template>
  <main class="w-full max-w-md bg-white rounded-xl shadow-lg p-6 mx-auto mt-10 space-y-6">
    <div class="flex justify-center">
      <img src="@/assets/img/LOGO.png" alt="Logo" width="300" height="200" class="object-contain" />
    </div>

    <form @submit.prevent="handleLogin" class="space-y-3">
     <label class="block text-sm mb-2">Email:</label>
      <input v-model="email" type="email" required />
      <label class="block text-sm mb-2">Password:</label>
      <input v-model="password" type="password" required />
      <div v-if="loginError" class="text-red-500 text-sm mt-1">{{ loginError }}</div>
      <button type="submit" class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition">
        Sign in
      </button>
    </form>
    <div class="text-center mt-4">
        <p class="text-sm text-gray-500">You dont have an account? 
          <router-link to="/register" class="text-blue-600 hover:text-blue-700">Sign up</router-link>
        </p>
    </div>
  </main>
</template>


<script>
import axios from 'axios';
import router from '../router';

export default {
  data() {
    return {
      email: '',
      password: '',
      loginError: ''
    }
  },
  methods: {
    async handleLogin(){
      axios.post('/api/users/loginUser',{
          email: this.email,
          password: this.password
        })
        .then((response) =>{
          this.loginError = '';
          localStorage.setItem('token', response.data.token);
          console.log('[LOGINPAGE] Successful login: ', response.data.token);

          const lastPath = localStorage.getItem('lastPath') ?? '/dashboard';
          router.replace(lastPath);
        })
        .catch((error) => {
          console.log(error)
          this.loginError = 'Login failed. Please check your credentials.';
        })
    }
  }
}
</script>

<style scoped>
input {
  @apply w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500;
}
</style>