package com.uniwin.asm.personal.ui.data.teacherinfo.jyqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.GhFileService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PkpyService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XmzzService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.YjfxService;
import org.iti.gh.service.ZsService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
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
import com.uniwin.asm.personal.ui.data.teacherinfo.HjjycgWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class JycgWindow extends BaseWindow {

	//获奖情况
	Toolbarbutton button9;
	Listbox listbox9;
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
	PkpyService pkpyService;
	YjfxService yjfxService;
	GhFileService ghfileService;
	WkTUser user;
	JsxmService jsxmService;
	@Override
	public void initShow() {
		initWindow();
		listbox9.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhCg cg = (GhCg) arg1;
				
				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				
				//成果名称
				Listcell c2 = new Listcell();
				if(cg.getKyMc()!=null){
					InnerButton ib = new InnerButton();
					ib.setLabel(cg.getKyMc());
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
							final HjjycgWindow cgWin = (HjjycgWindow) Executions.createComponents
							("/admin/personal/data/teacherinfo/jxqk/hjjycg.zul", null, null);
							cgWin.setCg(cg);
							cgWin.setKuid(user.getKuId());
							//cgWin.doHighlighted();
							cgWin.initWindow();
							cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

								public void onEvent(Event arg0)
								throws Exception {
								   initWindow();
									cgWin.detach();
								}

							});
							cgWin.doModal();
						}
					});
					c2.appendChild(ib);
				}

				//获奖时间
				Listcell c3 = new Listcell();
				if(cg.getKySj()!=null){
					c3.setLabel(cg.getKySj());
				}
				//获奖名称
				Listcell c4 = new Listcell();
				if(cg.getKyHjmc()!=null){
					c4.setLabel(cg.getKyHjmc());
				}
				//获奖等级
				Listcell c5 = new Listcell();
				if(cg.getKyDj()!=null){
					c5.setLabel(cg.getKyDj());
				}
				GhJsxm jx=jsxmService.findByXmidAndKuidAndType(cg.getKyId(), user.getKuId(), GhJsxm.HJJY);
				//本人名次
				Listcell c6 = new Listcell();
				if(cg.getKyGrpm()!=null){
					c6.setLabel(jx.getKyGx()+"/"+cg.getKyZrs());
				}

				//功能
				Listcell c7 = new Listcell();
				InnerButton gn = new InnerButton();
				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(cg.getKuId().intValue()!=user.getKuId().intValue()){
							Messagebox.show("您不是此项目提交人，故不能删除！","提示", Messagebox.OK,Messagebox.EXCLAMATION);
						}else{
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							//删除附件
							List list=ghfileService.findByFxmIdandFType(cg.getKyId(),11);
							if(list.size()>0){
								//存在附件
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							List cglist =jsxmService.findByXmidAndtype(cg.getKyId(), GhJsxm.HJJY);
							if(cglist != null && cglist.size() != 0){
								for(int i=0;i<cglist.size();i++){
									GhJsxm jsxm=(GhJsxm)cglist.get(i);
									jsxmService.delete(jsxm);
								}
							}
							xmService.delete(cg);
							if(cg.getKyXmid()!=null){
								GhXm xm=(GhXm)xmService.get(GhXm.class, cg.getKyXmid());
								if(xm!=null){
									xm.setKyPrize("1");//取消其获奖情况
									xmService.update(xm);
								}
							}
							  initWindow();
						}

						}
					}
				});
				c7.appendChild(gn);
				Listcell c9=new Listcell();
				InnerButton ib = new InnerButton();
				if(cg.getAuditState()==null){
					c9.setLabel("未审核");
				}else if(cg.getAuditState()!=null&&cg.getAuditState().shortValue()==GhXm.AUDIT_NO){
					ib.setLabel("未通过");
					c9.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 11);
						 arg.put("id",cg.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(cg.getAuditState()!=null&&cg.getAuditState().shortValue()==GhXm.AUDIT_YES){
					ib.setLabel("通过");
					c9.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 11);
						 arg.put("id",cg.getKyId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6);
				arg0.appendChild(c7); arg0.appendChild(c9); 
			}
		});

	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		//获奖情况
		List list9 = cgService.findByKuid(user.getKuId(), GhCg.CG_JY,GhJsxm.HJJY);
		listbox9.setModel(new ListModelList(list9));

	}
	public void onClick$button9() throws SuspendNotAllowedException, InterruptedException{

		final HjjycgWindow cgWin = (HjjycgWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/jxqk/hjjycg.zul", null, null);
		cgWin.setCg(null);
		cgWin.setKuid(user.getKuId());
		//cgWin.doHighlighted();
		cgWin.initWindow();
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener(){

			public void onEvent(Event arg0)
			throws Exception {
				initWindow();
				cgWin.detach();
			}

		});
		cgWin.doModal();
		
	}
	public void onClick$button9out()  throws IOException, WriteException{
		//获奖情况
		List list9 = cgService.findByKuid(user.getKuId(),GhCg.CG_JY,GhJsxm.HJJY);
		if(list9 == null || list9.size() == 0){
			 try {
	 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String fileName = "教研获奖成果情况.xls";
			String Title = "教研获奖成果获奖情况";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("序号");
			titlelist.add("项目编号");
			titlelist.add("成果名称");
			titlelist.add("成果来源");
			titlelist.add("获奖时间");
			titlelist.add("获奖名称 ");
			titlelist.add("获奖等级");
			titlelist.add("获奖证书编号");
			titlelist.add("获奖人员");
			titlelist.add("本人名次");
			titlelist.add("颁奖部门");
			
			String c[][]=new String[list9.size()][titlelist.size()];
			//从SQL中读数据
			for(int j=0;j<list9.size();j++){
				GhCg cg   =(GhCg)list9.get(j);
			    c[j][0]=j+1+"";
			    c[j][1]=cg.getKyNumber();
				c[j][2]=cg.getKyMc();
			    c[j][3]=cg.getKyLy();
				c[j][4]=cg.getKySj();
				c[j][5]=cg.getKyHjmc();
				c[j][6]=cg.getKyDj();
				c[j][7]=cg.getKyPrizenum();
				c[j][8]=cg.getKyPrizeper();
				GhJsxm jx=jsxmService.findByXmidAndKuidAndType(cg.getKyId(), user.getKuId(), GhJsxm.HJJY);
				if(jx.getKyGx() != null && cg.getKyZrs() != null){
					c[j][9]=jx.getKyGx()+"/"+cg.getKyZrs();
				}else {
					c[j][9]="";
				}
				
				c[j][10]=cg.getKyPrizedep();
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list9.size(), c);
			
		}
	}
}
