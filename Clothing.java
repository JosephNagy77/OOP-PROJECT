import java.util.Date;

public class Clothing extends Product{
    public enum Category  {
        Shirts , Jeans , Jackets , Miscellaneous
    };
    private Category category;
    public enum Size  {
        XXL, XL , L , Miscellaneous
    };
    private Size size;

    Clothing(){

    }

    Clothing(String Product_Name , double Product_Price, int Product_Stock , Category c , Size s){
        super(Product_Name , Product_Price, Product_Stock);
        category = c;
        size = s;
    }

    public void set_Category(String categoryInput){

        try{

            category = Category.valueOf(categoryInput);

        }
        catch (IllegalArgumentException ex){
            System.out.println("Invalid category output, try again");
        }
    }

    public void set_Size(String sizeInput){

        try{
            size = Size.valueOf(sizeInput);
        }
        catch (IllegalArgumentException ex){
            System.out.println("Invalid size output, try again");
        }
    }

    public Category get_Category(){
        return category;
    }

    public Size get_Size(){
        return size;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Category: " + category +
                ", Size: " + size;
    }

    public boolean set_UCategory(String categoryInput){
        try{
            category = Clothing.Category.valueOf(categoryInput);
            return true;
        }
        catch (IllegalArgumentException ex){
            System.out.println("Invalid Category");
            return false;
        }
    }

    public boolean set_USize(String sizeInput){
        try{
            size = Clothing.Size.valueOf(sizeInput);
            return true;
        }
        catch (IllegalArgumentException ex){

            System.out.println("Invalid Size");
            return false;
        }
    }

    public String get_MainCtg(){
        return "Clothing";
    }


}
