package org.iti.jxkh.business.meeting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.jxkh.entity.JXKH_HYLWFile;
import org.iti.jxkh.entity.JXKH_MeetingFile;
import org.iti.jxkh.entity.JXKH_QKLWFile;
import org.iti.jxkh.entity.Jxkh_AwardFile;
import org.iti.jxkh.entity.Jxkh_FruitFile;
import org.iti.jxkh.entity.Jxkh_HonourFile;
import org.iti.jxkh.entity.Jxkh_PatentFile;
import org.iti.jxkh.entity.Jxkh_ProjectFile;
import org.iti.jxkh.entity.Jxkh_ReportFile;
import org.iti.jxkh.entity.Jxkh_VideoFile;
import org.iti.jxkh.entity.Jxkh_WritingFile;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import com.iti.common.util.UploadUtil;
import com.iti.common.util.ZipUtils;

public class DownloadWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = -7700094870259453050L;
	private Listbox listbox;
	private Set<?> files;
	private String flag = "";
	private String filePath = "";

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		listbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				final String[] str = (String[]) data;
				item.setValue(str);
				Listcell c = new Listcell();
				Listcell c0 = new Listcell(item.getIndex() + 1 + "");
				Listcell c1 = new Listcell(str[0]);
				Listcell c2 = new Listcell(str[3]);
				Listcell c3 = new Listcell(str[2]);
				Listcell c4 = new Listcell();
				Toolbarbutton tb = new Toolbarbutton();
				tb.setImage("/css/default/images/button/down.gif");
				c4.appendChild(tb);
				item.appendChild(c);
				item.appendChild(c0);
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				tb.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						download(str[0], str[1]);
					}
				});
			}
		});
	}

	public void initWindow() {
		List<String[]> list = new ArrayList<String[]>();
		try {
			filePath = UploadUtil.getRealPath("/jxkh/files/");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag.trim().equals("M")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				JXKH_MeetingFile f = (JXKH_MeetingFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(), f.getDate(),
						f.getFileType() });
				// param=f.getMeeting().getMtName();
				// filePath = Executions.getCurrent().getDesktop().getWebApp()
				// .getRealPath("/upload/")
				// + f.getPath();
			}
			listbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("AWARD")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_AwardFile f = (Jxkh_AwardFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(), f.getDate(),
						f.getBelongType() });
			}
			listbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("FRUIT")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_FruitFile f = (Jxkh_FruitFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(), f.getDate(),
						f.getBelongType() });
			}
			listbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("REPORT")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_ReportFile f = (Jxkh_ReportFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(), f.getDate(),
						f.getBelongType() });
			}
			listbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("VIDEO")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_VideoFile f = (Jxkh_VideoFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(), f.getDate(),
						f.getBelongType() });
			}
			listbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("QKLW")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				JXKH_QKLWFile f = (JXKH_QKLWFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(), f.getDate(),
						f.getFileType() });
			}
			listbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("HYLW")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				JXKH_HYLWFile f = (JXKH_HYLWFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(), f.getDate(),
						f.getFileType() });
			}
			listbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("honour")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_HonourFile f = (Jxkh_HonourFile) file[i];
				String fileTime;
				if (f.getFileDate() == null || "".equals(f.getFileDate())) {
					fileTime = "2011-11-11";
				} else {
					fileTime = f.getFileDate();
				}
				list.add(new String[] { f.getFileName(), f.getFilePath(),
						fileTime, "证明材料" });
			}
			listbox.setModel(new ListModelList(list));

		} else if (flag.trim().equals("zp")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_ProjectFile f = (Jxkh_ProjectFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(),
						f.getSubmitDate(), f.getBelongType() });
			}
			listbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("hp")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_ProjectFile f = (Jxkh_ProjectFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(),
						f.getSubmitDate(), f.getBelongType() });
			}
			listbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("cp")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_ProjectFile f = (Jxkh_ProjectFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(),
						f.getSubmitDate(), f.getBelongType() });
			}
			listbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("patent")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_PatentFile f = (Jxkh_PatentFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(),
						f.getSubmitDate(), f.getBelongType() });
			}
			listbox.setModel(new ListModelList(list));
		} else if (flag.trim().equals("writing")) {
			Object[] file = files.toArray();
			for (int i = 0; i < file.length; i++) {
				Jxkh_WritingFile f = (Jxkh_WritingFile) file[i];
				list.add(new String[] { f.getName(), f.getPath(),
						f.getSubmitDate(), f.getBelongType() });
			}
			listbox.setModel(new ListModelList(list));
		}
	}

	public void download(String fname, String fpath)
			throws InterruptedException {
		System.out.println("20120304path=" + UploadUtil.getRootPath() + fpath);
		File file = new File(UploadUtil.getRootPath() + fpath);
		if (file.exists()) {
			try {
				Filedownload.save(file, fname);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("无法下载，可能是因为文件没有被上传！ ", "错误", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void setFiles(Set<?> files) {
		this.files = files;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void onClick$load() {
		if (listbox.getSelectedItems() == null
				|| listbox.getSelectedItems().size() == 0) {
			try {
				Messagebox.show("您未选中欲下载的文件！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		List<File> fileList = new ArrayList<File>();
		Iterator<Listitem> it = listbox.getSelectedItems().iterator();
		while (it.hasNext()) {
			String[] str = (String[]) it.next().getValue();
			File file = new File(Executions.getCurrent().getDesktop()
					.getWebApp().getRealPath("/upload/")
					+ str[1]);
			// File file = (File) it.next().getValue();
			fileList.add(file);
			System.out.println("我要压缩的文件地址是" + file.getPath());
		}
		try {
			System.out.println("打包下载时，我的路径是：" + filePath);
			System.out.println("打包下载时，file的长度：" + fileList.size());
			ZipUtils.doZipFiles(filePath, fileList, "附件");
			File zipFile = new File(filePath + "附件" + ".zip");
			Filedownload.save(zipFile, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
