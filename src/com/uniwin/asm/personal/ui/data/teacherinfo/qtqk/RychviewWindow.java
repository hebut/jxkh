package com.uniwin.asm.personal.ui.data.teacherinfo.qtqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.entity.Jxkh_HonourFile;
import org.iti.jxkh.honour.AuditAdviceWindow;
import org.iti.jxkh.service.RychService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.asm.personal.ui.data.teacherinfo.RychWindow;
import com.uniwin.framework.entity.WkTUser;
 

public class RychviewWindow extends BaseWindow {

	//荣誉称号
	Button button3,delete,button3out;
	Listbox listbox3;
	RychService rychService;
	WkTUser user;
	Teacher teacher;
	Long kuid;
	@Override
	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		kuid=user.getKuId();
		initWindow();
		listbox3.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Jxkh_Honour ch = (Jxkh_Honour) arg1;
				arg0.setValue(ch);
				Listcell c0 = new Listcell();
				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				//荣誉名称，点击荣誉名称根据不同的角色可以查看详细并进行编辑详细页
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(ch.getRyName());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {

						final RychWindow  w = (RychWindow ) Executions.createComponents
						("/admin/personal/basicdata/honour/addHounour.zul", null, null);
						w.setKuid(user.getKuId());
						w.setRych(ch);						
						w.initWindow();
						w.setUser(user);
						w.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								 initWindow();
							}
						});
						w.doModal();
					}
				});
				c2.appendChild(ib);
				//年度
				Listcell c3 = new Listcell();
				c3.setLabel(ch.getRyYear());
				//颁发单位
				Listcell c4 = new Listcell();
				c4.setLabel(ch.getRyDep());
				//附件
				Listcell c5 = new Listcell();				
				Toolbarbutton tb = new Toolbarbutton();				
				tb.setImage("/css/default/images/button/down.gif");
				tb.setTooltiptext("点击下载附件");
				tb.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow w = (DownloadWindow)Executions.createComponents
								("/admin/personal/basicdata/honour/download.zul", null, null);
						Set<Jxkh_HonourFile> flist = ch.getFile();
						w.setFiles(flist);
						w.setFlag("honour");
						w.initWindow();						
						w.doModal();						
					}
				});
				tb.setParent(c5);
				//审核状态
				Listcell c6 = new Listcell();
				c6.setStyle("color:red");
				switch(ch.getState()) {
				case Jxkh_Honour.NONE:
					c6.setLabel("未审核");
					break;
				case Jxkh_Honour.BUSI_PASS:
					c6.setLabel("通过");
					break;
				case Jxkh_Honour.BUSI_OUT:
					c6.setLabel("退回");
					break;
				case Jxkh_Honour.RECORD_YES:
					c6.setLabel("已归档");
					break;
				}
				c6.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AuditAdviceWindow win = (AuditAdviceWindow)Executions.createComponents("/admin/personal/basicdata/honour/advice.zul", null, null);
						win.initWindow(ch,ch.getKuId());
						win.save.setVisible(false);
						win.doModal();
					}
				});
				//操作
				Listcell c7 =  new Listcell();
				Hbox hb = new Hbox();
				Toolbarbutton tb1 = new Toolbarbutton();
				tb1.setImage("/css/default/images/button/actEdit.gif");
				tb1.setTooltiptext("编辑");
				tb1.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final RychWindow  w = (RychWindow ) Executions.createComponents
								("/admin/personal/basicdata/honour/addHounour.zul", null, null);
								w.setKuid(user.getKuId());
								w.setRych(ch);						
								w.initWindow();
								w.addEventListener(Events.ON_CHANGE, new EventListener() {
									public void onEvent(Event arg0) throws Exception {
										 initWindow();
									}
								});
								w.doModal();
					}					
				});
				Toolbarbutton tb2 = new Toolbarbutton();
				tb2.setImage("/css/default/images/button/del.gif");
				tb2.setTooltiptext("删除");
				tb2.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						/*if(ch.getState()== Jxkh_Honour.BUSI_PASS || ch.getState() == Jxkh_Honour.DEPART_PASS || ch.getState() == Jxkh_Honour.RECORD_YES) {
							org.zkoss.zul.Messagebox.show("")
						}*/
						if(ch.getState()== Jxkh_Honour.BUSI_PASS || ch.getState() == Jxkh_Honour.RECORD_YES) {
						switch(ch.getState()) {						
						case Jxkh_Honour.BUSI_PASS:
							org.zkoss.zul.Messagebox.show("人事处已审核通过，不能进行删除！");
							break;
						case Jxkh_Honour.RECORD_YES:
							org.zkoss.zul.Messagebox.show("已归档，不能进行删除！");
							break;
						}
						return;
						}						
						
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							rychService.delete(ch);
							List<Jxkh_HonourFile> flist = rychService.findFileByHonour(ch);
							if(flist.size() != 0) {
								for(int m=0;m<flist.size();m++) {
									Jxkh_HonourFile file = flist.get(m);
									rychService.delete(file);
								}
							}
							/**
							 * 待完善解决：删除服务器上的相关文件
							 */
							initWindow();
						}
					}
				});
				tb1.setParent(hb);
				tb2.setParent(hb);
				c7.appendChild(hb);
				arg0.appendChild(c0);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6); 
				arg0.appendChild(c7); 
			}
		});

	}
	/**
	 * 初始化页面
	 */
	@Override
	public void initWindow() {		
		List chlist =rychService.FindByKuid(kuid);
		listbox3.setModel(new ListModelList(chlist));

	}
	/**
	 * 添加荣誉称号
	 * @throws SuspendNotAllowedException
	 * @throws InterruptedException
	 */
	public void onClick$button3() throws SuspendNotAllowedException, InterruptedException {
		final RychWindow cgWin = (RychWindow) Executions.createComponents
		("/admin/personal/basicdata/honour/addHounour.zul", null, null);
		cgWin.setKuid(user.getKuId());		
		cgWin.initWindow();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){
			public void onEvent(Event arg0)
			throws Exception {
				 initWindow();
				cgWin.detach();
			}
		});
		cgWin.doModal();
	}
	/**
	 * 批量删除
	 */
	public void onClick$delete() {		
		int count = 0;
		if(listbox3.getSelectedItems() != null) {
			Set delset = listbox3.getSelectedItems();
			Iterator iterator = delset.iterator();
			while(iterator.hasNext()) {
				Listitem item = (Listitem)iterator.next();
				Jxkh_Honour honour = (Jxkh_Honour)item.getValue();
				if(honour.getState() != Jxkh_Honour.BUSI_PASS) {
					rychService.delete(honour);
					List<Jxkh_HonourFile> flist = rychService.findFileByHonour(honour);
					if(flist.size() != 0) {
						for(int m=0;m<flist.size();m++) {
							Jxkh_HonourFile file = flist.get(m);
							rychService.delete(file);
						}
					}
					initWindow();
				}else {
					count++;
				}
			}
			if(count >0) {
				try {
	 				Messagebox.show("人事部门审核通过的不能删除！", "提示", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
			}
		}else {
			try {
 				Messagebox.show("请选中要删除的数据！", "提示", Messagebox.OK,Messagebox.INFORMATION);
 			} catch (InterruptedException e) {
 				e.printStackTrace();
 			}
 			  return;
		}
	}
	/**
	 * 导出
	 * @throws IOException
	 * @throws WriteException
	 */
	public void onClick$button3out()  throws IOException, WriteException{
		//荣誉称号
		List list3 =rychService.FindByKuid(kuid);
		if(list3 == null || list3.size() == 0){
			 try {
	 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String state = "";
			String fileName = "荣誉称号.xls";
			String Title = "荣誉称号 ";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("序号");
			titlelist.add("荣誉名称");
			titlelist.add("年度");
			titlelist.add("颁发单位");	
			titlelist.add("状态");
			String c[][]=new String[list3.size()][titlelist.size()];
			//从SQL中读数据
			for(int j=0;j<list3.size();j++){
				Jxkh_Honour ch =(Jxkh_Honour)list3.get(j);
			    c[j][0]=j+1+"";
				c[j][1]=ch.getRyName();
			    c[j][2]=ch.getRyYear();
				c[j][3]=ch.getRyDep();
				switch(ch.getState()) {
				case Jxkh_Honour.NONE:
					state = "未审核";
					break;
				case Jxkh_Honour.BUSI_PASS:
					state = "人事处通过";
					break;
				case Jxkh_Honour.BUSI_OUT:
					state = "人事处退回";
					break;
				case Jxkh_Honour.RECORD_YES:
					state = "已归档";
					break;
				}
				c[j][4]=state;
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list3.size(), c);
			
		}
	}

}
