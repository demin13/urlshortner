import axios from 'axios';

export const API_BASE_URL = 'https://localhost:8443';
// export const API_BASE_HTTP_URL = 'http://localhost:8000';

export const apiService = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const postApiRequest = (data) => {
    return apiService.post('/api/v1/url/shorten/', data);
};
