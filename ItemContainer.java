public abstract class ItemContainer {
    private static final int MAX_SIZE = 100;
    protected int ItemCount = 0;
    protected Product [] Container_Items = new Product[MAX_SIZE];
    protected int [] Container_OrderQuantity = new int[MAX_SIZE];


    public void AddItem(Product Item, int quantity) {
        // Check if the item already exists
        for (int i = 0; i < ItemCount; i++) {
            if (Container_Items[i].get_Name().equals(Item.get_Name())) {
                int newQuantity = Container_OrderQuantity[i] + quantity;
                if (newQuantity > Item.get_stock()) {
                    System.out.println("Not enough stock. Maximum stock of " + Item.get_Name() + " has been added to cart( current Cart:" + Item.get_stock() +" )");
                    Container_OrderQuantity[i] = Item.get_stock();
                } else {
                    Container_OrderQuantity[i] = newQuantity;
                }
                return;
            }
        }
        // Check if the container is full
        if (ItemCount >= MAX_SIZE) {
            System.out.println("Cannot add more items. The container is full.");
            return;
        }
        // Add new item
        Container_Items[ItemCount] = Item;
        if (quantity > Item.get_stock()) {
            System.out.println("Not enough stock. Maximum stock of " + Item.get_Name() + " has been added to cart( Current Cart:" + Item.get_stock() +" )");
            Container_OrderQuantity[ItemCount] = Item.get_stock();
        } else {
            Container_OrderQuantity[ItemCount] = quantity;
        }
        ItemCount++;
    }
    public void DeleteItem(Product Item){
        for(int i=0; i<ItemCount; i++){
            if((Container_Items[i].get_Name()).equals(Item.get_Name())){
                for(int k = i; k<(ItemCount - i); k++){
                    Container_Items[k] = Container_Items[k+1];
                    Container_OrderQuantity[k]= Container_OrderQuantity[k+1];
                }
                ItemCount--;
                break;
            }
        }
    }
    public void Edit_Quantity(Product Item, int new_quantity){
        for(int i=0; i<ItemCount; i++){
            if((Container_Items[i].get_Name()).equals(Item.get_Name())){
                Container_OrderQuantity[i] = new_quantity;
                if(Container_OrderQuantity[i] <= Item.get_stock()){
                    return;
                }
                else{
                    System.out.println("Not enough stock. Maximum stock of " + Item.get_Name() + " has been added to cart( Current Cart:" + Item.get_stock() +" )");            Container_OrderQuantity[ItemCount] = Item.get_stock();
                    Container_OrderQuantity[i] = Item.get_stock();
                }
            }
        }
    }
    public void Print_All(){
        for(int i=0; i<ItemCount; i++){
            System.out.println(Container_Items[i].get_Name() + "   " + Container_OrderQuantity[i]);
        }
    }

    public int get_ItemCount(){
        return ItemCount;
    }

    public Product get_Item(int i){
        return Container_Items[i];
    }


    public int get_Quantity(int i){
        return Container_OrderQuantity[i];
    }


    public int indexOfProduct(Product p){
        for(int i=0; i<ItemCount; i++) {
            if ((Container_Items[i].get_Name()).equals(p.get_Name())) {
                return i;
            }
        }
        return -1;
    }

}