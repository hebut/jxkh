package org.iti.projectmanage.science.project;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WriteException;


import org.apache.log4j.Logger;
import org.iti.gh.common.util.DateUtil;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.CgService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.XmService;
import org.iti.gh.ui.listbox.YjfxListbox;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.asm.personal.ui.data.teacherinfo.kyqk.SelectXmWindow;
import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.IsZipExists;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;
/**
 * <li>项目名称: Kygl科研管理
 * <li>功能描述: 纵向项目的增删查 
 * <li>版权: Copyright (c) 信息技术研究所
 * @author bobo
 */
public class KycgYhdWindow extends FrameworkWindow {	
	private static final long serialVersionUID = -1373071045258031974L;
	private Logger  log  = Logger.getLogger(KycgYhdWindow.class);	
	GhXm xm = (GhXm) Executions.getCurrent().getArg().get("zxxm");
	Long kuid = (Long) Executions.getCurrent().getArg().get("useKuid");

	private WkTUser user;	
	
	private Decimalbox jingfei ;
	private Textbox ly,number,prostaffs, proman, target,cgmc;
	private Label gongxian,writeuser,internalNum,writeuser1;
	private Listbox  subjetype,scale, progress, cdrw;	
	private YjfxListbox research;
	private Datebox identtime,lxsj,kaishi,jieshu;
//	Button downFileZip;
	private Row rowFile,rowFile2,rowFile3,rowFile4,proStaffsRow;
	private Listbox upList,upList2,upList3,upList4;
	private ListModelList fileModel,fileModel2,fileModel3,fileModel4;
	private Button downFile,deUpload,upFile;//上传申请书
	private Button upFile2,downFile2,deUpload2;//上传合同书
	private Button upFile3,downFile3,deUpload3;//上传中期报告
	private Button upFile4,downFile4,deUpload4;//上传立项报告	
	private Checkbox checkBackup,checkBackup2,checkBackup3,checkBackup4;
	private Toolbarbutton exportMiddReport,resultReport;
	//结项信息
	private Textbox contractNum,finishUnit,conFinishCondition,fruit,level;
	private Datebox setFinishTime,realFinishTime;
	private Decimalbox grants;
	
	private XmService xmService;
	private CgService cgService;
	private GhFileService ghfileService;
	private JsxmService jsxmService;
	private ProjectMemberService projectmemberService;
		 	
	private String  staff="";	
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");		
		//上传申请书
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);		
		upList.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
 				 Listitem item =(Listitem)upList.getSelectedItem();
 				 UploadFJ file =(UploadFJ)item.getValue();
 				 if(file.getGf()!=null&&!file.getGf().equals("")){
			         if(file.getGf().getFbackup()==1){
							checkBackup.setChecked(true);							
					 }else if(file.getGf().getFbackup()==0){
							checkBackup.setChecked(false);							
					 }
 				 }
		    }
		});		 
		//上传合同书
		fileModel2 = new ListModelList(new ArrayList());
		upList2.setModel(fileModel2);
		upList2.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
				 Listitem item =(Listitem)upList2.getSelectedItem();
				 UploadFJ file =(UploadFJ)item.getValue();
				 if(file.getGf()!=null&&!file.getGf().equals("")){
			         if(file.getGf().getFbackup()==1){
							checkBackup2.setChecked(true);							
					 }else if(file.getGf().getFbackup()==0){
							checkBackup2.setChecked(false);							
					 }
				 }
		    }
		});	
		
		//上传中期报告
		fileModel3 = new ListModelList(new ArrayList());
		upList3.setModel(fileModel3);
		upList3.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
 				 Listitem item =(Listitem)upList3.getSelectedItem();
 				 UploadFJ file =(UploadFJ)item.getValue();
 				 if(file.getGf()!=null&&!file.getGf().equals("")){
			         if(file.getGf().getFbackup()==1){
							checkBackup3.setChecked(true);							
					 }else if(file.getGf().getFbackup()==0){
							checkBackup3.setChecked(false);							
					 }
 				 }
		    }
		});
		//上传立项报告
		fileModel4 = new ListModelList(new ArrayList());
		upList4.setModel(fileModel4);
		upList4.addEventListener(Events.ON_SELECT, new EventListener(){
			public void onEvent(Event arg0) throws Exception {
 				 Listitem item =(Listitem)upList4.getSelectedItem();
 				 UploadFJ file =(UploadFJ)item.getValue();
 				 if(file.getGf()!=null&&!file.getGf().equals("")){
			         if(file.getGf().getFbackup()==1){
							checkBackup4.setChecked(true);							
					 }else if(file.getGf().getFbackup()==0){
							checkBackup4.setChecked(false);							
					 }
 				 }
		    }
		});		
	}
	
	/**
	 * 功能：初始化窗口
	 * @Data  2011-5-18
	 * @param null
	 */
	public void initWindow() {
		List subtype = new ArrayList();
		List subscale = new ArrayList();
		List subprogress = new ArrayList();
		List subcdrw = new ArrayList();
		String[] Subtype = { "-请选择-", "自然科学", "社会科学", "其他"};
		for (int i = 0; i < Subtype.length; i++) {
			subtype.add(Subtype[i]);
		}
		String[] Subscale = { "-请选择-", "国际合作项目", "国家级项目", "部（委、省）级项目", "市厅级项目", "委托及开发项目", "学校基金项目", "其他" };
		for (int i = 0; i < Subscale.length; i++) {
			subscale.add(Subscale[i]);
		}
		String[] Subprogress = { "-请选择-", "申请中", "在研", "已完成" };
		for (int i = 0; i < Subprogress.length; i++) {
			subprogress.add(Subprogress[i]);
		}
		String[] Subcdrw = { "-请选择-", "主持", "参加", "独立" };
		for (int i = 0; i < Subcdrw.length; i++) {
			subcdrw.add(Subcdrw[i]);
		}
		// 学科分类
		subjetype.setModel(new ListModelList(subtype));
		// 项目级别
		scale.setModel(new ListModelList(subscale));
		// 项目进展
		progress.setModel(new ListModelList(subprogress));
		// 本人承担任务或作用
		cdrw.setModel(new ListModelList(subcdrw));
		research.initYjfzList(user.getKuId(),null);
	
		if (xm != null) {//用户正在编辑项目信息			
			number.setValue(xm.getKyNumber());// 项目编号
			cgmc.setValue(xm.getKyMc());cgmc.setDisabled(true);// 名称			
			if (xm.getKyLy() != null) {// 来源可以为空
				ly.setValue(xm.getKyLy());
			}
//2011-5-18			
			internalNum.setValue(xm.getInternalNum());	
			if(xm.getKyLxsj()==null||xm.getKyLxsj().equals("")){//立项时间
				lxsj.setValue(null);
    		}else{
    			lxsj.setValue(DateUtil.getDate(xm.getKyLxsj()));
    		}
			if(xm.getKyKssj()==null||xm.getKyKssj().equals("")){// 开始时间
				kaishi.setValue(null);
    		}else{
    			kaishi.setValue(DateUtil.getDate(xm.getKyKssj()));
    		}
			if(xm.getKyJssj()==null||xm.getKyJssj().equals("")){// 结束时间
				jieshu.setValue(null);
    		}else{
    			jieshu.setValue(DateUtil.getDate(xm.getKyJssj()));
    		}
				
			//项目组成员，查询该项目成员表GH_PROMEMBER
			proStaffsRow.setVisible(true);		
			
			List mlist=projectmemberService.findByKyXmId(xm.getKyId());
			proStaffsRow.setVisible(true);
			if(mlist.size()!=0){
				staff="";
				String proMem="";
				for(int i=0;i<mlist.size();i++){
					GhJsxm member=(GhJsxm)mlist.get(i);						
					staff+=member.getKyMemberName()+"、";
				}
				if(staff.length()>=25) {
					proMem = staff.substring(0, 24)+"...";							
					prostaffs.setValue(proMem);
				}
				else {
					prostaffs.setValue(staff.substring(0, staff.length()-1));
				}		
			}
			else{
				prostaffs.setValue(xm.getKyProman());
			}
			
			if (xm.getKyJf() != null) {// 经费可以为空
				jingfei.setValue(BigDecimal.valueOf(xm.getKyJf()));
			}			
			if (xm.getKySubjetype() == null || xm.getKySubjetype() == "") {// 学科分类
				subjetype.setSelectedIndex(0);
			} else {
				subjetype.setSelectedIndex(Integer.valueOf(xm.getKySubjetype().trim()));
			}				
			if (xm.getKyScale() == null || xm.getKyScale() == "") {// 项目级别
				scale.setSelectedIndex(0);
			} else {
				scale.setSelectedIndex(Integer.valueOf(xm.getKyScale().trim()));
			}
			
			if (xm.getKyProgress() == null || xm.getKyProgress() == "") {// 项目进展
				progress.setSelectedIndex(0);
			} else {
				progress.setSelectedIndex(Integer.valueOf(xm.getKyProgress().trim()));
			}
			
			// 项目负责人
			if (xm.getKyProman() != null) {
				proman.setValue(xm.getKyProman());
			} else {
				proman.setValue("");
			}
			// 项目指标
			if (xm.getKyTarget() != null) {
				target.setValue(xm.getKyTarget());
			} else {
				target.setValue("");
			}
			// 鉴定时间
			if(xm.getKyIdenttime()==null||xm.getKyIdenttime().equals("")){
				identtime.setValue(null);
    		}else{
    			identtime.setValue(DateUtil.getDate(xm.getKyIdenttime()));
    		}
			// 鉴定水平
			if (xm.getKyLevel() != null) {
				level.setValue(xm.getKyLevel());
			} else {
				level.setValue("");
			}
			// 本人贡献
			
			List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuId());
			if(manager.size()!=0)
			{
				GhJsxm man=(GhJsxm) manager.get(0);
				gongxian.setValue(man.getKyGx().toString());
			}
			else
			{
				gongxian.setValue("1");
			}
			//教师项目
            GhJsxm jx=(GhJsxm)jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM);
            if(jx!=null){
            	// 本人贡献
//            	gongxian.setValue(jx.getKyGx()!=null?jx.getKyGx()+"":"0");
            	research.initYjfzList(user.getKuId(), jx.getYjId());
    			// 本人承担任务或作用
    			if (jx.getKyCdrw() == null || jx.getKyCdrw() == "") {
    				cdrw.setSelectedIndex(0);
    			} else {
    				cdrw.setSelectedIndex(Integer.valueOf(jx.getKyCdrw().trim()));
    			}
            }else{
//            	gongxian.setValue("0");
            	research.setSelectedIndex(0);
            	cdrw.setSelectedIndex(0);
            }
            
            //结项信息，合同编号
            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
            	contractNum.setValue(xm.getKyContraNum());
			} else {
				contractNum.setValue("");
			}
            //完成单位
            if (xm.getKyFinishUnit()!= null&&!xm.getKyFinishUnit().equals("")) {
            	finishUnit.setValue(xm.getKyFinishUnit());
			} else {
				finishUnit.setValue("");
			}
            //规定完成时间
            if(xm.getKySetFinishTime()==null||xm.getKySetFinishTime().equals("")){
            	setFinishTime.setValue(null);
    		}else{
    			setFinishTime.setValue(DateUtil.getDate(xm.getKySetFinishTime()));
    		}
            //实际完成情况
            if(xm.getKyRealFinishTime()==null||xm.getKyRealFinishTime().equals("")){
            	realFinishTime.setValue(null);
    		}else{
    			realFinishTime.setValue(DateUtil.getDate(xm.getKyRealFinishTime()));
    		}
            //完成情况
            if (xm.getKyContentFinishConditon()!= null&&!xm.getKyContentFinishConditon().equals("")) {
            	conFinishCondition.setValue(xm.getKyContentFinishConditon());
			} else {
				conFinishCondition.setValue("");
			}
            //成果及成效
            if (xm.getKyFruit()!= null&&!xm.getKyFruit().equals("")) {
            	fruit.setValue(xm.getKyFruit());
			} else {
				fruit.setValue("");
			}
            // 资助经费
            if (xm.getKyGrants()!= null) {
            	grants.setValue(BigDecimal.valueOf(xm.getKyGrants()));
			}
        	            
            writeuser.setValue(xm.getUser().getKuName());
            writeuser1.setValue(xm.getUser().getKuName());
			if(xm.getKuId().intValue()!=user.getKuId().intValue()){//非该用户创建的项目部分属性不可以编辑				
				number.setDisabled(true);ly.setDisabled(true);lxsj.setDisabled(true);
				kaishi.setDisabled(true);jieshu.setDisabled(true);
				jingfei.setDisabled(true);prostaffs.setDisabled(true);
				proman.setDisabled(true);
//				register.setDisabled(true);
				target.setDisabled(true);identtime.setDisabled(true);
				level.setDisabled(true);subjetype.setDisabled(true);
				scale.setDisabled(true);
				progress.setDisabled(true);
				
				//结项信息
				contractNum.setDisabled(true);finishUnit.setDisabled(true);
				conFinishCondition.setDisabled(true);fruit.setDisabled(true);
				setFinishTime.setDisabled(true);realFinishTime.setDisabled(true);
				grants.setDisabled(true);
				
				//不可以上传附件				
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
				downFile.setDisabled(true);
				checkBackup.setDisabled(true);
				
				deUpload2.setDisabled(true);
				upFile2.setDisabled(true);
				downFile2.setDisabled(true);
				checkBackup2.setDisabled(true);
				
				deUpload3.setDisabled(true);
				upFile3.setDisabled(true);
				downFile3.setDisabled(true);
				checkBackup3.setDisabled(true);
				
				deUpload4.setDisabled(true);
				upFile4.setDisabled(true);
				downFile4.setDisabled(true);
				checkBackup4.setDisabled(true);
//				downFileZip2.setDisabled(true);
				
				//导出报告
				exportMiddReport.setDisabled(true);
				resultReport.setDisabled(true);
			}	
//			if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_YES){
//				submit.setDisabled(true);
//				upFile.setDisabled(true);
//			}		
		} else {
			cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.KYXM);
				}
			});
			ly.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.KYXM);
				}
			});
			proman.addEventListener(Events.ON_CHANGE, new EventListener(){
				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.KYXM);
				}
			});
			cgmc.setValue("");
			ly.setValue("");
			kaishi.setValue(null);
			jieshu.setValue(null);
			jingfei.setValue(BigDecimal.valueOf(0l));
//			gongxian.setValue("0");
//			xm.setKyProstaffs("");
			proman.setValue("");
//			register.setValue("");
			target.setValue("");
			identtime.setValue(null);
			level.setValue("");
			writeuser.setValue(user.getKuName());
			writeuser1.setValue(user.getKuName());
			
			//结项信息
			contractNum.setValue("");finishUnit.setValue("");
			conFinishCondition.setValue("");fruit.setValue("");
			setFinishTime.setValue(null);realFinishTime.setValue(null);
			grants.setValue(BigDecimal.valueOf(0l));
			
			deUpload.setDisabled(true);
			downFile.setDisabled(true);
			checkBackup.setDisabled(true);
			
			deUpload2.setDisabled(true);
			downFile2.setDisabled(true);
			checkBackup2.setDisabled(true);
			
			deUpload3.setDisabled(true);
			downFile3.setDisabled(true);
			checkBackup3.setDisabled(true);
			
			deUpload4.setDisabled(true);
			downFile4.setDisabled(true);
			checkBackup4.setDisabled(true);
//			downFileZip.setDisabled(true);
			//选择项目名称
//			if(cgmc.getModel().getSize()>0){
//			cgmc.addEventListener(Events.ON_SELECT, new EventListener(){
//
//				public void onEvent(Event arg0) throws Exception {
//					if(cgmc.getSelectedItem()!=null){
//						Object [] mc=(Object [])cgmc.getSelectedItem().getValue();
//						GhXm xm=(GhXm)xmService.get(GhXm.class,(Long)mc[1]);
//						number.setValue(xm.getKyNumber());number.setDisabled(true);
//						ly.setValue(xm.getKyLy());ly.setDisabled(true);
//						lxsj.setValue(xm.getKyLxsj());lxsj.setDisabled(true);
//						kaishi.setValue(xm.getKyKssj());kaishi.setDisabled(true);
//						jieshu.setValue(xm.getKyJssj());jieshu.setDisabled(true);
//						jingfei.setValue(xm.getKyJf()+"");jingfei.setDisabled(true);
//						prostaffs.setValue(xm.getKyProstaffs());prostaffs.setDisabled(true);
//						proman.setValue(xm.getKyProman());proman.setDisabled(true);
////						register.setValue(xm.getKyRegister());register.setDisabled(true);
//						target.setValue(xm.getKyTarget());target.setDisabled(true);
//						identtime.setValue(xm.getKyIdenttime());identtime.setDisabled(true);
//						level.setValue(xm.getKyLevel());level.setDisabled(true);
//						// 学科分类
//						if (xm.getKySubjetype() == null || xm.getKySubjetype() == "") {
//							subjetype.setSelectedIndex(0);
//							subjetype.setDisabled(true);
//						} else {
//							subjetype.setSelectedIndex(Integer.valueOf(xm.getKySubjetype().trim()));
//							subjetype.setDisabled(true);
//						}
//						// 项目类别
//						if (xm.getKyClass() == null || xm.getKyClass() == "") {
//							kyclass.setSelectedIndex(0);
//							kyclass.setDisabled(true);
//						} else {
//							
//							kyclass.setSelectedIndex(Integer.valueOf(xm.getKyClass().trim()));
//							kyclass.setDisabled(true);
//						}
//						// 项目级别
//						if (xm.getKyScale() == null || xm.getKyScale() == "") {
//							scale.setSelectedIndex(0);
//							scale.setDisabled(true);
//						} else {
//							scale.setSelectedIndex(Integer.valueOf(xm.getKyScale().trim()));
//							scale.setDisabled(true);
//						}
//						// 项目进展
//						if (xm.getKyProgress() == null || xm.getKyProgress() == "") {
//							progress.setSelectedIndex(0);
//							progress.setDisabled(true);
//						} else {
//							progress.setSelectedIndex(Integer.valueOf(xm.getKyProgress().trim()));
//							progress.setDisabled(true);
//						}
////						// 是否获奖
////						if (xm.getKyPrize() == null || xm.getKyPrize() == "" || xm.getKyPrize().trim().equalsIgnoreCase("1")) {
////							prize.setSelectedIndex(0);
////						} else if (xm.getKyPrize().trim().equalsIgnoreCase("2")) {// 获奖 1代表“否”2代表“是”
////							prize.setSelectedIndex(1);
////						}
//						if(xm.getKyProstaffs().contains(user.getKuName())&&xm.getKyProstaffs().contains(",")){
//							List namelist=new ArrayList();
//							String str=xm.getKyProstaffs().trim();
//							while (str.contains("、")) {
//									Label lb = new Label(str.substring(0,str.indexOf("、")));
//									namelist.add(lb.getValue());
//									str = str.substring(str.indexOf("、") + 1, str.length());
//								
//							}
//							namelist.add(str);
//							gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
//							gongxian.setDisabled(true);
//						}else{
//							gongxian.setValue("0");
//							gongxian.setDisabled(true);
//						}
//						//附件情况
//						deUpload.setDisabled(true);
//						upFile.setDisabled(true);
//						List fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 1);
//						if (fjList.size() == 0) {// 无附件
//							rowFile.setVisible(false);
//							downFileZip.setDisabled(true);//无附件下载按钮无效
//						} else {// 有附件
//							// 初始化附件
//							downFileZip.setDisabled(false);
//							for (int i = 0; i < fjList.size(); i++) {
//								UploadFJ ufj = new UploadFJ(false);
//								try {
//									ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
//								fileModel.add(ufj);
//							}
//							upList.setModel(fileModel);
//							rowFile.setVisible(true);
//							}
//						
//					}
//				}
//				});
//			}
		}
		// 附件
		if (xm == null) {// 新添加
			rowFile.setVisible(true);
			rowFile2.setVisible(true);
			rowFile3.setVisible(true);
			rowFile4.setVisible(true);
		} else {
			//编辑修改，申请书
			List fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 19);
			if (fjList.size() == 0) {// 无附件
				rowFile.setVisible(true);	
			} else {				// 有附件，初始化附件
				for (int i = fjList.size()-1; i >=0 ; i--) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList.get(i));
						//2011-5-19						
						GhFile f = (GhFile)fjList.get(fjList.size()-1);
						if(f.getFbackup()==1){
							checkBackup.setChecked(true);							
						}else if(f.getFbackup()==0){
							checkBackup.setChecked(false);							
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fileModel.add(ufj);
				}				
				upList.setModel(fileModel);		
				upList.setVisible(true);downFile.setVisible(true);
				deUpload.setVisible(true);rowFile.setVisible(true);
				checkBackup.setVisible(true);				
			}
			
			//合同书
			List fjList2 = ghfileService.findByFxmIdandFType(xm.getKyId(), 20);
			if (fjList2.size() == 0) {// 无附件
				rowFile2.setVisible(true);
			} else {// 有附件，初始化附件
				for (int i = fjList2.size()-1; i >=0 ; i--) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList2.get(i));
						//2011-5-19
						GhFile f2 = (GhFile)fjList2.get(fjList2.size()-1);
						if(f2.getFbackup()==1){
							checkBackup2.setChecked(true);							
						}else if(f2.getFbackup()==0){
							checkBackup2.setChecked(false);							
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fileModel2.add(ufj);
				}
				upList2.setModel(fileModel2);
				upList2.setVisible(true);downFile2.setVisible(true);
				deUpload2.setVisible(true);rowFile2.setVisible(true);
				checkBackup2.setVisible(true);
			}
			
			//中期报告
			List fjList3 = ghfileService.findByFxmIdandFType(xm.getKyId(), 21);
			if (fjList3.size() == 0) {// 无附件
				rowFile3.setVisible(true);
			} else {// 有附件，初始化附件
				for (int i = fjList3.size()-1; i >=0 ; i--) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList3.get(i));
						//2011-5-19						
						GhFile f3 = (GhFile)fjList3.get(fjList3.size()-1);
						if(f3.getFbackup()==1){
							checkBackup3.setChecked(true);							
						}else if(f3.getFbackup()==0){
							checkBackup3.setChecked(false);							
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fileModel3.add(ufj);
				}
				upList3.setModel(fileModel3);
				upList3.setVisible(true);downFile3.setVisible(true);
				deUpload3.setVisible(true);rowFile3.setVisible(true);
				checkBackup3.setVisible(true);
			}
			
			//立项报告
			List fjList4 = ghfileService.findByFxmIdandFType(xm.getKyId(), 22);
			if (fjList4.size() == 0) {// 无附件
				rowFile4.setVisible(true);
			} else {// 有附件，初始化附件
				for (int i = fjList4.size()-1; i >=0 ; i--) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList4.get(i));
						//2011-5-19						
						GhFile f4 = (GhFile)fjList4.get(fjList4.size()-1);
						if(f4.getFbackup()==1){
							checkBackup4.setChecked(true);							
						}else if(f4.getFbackup()==0){
							checkBackup4.setChecked(false);							
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fileModel4.add(ufj);
				}
				upList4.setModel(fileModel4);
				upList4.setVisible(true);downFile4.setVisible(true);
				deUpload4.setVisible(true);rowFile4.setVisible(true);
				checkBackup4.setVisible(true);
			}
			
		}
 }
	/**
	 * 功能：提交保存
	 * 2011-5
	 * @throws InterruptedException 
	 * @throws InterruptedException
	 */
	public void onClick$submit() throws InterruptedException {		
		//项目编号 要么不填，要是填写必须唯一 if(number.getValue()==null||"".equals(number.getValue().trim())){ }else{ if(xm==null){//新建项目 //项目号唯一判重 if(xmService.findByKynumber(number.getValue()).size()>=1){ throw new WrongValueException(number, "项目编号必须唯一，该项目编号存在，请核实后填写！"); } }else{//修改项目 if(xmService.findByKynumber(number.getValue()).size()>=2){ throw new WrongValueException(number, "项目编号必须唯一，该项目编号存在，请核实后填写！"); } } }
		if(number.getValue()==null||"".equals(number.getValue().trim())){
			throw new WrongValueException(number, "您还没有填写项目编号！");
		}
		// 科研名称
		if (cgmc.getValue() == null || "".equals(cgmc.getValue().trim())) {
			throw new WrongValueException(cgmc, "您还没有填写科研名称！");
		}	
		//负责人
		if (proman.getValue() == null || "".equals(proman.getValue().trim())) {
			throw new WrongValueException(proman, "您还没有填写项目负责人！");
		}
		//立项时间
		if(lxsj.getValue()==null||lxsj.getValue().equals("")){
			throw new WrongValueException(lxsj,"您还没有填写项目立项时间！");
		}
		// 开始时间
		if (kaishi.getValue() == null || "".equals(kaishi.getValue())) {
			throw new WrongValueException(kaishi, "您还没有填写开始时间！");
		} 
		// 结束时间
		if (jieshu.getValue() == null || "".equals(jieshu.getValue())) {
			throw new WrongValueException(jieshu, "您还没有填写结束时间！");
		} 
		// 经费
		Float kyJf = 0F;
		if (jingfei.getValue() != null &&!"".equals(jingfei.getValue().toString().trim())) {
			kyJf = Float.parseFloat(jingfei.getValue()+"");
		}
		// 本人贡献
		Integer kyGx = 1;

//		if(contractNum.getValue() == null || "".equals(contractNum.getValue().trim())||
//			finishUnit.getValue() == null || "".equals(finishUnit.getValue().trim())||
//			setFinishTime.getValue()==null||setFinishTime.getValue().equals("")||
//			realFinishTime.getValue()==null||realFinishTime.getValue().equals("")){
//			Messagebox.show("您还没有填写项目结项的相关信息！", "提示", Messagebox.OK, Messagebox.INFORMATION);			  
//			return;
//		}
//		//合同编号		
//		if (contractNum.getValue() == null || "".equals(contractNum.getValue().trim())) {
//			throw new WrongValueException(contractNum, "您还没有填写项目合同编号！");			
//		}
//		//完成单位		
//		if (finishUnit.getValue() == null || "".equals(finishUnit.getValue().trim())) {
//			throw new WrongValueException(finishUnit, "您还没有填写项目完成单位！");
//		}
//		//规定完成时间
//		if(setFinishTime.getValue()==null||setFinishTime.getValue().equals("")){
//			throw new WrongValueException(setFinishTime,"您还没有填写项目规定完成时间！");
//		}
//		//规定完成时间
//		if(realFinishTime.getValue()==null||realFinishTime.getValue().equals("")){
//			throw new WrongValueException(realFinishTime,"您还没有填写项目实际完成时间！");
//		}		
		// false表示是修改，true表示是新建
		boolean index = false,owner=false;
		if (xm == null) {// 说明这是新建的一个项目
			 if(!lxsj.isDisabled()){
				 owner=true;
			    }
			xm = new GhXm();
			index = true;
		}
	
		//2011-5-19 文件上传是否选择		
		Sessions.getCurrent().setAttribute("check",checkBackup); 
		Sessions.getCurrent().setAttribute("check",checkBackup2); 
		Sessions.getCurrent().setAttribute("check",checkBackup3); 
		Sessions.getCurrent().setAttribute("check",checkBackup4); 
		if (index) {
			//新建项目,两种情况：（一）当前用户输入了项目名称，保存到项目表和教师项目表；（二），用户选择的项目名称，只保存教师项目表
			if(owner){//当前用户的项目
//				if(!xmService.CheckOnlyOne(cgmc.getValue().trim(), GhXm.KYXM, ly.getValue().trim(),proman.getValue().trim())){
//					Messagebox.show("您提交的项目信息已存在，不可以重复添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
//					xm=null;
//					return;
//				}
				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
					Messagebox.show("项目组中都不包括您的姓名，不可以添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
					xm=null;
					return;
				}
				xm.setKyNumber(number.getValue().trim());
				xm.setKyMc(cgmc.getValue().trim());
				if(lxsj.getValue()!=null){
					xm.setKyLxsj(DateUtil.getDateString(lxsj.getValue()));
				}
				
				if(kaishi.getValue()!=null){
					xm.setKyKssj(DateUtil.getDateString(kaishi.getValue()));
				}
				if(jieshu.getValue()!=null){
					xm.setKyJssj(DateUtil.getDateString(jieshu.getValue()));
				}
				
				//当添加项目的时候，项目成员表GH_PROMEMBER肯定为为空，先将负责人放入成员中				
				xm.setKyProstaffs(user.getKuName());
				xm.setKyProman(proman.getValue().trim());
//				xm.setKyRegister(register.getValue().trim());
				xm.setKyTarget(target.getValue().trim());
				if(identtime.getValue()!=null){
					xm.setKyIdenttime(DateUtil.getDateString(identtime.getValue()));
				}else{
					xm.setKyIdenttime("");
				}
				xm.setKyLevel(level.getValue().trim());
				xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
				xm.setKyClass("2");				
				xm.setKyScale(String.valueOf(scale.getSelectedIndex()));
				xm.setKyJf(kyJf);
				xm.setKyLy(ly.getValue().trim());
				xm.setKuId(user.getKuId());			
				xm.setKyLx(GhXm.KYXM);
				if (xm.getKyPrize() == "2") {// 获奖，则项目进展已完成
					xm.setKyProgress("3");// 项目进展已完成
				} else {
					xm.setKyProgress(String.valueOf(progress.getSelectedIndex()));
				}
				xm.setAuditState(null);
				xm.setAuditUid(null);
				xm.setReason(null);
				xmService.save(xm);
				//2011-5-18项目内部编号							
				if(Integer.valueOf(xm.getKyClass().trim())==1){
					xm.setInternalNum("H"+number.getValue());
				}else if(Integer.valueOf(xm.getKyClass().trim())==2){
					xm.setInternalNum("Z"+number.getValue());
				}
								
				xm.setKyContraNum(contractNum.getValue());//合同编号
				xm.setKyFinishUnit(finishUnit.getValue());//完成单位				
				if(setFinishTime.getValue()!=null){
					xm.setKySetFinishTime(DateUtil.getDateString(setFinishTime.getValue()));//规定完成时间
				}
				if(realFinishTime.getValue()!=null){
					xm.setKyRealFinishTime(DateUtil.getDateString(realFinishTime.getValue()));//实际完成时间
				}				
				xm.setKyGrants(Float.valueOf(grants.getValue().toString()));//资助经费
				xm.setKyContentFinishConditon(conFinishCondition.getValue());//完成情况
				xm.setKyFruit(fruit.getValue());//成果及成效				
				xmService.update(xm);
				
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());//申请书
				UploadFJ.ListModelListUploadCommand(fileModel2, xm.getKyId());//合同书
				UploadFJ.ListModelListUploadCommand(fileModel3, xm.getKyId());//中期报告
				UploadFJ.ListModelListUploadCommand(fileModel4, xm.getKyId());//立项报告
				
				//保存到教师项目表
				GhJsxm jsxm=new GhJsxm();
				jsxm.setKyId(xm.getKyId());
				jsxm.setJsxmType(GhJsxm.KYXM);
				jsxm.setKuId(user.getKuId());
				jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
				jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
				jsxm.setKyGx(1);
				jsxm.setKyMemberName(user.getKuName());
				jsxm.setKyTaskDesc("");
				jsxmService.save(jsxm);
				if (xm.getKyPrize() == "2") {// 获奖，更新获奖科研教研表
					GhCg cg = new GhCg();
					cg.setKyMc(xm.getKyMc());
					cg.setKyNumber(xm.getKyNumber());
					cg.setKyXmid(xm.getKyId());
					cg.setKuId(user.getKuId());
					cg.setKyLx(GhCg.CG_KY);
					cg.setKyLy(xm.getKyLy());
					// cg.setKyZrs(num);
					cgService.save(cg);
				}
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
				Events.postEvent(Events.ON_CHANGE, this, null);
			}else{
				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
					Messagebox.show("项目组人员不包括您的姓名，请联系项目信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				//保存到教师项目表
				GhJsxm jsxm=new GhJsxm();
				jsxm.setKyId(xm.getKyId());
				jsxm.setJsxmType(GhJsxm.KYXM);
				jsxm.setKuId(user.getKuId());
				jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
				jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
				jsxm.setKyGx(kyGx);
				jsxm.setKyMemberName(user.getKuName());
				jsxm.setKyTaskDesc("");
				jsxmService.save(jsxm);
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
				Events.postEvent(Events.ON_CHANGE, this, null);
			}	
		} else {//修改项目：原来项目是该用户创建的更新项目表和教师项目表；原来项目非该用户创建，只更新教师项目表
			// 本人贡献
			
			List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuId());
			if(manager.size()!=0)
			{
				GhJsxm man=(GhJsxm) manager.get(0);
				kyGx = Integer.parseInt(man.getKyGx().toString());
			}
			
			if(user.getKuId().intValue()==xm.getKuId().intValue()){
				if(!xmService.findByNameAndLxAndLy(xm, cgmc.getValue().trim(),GhXm.KYXM, ly.getValue().trim(),proman.getValue().trim())){
					Messagebox.show("您提交的项目信息已存在，此次修改不予保存！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
					return;
				}
				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
					Messagebox.show("项目组中都不包括您的姓名，不可以添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
					return;
				}
				xm.setKyNumber(number.getValue().trim());
//					xm.setKyMc(cgmc.getValue());//名称不可改
				if(lxsj.getValue()!=null){
					xm.setKyLxsj(DateUtil.getDateString(lxsj.getValue()));
				}
				if(kaishi.getValue()!=null){
					xm.setKyKssj(DateUtil.getDateString(kaishi.getValue()));
				}
				if(jieshu.getValue()!=null){
					xm.setKyJssj(DateUtil.getDateString(jieshu.getValue()));
				}
				//当编辑的时候，查询该项目成员表GH_PROMEMBER
				List mlist=projectmemberService.findByKyXmId(xm.getKyId());
				proStaffsRow.setVisible(true);
				if(mlist.size()!=0){
					staff="";
					String proMem="";
					for(int i=0;i<mlist.size();i++){
						GhJsxm member=(GhJsxm)mlist.get(i);						
						staff+=member.getKyMemberName()+"、";
					}
					if(staff.length()>=25) {
						proMem = staff.substring(0, 24)+"...";							
						xm.setKyProstaffs(proMem);
					}
					else {
						xm.setKyProstaffs(staff.substring(0, staff.length()-1));
					}		
				}
				else{
					xm.setKyProstaffs(user.getKuName());
				}
				xm.setKyProman(proman.getValue().trim());
//					xm.setKyRegister(register.getValue().trim());
				xm.setKyTarget(target.getValue().trim());
				if(identtime.getValue()!=null){
					xm.setKyIdenttime(DateUtil.getDateString(identtime.getValue()));
				}else{
					xm.setKyIdenttime("");
				}
				xm.setKyLevel(level.getValue().trim());
				xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
				xm.setKyClass("2");					
				//2011-5-18				
				if(xm.getKyClass().equals("1")){
					xm.setInternalNum("H"+number.getValue());
				}else if(xm.getKyClass().equals("2")){
					xm.setInternalNum("Z"+number.getValue());
				}					
				xm.setKyScale(String.valueOf(scale.getSelectedIndex()));
				xm.setKyJf(kyJf);
				xm.setKyLy(ly.getValue().trim());
				xm.setKuId(user.getKuId());
				xm.setKyLx(GhXm.KYXM);
				xm.setKyProgress(String.valueOf(progress.getSelectedIndex()));
				xm.setAuditState(null);
				xm.setAuditUid(null);
				xm.setReason(null);
				
				xm.setKyContraNum(contractNum.getValue());//合同编号
				xm.setKyFinishUnit(finishUnit.getValue());//完成单位
				if(setFinishTime.getValue()!=null){
					xm.setKySetFinishTime(DateUtil.getDateString(setFinishTime.getValue()));//规定完成时间
				}
				if(realFinishTime.getValue()!=null){
					xm.setKyRealFinishTime(DateUtil.getDateString(realFinishTime.getValue()));//实际完成时间
				}
				xm.setKyGrants(Float.valueOf(grants.getValue().toString()));//资助经费
				xm.setKyContentFinishConditon(conFinishCondition.getValue());//完成情况
				xm.setKyFruit(fruit.getValue());//成果及成效
				
				xmService.update(xm);
				
				List jslwlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.KYXM);
				for(int i=0;i<jslwlist.size();i++){
					GhJsxm js=(GhJsxm)jslwlist.get(i);
					if(!prostaffs.getValue().trim().contains(js.getUser().getKuName().trim())){
						jsxmService.delete(js);
					}
				}
				
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());//申请书
				UploadFJ.ListModelListUploadCommand(fileModel2, xm.getKyId());//合同书	
				UploadFJ.ListModelListUploadCommand(fileModel3, xm.getKyId());//中期报告
				UploadFJ.ListModelListUploadCommand(fileModel4, xm.getKyId());//立项报告
														
				GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM);
				if(jsxm!=null){
					jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
					jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
					jsxm.setKyGx(kyGx);
					jsxmService.update(jsxm);
					Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
					Events.postEvent(Events.ON_CHANGE, this, null);
				}else{
					jsxm=new GhJsxm();
					jsxm.setKyId(xm.getKyId());
					jsxm.setJsxmType(GhJsxm.KYXM);
					jsxm.setKuId(user.getKuId());
					jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
					jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
					jsxm.setKyGx(kyGx);
					jsxm.setKyMemberName(user.getKuName());
					jsxm.setKyTaskDesc("");
					jsxmService.save(jsxm);
					Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
					Events.postEvent(Events.ON_CHANGE, this, null);
				}
			}else{
				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
					Messagebox.show("项目组人员不包括您的姓名，请联系项目信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				//只更新教师项目表
				GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM);
				if(jsxm!=null){
					jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
					jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
					jsxm.setKyGx(kyGx);
					jsxmService.update(jsxm);
					Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
					Events.postEvent(Events.ON_CHANGE, this, null);
				}else{
					jsxm=new GhJsxm();
					jsxm.setKyId(xm.getKyId());
					jsxm.setJsxmType(GhJsxm.KYXM);
					jsxm.setKuId(user.getKuId());
					jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
					jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
					jsxm.setKyGx(kyGx);
					jsxmService.save(jsxm);
					Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
					Events.postEvent(Events.ON_CHANGE, this, null);
				}
			}
		}
	}
		
	/**
	 * 功能：初始化窗口
	 * @Data  2011-5-18
	 * @param null
	 */
    public void initXmWindow(String xmmc,String xmly,String porman,Short lx){
    	GhXm cxm=xmService.findByNameAndLyAndLxAndFzr(xmmc, lx, xmly, porman);
    	if(cxm!=null){
    		xm=cxm;
    		cgmc.setDisabled(true);
    		number.setValue(cxm.getKyNumber());number.setDisabled(true);
			ly.setValue(cxm.getKyLy());  ly.setDisabled(true);
			lxsj.setValue(DateUtil.getDate(cxm.getKyLxsj()));lxsj.setDisabled(true);
			kaishi.setValue(DateUtil.getDate(cxm.getKyKssj()));kaishi.setDisabled(true);
			jieshu.setValue(DateUtil.getDate(cxm.getKyJssj()));jieshu.setDisabled(true);
			jingfei.setValue(BigDecimal.valueOf(cxm.getKyJf()));jingfei.setDisabled(true);
			
			//当编辑的时候，查询该项目成员表GH_PROMEMBER
			List mlist=projectmemberService.findByKyXmId(cxm.getKyId());
			proStaffsRow.setVisible(true);
			if(mlist.size()!=0){
				staff="";
				String proMem="";
				for(int i=0;i<mlist.size();i++){
					GhJsxm member=(GhJsxm)mlist.get(i);						
					staff+=member.getKyMemberName()+"、";
				}
				if(staff.length()>=25) {
					proMem = staff.substring(0, 24)+"...";							
					prostaffs.setValue(proMem);
				}
				else {
					prostaffs.setValue(staff.substring(0, staff.length()-1));
				}		
			}
			else{
				prostaffs.setValue(cxm.getKyProman());
			}
//			prostaffs.setValue(cxm.getKyProstaffs());
			prostaffs.setDisabled(true);
			proman.setValue(cxm.getKyProman());proman.setDisabled(true);
//			register.setValue(xm.getKyRegister());register.setDisabled(true);
			target.setValue(cxm.getKyTarget());target.setDisabled(true);
			identtime.setValue(DateUtil.getDate(cxm.getKyIdenttime()));identtime.setDisabled(true);
			level.setValue(cxm.getKyLevel());level.setDisabled(true);
			
			//结项信息
			contractNum.setValue(cxm.getKyContraNum());contractNum.setDisabled(true);
			finishUnit.setValue(cxm.getKyFinishUnit());finishUnit.setDisabled(true);
			conFinishCondition.setValue(cxm.getKyContentFinishConditon());conFinishCondition.setDisabled(true);
			fruit.setValue(cxm.getKyFruit());fruit.setDisabled(true);
			setFinishTime.setValue(DateUtil.getDate(cxm.getKySetFinishTime()));setFinishTime.setDisabled(true);
			realFinishTime.setValue(DateUtil.getDate(cxm.getKyRealFinishTime()));realFinishTime.setDisabled(true);
			grants.setValue(BigDecimal.valueOf(cxm.getKyGrants()));grants.setDisabled(true);
			
			// 学科分类
			if (cxm.getKySubjetype() == null || cxm.getKySubjetype() == "") {
				subjetype.setSelectedIndex(0);
				subjetype.setDisabled(true);
			} else {
				subjetype.setSelectedIndex(Integer.valueOf(cxm.getKySubjetype().trim()));
				subjetype.setDisabled(true);
			}
			// 项目级别
			if (cxm.getKyScale() == null || cxm.getKyScale() == "") {
				scale.setSelectedIndex(0);
				scale.setDisabled(true);
			} else {
				scale.setSelectedIndex(Integer.valueOf(cxm.getKyScale().trim()));
				scale.setDisabled(true);
			}
			// 项目进展
			if (cxm.getKyProgress() == null || cxm.getKyProgress() == "") {
				progress.setSelectedIndex(0);
				progress.setDisabled(true);
			} else {
				progress.setSelectedIndex(Integer.valueOf(cxm.getKyProgress().trim()));
				progress.setDisabled(true);
			}
		    writeuser.setValue(cxm.getUser().getKuName());
		    writeuser1.setValue(cxm.getUser().getKuName());
		    
		       //结项信息
            //合同编号
            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
            	contractNum.setValue(xm.getKyContraNum());
			} else {
				contractNum.setValue("");
			}
            //完成单位
            if (xm.getKyFinishUnit()!= null&&!xm.getKyFinishUnit().equals("")) {
            	finishUnit.setValue(xm.getKyFinishUnit());
			} else {
				finishUnit.setValue("");
			}
            //规定完成时间
            if(xm.getKySetFinishTime()==null||xm.getKySetFinishTime().equals("")){
            	setFinishTime.setValue(null);
    		}else{
    			setFinishTime.setValue(DateUtil.getDate(xm.getKySetFinishTime()));
    		}
            //实际完成情况
            if(xm.getKyRealFinishTime()==null||xm.getKyRealFinishTime().equals("")){
            	realFinishTime.setValue(null);
    		}else{
    			realFinishTime.setValue(DateUtil.getDate(xm.getKyRealFinishTime()));
    		}
            //完成情况
            if (xm.getKyContentFinishConditon()!= null&&!xm.getKyContentFinishConditon().equals("")) {
            	conFinishCondition.setValue(xm.getKyContentFinishConditon());
			} else {
				conFinishCondition.setValue("");
			}
            //成果及成效
            if (xm.getKyFruit()!= null&&!xm.getKyFruit().equals("")) {
            	fruit.setValue(xm.getKyFruit());
			} else {
				fruit.setValue("");
			}
            // 资助经费
            if (xm.getKyGrants()!= null) {
            	grants.setValue(BigDecimal.valueOf(xm.getKyGrants()));
			}
            //查询GH_PROMEMBER表，获取贡献           
			List manager= projectmemberService.findByKyIdAndKuId(xm.getKyId(),user.getKuId());
			if(manager.size()!=0)
			{
				GhJsxm man=(GhJsxm) manager.get(0);
				gongxian.setValue(man.getKyGx().toString());
			}
			else
			{
				gongxian.setValue("1");
			}
    	}
    	else{
    		xm=null;
    	}
    }
    
    public void onClick$choice(){
    	Map args=new HashMap();
    	args.put("user", user);
    	args.put("lx", GhXm.KYXM);
    	args.put("type", GhJsxm.KYXM);
    	SelectXmWindow sxw=(SelectXmWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/kyxm/selectxm.zul", null, args);
    	sxw.initShow();
    	sxw.doHighlighted();
    	sxw.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				SelectXmWindow sxw=(SelectXmWindow)arg0.getTarget();
				if(sxw.getXmlist().getSelectedItem()!=null){
					GhXm Xm=(GhXm)sxw.getXmlist().getSelectedItem().getValue();
					if(!Xm.getKyProstaffs().contains(user.getKuName().trim())){
						Messagebox.show("您不是项目组成员，请联系项目填写人申请加入！", "提示：", Messagebox.OK, Messagebox.EXCLAMATION);
				}
				else{
					xm=Xm;
					cgmc.setValue(Xm.getKyMc());cgmc.setDisabled(true);
					number.setValue(Xm.getKyNumber());number.setDisabled(true);
					//ly.setValue(Xm.getKyLy());
					ly.setDisabled(true);
					lxsj.setValue(DateUtil.getDate(Xm.getKyLxsj()));lxsj.setDisabled(true);
					kaishi.setValue(DateUtil.getDate(Xm.getKyKssj()));kaishi.setDisabled(true);
					jieshu.setValue(DateUtil.getDate(Xm.getKyJssj()));jieshu.setDisabled(true);
					jingfei.setValue(BigDecimal.valueOf(Xm.getKyJf()));jingfei.setDisabled(true);
					prostaffs.setValue(Xm.getKyProstaffs());prostaffs.setDisabled(true);
					proman.setValue(Xm.getKyProman());proman.setDisabled(true);
	//				register.setValue(xm.getKyRegister());register.setDisabled(true);
					target.setValue(Xm.getKyTarget());target.setDisabled(true);
					identtime.setValue(DateUtil.getDate(Xm.getKyIdenttime()));identtime.setDisabled(true);
					level.setValue(Xm.getKyLevel());level.setDisabled(true);
					// 学科分类
					if (Xm.getKySubjetype() == null || Xm.getKySubjetype() == "") {
						subjetype.setSelectedIndex(0);
						subjetype.setDisabled(true);
					} else {
						subjetype.setSelectedIndex(Integer.valueOf(Xm.getKySubjetype().trim()));
						subjetype.setDisabled(true);
					}
					// 项目类别
	//				if (Xm.getKyClass() == null || Xm.getKyClass() == "") {
	//					kyclass.setSelectedIndex(0);
	//					kyclass.setDisabled(true);
	//				} else {
	//					
	//					kyclass.setSelectedIndex(Integer.valueOf(Xm.getKyClass().trim()));
	//					kyclass.setDisabled(true);
	//				}
					// 项目级别
					if (Xm.getKyScale() == null || Xm.getKyScale() == "") {
						scale.setSelectedIndex(0);
						scale.setDisabled(true);
					} else {
						scale.setSelectedIndex(Integer.valueOf(Xm.getKyScale().trim()));
						scale.setDisabled(true);
					}
					// 项目进展
					if (Xm.getKyProgress() == null || Xm.getKyProgress() == "") {
						progress.setSelectedIndex(0);
						progress.setDisabled(true);
					} else {
						progress.setSelectedIndex(Integer.valueOf(Xm.getKyProgress().trim()));
						progress.setDisabled(true);
					}
					if(Xm.getKyProstaffs().contains(user.getKuName())){
						List namelist=new ArrayList();
						String str=Xm.getKyProstaffs().trim();
						while (str.contains("、")) {
								Label lb = new Label(str.substring(0,str.indexOf("、")));
								namelist.add(lb.getValue().trim());
								str = str.substring(str.indexOf("、") + 1, str.length());
						}
						namelist.add(str.trim());
						gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
					}else{
						gongxian.setValue("0");
					}
					 writeuser.setValue(Xm.getUser().getKuName());
					 writeuser1.setValue(Xm.getUser().getKuName());
					 
					   //结项信息
			            //合同编号
			            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
			            	contractNum.setValue(xm.getKyContraNum());
						} else {
							contractNum.setValue("");
						}
			            //完成单位
			            if (xm.getKyFinishUnit()!= null&&!xm.getKyFinishUnit().equals("")) {
			            	finishUnit.setValue(xm.getKyFinishUnit());
						} else {
							finishUnit.setValue("");
						}
			            //规定完成时间
			            if(xm.getKySetFinishTime()==null||xm.getKySetFinishTime().equals("")){
			            	setFinishTime.setValue(null);
			    		}else{
			    			setFinishTime.setValue(DateUtil.getDate(xm.getKySetFinishTime()));
			    		}
			            //实际完成情况
			            if(xm.getKyRealFinishTime()==null||xm.getKyRealFinishTime().equals("")){
			            	realFinishTime.setValue(null);
			    		}else{
			    			realFinishTime.setValue(DateUtil.getDate(xm.getKyRealFinishTime()));
			    		}
			            //完成情况
			            if (xm.getKyContentFinishConditon()!= null&&!xm.getKyContentFinishConditon().equals("")) {
			            	conFinishCondition.setValue(xm.getKyContentFinishConditon());
						} else {
							conFinishCondition.setValue("");
						}
			            //成果及成效
			            if (xm.getKyFruit()!= null&&!xm.getKyFruit().equals("")) {
			            	fruit.setValue(xm.getKyFruit());
						} else {
							fruit.setValue("");
						}
			            // 资助经费
			            if (xm.getKyGrants()!= null) {
			            	grants.setValue(BigDecimal.valueOf(xm.getKyGrants()));
						}
					sxw.detach();				
				}
				}
			}
    	});
    }
    /**
     * 上传申请书
     * @date 2011-5-18
     * @throws InterruptedException
     */
	public void onClick$upFile() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 19, 10, "附件不能超过10MB","附件------文件大小不能超过10MB");
		if (media == null) {
			return;
		}
		fileModel.add(ufj);
		rowFile.setVisible(true);upList.setVisible(true);
		deUpload.setVisible(true);checkBackup.setVisible(true);
	}
	/**
     * 上传合同书
     * @date 2011-5-18
     * @throws InterruptedException
     */
	public void onClick$upFile2() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 20, 10, "附件不能超过10MB","附件------文件大小不能超过10MB");
		if (media == null) {
			return;
		}
		rowFile2.setVisible(true);upList2.setVisible(true);
		deUpload2.setVisible(true);fileModel2.add(ufj);
		checkBackup2.setVisible(true);
	}
	/**
     * 上传中期报告
     * @date 2011-5-18
     * @throws InterruptedException
     */
	public void onClick$upFile3() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 21, 10, "附件不能超过10MB","附件------文件大小不能超过10MB");
		if (media == null) {
			return;
		}
		rowFile3.setVisible(true);upList3.setVisible(true);
		deUpload3.setVisible(true);fileModel3.add(ufj);
		checkBackup3.setVisible(true);
	}
	/**
     * 上传立项报告
     * @date 2011-5-18
     * @throws InterruptedException
     */
	public void onClick$upFile4() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 22, 10, "附件不能超过10MB","附件------文件大小不能超过10MB");
		if (media == null) {
			return;
		}
		rowFile4.setVisible(true);upList4.setVisible(true);
		deUpload4.setVisible(true);fileModel4.add(ufj);
		checkBackup4.setVisible(true);
	}
	/**
	 * <li>删除上传的文件，重新选择
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload() {
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		if (fileModel.getSize() == 1) {
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
			rowFile.setVisible(true);
		} else if (fileModel.getSize() > 1) {
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
			rowFile.setVisible(true);upList.setVisible(true);
			deUpload.setVisible(true);
			checkBackup.setVisible(true);
		}
	}
	
	/**
	 * <li>删除上传的文件，重新选择
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload2() {
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		Listitem it = upList2.getSelectedItem();
		if (it == null) {
			if (fileModel2.getSize() > 0) {
				it = upList2.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		if (fileModel2.getSize() == 1) {
			temp.DeleteFJ();
			fileModel2.remove(it.getValue());
			rowFile2.setVisible(true);
		} else if (fileModel2.getSize() > 1) {
			temp.DeleteFJ();
			fileModel2.remove(it.getValue());
			rowFile2.setVisible(true);upList2.setVisible(true);
			deUpload2.setVisible(true);
			checkBackup2.setVisible(true);
		}
	}

	/**
	 * <li>删除上传的文件，重新选择
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload3() {
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		Listitem it = upList3.getSelectedItem();
		if (it == null) {
			if (fileModel3.getSize() > 0) {
				it = upList3.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		if (fileModel3.getSize() == 1) {
			temp.DeleteFJ();
			fileModel3.remove(it.getValue());
			rowFile3.setVisible(true);
		} else if (fileModel3.getSize() > 1) {
			temp.DeleteFJ();
			fileModel3.remove(it.getValue());
			rowFile3.setVisible(true);upList3.setVisible(true);
			deUpload3.setVisible(true);
			checkBackup3.setVisible(true);
		}
	}
	
	/**
	 * 删除上传的立项报告
	 * @date 2011-5-18
	 * 
	 */
	public void onClick$deUpload4() {
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		Listitem it = upList4.getSelectedItem();
		if (it == null) {
			if (fileModel4.getSize() > 0) {
				it = upList4.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		if (fileModel4.getSize() == 1) {
			temp.DeleteFJ();
			fileModel4.remove(it.getValue());
			rowFile4.setVisible(true);
		} else if (fileModel4.getSize() > 1) {
			temp.DeleteFJ();
			fileModel4.remove(it.getValue());
			rowFile4.setVisible(true);upList4.setVisible(true);
			deUpload4.setVisible(true);
			checkBackup4.setVisible(true);
		}
	}
	public void onClick$reset() {
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		List list = fileModel.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel.clear();
		rowFile.setVisible(true);
	}
	
	public void onClick$reset2() {
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		List list = fileModel2.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel2.clear();
		rowFile2.setVisible(true);
	}
	
	public void onClick$reset3() {
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		List list = fileModel3.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel3.clear();
		rowFile3.setVisible(true);
	}
	
	public void onClick$reset4(){
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath("/upload/xkjs/").trim()+ "\\" + "_" + xm.getKyId() + "_" + ".zip");
		List list = fileModel4.getInnerList();
		for(int i=0;i<list.size();i++){
			((UploadFJ)list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel4.clear();
		rowFile4.setVisible(true);
	}

	//打包下载附件
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.xm.getKyId(), fileModel);
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
	
	//打包下载附件
	public void onClick$downFileZip2(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.xm.getKyId(), fileModel2);
	}	
	//单个文件下载
	public void onClick$downFile2(){
		Listitem it = upList2.getSelectedItem();
		if (it == null) {
			if (fileModel2.getSize() > 0) {
				it = upList2.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		DownloadFJ.DownloadCommand(temp);
	}
	
	//打包下载附件
	public void onClick$downFileZip3(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.xm.getKyId(), fileModel3);
	}	
	//单个文件下载
	public void onClick$downFile3(){
		Listitem it = upList3.getSelectedItem();
		if (it == null) {
			if (fileModel3.getSize() > 0) {
				it = upList3.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		DownloadFJ.DownloadCommand(temp);
	}
	
	//打包下载附件
	public void onClick$downFileZip4(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.xm.getKyId(), fileModel4);
	}	
	//单个文件下载
	public void onClick$downFile4(){
		Listitem it = upList4.getSelectedItem();
		if (it == null) {
			if (fileModel4.getSize() > 0) {
				it = upList4.getItemAtIndex(0);
			}
		}
		UploadFJ temp = (UploadFJ) it.getValue();
		DownloadFJ.DownloadCommand(temp);
	}

	/**
	 * 生成中期报告
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws InterruptedException 
	 */
	public void onClick$exportMiddReport() throws WriteException, IOException, InterruptedException{
		Map arg=new HashMap();
		arg.put("zProgram", this.xm);
		ZMiddReportWindow zmWin = (ZMiddReportWindow) Executions.createComponents
								("/admin/personal/data/teacherinfo/kyqk/kyxm/ZmiddReport.zul", null, arg);
		zmWin.doHighlighted();
	}

	/**
	 * 生成结项报告
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws InterruptedException 
	 */
	public void onClick$resultReport() throws WriteException, IOException, InterruptedException{
		Map arg=new HashMap();
		arg.put("zProgram", this.xm);
		ZResultReportWindow zrWin = (ZResultReportWindow) Executions.createComponents
								("/admin/personal/data/teacherinfo/kyqk/kyxm/ZresultReport.zul", null, arg);
		zrWin.doHighlighted();
	}
	
	/**
	 * 打开项目来源管理窗口
	 */
	public void onClick$sourceSelect()
	{
		Map map=new HashMap();
		map.put("sourceTextbox", this.ly);
		ProjectSourceWindow psWin = (ProjectSourceWindow)Executions.createComponents
								("/admin/personal/data/teacherinfo/kyqk/kyxm/projectSource.zul", null, map);
		psWin.initWindow();
		try {
			psWin.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void initShow() {
	}
	
	public void onClick$close() {
		this.detach();
	}
//	public GhXm getXm() {
//		return xm;
//	}
//	public void setXm(GhXm xm) {
//		this.xm = xm;
//	}
//	public Long getKuid() {
//		return kuid;
//	}
//	public void setKuid(Long kuid) {
//		this.kuid = kuid;
//	}
}
