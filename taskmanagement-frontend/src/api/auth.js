import axios from 'axios';

const API_URL = 'http://localhost:8080/auth';

export const signUp = (data) => axios.post(`${API_URL}/sign-up`, data);
export const signIn = (data) => axios.post(`${API_URL}/sign-in`, data);
