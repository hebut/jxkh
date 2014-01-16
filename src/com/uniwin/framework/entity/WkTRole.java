package com.uniwin.framework.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.service.RoleService;

/**
 * WkTRole entity. @author MyEclipse Persistence Tools
 */
public class WkTRole implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * krArgs��ʮλ����һλ��ʾ�Ƿ�Ϊ��������������ֵ�ֱ�Ϊ0����1
	 */
	public static int INDEX_ISDD = 0;
	public static char ISDD_YES = '1', ISDD_NO = '0';
	/**
	 * krArgs��ʮλ���ڶ�λ��ʾ��ɫ����ֵ�ֱ�Ϊ0,1,2
	 */
	public static int INDEX_GRADE = 1;
	/**
	 * krArgs��ʮλ������λ��ʾ��ɫ���ͣ�ֵ�ֱ�Ϊѧ��0,��ʦ1,�쵼2,����3,����Ա4,ѧ���ɲ�5,�о���6,ѧ�Ƹ�����7
	 */
	public static int INDEX_TYPE = 2;
	public static char TYPE_XS = '0', TYPE_JS = '1', TYPE_LD = '2', TYPE_DD = '3', TYPE_FDY = '4', TYPE_XSGB = '5', TYPE_YJS = '6', TYPE_XK = '7';
	public static int INDEX_GZL = 3;
	public static char GZL_YES = '1', GZL_NO = '0';
	private Long krId;
	private Long krPid;
	private String krName;
	private String krDefault;
	private String krDesc;
	private Long kdId;
	private Long krOrder;
	private String krShare;
	private String krArgs;
	private String krAdmins;
	private Integer dept;
	WkTDept wkTDept;

	// Constructors
	/** default constructor */
	public WkTRole() {
		this.krArgs = "0000000000";
	}

	/** minimal constructor */
	public WkTRole(Long krId, Long kdId, String krArgs) {
		this.krId = krId;
		this.kdId = kdId;
		this.krArgs = krArgs;
	}

	/** full constructor */
	public WkTRole(Long krId, Long krPid, String krName, String krDefault, String krDesc, Long kdId, Long krOrder, String krShare, String krArgs, String krAdmins) {
		this.krId = krId;
		this.krPid = krPid;
		this.krName = krName;
		this.krDefault = krDefault;
		this.krDesc = krDesc;
		this.kdId = kdId;
		this.krOrder = krOrder;
		this.krShare = krShare;
		this.krArgs = krArgs;
		this.krAdmins = krAdmins;
	}

	// Property accessors
	public WkTDept getWkTDept() {
		if (wkTDept == null) {
			RoleService roleService = (RoleService) BeanFactory.getBean("roleService");
			wkTDept = (WkTDept) roleService.get(WkTDept.class, kdId);
		}
		return wkTDept;
	}

	public void setWkTDept(WkTDept wkTDept) {
		this.wkTDept = wkTDept;
	}

	public String getKrAdmins() {
		return krAdmins;
	}

	public void setKrAdmins(String krAdmins) {
		this.krAdmins = krAdmins;
	}

	public String getKrArgs() {
		return krArgs;
	}

	public void setKrArgs(String krArgs) {
		this.krArgs = krArgs;
	}

	public char getKrArgs(int index) {
		char[] args = krArgs.toCharArray();
		return args[index];
	}

	public void setKrArgs(int index, char arg) {
		char[] args = krArgs.toCharArray();
		args[index] = arg;
		this.krArgs = new String(args);
	}

	public Long getKrId() {
		return this.krId;
	}

	public void setKrId(Long krId) {
		this.krId = krId;
	}

	public Long getKrPid() {
		return this.krPid;
	}

	public void setKrPid(Long krPid) {
		this.krPid = krPid;
	}

	public String getKrName() {
		return this.krName;
	}

	public void setKrName(String krName) {
		this.krName = krName;
	}

	public String getKrDefault() {
		return this.krDefault;
	}

	public void setKrDefault(String krDefault) {
		this.krDefault = krDefault;
	}

	public String getKrDesc() {
		return this.krDesc;
	}

	public void setKrDesc(String krDesc) {
		this.krDesc = krDesc;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Long getKrOrder() {
		return this.krOrder;
	}

	public void setKrOrder(Long krOrder) {
		this.krOrder = krOrder;
	}

	public String getKrShare() {
		return this.krShare;
	}

	public void setKrShare(String krShare) {
		this.krShare = krShare;
	}

	public Integer getDept() {
		return dept;
	}

	public void setDept(Integer dept) {
		this.dept = dept;
	}
}