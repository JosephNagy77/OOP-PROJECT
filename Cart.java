public class Cart extends ItemContainer{
    protected Customer Customers_data;
    protected double TotalPrice = 0;


    Cart(Customer c){
        Customers_data = c;
        TotalPrice = 0;
    }

    public double get_TotalPrice(){
        double T_Price = 0;
        for(int i=0; i < ItemCount; i++){
            T_Price += (Container_Items[i].get_price() * Container_OrderQuantity[i]);
        }
        TotalPrice = T_Price;
        return T_Price;
    }

    public void Clear(){
        for(int i = 0; i<ItemCount ; i++){
            Container_Items[i] = null;
            Container_OrderQuantity[i] = 0;
        }
        ItemCount = 0;
        TotalPrice = 0;
    }

    public void ShowCart(){
        Print_All();
        System.out.println("Total Price = " + get_TotalPrice() + "$");
    }

}
