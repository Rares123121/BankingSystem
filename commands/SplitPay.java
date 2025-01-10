package org.poo.commands;

import org.poo.currency.CurrencyConverter;
import org.poo.currency.CurrencyVisitor;
import org.poo.transactions.Transactions;
import org.poo.user.User;

import java.util.ArrayList;
import java.util.List;

public class SplitPay implements CommandExecuter {
    private CurrencyConverter c;
    private String currency;
    private CurrencyVisitor visitor;
    private List<String> accounts;
    private double amount;
    private ArrayList<User> users;
    private int timestamp;

    /**
     * Getter
     * @return
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Setter
     * @param timestamp
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Getter
     * @return
     */
    public List<String> getAccounts() {
        return accounts;
    }

    /**
     * Setter
     * @param accounts
     */
    public void setAccounts(final List<String> accounts) {
        this.accounts = accounts;
    }

    /**
     * Getter
     * @return
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setter
     * @param amount
     */
    public void setAmount(final double amount) {
        this.amount = amount;
    }

    /**
     * Getter
     * @return
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Setter
     * @param users
     */
    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    /**
     * Getter
     * @return
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Setter
     * @param currency
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
    }


    public SplitPay(final CurrencyConverter c, final String currency,
                    final CurrencyVisitor visitor, final List<String> accounts,
                    final double amount, final int timestamp, final ArrayList<User> users) {
        this.c = c;
        this.currency = currency;
        this.visitor = visitor;
        this.accounts = accounts;
        this.amount = amount;
        this.users = users;
        this.timestamp = timestamp;
    }

    /**
     * Se prelucreaza comanda de plata impartita
     */
    public void executeCommand() {
        double amountPerAccount = amount / accounts.size();
        ArrayList<Double> values = new ArrayList<>();
        String noMoney = "";
        int ok = 1;
        for (int j = 0; j < accounts.size(); j++) {
            for (int k = 0; k < getUsers().size(); k++) {
                for (int l = 0; l < getUsers().get(k).getAccounts().size(); l++) {
                    if (getUsers().get(k).getAccounts().get(l).getIban().
                            equals(accounts.get(j))) {
                        String accountCurrency = getUsers().get(k).getAccounts().
                                get(l).getCurrency();

                        ArrayList<Double> changed = c.accept(visitor, currency,
                                accountCurrency, amount);
                        double changedAmount = amountPerAccount;
                        for (int o = 0; o < changed.size(); o++) {
                            changedAmount = changedAmount * changed.get(o);
                        }
                        double amountChanged = changedAmount;
                        values.add(amountChanged);
                        if (getUsers().get(k).getAccounts().get(l).getBalance()
                                >= amountChanged) {
                            double balance = getUsers().get(k).getAccounts().get(l).getBalance();
                            getUsers().get(k).getAccounts().get(l).
                                    setBalance(balance - amountChanged);
                        } else {
                            ok = 0;
                            noMoney = accounts.get(j);
                            double balance = getUsers().get(k).getAccounts().get(l).getBalance();
                            getUsers().get(k).getAccounts().get(l).
                                    setBalance(balance - amountChanged);
                        }
                    }
                }
            }
        }
        if (ok == 1) {
            Transactions transaction = new Transactions();
            transaction.setDescription("Split payment of " + amount + "0 " + currency);
            transaction.setTimestamp(timestamp);
            transaction.setAmount(amountPerAccount);
            transaction.setCurrency(currency);
            transaction.setAccounts(accounts);
            for (int j = 0; j < accounts.size(); j++) {
                for (int k = 0; k < getUsers().size(); k++) {
                    for (int l = 0; l < getUsers().get(k).getAccounts().size(); l++) {
                        if (getUsers().get(k).getAccounts().get(l).getIban().
                                equals(accounts.get(j))) {
                            getUsers().get(k).getTransactions().add(transaction);
                            getUsers().get(k).getAccounts().get(l).
                                    getAllTransactions().add(transaction);
                        }
                    }
                }
            }
        } else {

            Transactions transaction = new Transactions();
            transaction.setDescription("Split payment of " + amount + "0 " + currency);
            transaction.setTimestamp(timestamp);
            transaction.setAmount(amountPerAccount);
            transaction.setCurrency(currency);
            transaction.setAccounts(accounts);
            transaction.setError("Account " + noMoney + " has insufficient funds"
                    + " for a split payment.");

            for (int j = 0; j < accounts.size(); j++) {
                for (int k = 0; k < getUsers().size(); k++) {
                    for (int l = 0; l < getUsers().get(k).getAccounts().size(); l++) {
                        if (getUsers().get(k).getAccounts().get(l).getIban().
                                equals(accounts.get(j))) {
                            double balance = getUsers().get(k).getAccounts().get(l).getBalance();
                            getUsers().get(k).getAccounts().get(l).
                                    setBalance(balance + values.get(j));
                            getUsers().get(k).getTransactions().add(transaction);
                            getUsers().get(k).getAccounts().get(l).
                                    getAllTransactions().add(transaction);
                        }
                    }
                }
            }

        }
    }
}
