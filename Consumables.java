import java.util.Date;

public class Consumables extends Product {
    public enum Category  {
        Food , Drinks , Medicine , Miscellaneous
    };
    private Category category;
    private Date ExpirationDate;

    Consumables(){

    }

    Consumables(String Product_Name , double Product_Price, int Product_Stock , Category c ,String date){

        super(Product_Name , Product_Price, Product_Stock);
        category = c;
        ExpirationDate = java.sql.Date.valueOf(date);
    }


    public void set_Category(String categoryInput){
        try{

            category = Category.valueOf(categoryInput);
        }
        catch (IllegalArgumentException ex){
            System.out.println("Invalid category output, try again");
        }


    }


    public void set_ExpirationDate(String dateInput){
        try{
            ExpirationDate = java.sql.Date.valueOf(dateInput);
        }
        catch (IllegalArgumentException ex){
            System.out.println("Invalid date output, try again");
        }

    }

    public Category get_Category(){
        return category;
    }

    public String get_ExpirationDate(){
        return ExpirationDate.toString();
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Category: " + category +
                ", Expiration Date: " + ExpirationDate;
    }

    public boolean set_UCategory(String categoryInput){
        try{
            category = Category.valueOf(categoryInput);
            return true;
        }
        catch (IllegalArgumentException ex){

            return false;
        }


    }

    public boolean set_UExpirationDate(String dateInput){
        try{
            ExpirationDate = java.sql.Date.valueOf(dateInput);
            return true;
        }
        catch (IllegalArgumentException ex){
            System.out.println("Invalid date output, try again");
            return false;
        }
    }

    public String get_MainCtg(){
        return "Consumables";
    }

}
