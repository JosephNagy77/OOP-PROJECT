import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Product {
    protected String Name;
    protected double Price;
    protected int Stock;
    Scanner scanner = new Scanner(System.in);

    Product(){

    }

    Product(String Product_Name , double Product_Price, int Product_Stock){
        Name = Product_Name;
        Price = Product_Price;
        Stock = Product_Stock;
    }


    public void set_Name(String name){

        Name = name;

    }

    public void set_price(double price){
        try {
            Price = price;
            if (Price <= 0) {
                throw new IllegalArgumentException("Price must be greater than zero.");
            }

        }
        catch (InputMismatchException ex) {
            System.out.println("Invalid price input, must be a number");

        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void set_stock(int stock){
        try{
            Stock = stock;
            if (Stock <= 0) {
                throw new IllegalArgumentException("Stock must be greater than zero.");
            }
        }
        catch (InputMismatchException ex) {
            System.out.println("Invalid stock input, Stock must be a number");
        }
        catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }

    public String get_Name(){
        return Name;
    }

    public double get_price(){
        return Price;

    }

    public int get_stock(){
        return Stock;
    }

    @Override
    public String toString() {
        return "Name: " + Name +
                ", Price: " + Price +
                ", Stock: " + Stock;
    }



    public boolean set_UName(String name){

        if(Database.check_exist(name))
        {
            Name = name;
            return true;
        }
        else {
            System.out.println("Product exist");
            return false;
        }
    }

    public boolean set_UPrice(double price) {
        try {
            if (price <= 0) {
                throw new IllegalArgumentException("Price must be greater than zero.");
            }
            Price = price;
            return true;

        } catch (InputMismatchException ex) {
            System.out.println("Price must be number");
            return false;

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean set_Ustock(int stock){
        try{

            if (stock <= 0) {
                throw new IllegalArgumentException("Stock must be greater than zero.");
            }
            Stock = stock;
            return true;
        }
        catch (InputMismatchException ex) {
            System.out.println("Stock must be a number");
            return false;
        }
        catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }




}
