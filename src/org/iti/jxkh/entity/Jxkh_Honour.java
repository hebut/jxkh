package org.iti.jxkh.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

/**
 * GhRych entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JXKH_Hounour")
public class Jxkh_Honour implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1508448974459561538L;

	public static final short NONE=0,DEPART_PASS=1,DEPART_OUT=2,BUSI_PASS=3,BUSI_OUT=4,RECORD_YES=5;
	// Fields

	private Long ghId;
	private Long kuId;
	private String ryYear;
	private String ryName;
	private String ryDep;
	WkTUser user;
	private Set<Jxkh_HonourFile> file = new HashSet<Jxkh_HonourFile>();
	private short state;
	private String advice;
	private String recordId;
	// Constructors

	/** default constructor */
	public Jxkh_Honour() {
	}

	/** minimal constructor */
	public Jxkh_Honour(Long ghId, Long kuId) {
		this.ghId = ghId;
		this.kuId = kuId;
	}

	/** full constructor */
	public Jxkh_Honour(Long ghId, Long kuId, String ryYear, String ryName,
			String ryDep, WkTUser user, Set<Jxkh_HonourFile> file, short state,
			String advice, String recordId) {
		super();
		this.ghId = ghId;
		this.kuId = kuId;
		this.ryYear = ryYear;
		this.ryName = ryName;
		this.ryDep = ryDep;
		this.user = user;
		this.file = file;
		this.state = state;
		this.advice = advice;
		this.recordId = recordId;
	}

	// Property accessors
	@Transient
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getGhId() {
		return this.ghId;
	}

	public void setGhId(Long ghId) {
		this.ghId = ghId;
	}
	@Column
	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	@Column(length = 10)
	public String getRyYear() {
		return this.ryYear;
	}

	public void setRyYear(String ryYear) {
		this.ryYear = ryYear;
	}
	@Column(length = 400)
	public String getRyName() {
		return this.ryName;
	}

	public void setRyName(String ryName) {
		this.ryName = ryName;
	}
	
	@Column(length = 50)
	public String getRyDep() {
		return this.ryDep;
	}

	public void setRyDep(String ryDep) {
		this.ryDep = ryDep;
	}	
	@OneToMany(targetEntity=Jxkh_HonourFile.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="honour")
	public Set<Jxkh_HonourFile> getFile() {
		return file;
	}

	public void setFile(Set<Jxkh_HonourFile> file) {
		this.file = file;
	}
	@Column(length = 10)
	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}		
	
	@Column(length = 100)
	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}
	
	
	@Column(length = 100)
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ghId == null) ? 0 : ghId.hashCode());
		return result;
	}		

	@Override
	public String toString() {
		return "Jxkh_Honour [ghId=" + ghId + ", kuId=" + kuId + ", ryYear="
				+ ryYear + ", ryName=" + ryName + ", ryDep=" + ryDep
				+ ", user=" + user + ", file=" + file + ", state=" + state
				+ ", advice=" + advice + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jxkh_Honour other = (Jxkh_Honour) obj;
		if (ghId == null) {
			if (other.ghId != null)
				return false;
		} else if (!ghId.equals(other.ghId))
			return false;
		return true;
	}

	/*@Override
	public String EntityGlobeId() {
		return EntityUtil.buildEntityGlobeId(GhRych.class, this.ghId);
	}*/
}