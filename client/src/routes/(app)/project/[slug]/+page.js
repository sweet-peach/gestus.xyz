import {error} from '@sveltejs/kit';
import {getProjectById} from "$lib/services/projectService.js";
import {getToken} from "$lib/services/authService.js";
export async function load({params, locals}) {
    try {
        const token = params.token;
        console.log(token)
        const project = await getProjectById(params.slug,token);
        if(project != null){
            return project
        }
        return error(404, 'Not found');
    } catch (e) {
        console.log(e);
    }
}
