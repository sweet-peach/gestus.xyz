import {user} from "../stores/userStore.js";
import axios from "axios";

import Cookies from 'js-cookie';

export function saveToken(token) {
    Cookies.set('token', token, { expires: 1 });
}

export function getToken() {
    return Cookies.get('token');
}

export function logout() {
    Cookies.remove('token');
    user.set(null);
    window.location.href = '/login';
}

export async function login(email, password) {
    try {
        const response = await axios.post(`${import.meta.env.VITE_API_URL}/api/auth/login`, {
            email,
            password
        })
        user.set(response.data.user);
        saveToken(response.data.token);
        return true;
    } catch (e) {
        alert(e);
        return false;
    }
}


