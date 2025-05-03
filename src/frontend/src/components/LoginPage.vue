<template>
  <h1 class="text-3xl font-bold underline bg-red-500">Hola con Tailwind</h1>
  <h1>{{ msg }}</h1>
  <h2 class="bg-blue-900"><router-view/></h2>
  <router-link to="/dashboard">Login</router-link>
  <router-link to="/register">You don't have account. Register</router-link>
  <h1>{{login}}</h1>
</template>

<script>
export default {
  name: 'LoginPage',
  data() {
    return {
      msg: ''
    }
  },
  mounted() {
    fetch("/api/users/")
      .then((response) => response.text())
      .then((data) => {
          this.msg = data;
      });
    fetch('/api/users/loginUser', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        email: 'deba@test3.onrender.com',
        password: 'admin'
      })
    })
    .then(async res => {
      if (!res.ok) {
        const error = await res.json()
        throw new Error(error.message || 'Login fallido')
      }
      return res.json()
    })
    .then(data => {
      console.log('Login exitoso:', data)
    })
    .then((data)=>{
      this.login = data;
    })
    .catch(err => {
      console.error('Error en login:', err.message)
    });
  }
}
</script>


