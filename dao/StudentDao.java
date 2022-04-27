package in.sts.excelutility.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import in.sts.excelutility.model.MarksModel;
import in.sts.excelutility.model.StudentModel;
import in.sts.excelutility.mysqlconnection.DBConnection;

public class StudentDao implements IstudentDao {
	final Logger log = Logger.getLogger(StudentDao.class);

	final int FIRST_NAME = 1;
	final int MIDDLE_NAME = 2;
	final int LAST_NAME = 3;
	final int BRANCH = 4;
	final int MATHS = 5;
	final int ENGLISH = 6;
	final int SCIENCE = 7;
	final int PRESENT_FIRST_NAME = 1;
	final int PRESENT_MIDDLE_NAME = 2;
	final int PRESENT_LAST_NAME = 3;
	final int UPDATE_BRANCH =1;
	final int UPDATE_MATHS =2;
	final int UPDATE_ENGLISH =3;
	final int UPDATE_SCIENCE =4;
	final int STUDENT_ID = 5;
	final int DELETE_STUDENT_ID=1;
	PreparedStatement preparedStatement = null;
	Connection connection = null;
	/**
	 * retrive one data from database
	 */
	public StudentModel retriveDataFromDatabase(String firstName,String middleName,String lastName) {
		StudentModel studentModel = new StudentModel();
		try {
			connection = DBConnection.connect();
			ResultSet resultSet = null;
			String sql = ("select * from StudentDetails where firstName=? and middleName=? and lastName=?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(PRESENT_FIRST_NAME, firstName);
			preparedStatement.setString(PRESENT_MIDDLE_NAME, middleName);
			preparedStatement.setString(PRESENT_LAST_NAME, lastName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				/**
				 *  setting DB data into studentModel
				 */
				studentModel = new StudentModel();
				studentModel.setFirstName(resultSet.getString("FirstName"));
				studentModel.setMiddleName(resultSet.getString("MiddleName"));
				studentModel.setLastName(resultSet.getString("LastName"));
				studentModel.setBranch(resultSet.getString("branch"));
				studentModel.setStudent_id(resultSet.getInt("student_id"));
				MarksModel marksModel = new MarksModel();
				marksModel.setMaths(Integer.valueOf(resultSet.getString("Maths")));
				marksModel.setScience(Integer.valueOf(resultSet.getString("Science")));
				marksModel.setEnglish(Integer.valueOf(resultSet.getString("English")));
				studentModel.setMarksModel(marksModel);
				return studentModel;
			}
		} catch (SQLException exception) {
			log.error("Message = " + exception.getMessage());
		}finally {
			 if (connection != null) {
			        try {
			        	connection.close();
			        } catch (SQLException exception) { 
			        	log.error("Message = " +exception.getMessage());
			        }
			    }
		}
		studentModel = new StudentModel();
		return studentModel;
	}

	/**
	 * checking common data of fileData and databaseData 
	 */
	public boolean checkCommonData(StudentModel dataFromDatabase, StudentModel studentModel) {
		if (dataFromDatabase.getMarksModel().getMaths() != studentModel.getMarksModel().getMaths()
				|| dataFromDatabase.getMarksModel().getEnglish() != studentModel.getMarksModel().getEnglish()
				|| dataFromDatabase.getMarksModel().getScience() != studentModel.getMarksModel().getScience()
				|| !dataFromDatabase.getBranch().equals(studentModel.getBranch())) {
			return true;
		}
		return false;
	}

	public void insert(StudentModel studentModel) {
		try {
			connection = DBConnection.connect();
			String insertQuery = "Insert into StudentDetails values(student_id,?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(insertQuery);
			if (preparedStatement != null && studentModel != null) {
				preparedStatement.setString(FIRST_NAME, studentModel.getFirstName());
				preparedStatement.setString(MIDDLE_NAME, studentModel.getMiddleName());
				preparedStatement.setString(LAST_NAME, studentModel.getLastName());
				preparedStatement.setString(BRANCH, studentModel.getBranch());
				preparedStatement.setInt(MATHS, studentModel.getMarksModel().getMaths());
				preparedStatement.setInt(ENGLISH, studentModel.getMarksModel().getEnglish());
				preparedStatement.setInt(SCIENCE, studentModel.getMarksModel().getScience());
				preparedStatement.executeUpdate();
			} else {
				log.info("No record inserted...!");
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException sqlException) {
					log.error("Message = " + sqlException.getMessage());
				} if (connection != null) {
			        try {
			        	connection.close();
			        } catch (SQLException exception) { 
			        	log.error("Message = " +exception.getMessage());
			        }
			    }
			}
		}
	}
	public void update(StudentModel studentModel, int student_id) {
		try{
			connection = DBConnection.connect();
			MarksModel marks = studentModel.getMarksModel();
			String updateQuery = "update StudentDetails set branch=?, maths=? ,english=? ,science=?  where student_id = ?";
			preparedStatement = connection.prepareStatement(updateQuery);

			preparedStatement.setString(UPDATE_BRANCH, studentModel.getBranch());
			preparedStatement.setInt(UPDATE_MATHS, marks.getMaths());
			preparedStatement.setInt(UPDATE_ENGLISH, marks.getEnglish());
			preparedStatement.setInt(UPDATE_SCIENCE, marks.getScience());
			preparedStatement.setInt(STUDENT_ID, student_id);

			preparedStatement.executeUpdate();
			System.out.println("Updated row: " + studentModel.getFirstName() + "||"+ studentModel.getMiddleName() + "||" + studentModel.getLastName());

		} catch (SQLException sqlException) {
			log.error("Message = " + sqlException.getMessage());
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException sqlException) {
					log.error("Message = " + sqlException.getMessage());
				} if (connection != null) {
			        try {
			        	connection.close();
			        } catch (SQLException exception) { 
			        	log.error("Message = " +exception.getMessage());
			        }
			    }
			}
		}
	}

	/**
	 * user defined parameters is firstName,middleName,lastName.
	 * fetching ID from retriveDataFromDatabase method. 
	 */
	public void delete(String firstName, String middleName, String lastName) {
		try {
			connection = DBConnection.connect();
			StudentModel studentModel = retriveDataFromDatabase(firstName, middleName, lastName);

			String deleteQuery = "delete from studentDetails where student_id=?";
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(DELETE_STUDENT_ID, studentModel.getStudent_id());

			if (preparedStatement.executeUpdate() > 0)
				System.out.println("Record deleted successfully.");
			else
				System.out.println("Record not found.");

		} catch (SQLException exception) {
			log.error("Message = " +exception.getMessage());
		}finally {
			 if (connection != null) {
			        try {
			        	connection.close();
			        } catch (SQLException exception) { 
			        	log.error("Message = " +exception.getMessage());
			        }
			    }
		}
	}
	public void getHighestMarksInSubject(String subjectName) {
		Scanner subject = null;
		try {   
			connection = DBConnection.connect();
			String fetchQuery = "select firstName,branch,"+subjectName+" from studentdetails where "+subjectName+" in (select max("+subjectName+") from studentdetails)";
			preparedStatement = connection.prepareStatement(fetchQuery);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(
						resultSet.getString("firstName") + "--- " + resultSet.getString("branch") + "---" + resultSet.getInt(subjectName));
			}
			System.out.println("Data searched successfully");

		} catch (SQLException sqlException) {
			System.out.println("please enter the currect subject name:");
			subject = new Scanner(System.in);
			String subName = subject.nextLine();
			getHighestMarksInSubject(subName);
		} finally {
			if(subject !=null) 
				subject.close();
		}if (connection != null) {
	        try {
	        	connection.close();
	        } catch (SQLException exception) { 
	        	log.error("Message = " +exception.getMessage());
	        }
	    }
	}

}
