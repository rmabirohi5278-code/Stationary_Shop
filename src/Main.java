import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Create one shared inventory and pass it to both admin and customer.
        Inventory inventory = new Inventory();
        Customer customer = new Customer(inventory);
        Admin admin = new Admin(inventory, customer);

        String welcome = "Welcome to Aadhya Stationaries";
        System.out.println("\n" + welcome);
        boolean running = true;

        while (running) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String choice = scan.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter admin password: ");
                    String passwordInput = scan.nextLine().trim();
                    try {
                        int password = Integer.parseInt(passwordInput);
                        if (admin.checkAdmin(password)) {
                            System.out.println("Login successful. Welcome, " + admin.getAdminName() + "!");
                            admin.operationsOfAdmin(scan);
                        } else {
                            System.out.println("Incorrect password.");
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Password must be numeric.");
                    }
                    break;
                case "2":
                    customer.buy(scan);
                    break;
                case "0":
                    running = false;
                    System.out.println("Thank you for visiting Aadhya Stationaries!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 0.");
            }
        }

        scan.close();
    }
}
