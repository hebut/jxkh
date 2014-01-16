package com.uniwin.framework.ui.parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkmax.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;

public class RunLogWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String LogPath;
	Listbox fileList;
	ListModelList fileModelList;
	List<File> flist=new ArrayList<File>();
	MLogService mlogService;
	WkTUser user;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		fileModelList = new ListModelList();
		LogPath = Sessions.getCurrent().getWebApp().getRealPath("/");
		LogPath = LogPath.substring(0, LogPath.lastIndexOf("\\"));
		LogPath = LogPath.substring(0, LogPath.lastIndexOf("\\"));
		LogPath = LogPath.substring(0, LogPath.lastIndexOf("\\"));
		fileList.setItemRenderer(new FileItemRenderer());
		fileList.setModel(fileModelList);
		loadGrid();
	}

	private void loadGrid() {
		fileModelList.clear();
		File path = new File(LogPath + "/logs/");
		String[] fileNames = path.list();
		for (int i = 0; i < fileNames.length; i++) {
			File f = new File(LogPath + "/logs/" + fileNames[i]);
			flist.add(f);
		}
		fileModelList.addAll(flist);
	}

	@SuppressWarnings("unchecked")
	public void onClick$delete() throws InterruptedException {
		if (Messagebox.show("您确定要删除吗?", "确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
			Set<Listitem> fset = fileList.getSelectedItems();
			List<Object> dlist = new ArrayList<Object>();
			Iterator<Listitem> it = fset.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				dlist.add(item.getValue());
			}
			StringBuffer sb = new StringBuffer("删除运行日志，文件:");
			for (int i = 0; i < dlist.size(); i++) {
				File f = (File) dlist.get(i);
				if (f.exists()) {
					sb.append(f.getName() + ",");
					f.delete();
				}
			}
			mlogService.saveMLog(WkTMlog.FUNC_ADMIN, sb.toString(), user);
			loadGrid();
		}
	}

	class FileItemRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1) throws Exception {
			arg0.setValue(arg1);
			File f = (File) arg1;
			Listcell lname = new Listcell(f.getName());
			lname.setTooltip("点击查看日志内容");
			Listcell lsize = new Listcell(f.length() / 1024 + " kb");
			Listcell ldate = new Listcell(ConvertUtil.convertDateAndTimeString(f.lastModified()));
			Listcell ldown = new Listcell("下载");
			arg0.appendChild(lname);
			arg0.appendChild(lsize);
			arg0.appendChild(ldate);
			arg0.appendChild(ldown);
			arg0.setHeight("25px");
			lname.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					Listitem item = (Listitem) arg0.getTarget().getParent();
					File f = (File) item.getValue();
					LogViewWindow w = (LogViewWindow) Executions.createComponents("/admin/system/parameters/runBlog/viewLog.zul", null, null);
					w.doHighlighted();
					w.initWindow(f);
				}
			});
			ldown.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					Listitem item = (Listitem) arg0.getTarget().getParent();
					File f = (File) item.getValue();
					mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "下载运行日志,文件:" + f.getName(), user);
					Filedownload.save(f, null);
				}
			});
		}
	}
}
