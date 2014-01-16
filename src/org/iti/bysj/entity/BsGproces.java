package org.iti.bysj.entity;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.UserService;

/**
 * BsGproces entity. @author MyEclipse Persistence Tools
 */

public class BsGproces implements java.io.Serializable {

	// Fields

	private Long bgId;
	private String bgName;
	private String bgJc;
	private Integer bsGrade;
	private Long bgStart;
	private Long bgEnd;
	private Long kdId;
	private Double bgJsxs;
	private Double bgXzrxs;

	WkTDept dept;

	// Constructors
	public WkTDept getDept() {
		if(dept==null){
			UserService userService=(UserService)BeanFactory.getBean("userService");
			WkTDept d=(WkTDept)userService.get(WkTDept.class, kdId);
			this.dept=d;
		}
		return dept;
	}

	/** default constructor */
	public BsGproces() {
	}

	/** full constructor */
	public BsGproces(Long bgId, String bgName,String bgJc, Integer bsGrade, Long bgStart,
			Long bgEnd, Long kdId, Integer bgJsweight, Integer bgThweight,
			Integer bgDbweight,Double bgJsxs,Double bgXzrxs) {
		this.bgId = bgId;
		this.bgName = bgName;
		this.bgJc=bgJc;
		this.bsGrade = bsGrade;
		this.bgStart = bgStart;
		this.bgEnd = bgEnd;
		this.kdId = kdId;
		this.bgJsxs = bgJsxs;
		this.bgXzrxs = bgXzrxs;
	}

	// Property accessors

	public Long getBgId() {
		return this.bgId;
	}

	public void setBgId(Long bgId) {
		this.bgId = bgId;
	}
	public Double getBgJsxs() {
		return bgJsxs;
	}

	public void setBgJsxs(Double bgJsxs) {
		this.bgJsxs = bgJsxs;
	}

	public Double getBgXzrxs() {
		return bgXzrxs;
	}

	public void setBgXzrxs(Double bgXzrxs) {
		this.bgXzrxs = bgXzrxs;
	}


	public String getBgName() {
		return this.bgName;
	}

	public void setBgName(String bgName) {
		this.bgName = bgName;
	}
	public String getBgJc() {
		return this.bgJc;
	}

	public void setBgJc(String bgJc) {
		this.bgJc = bgJc;
	}

	public Integer getBsGrade() {
		return this.bsGrade;
	}

	public void setBsGrade(Integer bsGrade) {
		this.bsGrade = bsGrade;
	}

	public Long getBgStart() {
		return this.bgStart;
	}

	public void setBgStart(Long bgStart) {
		this.bgStart = bgStart;
	}

	public Long getBgEnd() {
		return this.bgEnd;
	}

	public void setBgEnd(Long bgEnd) {
		this.bgEnd = bgEnd;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}


}