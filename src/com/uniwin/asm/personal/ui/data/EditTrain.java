package com.uniwin.asm.personal.ui.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.iti.gh.entity.GhPxqk;
import org.iti.gh.service.PxqkService;
import org.iti.jxkh.business.meeting.UpfileWindow;
import org.iti.jxkh.entity.Jxkh_DataFile;
import org.iti.jxkh.service.UserDetailService;
import org.iti.xypt.ui.base.BaseWindow;
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

public class EditTrain extends BaseWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6355560959253342251L;
	Textbox mainpoint, content, place, proveperson;
	Datebox starttime, endtime;
	Button save, reset, close;
	private Listbox fileListbox;

	private List<String[]> fileList = new ArrayList<String[]>();

	PxqkService pxqkService;
	UserDetailService userDetailService;

	GhPxqk px;
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

	public void init(GhPxqk p) {
		px = p;
		if (p.getPxStarttime() != null) {
			starttime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(p.getPxStarttime())));
		}
		if (p.getPxEndtime() != null) {
			endtime.setValue(ConvertUtil.convertDate(DateUtil.getDateString(p.getPxEndtime())));
		}
		if (p.getPxMainpoint() != null || p.getPxContent() != null || p.getPxPlace() != null || p.getPxProve() != null) {
			mainpoint.setValue(p.getPxMainpoint());
			content.setValue(p.getPxContent());
			place.setValue(p.getPxPlace());
			proveperson.setValue(p.getPxProve());
		}
		/**
		 * 初始化附件
		 */
		fileList.clear();
		List<Jxkh_DataFile> list = userDetailService.getFileByUser(px.getPxId(), Jxkh_DataFile.TRAIN);
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

	public void onClick$save() throws InterruptedException, WrongValueException, ParseException {
		if (mainpoint.getValue() != null && content.getValue() != null && place.getValue() != null && proveperson.getValue() != null) {
			px.setPxMainpoint(mainpoint.getValue());
			px.setPxContent(content.getValue());
			px.setPxPlace(place.getValue());
			px.setPxProve(proveperson.getValue());

			if (starttime.getValue() != null && endtime.getValue() != null) {
				@SuppressWarnings("deprecation")
				Date start = ff.parse(starttime.getValue().toLocaleString().substring(0, 10));
				@SuppressWarnings("deprecation")
				Date end = ff.parse(endtime.getValue().toLocaleString().substring(0, 10));
				if (start.after(end)) {
					Messagebox.show("请正确设置时间，开始时间在结束时间之前！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				} else {
					px.setPxStarttime(DateUtil.getDateString(starttime.getValue()));
					px.setPxEndtime(DateUtil.getDateString(endtime.getValue()));
					pxqkService.update(px);
					Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);

				}

			} else {
				if (starttime.getValue() != null) {
					px.setPxStarttime(DateUtil.getDateString(starttime.getValue()));
					pxqkService.update(px);
					Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}
				if (endtime.getValue() != null) {
					px.setPxEndtime(DateUtil.getDateString(endtime.getValue()));
					pxqkService.update(px);
					Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}
				if (starttime.getValue() == null && endtime.getValue() == null) {
					pxqkService.update(px);
					Messagebox.show("保存成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
			/**
			 * 保存附件
			 */
			// 先删除原来的附件
			List<Jxkh_DataFile> list = userDetailService.getFileByUser(px.getPxId(), Jxkh_DataFile.TRAIN);
			if (list != null && list.size() > 0) {
				for (Jxkh_DataFile f : list) {
					if (f != null)
						pxqkService.delete(f);
				}
			}
			// 再保存新的附件
			if (fileList.size() > 0) {
				for (String[] s : fileList) {
					if (s != null) {
						Jxkh_DataFile f = new Jxkh_DataFile();
						f.setUserId(px.getPxId());
						f.setFilePath(s[0]);
						f.setFileName(s[1]);
						f.setUpTime(s[2]);
						f.setFileType(Jxkh_DataFile.TRAIN);
						pxqkService.save(f);
					}
				}
			}

			this.detach();

			Events.postEvent(Events.ON_CHANGE, this, null);

		}
	}

	public void onClick$reset() {
		mainpoint.setRawValue("");
		content.setRawValue("");
		place.setRawValue("");
		proveperson.setRawValue("");
		starttime.setRawValue(null);
		endtime.setRawValue(null);
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
