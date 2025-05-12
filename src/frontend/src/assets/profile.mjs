
import {jwtDecode} from 'jwt-decode'

//get jwt so we can know which user is going to upload an article

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

            const decoded = jwtDecode(jwt);
            const seller = decoded.uss;

            const response = await fetch("/api/products/addProduct", {
                method: "POST",
                headers: { 
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: String(name),
                    price: String(price),
                    description: String(description),
                    image: String(image),
                    email: String(seller)
                })
            });
    
            if (!response.ok) throw new Error("[PROFILE MJS]: Error while uploading the product");
    
            const result = await response;
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