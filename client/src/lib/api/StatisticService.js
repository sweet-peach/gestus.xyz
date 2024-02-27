import {ApiService} from "$lib/api/ApiService.js";

class StatisticService extends ApiService{
    async getUserActivityByYear(userId , year = new Date().getFullYear()) {
        return await this.axiosClient.get(`/api/statistic/users/${userId}/activity?year=${year}`);
    }

    async getProjectExtensionStatistic(projectId) {
        return await this.axiosClient.get(`/api/statistic/projects/${projectId}`);
    }

    async getAllProjectsExtensions(projectId) {
        return await this.axiosClient.get(`/api/statistic/projects`);
    }

    async getMostActiveUsers() {
        return await this.axiosClient.get(`/api/statistic/users`);
    }
}

export default StatisticService;