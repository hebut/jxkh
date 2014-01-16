package org.iti.gh.ghtj.hzqkdc;

import java.util.List;

import org.iti.gh.entity.GhJlhz;
import org.iti.gh.entity.GhJxbg;
import org.iti.gh.entity.GhXshy;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.XshyService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class JlhzWindow extends BaseWindow {

	List hylist=(List)Executions.getCurrent().getArg().get("hylist");
	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
//	List hzlist=(List)Executions.getCurrent().getArg().get("hzlist");
	Listbox xshy,jxbg,jlhz;
	XshyService xshyService;
	JlhzService jlhzService;
	JxbgService jxbgService;
	@Override
	public void initShow() {
		xshy.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0,Object arg1){
//				GhXshy lwzl=(GhXshy)arg1;
//				Listcell c1=new Listcell(arg0.getIndex()+1+"");
//				Listcell c2=new Listcell(lwzl.getHyMc());
//				Listcell c3=new Listcell(lwzl.getHySj());
//				Listcell c4=new Listcell(lwzl.getHyZrs()+"人");
//				Listcell c5=new Listcell(lwzl.getHyJwrs()+"人");
//				Listcell c6=new Listcell(lwzl.getUser().getKuName());
//				arg0.appendChild(c1);arg0.appendChild(c2);
//				arg0.appendChild(c3);arg0.appendChild(c4);
//				arg0.appendChild(c5);arg0.appendChild(c6);
				String mc=(String)arg1;
				List xslist=xshyService.findByMc(mc, GhXshy.IFCJ_YES,GhXshy.AUDIT_YES);
				GhXshy lwzl=(GhXshy)xslist.get(0);
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(lwzl.getHyMc());
				Listcell c3=new Listcell(lwzl.getHySj());
				Listcell c4=new Listcell(lwzl.getHyZrs()+"人");
				Listcell c5=new Listcell(lwzl.getHyJwrs()+"人");
				String name=null;
				for(int i=0;i<xslist.size();i++){
					GhXshy xs=(GhXshy)xslist.get(i);
					name=name+","+xs.getUser().getKuName();
				}
				Listcell c6=new Listcell(name.split("null,")[1]);
				arg0.appendChild(c1);arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4);
				arg0.appendChild(c5);arg0.appendChild(c6);
			}
		});
		jxbg.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0,Object arg1){
				GhJxbg lwzl=(GhJxbg)arg1;
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(lwzl.getJxJxmc());
				Listcell c3=new Listcell(lwzl.getJxHymc());
				Listcell c4=new Listcell(lwzl.getJxSj());
				Listcell c5=new Listcell(lwzl.getJxBgmc());
				Listcell c6=new Listcell(lwzl.getUser().getKuName());
				arg0.appendChild(c1);arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4);
				arg0.appendChild(c5);arg0.appendChild(c6);
			}
		});
		jlhz.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0,Object arg1){
//				GhJlhz lwzl=(GhJlhz)arg1;
//				Listcell c1=new Listcell(arg0.getIndex()+1+"");
//				Listcell c2=new Listcell(lwzl.getHzMc());
//				Listcell c3=new Listcell(lwzl.getHzSj());
//				Listcell c4=new Listcell(lwzl.getHzDx());
//				Listcell c5=new Listcell(lwzl.getUser().getKuName());
//				arg0.appendChild(c1);arg0.appendChild(c2);
//				arg0.appendChild(c3);arg0.appendChild(c4);
//				arg0.appendChild(c5);
				String mc=(String)arg1;
				List hzlist=jlhzService.findByMc(mc, GhXshy.IFCJ_YES,GhXshy.AUDIT_YES);
				GhJlhz lwzl=(GhJlhz)hzlist.get(0);
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				Listcell c2=new Listcell(lwzl.getHzMc());
				Listcell c3=new Listcell(lwzl.getHzSj());
				Listcell c4=new Listcell(lwzl.getHzDx());
				String name=null;
				for(int i=0;i<hzlist.size();i++){
					GhJlhz xs=(GhJlhz)hzlist.get(i);
					name=name+","+xs.getUser().getKuName();
				}
				Listcell c5=new Listcell(name.split("null,")[1]);
				arg0.appendChild(c1);arg0.appendChild(c2);
				arg0.appendChild(c3);arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
	}

	@Override
	public void initWindow() {
		
			if(lx==Short.parseShort("1")){
				if(hylist!=null){
					xshy.setModel(new ListModelList(hylist));
				}else{
					xshy.setModel(new ListModelList());
				}
				xshy.setVisible(true);
				jxbg.setVisible(false);
				jlhz.setVisible(false);
			}else if(lx==Short.parseShort("2")){
				if(hylist!=null){
				    jxbg.setModel(new ListModelList(hylist));
				}else{
					jxbg.setModel(new ListModelList());
				}
				xshy.setVisible(false);
				jxbg.setVisible(true);
				jlhz.setVisible(false);
			}else if(lx==Short.parseShort("3")){
				if(hylist!=null){
					jlhz.setModel(new ListModelList(hylist));
				}else{
					jlhz.setModel(new ListModelList());
				}
				xshy.setVisible(false);
				jxbg.setVisible(false);
				jlhz.setVisible(true);
			}
		
		}
	

}
