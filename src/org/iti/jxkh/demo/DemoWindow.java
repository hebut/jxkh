package org.iti.jxkh.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.business.meeting.UploadBox;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.iti.common.util.UploadUtil;

public class DemoWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = -3755074204648272061L;
	private Div upload;
	private UploadBox uploadbox;
	private ListModelList fileModel = new ListModelList(new ArrayList<Object>());
	private List<?> flist = null;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		uploadbox = (UploadBox) Executions.createComponents("/admin/jxkh/test/upload.zul", null, null);
		upload.appendChild(uploadbox);
		File file = new File(
				"E:\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\jxkh\\upload\\jxkh\\demo\\demo.txt");
		System.out.println(file.exists());
		try {
			Media media = new AMedia(file, null, "UTF-8");
			fileModel.add(media);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		uploadbox.setFileModel(fileModel);
		uploadbox.initWindow();
	}

	public void onClick$save() {
		if ((flist = uploadbox.getFileModel().getInnerList()) != null) {
			for (int n = 0; n < flist.size(); n++) {
				Media media = (Media) flist.get(n);
				if (media != null) {
					try {
						UploadUtil.saveFile(media, "\\jxkh\\demo\\",
								media.getName().substring(0, media.getName().lastIndexOf(".")), null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
