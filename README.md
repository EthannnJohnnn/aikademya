# AIKademya
A Competency-Based AI Literacy and Responsible-Use Training Platform for Philippine K–12 Teachers

Team: TRes
Institution: Cebu Institute of Technology – University (CIT-U)
Courses: CSIT 321 – Applications Development and Emerging Technology (Backend) and CSIT 343 – Industry Elective (Frontend)
Project status: Academic project in planning and initial development. The features described below are planned requirements, not a claim that they are already implemented or deployed.

1. Project Overview
AIKademya is a web-based professional learning platform designed to help Philippine basic education teachers develop the knowledge and judgment needed to use artificial intelligence responsibly in teaching. Rather than providing a generic sequence of lessons to every user, AIKademya aims to identify individual competency gaps through an initial diagnostic assessment and assign suitable training modules through clear, predefined rules.

Teachers will learn through short lessons, multiple-choice assessments, instructor-authored classroom scenarios, and practical exercises. The platform will track their progress and generate verifiable training-completion records after specified requirements are satisfied. School heads and authorized administrators will be able to view appropriate aggregate competency and completion information.

AIKademya's core assessment, recommendations, feedback, and progress tracking are rule-based; they do not require generative AI to function.
2. Problem Statement
AI tools such as ChatGPT are increasingly used for lesson preparation and assessment development, but teachers may lack structured training in evaluating AI output, protecting personal information, preserving academic integrity, writing effective prompts, and aligning AI-assisted materials with curriculum expectations. AIKademya addresses this gap with accessible, practical, competency-focused professional development for Philippine K–12 educators.
3. Project Objectives
- Assess teachers' baseline AI literacy across five competency domains.
- Identify training needs and recommend learning modules using predefined, explainable rules.
- Provide lessons and realistic classroom scenarios that teach responsible AI use.
- Monitor module completion and competency development.
- Generate verifiable completion records when training requirements are met.
- Give school heads and authorized administrators school-appropriate, aggregated readiness insights.
4. AI Literacy Competencies
1. Prompt Construction — Write clear prompts for lesson planning and other teaching tasks.
2. AI Output Verification — Check facts, sources, limitations, and possible inaccuracies.
3. Ethical Use and Academic Integrity — Use AI fairly, transparently, and appropriately.
4. Privacy Awareness — Protect student, teacher, and school information.
5. Curriculum Alignment — Evaluate whether AI-assisted materials fit applicable learning objectives and grade levels.
5. Target Users and Roles
Role	Purpose	Intended Access
Teacher	Participates in diagnostic assessments and learning activities	Own assessments, assigned modules, progress, and certificates
School Head	Monitors AI readiness and professional development within an assigned school	School-level aggregate competency and completion dashboards
Administrator	Manages the platform or training content within authorized scope	User/institution administration, learning content, diagnostics, and broader reporting


One person may hold more than one authorized role (for example, Teacher and School Head), subject to the final role/permission design. The Spring Boot backend must enforce permissions; hiding frontend navigation alone is insufficient.
6. Core Learning Workflow
Teacher Registration/Login
          ↓
Diagnostic Assessment
          ↓
Competency Results and Gap Identification
          ↓
Rule-Based Personalized Learning Pathway
          ↓
Learning Modules, Lessons, and Classroom Scenarios
          ↓
Progress Tracking and Completion Checks
          ↓
Verifiable Training-Completion Certificate
Functional Requirements
Must-have
- Secure authentication and role-based access.
- Multiple-choice and scenario-based diagnostic assessment.
- Competency results across the five AI literacy domains.
- Deterministic, results-based assignment of learning modules.
- Lessons, interactive scenario activities, and predefined feedback.
- Teacher progress tracking, assessments, and completion records.
- Certificate generation and verification after requirements are met.
Should-have
- Authorized module, lesson, diagnostic, and scenario content management.
- School-level dashboards showing aggregate completion and competency gaps.
- Offline-accessible learning materials and progress synchronization when feasible.
- Exportable reports for teacher professional development planning.
Future/optional enhancements
- AI-assisted extra practice scenarios and a curated-knowledge policy chatbot.
- AI output verification challenges and guided prompt construction exercises.
- Optional reflection feedback and targeted learning recommendations.
Optional AI features must not replace the rule-based core platform. AIKademya-generated certificates are platform completion records, not automatically DepEd-accredited credentials.
7. Approved Frontend Feature Backlog (CSIT 343)
Epic	Feature ID	Feature
User Authentication	AUTH-01	Login Page
User Authentication	AUTH-02	Registration Page
User Authentication	AUTH-03	Role-Based Navigation
Diagnostic Assessment	DIAG-01	Diagnostic Questionnaire
Diagnostic Assessment	DIAG-02	Scenario-Based Questions
Diagnostic Assessment	DIAG-03	Results Page
Learning Pathway	PATH-01	Recommended Modules
Learning Pathway	PATH-02	Personalized Pathway Display
Learning Modules	MOD-01	Module Catalog
Learning Modules	MOD-02	Lesson Viewer
Learning Modules	MOD-03	Interactive Scenarios
Progress and Certification	PROG-01	Teacher Progress Dashboard
Progress and Certification	PROG-02	Certificate Page
Administration	ADMIN-01	School Dashboard
Administration	ADMIN-02	Learning Content Management


Current milestone: Implemented frontend scaffolding and backend CRUD are being developed into integrated features. Individual feature completion depends on testing, review, and backend API integration; see the architecture document and linked task evidence.
8. Technology Stack and Architecture
Layer	Technology	Responsibility
Frontend — CSIT 343	React, JavaScript, Vite	User interfaces, forms, navigation, lessons, and dashboards
Backend — CSIT 321	Java, Spring Boot	REST APIs, authorization, diagnostic scoring, pathway rules, progress, and certificates
Database	Relational database (selection to be finalized)	Accounts, schools, assessments, content, results, and progress
Integration	HTTP/JSON REST API	Communication between React and Spring Boot
Collaboration	GitHub and ClickUp	Source control, task tracking, issues, reviews, and sprint planning


The frontend may use mock data during development while Spring Boot endpoints are being implemented. Mock authentication and local demo state are not substitutes for secure production authentication or persistent records.
9. Getting Started

The React app is in `frontend/`; Spring Boot is in `backend/`.

```bash
cd frontend
npm ci
npm run dev
```

For environment setup and frontend commands, see [frontend/README.md](frontend/README.md).
For backend commands, database isolation during tests, and application boundaries,
see [docs/architecture.md](docs/architecture.md). Keep local `.env` files and secrets
out of Git; `VITE_` values are visible in the browser.

10. Repository Structure

```text
aikademya/
|-- backend/          # Spring Boot application and tests
|-- frontend/         # React/Vite application and tests
|-- docs/             # Architecture, responsibilities, contracts and templates
|-- tests/            # Project-level test plan
|-- .github/          # Issue and PR templates
|-- AGENTS.md         # Shared instructions for teammate AI assistants
|-- CONTRIBUTING.md   # Branch, commit, PR and verification workflow
`-- README.md
```

The detailed structure and current implementation boundaries are documented in
[architecture.md](docs/architecture.md).

11. Team Workflow and Templates

Read [CONTRIBUTING.md](CONTRIBUTING.md) for the uniform cycle:

Task and acceptance criteria -> focused branch -> coherent commits and checks ->
pull request -> peer review -> verification -> squash merge -> handoff.

Use `type(scope): imperative summary` for commit messages and PR titles, for
example `feat(auth): add login form and session verification`. Use the actual
GitHub issue number in new task branches; all examples in the guides are illustrative.

- [Documentation index and template recommendations](docs/README.md)
- [PR title and description guide](docs/templates/pull-request.md)
- [Default PR description template](.github/pull_request_template.md)
- [AI task brief and handoff](docs/templates/ai-task-and-handoff.md)
- [Shared AI instructions](AGENTS.md)
- [API contract template](docs/templates/api-contract.md)
- [Team responsibilities](docs/team-roles.md)

GitHub templates take effect after reaching the remote default branch. Branch
protection, merge settings and CI need separate configuration; these documents
are not evidence those settings are already enabled.

12. Team Members and Proposed Responsibilities
Member	Section	Proposed Role
Ethan John Montejo	G3	GitHub & Frontend Lead — repository setup, React foundation, initial login UI
Cayl Joseph Redublado	G3	Feature & Sprint Lead — backlog/issues, requirements, sprint tracking
Christian Jake Alalim	G3	Documentation & QA Lead — architecture documentation, testing, peer reviews


These responsibilities are initial project assignments and can be revised by the group. All members should contribute through their own accounts and participate in review and verification.
13. Project Management
Team: TRes
Repository: https://github.com/EthannnJohnnn/aikademya
ClickUp Task Board: https://app.clickup.com/1300440000010246/v/li/1300440000066178

The ClickUp board tracks the 6 epics and 15 features, along with user stories, priorities, acceptance criteria, dependencies, assignments, reviewers, estimates, deadlines, and GitHub issue links.
14. Data Privacy and Responsible Use
- Use fictional classroom/student data in practice scenarios and development testing.
- Limit personal assessment data to authorized users and reporting scopes.
- Provide school heads with aggregate results where practical.
- Review curriculum-specific and policy-based instructional content before publication.
- Do not present training completion as professional accreditation or official DepEd certification without authorization.
15. Current Development Status
AIKademya has a React/Vite frontend with login/account interfaces and a Spring Boot backend with CRUD resources. Secure authentication/account APIs and the integrated learning workflow remain pending. See docs/architecture.md for the current boundaries and each PR for verification evidence. Do not treat implemented CRUD or frontend placeholders as proof that a complete feature is operational.
Developed by TRes — Cebu Institute of Technology – University
