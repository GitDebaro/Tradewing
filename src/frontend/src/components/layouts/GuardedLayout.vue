<template>
    <div>
      <Navbar />
      <div class="p-4">
        <router-view />
      </div>
    </div>
  </template>
  
  <script setup>
  import Navbar from '../common/NavBar.vue'
  </script>