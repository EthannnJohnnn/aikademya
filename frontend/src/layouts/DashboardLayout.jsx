import { useState } from "react";
import { Link, NavLink, Outlet, useNavigate } from "react-router";
import useAuth from "../features/auth/hooks/useAuth";
import { errorMessage } from "../lib/apiClient";

export default function DashboardLayout() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [pending, setPending] = useState(false);
  const [error, setError] = useState("");
  async function handleLogout() {
    if (pending) return;
    setPending(true);
    setError("");
    try {
      await logout();
      navigate("/login", { replace: true });
    } catch (failure) {
      setError(`${errorMessage(failure, "session")} Sign-out was not confirmed. Please try again.`);
    } finally {
      setPending(false);
    }
  }
  return (
    <div className="dashboard-shell">
      <header className="auth-header dashboard-header">
        <Link to="/account" className="brand">AIKademya</Link>
        <nav aria-label="Account navigation"><NavLink to="/account">Account Settings</NavLink></nav>
        <span className="user-email">{user.email}</span>
        <button className="secondary-button" onClick={handleLogout} disabled={pending}>{pending ? "Signing out…" : "Logout"}</button>
      </header>
      <main className="dashboard-main">
        {error && <div role="alert" className="message message-error">{error}</div>}
        <span className="sr-only" role="status">{pending ? "Signing out. Please wait." : ""}</span>
        <Outlet />
      </main>
    </div>
  );
}
