/**
 * 
 */
package org.iti.bysj.ui.base;
import java.util.Date;

import org.iti.bysj.entity.BsGpunit;
import org.iti.bysj.service.GpunitService;


/**
 * @author DaLei
 * @version $Id: XzrBysjWindow.java,v 1.1 2011/08/31 07:03:09 ljb Exp $
 */
   public abstract class XzrBysjWindow extends BysjWindow{
	   
	private BsGpunit gpunit;

	GpunitService gpunitService;
	

	public void initArgs() {
		this.gpunit = gpunitService.findByKdidAndGpid(
				getXyUserRole().getKdId(), getGprocess().getBgId());
		if(gpunit!=null)
		System.out.println("系主任管理的毕设单位编号："+gpunit.getBuId());
	}

	public GpunitService getGpunitService() {
		return gpunitService;
	}

	public void setGpunitService(GpunitService gpunitService) {
		this.gpunitService = gpunitService;
	}

	public BsGpunit getGpunit() {
		return gpunit;
	}

	public void setGpunit(BsGpunit gpunit) {
		this.gpunit = gpunit;
	}

	
	public String isAble() {
		Date d=new Date();
		if(getGpunit()==null){
			return "您所在的系或专业并没有参加本次毕设过程";
		}else if(d.getTime()<getGprocess().getBgStart()){
			return"该毕设过程尚未开始";
		}
		return null;
	}
	
	
}
