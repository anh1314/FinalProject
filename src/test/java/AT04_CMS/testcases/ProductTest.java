package AT04_CMS.testcases;

import AT04_CMS.pages.DashboardPage;
import AT04_CMS.pages.LoginPage;
import AT04_CMS.pages.ProductPage;
import common.BaseTest;

import helpers.ExcelHelper;
import helpers.PropertiesHelper;
import keywords.WebUI;
import org.testng.annotations.Test;

public class ProductTest extends BaseTest {
      LoginPage loginPage;
      DashboardPage dashboardPage;
      ProductPage productPage;

      private void navigate() {
            loginPage = new LoginPage();
            loginPage.loginCMS();
            dashboardPage = new DashboardPage();
            dashboardPage.clickMenuProducts();
            dashboardPage.navigateToProductPage();
      }

      @Test(description = "Add product and Check product details")
      public void testAddNewProductSuccess() {
            navigate();
            productPage = new ProductPage();
            System.out.println("\n ------\uD83E\uDDFE Add new product ⏩ ------ ");
            productPage.addNewProduct();
            WebUI.waitForPageLoaded();

            System.out.println("\n ------\uD83E\uDDFE Check product details ⏩ ------ ");
            productPage.viewProductDetails();
      }

      @Test(description = "Add product failed, Required field: Product Name is NULL ")
      public void testAddProduct_withProductNameNull() {
            navigate();
            productPage = new ProductPage();
            productPage.addProductNullProductName();
      }

      @Test(description = "Add product failed, Required field: Unit is are NULL ")
      public void testAddProduct_withUnitNull() {
            navigate();
            productPage = new ProductPage();
            productPage.addProductNullProductName();
      }

      @Test(description = "Add product failed, Required field: Unit price is NULL ")
      public void testAddProduct_withUnitPriceNull() {
            navigate();
            productPage = new ProductPage();
            productPage.addProductNullProductName();
      }

      @Test(description = "Add product failed, Required field: Discount is NULL ")
      public void testAddProduct_withDiscountNull() {
            navigate();
            productPage = new ProductPage();
            productPage.addProductNullProductName();
      }

      @Test()
      public void testEditProduct() {
            navigate();
            productPage = new ProductPage();
            productPage.editProduct();
      }

      @Test
      public void searchDataTable() {
            navigate();
            productPage = new ProductPage();
            productPage.verifyProductPage();
            ExcelHelper excelHelper = new ExcelHelper();
            excelHelper.setExcelFile(PropertiesHelper.getValue("EXCEL_PATH"), "ProductData");

            System.out.println("\n ------\uD83E\uDDFE Check data table ⏩ ------ ");
            productPage.searchProduct(excelHelper.getCellData(2, 10));
            productPage.Pagination(excelHelper.getCellData(2, 10));
      }

}
