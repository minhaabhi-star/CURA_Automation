
package Utlities;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {

    public static Object[][] readExcel(
            String fileName, String sheetName) throws IOException {

        String resourcePath = "/" + fileName;

        try (InputStream input =
                     ExcelReader.class.getResourceAsStream(resourcePath)) {

            if (input == null) {
                throw new IOException(
                    "Excel file not found: " + fileName
                    + ". Place it inside src/test/resources.");
            }

            try (Workbook workbook = WorkbookFactory.create(input)) {

                Sheet sheet = workbook.getSheet(sheetName);

                if (sheet == null) {
                    throw new IOException(
                        "Sheet not found: " + sheetName);
                }

                DataFormatter formatter = new DataFormatter();
                List<Object[]> data = new ArrayList<>();

                // Row 0 contains column headings.
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                    Row row = sheet.getRow(i);

                    if (row == null) {
                        continue;
                    }

                    String username = formatter.formatCellValue(
                        row.getCell(0)).trim();

                    String password = formatter.formatCellValue(
                        row.getCell(1)).trim();

                    String expected = formatter.formatCellValue(
                        row.getCell(2)).trim();

                    // Ignore completely empty rows.
                    if (username.isEmpty()
                            && password.isEmpty()
                            && expected.isEmpty()) {
                        continue;
                    }

                    data.add(new Object[] {
                        username, password, expected
                    });
                }

                return data.toArray(new Object[0][]);
            }
        }
    }
}
