<template>
    <div class="p-4 max-w-4xl mx-auto">
        <UserInfo />
        <div class="p-4">
            <div class="flex justify-center mb-4">
                <div class="inline-flex rounded-full border border-gray-300 overflow-hidden">
                    <button :class="['px-6 py-2 font-semibold transition-colors', tab === 'sale' ? 'bg-blue-600 text-white' : 'bg-white text-gray-600 hover:bg-gray-100']" @click="tab = 'sale'">For Sale</button>
                    <button :class="['px-6 py-2 font-semibold transition-colors', tab === 'purchased' ? 'bg-blue-600 text-white' : 'bg-white text-gray-600 hover:bg-gray-100']" @click="tab = 'purchased'">Purchased</button>
                </div>
            </div>
    
            <div> 
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4"> <!--check if its useful-->
                    <SaleProducts v-if="tab === 'sale'" />
                    <PurchasedProducts v-else />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import PurchasedProducts from './profile/PurchasedProducts.vue';
import SaleProducts from './profile/SaleProducts.vue';
import UserInfo from './profile/UserInfo.vue';

export default {
  data() {
    return {
      tab: 'sale'
    };
  },
  components: {
    PurchasedProducts,
    SaleProducts,
    UserInfo,
  },
};
</script>