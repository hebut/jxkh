package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Jxkh_DataFile")
public class Jxkh_DataFile implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6269044667651949366L;
	
	public static final Integer FIRST=0,HIGHT = 1,TRAIN=2,EMP=3,TITLE=4;//第一学历的附件：0，最高学历的附件：1，培训附件：2，聘用附件：3，职称附件：4
	
	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 用户id或者附件主体id
	 */
	@Column(length = 50)
	private Long userId;
	/**
	 * 文档类型
	 */
	@Column
	private Integer fileType;
	/**
	 * 文件名
	 */
	@Column(length = 500)
	private String fileName;
	/**
	 * 文件路径
	 */
	@Column(length = 500)
	private String filePath;
	/**
	 * 上传日期
	 */
	@Column(length = 20)
	private String upTime;
	
	public Jxkh_DataFile() {
		super();
	}
	public Jxkh_DataFile(Long id, Long userId, Integer fileType, String fileName, String filePath, String upTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.fileType = fileType;
		this.fileName = fileName;
		this.filePath = filePath;
		this.upTime = upTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getFileType() {
		return fileType;
	}
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUpTime() {
		return upTime;
	}
	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}
}
