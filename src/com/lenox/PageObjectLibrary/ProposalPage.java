package com.lenox.PageObjectLibrary;

/*
 * @author	:	abhilash.tc@gmail.com
 * 
 * This class is the PageFactory class for Proposals Page.
 * This has all the required WebElements and the methods for the relevant Page. 
*/


import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;

//import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
//import org.openqa.selenium.support.ui.FluentWait;
//import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.lenox.TestBase.TestBase;
import com.lenox.utilities.GeneralUtilities;
//import com.mongodb.diagnostics.logging.Logger;

public class ProposalPage extends TestBase {
	WebDriver driver;
	WebDriverWait wait;
	String fName, lName, eMail, phone;

	@SuppressWarnings("deprecation")
	public ProposalPage(WebDriver pDriver) {
		this.driver = pDriver;
		wait = new WebDriverWait(driver, 30);
	}

	@FindBy(how = How.XPATH, using = "//a[contains(@title,'Proposal Tool')]") WebElement proposalToolLink;
	@FindBy(how = How.ID, using = "firstName") WebElement firstName;
	@FindBy(how = How.ID, using = "lastName") WebElement lastName;
	@FindBy(how = How.ID, using = "phNo") WebElement phoneNum;
	@FindBy(how = How.ID, using = "email") WebElement emailID;
	@FindBy(how = How.NAME, using = "imageFiles") WebElement imageFile;
	@FindBy(how = How.XPATH, using = "(//span[contains(@class,'add-row error')])[3]") WebElement addMore;
	@FindBy(how = How.ID, using = "documents1.documentType") WebElement selectDocType;
	@FindBy(how = How.XPATH, using = "//div[@class='fileSelect show']") WebElement docUpl;
	@FindBy(how = How.ID, using = "btn-addLeadsForm") WebElement saveButon;
	@FindBy(how = How.XPATH, using = "//div[@id='globalMessages']") WebElement globalMsg;

	public void enterUserDetails(String userDetails[][]) {
		wait.until(ExpectedConditions.visibilityOf(proposalToolLink));
		proposalToolLink.click();

		// Providing the required spacing to the phone number to get it displayed as
		// intended, assuming its a 10 Digit Number
		String pNum = userDetails[0][2];
		pNum = pNum.substring(0, 3) + " " + pNum.substring(3, 6) + " " + pNum.substring(6);

		fName = userDetails[0][0];
		lName = userDetails[0][1];
		phone = pNum;
		eMail = userDetails[0][3];

		wait.until(ExpectedConditions.visibilityOf(firstName));
		firstName.sendKeys(fName);
		lastName.sendKeys(lName);
		phoneNum.sendKeys(phone);
		emailID.sendKeys(eMail);
		logger.info("Keyed In the First Name <" + fName + ">, Last Name<" + lName + ">" + ">, Last Phone Number<"
				+ phone + ">" + "> and Email ID<" + eMail + ">");

	}

	public boolean uploadImage(String imageName) throws Exception{
		logger.info("Going to Click on Browse to upload Image file :" + imageName);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", imageFile);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", imageFile);

		fileToUpload(imageName, "ImageFile");
		saveButon.click();
		logger.info("Clicked on Save Button");
		
		Thread.sleep(250);
		waitForPopUp();
		
		Thread.sleep(10000);
		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
		fWait.pollingEvery(10000, TimeUnit.MILLISECONDS);
		fWait.withTimeout(2, TimeUnit.SECONDS);
		
		fWait.until(ExpectedConditions.visibilityOf(globalMsg));
		
		System.out.println(globalMsg.getSize());
		System.out.println(globalMsg.getText());
		
		if(globalMsg.getText() == null) {
			System.out.println("globalMsg is blank");
			fWait.until(ExpectedConditions.visibilityOf(globalMsg));
		}
		
		String msg = globalMsg.getText();
		
		if(msg.equals("Lead has been created/edited successfully."))
			return true;
		else
			return false;
	}

	public boolean uploadDoc(String docName) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addMore);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", addMore);

		selectDocType.click();
		Select sDocType = new Select(selectDocType);
		sDocType.selectByVisibleText("PROPOSAL");

//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", imageFile);
		
		docUpl.click();
		fileToUpload(docName, "DocumentFile");
		saveButon.click();
		logger.info("Clicked on Save Button");
		Thread.sleep(250);
		
		try {
			waitForPopUp();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread.sleep(10000);
		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
		fWait.pollingEvery(10000, TimeUnit.MILLISECONDS);
		fWait.withTimeout(2, TimeUnit.SECONDS);
		
		fWait.until(ExpectedConditions.visibilityOf(globalMsg));
		
		System.out.println(globalMsg.getSize());
		System.out.println(globalMsg.getText());

		if(globalMsg.getText() == null) {
			System.out.println("globalMsg is blank");
			fWait.until(ExpectedConditions.visibilityOf(globalMsg));
		}
		
		String msg = globalMsg.getText();
		
		if(msg.equals("Lead has been created/edited successfully."))
			return true;
		else
			return false;

	}

	protected void fileToUpload(String fileName, String dataType) {
		String currentDir = System.getProperty("user.dir");
		String uploadFile = "";
		if (dataType.equals("ImageFile"))
			uploadFile = currentDir + "\\TestData\\ImageFiles\\" + fileName;
		else if (dataType.equals("DocumentFile"))
			uploadFile = currentDir + "\\TestData\\Docs\\" + fileName;

		logger.info("File to Upload is : " + uploadFile);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(uploadFile);
		clipboard.setContents(strSel, null);

		String fileUploadExec = currentDir + "\\Tools\\" + "FileUpload.exe";
		try {
			Runtime.getRuntime().exec(fileUploadExec);
		} catch (IOException e1) {	
			e1.printStackTrace();
		}

		System.out.println("Clicked on Browse...");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void waitForPopUp() throws Exception{
		int ctr = 0;
		boolean elementExist;
		String createDupe = "//button[@class='button button-default create-duplicate-lead']";
		WebElement createDupeButton = driver.findElement(By.xpath(createDupe));
		
		System.out.println("Inside waitForPopUp");
		while (true) {
			ctr++;
			try {
				if (ctr > 1000) {
					System.out.println("Wait for Element Timed Out!!!");
					System.out.println("Element XPath -> " + createDupe);
					elementExist = false;
					break;
				}
				Boolean isPresent = driver.findElements(By.xpath(createDupe)).size() > 0;
				if (isPresent) {
					System.out.println("Element Visible... " + createDupe);
					elementExist = true;
					logger.info("Going to Click on Create Duplicate... " + driver.findElements(By.xpath(createDupe)).size());
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createDupeButton);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", createDupeButton);
					System.out.println("Clicked On Create Duplicate");
//					Thread.sleep(10000);
					break;
				}
			} catch (ElementNotVisibleException ENF) {
				System.out.println(ENF);
			}
		}
	}

}
