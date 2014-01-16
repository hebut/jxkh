package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhXmzz;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XmzzService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.service.ZsService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.FmzlqkWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.KyxmzzlistWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class FmzlWindow extends BaseWindow {

	//获授权发明专利情况
	Toolbarbutton button5;
	Listbox listbox5;

	FmzlService fmzlService;
	QtService qtService;
	KyjhService kyjhService;
	PxService pxService;
	XshyService xshyService;
	SkService skService;
	ZsService zsService;
	JlhzService jlhzService;
	JxbgService jxbgService;
	XmService xmService;
	XmzzService xmzzService;
	QkdcService qkdcService;
	CgService cgService;
	LwzlService lwzlService;
	RjzzService rjzzService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	WkTUser user;
	JslwService jslwService;
	JsxmService jsxmService;
	@Override
	public void initShow() {
		initWindow();
		final Desktop desktop=this.getDesktop();
		listbox5.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhFmzl fm = (GhFmzl) arg1;

				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//发明专利名称
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(fm.getFmMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {

						final FmzlqkWindow cgWin = (FmzlqkWindow) Executions.createComponents
						("/admin/personal/data/teacherinfo/kyqk/fmzl/fmzlqk.zul", null, null);
						cgWin.setFm(fm);
						cgWin.setKuid(fm.getKuId());
						cgWin.doHighlighted();
						cgWin.initWindow();
						cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

							public void onEvent(Event arg0)
							throws Exception {
								initWindow();
								cgWin.detach();
							}

						});
					}
				});
				c2.appendChild(ib);

				//授权时间
				Listcell c3 = new Listcell(fm.getFmSj());

				//专利授权号
				Listcell c4 = new Listcell(fm.getFmSqh());
				
				Listcell c5 = new Listcell();
				InnerButton bj = new InnerButton("查看/编辑");
				c5.appendChild(bj);
				bj.addEventListener(Events.ON_CLICK, new EventListener(){
					public void onEvent(Event arg0) throws Exception {
						 KyxmzzlistWindow cgWin = (KyxmzzlistWindow) Executions.createComponents
							("/admin/personal/data/teacherinfo/kyqk/hylw/kyxmzzlist.zul", null, null);
						 	cgWin.setKuid(fm.getKuId());
							cgWin.setFmzl(fm);
							cgWin.initWindow();
							cgWin.doHighlighted();
					}});
				
				//功能
				Listcell c6 = new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("删除");
				
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(fm.getKuId().intValue()!=user.getKuId().intValue()){
							Messagebox.show("您不是此项目提交人，故不能删除！","提示", Messagebox.OK,Messagebox.EXCLAMATION);
						}else{
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							//删除附件
							List list=ghfileService.findByFxmIdandFType(fm.getFmId(), 7);
							if(list.size()>0){
								//存在附件
								//List[] fjList=new List[list.size()];
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(desktop, (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							List xmzzlist =	xmzzService.findByLwidAndKuid(fm.getFmId(),GhXmzz.FMZL);
							if(xmzzlist != null && xmzzlist.size() !=0){
								for(int i = 0;i < xmzzlist.size();i++){
									GhXmzz xmzz = (GhXmzz) xmzzlist.get(i);
									xmzzService.delete(xmzz);
								}
							}
							List cglist =jslwService.findByLwidAndType(fm.getFmId(), GhJslw.FMZL);
							if(cglist != null && cglist.size() != 0){
								for(int i=0;i<cglist.size();i++){
									GhJslw jslw=(GhJslw)cglist.get(i);
									jslwService.delete(jslw);
								}
							}
							//
							xmService.delete(fm);
							initWindow();
						}
						}
					}
				});
				c6.appendChild(gn);
				Listcell c7=new Listcell();
				InnerButton IB=new InnerButton();
			    if(fm.getAuditState()==null){
			        c7.setLabel("未审核");
			    }else if(fm.getAuditState()!=null&&fm.getAuditState().shortValue()==GhFmzl.AUDIT_NO){
			    	IB.setLabel("未通过");
			    	c7.appendChild(IB);
			    	IB.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 7);
						 arg.put("id",fm.getFmId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
			    }else if(fm.getAuditState()!=null&&fm.getAuditState().shortValue()==GhFmzl.AUDIT_YES){
			    	IB.setLabel("通过");
			    	c7.appendChild(IB);
			    	IB.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 7);
						 arg.put("id",fm.getFmId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/kyqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
			     }
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5);arg0.appendChild(c6);
				arg0.appendChild(c7);
			}

		});

	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		//获授权发明专利情况
		List list5 = fmzlService.findByKuid(user.getKuId());
		listbox5.setModel(new ListModelList(list5));

	}
	public void onClick$button5(){

		final FmzlqkWindow cgWin = (FmzlqkWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/kyqk/fmzl/fmzlqk.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		cgWin.initWindow();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0)
			throws Exception {
				initWindow();
				cgWin.detach();
			}

		});
	
	}
	 public void onClick$button5out()  throws IOException, WriteException{
			//获授权发明专利情况
			List list5 = fmzlService.findByKuid(user.getKuId());
			 if(list5.size()==0){
				 try {
		 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
		 			} catch (InterruptedException e) {
		 				e.printStackTrace();
		 			}
		 			  return;
			}else{
		    	Date now=new Date();
		    	String fileName = "获授权发明专利.xls";
		    	String Title = "获授权发明专利";
		    	WritableWorkbook workbook;
				List titlelist=new ArrayList();
				titlelist.add("序号");
				titlelist.add("发明专利名称");
				titlelist.add("授权时间");
				titlelist.add("专利授权号");
				titlelist.add("专利列别");
				titlelist.add("专利状态");
				titlelist.add("申请国别");
				titlelist.add("申请号");
				titlelist.add("申请日期");
				titlelist.add("申请公开号");
				titlelist.add("发明人");
				titlelist.add("本人排名");
				titlelist.add("研究方向");
				String c[][]=new String[list5.size()][titlelist.size()];
				//从SQL中读数据
				for(int j=0;j<list5.size();j++){
					GhFmzl fm  =(GhFmzl)list5.get(j);
				    c[j][0]=j+1+"";
					c[j][1]=fm.getFmMc();
				    c[j][2]=fm.getFmSj();
					c[j][3]=fm.getFmSqh();
					if(fm.getFmTypes() == null ||fm.getFmTypes().equals("")){
						c[j][4]="";	
					}else if(fm.getFmTypes().equalsIgnoreCase(GhFmzl.FM_FM.toString())){
						c[j][4]="发明";	
					}else if(fm.getFmTypes().equalsIgnoreCase(GhFmzl.FM_NEW.toString())){
						c[j][4]="实用新型";	
					}else if(fm.getFmTypes().equalsIgnoreCase(GhFmzl.FM_DESIGN.toString())){
						c[j][4]="外观设计";	
					}
					if(fm.getFmStatus().equals(GhFmzl.STA_NO.shortValue())){
						c[j][5]="失效专利";
					}else if(fm.getFmStatus().equals(GhFmzl.STA_YES.shortValue())){
						c[j][5]="有效专利";
					}
					c[j][6]=fm.getFmCountry();
					c[j][7]=fm.getFmRequestno();
					c[j][8]=fm.getFmRequestdate();
					c[j][9]=fm.getFmReqpublino();
					c[j][10]=fm.getFmInventor();
					GhJslw jslw=jslwService.findByKuidAndLwidAndType(user.getKuId(), fm.getFmId(), GhJslw.FMZL);
					if(jslw!=null&&jslw.getLwSelfs()!=null){
						c[j][11]=jslw.getLwSelfs()+"";
					}else{
						c[j][11]="";
					}
					if(jslw!=null&&jslw.getYjfx()!=null){
						c[j][12]=jslw.getYjfx().getGyName();
					}else{
						c[j][12]="";

					}
					
				}
		         ExportExcel ee=new ExportExcel();
				ee.exportExcel(fileName, Title, titlelist, list5.size(), c);
		    	}
		     }

}
