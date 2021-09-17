package banking;
import org.sqlite.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.HashMap;
import static java.lang.Math.random;

public class Main {

    public static void main(String[] args) {
        HashMap<Long, Integer> cardsHashMap = new HashMap<>();
        String url = "jdbc:sqlite:card.s3db";
        outerMenu(cardsHashMap, url);
    }

    private static SQLiteDataSource dataSource = null;

    public static void createDatabase(String url) {

        dataSource = new SQLiteDataSource();

        dataSource.setUrl(url);
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = dataSource.getConnection();
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0" +
                        ");");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void outerMenu(HashMap<Long, Integer> cardsHashMap, String url) throws NullPointerException {

        createDatabase(url);

        System.out.println();
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");

        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        switch (input) {

            case 1:
                createNewAccount(cardsHashMap);
                outerMenu(cardsHashMap, url);
                break;
            case 2:
                checkAccountData(scanner, cardsHashMap, url);
                break;
            case 0:
                scanner.close();
                System.out.println("Bye!");
                return;
        }
    }

    public static void createNewAccount(HashMap<Long, Integer> cardsHashMap) {

        int minPin = 1000;
        int maxPin = 9999;

        int pin = (int) (random() * (maxPin - minPin + 1) + minPin);
        long cardNum = createNewCard();
        int balance = 0;

        System.out.println();
        System.out.println("Your card has been created\n" +
                "Your card number:\n" + cardNum);
        System.out.println("Your card PIN:\n" + pin);

        cardsHashMap.put(cardNum, pin);
        open();
        insert(cardNum, pin, balance);
    }

    public static long createNewCard() {

        long min = 4000000000000000L;
        long max = 4000009999999999L;
        long cardNum = (long) (random() * (max - min + 1) + min);

        StringBuilder str = new StringBuilder("");

        String luhn = String.valueOf(cardNum);
        String[] cardLu = luhn.split("");

        int luhnNum = 0;
        int luhnNum1 = 0;
        int res = 0;
        int checkNum = 0;
        int checkSum = 0;
        int sum = 0;

        for (int i = 0; i < cardLu.length - 1; i++) {
            luhnNum = Integer.parseInt(cardLu[i]);
            if (i == 0 || i % 2 == 0) {
                luhnNum1 = luhnNum * 2;
            } else {
                luhnNum1 = luhnNum;
            }

            if (luhnNum1 > 9) {
                res = luhnNum1 - 9;
            } else {
                res = luhnNum1;
            }

            checkNum += res;
            str.append(cardLu[i]);
        }

        if (checkNum % 10 == 0) {
            int st1 = (Integer.parseInt(cardLu[cardLu.length - 2]) + 1);
            cardNum = Long.parseLong(str.delete(13, 14)
                    + String.valueOf(st1) + cardLu[cardLu.length - 1]);
        } else {
            int n = 0;
            for (n = 1; n < 10; n++) {
                checkSum = checkNum + n;
                if (checkSum % 10 == 0) {
                    sum = n;
                    break;
                }
            }
            cardNum = Long.parseLong(str + String.valueOf(sum));
        }
        return cardNum;
    }

    public static void checkAccountData(Scanner scanner, HashMap<Long, Integer> cardsHashMap, String url) {

        System.out.println();
        System.out.println("Enter your card number:");
        long cardInput = scanner.nextLong();

        System.out.println("Enter your PIN:");
        int pinInput = scanner.nextInt();

        if (null != cardsHashMap.get(cardInput)
                && cardsHashMap.get(cardInput) == pinInput) {
            System.out.println();
            System.out.println("You have successfully logged in!");
            innerMenuGreetings(scanner, cardsHashMap, url);
        } else {
            System.out.println();
            System.out.println("Wrong card number or PIN!");
            outerMenu(cardsHashMap, url);
        }
    }

    public static void innerMenuGreetings(Scanner scanner, HashMap<Long, Integer> cardsHashMap, String url) {
        System.out.println();
        System.out.println("1. Balance\n" +
                "2. Log out\n" +
                "0. Exit");
        innerMenu(scanner, cardsHashMap, url);
    }

    public static void innerMenu(Scanner scanner, HashMap<Long, Integer> cardsHashMap, String url) {

        int secondInput = scanner.nextInt();
        switch (secondInput) {
            case 1:
                System.out.println();
                System.out.println("Balance: 0");
                innerMenuGreetings(scanner, cardsHashMap, url);
                break;
            case 2:
                System.out.println("You have successfully logged out!");
                outerMenu(cardsHashMap, url);
                break;
            case 0:
                System.out.println("Bye!");
                return;
        }
    }

    public static Connection open() {
        Connection co = null;
        try {
            co = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/card.s3db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return co;
    }

    public static void insert(long cardNum, int pin, int balance) {
        
        try (Connection con = dataSource.getConnection()) {
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate(String
                        .format("INSERT INTO card " +
                                        "(number, pin, balance) " +
                                        "values('%s', '%s', %d);",
                                cardNum,
                                pin,
                                balance));
            } catch (SQLException sqe) {
                sqe.printStackTrace();
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }
}