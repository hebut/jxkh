package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTFileId entity. @author MyEclipse Persistence Tools
 */

public class WkTOrifileId implements java.io.Serializable {

	// Fields

	private Long koiId;
	private String kofName;
	private String kofShowname;
	private Long kuId;
	private String kofFlag;
	private String kofType;

	// Constructors

	/** default constructor */
	public WkTOrifileId() {
	}

	/** minimal constructor */
	public WkTOrifileId(String kofName, String kofShowname, Long kuId,
			String kofFlag, String kofType) {
		this.kofName = kofName;
		this.kofShowname = kofShowname;
		this.kuId = kuId;
		this.kofFlag = kofFlag;
		this.kofType = kofType;
	}

	/** full constructor */
	public WkTOrifileId(Long koiId, String kofName, String kofShowname, Long kuId,
			String kofFlag, String kofType) {
		this.koiId = koiId;
		this.kofName = kofName;
		this.kofShowname = kofShowname;
		this.kuId = kuId;
		this.kofFlag = kofFlag;
		this.kofType = kofType;
	}

	// Property accessors

	public Long getKoiId() {
		return this.koiId;
	}

	public void setKoiId(Long koiId) {
		this.koiId = koiId;
	}

	public String getKofName() {
		return this.kofName;
	}

	public void setKofName(String kofName) {
		this.kofName = kofName;
	}

	public String getKofShowname() {
		return this.kofShowname;
	}

	public void setKofShowname(String kofShowname) {
		this.kofShowname = kofShowname;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getKofFlag() {
		return this.kofFlag;
	}

	public void setKofFlag(String kofFlag) {
		this.kofFlag = kofFlag;
	}

	public String getKofType() {
		return this.kofType;
	}

	public void setKofType(String kofType) {
		this.kofType = kofType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WkTOrifileId))
			return false;
		WkTOrifileId castOther = (WkTOrifileId) other;

		return ((this.getKoiId() == castOther.getKoiId()) || (this.getKoiId() != null
				&& castOther.getKoiId() != null && this.getKoiId().equals(
				castOther.getKoiId())))
				&& ((this.getKofName() == castOther.getKofName()) || (this
						.getKofName() != null
						&& castOther.getKofName() != null && this.getKofName()
						.equals(castOther.getKofName())))
				&& ((this.getKofShowname() == castOther.getKofShowname()) || (this
						.getKofShowname() != null
						&& castOther.getKofShowname() != null && this
						.getKofShowname().equals(castOther.getKofShowname())))
				&& ((this.getKuId() == castOther.getKuId()) || (this.getKuId() != null
						&& castOther.getKuId() != null && this.getKuId()
						.equals(castOther.getKuId())))
				&& ((this.getKofFlag() == castOther.getKofFlag()) || (this
						.getKofFlag() != null
						&& castOther.getKofFlag() != null && this.getKofFlag()
						.equals(castOther.getKofFlag())))
				&& ((this.getKofType() == castOther.getKofType()) || (this
						.getKofType() != null
						&& castOther.getKofType() != null && this.getKofType()
						.equals(castOther.getKofType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getKoiId() == null ? 0 : this.getKoiId().hashCode());
		result = 37 * result
				+ (getKofName() == null ? 0 : this.getKofName().hashCode());
		result = 37
				* result
				+ (getKofShowname() == null ? 0 : this.getKofShowname()
						.hashCode());
		result = 37 * result
				+ (getKuId() == null ? 0 : this.getKuId().hashCode());
		result = 37 * result
				+ (getKofFlag() == null ? 0 : this.getKofFlag().hashCode());
		result = 37 * result
				+ (getKofType() == null ? 0 : this.getKofType().hashCode());
		return result;
	}

}