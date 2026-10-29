# Contributing to Aikademya

## Branching
- `main` – stable, working code only. Never push directly.
- `feature/<issue>-<short-name>` – new features (e.g. `feature/1-login-page`)
- `fix/<issue>-<short-name>` – bug fixes (e.g. `fix/8-login-validation`)

## Workflow
1. Pick a task card in ClickUp that has an owner and acceptance criteria.
2. Make sure a GitHub issue exists and the card links to it.
3. Create your branch from an up-to-date `main`.
4. Commit small and often.
5. Push and open a Pull Request that references the issue (`Closes #12`).
6. Request at least one peer reviewer (not yourself).
7. Fix review comments, then merge after approval.
8. Move the ClickUp card to Done only after review and verification.

## Commit Messages
Format: `type(scope): description`
Types: `feat`, `fix`, `docs`, `test`, `refactor`, `chore`
Examples:
- `feat(auth): implement login form`
- `docs(readme): add setup steps`

## Pull Requests
- Title is descriptive; the body references the issue (`Closes #N`).
- Describe what changed and how you tested it.
- At least 1 approval before merging.

## Code Review Checklist
- [ ] Meets the acceptance criteria
- [ ] App runs without errors
- [ ] No secrets, tokens or `.env` files committed
- [ ] Code is readable and follows project style

## Security
Never commit real secrets. Use `.env` locally and keep `.env.example` as a template.

## Task Status Rules
Backlog → To Do → In Progress → For Review → Testing → Done
- No **In Progress** without an owner and acceptance criteria.
- No **Done** without review and verification evidence.