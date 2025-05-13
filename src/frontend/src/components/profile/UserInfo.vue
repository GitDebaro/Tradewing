<template>
    <div v-if="user" class="flex items-center space-x-4 mb-6">
        <img :src="user.image" alt="Foto de perfil" class="w-24 h-24 rounded-full object-cover bg-blue-500">
        <div>
          <h2 class="text-2xl font-semibold">{{ user.name }} {{user.surname}}</h2>
          <p class="text-gray-600">{{ user.email }}</p>
        </div>
      </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      user: null,
    };
  },
  mounted() {
    axios.post('/api/users/data', {
        token: localStorage.getItem('token')
    })
    .then(response => {
      this.user = response.data;
      console.log(this.user)
    })
    .catch(error => {
      console.error('Error al obtener usuario:', error.response?.data || error.message);
    });
  },
};
</script>