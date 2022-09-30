package ru.netology.touroftheday.page;

import ru.netology.touroftheday.card.BankCard;
import static com.codeborne.selenide.Condition.*;


public class TourPageCreditPay extends TourPageMain {

    public void makeBuy(BankCard card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getHolder());
        cvvField.setValue(card.getCvc());
        continueButton.click();
    }

}
