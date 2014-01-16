package org.iti.jxkh.base;

import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.entity.Title;
import org.iti.xypt.service.XyptTitleService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class TitleSelectListbox extends Listbox implements AfterCompose {

	/**
	 * @author WeifangWang
	 */
	private static final long serialVersionUID = 1L;
	
	XyptTitleService xypttitleService;
	

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		this.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				Title t = (Title)arg1;
				arg0.setValue(t);
				arg0.setLabel(t.getTiName());
			}
		});
		//this.setSelectedIndex(0);
	}
	
	public void initFListbox() {
		List ftitlelist = new ArrayList();
		ftitlelist.clear();
		List ftileList = xypttitleService.findFirstTitles();
		if(ftileList.size() != 0) {
			for(int i=0;i<ftileList.size();i++) {
				Title title = (Title)ftileList.get(i);
				ftitlelist.add(title);
			}
		}
		this.setModel(new ListModelList(ftitlelist));	
	}
	
	public void initSListbox(String ptiId) {
		List stitlelist = new ArrayList();
		stitlelist.clear();
		List stitleList = xypttitleService.findByPtid(ptiId);
		if(stitleList.size() != 0) {
			for(int i=0;i<stitleList.size();i++) {
				Title title = (Title)stitleList.get(i);
				stitlelist.add(title);
			}
		}
		this.setModel(new ListModelList(stitlelist));		
	}
	
	public Title getSelectTitle() {
		Title title = (Title)this.getSelectedItem().getValue();
		return title;
	}
	
	public void setSelectTitle(String tid) {
		List titlelist = xypttitleService.findBytid(tid);
		if(titlelist.size() != 0) {
			Title title = (Title)titlelist.get(0);
		}
	}
}
