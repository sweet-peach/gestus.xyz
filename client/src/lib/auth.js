import {user} from "./stores/userStore.js";
import axios from "axios";

export async function auth() {
    console.log("auth")
    const token = localStorage.getItem('token');
    if (!token) {
        console.log("no token")
        return; // Токен не найден
    }

    try {
        const response = await fetch(`${import.meta.env.VITE_API_URL}/api/auth`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        console.log(response);

        if (!response.ok) {
            localStorage.removeItem("token");
            throw new Error('Authorization failed');
        }

        const userData = await response.json();
        return user.set(userData.data);
    } catch (error) {
        console.error('Error during authentication:', error);
    }
}

export async function login(email, password) {
    try {
        console.log(email);
        console.log(password);
        const response = await axios.post(`${import.meta.env.VITE_API_URL}/api/auth/login`, {
            email,
            password
        })
        user.set(response.data.user)
        localStorage.setItem('token', response.data.token)
    } catch (e) {
        alert(e);
    }
}


