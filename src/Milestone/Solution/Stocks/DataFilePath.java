package Milestone.Solution.Stocks;
import java.io.File;

// This class is responsible for handling the file path to the inventory data
public class DataFilePath {
    private String filePath;

    public DataFilePath() {

        this.filePath = "src/Milestone/Solution/Stocks/MotorPH Inventory Data - March 2023 Inventory Data.tsv";
    }

    public String getFilePath() {
        return filePath;
    }

    // Checks if the inventory data file exists. true if the file exists, false otherwise
    public boolean fileExists() {
        File file = new File(filePath);
        System.out.println("Looking for file at: " + file.getAbsolutePath());
        return file.exists();
    }
}








