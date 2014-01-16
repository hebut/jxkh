package org.iti.xypt.personal.notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uniwin.common.util.ConvertUtil;

public class LongTimeFactory {
	public static final Long dayTime = 1000 * 60 * 60 * 24L;

	public static LongTime getToday() {
		Date now = new Date();
		Date today = ConvertUtil.convertDate(ConvertUtil.convertString(now));
		return new LongTime("今天", today.getTime(), now.getTime());
	}

	public static LongTime getYesterday() {
		Date now = new Date();
		Date today = ConvertUtil.convertDate(ConvertUtil.convertString(now));
		return new LongTime("昨天", today.getTime() - dayTime, today.getTime());
	}

	public static LongTime getBeforeYesterday() {
		Date now = new Date();
		Date today = ConvertUtil.convertDate(ConvertUtil.convertString(now));
		return new LongTime("前天", today.getTime() - 2 * dayTime, today.getTime() - dayTime);
	}

	public static LongTime getThreeDaysBefore() {
		Date now = new Date();
		Date today = ConvertUtil.convertDate(ConvertUtil.convertString(now));
		return new LongTime("三天前", 0L, today.getTime() - 2 * dayTime);
	}

	public static List getLongTimes() {
		List longs = new ArrayList();
		longs.add(getToday());
		longs.add(getYesterday());
		longs.add(getBeforeYesterday());
		longs.add(getThreeDaysBefore());
		return longs;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
}
