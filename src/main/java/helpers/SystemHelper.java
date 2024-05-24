package helpers;

import java.io.File;

public class SystemHelper {

      // dir: directory (danh mục)
      public static String getCurrentDir() {

            // System.getProperty("user.dir"): lấy ra đường dẫn File ( từ ổ đĩa đến folder project)
            // File.separator: thay thế cho ký tự đường dẫn trong máy tính
            String path = System.getProperty("user.dir") + File.separator;
            return path;
      }

}