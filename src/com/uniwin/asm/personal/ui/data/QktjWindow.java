package com.uniwin.asm.personal.ui.data;

import java.util.List;
import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhQkdc;
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
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.uniwin.framework.entity.WkTUser;

public class QktjWindow extends BaseWindow {
	WkTUser user;
	Row row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11, row12, row13, row14, row15, row16, row17;
	Hbox dcbutton;
	Button dcsubmit, dcreset;
	// 第一列
	Textbox dctextbox1, dctextbox2, dctextbox3, dctextbox4, dctextbox5, dctextbox6, dctextbox7, dctextbox8, dctextbox9, dctextbox10, dctextbox11, dctextbox12, dctextbox13, dctextbox14, dctextbox15, dctextbox16, dctextbox17;
	// 第二列
	Textbox dctextbox18, dctextbox19, dctextbox20, dctextbox21, dctextbox22, dctextbox23, dctextbox24, dctextbox25, dctextbox26, dctextbox27, dctextbox28, dctextbox29, dctextbox30, dctextbox31, dctextbox32, dctextbox33, dctextbox34;
	// 第三列
	Textbox dctextbox35, dctextbox36, dctextbox37, dctextbox38, dctextbox39, dctextbox40, dctextbox41, dctextbox42, dctextbox43, dctextbox44, dctextbox45, dctextbox46, dctextbox47, dctextbox48, dctextbox49, dctextbox50, dctextbox51;
	// 第四列
	Textbox dctextbox52, dctextbox53, dctextbox54, dctextbox55, dctextbox56, dctextbox57, dctextbox58, dctextbox59, dctextbox60, dctextbox61, dctextbox62, dctextbox63, dctextbox64, dctextbox65, dctextbox66, dctextbox67, dctextbox68;
	// 第五列
	Textbox dctextbox69, dctextbox70, dctextbox71, dctextbox72, dctextbox73, dctextbox74, dctextbox75, dctextbox76, dctextbox77, dctextbox78, dctextbox79, dctextbox80, dctextbox81, dctextbox82, dctextbox83, dctextbox84, dctextbox85;
	// 第六列
	Textbox dctextbox86, dctextbox87, dctextbox88, dctextbox89, dctextbox90, dctextbox91, dctextbox92, dctextbox93, dctextbox94, dctextbox95, dctextbox96, dctextbox97, dctextbox98, dctextbox99, dctextbox100, dctextbox101, dctextbox102;
	// 第七列
	Textbox dctextbox103, dctextbox104, dctextbox105, dctextbox106, dctextbox107, dctextbox108, dctextbox109, dctextbox110, dctextbox111, dctextbox112, dctextbox113, dctextbox114, dctextbox115, dctextbox116, dctextbox117, dctextbox118, dctextbox119;
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

	@Override
	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		initQkdc();
	}

	@Override
	public void initWindow() {
	}

	public void initQkdc() {
		// 首先清除所有的子节点
		row1.getChildren().clear();
		row2.getChildren().clear();
		row3.getChildren().clear();
		row4.getChildren().clear();
		row5.getChildren().clear();
		row6.getChildren().clear();
		row7.getChildren().clear();
		row8.getChildren().clear();
		row9.getChildren().clear();
		row10.getChildren().clear();
		row11.getChildren().clear();
		row12.getChildren().clear();
		row13.getChildren().clear();
		row14.getChildren().clear();
		row15.getChildren().clear();
		row16.getChildren().clear();
		row17.getChildren().clear();
		List dclist = qkdcService.findByKuid(user.getKuId());
		if (dclist.size() > 0) {// 这个教师已经填写过规划情况调查表
			GhQkdc dc = null;
			for (int i = 1; i <= 8; i++) {
				if (i == 1) {
					row1.appendChild(new Label("国家级（类别/项数）例：3/1"));
					row2.appendChild(new Label("省部级（类别/项数）例：3/1"));
					row3.appendChild(new Label("横向（项数）例：3"));
					row4.appendChild(new Label("其他（项数）例：2"));
					row5.appendChild(new Label("论文 （SCI/EI/ISTP/核心）例：1/1/2/3"));
					row6.appendChild(new Label("著作/专利 例：0/1"));
					row7.appendChild(new Label("国家级（等级/项数）例：一/2"));
					row8.appendChild(new Label("省部级（等级/项数）例：二/4"));
					row9.appendChild(new Label("其他（项数）例：4"));
					row10.appendChild(new Label("国际会议（主办/承办/参加）例：0/0/2"));
					row11.appendChild(new Label("国内会议（主办/承办/参加）例：0/0/2"));
					row12.appendChild(new Label("互访（次数/人数）例：2/30"));
					row13.appendChild(new Label("其他 例：3"));
					row14.appendChild(new Label("国家级奖励（等级/数量）例：一/2"));
					row15.appendChild(new Label("省部级奖励（等级/数量）例：三/2"));
					row16.appendChild(new Label("出版教材（数量）例：2"));
					row17.appendChild(new Label("其他 例：1"));
					continue;
				}
				if (i == 2) {
					List list = qkdcService.findByKuid(user.getKuId(), GhQkdc.BEFORE10);
					if (list.size() > 0) {
						dc = (GhQkdc) list.get(0);
					}
				}
				if (i == 3) {
					List list = qkdcService.findByKuid(user.getKuId(), GhQkdc.BETWEEN11TO15);
					if (list.size() > 0) {
						dc = (GhQkdc) list.get(0);
					}
				}
				if (i == 4) {
					List list = qkdcService.findByKuid(user.getKuId(), GhQkdc.AT2011);
					if (list.size() > 0) {
						dc = (GhQkdc) list.get(0);
					}
				}
				if (i == 5) {
					List list = qkdcService.findByKuid(user.getKuId(), GhQkdc.AT2012);
					if (list.size() > 0) {
						dc = (GhQkdc) list.get(0);
					}
				}
				if (i == 6) {
					List list = qkdcService.findByKuid(user.getKuId(), GhQkdc.AT2013);
					if (list.size() > 0) {
						dc = (GhQkdc) list.get(0);
					}
				}
				if (i == 7) {
					List list = qkdcService.findByKuid(user.getKuId(), GhQkdc.AT2014);
					if (list.size() > 0) {
						dc = (GhQkdc) list.get(0);
					}
				}
				if (i == 8) {
					List list = qkdcService.findByKuid(user.getKuId(), GhQkdc.AT2015);
					if (list.size() > 0) {
						dc = (GhQkdc) list.get(0);
					}
				}
				InnerButton ib1 = new InnerButton();
				InnerButton ib2 = new InnerButton();
				InnerButton ib3 = new InnerButton();
				InnerButton ib4 = new InnerButton();
				InnerButton ib5 = new InnerButton();
				InnerButton ib6 = new InnerButton();
				InnerButton ib7 = new InnerButton();
				InnerButton ib8 = new InnerButton();
				InnerButton ib9 = new InnerButton();
				InnerButton ib10 = new InnerButton();
				InnerButton ib11 = new InnerButton();
				InnerButton ib12 = new InnerButton();
				InnerButton ib13 = new InnerButton();
				InnerButton ib14 = new InnerButton();
				InnerButton ib15 = new InnerButton();
				InnerButton ib16 = new InnerButton();
				InnerButton ib17 = new InnerButton();
				final int s = i;
				if (dc != null) {
					if (dc.getKyxmGj() != null || dc.getKyxmGj().length() > 0) {
						ib1.setLabel(dc.getKyxmGj());
						ib1.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "国家级项目（类别/项数）", 1);
							}
						});
					} else {
						ib1.setLabel("添加");
						ib1.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "国家级项目（类别/项数）", 1);
							}
						});
					}
					if (dc.getKyxmSb() != null || dc.getKyxmSb().length() > 0) {
						ib2.setLabel(dc.getKyxmSb());
						ib2.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "省部级项目（类别/项数）", 2);
							}
						});
					} else {
						ib2.setLabel("添加");
						ib2.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "省部级项目（类别/项数）", 2);
							}
						});
					}
					if (dc.getKyxmHx() != null || dc.getKyxmHx().length() > 0) {
						ib3.setLabel(dc.getKyxmHx());
						ib3.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "横向项目（项数）", 3);
							}
						});
					} else {
						ib3.setLabel("添加");
						ib3.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "横向项目（项数）", 3);
							}
						});
					}
					if (dc.getKyxmQt() != null || dc.getKyxmQt().length() > 0) {
						ib4.setLabel(dc.getKyxmQt());
						ib4.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "其他（项数）", 4);
							}
						});
					} else {
						ib4.setLabel("添加");
						ib4.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "其他（项数）", 4);
							}
						});
					}
					if (dc.getKycgLw() != null || dc.getKycgLw().length() > 0) {
						ib5.setLabel(dc.getKycgLw());
						ib5.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "论文 （SCI/EI/ISTP/核心）（篇数）", 5);
							}
						});
					} else {
						ib5.setLabel("添加");
						ib5.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "论文 （SCI/EI/ISTP/核心）（篇数）", 5);
							}
						});
					}
					if (dc.getKycgZl() != null || dc.getKycgZl().length() > 0) {
						ib6.setLabel(dc.getKycgZl());
						ib6.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "著作/专利", 6);
							}
						});
					} else {
						ib6.setLabel("添加");
						ib6.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "著作/专利", 6);
							}
						});
					}
					if (dc.getKyjlGj() != null || dc.getKyjlGj().length() > 0) {
						ib7.setLabel(dc.getKyjlGj());
						ib7.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "国家级奖励（等级/项数）", 7);
							}
						});
					} else {
						ib7.setLabel("添加");
						ib7.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "国家级奖励（等级/项数）", 7);
							}
						});
					}
					if (dc.getKyjlSb() != null || dc.getKyjlSb().length() > 0) {
						ib8.setLabel(dc.getKyjlSb());
						ib8.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "省部级奖励（等级/项数）", 8);
							}
						});
					} else {
						ib8.setLabel("添加");
						ib8.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "省部级奖励（等级/项数）", 8);
							}
						});
					}
					if (dc.getKyjlQt() != null || dc.getKyjlQt().length() > 0) {
						ib9.setLabel(dc.getKyjlQt());
						ib9.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "其他（项数）", 9);
							}
						});
					} else {
						ib9.setLabel("添加");
						ib9.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "其他（项数）", 9);
							}
						});
					}
					if (dc.getKyhzGj() != null || dc.getKyhzGj().length() > 0) {
						ib10.setLabel(dc.getKyhzGj());
						ib10.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "国际会议（主办/承办/参加）", 10);
							}
						});
					} else {
						ib10.setLabel("添加");
						ib10.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "国际会议（主办/承办/参加）", 10);
							}
						});
					}
					if (dc.getKyhzGn() != null || dc.getKyhzGn().length() > 0) {
						ib11.setLabel(dc.getKyhzGn());
						ib11.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "国内会议（主办/承办/参加）", 11);
							}
						});
					} else {
						ib11.setLabel("添加");
						ib11.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "国内会议（主办/承办/参加）", 11);
							}
						});
					}
					if (dc.getKyhzHf() != null || dc.getKyhzHf().length() > 0) {
						ib12.setLabel(dc.getKyhzHf());
						ib12.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "互访（次数/人数）", 12);
							}
						});
					} else {
						ib12.setLabel("添加");
						ib12.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "互访（次数/人数）", 12);
							}
						});
					}
					if (dc.getKyhzQt() != null || dc.getKyhzQt().length() > 0) {
						ib13.setLabel(dc.getKyhzQt());
						ib13.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "其他", 13);
							}
						});
					} else {
						ib13.setLabel("添加");
						ib13.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "其他", 13);
							}
						});
					}
					if (dc.getJxcgGj() != null || dc.getJxcgGj().length() > 0) {
						ib14.setLabel(dc.getJxcgGj());
						ib14.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "教学成果国家级奖励（等级/数量）", 14);
							}
						});
					} else {
						ib14.setLabel("添加");
						ib14.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "教学成果国家级奖励（等级/数量）", 14);
							}
						});
					}
					if (dc.getJxcgSb() != null || dc.getJxcgSb().length() > 0) {
						ib15.setLabel(dc.getJxcgSb());
						ib15.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "教学成果省部级奖励（等级/数量）", 15);
							}
						});
					} else {
						ib15.setLabel("添加");
						ib15.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "教学成果省部级奖励（等级/数量）", 15);
							}
						});
					}
					if (dc.getJxcgCb() != null || dc.getJxcgCb().length() > 0) {
						ib16.setLabel(dc.getJxcgCb());
						ib16.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "出版教材（数量）", 16);
							}
						});
					} else {
						ib16.setLabel("添加");
						ib16.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "出版教材（数量）", 16);
							}
						});
					}
					if (dc.getJxcgQt() != null || dc.getJxcgQt().length() > 0) {
						ib17.setLabel(dc.getJxcgQt());
						ib17.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "其他", 17);
							}
						});
					} else {
						ib17.setLabel("添加");
						ib17.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								editWin(s, "其他", 17);
							}
						});
					}
					row1.appendChild(ib1);
					row2.appendChild(ib2);
					row3.appendChild(ib3);
					row4.appendChild(ib4);
					row5.appendChild(ib5);
					row6.appendChild(ib6);
					row7.appendChild(ib7);
					row8.appendChild(ib8);
					row9.appendChild(ib9);
					row10.appendChild(ib10);
					row11.appendChild(ib11);
					row12.appendChild(ib12);
					row13.appendChild(ib13);
					row14.appendChild(ib14);
					row15.appendChild(ib15);
					row16.appendChild(ib16);
					row17.appendChild(ib17);
				}
			}
		} else {// 这个教师还没有填写，那么全部都是Textbox
			dcbutton.setVisible(true);
			dctextbox1 = new Textbox();
			dctextbox2 = new Textbox();
			dctextbox3 = new Textbox();
			dctextbox4 = new Textbox();
			dctextbox5 = new Textbox();
			dctextbox6 = new Textbox();
			dctextbox7 = new Textbox();
			dctextbox8 = new Textbox();
			dctextbox9 = new Textbox();
			dctextbox10 = new Textbox();
			dctextbox11 = new Textbox();
			dctextbox12 = new Textbox();
			dctextbox13 = new Textbox();
			dctextbox14 = new Textbox();
			dctextbox15 = new Textbox();
			dctextbox16 = new Textbox();
			dctextbox17 = new Textbox();
			dctextbox18 = new Textbox();
			dctextbox19 = new Textbox();
			dctextbox20 = new Textbox();
			dctextbox21 = new Textbox();
			dctextbox22 = new Textbox();
			dctextbox23 = new Textbox();
			dctextbox24 = new Textbox();
			dctextbox25 = new Textbox();
			dctextbox26 = new Textbox();
			dctextbox27 = new Textbox();
			dctextbox28 = new Textbox();
			dctextbox29 = new Textbox();
			dctextbox30 = new Textbox();
			dctextbox31 = new Textbox();
			dctextbox32 = new Textbox();
			dctextbox33 = new Textbox();
			dctextbox34 = new Textbox();
			dctextbox35 = new Textbox();
			dctextbox36 = new Textbox();
			dctextbox37 = new Textbox();
			dctextbox38 = new Textbox();
			dctextbox39 = new Textbox();
			dctextbox40 = new Textbox();
			dctextbox41 = new Textbox();
			dctextbox42 = new Textbox();
			dctextbox43 = new Textbox();
			dctextbox44 = new Textbox();
			dctextbox45 = new Textbox();
			dctextbox46 = new Textbox();
			dctextbox47 = new Textbox();
			dctextbox48 = new Textbox();
			dctextbox49 = new Textbox();
			dctextbox50 = new Textbox();
			dctextbox51 = new Textbox();
			dctextbox52 = new Textbox();
			dctextbox53 = new Textbox();
			dctextbox54 = new Textbox();
			dctextbox55 = new Textbox();
			dctextbox56 = new Textbox();
			dctextbox57 = new Textbox();
			dctextbox58 = new Textbox();
			dctextbox59 = new Textbox();
			dctextbox60 = new Textbox();
			dctextbox61 = new Textbox();
			dctextbox62 = new Textbox();
			dctextbox63 = new Textbox();
			dctextbox64 = new Textbox();
			dctextbox65 = new Textbox();
			dctextbox66 = new Textbox();
			dctextbox67 = new Textbox();
			dctextbox68 = new Textbox();
			dctextbox69 = new Textbox();
			dctextbox70 = new Textbox();
			dctextbox71 = new Textbox();
			dctextbox72 = new Textbox();
			dctextbox73 = new Textbox();
			dctextbox74 = new Textbox();
			dctextbox75 = new Textbox();
			dctextbox76 = new Textbox();
			dctextbox77 = new Textbox();
			dctextbox78 = new Textbox();
			dctextbox79 = new Textbox();
			dctextbox80 = new Textbox();
			dctextbox81 = new Textbox();
			dctextbox82 = new Textbox();
			dctextbox83 = new Textbox();
			dctextbox84 = new Textbox();
			dctextbox85 = new Textbox();
			dctextbox86 = new Textbox();
			dctextbox87 = new Textbox();
			dctextbox88 = new Textbox();
			dctextbox89 = new Textbox();
			dctextbox90 = new Textbox();
			dctextbox91 = new Textbox();
			dctextbox92 = new Textbox();
			dctextbox93 = new Textbox();
			dctextbox94 = new Textbox();
			dctextbox95 = new Textbox();
			dctextbox96 = new Textbox();
			dctextbox97 = new Textbox();
			dctextbox98 = new Textbox();
			dctextbox99 = new Textbox();
			dctextbox100 = new Textbox();
			dctextbox101 = new Textbox();
			dctextbox102 = new Textbox();
			dctextbox103 = new Textbox();
			dctextbox104 = new Textbox();
			dctextbox105 = new Textbox();
			dctextbox106 = new Textbox();
			dctextbox107 = new Textbox();
			dctextbox108 = new Textbox();
			dctextbox109 = new Textbox();
			dctextbox110 = new Textbox();
			dctextbox111 = new Textbox();
			dctextbox112 = new Textbox();
			dctextbox113 = new Textbox();
			dctextbox114 = new Textbox();
			dctextbox115 = new Textbox();
			dctextbox116 = new Textbox();
			dctextbox117 = new Textbox();
			dctextbox118 = new Textbox();
			dctextbox119 = new Textbox();
			// 格式设定
			dctextbox1.setWidth("70px");
			dctextbox2.setWidth("70px");
			dctextbox3.setWidth("70px");
			dctextbox4.setWidth("70px");
			dctextbox5.setWidth("70px");
			dctextbox6.setWidth("70px");
			dctextbox7.setWidth("70px");
			dctextbox8.setWidth("70px");
			dctextbox9.setWidth("70px");
			dctextbox10.setWidth("70px");
			dctextbox11.setWidth("70px");
			dctextbox12.setWidth("70px");
			dctextbox13.setWidth("70px");
			dctextbox14.setWidth("70px");
			dctextbox15.setWidth("70px");
			dctextbox16.setWidth("70px");
			dctextbox17.setWidth("70px");
			dctextbox18.setWidth("70px");
			dctextbox19.setWidth("70px");
			dctextbox20.setWidth("70px");
			dctextbox21.setWidth("70px");
			dctextbox22.setWidth("70px");
			dctextbox23.setWidth("70px");
			dctextbox24.setWidth("70px");
			dctextbox25.setWidth("70px");
			dctextbox26.setWidth("70px");
			dctextbox27.setWidth("70px");
			dctextbox28.setWidth("70px");
			dctextbox29.setWidth("70px");
			dctextbox30.setWidth("70px");
			dctextbox31.setWidth("70px");
			dctextbox32.setWidth("70px");
			dctextbox33.setWidth("70px");
			dctextbox34.setWidth("70px");
			dctextbox35.setWidth("70px");
			dctextbox36.setWidth("70px");
			dctextbox37.setWidth("70px");
			dctextbox38.setWidth("70px");
			dctextbox39.setWidth("70px");
			dctextbox40.setWidth("70px");
			dctextbox41.setWidth("70px");
			dctextbox42.setWidth("70px");
			dctextbox43.setWidth("70px");
			dctextbox44.setWidth("70px");
			dctextbox45.setWidth("70px");
			dctextbox46.setWidth("70px");
			dctextbox47.setWidth("70px");
			dctextbox48.setWidth("70px");
			dctextbox49.setWidth("70px");
			dctextbox50.setWidth("70px");
			dctextbox51.setWidth("70px");
			dctextbox52.setWidth("70px");
			dctextbox53.setWidth("70px");
			dctextbox54.setWidth("70px");
			dctextbox55.setWidth("70px");
			dctextbox56.setWidth("70px");
			dctextbox57.setWidth("70px");
			dctextbox58.setWidth("70px");
			dctextbox59.setWidth("70px");
			dctextbox60.setWidth("70px");
			dctextbox61.setWidth("70px");
			dctextbox62.setWidth("70px");
			dctextbox63.setWidth("70px");
			dctextbox64.setWidth("70px");
			dctextbox65.setWidth("70px");
			dctextbox66.setWidth("70px");
			dctextbox67.setWidth("70px");
			dctextbox68.setWidth("70px");
			dctextbox69.setWidth("70px");
			dctextbox70.setWidth("70px");
			dctextbox71.setWidth("70px");
			dctextbox72.setWidth("70px");
			dctextbox73.setWidth("70px");
			dctextbox74.setWidth("70px");
			dctextbox75.setWidth("70px");
			dctextbox76.setWidth("70px");
			dctextbox77.setWidth("70px");
			dctextbox78.setWidth("70px");
			dctextbox79.setWidth("70px");
			dctextbox80.setWidth("70px");
			dctextbox81.setWidth("70px");
			dctextbox82.setWidth("70px");
			dctextbox83.setWidth("70px");
			dctextbox84.setWidth("70px");
			dctextbox85.setWidth("70px");
			dctextbox86.setWidth("70px");
			dctextbox87.setWidth("70px");
			dctextbox88.setWidth("70px");
			dctextbox89.setWidth("70px");
			dctextbox90.setWidth("70px");
			dctextbox91.setWidth("70px");
			dctextbox92.setWidth("70px");
			dctextbox93.setWidth("70px");
			dctextbox94.setWidth("70px");
			dctextbox95.setWidth("70px");
			dctextbox96.setWidth("70px");
			dctextbox97.setWidth("70px");
			dctextbox98.setWidth("70px");
			dctextbox99.setWidth("70px");
			dctextbox100.setWidth("70px");
			dctextbox101.setWidth("70px");
			dctextbox102.setWidth("70px");
			dctextbox103.setWidth("70px");
			dctextbox104.setWidth("70px");
			dctextbox105.setWidth("70px");
			dctextbox106.setWidth("70px");
			dctextbox107.setWidth("70px");
			dctextbox108.setWidth("70px");
			dctextbox109.setWidth("70px");
			dctextbox110.setWidth("70px");
			dctextbox111.setWidth("70px");
			dctextbox112.setWidth("70px");
			dctextbox113.setWidth("70px");
			dctextbox114.setWidth("70px");
			dctextbox115.setWidth("70px");
			dctextbox116.setWidth("70px");
			dctextbox117.setWidth("70px");
			dctextbox118.setWidth("70px");
			dctextbox119.setWidth("70px");
			row1.appendChild(new Label("国家级（类别/项数）例：3/1"));
			row2.appendChild(new Label("省部级（类别/项数）例：3/1"));
			row3.appendChild(new Label("横向（项数）例：3"));
			row4.appendChild(new Label("其他（项数）例：2"));
			row5.appendChild(new Label("论文 （SCI/EI/ISTP/核心）例：1/1/2/3"));
			row6.appendChild(new Label("著作/专利 例：0/1"));
			row7.appendChild(new Label("国家级（等级/项数）例：一/2"));
			row8.appendChild(new Label("省部级（等级/项数）例：二/4"));
			row9.appendChild(new Label("其他（项数）例：4"));
			row10.appendChild(new Label("国际会议（主办/承办/参加）例：0/0/2"));
			row11.appendChild(new Label("国内会议（主办/承办/参加）例：0/0/2"));
			row12.appendChild(new Label("互访（次数/人数）例：2/30"));
			row13.appendChild(new Label("其他 例：3"));
			row14.appendChild(new Label("国家级奖励（等级/数量）例：一/2"));
			row15.appendChild(new Label("省部级奖励（等级/数量）例：三/2"));
			row16.appendChild(new Label("出版教材（数量）例：2"));
			row17.appendChild(new Label("其他 例：1"));
			// 第一行
			row1.appendChild(dctextbox1);
			row1.appendChild(dctextbox18);
			row1.appendChild(dctextbox35);
			row1.appendChild(dctextbox52);
			row1.appendChild(dctextbox69);
			row1.appendChild(dctextbox86);
			row1.appendChild(dctextbox103);
			// 第二行
			row2.appendChild(dctextbox2);
			row2.appendChild(dctextbox19);
			row2.appendChild(dctextbox36);
			row2.appendChild(dctextbox53);
			row2.appendChild(dctextbox70);
			row2.appendChild(dctextbox87);
			row2.appendChild(dctextbox104);
			// 第三行
			row3.appendChild(dctextbox3);
			row3.appendChild(dctextbox20);
			row3.appendChild(dctextbox37);
			row3.appendChild(dctextbox54);
			row3.appendChild(dctextbox71);
			row3.appendChild(dctextbox88);
			row3.appendChild(dctextbox105);
			// 第四行
			row4.appendChild(dctextbox4);
			row4.appendChild(dctextbox21);
			row4.appendChild(dctextbox38);
			row4.appendChild(dctextbox55);
			row4.appendChild(dctextbox72);
			row4.appendChild(dctextbox89);
			row4.appendChild(dctextbox106);
			// 第五行
			row5.appendChild(dctextbox5);
			row5.appendChild(dctextbox22);
			row5.appendChild(dctextbox39);
			row5.appendChild(dctextbox56);
			row5.appendChild(dctextbox73);
			row5.appendChild(dctextbox90);
			row5.appendChild(dctextbox107);
			// 第六行
			row6.appendChild(dctextbox6);
			row6.appendChild(dctextbox23);
			row6.appendChild(dctextbox40);
			row6.appendChild(dctextbox57);
			row6.appendChild(dctextbox74);
			row6.appendChild(dctextbox91);
			row6.appendChild(dctextbox108);
			// 第七行
			row7.appendChild(dctextbox7);
			row7.appendChild(dctextbox24);
			row7.appendChild(dctextbox41);
			row7.appendChild(dctextbox58);
			row7.appendChild(dctextbox75);
			row7.appendChild(dctextbox92);
			row7.appendChild(dctextbox109);
			// 第八行
			row8.appendChild(dctextbox8);
			row8.appendChild(dctextbox25);
			row8.appendChild(dctextbox42);
			row8.appendChild(dctextbox59);
			row8.appendChild(dctextbox76);
			row8.appendChild(dctextbox93);
			row8.appendChild(dctextbox110);
			// 第九行
			row9.appendChild(dctextbox9);
			row9.appendChild(dctextbox26);
			row9.appendChild(dctextbox43);
			row9.appendChild(dctextbox60);
			row9.appendChild(dctextbox77);
			row9.appendChild(dctextbox94);
			row9.appendChild(dctextbox111);
			// 第十行
			row10.appendChild(dctextbox10);
			row10.appendChild(dctextbox27);
			row10.appendChild(dctextbox44);
			row10.appendChild(dctextbox61);
			row10.appendChild(dctextbox78);
			row10.appendChild(dctextbox95);
			row10.appendChild(dctextbox112);
			// 第十一行
			row11.appendChild(dctextbox11);
			row11.appendChild(dctextbox28);
			row11.appendChild(dctextbox45);
			row11.appendChild(dctextbox62);
			row11.appendChild(dctextbox79);
			row11.appendChild(dctextbox96);
			row11.appendChild(dctextbox113);
			// 第十二行
			row12.appendChild(dctextbox12);
			row12.appendChild(dctextbox29);
			row12.appendChild(dctextbox46);
			row12.appendChild(dctextbox63);
			row12.appendChild(dctextbox80);
			row12.appendChild(dctextbox97);
			row12.appendChild(dctextbox114);
			// 第十三行
			row13.appendChild(dctextbox13);
			row13.appendChild(dctextbox30);
			row13.appendChild(dctextbox47);
			row13.appendChild(dctextbox64);
			row13.appendChild(dctextbox81);
			row13.appendChild(dctextbox98);
			row13.appendChild(dctextbox115);
			// 第十四行
			row14.appendChild(dctextbox14);
			row14.appendChild(dctextbox31);
			row14.appendChild(dctextbox48);
			row14.appendChild(dctextbox65);
			row14.appendChild(dctextbox82);
			row14.appendChild(dctextbox99);
			row14.appendChild(dctextbox116);
			// 第十五行
			row15.appendChild(dctextbox15);
			row15.appendChild(dctextbox32);
			row15.appendChild(dctextbox49);
			row15.appendChild(dctextbox66);
			row15.appendChild(dctextbox83);
			row15.appendChild(dctextbox100);
			row15.appendChild(dctextbox117);
			// 第十六行
			row16.appendChild(dctextbox16);
			row16.appendChild(dctextbox33);
			row16.appendChild(dctextbox50);
			row16.appendChild(dctextbox67);
			row16.appendChild(dctextbox84);
			row16.appendChild(dctextbox101);
			row16.appendChild(dctextbox118);
			// 第十七行
			row17.appendChild(dctextbox17);
			row17.appendChild(dctextbox34);
			row17.appendChild(dctextbox51);
			row17.appendChild(dctextbox68);
			row17.appendChild(dctextbox85);
			row17.appendChild(dctextbox102);
			row17.appendChild(dctextbox119);
		}
	}

	/**
	 * 修改某一项情况调查表
	 * 
	 * @param i
	 *            第几列
	 * @param strlb
	 *            左侧显示的提示
	 * @param index
	 *            第几行
	 */
	public void editWin(int i, String strlb, int index) {
		final QkdcEditWindow editWin = (QkdcEditWindow) Executions.createComponents("/admin/personal/data/teacherinfo/qkdcedit.zul", null, null);
		String strCol = "";
		switch (i) {
		case 2:
			strCol = GhQkdc.BEFORE10;
			break;
		case 3:
			strCol = GhQkdc.BETWEEN11TO15;
			break;
		case 4:
			strCol = GhQkdc.AT2011;
			break;
		case 5:
			strCol = GhQkdc.AT2012;
			break;
		case 6:
			strCol = GhQkdc.AT2013;
			break;
		case 7:
			strCol = GhQkdc.AT2014;
			break;
		case 8:
			strCol = GhQkdc.AT2015;
			break;
		}
		editWin.setStrCol(strCol);
		editWin.setStrlb(strlb);
		editWin.setIndex(index);
		editWin.setUser(user);
		editWin.initWindow();
		editWin.doHighlighted();
		editWin.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				editWin.detach();
				initQkdc();
			}
		});
	}

	public void onClick$dcsubmit() throws InterruptedException {
		// 判断第一行是否有错误
		if (dctextbox1.getValue() == null || dctextbox1.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月情况国家级项目为空。");
			return;
		} else {
			String str[] = dctextbox1.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的截至到2010年7月情况国家级项目不符合规范。");
				return;
			}
		}
		if (dctextbox18.getValue() == null || dctextbox18.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015目标国家级项目为空。");
			return;
		} else {
			String str[] = dctextbox18.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011-2015目标国家级项目不符合规范。");
				return;
			}
		}
		if (dctextbox35.getValue() == null || dctextbox35.getValue().length() == 0) {
			Messagebox.show("您输入的2011年国家级项目为空。");
			return;
		} else {
			String str[] = dctextbox35.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011年国家级项目不符合规范。");
				return;
			}
		}
		if (dctextbox52.getValue() == null || dctextbox52.getValue().length() == 0) {
			Messagebox.show("您输入的2012年国家级项目为空。");
			return;
		} else {
			String str[] = dctextbox52.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2012年国家级项目不符合规范。");
				return;
			}
		}
		if (dctextbox69.getValue() == null || dctextbox69.getValue().length() == 0) {
			Messagebox.show("您输入的2013年国家级项目为空。");
			return;
		} else {
			String str[] = dctextbox69.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2013年国家级项目不符合规范。");
				return;
			}
		}
		if (dctextbox86.getValue() == null || dctextbox86.getValue().length() == 0) {
			Messagebox.show("您输入的2014年目标国家级项目为空。");
			return;
		} else {
			String str[] = dctextbox86.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2014年国家级项目不符合规范。");
				return;
			}
		}
		if (dctextbox103.getValue() == null || dctextbox103.getValue().length() == 0) {
			Messagebox.show("您输入的2015年国家级项目为空。");
			return;
		} else {
			String str[] = dctextbox103.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2015年国家级项目不符合规范。");
				return;
			}
		}
		// 判断第二行是否有错误
		if (dctextbox2.getValue() == null || dctextbox2.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月情况省部级项目为空。");
			return;
		} else {
			String str[] = dctextbox2.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的截至到2010年7月情况省部级项目不符合规范。");
				return;
			}
		}
		if (dctextbox19.getValue() == null || dctextbox19.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015目标省部级项目为空。");
			return;
		} else {
			String str[] = dctextbox19.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011-2015目标省部级项目不符合规范。");
				return;
			}
		}
		if (dctextbox36.getValue() == null || dctextbox36.getValue().length() == 0) {
			Messagebox.show("您输入的2011年省部级项目为空。");
			return;
		} else {
			String str[] = dctextbox36.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011年省部级项目不符合规范。");
				return;
			}
		}
		if (dctextbox53.getValue() == null || dctextbox53.getValue().length() == 0) {
			Messagebox.show("您输入的2012年省部级项目为空。");
			return;
		} else {
			String str[] = dctextbox53.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2012年省部级项目不符合规范。");
				return;
			}
		}
		if (dctextbox70.getValue() == null || dctextbox70.getValue().length() == 0) {
			Messagebox.show("您输入的2013年省部级项目为空。");
			return;
		} else {
			String str[] = dctextbox70.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2013年省部级项目不符合规范。");
				return;
			}
		}
		if (dctextbox87.getValue() == null || dctextbox87.getValue().length() == 0) {
			Messagebox.show("您输入的2014年目标省部级项目为空。");
			return;
		} else {
			String str[] = dctextbox87.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2014年省部级项目不符合规范。");
				return;
			}
		}
		if (dctextbox104.getValue() == null || dctextbox104.getValue().length() == 0) {
			Messagebox.show("您输入的2015年省部级项目为空。");
			return;
		} else {
			String str[] = dctextbox104.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2015年省部级项目不符合规范。");
				return;
			}
		}
		// 判断第三行是否有错误
		if (dctextbox3.getValue() == null || dctextbox3.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月情况横向项目为空。");
			return;
		}
		if (dctextbox20.getValue() == null || dctextbox20.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015目标横向项目为空。");
			return;
		}
		if (dctextbox37.getValue() == null || dctextbox37.getValue().length() == 0) {
			Messagebox.show("您输入的2011年省部级横向项目为空。");
			return;
		}
		if (dctextbox54.getValue() == null || dctextbox54.getValue().length() == 0) {
			Messagebox.show("您输入的2012年横向项目为空。");
			return;
		}
		if (dctextbox71.getValue() == null || dctextbox71.getValue().length() == 0) {
			Messagebox.show("您输入的2013年横向项目为空。");
			return;
		}
		if (dctextbox88.getValue() == null || dctextbox88.getValue().length() == 0) {
			Messagebox.show("您输入的2014年目标横向项目为空。");
			return;
		}
		if (dctextbox105.getValue() == null || dctextbox105.getValue().length() == 0) {
			Messagebox.show("您输入的2015年横向项目为空。");
			return;
		}
		// 判断第四行是否有错误
		if (dctextbox4.getValue() == null || dctextbox4.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月情况其他项目为空。");
			return;
		}
		if (dctextbox21.getValue() == null || dctextbox21.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015目标其他项目为空。");
			return;
		}
		if (dctextbox38.getValue() == null || dctextbox38.getValue().length() == 0) {
			Messagebox.show("您输入的2011年省部级其他项目为空。");
			return;
		}
		if (dctextbox55.getValue() == null || dctextbox55.getValue().length() == 0) {
			Messagebox.show("您输入的2012年其他项目为空。");
			return;
		}
		if (dctextbox72.getValue() == null || dctextbox72.getValue().length() == 0) {
			Messagebox.show("您输入的2013年其他项目为空。");
			return;
		}
		if (dctextbox89.getValue() == null || dctextbox89.getValue().length() == 0) {
			Messagebox.show("您输入的2014年目标其他项目为空。");
			return;
		}
		if (dctextbox106.getValue() == null || dctextbox106.getValue().length() == 0) {
			Messagebox.show("您输入的2015年其他项目为空。");
			return;
		}
		// 判断第五行是否有错误
		if (dctextbox5.getValue() == null || dctextbox5.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月情况论文为空。");
			return;
		} else {
			String str[] = dctextbox5.getValue().split("/");
			if (str.length != 4) {
				Messagebox.show("您输入的截至到2010年7月情况论文不符合规范。");
				return;
			}
		}
		if (dctextbox22.getValue() == null || dctextbox22.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015目标论文为空。");
			return;
		} else {
			String str[] = dctextbox22.getValue().split("/");
			if (str.length != 4) {
				Messagebox.show("您输入的2011-2015目标论文不符合规范。");
				return;
			}
		}
		if (dctextbox39.getValue() == null || dctextbox39.getValue().length() == 0) {
			Messagebox.show("您输入的2011年论文为空。");
			return;
		} else {
			String str[] = dctextbox39.getValue().split("/");
			if (str.length != 4) {
				Messagebox.show("您输入的2011年论文不符合规范。");
				return;
			}
		}
		if (dctextbox56.getValue() == null || dctextbox56.getValue().length() == 0) {
			Messagebox.show("您输入的2012年论文为空。");
			return;
		} else {
			String str[] = dctextbox56.getValue().split("/");
			if (str.length != 4) {
				Messagebox.show("您输入的2012年论文不符合规范。");
				return;
			}
		}
		if (dctextbox73.getValue() == null || dctextbox73.getValue().length() == 0) {
			Messagebox.show("您输入的2013年论文为空。");
			return;
		} else {
			String str[] = dctextbox73.getValue().split("/");
			if (str.length != 4) {
				Messagebox.show("您输入的2013年论文不符合规范。");
				return;
			}
		}
		if (dctextbox90.getValue() == null || dctextbox90.getValue().length() == 0) {
			Messagebox.show("您输入的2014年目标论文为空。");
			return;
		} else {
			String str[] = dctextbox90.getValue().split("/");
			if (str.length != 4) {
				Messagebox.show("您输入的2014年论文不符合规范。");
				return;
			}
		}
		if (dctextbox107.getValue() == null || dctextbox107.getValue().length() == 0) {
			Messagebox.show("您输入的2015年论文为空。");
			return;
		} else {
			String str[] = dctextbox107.getValue().split("/");
			if (str.length != 4) {
				Messagebox.show("您输入的2015年论文不符合规范。");
				return;
			}
		}
		// 判断第六行是否有错误
		if (dctextbox6.getValue() == null || dctextbox6.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月情况著作/专利为空。");
			return;
		} else {
			String str[] = dctextbox6.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的截至到2010年7月情况著作/专利不符合规范。");
				return;
			}
		}
		if (dctextbox23.getValue() == null || dctextbox23.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015目标著作/专利为空。");
			return;
		} else {
			String str[] = dctextbox23.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011-2015目标著作/专利不符合规范。");
				return;
			}
		}
		if (dctextbox40.getValue() == null || dctextbox40.getValue().length() == 0) {
			Messagebox.show("您输入的2011年著作/专利为空。");
			return;
		} else {
			String str[] = dctextbox40.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011年著作/专利不符合规范。");
				return;
			}
		}
		if (dctextbox57.getValue() == null || dctextbox57.getValue().length() == 0) {
			Messagebox.show("您输入的2012年著作/专利为空。");
			return;
		} else {
			String str[] = dctextbox57.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2012年著作/专利不符合规范。");
				return;
			}
		}
		if (dctextbox74.getValue() == null || dctextbox74.getValue().length() == 0) {
			Messagebox.show("您输入的2013年著作/专利为空。");
			return;
		} else {
			String str[] = dctextbox74.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2013年著作/专利不符合规范。");
				return;
			}
		}
		if (dctextbox91.getValue() == null || dctextbox91.getValue().length() == 0) {
			Messagebox.show("您输入的2014年目标著作/专利为空。");
			return;
		} else {
			String str[] = dctextbox91.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2014年著作/专利不符合规范。");
				return;
			}
		}
		if (dctextbox108.getValue() == null || dctextbox108.getValue().length() == 0) {
			Messagebox.show("您输入的2015年著作/专利为空。");
			return;
		} else {
			String str[] = dctextbox108.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2015年著作/专利不符合规范。");
				return;
			}
		}
		// 判断第七行是否有错误
		if (dctextbox7.getValue() == null || dctextbox7.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox7.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的截至到2010年7月国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的截至到2010年7月国家级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox24.getValue() == null || dctextbox24.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox24.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011-2015国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2011-2015国家级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox41.getValue() == null || dctextbox41.getValue().length() == 0) {
			Messagebox.show("您输入的2011年国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox41.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011年国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2011年国家级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox58.getValue() == null || dctextbox58.getValue().length() == 0) {
			Messagebox.show("您输入的2012年国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox58.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2012年国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2012年国家级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox75.getValue() == null || dctextbox75.getValue().length() == 0) {
			Messagebox.show("您输入的2013年国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox75.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2013年国家级奖励为空。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2013年国家级奖励为空。");
					return;
				}
			}
		}
		if (dctextbox92.getValue() == null || dctextbox92.getValue().length() == 0) {
			Messagebox.show("您输入的2014年国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox92.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2014年国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2014年国家级奖励不符合规范");
					return;
				}
			}
		}
		if (dctextbox109.getValue() == null || dctextbox109.getValue().length() == 0) {
			Messagebox.show("您输入的2015年国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox109.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2015年国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2015年国家级奖励不符合规范。");
					return;
				}
			}
		}
		// 判断第八行是否有错误
		if (dctextbox8.getValue() == null || dctextbox8.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox8.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的截至到2010年7月省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的截至到2010年7月省部级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox25.getValue() == null || dctextbox25.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox25.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011-2015省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2011-2015省部级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox42.getValue() == null || dctextbox42.getValue().length() == 0) {
			Messagebox.show("您输入的2011年省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox42.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011年省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2011年省部级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox59.getValue() == null || dctextbox59.getValue().length() == 0) {
			Messagebox.show("您输入的2012年省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox59.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2012年省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2012年省部级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox76.getValue() == null || dctextbox76.getValue().length() == 0) {
			Messagebox.show("您输入的2013年省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox76.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2013年省部级奖励为空。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2013年省部级奖励为空。");
					return;
				}
			}
		}
		if (dctextbox93.getValue() == null || dctextbox93.getValue().length() == 0) {
			Messagebox.show("您输入的2014年省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox93.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2014年省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2014年省部级奖励不符合规范");
					return;
				}
			}
		}
		if (dctextbox110.getValue() == null || dctextbox110.getValue().length() == 0) {
			Messagebox.show("您输入的2015年省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox110.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2015年省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2015年省部级奖励不符合规范。");
					return;
				}
			}
		}
		// 判断第九行是否有错误
		if (dctextbox9.getValue() == null || dctextbox9.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月其他奖励为空。");
			return;
		}
		if (dctextbox26.getValue() == null || dctextbox26.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015目标其他奖励为空。");
			return;
		}
		if (dctextbox43.getValue() == null || dctextbox43.getValue().length() == 0) {
			Messagebox.show("您输入的2011年省部级其他奖励为空。");
			return;
		}
		if (dctextbox60.getValue() == null || dctextbox60.getValue().length() == 0) {
			Messagebox.show("您输入的2012年其他奖励为空。");
			return;
		}
		if (dctextbox77.getValue() == null || dctextbox77.getValue().length() == 0) {
			Messagebox.show("您输入的2013年其他奖励为空。");
			return;
		}
		if (dctextbox94.getValue() == null || dctextbox94.getValue().length() == 0) {
			Messagebox.show("您输入的2014年目标其他奖励为空。");
			return;
		}
		if (dctextbox111.getValue() == null || dctextbox111.getValue().length() == 0) {
			Messagebox.show("您输入的2015年其他奖励为空。");
			return;
		}
		// 判断第十行是否有错误
		if (dctextbox10.getValue() == null || dctextbox10.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月国际会议为空。");
			return;
		} else {
			String str[] = dctextbox10.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的截至到2010年7月情况国际会议不符合规范。");
				return;
			}
		}
		if (dctextbox27.getValue() == null || dctextbox27.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015目标国际会议为空。");
			return;
		} else {
			String str[] = dctextbox27.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2011-2015目标国际会议不符合规范。");
				return;
			}
		}
		if (dctextbox44.getValue() == null || dctextbox44.getValue().length() == 0) {
			Messagebox.show("您输入的2011年国际会议为空。");
			return;
		} else {
			String str[] = dctextbox44.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2011年国际会议不符合规范。");
				return;
			}
		}
		if (dctextbox61.getValue() == null || dctextbox61.getValue().length() == 0) {
			Messagebox.show("您输入的2012年国际会议为空。");
			return;
		} else {
			String str[] = dctextbox61.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2012年国际会议不符合规范。");
				return;
			}
		}
		if (dctextbox78.getValue() == null || dctextbox78.getValue().length() == 0) {
			Messagebox.show("您输入的2013年国际会议为空。");
			return;
		} else {
			String str[] = dctextbox78.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2013年国际会议不符合规范。");
				return;
			}
		}
		if (dctextbox95.getValue() == null || dctextbox95.getValue().length() == 0) {
			Messagebox.show("您输入的2014年目标国际会议为空。");
			return;
		} else {
			String str[] = dctextbox95.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2014年国际会议不符合规范。");
				return;
			}
		}
		if (dctextbox112.getValue() == null || dctextbox112.getValue().length() == 0) {
			Messagebox.show("您输入的2015年国际会议为空。");
			return;
		} else {
			String str[] = dctextbox112.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2015年国际会议不符合规范。");
				return;
			}
		}
		// 判断第十一行是否有错误
		if (dctextbox11.getValue() == null || dctextbox11.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月国内会议为空。");
			return;
		} else {
			String str[] = dctextbox11.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的截至到2010年7月情况国内会议不符合规范。");
				return;
			}
		}
		if (dctextbox28.getValue() == null || dctextbox28.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015目标国内会议为空。");
			return;
		} else {
			String str[] = dctextbox28.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2011-2015目标国内会议不符合规范。");
				return;
			}
		}
		if (dctextbox45.getValue() == null || dctextbox45.getValue().length() == 0) {
			Messagebox.show("您输入的2011年国内会议为空。");
			return;
		} else {
			String str[] = dctextbox45.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2011年国内会议不符合规范。");
				return;
			}
		}
		if (dctextbox62.getValue() == null || dctextbox62.getValue().length() == 0) {
			Messagebox.show("您输入的2012年国内会议为空。");
			return;
		} else {
			String str[] = dctextbox62.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2012年国内会议不符合规范。");
				return;
			}
		}
		if (dctextbox79.getValue() == null || dctextbox79.getValue().length() == 0) {
			Messagebox.show("您输入的2013年国内会议为空。");
			return;
		} else {
			String str[] = dctextbox79.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2013年国内会议不符合规范。");
				return;
			}
		}
		if (dctextbox96.getValue() == null || dctextbox96.getValue().length() == 0) {
			Messagebox.show("您输入的2014年目标国内会议为空。");
			return;
		} else {
			String str[] = dctextbox96.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2014年国内会议不符合规范。");
				return;
			}
		}
		if (dctextbox113.getValue() == null || dctextbox113.getValue().length() == 0) {
			Messagebox.show("您输入的2015年国内会议为空。");
			return;
		} else {
			String str[] = dctextbox113.getValue().split("/");
			if (str.length != 3) {
				Messagebox.show("您输入的2015年国内会议不符合规范。");
				return;
			}
		}
		// 判断第十二行是否有错误
		if (dctextbox12.getValue() == null || dctextbox12.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月互访内容为空。");
			return;
		} else {
			String str[] = dctextbox12.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的截至到2010年7月互访内容不符合规范。");
				return;
			}
		}
		if (dctextbox29.getValue() == null || dctextbox29.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015目标互访内容为空。");
			return;
		} else {
			String str[] = dctextbox29.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011-2015目标互访内容不符合规范。");
				return;
			}
		}
		if (dctextbox46.getValue() == null || dctextbox46.getValue().length() == 0) {
			Messagebox.show("您输入的2011年互访内容为空。");
			return;
		} else {
			String str[] = dctextbox46.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011年互访内容不符合规范。");
				return;
			}
		}
		if (dctextbox63.getValue() == null || dctextbox63.getValue().length() == 0) {
			Messagebox.show("您输入的2012年互访内容为空。");
			return;
		} else {
			String str[] = dctextbox63.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2012年互访内容不符合规范。");
				return;
			}
		}
		if (dctextbox80.getValue() == null || dctextbox80.getValue().length() == 0) {
			Messagebox.show("您输入的2013年互访内容为空。");
			return;
		} else {
			String str[] = dctextbox80.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2013年互访内容不符合规范。");
				return;
			}
		}
		if (dctextbox97.getValue() == null || dctextbox97.getValue().length() == 0) {
			Messagebox.show("您输入的2014年目标互访内容为空。");
			return;
		} else {
			String str[] = dctextbox97.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2014年互访内容不符合规范。");
				return;
			}
		}
		if (dctextbox114.getValue() == null || dctextbox114.getValue().length() == 0) {
			Messagebox.show("您输入的2015年互访内容为空。");
			return;
		} else {
			String str[] = dctextbox114.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2015年互访内容不符合规范。");
				return;
			}
		}
		// 判断第十三行是否有错误
		if (dctextbox13.getValue() == null || dctextbox13.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月其他交流合作为空。");
			return;
		}
		if (dctextbox30.getValue() == null || dctextbox30.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015目标其他交流合作为空。");
			return;
		}
		if (dctextbox47.getValue() == null || dctextbox47.getValue().length() == 0) {
			Messagebox.show("您输入的2011年其他交流合作为空。");
			return;
		}
		if (dctextbox64.getValue() == null || dctextbox64.getValue().length() == 0) {
			Messagebox.show("您输入的2012年其他交流合作为空。");
			return;
		}
		if (dctextbox81.getValue() == null || dctextbox81.getValue().length() == 0) {
			Messagebox.show("您输入的2013年其他交流合作为空。");
			return;
		}
		if (dctextbox98.getValue() == null || dctextbox98.getValue().length() == 0) {
			Messagebox.show("您输入的2014年目标其他交流合作为空。");
			return;
		}
		if (dctextbox115.getValue() == null || dctextbox115.getValue().length() == 0) {
			Messagebox.show("您输入的2015年其他交流合作为空。");
			return;
		}
		// 判断第十四行是否有错误
		if (dctextbox14.getValue() == null || dctextbox14.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox14.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的截至到2010年7月国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的截至到2010年7月国家级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox31.getValue() == null || dctextbox31.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox31.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011-2015国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2011-2015国家级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox48.getValue() == null || dctextbox48.getValue().length() == 0) {
			Messagebox.show("您输入的2011年国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox48.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011年国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2011年国家级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox65.getValue() == null || dctextbox65.getValue().length() == 0) {
			Messagebox.show("您输入的2012年国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox65.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2012年国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2012年国家级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox82.getValue() == null || dctextbox82.getValue().length() == 0) {
			Messagebox.show("您输入的2013年国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox82.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2013年国家级奖励为空。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2013年国家级奖励为空。");
					return;
				}
			}
		}
		if (dctextbox99.getValue() == null || dctextbox99.getValue().length() == 0) {
			Messagebox.show("您输入的2014年国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox99.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2014年国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2014年国家级奖励不符合规范");
					return;
				}
			}
		}
		if (dctextbox116.getValue() == null || dctextbox116.getValue().length() == 0) {
			Messagebox.show("您输入的2015年国家级奖励为空。");
			return;
		} else {
			String str[] = dctextbox116.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2015年国家级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2015年国家级奖励不符合规范。");
					return;
				}
			}
		}
		// 判断第十五行是否有错误
		if (dctextbox15.getValue() == null || dctextbox15.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox15.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的截至到2010年7月省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的截至到2010年7月省部级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox32.getValue() == null || dctextbox32.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox32.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011-2015省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2011-2015省部级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox49.getValue() == null || dctextbox49.getValue().length() == 0) {
			Messagebox.show("您输入的2011年省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox49.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2011年省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2011年省部级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox66.getValue() == null || dctextbox66.getValue().length() == 0) {
			Messagebox.show("您输入的2012年省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox66.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2012年省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2012年省部级奖励不符合规范。");
					return;
				}
			}
		}
		if (dctextbox83.getValue() == null || dctextbox83.getValue().length() == 0) {
			Messagebox.show("您输入的2013年省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox83.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2013年省部级奖励为空。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2013年省部级奖励为空。");
					return;
				}
			}
		}
		if (dctextbox100.getValue() == null || dctextbox100.getValue().length() == 0) {
			Messagebox.show("您输入的2014年省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox100.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2014年省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2014年省部级奖励不符合规范");
					return;
				}
			}
		}
		if (dctextbox117.getValue() == null || dctextbox117.getValue().length() == 0) {
			Messagebox.show("您输入的2015年省部级奖励为空。");
			return;
		} else {
			String str[] = dctextbox117.getValue().split("/");
			if (str.length != 2) {
				Messagebox.show("您输入的2015年省部级奖励不符合规范。");
				return;
			} else {
				if (str[0].equalsIgnoreCase("一") || str[0].equalsIgnoreCase("二") || str[0].equalsIgnoreCase("三") || str[0].equalsIgnoreCase("四") || str[0].equalsIgnoreCase("五") || str[0].equalsIgnoreCase("六") || str[0].equalsIgnoreCase("七") || str[0].equalsIgnoreCase("八") || str[0].equalsIgnoreCase("九") || str[0].equalsIgnoreCase("十")) {
				} else {
					Messagebox.show("您输入的2015年省部级奖励不符合规范。");
					return;
				}
			}
		}
		// 判断第十六行是否有错误
		if (dctextbox16.getValue() == null || dctextbox16.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月出版教材为空。");
			return;
		}
		if (dctextbox33.getValue() == null || dctextbox33.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015出版教材为空。");
			return;
		}
		if (dctextbox50.getValue() == null || dctextbox50.getValue().length() == 0) {
			Messagebox.show("您输入的2011年出版教材为空。");
			return;
		}
		if (dctextbox67.getValue() == null || dctextbox67.getValue().length() == 0) {
			Messagebox.show("您输入的2012年出版教材为空。");
			return;
		}
		if (dctextbox84.getValue() == null || dctextbox84.getValue().length() == 0) {
			Messagebox.show("您输入的2013年出版教材为空。");
			return;
		}
		if (dctextbox101.getValue() == null || dctextbox101.getValue().length() == 0) {
			Messagebox.show("您输入的2014年出版教材为空。");
			return;
		}
		if (dctextbox118.getValue() == null || dctextbox118.getValue().length() == 0) {
			Messagebox.show("您输入的2015年出版教材为空。");
			return;
		}
		// 判断第十七行是否有错误
		if (dctextbox17.getValue() == null || dctextbox17.getValue().length() == 0) {
			Messagebox.show("您输入的截至到2010年7月其他教学成果为空。");
			return;
		}
		if (dctextbox34.getValue() == null || dctextbox34.getValue().length() == 0) {
			Messagebox.show("您输入的2011-2015其他教学成果为空。");
			return;
		}
		if (dctextbox51.getValue() == null || dctextbox51.getValue().length() == 0) {
			Messagebox.show("您输入的2011年其他教学成果为空。");
			return;
		}
		if (dctextbox68.getValue() == null || dctextbox68.getValue().length() == 0) {
			Messagebox.show("您输入的2012年其他教学成果为空。");
			return;
		}
		if (dctextbox85.getValue() == null || dctextbox85.getValue().length() == 0) {
			Messagebox.show("您输入的2013年其他教学成果为空。");
			return;
		}
		if (dctextbox102.getValue() == null || dctextbox102.getValue().length() == 0) {
			Messagebox.show("您输入的2014年其他教学成果为空。");
			return;
		}
		if (dctextbox119.getValue() == null || dctextbox119.getValue().length() == 0) {
			Messagebox.show("您输入的2015年其他教学成果为空。");
			return;
		}
		// 第一列
		GhQkdc dc1 = new GhQkdc();
		dc1.setNf(GhQkdc.BEFORE10);
		dc1.setKuId(user.getKuId());
		// 科研项目
		dc1.setKyxmGj(dctextbox1.getValue().trim());
		dc1.setKyxmSb(dctextbox2.getValue().trim());
		dc1.setKyxmHx(dctextbox3.getValue().trim());
		dc1.setKyxmQt(dctextbox4.getValue().trim());
		// 科研成果
		dc1.setKycgLw(dctextbox5.getValue().trim());
		dc1.setKycgZl(dctextbox6.getValue().trim());
		// 科研奖励
		dc1.setKyjlGj(dctextbox7.getValue().trim());
		dc1.setKyjlSb(dctextbox8.getValue().trim());
		dc1.setKyjlQt(dctextbox9.getValue().trim());
		// 科研交流合作
		dc1.setKyhzGj(dctextbox10.getValue().trim());
		dc1.setKyhzGn(dctextbox11.getValue().trim());
		dc1.setKyhzHf(dctextbox12.getValue().trim());
		dc1.setKyhzQt(dctextbox13.getValue().trim());
		// 教学成果
		dc1.setJxcgGj(dctextbox14.getValue().trim());
		dc1.setJxcgSb(dctextbox15.getValue().trim());
		dc1.setJxcgCb(dctextbox16.getValue().trim());
		dc1.setJxcgQt(dctextbox17.getValue().trim());
		// 第二列
		GhQkdc dc2 = new GhQkdc();
		dc2.setNf(GhQkdc.BETWEEN11TO15);
		dc2.setKuId(user.getKuId());
		// 科研项目
		dc2.setKyxmGj(dctextbox18.getValue().trim());
		dc2.setKyxmSb(dctextbox19.getValue().trim());
		dc2.setKyxmHx(dctextbox20.getValue().trim());
		dc2.setKyxmQt(dctextbox21.getValue().trim());
		// 科研成果
		dc2.setKycgLw(dctextbox22.getValue().trim());
		dc2.setKycgZl(dctextbox23.getValue().trim());
		// 科研奖励
		dc2.setKyjlGj(dctextbox24.getValue().trim());
		dc2.setKyjlSb(dctextbox25.getValue().trim());
		dc2.setKyjlQt(dctextbox26.getValue().trim());
		// 科研交流合作
		dc2.setKyhzGj(dctextbox27.getValue().trim());
		dc2.setKyhzGn(dctextbox28.getValue().trim());
		dc2.setKyhzHf(dctextbox29.getValue().trim());
		dc2.setKyhzQt(dctextbox30.getValue().trim());
		// 教学成果
		dc2.setJxcgGj(dctextbox31.getValue().trim());
		dc2.setJxcgSb(dctextbox32.getValue().trim());
		dc2.setJxcgCb(dctextbox33.getValue().trim());
		dc2.setJxcgQt(dctextbox34.getValue().trim());
		// 第三列
		GhQkdc dc3 = new GhQkdc();
		dc3.setNf(GhQkdc.AT2011);
		dc3.setKuId(user.getKuId());
		// 科研项目
		dc3.setKyxmGj(dctextbox35.getValue().trim());
		dc3.setKyxmSb(dctextbox36.getValue().trim());
		dc3.setKyxmHx(dctextbox37.getValue().trim());
		dc3.setKyxmQt(dctextbox38.getValue().trim());
		// 科研成果
		dc3.setKycgLw(dctextbox39.getValue().trim());
		dc3.setKycgZl(dctextbox40.getValue().trim());
		// 科研奖励
		dc3.setKyjlGj(dctextbox41.getValue().trim());
		dc3.setKyjlSb(dctextbox42.getValue().trim());
		dc3.setKyjlQt(dctextbox43.getValue().trim());
		// 科研交流合作
		dc3.setKyhzGj(dctextbox44.getValue().trim());
		dc3.setKyhzGn(dctextbox45.getValue().trim());
		dc3.setKyhzHf(dctextbox46.getValue().trim());
		dc3.setKyhzQt(dctextbox47.getValue().trim());
		// 教学成果
		dc3.setJxcgGj(dctextbox48.getValue().trim());
		dc3.setJxcgSb(dctextbox49.getValue().trim());
		dc3.setJxcgCb(dctextbox50.getValue().trim());
		dc3.setJxcgQt(dctextbox51.getValue().trim());
		// 第四列
		GhQkdc dc4 = new GhQkdc();
		dc4.setNf(GhQkdc.AT2012);
		dc4.setKuId(user.getKuId());
		// 科研项目
		dc4.setKyxmGj(dctextbox52.getValue().trim());
		dc4.setKyxmSb(dctextbox53.getValue().trim());
		dc4.setKyxmHx(dctextbox54.getValue().trim());
		dc4.setKyxmQt(dctextbox55.getValue().trim());
		// 科研成果
		dc4.setKycgLw(dctextbox56.getValue().trim());
		dc4.setKycgZl(dctextbox57.getValue().trim());
		// 科研奖励
		dc4.setKyjlGj(dctextbox58.getValue().trim());
		dc4.setKyjlSb(dctextbox59.getValue().trim());
		dc4.setKyjlQt(dctextbox60.getValue().trim());
		// 科研交流合作
		dc4.setKyhzGj(dctextbox61.getValue().trim());
		dc4.setKyhzGn(dctextbox62.getValue().trim());
		dc4.setKyhzHf(dctextbox63.getValue().trim());
		dc4.setKyhzQt(dctextbox64.getValue().trim());
		// 教学成果
		dc4.setJxcgGj(dctextbox65.getValue().trim());
		dc4.setJxcgSb(dctextbox66.getValue().trim());
		dc4.setJxcgCb(dctextbox67.getValue().trim());
		dc4.setJxcgQt(dctextbox68.getValue().trim());
		// 第五列
		GhQkdc dc5 = new GhQkdc();
		dc5.setNf(GhQkdc.AT2013);
		dc5.setKuId(user.getKuId());
		// 科研项目
		dc5.setKyxmGj(dctextbox69.getValue().trim());
		dc5.setKyxmSb(dctextbox70.getValue().trim());
		dc5.setKyxmHx(dctextbox71.getValue().trim());
		dc5.setKyxmQt(dctextbox72.getValue().trim());
		// 科研成果
		dc5.setKycgLw(dctextbox73.getValue().trim());
		dc5.setKycgZl(dctextbox74.getValue().trim());
		// 科研奖励
		dc5.setKyjlGj(dctextbox75.getValue().trim());
		dc5.setKyjlSb(dctextbox76.getValue().trim());
		dc5.setKyjlQt(dctextbox77.getValue().trim());
		// 科研交流合作
		dc5.setKyhzGj(dctextbox78.getValue().trim());
		dc5.setKyhzGn(dctextbox79.getValue().trim());
		dc5.setKyhzHf(dctextbox80.getValue().trim());
		dc5.setKyhzQt(dctextbox81.getValue().trim());
		// 教学成果
		dc5.setJxcgGj(dctextbox82.getValue().trim());
		dc5.setJxcgSb(dctextbox83.getValue().trim());
		dc5.setJxcgCb(dctextbox84.getValue().trim());
		dc5.setJxcgQt(dctextbox85.getValue().trim());
		// 第六列
		GhQkdc dc6 = new GhQkdc();
		dc6.setNf(GhQkdc.AT2014);
		dc6.setKuId(user.getKuId());
		// 科研项目
		dc6.setKyxmGj(dctextbox86.getValue().trim());
		dc6.setKyxmSb(dctextbox87.getValue().trim());
		dc6.setKyxmHx(dctextbox88.getValue().trim());
		dc6.setKyxmQt(dctextbox89.getValue().trim());
		// 科研成果
		dc6.setKycgLw(dctextbox90.getValue().trim());
		dc6.setKycgZl(dctextbox91.getValue().trim());
		// 科研奖励
		dc6.setKyjlGj(dctextbox92.getValue().trim());
		dc6.setKyjlSb(dctextbox93.getValue().trim());
		dc6.setKyjlQt(dctextbox94.getValue().trim());
		// 科研交流合作
		dc6.setKyhzGj(dctextbox95.getValue().trim());
		dc6.setKyhzGn(dctextbox96.getValue().trim());
		dc6.setKyhzHf(dctextbox97.getValue().trim());
		dc6.setKyhzQt(dctextbox98.getValue().trim());
		// 教学成果
		dc6.setJxcgGj(dctextbox99.getValue().trim());
		dc6.setJxcgSb(dctextbox100.getValue().trim());
		dc6.setJxcgCb(dctextbox101.getValue().trim());
		dc6.setJxcgQt(dctextbox102.getValue().trim());
		// 第七列
		GhQkdc dc7 = new GhQkdc();
		dc7.setNf(GhQkdc.AT2015);
		dc7.setKuId(user.getKuId());
		// 科研项目
		dc7.setKyxmGj(dctextbox103.getValue().trim());
		dc7.setKyxmSb(dctextbox104.getValue().trim());
		dc7.setKyxmHx(dctextbox105.getValue().trim());
		dc7.setKyxmQt(dctextbox106.getValue().trim());
		// 科研成果
		dc7.setKycgLw(dctextbox107.getValue().trim());
		dc7.setKycgZl(dctextbox108.getValue().trim());
		// 科研奖励
		dc7.setKyjlGj(dctextbox109.getValue().trim());
		dc7.setKyjlSb(dctextbox110.getValue().trim());
		dc7.setKyjlQt(dctextbox111.getValue().trim());
		// 科研交流合作
		dc7.setKyhzGj(dctextbox112.getValue().trim());
		dc7.setKyhzGn(dctextbox113.getValue().trim());
		dc7.setKyhzHf(dctextbox114.getValue().trim());
		dc7.setKyhzQt(dctextbox115.getValue().trim());
		// 教学成果
		dc7.setJxcgGj(dctextbox116.getValue().trim());
		dc7.setJxcgSb(dctextbox117.getValue().trim());
		dc7.setJxcgCb(dctextbox118.getValue().trim());
		dc7.setJxcgQt(dctextbox119.getValue().trim());
		qkdcService.save(dc1);
		qkdcService.save(dc2);
		qkdcService.save(dc3);
		qkdcService.save(dc4);
		qkdcService.save(dc5);
		qkdcService.save(dc6);
		qkdcService.save(dc7);
		Messagebox.show("保存成功");
		initShow();
	}

	public void onClick$dcreset() {
		initQkdc();
	}
}
