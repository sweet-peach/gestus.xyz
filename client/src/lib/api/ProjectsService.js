import {ApiService} from "$lib/api/ApiService.js";

class ProjectsService extends ApiService{
    async getAll(sortBy,sortDirection){
        return this.axiosClient.get('/api/projects',{
            params: {
                sortBy: sortBy,
                sortDirection: sortDirection
            }
        });
    }

    async getById(id) {
        return this.axiosClient.get(`/api/projects/${id}`);
    }

    async create() {
        return this.axiosClient.post('/api/projects', projectData);
    }

    async delete(id) {
        return this.axiosClient.delete(`/api/projects/${id}`);
    }
}
export default ProjectsService;