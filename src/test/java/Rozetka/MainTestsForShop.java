package Rozetka;

import com.codeborne.selenide.Condition;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import technologies.SqlExecutor;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MainTestsForShop {

    MainPage mainPage = new MainPage();
    SqlExecutor sql;

    @BeforeClass
    public void beforeClass() {
        mainPage.openPage();

        sql = SqlExecutor.getInstance();
        sql.readyDataBase("shop", "telephones");
    }

    @AfterClass
    public void afterClass() {
        sql.close();
    }

    @DataProvider(name = "CheckingValues")
    public Object[][] credentials() {
        return new Object[][]{
                {"Apple IPhone 7", "", 1, 1, ""},
                {"Apple IPhone 7 Plus", "", 1, 1, ""},
                {"Вес, г", "138", 2, 1, "Weight is not equals expected"},
                {"Вес, г", "188", 2, 2, "Weight is not equals expected"},
                {"Диагональ экрана", "4.7 \"", 2, 1, "Diagonal of display is not equals expected"},
                {"Диагональ экрана", "5.5 \"", 2, 2, "Diagonal of display is not equals expected"},
                {"Оперативная память", "2 ГБ", 2, 1, "RAM is not equals expected"},
                {"Оперативная память", "3 ГБ", 2, 2, "RAM is not equals expected"},
                {"", "Apple IPhone 7", 3, 1, "Name of telephone is not equals expected"},
                {"", "Apple IPhone 7 Plus", 3, 2, "Name of telephone is not equals expected"},
                {"", "", 4, 1, ""},
                {"", "", 4, 2, ""},
                {"", "", 5, 1, ""},
        };
    }

    @Test(dataProvider = "CheckingValues")
    public void checkValues(String valueName, String expectedValue, int step, int numOfThing, String reason) {
        if (step == 1) {
            mainPage.getSearchInputField().clear();
            mainPage.getSearchInputField().val(valueName);
            mainPage.getSearchInputField().pressEnter();

            mainPage.getResultOfSearch(1).shouldBe(Condition.visible);
            mainPage.getResultOfSearch(1).hover();
            mainPage.clickCompareSelector(1);
        } else if (step == 2) {
            mainPage.clickComparisonButton();
            mainPage.clickCompareButtonForThings();
            String value = mainPage.getSomeValueViaName(valueName, numOfThing).text();

            assertThat(reason, value, equalTo(expectedValue));
        } else if (step == 3) {
            mainPage.clickComparisonButton();
            mainPage.clickCompareButtonForThings();
            mainPage.clickBuy(numOfThing);
            mainPage.getPopUpCart().shouldBe(Condition.visible);
            String value = mainPage.getNameOfLastThing();
            mainPage.closePopUp();

            assertThat(reason, value, equalTo(expectedValue));//Test failed, because we have no "clear" name as in requirements
        } else if (step == 4) {
            mainPage.clickComparisonButton();
            mainPage.clickCompareButtonForThings();
            String nameOfTelephone = mainPage.getName(numOfThing);
            String stPrice = mainPage.getPrice(numOfThing).text();
            int price = Integer.parseInt(stPrice.replaceAll("[^0-9]", ""));

            sql.insertIntoTable("telephones", nameOfTelephone, price, new Date());
        } else {
            mainPage.clickComparisonButton();
            mainPage.clickCompareButtonForThings();

            System.out.println(mainPage.getEqualsFields().texts());
            //We have no actions for this step in requirements
        }
    }
}
