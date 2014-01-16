package org.iti.xypt.entity;

import java.util.Date;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.RoleService;
import com.uniwin.framework.service.UserService;

/**
 * XyMessage entity. @author MyEclipse Persistence Tools
 */
public class XyMessage implements java.io.Serializable {
	// Fields
	public static final Short TYPE_MESSAGE = 0, TYPE_NOTICE = 1, TYPE_ALL = 2;
	public static final Short STATE_SEND = 1, STATE_STORE = 0, STATE_DELETE = 2;
	public static final Short HFILE_NO = 0, HFILE_YES = 1;
	public static final String BASE_FILE = "/upload/message/";
	private Long xmId;//消息id
	private Long xmSndtime;
	private Long kuId;//发件人id
	private Long kdId;
	private Long krId;
	private String xmSubject;//主题
	private String xmContent;//内容
	private Short xmHfile;//是否有附件
	private Short xmState;
	private Short xmType;//信息类型
	private String xmReceivers;//收件人名集合
	private String xmSender;//发件人姓名
	private String xmKeyword;//关键字
	// Constructors
	WkTRole role;

	// Constructors
	public WkTRole getRole() {
		if (role == null) {
			RoleService roleService = (RoleService) BeanFactory.getBean("roleService");
			this.role = roleService.findByRid(krId);
		}
		return role;
	}

	public void setRole(WkTRole role) {
		this.role = role;
	}

	WkTDept dept;

	public WkTDept getDept() {
		if (dept == null) {
			UserService userService = (UserService) BeanFactory.getBean("userService");
			this.dept = (WkTDept) userService.get(WkTDept.class, kdId);
		}
		return dept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}

	/** default constructor */
	public XyMessage() {
		Date d = new Date();
		this.kdId = 0L;
		this.krId = 0L;
		this.xmState = STATE_SEND;
		this.xmHfile = HFILE_NO;
		this.xmSndtime = d.getTime();
	}

	/** full constructor */
	public XyMessage(Long xmId, Long xmSndtime, Long kuId, Long kdId, Long krId, String xmSubject, String xmContent, Short xmHfile, Short xmState, Short xmType, String xmSender) {
		this.xmId = xmId;
		this.xmSndtime = xmSndtime;
		this.kuId = kuId;
		this.kdId = kdId;
		this.krId = krId;
		this.xmSubject = xmSubject;
		this.xmContent = xmContent;
		this.xmHfile = xmHfile;
		this.xmState = xmState;
		this.xmType = xmType;
		this.xmSender = xmSender;
	}

	/** full constructor */
	public XyMessage(Long xmId, Long xmSndtime, Long kuId, Long kdId, Long krId, String xmSubject, String xmContent, Short xmHfile, Short xmState, Short xmType, String xmReceivers, String xmSender, String xmKeyword) {
		this.xmId = xmId;
		this.xmSndtime = xmSndtime;
		this.kuId = kuId;
		this.kdId = kdId;
		this.krId = krId;
		this.xmSubject = xmSubject;
		this.xmContent = xmContent;
		this.xmHfile = xmHfile;
		this.xmState = xmState;
		this.xmType = xmType;
		this.xmReceivers = xmReceivers;
		this.xmSender = xmSender;
		this.xmKeyword = xmKeyword;
	}

	// Property accessors
	/**
	 * 在basepath后面的路径
	 */
	public String getReleativeFilePath() {
		return kuId + "\\" + xmId + "\\";
	}

	public String getXmSender() {
		return xmSender;
	}

	public void setXmSender(String xmSender) {
		this.xmSender = xmSender;
	}

	public String getXmReceivers() {
		return xmReceivers;
	}

	public void setXmReceivers(String xmReceivers) {
		this.xmReceivers = xmReceivers;
	}

	public Long getXmId() {
		return this.xmId;
	}

	public void setXmId(Long xmId) {
		this.xmId = xmId;
	}

	public Long getXmSndtime() {
		return this.xmSndtime;
	}

	public void setXmSndtime(Long xmSndtime) {
		this.xmSndtime = xmSndtime;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Long getKrId() {
		return this.krId;
	}

	public void setKrId(Long krId) {
		this.krId = krId;
	}

	public String getXmSubject() {
		return this.xmSubject;
	}

	public void setXmSubject(String xmSubject) {
		this.xmSubject = xmSubject;
	}

	public String getXmContent() {
		return this.xmContent;
	}

	public void setXmContent(String xmContent) {
		this.xmContent = xmContent;
	}

	public Short getXmHfile() {
		return this.xmHfile;
	}

	public void setXmHfile(Short xmHfile) {
		this.xmHfile = xmHfile;
	}

	public Short getXmState() {
		return this.xmState;
	}

	public void setXmState(Short xmState) {
		this.xmState = xmState;
	}

	public Short getXmType() {
		return this.xmType;
	}

	public void setXmType(Short xmType) {
		this.xmType = xmType;
	}

	public String getXmKeyword() {
		return xmKeyword;
	}

	public void setXmKeyword(String xmKeyword) {
		this.xmKeyword = xmKeyword;
	}
}