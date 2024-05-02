package com.joaoScaraficci.automationTestWithSelenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.FileCopyUtils;
import resource.Database;
import resource.PageTest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class AutomationTestWithSeleniumApplicationTests {
	static WebDriver driver;

	private void currentLinkToTest(String linkText) {

		WebElement link = driver.findElement(By.partialLinkText(linkText));
        link.click();
	}

	private static void screenshot() {
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
			LocalDateTime time = LocalDateTime.now();
			String formatTime = String.valueOf(time).replace(':', '.');

			FileCopyUtils.copy(screenshot, new File("evidences/Evidence - " + formatTime + ".png"));
        }
		catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	@BeforeEach
	public void initialise() {
		driver = new ChromeDriver();
		driver.get(Database.webpage);
		driver.manage().window().maximize();
		screenshot();
	}

	@Test
	void addRemoveElements() {
		currentLinkToTest("Add/Remove Elements");

		WebElement addButton = driver.findElement(By.xpath(PageTest.addElementButton));
		Assertions.assertTrue(addButton.isDisplayed());
		addButton.click();
		screenshot();

		WebElement deleteButton = driver.findElement(By.xpath(PageTest.deleteButton));
		Assertions.assertTrue(deleteButton.isDisplayed());
		deleteButton.click();
		screenshot();
	}

	@Test
	void brokenImages() {
		currentLinkToTest("Broken Images");
		screenshot();

		List<WebElement> allImages = driver.findElements(By.xpath(PageTest.brokenImagesList));

		for(WebElement element: allImages) {
			driver.navigate().to(element.getAttribute("src"));
			screenshot();
			driver.navigate().back();
		}
	}

	@Test
	void dropdown() {
		currentLinkToTest("Dropdown");
		String expected = "Option 1";

		Select select = new Select(driver.findElement(By.xpath(PageTest.dropdown)));
		select.selectByValue("1");

		Assertions.assertEquals(expected, driver.findElement(By.xpath(PageTest.selectedOption)).getText());
		screenshot();
	}

	@Test
	void checkbox() {
		currentLinkToTest("Checkboxes");

		WebElement checkbox = driver.findElement(By.xpath(PageTest.checkbox1));
		checkbox.click();

		Assertions.assertTrue(checkbox.isSelected());
		screenshot();
	}

	@Test
	void invalidUsernameAndPassword() {
		currentLinkToTest("Form Authentication");
		String expected = "Your username is invalid!\n×";

		WebElement username = driver.findElement(By.xpath(PageTest.username));
		username.sendKeys(Database.wrongUsernameLoginPage);
		screenshot();

		WebElement password = driver.findElement(By.xpath(PageTest.password));
		password.sendKeys(Database.wrongPasswordLoginPage);
		screenshot();

		WebElement loginButton = driver.findElement(By.xpath(PageTest.loginButton));
		loginButton.click();

		Assertions.assertEquals(expected, driver.findElement(By.xpath(PageTest.errorLogin)).getText());
		screenshot();
	}

	@Test
	void validUsernameAndPassword() {
		currentLinkToTest("Form Authentication");
		String expected = "You logged into a secure area!\n×";

		WebElement username = driver.findElement(By.xpath(PageTest.username));
		username.sendKeys(Database.rightUsernameLoginPage);
		screenshot();

		WebElement password = driver.findElement(By.xpath(PageTest.password));
		password.sendKeys(Database.rightPasswordLoginPage);
		screenshot();

		WebElement loginButton = driver.findElement(By.xpath(PageTest.loginButton));
		loginButton.click();

		Assertions.assertEquals(expected, driver.findElement(By.xpath(PageTest.errorLogin)).getText());
		screenshot();
	}

	@AfterEach
	public void end() {
		driver.quit();
	}
}
