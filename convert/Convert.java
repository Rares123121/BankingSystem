package org.poo.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Accounts;
import org.poo.commerciants.Commerciants;
import org.poo.transactions.Transactions;
import org.poo.user.User;

import java.util.ArrayList;

public class Convert {
    private ArrayNode out;
    public Convert(final ArrayNode out) {
        this.out = out;
    }

    /**
     * Converteste in format JSON toti userii
     * @param users
     * @param timestamp
     */
    public void printUser(final ArrayList<User> users, final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "printUsers");
        ArrayNode user = mapper.createArrayNode();

        for (int i = 0; i < users.size(); i++) {
            ObjectNode tmp = mapper.createObjectNode();
            tmp.put("firstName", users.get(i).getFirstName());
            tmp.put("lastName", users.get(i).getLastName());
            tmp.put("email", users.get(i).getEmail());
            ArrayNode tmp2 = mapper.createArrayNode();
            for (int j = 0; j < users.get(i).getAccounts().size(); j++) {
                ObjectNode tmp3 = mapper.createObjectNode();
                tmp3.put("IBAN", users.get(i).getAccounts().get(j).getIban());
                tmp3.put("balance", users.get(i).getAccounts().get(j).getBalance());
                tmp3.put("currency", users.get(i).getAccounts().get(j).getCurrency());
                tmp3.put("type", users.get(i).getAccounts().get(j).getType());

                ArrayNode tmp4 = mapper.createArrayNode();
                for (int k = 0; k < users.get(i).getAccounts().get(j).getCards().size(); k++) {
                    ObjectNode tmp5 = mapper.createObjectNode();
                    tmp5.put("cardNumber",  users.get(i).getAccounts().get(j).
                            getCards().get(k).getNumber());
                    tmp5.put("status",  users.get(i).getAccounts().get(j).getCards().
                            get(k).getStatus());
                    tmp4.add(tmp5);
                }
                tmp3.put("cards", tmp4);
                tmp2.add(tmp3);
            }
            tmp.put("accounts", tmp2);
            user.add(tmp);
        }
        txt.set("output", user);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }

    /**
     * Daca user-ul asociat cardului nu exista,
     * se afisaza o eroare
     * @param timestamp
     */
    public void deleteFailed(final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "deleteAccount");
        ObjectNode user = mapper.createObjectNode();
        user.put("timestamp", timestamp);
        user.put("descprition", "User not found");
        txt.put("output", user);
        out.add(txt);
    }

    /**
     * Daca cardul nu exista se afisaza
     * o eroare
     * @param timestamp
     */
    public void deleteCard(final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "deleteCard");
        ObjectNode user = mapper.createObjectNode();
        user.put("timestamp", timestamp);
        user.put("descrition", "Card not found");
        txt.put("output", user);
        out.add(txt);
    }

    /**
     * Daca cardul exista si este sters cu succes,
     * se afisaza un mesaj de succes
     * @param timestamp
     */
    public void deleteSuccess(final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "deleteAccount");
        ObjectNode user = mapper.createObjectNode();
        user.put("success", "Account deleted");
        user.put("timestamp", timestamp);
        txt.put("output", user);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }

    /**
     * Se afisaza in format JSON o eroare
     * @param timestamp
     */
    public void deleteNoMoney(final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "deleteAccount");
        ObjectNode user = mapper.createObjectNode();
        user.put("error", "Account couldn't be deleted - "
                + "see org.poo.transactions for details");
        user.put("timestamp", timestamp);
        txt.put("output", user);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }

    /**
     * Daca cardul nu exista, nu se face
     * plata si se afisaza o eroare
     * @param timestamp
     */
    public void payOnline(final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "payOnline");
        ObjectNode user = mapper.createObjectNode();
        user.put("timestamp", timestamp);
        user.put("description", "Card not found");
        txt.put("output", user);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }

    /**
     * Se afisaza in format JSON
     * toate tranzactiile facute de un user
     * @param transactions
     * @param timestamp
     */
    public void transactions(final ArrayList<Transactions> transactions, final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "printTransactions");
        ArrayNode tx = mapper.createArrayNode();
        for (int j = 0; j < transactions.size(); j++) {
            ObjectNode tmp = mapper.createObjectNode();
            tmp.put("timestamp", transactions.get(j).getTimestamp());
            tmp.put("description", transactions.get(j).getDescription());
            if (transactions.get(j).getSenderIBAN() != null) {
                tmp.put("senderIBAN", transactions.get(j).getSenderIBAN());
            }
            if (transactions.get(j).getRecevierIBAN() != null) {
                tmp.put("receiverIBAN", transactions.get(j).getRecevierIBAN());
            }
            if (transactions.get(j).getCurrency() != null) {
                tmp.put("amount", transactions.get(j).getAmount() + " "
                        + transactions.get(j).getCurrency());
            }
            if (transactions.get(j).getTransferType() != null) {
                tmp.put("transferType", transactions.get(j).getTransferType());
            }
            if (transactions.get(j).getAccount() != null) {
                tmp.put("account", transactions.get(j).getAccount());
            }
            if (transactions.get(j).getCardNumber() != null) {
                tmp.put("card", transactions.get(j).getCardNumber());
            }
            if (transactions.get(j).getEmail() != null) {
                tmp.put("cardHolder", transactions.get(j).getEmail());
            }
            if (transactions.get(j).getCommerciant() != null) {
                tmp.put("amount", transactions.get(j).getAmount());
                tmp.put("commerciant", transactions.get(j).getCommerciant());
            }
            if (transactions.get(j).getAccounts() != null) {
                tmp.put("amount", transactions.get(j).getAmount());
                tmp.put("currency", transactions.get(j).getCurrency());

                ArrayNode tmp2 = mapper.createArrayNode();
                for (int k = 0; k < transactions.get(j).getAccounts().size(); k++) {
                    tmp2.add(transactions.get(j).getAccounts().get(k));
                }
                tmp.set("involvedAccounts", tmp2);
            }
            if (transactions.get(j).getError() != null) {
                tmp.put("error", transactions.get(j).getError());
            }
            if(transactions.get(j).getNewPlanType() != null) {
                tmp.put("newPlanType", transactions.get(j).getNewPlanType());
            }
            if(transactions.get(j).getAccountIBAN() != null) {
                tmp.put("accountIBAN", transactions.get(j).getAccountIBAN());
            }
            tx.add(tmp);
        }
        txt.put("output", tx);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }

    /**
     * Se afisaza statusul unui card
     * @param timestamp
     * @param status
     */
    public void checkStatus(final int timestamp, final String status) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "checkCardStatus");
        ObjectNode user = mapper.createObjectNode();
        user.put("timestamp", timestamp);
        user.put("description", status);
        txt.put("output", user);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }

    /**
     * Se genereaza raportul tranzactiilor facute
     * de pe un cont
     * @param timestamp
     * @param list
     * @param startIdx
     * @param endIdx
     * @param account
     */

    public void report(final int timestamp, final ArrayList<Transactions> list,
                       final int startIdx, final int endIdx,
                       final Accounts account) {
        ArrayList<Integer> visited = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();

        txt.put("command", "report");

        ObjectNode user = mapper.createObjectNode();
        user.put("IBAN", account.getIban());
        user.put("balance", account.getBalance());
        user.put("currency", account.getCurrency());


        ArrayNode tmp = mapper.createArrayNode();
        for (int j = 0; j < list.size(); j++) {
            if (list.get(j) != null && list.get(j).getTimestamp() >= startIdx
                    && list.get(j).getTimestamp() <= endIdx
                    && !visited.contains(list.get(j).getTimestamp())) {
                ObjectNode tmp2 = mapper.createObjectNode();

                visited.add(list.get(j).getTimestamp());

                tmp2.put("timestamp", list.get(j).getTimestamp());
                tmp2.put("description", list.get(j).getDescription());

                if (list.get(j).getSenderIBAN() != null) {
                    tmp2.put("senderIBAN", list.get(j).getSenderIBAN());
                }
                if (list.get(j).getRecevierIBAN() != null) {
                    tmp2.put("receiverIBAN", list.get(j).getRecevierIBAN());
                }
                if (list.get(j).getCurrency() != null) {
                    tmp2.put("amount", list.get(j).getAmount() + " "
                            + list.get(j).getCurrency());
                }
                if (list.get(j).getTransferType() != null) {
                    tmp2.put("transferType", list.get(j).getTransferType());
                }
                if (list.get(j).getAccount() != null) {
                    tmp2.put("account", list.get(j).getAccount());
                }
                if (list.get(j).getCardNumber() != null) {
                    tmp2.put("card", list.get(j).getCardNumber());
                }
                if (list.get(j).getEmail() != null) {
                    tmp2.put("cardHolder", list.get(j).getEmail());
                }
                if (list.get(j).getCommerciant() != null) {
                    tmp2.put("amount", list.get(j).getAmount());
                    tmp2.put("commerciant", list.get(j).getCommerciant());
                }
                if (list.get(j).getAccounts() != null) {
                    tmp2.put("amount", list.get(j).getAmount());
                    tmp2.put("currency", list.get(j).getCurrency());

                    ArrayNode tmp3 = mapper.createArrayNode();
                    for (int k = 0; k < list.get(j).getAccounts().size(); k++) {
                        tmp3.add(list.get(j).getAccounts().get(k));
                    }
                    tmp2.set("involvedAccounts", tmp3);
                }
                if (list.get(j).getError() != null) {
                    tmp2.put("error", list.get(j).getError());
                }
                tmp.add(tmp2);
            }
        }
        System.out.println();
        user.put("transactions", tmp);
        txt.set("output", user);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }

    /**
     * Daca contul asupra caruia vreau
     * sa vad raportul nu exsita se afisaza
     * un mesaj de eroare
     * @param timestamp
     */
    public void reportAccountDNE(final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "report");
        ObjectNode user = mapper.createObjectNode();
        user.put("description", "Account not found");
        user.put("timestamp", timestamp);
        txt.put("output", user);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }

    /**
     * Daca contul nu este de economii se
     * afisaza un mesaj de eroare
     * @param timestamp
     * @param command
     */
    public void interestRate(final int timestamp, final String command) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", command);
        ObjectNode user = mapper.createObjectNode();
        user.put("description", "This is not a savings account");
        user.put("timestamp", timestamp);
        txt.put("output", user);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }

    /**
     * Daca nu exista contul solicitat se
     * afisaza o eraore
     * @param timestamp
     */
    public void spendingError(final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "spendingsReport");
        ObjectNode user = mapper.createObjectNode();
        user.put("description", "Account not found");
        user.put("timestamp", timestamp);
        txt.put("output", user);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }

    /**
     * Se genereaza lista cu cheltuielile de pe
     * un card
     * @param timestamp
     * @param start
     * @param end
     * @param list
     * @param account
     */
    public void spendingsReport(final int timestamp, final int start, final int end,
                                final ArrayList<Transactions> list, final Accounts account) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "spendingsReport");

        ObjectNode user = mapper.createObjectNode();
        user.put("IBAN", account.getIban());
        user.put("balance", account.getBalance());
        user.put("currency", account.getCurrency());

        ArrayNode transactions = mapper.createArrayNode();
        ArrayList<Commerciants> commerciants = new ArrayList<>();
        for (Transactions transaction : list) {
            if (transaction != null && transaction.getTimestamp() >= start
                    && transaction.getTimestamp() <= end
                    && transaction.getDescription().equals("Card payment")) {
                ObjectNode transactionNode = mapper.createObjectNode();
                transactionNode.put("timestamp", transaction.getTimestamp());
                transactionNode.put("description", transaction.getDescription());

                if (transaction.getCommerciant() != null) {
                    transactionNode.put("amount", transaction.getAmount());
                    transactionNode.put("commerciant", transaction.getCommerciant());

                    Commerciants comm = new Commerciants();
                    comm.setCommerciant(transaction.getCommerciant());
                    comm.setTotal(transaction.getAmount() + comm.getTotal());
                    commerciants.add(comm);

                }
                transactions.add(transactionNode);
            }
        }

        user.set("transactions", transactions);
        for (int i = 0; i < commerciants.size(); i++) {
            for (int j = i + 1; j < commerciants.size(); j++) {
                if (commerciants.get(i).getCommerciant().
                        compareTo(commerciants.get(j).getCommerciant()) > 0) {
                    Commerciants aux = commerciants.get(i);
                    commerciants.set(i, commerciants.get(j));
                    commerciants.set(j, aux);
                }
            }
        }

        ArrayNode tmp = mapper.createArrayNode();
        for (int i = 0; i < commerciants.size(); i++) {
            ObjectNode tmp2 = mapper.createObjectNode();
            tmp2.put("commerciant", commerciants.get(i).getCommerciant());
            tmp2.put("total", commerciants.get(i).getTotal());
            tmp.add(tmp2);
        }

        user.set("commerciants", tmp);
        txt.set("output", user);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }

    /**
     * Se afiseaza un mesaj de eroare daca
     * contul nu este de economii
     * @param timestamp
     */
    public void spendingNotSaving(final int timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode txt = mapper.createObjectNode();
        txt.put("command", "spendingsReport");
        ObjectNode user = mapper.createObjectNode();
        user.put("error", "This kind of report is not supported for a saving account");
        txt.put("output", user);
        txt.put("timestamp", timestamp);
        out.add(txt);
    }
}
