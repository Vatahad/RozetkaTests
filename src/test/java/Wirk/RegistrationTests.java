package Wirk;


import com.codeborne.selenide.Condition;
import org.junit.Test;

public class RegistrationTests {

    RegistrationPage registrationPage = new RegistrationPage();

    String email = "test@gmail.com"; // If you want test this - put correct email and password
    String password = "testpassword1234";

    @Test
    public void checkingRegistrationViaEmail() {

        registrationPage.openRozetkaRegistration();

        registrationPage.setEmail(email);
        registrationPage.setName("Userok");
        registrationPage.setPassword(password + "S");

        registrationPage.clickRegButton();
        registrationPage.confirmRegistration(email, password);

        registrationPage.getEmailAfterConfirmField().shouldHave(Condition.text(email));
    }

}
