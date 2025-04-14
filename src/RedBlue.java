import java.util.Scanner;

/**
 * Red vs. Blue, it's me versus you....
 * This game is a simplified version of an old dice game.
 * If you get red = 1 point, blue = 1 point for blue, and joker = both lose point
 */
public class RedBlue extends Game {
    // scan is used as a class variable (not global) for now to handle input
    private final Scanner scan = new Scanner(System.in);

    /**
     * Play the game RedBlue
     */
    public void playGame() {
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

    protected void printGreeting() {
        clearScreen();
        System.out.println(loadRules("redblue"));

        System.out.print("Press enter to continue: ");
        scan.nextLine();

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
}
