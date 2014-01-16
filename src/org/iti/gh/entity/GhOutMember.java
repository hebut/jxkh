package org.iti.gh.entity;


public class GhOutMember implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long mId;
	private String outMemberName;
	private String dept;
	private String phone;
	private String address;
	private Long kuId;
	

	

	
	public Long getmId() {
		return mId;
	}
	public void setmId(Long mId) {
		this.mId = mId;
	}
	public String getOutMemberName() {
		return outMemberName;
	}
	public void setOutMemberName(String outMemberName) {
		this.outMemberName = outMemberName;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getKuId() {
		return kuId;
	}
	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	
}
