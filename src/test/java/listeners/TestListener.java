package listeners;

import com.aventstack.extentreports.Status;
import helpers.CaptureHelper;
import helpers.PropertiesHelper;
import keywords.WebUI;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.AllureManager;
import reports.ExtentReportManager;
import reports.ExtentTestManager;
import utils.LogUtils;

public class TestListener implements ITestListener {
      public String getTestName(ITestResult result) {
            return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
      }

      public String getTestDescription(ITestResult result) {
            return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
      }

      @Override
      public void onStart(ITestContext result) {
            PropertiesHelper.loadAllFiles();

            if (PropertiesHelper.getValue("VIDEO_RECORD").equals("on")) {
                  CaptureHelper.startRecord(result.getName());
            }
      }

      @Override
      public void onFinish(ITestContext result) {
            //Kết thúc và thực thi Extents Report
            ExtentReportManager.getExtentReports().flush();

            if (PropertiesHelper.getValue("VIDEO_RECORD").equals("on")) {
                  WebUI.sleep(1);
                  CaptureHelper.stopRecord();
            }
      }

      @Override
      public void onTestStart(ITestResult result) {
            LogUtils.info("\uD83C\uDF38 \uD83C\uDF38 \uD83C\uDF38 \uD83C\uDF38 " + result.getName() + " \uD83D\uDCDD");

            if (PropertiesHelper.getValue("VIDEO_RECORD").equals("on")) {
                  CaptureHelper.startRecord(result.getName());
            }

            //Bắt đầu ghi 1 TCs mới vào Extent Report
            ExtentTestManager.saveToReport(getTestName(result), getTestDescription(result));
      }

      @Override
      public void onTestSuccess(ITestResult result) {
            LogUtils.info("   ➡\uFE0F  " + result.getName() + " is successfully ✔\uFE0F"); //""

            if (PropertiesHelper.getValue("SCREENSHOT_PASS").equals("on")) {
                  CaptureHelper.takeScreenshot(result.getName());
            }

            if (PropertiesHelper.getValue("VIDEO_RECORD").equals("on")) {
                  WebUI.sleep(1);
                  CaptureHelper.stopRecord();
            }

            //Extent Report
            ExtentTestManager.logMessage(Status.PASS, result.getName() + " is passed ✔\uFE0F");
      }

      @Override
      public void onTestFailure(ITestResult result) {
            LogUtils.error("  ➡\uFE0F  " + result.getName() + " is FAIL ❌");

            if (PropertiesHelper.getValue("SCREENSHOT_FAIL").equals("on")) {
                  CaptureHelper.takeScreenshot(result.getName());
            }

            if (PropertiesHelper.getValue("VIDEO_RECORD").equals("on")) {
                  WebUI.sleep(1);
                  CaptureHelper.stopRecord();
            }

            //Extent Report
            ExtentTestManager.addScreenShot(result.getName());  //add hình
            ExtentTestManager.logMessage(Status.FAIL, result.getThrowable().toString());    // xuất ra lý do lỗi
            ExtentTestManager.logMessage(Status.FAIL, result.getName() + " is failed ❌");    // kết luận testcase fail

            //Allure Report
//        AllureManager.saveTextLog(result.getName() + " is failed.");
            AllureManager.saveScreenshotPNG();
      }

      @Override
      public void onTestSkipped(ITestResult result) {
            LogUtils.warn("⚠\uFE0F ⚠\uFE0F ⚠\uFE0F " + result.getName() + " is SKIPPED ❗❗❗❗");

            if (PropertiesHelper.getValue("VIDEO_RECORD").equals("on")) {
                  WebUI.sleep(1);
                  CaptureHelper.stopRecord();
            }

            //Extent Report
            ExtentTestManager.logMessage(Status.SKIP, result.getThrowable().toString());
      }

      @Override
      public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
            LogUtils.info("onTestFailedButWithinSuccessPercentage: " + result.getName());
      }
}