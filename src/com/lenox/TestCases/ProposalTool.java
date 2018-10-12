
package com.lenox.TestCases;

/*
 * @author	:	abhilash.tc@gmail.com
 * 
 * This class deals with the Test Cases for creating new Leads in the Proposal Page
*/

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.base.Verify;
import com.lenox.PageObjectLibrary.LoginPage;
import com.lenox.PageObjectLibrary.ProposalPage;
import com.lenox.TestBase.BrowserFactory;
import com.lenox.TestBase.TestBase;
import com.lenox.library.EMailTestResults;
import com.lenox.library.ExtentReportLibrary;
import com.lenox.listeners.CustomRetryListener;
import com.lenox.utilities.GeneralUtilities;

public class ProposalTool extends TestBase{
	
	public static String reportName;
	
	@BeforeTest
	public static void BeforeTest() {
		//Initializing the WebBrowser
		driver = BrowserFactory.initializeBrowser(browser);
	}
	
	@BeforeMethod
	public void register(Method method) {
		ExtRep.register(method);
		logger.info("Inside @BeforeMethod HomePage is " + homePage );
		logger.info("Inside @BeforeMethod URL is " + url );
		driver.get(homePage);
		logger.info("Page Title is : " + driver.getTitle());
	}
	
	@AfterMethod
	public void captureStatus(ITestResult result) throws Exception {
		ExtRep.captureStatus(result, driver);
		LoginPage.logOut();
	}
	
	@AfterTest
	public void cleanUp() {
		ExtRep.cleanUp();
	}
	
	@AfterSuite
	public void afterSuite() throws Exception {
		System.out.println("Inside @AfterSuite in ExtentReportTest");

	}
	

	
	
/*	
	This Test will Add Lead in the Proposal Tool using an Image File, the user details are taken from TestData\UserDetails.xlsx under the Project Home Folder
	 * The Image files are located in the TestData\ImageFiles folder under the ProjectHome Folder
	 * We could even provide the info pertaining to the DocumentFile Name in the Excel sheet which has the user details if any requirement arises.
	*/
	@Test (priority=1, enabled=true, retryAnalyzer=com.lenox.listeners.CustomRetryListener.class)
	public void testOne_ProposalWithImage() throws Exception {
		logger.info("Started testOne");
		Boolean titleVerify = driver.getTitle().equals("Homepage | LennoxPROs.com");
		Verify.verify(titleVerify);
		logger.info("Verified Page Title and it has : " + titleVerify);
		
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		
		String userName = GeneralUtilities.getValueOfConfigProp("environmentDetails.properties", "loginID");
		String password = GeneralUtilities.getValueOfConfigProp("environmentDetails.properties", "password");
		boolean loginStatus = loginPage.login(userName, password);
		Assert.assertTrue(loginStatus);
		
		ProposalPage proposalPage = PageFactory.initElements(driver, ProposalPage.class);
		String userDetails = "UserDetails.xlsx";
		String userData[][] = GeneralUtilities.ReadExcel(userDetails);
		proposalPage.enterUserDetails(userData);
		
		//Image files are located in the TestData\ImageFiles folder under the ProjectHome Folder
		//We can even provide this info in the Excel sheet from where the user details are provided
		String imageName = "IMG-20180404-WA0002.jpg";
		boolean imageUploadStatus = proposalPage.uploadImage(imageName);
		Assert.assertTrue(imageUploadStatus);
	}
	
	
	
	
	
	/*
	This Test will Add Lead in the Proposal Tool using an Document File, the user details are taken from TestData\UserDetails.xlsx under the Project Home Folder
	 * The Document files are located in the TestData\Docs folder under the ProjectHome Folder.
	 * We could even provide the info pertaining to the DocumentFile Name in the Excel sheet which has the user details if any requirement arises
	*/
	@Test (priority=5, enabled=true, retryAnalyzer=com.lenox.listeners.CustomRetryListener.class)
	public void testTwo_ProposalWithDoc() throws Exception {
		logger.info("Started testTwo");
		Boolean titleVerify = driver.getTitle().equals("Homepage | LennoxPROs.com");
		Verify.verify(titleVerify);
		logger.info("Verified Page Title and it has : " + titleVerify);
		
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		
		String userName = GeneralUtilities.getValueOfConfigProp("environmentDetails.properties", "loginID");
		String password = GeneralUtilities.getValueOfConfigProp("environmentDetails.properties", "password");
		boolean loginStatus = loginPage.login(userName, password);
		Assert.assertTrue(loginStatus);
		
		//Image files are located in the TestData\ImageFiles folder under the ProjectHome Folder
		//We can even provide this info in the Excel sheet from where the user details are provided
		ProposalPage proposalPage = PageFactory.initElements(driver, ProposalPage.class);
		String userDetails = "UserDetails.xlsx";
		String userData[][] = GeneralUtilities.ReadExcel(userDetails);
		proposalPage.enterUserDetails(userData);
		
		String docName = "LenoxTestDoc1.docx";
		proposalPage.uploadDoc(docName);
	}
	
	

}
