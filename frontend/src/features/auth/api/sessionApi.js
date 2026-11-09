import { apiClient } from "../../../lib/apiClient";

export const login = (credentials) => apiClient.post("/api/auth/login", credentials);
export const me = (options) => apiClient.get("/api/auth/me", options);
export const logout = () => apiClient.post("/api/auth/logout");
