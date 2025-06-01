<template>
<ProductCardProfile v-for="product in inventory" :key="product.id" :product="product" @deleteProd="handleDelete"/>
</template>

<script>
import axios from 'axios';
import ProductCardProfile from '../products/ProductCardProfile.vue';

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
    },
    async handleDelete(productId) {
      try {
        const jwt = localStorage.getItem('token');
        if (!jwt) throw new Error('JWT not found');

        await axios.delete(`/api/products/removeProduct`, {
          params: {
            productId
          },
          headers: {
            Authorization: `Bearer ${jwt}`
          }
        });

        location.reload();

      } catch (err) {
        console.error('[SALEPRODUCT] Error removing product:', err);
        alert('Failed to delete a product');
      }
    }
  },
  mounted() {
    this.fetchMyInventory();
  },
  components: {
    ProductCardProfile
  }
}
</script>