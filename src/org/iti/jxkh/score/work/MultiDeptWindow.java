package org.iti.jxkh.score.work;

import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxgl.entity.JxYear;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.JXKH_MultiDept;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.JXKH_QKLWDept;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_AwardDept;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_FruitDept;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_PatentDept;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.entity.Jxkh_Writing;
import org.iti.jxkh.entity.Jxkh_WritingDept;
import org.iti.jxkh.service.AuditResultService;
import org.iti.xypt.ui.base.BaseWindow;
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

import com.uniwin.framework.entity.WkTUser;

public class MultiDeptWindow extends BaseWindow {
	private static final long serialVersionUID = -2803419586964832849L;
	Listbox hylwlist, qklwlist, zzlist, jllist, splist, xmlist, zllist, cglist, hylist, bglist;
	private AuditResultService auditResultService;
	private String year;
	private YearListbox yearlist;

	public void initShow() {
	//	yearlist.initAnyyearlist();
		yearlist.initYearListbox("");
		hylwlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final JXKH_HYLW hylw = (JXKH_HYLW) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(hylw.getLwName());
				String master = "", other = "";
				for (JXKH_HYLWDept d : hylw.getLwDep()) {
					if (d.getRank().intValue() == 1) {
						master = d.getName();
					} else {
						other += d.getName() + " ";
					}
				}
				Listcell c2 = new Listcell(master);
				Listcell c3 = new Listcell(other);
				Listcell c4 = new Listcell();
				final JXKH_MultiDept md = auditResultService.findMultiDeptByPrIdAndType(hylw.getLwId(), JXKH_MultiDept.HYLW);
				if (md == null) {
					c4.setLabel("未指定");
				} else {
					c4.setLabel(md.getRate());
				}
				Listcell c5 = new Listcell();
				Button btn = new Button("设置");
				btn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AddMultiDeptWindow win = (AddMultiDeptWindow) Executions.createComponents("/admin/jxkh/score/work/multidept/add.zul", null, null);
						win.doHighlighted();
						win.setDlist(hylw.getLwDep());
						win.setType(JXKH_MultiDept.HYLW);
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								JXKH_MultiDept multiDept = new JXKH_MultiDept();
								if (md != null) {
									multiDept = md;
								}
								multiDept.setPrId(hylw.getLwId());
								multiDept.setDepts(win.getStrDept());
								multiDept.setRate(win.getStrRate());
								multiDept.setType(JXKH_MultiDept.HYLW);
								auditResultService.saveOrUpdate(multiDept);
								initWindow();
								win.detach();
							}
						});
					}
				});
				btn.setParent(c5);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		qklwlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final JXKH_QKLW qklw = (JXKH_QKLW) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(qklw.getLwName());
				String master = "", other = "";
				for (JXKH_QKLWDept d : qklw.getLwDep()) {
					if (d.getRank().intValue() == 1) {
						master = d.getName();
					} else {
						other += d.getName() + " ";
					}
				}
				Listcell c2 = new Listcell(master);
				Listcell c3 = new Listcell(other);
				Listcell c4 = new Listcell();
				final JXKH_MultiDept md = auditResultService.findMultiDeptByPrIdAndType(qklw.getLwId(), JXKH_MultiDept.QKLW);
				if (md == null) {
					c4.setLabel("未指定");
				} else {
					c4.setLabel(md.getRate());
				}
				Listcell c5 = new Listcell();
				Button btn = new Button("设置");
				btn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AddMultiDeptWindow win = (AddMultiDeptWindow) Executions.createComponents("/admin/jxkh/score/work/multidept/add.zul", null, null);
						win.doHighlighted();
						win.setDlist(qklw.getLwDep());
						win.setType(JXKH_MultiDept.QKLW);
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								JXKH_MultiDept multiDept = new JXKH_MultiDept();
								if (md != null) {
									multiDept = md;
								}
								multiDept.setPrId(qklw.getLwId());
								multiDept.setDepts(win.getStrDept());
								multiDept.setRate(win.getStrRate());
								multiDept.setType(JXKH_MultiDept.QKLW);
								auditResultService.saveOrUpdate(multiDept);
								initWindow();
								win.detach();
							}
						});
					}
				});
				btn.setParent(c5);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		zzlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Jxkh_Writing zz = (Jxkh_Writing) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(zz.getName());
				String master = "", other = "";
				for (Jxkh_WritingDept d : zz.getWritingDept()) {
					if (d.getRank().intValue() == 1) {
						master = d.getName();
					} else {
						other += d.getName() + " ";
					}
				}
				Listcell c2 = new Listcell(master);
				Listcell c3 = new Listcell(other);
				Listcell c4 = new Listcell();
				final JXKH_MultiDept md = auditResultService.findMultiDeptByPrIdAndType(zz.getId(), JXKH_MultiDept.ZZ);
				if (md == null) {
					c4.setLabel("未指定");
				} else {
					c4.setLabel(md.getRate());
				}
				Listcell c5 = new Listcell();
				Button btn = new Button("设置");
				btn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AddMultiDeptWindow win = (AddMultiDeptWindow) Executions.createComponents("/admin/jxkh/score/work/multidept/add.zul", null, null);
						win.doHighlighted();
						win.setDlist(zz.getWritingDept());
						win.setType(JXKH_MultiDept.ZZ);
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								JXKH_MultiDept multiDept = new JXKH_MultiDept();
								if (md != null) {
									multiDept = md;
								}
								multiDept.setPrId(zz.getId());
								multiDept.setDepts(win.getStrDept());
								multiDept.setRate(win.getStrRate());
								multiDept.setType(JXKH_MultiDept.ZZ);
								auditResultService.saveOrUpdate(multiDept);
								initWindow();
								win.detach();
							}
						});
					}
				});
				btn.setParent(c5);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		jllist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Jxkh_Award jl = (Jxkh_Award) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(jl.getName());
				String master = "", other = "";
				for (Jxkh_AwardDept d : jl.getAwardDept()) {
					if (d.getRank().intValue() == 1) {
						master = d.getName();
					} else {
						other += d.getName() + " ";
					}
				}
				Listcell c2 = new Listcell(master);
				Listcell c3 = new Listcell(other);
				Listcell c4 = new Listcell();
				final JXKH_MultiDept md = auditResultService.findMultiDeptByPrIdAndType(jl.getId(), JXKH_MultiDept.JL);
				if (md == null) {
					c4.setLabel("未指定");
				} else {
					c4.setLabel(md.getRate());
				}
				Listcell c5 = new Listcell();
				Button btn = new Button("设置");
				btn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AddMultiDeptWindow win = (AddMultiDeptWindow) Executions.createComponents("/admin/jxkh/score/work/multidept/add.zul", null, null);
						win.doHighlighted();
						win.setDlist(jl.getAwardDept());
						win.setType(JXKH_MultiDept.JL);
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								JXKH_MultiDept multiDept = new JXKH_MultiDept();
								if (md != null) {
									multiDept = md;
								}
								multiDept.setPrId(jl.getId());
								multiDept.setDepts(win.getStrDept());
								multiDept.setRate(win.getStrRate());
								multiDept.setType(JXKH_MultiDept.JL);
								auditResultService.saveOrUpdate(multiDept);
								initWindow();
								win.detach();
							}
						});
					}
				});
				btn.setParent(c5);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		splist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Jxkh_Video sp = (Jxkh_Video) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(sp.getName());
				String master = "", other = "";
				for (Jxkh_VideoDept d : sp.getVideoDept()) {
					if (d.getRank().intValue() == 1) {
						master = d.getName();
					} else {
						other += d.getName() + " ";
					}
				}
				Listcell c2 = new Listcell(master);
				Listcell c3 = new Listcell(other);
				Listcell c4 = new Listcell();
				final JXKH_MultiDept md = auditResultService.findMultiDeptByPrIdAndType(sp.getId(), JXKH_MultiDept.SP);
				if (md == null) {
					c4.setLabel("未指定");
				} else {
					c4.setLabel(md.getRate());
				}
				Listcell c5 = new Listcell();
				Button btn = new Button("设置");
				btn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AddMultiDeptWindow win = (AddMultiDeptWindow) Executions.createComponents("/admin/jxkh/score/work/multidept/add.zul", null, null);
						win.doHighlighted();
						win.setDlist(sp.getVideoDept());
						win.setType(JXKH_MultiDept.SP);
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								JXKH_MultiDept multiDept = new JXKH_MultiDept();
								if (md != null) {
									multiDept = md;
								}
								multiDept.setPrId(sp.getId());
								multiDept.setDepts(win.getStrDept());
								multiDept.setRate(win.getStrRate());
								multiDept.setType(JXKH_MultiDept.SP);
								auditResultService.saveOrUpdate(multiDept);
								initWindow();
								win.detach();
							}
						});
					}
				});
				btn.setParent(c5);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		xmlist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Jxkh_Project project = (Jxkh_Project) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(project.getName());
				String master = "", other = "";
				for (Jxkh_ProjectDept d : project.getProjectDept()) {
					if (d.getRank().intValue() == 1) {
						master = d.getName();
					} else {
						other += d.getName() + " ";
					}
				}
				Listcell c2 = new Listcell(master);
				Listcell c3 = new Listcell(other);
				Listcell c4 = new Listcell();
				final JXKH_MultiDept md = auditResultService.findMultiDeptByPrIdAndType(project.getId(), JXKH_MultiDept.XM);
				if (md == null) {
					c4.setLabel("未指定");
				} else {
					c4.setLabel(md.getRate());
				}
				Listcell c5 = new Listcell();
				Button btn = new Button("设置");
				btn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AddMultiDeptWindow win = (AddMultiDeptWindow) Executions.createComponents("/admin/jxkh/score/work/multidept/add.zul", null, null);
						win.doHighlighted();
						win.setDlist(project.getProjectDept());
						win.setType(JXKH_MultiDept.XM);
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								JXKH_MultiDept multiDept = new JXKH_MultiDept();
								if (md != null) {
									multiDept = md;
								}
								multiDept.setPrId(project.getId());
								multiDept.setDepts(win.getStrDept());
								multiDept.setRate(win.getStrRate());
								multiDept.setType(JXKH_MultiDept.XM);
								auditResultService.saveOrUpdate(multiDept);
								initWindow();
								win.detach();
							}
						});
					}
				});
				btn.setParent(c5);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		zllist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Jxkh_Patent zl = (Jxkh_Patent) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(zl.getName());
				String master = "", other = "";
				for (Jxkh_PatentDept d : zl.getPatentDept()) {
					if (d.getRank().intValue() == 1) {
						master = d.getName();
					} else {
						other += d.getName() + " ";
					}
				}
				Listcell c2 = new Listcell(master);
				Listcell c3 = new Listcell(other);
				Listcell c4 = new Listcell();
				final JXKH_MultiDept md = auditResultService.findMultiDeptByPrIdAndType(zl.getId(), JXKH_MultiDept.ZL);
				if (md == null) {
					c4.setLabel("未指定");
				} else {
					c4.setLabel(md.getRate());
				}
				Listcell c5 = new Listcell();
				Button btn = new Button("设置");
				btn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AddMultiDeptWindow win = (AddMultiDeptWindow) Executions.createComponents("/admin/jxkh/score/work/multidept/add.zul", null, null);
						win.doHighlighted();
						win.setDlist(zl.getPatentDept());
						win.setType(JXKH_MultiDept.ZL);
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								JXKH_MultiDept multiDept = new JXKH_MultiDept();
								if (md != null) {
									multiDept = md;
								}
								multiDept.setPrId(zl.getId());
								multiDept.setDepts(win.getStrDept());
								multiDept.setRate(win.getStrRate());
								multiDept.setType(JXKH_MultiDept.ZL);
								auditResultService.saveOrUpdate(multiDept);
								initWindow();
								win.detach();
							}
						});
					}
				});
				btn.setParent(c5);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		cglist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Jxkh_Fruit cg = (Jxkh_Fruit) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(cg.getName());
				String master = "", other = "";
				for (Jxkh_FruitDept d : cg.getFruitDept()) {
					if (d.getRank().intValue() == 1) {
						master = d.getName();
					} else {
						other += d.getName() + " ";
					}
				}
				Listcell c2 = new Listcell(master);
				Listcell c3 = new Listcell(other);
				Listcell c4 = new Listcell();
				final JXKH_MultiDept md = auditResultService.findMultiDeptByPrIdAndType(cg.getId(), JXKH_MultiDept.CG);
				if (md == null) {
					c4.setLabel("未指定");
				} else {
					c4.setLabel(md.getRate());
				}
				Listcell c5 = new Listcell();
				Button btn = new Button("设置");
				btn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AddMultiDeptWindow win = (AddMultiDeptWindow) Executions.createComponents("/admin/jxkh/score/work/multidept/add.zul", null, null);
						win.doHighlighted();
						win.setDlist(cg.getFruitDept());
						win.setType(JXKH_MultiDept.CG);
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								JXKH_MultiDept multiDept = new JXKH_MultiDept();
								if (md != null) {
									multiDept = md;
								}
								multiDept.setPrId(cg.getId());
								multiDept.setDepts(win.getStrDept());
								multiDept.setRate(win.getStrRate());
								multiDept.setType(JXKH_MultiDept.CG);
								auditResultService.saveOrUpdate(multiDept);
								initWindow();
								win.detach();
							}
						});
					}
				});
				btn.setParent(c5);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		hylist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final JXKH_MEETING hy = (JXKH_MEETING) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(hy.getMtName());
				String master = "", other = "";
				for (JXKH_MeetingDept d : hy.getMtDep()) {
					if (d.getRank().intValue() == 1) {
						master = d.getName();
					} else {
						other += d.getName() + " ";
					}
				}
				Listcell c2 = new Listcell(master);
				Listcell c3 = new Listcell(other);
				Listcell c4 = new Listcell();
				final JXKH_MultiDept md = auditResultService.findMultiDeptByPrIdAndType(hy.getMtId(), JXKH_MultiDept.HY);
				if (md == null) {
					c4.setLabel("未指定");
				} else {
					c4.setLabel(md.getRate());
				}
				Listcell c5 = new Listcell();
				Button btn = new Button("设置");
				btn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AddMultiDeptWindow win = (AddMultiDeptWindow) Executions.createComponents("/admin/jxkh/score/work/multidept/add.zul", null, null);
						win.doHighlighted();
						win.setDlist(hy.getMtDep());
						win.setType(JXKH_MultiDept.HY);
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								JXKH_MultiDept multiDept = new JXKH_MultiDept();
								if (md != null) {
									multiDept = md;
								}
								multiDept.setPrId(hy.getMtId());
								multiDept.setDepts(win.getStrDept());
								multiDept.setRate(win.getStrRate());
								multiDept.setType(JXKH_MultiDept.HY);
								auditResultService.saveOrUpdate(multiDept);
								initWindow();
								win.detach();
							}
						});
					}
				});
				btn.setParent(c5);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		bglist.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				final Jxkh_Report rp = (Jxkh_Report) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(rp.getName());
				String master = "", other = "";
				for (Jxkh_ReportDept d : rp.getReprotDept()) {
					if (d.getRank().intValue() == 1) {
						master = d.getName();
					} else {
						other += d.getName() + " ";
					}
				}
				Listcell c2 = new Listcell(master);
				Listcell c3 = new Listcell(other);
				Listcell c4 = new Listcell();
				final JXKH_MultiDept md = auditResultService.findMultiDeptByPrIdAndType(rp.getId(), JXKH_MultiDept.BG);
				if (md == null) {
					c4.setLabel("未指定");
				} else {
					c4.setLabel(md.getRate());
				}
				Listcell c5 = new Listcell();
				Button btn = new Button("设置");
				btn.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						final AddMultiDeptWindow win = (AddMultiDeptWindow) Executions.createComponents("/admin/jxkh/score/work/multidept/add.zul", null, null);
						win.doHighlighted();
						win.setDlist(rp.getReprotDept());
						win.setType(JXKH_MultiDept.BG);
						win.initWindow();
						win.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								JXKH_MultiDept multiDept = new JXKH_MultiDept();
								if (md != null) {
									multiDept = md;
								}
								multiDept.setPrId(rp.getId());
								multiDept.setDepts(win.getStrDept());
								multiDept.setRate(win.getStrRate());
								multiDept.setType(JXKH_MultiDept.BG);
								auditResultService.saveOrUpdate(multiDept);
								initWindow();
								win.detach();
							}
						});
					}
				});
				btn.setParent(c5);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		//initWindow();
	}

	public void initWindow() {
		/*JxYear jy = (JxYear)yearlist.getSelectedItem().getValue();
		String year = jy.getYears();*/
		String year = yearlist.getSelectYear();
		WkTUser user= (WkTUser)Sessions.getCurrent().getAttribute("user");
		List<JXKH_HYLW> hylw = auditResultService.findMultiDeptHYLW(year,user.getDept().getKdNumber());
		hylwlist.setModel(new ListModelList(hylw));
		List<JXKH_QKLW> qklw = auditResultService.findMultiDeptQKLW(year,user.getDept().getKdNumber());
		qklwlist.setModel(new ListModelList(qklw));
		List<Jxkh_Writing> zz = auditResultService.findMultiDeptZZ(year,user.getDept().getKdNumber());
		zzlist.setModel(new ListModelList(zz));
		List<Jxkh_Award> jl = auditResultService.findMultiDeptJL(year,user.getDept().getKdNumber());
		jllist.setModel(new ListModelList(jl));
		List<Jxkh_Video> sp = auditResultService.findMultiDeptSP(year,user.getDept().getKdNumber());
		splist.setModel(new ListModelList(sp));
		List<Jxkh_Project> xm = auditResultService.findMultiDeptXM(year,user.getDept().getKdNumber());
		xmlist.setModel(new ListModelList(xm));
		List<Jxkh_Patent> zl = auditResultService.findMultiDeptZL(year,user.getDept().getKdNumber());
		zllist.setModel(new ListModelList(zl));
		List<Jxkh_Fruit> cg = auditResultService.findMultiDeptCG(year,user.getDept().getKdNumber());
		cglist.setModel(new ListModelList(cg));
		List<JXKH_MEETING> hy = auditResultService.findMultiDeptHY(year,user.getDept().getKdNumber());
		hylist.setModel(new ListModelList(hy));
		List<Jxkh_Report> bg = auditResultService.findMultiDeptBG(year,user.getDept().getKdNumber());
		bglist.setModel(new ListModelList(bg));
	}

	public void onClick$compute() {
	}
	public void onSelect$yearlist() {
		initWindow();
	}
}
