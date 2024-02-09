import {getToken} from "$lib/services/authService.js";
import Crud from "$lib/services/crudService.js";

export async function getUsers( token = getToken()){
    const crud = new Crud(token);
    const params = {};

    try {
        return await crud.get(`/api/users`,params);
    } catch (e) {
        return;
    }
}