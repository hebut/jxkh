package org.iti.xypt.entity;

public class XyFudao implements java.io.Serializable {

	private XyFudaoId id;

	public XyFudao() {

	}

	public XyFudao(XyFudaoId id) {
		super();
		this.id = id;
	}

	public XyFudaoId getId() {
		return id;
	}

	public void setId(XyFudaoId id) {
		this.id = id;
	}
}
