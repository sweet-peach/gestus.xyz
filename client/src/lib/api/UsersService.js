import createAxiosClient from "$lib/axiosClient.js";
import AxiosClient from "$lib/axiosClient.js";

class Users {
    static axiosClient = AxiosClient.get();
    static getAll = () => this.axiosClient.get('/api/users');
    static getById = (id) => this.axiosClient.get(`/api/users/${id}`);
    static create = (userData) => this.axiosClient.post('/api/auth/registration', userData);
    static update = (id, userData) => this.axiosClient.put(`/api/users/${id}`, userData);
    static delete = (id) => this.axiosClient.delete(`/users/${id}`);
}

export default Users;