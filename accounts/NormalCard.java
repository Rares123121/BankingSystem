package org.poo.accounts;
public class NormalCard extends Card {
    /**
     * Constructor care memoreaza un card normal
     * @param number
     * @param status
     * @param timestamp
     * @param type
     */
    public NormalCard(final String number, final String status, final int timestamp,
                      final int type) {
        super(number, status, timestamp, type);
    }
}
