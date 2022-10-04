package ru.netology.touroftheday.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.touroftheday.card.BankCard;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class TourPageForm {
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
    public static final SelenideElement debitPayFormHeader = $x("//h3[(text()='Оплата по карте')]");
    public static final SelenideElement creditPayFormHeader = $x("//h3[(text()='Кредит по данным карты')]");
    public static final SelenideElement payForm = $x("//form");

    public TourPageForm() {
        cardNumberField.shouldBe(visible);
        monthField.shouldBe(visible);
        yearField.shouldBe(visible);
        holderField.shouldBe(visible);
        cvvField.shouldBe(visible);
        continueButton.shouldBe(visible);
    }

    public void makeBuy(BankCard card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getHolder());
        cvvField.setValue(card.getCvc());
        continueButton.click();
    }

    public void checkOperationIsSuccessful() {
        successNotification.should(Condition.visible, Duration.ofSeconds(15));
        successNotification.should(Condition.cssClass("notification_visible"));
        successNotification.should(Condition.text("Успешно"));
        successNotification.should(Condition.text("Операция одобрена Банком."));
        successCloseButton.click();
        successNotification.should(Condition.hidden);
    }

    public void checkOperationWithError() {
        errorNotification.should(Condition.visible, Duration.ofSeconds(15));
        errorNotification.should(Condition.cssClass("notification_visible"));
        errorNotification.should(Condition.text("Ошибка"));
        errorNotification.should(Condition.text("Ошибка! Банк отказал в проведении операции."));
        errorCloseButton.click();
        errorNotification.should(Condition.hidden);
    }

    public void wrongFormatNotification(String text) {
        $x("//span[text()='" + text +"']/..//span[@class='input__sub']").shouldBe(visible, text("Неверный формат"));
    }

    public void mustBeFilledNotification(String text) {
        $x("//span[text()='" + text +"']/..//span[@class='input__sub']").shouldBe(visible, text("Поле обязательно для заполнения"));
    }

    public void invalidDateNotification(String text) {
        $x("//span[text()='" + text +"']/..//span[@class='input__sub']").shouldBe(visible, text("Неверно указан срок действия карты"));
    }

    public void expiredNotification(String text) {
        $x("//span[text()='" + text +"']/..//span[@class='input__sub']").shouldBe(visible, text("Истёк срок действия карты"));
    }
}
