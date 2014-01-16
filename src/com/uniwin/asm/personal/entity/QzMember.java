package com.uniwin.asm.personal.entity;

/**
 * QzMember entity. @author MyEclipse Persistence Tools
 */
public class QzMember implements java.io.Serializable {
	// Fields
	private Long mbId;
	private Long qzId;
	private Long mbMember;
	private Short mbAccept;
	private Short mbAgree;
	private Integer mbRole;
	public static final Short ACCEPT_NO = 0, ACCEPT_YES = 1, ACCEPT_NULL = 2; // 0为拒绝邀请，1为接受邀请
	public static final Short AGREE_NO = 0, AGREE_YES = 1, AGREE_NULL = 2; // 0为拒绝加入，1为同意加入
	public static final Integer Normal = 0, Admin = 1, NULL = 10; // 0为普通成员，1为管理员

	// Constructors
	/** default constructor */
	public QzMember() {
	}

	/** minimal constructor */
	public QzMember(Long mbId) {
		this.mbId = mbId;
	}

	/** full constructor */
	public QzMember(Long mbId, Long qzId, Long mbMember, Short mbAccept, Short mbAgree, Integer mbRole) {
		this.mbId = mbId;
		this.qzId = qzId;
		this.mbMember = mbMember;
		this.mbAccept = mbAccept;
		this.mbAgree = mbAgree;
		this.mbRole = mbRole;
	}

	// Property accessors
	public Long getMbId() {
		return this.mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}

	public Long getQzId() {
		return this.qzId;
	}

	public void setQzId(Long qzId) {
		this.qzId = qzId;
	}

	public Long getMbMember() {
		return this.mbMember;
	}

	public void setMbMember(Long mbMember) {
		this.mbMember = mbMember;
	}

	public Short getMbAccept() {
		return this.mbAccept;
	}

	public void setMbAccept(Short mbAccept) {
		this.mbAccept = mbAccept;
	}

	public Short getMbAgree() {
		return this.mbAgree;
	}

	public void setMbAgree(Short mbAgree) {
		this.mbAgree = mbAgree;
	}

	public Integer getMbRole() {
		return this.mbRole;
	}

	public void setMbRole(Integer mbRole) {
		this.mbRole = mbRole;
	}
}