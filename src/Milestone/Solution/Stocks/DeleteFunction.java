package Milestone.Solution.Stocks;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

// This class is used for deleting stock to the inventory
public class DeleteFunction {
    private LinkedList<ProductInsideFile> productList;
    private Scanner scanner;
    private String filePath;

    public DeleteFunction() {
        DataFilePath dataFilePath = new DataFilePath();
        this.filePath = dataFilePath.getFilePath();
        this.productList = new LinkedList<>(ProductInsideFile.loadProductsFromFile());
        this.scanner = new Scanner(System.in);
    }

    // It displays the current stock and prompts the user to select an item to delete. And delete a stock item from the inventory
    public void deleteStock() {
        if (productList.isEmpty()) {
            System.out.println("No stocks available to delete.");
            return;
        }

        // This display current stock list with numbers
        System.out.println("\nCurrent Stock List:");
        int index = 1;
        for (ProductInsideFile product : productList) {
            System.out.printf("%d. %s | %s | %s | %s | %s%n",
                    index, product.getDateEntered(), product.getStockLabel(),
                    product.getBrand(), product.getEngineNumber(), product.getStatus());
            index++;
        }

        System.out.print("\nEnter the number of stock to delete (or press 0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // To Consume newline

        // This deletes the selected stock if valid
        if (choice > 0 && choice <= productList.size()) {
            productList.remove(choice - 1); // <-- used .remove to selected stock
            System.out.println("Stock removed successfully");
            saveProductsToFile(); // To save updated list to the file
        } else {
            System.out.println("Invalid selection. No stock removed.");
        }
    }

    // Now this saves the current product list to the data file. Overwrites the existing file with the updated list
    private void saveProductsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            writer.write("Date Entered\tStock Label\tBrand\tEngine Number\tStatus");
            writer.newLine();

            // Write each product
            for (ProductInsideFile product : productList) {
                writer.write(String.format("%s\t%s\t%s\t%s\t%s",
                        product.getDateEntered(),
                        product.getStockLabel(),
                        product.getBrand(),
                        product.getEngineNumber(),
                        product.getStatus()));
                writer.newLine();
            }

            System.out.println("File updated successfully");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}



