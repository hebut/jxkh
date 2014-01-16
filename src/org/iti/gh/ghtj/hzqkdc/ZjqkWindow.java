package org.iti.gh.ghtj.hzqkdc;

import java.util.List;

import org.iti.gh.entity.GhJszz;
import org.iti.gh.entity.GhZz;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ZjqkWindow extends BaseWindow {
	Listbox zzlist,jclist;
	Short type=(Short)Executions.getCurrent().getArg().get("type");//1 、出版教材 2、科研专著
	List zjlist=(List)Executions.getCurrent().getArg().get("zjlist");
	@Override
	public void initShow() {
		 zzlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				GhZz zz= (GhZz)arg1;
				arg0.setValue(zz);
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(zz.getZzMc());
				Listcell c3=new Listcell(zz.getZzKw());
				Listcell c4=new Listcell(zz.getZzPublitime());
				Listcell c5=new Listcell(zz.getZzZb());
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
				arg0.appendChild(c4);arg0.appendChild(c5);
			}
			 
		 });
		 jclist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				GhZz zz= (GhZz)arg1;
				arg0.setValue(zz);
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(zz.getZzMc());
				Listcell c3=new Listcell(zz.getZzKw());
				Listcell c4=new Listcell(zz.getZzPublitime());
				Listcell c5=new Listcell(zz.getZzZb());
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
				arg0.appendChild(c4);arg0.appendChild(c5);
			}
			 
		 });

	}

	@Override
	public void initWindow() {
		if(type==1){
			jclist.setModel(new ListModelList(zjlist));
			jclist.setVisible(true);
			zzlist.setVisible(false);
		}else if(type==2){
			zzlist.setModel(new ListModelList(zjlist));
			jclist.setVisible(false);
			zzlist.setVisible(true);
		}

	}

}
