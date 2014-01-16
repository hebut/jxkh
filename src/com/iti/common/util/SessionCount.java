package com.iti.common.util;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SessionCount implements HttpSessionListener{
	private static Log log=LogFactory.getLog(SessionCount.class);
	private static AtomicInteger count=new AtomicInteger();
	public static final int getCount() {
		return count.get();
	}
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		count.getAndIncrement();
		if(log.isDebugEnabled()){
			log.debug(count.get());
		}
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		count.getAndDecrement();
		if(log.isDebugEnabled()){
			log.debug(count.get());
		}
	}
}
