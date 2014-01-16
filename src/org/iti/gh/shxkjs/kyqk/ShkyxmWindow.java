package org.iti.gh.shxkjs.kyqk;

import java.util.ArrayList;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.XmService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;


public class ShkyxmWindow extends BaseWindow {

	Window kyxmWin;
	Listbox kyxm;
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short type=(Short)Executions.getCurrent().getArg().get("type");
	XmService xmService;
	Grid auditxm;
	Label lxsj,number,cgmc,kaishi,jieshu,ly,prostaffs,proman,target,identtime,level;
	Listbox subjetype,kyclass,scale,progress;
	GhXm ghXm;
	Textbox reason;
	Radiogroup audit;
	WkTUser user;
	//暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel=new ListModelList();
	GhFileService ghfileService;
	Button downFileZip;
	@Override
	public void initShow() {
	
		kyxm.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhXm xm=(GhXm)arg1;
				arg0.setValue(xm);
				Listcell c0=new Listcell(arg0.getIndex()+1+"");
				Listcell c1=new Listcell();
			 	String str0 = null;
				if (xm.getKyMc() == null) {
					str0 = "";
				} else {
					str0 =xm.getKyMc();
					int len = str0.trim().length();
					if (len > 21) {
						str0 = str0.substring(0, 20) + "...";
					} else {
						str0 =xm.getKyMc();
					}
				}
				c1.setLabel(str0);
				Listcell c2=new Listcell( );
				String str1 = null;
				if (xm.getKyLy() == null) {
					str1 = "";
				} else {
					str1 =xm.getKyLy();
					int len = str1.trim().length();
					if (len > 14) {
						str1 = str1.substring(0, 13) + "...";
					} else {
						str1=xm.getKyLy();
					}
				}
				c2.setLabel(str1);
				Listcell c3=new Listcell(xm.getKyProman());
//				Listcell c33=new Listcell(xm.getKyKssj());
//				Listcell c4=new Listcell(xm.getKyJssj());
				Listcell c5=new Listcell(xm.getUser().getKuName());
				Listcell c6=new Listcell();
				InnerButton ib=new InnerButton("审核");
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						ghXm=xm;
						kyxm.setVisible(false);
						auditxm.setVisible(true);
						initAuditxm(xm);
					}
					
				});
				c6.appendChild(ib);
				arg0.appendChild(c0);arg0.appendChild(c1);arg0.appendChild(c2);
				arg0.appendChild(c3);
//				arg0.appendChild(c33);arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				
			}
			
		});
	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		auditxm.setVisible(false);
		reason.setValue("");
		audit.setSelectedIndex(0);
		if(kdid==null){
			kdid=getXyUserRole().getKdId();
		}
		List noauditlist=xmService.findByKdidAndLxAndTypeAndState(kdid,type,null);
		kyxm.setVisible(true);
		if(noauditlist!=null&&noauditlist.size()>0){
			kyxm.setModel(new ListModelList(noauditlist));
		}else{
			kyxm.setModel(new ListModelList());
		}
		
		initShow();
	}
    public void initAuditxm(GhXm xm){
    	fileModel.clear();
    	List subtype = new ArrayList();
		List subclass = new ArrayList();
		List subscale = new ArrayList();
		List subprogress = new ArrayList();
		String[] Subtype = { "-请选择-", "自然科学", "社会科学" ,"其他"};
		for (int i = 0; i < Subtype.length; i++) {
			subtype.add(Subtype[i]);
		}
		String[] Subclass = { "-请选择-", "横向", "纵向" };
		for (int i = 0; i < Subclass.length; i++) {
			subclass.add(Subclass[i]);
		}
		String[] Subscale = { "-请选择-", "国际合作项目", "国家级项目", "部（委、省）级项目", "市厅级项目", "委托及开发项目", "学校基金项目","其他" };
		for (int i = 0; i < Subscale.length; i++) {
			subscale.add(Subscale[i]);
		}
		String[] Subprogress = { "-请选择-", "申请中", "在研", "已完成" };
		for (int i = 0; i < Subprogress.length; i++) {
			subprogress.add(Subprogress[i]);
		}
		// 学科分类
		subjetype.setModel(new ListModelList(subtype));
		// 项目类别
		kyclass.setModel(new ListModelList(subclass));
		// 项目级别
		scale.setModel(new ListModelList(subscale));
		// 项目进展
		progress.setModel(new ListModelList(subprogress));
		lxsj.setValue(xm.getKyLxsj());
    	number.setValue(xm.getKyNumber());cgmc.setValue(xm.getKyMc());
    	kaishi.setValue(xm.getKyKssj());jieshu.setValue(xm.getKyJssj());
    	ly.setValue(xm.getKyLy());
//    	jingfei.setValue(xm.getKyJf()+"");
    	prostaffs.setValue(xm.getKyProstaffs());
    	proman.setValue(xm.getKyProman());
//    	register.setValue(xm.getKyRegister());
    	target.setValue(xm.getKyTarget());identtime.setValue(xm.getKyIdenttime());
    	level.setValue(xm.getKyLevel());
    	// 学科分类
		if (xm.getKySubjetype() == null || xm.getKySubjetype() == "") {
			subjetype.setSelectedIndex(0);
		} else {
			subjetype.setSelectedIndex(Integer.valueOf(xm.getKySubjetype().trim()));
		}
		// 项目类别
		if (xm.getKyClass() == null || xm.getKyClass() == "") {
			kyclass.setSelectedIndex(0);
		} else {
			kyclass.setSelectedIndex(Integer.valueOf(xm.getKyClass().trim()));
		}
		// 项目级别
		if (xm.getKyScale() == null || xm.getKyScale() == "") {
			scale.setSelectedIndex(0);
		} else {
			scale.setSelectedIndex(Integer.valueOf(xm.getKyScale().trim()));
		}
		// 项目进展
		if (xm.getKyProgress() == null || xm.getKyProgress() == "") {
			progress.setSelectedIndex(0);
		} else {
			progress.setSelectedIndex(Integer.valueOf(xm.getKyProgress().trim()));
		}
		subjetype.setDisabled(true);kyclass.setDisabled(true);
		scale.setDisabled(true); progress.setDisabled(true);
		 List fjList=new ArrayList();
		   if(type==GhXm.KYXM){
			   fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 1);
		   }else if(type==GhXm.JYXM){
			   fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 10);
		   }
		   if (fjList==null||fjList!=null&&fjList.size() == 0)  {// 无附件
				rowFile.setVisible(false);
				downFileZip.setDisabled(true);
			} else {// 有附件
				downFileZip.setDisabled(false);
				// 初始化附件
				if(fjList!=null){
				for (int i = 0; i < fjList.size(); i++) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fileModel.add(ufj);
				}
				}
				upList.setModel(fileModel);
				rowFile.setVisible(true);
			}
//		 
		
    }
  //打包下载附件
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), ghXm.getKyId(), fileModel);
	}
	//单个文件下载
	public void onClick$downFile(){
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		DownloadFJ.DownloadCommand(temp);
	}
    public void onClick$submit() {
    	ghXm.setAuditState(Short.parseShort(audit.getSelectedIndex()+""));
    	ghXm.setReason(reason.getValue());
    	ghXm.setAuditUid(user.getKuId());
    	xmService.update(ghXm);
    	try {
			Messagebox.show("审核成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		initWindow();
    }
    
    public void onClick$close(){
    	initWindow();
    }
    public void onClick$back(){
    	this.detach();
    	Events.postEvent(Events.ON_CHANGE, this, null);
    }
}
