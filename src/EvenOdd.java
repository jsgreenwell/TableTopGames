import java.util.Scanner;

public class EvenOdd extends Game {
    // scan is used as a class variable (not global) for now to handle input
    private final Scanner scan = new Scanner(System.in);
    // Let's just set the dice value (scale later)
    private final int[] dieValues  = new int[2];

    // Constructor = sets the inital values of the dice rolls
    public EvenOdd() {
        setDieValues();
    }

    // A needed setter function because its not set in the traditional way
    protected void setDieValues() {
        dieValues[0] = getRandom(1, maxValue);
        dieValues[1] = getRandom(1, maxValue);
    }

    /**
     * Main function for playing ChoHan (Even/Odd).
     */
    public void playGame() {
        // set starting values of die
        setDieValues();

        // print the rules
        System.out.println(loadRules("evenodd"));

        System.out.print("Press enter to continue: ");
        scan.nextLine();

        clearScreen();

        // Print start page
        printGreeting();

        // Get Type of Bet & Bet Amount
        boolean win = getGuess(dieValues[0], dieValues[1]);
        long bet = getBet();

        if (!win) {
            System.out.printf("\n\nSorry you lost $%.2f! Dice were %d & %d!\nFeel Free to play again!\n",
                    bet*1.0/100, dieValues[0], dieValues[1]);
        } else {
            long winnings = getWinnings(dieValues[0]+dieValues[1])*bet;

            System.out.print("\n\n********Congrats!!!*******\n\n");
            System.out.printf("You have won $%.2f from a bet of $%.2f with rolls of %d & %d!\n",
                    winnings*1.0/100, bet*1.0/100, dieValues[0], dieValues[1]);
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


    protected void printGreeting() {
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
}
