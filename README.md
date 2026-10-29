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


Current milestone: Project setup, task traceability, and initial frontend scaffolding. Individual feature completion depends on development, testing, review, and (where applicable) backend API integration.
8. Technology Stack and Architecture
Layer	Technology	Responsibility
Frontend — CSIT 343	React, JavaScript, Vite	User interfaces, forms, navigation, lessons, and dashboards
Backend — CSIT 321	Java, Spring Boot	REST APIs, authorization, diagnostic scoring, pathway rules, progress, and certificates
Database	Relational database (selection to be finalized)	Accounts, schools, assessments, content, results, and progress
Integration	HTTP/JSON REST API	Communication between React and Spring Boot
Collaboration	GitHub and ClickUp	Source control, task tracking, issues, reviews, and sprint planning


The frontend may use mock data during development while Spring Boot endpoints are being implemented. Mock authentication and local demo state are not substitutes for secure production authentication or persistent records.
9. Getting Started (Frontend)
Prerequisites
- A recent Node.js LTS version with npm
- Git
- VS Code or another code editor
Clone the Repository
 git clone https://github.com/EthannnJohnnn/aikademya.git
 cd aikademya
Install and Run (after the React/Vite scaffold has been committed)
npm install
npm run dev
Vite will display a local development URL in the terminal (commonly http://localhost:5173). Open that address in your browser.
Build the Frontend
npm run build
Note: The commands npm install, npm run dev, and npm run build will work only after the React/Vite project files, including package.json, have been added to this repository. During the initial documentation-only phase, the frontend has not yet been initialized.
Environment Configuration
Once .env.example is added, create a local .env file using the example variables. The planned frontend API setting is:
VITE_API_BASE_URL=http://localhost:8080
Do not commit .env or real API keys, passwords, credentials, or other secrets. Vite variables beginning with VITE_ are exposed in client-side bundles and must never contain secrets.
10. Planned Repository Structure
aikademya/
├── README.md
├── .gitignore
├── .env.example
├── CONTRIBUTING.md
├── docs/
│   ├── requirements.md
│   ├── architecture.md
│   └── team-roles.md
├── src/                  # React source files (after Vite setup)
├── tests/                # Tests and/or test plans
├── package.json          # Generated during Vite setup
└── index.html            # Generated during Vite setup
This is a target structure. Some files and folders may not yet exist and will be added during project setup.
11. GitHub Collaboration and Development Workflow
1. Select an assigned ClickUp task and its matching GitHub issue.
2. Branch from the latest stable main using feature/<issue-number>-<short-name> (or fix/<issue-number>-<short-name> for fixes).
3. Make a focused change, test it, and commit it with a descriptive message.
4. Push the feature branch and open a pull request targeting main.
5. Request a review from another group member, address feedback, and record verification evidence.
6. Merge only after required review and checks pass; update the ClickUp status and issue.
Example for the existing AUTH-01 issue:
git switch main
git pull origin main
git switch -c feature/1-login-page
# Make and save your changes
git add .
git commit -m "feat(auth): create login page scaffold"
git push -u origin feature/1-login-page
- Existing issue: AUTH-01 – Login Page (#1)
- Use actual issue numbers assigned by GitHub for other features.
- Follow the team's rules in CONTRIBUTING.md once that file is added.
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
AIKademya is in initial setup/development. The feature backlog and intended architecture are documented; React implementation, Spring Boot integration, database persistence, dashboard reporting, and certificate verification must be completed and tested before being described as operational.
Developed by TRes — Cebu Institute of Technology – University
