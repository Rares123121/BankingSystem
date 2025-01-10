package org.poo.user;

import org.poo.accounts.Accounts;
import org.poo.transactions.Transactions;

import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Accounts> accounts;
    private ArrayList<Transactions> transactions = new ArrayList<>();
    private String occupation, birth;
    private int tech, clothes, food;
    private double total;
    private int age;
    /**
     * Getter
     * @return
     */
    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }

    /**
     * Setter
     * @param transactions
     */
    public void setTransactions(final ArrayList<Transactions> transactions) {
        this.transactions = transactions;
    }

    /**
     * Setter
     * @param firstName
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter
     * @param lastName
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
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
    public ArrayList<Accounts> getAccounts() {
        return accounts;
    }

    /**
     * Setter
     * @param accounts
     */
    public void setAccounts(final ArrayList<Accounts> accounts) {
        this.accounts = accounts;
    }

    /**
     * Getter
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter
     * @return
     */
    public String getEmail() {
        return email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTech() {
        return tech;
    }

    public void setTech(int tech) {
        this.tech = tech;
    }

    public int getClothes() {
        return clothes;
    }

    public void setClothes(int clothes) {
        this.clothes = clothes;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
