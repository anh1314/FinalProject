package AT04_CMS.testcases;

import common.BaseTest;
import AT04_CMS.pages.*;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {
      LoginPage loginPage;
      DashboardPage dashboardPage;
      CategoryPage categoryPage;
      ProductPage productPage;
      BrandPage brandPage;

      @Test
      public void testNavigateBrandPage() {
            loginPage = new LoginPage();
            loginPage.loginCMS();

            dashboardPage = new DashboardPage();
            brandPage = dashboardPage.navigateToBrandPage();
      }

      @Test
      public void testNavigateCategoryPage() {
            loginPage = new LoginPage();
            loginPage.loginCMS();

            dashboardPage = new DashboardPage();
            dashboardPage.clickMenuProducts();
            categoryPage = dashboardPage.navigateToCategoryPage();
      }

      @Test
      public void testNavigateProductPage() {
            loginPage = new LoginPage();
            loginPage.loginCMS();

            dashboardPage = new DashboardPage();
            dashboardPage.clickMenuProducts();
            productPage = dashboardPage.navigateToProductPage();
      }

      @Test
      public void testLogout() {
            loginPage = new LoginPage();
            loginPage.loginCMS();

            dashboardPage = new DashboardPage();
            dashboardPage.Logout();
      }

}
