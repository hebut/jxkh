package org.iti.projectmanage.teach.member;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.iti.gh.entity.GH_ARCHIVE;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.ArCommentService;
import org.iti.gh.service.ArchiveService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.common.util.DateUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;


public class JYArticalWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 3255000106611398024L;
	
	JYArticalWindow jxArticallist;
	WkTUser user;
	private GhXm xm;
	private GhJsxm member;
	private Listbox articalListbox;
	private ArchiveService archiveService;
	private UserService userService;
	private ArCommentService arCommentService;
	private Toolbarbutton add;
	private Paging arPaging;
	private int arRowNum = 0;
		
	public void afterCompose() 
	{
		
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		articalListbox.setItemRenderer(new ArListitemRenderer());
		arPaging.addEventListener("onPaging", new PagingListener());
	}
	
	/**
	 * 初始化当前窗口，并初始化参考文献列表
	 */
	public void initWindow()
	{
		if(member.getKuId().equals(user.getKuId()))
			add.setVisible(true);
		List<Long> countList = archiveService.getCountByKyIdAndKuId(xm.getKyId(),member.getKuId(),GhXm.JYXM);
		int articalNum = 0;
		if(null!=countList&&countList.size()>0)
		{
			articalNum = ((Long)countList.get(0)).intValue();
		}
		arPaging.setTotalSize(articalNum);
		List<GH_ARCHIVE> archiveList = archiveService.findByKyIdAndUserIdAndPage(xm.getKyId(), member.getKuId(),GhXm.JYXM, 0, arPaging.getPageSize());
		ListModel articalListModel = new ListModelList(archiveList);
		articalListbox.setModel(articalListModel);
	}
	
	/**
	 * 打开参考文献添加窗口，并将当前窗口类和项目实体传递过去，同时调用文献添加窗口的初始化方法
	 */
	public void onClick$add()
	{		
		JYArticalNewWindow w=(JYArticalNewWindow)
			Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/member/jyArticalNew.zul",null,null);
		w.setXm(xm);
		w.setJxArticallist(jxArticallist);
		w.initWindow();
		try {
			w.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回项目成员窗口
	 */
	public void onClick$reback()
	{
		Events.postEvent(Events.ON_CHANGE, this, null);
		jxArticallist.detach();
	}
	
	/**
	 * 参考文献列表渲染器
	 * @author Administrator
	 *
	 */
	public class ArListitemRenderer implements ListitemRenderer {

		public void render(Listitem item, Object obj) throws Exception {
			if(obj==null)
				return;
			GH_ARCHIVE archive = (GH_ARCHIVE)obj;
			item.setValue(archive);
			arRowNum = arPaging.getActivePage() * arPaging.getPageSize() + item.getIndex() + 1;
			Listcell c0 = new Listcell(arRowNum+"");
			Listcell c1 = new Listcell();
			if(archive.getArName().length()>25)
			{
				c1.setLabel(archive.getArName().substring(0,24)+"...");
			}else{
				c1.setLabel(archive.getArName());
			}
			c1.setStyle("color:blue");
			WkTUser postUser = userService.getUserBykuid(archive.getArUpUserId());
			Listcell c2 = new Listcell(postUser.getKuName());
			Listcell c3 = new Listcell(DateUtil.getDateString(archive.getArPostDate()));
			Listcell c4 = new Listcell();
			Hbox hbox = new Hbox();
			
			Toolbarbutton arDownload = new Toolbarbutton();//文献评论按钮
			arDownload.setImage("/css/sat/down.png");
			arDownload.setTooltip("下载");
			arDownload.addEventListener(Events.ON_CLICK, new DownloadEventListener());
			hbox.appendChild(arDownload);
			
			Toolbarbutton arView = new Toolbarbutton();//文献评论按钮
			arView.setImage("/css/sat/comment.png");
			arView.setTooltip("评论");
			arView.addEventListener(Events.ON_CLICK, new CommentEventListener());
			hbox.appendChild(arView);			
			
			Toolbarbutton arEdit = new Toolbarbutton();//编辑文献按钮
			arEdit.setImage("/css/sat/edit.gif");
			arView.setTooltip("编辑");
			arEdit.addEventListener(Events.ON_CLICK, new EditEventListener());
			hbox.appendChild(arEdit);
			
			//当前用户为项目负责人、或者是该参考文献的上传者时，才具有该条记录的"编辑"和"删除"权限
			if(user.getKuId().equals(archive.getArUpUserId())||user.getKuId().equals(xm.getKuId()))
			{
				Toolbarbutton arDel = new Toolbarbutton();//删除文献按钮
				arDel.setImage("/css/sat/actDel.gif");
				arView.setTooltip("删除");			
				arDel.addEventListener(Events.ON_CLICK, new DelEventListener());
				hbox.appendChild(arDel);
			}
			
			c4.appendChild(hbox);			
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);

		}
	}
	
	/**
	 * 参考文献附件下载事件监听器
	 * @author Administrator
	 *
	 */
	public class DownloadEventListener implements EventListener {

		public void onEvent(Event event) throws Exception 
		{
			Listitem item = (Listitem)event.getTarget().getParent().getParent().getParent();
			GH_ARCHIVE archive = (GH_ARCHIVE)item.getValue();
			String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
				+ File.separator+xm.getKyId() + File.separator + archive.getArPath();
			File archiveFile = new File(archivePath);
			try 
			{
				if(!archiveFile.exists())
				{
					Messagebox.show("参考文献已经被删除！", "提示", Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				Filedownload.save(archiveFile,null);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 参考文献删除事件监听器。触发后首先删除该文献的评论记录，然后删除该文献的附件，最后删除该文献记录
	 * @author Administrator
	 *
	 */
	public class DelEventListener implements EventListener 
	{
		public void onEvent(Event event) throws Exception 
		{
			if(Messagebox.NO==Messagebox.show("您是否确定删除该参考文献？", "确认", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION))
			{
				return;
			}
			Listitem item = (Listitem)event.getTarget().getParent().getParent().getParent();
			GH_ARCHIVE archive = (GH_ARCHIVE)item.getValue();
			boolean isSucceed = false;
			try
			{
				//首先删除该参考文献记录的评论
				isSucceed = arCommentService.deleteCommentsByArchive(archive.getArId());
				if(!isSucceed)
				{
					Messagebox.show("文献评论删除失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				//然后删除文献附件
				String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
					+ File.separator+xm.getKyId() + File.separator + archive.getArPath();
				File archiveFile = new File(archivePath);
				if(archiveFile.exists())
				{
					isSucceed = archiveFile.delete();
				}
				if(!isSucceed)
				{
					Messagebox.show("参考文献附件删除失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				//最后删除该参考文献记录
				archiveService.delete(archive);
				jxArticallist.initWindow();
			}catch(Exception e){
				Messagebox.show("参考文献删除失败，请重试", "提示！", Messagebox.OK, Messagebox.INFORMATION);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 参考文献编辑事件监听器。触发后打开文献编辑窗口
	 * @author Administrator
	 *
	 */
	public class EditEventListener implements EventListener 
	{
		public void onEvent(Event event) throws Exception 
		{
			Listitem item = (Listitem)event.getTarget().getParent().getParent().getParent();
			GH_ARCHIVE archive = (GH_ARCHIVE)item.getValue();
			JYArticalEditWindow w=(JYArticalEditWindow)
				Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/member/jyArticalEdit.zul",null,null);
			w.setXm(xm);
			w.setArchive(archive);
			w.setJxArticallist(jxArticallist);
			w.initWindow();
			w.doModal();
		}
	}

	/**
	 * 参考文献评论事件监听器。触发后打开文献评论窗口
	 * @author Administrator
	 *
	 */
	public class CommentEventListener implements EventListener
	{
		public void onEvent(Event event) throws Exception 
		{
			Listitem item = (Listitem)event.getTarget().getParent().getParent().getParent();
			GH_ARCHIVE archive = (GH_ARCHIVE)item.getValue();
			JYArCommentWindow w=(JYArCommentWindow)
				Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/member/jyArticalComment.zul",null,null);
			w.setArchive(archive);
			w.initWindow();
			w.doModal();
		}
	}
	
	/**
	 * 参考文献列表分页事件监听器
	 * @author Administrator
	 *
	 */
	public class PagingListener implements EventListener {

		public void onEvent(Event event) throws Exception {
			List<GH_ARCHIVE> archiveList = archiveService.findByKyIdAndUserIdAndPage(xm.getKyId(),member.getKuId(),GhXm.JYXM,arPaging.getActivePage(),arPaging.getPageSize());
			ListModel articalListModel = new ListModelList(archiveList);
			articalListbox.setModel(articalListModel);
		}

	}
	
	public GhXm getXm() {
		return xm;
	}

	public void setXm(GhXm xm) {
		this.xm = xm;
	}

	public GhJsxm getMember() {
		return member;
	}

	public void setMember(GhJsxm member) {
		this.member = member;
	}
	
	
}