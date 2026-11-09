import assert from "node:assert/strict";
import { after, test } from "node:test";
import { createServer } from "vite";
import React from "react";
import { renderToStaticMarkup } from "react-dom/server";

// Load application modules through Vite so import.meta.env and JSX use the
// same configuration as the application. Fixtures never enable app authentication.
const server = await createServer({ server: { middlewareMode: true }, appType: "custom" });
const originalFetch = globalThis.fetch;
after(async () => { globalThis.fetch = originalFetch; await server.close(); });
const { apiClient, ApiError, errorMessage } = await server.ssrLoadModule("/src/lib/apiClient.js");
const { emailError } = await server.ssrLoadModule("/src/lib/validation.js");
const { login, me, logout } = await server.ssrLoadModule("/src/features/auth/api/sessionApi.js");
const { getMe, updateMe } = await server.ssrLoadModule("/src/features/account/api/accountApi.js");

test("API methods use the proposed endpoints and credentialed requests", async () => {
  const calls = [];
  globalThis.fetch = async (url, options) => { calls.push({ url, ...options }); return new Response(null, { status: 204 }); };
  await login({ email: "teacher@example.com", password: "test-only" });
  await me();
  await logout();
  await getMe();
  await updateMe({ email: "updated@example.com", userId: 99, roles: ["Administrator"] });
  await apiClient.delete("/example");
  assert.deepEqual(calls.map(({ url, method }) => [new URL(url).pathname, method]), [
    ["/api/auth/login", "POST"], ["/api/auth/me", "GET"], ["/api/auth/logout", "POST"],
    ["/api/account/me", "GET"], ["/api/account/me", "PATCH"], ["/example", "DELETE"],
  ]);
  assert.ok(calls.every((call) => call.credentials === "include"));
  assert.deepEqual(JSON.parse(calls[4].body), { email: "updated@example.com" });
});

test("API handles JSON, empty bodies, malformed responses, and network failures", async () => {
  globalThis.fetch = async () => new Response('{"userId":1}');
  assert.deepEqual(await me(), { userId: 1 });
  globalThis.fetch = async () => new Response(null, { status: 204 });
  assert.equal(await logout(), null);
  globalThis.fetch = async () => new Response("<html>Not JSON</html>");
  await assert.rejects(me, (error) => error instanceof ApiError && error.code === "INVALID_RESPONSE");
  globalThis.fetch = async () => { throw new TypeError("connection refused"); };
  await assert.rejects(me, (error) => error.code === "NETWORK_ERROR" && /backend/.test(error.message));
  globalThis.fetch = async () => ({ ok: true, text: async () => { throw new TypeError("broken stream"); } });
  await assert.rejects(me, (error) => error.code === "NETWORK_ERROR");
});

test("HTTP failures remain failures and produce readable, non-sensitive errors", async () => {
  for (const status of [401, 403, 404, 429, 500]) {
    globalThis.fetch = async () => new Response("raw server secrets", { status });
    await assert.rejects(login.bind(null, {}), (error) => {
      assert.equal(error.status, status);
      const message = errorMessage(error, "login");
      assert.ok(message.length > 15);
      assert.ok(!message.includes("raw server secrets"));
      return true;
    });
  }
  assert.match(errorMessage(new ApiError("", { status: 401 }), "login"), /Invalid email or password/);
  assert.match(errorMessage(new ApiError("", { status: 404 }), "login"), /authentication endpoint is unavailable/);
  assert.match(errorMessage(new ApiError("", { status: 429 })), /Too many requests/);
});

test("aborted requests remain distinguishable from connection failures", async () => {
  globalThis.fetch = async () => { throw new DOMException("Aborted", "AbortError"); };
  await assert.rejects(me, (error) => error.name === "AbortError");
});

test("email validation handles required, malformed and trimmed valid input", () => {
  assert.match(emailError("  "), /required/);
  for (const value of ["teacher", "teacher@", "teacher@school", "a b@school.com"]) assert.match(emailError(value), /valid email/);
  assert.equal(emailError(" teacher@example.com "), "");
});

test("login renders labeled fields, masked password, toggle, and Cayl's links", async () => {
  const { MemoryRouter } = await import("react-router");
  const { AuthContext } = await server.ssrLoadModule("/src/features/auth/context/AuthContext.js");
  const { default: LoginPage } = await server.ssrLoadModule("/src/features/auth/pages/LoginPage.jsx");
  const html = renderToStaticMarkup(React.createElement(MemoryRouter, null,
    React.createElement(AuthContext.Provider, { value: { user: null, initialLoading: false, sessionError: "Authentication endpoint unavailable" } },
      React.createElement(LoginPage))));
  for (const expected of ['for="login-email"', 'for="login-password"', 'type="password"', 'aria-label="Show password"', 'href="/register"', 'href="/forgot-password"', 'role="alert"', "Authentication endpoint unavailable"]) {
    assert.ok(html.includes(expected), `Missing ${expected}`);
  }
});

test("protected routes render no private content while checking or without a session", async () => {
  const { MemoryRouter } = await import("react-router");
  const { AuthContext } = await server.ssrLoadModule("/src/features/auth/context/AuthContext.js");
  const { default: ProtectedRoute } = await server.ssrLoadModule("/src/routes/ProtectedRoute.jsx");
  const render = (value) => renderToStaticMarkup(React.createElement(MemoryRouter, { initialEntries: ["/account"] },
    React.createElement(AuthContext.Provider, { value }, React.createElement(ProtectedRoute))));
  assert.match(render({ user: null, initialLoading: true }), /Checking your session/);
  assert.equal(render({ user: null, initialLoading: false }), "");
});
