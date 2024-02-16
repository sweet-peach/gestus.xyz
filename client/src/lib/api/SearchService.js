import {ApiService} from "$lib/api/ApiService.js";

class Searcher extends ApiService{
    async search(query = "", keywords = []) {
        return this.axiosClient.get(`/api/search?query=${encodeURIComponent(query)}&keywords=${keywords.join(',')}`);
    }
}

export default Searcher;