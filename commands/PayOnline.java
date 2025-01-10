package org.poo.commands;

import org.poo.accounts.Card;
import org.poo.accounts.CardFactory;
import org.poo.commerciants.Commerciants;
import org.poo.convert.Convert;
import org.poo.currency.CurrencyConverter;
import org.poo.currency.CurrencyVisitor;
import org.poo.fileio.CommerciantInput;
import org.poo.transactions.Transactions;
import org.poo.user.User;

import java.util.ArrayList;

import static org.poo.utils.Utils.generateCardNumber;

public class PayOnline implements CommandExecuter {
    private CurrencyConverter c;
    private String cardNumber, email, currency, commerciant;
    private double amount;
    private int timestamp;
    private ArrayList<User> users;
    private Convert convert;
    private CurrencyVisitor visitor;
    private Transactions transactions;
    private ArrayList<CommerciantInput> tech, food, clothes;
    private CommerciantInput[] commerciants;
    /**
     * Getter
     * @return
     */
    public String getCommerciant() {
        return commerciant;
    }

    /**
     * Setter
     * @param commerciant
     */
    public void setCommerciant(final String commerciant) {
        this.commerciant = commerciant;
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
    public String getCurrency() {
        return currency;
    }

    /**
     * Setter
     * @param currency
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
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

    public PayOnline(final CurrencyConverter c, final String cardNumber, final String email,
                     final String currency, final double amount, final int timestamp,
                     final ArrayList<User> users, final Convert convert,
                     final CurrencyVisitor visitor, final Transactions transactions,
                     final String commerciant, final ArrayList<CommerciantInput> tech,
                     final ArrayList<CommerciantInput> food, final ArrayList<CommerciantInput> clothes,
                     final CommerciantInput[] commerciants) {
        this.c = c;
        this.cardNumber = cardNumber;
        this.email = email;
        this.currency = currency;
        this.amount = amount;
        this.timestamp = timestamp;
        this.users = users;
        this.convert = convert;
        this.visitor = visitor;
        this.transactions = transactions;
        this.commerciant = commerciant;
        this.tech = tech;
        this.food = food;
        this.clothes = clothes;
        this.commerciants = commerciants;
    }

    /**
     * Se creeaza o noua tranzactie atunci cand
     * un nou card este generat
     * @param ts
     * @param cardNr
     * @param email2
     * @param account2
     * @param newCard
     * @return
     */
    public Transactions newCard(final int ts, final String cardNr,
                                final String email2, final String account2,
                                final Transactions newCard) {
        newCard.setDescription("New card created");
        newCard.setCommand("createOneTimeCard");
        newCard.setTimestamp(ts);
        newCard.setCardNumber(cardNr);
        newCard.setEmail(email2);
        newCard.setAccount(account2);
        return newCard;
    }

    /**
     * Atunci cand se face o plata cu cardul
     * se creeaza o noua tranzactie
     * @param commerciant1
     * @param amount1
     * @param timestamp1
     * @param forCard
     * @return
     */
    public Transactions forCard(final String commerciant1, final double amount1,
                                final int timestamp1, final Transactions forCard) {
        forCard.setDescription("Card payment");
        forCard.setCommerciant(commerciant1);
        forCard.setAmount(amount1);
        forCard.setTimestamp(timestamp1);
        return forCard;
    }

    /**
     * Se prelucreaza comanda prin care
     * se plateste cu un card
     */
    public void executeCommand() {
        int index = -1;
        for (int j = 0; j < getUsers().size(); j++) {
            if (getUsers().get(j).getEmail().equals(email)) {
                index = j;
            }
        }
        if (index != -1) {
            int sizeAccount = getUsers().get(index).getAccounts().size();
            int idxAccount = 0, idxCard = -1;
            String accountCurrency = "";
            for (int j = 0; j < sizeAccount; j++) {
                int sizeCards = getUsers().get(index).getAccounts().get(j).getCards().size();
                accountCurrency = getUsers().get(index).getAccounts().get(j).getCurrency();
                for (int k = 0; k < sizeCards; k++) {
                    if (getUsers().get(index).getAccounts().get(j).getCards().get(k).
                            getNumber().equals(cardNumber)) {
                        idxAccount = j;
                        idxCard = k;
                    }
                }
            }
            if (idxCard == -1) {
                convert.payOnline(timestamp);
            } else {
                if (!getUsers().get(index).getAccounts().get(idxAccount).
                        getCards().get(idxCard).getStatus().equals("frozen")) {
                    if (getUsers().get(index).getAccounts().get(idxAccount).
                            getCards().get(idxCard).getType() == 0) {
                        if (getUsers().get(index).getAccounts().get(idxAccount).
                                getCards().get(idxCard).getPayed() == 1) {
                            double money = getUsers().get(index).getAccounts().
                                    get(idxAccount).getBalance();
                            ArrayList<Double> changed = c.accept(visitor, currency,
                                    accountCurrency, amount);
                            double changedAmount = amount;
                            for (int k = 0; k < changed.size(); k++) {
                                changedAmount = changedAmount * changed.get(k);
                            }
                            if (money >= changedAmount) {
                                getUsers().get(index).getAccounts().get(idxAccount).
                                        setBalance(money - changedAmount);
                                getUsers().get(index).getAccounts().get(idxAccount).
                                        getCards().get(idxCard).setPayed(0);

                                Card card = CardFactory.createCard(generateCardNumber(),
                                        "active", timestamp, 0);


                                String type = null;
                                int ok = 0;
                                for(int k = 0; k < commerciants.length; k++) {
                                    if(commerciants[k].getCommerciant().equals(commerciant)) {
                                        type = commerciants[k].getType();
                                        if(commerciants[k].getCashbackStrategy().equals("nrOfTransactions")) {
                                            ok = 1;
                                        }
                                        if(commerciants[k].getCashbackStrategy().equals("spendingThreshold")) {
                                            ok = 2;
                                        }
                                    }
                                }
                                String plan = getUsers().get(index).getAccounts().get(idxAccount).getAccountPlan();
                                if(type != null) {
                                    if (type.equals("Tech")) {
                                        if(getUsers().get(index).getTech() == 2){
                                            getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 2 / 100) +
                                                    getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                        }
                                        if(getUsers().get(index).getTech() == 5){
                                            getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 5 / 100) +
                                                    getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                        }
                                        if(getUsers().get(index).getTech() == 10){
                                            getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 10 / 100) +
                                                    getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                        }

                                        if(ok == 1) {
                                            getUsers().get(index).setTech(getUsers().get(index).getTech() + 1);
                                        }
                                        if(ok == 2){
                                            ArrayList<Double> RON = c.accept(visitor, currency,
                                                    "RON", amount);
                                            double inRon = amount;
                                            for (int k = 0; k < RON.size(); k++) {
                                                inRon = inRon * RON.get(k);
                                            }
                                            getUsers().get(index).getAccounts().get(idxAccount).setSpendingTech(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() +
                                                    inRon);
                                            if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 100
                                                    && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 300){
                                                if(plan.equals("standard") || plan.equals("student")){
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.1 / 100);

                                                } else if (plan.equals("silver")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.3 / 100);
                                                } else if(plan.equals("gold")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.5 / 100);
                                                }
                                            } else if (getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 300
                                                    && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 500) {
                                                if(plan.equals("standard") || plan.equals("student")){
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.2 / 100);

                                                } else if (plan.equals("silver")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.4 / 100);
                                                } else if(plan.equals("gold")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.55 / 100);
                                                }
                                            } else if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 500) {
                                                if(plan.equals("standard") || plan.equals("student")){
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.25 / 100);

                                                } else if (plan.equals("silver")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.5 / 100);
                                                } else if(plan.equals("gold")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.7 / 100);
                                                }
                                            }
                                        }
                                        getUsers().get(index).getAccounts().get(idxAccount).setTotalTech(
                                                getUsers().get(index).getAccounts().get(idxAccount).getTotalTech() + changedAmount);

                                    }
                                    if (type.equals("Clothes")) {
                                        if(getUsers().get(index).getClothes() == 2){
                                            getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 2 / 100) +
                                                    getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                        }
                                        if(getUsers().get(index).getClothes() == 5){
                                            getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 5 / 100) +
                                                    getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                        }
                                        if(getUsers().get(index).getClothes() == 10){
                                            getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 10 / 100) +
                                                    getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                        }
                                        if(ok == 1) {
                                            getUsers().get(index).setClothes(getUsers().get(index).getClothes() + 1);
                                        }
                                        if(ok == 2){
                                            ArrayList<Double> RON = c.accept(visitor, currency,
                                                    "RON", amount);
                                            double inRon = amount;
                                            for (int k = 0; k < RON.size(); k++) {
                                                inRon = inRon * RON.get(k);
                                            }
                                            getUsers().get(index).getAccounts().get(idxAccount).setSpendingClothes(getUsers().get(index).getAccounts().get(idxAccount).getSpendingClothes() +
                                                    inRon);
                                            if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 100
                                                    && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 300){
                                                if(plan.equals("standard") || plan.equals("student")){
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.1 / 100);

                                                } else if (plan.equals("silver")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.3 / 100);
                                                } else if(plan.equals("gold")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.5 / 100);
                                                }
                                            } else if (getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 300
                                                    && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 500) {
                                                if(plan.equals("standard") || plan.equals("student")){
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.2 / 100);

                                                } else if (plan.equals("silver")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.4 / 100);
                                                } else if(plan.equals("gold")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.55 / 100);
                                                }
                                            } else if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 500) {
                                                if(plan.equals("standard") || plan.equals("student")){
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.25 / 100);

                                                } else if (plan.equals("silver")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.5 / 100);
                                                } else if(plan.equals("gold")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.7 / 100);
                                                }
                                            }
                                        }
                                        getUsers().get(index).getAccounts().get(idxAccount).setTotalClothes(
                                                getUsers().get(index).getAccounts().get(idxAccount).getTotalClothes() + changedAmount);
                                    }
                                    if (type.equals("Food")) {
                                        if(getUsers().get(index).getFood() == 2){
                                            getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 2 / 100) +
                                                    getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                        }
                                        if(getUsers().get(index).getFood() == 5){
                                            getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 5 / 100) +
                                                    getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                        }
                                        if(getUsers().get(index).getFood() == 10){
                                            getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 10 / 100) +
                                                    getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                        }
                                        if(ok == 1) {
                                            getUsers().get(index).setFood(getUsers().get(index).getFood() + 1);
                                        }
                                        if(ok == 2){
                                            ArrayList<Double> RON = c.accept(visitor, currency,
                                                    "RON", amount);
                                            double inRon = amount;
                                            for (int k = 0; k < RON.size(); k++) {
                                                inRon = inRon * RON.get(k);
                                            }
                                            getUsers().get(index).getAccounts().get(idxAccount).setSpendingFood(getUsers().get(index).getAccounts().get(idxAccount).getSpendingFood() +
                                                    inRon);
                                            if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 100
                                                    && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 300){
                                                if(plan.equals("standard") || plan.equals("student")){
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.1 / 100);

                                                } else if (plan.equals("silver")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.3 / 100);
                                                } else if(plan.equals("gold")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.5 / 100);
                                                }
                                            } else if (getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 300
                                                    && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 500) {
                                                if(plan.equals("standard") || plan.equals("student")){
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.2 / 100);

                                                } else if (plan.equals("silver")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.4 / 100);
                                                } else if(plan.equals("gold")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.55 / 100);
                                                }
                                            } else if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 500) {
                                                if(plan.equals("standard") || plan.equals("student")){
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.25 / 100);

                                                } else if (plan.equals("silver")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.5 / 100);
                                                } else if(plan.equals("gold")) {
                                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                    changedAmount * 0.7 / 100);
                                                }
                                            }
                                        }
                                        getUsers().get(index).getAccounts().get(idxAccount).setTotalFood(
                                                getUsers().get(index).getAccounts().get(idxAccount).getTotalFood() + changedAmount);
                                    }
                                }

                                ArrayList<Double> RON = c.accept(visitor, currency,
                                        "RON", amount);
                                double inRon = amount;
                                for (int k = 0; k < RON.size(); k++) {
                                    inRon = inRon * RON.get(k);
                                }

                                if(getUsers().get(index).getAccounts().get(idxAccount).getAccountPlan().equals("standard")) {
                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() -
                                                    changedAmount * 0.2 / 100);
                                } else if(getUsers().get(index).getAccounts().get(idxAccount).getAccountPlan().equals("silver")) {
                                    if(inRon >= 500) {
                                        getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                getUsers().get(index).getAccounts().get(idxAccount).getBalance() -
                                                        changedAmount * 0.1 / 100);
                                    }
                                }
                                transactions.setDescription("Card payment");
                                transactions.setCommerciant(commerciant);
                                transactions.setAmount(changedAmount);
                                transactions.setTimestamp(timestamp);
                                getUsers().get(index).getTransactions().add(transactions);

                                Transactions tr = new Transactions();
                                tr.setAccount(getUsers().get(index).getAccounts().
                                        get(idxAccount).getIban());
                                tr.setCardNumber(getUsers().get(index).getAccounts().
                                        get(idxAccount).getCards().get(idxCard).getNumber());
                                tr.setEmail(email);
                                tr.setDescription("The card has been destroyed");
                                tr.setTimestamp(timestamp);
                                getUsers().get(index).getTransactions().add(tr);

                                getUsers().get(index).getAccounts().get(idxAccount).
                                        getCards().remove(idxCard);

                                getUsers().get(index).getAccounts().get(idxAccount).
                                        getCards().add(card);
                                Transactions newCard = new Transactions();
                                newCard = newCard(timestamp, card.getNumber(), email,
                                        getUsers().get(index).getAccounts().get(idxAccount).
                                                getIban(), newCard);
                                getUsers().get(index).getTransactions().add(newCard);
                                getUsers().get(index).getAccounts().get(idxAccount).
                                        getAllTransactions().add(newCard);

                                Transactions forCard = new Transactions();
                                forCard = forCard(commerciant, changedAmount, timestamp, forCard);
                                getUsers().get(index).getAccounts().get(idxAccount).
                                        getTransactions().add(forCard);
                                getUsers().get(index).getAccounts().get(idxAccount).
                                        getAllTransactions().add(forCard);
                            } else {
                                transactions.setDescription("Insufficient funds");
                                transactions.setTimestamp(timestamp);
                                getUsers().get(index).getTransactions().add(transactions);

                                Transactions forCard = new Transactions();
                                forCard.setDescription("Insufficient funds");
                                forCard.setTimestamp(timestamp);
                                getUsers().get(index).getAccounts().get(idxAccount).
                                        getTransactions().add(forCard);
                            }
                        }
                    } else {
                        double money = getUsers().get(index).getAccounts().
                                get(idxAccount).getBalance();

                        ArrayList<Double> changed = c.accept(visitor, currency,
                                accountCurrency, amount);
                        double changedAmount = amount;
                        for (int k = 0; k < changed.size(); k++) {
                            changedAmount = changedAmount * changed.get(k);
                        }
                        if (money >= changedAmount) {
                            getUsers().get(index).getAccounts().get(idxAccount).setBalance(money
                                    - changedAmount);
                            String type = null;
                            int ok = 0;
                            for(int k = 0; k < commerciants.length; k++) {
                                if(commerciants[k].getCommerciant().equals(commerciant)) {
                                    type = commerciants[k].getType();
                                    if(commerciants[k].getCashbackStrategy().equals("nrOfTransactions")) {
                                        ok = 1;
                                    }
                                    if(commerciants[k].getCashbackStrategy().equals("spendingThreshold")) {
                                        ok = 2;
                                    }
                                }
                            }
                            String plan = getUsers().get(index).getAccounts().get(idxAccount).getAccountPlan();
                            if(type != null) {
                                if (type.equals("Tech")) {
                                    if(getUsers().get(index).getTech() == 2){
                                        getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 2 / 100) +
                                                getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                    }
                                    if(getUsers().get(index).getTech() == 5){
                                        getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 5 / 100) +
                                                getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                    }
                                    if(getUsers().get(index).getTech() == 10){
                                        getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 10 / 100) +
                                                getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                    }

                                    if(ok == 1) {
                                        getUsers().get(index).setTech(getUsers().get(index).getTech() + 1);
                                    }
                                    if(ok == 2){
                                        ArrayList<Double> RON = c.accept(visitor, currency,
                                                "RON", amount);
                                        double inRon = amount;
                                        for (int k = 0; k < RON.size(); k++) {
                                            inRon = inRon * RON.get(k);
                                        }
                                        getUsers().get(index).getAccounts().get(idxAccount).setSpendingTech(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() +
                                                inRon);
                                        if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 100
                                            && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 300){
                                            if(plan.equals("standard") || plan.equals("student")){
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.1 / 100);

                                            } else if (plan.equals("silver")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.3 / 100);
                                            } else if(plan.equals("gold")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.5 / 100);
                                            }
                                        } else if (getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 300
                                                && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 500) {
                                            if(plan.equals("standard") || plan.equals("student")){
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.2 / 100);

                                            } else if (plan.equals("silver")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.4 / 100);
                                            } else if(plan.equals("gold")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.55 / 100);
                                            }
                                        } else if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 500) {
                                            if(plan.equals("standard") || plan.equals("student")){
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.25 / 100);

                                            } else if (plan.equals("silver")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.5 / 100);
                                            } else if(plan.equals("gold")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.7 / 100);
                                            }
                                        }
                                    }
                                    getUsers().get(index).getAccounts().get(idxAccount).setTotalTech(
                                            getUsers().get(index).getAccounts().get(idxAccount).getTotalTech() + changedAmount);

                                }
                                if (type.equals("Clothes")) {
                                    if(getUsers().get(index).getClothes() == 2){
                                        getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 2 / 100) +
                                                getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                    }
                                    if(getUsers().get(index).getClothes() == 5){
                                        getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 5 / 100) +
                                                getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                    }
                                    if(getUsers().get(index).getClothes() == 10){
                                        getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 10 / 100) +
                                                getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                    }
                                    if(ok == 1) {
                                        getUsers().get(index).setClothes(getUsers().get(index).getClothes() + 1);
                                    }
                                    if(ok == 2){
                                        ArrayList<Double> RON = c.accept(visitor, currency,
                                                "RON", amount);
                                        double inRon = amount;
                                        for (int k = 0; k < RON.size(); k++) {
                                            inRon = inRon * RON.get(k);
                                        }
                                        getUsers().get(index).getAccounts().get(idxAccount).setSpendingClothes(getUsers().get(index).getAccounts().get(idxAccount).getSpendingClothes() +
                                                inRon);
                                        if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 100
                                                && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 300){
                                            if(plan.equals("standard") || plan.equals("student")){
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.1 / 100);

                                            } else if (plan.equals("silver")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.3 / 100);
                                            } else if(plan.equals("gold")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.5 / 100);
                                            }
                                        } else if (getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 300
                                                && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 500) {
                                            if(plan.equals("standard") || plan.equals("student")){
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.2 / 100);

                                            } else if (plan.equals("silver")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.4 / 100);
                                            } else if(plan.equals("gold")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.55 / 100);
                                            }
                                        } else if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 500) {
                                            if(plan.equals("standard") || plan.equals("student")){
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.25 / 100);

                                            } else if (plan.equals("silver")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.5 / 100);
                                            } else if(plan.equals("gold")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.7 / 100);
                                            }
                                        }
                                    }
                                    getUsers().get(index).getAccounts().get(idxAccount).setTotalClothes(
                                            getUsers().get(index).getAccounts().get(idxAccount).getTotalClothes() + changedAmount);
                                }
                                if (type.equals("Food")) {
                                    if(getUsers().get(index).getFood() == 2){
                                        getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 2 / 100) +
                                                getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                    }
                                    if(getUsers().get(index).getFood() == 5){
                                        getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 5 / 100) +
                                                getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                    }
                                    if(getUsers().get(index).getFood() == 10){
                                        getUsers().get(index).getAccounts().get(idxAccount).setBalance((changedAmount * 10 / 100) +
                                                getUsers().get(index).getAccounts().get(idxAccount).getBalance());
                                    }
                                    if(ok == 1) {
                                        getUsers().get(index).setFood(getUsers().get(index).getFood() + 1);
                                    }
                                    if(ok == 2){
                                        ArrayList<Double> RON = c.accept(visitor, currency,
                                                "RON", amount);
                                        double inRon = amount;
                                        for (int k = 0; k < RON.size(); k++) {
                                            inRon = inRon * RON.get(k);
                                        }
                                        getUsers().get(index).getAccounts().get(idxAccount).setSpendingFood(getUsers().get(index).getAccounts().get(idxAccount).getSpendingFood() +
                                                inRon);
                                        if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 100
                                                && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 300){
                                            if(plan.equals("standard") || plan.equals("student")){
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.1 / 100);

                                            } else if (plan.equals("silver")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.3 / 100);
                                            } else if(plan.equals("gold")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.5 / 100);
                                            }
                                        } else if (getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 300
                                                && getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() < 500) {
                                            if(plan.equals("standard") || plan.equals("student")){
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.2 / 100);

                                            } else if (plan.equals("silver")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.4 / 100);
                                            } else if(plan.equals("gold")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.55 / 100);
                                            }
                                        } else if(getUsers().get(index).getAccounts().get(idxAccount).getSpendingTech() >= 500) {
                                            if(plan.equals("standard") || plan.equals("student")){
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.25 / 100);

                                            } else if (plan.equals("silver")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.5 / 100);
                                            } else if(plan.equals("gold")) {
                                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() +
                                                                changedAmount * 0.7 / 100);
                                            }
                                        }
                                    }
                                    getUsers().get(index).getAccounts().get(idxAccount).setTotalFood(
                                            getUsers().get(index).getAccounts().get(idxAccount).getTotalFood() + changedAmount);
                                }
                            }

                            ArrayList<Double> RON = c.accept(visitor, currency,
                                    "RON", amount);
                            double inRon = amount;
                            for (int k = 0; k < RON.size(); k++) {
                                inRon = inRon * RON.get(k);
                            }

                            if(getUsers().get(index).getAccounts().get(idxAccount).getAccountPlan().equals("standard")) {
                                getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                        getUsers().get(index).getAccounts().get(idxAccount).getBalance() -
                                                changedAmount * 0.2 / 100);
                            } else if(getUsers().get(index).getAccounts().get(idxAccount).getAccountPlan().equals("silver")) {
                                if(inRon >= 500) {
                                    getUsers().get(index).getAccounts().get(idxAccount).setBalance(
                                            getUsers().get(index).getAccounts().get(idxAccount).getBalance() -
                                                    changedAmount * 0.1 / 100);
                                }
                            }
                            transactions.setDescription("Card payment");
                            transactions.setCommerciant(commerciant);
                            transactions.setAmount(changedAmount);
                            transactions.setTimestamp(timestamp);
                            getUsers().get(index).getTransactions().add(transactions);
                            getUsers().get(index).getAccounts().get(idxAccount).
                                    getAllTransactions().add(transactions);

                            Transactions forCard = new Transactions();
                            forCard = forCard(commerciant, changedAmount, timestamp, forCard);

                            getUsers().get(index).getAccounts().get(idxAccount).
                                    getTransactions().add(forCard);
                            getUsers().get(index).getAccounts().get(idxAccount).
                                    getAllTransactions().add(forCard);
                        } else {
                            transactions.setDescription("Insufficient funds");
                            transactions.setTimestamp(timestamp);
                            getUsers().get(index).getTransactions().add(transactions);

                            Transactions forCard = new Transactions();
                            forCard.setDescription("Insufficient funds");
                            forCard.setTimestamp(timestamp);
                            getUsers().get(index).getAccounts().get(idxAccount).
                                    getTransactions().add(forCard);
                            getUsers().get(index).getAccounts().get(idxAccount).
                                    getAllTransactions().add(forCard);
                        }
                    }
                    if (getUsers().get(index).getAccounts().get(idxAccount).getBalance() <= 0) {
                        getUsers().get(index).getAccounts().get(idxAccount).setBalance(0);
                    }
                } else {
                    Transactions tr = new Transactions();
                    tr.setDescription("The card is frozen");
                    tr.setTimestamp(timestamp);
                    getUsers().get(index).getTransactions().add(tr);
                }
            }

        }
    }
}
