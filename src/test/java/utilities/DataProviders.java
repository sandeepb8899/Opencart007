package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

    // DataProvider 1
    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {
        String path = ".\\testData\\Opencart_LoginData.xlsx"; // Taking Excel file from testData
//        String path = System.getProperty("user.dir") + "\\testData\\Opencart_LoginData.xlsx"; // Dynamic path


        ExcelUtility xlutil = new ExcelUtility(path); // Creating an object for ExcelUtility

        int totalrows = xlutil.getRowCount("Sheet1");
        int totalcols = xlutil.getCellCount("Sheet1", 1);

        String logindata[][] = new String[totalrows][totalcols]; // Creating a 2D array

        // Reading data from Excel and storing it in the 2D array
        for (int i = 1; i <= totalrows; i++) { // Start from 1 since row 0 is usually headers
            for (int j = 0; j < totalcols; j++) { // Columns start from 0
                logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j); // Store data in array
            }
        }
        return logindata; // Returning the 2D array
    }
}
