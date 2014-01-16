package org.iti.xypt.entity;

/**
 * XyMReceiverId entity. @author MyEclipse Persistence Tools
 */

public class XyMReceiverId implements java.io.Serializable {

	// Fields

	private Long xmId;//消息id
	private Long kuId;//收件人id

	// Constructors

	/** default constructor */
	public XyMReceiverId() {
	}

	/** full constructor */
	public XyMReceiverId(Long xmId, Long kuId) {
		this.xmId = xmId;
		this.kuId = kuId;
	}

	// Property accessors

	public Long getXmId() {
		return this.xmId;
	}

	public void setXmId(Long xmId) {
		this.xmId = xmId;
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
		if (!(other instanceof XyMReceiverId))
			return false;
		XyMReceiverId castOther = (XyMReceiverId) other;

		return ((this.getXmId() == castOther.getXmId()) || (this.getXmId() != null
				&& castOther.getXmId() != null && this.getXmId().equals(
				castOther.getXmId())))
				&& ((this.getKuId() == castOther.getKuId()) || (this.getKuId() != null
						&& castOther.getKuId() != null && this.getKuId()
						.equals(castOther.getKuId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getXmId() == null ? 0 : this.getXmId().hashCode());
		result = 37 * result
				+ (getKuId() == null ? 0 : this.getKuId().hashCode());
		return result;
	}

}