# Team responsibilities and ownership

Use this document to coordinate work. The assigned GitHub issue and linked ClickUp
task are the current source of truth for implementation ownership, acceptance criteria
and reviewer. Update this document when the team changes standing responsibilities.

## Coordination roles

These roles are recorded in the project README as initial/proposed responsibilities.
They do not assign every feature or make any member the sole reviewer for an area.

| Member | Recorded coordination role | Focus |
| --- | --- | --- |
| Ethan John Montejo | GitHub & Frontend Lead | Repository coordination, React foundation and frontend integration |
| Cayl Joseph Redublado | Feature & Sprint Lead | Backlog/issues, requirements and sprint coordination |
| Christian Jake Alalim | Documentation & QA Lead | Architecture documentation, test planning, peer review and verification records |

Every member owns the correctness of their contributions and participates in peer
review. Human authors remain responsible for AI-generated changes and verification.

## Confirmed feature boundaries

The following frontend boundaries are documented in [frontend/README.md](../frontend/README.md).

| Area | Assigned member | Current boundary |
| --- | --- | --- |
| Login/logout, authentication state, protected routes, Edit Account | Ethan | Frontend auth/account features and proposed API clients |
| Registration and password recovery/reset | Cayl | `/register`, `/forgot-password`, `/reset-password`; placeholders are retained until implemented |
| Other frontend features and backend resources | Assign in the relevant issue | This document does not infer feature ownership from commit history or coordination titles |

Backend session/authentication implementation requires an assigned issue and agreement
with its frontend consumers. An absent endpoint is a dependency to record, not an
assignment to implement another teammate's task without coordination.

## Shared files and interfaces

Changes to `frontend/src/app/`, shared layouts/styles, `frontend/src/lib/`, dependency
manifests, backend DTOs/entities used across features and repository-wide configuration
can affect several members. Identify the affected consumer/owner in the issue or PR
and request review from an appropriate teammate. Coordinate overlapping edits or use
isolated worktrees rather than letting assistants overwrite the same working files.

For each task, record:

- GitHub issue and ClickUp link.
- One implementation owner and at least one peer reviewer.
- Scope, acceptance criteria and explicit exclusions.
- Files/interfaces likely to overlap with another task.
- Dependency issues/PRs, integration status and verification evidence.

The reviewer may also verify the work, but the author must not approve their own PR.
Coordination roles do not bypass the review/merge rules in
[CONTRIBUTING.md](../CONTRIBUTING.md).

## AI collaboration and handoff

Give each assistant the repository guidance, current task, ownership boundaries and
actual working-tree state. Assistants should inspect files before proposing changes;
they must not invent completed work, APIs, test results, approvals or task ownership.

Handoff notes should identify the branch and latest commit, changed files, checks run
with results, remaining dependencies and the next concrete action. Link the PR and
evidence once they exist; do not fill placeholders with guessed URLs or issue numbers.
Maintain delivery records in [contribution-matrix.md](contribution-matrix.md),
[review-log.md](review-log.md) and [verification-report.md](verification-report.md)
using evidence from completed work. Their existing example entries are not proof of
completion or approval.
