<template>
    <style scoped src="../assets/css/dashboardStyle.css"></style>
    
    <!DOCTYPE html>
    <html lang="es">

    <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TradeWing - Dashboard</title>
    <link rel="icon" type="image/png" sizes="64x64" href="../assets/logo.png">
    <link rel="stylesheet" href="../assets/tailwind.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
    

    </head>

    <body>
    <main class="container">
        <div class="botones">
        <router-link to="/profile"> <button id="botPerfil">Perfil</button></router-link>
        <button id="logout">Cerrar Sesión</button>
        </div>

        <form @submit.prevent="handleSubmit" class="form" style="width: 100%">
            <div class="busqueda-grid">
            <input
                type="text"
                v-model="searchTerm"
                id="name"
                name="name"
                placeholder="Nombre del artículo..."
            />
            <button type="submit" id="botonBuscar">Buscar</button>
            </div>
        </form>

        <div class="pagina">
            <section class="panel" id="catalogo">
                <h1>Catálogo</h1>
                <p>Busque un producto para empezar a comprar</p>
                <div id="productos"></div>
            </section>
            <section class="panel" id="cesta">
                <h1>Cesta</h1>
                <div class="cesta">
                <ul id="cesta-compra">
                </ul>
                </div>
                <button @click="handlePagar">Finalizar Compra</button>
            </section>
        </div>
    </main>
    </body>
    </html>
    <router-view/>
    <router-link to="/profile">Profile</router-link>
</template>

<script setup>
import { useSearch, handlePagar, cargarCatalogoAlIniciar } from '../assets/dashboard.mjs'

const { searchTerm, handleSubmit } = useSearch()
</script>

<script>
 import('../assets/css/dashboardStyle.css');
 
 export default {
  name: 'DashboardPage',
  mounted() {
    cargarCatalogoAlIniciar();
  }
}
</script>