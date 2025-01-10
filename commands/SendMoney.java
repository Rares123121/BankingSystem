package org.poo.commands;

import org.poo.convert.Convert;
import org.poo.currency.CurrencyConverter;
import org.poo.currency.CurrencyVisitor;
import org.poo.transactions.Transactions;
import org.poo.user.User;

import java.util.ArrayList;

public class SendMoney implements CommandExecuter {
    private CurrencyConverter c;
    private String account, recevier, email;
    private double amount;
    private ArrayList<User> users;
    private Convert convert;
    private CurrencyVisitor visitor;
    private Transactions trSender, trReceiver;

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
    public double getAmount() {
        return amount;
    }

    /**
     * Setter
     * @param amount
     */
    public void setAmount(final double amount) {
        this.amount = amount;
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

    public SendMoney(final CurrencyConverter c, final String account, final String recevier,
                     final String email, final double amount, final ArrayList<User> users,
                     final Convert convert,
                     final CurrencyVisitor visitor, final Transactions trSender,
                     final Transactions trReceiver) {
        this.c = c;
        this.account = account;
        this.recevier = recevier;
        this.email = email;
        this.amount = amount;
        this.users = users;
        this.convert = convert;
        this.visitor = visitor;
        this.trSender = trSender;
        this.trReceiver = trReceiver;
    }

    /**
     * Se prelucreaza comanda prin care se trimit
     * bani dintr-un cont in altul
     */
    public void executeCommand() {
        int idxAccount = -1, idxUser = -1, idxRecevier = -1, idxUserRecevier = -1;
        for (int j = 0; j < getUsers().size(); j++) {
            if (getUsers().get(j).getEmail().equals(email)) {
                idxUser = j;
            }
        }

        if (idxUser != -1) {
            int size = getUsers().get(idxUser).getAccounts().size();
            String accountCurrency = "";
            String recevierCurrency = "";
            for (int j = 0; j < size; j++) {
                if (getUsers().get(idxUser).getAccounts().get(j).getIban().equals(account)) {
                    idxAccount = j;
                }
            }
            if (idxAccount != -1) {
                accountCurrency = getUsers().get(idxUser).getAccounts().
                        get(idxAccount).getCurrency();

                for (int j = 0; j < getUsers().size(); j++) {
                    for (int k = 0; k < getUsers().get(j).getAccounts().size(); k++) {
                        if (getUsers().get(j).getAccounts().get(k).getIban().equals(recevier)) {
                            idxRecevier = k;
                            idxUserRecevier = j;
                            recevierCurrency = getUsers().get(j).getAccounts().
                                    get(k).getCurrency();
                        } else if (getUsers().get(j).getAccounts().get(k).getAlias() != null
                                && getUsers().get(j).getAccounts().get(k).
                                getAlias().equals(recevier)) {
                            idxRecevier = k;
                            idxUserRecevier = j;
                            recevierCurrency = getUsers().get(j).getAccounts().
                                    get(k).getCurrency();
                        }
                    }
                }

                if (idxRecevier != -1) {
                    double money = getUsers().get(idxUser).getAccounts().
                            get(idxAccount).getBalance();
                    ArrayList<Double> changed = c.accept(visitor, accountCurrency,
                            recevierCurrency, amount);
                    double amountChanged = amount;
                    for (int k = 0; k < changed.size(); k++) {
                        amountChanged = amountChanged * changed.get(k);
                    }

                    if (money >= amount) {
                        double moneyRecevier = getUsers().get(idxUserRecevier).getAccounts().
                                get(idxRecevier).getBalance();
                        getUsers().get(idxUser).getAccounts().get(idxAccount).
                                setBalance(money - amount);
                        getUsers().get(idxUserRecevier).getAccounts().
                                get(idxRecevier).setBalance(moneyRecevier + amountChanged);

                        trSender.setSenderIBAN(account);
                        trSender.setRecevierIBAN(recevier);
                        trSender.setAmount(amount);
                        trSender.setCurrency(accountCurrency);
                        trSender.setTransferType("sent");
                        getUsers().get(idxUser).getTransactions().add(trSender);
                        getUsers().get(idxUser).getAccounts().get(idxAccount).
                                getAllTransactions().add(trSender);

                        trReceiver.setSenderIBAN(account);
                        trReceiver.setRecevierIBAN(recevier);
                        trReceiver.setAmount(amountChanged);
                        trReceiver.setCurrency(recevierCurrency);
                        trReceiver.setTransferType("received");
                        getUsers().get(idxUserRecevier).getTransactions().add(trReceiver);
                        getUsers().get(idxUserRecevier).getAccounts().get(idxRecevier).
                                getAllTransactions().add(trReceiver);

                        ArrayList<Double> RON = c.accept(visitor, accountCurrency,
                                "RON", amount);
                        double inRon = amount;
                        for (int k = 0; k < RON.size(); k++) {
                            inRon = inRon * RON.get(k);
                        }

                        if(getUsers().get(idxUser).getAccounts().get(idxAccount).getAccountPlan().equals("standard")) {

                            getUsers().get(idxUser).getAccounts().get(idxAccount).setBalance(
                                    getUsers().get(idxUser).getAccounts().get(idxAccount).getBalance() -
                                            amountChanged * 0.2 / 100);
                        } else if(getUsers().get(idxUser).getAccounts().get(idxAccount).getAccountPlan().equals("silver")) {
                            if(inRon >= 500) {
                                getUsers().get(idxUser).getAccounts().get(idxAccount).setBalance(
                                        getUsers().get(idxUser).getAccounts().get(idxAccount).getBalance() -
                                                amountChanged * 0.1 / 100);
                            }
                        }
                    } else {
                        trSender.setDescription("Insufficient funds");
                        getUsers().get(idxUser).getTransactions().add(trSender);
                        getUsers().get(idxUser).getAccounts().get(idxAccount).
                                getAllTransactions().add(trSender);
                    }
                }
            }
        }
    }
}
