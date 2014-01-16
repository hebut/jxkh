package org.iti.bysj.entity;

public class BsUserDudaoId implements java.io.Serializable{

	// Fields

	private Long kdId;
	private Long kuId;
	
	public BsUserDudaoId(){
		
	}

	public BsUserDudaoId(Long kdId, Long kuId) {
		super();
		this.kdId = kdId;
		this.kuId = kuId;
	}

	public Long getKdId() {
		return kdId;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
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
		if (!(other instanceof BsUserDudaoId))
			return false;
		BsUserDudaoId castOther = (BsUserDudaoId) other;

		return ((this.getKdId() == castOther.getKdId()) || (this.getKdId() != null && castOther.getKdId() != null && this.getKdId().equals(castOther.getKdId())))
				&& ((this.getKuId() == castOther.getKuId()) || (this.getKuId() != null && castOther.getKuId() != null && this.getKuId().equals(castOther.getKuId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getKdId() == null ? 0 : this.getKdId().hashCode());
		result = 37 * result + (getKuId() == null ? 0 : this.getKuId().hashCode());
		return result;
	}
}
