# Swag Labs UI Automation Framework 

A production-ready, enterprise-grade **End-to-End (E2E) Automation Testing Framework** built to validate the core business workflows of the Swag Labs e-commerce platform. This project leverages the **Page Object Model (POM)** design pattern to ensure high maintainability, scalability, and clean separation of concerns.

---

##  Business & Technical Problem
Manual execution of critical regression suites on e-commerce platforms introduces bottlenecks in the CI/CD pipeline, increases human error, and delays time-to-market. 

### The Challenges:
* **Flaky Workflows:** E-commerce funnels (Login, Cart, Multi-step Checkout) frequently break silently due to UI changes.
* **Test Maintenance Overhead:** Hardcoded locators and poorly structured test scripts lead to high maintenance costs when application code scales.
* **Environmental Instability:** Using bleeding-edge or non-LTS Java runtimes causes unpredictable compiler behavior and dependency conflicts across test runner environments.

---

## Proposed Solution & Core Features
This framework solves the reliability gap by providing a fully automated, decoupled regression testing suite that simulates deterministic user interactions.

### Key Architecture & Implementation Details:
* **Page Object Model (POM):** Clean decoupling of UI elements and action methods from the actual test assertions, ensuring that any UI change requires updating only a single locator file.
* **Stable Runtime Environment:** Compiled using standard **Java 17 (LTS)** parameters to guarantee seamless, reproducible execution across local environments and headless test configurations.
* **Robust Test Distribution:** Executions are globally managed via a unified `testng.xml` suite wrapper, supporting logical groupings (Regression, Smoke, Sanity).
* **Rich Visual Reporting:** Integrated custom listeners backed by **ExtentReports** to deliver automated, step-by-step HTML documentation with execution statuses.

---

## Tech Stack & Dependencies

| Tool / Dependency | Version | Industry Purpose |
| :--- | :--- | :--- |
| **Java 17 (LTS)** | `17.0.19` | Provides a bulletproof, long-term support runtime environment. |
| **Selenium Java** | `4.39.0` | Powers W3C-compliant browser automation and explicit wait strategies. |
| **TestNG** | `7.10.2` | Drives advanced test configuration, lifecycle hooks, and suite setups. |
| **ExtentReports** | `5.1.1` | Generates interactive, high-level execution dashboards for stakeholders. |
| **Maven** | Native | Automates project lifecycle management and dependency scopes. |

---

## Project Structure

```text
src/
├── main/java/Pages/         # Page Objects containing strictly encapsulated locators & actions
│   ├── LoginPage.java
│   ├── ProductListPage.java
│   ├── ProductDetailsPage.java
│   └── ... (CartPage, Checkout Steps)
└── test/java/
    ├── BaseClass/          # Driver instantiation management and dynamic browser configurations
    ├── TestCases/          # Pure assertions verifying operational requirements
    │   ├── LoginPageTestCases.java
    │   ├── ProductListPage_TestCases.java
    │   └── ProductDetailsPage_TestCases.java
    └── Utilities/          # Enterprise test listeners and custom configuration utilities
