package org.iti.xypt.personal.notice;

import java.util.List;

public class LongTime {
	String name;
	Long beginTime;
	Long endTime;
	List mlist;

	public LongTime(String name, Long beginTime, Long endTime) {
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.name = name;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getMlist() {
		return mlist;
	}

	public void setMlist(List mlist) {
		this.mlist = mlist;
	}
}
