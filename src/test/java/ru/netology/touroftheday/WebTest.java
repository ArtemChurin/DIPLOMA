package ru.netology.touroftheday;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.touroftheday.card.BankCard;
import ru.netology.touroftheday.page.TourPageDebitPay;
import ru.netology.touroftheday.page.TourPageMain;
import ru.netology.touroftheday.util.BankCardUtil;

import static com.codeborne.selenide.Selenide.open;

public class WebTest {

    private static TourPageMain tourPageMain;

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080/");
        tourPageMain = new TourPageMain();
    }

    //1
    @Test
    @DisplayName("Покупка валидной дебетовой картой")
    public void testDebitPayValidCard() {
        TourPageMain mainPage = new TourPageMain();
        TourPageDebitPay debitPage = mainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getValidCard();
        debitPage.makeBuy(card);
        System.out.println("Pay Debit Card");
        //TODO: check notifications
        //TODO: check database
    }
}