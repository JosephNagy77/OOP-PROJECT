import java.util.Scanner;

public class Customer extends Person {
    // Enum for gender
    enum Gender {
        MALE,
        FEMALE,
    }

    // Enum for interests
    enum Interests {
        FoodAndBeverages,
        Electronics,
        Clothing,
        TechAccessories,
        HouseholdEssentials,
        PersonalCare,
        Stationery,
        HealthAndWellness,
        PetSupplies,
        Miscellaneous
    }

    private String Address;
    private Gender CustomerGender;
    private Interests[] interests = new Interests[10];
    private double Balance;
    private int count = 0;

    // Default constructor
    Customer() {
        super(); // Call parent constructor to set username, password, and date of birth
        Address = "No address";
        CustomerGender = Gender.MALE;
        Balance = 0.0;
        interests[count] = Interests.Miscellaneous;
        count++;
    }

    // Parameterized constructor
    Customer(String name, String pass, String date, String address, Gender gender, double balance, Interests interest) {
        super(name, pass, date); // Call parent constructor
        Address = address;
        CustomerGender = gender;
        Balance = balance;
        add_Interest(interest);
    }

    // Setters
    public void set_Address(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        Address = address;
    }

    public void set_CustomerGender(Gender gender) {
        CustomerGender = gender;
    }

    public void set_Balance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        Balance = balance;
    }

    // Add a single interest
    public void add_Interest(Interests interest) {
        if (count >= interests.length) {
            System.out.println("Interest list is full!");
            return;
        }
        for (int i = 0; i < count; i++) {
            if (interests[i] == interest) {
                System.out.println("Interest already added.");
                return;
            }
        }
        interests[count] = interest;
        count++;
    }

    // Add multiple interests from a list or array
    public void add_Interests(Interests[] newInterests) {
        for (Interests interest : newInterests) {
            add_Interest(interest);
        }
    }

    // Getters
    public String get_Address() {
        return Address;
    }

    public Gender get_CustomerGender() {
        return CustomerGender;
    }

    public double get_Balance() {
        return Balance;
    }

    // Display all interests
    public void display_Interests() {
        if (count == 0) {
            System.out.println("No interests added.");
        } else {
            System.out.println("Customer Interests:");
            for (int i = 0; i < count; i++) {
                System.out.println("- " + interests[i]);
            }
        }
    }

    // Get a specific interest by index
    public Interests get_InterestByIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Invalid index. Must be between 0 and " + (count - 1));
        }
        return interests[index];
    }

    // Method to validate and set the address
    public static void promptForAddress(Scanner scanner, Customer customer) {
        while (true) {
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            try {
                customer.set_Address(address);
                break; // Exit the loop if address is valid
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Method to validate and set the gender
    public static void promptForGender(Scanner scanner, Customer customer) {
        while (true) {
            System.out.print("Enter gender (Male/Female/RatherNotSay): ");
            String genderInput = scanner.nextLine().trim();
            try {
                if (genderInput.equalsIgnoreCase("Male")) {
                    customer.set_CustomerGender(Gender.MALE);
                } else if (genderInput.equalsIgnoreCase("Female")) {
                    customer.set_CustomerGender(Gender.FEMALE);
                }
                else {
                    throw new IllegalArgumentException("Invalid gender. Please enter Male, Female, or RatherNotSay.");
                }
                break; // Exit the loop if gender is valid
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Method to validate and set the balance
    public static void promptForBalance(Scanner scanner, Customer customer) {
        while (true) {
            System.out.print("Enter balance: ");
            String balanceInput = scanner.nextLine();
            try {
                double balance = Double.parseDouble(balanceInput);
                customer.set_Balance(balance);
                break; // Exit the loop if balance is valid
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number for balance.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Method to validate and set interests
    public static void promptForInterests(Scanner scanner, Customer customer) {
        while (true) {
            System.out.print("Enter interests (comma-separated, e.g. FoodAndBeverages, Electronics): ");
            String input = scanner.nextLine().trim();
            String[] interestInputs = input.split(",");
            Interests[] selectedInterests = new Interests[interestInputs.length];

            boolean valid = true;
            for (int i = 0; i < interestInputs.length; i++) {
                try {
                    selectedInterests[i] = Interests.valueOf(interestInputs[i].trim());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid interest: " + interestInputs[i].trim());
                    valid = false;
                    break;
                }
            }
            if (valid) {
                customer.add_Interests(selectedInterests);
                break; // Exit the loop if interests are valid
            }
        }
    }

}
