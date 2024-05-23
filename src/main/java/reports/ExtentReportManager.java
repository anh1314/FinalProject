package reports;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

      private static final ExtentReports extentReports = new ExtentReports();

      public synchronized static ExtentReports getExtentReports() {
            ExtentSparkReporter reporter = new ExtentSparkReporter("report/ExtentReport/ExtentReport.html");   //đường dẫn folder report
            reporter.config().setReportName("Extent Report | Anh ");
            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("Framework Name", "Selenium Java | Anh ");
            extentReports.setSystemInfo("Author", "Anh ");
            return extentReports;
      }

}
