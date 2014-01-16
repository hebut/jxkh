package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iti.common.interfaces.EntityCommonInterface;
import com.iti.common.util.EntityUtil;

@Entity
@Table(name = "Jxkh_HonourFile")
public class Jxkh_HonourFile implements EntityCommonInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	private Long fileId;
	private String fileName;
	private String filePath;
	private String fileDate;
	private Jxkh_Honour honour;
	// Constructors
	/** default constructor */
	public Jxkh_HonourFile() {
		
	}
	/** full constructor */
	public Jxkh_HonourFile(Long fileId, String fileName, String filePath,
			String fileDate, Jxkh_Honour honour) {
		super();
		this.fileId = fileId;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileDate = fileDate;
		this.honour = honour;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getFileId() {
		return fileId;
	}
	
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	@Column(length = 500)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length = 800)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length = 50)	
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	@ManyToOne
	@JoinColumn(name="ghId")
	public Jxkh_Honour getHonour() {
		return honour;
	}
	public void setHonour(Jxkh_Honour honour) {
		this.honour = honour;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		return result;
	}
	
	
	
	@Override
	public String toString() {
		return "RychFile [fileId=" + fileId + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", honour=" + honour + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jxkh_HonourFile other = (Jxkh_HonourFile) obj;
		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		return true;
	}
	@Override
	public String EntityGlobeId() {
		return EntityUtil.buildEntityGlobeId(Jxkh_HonourFile.class, this.fileId);
	}	
}
