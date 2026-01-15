package Com.SRP_Filter_Model;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//public class ExcelReader {
//
//	    public static String getFullUrl(String filePath, String sheetName) {
//	        try (FileInputStream fis = new FileInputStream(filePath);
//	             Workbook workbook = new XSSFWorkbook(fis)) {
//
//	            Sheet sheet = workbook.getSheet(sheetName);
//
//	            Row row = sheet.getRow(0);       // First row
//	            String baseUrl = row.getCell(0).getStringCellValue().trim();
//	            String endUrl  = row.getCell(1).getStringCellValue().trim();
//
//	            return baseUrl + endUrl;         // Combine and return full URL
//	        }
//	        catch (Exception e) {
//	            throw new RuntimeException("Error reading URL from Excel", e);
//	        }
//	    }
//		}


public class ExcelReader {

    public static List<String> getFullUrl(String filePath, String sheetName) {
        List<String> fullUrls = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            int lastRow = sheet.getLastRowNum(); // total number of rows

            for (int i = 0; i <= lastRow; i++) {
                Row row = sheet.getRow(i);

                if (row == null) continue;
//                if (row != null) {
                    Cell baseCell = row.getCell(0);
                    Cell endCell  = row.getCell(1);
                    
//                    String baseUrl = (baseCell != null) ? baseCell.getStringCellValue().trim() : "";
//                    String endUrl  = (endCell != null) ? endCell.getStringCellValue().trim() : "";

//                    if (baseCell == null && endCell == null) {
//                        String baseUrl = baseCell.getStringCellValue().trim();
//                        String endUrl  = endCell.getStringCellValue().trim();
//                    
//                        if (baseUrl.isEmpty() || endUrl.isEmpty()) continue;
                    
                    String baseUrl = (baseCell != null) ? baseCell.getStringCellValue().trim() : "";
                    String endUrl  = (endCell != null) ? endCell.getStringCellValue().trim() : "";

                    if (endUrl.isEmpty()) continue; // skip if second column is empty
                    
                    if (!baseUrl.startsWith("http")) {
                        System.out.println("Skipping invalid URL/text: " + baseUrl + endUrl);
                        continue;
                    }
                        
                        fullUrls.add(baseUrl + endUrl);
                    }
                    
                
            

        } catch (Exception e) {
            throw new RuntimeException("Error reading URLs from Excel", e);
        }

        return fullUrls;
    }



public static List<Map<String, String>> extractQueryParamsFromExcel(String filePath) {
	List<Map<String, String>> allQueryParams = new ArrayList<>();

	try (FileInputStream file = new FileInputStream(new String(filePath));
			Workbook workbook = new XSSFWorkbook(file)) {

		Sheet sheet = workbook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);

		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row currentRow = sheet.getRow(rowIndex);
			if (currentRow == null)
				continue;

			Map<String, String> queryParams = new HashMap<>();

			for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
				Cell headerCell = headerRow.getCell(cellIndex);
				Cell valueCell = currentRow.getCell(cellIndex);

				if (headerCell == null || valueCell == null)
					continue;

				headerCell.setCellType(CellType.STRING);
				valueCell.setCellType(CellType.STRING);

//				String key = headerCell.getStringCellValue().replaceAll("^\"|\"$", "").trim();
//				String value = valueCell.getStringCellValue().replaceAll("^\"|\"$", "").trim();
				
				String key = headerCell.getStringCellValue().replace("\"", "").trim();
				String value = valueCell.getStringCellValue().replace("\"", "").trim();

				// ✅ Add to queryParams map
                queryParams.put(key, value);
				
			}

			if (!queryParams.isEmpty()) {
				allQueryParams.add(queryParams);
			}
		}

	} catch (IOException e) {
		e.printStackTrace();
	}

	return allQueryParams;
}}

