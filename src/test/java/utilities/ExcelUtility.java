package utilities;

import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtility {
	private String path;
	private FileInputStream fi;
	private FileOutputStream fo;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	private CellStyle style;

	// Constructor to initialize the file path
	public ExcelUtility(String path) {
		this.path = path;
	}

	// Method to get the total row count in a given sheet
	public int getRowCount(String sheetName) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int rowcount = sheet.getLastRowNum(); // Get last row number (zero-based index)
		workbook.close();
		fi.close();
		return rowcount;
	}

	// Method to get the total column count in a specific row of a given sheet
	public int getCellCount(String sheetName, int rownum) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		int cellcount = (row != null) ? row.getLastCellNum() : 0; // Get total columns in the row
		workbook.close();
		fi.close();
		return cellcount;
	}

	// Method to retrieve cell data as a String from a specified sheet, row, and
	// column
	public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = (row != null) ? row.getCell(colnum) : null;
		DataFormatter formatter = new DataFormatter();
		String data = (cell != null) ? formatter.formatCellValue(cell) : ""; // Get formatted cell value
		workbook.close();
		fi.close();
		return data;
	}

	// Method to set data in a specific cell of a given sheet, row, and column
	public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
		File xlfile = new File(path);
		if (!xlfile.exists()) {
			// Create a new Excel file if it doesn't exist
			workbook = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			workbook.write(fo);
			fo.close();
		}
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		fi.close();

		// Create the sheet if it does not exist
		if (workbook.getSheetIndex(sheetName) == -1)
			workbook.createSheet(sheetName);
		sheet = workbook.getSheet(sheetName);

		// Create the row if it does not exist
		if (sheet.getRow(rownum) == null)
			sheet.createRow(rownum);
		row = sheet.getRow(rownum);

		// Create the cell and set the value
		cell = row.createCell(colnum);
		cell.setCellValue(data);

		// Save changes to the file
		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fo.close();
	}

	// Method to fill a cell with green color to indicate success
	public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
		modifyCellColor(sheetName, rownum, colnum, IndexedColors.GREEN.getIndex());
	}

	// Method to fill a cell with red color to indicate failure
	public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
		modifyCellColor(sheetName, rownum, colnum, IndexedColors.RED.getIndex());
	}

	// Private method to modify the background color of a specific cell
	private void modifyCellColor(String sheetName, int rownum, int colnum, short color) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		fi.close();
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		if (row != null) {
			cell = row.getCell(colnum);
			if (cell != null) {
				style = workbook.createCellStyle();
				style.setFillForegroundColor(color);
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cell.setCellStyle(style);
				fo = new FileOutputStream(path);
				workbook.write(fo);
				fo.close();
			}
		}
		workbook.close();
	}
}
