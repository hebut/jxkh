/**
 * 
 */
package org.iti.bysj.ui.base;

import org.zkoss.zul.Toolbarbutton;

/**
 * <li>项目名称: xypt
 * <li>功能描述: 该文件的功能描述
 * <li>版权: Copyright (c) 2000-2007 UniWin Co. Ltd.
 * <li>公司: 中信联信息技术有限公司
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
