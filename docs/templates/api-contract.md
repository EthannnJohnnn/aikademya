# API contract / integration agreement

Use for new/changed frontend/backend interfaces. Copy into the issue or
`docs/contracts/<domain>-<operation>.md`. Reuse an existing agreement if present.

- Status: **Proposed / Agreed / Implemented / Verified / Deprecated**
- GitHub issue and ClickUp links:
- Provider owner and consumer owner:
- Reviewer / agreement date:
- Implementation PRs:

## Purpose and current behavior

<Needed operation, what exists today, what remains unavailable.>

## Request

- Method and path:
- Authentication/authorization: <session identity, roles, ownership>
- Cookie, CORS, and CSRF requirements, if relevant:
- Path/query parameters: <name, type, required, constraints>
- JSON fields: <types, nullability, validation, fictional example>
- Unknown/disallowed fields: <reject/ignore policy, privileged fields>
- Identifier mapping: <Java type, entity key, JSON representation>

## Response and errors

- Success status and body (or explicitly empty):
- Field types, nullability, roles, and date/time format:
- Error shape/status for each failure case:
- Invalid input, missing record, unauthenticated, unauthorized, conflict cases:
- Retry/idempotency behavior, if relevant:

## Compatibility and rollout

- Existing consumers affected:
- Schema/migration/backfill impact, if applicable:
- Integration order / blocking tasks:
- Breaking change and consumer migration, if applicable:
- Rollback or forward repair, if applicable:

## Verification

| Case | Provider evidence | Consumer evidence | Status |
| --- | --- | --- | --- |
| <success / error / edge case> | <test or API run> | <test or browser run> | <Not run initially> |

Agreement does not mean implementation. A mocked response verifies consumer
assumptions, not the live provider. `Verified` requires relevant evidence.
