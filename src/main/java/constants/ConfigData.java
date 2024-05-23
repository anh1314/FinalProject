package constants;

import helpers.PropertiesHelper;

public class ConfigData {

      static {
            PropertiesHelper.loadAllFiles();
      }

      public static String URL = PropertiesHelper.getValue("URL");
      public static String Email = PropertiesHelper.getValue("EMAIL");
      public static String Password = PropertiesHelper.getValue("PASSWORD");
}