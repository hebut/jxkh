package org.iti.gh.ghtj.hzqkdc;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhCg;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class CgxqWindow extends BaseWindow {
	Listbox hjcg;
	List cglist = (List) Executions.getCurrent().getArg().get("cglist");

	@Override
	public void initShow() {
		hjcg.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhCg cg = (GhCg) arg1;
				arg0.setValue(cg);
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(cg.getKyMc());
				// Listcell c2=new Listcell(cg.getKyLy()!=null?cg.getKyLy():"");
				Listcell c3 = new Listcell(cg.getKySj());
				Listcell c4 = new Listcell(cg.getKyHjmc());
				Listcell c5 = new Listcell(cg.getKyDj());
				Listcell c6 = new Listcell(cg.getKyPrizeper());
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				// arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
			}

		});

	}

	@Override
	public void initWindow() {
		System.out.println(cglist.size()+"传过来的》");
		hjcg.setModel(new ListModelList(cglist));
	}

}
