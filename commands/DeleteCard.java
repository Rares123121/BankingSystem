package org.poo.commands;

import org.poo.convert.Convert;
import org.poo.transactions.Transactions;
import org.poo.user.User;

import java.util.ArrayList;

public class DeleteCard implements CommandExecuter {
    private String cardNumber, email;
    private ArrayList<User> users;
    private int timestamp;
    private Convert convert;
    private Transactions transactions;
    public DeleteCard(final String cardNumber, final String email, final ArrayList<User> users,
                      final int timestamp, final Convert convert,
                      final Transactions transactions) {
        this.cardNumber = cardNumber;
        this.email = email;
        this.users = users;
        this.timestamp = timestamp;
        this.convert = convert;
        this.transactions = transactions;
    }

    /**
     * Getter
     * @return
     */
    public Transactions getTransactions() {
        return transactions;
    }

    /**
     * Setter
     * @param transactions
     */
    public void setTransactions(final Transactions transactions) {
        this.transactions = transactions;
    }

    /**
     * Getter
     * @return
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Setter
     * @param cardNumber
     */
    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
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
     * Comanda de a sterge un card dintr-un
     * cont
     */
    public void executeCommand() {
        int idx = -1;
        for (int j = 0; j < getUsers().size(); j++) {
            if (getUsers().get(j).getEmail().equals(email)) {
                idx = j;
            }
        }
        if (idx == -1) {
            convert.deleteCard(timestamp);
        } else {
            int size = getUsers().get(idx).getAccounts().size();
            int ok = 0;
            for (int j = 0; j < size && ok == 0; j++) {
                int sizeCards = getUsers().get(idx).getAccounts().get(j).getCards().size();
                for (int k = 0; k < sizeCards; k++) {
                    if (getUsers().get(idx).getAccounts().get(j).getCards().
                            get(k).getNumber().equals(cardNumber)) {
                        getUsers().get(idx).getAccounts().get(j).getCards().remove(k);
                        transactions.setTimestamp(timestamp);
                        transactions.setCardNumber(cardNumber);
                        transactions.setAccount(getUsers().get(idx).getAccounts().
                                get(j).getIban());
                        transactions.setDescription("The card has been destroyed");
                        transactions.setEmail(email);
                        getUsers().get(idx).getTransactions().add(transactions);
                        getUsers().get(idx).getAccounts().get(j).getAllTransactions().
                                add(transactions);

                        ok = 1;
                    }
                }
            }

        }
    }
}
