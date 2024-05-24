package drivers;

import org.openqa.selenium.WebDriver;

public class DriverManager {

      //ThreadLocal thuộc Java
      private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

      private DriverManager() {
      }

      // thay thế cho tất cả giá trị driver bình thường trong project (vì nó lấy giá trị từ ThreadLocal)
      public static WebDriver getDriver() {
            return driver.get();    // thuộc selenium nhưng lấy giá trị từ ThreadLocal
      }

      // dùng tại BaseTest vị trí @Before (cần set giá trị driver trước khi chạy testcase)
      public static void setDriver(WebDriver driver) {
            DriverManager.driver.set(driver);
      }

      // dùng tại BaseTest vị trí @After (để reset giá trị driver về null, và xóa luôn vị trí driver đó trong ThreadLocal sau mỗi testcase)
      public static void quit() {
            if ( DriverManager.driver.get() != null ){
                  DriverManager.driver.get().quit();  // thuộc selenium nhưng lấy giá trị từ ThreadLocal
                  driver.remove();    // thuộc ThreadLocal
            }
      }
}
