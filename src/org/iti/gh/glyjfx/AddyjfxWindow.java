package org.iti.gh.glyjfx;

import java.util.List;

import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.YjfxService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Textbox;

public class AddyjfxWindow extends BaseWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Textbox name;
	YjfxService yjfxService;

	@Override
	public void initShow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initWindow() {
		// TODO Auto-generated method stub

	}
	public void onClick$submit(){
		List<GhYjfx> listyjfx=yjfxService.findByKdidAndGyname(getXyUserRole().getKdId(), name.getValue().trim());
		if(listyjfx!=null && listyjfx.size()!=0){
			throw new WrongValueException(name, "该研究方向已经存在!"); 
		}else{
			GhYjfx yjfx=new GhYjfx();
			yjfx.setGyName(name.getValue());
			yjfx.setKdId(getXyUserRole().getKdId());
			yjfxService.save(yjfx);
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
