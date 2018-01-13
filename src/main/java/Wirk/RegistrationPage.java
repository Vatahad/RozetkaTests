package Wirk;

import Gmail.GmailInbox;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationPage {

    private SelenideElement registrationButton = $(".signup-submit .btn-link-i");
    private SelenideElement messageAfterConfirmField = $("div[name=\"app-message\"] .message");
    private SelenideElement emailAfterConfirmField = $(".profile-col-main .clearfix:nth-child(2) .profile-info-l-i-text");
    private ElementsCollection inputFields = $$(".auth-input-text");

    public RegistrationPage(){
        System.setProperty("selenide.browser", "Chrome");
    }

    public void openRozetkaRegistration(){
        open("https://my.rozetka.com.ua/signup/");

        registrationButton.shouldBe(Condition.visible);
    }

    public void setName(String name){
        inputFields.get(0).val(name);
    }

    public SelenideElement getMessageAfterConfirmField() {
        return messageAfterConfirmField;
    }

    public SelenideElement getEmailAfterConfirmField() {
        return emailAfterConfirmField;
    }

    public void setEmail(String email){
        inputFields.get(1).val(email);
    }

    public void setPassword(String password){
        inputFields.get(2).val(password);
    }

    public void clickRegButton(){
        registrationButton.click();
    }

    public String getConfirmLinkFromMessage(String message){
        int indexOfSearchCombination = message.indexOf("Для подтверждения регистрации");
        String url = "";
        Boolean isExit = false;
        for (int i = indexOfSearchCombination; i < message.length(); i++) {
            if(message.charAt(i) == '"'){
                for (int j = i+1; j < message.length(); j++) {
                    if(message.charAt(j) == '"'){
                        isExit = true;
                        break;
                    } else {
                        url += message.charAt(j);
                    }
                }
            }

            if(isExit) break;
        }
        return url;
    }

    public void confirmRegistration(String email, String password){
        GmailInbox gmail = new GmailInbox();

        String textMessage = gmail.read(email, password);
        String urlForConfirm = getConfirmLinkFromMessage(textMessage);

        open(urlForConfirm);
        getMessageAfterConfirmField().shouldBe(Condition.visible);
    }
}
