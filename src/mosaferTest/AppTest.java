package mosaferTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
//import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class AppTest extends TestData {

	WebDriver driver = new ChromeDriver();

	@BeforeTest
	public void mySetup() {
		driver.get("https://www.almosafer.com/en");
		driver.manage().window().maximize();

	}

	@Test(priority = 1, enabled = true)
	public void CheckTheDefaultLanguageIsEnglish() {
		String ActualLang = driver.findElement(By.tagName("html")).getDomAttribute("lang");
		Assert.assertEquals(ActualLang, ExpectedLang);
	}

	@Test(priority = 2, enabled = true)
	public void CheckTheCurrencyIsSAR() {
		WebElement Currency = driver.findElement(By.cssSelector(".sc-hUfwpO.kAhsZG"));
		String ActualCurrency = Currency.getText();
		Assert.assertEquals(ActualCurrency, ExpectedCurrency);
		System.out.println(ActualCurrency);

	}

	@Test(priority = 3, enabled = true)
	public void CheckTheContactNum() {
		WebElement contactNum = driver.findElement(By.cssSelector(".sc-cjHlYL.gdvIKd"));
		String ActualContactNum = contactNum.getText();
		Assert.assertEquals(ActualContactNum, ExpectedNum);
	}

	@Test(priority = 4, enabled = true)
	public void navigateToSite() throws InterruptedException {
		driver.get("https://www.almosafer.com/en");

		Thread.sleep(3000);

		try {
			WebElement saudiButton = driver.findElement(By.xpath("//button[contains(.,'Kingdom of Saudi Arabia')]"));
			if (saudiButton.isDisplayed()) {
				saudiButton.click();
			}
		} catch (NoSuchElementException e) {
			System.out.println("Country selection popup not found. Continuing...");
		}

		// إشعار المتصفح
		// driver.switchTo().alert().dismiss();
	}

	@Test(priority = 5, enabled = true)
	public void verifyQitafLogoIsDisplayed() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement qitafLogo = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sc-bdVaJa.bxRSiR.sc-lcpuFF.jipXfR")));

		Assert.assertTrue(qitafLogo.isDisplayed(), "Qitaf logo is not displayed in the footer.");

		// Scroll back
		js.executeScript("window.scrollTo(0, 0);");
	}

	@Test(priority = 6, enabled = true)
	public void HotelTab() {
		WebElement HotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		String ActualValue = HotelTab.getDomAttribute("aria-selected");
		Assert.assertEquals(ActualValue, ExpectedValue);
		System.out.println(ActualValue);
	}

	@Test(priority = 7, enabled = true)
	public void DepartureFlightDate() {
		String ActualDepature = driver.findElement(By.xpath("//div[@data-testid='FlightSearchBox__FromDateButton']"))
				.getText();
		Assert.assertTrue(ActualDepature.contains(ExpectedDeparture));
		System.out.println("Departure text: " + ActualDepature);
	}

	@Test(priority = 8, enabled = true)
	public void ReturnFlightDate() {
		String ActualReturn = driver.findElement(By.xpath("//div[@data-testid='FlightSearchBox__ToDateButton']"))
				.getText();
		Assert.assertTrue(ActualReturn.contains(ExpectedReturn));
		System.out.println("Return text: " + ActualReturn);
	}

	@Test(priority = 9, enabled = true)
	public void randomLanguageSwitch() {
		Random random = new Random();
		boolean changeLang = random.nextBoolean(); // true: change, false: keep

		String currentLang = driver.findElement(By.tagName("html")).getDomAttribute("lang");
		if (changeLang) {
			WebElement langSwitch = driver.findElement(By.cssSelector("[data-testid='Header__LanguageSwitch']"));
			langSwitch.click();
		}

		String newLang = driver.findElement(By.tagName("html")).getDomAttribute("lang");
		assert changeLang ? !newLang.equals(currentLang) : newLang.equals(currentLang);
	}

	@Test(priority = 10, enabled = true)
	public void switchToHotelTabAndSearch() throws InterruptedException {
		driver.findElement(By.id("uncontrolled-tab-example-tab-hotels")).click();

		Thread.sleep(1000);

		String lang = driver.findElement(By.tagName("html")).getDomAttribute("lang");
		WebElement locationInput = driver.findElement(By.cssSelector("input[data-testid='AutoCompleteInput']"));

		String[] enCities = { "Dubai", "Jeddah", "Riyadh" };
		String[] arCities = { "دبي", "جدّة", "الرياض" };

		Random rand = new Random();
		String city = lang.equals("en") ? enCities[rand.nextInt(3)] : arCities[rand.nextInt(3)];

		locationInput.clear();
		locationInput.sendKeys(city);
		Thread.sleep(1000);

		locationInput.sendKeys(Keys.ARROW_DOWN);
		locationInput.sendKeys(Keys.ENTER);
	}

	@Test(priority = 11, enabled = true)

	public void selectRoomDetails() throws InterruptedException {

		WebElement dropdownElement = driver.findElement(By.cssSelector(".sc-tln3e3-1.gvrkTi"));

		Select dropdown = new Select(dropdownElement);

		List<WebElement> options = dropdown.getOptions();

		Random random = new Random();
		int randomIndex = random.nextInt(options.size() - 1) + 1;

		dropdown.selectByIndex(randomIndex);

		System.out.println("Selected Option: " + options.get(randomIndex).getText());
	}

	@Test(priority = 12, enabled = true)
	public void clickSearchHotels() {
		driver.findElement(By.cssSelector("[data-testid='HotelSearchBox__SearchButton']")).click();
	}

	@Test(priority = 13, enabled = true)
	public void waitForSearchResults() {
		String resultText = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid='srp_properties_found']")))
				.getText();
		Assert.assertTrue(resultText.contains("found") || resultText.contains("مكان"));
	}

}