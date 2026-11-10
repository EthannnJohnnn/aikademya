# Contributing to AIKademya

This is the shared workflow for TRes members and their AI assistants. It uses
Conventional Commits and GitHub pull requests, with the team choices below.
These documents do not configure GitHub permissions, checks, or merge settings.

## 1. One owned task per focused change

Every implementation task needs a GitHub issue, linked ClickUp task, one owner,
a peer reviewer, scope, acceptance criteria, and known dependencies. Use the
[feature/task](.github/ISSUE_TEMPLATE/feature_request.md) or
[bug](.github/ISSUE_TEMPLATE/bug_report.md) template. Documentation/setup work
can use the feature/task template too.

Keep one focused outcome per PR. A backend CRUD slice can include its entity,
DTOs, repository, service, controller, and tests in one PR. Separate unrelated
features. Split large work into linked subtasks with reviewable criteria.
Read [architecture](docs/architecture.md), [ownership](docs/team-roles.md), and
linked API contracts first. A person owns and verifies AI-assisted work.

## 2. Branch names

| Work | Format | Illustrative example |
| --- | --- | --- |
| Feature | `feature/<issue>-<short-name>` | `feature/42-account-email` |
| Bug fix | `fix/<issue>-<short-name>` | `fix/43-login-validation` |
| Documentation | `docs/<issue>-<short-name>` | `docs/44-team-workflow` |
| Maintenance | `chore/<issue>-<short-name>` | `chore/45-update-dependencies` |

Use lowercase kebab-case and the actual issue number; examples are not real task
assignments. Initial administrative work without an issue can omit the number,
such as `docs/team-workflow`; link a task before review. Existing active branches
need not be renamed. Avoid permanent person-specific integration branches.

`main` receives reviewed PRs. Do not push directly to it. Start from current
`origin/main`; inspect the working tree before switching. Preserve teammates'
uncommitted changes. Concurrent AIs need separate clones or worktrees and one
branch per task, not two branches in the same working tree.

## 3. Commit messages and PR titles

Use the same format for both:

```text
type(scope): imperative summary

Optional body explaining the reason and relevant behavior.

Refs: #42
```

[Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) defines
the message structure and feature/fix/breaking-change markers. Our team also
requires a scope, lowercase type/scope, an imperative summary ("add", "prevent"),
no trailing period, and a target of 72 characters or fewer. These extra style
choices are team rules, not universal requirements.

| Type | Use |
| --- | --- |
| `feat` | New user behavior or API capability |
| `fix` | Correct existing behavior |
| `docs` | Documentation only |
| `refactor` | Restructure without changing behavior |
| `test` | Add or correct tests |
| `style` | Formatting only; visual UI features use `feat` or `fix` |
| `perf` | Improve performance |
| `build` | Build tooling or dependency configuration |
| `ci` | Continuous integration configuration |
| `chore` | Other repository maintenance |
| `revert` | Reverse a change; explain why and reference its commit |

Use the narrowest stable domain scope: `auth`, `account`, `assessment`,
`competency`, `content`, `progress`, `pathway`, or `certificate`. Shared work
can use `ui`, `api`, `db`, `deps`, `docs`, `repo`, or `ci`. Scopes describe an
area, not an author or sprint. A domain scope works for both frontend and
backend; identify the changed layer in the body/PR.

```text
feat(auth): add login form and session verification
fix(auth): retain session when logout fails
feat(competency): add competency score CRUD
test(competency): cover deletion conflicts
docs(repo): standardize team contribution workflow
build(deps): update frontend development dependencies
```

For an incompatible contract/schema/consumer change, use `!` and explain impact:

```text
feat(api)!: change account response to include role objects

BREAKING CHANGE: roles now contains objects instead of strings. Update
consuming clients before rollout; see the linked migration task.
```

That is a syntax example, not a request to change an API. Avoid `updates`,
`final changes`, `Ethan's work`, or `fix stuff`. Put real issue references and
the ClickUp URL in the PR body. Do not invent ticket IDs.

## 4. Uniform commit cycle

1. **Claim and inspect:** agree on owner/reviewer/criteria; inspect the branch,
   working tree, affected code, and shared files.
2. **Agree on interfaces:** record endpoint/DTO/schema changes with affected
   owners before integration. Mark proposed endpoints as proposed.
3. **Make a coherent change:** implement one reviewable behavior, with needed
   tests/docs. Keep unrelated formatting out.
4. **Verify and inspect:** run relevant checks, inspect `git diff`, stage
   explicit paths or hunks, and inspect `git diff --cached`.
5. **Commit a meaningful checkpoint:** use the format above. No fixed commit
   count, hourly quota, or one-commit-per-file rule. Small tasks can be one
   commit; larger tasks can have several.
6. **Push and open a draft PR:** show scope, dependencies, and remaining work
   early using the [PR guide](docs/templates/pull-request.md).
7. **Review and correct:** add focused follow-up commits, rerun affected checks,
   and update validation evidence for the current revision.
8. **Approve and integrate:** obtain at least one other member's approval,
   resolve blocking findings, then **Squash and merge** into `main` when enabled.
   Use the final PR title for the squash title. Preserve useful rationale,
   issue references, and genuine co-author attribution in its body.
9. **Close and hand off:** verify the merged result, link evidence, update
   ClickUp, and delete the merged branch when it has no outstanding work.
   Start the next task from updated `main`.

For published branches, merge updated `origin/main` into the feature branch
when synchronization is needed. Resolve conflicts with the affected owner.
Do not routinely rewrite shared history or force-push. Git-generated merge
messages are exempt from the feature-commit format; the squash title is the
final reviewed record.

### Example cycle (Git Bash)

Run from the root with a clean working tree. Replace all example names/paths.
Publishing commands are for a person or an AI explicitly authorized to use them.

```bash
git status --short
git fetch origin
git switch main
git pull --ff-only origin main
git switch -c feature/42-account-email

# Make the scoped change and run applicable checks.
git diff --check
git diff
git add frontend/src/features/account/pages/AccountSettingsPage.jsx
git diff --cached
git commit -m "feat(account): allow updating the account email"
git push -u origin feature/42-account-email
# Open a PR targeting main using the repository template.
```

If fast-forward pull fails, investigate divergence; do not reset history away.
Preserve uncommitted work and coordinate before switching branches. Do not use
`git add .` as the default in a shared checkout.

## 5. Validation and definition of done

Record the command/scenario, environment, expected/actual result, and evidence.
Use **Pass**, **Fail**, **Not run**, **Blocked**, or **N/A (reason)**. A Vite HTML
HTTP 200 or successful build does not prove browser interaction or backend login.

| Change | Relevant checks |
| --- | --- |
| Frontend behavior | Lint, build, applicable automated tests, manual interaction/responsive checks for changed UI |
| Backend behavior | Applicable Maven tests; API success, validation, missing-record and relationship-conflict cases as relevant |
| API/schema contract | Provider/consumer compatibility, integration tests, migration/data impact |
| Documentation only | Paths, links, examples, consistent terminology, `git diff --check`; application builds usually N/A |

From `frontend/`:

```bash
npm ci
npm run lint
npm run build
node --test tests/account-features.test.js
```

Install only when dependencies need restoring. Run the named test suite for
auth/account/shared API changes; add meaningful tests for other domains as they
are implemented. There is currently no general `npm test` script.

From `backend/`, use an isolated database:

```bash
# Git Bash / macOS / Linux
./mvnw "-Dspring.datasource.url=jdbc:h2:mem:aikademya-tests" "-Dspring.jpa.hibernate.ddl-auto=create-drop" test
```

```powershell
# PowerShell
.\mvnw.cmd "-Dspring.datasource.url=jdbc:h2:mem:aikademya-tests" "-Dspring.jpa.hibernate.ddl-auto=create-drop" test
```

The existing context-load test otherwise inherits local database settings.
Tests must use disposable data. Mocked API tests do not verify live sessions;
backend compilation alone is not a test run.

Done means criteria met for the stated scope, relevant checks passed, human
peer review complete, docs/contracts updated, changes merged, and evidence
linked. A frontend subtask can be done while its end-to-end parent stays open
for missing backend integration. Use `Refs #N` for partial work and `Closes #N`
only for a fully satisfied issue.

ClickUp flow: `Backlog -> To Do -> In Progress -> For Review -> Testing -> Done`.
Return findings to `In Progress`. Record blockers, dependency owner, and next
action on the task. No `In Progress` without ownership/criteria; no `Done`
without review and verification evidence.

## 6. Shared work and AI review

At least one human peer other than the author reviews each PR. AI review
supports that review; it does not constitute human approval. Re-review material
changes. Keep findings and evidence on the PR and link them from reports.

Coordinate edits to shared routes/providers/styles/API helpers, relationships,
schemas, and dependency manifests. Describe required cross-area changes and
integration order. Never copy a stale branch over teammates' files to resolve
conflicts. Known owners are in [team roles](docs/team-roles.md).

AI sessions start with [AGENTS.md](AGENTS.md) and the same
[task brief](docs/templates/ai-task-and-handoff.md). The author checks the diff.
Never commit secrets, local `.env` files, credentials, database files, `target/`,
`dist/`, or `node_modules/`. `.env.example` contains safe placeholders only.

## 7. Repository settings to adopt separately

Recommended: require PRs and one peer approval for `main`, resolve conversations
before merge, prevent force pushes/deletion of `main`, and enable squash merges
with the PR title as default commit title. Add required status checks after real
CI jobs exist and run reliably. These settings have **not** been applied here.

See GitHub's [branch protection](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-protected-branches/about-protected-branches)
and [squash merge settings](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/configuring-pull-request-merges/configuring-commit-squashing-for-pull-requests).
