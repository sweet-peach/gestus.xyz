import {ApiService} from "$lib/api/ApiService.js";

class StatisticService extends ApiService{
    async getUserActivityByYear(userId , year = new Date().getFullYear()) {
        return await this.axiosClient.get(`/api/statistic/user/${userId}/activity?year=${year}`);
    }

    async getProjectExtensionStatistic(projectId) {
        return await this.axiosClient.get(`/api/statistic/project/${projectId}`);
    }
}

export default StatisticService;