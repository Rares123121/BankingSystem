package org.poo.commands;

import org.poo.accounts.Accounts;
import org.poo.convert.Convert;
import org.poo.transactions.Transactions;
import org.poo.user.User;

import java.util.ArrayList;

public class ChangeInteresetRate implements CommandExecuter {
    private String account;
    private Convert convert;
    private ArrayList<User> users;
    private int timestamp;
    private double interest;

    public ChangeInteresetRate(final String account, final Convert convert,
                               final ArrayList<User> users, final int timestamp,
                               final double interest) {
        this.account = account;
        this.convert = convert;
        this.users = users;
        this.timestamp = timestamp;
        this.interest = interest;
    }
    /**
     * Getter
     * @return
     */
    public double getInterest() {
        return interest;
    }

    /**
     * Setter
     * @param interest
     */
    public void setInterest(final double interest) {
        this.interest = interest;
    }
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
    public String getAccount() {
        return account;
    }

    /**
     * Setter
     * @param account
     */
    public void setAccount(final String account) {
        this.account = account;
    }
    /**
     * Getter
     * @return
     */
    public Convert getConvert() {
        return convert;
    }

    /**
     * Setter
     * @param convert
     */
    public void setConvert(final Convert convert) {
        this.convert = convert;
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
     * Schimbarea dobanzii unui cont de
     * economii
     */
    public void executeCommand() {
        Accounts acc = null;
        int idx = -1;
        for (int j = 0; j < getUsers().size(); j++) {
            for (int k = 0; k < getUsers().get(j).getAccounts().size(); k++) {
                if (getUsers().get(j).getAccounts().get(k).getIban().equals(account)) {
                    acc = getUsers().get(j).getAccounts().get(k);
                    idx = j;
                }
            }
        }

        if (acc != null && !acc.getType().equals("savings")) {
            convert.interestRate(timestamp, "changeInterestRate");
        } else if (acc != null && acc.getType().equals("savings")) {
            acc.setInterestRate(interest);
            Transactions tr = new Transactions();
            tr.setDescription("Interest rate of the account changed to " + interest);
            tr.setTimestamp(timestamp);
            getUsers().get(idx).getTransactions().add(tr);

        }
    }
}
