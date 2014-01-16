package org.iti.kyjf.whzb;

import java.math.BigDecimal;
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
import org.zkoss.zul.Panel;

public class AddZbWindow extends BaseWindow {

	Integer year=(Integer)Executions.getCurrent().getArg().get("year");
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Decimalbox zg,fg,zcqt,bd,sd,dsqt,gg,fgg,cjys,cj,tsxk,bszd,xyxs;//xyxs xueyuanxishu
	//gkxs,qtxy,wfxs,wyxs,
	Decimalbox zdjs,zdxk,zdsys;
	ZbService zbService;
	ZbteacherService zbteacherService;
	Kyzb kyzb;
	Panel xspanel;
	
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
     if(kyzb!=null){
    	xspanel.setTitle("去年的指标系数如下，根据情况修改之后点击“提交”按钮");
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
// 		gkxs.setValue(kyzb.getZxgkxs()!=null?BigDecimal.valueOf(kyzb.getZxgkxs()):null);
// 		wfxs.setValue(kyzb.getZxwfxs()!=null?BigDecimal.valueOf(kyzb.getZxwfxs()):null);
// 		wyxs.setValue(kyzb.getZxwyxs()!=null?BigDecimal.valueOf(kyzb.getZxwyxs()):null);
// 		qtxy.setValue(kyzb.getZxqtxy()!=null?BigDecimal.valueOf(kyzb.getZxqtxy()):null);
 		bszd.setValue(kyzb.getZxbszd()!=null?BigDecimal.valueOf(kyzb.getZxbszd()):null);
 		tsxk.setValue(kyzb.getZxtsxk()!=null?BigDecimal.valueOf(kyzb.getZxtsxk()):null);
 		xyxs.setValue(kyzb.getZxxyxs()!=null?BigDecimal.valueOf(kyzb.getZxxyxs()):null);
 	    zdjs.setValue(kyzb.getZxzdjs()!=null?BigDecimal.valueOf(kyzb.getZxzdjs()):null);
 	    zdxk.setValue(kyzb.getZxzdxk()!=null?BigDecimal.valueOf(kyzb.getZxzdxk()):null);
 		zdsys.setValue(kyzb.getZxzdsys()!=null?BigDecimal.valueOf(kyzb.getZxzdsys()):null);
     }
	}
   public void onClick$submit(){
	   Kyzb zb=new Kyzb();
	   zb.set年份(year);
	   zb.setKdId(kdid);
	   zb.setZxzg(bd.getValue()!=null?Float.valueOf(zg.getValue()+""):0);
	   zb.setZxfg(fg.getValue()!=null?Float.valueOf(fg.getValue()+""):0);
	   zb.setZxzcqt(zcqt.getValue()!=null?Float.valueOf(zcqt.getValue()+""):0);
	   zb.setZxbd(bd.getValue()!=null?Float.valueOf(bd.getValue()+""):0);
	   zb.setZxSd(sd.getValue()!=null?Float.valueOf(sd.getValue()+""):0);
	   zb.setZxDsqt(dsqt.getValue()!=null?Float.valueOf(dsqt.getValue()+""):0);
	   zb.setZxgg(gg.getValue()!=null?Float.valueOf(gg.getValue()+""):0);
	   zb.setZxfgg(fgg.getValue()!=null?Float.valueOf(fgg.getValue()+""):0);
	   zb.setZxcjys(cjys.getValue()!=null?Float.valueOf(cjys.getValue()+""):0);
	   zb.setZxcj(cj.getValue()!=null?Float.valueOf(cj.getValue()+""):0);
	   //zb.setZxgkxs(gkxs.getValue()!=null?Float.valueOf(gkxs.getValue()+""):0);
	   //zb.setZxwfxs(wfxs.getValue()!=null?Float.valueOf(wfxs.getValue()+""):0);
	   //zb.setZxwyxs(wyxs.getValue()!=null?Float.valueOf(wyxs.getValue()+""):0);
	   //zb.setZxqtxy(qtxy.getValue()!=null?Float.valueOf(qtxy.getValue()+""):0);
	   zb.setZxbszd(bszd.getValue()!=null?Float.valueOf(bszd.getValue()+""):0);
	   zb.setZxtsxk(tsxk.getValue()!=null?Float.valueOf(tsxk.getValue()+""):0);
	   zb.setZxxyxs(xyxs.getValue()!=null?Float.valueOf(xyxs.getValue()+""):0);
	   zb.setZxzdjs(zdjs.getValue()!=null?Float.valueOf(zdjs.getValue()+""):0);
	   zb.setZxzdxk(zdxk.getValue()!=null?Float.valueOf(zdxk.getValue()+""):0);
	   zb.setZxzdsys(zdsys.getValue()!=null?Float.valueOf(zdsys.getValue()+""):0);
	   zbService.save(zb);
	   //将上一年的教师添加进来
	   List tealist=zbteacherService.findByYear(year-1, kdid, "", "");
	   if(tealist!=null){
		   for(int i=0;i<tealist.size();i++){
			   Zbteacher teacher=(Zbteacher)tealist.get(i);
			   List yylist=zbteacherService.findByYear(year, kdid, teacher.getTeacher().getThId(), "");
			   if(yylist==null||yylist.size()==0){
				   teacher.setZtYear(year);
				   zbteacherService.save(teacher);
			   }
		   }
	   }
	   try {
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}   
	this.detach();
	Events.postEvent(Events.ON_CHANGE, this, null);
	
   }
   public void onClick$close(){
		this.detach();
   }
}
