public  class Admin extends Person{

    public enum Role
    {
        MANAGER,
        CASHIER,
        SALES,
        ASSISTANT,
        CUSTOMERSERVICE
    }
    private Role role;
    private int working_hours;

    Admin()
    {
        super();
    }

    Admin(String name, String pass, String date, Role role, int hours)
    {
        super(name, pass, date);
        this.role = role;
        working_hours = hours;
    }

    public void set_role(Role role)
    {
        this.role= role;
    }

    public void setWorking_hours(int hour)
    {
        this.working_hours = hour;
    }

    public Role get_Role()
    {
        return role;
    }
    public int get_Working_hours()
    {
        return working_hours;
    }

    public void show_Users()
    {
        Database.show_Customers();
    }
    public void show_admins()
    {
        Database.show_admins();
    }

    public void show_products()
    {
        System.out.println("Products List: ");
    }

    public void show_orders()
    {
        System.out.println("Orders List: ");
    }

    public void Show_all()
    {
        show_products();
        show_orders();
        show_Users();
        show_admins();
    }



}
