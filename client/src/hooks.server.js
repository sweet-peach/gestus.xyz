import {createAxiosClient} from "$lib/axiosClient.js";

export async function handle ({event, resolve}) {
    const token = event.cookies.get('token');

    if(!token){
        if (event.url.pathname !== '/login') {
            return new Response(null, {
                status: 302,
                headers: { location: '/login' }
            })
        }
    }

    if (token && event.params) {
        event.params.token = token;
    }
    const axiosClient = createAxiosClient(token)

    try {
        const user = await axiosClient.get(`/api/auth`);
        event.locals.user = user;
        event.params.user = user;

        if (event.url.pathname === '/login') {
            return new Response(null, {
                status: 302,
                headers: { location: '/' }
            })
        }
    } catch (e) {
        if (event.url.pathname !== '/login') {
            return new Response(null, {
                status: 302,
                headers: { location: '/login' }
            })
        }
    }

    return await resolve(event);
}