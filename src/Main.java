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
        int choice = 0;
        while (choice < 1 || choice > 4) {
            clearScreen();
            System.out.println("""
                    Welcome to our little Game System!
                    Please select one of the following games:
                    \t1. Even/Odd
                    \t2. Red/Blue
                    \t3. America's Game (Wheel)!
                    """);

            choice = scan.nextInt();
            scan.nextLine(); // remove extra newline
        }

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
            default:
                System.out.println("Invalid choice! Exiting...");
                break;
        }
    }

    /**
     * The game is based on the Hangman, but I like Wheel of Fortune's name better :)
     * A hint is given then you guess the letters of the word or guess the word.
     */
    private static void playWheel() {
        printGreeting("wheel");
        String answer = "History is written by the victors but victims write the memoirs";
        String hint = "Carol";

        // TODO: review if/switch statements here (why is one better/worse?)

        // If or Switch for get random to assign on of these options
        /*
        answer = "History repeats itself first as tragedy second as farce"
        hint = "Karl Marx quote on histories cyclic nature often attributed to others."

        answer = "History is written by the victors"
        hint = "This British prime minister during WWII had many famous quotes including this one about recording events"

        answer = "History is a set of lies agreed upon"
        hint = "Before his downfall at Waterloo, this French dictator shared his cynical view of humanity's memory in this quote"

        answer = "History is written by the victors but victims write the memoirs"
        hint = "Carol Travis devoted her life to fighting psychobabble, biobunk, and pseudoscience. She famously corrected Churchill with this quote"
        */

        // See algo on board. Sprints: 1. Build basic for loops. 2. Add Strings 3. Guess Logic
        // If time: add points
        String guessed = "";
        // TODO: Build for loop to build guessed here
        for (int i = 0; i<answer.length(); i++) {
            if (answer.charAt(i) != ' ') {
                guessed += "_";
            } else {
                guessed += " ";
            }
        }
        /* Alternate way to build: which do you like better?
        for (char letter : answer.toCharArray()) {
            if (letter != ' ') {
                guessed += "_";
            } else {
                guessed += " ";
            }
        }

         */
        System.out.println(guessed);
        System.out.println(answer);

        for (String word : answer.split(" ")) {
            System.out.println(word);
        }


        //TODO: Get guess and check versus string
        // To start print the answer & hint then make sure guess works
        // Remove answer after and just print guessed (26 guesses at max)
        // Add difficulty select (hard = less guesses & upper/lower matter)

        // Start more code here

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

        // TODO: Add a for loop to play for a number of rounds (based on asking player how many)
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
            case "redblue":
                System.out.print("""
                |A .  |
                | /.\\ |
                |(_._)|
                |  |  |
                |____V|
               Time to start Red & Blue!
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