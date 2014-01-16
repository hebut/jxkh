package com.uniwin.asm.personal.ui.data.teacherinfo.qtqk;


import org.iti.gh.entity.TeaThought;
import org.iti.gh.service.ThoughtService;
import org.iti.jxgl.ui.listbox.YearListbox;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.uniwin.framework.entity.WkTUser;

public class NdzjWindow extends BaseWindow {

	
	FCKeditor editor;
	YearListbox yearlist;
	ThoughtService thoughtService;
	WkTUser user;
	public void initShow() {
		initWindow();

	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
//		yearlist.initCqYearlist(user.getDept().getKdSchid());
		yearlist.initYearlist();
		onClick$query();
		if(yearlist.getSelectedItem()!=null){
			TeaThought tt=thoughtService.findByKuidAndYear(user.getKuId(),yearlist.getSelectedItem().getLabel());
			if(tt!=null&&tt.getTtContent()!=null){
				editor.setValue(tt.getTtContent());
			}else{
				editor.setValue("");
			}
		}
	}
	public void onClick$submit(){
		if(yearlist.getSelectedItem()==null){
			
		}else{
		TeaThought tt=thoughtService.findByKuidAndYear(user.getKuId(),yearlist.getSelectedItem().getLabel());
		if(tt!=null){
			tt.setTtContent(editor.getValue());
			thoughtService.update(tt);
			try {
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			tt=new TeaThought();
			tt.setKuId(user.getKuId());
			tt.setTtYear(yearlist.getSelectedItem().getLabel());
			tt.setTtContent(editor.getValue());
			thoughtService.save(tt);
			try {
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}
		
	}
	public void onClick$query(){
		if(yearlist.getSelectedItem()!=null){
			TeaThought tt=thoughtService.findByKuidAndYear(user.getKuId(),yearlist.getSelectedItem().getLabel());
			if(tt!=null&&tt.getTtContent()!=null){
				editor.setValue(tt.getTtContent());
			}else{
				editor.setValue("");
			}
		}
	}
}
