import {ApiService} from "$lib/api/ApiService.js";

class   LogsService extends ApiService{
    async getByUserId(userId) {
        return this.axiosClient.get(`/api/logs/${userId}`);
    }

    async getAll(){
        return this.axiosClient.get(`/api/logs`);
    }
}

export default LogsService;