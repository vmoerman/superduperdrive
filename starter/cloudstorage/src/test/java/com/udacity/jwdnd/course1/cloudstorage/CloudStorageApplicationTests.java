package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.parameters.P;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	private ResultPage resultPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach()
	{
		this.driver = new ChromeDriver();
		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
		homePage = new HomePage(driver);
		resultPage = new ResultPage(driver);
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
		assertEquals("Login", driver.getTitle());
	}


	@Test
	public void verifyNoAccess()
	{
		driver.get("http://localhost:" + this.port + "/home");
		assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/signup");
		assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/result");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void loginAndLogout()
	{
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup("Bert","Visser","bvisser","vissie");
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login("bvisser","vissie");
		homePage.logout();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Assertions.assertEquals("You have been logged out", driver.findElement(By.id("logout-msg")).getText());
	}

	@Test
	public void createNoteAndVerify() throws InterruptedException {
		signupAndLogin();
		createNote();
		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		homePage.switchToNotesTab(wait);
		Thread.sleep(1000);
		Assertions.assertEquals("testnote",driver.findElement(By.id("note-title-field")).getText());

	}

	@Test
	public void editNoteAndVerify() throws InterruptedException
	{
		signupAndLogin();
		createNote();
		editNote();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		homePage.switchToNotesTab(wait);
		Thread.sleep(1000);
		Assertions.assertEquals("change",driver.findElement(By.id("note-title-field")).getText());
	}

	@Test
	public void deleteNoteAndVerify() throws InterruptedException
	{
		signupAndLogin();
		createNote();
		deleteNote();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		homePage.switchToNotesTab(wait);
		Thread.sleep(1000);
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.id("note-title-field"));
		});
	}

	//	Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
	@Test
	public void createCredentialAndVerify() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		signupAndLogin();
		homePage.switchToCredentialsTab(wait);
		homePage.addCredential(wait);
		resultPage.returnToHome(wait);
		homePage.switchToCredentialsTab(wait);
		Thread.sleep(1000);
		Assertions.assertEquals("bertiee",driver.findElement(By.id("credential-url-field")).getText());
		Assertions.assertNotEquals("vissie",driver.findElement(By.id("credential-password-field")).getText());
	}

	//	Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
	@Test
	public void editCredentialAndVerify() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		signupAndLogin();
		homePage.switchToCredentialsTab(wait);
		homePage.addCredential(wait);
		resultPage.returnToHome(wait);
		homePage.switchToCredentialsTab(wait);
		Thread.sleep(1000);
		homePage.displayEditCredentialModal(wait);
		Assertions.assertEquals("vissie",driver.findElement(By.id("credential-password")).getAttribute("value"));
		homePage.editCredential(wait);
		resultPage.returnToHome(wait);
		homePage.switchToCredentialsTab(wait);
		Thread.sleep(1000);
		Assertions.assertEquals("change",driver.findElement(By.id("credential-url-field")).getText());

	}

	//	Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.

	@Test
	public void deleteCredentialAndVerify() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		signupAndLogin();
		homePage.switchToCredentialsTab(wait);
		homePage.addCredential(wait);
		resultPage.returnToHome(wait);
		homePage.switchToCredentialsTab(wait);
		Thread.sleep(1000);
		homePage.deleteCredential(wait);
		resultPage.returnToHome(wait);
		homePage.switchToCredentialsTab(wait);
		Thread.sleep(1000);
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.id("credential-url-field"));
		});
	}

	public void signupAndLogin()
	{
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.signup("Bert","Visser","bvisser","vissie");
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login("bvisser","vissie");
	}

	public void createNote() throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		homePage.switchToNotesTab(wait);
		homePage.createNote(wait);
		resultPage.returnToHome(wait);
	}

	public void editNote() throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		homePage.switchToNotesTab(wait);
		Thread.sleep(1000);
		homePage.editNote(wait);
		resultPage.returnToHome(wait);

	}

	private void deleteNote() throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		homePage.switchToNotesTab(wait);
		Thread.sleep(1000);
		homePage.deleteNote(wait);
		resultPage.returnToHome(wait);

	}



}
