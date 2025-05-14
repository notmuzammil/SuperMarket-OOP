/*******************************
 Names: Muzammil Ahmed, Abdur Rafay
 Roll No: 21k-3902, 21k3856
 Section: SE-B
 ********************************/
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
    //abstract method to display
    public abstract void display();

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
    private static int customerID = 0;

    public Customer(String name, String phone, String email){
        super(name, phone, email);
        customerID++;
    }
    public Customer(){
        super("","","");
        customerID++;
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
    public void setCustomerID(int customerID){
        this.customerID = customerID;
    }
    //getters
    public int getCustomerID(){
        return customerID;
    }
    //method to display customer details
    public void display(){
        System.out.println("Customer Details");
        System.out.println("Customer ID: " + getCustomerID());
        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
    }
    //buy items from supermarket method

}

class Employee extends Person{
    String employeeID;

    public Employee(String name, String phone, String email, String employeeID){
        super(name, phone, email);
        this.employeeID = employeeID;
    }
    //setters
    public void setEmployeeID(String employeeID){
        this.employeeID = employeeID;
    }
    //getters
    public String getEmployeeID(){
        return employeeID;
    }
    //method to display employee details
    public void display(){
        System.out.println("Operator ID: " + getEmployeeID());
        System.out.println("Operator Name: " + getName());
        System.out.println("Email: " + getEmail());
    }
}

class Item {
    String name;
    String id;
    String category;
    String description;
    int quantity;
    double price;

    Item(String name, String id, String category, String description, int quantity, double price) {
        this.name = name;
        this.id = id;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public String toString() {
        return "Name: " + name + " ID: " + id + " Category: " + category + " Description: " + description
                + " Quantity: " + quantity + " Price: " + price;
    }
}

class Inventory {
    Item[] items;
    static int size;

    Inventory() {
        items = new Item[100];
        size = 0;
    }

    public void add(Item item) {
        items[size] = item;
        size++;
    }

    public void delete(String id) {
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

    public void update(String id, int quantity) {
        for (int i = 0; i < size; i++) {
            if (items[i].id.equals(id)) {
                items[i].quantity = quantity;
                break;
            }
        }
    }

    public void decreaseQuantity(String id, int quantity) {
        for (int i = 0; i < size; i++) {
            if (items[i].id.equals(id)) {
                items[i].quantity = items[i].quantity - quantity;

                break;
            }
            try {
                save();
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }
    }
    //method to check same id does not exist
    public boolean checkID(String id) {
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
        System.out.format(format, "Name", "ID", "Category", "Description", "Quantity", "Price");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < size; i++) {
            System.out.format(format, items[i].name, items[i].id, items[i].category, items[i].description,
                    items[i].quantity, items[i].price);
        }
    }

    // save inventory to file from array
    public void save() throws IOException {
        FileWriter fw = new FileWriter("inventory.txt");
        for (int i = 0; i < size; i++) {
            fw.write(items[i].name + "," + items[i].id + "," + items[i].category + "," + items[i].description + ","
                    + items[i].quantity + "," + items[i].price + "\n");
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
            // what are tokens?. tokens are the strings that are separated by commas
            String[] tokens = line.split(",");
            Item item = new Item(tokens[0], tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]),
                    Double.parseDouble(tokens[5]));

            add(item);
            line = br.readLine();
        }
        br.close();
        fr.close();
    }

    public void low() {
        for (int i = 0; i < size; i++) {
            if (items[i].quantity < 10) {
                System.out.println(items[i]);
            }
        }
    }

    public void out() {
        for (int i = 0; i < size; i++) {
            if (items[i].quantity == 0) {
                System.out.println(items[i]);
            }
        }
    }

    public void excess() {
        for (int i = 0; i < size; i++) {
            if (items[i].quantity > 100) {
                System.out.println(items[i]);
            }
        }
    }

    public Item search(String id) {
        for (int i = 0; i < size; i++) {
            if (items[i].id.equals(id)) {
                return items[i];
            }
        }
        return null;
    }

    // quantityAvailable?
    public boolean quantityAvailable(String id, int quantity) {
        for (int i = 0; i < size; i++) {
            if (items[i].id.equals(id)) {
                if (items[i].quantity >= quantity) {
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
    int quantity[];
    Employee employee;
    Customer customer;

    Bill() {
        items = new Item[100];
        quantity = new int[100];
        size = 0;
        total = 0;
        GST = 0;
        employee = new Employee("Aroon", "0300-134567", "abc@gmail.com", "E-1");
        customer = new Customer();
        customer.input();

    }

    public double getTotalPrice() {
        return total;
    }

    public void AddItemtoBill(Item item, int quantity) {

        items[size] = item;
        this.quantity[size] = quantity;
        size++;
        total += item.price * quantity;
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void print() {
        clearScreen();
        System.out.println("\t\t\t\t--------------------Invoice-----------------");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        //Employee operator details

        date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday" };
        // prints current date and time
        System.out.println("Date: " + formatter.format(date) + " " + days[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        //prints operator details
        employee.display();
        //prints customer details
        customer.display();
        String format = "%-20s%-20s%-20s%-20s%n";
        System.out.format(format, "Name", "ID", "Quantity", "Price");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < size; i++) {
            System.out.format(format, items[i].name, items[i].id, quantity[i], items[i].price);
        }
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------");
        System.out.format(format, "Total", "", "", total + " Rs");
        GST = total * 0.14;
        System.out.format(format, "GST (14%)", "", "", GST + " Rs");
        System.out.format(format, "Grand Total", "", "", total + GST + " Rs");
        System.out.println("\t\t\t\t----------------Thank You for Shopping!!-----------------");
        System.out.println("\t\t\t\t                     Visit Again");
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

    public void addSales(double sales) {
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
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max);
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max);
                sc.nextLine();
            }
        }
        return input;
    }

    public static void main(String[] args) {
        int choice;

        Scanner input = new Scanner(System.in);
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
                String id = input.next();
                System.out.println("Enter your password: ");
                String password = input.next();
                if (id.equals("admin") && password.equals("admin")) {
                    System.out.println("Welcome Admin");
                    while (true) {
                        System.out.println(
                                "1) Add Item\n2) Delete Item\n3) Update Item\n4) Print Inventory\n5) Save Inventory\n6) Load Inventory\n7) Print Low Inventory\n8) Print Out of Stock Inventory\n9) Print Excess Inventory\n10)Print total sales and Profit for a day\n11) Exit");
                        // input validation
                        int choice2 = inputValidation(1, 11);

                        if (choice2 == 1) {
                            System.out.println("Enter name of item: ");
                            String name = input.next();
                            System.out.println("Enter ID of item: ");
                            String id2 = input.next();
                            while(inventory.checkID(id2)){
                                System.out.println("ID already exists. Enter a new ID: ");
                                id2 = input.next();
                            }
                            System.out.println("Enter category the item belongs to: ");
                            String category = input.next();
                            System.out.println("Enter description of the item: ");
                            String description = input.next();
                            System.out.println("Enter quantity of item: ");
                            int quantity = input.nextInt();
                            System.out.println("Enter price of item: ");
                            double price = input.nextDouble();
                            Item item = new Item(name, id2, category, description, quantity, price);
                            inventory.add(item);
                        } else if (choice2 == 2) {
                            System.out.println("Enter ID: ");
                            String id2 = input.next();
                            inventory.delete(id2);
                        } else if (choice2 == 3) {
                            System.out.println("Enter ID: ");
                            String id2 = input.next();
                            System.out.println("Enter quantity: ");
                            int quantity = input.nextInt();
                            inventory.update(id2, quantity);
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
                            inventory.low();
                        } else if (choice2 == 8) {
                            inventory.out();
                        } else if (choice2 == 9) {
                            inventory.excess();
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
                    // The customer should show the inventory and allow the customer to buy items
                    System.out.println("1) Add item to Cart\n2) Exit");
                    // input validation
                    int choice2 = inputValidation(1, 2);

                    if (choice2 == 1) {
                        inventory.print();
                        System.out.println("Enter ID: ");
                        String id2 = input.next();
                        System.out.println("Enter quantity: ");
                        int quantity = input.nextInt();
                        // check if item and its quantity is available
                        if (inventory.quantityAvailable(id2, quantity)) {
                            // add item to bill
                            bill.AddItemtoBill(inventory.search(id2), quantity);
                            // update inventory
                            inventory.decreaseQuantity(id2, quantity);
                            // add total price to sales
                            sales.addSales(bill.getTotalPrice());

                        } else {
                            System.out.println("Item not available in the required quantity");
                        }

                        // want to continue buying items?
                        System.out.println("Do you want to continue buying items? (y/n)");
                        String choice3 = input.next();
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
