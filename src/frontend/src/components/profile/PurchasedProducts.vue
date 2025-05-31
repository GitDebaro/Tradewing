<template>
    <div v-if="orders.length === 0" class="text-center text-gray-500 mt-4">
      No has realizado ninguna compra.
    </div>
    <OrderCardProfile
      v-for="order in orders"
      :key="order.id"
      :order="order"
      @deleteOrder="deleteOrder"
    />
</template>

<script>
import axios from 'axios';
import OrderCardProfile from '../products/OrderCardProfile.vue'; 

export default {
  name: 'PurchasedProducts',
  data() {
    return {
      orders: []
    };
  },
  components: {
    OrderCardProfile
  },
  methods: {
    async fetchOrders() {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('/api/order/my-orders', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        this.orders = response.data;
      } catch (error) {
        console.error('[Orders] Error al obtener órdenes:', error.response?.data || error.message);
      }
    },
    async deleteOrder(orderId) {
      try {
        const token = localStorage.getItem('token');
        await axios.delete('/api/order/removeOrder', {
          params: { orderId },
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        this.orders = this.orders.filter(order => order.id !== orderId);
      } catch (error) {
        console.error('[Orders] Error al eliminar orden:', error);
        alert('No se pudo eliminar el pedido.');
      }
    }
  },
  mounted() {
    this.fetchOrders();
    this.refreshInterval = setInterval(() => {
      this.fetchOrders();
    }, 60000);
  },
  beforeUnmount() {
    clearInterval(this.refreshInterval);
  }
};
</script>
