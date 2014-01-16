package org.iti.gh.shxkjs.kyqk;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.XmService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class HylwWindow extends BaseWindow {

	
	Listbox hylwlist;
	
	HylwService hylwService ;
	XmService xmService;
	@Override
	public void initShow() {
		hylwlist.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhXm xm = (GhXm) arg1;
				arg0.setValue(xm);
				// 序号
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c2 = new Listcell(xm.getKyMc());
				// 项目来源
				Listcell c3 = new Listcell();
				if (xm.getKyLy() != null) {
					c3.setLabel(xm.getKyLy());
				}

				// 开始时间
				Listcell c4 = new Listcell();
				if (xm.getKyKssj() != null) {
					c4.setLabel(xm.getKyKssj());
				}
				// 结束时间
				Listcell c5 = new Listcell();
				if (xm.getKyJssj() != null) {
					c5.setLabel(xm.getKyJssj());
				}
				// 经费（万元）
				Listcell c6 = new Listcell();
				if (xm.getKyJf() != null) {
					c6.setLabel(xm.getKyJf() + "");
				}
				Listcell c7=new Listcell();
				InnerButton b=new InnerButton("审核");
				c7.appendChild(b);
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);
				arg0.appendChild(c4);arg0.appendChild(c5);arg0.appendChild(c6);
				arg0.appendChild(c7);
				
			}
		});
	}

	@Override
	public void initWindow() {
//		lwzlService.findByKdidAndLxAndState(getXyUserRole().getKdId(), GhLwzl.KYLW, GhLwzl.LWHY,GhLwzl.)
		hylwlist.setModel(new ListModelList(xmService.findByKdidAndLxAndTypeAndState(getXyUserRole().getKdId(), GhXm.KYXM, GhXm.AUDIT_NO)));
	}

}
