package ru.netology.touroftheday.page;

import ru.netology.touroftheday.card.BankCard;
import ru.netology.touroftheday.util.Path;
import static com.codeborne.selenide.Condition.*;


public class TourPageDebitPay {
    public TourPageDebitPay() {
    }
    public void makeBuy(BankCard card) {
        Path.cardNumberField.setValue(card.getNumber());
        Path.monthField.setValue(card.getMonth());
        Path.yearField.setValue(card.getYear());
        Path.holderField.setValue(card.getHolder());
        Path.cvvField.setValue(card.getCvc());
        Path.continueButton.click();
    }
}