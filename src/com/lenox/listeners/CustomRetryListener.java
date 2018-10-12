package com.lenox.listeners;

/*
 * @author	:	abhilash.tc@gmail.com
 * 
 * This class deals with the Retrying the Failed Tests, the retry count can be set here.
 * The retry class need to me implemented in the tests
 * For Example... @Test (priority=1, enabled=true, retryAnalyzer=com.lenox.listeners.CustomRetryListener.class)
*/

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.Test;

public class CustomRetryListener implements IRetryAnalyzer{
	private int retryCount=1;
	private final int maxRetryCount=3;

	@Override
	public boolean retry(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE && retryCount <= maxRetryCount) {
			try {
				Thread.sleep(300);
//				System.out.println("Method Name : %s, Retry Count : %d", result.getMethod(), retryCount);
//				System.out.println("Method Name : " + result.getMethod() + ", Retry Count : ", + retryCount);
				System.out.println("Method Name : " + result.getMethod());
				System.out.println(" Retry Count : " + retryCount);
				retryCount++;
				return true;
			}
			catch (Exception e) {
				System.out.println("Exception " + e);
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

}
