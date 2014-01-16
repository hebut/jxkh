package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTFileId entity. @author MyEclipse Persistence Tools
 */

public class WkTFileId implements java.io.Serializable {

	// Fields

	private Long kiId;
	private String kfName;
	private String kfShowname;
	private Long kuId;
	private String kfFlag;
	private String kfType;

	// Constructors

	/** default constructor */
	public WkTFileId() {
	}

	/** minimal constructor */
	public WkTFileId(String kfName, String kfShowname, Long kuId,
			String kfFlag, String kfType) {
		this.kfName = kfName;
		this.kfShowname = kfShowname;
		this.kuId = kuId;
		this.kfFlag = kfFlag;
		this.kfType = kfType;
	}

	/** full constructor */
	public WkTFileId(Long kiId, String kfName, String kfShowname, Long kuId,
			String kfFlag, String kfType) {
		this.kiId = kiId;
		this.kfName = kfName;
		this.kfShowname = kfShowname;
		this.kuId = kuId;
		this.kfFlag = kfFlag;
		this.kfType = kfType;
	}

	// Property accessors

	public Long getKiId() {
		return this.kiId;
	}

	public void setKiId(Long kiId) {
		this.kiId = kiId;
	}

	public String getKfName() {
		return this.kfName;
	}

	public void setKfName(String kfName) {
		this.kfName = kfName;
	}

	public String getKfShowname() {
		return this.kfShowname;
	}

	public void setKfShowname(String kfShowname) {
		this.kfShowname = kfShowname;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public String getKfFlag() {
		return this.kfFlag;
	}

	public void setKfFlag(String kfFlag) {
		this.kfFlag = kfFlag;
	}

	public String getKfType() {
		return this.kfType;
	}

	public void setKfType(String kfType) {
		this.kfType = kfType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WkTFileId))
			return false;
		WkTFileId castOther = (WkTFileId) other;

		return ((this.getKiId() == castOther.getKiId()) || (this.getKiId() != null
				&& castOther.getKiId() != null && this.getKiId().equals(
				castOther.getKiId())))
				&& ((this.getKfName() == castOther.getKfName()) || (this
						.getKfName() != null
						&& castOther.getKfName() != null && this.getKfName()
						.equals(castOther.getKfName())))
				&& ((this.getKfShowname() == castOther.getKfShowname()) || (this
						.getKfShowname() != null
						&& castOther.getKfShowname() != null && this
						.getKfShowname().equals(castOther.getKfShowname())))
				&& ((this.getKuId() == castOther.getKuId()) || (this.getKuId() != null
						&& castOther.getKuId() != null && this.getKuId()
						.equals(castOther.getKuId())))
				&& ((this.getKfFlag() == castOther.getKfFlag()) || (this
						.getKfFlag() != null
						&& castOther.getKfFlag() != null && this.getKfFlag()
						.equals(castOther.getKfFlag())))
				&& ((this.getKfType() == castOther.getKfType()) || (this
						.getKfType() != null
						&& castOther.getKfType() != null && this.getKfType()
						.equals(castOther.getKfType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getKiId() == null ? 0 : this.getKiId().hashCode());
		result = 37 * result
				+ (getKfName() == null ? 0 : this.getKfName().hashCode());
		result = 37
				* result
				+ (getKfShowname() == null ? 0 : this.getKfShowname()
						.hashCode());
		result = 37 * result
				+ (getKuId() == null ? 0 : this.getKuId().hashCode());
		result = 37 * result
				+ (getKfFlag() == null ? 0 : this.getKfFlag().hashCode());
		result = 37 * result
				+ (getKfType() == null ? 0 : this.getKfType().hashCode());
		return result;
	}

}