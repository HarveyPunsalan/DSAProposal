package Milestone.Solution.Stocks;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This class represents a product in the inventory. It handles products from the data file and displaying them.
public class ProductInsideFile {
    private String dateEntered;
    private String stockLabel;
    private String brand;
    private String engineNumber;
    private String status;

    // Constructor
    public ProductInsideFile(String dateEntered, String stockLabel, String brand, String engineNumber, String status) {
        this.dateEntered = dateEntered;
        this.stockLabel = stockLabel;
        this.brand = brand;
        this.engineNumber = engineNumber;
        this.status = status;
    }

    // Getters
    public String getDateEntered() { return dateEntered; }
    public String getStockLabel() { return stockLabel; }
    public String getBrand() { return brand; }
    public String getEngineNumber() { return engineNumber; }
    public String getStatus() { return status; }

    //  This method is to load products from file
    public static List<ProductInsideFile> loadProductsFromFile() {
        List<ProductInsideFile> products = new ArrayList<>();
        DataFilePath dataFilePath = new DataFilePath();
        String filePath = dataFilePath.getFilePath();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; //  Skip the first line

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; //  Skip the header row
                }

                String[] columns = line.split("\t"); // .split to split by tab

                if (columns.length < 5) {
                    System.err.println("Skipping invalid row: " + line);
                    continue;
                }

                //  Keep date as String to avoid parsing errors
                String dateEntered = columns[0].trim();
                String stockLabel = columns[1].trim();
                String brand = columns[2].trim();
                String engineNumber = columns[3].trim();
                String status = columns[4].trim();

                // This is to add the product to the list
                products.add(new ProductInsideFile(dateEntered, stockLabel, brand, engineNumber, status));
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return products;
    }

    //  Method to display all products
    public static void displayProducts() {
        List<ProductInsideFile> products = loadProductsFromFile();

        if (products.isEmpty()) {
            System.out.println("No stocks available.");
            return;
        }

        // To display header or title of the product
        System.out.println("\nCurrent Stock List:");
        System.out.printf("%-15s %-20s %-15s %-20s %-10s%n", "Date Entered", "Stock Label", "Brand", "Engine Number", "Status");
        System.out.println("----------------------------------------------------------------------------------");

        // To display each product
        for (ProductInsideFile product : products) {
            System.out.printf("%-15s %-20s %-15s %-20s %-10s%n",
                    product.getDateEntered(), product.getStockLabel(), product.getBrand(), product.getEngineNumber(), product.getStatus());
        }
    }
}













