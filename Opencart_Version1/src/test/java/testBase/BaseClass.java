package testBase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	
	public Logger logger; //for logging
	
	public ResourceBundle rb;

	@Parameters("browser")
	@BeforeClass
	public void setup(String br)
	{
		logger = LogManager.getLogger(this.getClass()); 
		rb = ResourceBundle.getBundle("config"); // load config.properties files
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches",new String[] {"enable-automation"});
		options.addArguments("--remote-allow-origins=*");
		
		if(br.equals("chrome"))
		{
			driver = new ChromeDriver(options); 
		}
		if(br.equals("edge"))
		{
			driver = new EdgeDriver(); 
		}
		if(br.equals("firefox"))
		{
			driver = new FirefoxDriver(); 
		}
		
		System.setProperty("webdriver.chrome.driver","G:\\My Drive\\HP Pavillion Backup\\Documents\\Notes-VCT\\chromedriver_win32_110\\chromedriver.exe");
		
		
		//driver=new ChromeDriver(options);
		
		driver.manage().deleteAllCookies();
	//	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(rb.getString("appURL"));
		//driver.get("https://demo.opencart.com/index.php");
		
		driver.manage().window().maximize();
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}
	
	public String randomeString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return (generatedString);
	}

	public String randomeNumber() {
		String generatedString2 = RandomStringUtils.randomNumeric(10);
		return (generatedString2);
	}
	
	public String randomAlphaNumeric() {
		String st = RandomStringUtils.randomAlphabetic(4);
		String num = RandomStringUtils.randomNumeric(3);
		
		return (st+"@"+num);
	}

	public String captureScreen(String tname) {
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());
		
		TakesScreenshot ts=(TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir")+"\\Screensc\\"+tname+"_"+ts+".png";
        

        try 
        {
            FileUtils.copyFile(source, new File(destination));
        } catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return destination;	
	}
}
