import {getToken} from "$lib/services/authService.js";
import Crud from "$lib/services/crudService.js";


export async function getFilesByProjectId(projectId, token = getToken()){
    const crud = new Crud(token)
    try {
        const projects = await crud.get(`/api/project/${projectId}/files`)
        return projects;
    } catch (e) {
        return []
    }

}