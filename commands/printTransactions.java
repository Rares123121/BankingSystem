package org.poo.commands;

import org.poo.convert.Convert;
import org.poo.user.User;

import java.util.ArrayList;

public class printTransactions implements CommandExecuter {

    private String email;
    private int timestamp;
    private Convert convert;
    private ArrayList<User> users;

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

    public printTransactions(final String email, final int timestamp, final Convert convert,
                             final ArrayList<User> users) {
        this.email = email;
        this.timestamp = timestamp;
        this.convert = convert;
        this.users = users;
    }

    /**
     * Se afiseaza tranzactiile unui user
     * in format JSON
     */
    public void executeCommand() {
        int idx = -1;
        for (int j = 0; j < getUsers().size(); j++) {
            if (getUsers().get(j).getEmail().equals(email)) {
                idx = j;
            }
        }
        convert.transactions(getUsers().get(idx).getTransactions(), timestamp);
    }
}
