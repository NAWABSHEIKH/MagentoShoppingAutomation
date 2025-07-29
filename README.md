# 💼 Magento Selenium Test Automation Suite

This is a complete Selenium + TestNG automation framework built to test end-to-end scenarios on the Magento e-commerce demo site. The framework follows **Page Object Model (POM)** and includes reusable utility classes, reporting, and logging.

---

## ✅ Test Cases Covered

| Test Case Class                  | Description                                      |
| -------------------------------- | ------------------------------------------------ |
| `SignupTest`                     | Automates the user registration process          |
| `LoginTest`                      | Validates login functionality with valid data    |
| `AddToCartTest`                  | Adds a product to the cart directly from search  |
| `CheckoutTest`                   | Fills shipping form and completes guest checkout |
| `CartSummaryTest`                | Validates product name, price, and total in cart |
| `SearchSuggestionValidationTest` | Checks search suggestion dropdown functionality  |
| `OrderConfirmationTest`          | Verifies successful product purchase end-to-end  |

---

## 📊 Tools & Technologies

* Java 19
* Selenium WebDriver 4.21.0
* TestNG 7.10
* Maven
* ChromeDriver
* Page Object Model (POM)
* Git, GitHub

---

## 📁 Project Structure

```
MagentoShoppingAutomation/
├── src/
│   ├── main/java/
│   │   ├── pages/                # Page classes (HomePage, ProductDetailPage, etc.)
│   │   ├── utils/                # WaitUtil, AdHandlerUtil, reusable helpers
│   └── test/java/
│       └── tests/                # Test classes for each test scenario
├── pom.xml                       # Maven dependencies
├── testng.xml                    # TestNG test suite
├── .gitignore                    # Files/folders excluded from Git tracking
├── README.md                     # Project documentation
```

---

## 🚀 How to Run Tests

### Pre-requisites:

* Java JDK 17 or higher
* Maven
* Chrome browser installed

### Steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/NAWABSHEIKH/MagentoShoppingAutomation.git
   cd MagentoShoppingAutomation
   ```

2. Run tests via TestNG or Maven:

   * **From IDE (TestNG)**: Right-click on `testng.xml` > Run
   * **From CLI (Maven)**:

     ```bash
     mvn clean test
     ```

---

## 🔧 Utilities Implemented

* `WaitUtil.java` – Central wait logic (explicit waits)
* `AdHandlerUtil.java` – Removes pop-up overlays and ads
* `RetryUtil` – Retry mechanism for flaky steps (e.g., element unclickable)

---

## 📈 Sample Console Output

```
+++++++++++++++ Cart Summary Test +++++++++++++++
✅ Product searched: Juno Jacket
✅ Product clicked from search result
✅ Size selected: M
✅ Color selected: Blue
✅ Added to cart
✅ Navigated to Shopping Cart page
✅ Product name validated in cart: Juno Jacket
✅ Quantity: 1
✅ Price: $77.00
✅ Total: $77.00
+++++++++++++++ Test Passed +++++++++++++++
```

---

## 👤 Author

**MD NAWAB**
GitHub: [github.com/NAWABSHEIKH](https://github.com/NAWABSHEIKH)

---

## 🔖 License

Open for learning and collaboration purposes.

---
