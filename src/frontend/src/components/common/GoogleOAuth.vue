<template>
  <div ref="googleBtn"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
import router from '../../router';

const clientId = process.env.VUE_APP_GOOGLE_CLIENT_ID;
console.log(clientId);
const googleBtn = ref(null);

onMounted(() => {
    window.google.accounts.id.initialize({
        client_id: clientId,
        callback: handleCredentialResponse
    })

    window.google.accounts.id.renderButton(googleBtn.value, {
        theme: 'outline',
        size: 'large',
        locale: 'en'
    })
})

async function handleCredentialResponse(response) {
    const token = response.credential
    await axios.post('/api/auth/google', { token })
    .then((response) =>{
        this.loginError = '';
        localStorage.setItem('token', response.data);
        console.log('[LOGINPAGE] Successful login: ', response.data);

        const lastPath = localStorage.getItem('lastPath') ?? '/dashboard';
        router.replace(lastPath);
    })
    .catch((error) =>{
        console.log(error);
        this.loginError = 'Failed to get user data from Google';
    })

}
</script>