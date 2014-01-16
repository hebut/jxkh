package org.iti.xypt.entity;

public class XyFudaoId implements java.io.Serializable {

	private Long clId;
	private Long kuId;

	public XyFudaoId() {

	}

	public XyFudaoId(Long clId, Long kuId) {
		super();
		this.clId = clId;
		this.kuId = kuId;
	}

	public Long getClId() {
		return clId;
	}

	public void setClId(Long clId) {
		this.clId = clId;
	}

	public Long getKuId() {
		return kuId;
	}

	public void setKuId(Long kuId) {
		this.kuId = kuId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof XyFudaoId))
			return false;
		XyFudaoId castOther = (XyFudaoId) other;
		return ((this.getClId() == castOther.getClId()) || (this.getClId() != null && castOther.getClId() != null && this.getClId().equals(castOther.getClId())))
				&& ((this.getKuId() == castOther.getKuId()) || (this.getKuId() != null && castOther.getKuId() != null && this.getKuId().equals(castOther.getKuId())));
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (getClId() == null ? 0 : this.getClId().hashCode());
		result = 37 * result + (getKuId() == null ? 0 : this.getKuId().hashCode());
		return result;
	}
}
