<template>
    <div class="p-4 max-w-4xl mx-auto">
        <UserInfo />
        <SaleProducts />
        <PurchasedProducts />
    </div>
</template>

<script setup>
import PurchasedProducts from './profile/PurchasedProducts.vue';
import SaleProducts from './profile/SaleProducts.vue';
import UserInfo from './profile/UserInfo.vue';
</script>