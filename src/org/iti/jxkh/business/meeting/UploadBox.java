package org.iti.jxkh.business.meeting;

import java.util.ArrayList;

import org.iti.jxkh.entity.JXKH_HYLWFile;
import org.iti.jxkh.entity.JXKH_MeetingFile;
import org.iti.jxkh.entity.JXKH_QKLWFile;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

public class UploadBox extends Hbox implements AfterCompose {
	private static final long serialVersionUID = -4745833627929173507L;
	private ListModelList fileModel = new ListModelList(new ArrayList<Object>());
	private Listbox filelist;
	public Toolbarbutton up, del, down;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow() {
		filelist = (Listbox) this.getFirstChild();
		filelist.setModel(fileModel);
		filelist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				if (arg1 instanceof Media) {
					Media name = (Media) arg1;
					arg0.setLabel(name.getName());
				} else if (arg1 instanceof String[]) {
					String[] name = (String[]) arg1;
					arg0.setLabel(name[0]);
				}
			}
		});
		up = (Toolbarbutton) this.getFirstChild().getNextSibling();
		up.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Object media;
				try {
					media = Fileupload.get();
					if (media == null) {
						return;
					}
					fileModel.add(media);
					filelist.setModel(fileModel);
					del.setVisible(true);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		del = (Toolbarbutton) this.getLastChild();
		del.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				Listitem it = filelist.getSelectedItem();
				if (it == null) {
					if (fileModel.getSize() > 0) {
						it = filelist.getItemAtIndex(0);
					}
				}
				if (fileModel.getSize() == 1) {
					fileModel.remove(it.getValue());
					del.setVisible(false);
				} else if (fileModel.getSize() > 1) {
					fileModel.remove(it.getValue());
				}
			}
		});
		if (fileModel.size() > 0) {
			del.setVisible(true);
		}
	}

	public ListModelList getFileModel() {
		return fileModel;
	}

	public void setFileModel(ListModelList fileModel) {
		this.fileModel = fileModel;
	}

	public void initListbox(ListModelList fileModel) {
		filelist = (Listbox) this.getFirstChild();
		filelist.setModel(fileModel);
		if (fileModel.size() > 0) {
			del.setVisible(true);
		}
		// public void initListbox(List<Object> flist) {
		// filelist = (Listbox) this.getFirstChild();
		// fileModel = new ListModelList(flist);
		// filelist.setModel(fileModel);
		// if (fileModel.size() > 0) {
		// del.setVisible(true);
		// }
		/** 以下代码在查出来的实体没转换成media类型前有用 */
		filelist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				if (arg1 instanceof Media) {
					Media media = (Media) arg1;
					arg0.setValue(arg1);
					arg0.setLabel(media.getName());
				} else if (arg1 instanceof JXKH_MeetingFile) {
					JXKH_MeetingFile f = (JXKH_MeetingFile) arg1;
					arg0.setValue(arg1);
					arg0.setLabel(f.getName());
				} else if (arg1 instanceof JXKH_QKLWFile) {
					JXKH_QKLWFile f = (JXKH_QKLWFile) arg1;
					arg0.setValue(arg1);
					arg0.setLabel(f.getName());
				} else if (arg1 instanceof JXKH_HYLWFile) {
					JXKH_HYLWFile f = (JXKH_HYLWFile) arg1;
					arg0.setValue(arg1);
					arg0.setLabel(f.getName());
				}
			}
		});
	}

	public void initShow() {
		up.setVisible(true);	
		filelist = (Listbox) this.getFirstChild();
		filelist.setModel(fileModel);
		if (fileModel.size() > 0) {
			del.setVisible(true);
		}
	}
}
