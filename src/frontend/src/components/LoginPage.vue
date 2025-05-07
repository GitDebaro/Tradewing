<template>
  <head>
    <link rel="stylesheet" href="../assets/css/LoginPage.css">
  </head>
  <main class="w-full max-w-md bg-white rounded-xl shadow-lg p-6 mx-auto mt-10 space-y-6">
    <div class="flex justify-center">
      <img src="@/assets/img/LOGO.png" alt="Logo" width="300" height="200" class="object-contain" />
    </div>

    <form @submit.prevent="handleLogin" class="space-y-3">
      <div class="grid">
        <label class="block text-sm mb-2">Email:</label>
        <input v-model="email" id="input" type="email" required placeholder="..."/>
      </div>
      <div class="grid">
        <label class="block text-sm mb-2">Password:</label>
        <input v-model="password" id="input" type="password" required placeholder="..."/>
        <div v-if="passLengthError" class="text-red-500 text-sm mt-1">{{ passLengthError }}</div>
      </div>
      <button type="submit" class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition">
        Login
      </button>
    </form>
    <div class="text-center mt-4">
        <p class="text-sm text-gray-500">You dont have an account? 
          <router-link to="/register" class="text-blue-600 hover:text-blue-700">Register</router-link>
        </p>
    </div>
  </main>
</template>


<script>
export default {
  name: 'LoginPage',
  data() {
    return {
      msg: '',
      login: '',
      email: '',
      password: ''
    }
  },
  methods: {
    async handleLogin(){
      try {
          const response = await fetch('/api/users/loginUser', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              email: this.email,
              password: this.password
            })
        });

        if (!response.ok) {
          throw new Error('Login failed');
        }

        const data = await response.json();
        localStorage.setItem('jwt', data.token);
        console.log('[LOGINPAGE] Login completado: ',data.token);
        this.$router.push('/dashboard');

      } catch (err) {
        this.error = 'Email o contraseña incorrectos';
      }
    }
  }
}
</script>



