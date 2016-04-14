import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class ArbWeb {
    private static boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();
    static final Logger logger = LogManager.getLogger(ArbWeb.class.getName());
    public static void main(String[] args) throws Exception {
        logger.info("Application started");
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("C:/20168734/account2.txt")));
        String login=scanner.nextLine();
        String pass=scanner.nextLine();

        ActionsVK actionsVK=new ActionsVK(logger);
        authorizeVK(actionsVK,login,pass);
        try {
            runMaxGroupScore(actionsVK,logger);
            //runMaxFriendScore(actionsVK,logger);
            //runMaxImgScore(actionsVK, logger);
        }catch(Exception e){
            actionsVK.closeDriver();
        }
        logger.info("Application finished");
    }
    public static void authorizeVK(ActionsVK actionsVK, String login, String pass) throws InterruptedException {
        actionsVK.getParentWindow("https://likes.fm/");
        Thread.sleep(300);
        actionsVK.getElementByXpath("(//div[@class='button_blue enter']/button)").click();
        actionsVK.switchToNewWindow();
        actionsVK.getElementByXpath("(//input[@name='email'])").sendKeys(login);
        actionsVK.getElementByXpath("(//input[@name='pass'])").sendKeys(pass);
        actionsVK.getElementByXpath("(//button[@id='install_allow'])").click();
        actionsVK.switchToParentWindow();

    }


    public static void runMaxGroupScore(ActionsVK actionsVK, Logger logger) throws InterruptedException {
        //get Max Group Score
        boolean curAction=true;
        boolean maxGroupIsreached=false;
        int errCount=0;
        if (!maxGroupIsreached){
            for (int i = 0; i < 100; i++) {
                curAction = actionsVK.getElementByLinkTextAndClick("Подпишись на группу");
                if (!curAction) curAction = actionsVK.getElementByXpathAndClick("(//div[@class='subscribe_button'])");
                if (!curAction) curAction = actionsVK.getElementByLinkTextAndClick("Follow");
                if (!curAction) curAction = actionsVK.getElementByLinkTextAndClick("Subscribe");
                if (!curAction) curAction = actionsVK.getElementByLinkTextAndClick("Подпиcаться");
                if (curAction) {
                    actionsVK.switchToNewWindow();
                    //Thread.sleep(1000);
                    curAction = actionsVK.getElementByXpathAndClick("(//button[@class='flat_button button_big button_wide'])");
                    actionsVK.closeCurWindow();
                    actionsVK.switchToParentWindow();
                } else {
                    logger.warn("Probably current action is frozen");
                }
                //if (errCount>100) break;
            }

        }else{
            logger.warn("Probably you reached a limit per day!");
        }
    }

    public static void runMaxImgScore(ActionsVK actionsVK, Logger logger) throws InterruptedException {
        //get Max Group Score
        boolean curAction=true;
        boolean maxImgIsreached=false; //!!!
        int errCount=0;
        if (!maxImgIsreached){
            for (int i = 0; i < 1000; i++) {
                curAction = actionsVK.getElementByLinkTextAndClick("Лайкни фотографию");
                if (curAction) {
                    actionsVK.switchToNewWindow();
                    //Thread.sleep(500);
                    //curAction = actionsVK.getElementByXpathAndClick("(//button[@class='flat_button button_big button_wide'])");
                    curAction = actionsVK.getElementByXpathAndClick("(//i[@id='pv_like_icon'])");
                    if (curAction) {
                        actionsVK.closeCurWindow();
                        actionsVK.switchToParentWindow();
                    }
                    //if (!curAction) actionsVK.getElementByXpathAndClick("(//div[@class='fl_l desc_info']/div[@class='x_button'])");
                    //Thread.sleep(500);
                } else {
                    logger.warn("Probably image is frozen");
                    if (!actionsVK.checkItWindow()){
                        actionsVK.closeCurWindow();
                        actionsVK.switchToParentWindow();
                    }
                }

                boolean curAction2 = actionsVK.getElementByLinkTextAndClick("Лайкни запись на стене");
                //curAction = actionsVK.getElementByLinkTextAndClick("Лайкни фотографию");
                if (curAction2) {
                    actionsVK.switchToNewWindow();
                    //Thread.sleep(500);
                    //curAction = actionsVK.getElementByXpathAndClick("(//button[@class='flat_button button_big button_wide'])");
                    curAction2 = actionsVK.getElementByXpathAndClick("(//i[@class='fw_like_icon  fl_l'])");
                    if (curAction2)
                    {
                        actionsVK.closeCurWindow();
                        actionsVK.switchToParentWindow();
                    }
                    //if (!curAction2) actionsVK.getElementByXpathAndClick("(//div[@class='fl_l desc_info']/div[@class='x_button'])");
                    //Thread.sleep(500);
                } else {
                    logger.warn("Probably current image is frozen");
                    errCount++;
                    if (!actionsVK.checkItWindow()){
                        actionsVK.closeCurWindow();
                        actionsVK.switchToParentWindow();
                    }
                }
                //if (errCount>100) break;
            }
            logger.info("Task executed!");
        }else{
            logger.warn("Probably you reached an image limit per day!");
        }
    }

    public static void runMaxFriendScore(ActionsVK actionsVK, Logger logger) throws InterruptedException {
        //get Max Friend Score
        boolean curAction=true;
        boolean maxFriendIsreached=false;
        int errCount=0;
        //maxImgIsreached=actionsVK.getElementByXpathAndClick("(//div[@class='module_description'])");
        logger.info("Friend score is running");

        maxFriendIsreached=false;//TO DO
        if (!maxFriendIsreached){
            for (int i = 0; i < 1000; i++) {
                curAction = actionsVK.getElementByLinkTextAndClick("Отправь заявку в друзья");
                if (curAction) {
                    actionsVK.switchToNewWindow();
                    curAction = actionsVK.getElementByXpathAndClick("(//div[@class='profile_action_btn']/button)");
                    //curAction=actionsVK.getElementByLinkTextAndClick("Добавить в друзья");
                    //if (!curAction) Thread.sleep(2500);
                        actionsVK.closeCurWindow();
                    if (curAction) {
                        actionsVK.switchToParentWindow();
                        Thread.sleep(1000);
                    }
                } else {
                    errCount++;
                }
                Thread.sleep(1000);
            //if (errCount>100) break;
            }
            logger.info("Task executed!");
        }else{
            logger.warn("Probably you reached an image limit per day!");
        }
    }

}
