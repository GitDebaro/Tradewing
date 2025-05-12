<template>
    <h1>Profile</h1>
    <router-link to="/dashboard">Back to dashboard</router-link>
    <router-link to="/">Logout</router-link>
    <div class="form-container">
      <h2>Añadir artículo</h2>
      <form id="addArt" @submit.prevent="handleUpload">
        <input type="text" id="name" placeholder="Nombre" required>
        <input type="text" id="price" placeholder="Precio" required>
        <input type="text" id="image" placeholder="Imagen del artículo"> 
        <input type="textarea" id="description" placeholder="Descripcion" required>
        <button type="submit" id="anadir-articulo">Enviar</button>
      </form>
    </div>
</template>

<script setup>
import {uploadProduct} from "../assets/profile.mjs";

const handleUpload = uploadProduct();
</script>