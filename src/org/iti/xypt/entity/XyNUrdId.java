package org.iti.xypt.entity;

/**
 * XyNUrdId entity. @author MyEclipse Persistence Tools
 */

public class XyNUrdId implements java.io.Serializable {

	// Fields

	private Long krId;
	private Long kuId;
	private Long kdId;
	private Short xnuType;

	// Constructors

	/** default constructor */
	public XyNUrdId() {
	}

	/** full constructor */
	public XyNUrdId(Long krId, Long kuId, Long kdId, Short xnuType) {
		this.krId = krId;
		this.kuId = kuId;
		this.kdId = kdId;
		this.xnuType = xnuType;
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

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Short getXnuType() {
		return this.xnuType;
	}

	public void setXnuType(Short xnuType) {
		this.xnuType = xnuType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof XyNUrdId))
			return false;
		XyNUrdId castOther = (XyNUrdId) other;

		return ((this.getKrId() == castOther.getKrId()) || (this.getKrId() != null
				&& castOther.getKrId() != null && this.getKrId().equals(
				castOther.getKrId())))
				&& ((this.getKuId() == castOther.getKuId()) || (this.getKuId() != null
						&& castOther.getKuId() != null && this.getKuId()
						.equals(castOther.getKuId())))
				&& ((this.getKdId() == castOther.getKdId()) || (this.getKdId() != null
						&& castOther.getKdId() != null && this.getKdId()
						.equals(castOther.getKdId())))
				&& ((this.getXnuType() == castOther.getXnuType()) || (this
						.getXnuType() != null
						&& castOther.getXnuType() != null && this.getXnuType()
						.equals(castOther.getXnuType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getKrId() == null ? 0 : this.getKrId().hashCode());
		result = 37 * result
				+ (getKuId() == null ? 0 : this.getKuId().hashCode());
		result = 37 * result
				+ (getKdId() == null ? 0 : this.getKdId().hashCode());
		result = 37 * result
				+ (getXnuType() == null ? 0 : this.getXnuType().hashCode());
		return result;
	}

}