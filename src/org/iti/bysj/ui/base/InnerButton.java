/**
 * 
 */
package org.iti.bysj.ui.base;

import org.zkoss.zul.Toolbarbutton;

/**
 * <li>��Ŀ����: xypt
 * <li>��������: ���ļ��Ĺ�������
 * <li>��Ȩ: Copyright (c) 2000-2007 UniWin Co. Ltd.
 * <li>��˾: ��������Ϣ�������޹�˾
 *
 * @author DaLei
 * @version $Id: InnerButton.java,v 1.1 2011/08/31 07:03:09 ljb Exp $
 */
public class InnerButton extends Toolbarbutton {

	/**
	 * 
	 */
	public InnerButton() {
		super();
		 setSty();
	}

	/**
	 * @param label
	 * @param image
	 */
	public InnerButton(String label, String image) {
		super(label, image);
		 setSty();
	}

	/**
	 * @param label
	 */
	public InnerButton(String label) {
		super(label);
		setSty();
	}
	
	private void setSty(){
		this.setStyle("color:blue");
	}
  
}
