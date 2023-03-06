package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
		// click login url
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-href")));
		WebElement loginHref = driver.findElement(By.id("login-href"));
		loginHref.click();
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
//		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	/*1. Write Tests for User Signup, Login, and Unauthorized Access Restrictions.*/
	@Test
	public void unauthorizedUserAccess(){
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
		WebElement btnLogout = driver.findElement(By.id("logout"));
		btnLogout.click();

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/*2. Write Tests for Note Creation, Viewing, Editing, and Deletion.*/
	@Test
	public void testNoteManagement(){
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		doLogIn("UT", "123");

		/*Note Creation*/
		WebDriverWait webDriverWait = new WebDriverWait(driver, 3);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewNoteBtnModal")));
		WebElement btnModal = driver.findElement(By.id("addNewNoteBtnModal"));
		btnModal.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys("NOTE TITLE TEST");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.sendKeys("NOTE DESCRIPTION TEST");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveNote")));
		WebElement btnSaveModal = driver.findElement(By.id("saveNote"));
		btnSaveModal.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),\"NOTE TITLE TEST\")]")));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),\"NOTE DESCRIPTION TEST\")]")));
		List<WebElement> checkNoteTitle= driver.findElements(By.xpath("//*[contains(text(),\"NOTE TITLE TEST\")]"));
		List<WebElement> checkNoteDes= driver.findElements(By.xpath("//*[contains(text(),\"NOTE DESCRIPTION TEST\")]"));

		Assertions.assertEquals(true, checkNoteTitle.size() > 0 && checkNoteTitle != null);
		Assertions.assertEquals(true, checkNoteDes.size() > 0 && checkNoteDes != null);

		List<WebElement> editBtn= driver.findElements(By.xpath("//*[contains(text(),\"Edit\")]"));
		if (editBtn.size() > 0 && editBtn != null) {
			editBtn.get(editBtn.size() - 3).click();
		}

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		noteTitle.clear();
		noteTitle.sendKeys("EDIT NOTE TITLE TEST");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		noteDescription.clear();
		noteDescription.sendKeys("EDIT NOTE DESCRIPTION TEST");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveNote")));
		btnSaveModal.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),\"EDIT NOTE TITLE TEST\")]")));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),\"EDIT NOTE DESCRIPTION TEST\")]")));
		List<WebElement> checkEditNoteTitle = driver.findElements(By.xpath("//*[contains(text(),\"EDIT NOTE TITLE TEST\")]"));
		List<WebElement> checkEditNoteDes= driver.findElements(By.xpath("//*[contains(text(),\"EDIT NOTE DESCRIPTION TEST\")]"));

		Assertions.assertEquals(true, checkEditNoteTitle.size() > 0 && checkEditNoteTitle != null);
		Assertions.assertEquals(true, checkEditNoteDes.size() > 0 && checkEditNoteDes != null);

		List<WebElement> deleteBtn= driver.findElements(By.xpath("//*[contains(text(),\"Delete\")]"));
		if (deleteBtn.size() > 0 && deleteBtn != null) {
			deleteBtn.get(deleteBtn.size() - 3).click();
		}

		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		checkEditNoteTitle = driver.findElements(By.xpath("//*[contains(text(),\"EDIT NOTE TITLE TEST\")]"));
		checkEditNoteDes= driver.findElements(By.xpath("//*[contains(text(),\"EDIT NOTE DESCRIPTION TEST\")]"));
		Assertions.assertEquals(false, checkEditNoteTitle.size() > 0 && checkEditNoteTitle != null);
		Assertions.assertEquals(false, checkEditNoteDes.size() > 0 && checkEditNoteDes != null);
	}

	public void createSetOfCredentials(String url, String username, String password){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 3);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement tabCredential = driver.findElement(By.id("nav-credentials-tab"));
		tabCredential.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewCredentialButton")));
		WebElement addNewCredentialButton = driver.findElement(By.id("addNewCredentialButton"));
		addNewCredentialButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement urlElement = driver.findElement(By.id("credential-url"));
		urlElement.sendKeys(url);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement usernameElement = driver.findElement(By.id("credential-username"));
		usernameElement.sendKeys(username);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement passwordElement = driver.findElement(By.id("credential-password"));
		passwordElement.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveCredential")));
		WebElement saveCredential = driver.findElement(By.id("saveCredential"));
		saveCredential.click();
	}
}
