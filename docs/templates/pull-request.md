# Pull request titles and descriptions

Use the default [GitHub PR template](../../.github/pull_request_template.md).
The shared development and merge policy lives in [CONTRIBUTING.md](../../CONTRIBUTING.md).
Keep one PR focused on one task or independently reviewable outcome.

## Title format

```text
type(scope): imperative summary
```

Use a lowercase type and scope, an imperative verb (add, fix, prevent, document),
and a concise description of the final result. Aim for 72 characters or fewer;
clarity takes precedence. Omit a trailing period. Put issue links in the body.
The title should also work as the squash commit subject.
The required scope, imperative wording, and length target are team conventions
built on [Conventional Commits 1.0.0](https://www.conventionalcommits.org/en/v1.0.0/).
Use the scope registry in CONTRIBUTING as the source of truth.

| Area | Example title |
| --- | --- |
| Frontend feature | `feat(auth): add login form and session verification` |
| Frontend fix | `fix(auth): retain session after failed logout` |
| Backend feature | `feat(competency): add competency score CRUD` |
| Backend fix | `fix(competency): prevent deletion of referenced competencies` |
| Tests | `test(auth): cover unavailable authentication endpoints` |
| Documentation | `docs(repo): standardize team contribution templates` |

Use `type(scope)!: summary` only for an actual breaking change. Explain the old
and new contract, affected consumers, and migration in the description; include
a `BREAKING CHANGE:` footer in the resulting commit body. Do not call a feature
breaking merely because it is large.

## Description rules

1. Lead with the problem and resulting behavior. Reviewers should understand the
   change without reading the original AI conversation.
2. Link the GitHub issue and ClickUp task; identify owner and peer reviewer.
3. State what this PR delivers and which work remains in linked follow-ups.
4. Record exact verification commands or manual scenarios and actual outcomes.
   Use `Pass`, `Fail`, `Not run`, `Blocked`, or `N/A (reason)`, with environment
   and evidence. `Not run — browser unavailable` is useful; a bare “tested” is not.
5. Call out endpoint, payload, schema, route, context, or shared configuration
   changes, including the other owner who needs to integrate them.
6. Add screenshots for visible changes and sanitized examples for API changes
   when useful. Include rollback details for material migrations or deployment
   risks, rather than adding boilerplate to every documentation change.
7. Rewrite the title and description if scope changes during review. Remove
   abandoned plans and temporary implementation history unless they explain a
   decision the reviewer must assess.

Use `Closes #N` only if this PR finishes that issue's agreed scope. For a partial
implementation or related work, use `Refs #N` and link the remaining task.
A frontend-only task can be complete while an end-to-end feature remains blocked;
name that distinction explicitly. Keep the PR in draft if its own acceptance
criteria are not ready for review.

## Example: frontend task with a pending backend

```markdown
## Purpose and result

Teachers can submit the login form and receive clear validation and API errors.
The app verifies the server session before opening Account Settings.

## Traceability

- GitHub issue: Refs #<actual issue number>
- ClickUp task: <actual task URL>
- Owner: Ethan
- Peer reviewer: <assigned teammate>

## Scope

- Included: login, logout, session state, protected account route, email editing.
- Deferred: backend session/account endpoints in <linked issue>; registration
  and recovery remain Cayl's separate tasks.

## Verification

| Check / command | Environment / working directory | Expected result | Actual result / evidence |
| --- | --- | --- | --- |
| `npm run build` | `frontend/`, <Node version> | Production bundle succeeds | <actual outcome> |
| `npm run lint` | `frontend/`, <Node version> | No lint errors | <actual outcome> |
| Open `/account` without a session | <browser and local URL> | Redirect to login | <actual outcome or Not run + reason> |
| Submit valid-format test credentials while auth API is absent | <browser, local URL, backend status> | Readable failure; no authenticated session | <actual outcome or Not run + reason> |

## Integration and handoff

- Status: Frontend only, backend pending.
- Contract: `/api/auth/login`, `/api/auth/me`, `/api/auth/logout`, and
  `/api/account/me`; see the frontend README for proposed payloads.
- Pending dependency: <backend issue>, owner <assigned teammate>.
- Next action: implement the server contract, then verify real login, cookie
  persistence, account updates, session expiry, and logout end to end.
```

This is a fill-in example, not evidence that any check ran. Replace every
placeholder and select `Closes` or `Refs` based on the actual issue scope.

## Review and merge

The author reviews the entire diff, including AI-generated work, before requesting
review. At least one teammate other than the author reviews it. Resolve required
feedback, record applicable checks, and use the team squash-merge cycle described
in CONTRIBUTING. A PR template is guidance; it does not configure branch
protection, required approvals, CI checks, or the repository's available merge methods.
