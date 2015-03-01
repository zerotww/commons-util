import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.Test;

public class WordTest {

	public static void main(String[] args) {
		File file = new File("D:/test.doc");
		XWPFDocument document = new XWPFDocument();
		XWPFTable table = document.createTable(2, 6);
		for (int i = 0; i < 2; i++) {
			XWPFTableRow row = table.getRow(i);
			for (int j = 0; j < 6; j++) {
				XWPFTableCell cell = row.getCell(j);
				cell.setText(i + "*" + j + "=" + i * j);
			}
		}
		XWPFTableRow row = table.getRow(0);// 默认是有一行的
		row.getCell(0).setText("Hello");// 默认是有一个单元格的
		row.addNewTableCell().setText("你好!");
		XWPFTableRow row2 = table.createRow();// 第二行，因为第一行有两个单元格了所以第二行不用创建单元格默认有二个单元格了
		row2.getCell(0).setText("How are you!");
		row2.getCell(1).setText("你妹！");
		try {
			document.write(FileUtils.openOutputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testName() throws Exception {
		File file = new File("D:/test.docx");
		XWPFDocument document = new XWPFDocument();
		XWPFTable table = document.createTable(2, 6);
		for (int i = 0; i < 2; i++) {
			XWPFTableRow row = table.getRow(i);
			for (int j = 0; j < 6; j++) {
				XWPFTableCell cell = row.getCell(j);
				cell.setText(i + "*" + j + "=" + i * j);
			}
		}
		try {
			document.write(FileUtils.openOutputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
