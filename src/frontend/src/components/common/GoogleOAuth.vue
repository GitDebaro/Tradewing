<template>
  <div ref="googleBtn"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
import router from '../../router';

const googleBtn = ref(null);

onMounted(() => {
    window.google.accounts.id.initialize({
        client_id: '1088557608321-ooa1hg0026vmuehnmutkl89u6tf6oq70.apps.googleusercontent.com',
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

        const lastPath = localStorage.getItem('lastPath') ?? '/dashboard';
        router.replace(lastPath);
    })
    .catch(() =>{
        this.loginError = 'Failed to get user data from Google';
    })

}
</script>