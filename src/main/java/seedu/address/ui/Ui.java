package seedu.address.ui;

import java.util.Scanner;

public class Ui {
    private static final Scanner scanner = new Scanner(System.in);
    private Ui(){
    }
    public static void showReply(String msg) {
        System.out.println(msg);
    }

    public static String getUserInput() {
        return scanner.nextLine();
    }
}
