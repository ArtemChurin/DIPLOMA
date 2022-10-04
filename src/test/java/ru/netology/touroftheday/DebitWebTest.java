package ru.netology.touroftheday;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.touroftheday.card.BankCard;
import ru.netology.touroftheday.page.TourPageDebitPay;
import ru.netology.touroftheday.page.TourPageMain;
import ru.netology.touroftheday.util.BankCardUtil;
import ru.netology.touroftheday.util.DataBaseUtil;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DebitWebTest {

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
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getApprovedCard();

        debitPage.makeBuy(card);

        debitPage.checkOperationIsSuccessful();

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(1, payments.size());
        assertEquals(1, orders.size());

        assertEquals(45000, payments.get(0).getAmount());
        assertEquals("approved", payments.get(0).getStatus().toLowerCase());
        assertEquals(payments.get(0).getTransaction_id(), orders.get(0).getPayment_id());

        assertNull(orders.get(0).getCredit_id());
    }

    //2
    @Test
    @DisplayName("Покупка валидной дебетовой картой со статусом DECLINED")
    public void testDebitPayInvalidCard() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getDeclinedCard();

        debitPage.makeBuy(card);

        debitPage.checkOperationWithError();

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders  = DataBaseUtil.getOrders();
        assertEquals(1, payments.size());
        assertEquals(1, orders.size());

        assertEquals(45000, payments.get(0).getAmount());
        assertEquals("declined", payments.get(0).getStatus().toLowerCase());
        assertEquals(payments.get(0).getTransaction_id(), orders.get(0).getPayment_id());

        assertNull(orders.get(0).getCredit_id());
    }

    //3
    @Test
    @DisplayName("Покупка валидной дебетовой картой, отсутствующей в БД банка")
    public void testDebitPayValidCardNotInDB() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithNumberAbsentInDatabase();

        debitPage.makeBuy(card);

        debitPage.checkOperationWithError();

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders  = DataBaseUtil.getOrders();
        assertEquals(1, payments.size());
        assertEquals(1, orders.size());

        assertEquals(45000, payments.get(0).getAmount());
        assertEquals("declined", payments.get(0).getStatus().toLowerCase());
        assertEquals(payments.get(0).getTransaction_id(), orders.get(0).getPayment_id());
    }

    //4
    @Test
    @DisplayName("Покупка валидной дебетовой картой, номер карты отсутствует")
    public void testDebitPayNoCardNumber() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithEmptyNumberField();

        debitPage.makeBuy(card);

        debitPage.wrongFormatNotification("Номер карты");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders  = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //5
    @Test
    @DisplayName("Покупка валидной дебетовой картой, номер карты содержит 1 цифру")
    public void testDebitPayCardNumberOfZeros() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithNumberOfZeros();

        debitPage.makeBuy(card);

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders  = DataBaseUtil.getOrders();
        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //6
    @Test
    @DisplayName("Покупка валидной дебетовой картой, номер карты содержит 1 цифру")
    public void testDebitPayOneDigitCardNumber() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithNumberOfOneDigit();

        debitPage.makeBuy(card);

        debitPage.wrongFormatNotification("Номер карты");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders  = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //7
    @Test
    @DisplayName("Покупка валидной дебетовой картой, номер карты содержит 15 цифр")
    public void testDebitPayFifteenDigitsCardNumber() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithFifteenDigitNumber();

        debitPage.makeBuy(card);

        debitPage.wrongFormatNotification("Номер карты");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders  = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //8
    @Test
    @DisplayName("Покупка валидной дебетовой картой, месяц отсутствует")
    public void testDebitPayEmptyMonth() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithEmptyMonthField();

        debitPage.makeBuy(card);

        debitPage.wrongFormatNotification("Месяц");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders  = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //9
   @Test
    @DisplayName("Покупка валидной дебетовой картой, месяц значение 13")
    public void testDebitPayMonthGreaterTwelve() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithMonthFieldEqualsThirteen();

        debitPage.makeBuy(card);

       debitPage.invalidDateNotification("Месяц");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders  = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //10
    @Test
    @DisplayName("Покупка валидной дебетовой картой, месяц значение 00")
    public void testDebitPayMonthEqualsZero() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithMonthFieldEqualsZero();

        debitPage.makeBuy(card);

        debitPage.invalidDateNotification("Месяц");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders  = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //11
    @Test
    @DisplayName("Покупка валидной дебетовой картой, месяц значение предыдущий месяц")
    public void testDebitPayPreviousMonthCard() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithPreviousMonthField();

        debitPage.makeBuy(card);

        debitPage.invalidDateNotification("Месяц");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders  = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //12
    @Test
    @DisplayName("Покупка валидной дебетовой картой, год значение пусто")
    public void testDebitPayEmptyYear() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithEmptyYearField();

        debitPage.makeBuy(card);

        debitPage.mustBeFilledNotification("Год");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //13
    @Test
    @DisplayName("Покупка валидной дебетовой картой, год значение 00")
    public void testDebitPayYearOfZeros() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithYearFieldEqualsZero();

        debitPage.makeBuy(card);

        debitPage.expiredNotification("Год");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //14
    @Test
    @DisplayName("Покупка валидной дебетовой картой, год значение предыдущего года")
    public void testDebitPayPreviousYear() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithPreviousYearField();

        debitPage.makeBuy(card);

        debitPage.expiredNotification("Год");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //15
    @Test
    @DisplayName("Покупка валидной дебетовой картой, год значение текущего плюс 5")
    public void testDebitPayYearPlusFive() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithFarFutureYearField();

        debitPage.makeBuy(card);

        debitPage.invalidDateNotification("Год");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //16
    @Test
    @DisplayName("Покупка валидной дебетовой картой, CVV значение пусто")
    public void testDebitPayCvvIsEmpty() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithEmptyCvvField();

        debitPage.makeBuy(card);

        debitPage.mustBeFilledNotification("CVC/CVV");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //17
    @Test
    @DisplayName("Покупка валидной дебетовой картой, CVV значение 1 цифра")
    public void testDebitPayCvvIsOneDigit() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithCvvFieldOfOneDigit();

        debitPage.makeBuy(card);

        debitPage.wrongFormatNotification("CVC/CVV");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //18
    @Test
    @DisplayName("Покупка валидной дебетовой картой, CVV значение 2 цифры")
    public void testDebitPayCvvIsTwoDigits() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithCvvFieldOfTwoDigit();

        debitPage.makeBuy(card);

        debitPage.wrongFormatNotification("CVC/CVV");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //19
    @Test
    @DisplayName("Покупка валидной дебетовой картой, CVV значение 000")
    public void testDebitPayCvvIsThreeZeros() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithCvvFieldOfThreeZeros();

        debitPage.makeBuy(card);

        debitPage.checkOperationIsSuccessful();

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(1, payments.size());
        assertEquals(1, orders.size());

        assertEquals(45000, payments.get(0).getAmount());
        assertEquals("approved", payments.get(0).getStatus().toLowerCase());
        assertEquals(payments.get(0).getTransaction_id(), orders.get(0).getPayment_id());

        assertNull(orders.get(0).getCredit_id());
    }

    //20
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец в верхнем регистре")
    public void testDebitPayUppercaseHolder() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardHolderUppercase();

        debitPage.makeBuy(card);

        debitPage.checkOperationIsSuccessful();

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(1, payments.size());
        assertEquals(1, orders.size());

        assertEquals(45000, payments.get(0).getAmount());
        assertEquals("approved", payments.get(0).getStatus().toLowerCase());
        assertEquals(payments.get(0).getTransaction_id(), orders.get(0).getPayment_id());

        assertNull(orders.get(0).getCredit_id());
    }

    //21
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец через дефис")
    public void testDebitPayHolderWithDash() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardHolderWithDash();

        debitPage.makeBuy(card);

        debitPage.checkOperationIsSuccessful();

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(1, payments.size());
        assertEquals(1, orders.size());

        assertEquals(45000, payments.get(0).getAmount());
        assertEquals("approved", payments.get(0).getStatus().toLowerCase());
        assertEquals(payments.get(0).getTransaction_id(), orders.get(0).getPayment_id());

        assertNull(orders.get(0).getCredit_id());
    }

    //22
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец пусто")
    public void testDebitPayHolderIsEmpty() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithEmptyHolderField();

        debitPage.makeBuy(card);

        debitPage.mustBeFilledNotification("Владелец");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //23
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец без имени")
    public void testDebitPayHolderSurnameOnly() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardHolderSurnameOnly();

        debitPage.makeBuy(card);

        debitPage.wrongFormatNotification("Владелец");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //24
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец кириллицей")
    public void testDebitPayHolderRussianSymbols() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardHolderRussianLang();

        debitPage.makeBuy(card);

        debitPage.wrongFormatNotification("Владелец");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //25
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец цифрами")
    public void testDebitPayHolderOfDigits() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithHolderOfNumbers();

        debitPage.makeBuy(card);

        debitPage.wrongFormatNotification("Владелец");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }

    //26
    @Test
    @DisplayName("Покупка валидной дебетовой картой, значение владелец спецсимволами")
    public void testDebitPayHolderOfSpecialSymbols() {
        TourPageDebitPay debitPage = tourMainPage.openDebitPayPage();
        BankCard card = BankCardUtil.getCardWithHolderOfSpecialSymbols();

        debitPage.makeBuy(card);

        debitPage.wrongFormatNotification("Владелец");

        List<DataBaseUtil.PaymentEntity> payments = DataBaseUtil.getPayments();
        List<DataBaseUtil.OrderEntity> orders = DataBaseUtil.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, orders.size());
    }
}