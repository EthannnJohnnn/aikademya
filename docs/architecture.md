# Architecture – Aikademya

## Overview
Aikademya is a web application with a React (Vite) frontend.

## Tech Stack
| Layer | Technology |
|---|---|
| Frontend | React + Vite |
| Backend / Database | TBD (update when decided) |
| Version control | GitHub |
| Project management | ClickUp |


## Folder Structure
```
aikademya/
├── src/             # Application source code
├── tests/           # Test plan and test files
├── docs/            # Project documentation
├── README.md
├── CONTRIBUTING.md
└── .env.example
```


## Modules (Epics)
1. Authentication (AUTH) – login and registration
2. Diagnostic (DIAG) – diagnostic questionnaire
3. (Add remaining epics from the proposal)

## Data Flow
User → React UI → Service/API layer → Database/Storage

## Workflow
Feature branch → Pull Request → Peer review → Merge to `main`