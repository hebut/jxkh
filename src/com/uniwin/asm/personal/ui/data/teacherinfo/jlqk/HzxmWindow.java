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
import org.iti.gh.entity.GhJlhz;
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

import com.uniwin.asm.personal.ui.data.teacherinfo.CdgjhzWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.CkshyjWindow;
import com.uniwin.asm.personal.ui.data.teacherinfo.YqCdgjhzWindow;
import com.uniwin.framework.entity.WkTUser;

public class HzxmWindow extends BaseWindow {

	// 承担国际交流合作项目（已开展）

	Button button21;
	Listbox listbox21;
	// 承担国际交流合作项目（预期开展）

//	Button button22;
//	Listbox listbox22;
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
		listbox21.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhJlhz jl = (GhJlhz) arg1;
				// 序号
				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
				// 国际交流合作项目名称
				Listcell c2 = new Listcell();
				InnerButton ib = new InnerButton();
				ib.setLabel(jl.getHzMc());
				ib.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final CdgjhzWindow cgWin = (CdgjhzWindow) Executions
								.createComponents(
										"/admin/personal/data/teacherinfo/jlqk/hzxm/cdgjhz.zul",
										null, null);
						cgWin.setJl(jl);
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
				// 项目起止时间
				Listcell c3 = new Listcell(jl.getHzKssj());
				Listcell c31 = new Listcell(jl.getHzJssj());
				// 合作对象
				Listcell c4 = new Listcell();
				if (jl.getHzDx() != null) {
					c4.setLabel(jl.getHzDx());
				}

				// 功能
				Listcell c5 = new Listcell();

				InnerButton gn = new InnerButton();
				gn.setLabel("删除");
				gn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						if (Messagebox.show("确定删除吗?", "提示", Messagebox.OK
								| Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
							xmService.delete(jl);
							initWindow();
						}
					}
				});

				c5.appendChild(gn);
				Listcell c6=new Listcell();
				InnerButton IB = new InnerButton();
				if(jl.getAuditState()==null){
					c6.setLabel("未审核");
				}else if(jl.getAuditState()!=null&&jl.getAuditState().shortValue()==GhJlhz.AUDIT_NO){
					IB.setLabel("未通过");
					c6.appendChild(IB);
					IB.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 21);
						 arg.put("id",jl.getHzId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}else if(jl.getAuditState()!=null&&jl.getAuditState().shortValue()==GhJlhz.AUDIT_YES){
					IB.setLabel("通过");
					c6.appendChild(IB);
					IB.addEventListener(Events.ON_CLICK, new EventListener(){

						public void onEvent(Event arg0) throws Exception {
						 Map arg=new HashMap();
						 arg.put("type", 21);
						 arg.put("id",jl.getHzId());
						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
						 cw.initWindow();
						 cw.doHighlighted();
						}
						
					});
				}
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c31);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);

			}
		});
//		listbox22.setItemRenderer(new ListitemRenderer() {
//			public void render(Listitem arg0, Object arg1) throws Exception {
//				final GhJlhz jl = (GhJlhz) arg1;
//				// 序号
//				Listcell c1 = new Listcell(arg0.getIndex() + 1 + "");
//				// 国际交流合作项目名称
//				Listcell c2 = new Listcell();
//				InnerButton ib = new InnerButton();
//				ib.setLabel(jl.getHzMc());
//				ib.addEventListener(Events.ON_CLICK, new EventListener() {
//					public void onEvent(Event arg0) throws Exception {
//						final YqCdgjhzWindow cgWin = (YqCdgjhzWindow) Executions
//								.createComponents(
//										"/admin/personal/data/teacherinfo/jlqk/hzxm/yqcdgjhz.zul",
//										null, null);
//						cgWin.setJl(jl);
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
//				// 项目起止时间
//				Listcell c3 = new Listcell(jl.getHzKssj());
//				Listcell c31 = new Listcell(jl.getHzJssj());
//				// 合作对象
//				Listcell c4 = new Listcell();
//				if (jl.getHzDx() != null) {
//					c4.setLabel(jl.getHzDx());
//				}
//
//				// 功能
//				Listcell c5 = new Listcell();
//
//				InnerButton gn = new InnerButton();
//				gn.setLabel("删除");
//				gn.addEventListener(Events.ON_CLICK, new EventListener() {
//					public void onEvent(Event arg0) throws Exception {
//						if (Messagebox.show("确定删除吗?", "提示", Messagebox.OK
//								| Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
//							xmService.delete(jl);
//							initWindow();
//						}
//					}
//				});
//
//				c5.appendChild(gn);
////				Listcell c6=new Listcell();
////				InnerButton IB = new InnerButton();
////				if(jl.getAuditState()==null){
////					c6.setLabel("未审核");
////				}else if(jl.getAuditState()!=null&&jl.getAuditState().shortValue()==GhJlhz.AUDIT_NO){
////					ib.setLabel("未通过");
////					c6.appendChild(ib);
////					ib.addEventListener(Events.ON_CLICK, new EventListener(){
////
////						public void onEvent(Event arg0) throws Exception {
////						 Map arg=new HashMap();
////						 arg.put("type", 21);
////						 arg.put("id",jl.getHzId());
////						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
////						 cw.initWindow();
////						 cw.doHighlighted();
////						}
////						
////					});
////				}else if(jl.getAuditState()!=null&&jl.getAuditState().shortValue()==GhJlhz.AUDIT_YES){
////					ib.setLabel("通过");
////					c6.appendChild(ib);
////					ib.addEventListener(Events.ON_CLICK, new EventListener(){
////
////						public void onEvent(Event arg0) throws Exception {
////						 Map arg=new HashMap();
////						 arg.put("type", 21);
////						 arg.put("id",jl.getHzId());
////						 CkshyjWindow cw=(CkshyjWindow)Executions.createComponents("/admin/personal/data/teacherinfo/ckpsyj.zul", null, arg);
////						 cw.initWindow();
////						 cw.doHighlighted();
////						}
////						
////					});
////				}
//				arg0.appendChild(c1);
//				arg0.appendChild(c2);
//				arg0.appendChild(c3);
//				arg0.appendChild(c31);
//				arg0.appendChild(c4);
//				arg0.appendChild(c5);
////				arg0.appendChild(c6);
//			}
//		});
	}

	@Override
	public void initWindow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		// 承担国际交流合作项目（已开展）
		List list21 = jlhzService.findByKuid(user.getKuId(), GhJlhz.IFCJ_YES);
		listbox21.setModel(new ListModelList(list21));
		// 承担国际交流合作项目（预期开展）
//		List list22 = jlhzService.findByKuid(user.getKuId(), GhJlhz.IFCJ_NO);
//		listbox22.setModel(new ListModelList(list22));

	}
	public void onClick$button21() {

		final CdgjhzWindow cgWin = (CdgjhzWindow) Executions.createComponents(
				"/admin/personal/data/teacherinfo/jlqk/hzxm/cdgjhz.zul", null, null);
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
	public void onClick$button21out() throws IOException, WriteException {
		// 承担国际交流合作项目（已开展）
		List list21 = jlhzService.findByKuid(user.getKuId(), GhJlhz.IFCJ_YES);
		if (list21 == null || list21.size() == 0) {
			try {
				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "承担国际交流合作项目（已开展））.xls";
			String Title = "承担国际交流合作项目（已开展） ";
			WritableWorkbook workbook;
			List titlelist = new ArrayList();
			titlelist.add("序号");
			titlelist.add("国际交流合作项目名称");
			titlelist.add("项目开始时间");
			titlelist.add("项目结束时间");
			titlelist.add("合作对象");
			titlelist.add("备注");
			String c[][] = new String[list21.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < list21.size(); j++) {
				GhJlhz jl = (GhJlhz) list21.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = jl.getHzMc();
				c[j][2] = jl.getHzKssj();
				c[j][3] = jl.getHzJssj();
				c[j][4] = jl.getHzDx();
				c[j][5] = jl.getHzRemark();

			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list21.size(), c);
		}
	}
	public void onClick$button22() {

		final YqCdgjhzWindow cgWin = (YqCdgjhzWindow) Executions
				.createComponents(
						"/admin/personal/data/teacherinfo/jlqk/hzxm/yqcdgjhz.zul", null,
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
	public void onClick$button22out() throws IOException, WriteException {
		// 承担国际交流合作项目（预期开展）
		List list22 = jlhzService.findByKuid(user.getKuId(), GhJlhz.IFCJ_NO);
		if (list22 == null || list22.size() == 0) {
			try {
				Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = "承担国际交流合作项目（预期开展）.xls";
			String Title = "承担国际交流合作项目（预期开展） ";
			WritableWorkbook workbook;
			List titlelist = new ArrayList();
			titlelist.add("序号");
			titlelist.add("国际交流合作项目名称");
			titlelist.add("项目开始时间");
			titlelist.add("项目结束时间");
			titlelist.add("合作对象");
			titlelist.add("备注");
			String c[][] = new String[list22.size()][titlelist.size()];
			// 从SQL中读数据
			for (int j = 0; j < list22.size(); j++) {
				GhJlhz jl = (GhJlhz) list22.get(j);
				c[j][0] = j + 1 + "";
				c[j][1] = jl.getHzMc();
				c[j][2] = jl.getHzKssj();
				c[j][3] = jl.getHzJssj();
				c[j][4] = jl.getHzDx();
				c[j][5] = jl.getHzRemark();

			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list22.size(), c);
		}
	}
}
