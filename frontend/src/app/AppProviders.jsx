import AuthProvider from "../features/auth/context/AuthProvider";

export default function AppProviders({ children }) {
  return <AuthProvider>{children}</AuthProvider>;
}
