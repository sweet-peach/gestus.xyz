import {ApiService} from "$lib/api/ApiService.js";

class FileService extends ApiService {
    async getFiles(projectId, directoryId) {
        return this.axiosClient.get(`/api/projects/${projectId}/files`, {
            params: {
                parent: directoryId
            }
        });
    }

    async createFolder(projectId, directoryId, name) {
        return this.axiosClient.post(`/api/projects/${projectId}/files`, {
            name,
            parentId: directoryId
        });
    }

    async deleteFile(projectId, fileId) {
        return this.axiosClient.delete(`/api/projects/${projectId}/files/${fileId}`);
    }

    async uploadFile(projectId, directoryId, file, onUploadProgress) {
        const formData = new FormData();

        formData.append('file', file);
        formData.append('parentId', directoryId);

        let config = {
            onUploadProgress
        }

        return this.axiosClient.post(`/api/projects/${projectId}/files/upload`,formData,config);
    }
}

export default FileService;