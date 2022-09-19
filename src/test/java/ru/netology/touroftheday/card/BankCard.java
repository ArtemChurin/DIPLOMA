package ru.netology.touroftheday.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BankCard {
    private final String number;
    private final String month;
    private final String year;
    private final String holder;
    private final String cvc;
}