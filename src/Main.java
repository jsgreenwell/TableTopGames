import java.util.Scanner;

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
                    playEvenOdd();
                    break;
                case 2:
                    playRedBlue();
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

        int[] money = {5, 2, 1, 3, 4}; // drawn money (by denomination
        String[] action = {"Deal Breaker", "", "Rent", "House", "Hotel"};

        // The below works but is wasteful
        int totalMoney = 0;
        for (int bill : money) {
            totalMoney += bill;
        }
        System.out.println("Total money: " + totalMoney);

        for (String a : action) {
            System.out.println(a);
        }
        System.out.println();

        // Why is this better?
        totalMoney = 0;
        for (int i=0; i<money.length; i++) {
            System.out.printf("\tBill value %d with action %s\n", money[i], action[i]);
            // better add an if (action[i]) before this
            totalMoney += money[i];
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
                System.out.println(hint); // Remove after

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
     * Red vs. Blue, it's me versus you....
     * This game is a simplified version of an old dice game.
     * If you get red = 1 point, blue = 1 point for blue, and joker = both lose point
     */
    private static void playRedBlue() {
        printGreeting("redblue");

        int redTeam = 0;
        int blueTeam = 0;

        int card = getRandom(1,3);
        switch (card) {
            case 1:
                System.out.println("Card is Red!");
                redTeam++;
                System.out.println("\tCongrats! Red Team gets a point!");
                break;
            case 2:
                System.out.println("Card is Blue!");
                blueTeam++;
                System.out.println("\tBlue Team gets a point!");
                break;
            case 3:
                System.out.println("Card is Joker!");
                redTeam--;
                blueTeam--;
                System.out.println("\tJoker Steals a point from each team!");
                break; // not needed (end of switch)
        }

        if (redTeam > blueTeam) {
            System.out.println("You are the winner red!");
        } else if (redTeam < blueTeam) {
            System.out.println("Blue Team has won!");
        } else if (redTeam == blueTeam) {
            System.out.println("A Tie - no winners");
        } else if (redTeam < 0 && blueTeam < 0) {
            System.out.println("The Joker won and stole all the points!");
        }
    }

    /**
     * Main function for playing ChoHan (Even/Odd).
     */
    private static void playEvenOdd() {
        // Print start page
        printGreeting("evenodd");

        // Get random number for dice
        int dieOne = getRandom(1,12);
        int dieTwo = getRandom(1, 12);
        
        // Get Type of Bet & Bet Amount
        boolean win = getGuess(dieOne, dieTwo);
        long bet = getBet();

        if (!win) {
            System.out.printf("\n\nSorry you lost $%.2f! Dice were %d & %d!\nFeel Free to play again!\n",
                    bet*1.0/100, dieOne, dieTwo);
        } else {
            long winnings = getWinnings(dieOne+dieTwo)*bet;

            System.out.print("\n\n********Congrats!!!*******\n\n");
            System.out.printf("You have won $%.2f from a bet of $%.2f with rolls of %d & %d!\n",
                    winnings*1.0/100, bet*1.0/100, dieOne, dieTwo);
        }
    }

    /**
     * Print out art and greeting based on passed game.
     * @param game The game to play
     */
    private static void printGreeting(String game) {
        clearScreen();
        switch (game) {
            case "evenodd":
                System.out.print("""
                  ____
                 /\\' .\\    _____
                /: \\___\\  / .  /\\
                \\' / . / /____/..\\
                 \\/___/  \\'  '\\  /
                          \\'__'\\/
               Time to start Even & Odd (Cho-han)!
               Press enter when your ready to continue:""");
                break;
            case "redblue": case "deal":
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
     * This function checks to see if they guess even or odd (odd is default).
     * If even chosen and both items add to even number it returns true (otherwise false).
     * If odd chosen and both items add to an odd, it returns true (otherwise false).
     * @param addend1 The first number to be added
     * @param addend2 The second number to be added
     * @return True or False based on guess and sum of 2 items (even or odd sum)
     */
    private static boolean getGuess(int addend1, int addend2) {
        System.out.print("Please enter even or odd for your guess (default is odd): ");
        String guess = scan.nextLine().toLowerCase(); // Hah - just make it lowercase before checking
        if (guess.startsWith("e")) {
            // Could make this an if...but don't need to
            // the equation is checked against 0 remainder (which gives true or false)
            return (addend1 + addend2) % 2 == 0;
        } else {
            // (3 + 8)/2 => 11/2 => 5r1 <- mod (%) gets remainder
            return (addend1 + addend2) % 2 == 1;
        }
    }

    /**
     * This gets the bet the player wants to place on whether its odd or even.
     * Must be between 100 or 10000 dollars or will be set to 100 by default.
     * @return the player's bet
     */
    private static long getBet() {
        System.out.print("Bets are $100 minimum & $10000 max. How much will you bet? $");
        long bet = (long) (scan.nextDouble()*100);

        if (bet < 10000 || bet > 1000000) {
            // Set minimum bet because it wasn't in range
            System.out.println("Minimum bet of $100 set because bet out of range.");
            return 10000;
        }
        return bet;
    }

    /**
     * This calculates the odds of the dice total and returns it for the winnings.
     * So 7 is possible in 6/36 so double, 6&8 are next so 3, etc.
     * @param diceTotal The total of the 2 dice (added together)
     * @return The amount to increase the winnings
     */
    private static long getWinnings(int diceTotal) {
        if (diceTotal == 7) {
            return 2;
        } else if (diceTotal == 6 || diceTotal == 8) {
            return 3;
        } else if (diceTotal == 5 || diceTotal == 9) {
            return 4;
        } else if (diceTotal == 4 || diceTotal == 10) {
            return 5;
        } else if (diceTotal == 3 || diceTotal == 11) {
            return 6;
        } else {
            return 7;
        }
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