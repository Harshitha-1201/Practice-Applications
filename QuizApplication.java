import java.util.*;
import java.util.concurrent.*;

public class QuizApplication {

    private static class User {
        private String username;
        private String password;
        private String profileInfo;

        public User(String username, String password, String profileInfo) {
            this.username = username;
            this.password = password;
            this.profileInfo = profileInfo;
        }

        public String getUsername() {
            return username;
        }

        public String getProfileInfo() {
            return profileInfo;
        }

        public void setProfileInfo(String profileInfo) {
            this.profileInfo = profileInfo;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean authenticate(String password) {
            return this.password.equals(password);
        }
    }

    private static class Profile {
        public void updateProfile(User user, String newProfileInfo) {
            user.setProfileInfo(newProfileInfo);
            System.out.println("Profile updated successfully.");
        }

        public void updatePassword(User user, String newPassword) {
            user.setPassword(newPassword);
            System.out.println("Password updated successfully.");
        }
    }

    private static class Quiz {
        private final Map<String, String> questions;
        private final Map<String, String> answers;
        private final Map<String, String> userResponses;

        public Quiz() {
            questions = new HashMap<>();
            answers = new HashMap<>();
            userResponses = new HashMap<>();

            questions.put("What is the capital of France?", "A. Paris\nB. London\nC. Rome\nD. Berlin");
            answers.put("What is the capital of France?", "A");
        }

        public void startQuiz() {
            Scanner scanner = new Scanner(System.in);
            for (String question : questions.keySet()) {
                System.out.println(question);
                System.out.println(questions.get(question));
                System.out.print("Your answer: ");
                String response = scanner.nextLine().trim().toUpperCase();
                userResponses.put(question, response);
            }
        }

        public void displayResults() {
            int score = 0;
            for (String question : questions.keySet()) {
                String correctAnswer = answers.get(question);
                String userAnswer = userResponses.get(question);
                if (correctAnswer.equals(userAnswer)) {
                    score++;
                }
            }
            System.out.println("Your score: " + score + "/" + questions.size());
        }
    }

    private static class Session {
        private User currentUser;
        private boolean loggedIn;

        public void login(User user, String password) {
            if (user.authenticate(password)) {
                currentUser = user;
                loggedIn = true;
                System.out.println("Login successful.");
            } else {
                System.out.println("Invalid password.");
            }
        }

        public void logout() {
            currentUser = null;
            loggedIn = false;
            System.out.println("Logged out successfully.");
        }

        public boolean isLoggedIn() {
            return loggedIn;
        }

        public User getCurrentUser() {
            return currentUser;
        }
    }

    public static class Main {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            User user = new User("user1", "password123", "Initial profile info");
            Profile profile = new Profile();
            Quiz quiz = new Quiz();
            Session session = new Session();

            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (username.equals(user.getUsername())) {
                session.login(user, password);
            } else {
                System.out.println("Invalid username.");
                return;
            }

            if (session.isLoggedIn()) {
                while (true) {
                    System.out.println("\n1. Update Profile");
                    System.out.println("2. Update Password");
                    System.out.println("3. Start Quiz");
                    System.out.println("4. Logout");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.print("Enter new profile info: ");
                            String newProfileInfo = scanner.nextLine();
                            profile.updateProfile(user, newProfileInfo);
                            break;
                        case 2:
                            System.out.print("Enter new password: ");
                            String newPassword = scanner.nextLine();
                            profile.updatePassword(user, newPassword);
                            break;
                        case 3:
                            System.out.println("Starting quiz with 1-minute timer...");
                            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                            Runnable task = () -> {
                                System.out.println("Time is up! Submitting your quiz.");
                                quiz.displayResults();
                                System.exit(0);
                            };
                            scheduler.schedule(task, 1, TimeUnit.MINUTES);

                            quiz.startQuiz();
                            quiz.displayResults();
                            scheduler.shutdown();
                            break;
                        case 4:
                            session.logout();
                            return; // Exit after logout
                        default:
                            System.out.println("Invalid option.");
                            break;
                    }
                }
            }
        }
    }
}
