package org.poo.accounts;

public class ClassicAccount extends Accounts {
    /**
     * Constructor care memoreaza un cont clasic
     * @param iban
     * @param balance
     * @param currency
     * @param type
     * @param timestamp
     */
    public ClassicAccount(final String iban, final double balance, final String currency,
                          final String type, final int timestamp) {
        super(iban, balance, currency, type, timestamp);
    }
}
