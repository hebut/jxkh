package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTDocrmk entity. @author MyEclipse Persistence Tools
 */

public class WkTInfoscore implements java.io.Serializable {

	// Fields

	private String kiType;
	private Integer kiScore;


	// Constructors

	/** default constructor */
	public WkTInfoscore() {
	}


	/** full constructor */
	public WkTInfoscore(String kiType,Integer kiScore) {
		this.kiType = kiType;
		this.kiScore = kiScore;
	}

	// Property accessors



	public String getKiType() {
		return this.kiType;
	}

	public void setKiType(String kiType) {
		this.kiType = kiType;
	}

	public Integer getKiScore() {
		return this.kiScore;
	}

	public void setKiScore(Integer kiScore) {
		this.kiScore = kiScore;
	}


}