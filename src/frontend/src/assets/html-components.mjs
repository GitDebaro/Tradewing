//importar la cesta
import { addArticuloCarrito, removeArticuloCarrito, cesta } from './dashboard.mjs'


// Función para crear una tarjeta de producto
function crearTarjetaProducto(producto) {
    // Crear un botón para representar el artículo
    const botonArticulo = document.createElement('button');
    botonArticulo.className = 'product-card';

    //Crear y añadir la imagen del producto
    const imagen = document.createElement('img');
    imagen.src = producto.image;
    botonArticulo.appendChild(imagen);


    // Crear y añadir el título del producto
    const titulo = document.createElement('h2');
    titulo.innerText = producto.name;
    botonArticulo.appendChild(titulo);


    /*
      // Crear y añadir la descripción del producto
      const descripcion = document.createElement('p');
      descripcion.innerText = producto.descripcion;
      botonArticulo.appendChild(descripcion);
    */

    // Crear y añadir el precio del producto
    const precio = document.createElement('p');
    precio.innerText = `Precio: ${producto.price}€`;
    botonArticulo.appendChild(precio);

    /*
    //Crear y añadir el vendedor
    const vendedor = document.createElement('p');
    vendedor.innerText = producto.vendedor;
    botonArticulo.appendChild(vendedor);
    */

    // Obtener el elemento para mostrar detalles adicionales
    const elementoDetalles = document.getElementById('detalles');



    // Añadir evento de añadir al carrito
    botonArticulo.addEventListener('click', () => {
        addArticuloCarrito(producto.name);
        updateCestaCompra();
    });

    // Retornar el botón del artículo completo
    return botonArticulo;
}

// Función para añadir tarjetas de productos al catálogo
function anadirProductosAlCatalogo(productos) {
    // Obtener el elemento del catálogo
    const catalogo = document.getElementById('productos');

    catalogo.innerHTML = '';



    // Iterar sobre cada producto y crear una tarjeta para añadirla al catálogo
    productos.forEach(producto => {
        const tarjeta = crearTarjetaProducto(producto);
        catalogo.appendChild(tarjeta);
    });
}

//funcion para actualizar la cesta de la compra
function updateCestaCompra() {
    const cestaR = document.getElementById('cesta-compra');
    cestaR.innerHTML = "";

    cesta.forEach(articulo => {
        const li = document.createElement('li');
        li.style.listStyleType = 'none';
        li.style.padding = '0';
        li.style.margin = '0';
        const article = document.createElement('article');

        
        const buton = document.createElement('button');
        buton.classList.add('icon-button');
        buton.setAttribute('name', articulo.nombre);//nombre articulo
        // Añadir texto al botón
        buton.textContent = '🗑️';

        const i = document.createElement('i');
        i.classList.add('ti');
        i.classList.add('ti-trash');

        buton.appendChild(i);
        article.appendChild(document.createTextNode(articulo[0]));
        article.appendChild(buton);

        li.appendChild(article);
        cestaR.appendChild(li);
        buton.addEventListener('click', () => {
            removeArticuloCarrito(articulo[0]);
            updateCestaCompra();
        });
    });
}

// Exportar la función para poder ser utilizada en otros módulos
export { anadirProductosAlCatalogo };

console.log("Módulo HTML cargado");