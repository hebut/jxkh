package com.uniwin.asm.personal.document;

import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.entity.DocList;
import com.uniwin.asm.personal.service.DocListService;


public class NewDocNameWindow extends BaseWindow {
	Textbox docname;
	DocList docList;
	DocListService doclistService;
	Button submit;
	private static final long serialVersionUID = 1L;
	@Override
	public void initShow() {
		initWindow();
		this.addForward(Events.ON_OK, submit, Events.ON_CLICK);
	}

	@Override
	public void initWindow() {
		if(docList==null){
			docList=(DocList) Executions.getCurrent().getAttribute("docList");
		}
		docname.setValue(docList.getDlName().substring(0,docList.getDlName().lastIndexOf(".")));
	}
	public void onClick$submit() throws InterruptedException{
		
		docList.setDlName(docname.getValue().trim()+docList.getDlName().substring(docList.getDlName().lastIndexOf(".")));
		doclistService.update(docList);
		Messagebox.show("修改成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		Events.postEvent(new Event(Events.ON_CHANGE, this));
		this.detach();
	}
	public void onClick$reset(){
		initWindow();
	}

}
