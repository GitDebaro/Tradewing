<template>
  <div>
    <div v-if="orders.length === 0" class="text-center text-gray-500 mt-4">
      No has realizado ninguna compra.
    </div>
    
    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      <div v-for="order in orders" :key="order.id" class="border rounded-xl p-4 shadow-sm flex flex-col gap-2 bg-white">
        <img :src="order.product.imageUrl" alt="Imagen del producto" class="w-full h-48 object-cover rounded-md" />
        
        <h2 class="text-lg font-semibold">{{ order.product.name }}</h2>
        
        <p class="text-sm text-gray-600">
          Estado del envío: <span class="font-medium">{{ currentStep(order) }}</span>
        </p>
        
        <button
          v-if="currentStep(order) === 'RECIBIDO'"
          @click="deleteOrder(order.id)"
          class="mt-2 px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600 transition-colors"
        >
          Eliminar pedido
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'PurchasedProducts',
  data() {
    return {
      orders: []
    };
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
    currentStep(order) {
      if (!order.steps || order.steps.length === 0) return 'Desconocido';

      // Filtrar pasos cuyo deadline ya pasó (<= ahora)
      const now = new Date();
      const completedSteps = order.steps
        .filter(step => step.deadline && new Date(step.deadline) <= now);

      if (completedSteps.length === 0) return 'Pendiente';

      // Obtener el paso con el deadline más reciente (último completado)
      completedSteps.sort((a, b) => new Date(b.deadline) - new Date(a.deadline));
      return completedSteps[0].name || 'Desconocido';
    },
    async deleteOrder(orderId) {
      try {
        const token = localStorage.getItem('token');
        await axios.delete('/api/order/delete', {
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

    // Refrescar cada 60 segundos
    this.refreshInterval = setInterval(() => {
      this.fetchOrders();
    }, 60000);
  },
  beforeUnmount() {
    // Limpiar intervalo para evitar memory leaks
    clearInterval(this.refreshInterval);
  }
};

</script>
