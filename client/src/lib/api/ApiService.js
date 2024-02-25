import {createAxiosClient} from "$lib/axiosClient.js";


export class ApiService{
    constructor(token, returnResponse = false){
        this.token = token;
        this.axiosClient = createAxiosClient(token, returnResponse);
    }
}