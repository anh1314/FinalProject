package AT04_CMS.pages;

import drivers.DriverManager;
import helpers.ExcelHelper;
import helpers.PropertiesHelper;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class ProductPage {

      private By menuAddNewProduct = By.xpath("//span[normalize-space()='Add New Product']");
      private By headerProduct = By.xpath("//h1[normalize-space()='All products']");
      private By buttonAddNewProduct = By.xpath("//a[@class='btn btn-circle btn-info']");
      //Product Information
      private By inputProductName = By.xpath("//input[@placeholder='Product Name']");
      private By dropListCategory = By.xpath("//div[contains(text(),'Sport shoes')]");
      private By searchCategory = By.xpath("//div[@class='dropdown-menu show']//input[@aria-label='Search']");
      private By dropListBrand = By.xpath("//div[contains(text(),'Select Brand')]");
      private By searchBrand = By.xpath("//div[@class='dropdown-menu show']//input[@aria-label='Search']");
      private By inputUnit = By.xpath("//input[@placeholder='Unit (e.g. KG, Pc etc)']");
      private By inputWeight = By.xpath("//input[@placeholder='0.00']");
      private By inputMinimumPurchaseQty = By.xpath("//input[@name='min_qty']");
      private By textboxTags = By.xpath("//span[@role='textbox']");

      //Product Images
      private By browseGalleryImages = By.xpath("//div[@data-multiple='true']//div[@class='input-group-text bg-soft-secondary font-weight-medium'][normalize-space()='Browse']");
      private By searchImages = By.xpath(" //input[@placeholder='Search your files']");
      private By buttonAddFiles = By.xpath("//button[normalize-space()='Add Files']");
      private By browseThumbnailImage = By.xpath("//body/div[@class='aiz-main-wrapper']/div[@class='aiz-content-wrapper']/div[@class='aiz-main-content']/div[@class='px-15px px-lg-25px']/div/form[@id='choice_form']/div[@class='row gutters-5']/div[@class='col-lg-8']/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]");
      private By linkThumbnailImage = By.xpath("//div[@class='card-file-thumb']");
      //Product Videos
      private By inputVideoLink = By.xpath("//input[@placeholder='Video Link']");
      //Product Variation
      private By enableProductVariation = By.xpath("//div[@class='col-md-1']//span");
      private By dropListColors = By.xpath("(//button[@title='Nothing selected'])[1]");
      private By searchColor = By.xpath("//div[@class='dropdown-menu show']//input[@aria-label='Search']");

      //Product price + stock
      private By inputUnitPrice = By.xpath("//input[@placeholder='Unit price']");
      private By inputDiscountDateRange = By.xpath("//input[@placeholder='Select Date']");
      private By buttonSelectDate = By.xpath("//button[@class='applyBtn btn btn-sm btn-primary']");
      private By inputDiscount = By.xpath("//input[@placeholder='Discount']");
      private By inputPriceBlack = By.xpath("//input[@name='price_Black']");
      private By selectFlat_Percent = By.xpath("//select[@name='discount_type']");
      private By showVariant = By.xpath("//span[@class='footable-toggle fooicon fooicon-plus']");
      private By inputSKU = By.xpath("//input[@name='sku_Black']");
      private By inputQuantity = By.xpath("//input[@name='qty_Black']");

      //Product Description
      private By textboxDescription = By.xpath("//div[@role='textbox']");
      //SEO Meta Tags
      private By inputMetaTitle = By.xpath("//input[@placeholder='Meta Title']");
      private By inputDescriptionSEO = By.xpath("//textarea[@name='meta_description']");
      private By browseMetaImage = By.xpath("(//div[@class='input-group-prepend'])[5]");
      private By linkImage = By.xpath("//div[@class='card-file-thumb']");
      private By buttonSaveUnpublish = By.xpath("//button[normalize-space()='Save & Unpublish']");
      private By buttonSavePublish = By.xpath("//button[normalize-space()='Save & Publish']");

      //verify, edit,  value
      private By alertAddProductSuccess = By.xpath("//span[@data-notify='message']");
      private By alertUnitPrice_DiscountNull = By.xpath("//li[normalize-space()='The discount must be less than 0.']");
      private By editFirstProduct = By.xpath("(//a[@title='Edit'])[1]");
      private By buttonUpdateProduct = By.xpath("//button[normalize-space()='Update Product']");
      private By searchProductName = By.xpath("//input[@id='search']");
      private By alertUpdateSuccess = By.xpath(" //span[@data-notify='message']"); //div[@role='alert']

      // view product details
      private By viewProduct = By.xpath("(//a[@title='View'])[1]");
      private By nameProduct = By.xpath("//h1[contains(.,'Lenovo 05-2024')]");
      private By discountPrice = By.xpath("//strong[@class='h2 fw-600 text-primary']");
      private By quantity = By.xpath("//div[@class='avialable-amount opacity-60']");
      private By popupWeb = By.xpath("//button[@data-key='website-popup']");

      ExcelHelper excelHelper = new ExcelHelper();

      private void setFileExcel() {
            excelHelper.setExcelFile(PropertiesHelper.getValue("EXCEL_PATH"), "ProductData");
      }

      public void verifyProductPage() {
            WebUI.waitForPageLoaded();
            setFileExcel();
            Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().equals(excelHelper.getCellData(6, 1)), "NOT to the Product page");
            Assert.assertTrue(WebUI.checkElementDisplayed(headerProduct), "header Product page NOT display");
            WebUI.assertEquals(WebUI.getTextElement(headerProduct), excelHelper.getCellData(6, 2), "Incorrect header, NOT Product page");
            Assert.assertTrue(WebUI.checkElementEnable(buttonAddNewProduct), "Button Add new product NOT enable");
      }

      //Product Information
      private void enterProductInformation(String productName, String unit) {
            WebUI.sendKeys(inputProductName, productName);
            WebUI.selectDynamicDropdown(dropListCategory, searchCategory, excelHelper.getCellData(2, 3));
            WebUI.sleep(2);
            WebUI.selectDynamicDropdown(dropListBrand, searchBrand, excelHelper.getCellData(2, 4));
            WebUI.sendKeys(inputUnit, unit);
            WebUI.clearAndSendKeys(inputWeight, excelHelper.getCellData(2, 6));
            WebUI.clearAndSendKeys(inputMinimumPurchaseQty, excelHelper.getCellData(2, 7));
            WebUI.setTextAndKeysENTER(textboxTags, excelHelper.getCellData(2, 8));
      }

      //Product Images
      private void enterProductImages() {
            WebUI.getImage(browseGalleryImages, searchImages, excelHelper.getCellData(2, 10), linkImage, buttonAddFiles);
            WebUI.getImage(browseThumbnailImage, searchImages, excelHelper.getCellData(2, 10), linkThumbnailImage, buttonAddFiles);
      }

      //Product Videos, Product Variation
      private void enterProductVideos_Variation() {
            WebUI.sendKeys(inputVideoLink, excelHelper.getCellData(2, 11));
            WebUI.clickElement(enableProductVariation);
            WebUI.selectDynamicDropdown(dropListColors, searchColor, excelHelper.getCellData(2, 12));
            WebUI.clickElement(dropListColors);
      }

      //Product price + stock
      private void enterProductPrice_Stock(String unitPrice, String discount) {
            WebUI.sleep(3);
            WebUI.clearAndSendKeys(inputUnitPrice, unitPrice);
            WebUI.sendKeys(inputDiscountDateRange, excelHelper.getCellData(2, 15));
            WebUI.clickElement(buttonSelectDate);
            WebUI.clearAndSendKeys(inputDiscount, discount);
            WebUI.selectStaticDropdown(selectFlat_Percent, excelHelper.getCellData(2, 17));
            WebUI.sleep(3);
            if (WebUI.checkElementExist(showVariant)) {
                  WebUI.clickElement(showVariant);
            }
            WebUI.sleep(4);
            WebUI.clearAndSendKeys(inputPriceBlack, excelHelper.getCellData(2, 14));
            WebUI.sleep(2);
            WebUI.sendKeys(inputSKU, excelHelper.getCellData(2, 18));
            WebUI.clearAndSendKeys(inputQuantity, excelHelper.getCellData(2, 19));
      }

      // Product Description, SEO Meta Tags
      private void enterProductDescription_SEO() {
            WebUI.sendKeys(textboxDescription, excelHelper.getCellData(2, 20));
            WebUI.sendKeys(inputDescriptionSEO, excelHelper.getCellData(2, 23));
            WebUI.sendKeys(inputMetaTitle, excelHelper.getCellData(2, 22));
            WebUI.getImage(browseMetaImage, searchImages, excelHelper.getCellData(2, 10), linkImage, buttonAddFiles);
            WebUI.clickElement(buttonSavePublish);
      }

      private void enterProductData(String productName, String unit, String unitPrice, String discount) {
            setFileExcel();
            verifyProductPage();
            setFileExcel();
            WebUI.clickElement(buttonAddNewProduct);
            enterProductInformation(productName, unit);
            enterProductImages();
            enterProductVideos_Variation();
            enterProductPrice_Stock(unitPrice, discount);
            enterProductDescription_SEO();
      }

      public void addNewProduct() {
            setFileExcel();
            enterProductData(excelHelper.getCellData(2, 2),
                    excelHelper.getCellData(2, 5),
                    excelHelper.getCellData(2, 14),
                    excelHelper.getCellData(2, 16));
            WebUI.waitForPageLoaded();
            verifyAddProductSuccess();
      }

      public void addProductNullProductName() {
            setFileExcel();
            enterProductData("",
                    excelHelper.getCellData(2, 5),
                    excelHelper.getCellData(2, 14),
                    excelHelper.getCellData(2, 16));
            WebUI.sleep(3);
            verifyProductNameNull();
      }

      public void addProductNullUnit() {
            setFileExcel();
            enterProductData(excelHelper.getCellData(2, 2),
                    "",
                    excelHelper.getCellData(2, 14),
                    excelHelper.getCellData(2, 16));
            WebUI.sleep(3);
            verifyUnitNull();
      }

      public void addProductNullUnitPrice() {
            setFileExcel();
            enterProductData(excelHelper.getCellData(2, 2),
                    excelHelper.getCellData(2, 5),
                    "",
                    excelHelper.getCellData(2, 16));
            WebUI.sleep(3);
            verifyUnitPriceNull();
      }

      public void addProductNullUnitDiscount() {
            setFileExcel();
            enterProductData(excelHelper.getCellData(2, 2),
                    excelHelper.getCellData(2, 5),
                    excelHelper.getCellData(2, 14),
                    "");
            WebUI.sleep(3);
            verifyDiscountNull();
      }

      public void editProduct() {
            setFileExcel();
            verifyProductPage();
            WebUI.setTextAndKeysENTER(searchProductName, excelHelper.getCellData(2, 2));
            WebUI.clickElement(editFirstProduct);
            WebUI.waitForPageLoaded();
            WebUI.clearAndSendKeys(inputDescriptionSEO, excelHelper.getCellData(2, 24));
            WebUI.sleep(2);
            WebUI.clickElement(buttonUpdateProduct);
            WebUI.sleep(2);
            verifyEditProductSuccess();
      }

      // Check view product details
      public void viewProductDetails() {
            WebUI.sleep(2);
            setFileExcel();
            searchProduct(excelHelper.getCellData(2, 2));
            WebUI.waitForPageLoaded();
            WebUI.clickElement(viewProduct);
            WebUI.sleep(2);
            WebUI.switchToWindowOrTabByUrl("https://cms.anhtester.com/admin/products/all");
            WebUI.clickElement(popupWeb);

            WebUI.sleep(3);
            WebUI.assertEquals(WebUI.getTextElement(nameProduct), excelHelper.getCellData(2, 2), "Product name NOT match");
            WebUI.assertEquals(WebUI.getTextElement(discountPrice), excelHelper.getCellData(6, 7), "Discount price NOT match");
            WebUI.assertEquals(WebUI.getTextElement(quantity), excelHelper.getCellData(6, 8), "Quantity NOT match");
      }

      // Verify
      private void verifyAddProductSuccess() {
            Assert.assertTrue(WebUI.checkElementDisplayed(alertAddProductSuccess), "alertAddProductSuccess is NOT displayed");
            WebUI.assertEquals(WebUI.getTextElement(alertAddProductSuccess), excelHelper.getCellData(6, 3), "Content of alertAddProductSuccess NOT match");
      }

      private void verifyProductNameNull() {
            WebUI.verifyHTML5RequiredField(inputProductName);
            WebUI.assertEquals(WebUI.getHTML5MessageField(inputProductName), excelHelper.getCellData(6, 4), "Validation message HTML5 Product name not match");
      }

      private void verifyUnitNull() {
            WebUI.verifyHTML5RequiredField(inputUnit);
            WebUI.assertEquals(WebUI.getHTML5MessageField(inputUnit), excelHelper.getCellData(6, 4), "Validation message HTML5 Unit not match");
      }

      private void verifyUnitPriceNull() {
            WebUI.verifyHTML5RequiredField(inputUnitPrice);
            Assert.assertTrue(WebUI.checkElementDisplayed(alertUnitPrice_DiscountNull), "alertDiscountNull is NOT displayed");
            WebUI.assertEquals(WebUI.getTextElement(alertUnitPrice_DiscountNull), excelHelper.getCellData(6, 5), "content of UnitPrice_DiscountNull NOT match");
      }

      private void verifyDiscountNull() {
            WebUI.verifyHTML5RequiredField(inputDiscount);
            Assert.assertTrue(WebUI.checkElementDisplayed(alertUnitPrice_DiscountNull), "Alert DiscountNull is NOT displayed");
            WebUI.assertEquals(WebUI.getTextElement(alertUnitPrice_DiscountNull), excelHelper.getCellData(6, 5), "Content of alert UnitPrice_DiscountNull NOT match");
      }

      private void verifyEditProductSuccess() {
            Assert.assertTrue(WebUI.getWebElement(alertUpdateSuccess).isDisplayed(), "Alert Update success is NOT displayed");
            WebUI.assertEquals(WebUI.getTextElement(alertUpdateSuccess), excelHelper.getCellData(6, 6), "Content of alert update successful NOT match");

      }
//************Handle data Table, Pagination*********************

      public void searchProduct(String searchText) {
            WebUI.setTextAndKeysENTER(searchProductName, searchText);   // search Product
            WebUI.waitForPageLoaded();
            WebUI.sleep(2);
      }

      private By listPage = By.xpath("//a[@class='page-link']");

      // Xử lý phân trang
      public void Pagination(String value) {
            // Lấy tổng số page
            int getListPage = WebUI.getWebElements(listPage).size();
            WebUI.sleep(2);

            if (getListPage == 0) {
                  WebUI.logConsole("Number of results page lists displayed: " + (getListPage + 1));
                  WebUI.checkContainsValueOnTableByColumn(2, value);
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
