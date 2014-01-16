package org.iti.gh.entity;

/**
 * GhUseryjfxId entity. @author MyEclipse Persistence Tools
 */

public class GhUseryjfxId implements java.io.Serializable {

	// Fields

	private Long kuId;
	private Long gyId;

	// Constructors

	/** default constructor */
	public GhUseryjfxId() {
	}

	/** full constructor */
	public GhUseryjfxId(Long kuId, Long gyId) {
		this.kuId = kuId;
		this.gyId = gyId;
	}

	// Property accessors

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Long getGyId() {
		return this.gyId;
	}

	public void setGyId(Long gyId) {
		this.gyId = gyId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GhUseryjfxId))
			return false;
		GhUseryjfxId castOther = (GhUseryjfxId) other;

		return ((this.getKuId() == castOther.getKuId()) || (this.getKuId() != null
				&& castOther.getKuId() != null && this.getKuId().equals(
				castOther.getKuId())))
				&& ((this.getGyId() == castOther.getGyId()) || (this.getGyId() != null
						&& castOther.getGyId() != null && this.getGyId()
						.equals(castOther.getGyId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getKuId() == null ? 0 : this.getKuId().hashCode());
		result = 37 * result
				+ (getGyId() == null ? 0 : this.getGyId().hashCode());
		return result;
	}

}