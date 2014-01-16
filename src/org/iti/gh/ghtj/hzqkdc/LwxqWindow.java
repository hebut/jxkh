package org.iti.gh.ghtj.hzqkdc;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhQklw;
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

public class LwxqWindow extends BaseWindow {

	Listbox hylwlist,qklwlist;
	Short type=(Short)Executions.getCurrent().getArg().get("type");//1 、会议论文 2、期刊论文
	List lwlist=(List)Executions.getCurrent().getArg().get("lwlist");
	@Override
	public void initShow() {
		hylwlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhHylw lwzl=(GhHylw)arg1;
				Listcell c0=new Listcell(arg0.getIndex()+1+"");
				Listcell c1=new Listcell(lwzl.getLwMc());
				Listcell c2=new Listcell(lwzl.getLwTime());
				Listcell c3=new Listcell(lwzl.getLwKw());
				Listcell c4=new Listcell(lwzl.getLwFbsj());
				Listcell c5=new Listcell(lwzl.getLwAll());
				arg0.appendChild(c0);arg0.appendChild(c1);arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4);arg0.appendChild(c5);
//				arg0.appendChild(c6); arg0.appendChild(c7);
			}
			
		});
		qklwlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhQklw lw = (GhQklw) arg1;
				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				//论文名称
				Listcell c2 = new Listcell(lw.getLwMc());
				//刊物名称
				Listcell c3 = new Listcell(lw.getLwKw());
				//发表时间
				Listcell c4 = new Listcell(lw.getLwFbsj());
				//页码范围
				Listcell c5 = new Listcell(lw.getLwAll());
//				if(lw.getLwCenter().shortValue() == GhQklw.LWHX_NO.shortValue() ){
//					c5.setLabel("否");
//				}else{
//					c5.setLabel("是");
//				}
				//作者
				Listcell c6= new Listcell(lw.getLwPages());
//				//ISSN/CN
//				Listcell c7 = new Listcell(lw.getLwIssn());
//				//项目资助
//				Listcell c8= new Listcell(lw.getUser().getKuName());
//			    InnerButton bt=new InnerButton("查看");
//			    
//				 bt.addEventListener(Events.ON_CLICK, new EventListener(){
//					public void onEvent(Event arg0) throws Exception {
//						 KyxmzzlistWindow cgWin = (KyxmzzlistWindow) Executions.createComponents
//							("/admin/personal/data/teacherinfo/kyxmzzlist.zul", null, null);
//						 	cgWin.setKuid(lw.getKuId());
//							cgWin.setQklw(lw);
//							cgWin.initWindow();
//							cgWin.doHighlighted();
//					}});
//				 c8.appendChild(bt);
					
			
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6); 
//				arg0.appendChild(c7);
//				arg0.appendChild(c8);arg0.appendChild(c9); 
			}
		});

	}

	@Override
	public void initWindow() {
		if(type==1){
			hylwlist.setModel(new ListModelList(lwlist));
			hylwlist.setVisible(true);
			qklwlist.setVisible(false);
		}else if(type==2){
			qklwlist.setModel(new ListModelList(lwlist));
			hylwlist.setVisible(false);
			qklwlist.setVisible(true);
		}

	}

}
