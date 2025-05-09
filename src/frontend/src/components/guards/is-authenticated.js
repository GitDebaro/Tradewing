const isAuthenticated = (to,from,next) => {

    const token = localStorage.getItem('token');
    localStorage.setItem('lastPath',to.path);

    if(!token){
        return next('/login');
    }

    return next();
}

export default isAuthenticated;