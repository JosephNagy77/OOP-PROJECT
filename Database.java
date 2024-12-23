import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;

public class Database {
    private static final int MAX_CUSTOMERS = 100;
    protected static final int MAX_SIZE = 100;
    public static Product[] Products = new Product[MAX_SIZE];
    private static int productsCount = 20;
    public static int electronics_count = 7;
    public static int consumables_count = 7;
    public static int clothing_count = 6;

    private static final int MAX_Admins = 100;
    public static Customer[] customers = new Customer[MAX_CUSTOMERS];
    private static int customerCount = 0;
    public static Admin[] admin = new Admin[MAX_Admins];
    public static int AdminCount = 0;

    private static final int Max_Orders = 100;
    public static Order[] Orders_Data = new Order[Max_Orders];
    private static int Order_count = 3;

    Database()
    {
        for (int i = 0; i < 10; i++) {
            customers[i] = new Customer(); // Use the no-argument constructor
            customers[i].set_Address("Adress street " + i); // Assign unique IDs
            customers[i].set_Username("Customer" + i); // Assign default usernames
            customers[i].set_Password("Password@17"); // Assign default passwords
            customers[i].setDate("2006-02-02");
            customers[i].set_Balance(1000*i);// Assign balances
            customers[i].set_CustomerGender(Customer.Gender.MALE);
            customerCount++;
        }

        for (int j=0; j < 10; j++)
        {
            admin[j] = new Admin();
            admin[j].set_Username("Admin" + j);
            //admin[j].set_Password("Password@123 " + j);
            admin[j].setDate("2006-02-02");
            admin[j].set_role(Admin.Role.MANAGER);
            admin[j].setWorking_hours(40 + j);
            AdminCount++;
        }

        Products[0] = new Electronics("Mobile", 50, 50, Electronics.Category.MobilePhones, "2024-06-15");
        Products[1] = new Consumables("Water", 5, 10, Consumables.Category.Drinks, "2022-05-02");
        Products[2] = new Clothing("XL_Shirt", 500, 200, Clothing.Category.Shirts, Clothing.Size.XL);
        Products[3] = new Electronics("TV", 300, 20, Electronics.Category.Televisions, "2025-03-15");
        Products[4] = new Consumables("Bread", 2, 100, Consumables.Category.Food, "2023-06-10");
        Products[5] = new Clothing("Jacket", 120, 30, Clothing.Category.Jackets, Clothing.Size.L);
        Products[6] = new Electronics("Smartwatch", 150, 60, Electronics.Category.SmartWatches, "2024-01-05");
        Products[7] = new Consumables("Juice", 3, 50, Consumables.Category.Drinks, "2023-07-20");
        Products[8] = new Clothing("Pants", 70, 40, Clothing.Category.Shirts, Clothing.Size.XL);
        Products[9] = new Electronics("Headphones", 80, 70, Electronics.Category.Miscellaneous, "2026-12-25");
        Products[10] = new Consumables("Milk", 1.5, 200, Consumables.Category.Food, "2023-08-03");
        Products[11] = new Clothing("Shoes", 90, 25, Clothing.Category.Jackets, Clothing.Size.L);
        Products[12] = new Electronics("Laptop", 1000, 15, Electronics.Category.Miscellaneous, "2027-02-14");
        Products[13] = new Consumables("Candy", 0.5, 300, Consumables.Category.Food, "2024-09-09");
        Products[14] = new Clothing("Hat", 25, 60, Clothing.Category.Miscellaneous, Clothing.Size.XL);
        Products[15] = new Electronics("Tablet", 400, 35, Electronics.Category.Miscellaneous, "2026-10-31");
        Products[16] = new Consumables("Medicine", 10, 80, Consumables.Category.Medicine, "2024-11-22");
        Products[17] = new Clothing("Sweater", 45, 55, Clothing.Category.Miscellaneous, Clothing.Size.XXL);
        Products[18] = new Electronics("Camera", 600, 10, Electronics.Category.Miscellaneous, "2028-04-07");
        Products[19] = new Consumables("Energy Drink", 2.5, 90, Consumables.Category.Drinks, "2023-06-01");


        Orders_Data[0] = new Order(customers[0]);
        Orders_Data[1] = new Order(customers[1]);
        Orders_Data[2] = new Order(customers[2]);

        Orders_Data[0].AddItem(Products[0], 4);
        Orders_Data[0].AddItem(Products[4], 10);
        Orders_Data[0].AddItem(Products[6], 1);
        Orders_Data[0].AddItem(Products[3], 100);

        Orders_Data[1].AddItem(Products[2], 4);
        Orders_Data[1].AddItem(Products[7], 10);
        Orders_Data[1].AddItem(Products[3], 1);
        Orders_Data[1].AddItem(Products[12], 3);

        Orders_Data[2].AddItem(Products[1], 4);
        Orders_Data[2].AddItem(Products[14], 10);
        Orders_Data[2].AddItem(Products[18], 1);
        Orders_Data[2].AddItem(Products[0], 1);

        Orders_Data[0].set_PaymentMethod(Order.PaymentMethod.VodafoneCash);
        Orders_Data[1].set_PaymentMethod(Order.PaymentMethod.CashOnDelivery);
        Orders_Data[2].set_PaymentMethod(Order.PaymentMethod.Balance);

    }

    public static void updateStock(String productName , int productsStock){
        for(int i = 0; i < 20;i++){
            if(Products[i].Name.equals(productName)){
                try{
                    if(productsStock > Products[i].Stock){
                        throw new IllegalArgumentException("There is no enough stock");
                    }
                    Products[i].Stock -= productsStock;
                    return;
                }
                catch (InputMismatchException ex) {
                    System.out.println("Invalid input, must be a number.");
                    return;

                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    return;
                }

            }
        }
    }


    public static void show_Customers() {
        System.out.println("Customers List: ");
        for (int i = 0; i < customerCount; i++) {
            System.out.println("Customer Username " + customers[i].get_Username());
            System.out.println("Customer Balance " + customers[i].get_Balance());
            System.out.println("Customer Address " + customers[i].get_Address());
            System.out.println("Customer Gender " + customers[i].get_CustomerGender());
            System.out.println("Customer Date of Birth " + customers[i].getDate());
            System.out.println("Customer Age " + customers[i].get_Age());
            System.out.println("Customer Password " + customers[i].get_Password());
            customers[i].display_Interests();
        }
    }

    public static void add_admin(String name, String pass, String date, Admin.Role role, int hours)
    {
        admin[AdminCount] = new Admin();
        admin[AdminCount].set_Username(name);
        admin[AdminCount].set_Password(pass);
        admin[AdminCount].setDate(date);
        admin[AdminCount].set_role(role);
        admin[AdminCount].setWorking_hours(hours);
        AdminCount++;
    }

    public static void show_admins()
    {
        System.out.println("Admins List: ");
        for(int i = 0 ; i < AdminCount; i++)
        {
            System.out.println("Admin Username " + admin[i].get_Username());
            System.out.println("Admin Role " + admin[i].get_Role());
            System.out.println("Admin Password " + admin[i].get_Password());
            System.out.println("Admin Date of Birth " + admin[i].getDate());
            System.out.println("Admin Working Hours " + admin[i].get_Working_hours());
            System.out.println("Admin Age " + admin[i].get_Age());
        }
    }

    public static boolean addCustomer(Customer newCustomer) {
        if (customerCount < customers.length) {  // Check if there's space in the array
            customers[customerCount] = newCustomer;  // Add the new customer at the current count index
            customerCount++;  // Increment the customer count
            return true;  // Successfully added the customer
        } else {
            return false;  // No space left in the array
        }
    }

    public static void create_customer(String name, String pass, String date, String address, Customer.Gender gender, double balance, Customer.Interests[] interests) {
        customers[customerCount] = new Customer(name, pass, date, address, gender, balance, interests[0]);
        for (int i = 1; i < interests.length; i++) {
            customers[customerCount].add_Interest(interests[i]);
        }
        customerCount++;
    }

    public static void removeCustomer(int index) {
        if (index >= 0 && index < customerCount) { // Check index validity
            System.out.println("Removing customer: " + customers[index].get_Username());

            // Shift all elements after the index to the left
            for (int i = index; i < customerCount - 1; i++) {
                customers[i] = customers[i + 1];
            }

            // Nullify the last position (after shifting)
            customers[customerCount - 1] = null;

            // Decrement the customer count
            customerCount--;

            System.out.println("Customer removed successfully. Updated Customer Count: " + customerCount);
        } else {
            System.out.println("Invalid index. No customer removed.");
        }
    }

    public static void Add_Order(Order orders){
        Orders_Data[Order_count] = orders;
        Order_count++;
    }

    public static Product returnProduct(String productName){
        for(int i = 0; i <20; i++){
            if(Products[i].Name.equals(productName)){
                return Products[i];
            }
        }
        System.out.println("No product found with this name");
        return  null;
    }

    public static Product[] displayProducts() {
        Product[] products = new Product[productsCount];
        System.arraycopy(Database.Products, 0, products, 0, productsCount);
        return products;
    }

    public static boolean isProductArrayFull() {
        return productsCount >= MAX_SIZE;
    }

    public static boolean isProductNameUnique(String name) {
        for (int i = 0; i < Database.productsCount; i++) {
            if (Database.Products[i].get_Name().equalsIgnoreCase(name)) { // Case-insensitive check
                return false; // Name is not unique
            }
        }
        return true; // Name is unique
    }

    public static boolean check_exist(String name)
    {
        for(int i = 0; i < Database.productsCount;i++){
            if(Database.Products[i].Name.equals(name)){
                return false;
            }
        }
        return true;
    }

    public static void addProduct(Product product) {
        // Check if the item already exists
        for (int i = 0; i < Database.productsCount; i++) {
            if (Database.Products[i].get_Name().equals(product.get_Name())) {
                System.out.println("Product already exists");
                return;
            }
        }
        // Check if the container is full
        if (Database.productsCount >= Database.MAX_SIZE) {
            System.out.println("Cannot add more items. The list is full.");
            return;
        }
        // Add new item
        Database.Products[Database.productsCount] = product;
        Database.productsCount++;
    }

    public static void deleteProduct(Product item) {
        for (int i = 0; i < Database.productsCount; i++) {
            if (Database.Products[i].get_Name().equals(item.get_Name())) {

                for (int k = i; k < Database.productsCount - 1; k++) {
                    Database.Products[k] = Database.Products[k + 1];
                }
                Database.Products[Database.productsCount - 1] = null;
                Database.productsCount--;
                System.out.println("Product deleted");
                return;
            }
        }
        System.out.println("Product not found");
    }

    public static void editConsumablesProduct(Product product , String name, double price , int stock , String categoryInput , String dateInput){
        for(int i = 0; i < Database.productsCount;i++) {
            if(Database.Products[i].get_Name().equals(product.get_Name())) {

                if (Database.Products[i] instanceof Consumables) {

                    Consumables originalProduct = new Consumables(Database.Products[i].get_Name(), Database.Products[i].get_price(),
                            Database.Products[i].get_stock(), ((Consumables)Database.Products[i]).get_Category(),
                            ((Consumables)Database.Products[i]).get_ExpirationDate());

                    boolean allValid = true;

                    if (!(Database.Products[i].set_UName(name))){

                        allValid = false;
                    }

                    if(!(Database.Products[i].set_UPrice(price))){
                        allValid = false;
                    }

                    if(!(Database.Products[i].set_Ustock(stock))){
                        allValid = false;
                    }

                    if(!(((Consumables) Database.Products[i]).set_UCategory(categoryInput))){
                        allValid = false;
                    }

                    if(!(((Consumables) Database.Products[i]).set_UExpirationDate(dateInput))){
                        allValid = false;

                    }

                    if (allValid) {
                        System.out.println("Product updated successfully");
                        return;
                    }
                    else {
                        // If any validation failed, revert changes (restore original product state)
                        Database.Products[i].set_Name(originalProduct.get_Name());
                        Database.Products[i].set_price(originalProduct.get_price());
                        Database.Products[i].set_Ustock(originalProduct.get_stock());
                        ((Consumables) Database.Products[i]).set_Category(originalProduct.get_Category().name());
                        ((Consumables) Database.Products[i]).set_ExpirationDate(originalProduct.get_ExpirationDate());

                        System.out.println("Product update failed. No changes were made.");
                        return;
                    }
                }
            }
        }
        System.out.println("Invalid Product");

    }

    public static void editClothingProduct(Product product , String name, double price , int stock , String categoryInput , String sizeInput){
        for(int i = 0; i < Database.productsCount;i++) {
            if(Database.Products[i].get_Name().equals(product.get_Name())) {

                if (Database.Products[i] instanceof Clothing) {

                    Clothing originalProduct = new Clothing(Database.Products[i].get_Name(), Database.Products[i].get_price(),
                            Database.Products[i].get_stock(), ((Clothing)Database.Products[i]).get_Category(),
                            ((Clothing)Database.Products[i]).get_Size());

                    boolean allValid = true;

                    if (!(Database.Products[i].set_UName(name))){

                        allValid = false;
                    }

                    if(!(Database.Products[i].set_UPrice(price))){
                        allValid = false;
                    }

                    if(!(Database.Products[i].set_Ustock(stock))){
                        allValid = false;
                    }

                    if(!(((Clothing) Database.Products[i]).set_UCategory(categoryInput))){
                        allValid = false;
                    }

                    if(!(((Clothing) Database.Products[i]).set_USize(sizeInput))){
                        allValid = false;
                        //System.out.println("Invalid date output, try again");
                    }

                    if (allValid) {
                        System.out.println("Product updated successfully");
                        return;
                    }
                    else {
                        // If any validation failed, revert changes (restore original product state)
                        Database.Products[i].set_Name(originalProduct.get_Name());
                        Database.Products[i].set_price(originalProduct.get_price());
                        Database.Products[i].set_Ustock(originalProduct.get_stock());
                        ((Clothing) Database.Products[i]).set_Category(originalProduct.get_Category().name());
                        ((Clothing) Database.Products[i]).set_Size(originalProduct.get_Size().toString());

                        System.out.println("Product update failed. No changes were made.");
                        return;
                    }
                }
            }
        }

        System.out.println("Invalid Product");
    }

    public static void editElectronicsProduct(Product product , String name, double price , int stock , String categoryInput , String dateInput){
        for(int i = 0; i < Database.productsCount;i++) {
            if(Database.Products[i].get_Name().equals(product.get_Name())) {

                if (Database.Products[i] instanceof Electronics) {

                    Electronics originalProduct = new Electronics(Database.Products[i].get_Name(), Database.Products[i].get_price(),
                            Database.Products[i].get_stock(), ((Electronics)Database.Products[i]).get_Category(),
                            ((Electronics)Database.Products[i]).get_Waranty_Date_End());

                    boolean allValid = true;

                    if (!(Database.Products[i].set_UName(name))){

                        allValid = false;
                    }

                    if(!(Database.Products[i].set_UPrice(price))){
                        allValid = false;
                    }

                    if(!(Database.Products[i].set_Ustock(stock))){
                        allValid = false;
                    }

                    if(!(((Electronics) Database.Products[i]).set_UCategory(categoryInput))){
                        allValid = false;
                    }

                    if(!(((Electronics) Database.Products[i]).set_UExpirationDate(dateInput))){
                        allValid = false;
                        //System.out.println("Invalid date output, try again");
                    }

                    if (allValid) {
                        System.out.println("Product updated successfully");
                        return;
                    }
                    else {
                        // If any validation failed, revert changes (restore original product state)
                        Database.Products[i].set_Name(originalProduct.get_Name());
                        Database.Products[i].set_price(originalProduct.get_price());
                        Database.Products[i].set_Ustock(originalProduct.get_stock());
                        ((Electronics) Database.Products[i]).set_Category(originalProduct.get_Category().name());
                        ((Electronics) Database.Products[i]).set_Waranty_Date_End(originalProduct.get_Waranty_Date_End());

                        System.out.println("Product update failed. No changes were made.");
                        return;
                    }
                }
            }
        }
        System.out.println("Invalid Product");

    }
    public static Customer CheckCustomer(String username, String Pass) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].get_Username().equals(username) && customers[i].get_Password().equals(Pass)) {
                return customers[i];
            }
        }
        return null; // Should never happen if login succeeds
    }

    public static boolean CheckAdmin(String username, String Pass) {
        for (int i = 0; i < AdminCount; i++) {
            if (admin[i].get_Username().equals(username) && admin[i].get_Password().equals(Pass)) {
                return true;
            }
        }
        return false; // Should never happen if login succeeds
    }


    public static void UpdateStock(String productName , int amount_taken){
        for(int i = 0; i < productsCount;i++){
            if(Products[i].Name.equals(productName)){
                Products[i].Stock = Products[i].Stock - amount_taken;
            }
        }
    }

    public static int getCustomerCount() {
        return customerCount;
    }

    public static int getAdminCount() {
        return AdminCount;
    }

    public static int getOrder_count() {
        return Order_count;
    }

    public static int Decrement_Order_count() {
        Order_count--;
        return Order_count;
    }

    public static int getConsumables_count() {
        return consumables_count;
    }

    public static int getElectronics_count() {
        return electronics_count;
    }

    public static int getClothing_count() {
        return clothing_count;
    }

    public static double AverageAge(){
        double AvgAge = 0;
        for (int i = 0; i < customerCount; i++) {
            AvgAge += customers[i].get_Age();
        }
        AvgAge = AvgAge / customerCount;
        return AvgAge;
    }




}






