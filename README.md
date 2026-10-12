# Swag Labs UI Automation Framework

End-to-End UI test automation for the [Swag Labs demo store](https://www.saucedemo.com) (Login, Product list, Product details, Cart and the two-step Checkout). It is built with Java, Selenium WebDriver and TestNG, using the **Page Object Model (POM)** pattern.

---

## Tech Stack

| Tool | Version | Purpose |
| :--- | :--- | :--- |
| Java | 17 | Language / runtime (`maven.compiler` is set to 17) |
| Selenium Java | 4.39.0 | Browser automation |
| TestNG | 7.10.2 | Test runner and suite configuration |
| ExtentReports | 5.1.1 | HTML execution report with failure screenshots |
| Maven | 3.x | Build and dependency management |

---

## Prerequisites

* JDK 17 or newer
* Maven 3.x
* Google Chrome (default), or Firefox / Edge. Selenium Manager downloads the matching driver automatically.

---

## Running the tests

All commands run from the `Swag Laps Project/` folder (the one containing `pom.xml`).

```bash
# Default: Chrome with a visible window
mvn test

# Headless Chrome (useful for CI)
mvn test -Dheadless=true

# Another browser
mvn test -Dbrowser=firefox
```

| Property | Values | Default |
| :--- | :--- | :--- |
| `browser` | `chrome`, `firefox`, `edge` | `chrome` |
| `headless` | `true`, `false` | `false` |
| `baseUrl` | any URL | `https://www.saucedemo.com` |

`mvn test` runs the suite defined in `testng.xml` (6 test classes, one `Regression Tests` block) and registers the report listener.

---

## Reports

After a run, an HTML report is written to `Swag Laps Project/Reports/ExecutionReport_<timestamp>.html`. Failures (including failures in setup methods) are logged with the error message and a screenshot saved under `Swag Laps Project/Screenshots/`. Both folders are git-ignored.

---

## Project Structure

```text
Swag Laps Project/
├── pom.xml
├── testng.xml                      # Suite definition + listener registration
└── src/
    ├── main/java/Pages/            # Page Objects (locators + actions)
    │   ├── BasePage.java           # Shared explicit-wait helpers
    │   ├── LoginPage.java
    │   ├── ProductListPage.java
    │   ├── ProductDetailsPage.java
    │   ├── CartPage.java
    │   ├── CheckoutStepOnePage.java
    │   ├── CheckoutStepTwoPage.java
    │   └── CheckoutCompletePage.java
    └── test/java/
        ├── BaseClass/BaseClass.java        # Driver setup / teardown and run configuration
        ├── TestCases/                      # Test classes (assertions only)
        └── Utilities/Listener.java         # ExtentReports + screenshot listener
```

---

## Design notes

* Page Objects hold locators and actions; test classes hold the assertions.
* Page Objects extend `BasePage`, so every click, read and page transition uses an explicit wait instead of `Thread.sleep`.
* Each test starts with a fresh browser session (`@BeforeMethod`) and is independent of the others.

## Known limitations

* Only the `standard_user` and `locked_out_user` accounts are covered; there is no `problem_user` or `performance_glitch_user` scenario yet.
* There is no CI workflow in the repository yet.
* Tests run sequentially; the shared static driver in `BaseClass` would need to become thread-local before enabling parallel execution.
