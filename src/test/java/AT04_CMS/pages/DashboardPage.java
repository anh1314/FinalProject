package AT04_CMS.pages;

import drivers.DriverManager;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class DashboardPage {

      private By menuDashboard = By.xpath("//div[@class='d-flex']");
      private By menuProducts = By.xpath("//span[normalize-space()='Products']");
      private By menuAllProducts = By.xpath("//span[normalize-space()='All products']");
      private By menuCategory = By.xpath("//span[normalize-space()='Category']");
      private By menuBrand = By.xpath("//span[normalize-space()='Brand']");
      private By menuAccount = By.xpath("//span[@class='d-flex align-items-center']");
      private By optionLogout = By.xpath("//span[normalize-space()='Logout']");

      private void verifyDashboardPage() {
            Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("admin"), "NOT to the Dashboard page");
            Assert.assertTrue(WebUI.checkElementDisplayed(menuDashboard), "Menu Dashboard is NOT displayed");
            Assert.assertTrue(WebUI.checkElementDisplayed(menuAccount), "Menu Account is NOT displayed");
      }

      public void clickMenuProducts() {
            verifyDashboardPage();
            WebUI.clickElement(menuProducts);
            WebUI.sleep(2);
      }

      public ProductPage navigateToProductPage() {
            WebUI.clickElement(menuAllProducts);
            return new ProductPage();
      }

      public CategoryPage navigateToCategoryPage() {
            WebUI.clickElement(menuCategory);
            return new CategoryPage();
      }

      public LoginPage Logout() {
            verifyDashboardPage();
            WebUI.clickElement(menuAccount);
            WebUI.clickElement(optionLogout);
            return new LoginPage();
      }

      public BrandPage navigateToBrandPage() {
            clickMenuProducts();
            WebUI.clickElement(menuBrand);
            return new BrandPage();
      }

}
