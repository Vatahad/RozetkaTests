package Rozetka;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {

    private SelenideElement logo = $(".logo");
    private SelenideElement searchInputField = $("#rz-search input.rz-header-search-input-text");
    private SelenideElement comparisonButton = $("#comparison");
    private ElementsCollection resultOfSearch = $$("div[name=\"search_list\"]>div");
    private ElementsCollection resultOfSearchGoodLists = $$("div[name=\"goods_list\"]>div");
    private ElementsCollection equalsFields = $$(".comparison-t-row[name=\"equal\"]");
    private ElementsCollection comparisonTop = $$(".comparison-t-head-cell.valigned-top");
    private SelenideElement compareButtonForThings = $(".btn-link-to-compare .btn-link-i");
    private SelenideElement popUpCart = $(".popup-cart");
    private SelenideElement countThingsForComparison = $("#comparison .hub-i-count");

    public MainPage() {
        System.setProperty("selenide.browser", "Chrome");
    }


    public void openPage() {
        open("https://rozetka.com.ua/");
        logo.shouldBe(Condition.visible);
    }

    public SelenideElement getPopUpCart() {
        return popUpCart;
    }

    public void closePopUp() {
        popUpCart.$(".popup-close").click();
    }

    public String getNameOfLastThing() {
        return popUpCart.$(".cart-added .cart-info .cart-i-title").text();
    }

    public SelenideElement getPrice(int position) {
        return comparisonTop.get(position - 1).$(".g-price");
    }

    public String getName(int position) {
        return comparisonTop.get(position - 1).$(".comparison-g-title").text();
    }

    public void clickBuy(int position) {
        comparisonTop.get(position - 1).$(".g-tools-i .comparison-g-buy-small-btn").click();
    }

    public ElementsCollection getEqualsFields() {
        return equalsFields;
    }

    public SelenideElement getSomeValueViaName(String name, int thing) {
        return $$(By.xpath("//div[contains(text(),\"" + name + "\")]/../div[@class=\"comparison-t-cell\"]")).get(thing - 1);
    }

    public void clickComparisonButton() {
        comparisonButton.click();
    }

    public void clickCompareButtonForThings() {
        compareButtonForThings.click();
    }

    public SelenideElement getSearchInputField() {
        return searchInputField;
    }

    public SelenideElement getResultOfSearch(int number) {
        if (resultOfSearch.get(0).exists()) {
            return resultOfSearch.get(number - 1);
        } else {
            return resultOfSearchGoodLists.get(number - 1);
        }
    }

    public void clickCompareSelector(int numberOfThing) {
        int value = 0;
        if (countThingsForComparison.exists()) {
            value = Integer.parseInt(countThingsForComparison.text());
        }
        getResultOfSearch(numberOfThing).$(".g-compare").click();
        countThingsForComparison.shouldHave(Condition.text(String.valueOf(value + 1)));
    }

}
