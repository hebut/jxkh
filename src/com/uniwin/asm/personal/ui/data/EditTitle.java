package com.uniwin.asm.personal.ui.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.iti.gh.entity.GhZcqc;
import org.iti.gh.service.ZcqkService;
import org.iti.jxkh.business.meeting.UpfileWindow;
import org.iti.jxkh.entity.Jxkh_DataFile;
import org.iti.jxkh.service.UserDetailService;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.ui.base.BaseWindow;
import org.iti.xypt.ui.base.TitleSelectHbox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTUser;

public class EditTitle extends BaseWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5280036623281809183L;
	TitleSelectHbox titleHbox;
	Datebox titlegettime, gettime;
	Textbox certifino, certifidept, qualino, identdept;
	Button save, reset, close;
	private Listbox fileListbox;

	private List<String[]> fileList = new ArrayList<String[]>();

	GhZcqc zcqc;
	WkTUser user;
	ZcqkService zcqkService;
	TeacherService teacherService;
	UserDetailService userDetailService;
	SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void initShow() {
		fileListbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final String[] s = (String[]) arg1;
				if (s != null) {
					Listcell c0 = new Listcell(Integer.valueOf(arg0.getIndex() + 1).toString());
					Listcell c1 = new Listcell(s[1]);
					Listcell c2 = new Listcell();
					Toolbarbutton tb = new Toolbarbutton();
					tb.setImage("/css/default/images/button/del.gif");
					tb.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							fileList.remove(s);
							fileListbox.setModel(new ListModelList(fileList));
						}
					});
					arg0.setValue(s);
					arg0.appendChild(c0);
					arg0.appendChild(c1);
					arg0.appendChild(c2);
				}
			}
		});
	}

	@Override
	public void initWindow() {

	}

	public void init(GhZcqc z) {
		zcqc = z;
		if (z.getZcTime() != null) {
			titlegettime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(z.getZcTime())));
		}
		if (z.getZcPubtime() != null) {
			gettime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(z.getZcPubtime())));
		}
		if (z.getZcNum() != null || z.getZcQuasym() != null || z.getZcPubdept() != null || z.getZcIdentdept() != null) {
			certifino.setValue(z.getZcNum());
			qualino.setValue(z.getZcQuasym());
			certifidept.setValue(z.getZcPubdept());
			identdept.setValue(z.getZcIdentdept());
		}
		/**
		 * 初始化附件
		 */
		fileList.clear();
		List<Jxkh_DataFile> list = userDetailService.getFileByUser(zcqc.getZcId(), Jxkh_DataFile.TITLE);
		if (list != null && list.size() > 0) {
			for (Jxkh_DataFile f : list) {
				if (f != null) {
					String[] s = new String[4];
					s[0] = f.getFilePath();
					s[1] = f.getFileName();
					s[2] = f.getUpTime();
					s[3] = Jxkh_DataFile.TRAIN.toString();
					fileList.add(s);
				}
			}
		}
		fileListbox.setModel(new ListModelList(fileList));
	}

	public void onClick$save() throws WrongValueException, ParseException, InterruptedException {
		// user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		if (certifino.getValue() != null || certifidept.getValue() != null || qualino.getValue() != null || identdept.getValue() != null) {
			zcqc.setZcNum(certifino.getValue());
			zcqc.setZcPubdept(certifidept.getValue());
			zcqc.setZcQuasym(qualino.getValue());
			zcqc.setZcIdentdept(identdept.getValue());
			if (titlegettime.getValue() != null && gettime.getValue() != null) {
				@SuppressWarnings("deprecation")
				Date start = ff.parse(titlegettime.getValue().toLocaleString().substring(0, 10));
				@SuppressWarnings("deprecation")
				Date end = ff.parse(gettime.getValue().toLocaleString().substring(0, 10));
				if (start.after(end)) {
					Messagebox.show("请正确设置时间，开始时间在结束时间之前！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				} else {
					zcqc.setZcTime(DateUtil.getDateString(titlegettime.getValue()));
					zcqc.setZcPubtime(DateUtil.getDateString(gettime.getValue()));
					zcqkService.update(zcqc);
					Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}
			} else {
				if (titlegettime.getValue() != null) {
					zcqc.setZcTime(DateUtil.getDateString(titlegettime.getValue()));
					zcqkService.update(zcqc);
					Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}
				if (gettime.getValue() != null) {
					zcqc.setZcPubtime(DateUtil.getDateString(gettime.getValue()));
					zcqkService.update(zcqc);
					Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}
				if (titlegettime.getValue() == null && gettime.getValue() == null) {
					zcqkService.update(zcqc);
					Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
			/**
			 * 保存附件
			 */
			// 先删除原来的附件
			List<Jxkh_DataFile> list = userDetailService.getFileByUser(zcqc.getZcId(), Jxkh_DataFile.TITLE);
			if (list != null && list.size() > 0) {
				for (Jxkh_DataFile f : list) {
					if (f != null)
						zcqkService.delete(f);
				}
			}
			// 再保存新的附件
			if (fileList.size() > 0) {
				for (String[] s : fileList) {
					if (s != null) {
						Jxkh_DataFile f = new Jxkh_DataFile();
						f.setUserId(zcqc.getZcId());
						f.setFilePath(s[0]);
						f.setFileName(s[1]);
						f.setUpTime(s[2]);
						f.setFileType(Jxkh_DataFile.TITLE);
						zcqkService.save(f);
					}
				}
			}
			this.detach();
			Events.postEvent(Events.ON_CHANGE, this, null);
		}
	}

	public void onClick$close() {
		this.detach();
	}

	public void onClick$upBn() {
		final UpfileWindow w = (UpfileWindow) Executions.createComponents("/admin/personal/businessdata/meeting/upfile.zul", null, null);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filePath = (String) Executions.getCurrent().getAttribute("path");
				String fileName = (String) Executions.getCurrent().getAttribute("title");
				String upTime = (String) Executions.getCurrent().getAttribute("upTime");
				String[] arr = new String[4];
				arr[0] = filePath;
				arr[1] = fileName;
				arr[2] = upTime;
				arr[3] = Jxkh_DataFile.TRAIN.toString();
				fileList.add(arr);
				fileListbox.setModel(new ListModelList(fileList));
			}
		});
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
