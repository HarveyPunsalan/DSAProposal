package Milestone.Solution.Stocks;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

/* This class is used for adding new stock entries and storing them in a hash table. And this
 * ensures efficient lookup and prevents duplicate stock labels
 */
public class AddFunction {
    private Hashtable<String, LinkedList<ProductInsideFile>> stockTable;
    private DataFilePath dataFilePath;

    // Constructor initializes stock and loads existing products from the file
    public AddFunction() {
        this.stockTable = new Hashtable<>();
        this.dataFilePath = new DataFilePath();
        loadExistingStock();
    }

    //  To load existing stock from the file and adds it to the hash table
    private void loadExistingStock() {
        for (ProductInsideFile product : ProductInsideFile.loadProductsFromFile()) {
            addProductToTable(product);
        }
    }

    // Add product to the hash table with collision handling
    private void addProductToTable(ProductInsideFile product) {
        String key = product.getStockLabel(); // It uses stock label is used as the unique key
        stockTable.putIfAbsent(key, new LinkedList<>());
        stockTable.get(key).add(product);
    }

    // This is used to collects user input to create and add a new stock entry
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

        System.out.print("Enter Status: ");
        String status = scanner.nextLine().trim();

        ProductInsideFile newProduct = new ProductInsideFile(dateEntered, stockLabel, brand, engineNumber, status);
        addProductToTable(newProduct);
        saveToFile(newProduct);

        System.out.println("Stock successfully added.");
    }

    // This save new stock to the file
    private void saveToFile(ProductInsideFile product) {
        try (FileWriter writer = new FileWriter(dataFilePath.getFilePath(), true)) {
            writer.write(product.getDateEntered() + "\t" + product.getStockLabel() + "\t" +
                    product.getBrand() + "\t" + product.getEngineNumber() + "\t" + product.getStatus() + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}



