package AT04_CMS.testcases;

import AT04_CMS.pages.DashboardPage;
import AT04_CMS.pages.LoginPage;
import common.BaseTest;
import constants.ConfigData;

import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
      LoginPage loginPage = new LoginPage();
      DashboardPage dashboardPage = new DashboardPage();

      @Test
      public void testLoginSucess() {
            dashboardPage = loginPage.loginCMS();
      }

      @Test
      public void testLoginWithEmailInvalid() {
            loginPage.loginCMS("admin@example111.com", ConfigData.Password);
            loginPage.verifyLoginFail();
      }

      @Test
      public void testLoginWithPasswordInvalid() {
            loginPage.loginCMS(ConfigData.Email, "568690");
            loginPage.verifyLoginFail();
      }

      @Test
      public void testEmailNull() {
            loginPage.loginCMS("", ConfigData.Password);
            loginPage.verifyNullEmail();
      }

      @Test
      public void testIncorrectFormatEmail() {
            loginPage.loginCMS("abc", ConfigData.Password);
            loginPage.incorrectFormatEmail();
      }

      @Test
      public void testPasswordNull() {
            loginPage.loginCMS(ConfigData.Email, "");
            loginPage.verifyNullPassword();
      }

}
