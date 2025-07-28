import java.io.*; // Import classes for file input/output
import java.util.ArrayList; // Import ArrayList to store player guesses
import java.util.Random; // Import Random for number generation
import java.util.Scanner; // Import Scanner for user input

// Define the main class for the game
public class GuessTheNumber {
    // Max number of guesses allowed 
    private static final int MAX_ATTEMPTS = 10; // Constant for maximum number of allowed guesses
    // Random number range (1–100)
    private static final int NUMBER_RANGE = 100; // Constant for the upper bound of the random number range
    // File to store game results 
    private static final String FILE_NAME = "game_results.txt"; // Constant filename to save game results

    // Main method: entry point of the program
    public static void main(String[] args) {
        // Try-with-resources to safely use Scanner for input
        try (Scanner scanner = new Scanner(System.in)) {

            // Display previously saved game results from file
            readGameResultsFromFile();

            // Start the game
            System.out.println("Welcome to Guess the Number!"); // Greet the player
            System.out.println("I'm thinking of a number between 1 and " + NUMBER_RANGE); // Constant for the upper bound of the random number range
            System.out.println("You have " + MAX_ATTEMPTS + " attempts to guess it."); // Constant for maximum number of allowed guesses

            // Create Random object to generate the secret number
            Random rand = new Random();
            int targetNumber = rand.nextInt(NUMBER_RANGE) + 1; // random number from 1–100 
            ArrayList<Integer> guesses = new ArrayList<>(); // List to store all of the user's guesses

            boolean hasWon = false;
            int attempt;

            // Constant for maximum number of allowed guesses
            for (attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
                System.out.print("Enter your guess #" + attempt + ": "); // Prompt user to enter their guess
                int guess;

                // Validate integer input
                try {
                    guess = Integer.parseInt(scanner.nextLine()); // Parse user input as integer
                } catch (NumberFormatException e) { // Handle non-integer input errors
                    System.out.println("Invalid input. Please enter a valid number.");
                    attempt--; // Don't count invalid input as an attempt
                    continue;
                }
                // End of main method

                guesses.add(guess); // Store each guess // Add the guess to the list of guesses

                // Check if the user guessed correctly
                if (guess == targetNumber) {
                    // Notify user of correct guess
                    System.out.println("🎉 Congratulations! You guessed the number in " + attempt + " tries.");
                    hasWon = true;
                    break;
                } else if (guess < targetNumber) {
                    System.out.println("Too low!"); // Hint: user's guess was too low
                } else {
                    System.out.println("Too high!");
                }
            }

            // If user didn't win, reveal the correct number
            if (!hasWon) {
                // Inform user about allowed number of attempts
                System.out.println("❌ You've used all attempts. The correct number was: " + targetNumber);
            }

            // Save the game result to a file
            saveResultToFile(hasWon, attempt, guesses);

            scanner.close();
        }
    }

    /**
     *Reads previous game results from the file and displays them to the player.
     **/

    private static void readGameResultsFromFile() {
        System.out.println("Previous Game Results:");

        // Constant filename to save game results
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean hasData = false;

            while ((line = reader.readLine()) != null) {
                System.out.println("  • " + line);
                hasData = true;
            }

            if (!hasData) {
                System.out.println("  No previous results found.");
            }
        } catch (IOException e) {
            System.out.println("  No results file found. Starting fresh.");
        }

        System.out.println(); // blank line for spacing
    }

    /**
     * Saves the result of the current game to the file.
     **/
    
    private static void saveResultToFile(boolean hasWon, int attempts, ArrayList<Integer> guesses) {
        // List to store all of the user's guesses
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            // Constant filename to save game results
            String result = hasWon
                ? "Win in " + attempts + " attempts. Guesses: " + guesses
                : "Loss after " + MAX_ATTEMPTS + " attempts. Guesses: " + guesses; // Constant for maximum number of allowed guesses

            writer.write(result);
            writer.newLine(); // move to the next line for future results
            System.out.println("\n✅ Game result saved to file.");
        } catch (IOException e) {
            System.out.println("❗ Error saving game result: " + e.getMessage());
        }
    }
}
