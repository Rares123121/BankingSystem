package org.poo.commands;

import org.poo.convert.Convert;
import org.poo.user.User;

import java.util.ArrayList;

public class Spendings implements CommandExecuter {
    private int start, end, timestamp;
    private String account;
    private ArrayList<User> users;
    private Convert convert;

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

    public Spendings(final int start, final int end, final int timestamp, final String account,
                     final ArrayList<User> users, final Convert convert) {
        this.start = start;
        this.end = end;
        this.timestamp = timestamp;
        this.account = account;
        this.users = users;
        this.convert = convert;
    }

    /**
     * Se genereaza un raport al cheltuielilor
     * pentru fiecare card
     */
    public void executeCommand() {
        int idxUser = -1, idxAccount = -1;
        for (int j = 0; j < getUsers().size(); j++) {
            for (int k = 0; k < getUsers().get(j).getAccounts().size(); k++) {
                if (getUsers().get(j).getAccounts().get(k).getIban().equals(account)) {
                    idxUser = j;
                    idxAccount = k;
                }
            }
        }
        if (idxAccount == -1) {
            convert.spendingError(timestamp);
        } else if (!getUsers().get(idxUser).getAccounts().get(idxAccount).
                getType().equals("classic")) {
            convert.spendingNotSaving(timestamp);
        } else if (idxUser != -1) {
            convert.spendingsReport(timestamp, start, end,
                    getUsers().get(idxUser).getAccounts().get(idxAccount).
                            getTransactions(),
                    getUsers().get(idxUser).getAccounts().get(idxAccount));
        }
    }
}
