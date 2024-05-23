package AT04_CMS.pages;

import drivers.DriverManager;
import helpers.ExcelHelper;
import helpers.PropertiesHelper;
import helpers.SystemHelper;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

import static keywords.WebUI.*;

public class BrandPage {

      private By headerBrand = By.xpath("//h1[normalize-space()='All Brands']");
      private By inputName = By.xpath("//input[@placeholder='Name']");
      private By uploadLogo = By.xpath("//div[@class='input-group-prepend']");
      private By searchImage = By.xpath("//input[@placeholder='Search your files']");
      private By linkImage = By.xpath("//img[@class='img-fit']");
      private By buttonAddFiles = By.xpath("//button[normalize-space()='Add Files']");
      private By inputMetaTitle = By.xpath("//input[@placeholder='Meta Title']");
      private By inputMetaDescription = By.xpath("//textarea[@name='meta_description']");
      private By buttonSave = By.xpath("//button[normalize-space()='Save']");
      private By notifyMessage = By.xpath("//span[@data-notify='message']");
      private By uploadNew = By.xpath("//a[normalize-space()='Upload New']");
      private By browse = By.xpath("//button[normalize-space()='Browse']");
      private By buttonClose = By.xpath("//button[@aria-label='Close']");

      private String filePath = SystemHelper.getCurrentDir() + PropertiesHelper.getValue("IMAGE_PATH");
      private By inputSearchBrand = By.xpath("//input[@id='search']");
      private By deleteBrand = By.xpath("(//a[@title='Delete'])[1]");
      private By buttonDelete = By.xpath("//a[@id='delete-link']");
      private By headerDeleteForm = By.xpath("//h4[normalize-space()='Delete Confirmation']");
      private By confirmMessage = By.xpath("//p[@class='mt-1']");
      private By alertMessage = By.xpath("//div[@role='alert']");

      ExcelHelper excelHelper = new ExcelHelper();

      private void setFileExcel() {
            excelHelper.setExcelFile(PropertiesHelper.getValue("EXCEL_PATH"), "BrandData");
      }

      private void uploadFile() {
            clickElement(uploadLogo);
            waitForPageLoaded();
            clickElement(uploadNew);
            uploadFileWithLocalForm(browse, filePath);
            clickElement(buttonClose);
      }

      private void verifyBrandPage() {
            Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains(excelHelper.getCellData(6, 1)), "NOT to the Brand page");
            assertEquals(getTextElement(headerBrand), excelHelper.getCellData(6, 2), "header NOT match, not Brand page");
      }

      public DashboardPage addBrand(String brandName) {
            setFileExcel();
            verifyBrandPage();
            sendKeys(inputName, brandName);
//            uploadFile();
            getImage(uploadLogo, searchImage, excelHelper.getCellData(2, 2), linkImage, buttonAddFiles);
            sendKeys(inputMetaTitle, excelHelper.getCellData(2, 3));
            sendKeys(inputMetaDescription, excelHelper.getCellData(2, 4));
            clickElement(buttonSave);
            return new DashboardPage();
      }

      public void verifyAddBrandSuccess() {
            waitForPageLoaded();
            setFileExcel();
            Assert.assertTrue(getWebElement(notifyMessage).isDisplayed(), "Notify Add new brand success is NOT displayed");
            WebUI.sleep(2);
            assertEquals(getTextElement(notifyMessage), excelHelper.getCellData(6, 3), "Content of notify add new brand sucessful NOT match");
      }

      public void verifyNameRequiredField() {
            setFileExcel();
            verifyHTML5RequiredField(inputName);
            assertEquals(getHTML5MessageField(inputName), excelHelper.getCellData(6, 4), "Validation message name is required not match");
      }

      public void deleteBrand() {
            setFileExcel();
            verifyBrandPage();
            setTextAndKeysENTER(inputSearchBrand, excelHelper.getCellData(2, 5));
            waitForPageLoaded();
            clickElement(deleteBrand);
            verifyDeteleConfirmation();
            clickElement(buttonDelete);
            sleep(2);
            assertEquals(getTextElement(alertMessage), excelHelper.getCellData(6, 6), "Content of alert message delete sucessful NOT match");
      }

      private void verifyDeteleConfirmation() {
            checkElementDisplayed(headerDeleteForm);
            sleep(2);
            assertEquals(getTextElement(confirmMessage), excelHelper.getCellData(6, 5), "Content of confirm message delete NOT match");
            checkElementEnable(buttonDelete);
      }

}
