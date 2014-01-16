package com.uniwin.asm.personal.group.discuss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.asm.personal.entity.QzMessage;
import com.uniwin.asm.personal.entity.QzSubject;
import com.uniwin.asm.personal.service.QzMessageService;
import com.uniwin.asm.personal.service.SubjectService;
import com.uniwin.common.util.ConvertUtil;

public class GroupMessageWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = 1L;
	Grid M_grid;
	QzMessageService qzMessageService;
	SubjectService subjectService;
	Toolbarbutton newsubject, close;
	Long qzid = (Long) Executions.getCurrent().getArg().get("qzid");

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		QzGroup group = (QzGroup) qzMessageService.get(QzGroup.class, qzid);
		this.setTitle("[" + group.getQzName() + "] 组内话题详情");
		this.setStyle("font-size:15");
		List sublist = subjectService.findByQzid(qzid);
		M_grid.setModel(new ListModelList(sublist));
		M_grid.setRowRenderer(new RowRenderer() {
			public void render(Row arg0, Object arg1) throws Exception {
				final QzSubject qs = (QzSubject) arg1;
				arg0.setStyle("padding:0;border-width:medium 0px 0px");
				arg0.setHeight("30px");
				Image i0 = new Image("");
				if (qs.getSjImg() == 0 || qs.getSjImg() == null) {
					i0.setSrc("/admin/image/group/icon1.gif");
				} else {
					i0.setSrc("/admin/image/group/icon" + qs.getSjImg() + ".gif");
				}
				i0.setWidth("15px");
				i0.setHeight("15px");
				// 主题
				Toolbarbutton l1 = new Toolbarbutton(qs.getSjTitle());
				l1.setStyle("color:blue");
				l1.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						Map mapTList = new HashMap();
						mapTList.put("subject", qs);
						MessageWindow mw = (MessageWindow) Executions.createComponents("/admin/personal/group/discuss/message.zul", null, mapTList);
						mw.doHighlighted();
						mw.addEventListener(Events.ON_CHANGE, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								ReloadMgrid();
							}
						});
					}
				});
				Label type = new Label();
				switch (qs.getSjType()) {
				case 0:
					type.setValue("普通");
					break;
				case 1:
					type.setValue("讨论");
					break;
				case 2:
					type.setValue("推荐");
					break;
				}
				// 作者
				Label l2 = new Label(qs.getUser().getKuName());
				List hflist = qzMessageService.findBtSjid(qs.getSjId(), Short.parseShort("2"));
				// 回复/查看
				Label l3 = new Label(hflist.size() + "/" + qs.getSjViewsum());
				Hbox hb = new Hbox();
				// 最后回复的用户
				Label l4 = new Label();
				Label l5 = new Label();
				l5.setStyle("color:gray");
				if (hflist != null && hflist.size() > 0) {
					QzMessage qm = (QzMessage) hflist.get(hflist.size() - 1);
					l4.setValue(qm.getUser().getKuName());
					l5.setValue(ConvertUtil.convertDateAndTimeString(qm.getMgTime()));
				} else {
					l4.setValue(qs.getUser().getKuName());
					l5.setValue(ConvertUtil.convertDateAndTimeString(qs.getSjTime()));
				}
				hb.appendChild(l4);
				hb.appendChild(l5);
				arg0.appendChild(i0);
				arg0.appendChild(l1);
				arg0.appendChild(type);
				arg0.appendChild(l2);
				arg0.appendChild(l3);
				arg0.appendChild(hb);
			}
		});
	}

	public void ReloadMgrid() {
		List sublist = subjectService.findByQzid(qzid);
		M_grid.setModel(new ListModelList(sublist));
	}

	public void onClick$newsubject() {
		NewSubjectWindow qsw = (NewSubjectWindow) Executions.createComponents("/admin/personal/group/discuss/newmessage.zul", null, null);
		qsw.doHighlighted();
		qsw.setQzId(qzid);
		qsw.ReloadgroupList();
		qsw.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				ReloadMgrid();
			}
		});
	}

	public void onClick$close() {
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
	}
}
