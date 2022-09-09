package ru.netology.touroftheday.entity;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class TourPage {

    public static final SelenideElement pageHeader = $x("//div[@id='root']/div/h2");
    public static final SelenideElement tourDescription = $x("//*[@id=\"root\"]/div/div/div");

    public static final SelenideElement debitPayButton = $x("//*[@id=\"root\"]/div/button[1]");
    public static final SelenideElement creditPayButton = $x("//*[@id=\"root\"]/div/button[2]");

    public static final SelenideElement payFormHeader = $x("//*[@id=\"root\"]/div/h3");
    public static final SelenideElement payForm = $x("//*[@id=\"root\"]/div/form");

    public static final SelenideElement cardNumberField = $x("//*[@id=\"root\"]/div/form/fieldset/div[1]/span/span/span[2]/input");
    public static final SelenideElement monthField = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[1]/span/span/span[2]/input");
    public static final SelenideElement yearField = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[2]/span/span/span[2]/input");
    public static final SelenideElement holderField = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[1]/span/span/span[2]/input");
    public static final SelenideElement cvvField = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[2]/span/span/span[2]/input");
    public static final SelenideElement continueButton = $x("//*[@id=\"root\"]/div/form/fieldset/div[4]/button/span/span");


//    private static final SelenideElement successNotification = $x("//div[contains(@class, 'notification_status_ok')]");
//    private static final SelenideElement successCloseButton = successNotification.$x("./button");
//    private static final SelenideElement errorNotification = $x("//div[contains(@class, 'notification_status_error')]");
//    private static final SelenideElement errorCloseButton = errorNotification.$x("./button");


    public TourPage() {
        pageHeader.should(visible, text("Путешествие дня"));
        tourDescription.shouldBe(visible);
        debitPayButton.shouldBe(visible);
        creditPayButton.shouldBe(visible);
        payFormHeader.shouldBe(hidden);
        payForm.shouldBe(hidden);
    }

    public void showPayForm(boolean isCredit) {
        if (isCredit) {
            creditPayButton.click();
            payFormHeader.should(text("Кредит по данным карты"));
        } else {
            debitPayButton.click();
            payFormHeader.should(text("Оплата по карте"));
        }
        payForm.shouldBe(visible);
    }

    public void makeBuy(BankCard card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getHolder());
        cvvField.setValue(card.getCvc());
        continueButton.click();
    }

}
