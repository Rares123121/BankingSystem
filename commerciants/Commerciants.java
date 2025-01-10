package org.poo.commerciants;

public class Commerciants {
    private String commerciant;
    private double total;
    private int id;
    private String account, type, cashbackStrategy;

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
    public double getTotal() {
        return total;
    }

    /**
     * Setter
     * @param total
     */
    public void setTotal(final double total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCashbackStrategy() {
        return cashbackStrategy;
    }

    public void setCashbackStrategy(String cashbackStrategy) {
        this.cashbackStrategy = cashbackStrategy;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
