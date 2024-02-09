
class Crud{

    constructor(token) {
        this.token = token;
    }

    async get(url, params = {}){
        const searchParams = new URLSearchParams(params);
        try {
            const response = await fetch(`${import.meta.env.VITE_API_URL}${url}?${searchParams}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${this.token}`
                }
            });

            if(response.ok){
                return await response.json();
            }

            return null;
        } catch (e) {
            console.log(e)
            return;
        }
    }
}

export default Crud;