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
import org.iti.gh.entity.GhPx;
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
import com.uniwin.asm.personal.ui.data.teacherinfo.PyxsjsWindow;
import com.uniwin.framework.common.fileuploadex.UploadFJ;
import com.uniwin.framework.entity.WkTUser;

public class ZdxsWindow extends BaseWindow {

	//培养学生参加竞赛情况
	Toolbarbutton button12;
	Listbox listbox12;
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
		listbox12.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhPx px = (GhPx) arg1;

				//序号
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//获奖名称
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(px.getPxMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						
						final PyxsjsWindow cgWin = (PyxsjsWindow) Executions.createComponents
						("/admin/personal/data/teacherinfo/jxqk/pyxsjs.zul", null, null);
						cgWin.setPx(px);
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

				//主办单位
				Listcell c3 = new Listcell(px.getPxDw());

				//获得奖项
				Listcell c4 = new Listcell(px.getPxJx());

				//本人作用
				Listcell c5 = new Listcell();
				if(px.getPxBrzy()!=null){
					c5.setLabel(px.getPxBrzy());
				}

				//功能
				Listcell c6 = new Listcell();
				InnerButton gn = new InnerButton();

				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener(){

					public void onEvent(Event arg0) throws Exception {
						if(Messagebox.show("确定删除吗?", "提示", Messagebox.OK|Messagebox.CANCEL,
								Messagebox.QUESTION)==1){
							//删除附件
							List list=ghfileService.findByFxmIdandFType(px.getPxId(),15);
							if(list.size()>0){
								//存在附件
								for(int i=0;i<list.size();i++){
									UploadFJ ufj=new UploadFJ(false);
									ufj.ReadFJ(getDesktop(), (GhFile) list.get(i));
									ufj.DeleteFJ();
								}
							}
							xmService.delete(px);
							initWindow();
						}
					}
				});
				c6.appendChild(gn);
				Listcell c7=new Listcell();
				InnerButton IB = new InnerButton();
				if(px.getAuditState()==null){
					c7.setLabel("未审核");
				}else if(px.getAuditState()!=null&&px.getAuditState().shortValue()==GhXm.AUDIT_NO){
					ib.setLabel("未通过");
					c7.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 16);
						 arg.put("id",px.getPxId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/jxqk/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(px.getAuditState()!=null&&px.getAuditState().shortValue()==GhXm.AUDIT_YES){
					ib.setLabel("通过");
					c7.appendChild(ib);
					ib.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 16);
						 arg.put("id",px.getPxId());
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
		//培养学生参加竞赛情况
		List list12 = pxService.findByKuid(user.getKuId());
		listbox12.setModel(new ListModelList(list12));

	}
	public void onClick$button12(){
		final PyxsjsWindow cgWin = (PyxsjsWindow) Executions.createComponents
		("/admin/personal/data/teacherinfo/jxqk/pyxsjs.zul", null, null);
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
	public void onClick$button12out()  throws IOException, WriteException{
		//培养学生参加竞赛情况
		List list12 = pxService.findByKuid(user.getKuId());
		if(list12 == null || list12.size() == 0){
			 try {
	 				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			String fileName = "培养学生参加竞赛情况.xls";
			String Title = "培养学生参加竞赛情况";
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("序号");
			titlelist.add("获奖名称");
			titlelist.add("主办单位");
			titlelist.add("获得奖项 ");
			titlelist.add("奖励等级");
			titlelist.add("获奖时间");
			titlelist.add("获奖学生");
			titlelist.add("本人作用");
			String c[][]=new String[list12.size()][titlelist.size()];
			//从SQL中读数据
			for(int j=0;j<list12.size();j++){
				GhPx px = (GhPx)list12.get(j);
			    c[j][0]=j+1+"";
				c[j][1]=px.getPxMc();
			    c[j][2]=px.getPxDw();
				c[j][3]=px.getPxJx();
				c[j][4]=px.getPxDj();
				c[j][5]=px.getPxTime();
			    c[j][6]=px.getPxStu();
				c[j][7]=px.getPxBrzy();
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list12.size(), c);
			
		}
	}


}
