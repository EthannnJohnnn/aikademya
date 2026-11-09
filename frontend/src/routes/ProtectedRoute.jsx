import { Navigate, Outlet, useLocation } from "react-router";
import useAuth from "../features/auth/hooks/useAuth";

export default function ProtectedRoute() {
  const { user, initialLoading } = useAuth();
  const location = useLocation();
  if (initialLoading) return <main className="session-loading" role="status">Checking your session…</main>;
  return user ? <Outlet /> : <Navigate to="/login" replace state={{ from: { pathname: location.pathname, search: location.search, hash: location.hash } }} />;
}
