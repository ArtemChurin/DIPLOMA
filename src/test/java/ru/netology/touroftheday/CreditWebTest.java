package ru.netology.touroftheday;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.touroftheday.card.BankCard;
import ru.netology.touroftheday.page.TourPageCreditPay;
import ru.netology.touroftheday.page.TourPageMain;
import ru.netology.touroftheday.util.BankCardUtil;
import ru.netology.touroftheday.util.DataBaseUtil;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CreditWebTest {

    private static TourPageMain tourMainPage;

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080/");
        tourMainPage = new TourPageMain();
        DataBaseUtil.setDown();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    //1
    @Test
    @DisplayName("Покупка валидной дебетовой картой со статусом APPROVED")
    public void testDebitPayValidCard() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getApprovedCard();

        creditPage.makeBuy(card);

        creditPage.checkOperationIsSuccessful();

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(1, requests.size());
        assertEquals(1, orders.size());


        assertEquals("approved", requests.get(0).getStatus().toLowerCase());
        assertEquals(requests.get(0).getBank_id(), orders.get(0).getCredit_id());

        assertNull(orders.get(0).getPayment_id());//requests.get(0).getBank_id()
    }

    //2
    @Test
    @DisplayName("Покупка валидной дебетовой картой со статусом DECLINED")
    public void testDebitPayInvalidCard() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getDeclinedCard();

        creditPage.makeBuy(card);

        creditPage.checkOperationWithError();

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();
        assertEquals(1, requests.size());
        assertEquals(1, orders.size());


        assertEquals("declined", requests.get(0).getStatus().toLowerCase());
        assertEquals(requests.get(0).getBank_id(), orders.get(0).getCredit_id());

        assertNull(orders.get(0).getPayment_id());
    }

    //3
    @Test
    @DisplayName("Покупка валидной дебетовой картой, отсутствующей в БД банка")
    public void testDebitPayValidCardNotInDB() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithNumberAbsentInDatabase();

        creditPage.makeBuy(card);

        creditPage.checkOperationWithError();

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();
        assertEquals(1, requests.size());
        assertEquals(1, orders.size());

        assertEquals("declined", requests.get(0).getStatus().toLowerCase());
        assertEquals(requests.get(0).getBank_id(), orders.get(0).getCredit_id());
    }

    //4
    @Test
    @DisplayName("Покупка валидной дебетовой картой, номер карты отсутствует")
    public void testDebitPayNoCardNumber() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithEmptyNumberField();

        creditPage.makeBuy(card);

        creditPage.wrongFormatNotification("Номер карты");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //5
    @Test
    @DisplayName("Покупка валидной дебетовой картой, номер карты содержит 1 цифру")
    public void testDebitPayCardNumberOfZeros() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithNumberOfZeros();

        creditPage.makeBuy(card);

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();
        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //6
    @Test
    @DisplayName("Покупка валидной дебетовой картой, номер карты содержит 1 цифру")
    public void testDebitPayOneDigitCardNumber() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithNumberOfOneDigit();

        creditPage.makeBuy(card);

        creditPage.wrongFormatNotification("Номер карты");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //7
    @Test
    @DisplayName("Покупка валидной дебетовой картой, номер карты содержит 15 цифр")
    public void testDebitPayFifteenDigitsCardNumber() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithFifteenDigitNumber();

        creditPage.makeBuy(card);

        creditPage.wrongFormatNotification("Номер карты");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //8
    @Test
    @DisplayName("Покупка валидной дебетовой картой, месяц отсутствует")
    public void testDebitPayEmptyMonth() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithEmptyMonthField();

        creditPage.makeBuy(card);

        creditPage.wrongFormatNotification("Месяц");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //9
    @Test
    @DisplayName("Покупка валидной дебетовой картой, месяц значение 13")
    public void testDebitPayMonthGreaterTwelve() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithMonthFieldEqualsThirteen();

        creditPage.makeBuy(card);

        creditPage.invalidDateNotification("Месяц");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //10
    @Test
    @DisplayName("Покупка валидной дебетовой картой, месяц значение 00")
    public void testDebitPayMonthEqualsZero() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithMonthFieldEqualsZero();

        creditPage.makeBuy(card);

        creditPage.invalidDateNotification("Месяц");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //11
    @Test
    @DisplayName("Покупка валидной дебетовой картой, месяц значение предыдущий месяц")
    public void testDebitPayPreviousMonthCard() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithPreviousMonthField();

        creditPage.makeBuy(card);

        creditPage.invalidDateNotification("Месяц");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //12
    @Test
    @DisplayName("Покупка валидной дебетовой картой, год значение пусто")
    public void testDebitPayEmptyYear() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithEmptyYearField();

        creditPage.makeBuy(card);

        creditPage.mustBeFilledNotification("Год");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //13
    @Test
    @DisplayName("Покупка валидной дебетовой картой, год значение 00")
    public void testDebitPayYearOfZeros() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithYearFieldEqualsZero();

        creditPage.makeBuy(card);

        creditPage.expiredNotification("Год");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //14
    @Test
    @DisplayName("Покупка валидной дебетовой картой, год значение предыдущего года")
    public void testDebitPayPreviousYear() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithPreviousYearField();

        creditPage.makeBuy(card);

        creditPage.expiredNotification("Год");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //15
    @Test
    @DisplayName("Покупка валидной дебетовой картой, год значение текущего плюс 5")
    public void testDebitPayYearPlusFive() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithFarFutureYearField();

        creditPage.makeBuy(card);

        creditPage.invalidDateNotification("Год");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //16
    @Test
    @DisplayName("Покупка валидной дебетовой картой, CVV значение пусто")
    public void testDebitPayCvvIsEmpty() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithEmptyCvvField();

        creditPage.makeBuy(card);

        creditPage.mustBeFilledNotification("CVC/CVV");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //17
    @Test
    @DisplayName("Покупка валидной дебетовой картой, CVV значение 1 цифра")
    public void testDebitPayCvvIsOneDigit() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithCvvFieldOfOneDigit();

        creditPage.makeBuy(card);

        creditPage.wrongFormatNotification("CVC/CVV");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //18
    @Test
    @DisplayName("Покупка валидной дебетовой картой, CVV значение 2 цифры")
    public void testDebitPayCvvIsTwoDigits() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithCvvFieldOfTwoDigit();

        creditPage.makeBuy(card);

        creditPage.wrongFormatNotification("CVC/CVV");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //19
    @Test
    @DisplayName("Покупка валидной дебетовой картой, CVV значение 000")
    public void testDebitPayCvvIsThreeZeros() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithCvvFieldOfThreeZeros();

        creditPage.makeBuy(card);

        creditPage.checkOperationIsSuccessful();

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(1, requests.size());
        assertEquals(1, orders.size());

        assertEquals("approved", requests.get(0).getStatus().toLowerCase());
        assertEquals(requests.get(0).getBank_id(), orders.get(0).getCredit_id());

        assertNull(orders.get(0).getPayment_id());
    }

    //20
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец в верхнем регистре")
    public void testDebitPayUppercaseHolder() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardHolderUppercase();

        creditPage.makeBuy(card);

        creditPage.checkOperationIsSuccessful();

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(1, requests.size());
        assertEquals(1, orders.size());

        assertEquals("approved", requests.get(0).getStatus().toLowerCase());
        assertEquals(requests.get(0).getBank_id(), orders.get(0).getCredit_id());

        assertNull(orders.get(0).getPayment_id());
    }

    //21
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец через дефис")
    public void testDebitPayHolderWithDash() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardHolderWithDash();

        creditPage.makeBuy(card);

        creditPage.checkOperationIsSuccessful();

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(1, requests.size());
        assertEquals(1, orders.size());

        assertEquals("approved", requests.get(0).getStatus().toLowerCase());
        assertEquals(requests.get(0).getBank_id(), orders.get(0).getCredit_id());

        assertNull(orders.get(0).getPayment_id());
    }

    //22
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец пусто")
    public void testDebitPayHolderIsEmpty() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithEmptyHolderField();

        creditPage.makeBuy(card);

        creditPage.mustBeFilledNotification("Владелец");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //23
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец без имени")
    public void testDebitPayHolderSurnameOnly() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardHolderSurnameOnly();

        creditPage.makeBuy(card);

        creditPage.wrongFormatNotification("Владелец");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //24
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец кириллицей")
    public void testDebitPayHolderRussianSymbols() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardHolderRussianLang();

        creditPage.makeBuy(card);

        creditPage.wrongFormatNotification("Владелец");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //25
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец цифрами")
    public void testDebitPayHolderOfDigits() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithHolderOfNumbers();

        creditPage.makeBuy(card);

        creditPage.wrongFormatNotification("Владелец");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }

    //26
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец спецсимволами")
    public void testDebitPayHolderOfSpecialSymbols() {
        TourPageCreditPay creditPage = tourMainPage.openCreditPayPage();
        BankCard card = BankCardUtil.getCardWithHolderOfSpecialSymbols();

        creditPage.makeBuy(card);

        creditPage.wrongFormatNotification("Владелец");

        List<DataBaseUtil.CreditRequestEntity> requests = DataBaseUtil.getCreditsRequest();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, requests.size());
        assertEquals(0, orders.size());
    }
}