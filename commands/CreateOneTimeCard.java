package org.poo.commands;

import org.poo.accounts.Card;
import org.poo.accounts.CardFactory;
import org.poo.transactions.Transactions;
import org.poo.user.User;

import java.util.ArrayList;

import static org.poo.utils.Utils.generateCardNumber;

public class CreateOneTimeCard implements CommandExecuter {
    private String account;
    private ArrayList<User> users;
    private int timestamp;
    private Transactions transaction;

    public CreateOneTimeCard(final String account, final ArrayList<User> users,
                             final int timestamp,
                             final Transactions transaction) {
        this.account = account;
        this.users = users;
        this.timestamp = timestamp;
        this.transaction = transaction;
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
     * Comanda de creare a unui card
     * cu care se poate realiza o singura plata
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

        Card card = CardFactory.createCard(generateCardNumber(),
                "active", timestamp, 0);
        getUsers().get(idx).getAccounts().get(idxAccount).getCards().add(card);
        transaction.setTimestamp(timestamp);
        transaction.setCardNumber(card.getNumber());
        transaction.setEmail(getUsers().get(idx).getEmail());
        transaction.setAccount(account);
        getUsers().get(idx).getTransactions().add(transaction);
        getUsers().get(idx).getAccounts().get(idxAccount).getAllTransactions().add(transaction);
    }
}
