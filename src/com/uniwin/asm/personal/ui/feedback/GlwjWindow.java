package com.uniwin.asm.personal.ui.feedback;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Window;
public class GlwjWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Listcell bswjtz;
	Listcell gzglgd;
	Listcell gzywgf;
	Listcell gzssxz;
	Listcell gzlhdbf;

	

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
			InnerButton bswj=new InnerButton("毕设文件通知");
			bswj.setHref("/admin/feedback/glwj/bswjtz.htm");
			bswj.setTarget("_blank");
			InnerButton gzgl=new InnerButton("工作管理规定");
			gzgl.setHref("/admin/feedback/glwj/gzglgd.htm");
			gzgl.setTarget("_blank");
			InnerButton gzyw=new InnerButton("工作业务规范");
			gzyw.setHref("/admin/feedback/glwj/gzywgf.htm");
			gzyw.setTarget("_blank");
			InnerButton gzss=new InnerButton("工作实施细则");
			gzss.setHref("/admin/feedback/glwj/gzssxz.htm");
			gzss.setTarget("_blank");
			InnerButton gzl=new InnerButton("工作量核定办法");
			gzl.setHref("/admin/feedback/glwj/gzlhdbf.htm");
			gzl.setTarget("_blank");
			bswjtz.appendChild(bswj);
			gzglgd.appendChild(gzgl);
			gzywgf.appendChild(gzyw);
			gzssxz.appendChild(gzss);
			gzlhdbf.appendChild(gzl);
		
	}

}
