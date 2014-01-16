/**
 * 
 */
package org.iti.bysj.ui.base;

import org.iti.bysj.entity.BsGproces;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

/**
 * <li>��Ŀ����: xypt
 * <li>��������: ���ļ��Ĺ�������
 * <li>��Ȩ: Copyright (c) 2000-2007 UniWin Co. Ltd.
 * <li>��˾: ��������Ϣ�������޹�˾
 *
 * @author DaLei
 * @version $Id: BysjWindow.java,v 1.1 2011/08/31 07:03:09 ljb Exp $
 */
public abstract class BysjWindow  extends BaseWindow{
	
	public abstract void initArgs();
	

	private BsGproces gprocess;

	public BsGproces getGprocess() {
		return gprocess;
	}

	public void setGprocess(BsGproces gprocess) {
		this.gprocess = gprocess;
	}
	
	public abstract String isAble();
	
	public void showErrorPanel(String width,String title,String errorMessage){
		ErrorWindow ewin=(ErrorWindow)Executions.createComponents("/admin/bysj/common/error.zul", this,null);
		ewin.setTitle(title);
		if(width!=null)
		ewin.setWidth(width);
		ewin.setErrorMessage(errorMessage);
	}
	
	public void showErrorPanel(String width,String title,String errorMessage,Component  targetBefore){
		if(targetBefore==null){
			showErrorPanel(width,title,errorMessage);
		}
		ErrorWindow ewin=(ErrorWindow)Executions.createComponents("/admin/bysj/common/error.zul", null,null);
		ewin.setTitle(title);
		if(width!=null)
		ewin.setWidth(width);
		ewin.setErrorMessage(errorMessage);
		this.insertBefore(ewin, targetBefore);
	}
}
