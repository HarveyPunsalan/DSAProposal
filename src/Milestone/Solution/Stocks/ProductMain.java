package Milestone.Solution.Stocks;
import java.util.Scanner;

// My main class
public class ProductMain {
    public static void main(String[] args) {
        DataFilePath dataPath = new DataFilePath();

        if (!dataPath.fileExists()) {
            System.err.println("Error: File does not exist at " + dataPath.getFilePath());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int choice;

        // To display menu options to user
        do {
            System.out.println("\n=== MotorPH Inventory System ===");
            System.out.println("1. List Current Stock");
            System.out.println("2. Add New Stock");
            System.out.println("3. Delete Stock");
            System.out.println("4. Sort Products (by Brand)");
            System.out.println("5. Search Inventory");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    ProductInsideFile.displayProducts();
                    break;
                case 2:
                    AddFunction addFunction = new AddFunction();
                    addFunction.addNewStock();
                    break;
                case 3:
                    DeleteFunction deleteFunction = new DeleteFunction();
                    deleteFunction.deleteStock();
                    break;
                case 4:
                    SortFunction.sortProductsByBrand();
                    break;
                case 5:
                    SearchFunction.searchInventory();
                    break;
                case 6:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 6); // It continues until the user chooses to exit
    }
}







