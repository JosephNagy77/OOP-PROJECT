public class Order extends Cart{
    public enum PaymentMethod{
        CashOnDelivery,
        Balance,
        VodafoneCash
    }

    private PaymentMethod CustomerPayment;
    private boolean confirmed = false;

    Order(Customer c){
        super(c);
    }

    public void set_PaymentMethod(PaymentMethod p){
        CustomerPayment = p;
    }

    public PaymentMethod getCustomerPayment() {
        return CustomerPayment;
    }

    public void Confirm_Order(){
        for(int i=0; i< ItemCount; i++){
            Database.UpdateStock((Container_Items[i].get_Name()), Container_OrderQuantity[i]);
        }
        if(CustomerPayment == PaymentMethod.Balance && Customers_data.get_Balance() >= TotalPrice){
            Customers_data.set_Balance(Customers_data.get_Balance() - TotalPrice);
        }
        confirmed = true;
    }

    public void PrintOrder(){
        if(confirmed){
            System.out.println("Order Confirmed:-");
            System.out.println("Address delivered to:" + Customers_data.get_Address());
            System.out.println("Items Ordered:");
            ShowCart();
        }else{
            System.out.println("Order is not Confirmed yet");
        }
    }


}
