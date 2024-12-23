import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class Person {
    protected String Username;
    protected String Password;
    protected LocalDate DateOfBirth;
    protected int age; // Calculated from the date of birth

    // Default constructor
    Person()
    {
        Username = "Guest";
        Password = "admin";
        DateOfBirth = null;
        age = 0;
    }

    // Parameterized constructor with validation
    Person(String name, String pass, String date) {
        set_Username(name);
        set_Password(pass);
        setDate(date);
    }

    // Validates and sets the date
    public void setDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("Date of birth cannot be empty.");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(date, formatter);

            // Ensure the date is not in the future
            if (parsedDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Date of birth cannot be in the future.");
            }

            this.DateOfBirth = parsedDate;
            calculateAge();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd.");
        }
    }

    // Calculates the age based on the date of birth
    private void calculateAge() {
        if (DateOfBirth != null) {
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(DateOfBirth, currentDate);
            this.age = period.getYears();
        }
    }

    // Validates and sets the username
    public void set_Username(String user) {
        if (user == null || user.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        this.Username = user;
    }

    public void set_Password(String password) {
        this.Password = password;
    }

    // Validates and sets the password
   /* public void set_Password(String pass) {
        if (pass == null || pass.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (!isValidPassword(pass)) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, contain one uppercase letter, and one special character and one number.");
        }
        this.Password = pass;
    }*/

    // Validates the password using a regex
    /*protected boolean isValidPassword(String password) {
        boolean isMatch = Pattern.compile("^(?=.?[A-Z])(?=.?[a-z])(?=.?[0-9])(?=.?[#?!@$%^&*-]).{8,}$")
                .matcher(password)
                .find();
        return isMatch;
    }
*/
    // Getters
    public String get_Username() {
        return Username;
    }

    public String get_Password() {
        return Password;
    }

    public int get_Age() {
        return age;
    }
    public String getDate() {
        if (DateOfBirth != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return DateOfBirth.format(formatter);
        }
        return "No date set";
    }

    // Method to prompt for username and handle error if input is empty
    public static String promptForUsername(Scanner scanner, Person person) {
        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            try {
                person.set_Username(username);
                return username;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Method to prompt for password and handle error if input is empty
    public static String promptForPassword(Scanner scanner, Person person) {
        while (true) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();
            try {
                person.set_Password(password);
                return password;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Method to prompt for date of birth and handle error if input is empty or invalid
    public static String promptForDateOfBirth(Scanner scanner, Person person) {
        while (true) {
            System.out.print("Enter date of birth (yyyy-MM-dd): ");
            String date = scanner.nextLine().trim();
            try {
                person.setDate(date);
                return date;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Main method to manage program flow

}