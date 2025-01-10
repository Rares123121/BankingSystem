package org.poo.transactions;

import java.util.List;

public class Transactions {
    private String description;
    private int timestamp;
    private String senderIBAN, recevierIBAN;
    private String transferType;
    private double amount;
    private String currency;
    private String command;
    private String account, email, cardNumber, error;
    private String commerciant;
    private List<String> accounts;
    private String newPlanType;
    private String accountIBAN;

    public String getAccountIBAN() {
        return accountIBAN;
    }

    public void setAccountIBAN(String accountIBAN) {
        this.accountIBAN = accountIBAN;
    }

    public String getNewPlanType() {
        return newPlanType;
    }

    public void setNewPlanType(String newPlanType) {
        this.newPlanType = newPlanType;
    }

    /**
     * Getter
     * @return
     */
    public String getError() {
        return error;
    }

    /**
     * Setter
     * @param error
     */
    public void setError(final String error) {
        this.error = error;
    }

    /**
     * Getter
     * @return
     */
    public List<String> getAccounts() {
        return accounts;
    }

    /**
     * Setter
     * @param accounts
     */
    public void setAccounts(final List<String> accounts) {
        this.accounts = accounts;
    }

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
    public String getCommand() {
        return command;
    }

    /**
     * Setter
     * @param command
     */
    public void setCommand(final String command) {
        this.command = command;
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
    public String getDescription() {
        return description;
    }

    /**
     * Setter
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Getter
     * @return
     */
    public String getSenderIBAN() {
        return senderIBAN;
    }

    /**
     * Setter
     * @param senderIBAN
     */
    public void setSenderIBAN(final String senderIBAN) {
        this.senderIBAN = senderIBAN;
    }

    /**
     * Getter
     * @return
     */
    public String getTransferType() {
        return transferType;
    }

    /**
     * Setter
     * @param transferType
     */
    public void setTransferType(final String transferType) {
        this.transferType = transferType;
    }

    /**
     * Getter
     * @return
     */
    public String getRecevierIBAN() {
        return recevierIBAN;
    }

    /**
     * Setter
     * @param recevierIBAN
     */
    public void setRecevierIBAN(final String recevierIBAN) {
        this.recevierIBAN = recevierIBAN;
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
}
