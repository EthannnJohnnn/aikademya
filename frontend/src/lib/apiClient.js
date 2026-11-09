const baseUrl = (import.meta.env.VITE_API_BASE_URL || "http://localhost:8080").replace(/\/+$/, "");

export class ApiError extends Error {
  constructor(message, { status = 0, code = "HTTP_ERROR", cause } = {}) {
    super(message, { cause });
    this.name = "ApiError";
    this.status = status;
    this.code = code;
  }
}

export async function request(path, { method = "GET", data, signal } = {}) {
  let response;
  try {
    response = await fetch(`${baseUrl}${path}`, {
      method,
      credentials: "include",
      headers: { Accept: "application/json", ...(data === undefined ? {} : { "Content-Type": "application/json" }) },
      ...(data === undefined ? {} : { body: JSON.stringify(data) }),
      signal,
    });
  } catch (cause) {
    if (cause.name === "AbortError") throw cause;
    throw new ApiError("We couldn’t connect to the backend. Check your connection and try again.", { code: "NETWORK_ERROR", cause });
  }

  if (!response.ok) {
    // Avoid displaying raw server pages, stack traces, or echoed credentials.
    throw new ApiError("The request could not be completed. Please try again.", { status: response.status });
  }
  let text;
  try {
    text = await response.text();
  } catch (cause) {
    if (cause.name === "AbortError") throw cause;
    throw new ApiError("We couldn’t read the backend response. Check your connection and try again.", { code: "NETWORK_ERROR", cause });
  }
  if (!text.trim()) return null;
  try {
    return JSON.parse(text);
  } catch (cause) {
    throw new ApiError("The backend returned an unexpected response. Please try again later.", { status: response.status, code: "INVALID_RESPONSE", cause });
  }
}

export const apiClient = {
  get: (path, options) => request(path, options),
  post: (path, data, options) => request(path, { ...options, method: "POST", data }),
  patch: (path, data, options) => request(path, { ...options, method: "PATCH", data }),
  delete: (path, options) => request(path, { ...options, method: "DELETE" }),
};

export function errorMessage(error, context = "account") {
  if (error.code === "NETWORK_ERROR" || error.code === "INVALID_RESPONSE") return error.message;
  const messages = {
    400: "Please check your information and try again.",
    401: context === "login" ? "Invalid email or password. Please try again." : "Your session has expired. Please sign in again.",
    403: "Access denied. You don’t have permission to perform this action.",
    404: context === "account" ? "The account endpoint is unavailable. Please try again later." : "The authentication endpoint is unavailable. Please try again later.",
    409: "This email address is already in use. Please choose another.",
    429: "Too many requests. Please wait a moment before trying again.",
  };
  return messages[error.status] || "The service is temporarily unavailable. Please try again later.";
}
