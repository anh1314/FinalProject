package keywords;

import com.aventstack.extentreports.Status;
import drivers.DriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import reports.ExtentTestManager;
import utils.LogUtils;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class WebUI {
      private static int Timeout = 20;
      private static double ThreadSleep = 0.5;
      private static int PageLoadTimeout = 30;

      // Log console
      public static void logConsole(Object message) {
            LogUtils.info(message);
      }

      // Open URL
      @Step("Open URL: {0}")
      public static void openURL(String URL) {
            DriverManager.getDriver().get(URL);
            logConsole("Navigate to: " + URL);
            ExtentTestManager.logMessage(Status.INFO, "Open URL: " + URL);
      }

      // Get WebElement
      public static WebElement getWebElement(By by) {
            return DriverManager.getDriver().findElement(by);
      }

      // Get list WebElement
      public static List<WebElement> getWebElements(By by) {
            return DriverManager.getDriver().findElements(by);
      }

      // Get text
      public static String getTextElement(By by) {
            waitForElementVisible(by);
            String text = getWebElement(by).getText();
            logConsole("Get text of element " + by + " is " + text);
            return text;
      }

      // Get Attribute element
      public static String getAttributeElement(By by, String attribute) {
            waitForElementVisible(by);
            String value = getWebElement(by).getAttribute(attribute);
            logConsole("Get attribute of element " + by + " is " + value);
            return value;
      }

      // Check element exist
      public static boolean checkElementExist(By by) {
            List<WebElement> listElement = getWebElements(by);
            if (listElement.size() > 0) {
                  System.out.println("Element " + by + "exist");
                  return true;
            } else {
                  System.out.println("Element " + by + "NOT exist");
                  return false;
            }
      }

      // Check element Displayed
      public static boolean checkElementDisplayed(By by) {
            waitForElementVisible(by);
            boolean check = getWebElement(by).isDisplayed();
            return check;
      }

      // Check element Enable
      public static boolean checkElementEnable(By by) {
            waitForElementVisible(by);
            boolean check = getWebElement(by).isEnabled();
            return check;
      }

      // Check search table by column (xử lý cột trên 1 trang)
      public static void checkContainsValueOnTableByColumn(int column, String value) {
            List<WebElement> totalRows = getWebElements(By.xpath("//tbody/tr"));
            logConsole("Number of results for keywords (" + value + "): " + totalRows.size());

            if (totalRows.size() < 1) {
                  logConsole("Not found value: " + value);
            } else {
                  for (int i = 1; i <= totalRows.size(); i++) {
                        boolean res = false;
                        WebElement title = getWebElement(By.xpath("//tbody/tr[" + i + "]/td[" + column + "]"));
                        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
                        js.executeScript("arguments[0].scrollIntoView(false);", title); // lăn chuột xuống

//                        sleep(2);
                        res = title.getText().toUpperCase().contains(value.toUpperCase());
                        logConsole("Row " + i + ": " + title.getText());
                        Assert.assertTrue(res, "Row " + i + " (" + title.getText() + ")" + "NOT contains the search value: " + value);
                  }
            }
      }

      // Click element
      @Step("Click on element {0}")
      public static void clickElement(By by) {
            waitForElementClickable(by);
            getWebElement(by).click();
            logConsole("Click on element " + by);
            ExtentTestManager.logMessage(Status.INFO, "Click on element " + by);
      }

      // Click element  với seconds là giá trị thay đổi
      @Step("Click on element {0} with timeout {1}")
      public static void clickElement(By by, int seconds) {
            waitForElementClickable(by, seconds);
            getWebElement(by).click();
            logConsole("Click on element  " + by + " with timeout is " + seconds + " seconds ");
            ExtentTestManager.logMessage(Status.INFO, "Click on element " + by + " with timeout is " + seconds + " seconds ");
      }

      // Clear and sendKeys
      @Step("Clear and Set text {1} on input {0} ")
      public static void clearAndSendKeys(By by, String text) {
            waitForElementVisible(by);
            getWebElement(by).clear();
            sleep(2);
            getWebElement(by).sendKeys(text);
            logConsole("Clear and Set text " + text + " on input " + by);
            ExtentTestManager.logMessage(Status.INFO, "Clear and Set text " + text + " on input " + by);
      }

      // sendKeys
      @Step("Set text {1} on input {0}")
      public static void sendKeys(By by, String text) {
            waitForElementVisible(by);
            getWebElement(by).sendKeys(text);
            logConsole("Set text " + text + " on input " + by);
            ExtentTestManager.logMessage(Status.INFO, "Set text " + text + " on input " + by);
      }

      // sendKeys với seconds là giá trị thay đổi
      @Step("Set text {1} on input {0} with timeout {2}")
      public static void sendKeys(By by, String text, int seconds) {
            waitForElementVisible(by, seconds);
            getWebElement(by).sendKeys(text);
            logConsole("Set text " + text + " on input " + by + " with timeout is " + seconds + " seconds ");
            ExtentTestManager.logMessage(Status.INFO, "Set text " + text + " on input " + by + "with timeout is " + seconds + " seconds");
      }

      //Keys.ENTER
      @Step("Set text {1} on element {0} and sendKeys Keys.ENTER")
      public static void setTextAndKeysENTER(By by, String text) {
            waitForElementVisible(by);
            getWebElement(by).sendKeys(text, Keys.ENTER);
            waitForPageLoaded();
            sleep(2);
            logConsole("Set text: " + text + " on element " + by);
            ExtentTestManager.logMessage(Status.INFO, "Set text: " + text + " on element " + by + "sendKeys Keys.ENTER");
      }

      // Set text and Keys
      @Step("Set text {1} and set keys {2} on element {0}")
      public static void setTextAndKeys(By by, String text, Keys key) {
            waitForPageLoaded();
            getWebElement(by).sendKeys(text, key);
            logConsole("Set text: " + text + " on element " + by);
            ExtentTestManager.logMessage(Status.INFO, "Set text " + text + " set keys " + key + " on element " + by);
      }

      // Verify Equals
      @Step("Verify equals: {0} and {1}")
      public static boolean verifyEquals(Object actual, Object expected) {
            waitForPageLoaded();
            logConsole("Verify equals: " + actual + " and " + expected);
            boolean check = actual.equals(expected);
            return check;
      }

      // Assert Equals
      @Step("Assert equals: {0} and {1}")
      public static void assertEquals(Object actual, Object expected, String message) {
            waitForPageLoaded();
            logConsole("Assert equals " + actual + " and " + expected);
            Assert.assertEquals(actual, expected, message);
      }

      // Verify Contains
      @Step("Verify contains: {0} and {1}")
      public static boolean verifyContains(String actual, String expected) {
            waitForPageLoaded();
            logConsole("Verify contains " + actual + " and " + expected);
            boolean check = actual.contains(expected);
            return check;
      }

      //Assert Contains
      @Step("Assert contains: {0} and {1}")
      public static void assertContains(String actual, String expected, String message) {
            waitForPageLoaded();
            logConsole("Assert contains " + actual + " and " + expected);
            boolean check = actual.contains(expected);
            Assert.assertTrue(check, message);
      }

      // Get image
      @Step("Upload image")
      public static void getImage(By browse, By search, String textSearch, By image, By buttonAdd) {
            clickElement(browse);
            setTextAndKeysENTER(search, textSearch);
            sleep(3);
            clickElement(image);
            clickElement(buttonAdd);
            logConsole("Upload image on " + browse);
      }

      // upload file
      @Step("Upload File with open Local Form")
      public static void uploadFileWithLocalForm(By by, String filePath) {
            waitForPageLoaded();
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).click().perform();  //Click to open form upload
            sleep(3);
            // Create Robot class
            Robot robot = null;
            try {
                  robot = new Robot();
            } catch (AWTException e) {
                  e.printStackTrace();
            }

            // Copy File path to Clipboard
            StringSelection str = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
            // Press Control+V to paste
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            // Release the Control V
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            robot.delay(2000);
            // Press Enter
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            LogUtils.info("Upload File with Local Form: " + filePath);
//            if (ExtentTestManager.getTest() != null) {
//                  logConsole("Upload File with Local Form: " + filePath);
//            }
//            AllureManager.saveTextLog("Upload File with Local Form: " + filePath);
      }

      //Static droplist
      @Step("Select {1} from static dropdown {0}")
      public static void selectStaticDropdown(By dropdownBy, String optionText) {
            Select dropdown = new Select(getWebElement(dropdownBy));
            dropdown.selectByVisibleText(optionText);
            logConsole("Select " + optionText + " from static dropdown " + dropdownBy);
            ExtentTestManager.logMessage(Status.INFO, "Select " + optionText + " from static dropdown " + dropdownBy);
      }

      //Dynamic dropdown (Show only 1 search result)
      @Step("Select {2} from dynamic dropdown {0}")
      public static void selectDynamicDropdown(By dropdownBy, By searchBy, String searchText) {
            clickElement(dropdownBy);
            waitForPageLoaded();
            setTextAndKeysENTER(searchBy, searchText);
            sleep(4);
            logConsole("Select " + searchText + " from dynamic dropdown " + dropdownBy);
      }

      //*********HTML5, Popup window, Alert******

      public static Boolean verifyHTML5RequiredField(By by) {
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            Boolean verifyRequired = (Boolean) js.executeScript("return arguments[0].required;", getWebElement(by));
            logConsole("check HTML5 Required Field exists " + by);
            return verifyRequired;
      }

      public static String getHTML5MessageField(By by) {
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            String message = (String) js.executeScript("return arguments[0].validationMessage;", getWebElement(by));
            return message;
      }

      public static Boolean verifyHTML5ValidValueField(By by) {
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            Boolean verifyValid = (Boolean) js.executeScript("return arguments[0].validity.valid;", getWebElement(by));
            return verifyValid;
      }

      // Chuyển sang tab mới với URL
      public static void switchToWindowOrTabByUrl(String url) {
            //Store the ID of the original window
            String originalWindow = DriverManager.getDriver().getWindowHandle();
            waitForPageLoaded();

            //Loop through until we find a new window handle
            for (String windowHandle : DriverManager.getDriver().getWindowHandles()) {
                  if (!originalWindow.contentEquals(windowHandle)) {
                        DriverManager.getDriver().switchTo().window(windowHandle);
                        if (DriverManager.getDriver().getCurrentUrl().equals(url)) {
                              break;
                        }
                  }
            }
      }

      // getText alert
      @Step("Get text on Alert")
      public static String getTextAlert() {
            sleep(2);
            LogUtils.info("Get text ion alert: " + DriverManager.getDriver().switchTo().alert().getText());
            return DriverManager.getDriver().switchTo().alert().getText();
      }


      // ************* Javascript Executor, Actions class, Robot class ************

      //cuộn chuột đến vị trí element (đối tượng By)
      public static void scrollToElement(By by) {
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript("arguments[0].scrollIntoView(true);", by);
      }

      // cuộn chuột đến vị trí element (đối tượng WebElement)
      public static void scrollToElement(WebElement webElement) {
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript("arguments[0].scrollIntoView(true);", webElement);
      }

      // di chuyển chuột đến vị trí element
      public static boolean moveToElement(By toElement) {
            try {
                  Actions action = new Actions(DriverManager.getDriver());
                  action.moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
                  return true;
            } catch (Exception e) {
                  logConsole(e.getMessage());
                  return false;
            }
      }

      //di chuyển chuột đến vị trí element
      public static boolean mouseHover(By by) {
            try {
                  Actions action = new Actions(DriverManager.getDriver());
                  action.moveToElement(getWebElement(by)).perform();
                  return true;
            } catch (Exception e) {
                  return false;
            }
      }

      // kéo thả chuột
      public static boolean dragAndDrop(By fromElement, By toElement) {
            try {
                  Actions action = new Actions(DriverManager.getDriver());
                  action.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
                  //action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
                  return true;
            } catch (Exception e) {
                  logConsole(e.getMessage());
                  return false;
            }
      }

      // nhấn và giữ chuột
      public static boolean clickAndHoldElement(By fromElement, By toElement) {
            try {
                  Actions action = new Actions(DriverManager.getDriver());
                  action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
                  return true;
            } catch (Exception e) {
                  logConsole(e.getMessage());
                  return false;
            }
      }

      public static void clickElementWithJs(By by) {
            waitForElementPresent(by);
            //Scroll to element với Javascript Executor
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
            //Click with JS
            js.executeScript("arguments[0].click();", getWebElement(by));
            LogUtils.info("Click on element with JS: " + by);
      }

      // nhấn phím ENTER
      public static boolean pressENTER() {
            try {
                  Robot robot = new Robot();
                  robot.keyPress(KeyEvent.VK_ENTER);
                  robot.keyRelease(KeyEvent.VK_ENTER);
                  return true;
            } catch (Exception e) {
                  return false;
            }
      }

      // Nhấn phím ESC
      public static boolean pressESC() {
            try {
                  Robot robot = new Robot();
                  robot.keyPress(KeyEvent.VK_ESCAPE);
                  robot.keyRelease(KeyEvent.VK_ESCAPE);
                  return true;
            } catch (Exception e) {
                  return false;
            }
      }

      // Nhấn phím F11
      public static boolean pressF11() {
            try {
                  Robot robot = new Robot();
                  robot.keyPress(KeyEvent.VK_F11);
                  robot.keyRelease(KeyEvent.VK_F11);
                  return true;
            } catch (Exception e) {
                  return false;
            }
      }

      // Tô màu viền đỏ cho element
      public static WebElement highLightElement(By by) {
            if (DriverManager.getDriver() instanceof JavascriptExecutor) {
                  ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].style.border='3px solid red'", getWebElement(by));
                  sleep(1);
            }
            return getWebElement(by);
      }

// ************* Wait ************

      //visibilityOfElementLocated
      public static void waitForElementVisible(By by) {
            try {
                  WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(Timeout), Duration.ofMillis(500));
                  wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } catch (Throwable error) {
                  LogUtils.error("Timeout waiting for the element Visible " + by.toString());
                  Assert.fail("Timeout waiting for the element Visible " + by.toString());
            }
      }

      //visibilityOfElementLocated với seconds là giá trị thay đổi
      public static void waitForElementVisible(By by, int seconds) {
            try {
                  WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds), Duration.ofMillis(500));
                  wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } catch (Throwable error) {
                  LogUtils.error("Timeout waiting for the element Visible " + by.toString());
                  Assert.fail("Timeout waiting for the element Visible " + by.toString());
            }
      }

      // Wait for elementToBeClickable
      public static void waitForElementClickable(By by) {
            try {
                  WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(Timeout), Duration.ofMillis(500));
                  wait.until(ExpectedConditions.elementToBeClickable(by));
            } catch (Throwable error) {
                  LogUtils.error("Timeout waiting for the element Clickable" + by.toString());
                  Assert.fail("Timeout waiting for the element Clickable" + by.toString());
            }
      }

      // Wait for elementToBeClickable  với seconds là giá trị thay đổi
      public static void waitForElementClickable(By by, int seconds) {
            try {
                  WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds), Duration.ofMillis(500));
                  wait.until(ExpectedConditions.elementToBeClickable(by));
            } catch (Throwable error) {
                  LogUtils.error("Timeout waiting for the element Clickable" + by.toString());
                  Assert.fail("Timeout waiting for the element Clickable" + by.toString());
            }
      }

      //presenceOfElementLocated
      public static void waitForElementPresent(By by) {
            try {
                  WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(Timeout), Duration.ofMillis(500));
                  wait.until(ExpectedConditions.presenceOfElementLocated(by));
            } catch (Throwable error) {
                  LogUtils.error("Element not exits " + by.toString());
                  Assert.fail("Element not exits " + by.toString());
            }
      }

      //presenceOfElementLocated với seconds là giá trị thay đổi
      public static void waitForElementPresent(By by, int seconds) {
            try {
                  WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds), Duration.ofMillis(500));
                  wait.until(ExpectedConditions.presenceOfElementLocated(by));
            } catch (Throwable error) {
                  LogUtils.error("Element not exits " + by.toString());
                  Assert.fail("Element not exits " + by.toString());
            }
      }

      // Wait For Page Loaded:  chờ trang load xong mới thao tác
      public static void waitForPageLoaded() {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(Timeout), Duration.ofMillis(500));
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
//        Wait for Javascript to load
            ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
                  @Override
                  public Boolean apply(WebDriver driver) {
                        return js.executeScript("return document.readyState").toString().equals("complete");
                  }
            };
//        Check JS is Ready
            boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");
//        Wait Javascript until it is Ready!
            if (!jsReady) {
                  System.out.println("Javascript is NOT Ready.");
//            Wait for Javascript to load
                  try {
                        wait.until(jsLoad);
                  } catch (Throwable error) {
                        error.printStackTrace();
                        Assert.fail("FAILED. Timeout waiting for page load.");
                  }
            }
      }

      // Thread.sleep
      public static void sleep(double seconds) {
            try {
                  Thread.sleep((long) (1000 * seconds));
            } catch (InterruptedException e) {
                  throw new RuntimeException(e);
            }
      }

      // chụp màn hình
      public static void captureScreenImage(String imageName) {
            Robot robot = null;
            try {
                  robot = new Robot();
            } catch (AWTException e) {
                  throw new RuntimeException(e);
            }
//        Get size screen browser
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            System.out.println(screenSize);
//        Khởi tạo kích thước khung hình với kích cỡ trên
            Rectangle screenRectangle = new Rectangle(screenSize);
//        Tạo hình chụp với độ lớn khung đã tạo trên
            BufferedImage image = robot.createScreenCapture(screenRectangle);
//        Lưu hình vào dạng file với dạng png
            File file = new File("src/test/resources/screenshots/" + imageName + ".png");
            try {
                  ImageIO.write(image, "png", file);
            } catch (IOException e) {
                  throw new RuntimeException(e);
            }
      }
}
