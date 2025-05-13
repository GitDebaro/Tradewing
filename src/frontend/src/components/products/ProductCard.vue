<template>
  <router-link :to="`/product/${product.id}`" class="group relative block">
    <img
      :src="product.image"
      :alt="`Imagen de ${product.name}`"
      class="aspect-square w-full rounded-md bg-gray-200 object-cover group-hover:opacity-75 lg:aspect-auto lg:h-80"
    />
    <div class="mt-4 flex justify-between">
      <div>
        <h3 class="text-sm text-gray-700 font-semibold">
          {{ product.name }}
        </h3>
      </div>
      <p class="text-sm font-medium text-gray-900">{{ product.price }}€</p>
    </div>
  </router-link>
</template>

<script>
export default {
  props: {
    product: {
      type: Object,
      required: true
    }
  }
}
</script>