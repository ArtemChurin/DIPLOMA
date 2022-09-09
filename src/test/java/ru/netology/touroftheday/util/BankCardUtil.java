package ru.netology.touroftheday.util;

import ru.netology.touroftheday.entity.BankCard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class BankCardUtil {
    private static final String validCardNumber = "4444 4444 4444 4441";
    private static final String invalidCardNumber = "4444 4444 4444 4442";
    private static final String validMonth = "09";
    private static final String validYear = "22";
    private static final String validHolder = "Ivanov Ivan";
    private static final String validCVC = "999";

    //1
    public static BankCard getValidCard() {
        return new BankCard(validCardNumber, validMonth, validYear, validHolder, validCVC);
    }

    //2
    public static BankCard getInValidCard() {
        return new BankCard(invalidCardNumber, validMonth, validYear, validHolder, validCVC);
    }

    //3
    public static BankCard getCardWithNumberAbsentInDatabase() {
        return new BankCard("5555 5555 5555 5555", validMonth, validYear, validHolder, validCVC);
    }

    //4
    public static BankCard getCardWithEmptyNumberField() {
        return new BankCard("", validMonth, validYear, validHolder, validCVC);
    }

    //5
    public static BankCard getCardWithNumberOfOneDigit() {
        return new BankCard("1", validMonth, validYear, validHolder, validCVC);
    }

    //6
    public static BankCard getCardWithNumberOfZeros() {
        return new BankCard("0000 0000 0000 0000", validMonth, validYear, validHolder, validCVC);
    }

    //7
    public static BankCard getCardWithFifteenDigitNumber() {
        return new BankCard("4444 4444 4444 444", validMonth, validYear, validHolder, validCVC);
    }

    //8
    public static BankCard getCardWithEmptyMonthField() {
        return new BankCard(validCardNumber, "", validYear, validHolder, validCVC);
    }

    //9
    public static BankCard getCardWithMonthFieldEqualsThirteen() {
        return new BankCard(validCardNumber, "13", validYear, validHolder, validCVC);
    }

    //10
    public static BankCard getCardWithMonthFieldEqualsZero() {
        return new BankCard(validCardNumber, "00", validYear, validHolder, validCVC);
    }

    //11
    public static BankCard getCardWithPreviousMonthField() {
        return new BankCard(validCardNumber, LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM")), validYear, validHolder, validCVC);
    }

    //12
    public static BankCard getCardWithEmptyYearField() {
        return new BankCard(validCardNumber, validMonth, "", validHolder, validCVC);
    }

    //13
    public static BankCard getCardWithYearFieldEqualsZero() {
        return new BankCard(validCardNumber, validMonth, "00", validHolder, validCVC);
    }

    //14
    public static BankCard getCardWithPreviousYearField() {
        return new BankCard(validCardNumber, validMonth, LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy")), validHolder, validCVC);
    }

    //15
    public static BankCard getCardWithFarFutureYearField() {
        return new BankCard(validCardNumber, validMonth, LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy")), validHolder, validCVC);
    }

    //16
    public static BankCard getCardWithEmptyCvvField() {
        return new BankCard(validCardNumber, validMonth, validYear, validHolder, "");
    }

    //17
    public static BankCard getCardWithCvvFieldOfOneDigit() {
        return new BankCard(validCardNumber, validMonth, validYear, validHolder, "5");
    }

    //18
    public static BankCard getCardWithCvvFieldOfTwoDigit() {
        return new BankCard(validCardNumber, validMonth, validYear, validHolder, "55");
    }

    //19
    public static BankCard getCardWithCvvFieldOfThreeZeros() {
        return new BankCard(validCardNumber, validMonth, validYear, validHolder, "000");
    }

    //20
    public static BankCard getCardHolderUppercase() {
        return new BankCard(validCardNumber, validMonth, validYear, validHolder.toUpperCase(), validCVC);
    }

    //21
    public static BankCard getCardHolderWithDash() {
        return new BankCard(validCardNumber, validMonth, validYear, "Mama-Maria", validCVC);
    }

    //22
    public static BankCard getCardWithEmptyHolderField() {
        return new BankCard(validCardNumber, validMonth, validYear, "", validCVC);
    }

    //23
    public static BankCard getCardHolderSurnameOnly() {
        return new BankCard(validCardNumber, validMonth, validYear, "IVANOV", validCVC);
    }

    //24
    public static BankCard getCardHolderRussianLang() {
        return new BankCard(validCardNumber, validMonth, validYear, "Иванов Иван", validCVC);
    }

    //25
    public static BankCard getCardWithHolderOfNumbers() {
        return new BankCard(validCardNumber, validMonth, validYear, "874561986 64651658", validCVC);
    }

    //26
    public static BankCard getCardWithHolderOfSpecialSymbols() {
        return new BankCard(validCardNumber, validMonth, validYear, "@#$%^&*()~-+/*?><|", validCVC);
    }

}
