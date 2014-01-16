package org.iti.jxgl.entity;

/**
 * JxviewCoursetype entity. @author MyEclipse Persistence Tools
 */

public class JxviewCoursetype implements java.io.Serializable {

	// Fields

	private JxviewCoursetypeId id;

	// Constructors

	/** default constructor */
	public JxviewCoursetype() {
	}

	/** full constructor */
	public JxviewCoursetype(JxviewCoursetypeId id) {
		this.id = id;
	}

	// Property accessors

	public JxviewCoursetypeId getId() {
		return this.id;
	}

	public void setId(JxviewCoursetypeId id) {
		this.id = id;
	}

}