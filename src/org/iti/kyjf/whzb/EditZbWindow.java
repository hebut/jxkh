package org.iti.kyjf.whzb;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.iti.kyjf.entity.Kyzb;
import org.iti.kyjf.entity.Zbteacher;
import org.iti.kyjf.service.ZbService;
import org.iti.kyjf.service.ZbteacherService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;

public class EditZbWindow extends BaseWindow {

	Integer year=(Integer)Executions.getCurrent().getArg().get("year");
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Decimalbox zg,fg,zcqt,bd,sd,dsqt,gg,fgg,cjys,cj,tsxk,bszd,xyxs;
	//gkxs,qtxy,wfxs,wyxs,
	Decimalbox zdjs,zdxk,zdsys;
	ZbService zbService;
	ZbteacherService zbteacherService;
	Kyzb kyzb;
	 
	public Kyzb getKyzb() {
		return kyzb;
	}

	public void setKyzb(Kyzb kyzb) {
		this.kyzb = kyzb;
	}

	@Override
	public void initShow() {
   
	}

	@Override
	public void initWindow() {
		zg.setValue(kyzb.getZxzg()!=null?BigDecimal.valueOf(kyzb.getZxzg()):null);
		fg.setValue(kyzb.getZxfg()!=null?BigDecimal.valueOf(kyzb.getZxfg()):null);
		zcqt.setValue(kyzb.getZxzcqt()!=null?BigDecimal.valueOf(kyzb.getZxzcqt()):null);
		bd.setValue(kyzb.getZxbd()!=null?BigDecimal.valueOf(kyzb.getZxbd()):null);
		sd.setValue(kyzb.getZxSd()!=null?BigDecimal.valueOf(kyzb.getZxSd()):null);
		dsqt.setValue(kyzb.getZxDsqt()!=null?BigDecimal.valueOf(kyzb.getZxDsqt()):null);
		gg.setValue(kyzb.getZxgg()!=null?BigDecimal.valueOf(kyzb.getZxgg()):null);
		fgg.setValue(kyzb.getZxfgg()!=null?BigDecimal.valueOf(kyzb.getZxfgg()):null);
		cjys.setValue(kyzb.getZxcjys()!=null?BigDecimal.valueOf(kyzb.getZxcjys()):null);
		cj.setValue(kyzb.getZxcj()!=null?BigDecimal.valueOf(kyzb.getZxcj()):null);
//		gkxs.setValue(kyzb.getZxgkxs()!=null?BigDecimal.valueOf(kyzb.getZxgkxs()):null);
//		wfxs.setValue(kyzb.getZxwfxs()!=null?BigDecimal.valueOf(kyzb.getZxwfxs()):null);
//		wyxs.setValue(kyzb.getZxwyxs()!=null?BigDecimal.valueOf(kyzb.getZxwyxs()):null);
//		qtxy.setValue(kyzb.getZxqtxy()!=null?BigDecimal.valueOf(kyzb.getZxqtxy()):null);
		bszd.setValue(kyzb.getZxbszd()!=null?BigDecimal.valueOf(kyzb.getZxbszd()):null);
		tsxk.setValue(kyzb.getZxtsxk()!=null?BigDecimal.valueOf(kyzb.getZxtsxk()):null);
		xyxs.setValue(kyzb.getZxxyxs()!=null?BigDecimal.valueOf(kyzb.getZxxyxs()):null);
		  zdjs.setValue(kyzb.getZxzdjs()!=null?BigDecimal.valueOf(kyzb.getZxzdjs()):null);
	 	    zdxk.setValue(kyzb.getZxzdxk()!=null?BigDecimal.valueOf(kyzb.getZxzdxk()):null);
	 		zdsys.setValue(kyzb.getZxzdsys()!=null?BigDecimal.valueOf(kyzb.getZxzdsys()):null);
	}
   public void onClick$submit(){
	   kyzb.set年份(year);
	   kyzb.setKdId(kdid);
	   kyzb.setZxzg(Float.valueOf(zg.getValue()+""));
	   kyzb.setZxfg(Float.valueOf(fg.getValue()+""));
	   kyzb.setZxzcqt(Float.valueOf(zcqt.getValue()+""));
	   kyzb.setZxbd(Float.valueOf(bd.getValue()+""));
	   kyzb.setZxSd(Float.valueOf(sd.getValue()+""));
	   kyzb.setZxDsqt(Float.valueOf(dsqt.getValue()+""));
	   kyzb.setZxgg(Float.valueOf(gg.getValue()+""));
	   kyzb.setZxfgg(Float.valueOf(fgg.getValue()+""));
	   kyzb.setZxcjys(Float.valueOf(cjys.getValue()+""));
	   kyzb.setZxcj(Float.valueOf(cj.getValue()+""));
	   //kyzb.setZxgkxs(Float.valueOf(gkxs.getValue()+""));
	   //kyzb.setZxwfxs(Float.valueOf(wfxs.getValue()+""));
	   //kyzb.setZxwyxs(Float.valueOf(wyxs.getValue()+""));
	   //kyzb.setZxqtxy(Float.valueOf(qtxy.getValue()+""));
	   kyzb.setZxbszd(Float.valueOf(bszd.getValue()+""));
	   kyzb.setZxtsxk(Float.valueOf(tsxk.getValue()+""));
	   kyzb.setZxxyxs(xyxs.getValue()!=null?Float.valueOf(xyxs.getValue()+""):0);
	   kyzb.setZxzdjs(zdjs.getValue()!=null?Float.valueOf(zdjs.getValue()+""):0);
	   kyzb.setZxzdxk(zdxk.getValue()!=null?Float.valueOf(zdxk.getValue()+""):0);
	   kyzb.setZxzdsys(zdsys.getValue()!=null?Float.valueOf(zdsys.getValue()+""):0);
	   zbService.update(kyzb);
	   try {
		Messagebox.show("修改成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}  
	Events.postEvent(Events.ON_CHANGE, this, null);
	this.detach();
	  
   }
   public void onClick$close(){
		this.detach();
   }
}
