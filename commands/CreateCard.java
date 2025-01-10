package org.poo.commands;
import org.poo.accounts.Card;
import org.poo.accounts.CardFactory;
import org.poo.transactions.Transactions;
import org.poo.user.User;
import org.poo.utils.Utils;

import java.util.ArrayList;


public class CreateCard implements CommandExecuter {
    private ArrayList<User> users;
    private String account;
    private int timestamp;
    private Transactions transaction;
    private String email;

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
     * Contructorul clasei
     * @param users
     * @param account
     * @param timestamp
     * @param transaction
     * @param email
     */
    public CreateCard(final ArrayList<User> users, final String account, final int timestamp,
                      final Transactions transaction, final String email) {
        this.users = users;
        this.account = account;
        this.timestamp = timestamp;
        this.transaction = transaction;
        this.email = email;
    }

    /**
     * Metoda creeaza un card
     */
    public void executeCommand() {
        int idx = -1, idxAccount = -1;
        for (int j = 0; j < getUsers().size(); j++) {
            for (int k = 0; k < getUsers().get(j).getAccounts().size(); k++) {
                if (getUsers().get(j).getAccounts().get(k).getIban().equals(account)) {
                    idx = j;
                    idxAccount = k;
                }
            }
        }
        if (idx != -1) {
            if (getUsers().get(idx).getEmail().equals(email)) {
                Card card = CardFactory.createCard(Utils.generateCardNumber(),
                        "active", getTimestamp(), 1);
                getUsers().get(idx).getAccounts().get(idxAccount).getCards().add(card);
                transaction.setTimestamp(getTimestamp());
                transaction.setAccount(account);
                transaction.setCardNumber(card.getNumber());
                transaction.setEmail(getUsers().get(idx).getEmail());
                getUsers().get(idx).getTransactions().add(transaction);
                getUsers().get(idx).getAccounts().get(idxAccount).getAllTransactions().
                        add(transaction);
            }
        }
    }
}
