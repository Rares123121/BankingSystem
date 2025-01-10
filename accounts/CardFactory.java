package org.poo.accounts;
public final class CardFactory {
    private CardFactory() {
        throw new UnsupportedOperationException("Utility class");
    }
    /**
     * Metoda care se ocupa cu generarea de carduri
     * @param number
     * @param status
     * @param timestamp
     * @param type
     * @return
     */
    public static Card createCard(final String number, final String status, final int timestamp,
                                  final int type) {
        if (type == 0) {
            return new OneTimeCard(number, status, timestamp, type);
        }
        return new NormalCard(number, status, timestamp, type);

    }
}
