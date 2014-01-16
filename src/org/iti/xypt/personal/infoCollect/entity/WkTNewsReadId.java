package org.iti.xypt.personal.infoCollect.entity;



public class WkTNewsReadId implements java.io.Serializable {

	// Fields

	private Long kbId;
	private Long keId;
	public Long getKeId() {
		return keId;
	}

	public void setKeId(Long keId) {
		this.keId = keId;
	}

	private Long kuId;

	// Constructors

	/** default constructor */
	public WkTNewsReadId() {
	}

	/** full constructor */
	public WkTNewsReadId(Long kbId,Long keid, Long kuId) {
		this.kbId = kbId;
		this.keId=  keid;
		this.kuId = kuId;
	}

	// Property accessors

	public Long getKbId() {
		return this.kbId;
	}

	public void setKbId(Long kbId) {
		this.kbId = kbId;
	}

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WkTNewsReadId))
			return false;
		WkTNewsReadId castOther = (WkTNewsReadId) other;

		return ((this.getKbId() == castOther.getKbId()) || (this.getKbId() != null
				&& castOther.getKbId() != null && this.getKbId().equals(
				castOther.getKbId())))
				&& ((this.getKuId() == castOther.getKuId()) || (this.getKuId() != null
						&& castOther.getKuId() != null && this.getKuId()
						.equals(castOther.getKuId())))
				&&((this.getKeId() == castOther.getKeId()) || (this.getKeId() != null
						&& castOther.getKeId() != null && this.getKeId()
						.equals(castOther.getKeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getKbId() == null ? 0 : this.getKbId().hashCode());
		result = 37 * result
				+ (getKuId() == null ? 0 : this.getKuId().hashCode());
		return result;
	}

}