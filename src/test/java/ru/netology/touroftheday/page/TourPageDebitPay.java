package ru.netology.touroftheday.page;

import com.codeborne.selenide.Condition;
import ru.netology.touroftheday.card.BankCard;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static ru.netology.touroftheday.page.TourPageMain.*;


public class TourPageDebitPay {

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
}
