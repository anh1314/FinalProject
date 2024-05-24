package common;

import drivers.DriverManager;
import listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

@Listeners(TestListener.class)
public class BaseTest {
      
      @BeforeMethod
      @Parameters({"BROWSER"})
      public void createDriver(@Optional("chrome") String browserName) {
            WebDriver driver = setupBrowser(browserName);   //khởi tạo loại browser và gán vào driver
//        WebDriver driver = setupBrowser(PropertiesHelper.getValue("browser"));

            DriverManager.setDriver(driver);    // mang giá trị driver đã khởi tạo vào trong ThreadLocal
      }

      //Viết hàm trung gian để lựa chọn Browser cần chạy với giá trị tham số "browser" bên trên truyền vào
      public WebDriver setupBrowser(String browserName) {
            WebDriver driver ;
            switch (browserName.trim().toLowerCase()) {
                  case "chrome":
                        driver = initChromeDriver();
                        break;
                  case "firefox":
                        driver = initFirefoxDriver();
                        break;
                  case "edge":
                        driver = initEdgeDriver();
                        break;
                  default:
                        System.out.println("Browser: " + browserName + " is invalid, Launching Chrome as browser of choice...");
                        driver = initChromeDriver();
            }
            return driver;
      }

      // Viết các hàm khởi chạy cho từng Browser đó
      private WebDriver initChromeDriver() {
            WebDriver driver;
            System.out.println("\n  \uD83D\uDE80 Launching Chrome browser...");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            return driver;
      }

      private WebDriver initEdgeDriver() {
            WebDriver driver;
            System.out.println("\uD83D\uDE80 Launching Edge browser...");
            driver = new EdgeDriver();
            driver.manage().window().maximize();
            return driver;
      }

      private WebDriver initFirefoxDriver() {
            WebDriver driver;
            System.out.println("\uD83D\uDE80 Launching Firefox browser...");
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            return driver;
      }

      @AfterMethod
      public void closeDriver() {

            //Screenshot and Record video in TestListener
            DriverManager.quit();
      }

}

