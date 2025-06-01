<template>
  <div class="min-h-screen flex flex-col justify-between bg-gray-50">
    <main class="flex flex-col items-center justify-center flex-grow px-4 py-8">
      <div class="flex flex-col md:flex-row items-center gap-8 max-w-5xl w-full">
        <img
          src="@/assets/img/INICIO.png"
          alt="Side Illustration"
          class="w-full md:w-1/2 object-contain"
        />

        <div class="bg-white shadow-md rounded-lg p-8 text-center w-full md:w-1/2">
          <img
            src="@/assets/img/LOGO.png"
            alt="TradeWing Logo"
            class="w-20 h-20 mx-auto mb-4"
          />
          <h1 class="text-4xl font-bold mb-6 text-gray-800">TradeWing</h1>

          <router-link
            to="/login"
            class="inline-block bg-blue-600 hover:bg-blue-700 text-white font-semibold px-6 py-3 rounded transition"
          >
            Log In
          </router-link>

          <p class="mt-4 text-gray-600">
            Don't have an account?
            <router-link to="/register" class="text-blue-600 hover:underline">
              Sign Up
            </router-link>
          </p>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
</script>

