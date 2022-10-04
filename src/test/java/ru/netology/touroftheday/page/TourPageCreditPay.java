package ru.netology.touroftheday.page;

import ru.netology.touroftheday.card.BankCard;
import static com.codeborne.selenide.Condition.*;


public class TourPageCreditPay extends TourPageForm {

    public TourPageCreditPay() {
        super();
        creditPayFormHeader.shouldBe(visible);
        payForm.shouldBe(visible);
    }

}
