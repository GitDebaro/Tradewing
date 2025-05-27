<template>
  <div class="min-h-screen flex flex-col justify-center items-center bg-red-50 text-center p-6">
    <svg class="w-20 h-20 text-red-500 mb-4" fill="none" stroke="currentColor" stroke-width="1.5"
      viewBox="0 0 24 24">
      <path stroke-linecap="round" stroke-linejoin="round"
        d="M12 9v2m0 4h.01M21 12A9 9 0 1 1 3 12a9 9 0 0 1 18 0z" />
    </svg>
    <h1 class="text-3xl font-bold text-red-700 mb-2">Payment Cancelled</h1>
    <p class="text-gray-600 max-w-xl">
      The transaction was not completed. If this was a mistake, you can try again from the dashboard.
    </p>
    <router-link
      to="/dashboard"
      class="mt-6 inline-block bg-red-600 hover:bg-red-700 text-white px-5 py-3 rounded-lg transition"
    >
      Go back to dashboard
    </router-link>
  </div>
</template>

<script setup>
</script>
