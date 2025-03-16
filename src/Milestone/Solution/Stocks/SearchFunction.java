package Milestone.Solution.Stocks;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// This class is responsible for searching products in the inventory. And provides methods to search products by various criteria
public class SearchFunction {
    public static void searchInventory() {
        // To search the inventory for products matching specified criteria
        Queue<ProductInsideFile> productQueue = new LinkedList<>(ProductInsideFile.loadProductsFromFile());

        if (productQueue.isEmpty()) {
            System.out.println("No stocks available for search.");
            return;
        }

        // Prompt that display search options
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Search Inventory ===");
        System.out.println("Search by: ");
        System.out.println("1. Date Entered");
        System.out.println("2. Stock Label");
        System.out.println("3. Brand");
        System.out.println("4. Engine Number");
        System.out.println("5. Status");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        boolean found = false;

        System.out.println("\nSearch Results:");
        System.out.printf("%-15s %-20s %-15s %-20s %-10s%n", "Date Entered", "Stock Label", "Brand", "Engine Number", "Status");
        System.out.println("----------------------------------------------------------------------------------");

        // Linear Search to iterate through the queue sequentially
        for (ProductInsideFile product : productQueue) {
            boolean match = false;

            // The purpose of this is to check if the product matches the search criteria
            switch (choice) {
                case 1 -> match = product.getDateEntered().toLowerCase().contains(keyword);
                case 2 -> match = product.getStockLabel().toLowerCase().contains(keyword);
                case 3 -> match = product.getBrand().toLowerCase().contains(keyword);
                case 4 -> match = product.getEngineNumber().toLowerCase().contains(keyword);
                case 5 -> match = product.getStatus().toLowerCase().contains(keyword);
                default -> {
                    System.out.println("Invalid choice.");
                    return;
                }
            }
            // This displays matching products
            if (match) {
                System.out.printf("%-15s %-20s %-15s %-20s %-10s%n",
                        product.getDateEntered(), product.getStockLabel(), product.getBrand(), product.getEngineNumber(), product.getStatus());
                found = true;
            }
        }
        // To inform if no matches found
        if (!found) {
            System.out.println("No matching records found.");
        }
    }
}

