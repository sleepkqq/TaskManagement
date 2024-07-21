import axios from 'axios';
import { getAuthToken } from '../utils/auth';

const API_URL = 'http://localhost:8080/user';

export const getAllUsers = async () => {
    const token = getAuthToken();
    return axios.get(`${API_URL}/all/read`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getUserByUsername = async (username) => {
    const token = getAuthToken();
    return axios.get(`${API_URL}/${username}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getUserAssignedTasks = async (username) => {
    const token = getAuthToken();
    return axios.get(`${API_URL}/${username}/read-assigned-tasks`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const getUserReportedTasks = async (username) => {
    const token = getAuthToken();
    return axios.get(`${API_URL}/${username}/read-reported-tasks`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};
