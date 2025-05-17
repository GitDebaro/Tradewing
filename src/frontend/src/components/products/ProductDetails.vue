<template>
  <div class="p-4">
    <h1 class="text-2xl font-bold">Detalle del producto</h1>
    <p class="mt-2 text-lg">ID del producto: {{ productId }}</p>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'

const route = useRoute()
const productId = route.params.id
</script>