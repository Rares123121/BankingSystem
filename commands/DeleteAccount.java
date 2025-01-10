package org.poo.commands;

import org.poo.convert.Convert;
import org.poo.transactions.Transactions;
import org.poo.user.User;

import java.util.ArrayList;

public class DeleteAccount implements CommandExecuter {
    private String email;
    private ArrayList<User> users;
    private Convert convert;
    private int timestamp;
    private String account;
    public DeleteAccount(final String email, final ArrayList<User> users, final Convert convert,
                         final int timestamp, final String account) {
        this.email = email;
        this.users = users;
        this.convert = convert;
        this.timestamp = timestamp;
        this.account = account;
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
     * Comanda de a sterge un cont din
     * dreptul unui user
     */
    public void executeCommand() {
        int idx = -1;
        for (int j = 0; j < getUsers().size(); j++) {
            if (getUsers().get(j).getEmail().equals(email)) {
                idx = j;
            }
        }
        if (idx == -1) {
            convert.deleteFailed(timestamp);
        } else {
            int idxAccount = -1, noMoney = 0;
            for (int k = 0; k < getUsers().get(idx).getAccounts().size(); k++) {
                if (getUsers().get(idx).getAccounts().get(k).getIban().equals(account)) {
                    idxAccount = k;
                    if (getUsers().get(idx).getAccounts().get(k).getBalance() == 0) {
                        noMoney = 1;
                    }
                }
            }
            if (noMoney == 1 && idxAccount != -1) {
                int size = getUsers().get(idx).getAccounts().get(idxAccount).getCards().size();
                for (int k = 0; k < size; k++) {
                    getUsers().get(idx).getAccounts().get(idxAccount).getCards().remove(k);
                }
                getUsers().get(idx).getAccounts().remove(idxAccount);
                convert.deleteSuccess(timestamp);
            } else if (noMoney == 0 && idxAccount != -1) {
                convert.deleteNoMoney(timestamp);
                Transactions tr = new Transactions();
                tr.setDescription("Account couldn't be deleted - there are funds remaining");
                tr.setTimestamp(timestamp);
                getUsers().get(idx).getTransactions().add(tr);
                getUsers().get(idx).getAccounts().get(idxAccount).getAllTransactions().add(tr);
            }
        }
    }
}
