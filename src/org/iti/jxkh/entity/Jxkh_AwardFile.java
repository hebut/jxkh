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

import com.iti.common.util.EntityUtil;


@Entity
@Table(name = "Jxkh_AwardFile")
public class Jxkh_AwardFile implements java.io.Serializable {

	private static final long serialVersionUID = -6893202745565051511L;
	
	private Long id;
	
	private String name;//文件名
	
	private String path;//文件路径
	
	private String date;//上传日期
	
	private String belongType;//文件类型
	
	private Jxkh_Award award;//所属奖励

	/** default constructor */
	public Jxkh_AwardFile() {
		
	}
	/** full constructor */
	public Jxkh_AwardFile(Long id, String name, String path, String date,
			String belongType,Jxkh_Award award) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.date = date;
		this.belongType = belongType;
		this.award=award;
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
	@JoinColumn(name = "AWARD", nullable = true, insertable = true, updatable = true)
	public Jxkh_Award getAward() {
		return award;
	}
	public void setAward(Jxkh_Award award) {
		this.award = award;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jxkh_AwardFile other = (Jxkh_AwardFile) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String EntityGlobeId() {
		return EntityUtil.buildEntityGlobeId(Jxkh_AwardFile.class, this.id);
	}	
	

}
