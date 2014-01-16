package com.uniwin.asm.personal.ui.data.teacherinfo;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.CgService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.ui.listbox.YjfxListbox;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.asm.personal.ui.data.teacherinfo.kyqk.SelectXmWindow;
import com.uniwin.framework.entity.WkTUser;

import com.uniwin.framework.common.fileuploadex.DownloadFJ;
import com.uniwin.framework.common.fileuploadex.IsZipExists;
import com.uniwin.framework.common.fileuploadex.UploadFJ;

public class JyxmYwcWindow extends FrameworkWindow{

	Textbox ly,lxsj,kaishi,jieshu;
	Decimalbox jingfei;
	Label gongxian;
	Textbox cgmc;
	Textbox number,prostaffs,proman,target,identtime,level;
	Listbox subjetype,kyclass,scale,progress,cdrw;
	YjfxListbox research;
	Label writeuser;
//	private Radiogroup prize;
	GhXm xm;
	Long kuid;
	//暂存附件
	private Row rowFile;
	Listbox upList;
	ListModelList fileModel;
	Row members;
	XmService xmService;
	YjfxService yjfxService;
	CgService cgService;
	WkTUser user;
	GhFileService ghfileService;
	JsxmService jsxmService;
	String staff="";
	ProjectMemberService projectmemberService;
	Button downFile,deUpload,upFile,downFileZip,submit;
	@Override
	public void initShow() {
		
	}

	@Override
	public void initWindow() {
		
		List subtype = new ArrayList();
		List subclass = new ArrayList();
		List subscale = new ArrayList();
		List subprogress = new ArrayList();
		List subcdrw = new ArrayList();
		String[] Subtype = {"-请选择-","自然科学","社会科学", "其他"};
		for(int i = 0;i < Subtype.length; i++){
			subtype.add(Subtype[i]);
		}
		String[] Subclass = {"-请选择-","横向","纵向"};
		for(int i = 0;i < Subclass.length; i++){
			subclass.add(Subclass[i]);
		}
		String[] Subscale = {"-请选择-","国际合作项目","国家级项目","部（委、省）级项目","市厅级项目","委托及开发项目","学校基金项目", "其他"};
		for(int i = 0;i < Subscale.length; i++){
			subscale.add(Subscale[i]);
		}
		String[] Subprogress = {"-请选择-","申请中","在研","已完成"};
		for(int i = 0;i < Subprogress.length; i++){
			subprogress.add(Subprogress[i]);
		}
		String[] Subcdrw = {"-请选择-","主持","参加","独立"};
		for(int i = 0;i < Subcdrw.length; i++){
			subcdrw.add(Subcdrw[i]);
		}
//		//研究方向列表
//		List yjfxlist= yjfxService.findByKuid(kuid);
//		
//		List fx = new ArrayList();
//		if(yjfxlist != null && yjfxlist.size() !=0 ){
//			GhYjfx	yjfx = (GhYjfx) yjfxlist.get(0);
//			String[] Yjfx = {"-请选择-",yjfx.getYjResearch1(),yjfx.getYjResearch2(),yjfx.getYjResearch3(),yjfx.getYjResearch4(),yjfx.getYjResearch5()};
//			for(int i = 0;i < Yjfx.length; i++){
//				fx.add(Yjfx[i]);
//			}
//		
//		}else{
//			fx.add("--请选择--");
//		}
//		research.setModel(new ListModelList(fx));
		//学科分类
		subjetype.setModel(new ListModelList(subtype));
		//项目类别
		kyclass.setModel(new ListModelList(subclass));
		//项目级别
		scale.setModel(new ListModelList(subscale));
		//项目进展
		progress.setModel(new ListModelList(subprogress));
		//本人承担任务或作用
		cdrw.setModel(new ListModelList(subcdrw));
//		prostaffs.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				List namelist=new ArrayList();
//				String str=prostaffs.getValue().trim();
//				if(str.contains("，")||str.contains(",")){
//					throw new WrongValueException(prostaffs, "项目组人员填写错误,请选择顿号！");
//				}else{
//				while (str.contains("、")) {
//						Label lb = new Label(str.substring(0,str.indexOf("、")));
//						namelist.add(lb.getValue().trim());
//						str = str.substring(str.indexOf("、") + 1, str.length());
//					
//				}
//				namelist.add(str.trim());
//				gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
//			}
//			}
//			
//		});
//		cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
//			}
//		});
//		ly.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
//			}
//		});
//		proman.addEventListener(Events.ON_CHANGE, new EventListener(){
//
//			public void onEvent(Event arg0) throws Exception {
//				initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
//			}
//		});
		if(xm!=null){
			//项目编号
			number.setValue(xm.getKyNumber());
			
			//名称
			cgmc.setValue(xm.getKyMc());cgmc.setDisabled(true);

			//来源--可以为空
			if(xm.getKyLy()!=null){
				ly.setValue(xm.getKyLy());
			}
			if(xm.getKyLxsj()!=null){
			lxsj.setValue(xm.getKyLxsj());
			}
			//开始时间
			if(xm.getKyKssj()!=null){
				kaishi.setValue(xm.getKyKssj());
			}
			//结束时间
			if(xm.getKyJssj()!=null){
				jieshu.setValue(xm.getKyJssj());
			}
			
			//经费--可以为空
			if(xm.getKyJf()!=null){
				jingfei.setValue(BigDecimal.valueOf(xm.getKyJf()));
			}
//			//本人贡献
//			if(xm.getKyGx()!=null){
//				gongxian.setValue(xm.getKyGx()+"");
//			}
			//学科分类
			if(xm.getKySubjetype() == null || xm.getKySubjetype() == ""){
				subjetype.setSelectedIndex(0);
			}else {
				subjetype.setSelectedIndex(Integer.valueOf(xm.getKySubjetype().trim()));
			}
			//项目类别
			if(xm.getKyClass() == null || xm.getKyClass() == ""){
				kyclass.setSelectedIndex(0);
			}else {
				kyclass.setSelectedIndex(Integer.valueOf(xm.getKyClass().trim()));
			}
			//项目级别
			if(xm.getKyScale() == null || xm.getKyScale() == ""){
				scale.setSelectedIndex(0);
			}else {
				scale.setSelectedIndex(Integer.valueOf(xm.getKyScale().trim()));
			}
			//项目进展
			if(xm.getKyProgress() == null || xm.getKyProgress() == ""){
				progress.setSelectedIndex(0);
			}else {
				progress.setSelectedIndex(Integer.valueOf(xm.getKyProgress().trim()));
			}
			//项目组人员
			List mlist=projectmemberService.findByKyXmId(xm.getKyId());
			if(mlist.size()!=0)
			{
				staff="";
				for(int i=0;i<mlist.size();i++)
				{
					GhJsxm member=(GhJsxm)mlist.get(i);
				
				staff+=member.getKyMemberName()+"、";
				}
			}
			prostaffs.setValue(staff.substring(0, staff.length()-1));
			
			//项目负责人
			if(xm.getKyProman() != null){
				proman.setValue(xm.getKyProman());
			}else{
				proman.setValue("");
			}
			
//			//申报人
//			if(xm.getKyRegister() != null){
//				register.setValue(xm.getKyRegister());	
//			}else{
//				register.setValue("");	
//			}
			
			//项目指标
			if(xm.getKyTarget() != null){
				target.setValue(xm.getKyTarget());
			}else{
				target.setValue("");
			}
			
			//鉴定时间
			if(xm.getKyIdenttime() != null){
				identtime.setValue(xm.getKyIdenttime());
			}else{
				identtime.setValue("");	
			}
			
			//鉴定水平
			if(xm.getKyLevel() != null){
				level.setValue(xm.getKyLevel());
			}else{
				level.setValue("");
			}
			
			//教师项目
            GhJsxm jx=(GhJsxm)jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.JYXM);
            if(jx!=null){
            	// 本人贡献
            	gongxian.setValue(jx.getKyGx()!=null?jx.getKyGx()+"":"0");
//            	// 研究方向
//    			if (jx.getYjId() == null || jx.getYjId() == "") {
//    				research.setSelectedIndex(0);
//    			} else {
//    				research.setSelectedIndex(Integer.valueOf(jx.getYjId().trim()));
//    			}
    			research.initYjfzList(user.getKuId(),jx.getYjId());
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
            writeuser.setValue(xm.getUser().getKuName());
            if(xm.getKuId().intValue()!=user.getKuId().intValue()){
				//非该用户创建的项目部分属性不可以编辑
				number.setDisabled(true);ly.setDisabled(true);lxsj.setDisabled(true);
				kaishi.setDisabled(true);jieshu.setDisabled(true);
				jingfei.setDisabled(true);prostaffs.setDisabled(true);
				proman.setDisabled(true);
//				register.setDisabled(true);
				target.setDisabled(true);identtime.setDisabled(true);
				level.setDisabled(true);subjetype.setDisabled(true);
				kyclass.setDisabled(true);scale.setDisabled(true);
				progress.setDisabled(true);
				//非本人创建的项目不可以管理项目的附件
				deUpload.setDisabled(true);
				upFile.setDisabled(true);
			}
//            if(xm.getAuditState()!=null&&xm.getAuditState().shortValue()==GhXm.AUDIT_YES){
//            	 submit.setDisabled(true);
//    	    	 upFile.setDisabled(true);
//            }
		}else{
			members.setVisible(false);
			cgmc.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
				}
			});
			ly.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
				}
			});
			proman.addEventListener(Events.ON_CHANGE, new EventListener(){

				public void onEvent(Event arg0) throws Exception {
					initXmWindow(cgmc.getValue().trim(),ly.getValue().trim(),proman.getValue().trim(),GhXm.JYXM);
				}
			});
			cgmc.setValue("");
			ly.setValue("");
			kaishi.setValue("");
			jieshu.setValue("");
			jingfei.setValue(BigDecimal.valueOf(0l));
			gongxian.setValue("0");
			prostaffs.setValue("");
			proman.setValue("");
//			register.setValue("");
			target.setValue("");
			identtime.setValue("");
			level.setValue("");
			writeuser.setValue(user.getKuName());
			deUpload.setDisabled(true);
			downFileZip.setDisabled(true);
			downFile.setDisabled(true);
			research.initYjfzList(user.getKuId(),null);
			//选择项目名称
//			if(cgmc.getModel().getSize()>0){
//			cgmc.addEventListener(Events.ON_SELECT, new EventListener(){
//
//				public void onEvent(Event arg0) throws Exception {
//					if(cgmc.getSelectedItem()!=null){
//					Object [] mc=(Object [])cgmc.getSelectedItem().getValue();
//					GhXm xm=(GhXm)xmService.get(GhXm.class,(Long)mc[1]);
//					number.setValue(xm.getKyNumber());number.setDisabled(true);
//					ly.setValue(xm.getKyLy());ly.setDisabled(true);
//					lxsj.setValue(xm.getKyLxsj());lxsj.setDisabled(true);
//					kaishi.setValue(xm.getKyKssj());kaishi.setDisabled(true);
//					jieshu.setValue(xm.getKyJssj());jieshu.setDisabled(true);
//					jingfei.setValue(xm.getKyJf()+"");jingfei.setDisabled(true);
//					prostaffs.setValue(xm.getKyProstaffs());prostaffs.setDisabled(true);
//					proman.setValue(xm.getKyProman());proman.setDisabled(true);
//					register.setValue(xm.getKyRegister());register.setDisabled(true);
//					target.setValue(xm.getKyTarget());target.setDisabled(true);
//					identtime.setValue(xm.getKyIdenttime());identtime.setDisabled(true);
//					level.setValue(xm.getKyLevel());level.setDisabled(true);
//					// 学科分类
//					if (xm.getKySubjetype() == null || xm.getKySubjetype() == "") {
//						subjetype.setSelectedIndex(0);
//						subjetype.setDisabled(true);
//					} else {
//						subjetype.setSelectedIndex(Integer.valueOf(xm.getKySubjetype().trim()));
//						subjetype.setDisabled(true);
//					}
//					// 项目类别
//					if (xm.getKyClass() == null || xm.getKyClass() == "") {
//						kyclass.setSelectedIndex(0);
//						kyclass.setDisabled(true);
//					} else {
//						
//						kyclass.setSelectedIndex(Integer.valueOf(xm.getKyClass().trim()));
//						kyclass.setDisabled(true);
//					}
//					// 项目级别
//					if (xm.getKyScale() == null || xm.getKyScale() == "") {
//						scale.setSelectedIndex(0);
//						scale.setDisabled(true);
//					} else {
//						scale.setSelectedIndex(Integer.valueOf(xm.getKyScale().trim()));
//						scale.setDisabled(true);
//					}
//					// 项目进展
//					if (xm.getKyProgress() == null || xm.getKyProgress() == "") {
//						progress.setSelectedIndex(0);
//						progress.setDisabled(true);
//					} else {
//						progress.setSelectedIndex(Integer.valueOf(xm.getKyProgress().trim()));
//						progress.setDisabled(true);
//					}
//					if(xm.getKyProstaffs().contains(user.getKuName())&&xm.getKyProstaffs().contains(",")){
//						List namelist=new ArrayList();
//						String str=xm.getKyProstaffs().trim();
//						while (str.contains(",")) {
//								Label lb = new Label(str.substring(0,str.indexOf(",")));
//								namelist.add(lb.getValue());
//								str = str.substring(str.indexOf(",") + 1, str.length());
//							
//						}
//						namelist.add(str);
//						gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
//						gongxian.setDisabled(true);
//					}else{
//						gongxian.setValue("0");
//						gongxian.setDisabled(true);
//					}
//					// 附件情况
//					List fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 10);
//					if (fjList.size() == 0) {// 无附件
//						rowFile.setVisible(false);
//						downFileZip.setDisabled(true);
//					} else {// 有附件
//						// 初始化附件
//						downFileZip.setDisabled(false);
//						for (int i = 0; i < fjList.size(); i++) {
//							UploadFJ ufj = new UploadFJ(false);
//							try {
//								ufj.ReadFJ(getDesktop(), (GhFile) fjList.get(i));
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							fileModel.add(ufj);
//						}
//						upList.setModel(fileModel);
//						rowFile.setVisible(true);
//					}
//					}
//					
//				}
//				
//			});
//			}
			
		}
		// 附件
		if (xm == null) {// 新添加
			rowFile.setVisible(false);
			downFileZip.setDisabled(true);
		} else {// 修改
			List fjList = ghfileService.findByFxmIdandFType(xm.getKyId(), 10);
			if (fjList==null||fjList!=null&&fjList.size() == 0) {// 无附件
				rowFile.setVisible(false);
				downFileZip.setDisabled(true);
			} else {// 有附件
				// 初始化附件
				downFileZip.setDisabled(false);
				for (int i = 0; i < fjList.size(); i++) {
					UploadFJ ufj = new UploadFJ(false);
					try {
						ufj.ReadFJ(this.getDesktop(), (GhFile) fjList.get(i));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fileModel.add(ufj);
				}
				upList.setModel(fileModel);
				rowFile.setVisible(true);
			}
		}
		//
	}
	
	public void onClick$submit() throws InterruptedException{
	/*	if(xm==null){//添加新项目
		//项目号唯一判重
			
		if(xmService.findByKynumber(number.getValue()).size()!=0){
			throw new WrongValueException(number, "项目编号必须唯一，该项目编号存在，请核实后填写！");
		}}else{//修改新项目
			if(xmService.findByKynumber(number.getValue()).size()!=1){
				throw new WrongValueException(number, "项目编号必须唯一，该项目编号存在，请核实后填写！");
			}
		}
		//项目编号
		if(number.getValue()==null||"".equals(number.getValue().trim())){
			throw new WrongValueException(number, "您还没有填写项目编号！");
		}*/
		//科研名称
		if(cgmc.getValue()==null||"".equals(cgmc.getValue().trim())){
			throw new WrongValueException(cgmc, "您还没有填写科研名称！");
		}
		//来源
		if(ly.getValue()==null||"".equals(ly.getValue().trim())){
			throw new WrongValueException(ly, "您还没有填写项目来源！");
		}
		//项目负责人
		if(proman.getValue()==null||"".equals(proman.getValue().trim())){
			throw new WrongValueException(proman, "您还没有填写项目负责人！");
		}
		//立项时间
		if(lxsj.getValue()==null||"".equals(lxsj.getValue().trim())){
			throw new WrongValueException(lxsj, "您还没有填写立项时间，格式如2008/9/28、2008、2008/9！");
		}else{
			try{
				String str = lxsj.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(lxsj, "您输入的立项时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					 if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(lxsj, "您输入的立项时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					}
				}else{
					throw new WrongValueException(lxsj, "您输入的立项时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(lxsj, "您输入的立项时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		}
		//开始时间
		if(kaishi.getValue()==null||"".equals(kaishi.getValue().trim())){
			throw new WrongValueException(kaishi, "您还没有填写开始时间，格式如2008/9/28、2008、2008/9！");
		}else{
			try{
				String str = kaishi.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(kaishi, "您输入的开始时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					 if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(kaishi, "您输入的开始时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
					}
				}else{
					throw new WrongValueException(kaishi, "您输入的开始时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(kaishi, "您输入的开始时间格式有错误，请您重新填写，格式如2008/9/28、2008、2008/9！");
			}
		}
		//结束时间
		if(jieshu.getValue()==null||"".equals(jieshu.getValue().trim())){
			throw new WrongValueException(jieshu, "您还没有填写结束时间，格式如2008/9/28、2008、2008/9！");
		}else{
			try{
				String str = jieshu.getValue().trim();
				if(str.length()<4){
					throw new WrongValueException(jieshu, "您输入的结束时间格式有错误，格式如2008/9/28、2008、2008/9！");
				}
				else if(str.length()==4||'/'==str.charAt(4)){
					String s[] = str.split("/");
					if(s.length==1||s.length==2||s.length==3){
						for(int i=0;i<s.length;i++){
							String si = s[i];
							Integer.parseInt(si);
						}
					}else{
						throw new WrongValueException(jieshu, "您输入的结束时间格式有错误，格式如2008/9/28、2008、2008/9！");
						
					}
				}else{
					throw new WrongValueException(jieshu, "您输入的结束时间格式有错误，格式如2008/9/28、2008、2008/9！");
					
				}
			}catch(NumberFormatException e){
				throw new WrongValueException(jieshu, "您输入的结束时间格式有错误，格式如2008/9/28、2008、2008/9！");
				
			}
			
		}
		
		//经费
		Float kyJf = 0F;
		if (jingfei.getValue() != null &&!"".equals(jingfei.getValue().toString().trim())) {
			kyJf = Float.parseFloat(jingfei.getValue()+"");
		}
		//本人贡献
		Integer kyGx = 1;
//		if(gongxian.getValue()==null||"".equals(gongxian.getValue().trim())){
//			throw new WrongValueException(gongxian, "请检查项目组成员是否有非法字符，贡献是一个规范的数字！");
//		}else{
//			try{
//				kyGx = Integer.parseInt(gongxian.getValue());
//			}catch(NumberFormatException e){
//				throw new WrongValueException(gongxian, "项目组成员有非法字符！");
//			}
//		
//		}	
		//false表示是修改，true表示是新建
		boolean index = false,owner=false; 
		if(xm==null){//说明这是新建的一个项目
			 if(!lxsj.isDisabled()){
				 owner=true;
			    }
			xm = new GhXm();
			index = true;
		}
//		if(cgmc.getSelectedItem()==null){
//			//没有选择项目名称，该项目属于该用户
//			owner=true;
//		}else{
//			owner=false;
//		}		
		if(index){
			//新建项目,两种情况：（一）当前用户输入了项目名称，保存到项目表和教师项目表；（二），用户选择的项目名称，只保存教师项目表
			if(owner){
//				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
//					Messagebox.show("项目组人员不包括您的姓名，请联系项目信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
//					xm=null;
//					return;
//				}
				if(!xmService.CheckOnlyOne(cgmc.getValue().trim(), GhXm.JYXM, ly.getValue().trim(),proman.getValue().trim())){
					Messagebox.show("您提交的项目信息已存在，不可以重复添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
					xm=null;
					return;
				}
				xm.setKyNumber(number.getValue().trim());
				xm.setKyMc(cgmc.getValue().trim());
				xm.setKyLxsj(lxsj.getValue().trim());
				xm.setKyKssj(kaishi.getValue().trim());
				xm.setKyJssj(jieshu.getValue().trim());
				xm.setKyJf(kyJf);
				xm.setKyLy(ly.getValue().trim());
				xm.setKyProstaffs(proman.getValue().trim());
				xm.setKyProman(proman.getValue().trim());
//				xm.setKyRegister(register.getValue().trim());
				xm.setKyTarget(target.getValue().trim());
				xm.setKyIdenttime(identtime.getValue().trim());
				xm.setKyLevel(level.getValue().trim());
				xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
				xm.setKyClass(String.valueOf(kyclass.getSelectedIndex()));
				xm.setKyScale(String.valueOf(scale.getSelectedIndex()));
				xm.setKuId(kuid);
				xm.setKyLx(GhXm.JYXM);
				xm.setKyProgress(String.valueOf(progress.getSelectedIndex()));
				xmService.save(xm);
				//保存到教师项目表
				GhJsxm jsxm=new GhJsxm();
				jsxm.setKyId(xm.getKyId());
				jsxm.setJsxmType(GhJsxm.JYXM);
				jsxm.setKuId(user.getKuId());
				jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
				jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
				jsxm.setKyGx(kyGx);
				jsxm.setKyMemberName(user.getKuName());
				jsxm.setKyTaskDesc("");
				jsxmService.save(jsxm);
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
				Events.postEvent(Events.ON_CHANGE, this, null);
			}else{
				//保存到教师项目表
//				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
//					Messagebox.show("项目组人员不包括您的姓名，请联系项目信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
//					xm=null;
//					return;
//				}
//				GhJsxm jsxm=new GhJsxm();
//				jsxm.setKyId(xm.getKyId());
//				jsxm.setJsxmType(GhJsxm.JYXM);
//				jsxm.setKuId(user.getKuId());
//				jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
//				jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
//				jsxm.setKyGx(kyGx);
//				jsxmService.save(jsxm);
				Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
				Events.postEvent(Events.ON_CHANGE, this, null);
			}
		}else{
			//修改项目：原来项目是该用户创建的更新项目表和教师项目表；原来项目非该用户创建，只更新教师项目表
			if(user.getKuId().intValue()==xm.getKuId().intValue()){
				if(!xmService.findByNameAndLxAndLy(xm, cgmc.getValue().trim(),GhXm.JYXM, ly.getValue().trim(),proman.getValue().trim())){
					Messagebox.show("您提交的项目信息已存在，不可以重复添加！","警告",Messagebox.OK,Messagebox.EXCLAMATION);
					return;
				}
				
				xm.setKyNumber(number.getValue().trim());
//				xm.setKyMc(cgmc.getValue());//名称不可改
				xm.setKyLxsj(lxsj.getValue().trim());
				xm.setKyKssj(kaishi.getValue().trim());
				xm.setKyJssj(jieshu.getValue().trim());
				xm.setKyProstaffs(prostaffs.getValue().trim());
				xm.setKyProman(proman.getValue().trim());
//				xm.setKyRegister(register.getValue().trim());
				xm.setKyTarget(target.getValue().trim());
				xm.setKyIdenttime(identtime.getValue().trim());
				xm.setKyLevel(level.getValue().trim());
				xm.setKySubjetype(String.valueOf(subjetype.getSelectedIndex()));
				xm.setKyClass(String.valueOf(kyclass.getSelectedIndex()));
				xm.setKyScale(String.valueOf(scale.getSelectedIndex()));
				xm.setKyJf(kyJf);
				xm.setKyLy(ly.getValue());
				xm.setKuId(user.getKuId());
				xm.setKyLx(GhXm.JYXM);
				xm.setKyProgress(String.valueOf(progress.getSelectedIndex()));
				xm.setAuditState(null);
				xm.setAuditUid(null);
				xm.setReason(null);
				xmService.update(xm);
				List jslwlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.JYXM);
				for(int i=0;i<jslwlist.size();i++){
					GhJsxm js=(GhJsxm)jslwlist.get(i);
					if(!prostaffs.getValue().trim().contains(js.getUser().getKuName().trim())){
						jsxmService.delete(js);
					}
				}
				// 附件存储
				UploadFJ.ListModelListUploadCommand(fileModel, xm.getKyId());
				GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(), GhJsxm.JYXM);
				if(jsxm!=null){
					jsxm.setKyCdrw(String.valueOf(cdrw.getSelectedIndex()));
					jsxm.setYjId(research.getSelectedIndex()!=0?((GhYjfx)research.getSelectedItem().getValue()).getGyId():0L);
					jsxm.setKyGx(kyGx);
					jsxmService.update(jsxm);
					Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
					Events.postEvent(Events.ON_CHANGE, this, null);
				}else{
				
					Messagebox.show("保存成功！","提示",Messagebox.OK,Messagebox.INFORMATION);
					Events.postEvent(Events.ON_CHANGE, this, null);
				}
			}else{
				if(!prostaffs.getValue().trim().contains(user.getKuName())&&!proman.getValue().trim().contains(user.getKuName())){
					Messagebox.show("项目组人员不包括您的姓名，请联系项目信息添加人！", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				//只更新教师项目表
				GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(), user.getKuId(),GhJsxm.JYXM);
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
					jsxm.setJsxmType(GhJsxm.JYXM);
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
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		fileModel = new ListModelList(new ArrayList());
		upList.setModel(fileModel);
	
//		cgmc.setModel(new ListModelList(xmService.findAllname(user.getKuId(),user.getKuName(),GhXm.JYXM,GhJsxm.JYXM)));
//		cgmc.setItemRenderer(new ComboitemRenderer(){
//
//			public void render(Comboitem arg0, Object arg1) throws Exception {
//				Object[] name=(Object[])arg1;
//				arg0.setWidth("200px");
//				arg0.setValue(name);
//				arg0.setLabel((String)name[0]);
//			}
//			
//		});
	}
	
	 public void initXmWindow(String xmmc,String xmly,String porman,Short lx){
	    	GhXm cxm=xmService.findByNameAndLyAndLxAndFzr(xmmc, lx, xmly, porman);
	    	if(cxm!=null){
	    		xm=cxm;
	    		number.setValue(cxm.getKyNumber());number.setDisabled(true);
				ly.setValue(cxm.getKyLy()); 
				lxsj.setValue(cxm.getKyLxsj());lxsj.setDisabled(true);
				kaishi.setValue(cxm.getKyKssj());kaishi.setDisabled(true);
				jieshu.setValue(cxm.getKyJssj());jieshu.setDisabled(true);
				jingfei.setValue(BigDecimal.valueOf(cxm.getKyJf()));jingfei.setDisabled(true);
				prostaffs.setValue(cxm.getKyProstaffs());prostaffs.setDisabled(true);
				proman.setValue(cxm.getKyProman());
//				register.setValue(xm.getKyRegister());register.setDisabled(true);
				target.setValue(cxm.getKyTarget());target.setDisabled(true);
				identtime.setValue(cxm.getKyIdenttime());identtime.setDisabled(true);
				level.setValue(cxm.getKyLevel());level.setDisabled(true);
				// 学科分类
				if (cxm.getKySubjetype() == null || cxm.getKySubjetype() == "") {
					subjetype.setSelectedIndex(0);
					subjetype.setDisabled(true);
				} else {
					subjetype.setSelectedIndex(Integer.valueOf(cxm.getKySubjetype().trim()));
					subjetype.setDisabled(true);
				}
				// 项目类别
				if (cxm.getKyClass() == null || cxm.getKyClass() == "") {
					kyclass.setSelectedIndex(0);
					kyclass.setDisabled(true);
				} else {
					kyclass.setSelectedIndex(Integer.valueOf(cxm.getKyClass().trim()));
					kyclass.setDisabled(true);
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
			    List namelist=new ArrayList();
				String str=prostaffs.getValue().trim();
//				if(str.contains("，")||str.contains(",")){
//					throw new WrongValueException(prostaffs, "项目组人员填写错误,请选择顿号！");
//				}else{
//				while (str.contains("、")) {
//						Label lb = new Label(str.substring(0,str.indexOf("、")));
//						namelist.add(lb.getValue().trim());
//						str = str.substring(str.indexOf("、") + 1, str.length());
//					
//				}
//				namelist.add(str.trim());
//				gongxian.setValue(namelist.indexOf(user.getKuName())+1+"");
//	    	  }
				List listxm=projectmemberService.findByKyIdAndKuId(xm.getKyId(), user.getKuId());
				GhJsxm jsxm=(GhJsxm) listxm.get(0);
				gongxian.setValue(jsxm.getKyGx().toString());
	    	}
	    	else{
	    		xm=null;
//				kaishi.setValue("");
//				number.setValue("");
//				jieshu.setValue(""); 
//				jingfei.setValue("");
//				gongxian.setValue("0");
//				prostaffs.setValue("");
////				register.setValue("");
//				target.setValue("");
//				identtime.setValue("");
//				level.setValue("");
//				writeuser.setValue(user.getKuName());
//				number.setDisabled(false);
//				identtime.setDisabled(false);
//				level.setDisabled(false);
//				target.setDisabled(false);
//				lxsj.setValue("");
//				lxsj.setDisabled(false);
//				kaishi.setDisabled(false);
//				jieshu.setDisabled(false);
//				jingfei.setDisabled(false);
//				prostaffs.setDisabled(false);
//				subjetype.setSelectedIndex(0);subjetype.setDisabled(false);
//				kyclass.setSelectedIndex(0); kyclass.setDisabled(false);
//				scale.setSelectedIndex(0); scale.setDisabled(false);
//				progress.setSelectedIndex(0); progress.setDisabled(false);
	    	}
	    }
	 public void onClick$choice(){
	    	Map args=new HashMap();
	    	args.put("user", user);
	    	args.put("lx", GhXm.JYXM);
	    	args.put("type", GhJsxm.JYXM);
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
					ly.setValue(Xm.getKyLy());ly.setDisabled(true);
					lxsj.setValue(Xm.getKyLxsj());lxsj.setDisabled(true);
					kaishi.setValue(Xm.getKyKssj());kaishi.setDisabled(true);
					jieshu.setValue(Xm.getKyJssj());jieshu.setDisabled(true);
					jingfei.setValue(BigDecimal.valueOf(Xm.getKyJf()));jingfei.setDisabled(true);
					prostaffs.setValue(Xm.getKyProstaffs());prostaffs.setDisabled(true);
					proman.setValue(Xm.getKyProman());proman.setDisabled(true);
//					register.setValue(xm.getKyRegister());register.setDisabled(true);
					target.setValue(Xm.getKyTarget());target.setDisabled(true);
					identtime.setValue(Xm.getKyIdenttime());identtime.setDisabled(true);
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
					if (Xm.getKyClass() == null || Xm.getKyClass() == "") {
						kyclass.setSelectedIndex(0);
						kyclass.setDisabled(true);
					} else {
						
						kyclass.setSelectedIndex(Integer.valueOf(Xm.getKyClass().trim()));
						kyclass.setDisabled(true);
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
				   	sxw.detach();
				 	
					}
					}}
	    	});
	    }
	public void onClick$upFile() throws InterruptedException {
	/*	Object media = Fileupload.get();
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(media);*/
		UploadFJ ufj = new UploadFJ(true);
		Media media = ufj.Upload(this.getDesktop(),10, 10, "附件不能超过10MB",
				"教研项目附件------附件不能超过10MB");
		/* Object media = Fileupload.get(); */
		if (media == null) {
			return;
		}
		rowFile.setVisible(true);
		fileModel.add(ufj);
	}
	/**
	 * <li>删除上传的文件，重新选择
	 * 
	 * @author bobo 2010-4-11
	 */
	public void onClick$deUpload() {
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath(
				"/upload/xkjs/").trim()
				+ "\\" + "_" + this.xm.getKyId() + "_" + ".zip");
		Listitem it = upList.getSelectedItem();
		if (it == null) {
			if (fileModel.getSize() > 0) {
				it = upList.getItemAtIndex(0);
			}
		}
		//
		UploadFJ temp = (UploadFJ) it.getValue();
		//
		if (fileModel.getSize() == 1) {
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
			rowFile.setVisible(false);
		} else if (fileModel.getSize() > 1) {
			temp.DeleteFJ();
			fileModel.remove(it.getValue());
		}
	}

	public void onClick$reset(){
		// 去掉旧的打包附件
		IsZipExists.delZipFile(this.getDesktop().getWebApp().getRealPath(
				"/upload/xkjs/").trim()
				+ "\\" + "_" + this.xm.getKyId() + "_" + ".zip");
		List list = fileModel.getInnerList();
		for (int i = 0; i < list.size(); i++) {
			((UploadFJ) list.get(i)).DeleteFJ();
		}
		initWindow();
		fileModel.clear();
		rowFile.setVisible(false);
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
	public void onClick$close(){
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
