package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginWithValidUser() {
        String email = "user_ce9bb5d5@mailinator.com";   // Replace with actual test user
        String password = "Test@1234";

        HomePage homePage = new HomePage(driver);
        homePage.clickSignIn();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        AccountPage accountPage = new AccountPage(driver);
        Assert.assertTrue(accountPage.isUserLoggedInTopWelcomeMessagePresent(), 
        	    "❌ Login failed: Welcome username not found in header.");

    }
}
