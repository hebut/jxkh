package org.iti.gh.glyjfx;

import java.util.List;

import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.YjfxService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Textbox;

public class EdityjfxWindow extends BaseWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GhYjfx yjfx;
	Textbox name;
	YjfxService yjfxService;

	public GhYjfx getYjfx() {
		return yjfx;
	}

	public void setYjfx(GhYjfx yjfx) {
		this.yjfx = yjfx;
	}

	@Override
	public void initShow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initWindow() {
		name.setValue(yjfx.getGyName());

	}
	public void onClick$submit(){
		List listyjfx=yjfxService.findByKdidAndGynameAndNotGyid(getXyUserRole().getKdId(), name.getValue().trim(), yjfx.getGyId());
		if(listyjfx!=null && listyjfx.size()!=0){
			throw new WrongValueException(name, "该研究方向已经存在!"); 
		}else{
			yjfx.setGyName(name.getValue());
			yjfxService.update(yjfx);
			Events.postEvent(Events.ON_CHANGE, this, null);
		}
	}
	public void onClick$reset(){
		initWindow();
	}
	public void onClick$close(){
		this.detach();
	}

}
