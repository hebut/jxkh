package com.uniwin.framework.entity;

import java.util.ArrayList;
import java.util.List;
import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.UserService;

/**
 * WkTDept entity. @author MyEclipse Persistence Tools
 */
public class WkTDept implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Fields
	public static final String BUMEN = "0";

	public static final String DANWEI = "1";

	public static final String XUEKE = "3";

	public static final String QUANBU = "10";

	public static final String WORK = "0";

	public static final String MANAGE = "1";

	public static final Integer GRADE_XI = 3, GRADE_YUAN = 2, GRADE_SCH = 1;
	public static final Integer USE_YES=0,USE_NO=1;
	
	public static final String guanlidept = "000";

	private Long kdId;

	private Long kdPid;

	private String kdName;
	
	private Integer kdState;

	private String kdDesc;

	private Long kdOrder;

	private String kdType;

	private Integer dep;

	private String kdNumber;

	private Integer kdLevel;

	private Long kdSchid;

	private String kdClassify;

	private WkTDept schDept;

	private WkTDept pdept;

	private List<WkTDept> childDept = new ArrayList<WkTDept>();

	public List<WkTDept> getChildDept() {
		if (childDept == null) {
			DepartmentService departmentService = (DepartmentService) BeanFactory.getBean("departmentService");
			departmentService.getChildDepdw(kdId);
		}
		return childDept;
	}

	public WkTDept getPdept() {
		if (pdept == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			WkTDept d = (WkTDept) userService.get(WkTDept.class, kdPid);
			this.pdept = d;
		}
		return pdept;
	}

	// Constructors
	public String getGradeName(int grade) {
		String[] gradeNames;
		if (kdSchid.longValue() == kdId.longValue()) {
			gradeNames = kdDesc.split(",");
		} else {
			gradeNames = getSchDept().getKdDesc().split(",");
		}
		if (grade >= gradeNames.length) {
			return "Œ¥…Ë÷√";
		}
		return gradeNames[grade];
	}

	public WkTDept getSchDept() {
		if (schDept == null) {
			DepartmentService departmentService = (DepartmentService) BeanFactory.getBean("departmentService");
			schDept = (WkTDept) departmentService.get(WkTDept.class, kdSchid);
		}
		return schDept;
	}

	public void setSchDept(WkTDept schDept) {
		this.schDept = schDept;
	}

	public Long getKdSchid() {
		return kdSchid;
	}

	public void setKdSchid(Long kdSchid) {
		this.kdSchid = kdSchid;
	}

	public String getKdNumber() {
		return kdNumber;
	}

	public void setKdNumber(String kdNumber) {
		this.kdNumber = kdNumber;
	}

	/** default constructor */
	public WkTDept() {}

	/** minimal constructor */
	public WkTDept(Long kdId, Long kdPid, String kdName, Integer kdLevel, Long kdSchid) {
		this.kdId = kdId;
		this.kdPid = kdPid;
		this.kdName = kdName;
		this.kdLevel = kdLevel;
		this.kdSchid = kdSchid;
	}

	/** full constructor */
	public WkTDept(Long kdId, Long kdPid, String kdName, Integer kdState,
			String kdDesc, Long kdOrder, String kdType, Integer dep,
			String kdNumber, Integer kdLevel, Long kdSchid, String kdClassify,
			WkTDept schDept, WkTDept pdept, List<WkTDept> childDept) {
		super();
		this.kdId = kdId;
		this.kdPid = kdPid;
		this.kdName = kdName;
		this.kdState = kdState;
		this.kdDesc = kdDesc;
		this.kdOrder = kdOrder;
		this.kdType = kdType;
		this.dep = dep;
		this.kdNumber = kdNumber;
		this.kdLevel = kdLevel;
		this.kdSchid = kdSchid;
		this.kdClassify = kdClassify;
		this.schDept = schDept;
		this.pdept = pdept;
		this.childDept = childDept;
	}

	// Property accessors
	public Integer getKdLevel() {
		return kdLevel;
	}

	

	public void setKdLevel(Integer kdLevel) {
		this.kdLevel = kdLevel;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Long getKdPid() {
		return this.kdPid;
	}

	public void setKdPid(Long kdPid) {
		this.kdPid = kdPid;
	}	
	
	public Integer getKdState() {
		return kdState;
	}

	public void setKdState(Integer kdState) {
		this.kdState = kdState;
	}

	public String getKdName() {
		return this.kdName;
	}

	public void setKdName(String kdName) {
		this.kdName = kdName;
	}

	public String getKdDesc() {
		return this.kdDesc;
	}

	public void setKdDesc(String kdDesc) {
		this.kdDesc = kdDesc;
	}

	public Long getKdOrder() {
		return this.kdOrder;
	}

	public void setKdOrder(Long kdOrder) {
		this.kdOrder = kdOrder;
	}

	public String getKdType() {
		return this.kdType;
	}

	public void setKdType(String kdType) {
		this.kdType = kdType;
	}

	public Integer getDep() {
		return dep;
	}

	public void setDep(Integer dep) {
		this.dep = dep;
	}

	public String getKdClassify() {
		return kdClassify;
	}

	public void setKdClassify(String kdClassify) {
		this.kdClassify = kdClassify;
	}
}