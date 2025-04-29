<template>
  <h1 class="text-3xl font-bold underline bg-red-500">Hola con Tailwind</h1>
  <h1>{{ msg }}</h1>
  <h2 class="bg-blue-900"><router-view/></h2>
  <router-link to="/dashboard">Login</router-link>
  <router-link to="/register">You don't have account. Register</router-link>
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
  }
}
</script>


