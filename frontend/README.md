# AIKademya frontend

From the repository root:

```bash
cd frontend
npm ci
cp .env.example .env
npm run dev
```

Open the local URL printed by Vite. `npm run build` creates `dist/`;
`npm run preview` serves that build. `npm run lint` checks source files.
Run `node --test tests/account-features.test.js` for API contracts, error handling,
email validation, and server-rendered login/protected-route checks.
Deployments must serve `index.html` for client routes such as `/account`.

## Environment

`VITE_API_BASE_URL` defaults to `http://localhost:8080`. Set it in `.env`
to the Spring Boot origin (without `/api`). `VITE_APP_NAME` is reserved for
future branding configuration; current branding is AIKademya.
Vite environment values are public; never put secrets in them.

## Routes and ownership

Ethan owns `/login`, logout, session state, route protection, and `/account`.
`/account` preserves its requested URL through sign-in. `/` and unknown routes
redirect to `/login`. An authenticated login-page visitor goes to `/account`.
Cayl owns `/register`, `/forgot-password`, and `/reset-password`; these remain
the existing placeholders and are not implemented by this feature.

## Pending backend integration

These proposed endpoints are **not implemented by the current backend**:

| Method | Path | Contract |
| --- | --- | --- |
| POST | `/api/auth/login` | Accept `{ email, password }`; establish server session. |
| GET | `/api/auth/me` | Return authenticated user; 401 for no session. |
| POST | `/api/auth/logout` | Invalidate session; return 2xx (204 supported). |
| GET | `/api/account/me` | Return current user's account. |
| PATCH | `/api/account/me` | Accept `{ email }` for current user; 2xx/204 supported. |

User responses are JSON objects with `userId`, `email`, `schoolId` (nullable),
and `roles` (array, possibly empty). Roles can be strings or objects with
`roleName`/`name`; all are displayed read-only. A successful login is followed
by `/api/auth/me` verification before the UI considers the user signed in.
An account save is followed by another session refresh. Failed logout keeps
frontend state because server invalidation has not been confirmed.

Requests use `credentials: "include"`. Spring Boot must implement secure
password verification, session creation/expiry/invalidation, HttpOnly cookies,
appropriate SameSite/Secure settings, and server authorization for every
protected operation. Account identity must come from the session, never a
client-selected user ID. Cross-origin development requires explicit allowed
frontend origins and credentialed CORS (including PATCH/OPTIONS). Agree on and
integrate the server's CSRF protection/token contract before deployment.

Until those APIs exist, login/account requests show readable unavailable or
connection errors. No mock sessions or localStorage tokens/passwords are used.
Frontend route guards only control navigation; they do not authorize backend
access. Browser checks with an unavailable backend cannot validate real sign-in,
account saving, cookie persistence, or logout invalidation.

Manual browser checklist: check desktop/mobile layouts; submit empty fields and
an invalid email; toggle Show/Hide; follow registration and recovery links; open
`/account` without a session and confirm `/login` preserves the requested route;
submit valid-format test credentials against the unavailable backend and confirm
readable feedback. Once the backend exists, also verify login, reload persistence,
account updates, expired sessions, and failed/successful logout.
