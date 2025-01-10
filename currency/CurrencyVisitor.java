package org.poo.currency;


import java.util.ArrayList;

public interface CurrencyVisitor {
    /**
     * Metoda de visit
     * @param currency
     * @param from
     * @param to
     * @param amount
     * @return
     */
    ArrayList<Double> visitCurrency(CurrencyConverter currency, String from,
                             String to, double amount);
}
