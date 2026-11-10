# AI task brief and handoff

Give every teammate's AI the same brief format. Link repository guidance instead
of maintaining competing copies. Use fictional test data and no credentials.

## Copy into a new AI task

```text
Project: AIKademya
Repository: <local checkout path>
Task / GitHub issue: <actual link or not assigned yet>
ClickUp: <actual link or not assigned yet>
Human owner: <name>
Peer reviewer: <name or pending assignment>
Expected branch: <branch; inspect actual branch before editing>
Mode: <documentation only / implementation / review only>

Read AGENTS.md, CONTRIBUTING.md, docs/architecture.md, docs/team-roles.md,
and existing affected files first.

Outcome:
<Who needs what behavior, and why?>

In scope / allowed files or areas:
<Specific boundaries; identify necessary shared-file changes.>

Out of scope / other members' work:
<Exclusions, placeholders to preserve, and unrelated files.>

Acceptance criteria:
1. <Observable result and relevant error/edge behavior>
2. <Observable result>

Dependencies / contracts:
<Existing/proposed endpoint, payload, ID types, task links, dependency owner.>

Verification:
<Commands, manual scenarios, safe test environment.>

Git/publication permissions:
Edit files and run relevant local checks. Do not commit, push, open/merge
a PR, contact others, or change repository settings unless separately authorized.

Preserve unrelated work. Identify conflicts/missing contracts explicitly.
Return the handoff format from docs/templates/ai-task-and-handoff.md.
```

## Return at the end of the task

```markdown
Outcome: <implemented / partially implemented / reviewed / blocked>
Task and branch: <actual values>

### Behavior and scope
<Concrete change and which criteria it satisfies.>

### Files
- <path>: <reason changed>
- Shared files touched: <paths or none>

### Verification
| Command or scenario | Environment | Result | Evidence / limitation |
| --- | --- | --- | --- |
| <exact command> | <working directory and safe fixture> | <Pass / Fail / Not run / Blocked / N/A> | <actual result or reason> |

### Integration and next owner
- Contracts changed: <none or provider/consumer impact>
- Backend/dependency readiness: <verified / unavailable / proposed>
- Remaining work: <specific task and owner, or owner unassigned>
- Known limitations: <browser/API/testing gaps or none identified>

### Suggested Git text
Commit: <type(scope): summary>
PR title: <type(scope): final outcome>
PR body: <fill .github/pull_request_template.md or link a prepared draft>
Git actions performed: <actual actions; state if no commit/push occurred>
```

A handoff is not approval or proof of integration. The human owner reviews the
diff and evidence before publishing or requesting peer review.
