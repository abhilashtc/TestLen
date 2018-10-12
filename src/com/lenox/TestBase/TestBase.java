package com.lenox.TestBase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.lenox.library.EMailTestResults;
import com.lenox.library.ExtentReportLibrary;
import com.lenox.utilities.GeneralUtilities;

/* 
 * @author Abhilash TC
 * 
 * This class will initalize the log4J and ExtentReports and will the Email upon Test Completion to the intended 
 * recipients from the .\Configuration\eMailDetails.properties.
 * 
 * This class will be Extended (Inherited) in the Tests
 */

public class TestBase {
//**WebDriver, Properties File, Log4J, ExtentReports etc will be initialized here

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger logger;
	public static WebDriverWait wait;
	public static ExtentReportLibrary ExtRep;
//	public static WebElement lastWebElement;
	
	public static String browser;
	public static String url, homePage;
	public static String reportName;
	public static String driverPath;
	
	
	//This method will configure Log4j for logging the test
	public void setupLog4j() {
		String currentDir = System.getProperty("user.dir");
		String configFile = currentDir + "\\Configuration\\Log4j.properties\\";
		logger = Logger.getLogger("LogDemo");
		PropertyConfigurator.configure(configFile);
		System.out.println("Log4J Configured successfully");
		logger.info("Log4J Configured successfully");
	}
	
	
	
	
	//This method will configure ExtentReports
	public void setupExtentReports() throws Exception {
		ExtRep = new ExtentReportLibrary();
		System.out.println("Inside BeforeTest");
		reportName = ExtRep.setupExtentReports("LennoxRegressionTest");
		System.out.println("----------------" + reportName);
	}
	
	
	
	
	//This method will get the environment details and also initialize the log4J and ExtentReports
	@BeforeSuite
	public void setUp() throws Exception {
		System.out.println("=======START===============TestBase.java  @BeforeSuite setUP()============================");
		setupLog4j();
		setupExtentReports();
		logger.info("Inside @BeforeSuite in TestBase.java");
		
		logger.info(">>> START >>> TestBase.java  ~~~ @BeforeSuite setUP()");
		String currentDir = System.getProperty("user.dir");
		String configFile = currentDir + "\\Configuration\\environmentDetails.properties\\";
		driverPath = currentDir + "\\resources\\executables\\";
		logger.info("Directory is " + currentDir + " -- " + configFile);
		
		
		FileReader propertyFile = null;
		try {
			propertyFile = new FileReader(configFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Properties configProperty = new Properties();
		try {
			configProperty.load(propertyFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			browser = configProperty.getProperty("Browser");
		}
		catch (NullPointerException e) {
			System.out.println("NullPointerException " + e);
			logger.error("NullPointerException : " + e);
		}
		logger.info("Browser is ->" + browser + ".");
		if (browser == null) {
			browser = "FireFox";
		}
		
		url = configProperty.getProperty("URL");
		homePage = url;
		logger.info(("Browser ->" + browser));
		logger.info(("URL ->" + url));
		
		
		logger.info("======================TestBase.java  @BeforeSuite setUP()============================");
		logger.info("Exiting @BeforeSuite in TestBase.java");
		
	}
	
	//This method will call the EMail method to email the Test Report 
	@AfterSuite
	public void tearDown() {
		logger.info("Inside @tearDown in TestBase.java");
		System.out.println("=======START===============TestBase.java  @AfterSuite tearDown()============================");
		System.out.println("Inside @AfterSuie, Emailed Report " + reportName);
		try {
			EMailTestResults.emailResults(reportName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Test Results has been emailed to the intended recipients...");
		
		if (driver != null) {
			logger.info("Quiting WebDriver!!!");
			driver.quit();
		}
		logger.debug("Test execution completed !!!");
		logger.info("Exiting @tearDown in TestBase.java");
	}
}
	
