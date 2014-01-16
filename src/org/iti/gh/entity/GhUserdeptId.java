package org.iti.gh.entity;

/**
 * GhUserdeptId entity. @author MyEclipse Persistence Tools
 */

public class GhUserdeptId implements java.io.Serializable {

	// Fields

	private Long kuId;
	private Long kdId;

	// Constructors

	/** default constructor */
	public GhUserdeptId() {
	}

	/** full constructor */
	public GhUserdeptId(Long kuId, Long kdId) {
		this.kuId = kuId;
		this.kdId = kdId;
	}

	// Property accessors

	public Long getKuId() {
		return this.kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GhUserdeptId))
			return false;
		GhUserdeptId castOther = (GhUserdeptId) other;

		return ((this.getKuId() == castOther.getKuId()) || (this.getKuId() != null
				&& castOther.getKuId() != null && this.getKuId().equals(
				castOther.getKuId())))
				&& ((this.getKdId() == castOther.getKdId()) || (this.getKdId() != null
						&& castOther.getKdId() != null && this.getKdId()
						.equals(castOther.getKdId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getKuId() == null ? 0 : this.getKuId().hashCode());
		result = 37 * result
				+ (getKdId() == null ? 0 : this.getKdId().hashCode());
		return result;
	}

}