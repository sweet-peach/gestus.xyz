import {ApiService} from "$lib/api/ApiService.js";

class Users extends ApiService{
    async getAll(sortBy, sortOrder) {
        return this.axiosClient.get('/api/users',{
            params: {
                sortBy: sortBy,
                sortDirection: sortOrder
            }
        });
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