
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

            const formData = new FormData();
            formData.append("name", String(name));
            formData.append("price", String(price));
            formData.append("description", String(description));
            formData.append("image", String(image));
            formData.append("email", String(seller));

            const response = await fetch("/api/products/addProduct", {
                method: "POST",
                headers: { 
                },
                body: formData
            });
    
            if (!response.ok) throw new Error("[PROFILE]: Error while uploading the product");
    
            const result = await response.json();
            console.log("[PROFILE] Product added:", result);
            alert("Product added correctly");

            document.getElementById("addArt").reset();

        } catch (error) {
            console.error("ERROR: ", error);

            alert("");
    }
    }
}




export {uploadProduct};

console.log("Profile module is loaded.");