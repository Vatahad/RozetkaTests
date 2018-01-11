package Wirk;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationPage {

    private SelenideElement registrationButton = $(".signup-submit .btn-link-i");
    private ElementsCollection inputFields = $$(".auth-input-text");


    public void openRozetkaRegistration(){
        open("https://my.rozetka.com.ua/signup/");

        registrationButton.shouldBe(Condition.visible);
    }

    public void setName(String name){
        inputFields.get(0).val(name);
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


}
