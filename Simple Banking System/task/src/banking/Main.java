package banking;
import java.util.HashMap;
import java.util.Scanner;
import static java.lang.Math.random;

public class Main {
    public static void main(String[] args) {

        HashMap<Integer, Long> cards = new HashMap<>();
        outerMenu(cards);
    }

    public static void outerMenu(HashMap<Integer, Long> cards) {
        System.out.println();
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");

        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        switch (input) {

            case 1:
                createNewAccount(cards);
                break;
            case 2:
                checkData(scanner, cards);
                break;

            case 0:
                System.out.println("Bye!");
                return;
        }
    }

    public static void createNewAccount (HashMap<Integer, Long> cards) {

        long min = 4000000000000000L;
        long max = 4000009999999999L;
        long cardNum = (long) (random()*(max - min + 1) + min);

        int minPin = 1000;
        int maxPin = 9999;
        int pin = (int) (random()*(maxPin - minPin + 1) + minPin);

        System.out.println();
        System.out.println("Your card has been created\n" +
                "Your card number:\n" + cardNum);
        System.out.println("Your card PIN:\n" + pin);
        cards.put(pin, cardNum);

        outerMenu(cards);
    }

    public static void checkData (Scanner scanner, HashMap<Integer, Long> cards) {

        System.out.println();
        System.out.println("Enter your card number:");
        long cardInput = scanner.nextLong();

        System.out.println("Enter your PIN:");
        int pinInput = scanner.nextInt();

        if (cards.containsKey(pinInput) && cards.containsValue(cardInput)) {
            System.out.println();
            System.out.println("You have successfully logged in!");
            innerMenuGreetings(scanner, cards);
        } else {
            System.out.println();
            System.out.println("Wrong card number or PIN!");
            outerMenu(cards);
        }
    }

    public static void innerMenuGreetings (Scanner scanner, HashMap<Integer, Long> cards) {
        System.out.println();
        System.out.println("1. Balance\n" +
                "2. Log out\n" +
                "0. Exit");
        innerMenu(scanner, cards);
    }

    public static void innerMenu (Scanner scanner, HashMap<Integer, Long> cards) {

        int secondInput = scanner.nextInt();
        switch (secondInput) {
            case 1:
                System.out.println();
                System.out.println("Balance: 0");
                innerMenuGreetings(scanner, cards);
                break;
            case 2:
                System.out.println("You have successfully logged out!");
                outerMenu(cards);
                break;
            case 0:
                System.out.println("Bye!");
                return;
        }
    }
}