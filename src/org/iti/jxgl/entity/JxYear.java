package org.iti.jxgl.entity;

public class JxYear implements java.io.Serializable {
	private Long id;
	private String years;
	private String yearsname;
	private String grade;
	public JxYear(Long id, String years, String yearsname,String grade) {
		super();
		this.id = id;
		this.years = years;
		this.yearsname = yearsname;
		this.grade = grade;
	}
	public String getYearsname() {
		return yearsname;
	}
	public void setYearsname(String yearsname) {
		this.yearsname = yearsname;
	}
	public String getgrade() {
		return grade;
	}
	public void setgrade(String grade) {
		this.grade = grade;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	
	public JxYear(Long id) {
		super();
		this.id = id;
	}
	public JxYear() {
		super();
	}
	

}
