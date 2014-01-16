package org.iti.jxkh.honour;

import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.service.RychService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

public class AuditAdviceWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Radio pass,out;
	private Textbox advice;
	private Hbox buttonHbox;
	public Toolbarbutton save,close;
	
	Jxkh_Honour honour;
	
	private RychService rychService;
	Long kuId;
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);			
	}
	
	public void initWindow(Jxkh_Honour honour,Long kuId) {
		this.honour = honour;
		/**判断是否是荣誉称号的提交者*/
		//是该荣誉的提交者，则只能查看意见，其他的功能都不能有
		if(honour.getKuId() == kuId) {
			advice.setValue(honour.getAdvice());
			advice.setReadonly(true);
			buttonHbox.setVisible(false);
		}else {
			advice.setReadonly(false);
			buttonHbox.setVisible(true);
			save.setVisible(true);
		}
	}
	
	public void onClick$save() {
		if(honour != null) {
			if(pass.isChecked()) {
				honour.setState(Jxkh_Honour.BUSI_PASS);
				if(advice.getValue() != null) {
					honour.setAdvice(advice.getValue());
				}
				rychService.update(honour);
			}else {
				honour.setState(Jxkh_Honour.BUSI_OUT);
				if(advice.getValue() != null) {
					honour.setAdvice(advice.getValue());
				}
				rychService.update(honour);
			}			
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
	
	public void onClick$close() {
		this.detach();
	}
	

}
