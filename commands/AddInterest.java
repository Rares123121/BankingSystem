package org.poo.commands;

import org.poo.accounts.Accounts;
import org.poo.convert.Convert;
import org.poo.user.User;

import java.util.ArrayList;

public class AddInterest implements CommandExecuter {
    private String account;
    private Convert convert;
    private ArrayList<User> users;
    private int timestamp;

    /**
     * Constructorul clasei
     * @param account
     * @param convert
     * @param users
     * @param timestamp
     */
    public AddInterest(final String account, final Convert convert, final ArrayList<User> users,
                       final int timestamp) {
        this.account = account;
        this.convert = convert;
        this.users = users;
        this.timestamp = timestamp;
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
     * Metoda se ocupa cu adaugarea dobanzii
     */
    public void executeCommand() {

        Accounts acc = null;
        for (int j = 0; j < getUsers().size(); j++) {
            for (int k = 0; k < getUsers().get(j).getAccounts().size(); k++) {
                if (getUsers().get(j).getAccounts().get(k).getIban().equals(account)) {
                    acc = getUsers().get(j).getAccounts().get(k);
                }
            }
        }

        if (acc != null && !acc.getType().equals("savings")) {
            convert.interestRate(timestamp, "addInterest");
        } else if (acc != null && acc.getType().equals("savings")) {
            double balance = acc.getBalance();
            balance = balance * acc.getInterestRate();
            acc.setBalance(balance);
        }
    }
}
