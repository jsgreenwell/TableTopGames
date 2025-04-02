import java.util.Scanner;

public class RedBlue {
    // scan is used as a class variable (not global) for now to handle input
    private final Scanner scan = new Scanner(System.in);

    /**
     * Red vs. Blue, it's me versus you....
     * This game is a simplified version of an old dice game.
     * If you get red = 1 point, blue = 1 point for blue, and joker = both lose point
     */
    public void playRedBlue() {
        printGreeting();

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
        } else if (redTeam <= 0 && blueTeam <= 0) {
            System.out.println("Joker wins!");
        } else {
            System.out.println("A Tie - no winners");
        }
    }

    private void printGreeting() {
        clearScreen();

        System.out.print("""
                |A .  |
                | /.\\ |
                |(_._)|
                |  |  |
                |____V|
               Time to start a card game!
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
