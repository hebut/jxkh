package org.iti.bysj.entity;

public class BsUserDudao implements java.io.Serializable{

	// Fields

	private BsUserDudaoId id;

	
	public  BsUserDudao(){
		
	}
	
	
	public BsUserDudao(BsUserDudaoId id) {
		super();
		this.id = id;
	}


	public BsUserDudaoId getId() {
		return id;
	}


	public void setId(BsUserDudaoId id) {
		this.id = id;
	}
	
	
}
