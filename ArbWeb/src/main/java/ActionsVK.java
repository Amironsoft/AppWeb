import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


//All actions in browser
public class ActionsVK {
    private static WebDriver driver;
    private String parentWindow;
    private String curWindow;
    private Logger logger;
    public ActionsVK(Logger logger){
        this.logger=logger;
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        }

    public void closeDriver(){
        driver.close();
        //driver.quit();
    }

    public boolean getParentWindow(String baseUrl) throws InterruptedException{
        Thread.sleep(400);
        driver.get(baseUrl);
        parentWindow = driver.getWindowHandle();
        logger.warn("parentWindow="+parentWindow);
        return true;
    }

    public WebElement getElementByXpath(String xpathStr) throws InterruptedException{
        WebElement we=driver.findElement(By.xpath(xpathStr));
        if (we!=null) {return we;}else{
            logger.warn(xpathStr+" not found!");
        }
        return null;
    }

    public boolean getElementByXpathAndClick(String xpathStr) throws InterruptedException{
        try{
            WebElement we;
            we = driver.findElement(By.xpath(xpathStr));
            if (we!=null) {we.click();}else{
                logger.warn(xpathStr+" null!");
            }
            return true;

        }catch (Exception e){
            return false;
        }
    }

    public void switchToNewWindow(){
        for (String winHandle : driver.getWindowHandles()) {
            curWindow=winHandle;
            driver.switchTo().window(winHandle);
            // switch focus of WebDriver to the next found window handle
            //(that's your newly opened window)
        }
    }

    public void switchToParentWindow(){
        driver.switchTo().window(parentWindow);
    }

    public WebElement getElementByLinkText(String text){
        return  driver.findElement(By.linkText(text));
    }

    public boolean getElementByLinkTextAndClick(String text){

        try{
            driver.findElement(By.linkText(text)).click();
            return true;
        }catch (Exception e){
            logger.warn(text+" action failed!");
            return false;
        }

    }

    public void closeCurWindow(){
        if (!curWindow.equals(parentWindow)) {
            driver.switchTo().window(curWindow).close();
            curWindow=parentWindow;
        }
    }

    public boolean checkItWindow(){
        return (curWindow.equals(parentWindow));
    }

    public boolean checkExistingOfElement(String text){
        if (getElementByLinkText(text)!=null) return true;
        return false;
    }
}
