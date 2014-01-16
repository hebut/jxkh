package org.iti.gh.ghtj.hzqkdc;

import java.util.List;

import org.iti.gh.entity.GhXm;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class XmxqWindow extends BaseWindow {

	List xmlist=(List)Executions.getCurrent().getArg().get("xmlist");
	Listbox kjxm;
	@Override
	public void initShow() {
		kjxm.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0,Object arg1){
				GhXm xm=(GhXm)arg1;
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(xm.getKyMc());
				Listcell c3=new Listcell(xm.getKyLy());
				Listcell c4=new Listcell(xm.getKyProman());
				Listcell c5=new Listcell(xm.getKyLxsj());
				Listcell c6=new Listcell(xm.getKyKssj());
				Listcell c7=new Listcell(xm.getKyProstaffs());
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
				arg0.appendChild(c4);arg0.appendChild(c5);arg0.appendChild(c6);
				arg0.appendChild(c7);
				
			}
		});

	}

	@Override
	public void initWindow() {
		if(xmlist!=null&&xmlist.size()>0){
			kjxm.setModel(new ListModelList(xmlist));
		}else{
			kjxm.setModel(new ListModelList());
		}
		

	}

}
