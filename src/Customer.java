import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
    private final Inventory inventory;
    private double revenue;
    private int customerTotal;
    private int totalProductSold;
    private final ArrayList<String> customerLog;

    public Customer(Inventory inventory) {
        this.inventory = inventory;
        this.revenue = 0.0;
        this.customerTotal = 0;
        this.totalProductSold = 0;
        this.customerLog = new ArrayList<>();
    }

    public void buy(Scanner scan) {
        System.out.print("Enter your name: ");
        String name = scan.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        // Ask for customer type ONCE for the whole purchase
        System.out.println("Customer type: 1. Regular (10% discount) 2. Student (20% discount) 3. New/Other (no discount)");
        System.out.print("Choose 1, 2, or 3: ");
        Integer type = readInteger(scan);
        if (type == null || type < 1 || type > 3) {
            System.out.println("Invalid customer type. Transaction cancelled.");
            return;
        }

        double discountRate = (type == 1) ? 0.10 : (type == 2) ? 0.20 : 0.0;
        double overallSubtotal = 0.0;
        boolean madePurchase = false;

        System.out.println("\n--- Starting Purchase for " + name + " ---");
        inventory.displayProducts();

        while (true) {
            System.out.print("\nEnter product name to purchase (or type 'done' to finish): ");
            String productName = scan.nextLine().trim();
            if (productName.equalsIgnoreCase("done")) {
                break;
            }

            int index = inventory.getIndex(productName);
            if (index == -1) {
                System.out.println("Product not found. Please check the product name.");
                continue;
            }

            double unitPrice = inventory.getPriceAt(index);
            int available = inventory.getQuantityAt(index);
            System.out.printf("Price per item: $%.2f | Available quantity: %d%n", unitPrice, available);

            System.out.print("Enter quantity: ");
            Integer quantity = readInteger(scan);
            if (quantity == null) {
                System.out.println("Invalid quantity. Please enter a whole number.");
                continue;
            }
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than zero.");
                continue;
            }
            if (quantity > available) {
                System.out.println("Insufficient stock. Available quantity: " + available);
                continue;
            }

            if (!inventory.reduceStock(index, quantity)) {
                System.out.println("Could not complete purchase because stock changed. Please try again.");
                continue;
            }

            // Accumulate total raw cost before discount and track global item sales
            overallSubtotal += (unitPrice * quantity);
            totalProductSold += quantity;
            madePurchase = true;

            System.out.println("Added to cart.");
        }

        // Print final receipt summary after typing 'done'
        if (madePurchase) {
            // Apply the single discount rate to the whole purchase amount
            double finalDiscountAmount = overallSubtotal * discountRate;
            double finalTotalPaid = overallSubtotal - finalDiscountAmount;

            // Update global store metrics
            revenue += finalTotalPaid;
            customerTotal++;
            customerLog.add(name);

            System.out.println("\n=============== FINAL RECEIPT ===============");
            System.out.println("Customer Name:    " + name);
            System.out.printf("Cart Subtotal:    $%.2f%n", overallSubtotal);
            System.out.printf("Discount Applied: -$%.2f (%.0f%%)%n", finalDiscountAmount, (discountRate * 100));
            System.out.printf("Total Amount Paid: $%.2f%n", finalTotalPaid);
            System.out.println("=============================================");
            System.out.println("Thank you for shopping, " + name + "!");
        } else {
            System.out.println("No purchase was completed.");
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

    public void displayRevenue() {
        System.out.printf("Total revenue earned: $%.2f%n", revenue);
    }

    public void displayCustomerTotal() {
        System.out.println("Total customers who completed a purchase: " + customerTotal);
    }

    public void displayCustomerLog() {
        System.out.println("\n----------- CUSTOMER LOG -----------");
        if (customerLog.isEmpty()) {
            System.out.println("No completed purchases yet.");
        } else {
            for (int i = 0; i < customerLog.size(); i++) {
                System.out.println((i + 1) + ". " + customerLog.get(i));
            }
        }
        System.out.println("------------------------------------");
    }

    public void displayTotalProductsSold() {
        System.out.println("Total individual items sold: " + totalProductSold);
    }
}
