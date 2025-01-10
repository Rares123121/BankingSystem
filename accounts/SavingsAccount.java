package org.poo.accounts;

public class SavingsAccount extends Accounts {
    /**
     * Constructor pentru un cont de savings
     * @param iban
     * @param balance
     * @param currency
     * @param type
     * @param timestamp
     */
    public SavingsAccount(final String iban, final double balance, final String currency,
                          final String type, final int timestamp) {
        super(iban, balance, currency, type, timestamp);
    }
}

