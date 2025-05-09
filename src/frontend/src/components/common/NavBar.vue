<template>
    <nav class="bg-white shadow-md px-6 py-3 flex items-center space-x-6">
  
      <!-- MAY BE INSERT HERE A LOGO TO HOME PAGE-->

      <div class="flex space-x-6 items-center ml-auto">
        <router-link to="/dashboard" class="hover:underline text-gray-700">Dashboard</router-link>
        <router-link to="/profile" class="hover:underline text-gray-700">Profile</router-link>
  
        <button @click="logout" class="bg-blue-600 hover:bg-blue-700 text-white px-3 py-1 rounded">
          Log out
        </button>
      </div>
    </nav>
</template>

<script setup>
import router from '@/router';

function logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('lastPath');
  router.replace('/login');
}
</script>