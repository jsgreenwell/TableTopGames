
public class Wheel extends Game {
    // scan is used as a class variable (not global) for now to handle input
    /**
     * The game is based on the Hangman, but I like Wheel of Fortune's name better :)
     * A hint is given then you guess the letters of the word or guess the word.
     */
    public void playGame() {
        System.out.println(loadRules("wheel"));

        System.out.print("Press enter to continue: ");
        scan.nextLine();

        clearScreen();
        printGreeting();

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

    protected void printGreeting() {
        clearScreen();

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

        scan.nextLine();
        clearScreen();
    }
}
