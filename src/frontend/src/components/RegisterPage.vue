<template>
  <main class="w-full max-w-md bg-white rounded-xl shadow-lg p-6 mx-auto mt-10 space-y-6">
    <div class="flex justify-center">
      <img src="@/assets/img/LOGO.png" alt="Logo" width="300" height="200" class="object-contain" />
    </div>

    <form @submit.prevent="handleRegister" class="space-y-3">
      <label class="block text-sm mb-2">Name:</label>
      <input v-model="name" type="text" required />
      <label class="block text-sm mb-2">Surname:</label>
      <input v-model="surname" type="text" required />
      <label class="block text-sm mb-2">Email:</label>
      <input v-model="email" type="email" required />
      <label class="block text-sm mb-2">Password:</label>
      <input v-model="password" type="password" required />
      <label class="block text-sm mb-2">Repeat Password:</label>
      <input v-model="repeatPass" type="password" required />
      <div v-if="passLengthError" class="text-red-500 text-sm mt-1">{{ passLengthError }}</div>
      <div v-if="passMatchError" class="text-red-500 text-sm mt-1">{{ passMatchError }}</div>

      <button type="submit" class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition">
        Create an Account
      </button>
    </form>
    <div class="text-center mt-4">
        <p class="text-sm text-gray-500">Do you have an account? 
          <router-link to="/login" class="text-blue-600 hover:text-blue-700">Sign in</router-link>
        </p>
    </div>
  </main>
</template>

<script>
import axios from 'axios';
import router from '../router';

export default{
  data(){
    return{
      name: '',
      surname: '',
      email: '',
      password: '',
      repeatPass: '',
      passLengthError: '',
      passMatchError: ''
    }
  },
  methods: {
    handleRegister() {
      this.passLengthError = this.password.length > 7 ? '' : 'Password must be at least 8 characters long'
      this.passMatchError = this.password === this.repeatPass ? '' : 'Passwords do not match'
      if(!this.passLengthError && !this.passMatchError){
        console.log(this.name);
        console.log(this.surname);
        console.log(this.email);
        console.log(this.password);

        const name = this.name;
        const surname = this.surname;
        const email = this.email;
        const password = this.password;

        axios.post('/api/users/addUser',{
          name,
          surname,
          email,
          password
        })
        .then((response) => {
          alert(response.data + ". Redirecting to Login Page");
          router.push('login');
        })
        .catch((error) => {
          alert(error.response.data);
        })
      } 
    }
  }
}
</script>

<style scoped>
input {
  @apply w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500;
}
</style>