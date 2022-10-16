package org.ilonarumak.tests.testComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.ilonarumak.pageobjects.LoginPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public Properties userDataProperties;
    public Properties globalDataProperties;
    public LoginPage loginPage;

    public WebDriver initializeDriver() throws IOException {
        userDataProperties = new Properties();
        userDataProperties.load(new FileInputStream("src/main/java/org/ilonarumak/resources/userdata.properties"));
        globalDataProperties = new Properties();
        globalDataProperties.load(new FileInputStream("src/main/java/org/ilonarumak/resources/globalData.properties"));
        String browser = System.getProperty("browser") !=null ? System.getProperty("browser") : globalDataProperties.getProperty("browser");
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    public void goTo(String url) {
        driver.get(url);
    }

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        // convert json to string
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        // convert string to hashmap
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
        File file = new File(filePath);
        FileUtils.copyFile(src, file);
        return filePath;
    }

    @BeforeMethod(alwaysRun = true)
    public void launchApplication() throws IOException {
        driver = initializeDriver();
        goTo(globalDataProperties.getProperty("url"));
        loginPage = new LoginPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void shutDown() {
        driver.close();
    }
}
