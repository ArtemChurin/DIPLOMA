package ru.netology.touroftheday.util;

import com.github.javafaker.Faker;
import ru.netology.touroftheday.card.BankCard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class BankCardUtil {

    private static final Faker faker = new Faker();
    private static final String validCardNumber = "4444 4444 4444 4441";
    private static final String invalidCardNumber = "4444 4444 4444 4442";
    private static String getValidMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }
    private static String getPreviousMonth() {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }
    private static String getValidYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("YY"));
    }
    private static String getValidHolder() {
        return faker.name().fullName().toUpperCase(Locale.ENGLISH);
    }
    private static String getValidCVC() {
        return faker.numerify("###");
    }

    //1
    public static BankCard getApprovedCard() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    //2
    public static BankCard getDeclinedCard() {
        return new BankCard(invalidCardNumber, getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    //3
    public static BankCard getCardWithNumberAbsentInDatabase() {
        return new BankCard("5555 5555 5555 5555", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    //4
    public static BankCard getCardWithEmptyNumberField() {
        return new BankCard("", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    //5
    public static BankCard getCardWithNumberOfOneDigit() {
        return new BankCard("1", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    //6
    public static BankCard getCardWithNumberOfZeros() {
        return new BankCard("0000 0000 0000 0000", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    //7
    public static BankCard getCardWithFifteenDigitNumber() {
        return new BankCard("4444 4444 4444 444", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    //8
    public static BankCard getCardWithEmptyMonthField() {
        return new BankCard(validCardNumber, "", getValidYear(), getValidHolder(), getValidCVC());
    }

    //9
    public static BankCard getCardWithMonthFieldEqualsThirteen() {
        return new BankCard(validCardNumber, "13", getValidYear(), getValidHolder(), getValidCVC());
    }

    //10
    public static BankCard getCardWithMonthFieldEqualsZero() {
        return new BankCard(validCardNumber, "00", getValidYear(), getValidHolder(), getValidCVC());
    }

    //11
    public static BankCard getCardWithPreviousMonthField() {
        return new BankCard(validCardNumber, getPreviousMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    //12
    public static BankCard getCardWithEmptyYearField() {
        return new BankCard(validCardNumber, getValidMonth(), "", getValidHolder(), getValidCVC());
    }

    //13
    public static BankCard getCardWithYearFieldEqualsZero() {
        return new BankCard(validCardNumber, getValidMonth(), "00", getValidHolder(), getValidCVC());
    }

    //14
    public static BankCard getCardWithPreviousYearField() {
        return new BankCard(validCardNumber, getValidMonth(), LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy")), getValidHolder(), getValidCVC());
    }

    //15
    public static BankCard getCardWithFarFutureYearField() {
        return new BankCard(validCardNumber, getValidMonth(), LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy")), getValidHolder(), getValidCVC());
    }

    //16
    public static BankCard getCardWithEmptyCvvField() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), getValidHolder(), "");
    }

    //17
    public static BankCard getCardWithCvvFieldOfOneDigit() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), getValidHolder(), "5");
    }

    //18
    public static BankCard getCardWithCvvFieldOfTwoDigit() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), getValidHolder(), "55");
    }

    //19
    public static BankCard getCardWithCvvFieldOfThreeZeros() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), getValidHolder(), "000");
    }

    //20
    public static BankCard getCardHolderUppercase() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    //21
    public static BankCard getCardHolderWithDash() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), "Mama-Maria", getValidCVC());
    }

    //22
    public static BankCard getCardWithEmptyHolderField() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), "", getValidCVC());
    }

    //23
    public static BankCard getCardHolderSurnameOnly() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), "IVANOV", getValidCVC());
    }

    //24
    public static BankCard getCardHolderRussianLang() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), "Иванов Иван", getValidCVC());
    }

    //25
    public static BankCard getCardWithHolderOfNumbers() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), "874561986 64651658", getValidCVC());
    }

    //26
    public static BankCard getCardWithHolderOfSpecialSymbols() {
        return new BankCard(validCardNumber, getValidMonth(), getValidYear(), "^%###^&&*!@$%#^_-=+", getValidCVC());
    }

}
