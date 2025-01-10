package org.poo.accounts;

public final class AccountFactory {
    private AccountFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Metoda care se ocupoa cu generarea unui cont
     * @param iban
     * @param currency
     * @param type
     * @param timestamp
     * @return
     */
    public static Accounts createAccounts(final String iban, final String currency,
                                          final String type, final int timestamp) {
        double initialBalance = 0;
        Accounts accounts = null;
        if (type.equals("classic")) {
            accounts = new ClassicAccount(iban, initialBalance, currency, type, timestamp);
        } else if (type.equals("savings")) {
            accounts = new SavingsAccount(iban, initialBalance, currency, type, timestamp);
        }
        return accounts;
    }

}
