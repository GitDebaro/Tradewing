<template>
<ProductCard v-for="product in inventory" :key="product.id" :product="product"/>
</template>

<script>
import axios from 'axios';
import ProductCard from '../products/ProductCard.vue';

export default{
  data() {
    return {
      inventory: []
    };
  },
  methods: {
    fetchMyInventory() {
      axios.post('/api/users/my-inventory', {
        token: localStorage.getItem('token')
      })
      .then(response => {
        this.inventory = response.data;
      })
      .catch(error => {
        console.error('Inventory error:', error.response?.data || error.message);
      });
    }
  },
  mounted() {
    this.fetchMyInventory();
  },
  components: {
    ProductCard
  }
}
</script>