package org.poo.currency;

public class Exchange {
    private String from;
    private String to;
    private double rate;

    public Exchange(final String from, final String to,
                    final double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    /**
     * Getter
     * @return
     */
    public String getFrom() {
        return from;
    }

    /**
     * Setter
     * @param from
     */
    public void setFrom(final String from) {
        this.from = from;
    }

    /**
     * Getter
     * @return
     */
    public String getTo() {
        return to;
    }

    /**
     * Setter
     * @param to
     */
    public void setTo(final String to) {
        this.to = to;
    }

    /**
     * Getter
     * @return
     */
    public double getRate() {
        return rate;
    }

    /**
     * Setter
     * @param rate
     */
    public void setRate(final double rate) {
        this.rate = rate;
    }
}
