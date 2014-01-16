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
	 * ��ʼ����ǰ���ڣ�����ʼ���ο������б�
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
	 * �򿪲ο�������Ӵ��ڣ�������ǰ���������Ŀʵ�崫�ݹ�ȥ��ͬʱ����������Ӵ��ڵĳ�ʼ������
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
	 * ������Ŀ��Ա����
	 */
	public void onClick$reback()
	{
		Events.postEvent(Events.ON_CHANGE, this, null);
		jxArticallist.detach();
	}
	
	/**
	 * �ο������б���Ⱦ��
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
			
			Toolbarbutton arDownload = new Toolbarbutton();//�������۰�ť
			arDownload.setImage("/css/sat/down.png");
			arDownload.setTooltip("����");
			arDownload.addEventListener(Events.ON_CLICK, new DownloadEventListener());
			hbox.appendChild(arDownload);
			
			Toolbarbutton arView = new Toolbarbutton();//�������۰�ť
			arView.setImage("/css/sat/comment.png");
			arView.setTooltip("����");
			arView.addEventListener(Events.ON_CLICK, new CommentEventListener());
			hbox.appendChild(arView);			
			
			Toolbarbutton arEdit = new Toolbarbutton();//�༭���װ�ť
			arEdit.setImage("/css/sat/edit.gif");
			arView.setTooltip("�༭");
			arEdit.addEventListener(Events.ON_CLICK, new EditEventListener());
			hbox.appendChild(arEdit);
			
			//��ǰ�û�Ϊ��Ŀ�����ˡ������Ǹòο����׵��ϴ���ʱ���ž��и�����¼��"�༭"��"ɾ��"Ȩ��
			if(user.getKuId().equals(archive.getArUpUserId())||user.getKuId().equals(xm.getKuId()))
			{
				Toolbarbutton arDel = new Toolbarbutton();//ɾ�����װ�ť
				arDel.setImage("/css/sat/actDel.gif");
				arView.setTooltip("ɾ��");			
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
	 * �ο����׸��������¼�������
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
					Messagebox.show("�ο������Ѿ���ɾ����", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
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
	 * �ο�����ɾ���¼�������������������ɾ�������׵����ۼ�¼��Ȼ��ɾ�������׵ĸ��������ɾ�������׼�¼
	 * @author Administrator
	 *
	 */
	public class DelEventListener implements EventListener 
	{
		public void onEvent(Event event) throws Exception 
		{
			if(Messagebox.NO==Messagebox.show("���Ƿ�ȷ��ɾ���òο����ף�", "ȷ��", Messagebox.YES|Messagebox.NO, Messagebox.QUESTION))
			{
				return;
			}
			Listitem item = (Listitem)event.getTarget().getParent().getParent().getParent();
			GH_ARCHIVE archive = (GH_ARCHIVE)item.getValue();
			boolean isSucceed = false;
			try
			{
				//����ɾ���òο����׼�¼������
				isSucceed = arCommentService.deleteCommentsByArchive(archive.getArId());
				if(!isSucceed)
				{
					Messagebox.show("��������ɾ��ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				//Ȼ��ɾ�����׸���
				String archivePath =Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/archive")
					+ File.separator+xm.getKyId() + File.separator + archive.getArPath();
				File archiveFile = new File(archivePath);
				if(archiveFile.exists())
				{
					isSucceed = archiveFile.delete();
				}
				if(!isSucceed)
				{
					Messagebox.show("�ο����׸���ɾ��ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				//���ɾ���òο����׼�¼
				archiveService.delete(archive);
				jxArticallist.initWindow();
			}catch(Exception e){
				Messagebox.show("�ο�����ɾ��ʧ�ܣ�������", "��ʾ��", Messagebox.OK, Messagebox.INFORMATION);
				e.printStackTrace();
			}
		}
	}

	/**
	 * �ο����ױ༭�¼�������������������ױ༭����
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
	 * �ο����������¼�����������������������۴���
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
	 * �ο������б��ҳ�¼�������
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