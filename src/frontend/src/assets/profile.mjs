
import axios from 'axios';

//====================================================================================
function uploadProduct(){
    return async function handleUpload(){
        try{
            console.log("[DEBUG] handleUpload triggered");
            
            const name = document.getElementById("name").value;
            const price = document.getElementById("price").value;
            const description = document.getElementById("description").value;
            const image = document.getElementById("image").value;

            const jwt = localStorage.getItem('jwt');
            if (!jwt) throw new Error("JWT not found");

            const response = await axios.post("/api/products/addProduct", {
                name: String(name),
                price: String(price),
                description: String(description),
                image: String(image),
            },{
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwt}`
                }
            });
    
            if (!response || response.status != 200) throw new Error("[PROFILE MJS]: Error while uploading the product");
    
            const result = response;
            console.log("[PROFILE MJS] Product added:", result);
            alert("[PROFILE MJS] Product added correctly");

            document.getElementById("addArt").reset();

        } catch (error) {
            console.error("ERROR: ", error);

            alert("");
    }
    }
}




export {uploadProduct};

console.log("Profile module is loaded.");