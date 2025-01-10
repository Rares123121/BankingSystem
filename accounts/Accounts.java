package org.poo.accounts;
import org.poo.transactions.Transactions;
import java.util.ArrayList;

public class Accounts {
    private String iban;
    private double balance;
    private String currency;
    private String type;
    private int timestamp;
    private double minBalance;
    private ArrayList<Card> cards = new ArrayList<>();
    private String alias;
    private double interestRate;
    private ArrayList<Transactions> transactions = new ArrayList<>();
    private ArrayList<Transactions> allTransactions = new ArrayList<>();
    private String accountPlan;
    private double totalTech, totalFood, totalClothes;
    private double spendingTech, spendingFood, spendingClothes;

    public Accounts(final String iban, final double balance, final String currency,
                    final String type, final int timestamp) {
        this.iban = iban;
        this.balance = balance;
        this.currency = currency;
        this.type = type;
        this.timestamp = timestamp;
    }

    public double getSpendingTech() {
        return spendingTech;
    }

    public void setSpendingTech(double spendingTech) {
        this.spendingTech = spendingTech;
    }

    public double getSpendingClothes() {
        return spendingClothes;
    }

    public void setSpendingClothes(double spendingClothes) {
        this.spendingClothes = spendingClothes;
    }

    public double getSpendingFood() {
        return spendingFood;
    }

    public void setSpendingFood(double spendingFood) {
        this.spendingFood = spendingFood;
    }

    public double getTotalTech() {
        return totalTech;
    }

    public void setTotalTech(double totalTech) {
        this.totalTech = totalTech;
    }

    public double getTotalFood() {
        return totalFood;
    }

    public void setTotalFood(double totalFood) {
        this.totalFood = totalFood;
    }

    public double getTotalClothes() {
        return totalClothes;
    }

    public void setTotalClothes(double totalClothes) {
        this.totalClothes = totalClothes;
    }

    /**
     * Getter pentru o lista cu toate tranzactiile de pe un cont
     * @return
     */
    public ArrayList<Transactions> getAllTransactions() {
        return allTransactions;
    }

    /**
     * Setter pentru o lista cu toate tranzactiile de pe un cont
     * @param allTransactions
     */
    public void setAllTransactions(final ArrayList<Transactions> allTransactions) {
        this.allTransactions = allTransactions;
    }

    /**
     * Getter pentru tranzactiile cu cardul
     * @return
     */
    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }

    /**
     * Setter pentru tranzactiile cu cardul
     * @param transactions
     */
    public void setTransactions(final ArrayList<Transactions> transactions) {
        this.transactions = transactions;
    }

    /**
     * Getter pentru dobanda
     * @return
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Setter pentru dobanda
     * @param interestRate
     */
    public void setInterestRate(final double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Getter pentru alias
     * @return
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Setter pentru alias
     * @param alias
     */
    public void setAlias(final String alias) {
        this.alias = alias;
    }

    /**
     * Getter pentru balata minima
     * @return
     */
    public double getMinBalance() {
        return minBalance;
    }

    /**
     * Setter pentru balanata minima
     * @param minBalance
     */
    public void setMinBalance(final double minBalance) {
        this.minBalance = minBalance;
    }

    /**
     * Getter pentru o lista ce contine toate cardurile
     * dintr-un cont
     * @return
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Setter
     * @param cards
     */
    public void setCards(final ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * Getter pentru timestamp
     * @return
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Setter pentru timestamp
     * @param timestamp
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Getter pentru iban
     * @return
     */
    public String getIban() {
        return iban;
    }

    /**
     * Setter
     * @param iban
     */
    public void setIban(final String iban) {
        this.iban = iban;
    }

    /**
     * Getter pentru balanta contului
     * @return
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Setter
     * @param balance
     */
    public void setBalance(final double balance) {
        this.balance = balance;
    }

    /**
     * Getter pentru currency-ul unui cont
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
     * Getter pentru tipul contului
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Setter
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }

    public String getAccountPlan() {
        return accountPlan;
    }

    public void setAccountPlan(String accountPlan) {
        this.accountPlan = accountPlan;
    }
}
