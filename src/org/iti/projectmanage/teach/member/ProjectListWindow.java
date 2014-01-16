package org.iti.projectmanage.teach.member;



import java.util.List;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.XmService;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTUser;


public class ProjectListWindow extends Window implements AfterCompose{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	
	WkTUser user;
	XmService xmService;
	JsxmService jsxmService;
    ProjectMemberService projectmemberService;
	Listbox jxproject;
	Div jxplist;
	Window jxprojectlist;
	Paging jxlistPaging;
	public void afterCompose() 
	{
			Components.wireVariables(this, this);
			Components.addForwards(this, this);
			initShow();
	}

	public void onClick$edit()
	{
		jxplist.setVisible(false);
	StageEditWindow w=(StageEditWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/member/stageedit.zul", null, null);
	jxprojectlist.appendChild(w);
		w.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				initShow();
			}
		});
	}

	String  staff="";	
	public void initShow()
	{
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		List list1 = xmService.findCountByKuidAndType(user.getKuId(), GhJsxm.JYXM);
		List list2= xmService.findByKuidAndType(user.getKuId(), GhJsxm.JYXM,jxlistPaging.getActivePage(), jxlistPaging.getPageSize());
		if(list1.size()!=0)
		{
		int size=((Long) list1.get(0)).intValue();
		jxlistPaging.setTotalSize(size);	
		}
		jxproject.setModel(new ListModelList(list2));
		jxlistPaging.addEventListener("onPaging", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				List xmlist= xmService.findByKuidAndType(user.getKuId(), GhJsxm.JYXM,jxlistPaging.getActivePage(), jxlistPaging.getPageSize());
				jxproject.setModel(new ListModelList(xmlist));				
			}
		});
		jxproject.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				
				String c1String;
				String c2String;
				String c5String;
				arg0.setStyle("text-align:left");
				final GhXm xm = (GhXm)arg1;
				//序号
				Listcell c0 = new Listcell(arg0.getIndex()+1+"");
				Listcell c1 = new Listcell();
				//设置字体的颜色、只显示第几个字，当鼠标放到该字段时自动显示全部信息
				c1.setStyle("color:blue");	
				
				if(xm.getKyMc().length()>14) {
					c1String = xm.getKyMc().substring(0, 14)+"...";
					c1.setLabel(c1String);
				}
				else {
					c1.setLabel(xm.getKyMc());
				}

				c1.setTooltiptext(xm.getKyMc());
				
				
				Listcell c2 = new Listcell();								
				if(xm.getKyLy().length()>=11) {
					c2String = xm.getKyLy().substring(0, 10)+"...";
					c2.setLabel(c2String);
				}
				else {
					c2.setLabel(xm.getKyLy());
				}
				c2.setTooltiptext(xm.getKyLy());
				
				Listcell c3 = new Listcell(xm.getKyProman());
				Listcell c4 = new Listcell(); //personal sacrifice
				List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuId());
				if(manager.size()!=0)
				{
					GhJsxm man=(GhJsxm) manager.get(0);
					c4.setLabel(man.getKyGx().toString());
				}
				else
				{
					c4.setLabel("1");
				}
				Listcell c5 = new Listcell();
				List mlist=projectmemberService.findByKyXmId(xm.getKyId());
				if(mlist.size()!=0)
				{
					staff="";
					for(int i=0;i<mlist.size();i++)
					{
						GhJsxm member=(GhJsxm)mlist.get(i);
					
					staff+=member.getKyMemberName()+"、";
					}
					if(staff.length()>=11) {
						c5String = staff.substring(0, 10)+"...";
						c5.setLabel(c5String);
					}
					else {
						c5.setLabel(staff.substring(0, staff.length()-1));
					}
					c5.setTooltiptext(staff.substring(0, staff.length()-1));		
				}
				else
				{
				c5 = new Listcell(xm.getKyProman());
				}
				
				
				Listcell c6 = new Listcell();
				Hbox hbox = new Hbox();
				final Toolbarbutton btView = new Toolbarbutton();//查看阶段  view stage
				btView.setImage("/css/sat/sear_ico.jpg");
				btView.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						jxplist.setVisible(false);
						StageEditWindow w=(StageEditWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/member/stageedit.zul", null, null);
						jxprojectlist.appendChild(w);
						    w.initWindow(xm);//把项目传个项目成员的页面  pass project to the distribute member page
							w.addEventListener(Events.ON_CHANGE, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									jxplist.setVisible(true);
									initShow();
								}
							});
					}
					
				});
				btView.setTooltiptext("查看项目人员分工");
				btView.setParent(hbox);
				c6.appendChild(hbox);	
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				c1.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						jxplist.setVisible(false);
						StageEditWindow w=(StageEditWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/member/stageedit.zul", null, null);
						jxprojectlist.appendChild(w);
							w.initWindow(xm);
							w.addEventListener(Events.ON_CHANGE, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									jxplist.setVisible(true);
									initShow();
								}
							});
					}
				});
			}
			
		});
		
	}

	
}