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
	 * @author ��ΰ
	 */
	
	private Long id;
	private String year;
	private String deptName;//��������
	private Long deptId;// ���ű��
	private Double PersonNum;//��������
	private Double acountForth, acountThird, acountSecond, acountFirst;//�ĵ���������������һ������
	private Double perBasicAcount;//һ���������
	private Double basicNum;//�����Ż���
	private Double totalNum;//�ܶ�
	private Integer deptGrade;//���ŵ���
	
	/**
	 * ���ŵ���:4 �ĵ��Σ�3 �����Σ� 2 �����Σ� 1 һ���Σ�
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
	
	//ö��
	//GRADE_FORTH = 4, GRADE_THIRD = 3, GRADE_SECOND = 2, GRADE_FIRST = 1; 
	public enum DeptGradeEnum implements com.iti.common.interfaces.IEnum {
		GRADE_FORTH {

			@Override
			public Integer getValue() {
				return 4;
			}

			@Override
			public String getName() {
				return "�ĵ�";
			}

		},
		
		GRADE_THIRD {

			@Override
			public Integer getValue() {
				return 3;
			}

			@Override
			public String getName() {
				return "����";
			}

		},
		
		GRADE_SECOND {

			@Override
			public Integer getValue() {
				return 2;
			}

			@Override
			public String getName() {
				return "����";
			}

		},
		
		GRADE_FIRST {

			@Override
			public Integer getValue() {
				return 1;
			}

			@Override
			public String getName() {
				return "һ��";
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
