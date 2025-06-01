<template>
  <div class="relative group block border rounded-md p-2 hover:shadow-md transition bg-white">

    <button
      v-if="['Delivered', 'Unknown', 'Lost'].includes(currentStep(order))"
      @click.stop="confirmDelete"
      class="absolute top-2 right-2 z-20 bg-white rounded-full p-1 shadow hover:bg-red-100 text-red-600 hover:text-red-800 transition"
      title="Delete order"
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

    <router-link :to="`/product/${order.product.id}`" class="group relative block">
      <img
        :src="order.product.image"
        :alt="`Image: ${order.product.name}`"
        class="aspect-square w-full rounded-md bg-gray-200 object-cover group-hover:opacity-75 lg:h-64"
      />
      <div class="mt-4 flex justify-between items-center">
        <h3 class="text-sm text-gray-700 font-semibold">
          {{ order.product.name }}
        </h3>
        <p class="text-sm font-medium text-gray-900">
          {{ order.product.price }}€
        </p>
      </div>
      <p class="text-xs text-gray-500 mt-1">
        Current state: <strong>{{ currentStep(order) }}</strong>
      </p>
    </router-link>
  </div>
</template>

<script>
export default {
  props: {
    order: {
      type: Object,
      required: true
    }
  },
  emits: ['deleteOrder'],
  methods: {
    currentStep(order) {
      if (!order.steps || order.steps.length === 0) return 'Unknown';

      const now = new Date();
      const completedSteps = order.steps.filter(step =>
        step.deadline && new Date(step.deadline) <= now
      );

      if (completedSteps.length === 0) return 'Pending';

      completedSteps.sort((a, b) => new Date(b.deadline) - new Date(a.deadline));
      return completedSteps[0].name || 'Lost';
    },
    confirmDelete() {
      const confirmed = window.confirm('Delete this order?');
      if (confirmed) {
        this.$emit('deleteOrder', this.order.id);
      }
    }
  }
};
</script>
