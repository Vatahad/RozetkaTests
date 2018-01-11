package Wirk;


import org.junit.Test;

public class Tests {
//I must put different tests from different areas in different classes, but I put this test in one class

    @Test
    public void checkingRegistrationViaEmail(){
        RegistrationPage registrationPage = new RegistrationPage();

        registrationPage.openRozetkaRegistration();

        registrationPage.setEmail("tempmail@p33.org");
        registrationPage.setName("Userok");
        registrationPage.setPassword("123456yY");

        registrationPage.clickRegButton();
    }

}
