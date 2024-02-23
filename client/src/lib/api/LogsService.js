import {ApiService} from "$lib/api/ApiService.js";

class LogsService extends ApiService{
    async getByUserId(userId) {
        return this.axiosClient.get(`/api/logs/${userId}`);
    }
}

export default LogsService;