import {getToken} from "$lib/services/authService.js";
import axios from "axios";

class Crud{
    static api = axios.create({

    });
    static async get(url, params = {}){
        const searchParams = new URLSearchParams(params);
        try {
            const response = await fetch(`${import.meta.env.VITE_API_URL}${url}?${searchParams}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${getToken()}`
                }
            });
            return await response.json();
        } catch (e) {
            console.log(e)
            return;
        }
    }
}

export default Crud;