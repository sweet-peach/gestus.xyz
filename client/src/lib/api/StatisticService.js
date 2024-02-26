import {ApiService} from "$lib/api/ApiService.js";

class StatisticService extends ApiService{
    async getUserActivityByYear(userId , year = new Date().getFullYear()) {
        return await this.axiosClient.get(`/api/statistic/user/${userId}/activity?year=${year}`);
    }
}

export default StatisticService;