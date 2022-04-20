package in.sts.excelutility.main;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import in.sts.excelutility.dao.StudentDao;
import in.sts.excelutility.dao.StudentDaoInterface;
import in.sts.excelutility.files.ReadExcelTable;
import in.sts.excelutility.files.ReadTextTable;
import in.sts.excelutility.files.ReadXMLFile;
import in.sts.excelutility.model.StudentModel;

public class MainClass {
	
	
	final static Logger log = Logger.getLogger(MainClass.class);
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Scanner scanner =null;
		HashSet<StudentModel> uniqueSet = new HashSet<StudentModel>();
		ReadTextTable readTextTable = new ReadTextTable();
		ReadExcelTable readExcelTable = new ReadExcelTable();
		ReadXMLFile readeXmlFile = new ReadXMLFile();
		StudentDaoInterface studentDao = new StudentDao();

		int count = 0;
		System.out.println("Keep your files in folder: " + "C:\\Users\\kalyani.patil\\eclipse-workspace\\ExcelUtility\\");
		String inputPath = "C:\\Users\\kalyani.patil\\eclipse-workspace\\ExcelUtility\\Files";
		File fileInput[] = new File(inputPath).listFiles();

		for (File file : fileInput) {
			if (file.getName().endsWith(".txt")) {
				log.info("\n" + file.getName() + " Text file operation: ");
				uniqueSet = readTextTable.readTable(file);
			} else if (file.getName().endsWith(".xlsx")) {
				log.info("\n" + file.getName() + " Excel file operation: ");
				uniqueSet = readExcelTable.readExcel(file);
			} else if (file.getName().endsWith(".xml")) {
				log.info("\n" + file.getName() + "XMl file operation: ");
				uniqueSet = readeXmlFile.readXML(file);
			}
			// if file data is empty then no insert operation. uniqueSet.isEmpty()-->no new
			if (!uniqueSet.isEmpty()) {
				for (StudentModel inputFileData : uniqueSet) {
					StudentModel dataBaseData = studentDao.retriveDataFromDatabase(inputFileData.getFirstName());
					if (dataBaseData.getStudent_id() != 0) {
						if (studentDao.check(dataBaseData, inputFileData) == true) {
							studentDao.update(inputFileData, dataBaseData.getStudent_id()); // update one row
							count++;
						}
					} else {
						studentDao.insert(inputFileData);
						count++;
						log.info("Inserted row: " + inputFileData.getFirstName() + "||" + inputFileData.getLastName());
					}
				}
			} else {
				log.info("No data inserted for: " + file.getName());
			}
			if (count == 0) {
				log.info("No operation");
			}
			uniqueSet = new HashSet<StudentModel>();
		}

		// search highest marks data
		scanner = new Scanner(System.in);
		System.out.println("\n" + "To know highest marks in particular subject, ");
		System.out.println("Enter the subject name: ");
		String subjectName = scanner.nextLine();
		studentDao.getHighestMarksInSubject(subjectName);
		
		scanner.close();
	}

}
