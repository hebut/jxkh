package org.iti.xypt.entity;

/**
 * XyUserroleId entity. @author MyEclipse Persistence Tools
 */

public class XyUserroleId implements java.io.Serializable {

	// Fields

	private Long krId;
	private Long kuId;

	// Constructors

	/** default constructor */
	public XyUserroleId() {
	}

	/** full constructor */
	public XyUserroleId(Long krId, Long kuId) {
		this.krId = krId;
		this.kuId = kuId;
	}

	// Property accessors

	public Long getKrId() {
		return this.krId;
	}

	public void setKrId(Long krId) {
		this.krId = krId;
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
		if (!(other instanceof XyUserroleId))
			return false;
		XyUserroleId castOther = (XyUserroleId) other;

		return ((this.getKrId() == castOther.getKrId()) || (this.getKrId() != null && castOther.getKrId() != null && this.getKrId().equals(castOther.getKrId())))
				&& ((this.getKuId() == castOther.getKuId()) || (this.getKuId() != null && castOther.getKuId() != null && this.getKuId().equals(castOther.getKuId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getKrId() == null ? 0 : this.getKrId().hashCode());
		result = 37 * result + (getKuId() == null ? 0 : this.getKuId().hashCode());
		return result;
	}

}