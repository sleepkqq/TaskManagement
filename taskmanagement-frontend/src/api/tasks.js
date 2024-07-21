import axios from 'axios';
import { getAuthToken } from '../utils/auth';

const API_URL = 'http://localhost:8080/task';

export const createTask = (data) => {
    const token = getAuthToken();
    return axios.post(`${API_URL}/create`, data, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getTaskById = (id) => {
    const token = getAuthToken();
    return axios.get(`${API_URL}/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getAllTasks = () => {
    const token = getAuthToken();
    return axios.get(`${API_URL}/read-all`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getTasksByStatus = (status) => {
    const token = getAuthToken();
    return axios.get(`${API_URL}/read-by-status/${status}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getTasksByPriority = (priority) => {
    const token = getAuthToken();
    return axios.get(`${API_URL}/read-by-priority/${priority}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const deleteTaskById = (id) => {
    const token = getAuthToken();
    return axios.delete(`${API_URL}/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};
