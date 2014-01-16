package com.uniwin.framework.uploaddoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iti.xypt.entity.XyNUrd;
import org.iti.xypt.service.XyUserRoleService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTDoc;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.DepartmentService;
import com.uniwin.framework.service.DocService;
import com.uniwin.framework.service.DocTypeService;

public class LoadWindow extends BaseWindow {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Listbox mubanlist;
	Textbox searchname;
	Button search;
	DepartmentService departmentService;
	DocTypeService docTypeService;
	DocService docService;
	private XyUserRoleService xyUserRoleService;
	private List<WkTDoc> firstList=new ArrayList<WkTDoc>();
	@Override
	public void initShow() {
		initWindow();
		mubanlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				 final  WkTDoc bf=(WkTDoc)arg1;
				 Listcell c1=new Listcell(arg0.getIndex()+1+"");
				 Listcell c2=new Listcell(bf.getDocInfo());
				 Listcell c3=new Listcell(((int)(bf.getDocSize()/1024))+"KB");
				 Listcell c4=new Listcell(bf.getWktdoctype().getDoctName());
				 Listcell c5=new Listcell();
					 Image im=new Image();
					 im.setSrc("/admin/image/bysj/save.jpg");
					 im.addEventListener(Events.ON_CLICK, new EventListener(){
						public void onEvent(Event arg0) throws Exception {
							downloadFile(bf.getDocPath(),bf.getDocName());
						}						 
					 });
					 c5.appendChild(im);
					 Listcell c6=new Listcell();
						if(bf.getDocLevel()==WkTDoc.ULevel) c6.setLabel("校级");
						if(bf.getDocLevel()==WkTDoc.DLevel) c6.setLabel("院级");
						if(bf.getDocLevel()==WkTDoc.CLevel) c6.setLabel("系级");
					WkTDept wdt=(WkTDept)departmentService.get(WkTDept.class, bf.getDocKdid());
					Listcell c7=new Listcell(wdt.getKdName());
					Listcell c8=new Listcell(bf.getDocKuname());
					Listcell c9=new Listcell(DateUtil.getDate(new Date(bf.getDoctime())));
				 arg0.appendChild(c1);arg0.appendChild(c2);
				 arg0.appendChild(c3);arg0.appendChild(c4);
				 arg0.appendChild(c6);
				 arg0.appendChild(c7);
				 arg0.appendChild(c8);
				 arg0.appendChild(c9);
				 arg0.appendChild(c5);
			}
			
		});
	}
	public void downloadFile(String relativePath, String fileName) throws InterruptedException {
		java.io.InputStream is = this.getDesktop().getWebApp().getResourceAsStream(relativePath);
		if (is != null) {
			Filedownload.save(is, "text/html", fileName);
		} else {
			Messagebox.show("服务器不存在此文件 ", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@Override
	public void initWindow() {
		Map<Long,WkTDoc> map=new HashMap<Long,WkTDoc>();
		List<WkTDoc> mblist=new ArrayList<WkTDoc>();
		WkTUser user = (WkTUser)Sessions.getCurrent().getAttribute("user");
		List<XyNUrd> xynurdlist=xyUserRoleService.find("from XyNUrd as model where model.id.kuId=? ", new Object[]{user.getKuId()});
		for(int j=0;j<xynurdlist.size();j++){
			mblist.addAll(docService.findByKdidkrid(((XyNUrd)(xynurdlist.get(j))).getId().getKdId(), ((XyNUrd)(xynurdlist.get(j))).getId().getKrId()));
		}
		for(WkTDoc wktdoc:mblist)
			map.put(wktdoc.getDocId(), wktdoc);
		List<WkTDoc> sortList=new ArrayList<WkTDoc>();
		sortList.addAll( map.values());
		Collections.sort(sortList,new myCompartor());
		if(firstList.size()==0){
			firstList.addAll(sortList);
		}
		mubanlist.setModel(new ListModelList(sortList));
		this.addForward(Events.ON_OK, search, Events.ON_CLICK);
	}
	public void onClick$search(){
		String v=searchname.getValue();
		if(v.length()==0){
			initWindow();
		}else{
			List<WkTDoc> nlist=new ArrayList<WkTDoc>();
			for(int i=0;i<firstList.size();i++){
				WkTDoc item=(WkTDoc)firstList.get(i);
				if(item.getDocInfo().contains(v)){
					nlist.add(item);
				}
			}
			mubanlist.setModel(new ListModelList(nlist));
		}
	}
	public class myCompartor extends WkTDoc implements Comparator{

		private static final long serialVersionUID = 1L;

		public int compare(Object a, Object b) {
			 WkTDoc s1 = (WkTDoc)a;
			 WkTDoc s2 = (WkTDoc)b;
			return s1.getDoctime()>s2.getDoctime()?-1:1;
		}
	}
}
