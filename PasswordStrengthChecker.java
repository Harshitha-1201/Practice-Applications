import java.util.Scanner;

public class PasswordStrengthChecker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a password to check its strength: ");
        String password = scanner.nextLine();

        System.out.println("Password Strength: " + getPasswordStrength(password));

        scanner.close();
    }

    private static String getPasswordStrength(String password) {
        if (password.length() < 8) {
            return "Weak: Password must be at least 8 characters long.";
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpperCase = true;
            if (Character.isLowerCase(c)) hasLowerCase = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
        }

        if (hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar) {
            return "Strong: Password meets all criteria.";
        } 
        if (hasUpperCase && hasLowerCase && hasDigit) {
            return "Moderate: Password is missing special characters.";
        } 
        if (hasUpperCase && hasLowerCase) {
            return "Weak: Password should include digits and special characters.";
        }

        return "Very Weak: Password should include uppercase letters, lowercase letters, digits, and special characters.";
    }
}
