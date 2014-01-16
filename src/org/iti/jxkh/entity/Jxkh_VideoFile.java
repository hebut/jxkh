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
@Table(name = "Jxkh_VideoFile")
public class Jxkh_VideoFile implements java.io.Serializable{

	private static final long serialVersionUID = 109850648565852846L;

	private Long id;
	
	private String name;//文件名
	
	private String path;//文件路径
	
	private String date;//上传日期
	
	private String belongType;//文件类型
	
	private Jxkh_Video video;//所属影视

	/** default constructor */
	public Jxkh_VideoFile() {
		
	}
	/** full constructor */
	public Jxkh_VideoFile(Long id, String name, String path, String date,
			String belongType,Jxkh_Video video) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.date = date;
		this.belongType = belongType;
		this.video=video;
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
	
	@Column(length = 50)
	public String getBelongType() {
		return belongType;
	}
	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VIDEO", nullable = true, insertable = true, updatable = true)
	public Jxkh_Video getVideo() {
		return video;
	}
	public void setVideo(Jxkh_Video video) {
		this.video = video;
	}
	
}
