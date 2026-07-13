Test Case Suite — Qase.io Projects & Cases (API + UI)

Project link: https://app.qase.io/login

## Project checklist
- [ ] Add dependencies and properties to pom.xml (TestNG, Selenide, Allure, Log, rest-assured, Lombok)
- [ ] Create base packages structure
- BaseTest
- BasePage
- LoginPage

- [ ] Hide password, login, token: 
- config.properties --> in gitignore, 
- config.properties.example (will be commited)
- Config --> gets Property
- property Reader (don't add to git config.properties )

- [ ] crossbrowsser testing
- add settings in basetest and base adapter
- set up testng.xml --> runner
- add listeners to testng.xml
- run with maven??

- [ ] set up allure.properties and allure
- add to base test
- create property
- add dependencies (including rest assured and so on)

1. Add a browser key to config.properties + a Config.getBrowser() getter
2. In BaseTest, branch on browser → build ChromeOptions/FirefoxOptions/EdgeOptions accordingly
3. Make the setup method accept it via TestNG injection: @Parameters("browser") + @Optional("chrome") on the parameter — not just reading a system property
4. In testng.xml: one <test> block per browser, each with its own <parameter name="browser" value="...">
5. Add <listeners> in testng.xml for any custom ITestListener
6. In pom.xml: add maven-surefire-plugin, point suiteXmlFiles at testng.xml — otherwise Maven ignores the whole suite
7. Add aspectjweaver as an explicit dependency + -javaagent argLine in that same surefire config — needed for @Attachment/@Step to actually work
8. Add allure-maven plugin for mvn allure:report / allure:serve
9. For API tests: wire .addFilter(new AllureRestAssured()) into your request spec, and make sure the request-spec class's dependency scope matches where it lives (main vs test)


- [ ] set up jenkins 
- [ ] Logs log4j2.xml
- [ ] APi tests

- [ ] what to add 
- @Step --> allure reports 
## Problems 
- Selenide should be moved to root test --> change in pom.xml to compile
## API Checklist

### API — Project

- [ ] API-PRJ-01 — Verify `GET /project` returns the project list with a valid token
- [ ] API-PRJ-02 — Verify `GET /project` with `limit=1` returns the minimum allowed number of records
- [ ] API-PRJ-04 — Verify `GET /project` with `limit=101` is rejected as above the maximum limit
- [ ] API-PRJ-05 — Verify `GET /project/{code}` returns data for an existing project
- [ ] API-PRJ-06 — Verify `GET /project/{code}` returns an error for a non-existent project code
- [x] API-PRJ-07 — Verify `POST /project` creates a project with valid data
- [ ] API-PRJ-08 — Verify `POST /project` returns an error when a required field is missing
- [ ] API-PRJ-09 — Verify `DELETE /project/{code}` deletes an existing project
- [ ] API-PRJ-10 — Verify `PATCH /project/{code}` updates an existing project with valid data
- [ ] API-AUTH-01 — Verify requests with a missing or invalid token are rejected

### API — Case

- [ ] API-CASE-01 — Verify `GET /case/{code}` returns the case list for an existing project
- [ ] API-CASE-02 — Verify `GET /case/{code}` filters cases by status and priority
- [ ] API-CASE-03 — Verify `GET /case/{code}` returns an error for a non-existent project code
- [ ] API-CASE-04 — Verify `POST /case/{code}` creates a case with valid data
- [ ] API-CASE-05 — Verify `POST /case/{code}` returns an error when the title is empty
- [ ] API-CASE-06 — Verify `DELETE /case/{code}/{id}` deletes an existing case
- [ ] API-CASE-07 — Verify `GET /case/{code}/{id}` returns data for an existing case
- [ ] API-CASE-08 — Verify `GET /case/{code}/{id}` returns an error for a non-existent case ID
- [ ] API-CASE-09 — Verify `PATCH /case/{code}/{id}` updates an existing case with valid data
- [ ] API-CASE-BULK-01 — Verify `POST /case/{code}/bulk` creates multiple cases with valid data
- [ ] API-CASE-BULK-02 — Verify `POST /case/{code}/bulk` handles a batch containing one invalid case

## UI Checklist

### UI — Project

- [ ] UI-PRJ-01 — Verify a project can be created via the form with valid data
- [ ] UI-PRJ-02 — Verify project creation is blocked when a required field is empty
- [ ] UI-PRJ-03 — Verify project creation is blocked when the project code already exists
- [ ] UI-PRJ-04 — Verify a created project can be found using search
- [ ] UI-PRJ-05 — Verify a created project can be deleted
- [ ] UI-PRJ-06 — Verify an existing project can be edited

### UI — Case
- [ ] UI-CASE-01 — Verify cases can be filtered by status
- [ ] UI-CASE-02 — Verify a case can be created via the form with valid data
- [ ] UI-CASE-03 — Verify a case can be created with special characters or emoji in the title
- [ ] UI-CASE-04 — Verify case creation is handled correctly when the title exceeds the maximum field length
- [ ] UI-CASE-05 — Verify an existing case can be edited
- [ ] UI-CASE-06 — Verify a case can be deleted with confirmation
- [ ] UI-CASE-BULK-01 — Verify cases can be imported from file into a project
- [ ] UI-CASE-BULK-02 — Verify multiple cases can be selected and bulk-deleted
- [ ] UI-CASE-BULK-03 — Verify multiple cases can be selected and bulk-updated for a shared field such as status or priority
- [ ] UI-CASE-BULK-04 — Verify importing invalid file (wrong type or bad row) is rejected 
