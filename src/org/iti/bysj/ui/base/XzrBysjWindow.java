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
		System.out.println("ϵ���ι���ı��赥λ��ţ�"+gpunit.getBuId());
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
			return "�����ڵ�ϵ��רҵ��û�вμӱ��α������";
		}else if(d.getTime()<getGprocess().getBgStart()){
			return"�ñ��������δ��ʼ";
		}
		return null;
	}
	
	
}
