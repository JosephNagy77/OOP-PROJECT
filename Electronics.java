import java.util.Date;
public class Electronics extends Product{
    public enum Category  {
        MobilePhones , Televisions , SmartWatches , Miscellaneous
    };
    private Category category;
    private Date Waranty_Date_End;

    Electronics(){

    }

    Electronics(String Product_Name , double Product_Price, int Product_Stock , Category c , String date){
        super(Product_Name , Product_Price, Product_Stock);
        category = c;
        Waranty_Date_End = java.sql.Date.valueOf(date);
    }

    public void set_Category(String categoryInput){

        try{
            category = Category.valueOf(categoryInput);
        }
        catch (IllegalArgumentException ex){
            System.out.println("Invalid category output, try again");
        }

    }

    public void set_Waranty_Date_End(String dateInput){
        try{
            Waranty_Date_End = java.sql.Date.valueOf(dateInput);
        }
        catch (IllegalArgumentException ex){
            System.out.println("Invalid waranty date output, try again");
        }
    }

    public Category get_Category(){
        return category;
    }

    public String get_Waranty_Date_End(){
        return Waranty_Date_End.toString();
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Category: " + category +
                ", Warranty End Date: " + Waranty_Date_End;
    }

    public boolean set_UCategory(String categoryInput){
        try{
            category = Electronics.Category.valueOf(categoryInput);
            return true;
        }
        catch (IllegalArgumentException ex){

            return false;
        }


    }

    public boolean set_UExpirationDate(String dateInput){
        try{
            Waranty_Date_End = java.sql.Date.valueOf(dateInput);
            return true;
        }
        catch (IllegalArgumentException ex){

            System.out.println("Invalid date output, try again");
            return false;
        }

    }




}
