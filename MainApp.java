import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.geometry.Insets;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


public class MainApp extends Application {

    private Cart cart;
    private Label cartCounter;
    private Database db = new Database();// Cart item counter label
    private VBox productDisplay; // For displaying search results
    private TextField searchBar; // For the search bar


    @Override
    public void start(Stage primaryStage) {

        cartCounter = new Label("0");
        showLoginWindow(primaryStage);
    }


    private void showLoginWindow(Stage primaryStage) {
        VBox loginLayout = new VBox();
        loginLayout.setSpacing(20);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setStyle("-fx-padding: 20; -fx-background-color: #f8f9fa; -fx-border-color: #e0e0e0; -fx-border-width: 1;");

        Label title = new Label("Welcome to the Supermarket App");
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");

        Label customerLabel = new Label("Customer");
        customerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2ecc71;");

        Button customerRegisterButton = new Button("Register");
        styleButton(customerRegisterButton, "#2ecc71", "white");

        Button customerLoginButton = new Button("Login");
        styleButton(customerLoginButton, "#3498db", "white");

        HBox customerButtons = new HBox(customerRegisterButton, customerLoginButton);
        customerButtons.setSpacing(10);
        customerButtons.setAlignment(Pos.CENTER);

        Label adminLabel = new Label("Admin");
        adminLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #e74c3c;");

        Button adminLoginButton = new Button("Admin Login");
        styleButton(adminLoginButton, "#e74c3c", "white");

        loginLayout.getChildren().addAll(title, customerLabel, customerButtons, adminLabel, adminLoginButton);

        Scene loginScene = new Scene(loginLayout, 400, 300);

        // Bindings for responsiveness
        loginScene.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            double fontSize = Math.max(12, newWidth.doubleValue() / 30); // Dynamically calculate font size
            double buttonWidth = Math.min(200, newWidth.doubleValue() / 4); // Limit button width
            double buttonHeight = Math.min(50, newWidth.doubleValue() / 15); // Limit button height

            // Update title font size
            title.setStyle("-fx-font-size: " + (fontSize * 1.5) + "px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

            // Update other elements' font sizes
            customerLabel.setStyle("-fx-font-size: " + fontSize + "px; -fx-font-weight: bold; -fx-text-fill: #2ecc71;");
            adminLabel.setStyle("-fx-font-size: " + fontSize + "px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");

            // Update button sizes
            customerRegisterButton.setPrefWidth(buttonWidth);
            customerRegisterButton.setPrefHeight(buttonHeight);
            customerLoginButton.setPrefWidth(buttonWidth);
            customerLoginButton.setPrefHeight(buttonHeight);
            adminLoginButton.setPrefWidth(buttonWidth);
            adminLoginButton.setPrefHeight(buttonHeight);
        });

        primaryStage.setTitle("Login / Register");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // Button Actions
        customerRegisterButton.setOnAction(event -> showCustomerRegisterWindow(primaryStage));
        customerLoginButton.setOnAction(event -> showCustomerLoginWindow(primaryStage));
        adminLoginButton.setOnAction(event -> showAdminLoginWindow(primaryStage));
    }


    private void showAdminLoginWindow(Stage primaryStage) {
        VBox adminLoginLayout = new VBox();
        adminLoginLayout.setSpacing(20);
        adminLoginLayout.setAlignment(Pos.CENTER);
        adminLoginLayout.setStyle("-fx-padding: 20; -fx-background-color: #f8f9fa; -fx-border-color: #e0e0e0; -fx-border-width: 1;");

        Label title = new Label("Admin Login");
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");

        // Username Field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter admin username");
        usernameField.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        // Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter admin password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        // Login Button
        Button loginButton = new Button("Login");
        styleButton(loginButton, "#e74c3c", "white");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // Validate admin credentials using the CheckAdmin function
            if (!username.isEmpty() && !password.isEmpty()) {
                if (Database.CheckAdmin(username, password)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Admin Login Successful!");
                    alert.showAndWait();
                    showAdminDashboard(primaryStage); // Proceed to Admin Dashboard
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password. Please try again.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter both username and password.");
                alert.showAndWait();
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        styleButton(backButton, "#e74c3c", "white");
        backButton.setOnAction(e -> showLoginWindow(primaryStage));

        // Add all components to the layout
        adminLoginLayout.getChildren().addAll(title, usernameField, passwordField, loginButton, backButton);

        // Create and set the scene
        Scene adminLoginScene = new Scene(adminLoginLayout, 400, 300);

        primaryStage.setScene(adminLoginScene);

        // Reset input fields each time the scene is shown
        primaryStage.setOnShown(event -> {
            usernameField.clear();
            passwordField.clear();
        });
    }


    private void showCustomerLoginWindow(Stage primaryStage) {
        VBox loginLayout = new VBox();
        loginLayout.setSpacing(20);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setStyle("-fx-padding: 20; -fx-background-color: #f8f9fa; -fx-border-color: #e0e0e0; -fx-border-width: 1;");

        Label title = new Label("Customer Login");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: #34495e;");

        // Username Field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        // Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        // Login Button
        Button loginButton = new Button("Login");
        styleButton(loginButton, "#3498db", "white");

        loginButton.setOnAction(e -> {
            String enteredUsername = usernameField.getText().trim();
            String enteredPassword = passwordField.getText().trim();

            // Check credentials and get the customer
            Customer loggedInCustomer = Database.CheckCustomer(enteredUsername, enteredPassword);

            if (loggedInCustomer != null) {
                // Successful login, create a new Cart for the customer
                cart = new Cart(loggedInCustomer);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Login Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Welcome back, " + loggedInCustomer.get_Username() + "!");
                successAlert.showAndWait();

                showMainWindow(primaryStage); // Proceed to the main window
            } else {
                // Invalid credentials
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Login Failed");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Invalid username or password. Please try again.");
                errorAlert.showAndWait();
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        styleButton(backButton, "#e74c3c", "white");
        backButton.setOnAction(e -> showLoginWindow(primaryStage));

        // Add all components to the layout
        loginLayout.getChildren().addAll(title, usernameField, passwordField, loginButton, backButton);

        // Create and set the scene
        Scene loginScene = new Scene(loginLayout, 400, 300);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Customer Login");
        primaryStage.show();
    }



    private void showCustomerRegisterWindow(Stage primaryStage) {
        VBox registerLayout = new VBox();
        registerLayout.setSpacing(10);
        registerLayout.setAlignment(Pos.TOP_CENTER);
        registerLayout.setStyle("-fx-padding: 20; -fx-background-color: #f8f9fa; -fx-border-color: #e0e0e0; -fx-border-width: 1;");

        Label title = new Label("Customer Registration");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        // Username Field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        // Password Field with Show/Hide Feature
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Enter your password");
        passwordTextField.setStyle("-fx-font-size: 14px; -fx-padding: 5;");
        passwordTextField.setManaged(false);
        passwordTextField.setVisible(false);

        // Link the PasswordField and TextField
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        CheckBox showPasswordCheckbox = new CheckBox("Show Password");
        showPasswordCheckbox.setFont(Font.font("Arial", 12));
        showPasswordCheckbox.setTextFill(Color.GRAY);

        // Toggle between showing and hiding the password
        showPasswordCheckbox.setOnAction(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passwordTextField.setVisible(true);
                passwordTextField.setManaged(true);
                passwordField.setVisible(false);
                passwordField.setManaged(false);
            } else {
                passwordTextField.setVisible(false);
                passwordTextField.setManaged(false);
                passwordField.setVisible(true);
                passwordField.setManaged(true);
            }
        });

        Label passwordInfo = new Label("Password must contain at least: 8 characters, 1 capital letter, and 1 special character.");
        passwordInfo.setFont(Font.font("Arial", 10));
        passwordInfo.setTextFill(Color.GRAY);

        // Date of Birth Fields: Year, Month, Day
        TextField yearField = new TextField();
        yearField.setPromptText("YYYY");
        yearField.setMaxWidth(70);
        TextField monthField = new TextField();
        monthField.setPromptText("MM");
        monthField.setMaxWidth(50);
        TextField dayField = new TextField();
        dayField.setPromptText("DD");
        dayField.setMaxWidth(50);

        HBox dobFields = new HBox(10, yearField, monthField, dayField);
        dobFields.setAlignment(Pos.CENTER);

        Label dobInfo = new Label("Enter date of birth: YYYY-MM-DD");
        dobInfo.setFont(Font.font("Arial", 10));
        dobInfo.setTextFill(Color.GRAY);

        // Address Field
        TextField addressField = new TextField();
        addressField.setPromptText("Enter your address");
        addressField.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        // Gender Dropdown
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Rather not say");
        genderComboBox.setPromptText("Choose gender");
        genderComboBox.setStyle("-fx-font-size: 14px;");

        // Interests Pane
        Label interestsLabel = new Label("Select Interests:");
        interestsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        FlowPane interestsPane = new FlowPane();
        interestsPane.setHgap(10);
        interestsPane.setVgap(10);
        interestsPane.setStyle("-fx-padding: 10;");

        String[] interestsOptions = {
                "FoodAndBeverages", "Electronics", "Clothing", "TechAccessories",
                "HouseholdEssentials", "PersonalCare", "Stationery", "HealthAndWellness",
                "PetSupplies", "Others"
        };

        List<String> selectedInterests = new ArrayList<>();
        for (String interest : interestsOptions) {
            Button interestButton = new Button(interest);
            interestButton.setStyle("-fx-font-size: 14px; -fx-background-color: #e0e0e0; -fx-text-fill: #34495e; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
            interestButton.setOnAction(e -> {
                if (interestButton.getStyle().contains("2ecc71")) {
                    interestButton.setStyle("-fx-font-size: 14px; -fx-background-color: #e0e0e0; -fx-text-fill: #34495e; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
                    selectedInterests.remove(interest);
                } else {
                    interestButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2ecc71; -fx-text-fill: white; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
                    selectedInterests.add(interest);
                }
            });
            interestsPane.getChildren().add(interestButton);
        }

        // Register and Back Buttons
        Button registerButton = new Button("Register");
        styleButton(registerButton, "#2ecc71", "white");
        Button backButton = new Button("Back");
        styleButton(backButton, "#e74c3c", "white");
        backButton.setOnAction(e -> showLoginWindow(primaryStage));

        // Register button action
        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String year = yearField.getText().trim();
            String month = monthField.getText().trim();
            String day = dayField.getText().trim();
            String address = addressField.getText().trim();
            String genderStr = genderComboBox.getValue();

            // Input validation
            if (username.isEmpty() || password.isEmpty() || year.isEmpty() || month.isEmpty() || day.isEmpty() || address.isEmpty() || genderStr == null) {
                showAlert(Alert.AlertType.ERROR, "Missing Information", "All fields must be filled!");
                return;
            }

            // Password validation
            if (!password.matches("^(?=.*[A-Z])(?=.*[!@#$%^&+-])(?=.*[a-z])(?=.*\\d).{8,}$")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Password",
                        "Password must be at least 8 characters long, contain one uppercase letter, one lowercase letter, one number, and one special character (!@#$%^&+-).\nExample: Password1!");
                return;
            }

            // Construct and validate date of birth
            String dateOfBirth;
            try {
                dateOfBirth = constructAndValidateDate(year, month, day);
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Date", ex.getMessage());
                return;
            }

            // Determine gender
            Customer.Gender gender = switch (genderStr) {
                case "Female" -> Customer.Gender.FEMALE;
                case "Male" -> Customer.Gender.MALE;
                default -> Customer.Gender.MALE;
            };

            Customer.Interests[] selectedCustomerInterests = getSelectedInterests(selectedInterests);

            Database.create_customer(username, password, dateOfBirth, address, gender, 0.0, selectedCustomerInterests);

            // Success message
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Welcome, " + username + "!");
            showLoginWindow(primaryStage);
        });

        // Add all components to the layout
        registerLayout.getChildren().addAll(
                title, usernameField,
                new VBox(5, passwordField, passwordTextField, showPasswordCheckbox),
                passwordInfo, dobFields, dobInfo,
                addressField, genderComboBox,
                interestsLabel, interestsPane,
                registerButton, backButton
        );

        // Create and set the scene
        Scene registerScene = new Scene(registerLayout, 600, 800);
        primaryStage.setScene(registerScene);
    }

    private Customer.Interests[] getSelectedInterests(List<String> selectedInterests) {
        if (selectedInterests.isEmpty()) {
            return new Customer.Interests[]{Customer.Interests.Miscellaneous};
        }

        List<Customer.Interests> interestList = new ArrayList<>();
        for (String interestStr : selectedInterests) {
            try {
                interestList.add(Customer.Interests.valueOf(interestStr));
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid interest: " + interestStr);
            }
        }
        return interestList.toArray(new Customer.Interests[0]);
    }


    private String constructAndValidateDate(String year, String month, String day) throws IllegalArgumentException {
        try {
            int yearInt = Integer.parseInt(year);
            int monthInt = Integer.parseInt(month);
            int dayInt = Integer.parseInt(day);

            if (yearInt < 1900 || yearInt > 2024) {
                throw new IllegalArgumentException("Year must be between 1900 and 2024.");
            }
            if (monthInt < 1 || monthInt > 12) {
                throw new IllegalArgumentException("Month must be between 1 and 12.");
            }
            if (dayInt < 1 || dayInt > getDaysInMonth(monthInt, yearInt)) {
                throw new IllegalArgumentException("Invalid day for the selected month and year.");
            }

            // Return formatted date
            return String.format("%04d-%02d-%02d", yearInt, monthInt, dayInt);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Year, month, and day must be valid numbers.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private int getDaysInMonth(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31; // Months with 31 days
            case 4: case 6: case 9: case 11:
                return 30; // Months with 30 days
            case 2:
                // February: Check for leap year
                return isLeapYear(year) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid month: " + month);
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }




    private void showMainWindow(Stage primaryStage) {
        VBox root = new VBox(10);
        HBox header = new HBox(10);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        header.setStyle("-fx-background-color: #2ecc71; -fx-padding: 10;");
        header.setAlignment(Pos.CENTER_LEFT);

        // Back Button
        Button backButton = new Button("\u2190 Back");
        styleHeaderButton(backButton);
        backButton.setOnAction(event -> showLoginWindow(primaryStage));

        // Search Bar
        searchBar = new TextField(); // Use class-level field
        searchBar.setPrefWidth(400);
        searchBar.setPromptText("Search product here");
        searchBar.setStyle(
                "-fx-font-size: 14px; -fx-padding: 5; -fx-border-radius: 15; " +
                        "-fx-background-color: #ecf0f1; -fx-text-fill: #7f8c8d;");

        // Cart Button
        Button cartButton = new Button("\ud83d\uded2 Cart");
        styleHeaderButton(cartButton);
        cartCounter = new Label(String.valueOf(cart.get_ItemCount()));
        cartCounter.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox cartBox = new HBox(5, cartButton, cartCounter);
        cartBox.setAlignment(Pos.CENTER_RIGHT);

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        header.getChildren().addAll(backButton, leftSpacer, searchBar, rightSpacer, cartBox);
        root.getChildren().add(header);

        // Product Display
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        productDisplay = new VBox(15); // Use class-level field
        productDisplay.setStyle("-fx-padding: 20; -fx-background-color: #f8f9fa;");
        scrollPane.setContent(productDisplay);

        root.getChildren().add(scrollPane);

        // Event for Search Bar
        searchBar.setOnKeyReleased(event -> refreshSearchResults());

        cartButton.setOnAction(event -> openCartWindow(primaryStage));

        // Initial call to populate search results
        refreshSearchResults();

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Supermarket App");
        primaryStage.show();
    }


    private HBox createProductRow(Product product) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Product Name
        Label nameLabel = new Label(product.get_Name());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        // Category Label
        Label categoryLabel = new Label("Category: " + getCategoryName(product));
        categoryLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        // Price Label
        Label priceLabel = new Label("Price: $" + String.format("%.2f", product.get_price()));
        priceLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");

        // Stock Label
        Label stockLabel = new Label("Stock: " + product.get_stock());
        stockLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");

        // Quantity Selector
        Spinner<Integer> quantitySpinner = new Spinner<>(1, product.get_stock(), 1);
        quantitySpinner.setEditable(true);
        quantitySpinner.setStyle("-fx-background-color: white; -fx-font-size: 14px;");

        // Add to Cart Button
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle(
                "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15; -fx-border-radius: 5; -fx-background-radius: 5;");

        addToCartButton.setOnAction(event -> {
            int selectedQuantity = quantitySpinner.getValue();
            if (selectedQuantity > 0) {
                int availableStock = product.get_stock();
                if (selectedQuantity > availableStock) {
                    // If quantity exceeds stock, add all available stock
                    cart.AddItem(product, availableStock);
                    showAlert("Stock Limit", "Only " + availableStock + " items of \"" + product.get_Name() + "\" were added to the cart.");
                } else {
                    cart.AddItem(product, selectedQuantity);
                    showAlert("Added to Cart", selectedQuantity + " items of \"" + product.get_Name() + "\" added to your cart.");
                }
                cartCounter.setText(String.valueOf(cart.get_ItemCount()));
            } else {
                showAlert("Invalid Quantity", "Quantity cannot be 0.");
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Add all components to the row
        row.getChildren().addAll(nameLabel, categoryLabel, priceLabel, stockLabel, spacer, quantitySpinner, addToCartButton);
        return row;
    }

     private String getCategoryName(Product product) {
        if (product instanceof Electronics) {
            return ((Electronics) product).get_Category().toString();
        } else if (product instanceof Consumables) {
            return ((Consumables) product).get_Category().toString();
        } else if (product instanceof Clothing) {
            return ((Clothing) product).get_Category().toString() + " - " + ((Clothing) product).get_Size();
        }
        return "Unknown";
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void styleHeaderButton(Button button) {
        button.setStyle(
                "-fx-background-color: white; -fx-text-fill: #2ecc71; -fx-font-size: 14px;" +
                        "-fx-font-weight: bold; -fx-border-color: #2ecc71; -fx-border-width: 1;" +
                        "-fx-padding: 5 15; -fx-border-radius: 5; -fx-background-radius: 5;");

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px;" +
                        "-fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: white; -fx-text-fill: #2ecc71; -fx-font-size: 14px;" +
                        "-fx-font-weight: bold; -fx-border-color: #2ecc71; -fx-border-width: 1;" +
                        "-fx-padding: 5 15; -fx-border-radius: 5; -fx-background-radius: 5;"));
    }



    /**
     * Displays the Cart Window with a styled table, proceed to checkout button, and total price.
     */
    private Label totalLabel; // Declare totalLabel as a class-level variable

    private void openCartWindow(Stage primaryStage) {
        Stage cartStage = new Stage();
        VBox cartLayout = new VBox();
        cartStage.setMinWidth(700); // Set minimum width
        cartStage.setMinHeight(500); // Set minimum height
        cartLayout.setSpacing(20);
        cartLayout.setAlignment(Pos.TOP_CENTER);
        cartLayout.setStyle("-fx-padding: 20; -fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 1;");

        Label cartLabel = new Label("🛒 Your Shopping Cart");
        cartLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        TableView<Product> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().get_Name()));
        nameColumn.setPrefWidth(200);

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cart.get_Quantity(cart.indexOfProduct(cellData.getValue()))).asObject());
        quantityColumn.setPrefWidth(100);

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price Per Piece ($)");
        priceColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().get_price()).asObject());
        priceColumn.setPrefWidth(150);

        // Delete Button Column
        TableColumn<Product, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5;");
                deleteButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());

                    // Remove product from the cart
                    cart.DeleteItem(product);
                    getTableView().getItems().remove(product);
                    cartCounter.setText(String.valueOf(cart.get_ItemCount())); // Update cart counter
                    // Update total label
                    totalLabel.setText("Total Price: $" + String.format("%.2f", cart.get_TotalPrice()));
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Populate the TableView with products from the cart
        for (int i = 0; i < cart.get_ItemCount(); i++) {
            tableView.getItems().add(cart.get_Item(i));
        }

        // Add columns to the table
        tableView.getColumns().addAll(nameColumn, quantityColumn, priceColumn, deleteColumn);

        totalLabel = new Label("Total Price: $" + String.format("%.2f", cart.get_TotalPrice())); // Initialize totalLabel
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2ecc71;");

        // Proceed to Checkout Button
        Button checkoutButton = new Button("Proceed to Checkout");
        styleButton(checkoutButton, "#2ecc71", "white");

        checkoutButton.setOnAction(e -> {
            if (cart.get_ItemCount() == 0) {
                // Validation: If the cart is empty, show a warning
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty Cart");
                alert.setHeaderText(null);
                alert.setContentText("Your cart is empty. Please add items to proceed with checkout.");
                alert.showAndWait();
            } else {
                Order newOrder = new Order(cart.Customers_data);
                for (int i = 0; i < cart.get_ItemCount(); i++) {
                    newOrder.AddItem(cart.get_Item(i), cart.get_Quantity(i));
                }

                // Proceed to the checkout window
                showCheckoutWindow(primaryStage, cartStage, newOrder);
            }
        });

        Button closeButton = new Button("Close");
        styleButton(closeButton, "#e74c3c", "white");
        closeButton.setOnAction(e -> cartStage.close());

        HBox buttonBox = new HBox(10, checkoutButton, closeButton);
        buttonBox.setAlignment(Pos.CENTER);

        cartLayout.getChildren().addAll(cartLabel, tableView, totalLabel, buttonBox);
        Scene cartScene = new Scene(cartLayout, 600, 500);
        cartStage.setScene(cartScene);
        cartStage.setTitle("Cart");
        cartStage.show();
    }





    /**
     * Updates the stock in the database after an order is confirmed.
     */
    private void updateStockAfterOrder(Order order) {
        for (int i = 0; i < order.get_ItemCount(); i++) {
            Product product = order.get_Item(i);
            int quantity = order.get_Quantity(i);
            Database.UpdateStock(product.get_Name(), quantity);
        }
    }



    private void updateCart() {
        cart.Clear(); // Clear the cart using the clear method
        cartCounter.setText(String.valueOf(cart.get_ItemCount())); // Update cart counter
    }

    private void showCheckoutWindow(Stage primaryStage, Stage cartStage , Order newOrder) {
        Stage checkoutStage = new Stage();
        VBox checkoutLayout = new VBox();
        checkoutLayout.setSpacing(20);
        checkoutLayout.setAlignment(Pos.CENTER);
        checkoutLayout.setStyle("-fx-padding: 20; -fx-background-color: #f8f9fa; -fx-border-color: #e0e0e0; -fx-border-width: 1;");

        Label checkoutLabel = new Label("Select Payment Method");
        checkoutLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        Button cashOnDeliveryButton = new Button("Cash on Delivery");
        styleButton(cashOnDeliveryButton, "#3498db", "white");
        cashOnDeliveryButton.setOnAction(e -> {
            updateCart(); // Clear the cart and update UI
            newOrder.set_PaymentMethod(Order.PaymentMethod.CashOnDelivery);
            confirmOrder(newOrder, cartStage, checkoutStage);
            showOrderConfirmation();
        });

        Button balanceButton = new Button("Balance");
        styleButton(balanceButton, "#3498db", "white");
        balanceButton.setOnAction(e -> {
            double totalPrice = newOrder.get_TotalPrice(); // Get total price of the order
            double customerBalance = newOrder.Customers_data.get_Balance(); // Get customer's balance

            if (customerBalance >= totalPrice) {
                // Deduct the total price from the customer's balance
                newOrder.Customers_data.set_Balance(customerBalance - totalPrice);

                // Continue with checkout
                updateCart(); // Clear the cart and update UI
                newOrder.set_PaymentMethod(Order.PaymentMethod.Balance);
                confirmOrder(newOrder, cartStage, checkoutStage);
                showOrderConfirmation();
            } else {
                // Show error alert if balance is insufficient
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Insufficient Balance");
                alert.setHeaderText(null);
                alert.setContentText("Your balance is insufficient to complete this purchase.\n" +
                        "Current Balance: $" + String.format("%.2f", customerBalance) + "\n" +
                        "Total Price: $" + String.format("%.2f", totalPrice));
                alert.showAndWait();
            }
        });


        Button vodafoneCashButton = new Button("Vodafone Cash");
        styleButton(vodafoneCashButton, "#3498db", "white");
        vodafoneCashButton.setOnAction(e -> {
            showVodafoneCashWindow(cartStage, checkoutStage, newOrder);
        });

        checkoutLayout.getChildren().addAll(checkoutLabel, cashOnDeliveryButton, balanceButton, vodafoneCashButton);
        Scene checkoutScene = new Scene(checkoutLayout, 400, 300);
        checkoutStage.setScene(checkoutScene);
        checkoutStage.setTitle("Checkout");
        checkoutStage.show();
    }



    private String cartToString() {
        StringBuilder cartString = new StringBuilder();
        for (int i = 0; i < cart.get_ItemCount(); i++) {
            Product product = cart.get_Item(i); // Assuming you have get_Item method
            int quantity = cart.get_Quantity(i); // Assuming you have get_Quantity method
            cartString.append(product.get_Name())
                    .append(" - Quantity: ").append(quantity)
                    .append(", Price: $").append(product.get_price())
                    .append("\n");
        }
        return cartString.toString();
    }


    private void showVodafoneCashWindow(Stage cartStage, Stage checkoutStage, Order newOrder) {
        Stage vodafoneStage = new Stage();
        VBox vodafoneLayout = new VBox();
        vodafoneLayout.setSpacing(20);
        vodafoneLayout.setAlignment(Pos.CENTER);
        vodafoneLayout.setStyle("-fx-padding: 20; -fx-background-color: #f8f9fa; -fx-border-color: #e0e0e0; -fx-border-width: 1;");

        Label phoneLabel = new Label("Enter Your Vodafone Cash Number:");
        phoneLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Enter phone number");
        phoneNumberField.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        Button confirmButton = new Button("Confirm");
        styleButton(confirmButton, "#2ecc71", "white");

        confirmButton.setOnAction(e -> {
            String phoneNumber = phoneNumberField.getText().trim();

            // Validation logic
            if (phoneNumber.length() == 11 && phoneNumber.matches("\\d+") && phoneNumber.startsWith("010")) {
                // Valid phone number
                updateCart();
                vodafoneStage.close();
                newOrder.set_PaymentMethod(Order.PaymentMethod.VodafoneCash);
                confirmOrder(newOrder, cartStage, checkoutStage);
                showOrderConfirmation();
            } else {
                // Show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Phone Number");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid Vodafone Cash number.\n" +
                        "It should be 11 digits long, contain only numbers, \n" +
                        "Must start with '010'.");
                alert.showAndWait();
            }
        });

        vodafoneLayout.getChildren().addAll(phoneLabel, phoneNumberField, confirmButton);
        Scene vodafoneScene = new Scene(vodafoneLayout, 400, 200);
        vodafoneStage.setScene(vodafoneScene);
        vodafoneStage.setTitle("Vodafone Cash");
        vodafoneStage.show();
    }


    private void confirmOrder(Order order, Stage cartStage, Stage checkoutStage) {
        order.Confirm_Order(); // Update stock and balance
        Database.Add_Order(order); // Add order to the database

        // Reinitialize cart for the same customer
        cart = new Cart(cart.Customers_data);

        // Clear the cart UI and reset cart counter
        cartCounter.setText(String.valueOf(cart.get_ItemCount()));

        // Refresh the search bar display
        refreshSearchResults();

        // Close the stages
        cartStage.close();
        checkoutStage.close();
    }

    private void refreshSearchResults() {
        productDisplay.getChildren().clear();
        String searchText = searchBar.getText().trim();
        if (!searchText.isEmpty()) {
            for (Product product : Database.Products) {
                if (product != null && product.get_Name().toLowerCase().contains(searchText.toLowerCase())) {
                    HBox productRow = createProductRow(product);
                    productDisplay.getChildren().add(productRow);
                }
            }
        }
    }





    private void showOrderConfirmation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText(null);
        alert.setContentText("Your order has been placed successfully!");
        alert.showAndWait();
    }


    private void styleButton(Button button, String backgroundColor, String textColor) {
        String baseStyle = "-fx-background-color: " + backgroundColor + "; -fx-text-fill: " + textColor + "; -fx-font-size: 14px; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;";
        button.setStyle(baseStyle);

        // Reapply the style when resizing
        button.widthProperty().addListener((observable, oldWidth, newWidth) -> button.setStyle(baseStyle));
        button.heightProperty().addListener((observable, oldHeight, newHeight) -> button.setStyle(baseStyle));
    }

    private void showAdminDashboard(Stage primaryStage) {
        // Root Layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa;");

        // Header Layout (Green Section with Title and Buttons)
        VBox headerSection = new VBox(10); // Reduced spacing to save space
        headerSection.setAlignment(Pos.TOP_CENTER); // Shifted upwards
        headerSection.setStyle("-fx-background-color: #2ecc71; -fx-padding: 10;");

        // Back Button (Top Left)
        HBox backButtonBox = new HBox();
        backButtonBox.setAlignment(Pos.CENTER_LEFT);

        Button backButton = new Button("\u2190 Back");
        backButton.setStyle(
                "-fx-background-color: white; -fx-text-fill: #2ecc71; -fx-font-size: 14px;" +
                        "-fx-font-weight: bold; -fx-border-color: #2ecc71; -fx-border-width: 1;" +
                        "-fx-padding: 5 15; -fx-border-radius: 5; -fx-background-radius: 5;");
        backButton.setOnAction(event -> showLoginWindow(primaryStage));
        backButtonBox.getChildren().add(backButton);

        // Title Label (Shifted upwards slightly)
        Label dashboardTitle = new Label("Admin Dashboard");
        dashboardTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Buttons for the Admin Dashboard (Side-by-side, centered)
        HBox buttonSection = new HBox(10);
        buttonSection.setAlignment(Pos.CENTER);

        Button customersButton = createStyledButton("Customers");
        customersButton.setOnAction(event -> displayCustomers(root));

        Button adminsButton = createStyledButton("Admins");
        adminsButton.setOnAction(event -> displayAdmins(root));

        Button ProductsButton = createStyledButton("Products");
        ProductsButton.setOnAction(event -> displayProducts(root));

        Button ordersButton = createStyledButton("Orders");
        ordersButton.setOnAction(event -> displayOrders(root));

        Button statisticsButton = createStyledButton("Statistics");
        statisticsButton.setOnAction(event -> displayStatistics(root));

        buttonSection.getChildren().addAll(customersButton, adminsButton, ProductsButton, ordersButton, statisticsButton);

        // Add all elements to the header section
        headerSection.getChildren().addAll(backButtonBox, dashboardTitle, buttonSection);

        // Add header section to the top of the root layout
        root.setTop(headerSection);

        // Welcome message in the center area initially
        Label welcomeMessage = new Label("Welcome to the Admin Dashboard! Use the buttons above to navigate.");
        welcomeMessage.setStyle("-fx-font-size: 16px; -fx-text-fill: #34495e; -fx-padding: 20;");
        root.setCenter(welcomeMessage);

        // Create and set the scene
        Scene adminDashboardScene = new Scene(root, 900, 600);
        primaryStage.setScene(adminDashboardScene);
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.show();
    }



    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #34495e;" +  // Dark gray text
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: #2ecc71;" +
                        "-fx-border-width: 1;" +
                        "-fx-padding: 10 20;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;");
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #27ae60;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: #2ecc71;" +
                        "-fx-border-width: 1;" +
                        "-fx-padding: 10 20;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #34495e;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: #2ecc71;" +
                        "-fx-border-width: 1;" +
                        "-fx-padding: 10 20;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"));
        return button;
    }

    private void displayCustomers(BorderPane root) {
        // Create TableView
        TableView<String[]> customerTable = new TableView<>();
        customerTable.setEditable(false); // Initially not editable
        customerTable.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1;");

        // Username Column
        TableColumn<String[], String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
        usernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        usernameColumn.setOnEditCommit(event -> {
            String[] rowData = event.getRowValue();
            String newUsername = event.getNewValue();
            if (newUsername.trim().isEmpty()) {
                showAlert("Invalid Input", "Username cannot be empty.");
            } else {
                rowData[0] = newUsername;
                updateDatabase(rowData, getIndex(event));
            }
        });
        usernameColumn.setPrefWidth(150);

        // Password Column
        TableColumn<String[], String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setOnEditCommit(event -> {
            String[] rowData = event.getRowValue();
            String newPassword = event.getNewValue();
            if (newPassword.trim().isEmpty()) {
                showAlert("Invalid Input", "Password cannot be empty.");
            } else if (!isValidPassword(newPassword)) {
                showAlert("Invalid Input", "Password must be at least 8 characters long, contain 1 uppercase letter, 1 number, and 1 special character.");
            } else {
                rowData[1] = newPassword;
                updateDatabase(rowData, getIndex(event));
            }
        });
        passwordColumn.setPrefWidth(150);

        // Address Column
        TableColumn<String[], String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit(event -> {
            String[] rowData = event.getRowValue();
            String newAddress = event.getNewValue();
            if (newAddress.trim().isEmpty()) {
                showAlert("Invalid Input", "Address cannot be empty.");
            } else {
                rowData[2] = newAddress;
                updateDatabase(rowData, getIndex(event));
            }
        });
        addressColumn.setPrefWidth(200);

        // Gender Column (Case-insensitive)
        TableColumn<String[], String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));
        genderColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        genderColumn.setOnEditCommit(event -> {
            String[] rowData = event.getRowValue();
            String newGender = event.getNewValue().toUpperCase().trim();  // Make case-insensitive and trim spaces

            // Validate gender (MALE or FEMALE only)
            if ("MALE".equals(newGender) || "FEMALE".equals(newGender)) {
                rowData[3] = newGender;  // Set the new gender value
                updateDatabase(rowData, getIndex(event)); // Update the database
            } else {
                showAlert("Invalid Input", "Gender must be either 'MALE' or 'FEMALE'.");
            }
        });
        genderColumn.setPrefWidth(100);


        // Balance Column
        TableColumn<String[], String> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[4]));
        balanceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        balanceColumn.setOnEditCommit(event -> {
            String[] rowData = event.getRowValue();
            try {
                double newBalance = Double.parseDouble(event.getNewValue());
                if (newBalance < 0) {
                    showAlert("Invalid Input", "Balance cannot be negative.");
                } else {
                    rowData[4] = Double.toString(newBalance);
                    updateDatabase(rowData, getIndex(event));
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Balance must be a valid number.");
            }
        });
        balanceColumn.setPrefWidth(100);

        // Interest Column (Case-insensitive)
        TableColumn<String[], String> interestColumn = new TableColumn<>("Interest");
        interestColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[5]));
        interestColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        interestColumn.setOnEditCommit(event -> {
            String[] rowData = event.getRowValue();
            String newInterest = event.getNewValue().toUpperCase(); // Convert input to uppercase to handle case insensitivity
            // Define a list of allowed interests
            List<String> allowedInterests = Arrays.asList(
                    "FOODANDBEVERAGES", "ELECTRONICS", "CLOTHING", "TECHACCESSORIES",
                    "HOUSEHOLDESSENTIALS", "PERSONALCARE", "STATIONERY", "HEALTHANDWELLNESS",
                    "PETSUPPLIES", "MISCELLANEOUS"
            );

            // Check if the new interest is in the list of allowed values
            if (allowedInterests.contains(newInterest)) {
                rowData[5] = newInterest; // Set the new interest value
                updateDatabase(rowData, getIndex(event)); // Update the database
            } else {
                showAlert("Invalid Input", "Interest must be one of the following: FOODANDBEVERAGES, ELECTRONICS, CLOTHING, " +
                        "TECHACCESSORIES, HOUSEHOLDESSENTIALS, PERSONALCARE, STATIONERY, HEALTHANDWELLNESS, PETSUPPLIES, MISCELLANEOUS.");
            }
        });
        interestColumn.setPrefWidth(200); // Set the width of the column

        // Date of Birth Column
        TableColumn<String[], String> dobColumn = new TableColumn<>("Date of Birth");
        dobColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[6]));
        dobColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dobColumn.setOnEditCommit(event -> {
            String[] rowData = event.getRowValue();
            String newDate = event.getNewValue();
            if (!isValidDate(newDate)) {
                showAlert("Invalid Input", "Invalid date format. Expected format: yyyy-MM-dd with year between 1900 and 2014.");
            } else {
                rowData[6] = newDate;
                updateDatabase(rowData, getIndex(event));
            }
        });
        dobColumn.setPrefWidth(150);

        // Delete Column
        TableColumn<String[], Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("🗑");

            {
                deleteButton.setStyle(
                        "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5;" +
                                "-fx-border-radius: 5; -fx-background-radius: 5;"
                );
                deleteButton.setOnAction(e -> {
                    if (customerTable.isEditable()) { // Check if Edit Mode is ON
                        int rowIndex = getIndex();
                        getTableView().getItems().remove(rowIndex); // Remove row from table
                        Database.removeCustomer(rowIndex); // Remove customer from the database
                        System.out.println("Row Deleted: " + rowIndex);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Create TableView
        customerTable.setEditable(false); // Initially not editable
        customerTable.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1;");

        // Define columns (same as in your original code)

        // Add Columns to TableView
        customerTable.getColumns().addAll(usernameColumn, passwordColumn, addressColumn, genderColumn,
                balanceColumn, interestColumn, dobColumn, deleteColumn);

        // Load Existing Customers into the TableView Dynamically
        for (Customer customer : Database.customers) {
            if (customer != null) {
                customerTable.getItems().add(new String[] {
                        customer.get_Username(),
                        customer.get_Password(),
                        customer.get_Address(),
                        customer.get_CustomerGender().toString(),
                        Double.toString(customer.get_Balance()),
                        customer.get_InterestByIndex(0).toString(),
                        customer.getDate()
                });
            }
        }

        // Add "Edit Mode" Toggle Button
        Button editToggleButton = new Button("Edit: OFF");
        editToggleButton.setStyle(
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5;" +
                        "-fx-border-radius: 5; -fx-background-radius: 5;");

        editToggleButton.setOnAction(event -> {
            if (customerTable.isEditable()) {
                customerTable.setEditable(false);
                editToggleButton.setText("Edit: OFF");
                editToggleButton.setStyle(
                        "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5;" +
                                "-fx-border-radius: 5; -fx-background-radius: 5;");
            } else {
                customerTable.setEditable(true);
                editToggleButton.setText("Edit: ON");
                editToggleButton.setStyle(
                        "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5;" +
                                "-fx-border-radius: 5; -fx-background-radius: 5;");
            }
        });

        // Add "Add Customer" Button
        Button addCustomerButton = new Button("Add Customer");
        addCustomerButton.setStyle(
                "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5;" +
                        "-fx-border-radius: 5; -fx-background-radius: 5;");
        addCustomerButton.setOnAction(e -> {
            // Call method to add a new customer (e.g., open input dialog)
            openAddCustomerDialog(customerTable);
        });

        // Header section (edit toggle + add customer button)
        HBox header = new HBox(editToggleButton, addCustomerButton);
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setSpacing(10);  // Space between buttons
        header.setStyle("-fx-padding: 10;");

        // Layout for the table and buttons
        VBox layout = new VBox(header, customerTable);
        layout.setSpacing(10);

        root.setCenter(layout);
    }

    private boolean isValidPassword(String password) {
        // Password must have at least one uppercase letter, one number, and one special character.
        String regex = "^(?=.[A-Z])(?=.[0-9])(?=.[!@#$%^&(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]{8,}$";
        return password.matches(regex);
    }

    private void openAddCustomerDialog(TableView<String[]> customerTable) {
        // Create a dialog to input new customer details
        Dialog<Customer> dialog = new Dialog<>();
        dialog.setTitle("Add New Customer");
        dialog.setHeaderText("Enter new customer details:");

        // Set up the dialog fields (for example, username, password, etc.)
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("MALE", "FEMALE");
        genderComboBox.setPromptText("Gender");

        TextField balanceField = new TextField();
        balanceField.setPromptText("Balance");

        ComboBox<String> interestComboBox = new ComboBox<>();
        interestComboBox.getItems().addAll(
                "FoodAndBeverages", "Electronics", "Clothing", "TechAccessories",
                "HouseholdEssentials", "PersonalCare", "Stationery",
                "HealthAndWellness", "PetSupplies", "Miscellaneous"
        );
        interestComboBox.setPromptText("Interest");

        TextField dobField = new TextField();
        dobField.setPromptText("Date of Birth (yyyy-mm-dd)");

        // Set up a GridPane to lay out the form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(new Label("Address:"), 0, 2);
        grid.add(addressField, 1, 2);
        grid.add(new Label("Gender:"), 0, 3);
        grid.add(genderComboBox, 1, 3);
        grid.add(new Label("Balance:"), 0, 4);
        grid.add(balanceField, 1, 4);
        grid.add(new Label("Interest:"), 0, 5);
        grid.add(interestComboBox, 1, 5);
        grid.add(new Label("Date of Birth:"), 0, 6);
        grid.add(dobField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Add OK and Cancel buttons to the dialog
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        // Handle OK button action
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                // Collect the data from the form fields
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();
                String address = addressField.getText().trim();
                String genderStr = genderComboBox.getValue();
                String balanceText = balanceField.getText().trim();
                String interestStr = interestComboBox.getValue();
                String dob = dobField.getText().trim();

                // Check if any of the required fields are empty
                if (username.isEmpty() || password.isEmpty() || address.isEmpty() ||
                        genderStr == null || balanceText.isEmpty() || interestStr == null || dob.isEmpty()) {
                    showAlert("Invalid Input", "All fields must be filled out.");
                    return null;
                }

                double balance;
                try {
                    balance = Double.parseDouble(balanceText);
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Balance must be a valid number.");
                    return null;
                }

                // Convert gender and interest to enum values, with null checks
                Customer.Gender gender = null;
                Customer.Interests interest = null;

                try {
                    // Validate Gender
                    if (genderStr != null) {
                        gender = Customer.Gender.valueOf(genderStr);
                    }
                    // Validate Interest
                    if (interestStr != null) {
                        interest = Customer.Interests.valueOf(interestStr);
                    }
                } catch (IllegalArgumentException e) {
                    showAlert("Invalid Input", "Gender or Interest is not valid.");
                    return null;
                }

                // Ensure gender and interest are valid
                if (gender == null || interest == null) {
                    showAlert("Invalid Input", "Please select a valid Gender and Interest.");
                    return null;
                }

                // Create a new Customer object
                Customer newCustomer = new Customer(username, password, dob, address, gender, balance, interest);

                // Add the new customer to the table and the database
                customerTable.getItems().add(new String[] {
                        newCustomer.get_Username(),
                        newCustomer.get_Password(),
                        newCustomer.get_Address(),
                        newCustomer.get_CustomerGender().toString(),
                        Double.toString(newCustomer.get_Balance()),
                        newCustomer.get_InterestByIndex(0).toString(),
                        newCustomer.getDate()
                });

                Database.addCustomer(newCustomer);  // Save the customer to the database

                return newCustomer;
            }
            return null;
        });

        dialog.showAndWait();
    }


    private void updateDatabase(String[] rowData, int index) {
        if (index >= 0 && index < Database.customers.length) {
            Customer customer = Database.customers[index];
            customer.set_Username(rowData[0]);
            customer.set_Password(rowData[1]);
            customer.set_Address(rowData[2]);

            try {
                // Parse gender into the Gender enum
                Customer.Gender gender = Customer.Gender.valueOf(rowData[3].toUpperCase().trim());
                customer.set_CustomerGender(gender);  // Set the valid gender

                // Validate and update interest (existing code)
                String interestInput = rowData[5].toUpperCase().replace(" ", ""); // Remove spaces and make uppercase
                boolean validInterest = false;

                for (Customer.Interests interest : Customer.Interests.values()) {
                    if (interest.name().equals(interestInput)) {
                        customer.add_Interest(interest);
                        validInterest = true;
                        break;
                    }
                }

                if (!validInterest) {
                    throw new IllegalArgumentException("Invalid Interest");
                }

            } catch (IllegalArgumentException e) {
                showAlert("Invalid Input", "Gender must be either 'MALE' or 'FEMALE'.");
                return;  // Exit the method if invalid data is found
            }

            // Update other customer fields
            customer.set_Balance(Double.parseDouble(rowData[4]));
            customer.setDate(rowData[6]);
            System.out.println("Customer Updated: " + customer.get_Username());
        }
    }


    // Utility: Extracts row index from edit events
    private int getIndex(TableColumn.CellEditEvent<String[], String> event) {
        return event.getTablePosition().getRow();
    }


    private void displayAdmins(BorderPane root) {
        // Create TableView
        TableView<String[]> adminTable = new TableView<>();
        adminTable.setEditable(false);
        adminTable.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1;");

        // Username Column
        TableColumn<String[], String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
        usernameColumn.setPrefWidth(150);

        // Password Column
        TableColumn<String[], String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
        passwordColumn.setPrefWidth(150);

        // Role Column
        TableColumn<String[], String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));
        roleColumn.setPrefWidth(150);

        // Working Hours Column
        TableColumn<String[], String> hoursColumn = new TableColumn<>("Working Hours");
        hoursColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));
        hoursColumn.setPrefWidth(150);

        // Delete Column
        TableColumn<String[], Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("🗑");

            {
                deleteButton.setStyle(
                        "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5;" +
                                "-fx-border-radius: 5; -fx-background-radius: 5;"
                );
                deleteButton.setOnAction(e -> {
                    int rowIndex = getIndex();
                    getTableView().getItems().remove(rowIndex); // Remove row from the table
                    deleteAdminFromDatabase(rowIndex); // Update the database properly
                    refreshAdminTable(adminTable); // Refresh the table
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        adminTable.getColumns().addAll(usernameColumn, passwordColumn, roleColumn, hoursColumn, deleteColumn);

        // Load Existing Admins into the TableView Dynamically
        refreshAdminTable(adminTable);

        // Layout for TableView and Buttons
        VBox layout = new VBox(adminTable);
        layout.setSpacing(10);

        root.setCenter(layout);
    }

    // Refresh Admin Table
    private void refreshAdminTable(TableView<String[]> adminTable) {
        adminTable.getItems().clear(); // Clear the table
        for (int i = 0; i < Database.AdminCount; i++) {
            Admin admin = Database.admin[i];
            if (admin != null) {
                adminTable.getItems().add(new String[]{
                        admin.get_Username(),
                        admin.get_Password(),
                        admin.get_Role().toString(),
                        Integer.toString(admin.get_Working_hours())
                });
            }
        }
    }

    // Delete Admin from Database
    private void deleteAdminFromDatabase(int index) {
        // Shift elements to the left
        for (int i = index; i < Database.AdminCount - 1; i++) {
            Database.admin[i] = Database.admin[i + 1];
        }
        Database.admin[Database.AdminCount - 1] = null; // Clear the last entry
        Database.AdminCount--; // Decrement the admin count
    }


    // Helper Methods
    private int getAdminIndex(TableColumn.CellEditEvent<String[], String> event) {
        return event.getTablePosition().getRow();
    }

    private void updateAdminDatabase(String[] rowData, int index) {
        Admin admin = Database.admin[index];
        admin.set_Username(rowData[0]);
        admin.set_Password(rowData[1]);
        try {
            admin.set_role(Admin.Role.valueOf(rowData[2])); // Update role
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to update role. Invalid input: " + rowData[2]);
        }
        admin.setWorking_hours(Integer.parseInt(rowData[3]));
        System.out.println("Admin Updated: " + admin.get_Username());
    }

    private TableView<Electronics> electronicsTable;
    private TableView<Consumables> consumablesTable;
    private TableView<Clothing> clothingTable;
    public void initialize() {
        electronicsTable = createElectronicsTable();
        consumablesTable = createConsumablesTable();
        clothingTable = createClothingTable();
        // Add electronicsTable to the layout if needed
    }


    private void displayProducts(BorderPane root) {
        // Retrieve all products from the database
        Product[] products = Database.displayProducts();

        // Create Edit Toggle Button
        Button editToggleButton = new Button("Edit: OFF");
        editToggleButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px;");

        // Create tables and labels for each category
        Label electronicsLabel = new Label("Electronics");
        electronicsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        electronicsTable = createElectronicsTable();
        Button addElectronicsButton = new Button("Add Product");
        addElectronicsButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14px;"); // Same style as the Edit button
        addElectronicsButton.setDisable(true); // Initially disabled

        Label consumablesLabel = new Label("Consumables");
        consumablesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        consumablesTable = createConsumablesTable();
        Button addConsumablesButton = new Button("Add Product");
        addConsumablesButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14px;"); // Same style as the Edit button
        addConsumablesButton.setDisable(true); // Initially disabled

        Label clothingLabel = new Label("Clothing");
        clothingLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        clothingTable = createClothingTable();
        Button addClothingButton = new Button("Add Product");
        addClothingButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14px;"); // Same style as the Edit button
        addClothingButton.setDisable(true); // Initially disabled

        // Populate Tables based on Product Type
        for (Product product : products) {
            if (product instanceof Electronics) {
                electronicsTable.getItems().add((Electronics) product);
            } else if (product instanceof Consumables) {
                consumablesTable.getItems().add((Consumables) product);
            } else if (product instanceof Clothing) {
                clothingTable.getItems().add((Clothing) product);
            }
        }

        // Edit Mode Logic
        editToggleButton.setOnAction(event -> {
            boolean editable = !electronicsTable.isEditable();
            electronicsTable.setEditable(editable);
            consumablesTable.setEditable(editable);
            clothingTable.setEditable(editable);

            addElectronicsButton.setDisable(!editable);
            addConsumablesButton.setDisable(!editable);
            addClothingButton.setDisable(!editable);

            if (editable) {
                editToggleButton.setText("Edit: ON");
                editToggleButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
            } else {
                editToggleButton.setText("Edit: OFF");
                editToggleButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            }
        });

        // Add Product Button Action
        addElectronicsButton.setOnAction(event -> {
            if (Database.isProductArrayFull()) {
                showValidationMessage("Products are full, cannot add more products.");
            } else {
                openAppWindow("Electronics");
            }
        });

        addConsumablesButton.setOnAction(event -> {
            if (Database.isProductArrayFull()) {
                showValidationMessage("Products are full, cannot add more products.");
            } else {
                openAppWindow("Consumables");
            }
        });

        addClothingButton.setOnAction(event -> {
            if (Database.isProductArrayFull()) {
                showValidationMessage("Products are full, cannot add more products.");
            } else {
                openAppWindow("Clothing");
            }
        });

        // Layout
        VBox layout = new VBox(
                new HBox(editToggleButton),
                electronicsLabel, electronicsTable, addElectronicsButton,
                consumablesLabel, consumablesTable, addConsumablesButton,
                clothingLabel, clothingTable, addClothingButton
        );
        layout.setSpacing(5);
        layout.setStyle("-fx-padding: 5; -fx-background-color: #f8f9fa;");
        root.setCenter(layout);
    }
    private void openAppWindow(String productCategory) {
        Stage window = new Stage();

        // Set the title dynamically based on the product category
        window.setTitle("Add " + productCategory);

        // Layout setup
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // Create input fields
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField stockField = new TextField();
        TextField additionalField = new TextField();  // This field will be for warranty date, expiration date, or size

        Label nameLabel = new Label("Product Name:");
        Label priceLabel = new Label("Price:");
        Label stockLabel = new Label("Stock:");
        Label additionalLabel = new Label();

        // Category ComboBox (we will pre-define categories in the next block)
        ComboBox<String> categoryComboBox = new ComboBox<>();

        Label additionalFieldLabel = new Label();
        ComboBox<String> sizeComboBox = new ComboBox<>();

        // Set up the form depending on the product category
        if (productCategory.equals("Electronics")) {
            categoryComboBox.getItems().addAll("MobilePhones", "Televisions", "SmartWatches", "Miscellaneous");
            additionalFieldLabel.setText("Warranty Date:");
        } else if (productCategory.equals("Consumables")) {
            categoryComboBox.getItems().addAll("Food", "Drinks", "Medicine", "Miscellaneous");
            additionalFieldLabel.setText("Expiration Date:");
        } else if (productCategory.equals("Clothing")) {
            categoryComboBox.getItems().addAll("Shirts", "Jeans", "Jackets", "Miscellaneous");
            additionalFieldLabel.setText("Size:");
            sizeComboBox.getItems().addAll("XXL", "XL", "L", "Miscellaneous");
        }

        // Add elements to the grid
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(priceLabel, 0, 1);
        grid.add(priceField, 1, 1);
        grid.add(stockLabel, 0, 2);
        grid.add(stockField, 1, 2);
        grid.add(new Label("Category:"), 0, 3);
        grid.add(categoryComboBox, 1, 3);
        grid.add(additionalFieldLabel, 0, 4);

        if (productCategory.equals("Clothing")) {
            grid.add(sizeComboBox, 1, 4);
        } else {
            grid.add(additionalField, 1, 4);
        }

        // Submit Button
        Button submitButton = new Button("Submit");
        grid.add(submitButton, 1, 5);

        // Submit Button Action
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            String category = categoryComboBox.getValue();
            String additionalInfo = additionalField.getText();
            String size = sizeComboBox.getValue();

            // Validation
            if (name.isEmpty()) {
                showValidationMessage("Product Name is required.");
                return;
            }

            if (!Database.isProductNameUnique(name)) {
                showValidationMessage("Product with this name already exists.");
            } else {
                // Proceed with adding or editing the product
                System.out.println("Product name is valid.");
            }



            double price = 0;
            try {
                price = Double.parseDouble(priceText);
                if (price <= 0) {
                    showValidationMessage("Price must be a positive number.");
                    return;
                }
            } catch (NumberFormatException ex) {
                showValidationMessage("Price must be a valid number.");
                return;
            }

            int stock = 0;
            try {
                stock = Integer.parseInt(stockText);
                if (stock < 0) {
                    showValidationMessage("Stock must be a non-negative integer.");
                    return;
                }
            } catch (NumberFormatException ex) {
                showValidationMessage("Stock must be a valid integer.");
                return;
            }

            if (category == null || category.isEmpty()) {
                showValidationMessage("Category is required.");
                return;
            }

            if (productCategory.equals("Electronics")) {
                if (additionalInfo.isEmpty()) {
                    showValidationMessage("Warranty Date is required.");
                    return;

                }
                if (!isValidDate(additionalInfo)) {
                    showValidationMessage("Invalid warranty date format. Please use yyyy-mm-dd.");
                    return;
                }



                // Add electronics product to the list and database (in-memory)
                addElectronicsProduct(name, price, stock, category, additionalInfo);
            }




            else if (productCategory.equals("Consumables")) {
                if (additionalInfo.isEmpty()) {
                    showValidationMessage("Expiration Date is required.");
                    return;
                }

                if (!isValidDate(additionalInfo)) {
                    showValidationMessage("Invalid expiration date format. Please use yyyy-mm-dd.");
                    return;
                }
                // Add consumables product to the list and database (in-memory)
                addConsumablesProduct(name, price, stock, category, additionalInfo);
            }

            else if (productCategory.equals("Clothing")) {
                if (size == null || size.isEmpty()) {
                    showValidationMessage("Size is required.");
                    return;
                }
                // Add clothing product to the list and database (in-memory)
                addClothingProduct(name, price, stock, category, size);
            }

            // Close the add product window after adding the product
            window.close();
        });

        // Show the window
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.show();
    }

    // Validation message display

    // Method to add Electronics product to the in-memory list and database
    private void addElectronicsProduct(String name, double price, int stock, String category, String warrantyDate) {
        Electronics.Category categoryEnum = Electronics.Category.valueOf(category);
        Electronics product = new Electronics(name, price, stock, categoryEnum, warrantyDate);
        Database.addProduct(product);  // Add to the in-memory list (database)
        System.out.println("Electronics product added: " + product);
        electronicsTable.getItems().add(product);
        Database.electronics_count++;
    }

    // Method to add Consumables product to the in-memory list and database
    private void addConsumablesProduct(String name, double price, int stock, String category, String expirationDate) {
        Consumables.Category categoryEnum = Consumables.Category.valueOf(category);
        Consumables product = new Consumables(name, price, stock, categoryEnum, expirationDate);
        Database.addProduct(product);  // Add to the in-memory list (database)
        System.out.println("Consumables product added: " + product);
        consumablesTable.getItems().add(product);
        Database.consumables_count++;
    }

    // Method to add Clothing product to the in-memory list and database
    private void addClothingProduct(String name, double price, int stock, String category, String size) {
        Clothing.Category categoryEnum = Clothing.Category.valueOf(category);
        Clothing.Size sizeEnum = Clothing.Size.valueOf(size);
        Clothing product = new Clothing(name, price, stock, categoryEnum, sizeEnum);
        Database.addProduct(product);  // Add to the in-memory list (database)
        System.out.println("Clothing product added: " + product);
        clothingTable.getItems().add(product);
        Database.clothing_count++;
    }



    private TableView<Electronics> createElectronicsTable() {
        TableView<Electronics> table = new TableView<>();
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Name Column
        TableColumn<Electronics, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_Name()));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(event -> {
            String newName = event.getNewValue();

            // Validate the product name
            if (newName.isEmpty()) {
                showValidationMessage("Product Name is required.");
                return; // Prevent the update if the name is empty
            }

            if (!Database.isProductNameUnique(newName)) {
                showValidationMessage("Product with this name already exists.");
                return; // Prevent the update if the name is not unique
            }

            // If validation passes, proceed with setting the new name
            event.getRowValue().set_Name(newName);
            System.out.println("Product name updated to: " + newName);
        });

        // Price Column
        TableColumn<Electronics, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().get_price()).asObject());
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
            @Override
            public String toString(Double value) {
                return "$" + super.toString(value);
            }
        }));
        priceCol.setOnEditCommit(event -> {
            Double newPrice = event.getNewValue();
            if (newPrice != null && newPrice >= 0) {
                event.getRowValue().set_price(newPrice);
            } else {
                showValidationMessage("Price must be a number greater than or equal to 0");
            }
        });

        // Stock Column
        TableColumn<Electronics, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().get_stock()).asObject());
        stockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stockCol.setOnEditCommit(event -> {
            try {
                // Get the new value entered by the user (as String)
                String newValue = String.valueOf(event.getNewValue());

                // Check if the value contains a decimal point (check if it’s a valid integer)
                if (newValue.matches(".\\..")) {
                    // If it's a decimal number, show the error message
                    showValidationMessage("Stock must be a whole number (integer).");
                } else {
                    // Try parsing the value to an integer
                    Integer newStock = Integer.parseInt(newValue);

                    // Check if the integer is greater than or equal to 0
                    if (newStock >= 0) {
                        event.getRowValue().set_stock(newStock); // Set the stock value
                    } else {
                        showValidationMessage("Stock must be greater than or equal to 0.");
                    }
                }
            } catch (NumberFormatException e) {
                // Handle invalid integer input (when the input is not a number at all)
                showValidationMessage("Invalid input for stock. Please enter a valid integer.");
            }
        });



        // Category Column
        TableColumn<Electronics, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_Category().toString()));
        categoryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryCol.setOnEditCommit(event -> {
            try {
                event.getRowValue().set_Category(Electronics.Category.valueOf(event.getNewValue()).toString());
            } catch (IllegalArgumentException ex) {
                showValidationMessage("Invalid category, try again");
            }
        });

        // Warranty Date Column
        TableColumn<Electronics, String> warrantyCol = new TableColumn<>("Warranty Date");
        warrantyCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_Waranty_Date_End()));
        warrantyCol.setCellFactory(TextFieldTableCell.forTableColumn());
        warrantyCol.setOnEditCommit(event -> {
            String newWarrantyDate = event.getNewValue();
            if (isValidDate(newWarrantyDate)) {
                event.getRowValue().set_Waranty_Date_End(newWarrantyDate);
            } else {
                showValidationMessage("Invalid warranty date. Please use the format yyyy-mm-dd.");
            }
        });

        // Delete Button Column
        TableColumn<Electronics, Void> deleteCol = new TableColumn<>("Delete");
        deleteCol.setCellFactory(col -> new TableCell<Electronics, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                deleteButton.setOnAction(event -> {
                    if(electronicsTable.isEditable()) {
                        Electronics product = getTableView().getItems().get(getIndex());
                        Database.deleteProduct(product); // Delete from database
                        getTableView().getItems().remove(product); // Remove from table
                        Database.electronics_count--;
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        table.getColumns().addAll(nameCol, priceCol, stockCol, categoryCol, warrantyCol, deleteCol);
        return table;
    }

    private TableView<Consumables> createConsumablesTable() {
        TableView<Consumables> table = new TableView<>();
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Name Column
        TableColumn<Consumables, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_Name()));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(event -> event.getRowValue().set_Name(event.getNewValue()));

        // Price Column
        TableColumn<Consumables, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().get_price()).asObject());
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
            @Override
            public String toString(Double value) {
                return "$" + super.toString(value);
            }
        }));
        priceCol.setOnEditCommit(event -> {
            Double newPrice = event.getNewValue();
            if (newPrice != null && newPrice >= 0) {
                event.getRowValue().set_price(newPrice);
            } else {
                showValidationMessage("Price must be a number greater than or equal to 0");
            }
        });

        // Stock Column
        TableColumn<Consumables, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().get_stock()).asObject());
        stockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stockCol.setOnEditCommit(event -> {
            try {
                String newValue = String.valueOf(event.getNewValue());

                if (newValue.contains(".")) {
                    showValidationMessage("Stock must be a whole number (integer).");
                } else {
                    Integer newStock = Integer.parseInt(newValue);

                    if (newStock >= 0) {
                        event.getRowValue().set_stock(newStock);
                    } else {
                        showValidationMessage("Stock must be greater than or equal to 0.");
                    }
                }
            } catch (NumberFormatException e) {
                showValidationMessage("Invalid input for stock. Please enter a valid integer.");
            }
        });

        // Category Column
        TableColumn<Consumables, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_Category().toString()));
        categoryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryCol.setOnEditCommit(event -> {
            try {
                event.getRowValue().set_Category(Consumables.Category.valueOf(event.getNewValue()).toString());
            } catch (IllegalArgumentException ex) {
                showValidationMessage("Invalid category, try again");
            }
        });

        TableColumn<Consumables, String> expirationCol = new TableColumn<>("Expiration Date");
        expirationCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_ExpirationDate()));
        expirationCol.setCellFactory(TextFieldTableCell.forTableColumn());
        expirationCol.setOnEditCommit(event -> {
            String newWarrantyDate = event.getNewValue();
            if (isValidDate(newWarrantyDate)) {
                event.getRowValue().set_ExpirationDate(newWarrantyDate);
            } else {
                showValidationMessage("Invalid warranty date. Please use the format yyyy-mm-dd.");
            }
        });

        // Delete Button Column
        TableColumn<Consumables, Void> deleteCol = new TableColumn<>("Delete");
        deleteCol.setCellFactory(col -> new TableCell<Consumables, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                deleteButton.setOnAction(event -> {
                    if(consumablesTable.isEditable()) {
                        Consumables product = getTableView().getItems().get(getIndex());
                        Database.deleteProduct(product);
                        getTableView().getItems().remove(product);
                        Database.consumables_count--;
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        table.getColumns().addAll(nameCol, priceCol, stockCol, categoryCol, expirationCol, deleteCol);
        return table;
    }

    private TableView<Clothing> createClothingTable() {
        TableView<Clothing> table = new TableView<>();
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Name Column
        TableColumn<Clothing, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_Name()));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(event -> event.getRowValue().set_Name(event.getNewValue()));

        // Price Column
        TableColumn<Clothing, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().get_price()).asObject());
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
            @Override
            public String toString(Double value) {
                return "$" + super.toString(value);
            }
        }));
        priceCol.setOnEditCommit(event -> {
            Double newPrice = event.getNewValue();
            if (newPrice != null && newPrice >= 0) {
                event.getRowValue().set_price(newPrice);
            } else {
                showValidationMessage("Price must be a number greater than or equal to 0");
            }
        });

        // Stock Column
        TableColumn<Clothing, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().get_stock()).asObject());
        stockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stockCol.setOnEditCommit(event -> {
            try {
                String newValue = String.valueOf(event.getNewValue());

                if (newValue.contains(".")) {
                    showValidationMessage("Stock must be a whole number (integer).");
                } else {
                    Integer newStock = Integer.parseInt(newValue);

                    if (newStock >= 0) {
                        event.getRowValue().set_stock(newStock);
                    } else {
                        showValidationMessage("Stock must be greater than or equal to 0.");
                    }
                }
            } catch (NumberFormatException e) {
                showValidationMessage("Invalid input for stock. Please enter a valid integer.");
            }
        });

        // Category Column
        TableColumn<Clothing, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_Category().toString()));
        categoryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryCol.setOnEditCommit(event -> {
            try {
                event.getRowValue().set_Category(Clothing.Category.valueOf(event.getNewValue()).toString());
            } catch (IllegalArgumentException ex) {
                showValidationMessage("Invalid category, try again");
            }
        });

        TableColumn<Clothing, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_Size().toString()));
        sizeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        sizeCol.setOnEditCommit(event -> {
            try {
                event.getRowValue().set_Size(Clothing.Size.valueOf(event.getNewValue()).toString());
            } catch (IllegalArgumentException ex) {
                showValidationMessage("Invalid Size, try again");
            }
        });

        // Delete Button Column
        TableColumn<Clothing, Void> deleteCol = new TableColumn<>("Delete");
        deleteCol.setCellFactory(col -> new TableCell<Clothing, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                deleteButton.setOnAction(event -> {
                    if(clothingTable.isEditable()) {
                        Clothing product = getTableView().getItems().get(getIndex());
                        Database.deleteProduct(product);
                        getTableView().getItems().remove(product);
                        Database.clothing_count--;
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        table.getColumns().addAll(nameCol, priceCol, stockCol, categoryCol, sizeCol,deleteCol);
        return table;
    }

    private void adjustTableHeight(TableView<?> table) {
        int rowHeight = 25; // Estimated row height
        int maxVisibleRows = 10; // Maximum number of rows to display without scrolling
        int itemCount = table.getItems().size();
        int visibleRows = Math.min(itemCount, maxVisibleRows);
        table.setPrefHeight((visibleRows + 1) * rowHeight); // +1 for header
        table.setMaxHeight((visibleRows + 1) * rowHeight);
    }


    private void showValidationMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidDate(String date) {
        try {
            // Regular expression for matching a date in yyyy-mm-dd format
            String regex = "\\d{4}-\\d{2}-\\d{2}"; // Matches four digits, a hyphen, two digits, a hyphen, two digits
            if (date.matches(regex)) {
                return true; // If the input matches the format, it's considered valid
            }
        } catch (Exception e) {
            // If an exception occurs (e.g., null input or bad format), consider it invalid
        }
        return false; // If the date doesn't match the expected format, return false
    }



    private void displayOrders(BorderPane root) {
        VBox ordersLayout = new VBox(15);
        ordersLayout.setStyle("-fx-padding: 10; -fx-background-color: #f8f9fa;");

        // Call the method to populate orders
        updateOrderList(ordersLayout, Database.Orders_Data);

        // Add a ScrollPane to allow scrolling
        ScrollPane scrollPane = new ScrollPane(ordersLayout);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);
    }

    void updateOrderList(VBox ordersLayout, Order[] orderArray) {
        ordersLayout.getChildren().clear(); // Clear existing orders in layout

        for (int i = 0; i < Database.getOrder_count(); i++) {
            if (orderArray[i] == null) continue; // Skip null entries

            final int currentIndex = i; // Track index for removing the current order
            Order currentOrder = orderArray[i];
            Customer customer = currentOrder.Customers_data; // Retrieve the customer object

            // Order Header
            HBox orderHeader = new HBox();
            orderHeader.setStyle("-fx-background-color: #27ae60; -fx-padding: 10;");
            orderHeader.setSpacing(10);

            // Retrieve customer details
            String username = customer.get_Username();
            String address = customer.get_Address();

            Label orderLabel = new Label("Order #" + (i + 1) +
                    "  |  Customer: " + username +
                    "  |  Address: " + address);
            orderLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

            Button deliveredButton = new Button("Order Delivered");
            deliveredButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px;");
            deliveredButton.setOnAction(event -> {
                for (int j = 0; j < currentOrder.get_ItemCount(); j++) {
                    Product product = currentOrder.get_Item(j);
                    int quantity = currentOrder.get_Quantity(j);
                    Database.UpdateStock(product.get_Name(), quantity);
                }
                shiftOrdersLeft(currentIndex, orderArray);
                Database.Decrement_Order_count();
                updateOrderList(ordersLayout, orderArray);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Order Delivered");
                alert.setHeaderText(null);
                alert.setContentText("Order #" + (currentIndex + 1) + " has been delivered successfully!");
                alert.showAndWait();
            });

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            orderHeader.getChildren().addAll(orderLabel, spacer, deliveredButton);

            // Total Price Label
            Label totalPriceLabel = new Label("Total Price: $" + String.format("%.2f", currentOrder.get_TotalPrice()));
            totalPriceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

            // Product Table
            TableView<Product> productTable = new TableView<>();
            productTable.setEditable(true);
            productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            productTable.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7;");

            // Product Name Column
            TableColumn<Product, String> productNameCol = new TableColumn<>("Product Name");
            productNameCol.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get_Name()));
            productNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            productNameCol.setOnEditCommit(event -> {
                String newName = event.getNewValue();
                if (!Database.check_exist(newName)) {
                    Product product = event.getRowValue();
                    product.set_Name(newName);
                } else {
                    showErrorAlert("Invalid Product Name", "Product name '" + newName + "' does not exist in the database.");
                    productTable.refresh();
                }
            });

            // Quantity Column
            TableColumn<Product, String> quantityCol = new TableColumn<>("Quantity");
            quantityCol.setCellValueFactory(cellData -> {
                int index = currentOrder.indexOfProduct(cellData.getValue());
                return new SimpleStringProperty(String.valueOf(currentOrder.get_Quantity(index)));
            });
            quantityCol.setCellFactory(TextFieldTableCell.forTableColumn());
            quantityCol.setOnEditCommit(event -> {
                try {
                    int newQuantity = Integer.parseInt(event.getNewValue());
                    Product product = event.getRowValue();
                    if (newQuantity <= product.get_stock()) {
                        currentOrder.Edit_Quantity(product, newQuantity);
                        productTable.refresh();
                        updateTotalPrice(totalPriceLabel, currentOrder);
                    } else {
                        showErrorAlert("Invalid Quantity", "Quantity exceeds stock for " + product.get_Name());
                    }
                } catch (NumberFormatException e) {
                    showErrorAlert("Invalid Input", "Please enter a valid number for quantity.");
                    productTable.refresh();
                }
            });

            // Price Column
            TableColumn<Product, String> priceCol = new TableColumn<>("Price per Product");
            priceCol.setCellValueFactory(cellData ->
                    new SimpleStringProperty(String.format("$%.2f", cellData.getValue().get_price())));
            priceCol.setEditable(false);

            productTable.getColumns().addAll(productNameCol, quantityCol, priceCol);

            for (int j = 0; j < currentOrder.get_ItemCount(); j++) {
                productTable.getItems().add(currentOrder.get_Item(j));
            }

            // Total Price Row with Payment Method
            HBox totalRow = new HBox();
            totalRow.setStyle("-fx-background-color: #e74c3c; -fx-padding: 10;");
            totalRow.setAlignment(Pos.CENTER_RIGHT);

            Label paymentMethodLabel = new Label("Payment Method: " + currentOrder.getCustomerPayment());
            paymentMethodLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

            Region leftSpacer = new Region();
            HBox.setHgrow(leftSpacer, Priority.ALWAYS);

            totalRow.getChildren().addAll(paymentMethodLabel, leftSpacer, totalPriceLabel);

            // Combine all elements into a single order section
            VBox orderSection = new VBox(orderHeader, productTable, totalRow);
            orderSection.setStyle("-fx-border-color: #27ae60; -fx-border-width: 2; -fx-background-color: white; -fx-padding: 5;");
            ordersLayout.getChildren().add(orderSection);
        }
    }


    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Updates the total price label.
     */
    private void updateTotalPrice(Label totalPriceLabel, Order currentOrder) {
        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", currentOrder.get_TotalPrice()));
    }

    private void shiftOrdersLeft(int deletedIndex, Order[] orderArray) {
        int orderCount = Database.getOrder_count();
        for (int i = deletedIndex; i < orderCount - 1; i++) {
            orderArray[i] = orderArray[i + 1];
        }
        orderArray[orderCount - 1] = null; // Clear last position
    }


    private void displayStatistics(BorderPane root) {
        VBox layout = new VBox();
        layout.setSpacing(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f8f9fa;");

        // Number of Customers
        int numberOfCustomers = Database.getCustomerCount();
        Label customerLabel = new Label("👥 Customers: " + numberOfCustomers);
        customerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2ecc71;");

        // Number of Admins
        int numberOfAdmins = Database.getAdminCount();
        Label adminLabel = new Label("👔 Admins: " + numberOfAdmins);
        adminLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");

        // Average Age of Customers
        Label averageAgeLabel = new Label("📊 Avg. Age: " + Database.AverageAge());
        averageAgeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #f39c12;");

        // Pie Chart Data
        PieChart pieChart = createStyledPieChart(root, Database.getElectronics_count(), Database.getClothing_count(), Database.getConsumables_count()); // Example values for electronics, clothing, and consumables

        // Add elements to layout
        layout.getChildren().addAll(customerLabel, adminLabel, averageAgeLabel, pieChart);

        // Set layout to the center of the root
        root.setCenter(layout);
    }

    private PieChart createStyledPieChart(BorderPane root, int electronics, int clothing, int consumables) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Electronics", electronics),
                new PieChart.Data("Clothing", clothing),
                new PieChart.Data("Consumables", consumables)
        );

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Product Categories");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setLabelsVisible(false);
        pieChart.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Add hover tooltips
        for (PieChart.Data data : pieChart.getData()) {
            Tooltip tooltip = new Tooltip();
            tooltip.textProperty().bind(Bindings.concat(data.getName(), ": ", data.pieValueProperty().asString("%.0f")));
            Tooltip.install(data.getNode(), tooltip);

            // Style the tooltip
            tooltip.setStyle("-fx-font-size: 14px; -fx-background-color: #34495e; -fx-text-fill: white; -fx-padding: 10; -fx-border-radius: 5;");
        }

        // Make the pie chart responsive
        pieChart.prefWidthProperty().bind(root.widthProperty().multiply(0.5));
        pieChart.prefHeightProperty().bind(root.heightProperty().multiply(0.5));

        return pieChart;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
