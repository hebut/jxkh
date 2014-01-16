package org.iti.projectmanage.teach.member;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GH_ARCHIVE;
import org.iti.gh.entity.GH_ARCHIVECOMMENT;
import org.iti.gh.service.ArCommentService;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class JYArCommentWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = -3150477057105786106L;
	
	GH_ARCHIVE archive;
	WkTUser user;
	private Image userImage;
	private Label userName,userSuject,time;
	private Toolbarbutton fileName;
	private Html contentHtml;
	private Grid commentGrid;
	private FCKeditor commentFck;
	private UserService userService;
	private ArCommentService arCommentService;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		commentGrid.setRowRenderer(new CommentRowRenderer());
	}

	/**
	 * 初始化参考文献评论窗口
	 */
	public void initWindow()
	{
		WkTUser arUser = userService.getUserBykuid(archive.getArUpUserId());
		String imagePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/admin/image/head/" + arUser.getKuLid().trim() + ".jpg");
		File imageFile = new File(imagePath);
		if (imageFile.exists()) {
			userImage.setSrc(imagePath.replace("\\", "/"));
		} else {
			userImage.setSrc("/admin/image/default.jpg");
		}
		userName.setValue(arUser.getKuName());
		userSuject.setValue(arUser.getDept().getKdName());
		time.setValue("文献上传于："+DateUtil.getTimeString(archive.getArUpTime()));
		String archivePath=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
			+ File.separator+archive.getArProId() + File.separator + archive.getArPath();
		File arFile = new File(archivePath);
		if(arFile.exists())
		{
			fileName.setLabel(archive.getArPath());
			fileName.addEventListener(Events.ON_CLICK, new DownloadListener());
		}
		contentHtml.setContent(archive.getArReadFeel());
		initShow();
	}
	
	/**
	 * 初始化当前参考文献评论列表
	 */
	public void initShow()
	{
		commentFck.setValue("");
		List<GH_ARCHIVECOMMENT> commentList = arCommentService.findByArId(archive.getArId());
		ListModelList commentListModel = new ListModelList(commentList);
		commentGrid.setModel(commentListModel);
	}
	
	/**
	 * 提交用户评论信息
	 */
	public void onClick$save()
	{
		try {
			if("".equals(commentFck.getValue().trim())||commentFck.getValue().trim().length()==0)
			{
				Messagebox.show("评论内容不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			if(commentFck.getValue().length()>254)
			{
				
				Messagebox.show("评论内容过长，请重新编辑！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
		GH_ARCHIVECOMMENT comment = new GH_ARCHIVECOMMENT();
		comment.setArId(archive.getArId());
		comment.setCommContext(commentFck.getValue().trim());
		comment.setCommDate(DateUtil.getDateTimeString(new Date()));
		comment.setCommUserId(user.getKuId());
		comment.setCommUserMajor(user.getDept().getKdName());
		comment.setCommUserName(user.getKuName());
		try{
			arCommentService.save(comment);
			initShow();
			commentGrid.setActivePage(commentGrid.getPageCount()-1);
		}catch(Exception e){
			try {
				Messagebox.show("评论内容提交失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭当前窗口
	 */
	public void onClick$close()
	{
		this.detach();
	}
	
	/**
	 * 参考文献评论列表渲染器。用户只能删除自己提交的评论
	 * @author Administrator
	 *
	 */
	public class CommentRowRenderer implements RowRenderer {

		public void render(Row row, Object data) throws Exception 
		{
			if(null==data)
				return;
			GH_ARCHIVECOMMENT comment = (GH_ARCHIVECOMMENT)data;
			row.setValue(comment);
			row.setValign("top");
			Vbox left = new Vbox();
			left.setAlign("center");
			WkTUser commUser = userService.getUserBykuid(comment.getCommUserId());
			String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/admin/image/head/" + commUser.getKuLid() + ".jpg");
			File file = new File(path);
			Image image = new Image();
			image.setWidth("30px");
			image.setHeight("30px");
			if (file.exists()) {
				image.setSrc(path.replace("\\", "/"));
			} else {
				image.setSrc("/admin/image/default.jpg");
			}
			Label name = new Label();
			Label major = new Label();
			name.setValue(comment.getCommUserName());
			name.setStyle("font-family:'宋体';color:red");
			major.setValue(comment.getCommUserMajor());
			major.setStyle("color:gray");
			left.appendChild(image);
			left.appendChild(name);
			left.appendChild(major);
			
			Vbox right = new Vbox();
			Hbox timeHbox = new Hbox();
			Label time = new Label("回复于：" + DateUtil.getTimeString(comment.getCommDate()));
			time.setStyle("color:gray");
			time.setWidth("400px");
			InnerButton delete = new InnerButton("删除");
			delete.setStyle("color:red");
			delete.addEventListener(Events.ON_CLICK, new DelEventListener());
			timeHbox.appendChild(time);
			if (user.getKuId().equals(comment.getCommUserId())) {
				timeHbox.appendChild(delete);
			}
			Html contents = new Html();
			contents.setContent(comment.getCommContext());
			right.appendChild(timeHbox);
			right.appendChild(contents);
			
			row.appendChild(left);
			row.appendChild(right);
		}
	}
	
	/**
	 * 参考文献评论删除事件监听器
	 * @author Administrator
	 *
	 */
	public class DelEventListener implements EventListener {

		public void onEvent(Event event) throws Exception {
			if (Messagebox.show("是否要删除此评论？", "提示", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) 
			{
				Row row = (Row)event.getTarget().getParent().getParent().getParent();
				GH_ARCHIVECOMMENT comment = (GH_ARCHIVECOMMENT)row.getValue();
				arCommentService.delete(comment);
				initShow();
			}
		}
	}
	
	/**
	 * 参考文献附件下载事件监听器
	 * @author Administrator
	 *
	 */
	public class DownloadListener implements EventListener {

		public void onEvent(Event event) throws Exception {
			String archivePath=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
				+ File.separator+archive.getArProId() + File.separator + archive.getArPath();
			File arFile = new File(archivePath);
			if(!arFile.exists())
			{
				Messagebox.show("参考文献已不存在，请确认！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			try{
				Filedownload.save(arFile, null);
			}catch(Exception e){
				Messagebox.show("参考文献下载失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			}
		}

	}

	public GH_ARCHIVE getArchive() {
		return archive;
	}

	public void setArchive(GH_ARCHIVE archive) {
		this.archive = archive;
	}
	
}
