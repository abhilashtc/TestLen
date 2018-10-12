package com.lenox.TestBase;

/*
 * @author	:	abhilash.tc@gmail.com
 * 
 * This class deals with loading the Browser Driver
*/

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserFactory extends TestBase {
	public static WebDriver initializeBrowser(String browserType) {
		logger.info("Inside @BeforeTest in ProposalTool.java");
		logger.info("Browser Type is " + browser);
		if (browser.equals("FireFox")) {
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
			driver = new FirefoxDriver();
			logger.debug("FireFox Launched !!!");
		} else if (browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
			System.getProperty("user.dir") + driverPath + "chromedriver.exe");
			driver = new ChromeDriver();
			logger.debug("Chrome Launched !!!");
		} else if (browser.equals("InternetExplorer")) {
			System.setProperty("webdriver.ie.driver",
			System.getProperty("user.dir") + driverPath + "IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		return driver;
		
	}
	

}
