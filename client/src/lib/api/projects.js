import {getToken} from "$lib/services/authService.js";
import createAxiosClient from "$lib/api/axiosClient.js";

const isClient = typeof window !== 'undefined';

let token = "";
if (isClient) {
    token = getToken();
}

class Users{
    constructor(token) {
        this.token = token;
        this.axiosClient = createAxiosClient(token);
    }

    getAll = () => this.axiosClient.get('/api/users');
    getById = (id) => this.axiosClient.get(`/users/${id}`);
    create = (userData) => this.axiosClient.post('/api/auth/registration', userData);
    update = (id, userData) => this.axiosClient.put(`/api/users/${id}`, userData);
    delete = (id) => this.axiosClient.delete(`/users/${id}`);

}




export default Users;