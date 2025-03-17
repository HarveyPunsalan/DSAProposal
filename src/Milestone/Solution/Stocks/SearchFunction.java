package Milestone.Solution.Stocks;
import java.util.Scanner;
import java.util.List;

// Handles searching in inventory using BST for stock labels and linear search for other fields
public class SearchFunction {
    private static Node root; // The root node of the BST for stock labels for stock labels

    // Inner class that  represents the node class for in the BST
    private static class Node {
        ProductInsideFile stockData;
        Node left, right;

        Node(ProductInsideFile stockData) {
            this.stockData = stockData;
            this.left = this.right = null;
        }
    }

    // Insert stock into BST by Stock Label
    private static void insertIntoBST(ProductInsideFile stockData) {
        root = insertRec(root, stockData);
    }

    private static Node insertRec(Node node, ProductInsideFile stockData) {
        if (node == null) {
            return new Node(stockData);
        }
        int compare = stockData.getStockLabel().compareToIgnoreCase(node.stockData.getStockLabel());
        if (compare < 0) {
            node.left = insertRec(node.left, stockData);
        } else if (compare > 0) {
            node.right = insertRec(node.right, stockData);
        }
        return node;
    }

    // Search BST for stock label
    private static ProductInsideFile searchBST(String stockLabel) {
        return searchBSTRec(root, stockLabel);
    }

    private static ProductInsideFile searchBSTRec(Node node, String stockLabel) {
        if (node == null) {
            return null; // Not found
        }
        // This is comparing stock labels to decide where to insert
        int compare = stockLabel.compareToIgnoreCase(node.stockData.getStockLabel());
        if (compare == 0) {
            return node.stockData; // Found
        } else if (compare < 0) {
            return searchBSTRec(node.left, stockLabel);
        } else {
            return searchBSTRec(node.right, stockLabel);
        }
    }

    // Load inventory into BST when class is first used
    static {
        List<ProductInsideFile> stockList = ProductInsideFile.loadProductsFromFile();
        for (ProductInsideFile stock : stockList) {
            insertIntoBST(stock);
        }
    }

    // Search inventory function
    public static void searchInventory() {
        Scanner scanner = new Scanner(System.in);

        // Prompt that displays search options
        System.out.println("\n=== Search Inventory ===");
        System.out.println("Search by: ");
        System.out.println("1. Date Entered");
        System.out.println("2. Stock Label");
        System.out.println("3. Brand");
        System.out.println("4. Engine Number");
        System.out.println("5. Status");
        System.out.print("Enter your choice: ");
        int searchOption = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter search keyword: ");
        String searchKeyword = scanner.nextLine().trim().toLowerCase();

        boolean isMatchFound = false;

        // Table format header
        System.out.println("\nSearch Results:");
        System.out.printf("%-15s %-20s %-15s %-20s %-10s%n", "Date Entered", "Stock Label", "Brand", "Engine Number", "Status");
        System.out.println("----------------------------------------------------------------------------------");

        if (searchOption == 2) {
            // I use bst for searching stock label
            ProductInsideFile foundStock = searchBST(searchKeyword);
            if (foundStock != null) {
                displayStock(foundStock);
                isMatchFound = true;
            }
        } else {
            // I use linear search for other search criteria
            for (ProductInsideFile stockItem : ProductInsideFile.loadProductsFromFile()) {
                boolean isMatch = false;
                switch (searchOption) {
                    case 1 -> isMatch = stockItem.getDateEntered().toLowerCase().contains(searchKeyword);
                    case 3 -> isMatch = stockItem.getBrand().toLowerCase().contains(searchKeyword);
                    case 4 -> isMatch = stockItem.getEngineNumber().toLowerCase().contains(searchKeyword);
                    case 5 -> isMatch = stockItem.getStatus().toLowerCase().contains(searchKeyword);
                    default -> {
                        System.out.println("Invalid choice.");
                        return;
                    }
                }
                if (isMatch) {
                    displayStock(stockItem);
                    isMatchFound = true;
                }
            }
        }

        if (!isMatchFound) {
            System.out.println("No matching records found.");
        }
    }

    // This is used to format and prints stock details in tabular format
    private static void displayStock(ProductInsideFile stock) {
        System.out.printf("%-15s %-20s %-15s %-20s %-10s%n",
                stock.getDateEntered(), stock.getStockLabel(), stock.getBrand(),
                stock.getEngineNumber(), stock.getStatus());
    }
}


