package org.poo.commands;

import org.poo.convert.Convert;
import org.poo.user.User;

import java.util.ArrayList;

public class Report implements CommandExecuter {
    private int start, end, timestamp;
    private String account;
    private ArrayList<User> users;
    private Convert convert;

    public Report(final int end, final int start, final int timestamp, final String account,
                  final ArrayList<User> users, final Convert convert) {
        this.end = end;
        this.start = start;
        this.timestamp = timestamp;
        this.account = account;
        this.users = users;
        this.convert = convert;
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
     * Se genereaza raportul tranzactiilor
     * facute in dreptul unui cont
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

        if (idxUser != -1 && idxAccount != -1) {
        convert.report(timestamp,
                getUsers().get(idxUser).getAccounts().get(idxAccount).getAllTransactions(),
                start, end,
                getUsers().get(idxUser).getAccounts().get(idxAccount));
        }
        if (idxAccount == -1) {
            convert.reportAccountDNE(timestamp);
        }
    }
}
