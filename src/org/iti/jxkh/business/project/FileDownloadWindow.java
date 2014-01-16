package org.iti.jxkh.business.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import com.iti.common.util.ConvertUtil;
import com.iti.common.util.DownLoadFile;
import com.iti.common.util.UploadUtil;
import com.iti.common.util.ZipUtils;

public class FileDownloadWindow extends BaseWindow {

	private static final long serialVersionUID = 8916994336028789901L;

	private Listbox fileListbox;

	private Jxkh_Project project;

	@Override
	public void initShow() {
		fileListbox.setItemRenderer(new FileRenderer());
	}

	@Override
	public void initWindow() {
		String filePath = "";
		String filePath2 = "";
		String filePath3 = "";
		String filePath4 = "";
		String filePath5 = "";
		try {
			filePath = UploadUtil.getRealPath("/jxkh/project/1/"
					+ project.getId());
			filePath2 = UploadUtil.getRealPath("/jxkh/project/2/"
					+ project.getId());
			filePath3 = UploadUtil.getRealPath("/jxkh/project/3/"
					+ project.getId());
			filePath4 = UploadUtil.getRealPath("/jxkh/project/4/"
					+ project.getId());
			filePath5 = UploadUtil.getRealPath("/jxkh/project/5/"
					+ project.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file5 = new File(filePath5);
		File file = new File(filePath);
		File file2 = new File(filePath2);
		File file3 = new File(filePath3);
		File file4 = new File(filePath4);

		List a = new ArrayList();
		if (file5.exists()) {
			a.addAll(Arrays.asList(file5.listFiles()));
		}
		if (file.exists()) {
			a.addAll(Arrays.asList(file.listFiles()));
		}
		if (file2.exists()) {
			a.addAll(Arrays.asList(file2.listFiles()));
		}
		if (file3.exists()) {
			a.addAll(Arrays.asList(file3.listFiles()));
		}
		if (file4.exists()) {
			a.addAll(Arrays.asList(file4.listFiles()));
		}

		fileListbox.setModel(new ListModelList(a));
	}

	public void onClick$download() {
		if (fileListbox.getSelectedItems() == null
				|| fileListbox.getSelectedItems().size() == 0) {
			try {
				Messagebox.show("您未选中欲下载的文件！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		List<File> fileList = new ArrayList<File>();
		Iterator<Listitem> it = fileListbox.getSelectedItems().iterator();
		while (it.hasNext()) {
			File file = (File) it.next().getValue();
			fileList.add(file);
		}
		try {
			String filePath = UploadUtil.getRealPath("/jxkh/project/");
			ZipUtils.doZipFiles(filePath, fileList, project.getId() + "");
			File zipFile = new File(filePath + project.getId() + ".zip");
			Filedownload.save(zipFile, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class FileRenderer implements ListitemRenderer {
		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			File file = (File) data;
			item.setValue(file);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(file.getName());
			String abc ="";
			if (file.getPath().contains("\\jxkh\\project\\1\\")) {
				abc ="项目申请书";
			}
			if (file.getPath().contains("\\jxkh\\project\\2\\")) {
				abc ="项目合同书";
			}
			if (file.getPath().contains("\\jxkh\\project\\3\\")) {
				abc ="年度进度文档";
			}
			if (file.getPath().contains("\\jxkh\\project\\4\\")) {
				abc ="项目验收证书";
			}
			if (file.getPath().contains("\\jxkh\\project\\5\\")) {
				abc ="项目任务书";
			}
			Listcell c5 = new Listcell(abc);
			c5.setTooltiptext(abc);
			Listcell c3 = new Listcell(ConvertUtil.convertDateString(new Date(
					file.lastModified())));
			Listcell c4 = new Listcell();
			Toolbarbutton tb = new Toolbarbutton();
			tb.setImage("/css/default/images/button/down.gif");
			tb.addEventListener(Events.ON_CLICK, new DownloadListener());
			c4.appendChild(tb);
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c5);
			item.appendChild(c3);
			item.appendChild(c4);
		}
	}

	public class DownloadListener implements EventListener {
		@Override
		public void onEvent(Event event) throws Exception {
			Listitem item = (Listitem) event.getTarget().getParent()
					.getParent();
			File file = (File) item.getValue();
			Filedownload.save(file, null);
//			if (file.getPath().contains("\\jxkh\\project\\1\\")) {
//				DownLoadFile.onDownfile(UploadUtil
//						.getRealPath("/jxkh/project/1/" + project.getId() + "/"
//								+ file.getName()));
//			}
//			if (file.getPath().contains("\\jxkh\\project\\2\\")) {
//				DownLoadFile.onDownfile(UploadUtil
//						.getRealPath("/jxkh/project/2/" + project.getId() + "/"
//								+ file.getName()));
//			}
//			if (file.getPath().contains("\\jxkh\\project\\3\\")) {
//				DownLoadFile.onDownfile(UploadUtil
//						.getRealPath("/jxkh/project/3/" + project.getId() + "/"
//								+ file.getName()));
//			}
//			if (file.getPath().contains("\\jxkh\\project\\4\\")) {
//				DownLoadFile.onDownfile(UploadUtil
//						.getRealPath("/jxkh/project/4/" + project.getId() + "/"
//								+ file.getName()));
//			}
//			if (file.getPath().contains("\\jxkh\\project\\5\\")) {
//				DownLoadFile.onDownfile(UploadUtil
//						.getRealPath("/jxkh/project/5/" + project.getId() + "/"
//								+ file.getName()));
//			}
		}
	}

	public Jxkh_Project getProject() {
		return project;
	}

	public void setProject(Jxkh_Project project) {
		this.project = project;
	}


}
