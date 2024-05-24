package helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class PropertiesHelper {

      private static Properties properties;
      private static String linkFile;
      private static FileInputStream file;
      private static FileOutputStream out;
      private static String relPropertiesFilePathDefault = "src/test/resources/configs/configs.properties";

      // Read multiple Files
      public static Properties loadAllFiles() {
            LinkedList<String> files = new LinkedList<>();
            // Add tất cả file Properties vào đây theo mẫu
            files.add("src/test/resources/configs/configs.properties"); //  link từ folder project đến Files

            try {
                  properties = new Properties();
                  for (String f : files) {
                        Properties tempProp = new Properties();
                        linkFile = SystemHelper.getCurrentDir() + f;
                        file = new FileInputStream(linkFile);   // đọc Files
                        tempProp.load(file);
                        properties.putAll(tempProp);    // đọc tất cả data của Files ghép lại với nhau
                  }
                  System.out.println(" Load All Config \uD83D\uDD04");
                  return properties;
            } catch (IOException ioe) {
                  return new Properties();
            }
      }

      // Read 1 File: truyền vào đường link File cụ thể
      public static void setFile(String relPropertiesFilePath) {
            properties = new Properties();
            try {
                  linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePath;
                  file = new FileInputStream(linkFile);
                  properties.load(file);
                  file.close();
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      // lấy file từ biến toàn cục
      public static void setDefaultFile() {
            properties = new Properties();
            try {
                  linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePathDefault;
                  file = new FileInputStream(linkFile);
                  properties.load(file);
                  file.close();
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      // lấy giá trị thông qua key (của file)
      public static String getValue(String key) {
            String value = null;
            try {
                  if (file == null) {
                        properties = new Properties();
                        linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePathDefault;
                        file = new FileInputStream(linkFile);
                        properties.load(file);
                        file.close();
                  }
                  // Lấy giá trị từ file đã Set
                  value = properties.getProperty(key);
            } catch (Exception e) {
                  System.out.println(e.getMessage());
            }
            return value;
      }

      // Ghi file
      public static void setValue(String key, String keyValue) {
            try {
                  if (file == null) {
                        properties = new Properties();
                        file = new FileInputStream(SystemHelper.getCurrentDir() + relPropertiesFilePathDefault);
                        properties.load(file);
                        file.close();
                        out = new FileOutputStream(SystemHelper.getCurrentDir() + relPropertiesFilePathDefault);   //ghi file
                  }
                  //Ghi vào cùng file Prop với file lấy ra
                  out = new FileOutputStream(linkFile);
                  System.out.println(linkFile);
                  properties.setProperty(key, keyValue);
                  properties.store(out, null); // lưu giá trị vừa set vào (đường link) file
                  out.close();
            } catch (Exception e) {
                  System.out.println(e.getMessage());
            }
      }

}