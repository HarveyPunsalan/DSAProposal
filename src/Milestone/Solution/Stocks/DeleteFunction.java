package Milestone.Solution.Stocks;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// This class is used for deleting stock to the inventory
public class DeleteFunction {
    private StockTree stockTree;
    private Scanner scanner;
    private String filePath;

    public DeleteFunction() {
        DataFilePath dataFilePath = new DataFilePath();
        this.filePath = dataFilePath.getFilePath();
        this.scanner = new Scanner(System.in);

        // To initialize the BST with products from file
        List<ProductInsideFile> products = ProductInsideFile.loadProductsFromFile();
        this.stockTree = new StockTree();

        // To add all products to the BST
        for (ProductInsideFile product : products) {
            stockTree.addItem(product);
        }
    }

    // It displays the current stock and prompts the user to select an item to delete. And delete a stock item from the inventory
    public void deleteStock() {
        if (stockTree.isEmpty()) {
            System.out.println("No stocks available to delete.");
            return;
        }

        // This display current stock list with numbers
        System.out.println("\nCurrent Stock List:");
        List<ProductInsideFile> stockList = stockTree.getInventoryList();
        int index = 1;
        for (ProductInsideFile product : stockList) {
            System.out.printf("%d. %s | %s | %s | %s | %s%n",
                    index, product.getDateEntered(), product.getStockLabel(),
                    product.getBrand(), product.getEngineNumber(), product.getStatus());
            index++;
        }

        System.out.print("\nEnter the number of stock to delete (or press 0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // To Consume newline

        // This deletes the selected stock if valid
        if (choice > 0 && choice <= stockList.size()) {
            ProductInsideFile itemToRemove = stockList.get(choice - 1);
            stockTree.removeItem(itemToRemove);
            System.out.println("Stock removed successfully");
            saveUpdatedInventory(); // To save updated list to the file
        } else if (choice == 0) {
            System.out.println("Operation cancelled.");
        } else {
            System.out.println("Invalid selection. No stock removed.");
        }
    }

    // Now this saves the current product list to the data file. Overwrites the existing file with the updated list
    private void saveUpdatedInventory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            writer.write("Date Entered\tStock Label\tBrand\tEngine Number\tStatus");
            writer.newLine();

            // Write each product
            List<ProductInsideFile> updatedStock = stockTree.getInventoryList();
            for (ProductInsideFile item : updatedStock) {
                writer.write(String.format("%s\t%s\t%s\t%s\t%s",
                        item.getDateEntered(),
                        item.getStockLabel(),
                        item.getBrand(),
                        item.getEngineNumber(),
                        item.getStatus()));
                writer.newLine();
            }

            System.out.println("File updated successfully");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Node class for Binary Search Tree
    private static class StockNode {
        ProductInsideFile item;
        StockNode left;
        StockNode right;

        public StockNode(ProductInsideFile item) {
            this.item = item;
            this.left = null;
            this.right = null;
        }
    }

    // This is the binary search tree implementation for better stock management
    private static class StockTree {
        private StockNode head;

        public StockTree() {
            this.head = null;
        }

        // Check if its empty
        public boolean isEmpty() {
            return head == null;
        }

        // Add a product to the tree
        public void addItem(ProductInsideFile item) {
            head = addItemFromTree(head, item);
        }

        // A recursive helper method for adding items
        private StockNode addItemFromTree(StockNode current, ProductInsideFile item) {
            // If tree is empty insert the new product here
            if (current == null) {
                return new StockNode(item);
            }

            // Used the engine number for comparison and appears to be unique
            int result = item.getEngineNumber().compareTo(current.item.getEngineNumber());

            // Add to left or right branch based on comparison
            if (result < 0) {
                current.left = addItemFromTree(current.left, item);
            } else if (result > 0) {
                current.right = addItemFromTree(current.right, item);
            }

            return current;
        }

        // Now this is used to remove an item from the tree
        public void removeItem(ProductInsideFile item) {
            head = deleteItemFromTree(head, item);
        }

        // Helper method for removing items
        private StockNode  deleteItemFromTree(StockNode current, ProductInsideFile item) {
            // The base case if node is not found, return null
            if (current == null) {
                return null;
            }

            // Find the node to remove
            int result = item.getEngineNumber().compareTo(current.item.getEngineNumber());

            if (result < 0) {
                current.left = deleteItemFromTree(current.left, item);
            } else if (result > 0) {
                current.right = deleteItemFromTree(current.right, item);
            } else {
                // Found the node to delete

                // Case 1: Leaf node or single child
                if (current.left == null) {
                    return current.right;
                } else if (current.right == null) {
                    return current.left;
                }

                // Case 2: Two children
                // Find successor (smallest in right subtree)
                current.item = findSmallestItem(current.right);

                // Remove the successor
                current.right = deleteItemFromTree(current.right, current.item);
            }

            return current;
        }

        // Find smallest item in a subtree
        private ProductInsideFile findSmallestItem(StockNode node) {
            ProductInsideFile smallest = node.item;
            while (node.left != null) {
                smallest = node.left.item;
                node = node.left;
            }
            return smallest;
        }

        // Get all items in sorted order
        public List<ProductInsideFile> getInventoryList() {
            List<ProductInsideFile> inventory = new ArrayList<>();
            buildSortedInventory(head, inventory);
            return inventory;
        }

        // Helper for in-order traversal
        private void buildSortedInventory(StockNode node, List<ProductInsideFile> inventory) {
            if (node != null) {
                buildSortedInventory(node.left, inventory);
                inventory.add(node.item);
                buildSortedInventory(node.right, inventory);
            }
        }
    }
}



