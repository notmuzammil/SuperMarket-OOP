/**
 Names: Muzammil Ahmed, Abdur Rafay
 Roll No: 21k-3902
 Section: SE-B
 **/
import java.util.Scanner;
import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;

abstract class Person{

    private String name;
    private String phone;
    private String email;

    public Person(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    //abstract method to Display
    public abstract void Display();

    public void setName(String name){
        this.name = name;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setEmail(String email){
        this.email = email;
    }
    //getters
    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }
    public String getEmail(){
        return email;
    }

}

class Customer extends Person{
    private static int Cust_ID = 0;

    public Customer(String name, String phone, String email){
        super(name, phone, email);
        Cust_ID++;
    }
    public Customer(){
        super("","","");
        Cust_ID++;
    }
    public void input(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter customer name: ");
        setName(input.nextLine());
        System.out.println("Enter customer phone: ");
        setPhone(input.nextLine());
        System.out.println("Enter customer email: ");
        setEmail(input.nextLine());
    }
    //setters
    public void setCust_ID(int Cust_ID){
        this.Cust_ID = Cust_ID;
    }
    //getters
    public int getCust_ID(){
        return Cust_ID;
    }
    //method to Display customer details
    public void Display(){
        System.out.println("-------------|| Customer Details ||-------------");
        System.out.println("Customer ID: " + getCust_ID());
        System.out.println("Customer Name: " + getName());
        System.out.println("Customer PhoneNumber: " + getPhone());
        System.out.println("Customer Email: " + getEmail());
    }


}

class Employee extends Person{
    String emp_ID;

    public Employee(String name, String phone, String email, String emp_ID){
        super(name, phone, email);
        this.emp_ID = emp_ID;
    }
    public void setEmp_ID(String emp_ID){
        this.emp_ID = emp_ID;
    }
    //getters
    public String getEmp_ID(){
        return emp_ID;
    }
    //method to Display employee details
    public void Display(){
        System.out.println("Operator ID: " + getEmp_ID());
        System.out.println("Operator Name: " + getName());
        System.out.println("Email: " + getEmail());
    }
}

class Item {
    String name;
    String id;
    String category;
    String description;
    int qty;
    double price;

    Item(String name, String id, String category, String description, int qty, double price) {
        this.name = name;
        this.id = id;
        this.category = category;
        this.description = description;
        this.qty = qty;
        this.price = price;
    }

    public String toString() {
        return "Name: " + name + " ID: " + id + " Category: " + category + " Description: " + description
                + " Qty: " + qty + " Price: " + price;
    }
}

class Inventory {
    Item[] items;
    static int size;

    Inventory() {
        items = new Item[100];
        size = 0;
    }

    public void AddItem(Item item) {
        items[size] = item;
        size++;
    }

    public void DeleteItem(String id) {
        for (int i = 0; i < size; i++) {
            if (items[i].id.equals(id)) {
                for (int j = i; j < size - 1; j++) {
                    items[j] = items[j + 1];
                }
                size--;
                break;
            }
        }
    }

    public void updateItem(String id, int qty) {
        for (int i = 0; i < size; i++) {
            if (items[i].id.equals(id)) {
                items[i].qty = qty;
                break;
            }
        }
    }

    public void DecreaseQty(String id, int qty) {
        for (int i = 0; i < size; i++) {
            if (items[i].id.equals(id)) {
                items[i].qty = items[i].qty - qty;

                break;
            }
            try {
                save();
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }
    }
    //To Check that same id's must not exist
    public boolean CheckID(String id) {
        for (int i = 0; i < size; i++) {
            if (items[i].id.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void print() {
        // show inventory items in columns
        String format = "%-20s%-20s%-20s%-20s%-20s%-20s%n";
        System.out.format(format, "Name", "ID", "Category", "Description", "Qty", "Price");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < size; i++) {
            System.out.format(format, items[i].name, items[i].id, items[i].category, items[i].description,
                    items[i].qty, items[i].price);
        }
    }

    // Save inventory to file from array
    public void save() throws IOException {
        FileWriter fw = new FileWriter("inventory.txt");
        for (int i = 0; i < size; i++) {
            fw.write(items[i].name + "," + items[i].id + "," + items[i].category + "," + items[i].description + ","
                    + items[i].qty + "," + items[i].price + "\n");
        }
        fw.close();
    }

    // load inventory from file to array
    public void load() throws IOException {
        // file reader to read the file
        FileReader fr = new FileReader("inventory.txt");
        // Buffered reader to read line by line
        BufferedReader br = new BufferedReader(fr);
        // String to store the line
        String line = br.readLine();
        while (line != null) {
            String[] tokens = line.split(",");
            Item item = new Item(tokens[0], tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]),
                    Double.parseDouble(tokens[5]));

            AddItem(item);
            line = br.readLine();
        }
        br.close();
        fr.close();
    }

    public void Low() {
        for (int i = 0; i < size; i++) {
            if (items[i].qty < 10) {
                System.out.println(items[i]);
            }
        }
    }

    public void out() {
        for (int i = 0; i < size; i++) {
            if (items[i].qty == 0) {
                System.out.println(items[i]);
            }
        }
    }

    public void Excess() {
        for (int i = 0; i < size; i++) {
            if (items[i].qty > 100) {
                System.out.println(items[i]);
            }
        }
    }

    public Item Search(String id) {
        for (int i = 0; i < size; i++) {
            if (items[i].id.equals(id)) {
                return items[i];
            }
        }
        return null;
    }

    // IsQtyAvailable?
    public boolean IsQtyAvailable(String id, int qty) {
        for (int i = 0; i < size; i++) {
            if (items[i].id.equals(id)) {
                if (items[i].qty >= qty) {
                    return true;
                }
            }
        }
        return false;
    }
}

class Bill {

    Date date;
    Item[] items;
    int size;
    double total;
    double GST;
    int qty[];
    Employee employee;
    Customer customer;

    Bill() {
        items = new Item[100];
        qty = new int[100];
        size = 0;
        total = 0;
        GST = 0;
        employee = new Employee("Muzammil", "0332-2726688", "muzammil@gmail.com", "M1");
        customer = new Customer();
        customer.input();

    }

    public double getTotalPrice() {
        return total;
    }

    public void AddItemtoBill(Item item, int qty) {

        items[size] = item;
        this.qty[size] = qty;
        size++;
        total += item.price * qty;
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void print() {
        clearScreen();
        System.out.println("\t\t\t\t------------------|| Invoice ||---------------");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        //Employee operator details

        date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday" };
        // prints current date and time
        System.out.println("Date: " + formatter.format(date) + " " + days[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        //prints operator details
        employee.Display();
        //prints customer details
        customer.Display();
        String format = "%-20s%-20s%-20s%-20s%n";
        System.out.format(format, "Name", "ID", "Qty", "Price");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < size; i++) {
            System.out.format(format, items[i].name, items[i].id, qty[i], items[i].price);
        }
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------");
        System.out.format(format, "Total", "", "", total + " Rs");
        GST = total * 0.14;
        System.out.format(format, "GST (14%)", "", "", GST + " Rs");
        System.out.format(format, "Grand Total", "", "", total + GST + " Rs");
        System.out.println("\t\t\t\t----------------Thanks For Shopping!!-----------------");
        System.out.println("\t\t\t\t                     Please Visit Again");
    }
}

class Sales {
    Date date;
    double totalSales;
    double currentSales;

    Sales() {
        totalSales = 0;
        currentSales = 0;
    }

    public void AddItemSales(double sales) {
        totalSales += sales;
        currentSales += sales;

    }

    //method to show current sales of this session
    public void showSales() {
        System.out.println("Total Sales: " + currentSales);
    }
    //store total sales in file to show total sales till date
    public void save() throws IOException {
        FileWriter fw = new FileWriter("sales.txt");
        fw.write(totalSales + "\n");
        fw.close();
    }

    //load total sales from file
    public void load() throws IOException {
        // file reader to read the file
        FileReader fr = new FileReader("sales.txt");
        // Buffered reader to read line by line
        BufferedReader br = new BufferedReader(fr);
        // String to store the line
        String line = br.readLine();
        while (line != null) {
            totalSales = Double.parseDouble(line);
            line = br.readLine();
        }
        br.close();
        fr.close();
    }

    public void print() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        // prints current date and time
        System.out.println("Date: " + formatter.format(date) + " " + days[calendar.get(Calendar.        DAY_OF_WEEK) - 1]);
        System.out.println("Total Sales: " + totalSales);
        System.out.println("Current Sales: " + currentSales);
        // System.out.println("Total Profit: " + totalProfit);
    }
}

public class Supermarket {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // read heading from file
    public static void readHeading() throws IOException {
        FileReader fr = new FileReader("heading.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
        br.close();
        fr.close();
    }

    // input validation method to check the range of input
    public static int inputValidation(int min, int max) {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        boolean valid = false;
        while (!valid) {
            try {
                input = sc.nextInt();
                if (input < min || input > max) {
                    System.out.println("Invalid! ---> Please enter a number between " + min + " and " + max);
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid! ---> Please enter a number between " + min + " and " + max);
                sc.nextLine();
            }
        }
        return input;
    }

    public static void main(String[] args) {
        int choice;

        Scanner S = new Scanner(System.in);
        Inventory inventory = new Inventory();
        Sales sales = new Sales();

        try {
            inventory.load();

        } catch (IOException e) {
            System.out.println("File not found");
        }

        while (true) {
            // clearScreen();
            try {
                readHeading();
            } catch (IOException e) {
                System.out.println("File not found");
            }
            System.out.println("Are you a:\n1) Customer\n2)Employee");
            choice = inputValidation(1, 2);
            if (choice == 2) {
                System.out.println("Enter your ID: ");
                String id = S.next();
                System.out.println("Enter your password: ");
                String password = S.next();
                if (id.equals("admin") && password.equals("admin")) {
                    System.out.println("Welcome Admin");
                    while (true) {
                        System.out.println(
                                "1) AddItem \n2) DeleteItem \n3) updateItem \n4) Print Inventory\n5) Save Inventory\n6) Load Inventory\n7) Print Low Inventory\n8) Print Out of Stock Inventory\n9) Print Excess Inventory\n10)Print total sales and Profit for a day\n11) Exit");
                        // input validation
                        int choice2 = inputValidation(1, 11);

                        if (choice2 == 1) {
                            System.out.println("Enter name of item: ");
                            String name = S.next();
                            System.out.println("Enter ID of item: ");
                            String id2 = S.next();
                            while(inventory.CheckID(id2)){
                                System.out.println("ID already exists. Enter a new ID: ");
                                id2 = S.next();
                            }
                            System.out.println("Enter category the item belongs to: ");
                            String category = S.next();
                            System.out.println("Enter description of the item: ");
                            String description = S.next();
                            System.out.println("Enter qty of item: ");
                            int qty = S.nextInt();
                            System.out.println("Enter price of item: ");
                            double price = S.nextDouble();
                            Item item = new Item(name, id2, category, description, qty, price);
                            inventory.AddItem(item);
                        } else if (choice2 == 2) {
                            System.out.println("Enter ID: ");
                            String id2 = S.next();
                            inventory.DeleteItem(id2);
                        } else if (choice2 == 3) {
                            System.out.println("Enter ID: ");
                            String id2 = S.next();
                            System.out.println("Enter qty: ");
                            int qty = S.nextInt();
                            inventory.updateItem(id2, qty);
                        } else if (choice2 == 4) {
                            inventory.print();
                        } else if (choice2 == 5) {
                            try {
                                inventory.save();
                            } catch (IOException e) {
                                System.out.println("File not found");
                            }
                        } else if (choice2 == 6) {
                            try {
                                inventory.load();
                            } catch (IOException e) {
                                System.out.println("File not found");
                            }
                        } else if (choice2 == 7) {
                            inventory.Low();
                        } else if (choice2 == 8) {
                            inventory.out();
                        } else if (choice2 == 9) {
                            inventory.Excess();
                        } else if (choice2 == 10) {
                            try {
                                sales.load();
                            } catch (IOException e) {
                                System.out.println("File not found");
                            }
                            sales.print();
                        } else if (choice2 == 11) {
                            break;
                        } else if (choice2 == 11) {
                            break;
                        } else
                            break;
                    }
                }

            } else if (choice == 1) {
                Bill bill = new Bill();

                while (true) {
                    System.out.println("1) AddItem item to Cart\n2) Exit");
                    // input validation
                    int choice2 = inputValidation(1, 2);

                    if (choice2 == 1) {
                        inventory.print();
                        System.out.println("Enter ID: ");
                        String id2 = S.next();
                        System.out.println("Enter qty: ");
                        int qty = S.nextInt();
                        // check if item and its qty is available
                        if (inventory.IsQtyAvailable(id2, qty)) {
                            // AddItem to bill
                            bill.AddItemtoBill(inventory.Search(id2), qty);
                            // updateItem
                            inventory.DecreaseQty(id2, qty);
                            // AddItem total price to sales
                            sales.AddItemSales(bill.getTotalPrice());

                        } else {
                            System.out.println("Item not available in the required qty");
                        }

                        // want to continue buying items?
                        System.out.println("Do you want to continue buying items? (y/n)");
                        String choice3 = S.next();
                        if (choice3.equals("y")) {
                            continue;
                        } else {
                            // if no items in bill, then exit else print bill
                            if (bill.size > 0) {
                                bill.print();
                                try {
                                    sales.save();
                                } catch (IOException e) {
                                    System.out.println("File not found");
                                }

                            } else {
                                System.out.println("No items in the cart");
                            }
                            break;
                        }
                    } else if (choice2 == 2) {
                        break;
                    }
                }
            }
        }
    }
}
