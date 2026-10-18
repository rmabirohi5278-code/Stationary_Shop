import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final ArrayList<String> products;
    private final ArrayList<Double> prices;
    private final ArrayList<Integer> quantities;

    public Inventory() {
        products = new ArrayList<>(List.of(
                "Pencils", "Pens", "Scale", "Eraser",
                "Sharpener", "NoteBooks", "Chart", "ChalkPiece"
        ));
        prices = new ArrayList<>(List.of(5.0, 10.0, 5.0, 2.0, 5.0, 40.0, 6.0, 10.0));
        quantities = new ArrayList<>(List.of(10, 13, 15, 20, 26, 13, 21, 19));
    }

    public int getProductCount() {
        return products.size();
    }

    public String getProductName(int index) {
        return products.get(index);
    }

    public double getPriceAt(int index) {
        return prices.get(index);
    }

    public int getQuantityAt(int index) {
        return quantities.get(index);
    }

    public ArrayList<String> getProducts() {
        return new ArrayList<>(products);
    }

    public int getIndex(String productName) {
        if (productName == null) {
            return -1;
        }
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).equalsIgnoreCase(productName.trim())) {
                return i;
            }
        }
        return -1;
    }

    public void displayProducts() {
        System.out.println("\n--------------- INVENTORY ---------------");
        System.out.printf("%-5s %-20s %-12s %-10s%n", "No.", "Product", "Price", "Quantity");
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%-5d %-20s $%-11.2f %-10d%n",
                    i + 1, products.get(i), prices.get(i), quantities.get(i));
        }
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
        }
        System.out.println("-----------------------------------------");
    }

    public boolean addProduct(String productName, double productPrice, int productQuantity) {
        if (productName == null || productName.trim().isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return false;
        }
        if (getIndex(productName) != -1) {
            System.out.println("A product with that name already exists.");
            return false;
        }
        if (!Double.isFinite(productPrice) || productPrice < 0) {
            System.out.println("Price must be a valid non-negative number.");
            return false;
        }
        if (productQuantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return false;
        }

        products.add(productName.trim());
        prices.add(productPrice);
        quantities.add(productQuantity);
        System.out.println("Product added successfully.");
        return true;
    }

    public boolean removeProduct(String productName) {
        int index = getIndex(productName);
        if (index == -1) {
            System.out.println("Product not found.");
            return false;
        }
        products.remove(index);
        prices.remove(index);
        quantities.remove(index);
        System.out.println("Product removed successfully.");
        return true;
    }

    public boolean changePrice(String productName, double newPrice) {
        int index = getIndex(productName);
        if (index == -1) {
            System.out.println("Product not found.");
            return false;
        }
        if (!Double.isFinite(newPrice) || newPrice < 0) {
            System.out.println("Price must be a valid non-negative number.");
            return false;
        }
        prices.set(index, newPrice);
        System.out.println("Price updated successfully.");
        return true;
    }

    public boolean changeQuantity(String productName, int newQuantity) {
        int index = getIndex(productName);
        if (index == -1) {
            System.out.println("Product not found.");
            return false;
        }
        if (newQuantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return false;
        }
        quantities.set(index, newQuantity);
        System.out.println("Quantity updated successfully.");
        return true;
    }

    public boolean reduceStock(int index, int quantity) {
        if (index < 0 || index >= products.size() || quantity <= 0 || quantities.get(index) < quantity) {
            return false;
        }
        quantities.set(index, quantities.get(index) - quantity);
        return true;
    }
}
