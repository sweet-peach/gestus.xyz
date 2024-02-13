import {ApiService} from "$lib/api/ApiService.js";

class Users extends ApiService{
    async getAll() {
        return this.axiosClient.get('/api/users');
    }
    async getById(id) {
        return this.axiosClient.get(`/api/users/${id}`);
    }
    async create(userData) {
        return this.axiosClient.post('/api/auth/registration', userData);
    }
    async update(id,userData) {
        return this.axiosClient.put(`/api/users/${id}`, userData);
    }
    async delete(id) {
        return this.axiosClient.delete(`/api/users/${id}`);
    }
}

export default Users;