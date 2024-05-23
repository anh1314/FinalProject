package AT04_CMS.pages;

import drivers.DriverManager;
import helpers.ExcelHelper;
import helpers.PropertiesHelper;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

import static keywords.WebUI.*;

public class CategoryPage {

      private By buttonAddNewCategory = By.xpath("//a[@class='btn btn-primary']");
      private By inputName = By.xpath("//input[@id='name']");
      private By listParentCategory = By.xpath("//button[@title='No Parent']");
      private By searchParentCategory = By.xpath("//div[@class='dropdown-menu show']//input[@aria-label='Search']");
      private By inputOrderingNumber = By.xpath("//input[@id='order_level']");
      private By droplistType = By.xpath("//select[@name='digital']");
      private By typeDigital = By.xpath("//span[normalize-space()='Digital']");
      private By browseBaner = By.xpath("(//div[@class='input-group-prepend'])[1]/div");
      private By searchImage = By.xpath("//input[@placeholder='Search your files']");
      private By image = By.xpath("//div[@class='card-file-thumb']//img[@class='img-fit']");
      private By buttonAddFiles = By.xpath("//button[normalize-space()='Add Files']");
      private By browseIcon = By.xpath("(//div[@class='input-group-prepend'])[2]");
      private By inputMetaTitle = By.xpath("//input[@placeholder='Meta Title']");
      private By inputMetaDescription = By.xpath("//textarea[@name='meta_description']");
      private By droplistFilteringAttributes = By.xpath("//div[contains(text(),'Nothing selected')]");
      private By searchFilteringAttributes = By.xpath("//div[@class='dropdown-menu show']//input[@aria-label='Search']");
      private By buttonSave = By.xpath("//button[normalize-space()='Save']");
      private By alertMessage = By.xpath("//div[@role='alert']");

      private By searchCategoryName = By.xpath("//input[@id='search']");
      private By listCategoryName = By.xpath("//tbody/tr/td[@class='footable-first-visible']");
      private By editCategory = By.xpath("(//a[@title='Edit'])[1]");
      private By alertUpdateSuccess = By.xpath("//span[@data-notify='message']");
      private By deleteName = By.xpath("(//a[@title='Delete'])[1]");
      private By dialogDeleteCofirmation = By.xpath("// h4[normalize-space()='Delete Confirmation']");
      private By buttonDelete = By.xpath("//a[@id='delete-link']");
      private By messageDeleteSucess = By.xpath("//span[@data-notify='message']");

      ExcelHelper excelHelper = new ExcelHelper();

      private void setFileExcel() {
            excelHelper.setExcelFile(PropertiesHelper.getValue("EXCEL_PATH"), "CategoryData");
      }

      private void verifyCategoryPage() {
            Assert.assertTrue(WebUI.checkElementDisplayed(buttonAddNewCategory), "Button Add new category is NOT displayed");
            Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("categories"), "NOT to the Category page");
      }

      public void addNewCategory(String categoryName) {
            setFileExcel();
            verifyCategoryPage();
            clickElement(buttonAddNewCategory);
            waitForPageLoaded();
            sendKeys(inputName, categoryName);
            selectDynamicDropdown(listParentCategory, searchParentCategory, excelHelper.getCellData(2, 2)); //Parent Category
            sendKeys(inputOrderingNumber, excelHelper.getCellData(2, 3)); //Ordering Number
            selectStaticDropdown(droplistType, excelHelper.getCellData(2, 4));
            getImage(browseBaner, searchImage, excelHelper.getCellData(2, 5), image, buttonAddFiles);
            getImage(browseIcon, searchImage, excelHelper.getCellData(2, 5), image, buttonAddFiles);
            sendKeys(inputMetaTitle, excelHelper.getCellData(2, 6));    //Meta Title
            sendKeys(inputMetaDescription, excelHelper.getCellData(2, 7));
            clickElement(droplistFilteringAttributes);
            setTextAndKeys(searchFilteringAttributes, excelHelper.getCellData(2, 8), Keys.ENTER);
            clickElement(buttonSave);
      }

      public void verifyNameRequiredField() {
            setFileExcel();
            WebUI.verifyHTML5RequiredField(inputName);
            WebUI.assertEquals(WebUI.getHTML5MessageField(inputName), excelHelper.getCellData(6, 1), "Validation HTML5 message Name not match");
      }

      public void verifyAddCategorySuccess() {
            setFileExcel();
            Assert.assertTrue(WebUI.getWebElement(alertMessage).isDisplayed(), "Alert message add category successful is NOT displayed");
            WebUI.assertEquals(WebUI.getTextElement(alertMessage), excelHelper.getCellData(6, 2), "Content of alert message add category successful not match");
      }

      private void searchCategoryName() {
            setFileExcel();
            WebUI.setTextAndKeys(searchCategoryName, excelHelper.getCellData(2, 1), Keys.ENTER);
            WebUI.waitForPageLoaded();
//        WebUI.sleep(2);
      }

      public void searchCategoryName(String searchText) {
            WebUI.setTextAndKeysENTER(searchCategoryName, searchText);
            WebUI.waitForPageLoaded();
            WebUI.sleep(2);
      }

      public void verifyCategoryDetail() {
            setFileExcel();
            WebUI.sleep(2);
            WebUI.assertEquals(WebUI.getAttributeElement(inputName, "value"), excelHelper.getCellData(2, 1), "FAIL, incorrect Name value");
            WebUI.assertEquals(WebUI.getAttributeElement(inputOrderingNumber, "value"), excelHelper.getCellData(2, 9), "FAIL, incorrect Ordering Number value");
            WebUI.sleep(2);
            WebUI.assertEquals(WebUI.getAttributeElement(inputMetaTitle, "value"), excelHelper.getCellData(2, 6), "FAIL, incorrect MetaTitle value");
            WebUI.assertEquals(WebUI.getAttributeElement(inputMetaDescription, "value"), excelHelper.getCellData(2, 7), "FAIL, incorrect Meta Description value");
      }

      public void getFirstCategoryName() {
            searchCategoryName();
            WebUI.sleep(2);
            List<WebElement> listName = WebUI.getWebElements(listCategoryName);
            for (WebElement element : listName.subList(0, 1)) {
                  System.out.println("Name: " + element.getText());
            }
      }

      public void verifyEditCategory() {
            waitForPageLoaded();
            setFileExcel();
            searchCategoryName();
            WebUI.clickElement(editCategory);
            WebUI.clearAndSendKeys(inputOrderingNumber, excelHelper.getCellData(2, 9));
            WebUI.sleep(4);
            WebUI.clickElement(buttonSave);
            WebUI.sleep(2);
            Assert.assertTrue(WebUI.checkElementDisplayed(alertUpdateSuccess), "Alert update successful is NOT displayed");
            WebUI.assertEquals(WebUI.getTextElement(alertUpdateSuccess), excelHelper.getCellData(6, 3), "Content of alert Update success NOT match ");
      }

      public void verifyDeleteCategory() {
            waitForPageLoaded();
            setFileExcel();
            searchCategoryName();
            WebUI.clickElement(deleteName);
            Assert.assertTrue(WebUI.checkElementDisplayed(dialogDeleteCofirmation), "Delete confirmation dialog is not displayed");
            Assert.assertTrue(WebUI.checkElementEnable(buttonDelete), "Button Delete is NOT enabled, Can NOT delete item");
            WebUI.clickElement(buttonDelete);
            waitForPageLoaded();
            Assert.assertTrue(WebUI.checkElementDisplayed(messageDeleteSucess), "Alert message Delete sucess is NOT displayed");
            sleep(2);
            WebUI.assertEquals(WebUI.getWebElement(messageDeleteSucess).getText(), excelHelper.getCellData(6, 4), "Content of message Delete sucess NOT match ");
      }

      private By listPage = By.xpath("//a[@class='page-link']");

      public void Pagination(String value) {
            // Lấy tổng số page
            int getListPage = WebUI.getWebElements(listPage).size();
            WebUI.sleep(2);

            if (getListPage == 0) {
                  WebUI.checkContainsValueOnTableByColumn(2, value);
                  WebUI.logConsole("Number of results page lists displayed: " + (getListPage + 1));
            } else {
                  WebUI.logConsole("Number of results page lists displayed: " + getListPage);
                  String pageTotal = DriverManager.getDriver().findElement(By.xpath("(//a[@class='page-link'])[" + (getListPage - 1) + "]")).getText();
                  WebUI.logConsole("Total pages: " + pageTotal);
                  int tongPage = Integer.parseInt(pageTotal);

                  for (int i = 1; i <= tongPage; i++) {
                        WebUI.logConsole("\n Page" + i + ":");
                        WebUI.checkContainsValueOnTableByColumn(2, value);

                        //Nhấn nút Next để đến trang tiếp theo
                        if (i < tongPage) {
                              WebUI.clickElement(By.xpath("//a[contains(text(),'›')]"));
                              WebUI.sleep(2);
                        }
                  }
            }
      }

}
