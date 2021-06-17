package selenium;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.mail.*;
import javax.mail.util.*;
import javax.mail.internet.*;
import javax.activation.*;


public class script {
	public static WebDriver driver;
	public static ArrayList<String> openCities = new ArrayList<String>();
	
	public static void main(String[] args) throws MessagingException {
		run();
	}
	
    
	public static void run() throws MessagingException {
		System.setProperty("webdriver.chrome.driver","lib\\chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
	    driver = new ChromeDriver(chromeOptions);		
	    chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        try {
        driver.get("https://onlineservices.dps.mn.gov/EServices/_/");
        WebElement element = driver.findElement(By.id("l_Df-9-14"));
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 13000);
        wait.until(ExpectedConditions.stalenessOf(element));
        By id = By.className("FastComboButtonLabelText");
        wait.until(ExpectedConditions.presenceOfElementLocated(id)).click(); 
        WebElement permitNumber = driver.findElement(By.id("Dc-9"));
        WebElement dateOfBirth = driver.findElement(By.id("Dc-a"));
        permitNumber.sendKeys("license_id");
        dateOfBirth.sendKeys("DD-mmm-YYYY");
        WebElement nextButton = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div/main/div/div/div[1]/div[2]/form/div[1]/div/div[3]/button[2]"));
        		
        //WebElement nextButton = driver.findElement(By.id("Dc-__NextStep"));
        nextButton.click();
        wait.until(ExpectedConditions.stalenessOf(nextButton));
        
        By reschedID = By.id("cl_Dc-b1");
        wait.until(ExpectedConditions.presenceOfElementLocated(reschedID)).click();  
        By yesButton = By.xpath("/html/body/div[7]/div[3]/div/button[2]");
        wait.until(ExpectedConditions.presenceOfElementLocated(yesButton)).click();
        
        By Eagan = By.xpath("/html/body/div[1]/div[2]/div[1]/div/div/main/div/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[2]/td/div/div[1]/table/tbody/tr[2]/td[1]/div/div/a/span");
        wait.until(ExpectedConditions.presenceOfElementLocated(Eagan));
        element = driver.findElement(Eagan);
        String eaganStatus = findLoc(element,"/html/body/div[1]/div[2]/div[1]/div/div/main/div/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[2]/td/div/div[1]/table/tbody/tr[3]/td[2]/div[1]/div/span/span[2]");
        if(!eaganStatus.contains("no")) {
        	openCities.add("Eagan");
        }
        //Location: Chaska
        By Chaska = By.id("caption2_Dc-j2-1");
        wait.until(ExpectedConditions.stalenessOf(element));
        element = driver.findElement(Chaska);
        String chaskaStatus = findLoc(element,"/html/body/div[1]/div[2]/div[1]/div/div/main/div/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[1]/td/div/div[1]/table/tbody/tr[3]/td[2]/div[2]/div/span/span[2]");
        if(!chaskaStatus.contains("no")) {
        	openCities.add("Chaska");
        }
        By Hastings = By.xpath("/html/body/div[1]/div[2]/div[1]/div/div/main/div/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[3]/td/div/div[1]/table/tbody/tr[2]/td[1]/div/div/a/span");
        wait.until(ExpectedConditions.presenceOfElementLocated(Hastings));
        element = driver.findElement(Hastings);
        String hastingsStatus = findLoc(element,"/html/body/div[1]/div[2]/div[1]/div/div/main/div/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[3]/td/div/div[1]/table/tbody/tr[3]/td[2]/div[2]/div/span/span[2]");
        if(!hastingsStatus.contains("no")) {	
        	openCities.add("Hastings");
        }
        
        By Plymouth = By.xpath("/html/body/div[1]/div[2]/div[1]/div/div/main/div/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[4]/td/div/div[1]/table/tbody/tr[2]/td[1]/div/div/a/span");
        wait.until(ExpectedConditions.presenceOfElementLocated(Plymouth));
        element = driver.findElement(Plymouth);
        String plymouthStatus = findLoc(element,"/html/body/div[1]/div[2]/div[1]/div/div/main/div/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[4]/td/div/div[1]/table/tbody/tr[3]/td[2]/div[1]/div/span/span[2]");
        if(!plymouthStatus.contains("no")) {	
        	openCities.add("Plymouth");
        }
        
        if(openCities.size() > 0) {
        	sendEmail();
        }
        
        
        
        
        }
        finally {
        	driver.quit();
        }

	}
	
	public static String findLoc(WebElement city, String text) {
		String status = "";
		try {
		city.click(); 
		WebElement statusText = driver.findElement(By.xpath(text));
		status = statusText.getAttribute("innerText");
		}
		catch(Exception e){
			WebElement statusText = driver.findElement(By.xpath(text));
			status = statusText.getAttribute("innerText");
		}
		return status;

		
	}
	
	public static void sendEmail() throws MessagingException {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.port","587");
		
		String myAccountEmail = "your_email_id";
		String password = "your_password";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});
		
		Message message = prepareMessage(session, myAccountEmail, "recipient_email");
		
		Transport.send(message);
	}
		
		public static Message prepareMessage(Session session, String myAccountEmail, String recipient) throws MessagingException{
			String emailContent = "";
			for(int i = 0; i < openCities.size(); i++) {
				emailContent += openCities.get(i) + " has an available appointment. ";
			}
			Message m = null;
			try {
			m = new MimeMessage(session);
			m.setFrom(new InternetAddress(myAccountEmail));
			m.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			m.setSubject("Found Road Test Appointment");
			m.setText(emailContent);
			return m;
			} catch (AddressException ex) {
				Logger.getLogger(script.class.getName()).log(Level.SEVERE, null, ex);
			
			}
			return null;
		
		
	   
	}
		
	
}
