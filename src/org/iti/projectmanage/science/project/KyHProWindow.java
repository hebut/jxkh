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
import org.iti.xypt.ui.base.FrameworkWindow;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
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
 * <li>功能描述: 横向项目的增删查 
 * <li>版权: Copyright (c) 信息技术研究所
 */
public class KyHProWindow extends FrameworkWindow {	
	private static final long serialVersionUID = 1477614318452721480L;
	private Logger  log  = Logger.getLogger(KyHProWindow.class);
	GhXm xm = (GhXm) Executions.getCurrent().getArg().get("hxxm");
	Long kuid = (Long) Executions.getCurrent().getArg().get("useKuid");
	private WkTUser user;	
	
	private XmService xmService;
	private CgService cgService;
	private GhFileService ghfileService;
	private JsxmService jsxmService;
	
	private Decimalbox jingfei ;
	private Textbox ly,prostaffs,number,proman, target,cgmc;
	private Label gongxian,writeuser,internalNum;
	private Listbox  subjetype, scale, progress, cdrw,contraTypeListbox;	
	private YjfxListbox research;
	private Datebox identtime;
	private Row rowFile;
	private Listbox upList;
	private ListModelList fileModel;
	private Button downFile,deUpload,upFile;//上传合同书
	private Checkbox checkBackup;
	private Toolbarbutton exportMiddReport,resultReport;
	private Textbox contractNum,finishUnit,conFinishCondition,fruit,level,commitCom,commitComplace,acceptCom,acceptComPlace;
	private Datebox setFinishTime,realFinishTime;	

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");		
		//上传合同书
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
	}
	
	/**
	* <li>功能描述：初始化窗口。
	 * @param null
	 * @return null
	 */
	public void initWindow() {
		List subtype = new ArrayList();
		List subclass = new ArrayList();
		List subscale = new ArrayList();
		List subprogress = new ArrayList();
		List subcdrw = new ArrayList();
		List contraType = new ArrayList();
		String[] Subtype = { "-请选择-", "自然科学", "社会科学", "其他"};
		for (int i = 0; i < Subtype.length; i++) {
			subtype.add(Subtype[i]);
		}
		String[] Subclass = { "-请选择-", "横向", "纵向" };
		for (int i = 0; i < Subclass.length; i++) {
			subclass.add(Subclass[i]);
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
		String[] ConType = {"-请选择-","技术开发","技术转让","技术服务","技术咨询"};
		for(int i=0;i<ConType.length;i++){
			contraType.add(ConType[i]);
		}
		//合同类型
		contraTypeListbox.setModel(new ListModelList(contraType));
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
			// 项目编号
			number.setValue(xm.getKyNumber());
			// 项目名称
			cgmc.setValue(xm.getKyMc());cgmc.setDisabled(true);	
			// 来源--可以为空
			if (xm.getKyLy() != null) {
				ly.setValue(xm.getKyLy());
			}
			//项目内部编号
			internalNum.setValue(xm.getInternalNum());	
			// 经费--可以为空		
			if (xm.getKyJf() != null) {
				jingfei.setValue(BigDecimal.valueOf(xm.getKyJf()));
			}	
			// 学科分类
			if (xm.getKySubjetype() == null || xm.getKySubjetype() == "") {
				subjetype.setSelectedIndex(0);
			} else {
				subjetype.setSelectedIndex(Integer.valueOf(xm.getKySubjetype().trim()));
			}	
			// 合同类型
			if (xm.getContractType() == null || xm.getContractType() == "") {
				contraTypeListbox.setSelectedIndex(0);
			} else {
				contraTypeListbox.setSelectedIndex(Integer.valueOf(xm.getContractType().trim()));
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
			// 项目组人员
			if (xm.getKyProstaffs() != null) {
				prostaffs.setValue(xm.getKyProstaffs());
			} else {
				prostaffs.setValue("");
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
			//教师项目
            GhJsxm jx=(GhJsxm)jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.KYXM);
            if(jx!=null){
            	// 本人贡献
            	gongxian.setValue(jx.getKyGx()!=null?jx.getKyGx()+"":"0");
            	research.initYjfzList(user.getKuId(), jx.getYjId());
    			// 本人承担任务或作用
    			if (jx.getKyCdrw() == null || jx.getKyCdrw() == "") {
    				cdrw.setSelectedIndex(0);
    			} else {
    				cdrw.setSelectedIndex(Integer.valueOf(jx.getKyCdrw().trim()));
    			}
            }else{
            	gongxian.setValue("0");
            	research.setSelectedIndex(0);
            	cdrw.setSelectedIndex(0);
            }            
            //合同编号
            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
            	contractNum.setValue(xm.getKyContraNum());
			} else {
				contractNum.setValue("");
			}
            //委托单位
            if (xm.getCommitCom()!= null&&!xm.getCommitCom().equals("")) {
            	commitCom.setValue(xm.getCommitCom());
			} else {
				commitCom.setValue("");
			}
            //委托单位地址
            if (xm.getCommitComPlace()!= null&&!xm.getCommitComPlace().equals("")) {
            	commitComplace.setValue(xm.getCommitComPlace());
			} else {
				commitComplace.setValue("");
			}
            //受托单位地址
            if (xm.getAcceptCom()!= null&&!xm.getAcceptCom().equals("")) {
            	acceptCom.setValue(xm.getAcceptCom());
			} else {
				acceptCom.setValue("");
			}
            //受托单位地址
            if (xm.getAcceptComPlace()!= null&&!xm.getAcceptComPlace().equals("")) {
            	acceptComPlace.setValue(xm.getAcceptComPlace());
			} else {
				acceptComPlace.setValue("");
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
            writeuser.setValue(xm.getUser().getKuName());
			if(xm.getKuId().intValue()!=user.getKuId().intValue()){//非该用户创建的项目部分属性不可以编辑				
				number.setDisabled(true);ly.setDisabled(true);
				jingfei.setDisabled(true);prostaffs.setDisabled(true);
				proman.setDisabled(true);
				target.setDisabled(true);identtime.setDisabled(true);
				level.setDisabled(true);subjetype.setDisabled(true);
				scale.setDisabled(true);contraTypeListbox.setDisabled(true);
				progress.setDisabled(true);
				
				//横向项目信息
				contractNum.setDisabled(true);finishUnit.setDisabled(true);
				conFinishCondition.setDisabled(true);fruit.setDisabled(true);
				setFinishTime.setDisabled(true);realFinishTime.setDisabled(true);
				commitCom.setDisabled(true);commitComplace.setDisabled(true);
				acceptCom.setDisabled(true);acceptComPlace.setDisabled(true);
				
				//不可以上传附件				
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
				downFile.setDisabled(true);
				checkBackup.setDisabled(true);
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
			jingfei.setValue(BigDecimal.valueOf(0l));
			gongxian.setValue("0");
			prostaffs.setValue("");
			proman.setValue("");
			target.setValue("");
			identtime.setValue(null);
			level.setValue("");
			writeuser.setValue(user.getKuName());
			
			//横向项目信息
			contractNum.setValue("");finishUnit.setValue("");
			conFinishCondition.setValue("");fruit.setValue("");
			setFinishTime.setValue(null);realFinishTime.setValue(null);
			commitCom.setValue("");commitComplace.setValue("");
			acceptCom.setValue("");acceptComPlace.setValue("");
			
			deUpload.setDisabled(true);
			downFile.setDisabled(true);
			checkBackup.setDisabled(true);
			
			//导出报告
			exportMiddReport.setDisabled(true);
			resultReport.setDisabled(true);			
		}
		// 附件
		if (xm == null) {// 新添加
			rowFile.setVisible(true);
		} else {
			//修改，技术合同书
			List fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 23);
			if (fjList.size() == 0) {// 无附件
				rowFile.setVisible(true);	
			} else {// 有附件，初始化附件
				for (int i = fjList.size()-1; i >=0 ; i--) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList.get(i));					
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
						
		}
 }
	/**
	 * 提交保存
	 * 2011-5
	 * @throws InterruptedException 
	 * @throws InterruptedException
	 */
	public void onClick$submit() throws InterruptedException {		
		//项目编号 要么不填，要是填写必须唯一 if(number.getValue()==null||"".equals(number.getValue().trim())){ }else{ if(xm==null){//新建项目 //项目号唯一判重 if(xmService.findByKynumber(number.getValue()).size()>=1){ throw new WrongValueException(number, "项目编号必须唯一，该项目编号存在，请核实后填写！"); } }else{//修改项目 if(xmService.findByKynumber(number.getValue()).size()>=2){ throw new WrongValueException(number, "项目编号必须唯一，该项目编号存在，请核实后填写！"); } } }
		// 科研名称
		if (cgmc.getValue() == null || "".equals(cgmc.getValue().trim())) {
			throw new WrongValueException(cgmc, "您还没有填写科研名称！");
		}	
		//项目来源
		if(ly.getValue()==null||ly.getValue().trim().equals("")){
			throw new WrongValueException(ly, "您还没有填写项目来源！");
		}
		//项目合同编号
		if(contractNum.getValue()==null||"".equals(contractNum.getValue().trim())){
			throw new WrongValueException(contractNum, "您还没有填写项目合同编号！");
		}
			
		//负责人
		if (proman.getValue() == null || "".equals(proman.getValue().trim())) {
			throw new WrongValueException(proman, "您还没有填写项目负责人！");
		}
		
		// 经费
		Float kyJf = 0F;
		if (jingfei.getValue() != null &&!"".equals(jingfei.getValue().toString().trim())) {
			kyJf = Float.parseFloat(jingfei.getValue()+"");
		}
		// 本人贡献
		Integer kyGx = 1;
		if(gongxian.getValue()==null||"".equals(gongxian.getValue().trim())){
			throw new WrongValueException(gongxian, "请检查项目组成员是否有非法字符，贡献是一个规范的数字！");
		}else{
			try{
				kyGx = Integer.parseInt(gongxian.getValue());
			}catch(NumberFormatException e){
				throw new WrongValueException(gongxian, "项目组成员有非法字符！");
			}		
		}	
				
		//规定完成时间
		if(setFinishTime.getValue()==null||setFinishTime.getValue().equals("")){
			throw new WrongValueException(setFinishTime,"您还没有填写项目计划开始时间！");
		}
		//规定完成时间
		if(realFinishTime.getValue()==null||realFinishTime.getValue().equals("")){
			throw new WrongValueException(realFinishTime,"您还没有填写项目计划结束时间！");
		}		
		// false表示是修改，true表示是新建
		boolean index = false,owner=false;
		if (xm == null) {// 说明这是新建的一个项目
//			 if(!lxsj.isDisabled()){
//				 owner=true;
//			    }
			owner=true;
			xm = new GhXm();
			index = true;
		}
	
		//2011-5-19 文件上传是否选择		
		Sessions.getCurrent().setAttribute("check",checkBackup); 
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
				xm.setKyProstaffs(user.getKuName());
				xm.setKyProman(proman.getValue().trim());
				xm.setKyTarget(target.getValue().trim());
				if(identtime.getValue()!=null){
					xm.setKyIdenttime(DateUtil.getDateString(identtime.getValue()));
				}
				xm.setKyLevel(level.getValue().trim());
				xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
				xm.setContractType(String.valueOf(contraTypeListbox.getSelectedIndex()));
				xm.setKyClass("1");				//横向为1
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
				//2011-5-18项目内部编号  字母+合同编号							
				if(Integer.valueOf(xm.getKyClass().trim())==1){
					xm.setInternalNum("H"+contractNum.getValue());
				}else if(Integer.valueOf(xm.getKyClass().trim())==2){
					xm.setInternalNum("Z"+contractNum.getValue());
				}
				
				xm.setCommitCom(commitCom.getValue());
				xm.setCommitComPlace(commitComplace.getValue());
				xm.setAcceptCom(acceptCom.getValue());
				xm.setAcceptComPlace(acceptComPlace.getValue());
				
				xm.setKyContraNum(contractNum.getValue());
				xm.setKyFinishUnit(finishUnit.getValue());			
				if(setFinishTime.getValue()!=null){
					xm.setKySetFinishTime(DateUtil.getDateString(setFinishTime.getValue()));
				}
				if(realFinishTime.getValue()!=null){
					xm.setKyRealFinishTime(DateUtil.getDateString(realFinishTime.getValue()));
				}				
				xm.setKyContentFinishConditon(conFinishCondition.getValue());
				xm.setKyFruit(fruit.getValue());				
				xmService.update(xm);
				
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());//合同书
				
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
				jsxm.setJsxmType(1);
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
					xm.setKyProstaffs(user.getKuName());
					xm.setKyProman(proman.getValue().trim());
					xm.setKyTarget(target.getValue().trim());
					if(identtime.getValue()!=null){
						xm.setKyIdenttime(DateUtil.getDateString(identtime.getValue()));
					}
					xm.setKyLevel(level.getValue().trim());
					xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
					xm.setContractType(String.valueOf(contraTypeListbox.getSelectedIndex()));
					xm.setKyClass("1");					
					//2011-5-18				
					if(xm.getKyClass().equals("1")){
						xm.setInternalNum("H"+contractNum.getValue());
					}else if(xm.getKyClass().equals("2")){
						xm.setInternalNum("Z"+contractNum.getValue());
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
					
					xm.setCommitCom(commitCom.getValue());
					xm.setCommitComPlace(commitComplace.getValue());
					xm.setAcceptCom(acceptCom.getValue());
					xm.setAcceptComPlace(acceptComPlace.getValue());
					
					xm.setKyContraNum(contractNum.getValue());
					xm.setKyFinishUnit(finishUnit.getValue());
					if(setFinishTime.getValue()!=null){
						xm.setKySetFinishTime(DateUtil.getDateString(setFinishTime.getValue()));
					}
					if(realFinishTime.getValue()!=null){
						xm.setKyRealFinishTime(DateUtil.getDateString(realFinishTime.getValue()));
					}
					xm.setKyContentFinishConditon(conFinishCondition.getValue());
					xm.setKyFruit(fruit.getValue());
					
					xmService.update(xm);
					
//					List jslwlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.KYXM);
//					for(int i=0;i<jslwlist.size();i++){
//						GhJsxm js=(GhJsxm)jslwlist.get(i);
//						if(!prostaffs.getValue().trim().contains(js.getUser().getKuName().trim())){
//							jsxmService.delete(js);
//						}
//					}
					
					// 附件存储
					UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());//合同书
					
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
						jsxm.setKyMemberName(user.getKuName());
						jsxm.setKyTaskDesc("");
						jsxmService.save(jsxm);
						Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
						Events.postEvent(Events.ON_CHANGE, this, null);
					}
				}
			}
	}
	
		
    public void initXmWindow(String xmmc,String xmly,String porman,Short lx){
    	GhXm cxm=xmService.findByNameAndLyAndLxAndFzr(xmmc, lx, xmly, porman);
    	if(cxm!=null){
    		xm=cxm;
    		cgmc.setDisabled(true);
    		number.setValue(cxm.getKyNumber());number.setDisabled(true);
			ly.setValue(cxm.getKyLy());  ly.setDisabled(true);
			jingfei.setValue(BigDecimal.valueOf(cxm.getKyJf()));jingfei.setDisabled(true);
			prostaffs.setValue(cxm.getKyProstaffs());prostaffs.setDisabled(true);
			proman.setValue(cxm.getKyProman());proman.setDisabled(true);
			target.setValue(cxm.getKyTarget());target.setDisabled(true);
			identtime.setValue(DateUtil.getDate(cxm.getKyIdenttime()));identtime.setDisabled(true);
			level.setValue(cxm.getKyLevel());level.setDisabled(true);
			
			//横向项目信息
			contractNum.setValue(cxm.getKyContraNum());contractNum.setDisabled(true);
			finishUnit.setValue(cxm.getKyFinishUnit());finishUnit.setDisabled(true);
			commitCom.setValue(cxm.getCommitCom());commitCom.setDisabled(true);
			commitComplace.setValue(cxm.getCommitComPlace());commitComplace.setDisabled(true);
			acceptCom.setValue(cxm.getAcceptCom());acceptCom.setDisabled(true);
			acceptComPlace.setValue(cxm.getAcceptComPlace());acceptComPlace.setDisabled(true);
			
			conFinishCondition.setValue(cxm.getKyContentFinishConditon());conFinishCondition.setDisabled(true);
			fruit.setValue(cxm.getKyFruit());fruit.setDisabled(true);
			setFinishTime.setValue(DateUtil.getDate(cxm.getKySetFinishTime()));setFinishTime.setDisabled(true);
			realFinishTime.setValue(DateUtil.getDate(cxm.getKyRealFinishTime()));realFinishTime.setDisabled(true);
			
			// 学科分类
			if (cxm.getKySubjetype() == null || cxm.getKySubjetype() == "") {
				subjetype.setSelectedIndex(0);
				subjetype.setDisabled(true);
			} else {
				subjetype.setSelectedIndex(Integer.valueOf(cxm.getKySubjetype().trim()));
				subjetype.setDisabled(true);
			}
			// 合同类型
			if (cxm.getContractType()== null || cxm.getContractType() == "") {
				contraTypeListbox.setSelectedIndex(0);
				contraTypeListbox.setDisabled(true);
			} else {
				contraTypeListbox.setSelectedIndex(Integer.valueOf(cxm.getContractType().trim()));
				contraTypeListbox.setDisabled(true);
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
            //合同编号
            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
            	contractNum.setValue(xm.getKyContraNum());
			} else {
				contractNum.setValue("");
			}
//            //完成单位
//            if (xm.getKyFinishUnit()!= null&&!xm.getKyFinishUnit().equals("")) {
//            	finishUnit.setValue(xm.getKyFinishUnit());
//			} else {
//				finishUnit.setValue("");
//			}
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
		    List namelist=new ArrayList();
			String str=prostaffs.getValue().trim();
			if(str.contains("，")||str.contains(",")){
				throw new WrongValueException(prostaffs, "全部作者填写错误，请选择顿号！");
			}else{
			while (str.contains("、")) {
					Label lb = new Label(str.substring(0,str.indexOf("、")));
					namelist.add(lb.getValue().trim());
					str = str.substring(str.indexOf("、") + 1, str.length());
				
			}
			namelist.add(str.trim());
			gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
			}
    	}
    	else{
    		xm=null;
//			kaishi.setValue("");
//			number.setValue("");
//			jieshu.setValue(""); 
//			jingfei.setValue("");
//			gongxian.setValue("0");
//			prostaffs.setValue("");
////		register.setValue("");
//			target.setValue("");
//			identtime.setValue("");
//			level.setValue("");
//			writeuser.setValue(user.getKuName());
			
//			number.setDisabled(false);
//			identtime.setDisabled(false);
//			level.setDisabled(false);
//			target.setDisabled(false);
//			lxsj.setValue("");
//			lxsj.setDisabled(false);
//			kaishi.setDisabled(false);
//			jieshu.setDisabled(false);
//			jingfei.setDisabled(false);
//			prostaffs.setDisabled(false);
//			subjetype.setSelectedIndex(0);
//			subjetype.setDisabled(false);
//			kyclass.setSelectedIndex(0);
//			kyclass.setDisabled(false);
//			scale.setSelectedIndex(0);
//			scale.setDisabled(false);
//			progress.setSelectedIndex(0);
//			progress.setDisabled(false);
    	}
    }
    /**
	* <li>功能描述：选择项目名称。
	 * @param null
	 * @return null
	 */
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
				if(!Xm.getKyProstaffs().contains(user.getKuName())){
					Messagebox.show("您不是项目组成员，请联系项目填写人申请加入！", "提示：", Messagebox.OK, Messagebox.EXCLAMATION);
				}
				else{
				xm=Xm;
				cgmc.setValue(Xm.getKyMc());cgmc.setDisabled(true);
				number.setValue(Xm.getKyNumber());number.setDisabled(true);
				ly.setDisabled(true);
				jingfei.setValue(BigDecimal.valueOf(Xm.getKyJf()));jingfei.setDisabled(true);
//				prostaffs.setValue(Xm.getKyProstaffs());prostaffs.setDisabled(true);
				proman.setValue(Xm.getKyProman());proman.setDisabled(true);
				target.setValue(Xm.getKyTarget());target.setDisabled(true);
				identtime.setValue(DateUtil.getDate(Xm.getKyIdenttime()));identtime.setDisabled(true);
				level.setValue(Xm.getKyLevel());level.setDisabled(true);				
				
				commitCom.setValue(Xm.getCommitCom());commitCom.setDisabled(true);
				commitComplace.setValue(Xm.getCommitComPlace());commitComplace.setDisabled(true);
				acceptCom.setValue(Xm.getAcceptCom());acceptCom.setDisabled(true);
				acceptComPlace.setValue(Xm.getAcceptComPlace());acceptComPlace.setDisabled(true);
				
				// 学科分类
				if (Xm.getKySubjetype() == null || Xm.getKySubjetype() == "") {
					subjetype.setSelectedIndex(0);
					subjetype.setDisabled(true);
				} else {
					subjetype.setSelectedIndex(Integer.valueOf(Xm.getKySubjetype().trim()));
					subjetype.setDisabled(true);
				}
				// 合同类型
				if (Xm.getContractType() == null || Xm.getContractType() == "") {
					contraTypeListbox.setSelectedIndex(0);
					contraTypeListbox.setDisabled(true);
				} else {
					contraTypeListbox.setSelectedIndex(Integer.valueOf(Xm.getContractType().trim()));
					contraTypeListbox.setDisabled(true);
				}
				
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

				 writeuser.setValue(Xm.getUser().getKuName());		
	            //合同编号
	            if (xm.getKyContraNum()!= null&&!xm.getKyContraNum().equals("")) {
	            	contractNum.setValue(xm.getKyContraNum());
				} else {
					contractNum.setValue("");
				}
//	            //完成单位
//	            if (xm.getKyFinishUnit()!= null&&!xm.getKyFinishUnit().equals("")) {
//	            	finishUnit.setValue(xm.getKyFinishUnit());
//				} else {
//					finishUnit.setValue("");
//				}
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
				sxw.detach();				
				}
			 }
			}
    	});
    }
    /**
     * 上传技术合同书
     * @date 2011-5-18
     * @throws InterruptedException
     */
	public void onClick$upFile() throws InterruptedException {
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(), 23, 10, "附件不能超过10MB","附件------文件大小不能超过10MB");
		if (media == null) {
			return;
		}
		fileModel.add(ufj);
		rowFile.setVisible(true);upList.setVisible(true);
		deUpload.setVisible(true);checkBackup.setVisible(true);
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
	
	/**
	* <li>功能描述：打包下载附件。
	 * @param null
	 * @return null
	 */
	public void onClick$downFileZip(){
		DownloadFJ.ListModelListDownloadCommand(this.getDesktop(), this.xm.getKyId(), fileModel);
	}

	/**
	* <li>功能描述：单个文件下载。
	 * @param null
	 * @return null
	 */
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
	
	/**
	 * 生成中期报告
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws InterruptedException 
	 */
	public void onClick$exportMiddReport() throws WriteException, IOException, InterruptedException{
		Map arg=new HashMap();
		arg.put("hProgram", this.xm);
		HMiddReportWindow hmWin = (HMiddReportWindow) Executions.createComponents
								("/admin/personal/data/teacherinfo/kyqk/kyxm/HmiddReport.zul", null, arg);
		hmWin.doHighlighted();
	}

	/**
	 * 生成结项报告
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws InterruptedException 
	 */
	public void onClick$resultReport() throws WriteException, IOException, InterruptedException{
		Map arg=new HashMap();
		arg.put("hProgram", this.xm);
		HResultReportWindow hrWin = (HResultReportWindow) Executions.createComponents
								("/admin/personal/data/teacherinfo/kyqk/kyxm/HresultReport.zul", null, arg);
		hrWin.doHighlighted();
	}

	public void initShow() {
	}
	public void onClick$close() {
		this.detach();
	}
	public GhXm getXm() {
		return xm;
	}
	public void setXm(GhXm xm) {
		this.xm = xm;
	}
	public Long getKuid() {
		return kuid;
	}
	public void setKuid(Long kuid) {
		this.kuid = kuid;
	}
}
