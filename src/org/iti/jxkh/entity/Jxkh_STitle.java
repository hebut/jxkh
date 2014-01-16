package org.iti.jxkh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * �û�ѡ����ҳ��ʾ����
 * @author wangweifang
 *
 */
@Entity
@Table(name = "Jxkh_STitle")
public class Jxkh_STitle implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7402395910892742629L;
	
	/**
	 * ����id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * �û�id
	 */
	@Column
	private Long userId;
	/**
	 * ��ѡ����id
	 */
	@Column
	private Long titleId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTitleId() {
		return titleId;
	}
	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jxkh_STitle other = (Jxkh_STitle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Jxkh_STitle [id=" + id + ", userId=" + userId + ", titleId=" + titleId + "]";
	}	
}
