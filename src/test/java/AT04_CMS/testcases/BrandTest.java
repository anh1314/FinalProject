package AT04_CMS.testcases;

import AT04_CMS.pages.BrandPage;
import AT04_CMS.pages.DashboardPage;
import AT04_CMS.pages.LoginPage;
import common.BaseTest;

import helpers.ExcelHelper;
import helpers.PropertiesHelper;
import org.testng.annotations.Test;

public class BrandTest extends BaseTest {
      LoginPage loginPage = new LoginPage();
      DashboardPage dashboardPage = new DashboardPage();
      BrandPage brandPage = new BrandPage();
      ExcelHelper excelHelper = new ExcelHelper();

      @Test
      public void testAddNewBrand() {
            loginPage.loginCMS();
            dashboardPage.navigateToBrandPage();

            excelHelper.setExcelFile(PropertiesHelper.getValue("EXCEL_PATH"), "BrandData");
            brandPage.addBrand(excelHelper.getCellData(2, 1));
            brandPage.verifyAddBrandSuccess();
      }

      @Test
      public void testAddBrandNullName() {
            loginPage.loginCMS();
            dashboardPage.navigateToBrandPage();

            brandPage.addBrand("");
            brandPage.verifyNameRequiredField();
      }

      @Test
      public void testDeleteBrand(){
            loginPage.loginCMS();
            dashboardPage.navigateToBrandPage();

            brandPage.deleteBrand();
      }
}
