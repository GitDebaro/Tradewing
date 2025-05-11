const loginUser = async (email, password) => {
  const datos = {};
  datos.email = email;
  datos.password = password;

  const response = await fetch(`/api/tradeWing/users/email`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(datos),
    'credentials': 'include'
  });

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  return response;
}

const registerUser = async (data) => {

  const user = {
    nombre: data.get('name'),
    apellidos: data.get('surname'),
    email: data.get('loginEmail'),
    password: data.get('loginPassword')
  }

  return fetch('/api/tradeWing/users/register', {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(user),
    credentials: "include"
  });
};

const logoutUser = async () => {
  return fetch('/api/tradeWing/users/logout', {
    method: 'GET',
    credentials: 'include'
  });
};

const getUserInfo = async () => {
  return fetch('/api/tradeWing/users/info', {
    method: 'GET',
    credentials: 'include'
  });
};

const updateUser = async (data) => {
  return fetch('/api/tradeWing/users/info', {
    method: 'PUT',
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
    credentials: 'include'
  });
}

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




const obtenerInventario = async () => {

  const response = await fetch(`/api/tradeWing/dashboard/articulo/inventario`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    'credentials': 'include'
  });

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  return response;
}

export { loginUser, registerUser, logoutUser, getUserInfo, updateUser, buscarArticulo, insertarArticulo, obtenerInventario, eliminarArticulo };

console.log("Modulo API cargado");