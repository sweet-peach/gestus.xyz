import UsersService from "$lib/api/UsersService.js";
import {error} from "@sveltejs/kit";

export async function load({params}) {
    try {
        const {token, userId} = params;
        const userService = new UsersService(token);

        return await userService.getById(userId);
    } catch (e) {
        error(404,"Project not found");
    }
}
