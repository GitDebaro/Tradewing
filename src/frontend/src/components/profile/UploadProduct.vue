<template>
  <div class="w-full max-w-5xl mx-auto bg-white p-6 rounded-lg shadow-md mt-10">
    <h2 class="text-2xl font-bold mb-4 text-gray-800">Upload Product</h2>
    <form id="addProduct" @submit.prevent="handleUpload" class="space-y-4">
      <input v-model="pName" type="text" placeholder="Name" class="w-[90%] block mx-auto px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" required>
      <input v-model="ppPrice" type="number" placeholder="Price" min="0" max="999999" class="w-[90%] block mx-auto px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" required>
      <input v-model="pImage" type="text" placeholder="Image" class="w-[90%] block mx-auto px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
      <textarea v-model="pDesc" placeholder="Description" rows="4" class="w-[90%] block mx-auto px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 resize-none" required></textarea>
      <button type="submit" class="w-[50%] block mx-auto bg-blue-600 text-white font-semibold py-2 rounded-md hover:bg-blue-700 transition duration-200">
        Save
      </button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';
export default{
    data(){
        return{
            pName: '',
            ppPrice: '',
            pImage: '',
            pDesc: ''
        }
    },
    methods: {
        async handleUpload(){
            try{
                const jwt = localStorage.getItem('token');
                if (!jwt) throw new Error("JWT not found");

                const response = await axios.post("/api/products/addProduct", {
                    name: String(this.pName),
                    price: String(this.ppPrice),
                    description: String(this.pDesc),
                    image: String(this.pImage),
                    },{
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${jwt}`
                    }
                });
    
                if (!response || response.status != 200) throw new Error("[PROFILE MJS]: Error while uploading the product");
    
                const result = response;
                alert("Product added correctly");

                this.pName = '';
                this.ppPrice = '';
                this.pImage = '';
                this.pDesc = '';

        } catch (error) {
            console.error("ERROR: ", error);

            alert("Failed to upload a product");
        }
        }
    }
}
</script>