package org.iti.jxkh.dutyChange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.jxkh.entity.JXKH_PerTrans;
import org.iti.jxkh.service.DutyChangeService;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTDept;

public class DutyChangeWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Listbox history;
	private Paging zxPaging;
	
	private WkTDept dept;
	
	DutyChangeService dutyChangeService;
	
	public void afterCompose() {		
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		history.setItemRenderer(new DutyChangeItemRender());
	}
	/**
	 * 初始化页面
	 * @param dept
	 */
	public void initWindow(WkTDept dept) {
		this.dept = dept;
		reloadList();
	}
	/**
	 * 实现列表数据的分页加载
	 */
	private void reloadList() {
		List<JXKH_PerTrans> dutyChange = new ArrayList<JXKH_PerTrans>();
		dutyChange.clear();
		List<JXKH_PerTrans> changeList = dutyChangeService.findDutyChangeByDept(dept.getKdId());
		List<JXKH_PerTrans> listByPage = dutyChangeService.findChangeByPage(dept.getKdId(), zxPaging.getActivePage(), zxPaging.getPageSize());
		if(changeList.size() != 0) {
			zxPaging.setTotalSize(changeList.size());
			if(listByPage.size() != 0) {
				for(int i=0;i<listByPage.size();i++) {
					JXKH_PerTrans jp = listByPage.get(i);
					dutyChange.add(jp);
				}
			}
			history.setModel(new ListModelList(dutyChange));
			zxPaging.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					List<JXKH_PerTrans> listByPage2 = dutyChangeService.findChangeByPage(dept.getKdId(), zxPaging.getActivePage(), zxPaging.getPageSize());
					List<JXKH_PerTrans> dutyChange2 = new ArrayList<JXKH_PerTrans>();
					dutyChange2.clear();
					if(listByPage2.size() != 0) {
						for(int i=0;i<listByPage2.size();i++) {
							JXKH_PerTrans jp2 = listByPage2.get(i);
							dutyChange2.add(jp2);
						}
					}
					history.setModel(new ListModelList(dutyChange2));
				}
			});
			/*for(int i=0;i<changeList.size();i++) {
				JXKH_PerTrans jp = changeList.get(i);
				dutyChange.add(jp);
			}
			history.setModel(new ListModelList(dutyChange));*/
			
		}else {
			history.setModel(new ListModelList(dutyChange));
		}
		
	}
	/**
	 * 添加人员调动
	 * @throws InterruptedException 
	 * @throws SuspendNotAllowedException 
	 */
	public void onClick$addHis() throws SuspendNotAllowedException, InterruptedException {
		AddDutyChangeWindow acw = (AddDutyChangeWindow)Executions.createComponents("/admin/jxkh/dutyChange/addHistory.zul", null, null);
		acw.initWindow(null, dept);
		acw.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				reloadList();
			}
		});
		acw.doModal();
	}
	/**
	 * 删除信息
	 */
	public void onClick$del() {
		if(history.getSelectedItems() != null) {
			Set<?> delset = history.getSelectedItems();
			Iterator<?> iterator = delset.iterator();
			while(iterator.hasNext()) {
				Listitem item = (Listitem)iterator.next();
				JXKH_PerTrans honour = (JXKH_PerTrans)item.getValue();				
				dutyChangeService.delete(honour);
				reloadList();
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
	 * 导出20120717
	 */
	public void onClick$exportExcel() {

		if (history.getSelectedCount() == 0) {
			try {
				Messagebox.show("请选择要导出的数据", "提示", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			int i = 0;
			ArrayList<JXKH_PerTrans> expertlist = new ArrayList<JXKH_PerTrans>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = history.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				JXKH_PerTrans p = (JXKH_PerTrans) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "RenYuanDiaoDong" + ".xls";
			String Title = "人员调动信息";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("序号");
			titlelist.add("人员姓名");
			titlelist.add("原部门");
			titlelist.add("原职务");
			titlelist.add("新部门");
			titlelist.add("新职务");
			titlelist.add("调动时间");
			titlelist.add("调动原因");
			
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < expertlist.size(); j++) {
				JXKH_PerTrans per = (JXKH_PerTrans) expertlist.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = per.getUser().getKuName();
				c[j][2] = per.getOldDept().getKdName();
				c[j][3] = per.getOldPost();
				c[j][4] = per.getNewDept().getKdName();
				c[j][5] = per.getNewPost();
				c[j][6] = per.getPtDate();
				c[j][7] = per.getReason();
			}
			ExportExcel ee = new ExportExcel();
			try {
				ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}
	/**
	 * 人员调动主页面的触发器
	 * @author WeifangWang
	 *
	 */
	class DutyChangeItemRender implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1) throws Exception {
			final JXKH_PerTrans jp = (JXKH_PerTrans)arg1;
			arg0.setValue(jp);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(arg0.getIndex()+1+"");
			Listcell c2 = new Listcell(jp.getUser().getKuName());
			c2.setStyle("color:blue");
			c2.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					AddDutyChangeWindow acw = (AddDutyChangeWindow)Executions.createComponents("/admin/jxkh/dutyChange/addHistory.zul", null, null);
					acw.setUser(jp.getUser());
					acw.initWindow(jp,dept);
					acw.addEventListener(Events.ON_CHANGE, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							reloadList();
						}
					});
					acw.doModal();
				}
			});
			Listcell c3 = new Listcell(jp.getOldDept().getKdName());
			Listcell c4 = new Listcell(jp.getOldPost());
			Listcell c5 = new Listcell(jp.getNewDept().getKdName());
			Listcell c6 = new Listcell(jp.getNewPost());
			Listcell c7 = new Listcell(jp.getPtDate());
			arg0.appendChild(c0);
			arg0.appendChild(c1);
			arg0.appendChild(c2);
			arg0.appendChild(c3);
			arg0.appendChild(c4);
			arg0.appendChild(c5);
			arg0.appendChild(c6);
			arg0.appendChild(c7);
		}
		
	}
}
