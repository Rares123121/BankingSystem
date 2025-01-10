package org.poo.currency;

import java.util.ArrayList;

public interface CurrencyVisitable {
     /**
      * Metoda de accept
      * @param visitor
      * @param from
      * @param to
      * @param money
      * @return
      */
     ArrayList<Double> accept(CurrencyVisitor visitor, String from, String to,
                              double money);
}
