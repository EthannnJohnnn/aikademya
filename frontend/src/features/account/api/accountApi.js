import { apiClient } from "../../../lib/apiClient";

export const getMe = (options) => apiClient.get("/api/account/me", options);
export const updateMe = ({ email }) => apiClient.patch("/api/account/me", { email });
