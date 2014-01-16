package org.iti.kyjf.whxkqk;

import org.iti.kyjf.entity.Zbdeptype;
import org.iti.kyjf.service.ZbdeptypeService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;

public class AddxkqkWindow extends BaseWindow {

	Radiogroup tsxk,zdjs,bsd,zdxk,zdsys;
	Zbdeptype zbdeptype;
	Integer year=(Integer)Executions.getCurrent().getArg().get("year");
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	ZbdeptypeService zbdeptypeService;
	

	public Zbdeptype getZbdeptype() {
		return zbdeptype;
	}

	public void setZbdeptype(Zbdeptype zbdeptype) {
		this.zbdeptype = zbdeptype;
	}

	@Override
	public void initShow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initWindow() {
		if(zbdeptype!=null){
			tsxk.setSelectedIndex(zbdeptype.getZdTsxk()!=null?zbdeptype.getZdTsxk():1);
			zdjs.setSelectedIndex(zbdeptype.getZdZdjs()!=null?zbdeptype.getZdZdjs():1);
			bsd.setSelectedIndex(zbdeptype.getZdBsd()!=null?zbdeptype.getZdBsd():1);
			zdxk.setSelectedIndex(zbdeptype.getZdZdxk()!=null?zbdeptype.getZdZdxk():1);
			zdsys.setSelectedIndex(zbdeptype.getZdZdsys()!=null?zbdeptype.getZdZdsys():1);
		}

	}
	public void onClick$save(){
		Zbdeptype deptype=new Zbdeptype();
		deptype.setKdId(kdid);
		deptype.setZxyear(year);
		deptype.setZdBsd(Short.parseShort(bsd.getSelectedIndex()+""));
		deptype.setZdTsxk(Short.parseShort(tsxk.getSelectedIndex()+""));
		deptype.setZdZdjs(Short.parseShort(zdjs.getSelectedIndex()+""));
		deptype.setZdZdxk( Short.parseShort(zdxk.getSelectedIndex()+""));
		deptype.setZdZdsys(Short.parseShort(zdsys.getSelectedIndex()+""));
		zbdeptypeService.save(deptype);
		try {
			Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			System.out.println("保存信息异常");
		}
		this.detach();
		Events.postEvent(Events.ON_CHANGE, this, null);
		
	}
	public void onClick$close(){
		this.detach();
	}
}
