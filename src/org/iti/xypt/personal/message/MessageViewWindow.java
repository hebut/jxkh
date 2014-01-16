package org.iti.xypt.personal.message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.entity.XyMReceiver;
import org.iti.xypt.entity.XyMReceiverId;
import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.service.MessageService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class MessageViewWindow extends Window implements AfterCompose {
	// 消息数据接口
	private MessageService messageService;
	// 消息输入框
	private Label xmSubject, xmSource, xmSendTime, xmReceive;
	// Html标签显示内容
	private Html xmContent;
	// 消息实体
	private XyMessage notice;
	// 行
	private Row rowFile, xmReceiveRow;
	// list模型
	Listbox downList;
	List fileNameList = new ArrayList();;
	ListModelList modelListbox;
	// 收消息列表数据接口
	WkTUser wkTUser;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		modelListbox = new ListModelList(fileNameList);
		downList.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				arg0.setValue(arg1);
				File f = (File) arg1;
				arg0.setValue(arg1);
				arg0.setLabel(f.getName() + "   " + "(" + f.length() / 1024 + "KB" + ")");
			}
		});
		downList.setModel(modelListbox);
	}

	/**
	 * <Li>用于在perReceive页面加载数据，初始化窗口 2010-4-8
	 * 
	 * @author bobo
	 * 
	 */
	public void initWindow(XyMessage notice) throws IOException {
		this.notice = notice;
		xmReceive.setValue(notice.getXmReceivers());
		if (notice.getKuId().longValue() == wkTUser.getKuId().longValue()) {
			xmReceiveRow.setVisible(true);
		} else {
			xmReceiveRow.setVisible(false);
		}
		xmSubject.setValue(notice.getXmSubject());
		xmSource.setValue(notice.getXmSender());
		xmContent.setContent(notice.getXmContent());
		xmSendTime.setValue(ConvertUtil.convertDateAndTimeString(notice.getXmSndtime()));
		if (notice.getXmHfile().shortValue() == XyMessage.HFILE_NO.shortValue()) {
			rowFile.setVisible(false);
		} else {
			rowFile.setVisible(true);
			String basepath = Executions.getCurrent().getDesktop().getWebApp().getRealPath(XyMessage.BASE_FILE);
			File folder = new File(basepath + "\\" + notice.getReleativeFilePath());
			String[] filelist = folder.list();
			if (filelist != null && filelist.length != 0) {
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(folder.getAbsolutePath() + "\\" + filelist[i]);
					if (readfile.exists()) {
						modelListbox.add(readfile);
					}
				}
			}
		}
		XyMReceiverId xymId = new XyMReceiverId(notice.getXmId(), wkTUser.getKuId());
		XyMReceiver xym = (XyMReceiver) messageService.get(XyMReceiver.class, xymId);
		if (xym != null && xym.getXmrState().shortValue() == XyMReceiver.STATE_UNREAD.shortValue()) {
			xym.setXmrState(XyMReceiver.STATE_READED);
			messageService.update(xym);
		}
	}

	/**
	 * <Li>ZK组件下载附件 2010-4-11
	 * 
	 * @author bobo
	 * @throws FileNotFoundException
	 * 
	 */
	public void onClick$download() throws InterruptedException, FileNotFoundException {
		Listitem it = downList.getSelectedItem();
		if (it == null) {
			if (modelListbox.getSize() > 0) {
				it = downList.getItemAtIndex(0);
			} else {
				return;
			}
		}
		File f = (File) it.getValue();
		Filedownload.save(f, null);
	}

	public void onClick$concel() {
		this.detach();
	}

	public void onClick$reply() {
		this.notice = notice;
		List userList = new ArrayList();
		MessageNewWindow win = (MessageNewWindow) Executions.createComponents("/admin/personal/message/new/index.zul", null, null);
		WkTUser user = (WkTUser) messageService.get(WkTUser.class, notice.getKuId());
		userList.add(user);
		win.addReceiver(userList);
		win.doHighlighted();
		win.setWidth("800px");
		win.setBorder("normal");
		win.setTitle("回复消息");
		win.setClosable(true);
	}

	public ListModelList getModelList() {
		return modelListbox;
	}
}
