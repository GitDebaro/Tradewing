<template>
    <div class="relative group block border rounded-md p-2 hover:shadow-md transition">

        <!-- Botón flotante de eliminar -->
        <button
        @click.stop="deleteProd"
        class="absolute top-2 right-2 z-20 bg-white rounded-full p-1 shadow hover:bg-red-100 text-red-600 hover:text-red-800 transition"
        title="Eliminar producto"
        >
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none"
            viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round"
                d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7
                    m5 4v6
                    m4-6v6
                    m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3
                    m-4 0h14"/>
        </svg>
        </button>

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
    </div>
</template>

<script>
export default {
  props: {
    product: {
      type: Object,
      required: true
    }
  },
  methods:{
    deleteProd(){
        const confirmed = window.confirm("Are you sure you want to delete this product?");
        if (confirmed) {
            this.$emit('deleteProd', this.product.id);
        }
    }
  }
}
</script>