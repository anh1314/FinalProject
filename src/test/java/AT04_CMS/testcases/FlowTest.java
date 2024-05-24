package AT04_CMS.testcases;

import common.BaseTest;
import AT04_CMS.pages.*;
import helpers.ExcelHelper;
import helpers.PropertiesHelper;
import keywords.WebUI;
import org.testng.annotations.Test;

public class FlowTest extends BaseTest {
      LoginPage loginPage = new LoginPage();
      DashboardPage dashboardPage = new DashboardPage();
      BrandPage brandPage = new BrandPage();
      CategoryPage categoryPage = new CategoryPage();
      ProductPage productPage = new ProductPage();
      ExcelHelper excelHelper = new ExcelHelper();

      @Test(description = "Flow: Login > Add brand > Add category > Add product ")
      public void testFlow() {
            System.out.println("\n ------\uD83E\uDDFE Login ⏩ ------ ");
            dashboardPage = loginPage.loginCMS();

            System.out.println("\n ------\uD83E\uDDFE Add Brand ⏩ ------ ");
            brandPage = dashboardPage.navigateToBrandPage();
            excelHelper.setExcelFile(PropertiesHelper.getValue("EXCEL_PATH"), "BrandData");
            brandPage.addBrand(excelHelper.getCellData(2, 1));
            WebUI.waitForPageLoaded();

            System.out.println("\n ------\uD83E\uDDFE Add Category ⏩ ------ ");
            categoryPage = dashboardPage.navigateToCategoryPage();
            excelHelper.setExcelFile(PropertiesHelper.getValue("EXCEL_PATH"), "CategoryData");
            categoryPage.addNewCategory(excelHelper.getCellData(2, 1));
            WebUI.waitForPageLoaded();

            System.out.println("\n ------\uD83E\uDDFE Add Product ⏩ ------ ");
            productPage = dashboardPage.navigateToProductPage();
            productPage.addNewProduct();
            WebUI.sleep(3);
      }
}
