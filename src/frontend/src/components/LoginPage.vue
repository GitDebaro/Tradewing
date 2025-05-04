<template>
  <h1 class="text-3xl font-bold underline bg-red-500">Hola con Tailwind</h1>
  <h1>{{ msg }}</h1>
  <h2 class="bg-blue-900"><router-view/></h2>
  <router-link to="/dashboard">Login</router-link>
  <router-link to="/register">You don't have account. Register</router-link>
  <h1>{{ login }}</h1>
  <div class="login-container">
    <h1>Tradewing Login</h1>
    <form id="LoginForm" class="form login-form">
      <div class="grid">
                <input v-model="email" type="email" id="email" name="email" placeholder="Email..." required>
            </div>
            <div class="grid">
                <input v-model ="password" type="password" id="password" name="password" placeholder="Password..." required>
            </div>
            <button type="submit" @click.prevent="handleLogin">Login</button>
    </form>
  </div>
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
  mounted() {
    fetch("/api/users/")
      .then((response) => response.text())
      .then((data) => {
          this.msg = data;
      });
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


