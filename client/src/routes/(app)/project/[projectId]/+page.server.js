import ProjectsService from "$lib/api/ProjectsService.js";
import {error} from "@sveltejs/kit";

export async function load({params}) {
    try {
        const {token, projectId} = params;
        console.log(projectId)
        const projectService = new ProjectsService(token);

        return await projectService.getById(projectId);
    } catch (e) {
        error(404,"Project not found");
    }
}
