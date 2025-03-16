package Milestone.Solution.Stocks;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

// This class is used for adding new stock to the inventory.
public class AddFunction {
    private LinkedList<ProductInsideFile> stockList;
    private DataFilePath dataFilePath;

    // This constructor is used to initialize the stock list and data file path. And to load existing products from the file
    public AddFunction() {
        this.stockList = new LinkedList<>(ProductInsideFile.loadProductsFromFile()); // To load existing stocks
        this.dataFilePath = new DataFilePath();
    }

    // This is the prompt to the user to add a new stock item to the inventory and add the new product to the list
    public void addNewStock() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Add New Stock ===");
        System.out.print("Enter Date Entered (YYYY-MM-DD): ");
        String dateEntered = scanner.nextLine().trim();

        System.out.print("Enter Stock Label: ");
        String stockLabel = scanner.nextLine().trim();

        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine().trim();

        System.out.print("Enter Engine Number: ");
        String engineNumber = scanner.nextLine().trim();

        System.out.print("Enter Status (Available/Sold): ");
        String status = scanner.nextLine().trim();

        // Create new stock entry
        ProductInsideFile newStock = new ProductInsideFile(dateEntered, stockLabel, brand, engineNumber, status);
        stockList.add(newStock); // Add to linked list

        // And save the changes to the file
        saveToFile();

        System.out.println("Stock added successfully!");
    }
    // This saves the current stock list to the data file. Overwrites the existing file with the updated list
    private void saveToFile() {
        String filePath = dataFilePath.getFilePath();

        try (FileWriter writer = new FileWriter(filePath, false)) { // Overwrite file
            // Write header
            writer.write("Date Entered\tStock Label\tBrand\tEngine Number\tStatus\n");

            // Write each stock entry
            for (ProductInsideFile stock : stockList) {
                writer.write(stock.getDateEntered() + "\t" +
                        stock.getStockLabel() + "\t" +
                        stock.getBrand() + "\t" +
                        stock.getEngineNumber() + "\t" +
                        stock.getStatus() + "\n");
            }

        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }
}



