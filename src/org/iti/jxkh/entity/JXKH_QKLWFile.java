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
@Table(name = "JXKH_QKLWFile")
public class JXKH_QKLWFile implements java.io.Serializable {

	/**
	 * @author CuiXiaoxin
	 */
	private static final long serialVersionUID = 7544333480384071593L;

	private Long id;// 序号
	private String name;// 文件名
	private String path;// 路径
	private String date;// 提交日期
	private JXKH_QKLW meeting;// 所属期刊
	private String fileType;// 文件类型

	public JXKH_QKLWFile() {
		super();
	}

	public JXKH_QKLWFile(Long id, String name, String path, String date,
			JXKH_QKLW meeting, String fileType) {
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
	@JoinColumn(name = "lwId", insertable = true, updatable = true)
	public JXKH_QKLW getMeeting() {
		return meeting;
	}

	public void setMeeting(JXKH_QKLW meeting) {
		this.meeting = meeting;
	}

	@Column(length = 100)
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((fileType == null) ? 0 : fileType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((meeting == null) ? 0 : meeting.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JXKH_QKLWFile other = (JXKH_QKLWFile) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (fileType == null) {
			if (other.fileType != null)
				return false;
		} else if (!fileType.equals(other.fileType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (meeting == null) {
			if (other.meeting != null)
				return false;
		} else if (!meeting.equals(other.meeting))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JXKH_QKLWFile [id=" + id + ", name=" + name + ", path=" + path
				+ ", date=" + date + ", meeting=" + meeting + ", fileType="
				+ fileType + "]";
	}

}
