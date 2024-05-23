package AT04_CMS.pages;

import constants.ConfigData;
import drivers.DriverManager;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class LoginPage {

      private By headerPage = By.xpath("//p[normalize-space()='Login to your account.']");
      private By inputEmail = By.xpath("//input[@id='email']");
      private By inputPassword = By.xpath("//input[@id='password']");
      private By buttonLogin = By.xpath("//button[normalize-space()='Login']");
      private By alertMessage = By.xpath("//div[@role='alert']");

      private void getBrownser() {
            WebUI.openURL(ConfigData.URL);
            WebUI.waitForPageLoaded();
            WebUI.assertEquals(WebUI.getTextElement(headerPage), "Login to your account.", "NOT the Login page");
      }

      public DashboardPage loginCMS(String email, String password) {
            getBrownser();
            WebUI.sendKeys(inputEmail, email);
            WebUI.sendKeys(inputPassword, password);
            WebUI.clickElement(buttonLogin);
            WebUI.waitForPageLoaded();
            return new DashboardPage();
      }

      public DashboardPage loginCMS() {
            getBrownser();
            WebUI.sendKeys(inputEmail, ConfigData.Email);
            WebUI.sendKeys(inputPassword, ConfigData.Password);
            WebUI.clickElement(buttonLogin);
            WebUI.waitForPageLoaded();
            verifyLoginSucess();
            return new DashboardPage();
      }

      public void verifyNullEmail() {
            Assert.assertTrue(WebUI.verifyHTML5RequiredField(inputEmail), "Email is NOT a required field");
            WebUI.assertEquals(WebUI.getHTML5MessageField(inputEmail), "Please fill out this field.", "Validation message of Email not match");
            WebUI.sleep(2);
      }

      public void verifyNullPassword() {
            Assert.assertTrue(WebUI.verifyHTML5RequiredField(inputPassword), "Password  is NOT a required field");
            WebUI.assertEquals(WebUI.getHTML5MessageField(inputPassword), "Please fill out this field.", "Validation message of Password not match");
            WebUI.sleep(2);
      }

      public void incorrectFormatEmail() {
            Assert.assertTrue(WebUI.verifyHTML5RequiredField(inputEmail), "Validation message of incorrect format Email NOT exists");
            WebUI.assertEquals(WebUI.getHTML5MessageField(inputEmail), "Please include an '@' in the email address. 'abc' is missing an '@'.", "Validation message of incorrect format Email not match");
            WebUI.sleep(2);
      }

      private void verifyLoginSucess() {
            Assert.assertFalse(DriverManager.getDriver().getCurrentUrl().contains("login"), "Login fail");
      }

      public void verifyLoginFail() {
            WebUI.checkElementDisplayed(alertMessage);
            Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("login"), "Fail, NOT on the Login page");
            WebUI.assertEquals(WebUI.getTextElement(alertMessage), "Invalid login credentials", "Content of alert message not match");
            WebUI.sleep(2);
      }
}
