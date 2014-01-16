package org.iti.xypt.entity;

/**
 * XyNReceiverId entity. @author MyEclipse Persistence Tools
 */

public class XyNReceiverId implements java.io.Serializable {

	// Fields

	private Long xmId;
	private Long krId;
	private Long kdId;
	private Short xmType;

	// Constructors

	/** default constructor */
	public XyNReceiverId() {
	}

	/** full constructor */
	public XyNReceiverId(Long xmId, Long krId, Long kdId, Short xmType) {
		this.xmId = xmId;
		this.krId = krId;
		this.kdId = kdId;
		this.xmType = xmType;
	}

	// Property accessors

	public Long getXmId() {
		return this.xmId;
	}

	public void setXmId(Long xmId) {
		this.xmId = xmId;
	}

	public Long getKrId() {
		return this.krId;
	}

	public void setKrId(Long krId) {
		this.krId = krId;
	}

	public Long getKdId() {
		return this.kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public Short getXmType() {
		return this.xmType;
	}

	public void setXmType(Short xmType) {
		this.xmType = xmType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof XyNReceiverId))
			return false;
		XyNReceiverId castOther = (XyNReceiverId) other;

		return ((this.getXmId() == castOther.getXmId()) || (this.getXmId() != null
				&& castOther.getXmId() != null && this.getXmId().equals(
				castOther.getXmId())))
				&& ((this.getKrId() == castOther.getKrId()) || (this.getKrId() != null
						&& castOther.getKrId() != null && this.getKrId()
						.equals(castOther.getKrId())))
				&& ((this.getKdId() == castOther.getKdId()) || (this.getKdId() != null
						&& castOther.getKdId() != null && this.getKdId()
						.equals(castOther.getKdId())))
				&& ((this.getXmType() == castOther.getXmType()) || (this
						.getXmType() != null
						&& castOther.getXmType() != null && this.getXmType()
						.equals(castOther.getXmType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getXmId() == null ? 0 : this.getXmId().hashCode());
		result = 37 * result
				+ (getKrId() == null ? 0 : this.getKrId().hashCode());
		result = 37 * result
				+ (getKdId() == null ? 0 : this.getKdId().hashCode());
		result = 37 * result
				+ (getXmType() == null ? 0 : this.getXmType().hashCode());
		return result;
	}

}