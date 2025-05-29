<template>
  <div class="flex flex-col md:flex-row p-6 gap-6" v-if="product">
    <!-- Imagen del producto -->
    <div class="md:w-1/3 w-full flex justify-center items-center">
      <img
        :src="product.image"
        alt="Imagen del producto"
        class="rounded-xl object-cover max-w-full max-h-[500px]"
      />
    </div>

    <!-- Detalles del producto -->
    <div class="md:w-1/3 w-full flex flex-col justify-start gap-4">
      <h1 class="text-3xl font-bold">{{ product.name }}</h1>
      <p class="text-gray-700">{{ product.description }}</p>
      <p class="text-2xl font-semibold text-green-600">€{{ product.price }}</p>
      <!-- Dirección de envío -->
      <div class="flex flex-col gap-2">
        <label for="direccion" class="font-semibold text-gray-700">Dirección de envío:</label>
        <input
          id="direccion"
          v-model="shippingAddress"
          type="text"
          placeholder="Calle, número, ciudad, código postal..."
          class="border border-gray-300 rounded-xl px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>
      <button
        class="bg-blue-600 text-white px-6 py-3 rounded-xl hover:bg-blue-700 transition"
        @click = comprarProducto
      >
        Buy now
      </button>
    </div>

    <!-- Información del vendedor -->
    <div class="md:w-1/3 w-full">
      <div class="border p-4 rounded-xl bg-white shadow-md">
        <p class="font-semibold text-gray-600 mb-2">Seller information</p>
        <SellerProfile :seller="product.vendedor" />
      </div>
    </div>
  </div>

  <div v-else class="p-6 text-center text-gray-600">
    Cargando producto...
  </div>
</template>

<script setup>
import axios from 'axios';


import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
// Si tienes el componente del vendedor
import SellerProfile from './SellerInfo.vue'

const route = useRoute()
const productId = route.params.id
const product = ref(null)
const shippingAddress = ref('') // Aquí se almacena la dirección

onMounted(async () => {
  try {
    const response = await axios.get('/api/products/searchId', {
      params: { id: productId }
    })
    product.value = response.data
    console.log(product.value.vendedor)
  } catch (error) {
    console.error('Error al cargar el producto:', error)
  }
})

async function comprarProducto() {
  if (!shippingAddress.value.trim()) {
    alert('Por favor, introduce una dirección de envío.');
    return;
  }

  try {
    await axios.post('/api/orders', {
      productId: product.value.id,
      buyerId: product.value.vendedor,
      shippingAddress: shippingAddress.value
    });

    alert('Pedido realizado correctamente.');
    // Opcional: redirigir a /profile o mostrar confirmación
  } catch (error) {
    console.error('Error al crear el pedido:', error);
    alert('Error al realizar el pedido.');
  }
}
</script>