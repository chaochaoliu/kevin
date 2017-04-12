

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * <a href="mailto:nathanael4ever@gmail.com">Nathanael Yang</a> 4/11/2017 5:44 PM
 */
public class GmailFetcher {

    private static WebDriver mDriver = null;

    private static final String DRIVER_FIREFOX = "firefox";
    private static final String DRIVER_CHROME = "chrome";
    private static final String DRIVER_PHANTOMJS = "phantomjs";



    protected static DesiredCapabilities sCaps;

    public static void configure() throws IOException {

        // Prepare capabilities
        sCaps = new DesiredCapabilities();
        sCaps.setJavascriptEnabled(true);
        sCaps.setCapability("takesScreenshot", false);

// 01
        // Change "User-Agent" via page-object capabilities
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", "My User Agent - Chrome");

// 02
        // Disable "web-security", enable all possible "ssl-protocols" and "ignore-ssl-errors" for PhantomJSDriver
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{
                "--web-security=false",
                "--ssl-protocol=any",
                "--ignore-ssl-errors=true",
                "--webdriver-loglevel=DEBUG"
        });
    }

    public static void prepareDriver(String driver) throws Exception {

        System.out.println("* SELECTED DRIVER: " + driver);

        // Start appropriate Driver

        if (driver.equals(DRIVER_FIREFOX)) {

            mDriver = new FirefoxDriver(sCaps);

        } else if (driver.equals(DRIVER_CHROME)) {
            File file = new File("C:\\Users\\ACCEB-4\\Downloads\\chromedriver_win32\\chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            mDriver = new ChromeDriver(sCaps);

        } else if (driver.equals(DRIVER_PHANTOMJS)) {
            //Set the path to access the phantomjs.exe file
            File src = new File("C:\\Users\\ACCEB-4\\Downloads\\phantomjs-2.1.1-windows\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
            System.setProperty("phantomjs.binary.path", src.getAbsolutePath());
            mDriver = new PhantomJSDriver(sCaps);
        }
    }


    public static void quitDriver() {
        if (mDriver != null) {
            mDriver.quit();
            mDriver = null;
        }
    }

    public static void fetchEmail(String email, String password, String driver) throws Exception {

        prepareDriver(driver);


        //Open gmail
        mDriver.get("http://www.gmail.com");

        // Enter userd id
        WebElement element = mDriver.findElement(By.id("Email"));
        element.sendKeys(email);

        //wait 2 secs for  userid to be entered
        TimeUnit.SECONDS.sleep(2);


        WebElement next_button = mDriver.findElement(By.id("next"));

        next_button.click();

        TimeUnit.SECONDS.sleep(2);

        //Enter Password
        WebElement element1 = mDriver.findElement(By.id("Passwd"));
        TimeUnit.SECONDS.sleep(3);
        element1.sendKeys(password);

        //Submit button
        WebElement signIn_button = mDriver.findElement(By.id("signIn"));

        TimeUnit.SECONDS.sleep(2);

        signIn_button.click();

        TimeUnit.SECONDS.sleep(5);

        WebElement baseTable = mDriver.findElement(By.cssSelector(".F.cf.zt"));
        List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));

        for (int i = 0; i < 10; i++) {
            System.out.println(tableRows.get(i).findElement(By.className("bog")).getText());

        }

        System.out.println("Finished");

    }

    public static void main(String[] args) throws Exception {
        configure();
        fetchEmail("0919starttowork@gmail.com", "chenguang323", "chrome");

        quitDriver();
    }

}

