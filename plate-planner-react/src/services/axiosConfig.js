import axios from 'axios';

/**
 * This is the base configuration for axios.
 * It tells it the base URL to use, which will be our backend server.
 *
 * We will use this for all of our service calls.
 *
 * @example httpClient.get('/recipe/all');
 * @return An axios instance with the base URL set to our backend server.
 */
const httpClient = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true
});

export default httpClient;