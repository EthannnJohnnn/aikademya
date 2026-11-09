import { useRef, useState } from "react";
import { Link, Navigate, useLocation, useNavigate } from "react-router";
import useAuth from "../hooks/useAuth";
import { errorMessage } from "../../../lib/apiClient";
import { emailError } from "../../../lib/validation";

export default function LoginPage() {
  const { user, initialLoading, sessionError, login } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [visible, setVisible] = useState(false);
  const [errors, setErrors] = useState({});
  const [serverError, setServerError] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const emailInput = useRef(null);
  const passwordInput = useRef(null);
  const requested = location.state?.from;
  // Only return to routes implemented by this application; never accept an external URL.
  const destination = requested?.pathname === "/account" ? `${requested.pathname}${requested.search || ""}${requested.hash || ""}` : "/account";

  async function handleSubmit(event) {
    event.preventDefault();
    if (submitting) return;
    const fields = { email: emailError(email), password: password ? "" : "Password is required." };
    setErrors(fields);
    setServerError("");
    if (fields.email || fields.password) {
      (fields.email ? emailInput : passwordInput).current?.focus();
      return;
    }
    setSubmitting(true);
    try {
      await login({ email: email.trim(), password });
      navigate(destination, { replace: true });
    } catch (error) {
      setServerError(errorMessage(error, "login"));
    } finally {
      setPassword("");
      setSubmitting(false);
    }
  }

  if (initialLoading) return <p role="status">Checking your session…</p>;
  if (user) return <Navigate to={destination} replace />;

  return (
    <div className="login-grid">
      <aside className="login-story" aria-label="About AIKademya">
        <span className="eyebrow">FOR THE EDUCATORS OF TOMORROW</span>
        <h1>Teach with purpose.<br /><span>Grow with AI.</span></h1>
        <p>Your next chapter in responsible AI starts here. Build the knowledge and confidence to bring thoughtful AI use into your classroom.</p>
        <div className="learning-art" aria-hidden="true"><span>AI</span><div className="art-orbit" /><div className="art-dot" /></div>
        <div className="story-note"><span className="note-icon" aria-hidden="true">✦</span><p>Practical learning.<br /><strong>Made for Philippine educators.</strong></p></div>
      </aside>
      <section className="auth-card login-card" aria-labelledby="login-title">
        <span className="eyebrow">WELCOME TO AIKADEMYA</span>
        <h2 id="login-title">Welcome back</h2>
        <p className="muted">Sign in to continue your learning journey.</p>
        {(serverError || sessionError) && <div className="message message-error" role="alert">{serverError || sessionError}</div>}
        <form className="form" onSubmit={handleSubmit} noValidate aria-busy={submitting}>
          <div className="form-field">
            <label htmlFor="login-email">Email address</label>
            <input ref={emailInput} id="login-email" name="email" type="email" autoComplete="username" required value={email} onChange={(event) => setEmail(event.target.value)} disabled={submitting} aria-invalid={Boolean(errors.email)} aria-describedby={errors.email ? "email-error" : undefined} placeholder="you@example.com" />
            {errors.email && <span id="email-error" className="field-error">{errors.email}</span>}
          </div>
          <div className="form-field">
            <div className="field-heading"><label htmlFor="login-password">Password</label><Link to="/forgot-password">Forgot password?</Link></div>
            <div className="password-field">
              <input ref={passwordInput} id="login-password" name="password" type={visible ? "text" : "password"} autoComplete="current-password" required value={password} onChange={(event) => setPassword(event.target.value)} disabled={submitting} aria-invalid={Boolean(errors.password)} aria-describedby={errors.password ? "password-error" : undefined} />
              <button type="button" className="password-toggle" onClick={() => setVisible(!visible)} aria-label={visible ? "Hide password" : "Show password"} aria-pressed={visible} aria-controls="login-password">{visible ? "Hide" : "Show"}</button>
            </div>
            {errors.password && <span id="password-error" className="field-error">{errors.password}</span>}
          </div>
          <button className="primary-button" disabled={submitting} type="submit">{submitting ? "Signing in…" : "Sign In"}<span aria-hidden="true"> →</span></button>
          <span className="sr-only" role="status">{submitting ? "Signing in. Please wait." : ""}</span>
        </form>
        <p className="signup-note">New to AIKademya? <Link to="/register">Create an account</Link></p>
        <p className="login-footnote">A little learning. A lasting impact.</p>
      </section>
    </div>
  );
}
