import {error} from '@sveltejs/kit';
import {getProjectById} from "$lib/services/projectService.js";
import {getToken} from "$lib/services/authService.js";
import {getUsers} from "$lib/services/userService.js";
export async function load({params, locals}) {
    try {
        const token = params.token;
        console.log(token)
        const users = await getUsers(token);

        if(users != null){
            return {users}
        }

        return error(404, 'Not found');
    } catch (e) {
        console.log(e);
    }
}
