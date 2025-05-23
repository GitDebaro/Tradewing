<template>
  <div v-if="user" class="flex justify-between items-center mb-6">
    <div class="flex items-center space-x-4">
        <img :src="user.image + '?t=' + imageTimestamp" alt="Foto de perfil" referrerpolicy="no-referrer" class="w-24 h-24 rounded-full object-cover bg-blue-500">
        <div>
          <h2 class="text-2xl font-semibold">{{ user.name }} {{user.surname}}</h2>
          <p class="text-gray-600">{{ user.email }}</p>
        </div>
    </div>

    <button @click="showModal = true" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
      Modify Image
    </button>
  </div>

  <div v-if="showModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white p-6 rounded shadow-md w-96">
      <h3 class="text-lg font-bold mb-4">Change profile picture</h3>

      <form @submit.prevent="handleUpdate">
        <div class="mb-4">
          <label class="block font-semibold">URL</label>
          <input v-model="image" type="text" class="w-full border px-3 py-2 rounded" />
          <p v-if="imageError" class="text-red-600 text-sm mt-1">{{ imageError }}</p>
        </div>

        <div class="flex justify-end space-x-2">
          <button type="button" @click="showModal = false" class="bg-gray-300 px-4 py-2 rounded hover:bg-gray-400">
          Cancel
          </button>
          <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
          Save Changes
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      user: null,
      showModal: false,
      image: '',
      imageError: '',
      imageTimestamp: Date.now()
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
  methods: {
    async handleUpdate(){
      this.imageError = this.image!='' ? '' : 'URL must not be empty';
      if(this.imageError==''){
          axios.post('/api/users/update',{
          token: localStorage.getItem('token'),
          image: this.image
        })
        .then((response) =>{
          this.imageError = '';
          this.image = '';
          this.showModal = false;
          console.log('[UPDATE] Successful', response);
          this.user = response.data;
          this.imageTimestamp = Date.now();
        })
        .catch((error) => {
          console.log(error)
          this.imageError = '';
          this.image = '';
          this.showModal = false;
        })
      }
    }
  }
};
</script>