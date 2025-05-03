<template>
    <h1>Register</h1>
    <router-link to="/dashboard">Register</router-link>
    <router-link to="/">Login</router-link>
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