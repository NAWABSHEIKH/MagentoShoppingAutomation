package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.RegisterPage;
import utils.AdHandlerUtil;
import utils.RandomDataUtil;
import utils.WaitUtil;

public class SignupTest extends BaseTest {

    @Test
    public void registerNewUser() {
        String firstName = "Nawab";
        String lastName = "Sheikh";
        String email = RandomDataUtil.generateRandomEmail();
        String password = "Test@1234";

        HomePage homePage = new HomePage(driver);
        homePage.clickCreateAccount();

        // 🕒 Wait for registration form to begin rendering
        WaitUtil.waitForElementSafe(driver, By.id("firstname"), 10);

        // ✅ Centralized ad cleanup
        AdHandlerUtil.removeAds(driver);

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registerNewUser(firstName, lastName, email, password);

        AccountPage accountPage = new AccountPage(driver);
        Assert.assertTrue(accountPage.isRegistrationSuccessMessageDisplayed(),
                "❌ Registration failed or success message not visible");
    }
}
