package org.iti.gh.shxkjs.jyqk;


import java.util.List;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhSk;
import org.iti.gh.service.SkService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class JxjlWindow extends BaseWindow {

	Listbox jxjllistbox;
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	SkService skService;
	UserService userService;
	WkTUser user;
	@Override
	
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		if(kdid==null){
			kdid=getXyUserRole().getKdId();
		}	
		List jyjllist=skService.findByKdIdAudit(kdid);
		if(jyjllist!=null&&jyjllist.size()>0){
			jxjllistbox.setModel(new ListModelList(jyjllist));
		}else{
			jxjllistbox.setModel(new ListModelList());
		}
	
	}


	@Override
	public void initShow() {
		
		jxjllistbox.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhSk jxjl= (GhSk) arg1;
          WkTUser user=userService.getUserBykuid(jxjl.getUser().getKuId());
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				Listcell c2 = new Listcell(jxjl.getSkMc());
				Listcell c3 = new Listcell(jxjl.getSkGrade());
				Listcell c4 = new Listcell(jxjl.getSkZy());
				Listcell c5 = new Listcell(jxjl.getSkYear());
				Listcell c6 = new Listcell(jxjl.getSkXs());
				Listcell c7 = new Listcell(user.getKuName());
				Listcell c8 = new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("审核");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("确定要通过审核吗？", "确认", 
								Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK)
						{  
							jxjl.setAuditState(Short.valueOf("1"));
							skService.update(jxjl);
							initWindow();
						}
					}
				});
				c8.appendChild(gn);
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6);
				arg0.appendChild(c7);arg0.appendChild(c8);

			}
		});	
	}
}
