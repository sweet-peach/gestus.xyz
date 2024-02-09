import axios from "axios";
import Crud from "$lib/services/crudService.js";


export async function getProjects(keywords,query){
    const params = {
        query: query
    };

    try {
        return await Crud.get("/api/project",params);
    } catch (e) {
        return;
    }
}