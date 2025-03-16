package Milestone.Solution.Stocks;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This class is responsible for sorting products in the inventory and provides methods to sort products by brand
public class SortFunction {
    public static void sortProductsByBrand() {
        // To load products as array for sorting
        ProductInsideFile[] productsArray = loadProductsAsArray();

        if (productsArray.length == 0) {
            System.out.println("No stocks available to sort.");
            return;
        }

        // Bubble Sort by Brand
        int n = productsArray.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (productsArray[j].getBrand().compareToIgnoreCase(productsArray[j + 1].getBrand()) > 0) {
                    // Swap products
                    ProductInsideFile temp = productsArray[j];
                    productsArray[j] = productsArray[j + 1];
                    productsArray[j + 1] = temp;
                }
            }
        }

        // Display the sorted products that still remain in the correct order based on the entered data, stock label, brand, engine number, status
        System.out.println("\nSorted Stock List (by Brand):");
        System.out.printf("%-15s %-20s %-15s %-20s %-10s%n", "Date Entered", "Stock Label", "Brand", "Engine Number", "Status");
        System.out.println("----------------------------------------------------------------------------------");

        for (ProductInsideFile product : productsArray) {
            System.out.printf("%-15s %-20s %-15s %-20s %-10s%n",
                    product.getDateEntered(), product.getStockLabel(), product.getBrand(), product.getEngineNumber(), product.getStatus());
        }
    }

    // To load products from file and return as array
    private static ProductInsideFile[] loadProductsAsArray() {
        List<ProductInsideFile> productList = new ArrayList<>();
        DataFilePath dataFilePath = new DataFilePath();
        String filePath = dataFilePath.getFilePath();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // Skip the first line

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header row
                }

                String[] columns = line.split("\t"); // Split by tab

                if (columns.length < 5) {
                    System.err.println("Skipping invalid row: " + line);
                    continue;
                }

                String dateEntered = columns[0].trim();
                String stockLabel = columns[1].trim();
                String brand = columns[2].trim();
                String engineNumber = columns[3].trim();
                String status = columns[4].trim();

                productList.add(new ProductInsideFile(dateEntered, stockLabel, brand, engineNumber, status));
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return productList.toArray(new ProductInsideFile[0]); // Convert List to Array
    }
}

