package org.iti.jxgl.entity;

/**
 * JxviewCoursetypeId entity. @author MyEclipse Persistence Tools
 */

public class JxviewCoursetypeId implements java.io.Serializable {

	// Fields

	private String jctId;
	private String oneName;
	private String twoName;

	// Constructors

	/** default constructor */
	public JxviewCoursetypeId() {
	}

	/** minimal constructor */
	public JxviewCoursetypeId(String jctId) {
		this.jctId = jctId;
	}

	/** full constructor */
	public JxviewCoursetypeId(String jctId, String oneName, String twoName) {
		this.jctId = jctId;
		this.oneName = oneName;
		this.twoName = twoName;
	}

	// Property accessors

	public String getJctId() {
		return this.jctId;
	}

	public void setJctId(String jctId) {
		this.jctId = jctId;
	}

	public String getOneName() {
		return this.oneName;
	}

	public void setOneName(String oneName) {
		this.oneName = oneName;
	}

	public String getTwoName() {
		return this.twoName;
	}

	public void setTwoName(String twoName) {
		this.twoName = twoName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof JxviewCoursetypeId))
			return false;
		JxviewCoursetypeId castOther = (JxviewCoursetypeId) other;

		return ((this.getJctId() == castOther.getJctId()) || (this.getJctId() != null
				&& castOther.getJctId() != null && this.getJctId().equals(
				castOther.getJctId())))
				&& ((this.getOneName() == castOther.getOneName()) || (this
						.getOneName() != null
						&& castOther.getOneName() != null && this.getOneName()
						.equals(castOther.getOneName())))
				&& ((this.getTwoName() == castOther.getTwoName()) || (this
						.getTwoName() != null
						&& castOther.getTwoName() != null && this.getTwoName()
						.equals(castOther.getTwoName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getJctId() == null ? 0 : this.getJctId().hashCode());
		result = 37 * result
				+ (getOneName() == null ? 0 : this.getOneName().hashCode());
		result = 37 * result
				+ (getTwoName() == null ? 0 : this.getTwoName().hashCode());
		return result;
	}

}