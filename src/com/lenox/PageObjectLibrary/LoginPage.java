package com.lenox.PageObjectLibrary;

/*
 * @author	:	abhilash.tc@gmail.com

 * 
 * This class is the PageFactory class for Login Page.
 * This has all the required WebElements and the methods for the relevant Page. 
*/


import java.util.StringTokenizer;

//import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.FluentWait;
//import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.lenox.TestBase.TestBase;
//import com.mongodb.diagnostics.logging.Logger;

public class LoginPage extends TestBase{
	WebDriver driver;
	WebDriverWait wait;
	
	public LoginPage(WebDriver pDriver) {
		this.driver = pDriver;
		wait = new WebDriverWait(driver,30);
	}
	
	@FindBy(how=How.XPATH, using="//a[contains(.,'Sign In')]") WebElement signIn;
	@FindBy(how=How.ID, using="j_username") WebElement loginID;
	@FindBy(how=How.ID, using="j_password") WebElement passwd;
	@FindBy(how=How.ID, using="loginSubmit") WebElement loginButton;
	@FindBy(how=How.XPATH, using="//li[@class='welcome-msg']") WebElement welcomeMsg;
	@FindBy(how=How.ID, using="logout_id") static WebElement signOut;
	
	public boolean login(String userName, String password) {
		wait.until(ExpectedConditions.visibilityOf(signIn));
		signIn.click();
		
		wait.until(ExpectedConditions.visibilityOf(loginID));
		loginID.sendKeys(userName);
		passwd.sendKeys(password);
		logger.info("Keyed In the login ID <" + userName + "> and Password <" + password + ">");
		
		wait.until(ExpectedConditions.visibilityOf(loginButton));
		loginButton.click();
		logger.info("Clicked on Login Button");
		boolean loginStatus = validateLogin(userName);
		logger.info("Login Status is : " + loginStatus);
		return loginStatus;
	}
	
	public boolean validateLogin(String userName) {
		wait.until(ExpectedConditions.visibilityOf(welcomeMsg));
		logger.info("Welcome Message -> " + welcomeMsg.getText());
		String welcomeTxt = welcomeMsg.getText();
		
		StringTokenizer str = new StringTokenizer(userName, "@");
		String user = str.nextToken();
		
		if(welcomeTxt.equals("Welcome," + user)) {
			logger.info("Logged in Successfully");
			return true;
		} else {
			logger.info("Log in Failed");
			return false;
		} 
	}
	
	public static void logOut() {
		signOut.click();
	}
}
