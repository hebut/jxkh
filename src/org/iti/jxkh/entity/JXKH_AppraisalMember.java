package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

@Entity
@Table(name = "JXKH_AppraisalMember")
public class JXKH_AppraisalMember implements java.io.Serializable{
	/**
	 * @CXX ，WXY
	 */
	private static final long serialVersionUID = 7709133460990617462L;
	private Long id;
	private String personId;//成员编号
	private String name;//成员姓名
	private String dept;//成员所属部门
	private Long deptId;//部门ID
	private Integer kuType;//成员类型
	private String year;//年份
	private Long KuId;//成员号
	private WkTUser user;
	
	public JXKH_AppraisalMember() {
		super();
	}

	public JXKH_AppraisalMember(Long id, String personId, String name,
			String dept, Long deptId, Integer kuType, String year, Long kuId) {
		super();
		this.id = id;
		this.personId = personId;
		this.name = name;
		this.dept = dept;
		this.deptId = deptId;
		this.kuType = kuType;
		this.year = year;
		this.KuId = kuId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 20)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 100)
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	@Column(length = 100)
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Column
	public Integer getKuType() {
		return kuType;
	}

	public void setKuType(Integer kuType) {
		this.kuType = kuType;
	}
	@Column
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	@Column
	public Long getKuId() {
		return KuId;
	}

	public void setKuId(Long kuId) {
		KuId = kuId;
	}

	@Transient
	public WkTUser getUser() {
		if (user == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			user = (WkTUser) userService.get(WkTUser.class, KuId);
		}
		return user;
	}
}
