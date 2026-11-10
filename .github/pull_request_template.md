<!-- Title: type(scope): imperative summary. See docs/templates/pull-request.md.
     Replace placeholders, remove guidance, and keep only sections relevant to this PR.
     Use Closes only when the linked issue's entire scope is complete; otherwise use Refs. -->

## Purpose and result

<!-- What problem does this solve, and what changes for the user? Give a concrete before/after when helpful. -->

## Traceability

- GitHub issue: <!-- Closes #N or Refs #N -->
- ClickUp task: <!-- URL -->
- Owner: <!-- teammate -->
- Peer reviewer: <!-- teammate other than the author -->

## Scope

- Included: <!-- focused changes and affected frontend/backend/docs areas -->
- Deferred: <!-- dependencies or follow-up issues; write None if complete -->

## Verification

<!-- Record actual outcomes. A build pass is not an end-to-end pass.
     Use Pass, Fail, Not run, Blocked, or N/A (reason); explain unverified checks. -->

| Check / command | Environment / working directory | Expected result | Actual result / evidence |
| --- | --- | --- | --- |
| <!-- command, manual scenario, or docs check --> | <!-- directory, safe fixture, browser if relevant --> | <!-- expected --> | <!-- Pass / Fail / Not run / Blocked / N/A, concise evidence --> |

<!-- For UI changes, include relevant desktop/mobile screenshots or explain unavailable browser verification.
     For API changes, include sanitized request/response examples and status codes. -->

## Integration and handoff

- Status: <!-- Fully integrated / Frontend only, backend pending / Backend only, frontend pending / Documentation only -->
- Contract or shared-file changes: <!-- endpoints, payloads, schema, routes, context, configuration; or None -->
- Pending dependencies and owner: <!-- linked task, owner, next action; or None -->
- Risks / rollback: <!-- include only for a material behavior, migration, or deployment risk; otherwise remove this line -->

## Review checklist

- [ ] The scope matches the linked acceptance criteria; unfinished work is identified.
- [ ] Relevant checks and limitations are recorded above.
- [ ] Shared interfaces and documentation are updated where affected.
- [ ] The diff contains no unrelated changes, secrets, or real personal test data.
- [ ] If AI assisted, the author inspected the diff and verified its claims.
