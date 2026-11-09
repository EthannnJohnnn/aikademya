import { Navigate, Route, Routes } from "react-router";
import AuthLayout from "../layouts/AuthLayout";
import PlaceholderPage from "../components/ui/PlaceholderPage";
import LoginPage from "../features/auth/pages/LoginPage";
import AccountSettingsPage from "../features/account/pages/AccountSettingsPage";
import ProtectedRoute from "../routes/ProtectedRoute";
import DashboardLayout from "../layouts/DashboardLayout";

export default function App() {
  return (
    <Routes>
      <Route element={<AuthLayout />}>
        <Route
          path="/"
          element={<Navigate to="/login" replace />}
        />

        <Route
          path="/login"
          element={<LoginPage />}
        />

        <Route
          path="/register"
          element={<PlaceholderPage title="Registration" />}
        />

        <Route
          path="/forgot-password"
          element={<PlaceholderPage title="Forgot Password" />}
        />

        <Route
          path="/reset-password"
          element={<PlaceholderPage title="Reset Password" />}
        />
      </Route>

      <Route element={<ProtectedRoute />}>
        <Route element={<DashboardLayout />}>
          <Route path="/account" element={<AccountSettingsPage />} />
        </Route>
      </Route>

      <Route
        path="*"
        element={<Navigate to="/login" replace />}
      />
    </Routes>
  );
}
