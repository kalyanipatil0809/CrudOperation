package in.sts.excelutility.dao;

import in.sts.excelutility.model.StudentModel;

public interface StudentDaoInterface {

	public StudentModel retriveDataFromDatabase(String firstName);
	
	public boolean check(StudentModel dataFromDatabase, StudentModel studentModel);
	
	public void insert(StudentModel fileData) ;
	
	public void update(StudentModel studentModel, int student_id) ;
	
	public void getHighestMarksInSubject(String subjectName);
}
