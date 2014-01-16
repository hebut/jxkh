package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;


@Entity
@Table(name = "JXKH_PerTrans")
public class JXKH_PerTrans implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	private Long ptId;
	private Long kuId;
	private String ptDate;
	private Long oldKdId;
	private Long newKdId;
	private String oldPost;
	private String newPost;
	private String reason;
	
	WkTUser user;
	WkTDept oldDept;
	WkTDept newDept;
	// Constructors
	/** default constructor */
	public JXKH_PerTrans() {
		
	}
	/** full constructor */
	public JXKH_PerTrans(Long ptId, Long kuId, String ptDate, Long oldKdId,
			Long newKdId, String oldPost, String newPost, String reason) {
		super();
		this.ptId = ptId;
		this.kuId = kuId;
		this.ptDate = ptDate;
		this.oldKdId = oldKdId;
		this.newKdId = newKdId;
		this.oldPost = oldPost;
		this.newPost = newPost;
		this.reason = reason;		
	}
	
	/**
	 * 通过kuid获得该用户
	 * @return
	 */
	@Transient
	public WkTUser getUser() {
		if(user==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			this.user=(WkTUser)userService.get(WkTUser.class, kuId);
		}
		return user;
	}
	
	/**
	 * 通过oldKdId获得该部门
	 * @return
	 */
	@Transient
	public WkTDept getOldDept() {
		if(oldDept == null) {
			DepartmentService deptService = (DepartmentService) BeanFactory.getBean("departmentService");
			oldDept = (WkTDept) deptService.get(WkTDept.class, oldKdId);
		}
		return oldDept;
	}
	/**
	 * 通过newKdId获得该部门
	 * @return
	 */
	@Transient
	public WkTDept getNewDept() {
		if(newDept == null) {
			DepartmentService deptService = (DepartmentService) BeanFactory.getBean("departmentService");
			newDept = (WkTDept) deptService.get(WkTDept.class, newKdId);
		}
		return newDept;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getPtId() {
		return ptId;
	}	
	
	public void setPtId(Long ptId) {
		this.ptId = ptId;
	}
	@Column
	public Long getKuId() {
		return kuId;
	}
	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}
	@Column(length = 20)
	public String getPtDate() {
		return ptDate;
	}	
	public void setPtDate(String ptDate) {
		this.ptDate = ptDate;
	}
	@Column(name = "oldKdId")
	public Long getOldKdId() {
		return oldKdId;
	}
	public void setOldKdId(Long oldKdId) {
		this.oldKdId = oldKdId;
	}
	@Column(name = "newKdId")
	public Long getNewKdId() {
		return newKdId;
	}
	public void setNewKdId(Long newKdId) {
		this.newKdId = newKdId;
	}
	@Column(length = 30)
	public String getOldPost() {
		return oldPost;
	}	
	public void setOldPost(String oldPost) {
		this.oldPost = oldPost;
	}
	@Column(length = 30)
	public String getNewPost() {
		return newPost;
	}
	public void setNewPost(String newPost) {
		this.newPost = newPost;
	}	
	@Column(length = 100)
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ptId == null) ? 0 : ptId.hashCode());
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
		JXKH_PerTrans other = (JXKH_PerTrans) obj;
		if (ptId == null) {
			if (other.ptId != null)
				return false;
		} else if (!ptId.equals(other.ptId))
			return false;
		return true;
	}		
	@Override
	public String toString() {
		return "JXKH_PerTrans [ptId=" + ptId + ", kuId=" + kuId + ", ptDate="
				+ ptDate + ", oldKdId=" + oldKdId + ", newKdId=" + newKdId
				+ ", oldPost=" + oldPost + ", newPost=" + newPost + ", reason="
				+ reason + ", user=" + user + ", oldDept=" + oldDept
				+ ", newDept=" + newDept + "]";
	}
	/*@Override
	public String EntityGlobeId() {
		return EntityUtil.buildEntityGlobeId(JXKH_PerTrans.class, this.ptId);
	}*/
}
