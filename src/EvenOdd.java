import java.util.Scanner;

public class EvenOdd {
    // scan is used as a class variable (not global) for now to handle input
    private final Scanner scan = new Scanner(System.in);

    /**
     * Main function for playing ChoHan (Even/Odd).
     */
    public void playEvenOdd() {
        // Print start page
        printGreeting();

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
     * This function checks to see if they guess even or odd (odd is default).
     * If even chosen and both items add to even number it returns true (otherwise false).
     * If odd chosen and both items add to an odd, it returns true (otherwise false).
     * @param addend1 The first number to be added
     * @param addend2 The second number to be added
     * @return True or False based on guess and sum of 2 items (even or odd sum)
     */
    private boolean getGuess(int addend1, int addend2) {
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
    private long getBet() {
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
    private long getWinnings(int diceTotal) {
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


    private void printGreeting() {
        clearScreen();

        System.out.print("""
                  ____
                 /\\' .\\    _____
                /: \\___\\  / .  /\\
                \\' / . / /____/..\\
                 \\/___/  \\'  '\\  /
                          \\'__'\\/
               Time to start Even & Odd (Cho-han)!
               Press enter when your ready to continue:""");

        scan.nextLine();
        clearScreen();
    }

    /**
     * Returns a random integer between the min & max values passed.
     * @param min Minimum number to return
     * @param max Maximum number to return
     * @return A number between the min & max passed.
     */
    private int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    /**
     * This is a more true clear screen (not quite but closer).
     * It checks the OS name then uses a bunch of newlines for Windows.
     * ANSI escape codes for Linux or Linux-based systems (i.e. everything else)
     */
    private void clearScreen() {
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
