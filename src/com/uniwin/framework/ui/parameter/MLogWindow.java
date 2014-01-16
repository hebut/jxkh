package com.uniwin.framework.ui.parameter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkmax.zul.Filedownload;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;

public class MLogWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grid logGrid;
	ListModelList logModel;
	MLogService mlogService;
	Datebox btime, etime;
	Button serchButton, exportButton, deleteButton;
	String beginTime = "0000-00-00 00:00:00", endTime;
	String PATH = "";
	WkTUser user;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		logModel = new ListModelList();
		  //System.out.println("现在时间是："+new Date());

		beginTime = ConvertUtil.convertDateAndTimeString(new Date());;
		endTime = ConvertUtil.convertDateAndTimeString(new Date());
		logGrid.setModel(logModel);
		PATH = Sessions.getCurrent().getWebApp().getRealPath("/upload/mlog/");
		logGrid.setRowRenderer(new RowRenderer() {
			public void render(Row row, Object data) throws Exception {
				row.setValue(data);
				WkTMlog log = (WkTMlog) data;
				row.appendChild(new Label(log.getKmlTime()));
				row.appendChild(new Label(log.getKuName()));
				row.appendChild(new Label(log.getKmlIp()));
				row.appendChild(new Label(log.getKmlFunc()));
				row.appendChild(new Label(log.getKmlDesc()));
			}
		});
		reloadWindow();
	}

	private void reloadWindow() {
		logModel.clear();
		List<WkTMlog> mlist = mlogService.findLogsByTimes(beginTime, endTime);
		logModel.addAll(mlist);
	}

	public void onClick$serchButton() {
		if (btime.getValue() != null) {
			beginTime = ConvertUtil.convertDateAndTimeString(btime.getValue());
		}
		if (etime.getValue() != null) {
			endTime = ConvertUtil.convertDateAndTimeString(etime.getValue());
			endTime=endTime.split(" ")[0]+" 23:59:59";
			//System.out.println(endTime);
			
		}
		reloadWindow();
	}

	public void onClick$deleteButton() throws WrongValueException, InterruptedException {
		if (Messagebox.show("您确定要删除吗?", "请确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
			if (btime.getValue() != null) {
				beginTime = ConvertUtil.convertDateAndTimeString(btime.getValue());
			} else {
				Messagebox.show("请输入时间段", "错误", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if (etime.getValue() != null) {
				endTime = ConvertUtil.convertDateAndTimeString(etime.getValue());
			} else {
				Messagebox.show("请输入时间段", "错误", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			List<WkTMlog> mlist = mlogService.findLogsByTimes(beginTime, endTime);
			mlogService.deleteAll(mlist);
			mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "删除管理日志,时间从:" + beginTime + "到" + endTime, user);
			reloadWindow();
		}
	}

	public void onClick$exportButton() throws InterruptedException, IOException {
		if (btime.getValue() != null) {
			beginTime = ConvertUtil.convertDateAndTimeString(btime.getValue());
		} else {
			Messagebox.show("请输入时间段", "错误", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (etime.getValue() != null) {
			endTime = ConvertUtil.convertDateAndTimeString(etime.getValue());
		} else {
			Messagebox.show("请输入时间段", "错误", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		List<WkTMlog> mlist = mlogService.findLogsByTimes(beginTime, endTime);
		FileOutputStream outSTr = null;
		BufferedOutputStream buff = null;
		String fileName = beginTime.substring(0, 10) + "_" + endTime.substring(0, 10) + ".mlog";
		outSTr = new FileOutputStream(new File(PATH + "\\" + fileName));
		buff = new BufferedOutputStream(outSTr);
		byte[] bs = "管理时间	|日志id	|管理员id	|管理员姓名	|管理IP地址 	|功能模块	|管理摘要\r\n".getBytes();
		buff.write(bs);
		for (int i = 0; i < mlist.size(); i++) {
			WkTMlog log = (WkTMlog) mlist.get(i);
			byte[] temp = (log.getKmlTime() + "	|" + log.getKmlId() + "	|" + log.getKuId() + "	|" + log.getKuName().trim() + "	|" + log.getKmlIp() + " 	|" + log.getKmlFunc().trim() + "	|" + log.getKmlDesc() + "\r\n").getBytes();
			buff.write(temp);
		}
		buff.flush();
		buff.close();
		outSTr.close();
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "导出管理日志,时间从:" + beginTime + "到" + endTime, user);
		Filedownload.save(new File(PATH + "\\" + fileName), null);
	}
}
