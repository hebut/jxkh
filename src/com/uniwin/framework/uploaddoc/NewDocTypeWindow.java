package com.uniwin.framework.uploaddoc;

import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTDocType;
import com.uniwin.framework.service.DocTypeService;

public class NewDocTypeWindow extends BaseWindow {

	private static final long serialVersionUID = 1L;
	Textbox doctype;
	private DocTypeService doctypeService;
	@Override
	public void initShow() {
		initWindow();
	}

	@Override
	public void initWindow() {
		
	}
	public void onClick$submit() throws InterruptedException{
		WkTDocType wktdoctype=new WkTDocType();
		wktdoctype.setDoctKdid(this.getXyUserRole().getKdId());
		wktdoctype.setDoctName(doctype.getValue());
		doctypeService.save(wktdoctype);
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		Events.postEvent(new Event(Events.ON_CHANGE, this));
		this.detach();
	}
	public void onClick$reset(){
		initWindow();
	}

}
