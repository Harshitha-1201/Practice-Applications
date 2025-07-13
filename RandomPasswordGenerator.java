import java.security.SecureRandom;
import java.util.Scanner;

public class RandomPasswordGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SecureRandom random = new SecureRandom();

        System.out.print("Enter password length: ");
        int length = scanner.nextInt();

        System.out.print("Include numbers? (y/n): ");
        boolean includeNumbers = scanner.next().equalsIgnoreCase("y");

        System.out.print("Include lowercase letters? (y/n): ");
        boolean includeLowercase = scanner.next().equalsIgnoreCase("y");

        System.out.print("Include uppercase letters? (y/n): ");
        boolean includeUppercase = scanner.next().equalsIgnoreCase("y");

        System.out.print("Include special characters? (y/n): ");
        boolean includeSpecial = scanner.next().equalsIgnoreCase("y");

        String allowedCharacters = (includeNumbers ? "0123456789" : "") +
                                   (includeLowercase ? "abcdefghijklmnopqrstuvwxyz" : "") +
                                   (includeUppercase ? "ABCDEFGHIJKLMNOPQRSTUVWXYZ" : "") +
                                   (includeSpecial ? "!@#$%^&*()-_=+[]{}|;:,.<>?" : "");

        if (allowedCharacters.isEmpty()) {
            System.out.println("At least one character type must be selected.");
            return;
        }

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allowedCharacters.length());
            password.append(allowedCharacters.charAt(index));
        }

        System.out.println("Generated Password: " + password);
        scanner.close();
    }
}
