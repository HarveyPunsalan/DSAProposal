package Milestone.Solution.Stocks;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This class is responsible for sorting products in the inventory and provides methods to sort products by brand
public class SortFunction {
    public static void sortProductsByBrand() {    // Sorts the products by brand using merge sort
        // To load products as array for sorting
        ProductInsideFile[] productsArray = loadProductsAsArray();

        if (productsArray.length == 0) {
            System.out.println("No stocks available to sort");
            return;
        }

        // Merge Sort by Brand
        mergeSortProducts(productsArray, 0, productsArray.length - 1);

        // Display the sorted products that still remain in the correct order based on the entered data, stock label, brand, engine number, status
        System.out.println("\nSorted Stock List (by Brand):");
        System.out.printf("%-15s %-20s %-15s %-20s %-10s%n", "Date Entered", "Stock Label", "Brand", "Engine Number", "Status");
        System.out.println("----------------------------------------------------------------------------------");

        for (ProductInsideFile product : productsArray) {
            System.out.printf("%-15s %-20s %-15s %-20s %-10s%n",
                    product.getDateEntered(), product.getStockLabel(), product.getBrand(), product.getEngineNumber(), product.getStatus());
        }
    }

    // Merge Sort implementation for sorting products
    private static void mergeSortProducts(ProductInsideFile[] stockItems, int startIdx, int endIdx) {
        if (startIdx < endIdx) {
            // Find the middle point of the array
            int midPoint = startIdx + (endIdx - startIdx) / 2;

            // Sort first and second halves
            mergeSortProducts(stockItems, startIdx, midPoint);
            mergeSortProducts(stockItems, midPoint + 1, endIdx);

            // Merge the sorted halves
            combineProductArrays(stockItems, startIdx, midPoint, endIdx);
        }
    }

    // Method to combine two sorted sub arrays
    private static void combineProductArrays(ProductInsideFile[] stockItems, int startIdx, int midPoint, int endIdx) {
        // Calculate sizes of two sub arrays to be merged
        int firstGroupSize = midPoint - startIdx + 1;
        int secondGroupSize = endIdx - midPoint;

        // Create temp arrays
        ProductInsideFile[] firstHalf = new ProductInsideFile[firstGroupSize];
        ProductInsideFile[] secondHalf = new ProductInsideFile[secondGroupSize];

        // Then this copy data to temp arrays
        for (int idx = 0; idx < firstGroupSize; idx++)
            firstHalf[idx] = stockItems[startIdx + idx];
        for (int idx = 0; idx < secondGroupSize; idx++)
            secondHalf[idx] = stockItems[midPoint + 1 + idx];

        // Then this will merge the sorted sub arrays back into the main array
        int firstCounter = 0;
        int secondCounter = 0;
        int mergedCounter = startIdx;

        while (firstCounter < firstGroupSize && secondCounter < secondGroupSize) {
            // The usage of this is to compare brands case-insensitive manner
            if (firstHalf[firstCounter].getBrand().compareToIgnoreCase(secondHalf[secondCounter].getBrand()) <= 0) {
                stockItems[mergedCounter] = firstHalf[firstCounter];
                firstCounter++;
            } else {
                stockItems[mergedCounter] = secondHalf[secondCounter];
                secondCounter++;
            }
            mergedCounter++;
        }

        // Copy remaining elements of firstHalf if any
        while (firstCounter < firstGroupSize) {
            stockItems[mergedCounter] = firstHalf[firstCounter];
            firstCounter++;
            mergedCounter++;
        }

        // Copy remaining elements of secondHalf if any
        while (secondCounter < secondGroupSize) {
            stockItems[mergedCounter] = secondHalf[secondCounter];
            secondCounter++;
            mergedCounter++;
        }
    }

    // To load products from file and return it as array
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

        return productList.toArray(new ProductInsideFile[0]); // to convert the list to an array before returning
    }
}

