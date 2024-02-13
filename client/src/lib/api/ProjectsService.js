import AxiosClient from "$lib/axiosClient.js";

class Projects {
    static axiosClient = AxiosClient.get();

    static async getAll() {
        return this.axiosClient.get('/api/projects');
    }

    static async getById(id) {
        return this.axiosClient.get(`/api/projects/${id}`);
    }

    static async create() {
        return this.axiosClient.post('/api/projects', projectData);
    }

    static async delete(id) {
        return this.axiosClient.delete(`/api/projects/${id}`);
    }
}
export default Projects;