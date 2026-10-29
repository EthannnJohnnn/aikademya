# Contributing to Aikademya

## Branching
- `main` – stable code only. Never push directly to it.
- `feature/<issue>-<short-name>` – new features (e.g. `feature/1-login-page`)
- `fix/<issue>-<short-name>` – bug fixes (e.g. `fix/8-login-validation`)


## Commit Messages
Format: `type(scope): description`

Types: `feat`, `fix`, `docs`, `test`, `refactor`, `chore`

Examples:
- `feat(auth): implement login form`
- `docs(readme): add setup steps`

## Pull Request Workflow
1. Pick a ClickUp task that has an owner and acceptance criteria.
2. Make sure a GitHub issue exists and is linked to the card.
3. Branch from an up-to-date `main`.
4. Commit small and often, then push your branch.
5. Open a PR that references the issue (`Closes #N`).
6. Request at least one peer reviewer (not yourself).
7. Address review comments, then merge after approval.
8. Move the ClickUp card to Done only after review and verification.

## Review Checklist
- [ ] Meets the acceptance criteria
- [ ] App runs without errors
- [ ] No secrets or `.env` files committed
- [ ] Code is readable and consistent

## Task Status Rules
Backlog → To Do → In Progress → For Review → Testing → Done
- No **In Progress** without an owner and acceptance criteria.
- No **Done** without review and verification evidence.

## Security
Never commit real secrets. Keep them in a local `.env` and update `.env.example` with placeholders only.