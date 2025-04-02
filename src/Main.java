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

            switch (choice) {
                case 1:
                    evenOdd.playEvenOdd();
                    break;
                case 2:
                    redBlue.playRedBlue();
                    break;
                case 3:
                    playWheel();
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
     * The game is based on the Hangman, but I like Wheel of Fortune's name better :)
     * A hint is given then you guess the letters of the word or guess the word.
     */
    private static void playWheel() {
        printGreeting("wheel");

        // Two constants for answers and hints
        final String[] answers = {
                "History repeats itself first as tragedy second as farce",
                "History is written by the victors",
                "History is a set of lies agreed upon",
                "History is written by the victors but victims write the memoirs"
        };

            ;
        final String[] hints = {
                "Karl Marx quote on histories cyclic nature often attributed to others.",
                "This British prime minister during WWII had many famous quotes including this one about recording events",
                "Before his downfall at Waterloo, this French dictator shared his cynical view of humanity's memory in this quote",
                "Carol Travis devoted her life to fighting psychobabble, biobunk, and pseudoscience. She famously corrected Churchill with this quote"
        };

        // Set up the answer and hint for guessing (pick one from array at random
        int ranNum = getRandom(0,answers.length);
        String answer = answers[ranNum];
        String hint = hints[ranNum];
        String guessed = "";

        /* Alternate way to build: which do you like better?
           For Each loop that builds a string based on the letters (of ___)
           Why var - what's var mean? (what was it before)
         */
        for (var letter : answer.toCharArray()) {
            if (letter != ' ') {
                guessed += "_";
            } else {
                guessed += " ";
            }
        }

        // Loop until solved or last letter used (i.e. run for all letters)
        // Incorrect solve also exits but with different message
        for (char c = 'A'; c <= 'Z'; c++) {
            clearScreen();

            System.out.print("Would you like to guess a letter or solve? (l/s) : ");
            String choice = scan.nextLine().toLowerCase();
            if (choice.startsWith("l")) {
                // Here we start: print current guess
                System.out.print("Current guess:\n\t");
                System.out.println(guessed);
                System.out.println(hint);

                System.out.print("Enter the letter you guess: ");
                char newguess = scan.nextLine().toLowerCase().charAt(0);

                // flag to keep from printing the correct letter message too much
                boolean correct = false;

                // Loop over each letter and when its correct change that same letter in guessed
                for (int i = 0; i < answer.length(); i++) {
                    if (answer.toLowerCase().charAt(i) == newguess) {
                        if (!correct) {
                            System.out.println("You guessed a correct letter!");
                            correct = true;
                        }
                        guessed = guessed.substring(0, i) + answer.charAt(i) + guessed.substring(i + 1);
                    }
                }
                correct = false;

                System.out.println("Correct guesses: " + guessed);
                System.out.print("Hit any enter to continue...");
                scan.nextLine();

            } else {
                System.out.println("Guessed Phrase letters: \n\t" + guessed);
                System.out.println("Please enter your guess (no punctuation but include spaces):");
                String finalGuess = scan.nextLine();

                if (finalGuess.equalsIgnoreCase(answer)) {
                    System.out.println("You guessed correctly!");
                } else {
                    System.out.println("Your guess is not correct!");
                }

                // Right or wrong it is time to exit
                System.out.print("Thanks for playing! Hit any enter to exit...");
                scan.nextLine();
                return;
            }
        }
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
     * Returns a random integer between the min & max values passed.
     * @param min Minimum number to return
     * @param max Maximum number to return
     * @return A number between the min & max passed.
     */
    private static int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
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