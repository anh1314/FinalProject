package AT04_CMS.testcases;

import AT04_CMS.pages.CategoryPage;
import AT04_CMS.pages.DashboardPage;
import AT04_CMS.pages.LoginPage;
import common.BaseTest;

import helpers.ExcelHelper;
import helpers.PropertiesHelper;
import keywords.WebUI;
import org.testng.annotations.Test;

public class CategoryTest extends BaseTest {
      LoginPage loginPage;
      DashboardPage dashboardPage;
      CategoryPage categoryPage = new CategoryPage();
      ;
      ExcelHelper excelHelper = new ExcelHelper();

      private void navigate() {
            loginPage = new LoginPage();
            loginPage.loginCMS();
            dashboardPage = new DashboardPage();
            dashboardPage.clickMenuProducts();
            dashboardPage.navigateToCategoryPage();
      }

      @Test(priority = 1)
      public void testAddNewCategory() {
            navigate();
            excelHelper.setExcelFile(PropertiesHelper.getValue("EXCEL_PATH"), "CategoryData");
            categoryPage.addNewCategory(excelHelper.getCellData(2, 1));
            categoryPage.verifyAddCategorySuccess();

            System.out.println("\n ------\uD83E\uDDFE Get first category name ⏩ ------ ");
            categoryPage.getFirstCategoryName();
      }

      @Test(priority = 3, description = "Add category failed, Required field: Name is NULL")
      public void testAddNewCategoryNull() {
            navigate();
            categoryPage.addNewCategory("");
            categoryPage.verifyNameRequiredField();
      }

      @Test(priority = 2, description = "update Ordering Number")
      public void testEditCategory() {
            navigate();
            System.out.println("\n ------\uD83E\uDDFE Edit product ⏩ ------ ");
            categoryPage.verifyEditCategory();

            System.out.println("\n ------\uD83E\uDDFE Check category details ⏩ ------ ");
            WebUI.waitForPageLoaded();
            categoryPage.verifyCategoryDetail();
      }

      @Test(priority = 4)
      public void testDeleteCategory() {
            navigate();
            categoryPage.verifyDeleteCategory();
      }

      @Test(priority = 5)
      public void testDataTable() {
            navigate();
            excelHelper.setExcelFile(PropertiesHelper.getValue("EXCEL_PATH"), "CategoryData");
            categoryPage.searchCategoryName(excelHelper.getCellData(2, 10));
            categoryPage.Pagination(excelHelper.getCellData(2, 10));
      }
}
