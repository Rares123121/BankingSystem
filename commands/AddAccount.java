package org.poo.commands;
import org.poo.accounts.AccountFactory;
import org.poo.accounts.Accounts;
import org.poo.convert.Convert;
import org.poo.fileio.CommandInput;
import org.poo.transactions.Transactions;
import org.poo.user.User;
import org.poo.utils.Utils;

import java.util.ArrayList;

public class AddAccount implements CommandExecuter {
    private ArrayList<User> users;
    private CommandInput command;
    private Convert convert;
    private Transactions transactions;
    private int timestamp;

    /**
     * Constructor
     * @param users
     * @param commandInput
     * @param convert
     * @param transactions
     * @param timestamp
     */
    public AddAccount(final ArrayList<User> users, final CommandInput commandInput,
                      final Convert convert, final Transactions transactions,
                      final int timestamp) {
        this.users = users;
        this.command = commandInput;
        this.convert = convert;
        this.transactions = transactions;
        this.timestamp = timestamp;
    }

    /**
     * Getter pentru timestamp
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
     * Getter pentru tranzactii
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
    public CommandInput getCommand() {
        return command;
    }

    /**
     * Setter
     * @param command
     */
    public void setCommand(final CommandInput command) {
        this.command = command;
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
     * Metoda care executa comanda de adauga un cont
     */
    public void executeCommand() {
        String email = command.getEmail();
        int idx = -1;
        for (int j = 0; j < getUsers().size(); j++) {
            if (getUsers().get(j).getEmail().equals(email)) {
                idx = j;
            }
        }

        if (idx != -1) {
            transactions.setTimestamp(timestamp);
            Accounts acc = AccountFactory.createAccounts(Utils.generateIBAN(), command.getCurrency(),
                    command.getAccountType(), command.getTimestamp());
            if(acc != null) {
                if (acc.getType().equals("savings")) {
                    acc.setInterestRate(command.getInterestRate());
                }
                if(getUsers().get(idx).getOccupation().equals("student"))
                    acc.setAccountPlan("student");
                else
                    acc.setAccountPlan("standard");
                int idxAcc = -1;
                getUsers().get(idx).getAccounts().add(acc);
                getUsers().get(idx).getTransactions().add(transactions);
                for (int i = 0; i < getUsers().get(idx).getAccounts().size(); i++) {
                    if (getUsers().get(idx).getAccounts().get(i).getIban().equals(acc.getIban())) {
                        idxAcc = i;
                    }
                }
                getUsers().get(idx).getAccounts().get(idxAcc).getAllTransactions().add(transactions);
            }
        }
    }
}
