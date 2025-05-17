const buscarArticulo = async (name) => {

  const datos = {};
  datos.name = name;


  const response = await fetch(`/api/products/search?name=${encodeURIComponent(name)}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    //'credentials': 'include'
  });

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  return response;

};

//DEPRECATED
const insertarArticulo = async (data) => {

  const art = {
    name: data.get('name'),
    precio: data.get('precio'),
    imagen: {
      hires: data.get('imagen')
    },
    vendedor: data.get('vendedor'),
    descripcion: data.get('descripcion')
  }

  console.log(art);

  return fetch('/api/tradeWing/dashboard/articulo', {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(art),
    credentials: "include"
  });

}

//DEPRECATED
const eliminarArticulo = async (name) => {
  const art = {
    name: name
  }

  console.log(`Eliminando artículo: ${art.name}`);

  try {
    const response = await fetch('/api/tradeWing/dashboard/articulo', {
      method: "DELETE",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(art),
      credentials: "include"
    });

    if (!response.ok) {
      throw new Error(`Error al eliminar el artículo: ${response.statusText}`);
    }

    return response;
  } catch (error) {
    console.error('Error:', error);
    throw error;
  }
};

export { buscarArticulo, insertarArticulo, eliminarArticulo };

console.log("Modulo API cargado");