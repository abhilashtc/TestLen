package com.lenox.library;

/*
 * @author	:	abhilash.tc@gmail.com
 * 
 * This class deals with the reports for the test, its been done using the Extent Reports library, I have just customized it
*/

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

//--------------
import org.apache.commons.io.FileUtils;
import com.google.common.io.Files;
import com.lenox.TestBase.TestBase;
import com.lenox.utilities.GeneralUtilities;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import javax.imageio.ImageIO;


public class ExtentReportLibrary {
	
	static ExtentReports reports;
	static ExtentTest test;
	public static ExtentTest testInfo;
	static ExtentHtmlReporter htmlReporter;
	
	
	public String setupExtentReports(String ReportName) throws Exception {
		String currentDir = System.getProperty("user.dir");
		String configDir = currentDir + "/Configuration";
		System.out.println("Directory is " + currentDir);
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		timeStamp = GeneralUtilities.tokenizeTime(timeStamp);
		
		File fl = new File(currentDir + "/TestReport");
		
		if(!fl.exists()) {
			fl.mkdir();
			System.out.println("Directory " + currentDir + "/TestReport has been created.");
			System.out.println("fl -> " + fl.getAbsolutePath());
		}
		currentDir = currentDir + "/TestReport";
		
		
		htmlReporter = new ExtentHtmlReporter(new File (currentDir + "/" + ReportName + "_" + timeStamp + ".html"));  
		htmlReporter.loadXMLConfig(configDir + "/Extent-Config.xml");
		reports = new ExtentReports();
		reports.setSystemInfo("Envirnoment", "QA");
		reports.attachReporter(htmlReporter);
		return (currentDir + "/" + ReportName + "_" + timeStamp + ".html");
	}
	
	public void register(Method method) {
		String testName = method.getName();
		testInfo = reports.createTest(testName);
	}
	
	public void captureStatus(ITestResult result, WebDriver driver) throws Exception {
		System.out.println("*************************************INSIDE captureStatus");
		if(result.getStatus() == ITestResult.SUCCESS) {
			testInfo.log(Status.PASS, "The Test Method Name is " + result.getName() + " PASSED.");
		}else if(result.getStatus() == ITestResult.FAILURE){
			testInfo.log(Status.FAIL, "The Test Method Name is " + result.getName() + " FAILED.");
			testInfo.log(Status.FAIL, "Test Failure... " + result.getThrowable());

		}else if(result.getStatus() == ITestResult.SKIP){
			testInfo.log(Status.FAIL, "The Test Method Name is " + result.getName() + " SKIPPED.");
			testInfo.log(Status.FAIL, "Test Failure... " + result.getThrowable());
		}
	}
	
	
	
	
	public void cleanUp() {
		reports.flush();
	}
	

	public String captureScreen(String testName, WebDriver driver) throws Exception {
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		timeStamp = tokenizeTime(timeStamp);
		String dest ="D://NodeJS//JIRA_Tickets//screenshots//"+ testName + "_" + timeStamp +".png";
		String fileName = testName + "_" + timeStamp +".png";
		File target = new File(dest);
		FileUtils.copyFile(src, target);
		return fileName;
	
	}
	

	
	public static String tokenizeTime(String timeStamp) throws Exception{
		System.out.println("Inside tokenizeTime " + timeStamp);
		String year = timeStamp.substring(0, 4);
		String month = timeStamp.substring(4, 6);
		String day = timeStamp.substring(6, 8);
		
		String tIME = timeStamp.substring(9, 15);
		String time1 = tIME.substring(0, 2) + "-" + tIME.substring(3, 5) + "-" + tIME.substring(5, 6);
		
		return(month + "~" + day + "~" + year + "_" + time1);
	}
	

 
}
