// Importa todas las funciones y variables del módulo "rest-api.mjs" y las asigna al alias "rest_api".
import * as rest_api from "./rest-api.mjs";

// Importa la función específica "anadirProductosAlCatalogo" del módulo "html-components.mjs".
import { anadirProductosAlCatalogo } from "./html-components.mjs";
import { ref } from 'vue'
import jwt_decode from 'jwt-decode'

//get jwt so we can know which user is going to upload an article

//====================================================================================
function uploadProduct(){
    async function handleUpload(){
        try{
            const name = document.getElementById("name").value;
            const price = document.getElementById("price").value;
            const description = document.getElementById("description").value;
            const image = document.getElementById("image").value;

            const jwt = localStorage.getItem('jwt');
            if (!jwt) throw new Error("JWT not found");

            const decoded = jwt_decode(jwt);
            const seller = decoded.uss;

            const formData = new FormData();
            formData.append("name", name);
            formData.append("price", price);
            formData.append("description", description);
            formData.append("image", image);
            formData.append("email", email);

            const response = await fetch("/api/products/upload", {
                method: "POST",
                headers: { 
                },
                body: formData
            });
    
            if (!response.ok) throw new Error("[PROFILE]: Error while uploading the product");
    
            const result = await response.json();
            console.log("[PROFILE] Product added:", result);
            alert("Product added correctly");

        } catch (error) {
            console.error("ERROR: ", error);

            alert("");
    }
    }
}






console.log("Profile module is loaded.");