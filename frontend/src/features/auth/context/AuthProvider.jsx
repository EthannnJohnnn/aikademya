import { useCallback, useEffect, useRef, useState } from "react";
import * as sessionApi from "../api/sessionApi";
import { ApiError, errorMessage } from "../../../lib/apiClient";
import { AuthContext } from "./AuthContext";

function sessionUser(value) {
  if (!value || value.userId == null || typeof value.email !== "string" || !value.email) {
    throw new ApiError("The backend did not return a valid authenticated user.", { code: "INVALID_RESPONSE" });
  }
  return { ...value, roles: Array.isArray(value.roles) ? value.roles : [] };
}

export default function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [initialLoading, setInitialLoading] = useState(true);
  const [sessionError, setSessionError] = useState("");
  const revision = useRef(0);

  useEffect(() => {
    const controller = new AbortController();
    const current = ++revision.current;
    sessionApi.me({ signal: controller.signal })
      .then(sessionUser)
      .then((value) => { if (current === revision.current) setUser(value); })
      .catch((error) => {
        if (controller.signal.aborted || current !== revision.current) return;
        if (error.status !== 401) setSessionError(errorMessage(error, "session"));
      })
      .finally(() => { if (!controller.signal.aborted && current === revision.current) setInitialLoading(false); });
    return () => controller.abort();
  }, []);

  const login = useCallback(async (credentials) => {
    const current = ++revision.current;
    setSessionError("");
    try {
      await sessionApi.login(credentials);
      const value = sessionUser(await sessionApi.me());
      if (current === revision.current) setUser(value);
      return value;
    } finally {
      if (current === revision.current) setInitialLoading(false);
    }
  }, []);

  const logout = useCallback(async () => {
    // Retain the current user until the server confirms session invalidation.
    await sessionApi.logout();
    ++revision.current;
    setUser(null);
    setSessionError("");
  }, []);

  const refreshUser = useCallback(async () => {
    const current = ++revision.current;
    try {
      const value = sessionUser(await sessionApi.me());
      if (current === revision.current) {
        setUser(value);
        setSessionError("");
      }
      return value;
    } catch (error) {
      if (current === revision.current && error.status === 401) setUser(null);
      throw error;
    }
  }, []);

  return <AuthContext.Provider value={{ user, initialLoading, sessionError, login, logout, refreshUser }}>{children}</AuthContext.Provider>;
}
