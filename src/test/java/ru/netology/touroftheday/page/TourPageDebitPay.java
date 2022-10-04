package ru.netology.touroftheday.page;

import com.codeborne.selenide.Condition;
import ru.netology.touroftheday.card.BankCard;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static ru.netology.touroftheday.page.TourPageMain.*;


public class TourPageDebitPay extends TourPageForm {

    public TourPageDebitPay() {
        super();
        debitPayFormHeader.shouldBe(visible);
        payForm.shouldBe(visible);
    }
}
