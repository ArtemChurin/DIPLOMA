package ru.netology.touroftheday.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class TourPageMain {

    public static final SelenideElement pageHeader = $x("//h2[text()='Путешествие дня']");
    public static final SelenideElement tourDescription = $x("//div[contains(@class, 'Preview')]");
    public static final SelenideElement debitPayButton = $x("//button//span[text()='Купить']/../..");
    public static final SelenideElement creditPayButton = $x("//button//span[text()='Купить в кредит']/../..");


    public TourPageMain() {
        pageHeader.should(visible);
        tourDescription.shouldBe(visible);
        debitPayButton.shouldBe(visible);
        creditPayButton.shouldBe(visible);
    }

    public TourPageDebitPay openDebitPayPage() {
        debitPayButton.click();
        return new TourPageDebitPay();
    }

    public TourPageCreditPay openCreditPayPage() {
        creditPayButton.click();
        return new TourPageCreditPay();
    }

}
