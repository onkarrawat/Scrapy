package uistepdefinitions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utils.LoggerFile;

public class DemoBlazeStepDef {
	Integer OrderId;
	Double Amount, AmountForSonyvaio;
	WebDriver driver = null;
	Logger logger = LoggerFile.logConfig(DemoBlazeStepDef.class.getName());

	@Given("launch browser and url")
	public void launch_browser_and_url() throws Throwable {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator+"\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.demoblaze.com/index.html");
		Thread.sleep(5000);
		logger.info("Browser and url launched successfully");

	}

	@When("^custumer navigate to Laptop to select \"(.*)\"$")
	public void customer_navigate_to_laptop_to_select(String LaptopModel) throws Throwable {

		driver.findElement(By.xpath("//div[@class='list-group']//a[text()='Laptops']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@class='hrefch' and text()='" + LaptopModel + "']")).click();
		logger.info("Laptop model clicked successfully");
		
	}

	@When("^customer adds item to cart$")
	public void customer_adds_item_to_cart() throws Throwable {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@class='btn btn-success btn-lg' and text()='Add to cart']")).click();
		Thread.sleep(2000);
		Alert al = driver.switchTo().alert();
		// click on OK to accept with accept()
		al.accept();
		logger.info("item added to cart successfully");
	}

	@When("^customer navigates to \"(.*)\" page$")
	public void customer_navigates_to_Home_page(String PageNavigate) throws Throwable {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@class='nav-link' and contains(text(),'" + PageNavigate + "')]")).click();
		Thread.sleep(2000);
		logger.info("Clicked on " + PageNavigate);
	}

	@When("^customer deletes item \"(.*)\" from cart$")
	public void customer_deletes_item_from_cart(String laptopModel) throws Throwable {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//td[text()='" + laptopModel + "']//parent::tr//a[text()='Delete']")).click();
		Thread.sleep(6000);
		logger.info("item " + laptopModel + " deleted from cart successfully");
		if (laptopModel.equals("Sony vaio i5")==false) {
			AmountForSonyvaio = Double.valueOf((driver
					.findElement(By.xpath("(//td[text()='Sony vaio i5']/following-sibling::td)[1]")).getText().trim()));
			logger.info("AmountForSonyvaio " + AmountForSonyvaio);
		}
	}

	

	@When("^customer clicks on place Order button$")
	public void customer_clicks_on_Place_Order_button() throws Throwable {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@class='btn btn-success']")).click();
		Thread.sleep(2000);
		logger.info("Clicked on Place Order button ");

	}

	@When("^customer fills the purchase form$")
	public void customer_fills_the_order_form() throws Throwable {
		Thread.sleep(2000);
		driver.findElement(By.id("name")).sendKeys("Onkar");
		driver.findElement(By.id("country")).sendKeys("India");
		driver.findElement(By.id("city")).sendKeys("Delhi");
		driver.findElement(By.id("card")).sendKeys("1111111111111111");
		driver.findElement(By.id("month")).sendKeys("October");
		driver.findElement(By.id("year")).sendKeys("2021");
		driver.findElement(By.xpath("//button[@class='btn btn-primary' and text()='Purchase']")).click();
		Thread.sleep(2000);
		logger.info("clicked on Purchase button");
		Thread.sleep(2000);
		String OrderInfo = driver.findElement(By.xpath("(//p[contains(@class,'lead text-muted')])")).getText().trim();
		logger.info("OrderInfo " + OrderInfo);

		List<String> arlist = new ArrayList<String>();
		arlist = Arrays.asList(OrderInfo.split(":"));
		OrderId = Integer.valueOf(arlist.get(1).replace("Amount", "").trim());
		Amount = Double.valueOf(arlist.get(2).replace("Card Number", "").replace("USD", "").trim());
		logger.info("OrderId " + OrderId.intValue());
		logger.info("Amount " + Amount.doubleValue());
		
		driver.findElement(By.xpath("//button[@class='confirm btn btn-lg btn-primary']")).click();
		logger.info("successfully clicked on OK button");

	}
	
	@Then("^assert expected price matches with the actual price of the item$")
	public void Assert_ExpectedPrice_matches_with_actual_price_of_item() throws Throwable {
		Assert.assertEquals(AmountForSonyvaio, Amount);
	}
	
	@When("close the browser")
	public void close_the_browser() throws Throwable {
		driver.quit();
	}
}
