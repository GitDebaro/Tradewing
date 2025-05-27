const isAuthenticated = (to,from,next) => {

    const token = localStorage.getItem('token');
    console.log(to.path)
    
    if(to.path.includes('/cancel') || to.path.includes('/success'))
        localStorage.setItem('lastPath','/dashboard')  
    else
        localStorage.setItem('lastPath',to.path);

    if(!token){
        return next('/login');
    }

    return next();
}

export default isAuthenticated;