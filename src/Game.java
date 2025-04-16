import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Repeated utility functions for games:
 * clearscreen, getrandom, and Scanner access
 */
public abstract class Game {
    public Scanner scan = new Scanner(System.in);
    protected int maxValue = 12;

    public Game() {}
    public Game(int newValue) {
        setMaxValue(newValue);
    }

    // New stuff here

    public abstract void playGame();
    protected abstract void printGreeting();

    public record LeaderBoard(String initials, long score, String game) {}
    public Map<String, Long> gameLeaders = new HashMap<>();
    public record gameLeader(String initials, long score) {};
    private List<LeaderBoard> allLeaders = new ArrayList<>();

    protected String loadRules(String filename) {
        StringBuilder rules = new StringBuilder();

        try {
            for (String line : Files.readAllLines(Paths.get(
                    "data/" + filename + "_rules.txt"))) {
                rules.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rules.toString();
    }

    protected void loadGameLeaders(String filename) {
        // Filename cannot be blank
        if (filename.isBlank()) {
            System.out.println("No game leaders file specified");
            return;
        }

        try {
            for (String line : Files.readAllLines(Paths.get(
                    "data/leaderboard_" + filename + ".txt"))) {
                System.out.printf("Line is %s, split its %s\n",
                        line, Arrays.toString(line.split(": ")));

                String[] parts = line.split(": ");
                gameLeaders.put(parts[0], Long.parseLong(parts[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void loadAllLeaders() {
        try {
            for (String line : Files.readAllLines(Paths.get("data/universal_leaderboard.csv"))) {
                if (!line.startsWith("Initials")) {
                    System.out.println("Line is " + line);
                    String[] parts = line.split(",");
                    System.out.println(Arrays.toString(parts));

                    LeaderBoard temp = new LeaderBoard(parts[0], Long.parseLong(parts[1]), parts[2]);
                    allLeaders.add(temp);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void displayAllLeaders() {
        for (LeaderBoard leader : allLeaders) {
            System.out.println("just leader: " + leader.toString());
        }
    }

    protected boolean updateRules(String filename, String newRules) {
        // Always verify and validate
        if (newRules.trim().isBlank()) {
            // It's empty so just exit
            System.out.println("Rules cannot be empty");
            return false;
        }

        try {
            Files.write(Paths.get("reports/query.csv"), newRules.getBytes());
        } catch (IOException e) {
            // Failed miserably - false
            System.out.printf("Error writing to file: %s: %s\n", filename, e.getMessage());
            return false;
        }

        // It actually worked! Return true
        return true;
    }



    // old utility below

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
