package com.uniwin.asm.personal.document;

import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.entity.DocTree;
import com.uniwin.asm.personal.service.DocTreeService;

public class NewDocTreeWindow extends BaseWindow {
	Textbox doctname,doctinfo;
	private static final long serialVersionUID = 1L;
	private Long pid;
	private Long kuid;
	Button submit;
	DocTreeService doctreeService;
	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Override
	public void initShow() {
		initWindow();
		this.addForward(Events.ON_OK, submit, Events.ON_CLICK);
	}

	@Override
	public void initWindow() {
		
	}
	public void onClick$submit() throws InterruptedException{
		DocTree doctree=new DocTree();
		doctree.setDtName(doctname.getValue());
		doctree.setTotleDocument(0);
		doctree.setTotleSize(0L);
		doctree.setDtBz(doctinfo.getValue());
		doctree.setDtKuid(kuid);
		doctree.setDtpId(pid);
		doctreeService.save(doctree);
		Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
		Events.postEvent(new Event(Events.ON_CHANGE, this));
		this.detach();
	}
	public void onClick$reset(){
		initWindow();
	}

}
