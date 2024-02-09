import axios from 'axios';

function createAxiosClient(token = null) {
    const client = axios.create({
        baseURL: import.meta.env.VITE_API_URL,
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (token) {
        client.interceptors.request.use(config => {
            config.headers.Authorization = `Bearer ${token}`;
            return config;
        });
    }

    client.interceptors.response.use(
        response => response.data,
        error => {
            if (error.response) {
                // Handling HTTP status errors
                console.error(`HTTP error: ${error.response.status} - ${error.response.statusText}`);
                throw {
                    status: error.response.status,
                    message: error.response.data.message || error.response.statusText,
                    data: error.response.data,
                };
            } else if (error.request) {
                // The request was made, but no response was received
                console.error('No Response error:', error.request);
                throw { message: 'No response was received' };
            } else {
                // Error in setting the query
                console.error('Request Setup error:', error.message);
                throw { message: error.message };
            }
        }
    );

    return client;
}

export default createAxiosClient;
