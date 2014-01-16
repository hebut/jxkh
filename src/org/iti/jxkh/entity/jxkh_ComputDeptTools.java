package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jxkh_ComputDeptTools")
public class jxkh_ComputDeptTools implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7413185300900828365L;
	/**
	 * @author 马静伟
	 */
	
	private Long id;
	private String year;
	private String deptName;//部门名称
	private Long deptId;// 部门编号
	private Double PersonNum;//部门人数
	private Double acountForth, acountThird, acountSecond, acountFirst;//四档、三档、二档、一档人数
	private Double perBasicAcount;//一个基数额度
	private Double basicNum;//各部门基数
	private Double totalNum;//总额
	private Integer deptGrade;//部门档次
	
	/**
	 * 部门档次:4 四档次，3 三档次， 2 二档次， 1 一档次；
	 */
	public static final int GRADE_FORTH = 4, GRADE_THIRD = 3, GRADE_SECOND = 2, GRADE_FIRST = 1; 

	public jxkh_ComputDeptTools() {
		super();
	}

	public jxkh_ComputDeptTools(Long id, String year, String deptName, Long deptId,
			Double personNum, Double acountForth, Double acountThird,
			Double acountSecond, Double acountFirst, Double perBasicAcount,
			Double basicNum, Double totalNum, Integer deptGrade) {
		super();
		this.id = id;
		this.deptName = deptName;
		this.deptId = deptId;
		this.year = year;
		PersonNum = personNum;
		this.acountForth = acountForth;
		this.acountThird = acountThird;
		this.acountSecond = acountSecond;
		this.acountFirst = acountFirst;
		this.perBasicAcount = perBasicAcount;
		this.basicNum = basicNum;
		this.totalNum = totalNum;
		this.deptGrade = deptGrade;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Column
	public Double getPersonNum() {
		return PersonNum;
	}

	public void setPersonNum(Double personNum) {
		PersonNum = personNum;
	}

	@Column
	public Double getAcountForth() {
		return acountForth;
	}

	public void setAcountForth(Double acountForth) {
		this.acountForth = acountForth;
	}

	@Column
	public Double getAcountThird() {
		return acountThird;
	}

	public void setAcountThird(Double acountThird) {
		this.acountThird = acountThird;
	}

	@Column
	public Double getAcountSecond() {
		return acountSecond;
	}

	public void setAcountSecond(Double acountSecond) {
		this.acountSecond = acountSecond;
	}

	@Column
	public Double getAcountFirst() {
		return acountFirst;
	}

	public void setAcountFirst(Double acountFirst) {
		this.acountFirst = acountFirst;
	}

	@Column
	public Double getPerBasicAcount() {
		return perBasicAcount;
	}

	public void setPerBasicAcount(Double perBasicAcount) {
		this.perBasicAcount = perBasicAcount;
	}

	@Column
	public Double getBasicNum() {
		return basicNum;
	}

	public void setBasicNum(Double basicNum) {
		this.basicNum = basicNum;
	}

	@Column
	public Double getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}

	@Column
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Column
	public Integer getDeptGrade() {
		return deptGrade;
	}

	public void setDeptGrade(Integer deptGrade) {
		this.deptGrade = deptGrade;
	}
	
	//枚举
	//GRADE_FORTH = 4, GRADE_THIRD = 3, GRADE_SECOND = 2, GRADE_FIRST = 1; 
	public enum DeptGradeEnum implements com.iti.common.interfaces.IEnum {
		GRADE_FORTH {

			@Override
			public Integer getValue() {
				return 4;
			}

			@Override
			public String getName() {
				return "四档";
			}

		},
		
		GRADE_THIRD {

			@Override
			public Integer getValue() {
				return 3;
			}

			@Override
			public String getName() {
				return "三档";
			}

		},
		
		GRADE_SECOND {

			@Override
			public Integer getValue() {
				return 2;
			}

			@Override
			public String getName() {
				return "二档";
			}

		},
		
		GRADE_FIRST {

			@Override
			public Integer getValue() {
				return 1;
			}

			@Override
			public String getName() {
				return "一档";
			}

		};
	}
	
	public static DeptGradeEnum getEnum(Integer i) {
			switch (i) {
			case 1:
				return DeptGradeEnum.GRADE_FIRST;
			case 2:
				return DeptGradeEnum.GRADE_SECOND;
			case 3:
				return DeptGradeEnum.GRADE_THIRD;
			case 4:
				return DeptGradeEnum.GRADE_FORTH;
			
			default:
				throw new RuntimeException(new StringBuilder(
						DeptGradeEnum.class.getName()).append("[")
						.append("Error Input Value").append("]").toString());
			}
	}
}
