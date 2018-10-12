package com.lenox.utilities;

/*
 * @author	:	abhilash.tc@gmail.com
 * 
 * This class has some general utilies
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GeneralUtilities {
	public static WebDriver openWebPage(WebDriver driver, String URL){
		driver.get(URL);
		System.out.println("Opened the WebPage " + URL);
		return driver;
	}
	
	
	public static String getValueOf(String fileName, String Key) throws IOException{
		System.out.println("<*** Inside getValueOf ****>");
		System.out.println("File Name :" + fileName + ", Key : " + Key);
//		FileReader file = new FileReader("./src/Configuration/" + fileName);
		FileReader file = new FileReader(fileName);
		Properties prop = new Properties();
		prop.load(file);
		String keyValue = prop.getProperty(Key);
		file.close();
		System.out.println(keyValue);
		
		return keyValue;
	}
	
	public static String getValueOfConfigProp(String fileName, String Key) throws IOException{
		System.out.println("<*** Inside getValueOf ****>");
		System.out.println("File Name :" + fileName + ", Key : " + Key);
		String currentDir = System.getProperty("user.dir");
		String configDir = currentDir + "/Configuration";
		FileReader file = new FileReader(configDir + "/" + fileName);
//		FileReader file = new FileReader(fileName);
		Properties prop = new Properties();
		prop.load(file);
		String keyValue = prop.getProperty(Key);
		file.close();
		System.out.println(keyValue);
		return keyValue;
	}
	
	
	

	public static String[][] ReadExcel(String fileName) throws Exception
	{	
		System.out.println("Inside readExcel");
		String sArray1[][] = null;
		
		String currentDir = System.getProperty("user.dir");
		String inputFile = currentDir + "\\TestData\\" + fileName;
		System.out.println("Test Data Excel File :" + inputFile );
		
		
		
		File f = new File(inputFile);
		if (f.isFile() && f.canRead()) 
		{
			System.out.println("File Exists - " + inputFile);
			FileInputStream fis = new FileInputStream(inputFile);
			Workbook wb = WorkbookFactory.create(fis);
			
//			System.out.println(wb.getAllNames());
			
			System.out.println("----------------------------------");
			Sheet s = wb.getSheet("User Details");
			int rCount, cellCount;
			rCount = s.getLastRowNum();
			
			String sArray[][]=new String[rCount][4];
			
			DataFormatter dataFormatter = new DataFormatter();
			
			for(int rNum=1; rNum<=rCount;rNum++)
			{
				Row r = s.getRow(rNum);
				int c = r.getLastCellNum();
				
				for(int cNum=0;cNum<c;cNum++)
				{
					Cell cellData = r.getCell(cNum);
					sArray[rNum-1][cNum] = dataFormatter.formatCellValue(cellData);
				}
			}
			return(sArray);
		}
		else
			System.out.println("File don't exist..");
		return sArray1;
		
		
	}
	
	public static String tokenizeTime(String timeStamp) throws Exception{
		System.out.println("Inside tokenizeTime " + timeStamp);
		String year = timeStamp.substring(0, 4);
		String month = timeStamp.substring(4, 6);
		String day = timeStamp.substring(6, 8);
		
		String tIME = timeStamp.substring(9, 15);
		String time1 = tIME.substring(0, 2) + "-" + tIME.substring(2, 4) + "-" + tIME.substring(4, 6);
		System.out.println(month + "~" + day + "~" + year + "_" + time1);
		
		return(month + "~" + day + "~" + year + "_" + time1);
	}
	
	public static void highlightElement(WebDriver driver, WebElement element){
		System.out.println("Size of WebElement is : " + element.getSize());
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
	}

}


