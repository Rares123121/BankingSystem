package org.poo.commands;
import org.poo.user.User;
import java.util.ArrayList;
import org.poo.convert.Convert;
public class PrintUser implements CommandExecuter {
    private ArrayList<User> users;
    private Convert convert;
    private int timestamp;

    public PrintUser(final ArrayList<User> users, final Convert convert,
                     final int timestamp) {
        this.users = users;
        this.convert = convert;
        this.timestamp = timestamp;
    }

    /**
     * Se afiseaza toti userii
     */
    public void executeCommand() {
        convert.printUser(users, timestamp);
    }
}
