package in.sts.excelutility.dao;

import in.sts.excelutility.model.StudentModel;

public interface IstudentDao {

	public StudentModel retriveDataFromDatabase(String firstName, String middleName, String lastName);
	
	public boolean checkDBdataAndStudentmodelData(StudentModel dataFromDatabase, StudentModel studentModel);
	
	public void insert(StudentModel fileData) ;
	
	public void update(StudentModel studentModel, int student_id) ;
	
	public void getHighestMarksInSubject(String subjectName);

	public void delete(String firstName, String middleName, String lastName);
}
