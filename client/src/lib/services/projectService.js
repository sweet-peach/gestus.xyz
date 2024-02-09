import axios from "axios";
import Crud from "$lib/services/crudService.js";
import {getToken} from "$lib/services/authService.js";


export async function getProjects(keywords,query, token = getToken()){
    const crud = new Crud(token);
    const params = {
        query: query
    };
    try {
        return await crud.get("/api/project",params);
    } catch (e) {
        return;
    }
}

export async function getProjectById(id, token = getToken()){
    const crud = new Crud(token);
    const params = {};

    try {
        return await crud.get(`/api/project/${id}`,params);
    } catch (e) {
        return;
    }
}