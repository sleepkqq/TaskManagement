export const getAuthToken = () => {
    const token = localStorage.getItem('token');
    const expiration = localStorage.getItem('tokenExpiration');

    if (!token || !expiration) return null;

    const currentTime = Date.now() / 1000;
    if (currentTime > expiration) {
        removeAuthToken();
        window.location.href = '/sign-in';
        return null;
    }

    return token;
};

export const setAuthToken = (token, expiresIn) => {
    const currentTime = Date.now() / 1000;
    const expirationTime = currentTime + expiresIn;
    localStorage.setItem('token', token);
    localStorage.setItem('tokenExpiration', expirationTime);
};

export const removeAuthToken = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('tokenExpiration');
};