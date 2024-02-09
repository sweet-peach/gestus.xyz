import {user} from "$lib/stores/userStore.js";

export async function handle ({event, resolve}) {
    const token = event.cookies.get('token');

    console.log("Hook triggered");
    if(!token){
        if (event.url.pathname !== '/login') {
            return new Response(null, {
                status: 302,
                headers: { location: '/login' }
            })
        }
    }

    const response = await fetch(`${import.meta.env.VITE_API_URL}/api/auth`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    if(response.ok){
        event.locals.user = await response.json();

        if (event.url.pathname === '/login') {
            return new Response(null, {
                status: 302,
                headers: { location: '/' }
            })
        }
    } else {
        if (event.url.pathname !== '/login') {
            return new Response(null, {
                status: 302,
                headers: { location: '/login' }
            })
        }
    }

    return await resolve(event);
}