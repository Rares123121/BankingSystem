package org.poo.commands;
import org.poo.accounts.Accounts;
import org.poo.convert.Convert;
import org.poo.currency.ConvertTo;
import org.poo.currency.CurrencyVisitor;
import org.poo.fileio.UserInput;
import org.poo.fileio.ExchangeInput;
import org.poo.fileio.CommandInput;
import org.poo.fileio.CommerciantInput;
import org.poo.transactions.Transactions;
import org.poo.user.User;
import java.util.ArrayList;

import org.poo.currency.Exchange;
import org.poo.currency.CurrencyConverter;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Commands {

    private ArrayList<Exchange> exchanges = new ArrayList<>();
    private Convert convert;
    private CommerciantInput[] commerciant;
    private ExchangeInput[] exchange;
    private CommandInput[] commands;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<CommerciantInput> tech = new ArrayList<>();
    private ArrayList<CommerciantInput> clothes = new ArrayList<>();
    private ArrayList<CommerciantInput> food = new ArrayList<>();
    /**
     * Getter
     * @return
     */
    public Convert getConvert() {
        return convert;
    }

    /**
     * Setter
     * @param convert
     */
    public void setConvert(final Convert convert) {
        this.convert = convert;
    }

    /**
     * Getter
     * @return
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Setter
     * @param users
     */
    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    /**
     * Getter
     * @return
     */
    public CommerciantInput[] getCommerciant() {
        return commerciant;
    }

    /**
     * Setter
     * @param commerciant
     */
    public void setCommerciant(final CommerciantInput[] commerciant) {
        this.commerciant = commerciant;
    }

    /**
     * Getter
     * @return
     */
    public CommandInput[] getCommands() {
        return commands;
    }

    /**
     * Setter
     * @param commands
     */
    public void setCommands(final CommandInput[] commands) {
        this.commands = commands;
    }

    public Commands(final UserInput[] user, final CommerciantInput[] commerciant,
                    final ExchangeInput[] exchange, final CommandInput[] commands,
                    final Convert c) {
        this.convert = c;
        this.commerciant = commerciant;
        this.exchange = exchange;
        this.commands = commands;
        LocalDate today = LocalDate.now();
        for (int i = 0; i < user.length; i++) {
            User aux = new User();
            aux.setEmail(user[i].getEmail());
            aux.setFirstName(user[i].getFirstName());
            aux.setLastName(user[i].getLastName());
            ArrayList<Accounts> acc = new ArrayList<>();
            aux.setAccounts(acc);
            aux.setBirth(user[i].getBirthDate());
            aux.setOccupation(user[i].getOccupation());
            LocalDate birthday = LocalDate.parse(user[i].getBirthDate(), DateTimeFormatter.ISO_DATE);
            int age = Period.between(birthday, today).getYears();
            aux.setAge(age);
            this.users.add(aux);
        }
        for (int i = 0; i < exchange.length; i++) {
            Exchange aux = new Exchange(exchange[i].getFrom(), exchange[i].getTo(),
                    exchange[i].getRate());
            exchanges.add(aux);
        }
    }

    /**
     * Metoda itereaza prin lista de comenzi si
     * le executa, pentru fiecare comanda existand o
     */
    public void run() {
        for(int i = 0; i < getCommerciant().length; i++) {
            if(getCommerciant()[i].getType().equals("Tech"))
                tech.add(getCommerciant()[i]);
            if(getCommerciant()[i].getType().equals("Clothes"))
                clothes.add(getCommerciant()[i]);
            if(getCommerciant()[i].getType().equals("Food"))
                food.add(getCommerciant()[i]);
        }

        for (int i = 0; i < getCommands().length; i++) {
            if (getCommands()[i].getCommand().equals("printUsers")) {
                CommandExecuter com = new PrintUser(getUsers(),
                        convert, getCommands()[i].getTimestamp());
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("addAccount")) {
                Transactions tr = new Transactions();
                tr.setDescription("New account created");
                tr.setCommand(getCommands()[i].getCommand());
                CommandExecuter com = new AddAccount(getUsers(), getCommands()[i], convert, tr,
                        getCommands()[i].getTimestamp());
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("createCard")) {
                Transactions tr = new Transactions();
                tr.setDescription("New card created");
                tr.setCommand(getCommands()[i].getCommand());

                CommandExecuter com = new CreateCard(getUsers(), getCommands()[i].getAccount(),
                        getCommands()[i].getTimestamp(), tr, getCommands()[i].getEmail());
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("addFunds")) {
                CommandExecuter com = new AddFunds(getCommands()[i].getAccount(),
                        getCommands()[i].getAmount(), getUsers());
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("deleteAccount")) {
                CommandExecuter com = new DeleteAccount(getCommands()[i].getEmail(),
                                    getUsers(), convert, getCommands()[i].getTimestamp(),
                                    getCommands()[i].getAccount());
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("createOneTimeCard")) {
                Transactions tr = new Transactions();
                tr.setDescription("New card created");
                tr.setCommand(getCommands()[i].getCommand());

                CommandExecuter com = new CreateOneTimeCard(getCommands()[i].getAccount(),
                                    getUsers(), getCommands()[i].getTimestamp(), tr);
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("deleteCard")) {
                Transactions tr = new Transactions();
                CommandExecuter com = new DeleteCard(getCommands()[i].getCardNumber(),
                        getCommands()[i].getEmail(), getUsers(), getCommands()[i].getTimestamp(),
                        convert, tr);
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("setMinimumBalance")) {
                CommandExecuter com = new MinBalance(getCommands()[i].getAccount(), getUsers(),
                        getCommands()[i].getMinBalance());
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("payOnline")) {
                CurrencyConverter c = new CurrencyConverter(exchanges);
                c.generateRest();

                Transactions tr = new Transactions();
                CurrencyVisitor visitor = new ConvertTo();

                CommandExecuter com = new PayOnline(c, getCommands()[i].getCardNumber(),
                        getCommands()[i].getEmail(), getCommands()[i].getCurrency(),
                        getCommands()[i].getAmount(), getCommands()[i].getTimestamp(),
                        getUsers(), convert, visitor, tr, getCommands()[i].getCommerciant(),
                        tech, food, clothes, commerciant);
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("sendMoney")) {
                CurrencyConverter c = new CurrencyConverter(exchanges);
                c.generateRest();
                CurrencyVisitor visitor = new ConvertTo();

                Transactions trSender = new Transactions();
                Transactions trRecevier = new Transactions();
                trSender.setDescription(getCommands()[i].getDescription());
                trSender.setTimestamp(getCommands()[i].getTimestamp());
                trSender.setCommand(getCommands()[i].getCommand());

                trRecevier.setDescription(getCommands()[i].getDescription());
                trRecevier.setTimestamp(getCommands()[i].getTimestamp());
                trRecevier.setCommand(getCommands()[i].getCommand());
                CommandExecuter com = new SendMoney(c, getCommands()[i].getAccount(),
                        getCommands()[i].getReceiver(), getCommands()[i].getEmail(),
                        getCommands()[i].getAmount(), getUsers(), convert, visitor, trSender,
                        trRecevier);

                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("setAlias")) {
                CommandExecuter com = new SetAlias(getCommands()[i].getEmail(),
                        getCommands()[i].getAccount(), getCommands()[i].getAlias(),
                        getUsers());
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("printTransactions")) {
                CommandExecuter com = new printTransactions(getCommands()[i].getEmail(),
                        getCommands()[i].getTimestamp(), convert, getUsers());

                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("checkCardStatus")) {
                CommandExecuter com = new CheckCard(getCommands()[i].getCardNumber(),
                        getUsers(), convert, getCommands()[i].getTimestamp());

                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("splitPayment")) {
                CurrencyConverter c = new CurrencyConverter(exchanges);
                c.generateRest();
                CurrencyVisitor visitor = new ConvertTo();

                CommandExecuter com = new SplitPay(c, getCommands()[i].getCurrency(), visitor,
                        getCommands()[i].getAccounts(), getCommands()[i].getAmount(),
                        getCommands()[i].getTimestamp(), getUsers());
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("report")) {
                CommandExecuter com = new Report(getCommands()[i].getEndTimestamp(),
                        getCommands()[i].getStartTimestamp(), getCommands()[i].getTimestamp(),
                        getCommands()[i].getAccount(), getUsers(), convert);

                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("changeInterestRate")) {
                CommandExecuter com = new ChangeInteresetRate(getCommands()[i].getAccount(),
                        convert, getUsers(), getCommands()[i].getTimestamp(),
                        getCommands()[i].getInterestRate());
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("addInterest")) {
                CommandExecuter com = new AddInterest(getCommands()[i].getAccount(),
                        convert, getUsers(), getCommands()[i].getTimestamp());
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("spendingsReport")) {
                CommandExecuter com = new Spendings(getCommands()[i].getStartTimestamp(),
                        getCommands()[i].getEndTimestamp(), getCommands()[i].getTimestamp(),
                        getCommands()[i].getAccount(), getUsers(), convert);
                com.executeCommand();
            }
            if (getCommands()[i].getCommand().equals("withdrawSavings")) {
                String acc = getCommands()[i].getAccount();
                double sum = getCommands()[i].getAmount();
                int accIdx = -1, userIdx = -1;
                for (int j = 0; j < users.size(); j++) {
                    for (int l = 0; l < users.get(j).getAccounts().size(); l++) {
                        if (users.get(j).getAccounts().get(l).getIban().equals(acc)) {
                            accIdx = l;
                            userIdx = j;
                            break;
                        }
                    }
                }

                if(accIdx == -1){
                    Transactions tr = new Transactions();
                    tr.setDescription("Account not found");
                    tr.setTimestamp(getCommands()[i].getTimestamp());
                    users.get(userIdx).getTransactions().add(tr);
                }

                if (accIdx != -1 && userIdx != -1) {
                    if (users.get(userIdx).getAge() < 21) {
                        Transactions tr = new Transactions();
                        tr.setDescription("You don't have the minimum age required.");
                        tr.setTimestamp(getCommands()[i].getTimestamp());
                        users.get(userIdx).getTransactions().add(tr);
                    } else if(users.get(userIdx).getAccounts().get(accIdx).getType().equals("classic")) {
                        Transactions tr = new Transactions();
                        tr.setDescription("Account is not of type savings.");
                        tr.setTimestamp(getCommands()[i].getTimestamp());
                        users.get(userIdx).getTransactions().add(tr);
                    } else {
                        String currency = users.get(userIdx).getAccounts().get(accIdx).getCurrency();
                        int classicIdx = -1;
                        for (int j = 0; j < users.get(userIdx).getAccounts().size(); j++) {
                            if (users.get(userIdx).getAccounts().get(j).getType().equals("classic")
                                    && users.get(userIdx).getAccounts().get(j).getCurrency().equals(currency)) {
                                classicIdx = j;
                                break;
                            }
                        }

                        if (classicIdx == -1) {
                            Transactions tr = new Transactions();
                            tr.setDescription("You do not have a classic account.");
                            tr.setTimestamp(getCommands()[i].getTimestamp());
                            users.get(userIdx).getTransactions().add(tr);
                        } else {
                            if (sum > users.get(userIdx).getAccounts().get(accIdx).getBalance()) {
                                Transactions tr = new Transactions();
                                tr.setDescription("Insufficient funds");
                                tr.setTimestamp(getCommands()[i].getTimestamp());
                                users.get(userIdx).getTransactions().add(tr);
                            } else {
                                users.get(userIdx).getAccounts().get(accIdx).
                                        setBalance(users.get(userIdx).getAccounts().get
                                                (accIdx).getBalance() - sum);
                                users.get(userIdx).getAccounts().get(classicIdx).setBalance(sum
                                        * users.get(userIdx).getAccounts().get(accIdx).getInterestRate() +
                                        users.get(userIdx).getAccounts().get(classicIdx).getBalance());

                                Transactions tr = new Transactions();
                                tr.setDescription("Savings withdrawal");
                                tr.setTimestamp(getCommands()[i].getTimestamp());
                                users.get(userIdx).getTransactions().add(tr);

                                if(users.get(userIdx).getAccounts().get(accIdx).getAccountPlan().equals("standard")){
                                    users.get(userIdx).getAccounts().get(accIdx).
                                            setBalance(users.get(userIdx).getAccounts().get
                                                    (accIdx).getBalance() - sum * 0.2 / 100);
                                }
                                if(users.get(userIdx).getAccounts().get(classicIdx).getAccountPlan().equals("silver")){
                                    CurrencyConverter c = new CurrencyConverter(exchanges);
                                    c.generateRest();

                                    CurrencyVisitor visitor = new ConvertTo();

                                    ArrayList<Double> changed = c.accept(visitor, "RON",
                                            users.get(userIdx).getAccounts().get(accIdx).getCurrency(), sum);
                                    double changedAmount = sum;
                                    for (int k = 0; k < changed.size(); k++) {
                                        changedAmount = changedAmount * changed.get(k);
                                    }
                                    if(changedAmount >= 500){
                                        users.get(userIdx).getAccounts().get(accIdx).
                                                setBalance(users.get(userIdx).getAccounts().get
                                                        (accIdx).getBalance() - sum * 0.1 / 100);
                                    }
                                }
                            }
                        }
                    }

                }
            }
            if(getCommands()[i].getCommand().equals("upgradePlan")) {
                String acc = getCommands()[i].getAccount();
                String type = getCommands()[i].getNewPlanType();
                int idxAcc = -1, idxUser = -1;
                for(int j = 0; j < users.size(); j++) {
                    for(int k = 0; k < users.get(j).getAccounts().size(); k++) {
                        if(users.get(j).getAccounts().get(k).getIban().equals(acc)) {
                            idxAcc = k;
                            idxUser = j;
                            break;
                        }
                    }
                }

//                if(idxAcc == -1){
//                    Transactions tr = new Transactions();
//                    tr.setDescription("Account not found");
//                    tr.setTimestamp(getCommands()[i].getTimestamp());
//                    users.get(idxUser).getTransactions().add(tr);
//                }

                if (idxAcc != -1) {
                    String userType = users.get(idxUser).getAccounts().get(idxAcc).getAccountPlan();
                    if (userType.equals(type)) {
                        Transactions tr = new Transactions();
                        tr.setDescription("The user already has the " + type + " plan.");
                        tr.setTimestamp(getCommands()[i].getTimestamp());
                        users.get(idxUser).getTransactions().add(tr);
                    } else {
                        if(userType.equals("silver") && (type.equals("standard") || type.equals("student")) ||
                                (userType.equals("gold") && (type.equals("standard") || type.equals("student") || type.equals("silver")))) {
                            Transactions tr = new Transactions();
                            tr.setDescription("You cannot downgrade your plan.");
                            tr.setTimestamp(getCommands()[i].getTimestamp());
                            users.get(idxUser).getTransactions().add(tr);
                        } else {
                            double balance = users.get(idxUser).getAccounts().get(idxAcc).getBalance();
                            CurrencyConverter c = new CurrencyConverter(exchanges);
                            c.generateRest();
                            double amount = 0;
                            if(userType.equals("standard") || userType.equals("student") && type.equals("silver")) {
                                amount = 100;
                            } else if(userType.equals("standard") || userType.equals("student") && type.equals("gold")) {
                                amount = 350;
                            } else if(userType.equals("silver") && type.equals("gold")) {
                                amount = 250;
                            }

                            CurrencyVisitor visitor = new ConvertTo();

                            ArrayList<Double> changed = c.accept(visitor, "RON",
                                    users.get(idxUser).getAccounts().get(idxAcc).getCurrency(), amount);
                            double changedAmount = amount;
                            for (int k = 0; k < changed.size(); k++) {
                                changedAmount = changedAmount * changed.get(k);
                            }

                            //System.out.println( users.get(idxUser).getAccounts().get(idxAcc).getAccountPlan());

                            if(changedAmount > balance){
                                Transactions tr = new Transactions();
                                tr.setDescription("Insuficient funds");
                                tr.setTimestamp(getCommands()[i].getTimestamp());
                                users.get(idxUser).getTransactions().add(tr);
                            } else {
                                Transactions tr = new Transactions();
                                tr.setDescription("Upgrade plan");
                                tr.setAccountIBAN(acc);
                                tr.setNewPlanType(type);
                                tr.setTimestamp(getCommands()[i].getTimestamp());
                                users.get(idxUser).getTransactions().add(tr);
                                for(int k = 0; k < users.get(idxUser).getAccounts().size(); k++){
                                    users.get(idxUser).getAccounts().get(k).setAccountPlan(type);
                                }
                                users.get(idxUser).getAccounts().get(idxAcc).setBalance(balance - changedAmount);
                            }
                        }
                    }
                }
            }
        }
    }
}
