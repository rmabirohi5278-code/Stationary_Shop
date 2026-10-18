import java.util.Scanner;

public class Admin {
    private static final int PASSWORD = 2705;
    private static final String ADMIN_NAME = "Someone_coder";
    private final Inventory inventory;
    private final Customer customer;

    public Admin(Inventory inventory, Customer customer) {
        this.inventory = inventory;
        this.customer = customer;
    }

    public String getAdminName() {
        return ADMIN_NAME;
    }

    public boolean checkAdmin(int password) {
        return password == PASSWORD;
    }

    public void display() {
        inventory.displayProducts();
    }

    public void operationsOfAdmin(Scanner scan) {
        boolean running = true;
        while (running) {
            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1. See products in inventory");
            System.out.println("2. Add a product");
            System.out.println("3. Remove a product");
            System.out.println("4. Change product price");
            System.out.println("5. Change product quantity");
            System.out.println("6. See total revenue");
            System.out.println("7. See total customers");
            System.out.println("8. See customer log");
            System.out.println("9. See total products sold");
            System.out.println("0. Exit admin menu");
            System.out.print("Enter choice: ");

            Integer choice = readInteger(scan);
            if (choice == null) {
                System.out.println("Please enter a valid whole-number choice.");
                continue;
            }

            switch (choice) {
                case 1:
                    display();
                    break;
                case 2:
                    System.out.print("Enter product name: ");
                    String newName = scan.nextLine().trim();
                    Double newPrice = readDouble(scan, "Enter product price: ");
                    if (newPrice == null) break;
                    Integer newQuantity = readInteger(scan, "Enter product quantity: ");
                    if (newQuantity == null) break;
                    inventory.addProduct(newName, newPrice, newQuantity);
                    break;
                case 3:
                    System.out.print("Enter product name to remove: ");
                    inventory.removeProduct(scan.nextLine().trim());
                    break;
                case 4:
                    System.out.print("Enter product name: ");
                    String priceName = scan.nextLine().trim();
                    Double price = readDouble(scan, "Enter new price: ");
                    if (price != null) inventory.changePrice(priceName, price);
                    break;
                case 5:
                    System.out.print("Enter product name: ");
                    String quantityName = scan.nextLine().trim();
                    Integer quantity = readInteger(scan, "Enter new quantity: ");
                    if (quantity != null) inventory.changeQuantity(quantityName, quantity);
                    break;
                case 6:
                    customer.displayRevenue();
                    break;
                case 7:
                    customer.displayCustomerTotal();
                    break;
                case 8:
                    customer.displayCustomerLog();
                    break;
                case 9:
                    customer.displayTotalProductsSold();
                    break;
                case 0:
                    running = false;
                    System.out.println("Leaving admin menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a listed option.");
            }
        }
    }

    private Integer readInteger(Scanner scan) {
        String input = scan.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private Integer readInteger(Scanner scan, String prompt) {
        System.out.print(prompt);
        return readInteger(scan);
    }

    private Double readDouble(Scanner scan, String prompt) {
        System.out.print(prompt);
        String input = scan.nextLine().trim();
        try {
            double value = Double.parseDouble(input);
            if (!Double.isFinite(value)) return null;
            return value;
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a valid number.");
            return null;
        }
    }
}
