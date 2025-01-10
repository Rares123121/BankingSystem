package org.poo.commands;

import org.poo.user.User;

import java.util.ArrayList;

public class SetAlias implements CommandExecuter {
    private String email, account, alias;
    private ArrayList<User> users;

    /**
     * Getter
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     * @param email
     */
    public void setEmail(final String email) {
        this.email = email;
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

    public SetAlias(final String email, final String account, final String alias,
                    final ArrayList<User> users) {
        this.email = email;
        this.account = account;
        this.alias = alias;
        this.users = users;
    }

    /**
     * Se asociaza unui cont un alias
     */
    public void executeCommand() {
        int idxUser = -1;
        for (int j = 0; j < getUsers().size(); j++) {
            if (getUsers().get(j).getEmail().equals(email)) {
                idxUser = j;
            }
        }

        if (idxUser != -1) {
            int size = getUsers().get(idxUser).getAccounts().size();
            for (int k = 0; k < size; k++) {
                if (getUsers().get(idxUser).getAccounts().get(k).getIban().equals(account)) {
                    getUsers().get(idxUser).getAccounts().get(k).setAlias(alias);
                }
            }
        }
    }
}
