import {createAxiosClient} from "$lib/axiosClient.js";


export class ApiService{
    constructor(token) {
        this.token = token;
        this.axiosClient = createAxiosClient(token);
    }
}