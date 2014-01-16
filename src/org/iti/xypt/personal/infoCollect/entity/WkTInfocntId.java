package org.iti.xypt.personal.infoCollect.entity;

/**
 * WkTInfocntId entity. @author MyEclipse Persistence Tools
 */

public class WkTInfocntId implements java.io.Serializable {

	// Fields

	private Long kiId;
	private Long kiSubid;

	// Constructors

	/** default constructor */
	public WkTInfocntId() {
	}

	/** full constructor */
	public WkTInfocntId(Long kiId, Long kiSubid) {
		this.kiId = kiId;
		this.kiSubid = kiSubid;
	}

	// Property accessors

	public Long getKiId() {
		return this.kiId;
	}

	public void setKiId(Long kiId) {
		this.kiId = kiId;
	}

	public Long getKiSubid() {
		return this.kiSubid;
	}

	public void setKiSubid(Long kiSubid) {
		this.kiSubid = kiSubid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WkTInfocntId))
			return false;
		WkTInfocntId castOther = (WkTInfocntId) other;

		return ((this.getKiId() == castOther.getKiId()) || (this.getKiId() != null
				&& castOther.getKiId() != null && this.getKiId().equals(
				castOther.getKiId())))
				&& ((this.getKiSubid() == castOther.getKiSubid()) || (this
						.getKiSubid() != null
						&& castOther.getKiSubid() != null && this.getKiSubid()
						.equals(castOther.getKiSubid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getKiId() == null ? 0 : this.getKiId().hashCode());
		result = 37 * result
				+ (getKiSubid() == null ? 0 : this.getKiSubid().hashCode());
		return result;
	}

}