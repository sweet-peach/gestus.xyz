import {error} from "@sveltejs/kit";

export async function load({params}) {
    const {user} = params;
    if (user?.role === "ADMIN") {
        return;
    }
    error(403, "Forbidden");
}