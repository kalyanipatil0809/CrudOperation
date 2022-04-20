package in.sts.excelutility.model;

public class MarksModel {
	int maths;
	int english;
	int science;

	public int getMaths() {
		return maths;
	}

	public void setMaths(int maths) {
		this.maths = maths;
	}

	public int getEnglish() {
		return english;
	}

	public void setEnglish(int english) {
		this.english = english;
	}

	public int getScience() {
		return science;
	}

	public void setScience(int science) {
		this.science = science;
	}
	@Override
	public String toString() {
		return "[maths=" + maths + ", english=" + english + ", science=" + science + "]";
	}

}
