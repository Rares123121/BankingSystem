package org.poo.commands;

import org.poo.accounts.Card;
import org.poo.convert.Convert;
import org.poo.transactions.Transactions;
import org.poo.user.User;

import java.util.ArrayList;

public class CheckCard implements CommandExecuter {
    private String cardNumber;
    private ArrayList<User> users;
    private Convert convert;
    private int timestamp;
    public static final int DIFF = 30;
    /**
     * Constructor pentru clasa
     * @param cardNumber
     * @param users
     * @param convert
     * @param timestamp
     */
    public CheckCard(final String cardNumber, final ArrayList<User> users, final Convert convert,
                     final int timestamp) {
        this.cardNumber = cardNumber;
        this.users = users;
        this.convert = convert;
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
     * Se verifica status-ul unui cont:
     * daca are mai putini bani decat balanta mininma
     * sau e foarte aproape de aceasta
     */
    public void executeCommand() {
        String status = "";
        int ok = 0;
        for (int j = 0; j < getUsers().size(); j++) {
            for (int k = 0; k < getUsers().get(j).getAccounts().size(); k++) {
                for (int l = 0; l < getUsers().get(j).getAccounts().get(k).
                        getCards().size(); l++) {
                    if (getUsers().get(j).getAccounts().get(k).getCards().
                            get(l).getNumber().equals(cardNumber)) {
                        ok = 1;
                        Card card = getUsers().get(j).getAccounts().get(k).getCards().get(l);
                        if (card.getPayed() == 0 || getUsers().get(j).getAccounts().
                                get(k).getBalance()
                                < getUsers().get(j).getAccounts().get(k).getMinBalance()
                                || getUsers().get(j).getAccounts().get(k).getBalance() == 0) {
                            status = "frozen";
                            if (!card.getStatus().equals("frozen")) {
                                card.setStatus(status);
                                Transactions tr = new Transactions();
                                tr.setDescription("You have reached the minimum "
                                        + "amount of funds, the card will be frozen");
                                tr.setTimestamp(timestamp);
                                getUsers().get(j).getTransactions().add(tr);
                            } else {
                                Transactions tr = new Transactions();
                                tr.setDescription("The card is frozen");
                                tr.setTimestamp(timestamp);
                                getUsers().get(j).getTransactions().add(tr);
                            }
                        } else if (getUsers().get(j).getAccounts().get(k).getBalance()
                                - getUsers().get(j).getAccounts().get(k).getMinBalance()
                                <= DIFF) {
                            status = "warning";
                            card.setStatus(status);
                        }

                    }
                }
            }
        }
        if (ok == 0) {
            convert.checkStatus(timestamp, "Card not found");
        }
    }
}
