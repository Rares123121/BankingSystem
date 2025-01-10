package org.poo.currency;

import java.util.ArrayList;

public class ConvertTo implements CurrencyVisitor {
    /**
     * Meotda folosita in Visitor Pattern
     * @param currency
     * @param from
     * @param to
     * @param money
     * @return
     */
    public ArrayList<Double> visitCurrency(final CurrencyConverter currency, final String from,
                                final String to, final double money) {
        ArrayList<Double> res = currency.convert(from, to, money);
        return res;
    }
}
