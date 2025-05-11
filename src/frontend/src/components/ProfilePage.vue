<template>
    <h1>Profile</h1>
    <router-link to="/dashboard">Back to dashboard</router-link>
    <router-link to="/">Logout</router-link>
    <div class="form-container">
      <h2>Añadir artículo</h2>
      <form id="addArt" @submit.prevent="uploadProduct">
        <input type="text" name="name" placeholder="Nombre" required>
        <input type="text" name="precio" placeholder="Precio" required>
        <input type="text" name="imagen" placeholder="Imagen del artículo"> 
        <input type="textarea" name="descripcion" placeholder="Descripcion" required>
        <button type="submit" id="anadir-articulo">Enviar</button>
      </form>
    </div>
</template>

<script setup>

const { uploadProduct } = useSearch()
</script>