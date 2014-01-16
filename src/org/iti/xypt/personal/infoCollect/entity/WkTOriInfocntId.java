package org.iti.xypt.personal.infoCollect.entity;


public class WkTOriInfocntId implements java.io.Serializable{
	private Long koiId;
	public Long getKoiId() {
		return koiId;
	}

	public void setKoiId(Long koiId) {
		this.koiId = koiId;
	}

	public Long getKoiSubid() {
		return koiSubid;
	}

	public void setKoiSubid(Long koiSubid) {
		this.koiSubid = koiSubid;
	}

	private Long koiSubid;

	// Constructors

	/** default constructor */
	public WkTOriInfocntId() {
	}

	/** full constructor */
	public WkTOriInfocntId(Long koiId, Long koiSubid) {
		this.koiId = koiId;
		this.koiSubid = koiSubid;
	}

	// Property accessors
	

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WkTInfocntId))
			return false;
		WkTOriInfocntId castOther = (WkTOriInfocntId) other;

		return ((this.getKoiId() == castOther.getKoiId()) || (this.getKoiId() != null
				&& castOther.getKoiId() != null && this.getKoiId().equals(
				castOther.getKoiId())))
				&& ((this.getKoiSubid() == castOther.getKoiSubid()) || (this
						.getKoiSubid() != null
						&& castOther.getKoiSubid() != null && this.getKoiSubid()
						.equals(castOther.getKoiSubid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getKoiId() == null ? 0 : this.getKoiId().hashCode());
		result = 37 * result
				+ (getKoiSubid() == null ? 0 : this.getKoiSubid().hashCode());
		return result;
	}
}
