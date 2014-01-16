package org.iti.gh.entity;
/**
 * GhCg entity. @author MyEclipse Persistence Tools
 */

public class GH_ARCHIVE {
	private long arId;
	private String arName;
	private String arKeyWord;
	private String arAuthor;
	private String arSource;
	private Integer arSourceType;
	private String arFundNum;
	private String arPostDate;
	private long arProId;
	private String arPath;
	private long arUpUserId;
	private String arThema;
	private Integer arType;
	private String arReadFeel;
	private String arUpTime;
	private String arCLC;
	private String arIssue;
	private short arCategory;
	
	/*
	 * default constructor
	 */
	public GH_ARCHIVE(){
		
	}
	/*
	 * full constructor
	 */
	public GH_ARCHIVE(long arId,String arName,String arKeyWord,String arAuthor,String arSource,
			Integer arSourceType,String arFundNum,String arPostDate,long arProId,String arPath,
			long arUpUserId,String arThema,Integer arType,String arReadFeel,String arUpTime,
			String arCLC,String arIssue,short arCategory){
		this.arId=arId;
		this.arName=arName;
		this.arKeyWord=arKeyWord;
		this.arAuthor = arAuthor;
		this.arSource=arSource;
		this.arSourceType=arSourceType;
		this.arFundNum=arFundNum;
		this.arPostDate=arPostDate;
		this.arProId=arProId;
		this.arPath=arPath;
		this.arUpUserId=arUpUserId;
		this.arThema=arThema;
		this.arType = arType;
		this.arReadFeel=arReadFeel;
		this.arUpTime = arUpTime;
		this.arCLC = arCLC;
		this.arIssue = arIssue;
		this.arCategory = arCategory;
	}
	public Integer getArType() {
		return arType;
	}
	public void setArType(Integer arType) {
		this.arType = arType;
	}
	public String getArReadFeel() {
		return arReadFeel;
	}
	public void setArReadFeel(String arReadFeel) {
		this.arReadFeel = arReadFeel;
	}
	public long getArId() {
		return arId;
	}
	public void setArId(long arId) {
		this.arId = arId;
	}
	public String getArName() {
		return arName;
	}
	public void setArName(String arName) {
		this.arName = arName;
	}
	public String getArKeyWord() {
		return arKeyWord;
	}
	public void setArKeyWord(String arKeyWord) {
		this.arKeyWord = arKeyWord;
	}
	public String getArAuthor() {
		return arAuthor;
	}
	public void setArAuthor(String arAuthor) {
		this.arAuthor = arAuthor;
	}
	public String getArSource() {
		return arSource;
	}
	public void setArSource(String arSource) {
		this.arSource = arSource;
	}
	public Integer getArSourceType() {
		return arSourceType;
	}
	public void setArSourceType(Integer arSourceType) {
		this.arSourceType = arSourceType;
	}
	public String getArFundNum() {
		return arFundNum;
	}
	public void setArFundNum(String arFundNum) {
		this.arFundNum = arFundNum;
	}
	public String getArPostDate() {
		return arPostDate;
	}
	public void setArPostDate(String arPostDate) {
		this.arPostDate = arPostDate;
	}
	public long getArProId() {
		return arProId;
	}
	public void setArProId(long arProId) {
		this.arProId = arProId;
	}
	public String getArPath() {
		return arPath;
	}
	public void setArPath(String arPath) {
		this.arPath = arPath;
	}
	public long getArUpUserId() {
		return arUpUserId;
	}
	public void setArUpUserId(long arUpUserId) {
		this.arUpUserId = arUpUserId;
	}
	public String getArThema() {
		return arThema;
	}
	public void setArThema(String arThema) {
		this.arThema = arThema;
	}
	public String getArUpTime() {
		return arUpTime;
	}
	public void setArUpTime(String arUpTime) {
		this.arUpTime = arUpTime;
	}
	public String getArCLC() {
		return arCLC;
	}
	public void setArCLC(String arCLC) {
		this.arCLC = arCLC;
	}
	public String getArIssue() {
		return arIssue;
	}
	public void setArIssue(String arIssue) {
		this.arIssue = arIssue;
	}
	public short getArCategory() {
		return arCategory;
	}
	public void setArCategory(short arCategory) {
		this.arCategory = arCategory;
	}
}
