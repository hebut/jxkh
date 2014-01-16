package com.uniwin.asm.personal.group.discuss;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Configuration;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzMessage;
import com.uniwin.asm.personal.entity.QzSubject;
import com.uniwin.asm.personal.service.QzMessageService;
import com.uniwin.asm.personal.service.RelationService;
import com.uniwin.asm.personal.service.SubjectService;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class MessageWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	QzSubject subject = (QzSubject) Executions.getCurrent().getArg().get("subject");
	Rows rows;
	QzMessageService qzMessageService;
	SubjectService subjectService;
	RelationService relationService;
	WkTUser user;
	String Path = "";

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		inviteTimes();
		LoadMessgegrid();
	}

	public void inviteTimes() {
		// 更新用户访问次数
		subject.setSjViewsum(subject.getSjViewsum() + 1);
		subjectService.update(subject);
	}

	private void LoadMessgegrid() {
		List mlist = qzMessageService.findBtSjid(subject.getSjId(), Short.parseShort("1"));
		Row firstRow = new Row();
		firstRow.setStyle("padding:1;border-width:medium 0px 1px;background-color:#E8F3FD");
		{
			Vbox firstLeft = new Vbox();
			firstLeft.setAlign("center");
			{
				Image image = new Image();
				image.setSrc("/admin/image/default.jpg");
				image.setWidth("100px");
				image.setHeight("100px");
				Label qzname = new Label(subject.getUser().getKuName());
				qzname.setStyle("font-family:'华文楷体';font-size:15px;color:red");
				Label zctime = new Label(subject.getUser().getDept().getKdName());
				zctime.setStyle("color:gray");
				firstLeft.appendChild(image);
				firstLeft.appendChild(qzname);
				firstLeft.appendChild(zctime);
			}
			Vbox firstRight = new Vbox();
			{
				Label fbtime = new Label("话题发起于：" + ConvertUtil.convertDateAndTimeString(subject.getSjTime()));
				fbtime.setStyle("color:gray");
				Label sjtitle = new Label("主题：" + subject.getSjTitle());
				sjtitle.setStyle("font-family:'宋体';font-size:14px");
				Hbox hbox = new Hbox();
				hbox.setAlign("center");
				{
					Label extra = new Label("附件：");
					extra.setStyle("font-family:'宋体';font-size:14px");
					InnerButton download = new InnerButton();
					if (subject.getSjPath() != null && !subject.getSjPath().equals("")) {
						String strFile = subject.getSjPath();
						strFile = strFile.substring(strFile.indexOf("-") + 1);
						download.setLabel(strFile);
						download.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								downloadFile(subject.getSjPath());
							}
						});
						extra.setVisible(true);
						download.setVisible(true);
					} else {
						extra.setVisible(false);
						download.setVisible(false);
					}
					hbox.appendChild(extra);
					hbox.appendChild(download);
				}
				Html contents = new Html();
				contents.setContent(subject.getSjContent());
				firstRight.appendChild(fbtime);
				firstRight.appendChild(new Separator());
				firstRight.appendChild(sjtitle);
				firstRight.appendChild(hbox);
				firstRight.appendChild(new Separator());
				firstRight.appendChild(contents);
			}
			firstRow.appendChild(firstLeft);
			firstRow.appendChild(firstRight);
		}
		rows.appendChild(firstRow);
		if (mlist != null && mlist.size() > 0) {
			for (int i = 0; i < mlist.size(); i++) {
				final QzMessage qm = (QzMessage) mlist.get(i);
				Row row = new Row();
				row.setStyle("padding:0;border-width:medium 0px 1px;background-color:#FFFFFF");
				// 用户信息
				Vbox mainLeft = new Vbox();
				mainLeft.setAlign("center");
				{
					String path = this.getDesktop().getWebApp().getRealPath("/admin/image/head/" + qm.getUser().getKuLid().trim() + ".jpg");
					File file = new File(path);
					Image image = new Image();
					image.setWidth("100px");
					image.setHeight("100px");
					if (file.exists()) {
						image.setSrc(path.replace("\\", "/"));
					} else {
						image.setSrc("/admin/image/default.jpg");
					}
					Label name = new Label();
					Label dept = new Label();
					name.setValue(qm.getUser().getKuName());
					name.setStyle("font-family:'宋体';font-size:15px;color:red");
					dept.setValue(qm.getUser().getDept().getKdName());
					dept.setStyle("color:gray");
					mainLeft.appendChild(image);
					mainLeft.appendChild(name);
					mainLeft.appendChild(dept);
				}
				row.appendChild(mainLeft);
				// 回复的消息信息
				Hbox mainRight = new Hbox();
				{
					Vbox vbox = new Vbox();
					vbox.setWidth("550px");
					{
						Hbox uphbox = new Hbox();
						{
							Label time = new Label("回复于：" + ConvertUtil.convertDateAndTimeString(qm.getMgTime()));
							time.setStyle("color:gray");
							time.setWidth("400px");
							InnerButton delete = new InnerButton("删除");
							delete.setStyle("color:red");
							delete.addEventListener(Events.ON_CLICK, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									if (Messagebox.show("是否要删除此留言？", "提示", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
										deleteFile(qm.getMgPath());
										qzMessageService.delete(qm);
										rows.getChildren().clear();
										LoadMessgegrid();
									}
								}
							});
							Space space = new Space();
							uphbox.appendChild(time);
							uphbox.appendChild(space);
							if (qm.getMgSpeaker() == user.getKuId().longValue()) {
								uphbox.appendChild(delete);
							}
						}
						Hbox downhbox = new Hbox();
						downhbox.setAlign("center");
						{
							if (qm.getMgPath() != null && qm.getMgPath().length() != 0) {
								Label extra = new Label("附件：");
								extra.setStyle("font-family:'宋体';font-size:14px");
								InnerButton download = new InnerButton();
								String strFile = qm.getMgPath();
								strFile = strFile.substring(strFile.indexOf("-") + 1);
								download.setLabel(strFile);
								download.addEventListener(Events.ON_CLICK, new EventListener() {
									public void onEvent(Event arg0) throws Exception {
										downloadFile(qm.getMgPath());
									}
								});
								downhbox.appendChild(extra);
								downhbox.appendChild(download);
							}
						}
						Html contents = new Html();
						contents.setContent(qm.getMgContent());
						vbox.appendChild(uphbox);
						vbox.appendChild(new Separator());
						vbox.appendChild(downhbox);
						vbox.appendChild(new Separator());
						vbox.appendChild(contents);
					}
					mainRight.appendChild(vbox);
				}
				row.appendChild(mainRight);
				rows.appendChild(row);
			}
		}
		Row lastRow = new Row();
		lastRow.setStyle("background-color:#FFFFFF");
		lastRow.setSpans("2,0");
		Vbox vbox = new Vbox();
		final FCKeditor editor = new FCKeditor();
		editor.setHeight("260px");
		editor.setToolbarSet("Basic");
		vbox.setWidth("100%");
		{
			Separator separator = new Separator();
			separator.setBar(true);
			Grid grid = new Grid();
			{
				Columns columns = new Columns();
				{
					Column leftColumn = new Column();
					leftColumn.setWidth("10%");
					leftColumn.setAlign("center");
					Column rightColumn = new Column();
					columns.appendChild(leftColumn);
					columns.appendChild(rightColumn);
				}
				grid.appendChild(columns);
				Rows rows = new Rows();
				{
					Row upRow = new Row();
					{
						Label text = new Label("发表内容");
						upRow.appendChild(text);
						upRow.appendChild(editor);
					}
					Row middleRow = new Row();
					middleRow.setStyle("background-color:#FFFFFF");
					{
						Label text = new Label("上传附件");
						Hbox hbox = new Hbox();
						{
							final Label filename = new Label();
							final Button upload = new Button("上传");
							final Button delete = new Button("删除");
							upload.setVisible(true);
							delete.setVisible(false);
							upload.addEventListener(Events.ON_CLICK, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									upload(filename, upload, delete);
								}
							});
							delete.addEventListener(Events.ON_CLICK, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									delete(filename, upload, delete);
								}
							});
							hbox.appendChild(filename);
							hbox.appendChild(upload);
							hbox.appendChild(delete);
						}
						middleRow.appendChild(text);
						middleRow.appendChild(hbox);
					}
					Row downRow = new Row();
					downRow.setSpans("2,0");
					{
						Hbox hbox = new Hbox();
						{
							Button submit = new Button("发表回复");
							Button close = new Button("关闭页面");
							submit.addEventListener(Events.ON_CLICK, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									submit(editor);
								}
							});
							close.addEventListener(Events.ON_CLICK, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									close();
								}
							});
							hbox.appendChild(submit);
							hbox.appendChild(close);
						}
						downRow.appendChild(hbox);
					}
					rows.appendChild(upRow);
					rows.appendChild(middleRow);
					rows.appendChild(downRow);
				}
				grid.appendChild(rows);
			}
			vbox.appendChild(separator);
			vbox.appendChild(grid);
		}
		lastRow.appendChild(vbox);
		rows.appendChild(lastRow);
	}

	public void upload(Label filename, Button upload, Button delete) throws InterruptedException, IOException {
		Media media = Fileupload.get();
		if (media != null) {
			Long timekey = (new Date()).getTime();
			Path = this.getDesktop().getWebApp().getRealPath("/upload/group/" + user.getKuId());
			Configuration conf = this.getDesktop().getWebApp().getConfiguration();
			conf.setMaxUploadSize(1024 * 1024); // 最大1G
			conf.setUploadCharset("UTF-8");
			WkTUser wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获得登录用户
			String kuId = wkTUser.getKuId().toString();
			
				String fileName = media.getName();
				if (fileName.endsWith(".txt") || fileName.endsWith(".project")) {
					Reader r = media.getReaderData();
					//String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/");
					if (Path == null) {
						System.out.println("无法访问存储目录！");
						return;
					}
					File fUploadDir = new File(Path);
					if (!fUploadDir.exists()) {
						if (!fUploadDir.mkdir()) {
							System.out.println("无法创建存储目录！");
							return;
						}
					}
					String uploadDir = this.getDesktop().getWebApp().getRealPath("/upload/group");
					if (uploadDir == null) {
						System.out.println("无法访问存储目录！");
						return;
					}
					File fUploadDir2 = new File(uploadDir + "\\" + kuId); // 在upload文件下创建用户文件夹
					//String path2 = fUploadDir2.getCanonicalPath(); // 保存在path路径下
					if (!fUploadDir2.exists()) {
						if (!fUploadDir2.mkdir()) {
							System.out.println("无法创建存储目录！");
							return;
						}
					}
					String name = Executions.getCurrent().getDesktop().getWebApp().getRealPath("\\upload\\group") + "\\" + kuId + "\\" + timekey + "-" + fileName.trim(); // 文件保存名
					File f = new File(name);
					Files.copy(f, r, null);
					Path = "\\upload\\group" + "\\" + kuId + "\\" + timekey + "-" + fileName.trim();
					Messagebox.show("上传成功: " + media.getName(), "提示", Messagebox.OK, Messagebox.INFORMATION);
					filename.setValue(media.getName());
					upload.setVisible(false);
					delete.setVisible(true);
				} else {
					InputStream objin = media.getStreamData();
					//String fName = media.getName();
					//String path = this.getDesktop().getWebApp().getRealPath("/upload/");
					String pa = this.getDesktop().getWebApp().getRealPath("/upload/group");
					if (pa == null) {
						System.out.println("无法访问存储目录！");
						return;
					}
					File fUploadDir = new File(pa);
					if (!fUploadDir.exists()) {
						if (!fUploadDir.mkdir()) {
							System.out.println("无法创建存储目录！");
							return;
						}
					}
					String uploadDir = this.getDesktop().getWebApp().getRealPath("/upload/group/");
					if (uploadDir == null) {
						System.out.println("无法访问存储目录！");
						return;
					}
					File fUploadDir2 = new File(uploadDir + "\\" + kuId); // 在upload文件下创建用户文件夹
					//String path2 = fUploadDir2.getCanonicalPath(); // 保存在path路径下
					if (!fUploadDir2.exists()) {
						if (!fUploadDir2.mkdir()) {
							System.out.println("无法创建存储目录！");
							return;
						}
					}
					String name = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/group") + "//" + kuId + "//" + timekey + "-" + fileName.trim(); // 文件保存名
					FileOutputStream out = new FileOutputStream(name);
					DataOutputStream objout = new DataOutputStream(out);
					Files.copy(objout, objin);
					Messagebox.show("上传成功: " + media.getName(), "提示", Messagebox.OK, Messagebox.INFORMATION);
					Path = "\\upload\\group" + "\\" + kuId + "\\" + timekey + "-" + fileName.trim();
					filename.setValue(media.getName());
					upload.setVisible(false);
					delete.setVisible(true);
				}
		} else if (media == null) {
			Messagebox.show("您没有选择上传文件，请选择！ ", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	}

	public void delete(Label filename, Button upload, Button delete) {
		deleteFile(Path);
		upload.setVisible(true);
		delete.setVisible(false);
		filename.setValue("");
		Path = "";
	}

	public void submit(FCKeditor editor) throws InterruptedException {
		if (!editor.getValue().equals("") && editor.getValue().length() != 0) {
			if (Messagebox.show("您确定要发布此条回复吗？", "询问", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
				Date d = new Date();
				QzMessage qm = new QzMessage();
				qm.setSjId(subject.getSjId());
				qm.setMgTime(d);
				qm.setMgContent(editor.getValue());
				qm.setMgSpeaker(user.getKuId());
				qm.setMgPath(Path);
				qzMessageService.save(qm);
				//回复后重新可见
				relationService.updatesql("update QZ_RELATION set RL_SHOW = ? ,RL_STATE =? where SJ_ID = ? and KU_ID <> ?",new Object[]{Short.valueOf("2"),Short.valueOf("2"),subject.getSjId(),user.getKuId()});
				rows.getChildren().clear();
				LoadMessgegrid();
				Path = "";
			} else {
				return;
			}
		} else {
			Messagebox.show("你的回复消息不可以为空！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	}

	public void close() {
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
	}

	public void onClose$window() {
		close();
	}

	public void deleteFile(String Path) {
		if (Path != null && Path.length() != 0) {
			String RealPath = this.getDesktop().getWebApp().getRealPath(Path);
			File file = new File(RealPath);
			if (file.exists())
				file.delete();
		}
	}

	public void downloadFile(String relativePath) throws InterruptedException {
		java.io.InputStream is = this.getDesktop().getWebApp().getResourceAsStream(relativePath);
		String fileName = "";
		if (is != null) {
			int i = relativePath.lastIndexOf("\\");
			if (i <= 0) {
				i = relativePath.lastIndexOf("/");
			}
			fileName = relativePath.substring(i + 1);
			Filedownload.save(is, "text/html", fileName);
		} else {
			Messagebox.show("服务器不存在此文件 ", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}
}
