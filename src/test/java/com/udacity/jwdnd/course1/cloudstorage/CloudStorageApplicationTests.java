package com.udacity.jwdnd.course1.cloudstorage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	private static final String USER_USERNAME = "Hakim";
	private static final String USER_PASSWORD = "password";
	private static final String USER_FIRST_NAME = "Hakim";
	private static final String USER_LAST_NAME = "Benyoucef";

	private static final String NOTE_TITLE = "This is the title";
	private static final String NOTE_DESCRIPTION = "This is the description";

	private static final String NOTE_ALT_TITLE = "This is an updated title";
	private static final String NOTE_ALT_DESCRIPTION = "This is an updated description";

	private static final String CREDENTIAL_URL = "http://www.secret.com";
	private static final String CREDENTIAL_USERNAME = "hakim@gmail.com";
	private static final String CREDENTIAL_PASSWORD = "password";

	private static final String CREDENTIAL_ALT_URL = "http://www.confidential.com";
	private static final String CREDENTIAL_ALT_USERNAME = "benyoucef@gmail.com";
	private static final String CREDENTIAL_ALT_PASSWORD = "changedPassword";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	private static void beforeAll() {
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
	void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(1)
	void getLoginRedirection() {
		driver.get(String.format("http://localhost:%s/home", this.port));

		assertThat(driver.getTitle()).isEqualTo("Login");
	}

	@Test
	@Order(2)
	void signUp() {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Step 1: Sign up the user
		driver.get(String.format("http://localhost:%s/signup", this.port));

		assertThat(driver.getTitle()).isEqualTo("Sign Up");

		WebElement firstNameInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		firstNameInputField.sendKeys(USER_FIRST_NAME);

		WebElement lastNameNameInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		lastNameNameInputField.sendKeys(USER_LAST_NAME);

		WebElement usernameInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		usernameInputField.sendKeys(USER_USERNAME);

		WebElement passwordInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		passwordInputField.sendKeys(USER_PASSWORD);

		WebElement signUpButton = driver.findElement(By.id("signup"));
		wait.until(ExpectedConditions.elementToBeClickable(signUpButton)).submit();

		WebElement divSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-msg")));

		assertThat(divSuccess.getText().contains("You successfully signed up!"));

		// Step 2: Login the user and check if it redirects to the home page
		driver.get(String.format("http://localhost:%s/login", this.port));

		assertThat(driver.getTitle()).isEqualTo("Login");

		usernameInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		usernameInputField.sendKeys(USER_USERNAME);

		passwordInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		passwordInputField.sendKeys(USER_PASSWORD);

		WebElement loginButton = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).submit();

		WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));

		assertThat(driver.getTitle()).isEqualTo("Home");

		// Step 3: Logout the user and check it redirects to the login page
		logoutButton.submit();

		usernameInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		passwordInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));

		assertThat(driver.getTitle()).isEqualTo("Login");

		// Step 4: Check if the
		driver.get(String.format("http://localhost:%s/home", this.port));

		assertThat(driver.getTitle()).isEqualTo("Login");
	}

	@Test
	@Order(3)
	void addNote() {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Step 1: login the user
		driver.get(String.format("http://localhost:%s/login", this.port));

		assertThat(driver.getTitle()).isEqualTo("Login");

		WebElement usernameInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		usernameInputField.sendKeys(USER_USERNAME);

		WebElement passwordInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		passwordInputField.sendKeys(USER_PASSWORD);

		WebElement loginButton = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));

		assertThat(driver.getTitle()).isEqualTo("Home");

		// Step 2: Create a note
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		WebElement navNotesTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		executor.executeScript("arguments[0].click()", navNotesTab);

		WebElement addNoteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newnote")));
		addNoteButton.click();

		WebElement noteTitleInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		noteTitleInputField.clear();
		noteTitleInputField.sendKeys(NOTE_TITLE);

		WebElement noteDescriptionInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		noteDescriptionInputField.clear();
		noteDescriptionInputField.sendKeys(NOTE_DESCRIPTION);

		WebElement saveNoteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("savenote")));

		saveNoteButton.click();

		// Step 3: Check if the note is listed
		driver.get(String.format("http://localhost:%s/home", this.port));

		assertThat(driver.getTitle()).isEqualTo("Home");

		navNotesTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		executor.executeScript("arguments[0].click()", navNotesTab);

		assertThat(navNotesTab.getText().contains(NOTE_TITLE));
		assertThat(navNotesTab.getText().contains(NOTE_DESCRIPTION));
	}

	@Test
	@Order(4)
	void updateNote() {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Step 1: login the user
		driver.get(String.format("http://localhost:%s/login", this.port));

		assertThat(driver.getTitle()).isEqualTo("Login");

		WebElement usernameInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		usernameInputField.sendKeys(USER_USERNAME);

		WebElement passwordInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		passwordInputField.sendKeys(USER_PASSWORD);

		WebElement loginButton = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).submit();

		WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));

		assertThat(driver.getTitle()).isEqualTo("Home");

		// Step 2: Edit the note
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		WebElement navNotesTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		executor.executeScript("arguments[0].click()", navNotesTab);

		List<WebElement> editButtons = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("editNote")));

		if (editButtons.size() > 0) {
			editButtons.get(0).click();

			WebElement titleInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
			titleInputField.clear();
			titleInputField.sendKeys(NOTE_ALT_TITLE);

			WebElement descriptionInputField = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
			descriptionInputField.clear();
			descriptionInputField.sendKeys(NOTE_ALT_DESCRIPTION);

			WebElement saveNoteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("savenote")));

			saveNoteButton.click();

			assertThat(driver.getTitle()).isEqualTo("Result");
		}

		// Step 3: Check that the note changes are reflected.
		driver.get(String.format("http://localhost:%s/home", this.port));

		assertThat(driver.getTitle()).isEqualTo("Home");

		navNotesTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		executor.executeScript("arguments[0].click()", navNotesTab);

		assertThat(navNotesTab.getText().contains(NOTE_ALT_TITLE));
		assertThat(navNotesTab.getText().contains(NOTE_ALT_DESCRIPTION));
	}

	@Test
	@Order(5)
	void delteNote() {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Step 1: login the user
		driver.get(String.format("http://localhost:%s/login", this.port));

		assertThat(driver.getTitle()).isEqualTo("Login");

		WebElement usernameInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		usernameInputField.sendKeys(USER_USERNAME);

		WebElement passwordInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		passwordInputField.sendKeys(USER_PASSWORD);

		WebElement loginButton = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).submit();

		WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));

		assertThat(driver.getTitle()).isEqualTo("Home");

		// Step 2: Delete the note
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		WebElement navNotesTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		executor.executeScript("arguments[0].click()", navNotesTab);

		List<WebElement> deleteButtons = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("deleteNote")));

		if (deleteButtons.size() > 0) {
			deleteButtons.get(0).click();

			assertThat(driver.getTitle()).isEqualTo("Result");
		}

		// Step 3: Check that the note is no longer listed.
		driver.get(String.format("http://localhost:%s/home", this.port));

		assertThat(driver.getTitle()).isEqualTo("Home");

		navNotesTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		executor.executeScript("arguments[0].click()", navNotesTab);

		assertThat(navNotesTab.getText().contains(NOTE_ALT_TITLE)).isFalse();
		assertThat(navNotesTab.getText().contains(NOTE_ALT_DESCRIPTION)).isFalse();
	}

	@Test
	@Order(6)
	void addCredentials() {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Step 1: login the user
		driver.get(String.format("http://localhost:%s/login", this.port));

		assertThat(driver.getTitle()).isEqualTo("Login");

		WebElement usernameInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		usernameInputField.sendKeys(USER_USERNAME);

		WebElement passwordInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		passwordInputField.sendKeys(USER_PASSWORD);

		WebElement loginButton = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).submit();

		WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));

		assertThat(driver.getTitle()).isEqualTo("Home");

		// Step 2: Create the credential
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		WebElement navCredentialsTab = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		executor.executeScript("arguments[0].click()", navCredentialsTab);

		WebElement addCredentialButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCred")));
		addCredentialButton.click();

		WebElement credentialUrlInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		credentialUrlInputField.clear();
		credentialUrlInputField.sendKeys(CREDENTIAL_URL);

		WebElement credentialUsernameInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		credentialUsernameInputField.clear();
		credentialUsernameInputField.sendKeys(CREDENTIAL_USERNAME);

		WebElement credentialPasswordInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		credentialPasswordInputField.clear();
		credentialPasswordInputField.sendKeys(CREDENTIAL_PASSWORD);

		WebElement saveCredentialButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveCred")));

		saveCredentialButton.click();

		assertThat(driver.getTitle()).isEqualTo("Result");

		// Step 3: Check if the note is listed
		driver.get(String.format("http://localhost:%s/home", this.port));

		assertThat(driver.getTitle()).isEqualTo("Home");

		navCredentialsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		executor.executeScript("arguments[0].click()", navCredentialsTab);

		assertThat(navCredentialsTab.getText().contains(CREDENTIAL_URL));
		assertThat(navCredentialsTab.getText().contains(CREDENTIAL_USERNAME));
	}

	@Test
	@Order(7)
	void updateCredentials() {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Step 1: login the user
		driver.get(String.format("http://localhost:%s/login", this.port));

		assertThat(driver.getTitle()).isEqualTo("Login");

		WebElement usernameInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		usernameInputField.sendKeys(USER_USERNAME);

		WebElement passwordInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		passwordInputField.sendKeys(USER_PASSWORD);

		WebElement loginButton = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).submit();

		WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));

		assertThat(driver.getTitle()).isEqualTo("Home");

		// Step 2: Edit the credential
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		WebElement navCredentialsTab = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		executor.executeScript("arguments[0].click()", navCredentialsTab);

		List<WebElement> editButtons = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("addCred")));

		if (editButtons.size() > 0) {
			editButtons.get(0).click();

			WebElement credentialUrlInputField = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
			credentialUrlInputField.clear();
			credentialUrlInputField.sendKeys(CREDENTIAL_ALT_URL);

			WebElement credentialUsernameInputField = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
			credentialUsernameInputField.clear();
			credentialUsernameInputField.sendKeys(CREDENTIAL_ALT_USERNAME);

			WebElement credentialPasswordInputField = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
			credentialPasswordInputField.clear();
			credentialPasswordInputField.sendKeys(CREDENTIAL_ALT_PASSWORD);

			WebElement saveCredentialButton = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveCred")));

			saveCredentialButton.click();

			assertThat(driver.getTitle()).isEqualTo("Result");
		}

		// Step 3: Check that credential changes are reflected
		driver.get(String.format("http://localhost:%s/home", this.port));

		assertThat(driver.getTitle()).isEqualTo("Home");

		navCredentialsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		executor.executeScript("arguments[0].click()", navCredentialsTab);

		assertThat(navCredentialsTab.getText().contains(CREDENTIAL_URL));
		assertThat(navCredentialsTab.getText().contains(CREDENTIAL_USERNAME));
	}

	@Test
	@Order(8)
	void deleteCredentials() {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Step 1: login the user
		driver.get(String.format("http://localhost:%s/login", this.port));

		assertThat(driver.getTitle()).isEqualTo("Login");

		WebElement usernameInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		usernameInputField.sendKeys(USER_USERNAME);

		WebElement passwordInputField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		passwordInputField.sendKeys(USER_PASSWORD);

		WebElement loginButton = driver.findElement(By.id("login"));
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).submit();

		WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));

		assertThat(driver.getTitle()).isEqualTo("Home");

		// Step 2: Delete the credential
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		WebElement navCredentialsTab = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		executor.executeScript("arguments[0].click()", navCredentialsTab);

		List<WebElement> deleteButtons = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("deleteCred")));

		if (deleteButtons.size() > 0) {
			deleteButtons.get(0).click();

			assertThat(driver.getTitle()).isEqualTo("Result");
		}

		// Step 3: Check that the credential is no longer listed.
		driver.get(String.format("http://localhost:%s/home", this.port));

		assertThat(driver.getTitle()).isEqualTo("Home");

		navCredentialsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		executor.executeScript("arguments[0].click()", navCredentialsTab);

		assertThat(navCredentialsTab.getText().contains(CREDENTIAL_ALT_URL)).isFalse();
		assertThat(navCredentialsTab.getText().contains(CREDENTIAL_ALT_USERNAME)).isFalse();
	}
}
