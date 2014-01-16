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
import org.iti.gh.entity.GhFile;
import org.iti.gh.entity.GhPkpy;
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
import com.uniwin.asm.personal.ui.data.teacherinfo.PkxyjbgWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class JbgWindow extends BaseWindow {


	//各类评课选优、基本功竞赛
	Toolbarbutton button91;
	Listbox listbox91;
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
		listbox91.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhPkpy pkpy = (GhPkpy) arg1;

				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");
				//赛事名称
				Listcell c2 = new Listcell("");

				if(pkpy.getPkName()!=null){
					InnerButton ib = new InnerButton();
					ib.setLabel(pkpy.getPkName());
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
							final PkxyjbgWindow cgWin = (PkxyjbgWindow) Executions.createComponents
							("/admin/personal/data/teacherinfo/jxqk/pkxyjbg.zul", null, null);
							cgWin.setPkpy(pkpy);
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
					});
					c2.appendChild(ib);
				}

				//获奖级别
				Listcell c3 = new Listcell(pkpy.getPkLevel());
				

				//获奖时间
				Listcell c4 = new Listcell(pkpy.getPkTime());
				

				//颁奖部门
				Listcell c5 = new Listcell(pkpy.getPkDept());
			

				//功能
				Listcell c6 = new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							//删除附件
							List list=ghfileService.findByFxmIdandFType(pkpy.getPkId(),12);
							if(list.size()>0){
								//存在附件
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							xmService.delete(pkpy);
							initWindow();
						}
					}
				});
				c6.appendChild(gn);
				Listcell c7=new Listcell();
				InnerButton ib = new InnerButton();
				if(pkpy.getAuditState()==null){
					c7.setLabel("未审核");
				}else if(pkpy.getAuditState()!=null&&pkpy.getAuditState().shortValue()==GhXm.AUDIT_NO){
					ib.setLabel("未通过");
					c7.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 12);
						 arg.put("id",pkpy.getPkId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(pkpy.getAuditState()!=null&&pkpy.getAuditState().shortValue()==GhXm.AUDIT_YES){
					ib.setLabel("通过");
					c7.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 12);
						 arg.put("id",pkpy.getPkId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}
				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6);
				arg0.appendChild(c7);
				
			}
		});


	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		//各类评课选优、基本功竞赛获奖情况
		List list91 = pkpyService.findByKuid(user.getKuId());
		listbox91.setModel(new ListModelList(list91));

	}
	public void onClick$button91(){
		final PkxyjbgWindow cgWin = (PkxyjbgWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/jxqk/pkxyjbg.zul", null, null);
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
	public void onClick$button91out()  throws IOException, WriteException{
		//各类评课选优,基本功竞赛
		List list91 = pkpyService.findByKuid(user.getKuId());
		if(list91 == null || list91.size() == 0){
			 try {
	 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String fileName = "各类评课选优、基本功竞赛.xls";
			String Title = "各类评课选优、基本功竞赛";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("序号");
			titlelist.add("赛事名称");
			titlelist.add("获奖级别");
			titlelist.add("获奖时间");
			titlelist.add("颁奖部门");
			
			String c[][]=new String[list91.size()][titlelist.size()];
			//从SQL中读数据
			for(int j=0;j<list91.size();j++){
				GhPkpy pkpy   =(GhPkpy)list91.get(j);
			    c[j][0]=j+1+"";
			    c[j][1]=pkpy.getPkName();
				c[j][2]=pkpy.getPkLevel();
			    c[j][3]=pkpy.getPkTime();
				c[j][4]=pkpy.getPkDept();
				
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list91.size(), c);
			
		}
	}
}
