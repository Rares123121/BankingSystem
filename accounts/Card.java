package org.poo.accounts;

public class Card {
    private String number;
    private String status;
    private int timestamp;
    private int type; //0 = OneTime, 1 = Normal
    private int payed = 1;

    public Card(final String number, final String status, final int timestamp,
                final int type) {
        this.number = number;
        this.status = status;
        this.timestamp = timestamp;
        this.type = type;
    }

    /**
     * Getter pentru a verifica daca s-a facut tranzactia
     * unica
     * @return
     */
    public int getPayed() {
        return payed;
    }

    /**
     * Setter
     * @param payed
     */
    public void setPayed(final int payed) {
        this.payed = payed;
    }

    /**
     * Getter pentru tipul cardului
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     * Setter
     * @param type
     */
    public void setType(final int type) {
        this.type = type;
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
     * Getter pentru numarul cardului
     * @return
     */
    public String getNumber() {
        return number;
    }

    /**
     * Setter
     * @param number
     */
    public void setNumber(final String number) {
        this.number = number;
    }

    /**
     * Getter pentru status
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter
     * @param status
     */
    public void setStatus(final String status) {
        this.status = status;
    }
}
