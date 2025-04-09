import java.util.Scanner;

/**
 * Repeated utility functions for games:
 * clearscreen, getrandom, and Scanner access
 */
public class Utility {
    public Scanner scan = new Scanner(System.in);
    private int maxValue = 12;

    public Utility() {}
    public Utility(int newValue) {
        setMaxValue(newValue);
    }

    /**
     * Set Max Value for random number based on passed value
     * @param newValue Must be a number between 1 and 21
     */
    public void setMaxValue(int newValue) {
        if (newValue > 0 && newValue <= 21) {
            maxValue = newValue;
        }
    }

    /**
     * Returns the current max value for dice rolls
     * @return The current maximum value for a single Die.
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Returns a random integer between the min & max values passed.
     * @param min Minimum number to return
     * @param max Maximum number to return
     * @return A number between the min & max passed.
     */
    public int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    /**
     * This is a more true clear screen (not quite but closer).
     * It checks the OS name then uses a bunch of newlines for Windows.
     * ANSI escape codes for Linux or Linux-based systems (i.e. everything else)
     */
    public void clearScreen() {
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
