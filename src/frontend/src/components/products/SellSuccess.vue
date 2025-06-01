<template>
  <div class="flex flex-col items-center justify-center min-h-screen p-6 bg-green-50 text-center">
    <div class="bg-white rounded-xl shadow-md p-8 max-w-md w-full">
      <h1 class="text-3xl font-bold text-green-600 mb-4">Payment Successful! 🎉</h1>
      <p class="text-gray-700 mb-6">Thank you for your purchase. Your payment has been processed successfully.</p>
      <router-link
        to="/dashboard"
        class="inline-block bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-xl transition"
      >
        Back to Store
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const productId = route.params.id
// Puedes usar esto para enviar un correo o cargar los datos del producto:
const sendConfirmationEmail = async () => {
    try {
         const response = await axios.post('/api/users/data', {
            token: localStorage.getItem('token')
        })
        
        const user = response.data;

        await axios.get('/api/payment/sendConfirmation', {
            params: { id: productId.toString(),
            email: user.email    
            }
        })
        // Crear orden de pedido
        const jwt = localStorage.getItem('token');
        await axios.post('/api/order/create', {
          productId: productId,
          shippingAddress: localStorage.getItem('shippingAddress')  // Puedes cambiarlo según el flujo real
        },{
          headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${jwt}`
          }
        })

        console.log('Order created successfully.')

    } catch (error) {
        console.error('Error sending confirmation email:', error);
    }
}
sendConfirmationEmail()

</script>