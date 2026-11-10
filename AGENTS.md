# Shared instructions for AI assistants

Read this before working in AIKademya. For tools that do not discover `AGENTS.md`,
attach it to the AI's task explicitly. The human's current task defines authorized
scope; describe conflicts with guidance instead of silently broadening the work.

## Read and inspect first

1. Read `CONTRIBUTING.md`, `docs/architecture.md`, and `docs/team-roles.md`.
2. Read the task/issue criteria and relevant feature/contract documentation.
3. Inspect the actual branch, `git status --short`, and affected source files.
   Do not assume a pasted branch name or API description matches the checkout.
4. Preserve unrelated work. Concurrent workers need isolated checkouts/worktrees.
   Do not switch branches in another active worker's checkout.

## Scope and architecture

- Follow the task, not adjacent backlog items. Documentation-only work changes
  documentation/templates, not application logic or dependencies.
- Follow existing React feature folders and Spring Boot layers. Reuse installed
  dependencies and repository conventions.
- Inspect contracts and identifier types; do not invent endpoints, fields,
  migrations, task ownership, or successful authentication.
- Keep shared-file edits focused and list them in the handoff. Preserve other
  members' features/placeholders unless changing them is within scope.
- Document proposed contracts and follow-ups when integration is missing.
  Frontend route guards do not authorize backend operations.
- Avoid unnecessary packages, frameworks, production mock sessions, sweeping
  formatting, and unrelated refactors.

## Verify and hand off

- Use applicable checks from `CONTRIBUTING.md`. Documentation-only work needs
  path/link/content checks, not application tests by default.
- Report actual commands and outcomes. Distinguish build, automated tests,
  browser checks, and live backend integration.
- Mark unavailable checks `Not run` or `Blocked`, with reasons. Do not fabricate
  evidence, approval, issue IDs, or CI results.
- Keep passwords, tokens, private data, and `.env` contents out of logs and docs.
- Follow `docs/templates/ai-task-and-handoff.md` for the final handoff and
  `CONTRIBUTING.md` for suggested commit and PR text.
- Do not commit, push, open/merge a PR, publish, contact teammates, or change
  repository settings unless the current user authorizes that action. Editing
  files and running applicable local checks are normally within task scope.

## Coordination

The issue/ClickUp task identifies the owner and reviewer. `docs/team-roles.md`
records known boundaries; it does not authorize reassignment. Resolve unknown
scope/ownership through the human task owner. Continue independent useful work
while necessary clarification is pending. Flag shared-file conflicts early and
resolve against current code rather than overwriting it.
