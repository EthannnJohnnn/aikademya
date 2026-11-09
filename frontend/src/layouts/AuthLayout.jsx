import { Link, Outlet } from "react-router";

export default function AuthLayout() {
  return (
    <div className="auth-shell">
      <header className="auth-header">
        <Link to="/login" className="brand">
          AIKademya
        </Link>
      </header>

      <main className="auth-main">
        <Outlet />
      </main>
    </div>
  );
}
