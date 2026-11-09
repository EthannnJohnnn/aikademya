import { Navigate, Route, Routes } from "react-router";
import AuthLayout from "../layouts/AuthLayout";
import PlaceholderPage from "../components/ui/PlaceholderPage";

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
          element={<PlaceholderPage title="Login" />}
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

      <Route
        path="*"
        element={<Navigate to="/login" replace />}
      />
    </Routes>
  );
}
