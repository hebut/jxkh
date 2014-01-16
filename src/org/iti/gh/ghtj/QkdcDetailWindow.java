package org.iti.gh.ghtj;

import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

public class QkdcDetailWindow extends Window implements AfterCompose {

	
	Listbox detaillist;
	Listheader header;
	Groupbox tsGroup;
	Label tsl;
	Label result;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		
		detaillist.setItemRenderer(new ListitemRenderer(){ 
			public void render(Listitem arg0, Object arg1) throws Exception {
				QkdcItem qitem=(QkdcItem)arg1;
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(qitem.getKuName());
				Listcell c3=new Listcell(qitem.getValue());
				
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
			} 
		});
	}
	
	public void initWindow(List itemList,String ts,String head,String res){
		
		header.setLabel(head);
		
		detaillist.setModel(new ListModelList(itemList));
		
		if(ts==null||ts.length()==0){
			tsGroup.setVisible(false);
		}else{
			tsGroup.setVisible(true);
			tsl.setValue(ts);
		}
		result.setValue(res);
		
	} 

}
