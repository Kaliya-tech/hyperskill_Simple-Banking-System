package banking;
import java.util.Scanner;
import java.util.HashMap;
import static java.lang.Math.random;

public class Main {
    public static void main(String[] args) {
        HashMap<Long, Integer> cards = new HashMap<>();
        try {
            outerMenu(cards);
        } catch (NullPointerException exception) {
            outerMenu(cards);
        } finally {
            outerMenu(cards);
        }
    }

    public static void outerMenu(HashMap<Long, Integer> cards) {
        System.out.println();
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");

        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        switch (input) {

            case 1:
                createNewAccount();
                break;
            case 2:
                checkData(scanner, cards);
                break;
            case 0:
                System.out.println("Bye!");
                return;
        }
    }

    public static void createNewAccount () {

        int minPin = 1000;
        int maxPin = 9999;

        int pin = (int) (random()*(maxPin - minPin + 1) + minPin);
        long cardNum = createLuhncard();
        // TODO HASHMAP
        HashMap<Long, Integer> cards = new HashMap<>();
        cards.put(cardNum, pin);

        System.out.println();
        System.out.println("Your card has been created\n" +
                "Your card number:\n" + cardNum);
        System.out.println("Your card PIN:\n" + pin);

        outerMenu(cards);
    }

        public static long createLuhncard (){

            long min = 4000000000000000L;
            long max = 4000009999999999L;
            long cardNum = (long) (random()*(max - min + 1) + min);

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

                 if(checkNum  % 10 == 0) {
                     int st1 = (Integer.parseInt(cardLu[cardLu.length - 2]) + 1);
                        cardNum = Long.parseLong(str.delete(13, 14) + String.valueOf(st1) + cardLu[cardLu.length - 1]);
                     System.out.println(checkNum + " no1");
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

    public static void checkData (Scanner scanner, HashMap<Long, Integer> cards) {

        System.out.println();
        System.out.println("Enter your card number:");
        long cardInput = scanner.nextLong();

        System.out.println("Enter your PIN:");
        int pinInput = scanner.nextInt();

        long checkCard = cardInput;
        int checkPin = pinInput;

        if (cards.get(checkCard) == checkPin) {
            System.out.println();
            System.out.println("You have successfully logged in!");
            innerMenuGreetings(scanner, cards);
        } else {
            System.out.println();
            System.out.println("Wrong card number or PIN!");
            outerMenu(cards);
        }
    }

    public static void innerMenuGreetings (Scanner scanner, HashMap<Long, Integer> cards) {
        System.out.println();
        System.out.println("1. Balance\n" +
                "2. Log out\n" +
                "0. Exit");
        innerMenu(scanner, cards);
    }

    public static void innerMenu (Scanner scanner, HashMap<Long, Integer> cards) {

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