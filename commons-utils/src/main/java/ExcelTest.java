import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelTest {
	public static void main(String[] args) {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Holy Sheet");
		Row headRow = sheet.createRow(0);
		Cell cell = headRow.createCell(0);
		cell.setCellValue("Test");
	}
}
