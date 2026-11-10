# AIKademya architecture

This describes the repository inspected on 2026-11-09 and the boundaries contributors
should preserve. Update it in the same PR when a structural or API decision changes.
Development workflow belongs in [CONTRIBUTING.md](../CONTRIBUTING.md); feature ownership
belongs in [team-roles.md](team-roles.md) and the assigned issue.

## System and current status

AIKademya is one repository with two separately run applications: a React single-page
frontend and a Spring Boot REST backend. The backend persists relational data through
Spring Data JPA. Core learning and assessment behavior is intended to be rule-based;
no generative-AI service is required by the current architecture.

| Area | Present in the repository | Pending or limited |
| --- | --- | --- |
| Frontend | React, Vite, JavaScript/JSX, React Router, shared layouts, login/account UI and session API client | Real authentication depends on backend endpoints |
| Backend | Java 21 target, Spring Boot, Spring MVC, Jakarta Validation, Spring Data JPA; CRUD controllers/services/repositories | Secure authentication and account-self-service endpoints are not implemented |
| Persistence | File-backed H2 locally; Hibernate `ddl-auto=update` | MySQL driver is available but not configured as the active database; production database and migration process need a recorded decision |
| User administration | User, role, user-role, school and division CRUD | CRUD existence does not establish login or authorization |
| Learning and assessment | Learning content, assessments, attempts, responses, diagnostic results, competencies, pathways, progress and certificate CRUD | CRUD alone does not demonstrate an integrated learning workflow, scoring rules or certificate issuance process |
| Testing | Backend Spring Boot tests; frontend API/rendering checks and lint/build scripts | Rendering checks do not replace browser, accessibility or real session-integration testing |

Exact dependency versions are maintained in [backend/pom.xml](../backend/pom.xml),
[frontend/package.json](../frontend/package.json) and its lockfile. Do not upgrade
dependencies as an incidental part of a feature.

## Repository structure

```text
aikademya/
├── backend/
│   ├── src/main/java/com/csit321g3/tres/
│   │   ├── AikademyaApplication.java
│   │   ├── controller/       # HTTP endpoints and request validation
│   │   ├── dto/              # Request/response contracts
│   │   ├── service/          # Use cases and business rules
│   │   ├── repository/       # Spring Data persistence interfaces
│   │   └── entity/           # JPA tables and relationships
│   ├── src/main/resources/   # Application configuration
│   ├── src/test/java/        # Backend automated tests
│   ├── pom.xml
│   └── mvnw / mvnw.cmd
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── app/              # Route composition and providers
│   │   ├── features/         # auth/, account/, and future domain features
│   │   │   └── <feature>/    # api/, pages/, context/, hooks/ as needed
│   │   ├── components/ui/    # Reusable presentation components
│   │   ├── layouts/          # Public/authenticated page shells
│   │   ├── routes/           # Route guards
│   │   ├── lib/              # Shared API client and small utilities
│   │   ├── styles/
│   │   └── main.jsx          # One React root and BrowserRouter
│   ├── tests/
│   ├── .env.example
│   ├── package.json
│   ├── package-lock.json
│   └── vite.config.js
├── docs/                     # Architecture, ownership and delivery records
├── tests/                    # Project-level test plan
├── .github/                  # Issue and PR templates
├── AGENTS.md                 # Shared guidance for AI assistants
├── README.md
└── CONTRIBUTING.md
```

Keep generated `node_modules/`, `dist/`, `backend/target/`, local database files and
real environment secrets out of version control. Empty directories may be absent
from a fresh checkout because Git tracks files.

## Frontend responsibilities

The normal flow is page or hook → feature API module → shared API client → REST API.

- `app/App.jsx` composes routes; `AppProviders.jsx` registers shared providers. Keep
  the existing `BrowserRouter` in `main.jsx` as the single application router.
- Feature pages own interaction and presentation. Feature API modules own endpoint
  paths and payloads; `lib/apiClient.js` owns fetch, credentials and error parsing.
- `AuthProvider` owns session state. Components use `useAuth`; do not create independent
  auth contexts or interpret a browser flag as proof of authentication.
- Shared UI belongs in `components/ui/`; domain-specific behavior stays in its feature.
  Reuse existing layouts and styles instead of adding a second design system.
- Use PascalCase component filenames, `useX` hook names and camelCase JavaScript
  module names. Add only the feature subfolders that have a purpose.

Public routes currently include `/login`, `/register`, `/forgot-password` and
`/reset-password`. Registration and recovery remain placeholders. `/account` is
protected in the frontend. See [frontend/README.md](../frontend/README.md) for setup,
routing behavior and the proposed session contract.

## Backend responsibilities

The normal dependency direction is controller → service → repository → database.
DTOs define HTTP data; JPA entities define persistence. Controllers should not reach
directly into repositories or contain database/business-rule logic.

- Controllers bind and validate inputs, delegate use cases and select HTTP responses.
- Services validate related records, apply business rules and handle deletion guards.
  Related repositories or existing services may be used without dependency cycles.
- Repositories express persistence operations and derived queries; query names must
  match actual entity properties.
- Entities describe schema mappings. New relationship fields require coordinated DTO,
  repository, service, test and database-impact review.
- Prefer explicit request/response DTOs for new APIs. Existing controllers differ:
  some return entities, and LearningModule currently accepts an entity request.
  Do not silently rewrite response shapes as part of unrelated work.

Java classes use PascalCase; methods and DTO/JSON properties use camelCase. Follow
existing explicit table/column mappings, generally snake_case. Preserve established
endpoint paths and ID field names unless a contract change is agreed and documented.

### Local H2 Console

The AIKademya backend provides a browser-based H2 Console for inspecting the persistent development database. The console is enabled only when the Spring Boot `dev` profile is active.

**Start the backend (Git Bash):**

```bash
cd backend
./mvnw "-Dspring-boot.run.profiles=dev" spring-boot:run
```

**Console URL:** `http://localhost:8080/h2-console`

**Connection settings:**

| Setting | Value |
|---|---|
| Driver Class | `org.h2.Driver` |
| JDBC URL | `jdbc:h2:file:./data/aikademya` |
| Username | `sa` |
| Password | Empty (local development only) |

The H2 database path is relative to the working directory. Start Maven from `backend/` to access the expected database.

**Security notes:**
- The H2 Console is disabled by default.
- The `dev` profile binds the server to `127.0.0.1`.
- Remote H2 Console connections are disabled.
- Never expose the development console publicly.
- Do not commit local H2 database files.
- Use an isolated in-memory H2 database for automated tests.

**Example inspection query:**

```sql
SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'PUBLIC'
ORDER BY TABLE_NAME;
```

This lists the available database tables without modifying their contents.

## IDs and relationship conventions

| Entity group | Current Java ID type |
| --- | --- |
| LearningModule, Lesson, Scenario | `Long` |
| Other current repositories, including User, Competency and CompetencyScore | `Integer` |

Referenced IDs must use the referenced entity's type. For example, ModuleProgress
has an `Integer progressId` and refers to a `Long moduleId`. Do not standardize ID
types by changing one layer alone; a migration needs an explicit task and contract review.

Current integration details:

- `Question.competencyId` is still a scalar `Integer`, not a JPA relationship.
  Its repository uses `existsByCompetencyId`. Converting it requires coordinated changes.
- CompetencyScore links DiagnosticResult and Competency. It has `scoreId`, `resultId`
  and `competencyId`, with no numeric score value.
- ModuleProgress currently links a user and learning module; it has no completion
  percentage or status field.
- UserRole prevents duplicate user/role pairs. Competency deletion guards referenced
  scores/questions; diagnostic-result deletion guards pathways and competency scores.
  Verify the specific service rather than assuming every resource has deletion protection.

## HTTP contracts and security boundary

Existing CRUD endpoints use `/api/...`, generally GET for reads, POST for creation,
PUT for updates and DELETE for deletion. Expected status patterns are 200 reads/updates,
201 creation, 204 deletion, 400 invalid requests, 404 missing records and 409 known
relationship conflicts. Verify each endpoint's actual implementation and tests; a
uniform error-body schema has not yet been established.

The frontend proposes these additional endpoints, which are not yet implemented:

| Method | Endpoint | Purpose |
| --- | --- | --- |
| POST | `/api/auth/login` | Verify credentials and establish a server session |
| GET | `/api/auth/me` | Read the authenticated user |
| POST | `/api/auth/logout` | Invalidate the session |
| GET | `/api/account/me` | Read the current user's account |
| PATCH | `/api/account/me` | Update permitted fields on that account |

Requests use `credentials: "include"`; passwords and auth tokens are not stored in
localStorage. The backend must implement password verification, secure session cookies,
CSRF handling, explicit credentialed CORS and authorization. Account identity must come
from the server session. Multiple roles are supported by the proposed response contract;
clients must not assign their own roles. Frontend guards only control navigation.

Before changing a shared API, record method/path, request and response examples,
ID types/nullability, validation, status codes, authorization, migration impact and
affected consumers in the issue/PR. Link dependent frontend/backend tasks and identify
the integration reviewer. A proposed endpoint is not complete merely because a client
calls it or a mock returns a successful response.

Use the [API contract template](templates/api-contract.md) for this agreement.

## Local execution and verification

Frontend commands run inside `frontend/`:

```bash
npm ci
npm run dev
npm run lint
npm run build
node --test tests/account-features.test.js
```

Copy `frontend/.env.example` to a local `.env` when needed. `VITE_API_BASE_URL`
defaults to `http://localhost:8080`; all `VITE_` values are browser-visible.

Backend commands run inside `backend/`. PowerShell examples:

```powershell
.\mvnw.cmd spring-boot:run
.\mvnw.cmd "-Dspring.datasource.url=jdbc:h2:mem:aikademya-tests" "-Dspring.jpa.hibernate.ddl-auto=create-drop" test
```

In Git Bash, use `./mvnw` with the same arguments. The in-memory test override matters:
the default application configuration is file-backed H2, and the context-load test
otherwise inherits it. Development `ddl-auto=update` is not a production migration strategy.

Run the checks relevant to the change and record actual outcomes, including checks
not run. Compilation, server-rendered output, browser behavior and integrated API
behavior are separate evidence. Never report a blocked browser or missing backend
integration as verified. Documentation-only changes need link/content checks rather
than an application build unless an executable example or application behavior changes.
