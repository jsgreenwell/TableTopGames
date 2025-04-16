import java.util.*;

public class Main {
    // scan is used as a class variable (not global) for now to handle input
    private static final Scanner scan = new Scanner(System.in);

    /**
     * A collection of Dice, Card, and other TableTop games.
     * Completed as spikes in IS 221: Programming Fundamentals
     * @param args Args are passed from the commandline (think flags)
     */
    public static void main(String[] args) {
        // Display main menu & get user choice (repeat until there is a correct choice)

        EvenOdd evenOdd = new EvenOdd();
        RedBlue redBlue = new RedBlue();
        Wheel wheel = new Wheel();

        // Using label to break out of game - should use a function & return
        gameLoop:
        while (true) {
            clearScreen();
            System.out.println("""
                    Welcome to our little Game System!
                    Please select one of the following games:
                    \t1. Even/Odd
                    \t2. Red/Blue
                    \t3. America's Game (Wheel)!
                    \t4. Monopoly(-ish) Deal!
                    \t9. Exit
                    """);

            int choice = 0;
            choice = Integer.parseInt(scan.nextLine());
            // above removes extra newline & ensures an integer value

            if (choice == 0) {
                /*
                evenOdd.loadGameLeaders("evenodd");
                System.out.println(evenOdd.gameLeaders.toString());
                scan.nextLine();

                 */

                evenOdd.loadAllLeaders();
                evenOdd.displayAllLeaders();
                scan.nextLine();
            }

            if (choice < 9 ) { getPlayers(); }

            switch (choice) {
                case 1:
                    evenOdd.playGame();
                    break;
                case 2:
                    redBlue.playGame();
                    break;
                case 3:
                    wheel.playGame();
                    break;
                case 4:
                    playDeal();
                    break;
                case 9:
                    System.out.println("Thanks for playing! Goodbye!");
                    break gameLoop;
                default:
                    System.out.println("Invalid choice! Exiting...");
                    break gameLoop;
            }
        }
    }

    private static void getPlayers() {
        // For now assume 2 player
        Player player1 = new Player();

        player1.name = "John";
        player1.toString();
        //player1.getBet(); // doesn't work

        Gambler player2 = new Gambler();
        player2.name = "Bob"; // does work.
        player2.setBet(12.25);
        player2.getBet();
    }

    /**
     * This will have a game based on a card version of Monopoly.
     */
    private static void playDeal() {
        printGreeting("deal");

        List<Integer> money = new ArrayList<>();

        while (true) {
            System.out.print("Enter money value (1 to 10): ");
            int moneyValue = Integer.parseInt(scan.nextLine());
            if (moneyValue >= 1 && moneyValue <= 10) {
                money.add(moneyValue);
            }

            System.out.print("Do you have more money (y/n): ");
            String answer = scan.nextLine();
            if (!answer.toLowerCase().startsWith("y")) {
                break;
            }

        }

        int switched = 1; // how many switches? if more than 0 then keep going
        while (switched > 0) {
            switched = 0;

            // single pass of bubble sort
            for (int i = 0; i < money.size(); i++) {
                if (i != 0) {
                    if (money.get(i) < money.get(i - 1)) {
                        int temp = money.get(i - 1);
                        money.set(i - 1, money.get(i));
                        money.set(i, temp);
                        switched++;
                    }
                }
                // if it's the first value just ignore it and move on
            }
        }

        //Arrays.sort(money); // way faster

        // You can also use these options
        // Collections.sort(money);
        // Collections.reverse(money);
        // Collections.shuffle(money);


        String[] action = {"Deal Breaker", "", "Rent", "House", "Hotel"};
        Arrays.sort(action);

        // The below works but is wasteful
        int totalMoney = 0;
        for (int bill : money) {
            System.out.println(bill);
            totalMoney += bill;
        }
        System.out.println("Total money: " + totalMoney);

        for (String a : action) {
            System.out.println(a);
        }
        System.out.println();

        // Why is this better?
        totalMoney = 0;
        // 2nd change: length is now size
        // for (int i=0; i<money.length; i++) {
        for (int i=0; i<money.size(); i++) {
            // Final change: get not []
            System.out.printf("\tBill value %d with action %s\n", money.get(i), action[i]);
            // better add an if (action[i]) before this
            totalMoney += money.get(i);
        }
        System.out.println("Total money: " + totalMoney);

    }

    /**
     * Print out art and greeting based on passed game.
     * @param game The game to play
     */
    private static void printGreeting(String game) {
        clearScreen();
        switch (game) {
            case "deal":
                System.out.print("""
                |A .  |
                | /.\\ |
                |(_._)|
                |  |  |
                |____V|
               Time to start a card game!
               Press enter when your ready to continue:""");
                break;
            case "wheel":
                System.out.print("""
                                  _               _\s
                                 | |             | |
                        __      _| |__   ___  ___| |
                        \\ \\ /\\ / / '_ \\ / _ \\/ _ \\ |
                         \\ V  V /| | | |  __/  __/ |
                          \\_/\\_/ |_| |_|\\___|\\___|_|
                                                   \s
               Time to start Wheel!
               Press enter when your ready to continue:""");
                break;
        }
        // Pause until Enter hit then clear Logo screen
        scan.nextLine();
        clearScreen();
    }

    /**
     * This is a more true clear screen (not quite but closer).
     * It checks the OS name then uses a bunch of newlines for Windows.
     * ANSI escape codes for Linux or Linux-based systems (i.e. everything else)
     */
    private static void clearScreen() {
        // System has a few cool methods including one that gets the OS name
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        } else {
            // This works in Linux and ....Mac?
            System.out.print("\033[H\033[2J");
        }
        // Always flush when you use escape codes or use too many newlines
        System.out.flush();

    }
}