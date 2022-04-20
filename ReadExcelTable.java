package in.sts.excelutility.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import in.sts.excelutility.model.MarksModel;
import in.sts.excelutility.model.StudentModel;

public class ReadExcelTable {
	final Logger log = Logger.getLogger(ReadExcelTable.class);

	public HashSet<StudentModel> readExcel(File file) {

		Scanner fileInput = null;
		ArrayList<StudentModel> arrayList = new ArrayList<StudentModel>();
		HashSet<StudentModel> uniqueSet = null;
		try {
			FileInputStream inputStream = new FileInputStream(file);

			Workbook workBook = new XSSFWorkbook(inputStream);
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Sheet> sheet1 = workBook.sheetIterator();

			while (sheet1.hasNext()) {
				Sheet sh = sheet1.next();
				Iterator<Row> iterator = sh.iterator();
				while (iterator.hasNext()) {
					Row row = iterator.next();
					Iterator<Cell> cellIterator = row.iterator();

					StudentModel studentModel = new StudentModel();
					MarksModel marksModel = new MarksModel();

					int count = 1;

					String firstName = null;
					String middleName = null;
					String lastName = null;
					String branch = null;
					String mathsMarks = null;
					String englishMarks = null;
					String scienceMarks = null;

					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (count) {
						case 1:
							firstName = dataFormatter.formatCellValue(cell);
							studentModel.setFirstName(firstName);
							count++;
							break;

						case 2:
							middleName = dataFormatter.formatCellValue(cell);
							studentModel.setMiddleName(middleName);
							count++;
							break;

						case 3:
							lastName = dataFormatter.formatCellValue(cell);
							studentModel.setLastName(lastName);
							count++;
							break;

						case 4:
							branch = dataFormatter.formatCellValue(cell);
							studentModel.setBranch(branch);
							count++;
							break;

						case 5:
							mathsMarks = dataFormatter.formatCellValue(cell);
							marksModel.setMaths(Integer.parseInt(mathsMarks));
							count++;
							break;

						case 6:
							englishMarks = dataFormatter.formatCellValue(cell);
							marksModel.setEnglish(Integer.parseInt(englishMarks));
							count++;
							break;

						case 7:
							scienceMarks = dataFormatter.formatCellValue(cell);
							marksModel.setScience(Integer.parseInt(scienceMarks));
							count++;
							break;
						}

					}
					studentModel.setMarksModel(marksModel);
					arrayList.add(studentModel);
				}

			}
			uniqueSet = new HashSet<StudentModel>(arrayList);

			workBook.close();

		} catch (FileNotFoundException exception) {
			System.out.println("Please enter the correct file name..!");

			readExcel(file);

		} catch (IOException exception) {
			System.out.println("Data Not Found");
			System.out.println("Message = " + exception.getMessage());
		} catch (Exception exception) {
			System.out.println("Message = " + exception.getMessage());
		} finally {
			if (fileInput != null)
				fileInput.close();
		}
		return uniqueSet;

	}
}