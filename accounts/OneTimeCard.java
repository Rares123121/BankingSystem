package org.poo.accounts;

public class OneTimeCard extends Card {
    /**
     * Constructor pentru cardul cu care se poate face doar o plata
     * @param number
     * @param status
     * @param timestamp
     * @param type
     */
    public OneTimeCard(final String number, final String status, final int timestamp,
                       final int type) {
        super(number, status, timestamp, type);
    }
}
