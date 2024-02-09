import {user} from "./stores/userStore.js";
import axios from "axios";

// src/lib/auth.js

import Cookies from 'js-cookie';

export function saveToken(token) {
    Cookies.set('token', token, { expires: 7 }); // Сохраняем токен на 7 дней
}

export function getToken() {
    return Cookies.get('token');
}

export async function authenticate(request) {
    const token = getToken(); // Пытаемся получить токен из куков клиента или сервера
    console.log(token);
    if (!token) {
        return false;
    }

    try {
        const res = await fetch(`${import.meta.env.VITE_API_URL}/api/auth`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (res.ok) {
            const userData = await res.json();

            user.set(userData);
            return true;
        } else {
            Cookies.remove('token');
            user.set(null);
            return false;
        }
    } catch (error) {
        console.error('Authentication error:', error);
        return false;
    }
}

export function logout() {
    Cookies.remove('jwt');
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
    } catch (e) {
        alert(e);
    }
}


