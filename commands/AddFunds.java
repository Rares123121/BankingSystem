package org.poo.commands;

import org.poo.user.User;

import java.util.ArrayList;

public class AddFunds implements CommandExecuter {
    private String account;
    private double amount;
    private ArrayList<User> users;

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
     * Constructor
     * @param account
     * @param amount
     * @param users
     */
    public AddFunds(final String account, final double amount, final ArrayList<User> users) {
        this.account = account;
        this.amount = amount;
        this.users = users;
    }

    /**
     * Metoda se ocupa de adaugarea unor fonduri
     * intr-un cont
     */
    public void executeCommand() {
        for (int j = 0; j < getUsers().size(); j++) {
            for (int k = 0; k < getUsers().get(j).getAccounts().size(); k++) {
                if (getUsers().get(j).getAccounts().get(k).getIban().equals(account)) {
                    getUsers().get(j).getAccounts().get(k).setBalance(getUsers().get(j).
                            getAccounts().get(k).getBalance() + amount);
                }
            }
        }
    }

}
