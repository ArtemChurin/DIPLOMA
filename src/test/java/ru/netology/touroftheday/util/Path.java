package ru.netology.touroftheday.util;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class Path {

    public static final SelenideElement pageHeader = $x("//h2[text()='Путешествие дня']");
    public static final SelenideElement tourDescription = $x("//div[contains(@class, 'Preview')]");
    public static final SelenideElement debitPayButton = $x("//button//span[text()='Купить']/../..");
    public static final SelenideElement creditPayButton = $x("//button//span[text()='Купить в кредит']/../..");

    public static final SelenideElement debitPayFormHeader = $x("//h3[(text()='Оплата по карте')]");
    public static final SelenideElement creditPayFormHeader = $x("//h3[(text()='Кредит по данным карты')]");
    public static final SelenideElement payForm = $x("//form");

    public static final SelenideElement cardNumberField = $x("//form//span[text()='Номер карты']//..//input");
    public static final SelenideElement monthField = $x("//form//span[text()='Месяц']//..//input");
    public static final SelenideElement yearField = $x("//form//span[text()='Год']//..//input");
    public static final SelenideElement holderField = $x("//form//span[text()='Владелец']//..//input");
    public static final SelenideElement cvvField = $x("//form//span[text()='CVC/CVV']//..//input");
    public static final SelenideElement continueButton = $x("//form//button//span[text()='Продолжить']/../..");

//    private static final SelenideElement successNotification = $x("//div[contains(@class, 'notification_status_ok')]");
//    private static final SelenideElement successCloseButton = successNotification.$x("./button");
//    private static final SelenideElement errorNotification = $x("//div[contains(@class, 'notification_status_error')]");
//    private static final SelenideElement errorCloseButton = errorNotification.$x("./button");
}