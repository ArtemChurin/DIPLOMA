package ru.netology.touroftheday.page;

import ru.netology.touroftheday.card.BankCard;
import ru.netology.touroftheday.util.Path;
import static com.codeborne.selenide.Condition.*;

public class TourPageMain {

    public TourPageMain() {
        Path.pageHeader.should(visible);
        Path.tourDescription.shouldBe(visible);
        Path.debitPayButton.shouldBe(visible);
        Path.creditPayButton.shouldBe(visible);
        Path.debitPayFormHeader.shouldBe(hidden);
        Path.payForm.shouldBe(hidden);
    }

    public TourPageDebitPay openDebitPayPage() {
        Path.debitPayButton.click();
        Path.debitPayFormHeader.shouldBe(visible);
        Path.payForm.shouldBe(visible);
        Path.cardNumberField.shouldBe(visible);
        Path.monthField.shouldBe(visible);
        Path.yearField.shouldBe(visible);
        Path.holderField.shouldBe(visible);
        Path.cvvField.shouldBe(visible);
        Path.continueButton.shouldBe(visible);

        return new TourPageDebitPay();
    }

    public TourPageCreditPay openCreditPayPage() {
        Path.creditPayButton.click();
        Path.debitPayFormHeader.shouldBe(visible);
        Path.payForm.shouldBe(visible);
        Path.cardNumberField.shouldBe(visible);
        Path.monthField.shouldBe(visible);
        Path.yearField.shouldBe(visible);
        Path.holderField.shouldBe(visible);
        Path.cvvField.shouldBe(visible);
        Path.continueButton.shouldBe(visible);

        return new TourPageCreditPay();
    }
}