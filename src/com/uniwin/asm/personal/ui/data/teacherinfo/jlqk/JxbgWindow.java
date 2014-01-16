package com.uniwin.asm.personal.ui.data.teacherinfo.jlqk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhJxbg;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.KyjhService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QkdcService;
import org.iti.gh.service.QtService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XshyService;
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

import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.GwjxbgWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.YqGwjxbgWindow;
import com.uniwin.framework.entity.WkTUser;

public class JxbgWindow extends BaseWindow {

	// 国外讲学和报告情况（已进行）

	Button button19;
	Listbox listbox19;

	// 国外讲学和报告情况（预期进行）

//	Button button20;
//	Listbox listbox20;
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
	QkdcService qkdcService;
	CgService cgService;
	LwzlService lwzlService;
	WkTUser user;
	@Override
	public void initShow() {
		initWindow();
		listbox19.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhJxbg bg = (GhJxbg) arg1;
				// 序号
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// 讲学或报告人姓名
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(bg.getJxBgmc());
				ib.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final GwjxbgWindow cgWin = (GwjxbgWindow) Executions
								.createComponents(
										"/admin/personal/data/teacherinfo/jlqk/jxbg/gwjxbg.zul",
										null, null);
						cgWin.setBg(bg);
						cgWin.doHighlighted();
						cgWin.initWindow();
						cgWin.addEventListener(Events.ON_CHANGE,
								new EventListener() {
									public void onEvent(Event arg0)
											throws Exception {
										initWindow();
										cgWin.detach();
									}
								});
					}
				});
				c2.appendChild(ib);
				// 国外大学名称或国际会议名称
				Listcell c3 = new Listcell(bg.getJxHymc());

				// 讲学或报告名称
				Listcell c4 = new Listcell();
				if (bg.getJxJxmc() != null) {
					c4.setLabel(bg.getJxJxmc());
				}
				// 报告举办时间
				Listcell c5 = new Listcell(bg.getJxSj());
				// 功能
				Listcell c6 = new Listcell();

				InnerButton gn = new InnerButton();
				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (Messagebox.show("确定删除吗?", "提示", Messagebox.OK
								| Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							xmService.delete(bg);
							initWindow();
						}
					}
				});
			
				c6.appendChild(gn);
				Listcell c7=new Listcell();
				InnerButton IB = new InnerButton();
				if(bg.getAuditState()==null){
					c7.setLabel("未审核");
				}else if(bg.getAuditState()!=null&&bg.getAuditState().shortValue()==GhJxbg.AUDIT_NO){
					IB.setLabel("未通过");
					c7.appendChild(IB);
					IB.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 20);
						 arg.put("id",bg.getJxId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(bg.getAuditState()!=null&&bg.getAuditState().shortValue()==GhJxbg.AUDIT_YES){
					IB.setLabel("通过");
					c7.appendChild(IB);
					IB.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 20);
						 arg.put("id",bg.getJxId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);
			}
		});
//		listbox20.setItemRenderer(new ListitemRenderer() {
//			public void render(Listitem arg0, Object arg1) throws Exception {
//				final GhJxbg bg = (GhJxbg) arg1;
//				// 序号
//				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
//				// 讲学或报告人姓名
//				Listcell c2 = new Listcell();
//				InnerButton ib = new InnerButton();
//				ib.setLabel(bg.getJxBgmc());
//				ib.addEventListener(Events.ON_CLICK, new EventListener() {
//					public void onEvent(Event arg0) throws Exception {
//						final YqGwjxbgWindow cgWin = (YqGwjxbgWindow) Executions
//								.createComponents(
//										"/admin/personal/data/teacherinfo/jlqk/jxbg/yqgwjxbg.zul",
//										null, null);
//						cgWin.setBg(bg);
//						cgWin.doHighlighted();
//						cgWin.initWindow();
//						cgWin.addEventListener(Events.ON_CHANGE,
//								new EventListener() {
//									public void onEvent(Event arg0)
//											throws Exception {
//										initWindow();
//										cgWin.detach();
//									}
//								});
//					}
//				});
//				c2.appendChild(ib);
//				// 国外大学名称或国际会议名称
//				Listcell c3 = new Listcell(bg.getJxHymc());
//
//				// 讲学或报告名称
//				Listcell c4 = new Listcell();
//				if (bg.getJxJxmc() != null) {
//					c4.setLabel(bg.getJxJxmc());
//				}
//
//				// 报告举办时间
//				Listcell c5 = new Listcell(bg.getJxSj());
//
//				// 功能
//				Listcell c6 = new Listcell();
//
//				InnerButton gn = new InnerButton();
//				gn.setLabel("删除");
//				gn.addEventListener(Events.ON_CLICK, new EventListener() {
//					public void onEvent(Event arg0) throws Exception {
//						if (Messagebox.show("确定删除吗?", "提示", Messagebox.OK
//								| Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
//							xmService.delete(bg);
//							initWindow();
//						}
//					}
//				});
////				Listcell c7=new Listcell();
////				InnerButton IB = new InnerButton();
////				if(bg.getAuditState()==null){
////					c7.setLabel("未审核");
////				}else if(bg.getAuditState()!=null&&bg.getAuditState().shortValue()==GhJxbg.AUDIT_NO){
////					ib.setLabel("未通过");
////					c7.appendChild(ib);
////					ib.addEventListener(Events.ON_CLICK, new EventListener(){
////
////						public void onEvent(Event arg0) throws Exception {
////						 Map arg=new HashMap();
////						 arg.put("type", 20);
////						 arg.put("id",bg.getJxId());
////						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
////						 cw.initWindow();
////						 cw.doHighlighted();
////						}
////						
////					});
////				}else if(bg.getAuditState()!=null&&bg.getAuditState().shortValue()==GhJxbg.AUDIT_YES){
////					ib.setLabel("通过");
////					c7.appendChild(ib);
////					ib.addEventListener(Events.ON_CLICK, new EventListener(){
////
////						public void onEvent(Event arg0) throws Exception {
////						 Map arg=new HashMap();
////						 arg.put("type", 20);
////						 arg.put("id",bg.getJxId());
////						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
////						 cw.initWindow();
////						 cw.doHighlighted();
////						}
////						
////					});
////				}
//				c6.appendChild(gn);
//				arg0.appendChild(c1);
//				arg0.appendChild(c2);
//				arg0.appendChild(c3);
//				arg0.appendChild(c4);
//				arg0.appendChild(c5);
//				arg0.appendChild(c6);
////				arg0.appendChild(c7);
//			}
//		});
	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		// 国外讲学和报告情况（已进行）
		List list19 = jxbgService.findByKuidAndCj(user.getKuId(),GhJxbg.IFCJ_YES);
		listbox19.setModel(new ListModelList(list19));
		// 国外讲学和报告情况（预期进行）
//		List list20 = jxbgService.findByKuidAndCj(user.getKuId(),GhJxbg.IFCJ_NO);
//		listbox20.setModel(new ListModelList(list20));

	}
	public void onClick$button19() {

		final GwjxbgWindow cgWin = (GwjxbgWindow) Executions.createComponents(
				"/admin/personal/data/teacherinfo/jlqk/jxbg/gwjxbg.zul", null, null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				initWindow();
				cgWin.detach();
			}
		});
		cgWin.initWindow();
	}
	public void onClick$button19out() throws IOException, WriteException {
		// 国外讲学和报告情况（已进行）
		List list19 = jxbgService.findByKuidAndCj(user.getKuId(),
				GhJxbg.IFCJ_YES);
		if (list19 == null || list19.size() == 0) {
			try {
				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "国外讲学和报告情况（已进行）.xls";
			String Title = "国外讲学和报告情况（已进行） ";
			WritableWorkbook workbook;
			List titlelist = new ArrayList();
			titlelist.add("序号");
			titlelist.add("讲学或报告名称");
			titlelist.add("国外大学名称或国际会议名称");
			titlelist.add("讲学或报告人姓名");
			titlelist.add("报告主题");
			titlelist.add("报告举办时间");
			titlelist.add("报告举办地点");
			titlelist.add("备注");
			String c[][] = new String[list19.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < list19.size(); j++) {
				GhJxbg bg = (GhJxbg) list19.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = bg.getJxBgmc();
				c[j][2] = bg.getJxHymc();
				c[j][3] = bg.getJxJxmc();
				c[j][4] = bg.getJxSubject();
				c[j][5] = bg.getJxSj();
				c[j][6] = bg.getJxPlace();
				c[j][7] = bg.getJxRemarks();

			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list19.size(), c);
		}
	}
	public void onClick$button20() {

		final YqGwjxbgWindow cgWin = (YqGwjxbgWindow) Executions.createComponents("/admin/personal/data/teacherinfo/jlqk/jxbg/yqgwjxbg.zul", null,
						null);
		cgWin.setKuid(user.getKuId());
		cgWin.doHighlighted();
		
		cgWin.addEventListener(Events.ON_CHANGE, new EventListener() {

			public void onEvent(Event arg0) throws Exception {
				initWindow();
				cgWin.detach();
			}
		});
		cgWin.initWindow();
	}
	public void onClick$button20out() throws IOException, WriteException {
		// 国外讲学和报告情况（预期进行）
		List list20 = jxbgService.findByKuidAndCj(user.getKuId(),
				GhJxbg.IFCJ_NO);
		if (list20 == null || list20.size() == 0) {
			try {
				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "国外讲学和报告情况（预期进行）.xls";
			String Title = "国外讲学和报告情况（预期进行） ";
			WritableWorkbook workbook;
			List titlelist = new ArrayList();
			titlelist.add("序号");
			titlelist.add("讲学或报告名称");
			titlelist.add("国外大学名称或国际会议名称");
			titlelist.add("讲学或报告人姓名");
			titlelist.add("报告主题");
			titlelist.add("报告举办时间");
			titlelist.add("报告举办地点");
			titlelist.add("备注");
			String c[][] = new String[list20.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < list20.size(); j++) {
				GhJxbg bg = (GhJxbg) list20.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = bg.getJxBgmc();
				c[j][2] = bg.getJxHymc();
				c[j][3] = bg.getJxJxmc();
				c[j][4] = bg.getJxSubject();
				c[j][5] = bg.getJxSj();
				c[j][6] = bg.getJxPlace();
				c[j][7] = bg.getJxRemarks();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list20.size(), c);
		}
	}
}
