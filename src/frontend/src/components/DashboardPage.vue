<template>
    <main class="p-2 max-w-8xl mx-auto">
        <form @submit.prevent="handleSubmit" class="w-full">
            <div class="grid grid-cols-1 md:grid-cols-[1fr_auto] gap-2">
                <input type="text" v-model="searchTerm" id="name" name="name" placeholder="Item Name..."
                class="border border-gray-300 rounded-lg px-4 py-2 w-full focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                <button type="submit" id="botonBuscar"
                class="bg-blue-600 hover:bg-blue-700 text-white rounded-lg px-4 py-2 font-semibold transition">Search</button>
            </div>
        </form>

        <div class="grid grid grid-cols-1 gap-4 my-4 w-full min-h-ful">
            <section class="p-4 border border-gray-300 rounded-md flex flex-col flex-wrap gap-2 bg-blue-50" id="catalogo">
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                    <ProductCard v-for="product in products.filter(p => p.available)" :key="product.id" :product="product"/>
                </div>
            </section>
        </div>
    </main>
</template>

<script>
import axios from 'axios';
import ProductCard from './products/ProductCard.vue'

export default {
  data() {
    return {
      products: [],
       searchTerm: ''
    }
  },
  components: {
    ProductCard
  },
  methods: {
    async getItemList(name){
        axios.get('/api/products/search', {
            params: { name }
        })
        .then(response => {
            this.products = response.data;
        })
        .catch(error => {
            console.error(error);
        })
    },
    handleSubmit(){
        this.getItemList(this.searchTerm);
    }
  },
  mounted() {
    this.getItemList('');
  }
}
</script>