package ru.netology.touroftheday.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.touroftheday.card.BankCard;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class TourPageMain {

    public static final SelenideElement pageHeader = $x("//h2[text()='Путешествие дня']");
    public static final SelenideElement tourDescription = $x("//div[contains(@class, 'Preview')]");
    public static final SelenideElement debitPayButton = $x("//button//span[text()='Купить']/../..");
    public static final SelenideElement creditPayButton = $x("//button//span[text()='Купить в кредит']/../..");
    public static final SelenideElement debitPayFormHeader = $x("//h3[(text()='Оплата по карте')]");
    public static final SelenideElement creditPayFormHeader = $x("//h3[(text()='Кредит по данным карты')]");
    public static final SelenideElement payForm = $x("//form");

    public static final SelenideElement cardNumberField = $x("//form//span[text()='Номер карты']//..//input");
    public static final SelenideElement monthField = $x("//form//span[text()='Месяц']//..//input");
    public static final SelenideElement yearField = $x("//form//span[text()='Год']//..//input");
    public static final SelenideElement holderField = $x("//form//span[text()='Владелец']//..//input");
    public static final SelenideElement cvvField = $x("//form//span[text()='CVC/CVV']//..//input");
    public static final SelenideElement continueButton = $x("//form//button//span[text()='Продолжить']/../..");
    public static final SelenideElement successNotification = $x("//div[contains(@class, 'notification_status_ok')]");
    public static final SelenideElement successCloseButton = successNotification.$x("./button");
    public static final SelenideElement errorNotification = $x("//div[contains(@class, 'notification_status_error')]");
    public static final SelenideElement errorCloseButton = errorNotification.$x("./button");

    public TourPageMain() {
        pageHeader.should(visible);
        tourDescription.shouldBe(visible);
        debitPayButton.shouldBe(visible);
        creditPayButton.shouldBe(visible);
        debitPayFormHeader.shouldBe(hidden);
        payForm.shouldBe(hidden);
    }

    public TourPageDebitPay openDebitPayPage() {
        debitPayButton.click();
        debitPayFormHeader.shouldBe(visible);
        payForm.shouldBe(visible);
        cardNumberField.shouldBe(visible);
        monthField.shouldBe(visible);
        yearField.shouldBe(visible);
        holderField.shouldBe(visible);
        cvvField.shouldBe(visible);
        continueButton.shouldBe(visible);

        return new TourPageDebitPay();
    }

    public TourPageCreditPay openCreditPayPage() {
        creditPayButton.click();
        creditPayFormHeader.shouldBe(visible);
        payForm.shouldBe(visible);
        cardNumberField.shouldBe(visible);
        monthField.shouldBe(visible);
        yearField.shouldBe(visible);
        holderField.shouldBe(visible);
        cvvField.shouldBe(visible);
        continueButton.shouldBe(visible);

        return new TourPageCreditPay();
    }

}
