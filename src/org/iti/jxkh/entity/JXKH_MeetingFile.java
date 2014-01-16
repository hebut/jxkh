package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JXKH_MeetingFile")

public class JXKH_MeetingFile implements java.io.Serializable{
	
	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = 7239284962047658356L;
	private Long id;//序号
	private String name;//文件名
	private String path;//路径
	private String date;//提交日期
	private JXKH_MEETING meeting;//所属会议
	private String fileType;//文件类型
	
	public JXKH_MeetingFile() {
		super();
	}

	public JXKH_MeetingFile(Long id, String name, String path, String date,
			JXKH_MEETING meeting, String fileType) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.date = date;
		this.meeting = meeting;
		this.fileType = fileType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 500)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 500)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(length = 20)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mtId",  insertable = true, updatable = true)
	public JXKH_MEETING getMeeting() {
		return meeting;
	}

	public void setMeeting(JXKH_MEETING meeting) {
		this.meeting = meeting;
	}

	@Column(length = 100)
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
