import { useEffect, useRef, useState } from "react";
import * as accountApi from "../api/accountApi";
import useAuth from "../../auth/hooks/useAuth";
import { ApiError, errorMessage } from "../../../lib/apiClient";
import { emailError } from "../../../lib/validation";

export default function AccountSettingsPage() {
  const { user, refreshUser } = useAuth();
  const [account, setAccount] = useState(null);
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [fieldError, setFieldError] = useState("");
  const [attempt, setAttempt] = useState(0);
  const emailInput = useRef(null);

  useEffect(() => {
    const controller = new AbortController();
    setLoading(true);
    setError("");
    accountApi.getMe({ signal: controller.signal })
      .then((value) => {
        if (!value || typeof value.email !== "string" || value.userId !== user.userId) {
          throw new ApiError("The backend returned unexpected account information.", { code: "INVALID_RESPONSE" });
        }
        if (!controller.signal.aborted) { setAccount(value); setEmail(value.email); }
      })
      .catch(async (failure) => {
        if (controller.signal.aborted) return;
        setError(errorMessage(failure));
        if (failure.status === 401) { try { await refreshUser(); } catch { /* The provider clears an expired session. */ } }
      })
      .finally(() => { if (!controller.signal.aborted) setLoading(false); });
    return () => controller.abort();
  }, [attempt, user.userId, refreshUser]);

  async function handleSave(event) {
    event.preventDefault();
    if (saving || !account) return;
    const validation = emailError(email);
    setFieldError(validation);
    setError("");
    setSuccess("");
    if (validation) { emailInput.current?.focus(); return; }
    setSaving(true);
    let saved = false;
    try {
      await accountApi.updateMe({ email: email.trim() });
      saved = true;
      const updatedUser = await refreshUser();
      setAccount(updatedUser);
      setEmail(updatedUser.email);
      setSuccess("Your email address has been updated.");
    } catch (failure) {
      setError(saved ? "Your changes were saved, but we couldn’t refresh your session. Reload the page to confirm your account information." : errorMessage(failure));
      if (!saved && failure.status === 401) { try { await refreshUser(); } catch { /* Session verification handles expiration. */ } }
    } finally {
      setSaving(false);
    }
  }

  const info = account || user;
  const roles = Array.isArray(info.roles) ? info.roles : [];
  return (
    <section className="account-section" aria-labelledby="account-title">
      <span className="eyebrow">YOUR AIKADEMYA ACCOUNT</span>
      <h1 id="account-title">Account settings</h1>
      <p className="muted">Keep your details up to date as you continue learning.</p>
      <div className="account-grid">
        <section className="settings-card" aria-labelledby="profile-title">
          <h2 id="profile-title">Personal information</h2>
          <p className="muted">Manage the email address associated with your account.</p>
          {loading && <p role="status">Loading account information…</p>}
          {error && <div className="message message-error" role="alert">{error}</div>}
          {!loading && !account && <button className="secondary-button" onClick={() => setAttempt(attempt + 1)}>Retry loading account</button>}
          {success && <div className="message message-success" role="status">{success}</div>}
          <form className="form" noValidate onSubmit={handleSave} aria-busy={saving || loading}>
            <div className="form-field"><label htmlFor="account-email">Email address</label>
              <input ref={emailInput} id="account-email" name="email" type="email" autoComplete="email" required value={account ? email : user.email} onChange={(event) => { setEmail(event.target.value); setSuccess(""); }} disabled={loading || saving || !account} aria-invalid={Boolean(fieldError)} aria-describedby={fieldError ? "account-email-error" : undefined} />
              {fieldError && <span className="field-error" id="account-email-error">{fieldError}</span>}
            </div>
            <button type="submit" className="primary-button" disabled={loading || saving || !account}>{saving ? "Saving…" : "Save Changes"}</button>
          </form>
        </section>
        <aside className="settings-card assignment-card" aria-labelledby="assignment-title">
          <h2 id="assignment-title">Your assignment</h2>
          <p className="muted">School and role assignments are managed by your administrator.</p>
          <dl><dt>School assignment</dt><dd>{info.schoolId == null ? "No school assigned" : `School ID: ${info.schoolId}`}</dd><dt>Assigned roles</dt><dd><div className="role-list">{roles.length ? roles.map((role, index) => <span className="role-badge" key={index}>{typeof role === "string" ? role : role?.roleName || role?.name || "Assigned role"}</span>) : "No roles assigned"}</div></dd></dl>
        </aside>
      </div>
    </section>
  );
}
