# Team documentation and templates

Start with [CONTRIBUTING.md](../CONTRIBUTING.md) for workflow and
[architecture.md](architecture.md) for application structure. Update a rule or
contract in the PR that changes it.

## Where each fact belongs

| Information | Canonical location |
| --- | --- |
| Purpose and entry points | [Root README](../README.md) |
| Layers, data flow, current integration boundaries | [Architecture](architecture.md) |
| Branches, commits, PR cycle, checks, done criteria | [Contributing](../CONTRIBUTING.md) |
| Shared instructions for every AI | [AGENTS.md](../AGENTS.md) |
| Coordination roles and known feature ownership | [Team roles](team-roles.md) |
| Task scope, acceptance, owner, reviewer, dependencies | GitHub issue linked to ClickUp |
| Review/validation for a specific change | Its PR, linked from reports |
| New/changed API behavior | Contract linked from provider/consumer tasks |
| Frontend setup and pending auth contract | [Frontend README](../frontend/README.md) |

Do not keep agreements solely in individual AI chats. Record their agreed
summary in the issue or repository so each teammate's AI can read it.

## Templates included now

| Template | Use |
| --- | --- |
| [Default PR body](../.github/pull_request_template.md) | Every code/documentation PR |
| [PR titles and examples](templates/pull-request.md) | Describing the final change |
| [Feature/task issue](../.github/ISSUE_TEMPLATE/feature_request.md) | Features, improvements, docs, maintenance |
| [Bug report](../.github/ISSUE_TEMPLATE/bug_report.md) | Reproducible defects |
| [AI brief and handoff](templates/ai-task-and-handoff.md) | Starting/ending an AI session |
| [API contract](templates/api-contract.md) | Frontend/backend coordination |

After `.github` templates reach the remote default branch, GitHub can offer issue
templates and prefill PR bodies. Title conventions are not automatically enforced.
See the [GitHub template documentation](https://docs.github.com/en/communities/using-templates-to-encourage-useful-issues-and-pull-requests/creating-a-pull-request-template-for-your-repository).

## Additional templates to introduce when needed

| Trigger | Suggested artifact | Essential content |
| --- | --- | --- |
| Substantial architecture choice | `docs/decisions/NNNN-short-title.md` (ADR) | Context, alternatives, decision, consequences, owner, date, status |
| Feature verification | Extend [tests/test-plan.md](../tests/test-plan.md) | Criterion, fixture, environment, steps, expected/actual, date, tester, evidence |
| First shared deployment | `docs/runbooks/deployment.md` | Prerequisites, environment variable names, deploy/health checks, rollback, operator |
| Persisted data changes | `docs/templates/database-change.md` | Schema before/after, migration, backfill, compatibility, recovery, tests |
| First tagged release | `CHANGELOG.md` + release checklist | Visible changes, breaking changes, migrations, known issues, tag, verification |
| Ownership agreed with real GitHub handles | `.github/CODEOWNERS` | Confirmed accounts/teams and paths, including shared files |
| Repeatable iteration planning | `docs/templates/sprint-plan.md` | Goal, capacity, issue links, dependencies, exit criteria, review date |

Small changes need an issue, focused PR, and honest validation. An ADR is for a
real decision with alternatives, not every component. Link records instead of
copying the same checklist into several reports. Never guess usernames or approvals.

## Adoption order

1. Review the workflow together: agree on scopes, ownership, review, and squash merge.
2. Merge docs/templates through a PR. Give every AI `AGENTS.md` and the same task brief.
3. Configure the recommended GitHub settings in `CONTRIBUTING.md` separately.
4. Agree on missing authentication/account API contracts before integration.
5. Add CI and PR-title checks as a later configuration task; none is installed here.

## Existing reports

[Contribution matrix](contribution-matrix.md), [review log](review-log.md), and
[verification report](verification-report.md) include placeholders or historical
claims without linked evidence. They are not fresh proof of verification.
Future records should identify a PR/commit, person, date, and evidence. The empty
[requirements.md](requirements.md) needs the approved backlog and criteria in
a separate planning task; these templates do not invent requirements.
