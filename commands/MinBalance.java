package org.poo.commands;

import org.poo.user.User;

import java.util.ArrayList;

public class MinBalance implements CommandExecuter {
    private String account;
    private ArrayList<User> users;
    private double minBalance;

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


    public MinBalance(final String account, final ArrayList<User> users,
                      final double minBalance) {
        this.account = account;
        this.users = users;
        this.minBalance = minBalance;
    }

    /**
     * Se seteaza balanta minima a
     * unui cont
     */
    public void executeCommand() {
        int idx = 0, idxAccount = 0;
        for (int j = 0; j < getUsers().size(); j++) {
            for (int k = 0; k < getUsers().get(j).getAccounts().size(); k++) {
                if (getUsers().get(j).getAccounts().get(k).getIban().equals(account)) {
                    idx = j;
                    idxAccount = k;
                }
            }
        }

        getUsers().get(idx).getAccounts().get(idxAccount).setMinBalance(minBalance);
    }
}
