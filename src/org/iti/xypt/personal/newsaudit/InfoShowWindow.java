package org.iti.xypt.personal.newsaudit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.xypt.entity.XyMReceiver;
import org.iti.xypt.entity.XyMReceiverId;
import org.iti.xypt.entity.XyMessage;
import org.iti.xypt.personal.infoExtract.entity.WkTDistribute;
import org.iti.xypt.personal.infoExtract.entity.WkTInfo;
import org.iti.xypt.personal.infoExtract.service.NewsService;
import org.iti.xypt.personal.notice.LongTime;
import org.iti.xypt.personal.notice.NoticeGroupsModel;
import org.iti.xypt.service.MessageService;
import org.iti.xypt.service.StudentService;
import org.iti.xypt.service.TeacherService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.GroupsModel;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class InfoShowWindow extends Window implements AfterCompose {
	// 已收到消息列表框
	private Listbox infoshowlistbox;
	// 删除消息按钮组件
	NewsService newsService;
	TeacherService teacherService;
	StudentService studentService;
	ListModelList infoListModel;
	WkTUser wkTUser;

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		wkTUser = (WkTUser) Sessions.getCurrent().getAttribute("user");
		infoshowlistbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception 
			{
				final WkTDistribute dis=(WkTDistribute) data;
				WkTInfo info=newsService.getWkTInfo(dis.getKiId());
				 item.setValue(info);
				 Listcell c0=new Listcell(item.getIndex()+1+"");
				 item.appendChild(c0);
				 final Listcell c1=new Listcell();
				 if(dis.getKbMail().toString().trim().equals("0"))
				 {
					
					 c1.setImage("/images/ims.news.gif");		
				     item.appendChild(c1);
				 }
				 else
				 {
					 c1.setImage("/images/ims.readed.gif");
				     item.appendChild(c1);
				 }
				 Listcell c2=new Listcell();
				 if(info.getKiTitle().trim().length()>30){
						String str1 = "";                                                      //消息内容长度小于20字符，全部显示，否则截取前20字符显示
						str1=info.getKiTitle().trim().substring(0,30);
							c2=new Listcell(str1+"......");
					}else{
						 c2=new Listcell(info.getKiTitle());					
					}
				  c2.addEventListener(Events.ON_CLICK, new EventListener(){
					  public void onEvent(Event event) throws Exception 
						{
						    dis.setKbMail("1");
						    newsService.update(dis);
						    c1.setImage("/images/ims.readed.gif");
							Listitem c=(Listitem)event.getTarget().getParent();
						     final WkTInfo i=(WkTInfo)c.getValue();
						 	Executions.getCurrent().setAttribute("kiTitle", i);
						     NewsShowDetailWindow w=(NewsShowDetailWindow)Executions.createComponents("/admin/personal/message/send/showdetail.zul",null, null);	
						     w.doHighlighted();
						     w.initWindow(i);
						}
				  });
				 item.appendChild(c2);
				 Listcell c3=new Listcell(info.getKiSource().trim());
				 item.appendChild(c3);
				 Listcell c4=new Listcell(dis.getKbDtime().substring(0,10));
				 item.appendChild(c4);
			}
		});
		reloadGrid();
	}




	/**
	 * <li>功能描述：和数据加载器 void
	 * 
	 * @author bobo
	 * @2010-3-30
	 */
	public void reloadGrid() {

		String uid=wkTUser.getKuLid();
		List tList=teacherService.FindTeacherByThid(uid);
		List sList=studentService.findSnameByStid(uid);
		Integer pid=0;
		if(tList.size()>0){
			pid=0;
		}else if(sList.size()>0){
			pid=1;
		}
		List newsList=newsService.getPubInfo(pid);
		infoListModel=new ListModelList();
		infoListModel.addAll(newsList);
		infoshowlistbox.setModel(infoListModel);
	}
}
