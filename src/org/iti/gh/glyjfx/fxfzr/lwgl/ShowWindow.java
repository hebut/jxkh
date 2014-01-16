package org.iti.gh.glyjfx.fxfzr.lwgl;

import java.util.List;

import org.iti.gh.entity.GhYjfxlw;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class ShowWindow extends BaseWindow {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Listbox showlist;
	String type;
	UserService userService;
	@Override
	public void initShow() {
		showlist.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				GhYjfxlw lw=(GhYjfxlw) arg1;
				arg0.setValue(lw);
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				WkTUser user=(WkTUser)userService.get(WkTUser.class, lw.getKuId());
				Listcell c2=new Listcell(user.getKuName()+"");
				Listcell c3=new Listcell(type+"");
				Listcell c4=new Listcell(lw.getGlYear()+"");
				Listcell c5=new Listcell(lw.getGlNum()+"");
				Listcell c6=new Listcell(lw.getGlContent());
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
			}});
	}

	@Override
	public void initWindow() {
	    List list=(List)Executions.getCurrent().getAttribute("list");
	     type=(String)Executions.getCurrent().getAttribute("type");
		showlist.setModel(new ListModelList(list));

	}

}
