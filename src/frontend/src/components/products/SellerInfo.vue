<template>
  <div v-if="seller" class="flex items-center space-x-4 mb-6">
    <img :src="seller.image" alt="Foto de perfil" class="w-24 h-24 rounded-full object-cover bg-blue-500">
    <div>
      <h2 class="text-2xl font-semibold">{{ seller.name }} {{ seller.surname }}</h2>
      <p class="text-gray-600">{{ seller.email }}</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    seller: {
      type: Object,
      required: true
    }
  }
}
</script>