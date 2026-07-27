# Diploma checklist — how each requirement is met

## Project overview

Automated test suite (API + UI) for Qase.io's **Projects** and **Cases**
features — a real SaaS test-case-management product, not a toy app. Covers
both layers deliberately: REST-Assured-driven API tests hit `api.qase.io`
directly (fast, precise, good for negative/boundary cases), while
Selenide-driven UI tests exercise the actual browser flows a user would
follow (project/case creation, editing, deletion, bulk import).

## Architecture

- **Page Object Model** (`ui/pages/`) — one class per screen/component
  (`LoginPage`, `ProjectPage`, `ProjectsPage`, `CasesPage`, `CreateCasePage`,
  `EditCasePage`, `ViewCasePage`, `ImportCasePage`), all extending a shared
  `BasePage` for common waits. Every page method is `@Step`-annotated and
  logged, so an Allure report reads like a narrated walkthrough of the test.
- **Adapter pattern** for the API layer (`api/adapters/`) — `BaseAdapter`
  holds the shared RestAssured spec (base URI, auth header, Allure filter);
  `ProjectAdapter`/`CaseAdapter` expose one method per endpoint, returning
  typed response models (`api/models/`) instead of raw JSON.
- **Config layer** (`helpers/Config.java` + `PropertyReader.java`) — reads
  credentials from `config.properties` (gitignored) with a `-D` system
  property override, so the same code path works identically whether run
  locally or from Jenkins-injected credentials.
- **Test data** (`utils/TestDataGenerator.java`) — thread-safe unique name/code/
  title generation (timestamp + `AtomicLong` counter), so concurrent or
  repeated runs never collide on project codes.
- **Custom TestNG listeners** (`listeners/`) — `TestListener` for structured
  logging + failure screenshots, `RetryTransformer` for automatic retry
  wiring across every test class, decoupled from individual `@Test`
  annotations.

## Test coverage

Following this repo's own checklist convention (`READMELOCAL.md`), each test
maps to an ID (`UI-PRJ-XX`, `API-CASE-XX`, etc.) and the suite deliberately
spans all four categories: positive (happy path), negative (bad input, wrong
state), boundary (empty fields, duplicate codes), and edge cases (bulk
operations, invalid file imports).

## Engineering challenges actually solved this project

Worth highlighting in a defense — these weren't hypothetical, they were real
bugs hit and root-caused during development:

- **Cross-browser parallel execution corrupted itself.** Selenide's
  `Configuration`/`WebDriverRunner` state turned out to be shared static, not
  per-thread — running Chrome/Edge/Firefox concurrently let one thread's
  browser config clobber another's mid-launch. Diagnosed via the exact
  exception (`Conflicting browser name`), traced to the shared-state root
  cause, and made a deliberate trade-off (sequential, one-browser-per-run)
  rather than ship a flaky "fix."
- **A UI bug that only failed on the second delete, not the first.** Bulk
  project cleanup silently stopped after deleting just one item. Root-caused
  by capturing and reading the actual failure screenshot + page HTML at the
  failure moment — found the delete button selector was unscoped and
  collided with stale DOM state after the first deletion.
- **Account-level constraints from the real Qase service** — a free-tier
  2-project cap and a login rate limit — turned "why do my tests keep
  failing" into a proper fixture-lifecycle design (`@BeforeMethod`/
  `@AfterMethod` project creation+deletion per test) instead of manual
  account housekeeping.
- **Standing up Jenkins locally from scratch** (no Docker) surfaced a string
  of real CI issues, each independently diagnosed: credential scope
  ("System" vs "Global"), Git's local-checkout security block, a Maven tool
  name mismatch, Windows vs Unix shell steps, and — the last fix — the
  difference between a build that's `FAILURE` (broken) vs `UNSTABLE` (tests
  failed but the pipeline itself is healthy).

## Project structure

```
Qase-diploma/
├── Jenkinsfile                          # CI pipeline definition
├── pom.xml                              # Maven build + dependency config
├── README.md                            # Real, actively-maintained checklist — source of truth for UI-XXX/API-XXX IDs
│
└── src/
    ├── main/java/
    │   ├── api/
    │   │   ├── adapters/                # REST-Assured calls, one class per resource
    │   │   │   ├── BaseAdapter.java     #   shared spec: base URI, auth header, Allure filter
    │   │   │   ├── ProjectAdapter.java  #   /project endpoints
    │   │   │   └── CaseAdapter.java     #   /case endpoints
    │   │   └── models/                  # Typed request/response DTOs
    │   │       ├── project/             #   ProjectRq, ProjectRs, ProjectErrorRs, Result
    │   │       └── cases/                #   CaseRq, CaseRs, CaseErrorRs, Result
    │   │
    │   ├── ui/
    │   │   ├── pages/                   # Page Object Model — one class per screen
    │   │   │   ├── BasePage.java        #   shared waits + AIDEN-promo-modal dismissal
    │   │   │   ├── LoginPage.java
    │   │   │   ├── ProjectPage.java / ProjectsPage.java
    │   │   │   ├── CasesPage.java / CreateCasePage.java / EditCasePage.java / ViewCasePage.java
    │   │   │   └── ImportCasePage.java
    │   │   └── dict/
    │   │       └── Elements.java        #   shared UI text/label constants
    │   │
    │   ├── helpers/
    │   │   ├── Config.java              # Reads credentials/settings (system property → config.properties fallback)
    │   │   └── PropertyReader.java      # Loads config.properties from classpath
    │   │
    │   └── utils/
    │       └── TestDataGenerator.java   # Thread-safe unique name/code/title generation
    │
    └── test/
        ├── java/
        │   ├── listeners/
        │   │   ├── TestListener.java        # Logs test lifecycle, screenshots on failure
        │   │   ├── Retry.java               # IRetryAnalyzer, up to 3 attempts
        │   │   └── RetryTransformer.java    # Wires Retry onto every BaseTest-derived test automatically
        │   │
        │   ├── tests/
        │   │   ├── base/
        │   │   │   └── BaseTest.java        # Browser setup/teardown, shared login helper
        │   │   ├── api/
        │   │   │   ├── ProjectAPITest.java  # API-PRJ-XX
        │   │   │   └── CaseAPITest.java     # API-CASE-XX
        │   │   └── ui/
        │   │       ├── ProjectTest.java     # UI-PRJ-XX
        │   │       ├── CaseTest.java        # UI-CASE-XX
        │   │       └── ImportCaseTest.java  # UI-CASE-BULK-XX
        │   │
        │   └── utils/
        │       └── AllureUtils.java         # Manual screenshot attachment helper
        │
        └── resources/
            ├── testng.xml                   # Suite definition — UI + API test blocks
            ├── log4j2-test.xml              # Console + file (target/tests.log) logging config
            ├── allure.properties            # Allure results directory
            ├── config.properties            # Real local secrets (gitignored)
            ├── config.properties.example    # Template, committed
            └── test-data/                   # Fixture files for import tests (json)
```

## Tech stack

| Layer | Choice |
|---|---|
| Language / build | Java 17, Maven |
| Test framework | TestNG |
| UI automation | Selenide (on Selenium WebDriver) |
| API automation | REST-Assured |
| Reporting | Allure |
| Logging | Log4j2 |
| Boilerplate reduction | Lombok |
| CI | Jenkins (Pipeline, local install) |

## Known limitations / honest gaps
- Parallel/multi-browser-in-one-run is not implemented (see section 2 below)
  — a real fix needs a `SelenideDriver`-per-thread refactor.
- A few tests have flagged-but-unfinished assertions (`ProjectTest`,
  `ImportCaseTest` bulk-edit/bulk-delete) — functional, but not yet asserting
  the full expected outcome.

## 0. Чеклист по тестированию
This file documents how each of the 6 required pieces is implemented in this
project, and how to reproduce/verify each one locally.

## 1. Maven
- `pom.xml` declares TestNG, Selenide, rest-assured, Allure (testng/selenide/
  rest-assured), Lombok, log4j2, Gson, json-schema-validator.
- `maven-surefire-plugin` is configured with `suiteXmlFiles` pointing at
  `src/test/resources/testng.xml` — this is what makes `mvn test` actually run
  the suite instead of nothing.
- `aspectjweaver` is wired in as a `-javaagent` argLine in that same surefire
  config — required for Allure's `@Step`/`@Attachment` annotations to work.
- `allure-maven` plugin is present for `mvn allure:report` / `allure:serve`.
- Verify: `mvn -q -DskipTests test-compile` builds clean.

## 2. TestNG — names, Retry, TestListener, parallel/multi-browser
- All test methods have descriptive names and most carry a
  `description = "UI-XXX / API-XXX — ..."` tying back to this repo's own
  checklist IDs (see `READMELOCAL.md`).
- **TestListener** (`listeners/TestListener.java`) logs test start/success/
  failure/skip with duration, and takes an Allure screenshot on failure if a
  WebDriver session is active. Registered in `testng.xml`'s `<listeners>`.
- **Retry** (`listeners/Retry.java`, up to 3 attempts) is wired globally via
  `listeners/RetryTransformer.java` — an `IAnnotationTransformer` that applies
  `retryAnalyzer = Retry.class` to every test method in any class extending
  `BaseTest`, automatically. Also registered in `testng.xml`.
- **Parallel/multi-browser — known limitation, not currently running.**
  This was attempted (`thread-count="4"`, one `<test>` block per browser) but
  reverted: Selenide's `Configuration`/`WebDriverRunner` state is shared
  static, not per-thread, so concurrent browser launches corrupted each other
  (`Conflicting browser name` crashes). `testng.xml` now has a single `"UI"`
  block; which browser runs is controlled by the `-Dbrowser` system property
  (Jenkins' `BROWSER` parameter, or `@Optional("chrome")` default locally).
  A real fix would need a `SelenideDriver`-per-thread refactor across
  `BasePage` and every page object — not done.
- Verify: run `mvn test -Dbrowser=firefox` locally and confirm the console log
  shows Firefox launching, then re-run with `-Dbrowser=edge`.

## 3. Selenium/Selenide
- Selenide chosen over raw Selenium. Used throughout every class in
  `src/main/java/ui/pages/`. `BaseTest.setUp()` configures
  `Configuration.browser`, `.baseUrl`, `.browserSize`, `.timeout`,
  `.clickViaJs`, screenshots/page-source-on-failure, and builds
  Chrome/Firefox/Edge-specific options (headless by default, toggleable via
  `-Dheadless=false`).

## 4. Allure Reporting
- Dependencies: `allure-testng`, `allure-selenide`, `allure-rest-assured`.
- `AllureSelenide` listener registered per test run — captures screenshot +
  page source on every step, not just failures.
- `@Step` annotations on essentially every page-object method, each with a
  descriptive, parameterized label (e.g. `"Fill in project form: '{projectName}', '{projectCode}'"`).
- `allure.properties` sets `allure.results.directory=target/allure-results`.
- Generate/view locally: `mvn allure:serve` (spins up a local report server) or
  `mvn allure:report` (writes static HTML to `target/site/allure-maven-plugin`).
- In Jenkins: the Allure Jenkins Plugin renders results directly on the build
  page via the `allure` pipeline step (see Jenkins section below).

## 5. Jenkins — full local setup walkthrough

**Getting Jenkins running (no Docker needed):**
1. Download `jenkins.war` (LTS) — this project keeps it in `build/downloads/`.
2. Run standalone with its own home directory, so it doesn't clutter the repo:
   ```
   set JENKINS_HOME=C:\Users\volhako\.jenkins-diploma
   java -jar jenkins.war --httpPort=8080
   ```
3. Open `http://localhost:8080`, unlock with the initial admin password
   (printed to console / written to `<JENKINS_HOME>\secrets\initialAdminPassword`).
4. Install **suggested plugins** (bundles Git, Pipeline, Credentials Binding).
5. Install the **Allure Jenkins Plugin** separately (Manage Jenkins → Plugins
   → Available) — needed for the `allure` step in the Jenkinsfile's `post`.

**Global Tool Configuration** (Manage Jenkins → Tools):
- **JDK**: add one, uncheck "install automatically", point at a real local JDK
  install (e.g. Temurin 21).
- **Maven**: add one named **exactly** `maven 3.9.6` — this string must match
  the Jenkinsfile's `tools { maven 'maven 3.9.6' }` literally, character for
  character (the name is just a label; it doesn't have to match the real
  Maven version installed — point it at whatever Maven is actually on disk).
- **Allure Commandline**: add one, name arbitrary, auto-install is fine.
- **Shell executable** (further down, under **Manage Jenkins → System**, not
  Tools, in recent Jenkins versions): set to Git's bundled `sh.exe`
  (e.g. `C:\Program Files\Git\bin\sh.exe`) — the Jenkinsfile's `sh` steps need
  a Unix-style shell, which native Windows Jenkins doesn't have by default.

**Credentials** (Manage Jenkins → Credentials → System → Global credentials):
- Kind **Username with password**, ID `qase-user-password` — real Qase
  login/password. A "Username with password" binding auto-generates
  `QASE_CREDS_USR`/`QASE_CREDS_PSW` env vars (not `QASE_USER`/`QASE_PASSWORD`).
- Kind **Secret text**, ID `qase-token` — real Qase API token.
- IDs must match exactly what `credentials('...')` calls in the Jenkinsfile
  reference. **Scope must be "Global (Jenkins, nodes, items, all child items,
  etc)"** — if left as "System (Jenkins and nodes only)", a Pipeline job can't
  see it at all and credential resolution silently fails.
- Local checkout safety: Git's plugin blocks `file://` checkouts by default
  ("references a local directory, which may be insecure"). For a local repo
  as the SCM source, this Jenkins instance needs to be started with
  `-Dhudson.plugins.git.GitSCM.ALLOW_LOCAL_CHECKOUT=true` as a JVM argument.

**Creating the Pipeline job:**
1. New Item → Pipeline.
2. Pipeline section → Definition: **Pipeline script from SCM**.
3. SCM: Git. Repository URL: `file:///C:/path/to/this/repo`. Branch: `*/master`.
   Script Path: `Jenkinsfile` (already at repo root).
4. Save. First **Build Now** registers the `parameters {}` block (a Jenkins
   quirk — parameters only appear after one run); after that, use
   **Build with Parameters** to pick `BROWSER` (chrome/firefox/edge) and
   `BASE_URL`.

**Reading results correctly — UNSTABLE vs FAILURE:**
- The Jenkinsfile passes `-Dmaven.test.failure.ignore=true` to `mvn test`, so
  Maven doesn't abort with a non-zero exit just because tests failed.
- `post.always` runs a `junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true`
  step before the `allure` step — this parses actual test results and marks
  the build **UNSTABLE** (yellow) when tests fail, reserving **FAILURE** (red)
  for real breaks (compile errors, missing tools, bad credentials).

## 6. Logging
- `log4j2-test.xml` (in `src/test/resources`) configures a console appender
  and a file appender writing to `target/tests.log`, both at INFO level.
- `TestListener` and effectively every page-object class use `@Log4j2`
  (Lombok) + `log.info(...)` per action/step — so both the console and
  `target/tests.log` show a readable, step-by-step trace of every run.
- Verify: run any test, then check `target/tests.log` for the same log lines
  seen in the console.
