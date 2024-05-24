package dataProvider;

import helpers.ExcelHelper;
import org.testng.annotations.DataProvider;

public class DataProviderFactory {

      @DataProvider(name = "DataLogin", parallel = true)
      public Object[][] dataLogin(){

            ExcelHelper excelHelper = new ExcelHelper();
            Object[][] data = excelHelper.getExcelData("src/test/resources/testdata/UsersData.xlsx", "LoginData");
            return data;
      }

      @DataProvider(name = "data_provider_login_excel_hashtable")
      public Object[][] dataLoginHRMFromExcelHashtable() {
            ExcelHelper excelHelper = new ExcelHelper();
            Object[][] data = excelHelper.getDataHashTable(  "src/test/resources/testdata/UsersData.xlsx", "LoginData", 1, 2);
            System.out.println("Login Data from Excel: " + data);
            return data;
      }
}