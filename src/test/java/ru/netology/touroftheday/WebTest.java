package ru.netology.touroftheday;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.touroftheday.entity.BankCard;
import ru.netology.touroftheday.entity.TourPage;
import ru.netology.touroftheday.util.BankCardUtil;

import static com.codeborne.selenide.Selenide.open;

public class WebTest {

    private static TourPage tourPage;

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080/");
        tourPage = new TourPage();
    }

    //1
    @Test
    @DisplayName("Покупка валидной дебетовой картой")
    public void testDebitPayValidCard() {
        tourPage.showPayForm(false);
        BankCard card = BankCardUtil.getValidCard();
        tourPage.makeBuy(card);
        System.out.println("Pay Debit Card");
        //TODO: check notifications
        //TODO: check database
    }

}
