package org.poo.currency;

import java.util.ArrayList;

public class CurrencyConverter implements CurrencyVisitable {
    private ArrayList<Exchange> exchange;


    public CurrencyConverter(final ArrayList<Exchange> exchange) {
        this.exchange = exchange;
    }

    /**
     * Folosesc aceasta metoda pentru a genera toate conversiile inverse
     * intre monede
     */
    public void generateRest() {
        ArrayList<Exchange> inverse = new ArrayList<>();
        for (int i = 0; i < exchange.size(); i++) {
            double rate = 1 / exchange.get(i).getRate();
            Exchange aux = new Exchange(exchange.get(i).getTo(), exchange.get(i).getFrom(),
                    rate);

            int ok = 1;
            for (int j = 0; j < exchange.size(); j++) {
                 if (aux.getFrom().equals(exchange.get(j).getFrom())
                         && aux.getTo().equals(exchange.get(j).getTo())
                         && aux.getRate() == exchange.get(j).getRate()) {
                    ok = 0;
                }
            }
            if (ok == 1) {
                inverse.add(aux);
            }
        }
        for (int i = 0; i < inverse.size(); i++) {
            exchange.add(inverse.get(i));
        }
    }

    /**
     * Metoda apelata pentru a porni generarea
     * drumului
     * @param from
     * @param to
     * @param money
     * @return
     */
    public ArrayList<Double> convert(final String from, final String to, final double money) {
        return convert(from, to, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Construiesc drumul de la o moneda la alta, populand
     * un vector cu rate-urile afeerente
     * @param from
     * @param to
     * @param rates
     * @param visited
     * @return
     */
    public ArrayList<Double> convert(final String from, final String to,
                                     final ArrayList<Double> rates,
                                     final ArrayList<String> visited) {
        if (from.equals(to)) {
            rates.add(1.0);
            return rates;
        }

        visited.add(from);
        for (Exchange exchangeInput : exchange) {
            if (exchangeInput.getFrom().equals(from) && exchangeInput.getTo().equals(to)) {
                rates.add(exchangeInput.getRate());
                return rates;
            }
        }
        for (Exchange exchangeInput : exchange) {
            if (exchangeInput.getFrom().equals(from) && !visited.contains(exchangeInput.getTo())) {
                rates.add(exchangeInput.getRate());
                return convert(exchangeInput.getTo(), to, rates, visited);
            }
        }
        return null;
    }

    /**
     * Metoda de accept folosita in cadrul
     * desing patternului
     * @param visitor
     * @param from
     * @param to
     * @param amount
     * @return
     */
    public ArrayList<Double> accept(final CurrencyVisitor visitor, final String from,
                                    final String to, final double amount) {
        return visitor.visitCurrency(this, from, to, amount);
    }
}
