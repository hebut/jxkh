package org.iti.jxkh.score.work;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MultiDept;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_Writing;
import org.iti.jxkh.service.AuditResultService;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.iti.common.util.PropertiesLoader;

public class ShowDetailWindow extends Window implements AfterCompose {

	private static final long serialVersionUID = 2536546529761198347L;
	private Listhead head;
	private Listbox listbox;
	private Integer type;
	private BusinessIndicatorService businessIndicatorService;
	private AuditResultService auditResultService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		listbox.setItemRenderer(new ListitemRenderer() {

			@Override
			public void render(Listitem arg0, Object arg1) throws Exception {
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				String[] a = arg1.toString().split("-");
				Listcell c1 = new Listcell();
				Listcell c2 = new Listcell(a[1]);
				// Listcell c3 = new Listcell();
				switch (type) {
				case 0:
					if (a[2].equals("hy")) {
						JXKH_HYLW hy = (JXKH_HYLW) businessIndicatorService
								.get(JXKH_HYLW.class, Long.parseLong(a[0]));
						c1.setLabel(hy.getLwName());
						// c3.setLabel("会议");
					} else if (a[2].equals("qk")) {
						JXKH_QKLW hy = (JXKH_QKLW) businessIndicatorService
								.get(JXKH_QKLW.class, Long.parseLong(a[0]));
						c1.setLabel(hy.getLwName());
						// c3.setLabel("期刊");
					}
					break;
				case 1:
					Jxkh_Writing wt = (Jxkh_Writing) businessIndicatorService
							.get(Jxkh_Writing.class, Long.parseLong(a[0]));
					c1.setLabel(wt.getName());
					break;
				case 2:
					Jxkh_Award ar = (Jxkh_Award) businessIndicatorService.get(
							Jxkh_Award.class, Long.parseLong(a[0]));
					c1.setLabel(ar.getName());
					break;
				case 3:
					Jxkh_Video vd = (Jxkh_Video) businessIndicatorService.get(
							Jxkh_Video.class, Long.parseLong(a[0]));
					c1.setLabel(vd.getName());
					break;
				case 4:
					Jxkh_Project pj = (Jxkh_Project) businessIndicatorService
							.get(Jxkh_Project.class, Long.parseLong(a[0]));
					c1.setLabel(pj.getName());
					break;
				case 5:
					Jxkh_Patent pt = (Jxkh_Patent) businessIndicatorService
							.get(Jxkh_Patent.class, Long.parseLong(a[0]));
					c1.setLabel(pt.getName());
					break;
				case 6:
					Jxkh_Fruit fr = (Jxkh_Fruit) businessIndicatorService.get(
							Jxkh_Fruit.class, Long.parseLong(a[0]));
					c1.setLabel(fr.getName());
					break;
				case 7:
					JXKH_MEETING mt = (JXKH_MEETING) businessIndicatorService
							.get(JXKH_MEETING.class, Long.parseLong(a[0]));
					c1.setLabel(mt.getMtName());
					break;
				case 8:
					Jxkh_Report rp = (Jxkh_Report) businessIndicatorService
							.get(Jxkh_Report.class, Long.parseLong(a[0]));
					c1.setLabel(rp.getName());
					break;
				}
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				// arg0.appendChild(c3);
			}
		});
	}

	public void initWindow(String show, Integer type1) {
		this.type = type1;
		Listheader header0 = new Listheader("序号");
		Listheader header1 = null;
		List<String> a = null;
		String[] key = show.split("-");
		String year = key[0];
		String dept = key[1];
		String type = key[2];
		Properties property = PropertiesLoader.loader("title",
				"title.properties");
		switch (type1) {
		case 0:
			header1 = new Listheader("论文名称");
			a = auditResultService.countDeptDetail(dept, year,
					property.getProperty(type), JXKH_MultiDept.QKLW);
			List<String> c = auditResultService.countDeptDetail(dept, year,
					property.getProperty(type), JXKH_MultiDept.HYLW);
			a.addAll(c);
			if (type.equals("core")) {
				List<String> b = auditResultService.countDeptDetail(dept, year,
						property.getProperty("international"),
						JXKH_MultiDept.HYLW);
				a.addAll(b);
			}
			break;
		case 1:
			header1 = new Listheader("著作名称");
			a=this.auditResultService.countDeptWriType1(dept, year, property.getProperty(type));
			break;
		case 2:
			header1 = new Listheader("奖励名称");
			a = auditResultService.countDeptDetail(dept, year,
					property.getProperty(type), JXKH_MultiDept.JL);
			break;
		case 3:
			header1 = new Listheader("影视名称");
			a = auditResultService.countDeptDetail(dept, year,
					property.getProperty(type), JXKH_MultiDept.SP);
			break;
		case 4:
			header1 = new Listheader("经费名称");
			a = auditResultService.countDeptProType1(dept, year,
					property.getProperty(type));
			break;
		case 5:
			header1 = new Listheader("专利名称");
			a=this.auditResultService.countDeptPatType1(dept, year, property.getProperty(type));
			break;
		case 6:
			header1 = new Listheader("成果名称");
			a = auditResultService.countDeptDetail(dept, year,
					property.getProperty(type), JXKH_MultiDept.CG);
			break;
		case 7:
			header1 = new Listheader("会议名称");
			a=this.auditResultService.countDeptMeeType1(dept, year, property.getProperty(type));
			break;
		case 8:
			header1 = new Listheader("报告名称");
			a = auditResultService.countDeptDetail(dept, year,
					property.getProperty(type), JXKH_MultiDept.BG);
			break;
		}
		Listheader header2 = new Listheader("分值");
		// Listheader header3 = new Listheader("备注");
		header0.setWidth("6%");
		header2.setWidth("10%");
		// header3.setWidth("10%");
		head.appendChild(header0);
		head.appendChild(header1);
		head.appendChild(header2);
		// head.appendChild(header3);

		// List list = new ArrayList();
		// if (show.length() > 4) {
		// show = show.substring(4, show.length());
		// for (String str : show.split(",")) {
		// list.add(str);
		// }
		// }
		if (a != null)
			listbox.setModel(new ListModelList(a));
	}
}
