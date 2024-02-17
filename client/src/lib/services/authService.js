import {user} from "../stores/userStore.js";
import axios from "axios";

import Cookies from 'js-cookie';
import {createAxiosClient} from "$lib/axiosClient.js";

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
        const axiosClient = createAxiosClient();

        const response = await axiosClient.post(`/api/auth/login`, {
            email,
            password
        })
        user.set(response.user);
        saveToken(response.token);
        return {
            success: true,
            data: response
        };
    } catch (error) {
        throw error;
    }
}


