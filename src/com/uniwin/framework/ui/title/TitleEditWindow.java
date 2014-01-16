package com.uniwin.framework.ui.title;

/**
 * <li> 标题编辑功能，当标题管理中左侧选中标题后就会显示页面admin\system\title\tedit.zul
 * @author DaLei 
 * @2010-3-15
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.common.listbox.TitleListbox;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;
import com.uniwin.framework.service.TitleService;

public class TitleEditWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 当前编辑的标题对象
	WkTTitle editTitle;
	// 标题编辑的 输入框
	Textbox ktname, ktsecu, ktcontent;
	Intbox ktOrder;
	// 标题数据访问服务接口
	TitleService titleService;
	// 添加及权限设置按钮
	Button addButton, authButton, upImage;
	Listbox kttype, ktShow;
	// 父标题选择组件
	TitleListbox pselect;
	// 显示标题图片区域
	Hbox pics;
	WkTUser user;
	MLogService mlogService;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
	}

	public void onClick$upImage() throws InterruptedException {
		Media media = null;
		try {
			media = Fileupload.get();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if (media instanceof org.zkoss.image.Image) {
			pics.getChildren().clear();
			org.zkoss.zul.Image image = new org.zkoss.zul.Image();
			image.setContent((org.zkoss.image.Image) media);
			image.setWidth("25px");
			image.setHeight("25px");
			image.setParent(pics);
			Button b = new Button();
			b.setLabel("删除");
			b.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
				public void onEvent(Event event) throws Exception {
					pics.getChildren().clear();
				}
			});
			b.setParent(pics);
		} else {
			Messagebox.show("请选择图片上传！", "上传错误", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	public void onClick$submit() throws InterruptedException, IOException {
		editTitle.setKtName(ktname.getValue());
		editTitle.setKtSecurity(ktsecu.getValue());
		editTitle.setKtOrdno(Long.parseLong(ktOrder.getValue() + ""));
		editTitle.setKtContent(ktcontent.getValue());
		switch (kttype.getSelectedIndex()) {
		case 0:
			editTitle.setKtType("21");
			break;
		case 1:
			editTitle.setKtType("22");
			break;
		case 2:
			editTitle.setKtType("23");
			break;
		default:
			break;
		}
		editTitle.setKtNewwin(ktShow.getSelectedIndex() + "");
		WkTTitle t = (WkTTitle) pselect.getSelectedItem().getValue();
		editTitle.setKtPid(t.getKtId());
		// 保存图标
		List<Image> clist = pics.getChildren();
		if (clist.size() > 0) {
			Image img = (Image) clist.get(0);
			if (img.getContent() != null) {
				byte[] out = img.getContent().getByteData();
				String repSrc = Sessions.getCurrent().getWebApp().getRealPath("/upload/title");
				String fileName = editTitle.getKtId() + "_" + DateUtil.getDateTimeString(new Date()) + img.getContent().getName().substring(img.getContent().getName().indexOf("."));
				String path = repSrc + "\\" + fileName;
				System.out.println(path);
				File f = new File(repSrc + "\\" + editTitle.getKtImage());
				if (f.exists()) {
					f.delete();
				}
				f = new File(path);
				FileOutputStream fos = new FileOutputStream(new File(path));
				fos.write(out);
				fos.close();
				editTitle.setKtImage(fileName);
			}
		} else {
			if (editTitle.getKtImage() != null && editTitle.getKtImage().trim().length() > 0) {
				String repSrc = Sessions.getCurrent().getWebApp().getRealPath("/upload/title");
				File f = new File(repSrc + "\\" + editTitle.getKtImage());
				if (f.exists()) {
					f.delete();
				}
			}
			editTitle.setKtImage("");
		}
		titleService.update(editTitle);
		Executions.getCurrent().setAttribute("editTitle", editTitle);
		Sessions.getCurrent().setAttribute("editTitle", editTitle);
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "编辑标题，id:" + editTitle.getKtId(), user);
		if (Messagebox.show("保存成功,是否更新左侧树?", "编辑标题", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
			refreshTree();
		}
	}

	public void onClick$reset() {
		initWindow(getEditTitle());
	}

	public void onClick$delete() throws InterruptedException {
		List<WkTTitle> clist = titleService.getChildTitle(editTitle.getKtId());
		if (clist.size() > 0) {
			if (Messagebox.show("含有子标题，将一起删除", "注意", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
				deleteTitle(editTitle, clist);
			} else {
				return;
			}
		} else {
			if (Messagebox.show("您确定要删除吗?", "请确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
				deleteTitle(editTitle, null);
				this.editTitle = (WkTTitle) titleService.get(WkTTitle.class, editTitle.getKtPid());
			}
		}
		refreshTree();
	}

	private void deleteTitle(WkTTitle title, List<WkTTitle> clist) {
		if (clist != null)
			for (int i = 0; i < clist.size(); i++) {
				WkTTitle ctitle = (WkTTitle) clist.get(i);
				List<WkTTitle> nclist = titleService.getChildTitle(ctitle.getKtId());
				deleteTitle(ctitle, nclist);
			}
		mlogService.saveMLog(WkTMlog.FUNC_ADMIN, "删除标题，id:" + title.getKtId(), user);
		titleService.delete(title);
	}

	/**
	 * <li>功能描述：增加标题按钮事件处理。 void
	 * 
	 * @author DaLei
	 */
	public void onClick$addButton() {
		final TitleNewWindow w = (TitleNewWindow) Executions.createComponents("/admin/system/title/titleNew.zul", null, null);
		w.doHighlighted();
		w.setClosable(true);
		w.initWindow(getEditTitle());
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				refreshTree();
				w.detach();
			}
		});
	}

	public WkTTitle getEditTitle() {
		return editTitle;
	}

	public void onClick$authButton() {
		TitleAuthWindow w = (TitleAuthWindow) Executions.createComponents("/admin/system/title/titleAuth.zul", null, null);
		w.doHighlighted();
		w.setClosable(true);
		w.initWindow(editTitle);
	}

	/**
	 * <li>功能描述：初始化标题编辑界面.
	 * 
	 * @param editTitle
	 *            编辑的标题 void
	 * @author DaLei
	 */
	public void initWindow(WkTTitle editTitle) {
		this.editTitle = editTitle;
		this.setTitle("编辑标题：" + editTitle.getKtName());
		ktname.setValue(editTitle.getKtName());
		ktsecu.setValue(editTitle.getKtSecurity());
		ktOrder.setValue(editTitle.getKtOrdno().intValue());
		ktcontent.setValue(editTitle.getKtContent());
		pselect.initPTitleSelect(editTitle);
		ktShow.setSelectedIndex(Integer.valueOf(editTitle.getKtNewwin()));
		switch (Integer.parseInt(editTitle.getKtType())) {
		case 21:
			kttype.setSelectedIndex(0);
			break;
		case 22:
			kttype.setSelectedIndex(1);
			break;
		case 23:
			kttype.setSelectedIndex(2);
			break;
		default:
			kttype.setSelectedIndex(0);
			break;
		}
		pics.getChildren().clear();
		// 初始化显示标题图片了
		if (editTitle.getKtImage() != null && editTitle.getKtImage().trim().length() > 0) {
			Image img = new Image();
			img.setSrc("/upload/title/" + editTitle.getKtImage().trim());
			img.setWidth("25px");
			img.setHeight("25px");
			img.setParent(pics);
			Button b = new Button();
			b.setLabel("删除");
			b.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
				public void onEvent(Event event) throws Exception {
					pics.getChildren().clear();
				}
			});
			b.setParent(pics);
		}
	}

	/**
	 * <li>功能描述：刷新页面左侧树。 void
	 * 
	 * @author DaLei
	 */
	private void refreshTree() {
		Events.postEvent(Events.ON_CHANGE, this, null);
	}
}
