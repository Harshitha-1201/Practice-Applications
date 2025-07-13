import java.util.Random;
import java.util.Scanner;

public class GuessTheNumberGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        int totalScore = 0;
        int totalRounds = 0;

        System.out.println("Welcome to the Guess the Number Game!");

        while (playAgain) {
            totalRounds++;
            System.out.println("\nRound " + totalRounds);
            System.out.println("Choose difficulty level:");
            System.out.println("1. Easy (Number between 1 and 50, 10 attempts)");
            System.out.println("2. Medium (Number between 1 and 100, 10 attempts)");
            System.out.println("3. Hard (Number between 1 and 200, 15 attempts)");
            System.out.print("Enter your choice (1/2/3): ");
            
            int choice = scanner.nextInt();
            int maxRange = 0;
            int maxAttempts = 0;

            switch (choice) {
                case 1:
                    maxRange = 50;
                    maxAttempts = 10;
                    break;
                case 2:
                    maxRange = 100;
                    maxAttempts = 10;
                    break;
                case 3:
                    maxRange = 200;
                    maxAttempts = 15;
                    break;
                default:
                    System.out.println("Invalid choice. Defaulting to Medium difficulty.");
                    maxRange = 100;
                    maxAttempts = 10;
                    break;
            }

            int randomNumber = random.nextInt(maxRange) + 1;
            int attempts = 0;
            boolean guessedCorrectly = false;

            while (!guessedCorrectly && attempts < maxAttempts) {
                System.out.print("Enter your guess (1-" + maxRange + "): ");
                int guess = scanner.nextInt();
                attempts++;

                if (guess == randomNumber) {
                    System.out.println("Congratulations! You guessed the correct number " + randomNumber + " in " + attempts + " attempts.");
                    totalScore += maxAttempts - attempts + 1; // Score calculation based on attempts and difficulty level
                    guessedCorrectly = true;
                } else if (guess < randomNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Sorry, you did not guess the number. The correct number was: " + randomNumber);
            }

            System.out.println("Your current score: " + totalScore);
            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainResponse = scanner.next();
            playAgain = playAgainResponse.equalsIgnoreCase("yes");
        }

        System.out.println("Thank you for playing! Your final score is: " + totalScore);
        System.out.println("Total rounds played: " + totalRounds);
        scanner.close();
    }
}
