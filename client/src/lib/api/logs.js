import createAxiosClient from "$lib/api/axiosClient.js";

class Projects {
    constructor(token) {
        this.token = token;
        this.axiosClient = createAxiosClient(token);
    }

    getAll = (keywords, query) => this.axiosClient.get('/api/projects', {
        params: {keywords, query}
    });
    getById = (id) => this.axiosClient.get(`/api/projects/${id}`);
    create = (projectData) => this.axiosClient.post('/api/projects', projectData);
    update = (id, newProjectData) => this.axiosClient.put(`/api/projects/${id}`, newProjectData);
    delete = (id) => this.axiosClient.delete(`/api/projects/${id}`);

}


export default Projects;