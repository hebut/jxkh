package org.iti.projectmanage.teach.Literature;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.iti.gh.common.util.DateUtil;
import org.iti.gh.entity.GH_ARCHIVE;
import org.iti.gh.entity.GhXm;
import org.iti.projectmanage.science.member.ArticalComment;
import org.iti.projectmanage.science.reference.service.ReferenceService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class LiteratureSearchWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 8411633296853562210L;
	
	private Listbox sourceTypeListbox,searchFieldsListbox,referenceListbox;
	private Paging referencePaging;
	private Textbox searchContent,clc,issue;
	private Datebox startDate,endDate;
	
	private ReferenceService referenceService;
	private UserService userService;
	private ListModel arListModel;
	private int sourceType;//文献来源类型
	private int searchFiled;//文献检索类型
	private String content;//文献检索内容
	private String startTime;//文献检索开始时间
	private String endTime; //文献检索结束时间
	private String clcString;//中图分类号
	private String issueString;//文献所在期目
	
	public void afterCompose() 
	{
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		referenceListbox.setItemRenderer(new ReItemRenderer());
		referencePaging.addEventListener("onPaging", new PagingListener());
		initWindow();
	}
	
	/**
	 * 初始化当前窗口
	 */
	public void initWindow()
	{
	}
	
	/**
	 * 根据输入的内容查询符合条件的参考文件列表
	 */
	public void onClick$search()
	{
		sourceType = sourceTypeListbox.getSelectedIndex();
		searchFiled = searchFieldsListbox.getSelectedIndex();
		content = searchContent.getValue().trim();
		clcString = clc.getValue().trim();
		issueString = issue.getValue().trim();
		if(null==startDate.getValue())
		{
			startTime = "";
		}else{
			startTime = DateUtil.getDateString(startDate.getValue());
		}
		if(null==endDate.getValue())
		{
			endTime = "";
		}else{
			endTime = DateUtil.getDateString(endDate.getValue());
		}
		List<Long> countList = referenceService.findCountByTKAPSF(sourceType, sourceType, content, startTime, endTime,clcString,issueString,GhXm.JYXM);
		int reTotalSize = 0;
		if(null!=countList&&countList.size()>0)
		{
			reTotalSize = ((Long)countList.get(0)).intValue();
		}
		referencePaging.setTotalSize(reTotalSize);
		referencePaging.setActivePage(0);
		List<GH_ARCHIVE> arList = referenceService.findByTKAPSFAndPage(sourceType, searchFiled,
				content, startTime, endTime,clcString,issueString,GhXm.JYXM, 0, referencePaging.getPageSize());
		arListModel = new ListModelList(arList);
		referenceListbox.setModel(arListModel);
		
	}
	
	/**
	 * 初始化查询条件输入组件的值
	 */
	public void onClick$reset()
	{
		sourceTypeListbox.setSelectedIndex(0);
		searchFieldsListbox.setSelectedIndex(0);
		searchContent.setValue("");
		startDate.setValue(null);
		endDate.setValue(null);
	}

	/**
	 * 参考文献列表渲染器
	 * @author Administrator
	 *
	 */
	public class ReItemRenderer implements ListitemRenderer {

		public void render(Listitem item, Object obj) throws Exception {
			if(null==obj)
				return;
			GH_ARCHIVE archive = (GH_ARCHIVE)obj;
			item.setValue(archive);
			Listcell c0 = new Listcell(item.getIndex()+1+"");
			Listcell c1 = new Listcell();
			if(archive.getArName().length()>13)
			{
				c1.setLabel(archive.getArName().substring(0, 12)+"...");
			}else{
				c1.setLabel(archive.getArName());
			}
			c1.setTooltiptext(archive.getArName());
			c1.setStyle("color:blue");
			Listcell c2 = new Listcell();
			if(archive.getArAuthor().length()>12)
			{
				c2.setLabel(archive.getArAuthor().substring(0, 11)+"...");
			}else{
				c2.setLabel(archive.getArAuthor());
			}
			Listcell c3 = new Listcell();
			if(archive.getArSource().length()>12)
			{
				c3.setLabel(archive.getArSource().substring(0, 11)+"...");
			}else{
				c3.setLabel(archive.getArSource());
			}
			Listcell c4 = new Listcell(DateUtil.getDateString(archive.getArPostDate()));
			WkTUser upUser = userService.getUserBykuid(archive.getArUpUserId());
			Listcell c5 = new Listcell(upUser.getKuName());
			Listcell c6 = new Listcell();
			Hbox hbox = new Hbox();
			Toolbarbutton download = new Toolbarbutton();
			download.setImage("/css/sat/down.png");
			download.setTooltip("下载");
			download.addEventListener(Events.ON_CLICK, new DownloadEventListener());
			hbox.appendChild(download);
			
			Toolbarbutton comment = new Toolbarbutton();//文献评论按钮
			comment.setImage("/css/sat/comment.png");
			comment.setTooltip("评论");
			comment.addEventListener(Events.ON_CLICK, new CommentEventListener());
			hbox.appendChild(comment);
			
			c6.appendChild(hbox);
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c4);
			item.appendChild(c5);
			item.appendChild(c6);
		}
	}
	
	/**
	 * 参考文献附件下载事件监听器。触发后可以将当前文件保存到本地计算机
	 * @author Administrator
	 *
	 */
	public class DownloadEventListener implements EventListener {

		public void onEvent(Event event) throws Exception 
		{
			Listitem item = (Listitem)event.getTarget().getParent().getParent().getParent();
			GH_ARCHIVE archive = (GH_ARCHIVE)item.getValue();
			String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
				+ File.separator+archive.getArProId() + File.separator + archive.getArPath();
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
	 * 参考文献评论事件监听器。触发后打开参考文献评论窗口
	 * @author Administrator
	 *
	 */
	public class CommentEventListener implements EventListener
	{
		public void onEvent(Event event) throws Exception 
		{
			Listitem item = (Listitem)event.getTarget().getParent().getParent().getParent();
			GH_ARCHIVE archive = (GH_ARCHIVE)item.getValue();
			ArticalComment w=(ArticalComment)
				Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/member/articalComment.zul",null,null);
			w.setArchive(archive);
			w.initWindow();
			w.doModal();
		}
	}

	/**
	 * 参考文献列表分页组件单击事件监听器
	 * @author Administrator
	 *
	 */
	public class PagingListener implements EventListener {

		public void onEvent(Event event) throws Exception 
		{
			List<GH_ARCHIVE> arList = referenceService.findByTKAPSFAndPage(sourceType, searchFiled, content, 
					startTime, endTime,clcString,issueString,GhXm.JYXM,referencePaging.getActivePage(), referencePaging.getPageSize());
			referenceListbox.setModel(new ListModelList(arList));
		}

	}
}
