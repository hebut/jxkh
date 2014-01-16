package org.iti.jxkh.score.work;

import java.util.List;

import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.JXKH_MultiDept;
import org.iti.jxkh.entity.JXKH_QKLWDept;
import org.iti.jxkh.entity.Jxkh_AwardDept;
import org.iti.jxkh.entity.Jxkh_FruitDept;
import org.iti.jxkh.entity.Jxkh_PatentDept;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.entity.Jxkh_WritingDept;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

public class AddMultiDeptWindow extends Window implements AfterCompose {
	private static final long serialVersionUID = 7709604284024931540L;
	private Rows rows;
	private List<?> dlist;
	private Short type;
	private String strRate = "";
	private String strDept = "";
	private int[][] argArray = { { 70, 30 }, { 60, 30, 10 }, { 50, 30, 10, 10 }, { 50, 20, 10, 10, 10 }, { 50, 20, 10, 10, 5, 5 }, { 50, 15, 10, 10, 5, 5, 5 } };

	public void setDlist(List<?> dlist) {
		this.dlist = dlist;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getStrRate() {
		return strRate;
	}

	public String getStrDept() {
		return strDept;
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow() {
		if (type.shortValue() == JXKH_MultiDept.HYLW) {
			for (int i = 0; i < dlist.size(); i++) {
				JXKH_HYLWDept d = (JXKH_HYLWDept) dlist.get(i);
				Row row = new Row();
				Label lb = new Label(d.getName());
				strDept += d.getDepId() + ",";
				Intbox ib = new Intbox();
				ib.setValue(argArray[dlist.size() - 2][i]);
				ib.setId("ib" + i);
				row.appendChild(lb);
				row.appendChild(ib);
				rows.appendChild(row);
			}
		} else if (type.shortValue() == JXKH_MultiDept.QKLW) {
			for (int i = 0; i < dlist.size(); i++) {
				JXKH_QKLWDept d = (JXKH_QKLWDept) dlist.get(i);
				Row row = new Row();
				Label lb = new Label(d.getName());
				strDept += d.getDepId() + ",";
				Intbox ib = new Intbox();
				ib.setValue(argArray[dlist.size() - 2][i]);
				ib.setId("ib" + i);
				row.appendChild(lb);
				row.appendChild(ib);
				rows.appendChild(row);
			}
		} else if (type.shortValue() == JXKH_MultiDept.ZZ) {
			for (int i = 0; i < dlist.size(); i++) {
				Jxkh_WritingDept d = (Jxkh_WritingDept) dlist.get(i);
				Row row = new Row();
				Label lb = new Label(d.getName());
				strDept += d.getDeptId() + ",";
				Intbox ib = new Intbox();
				ib.setValue(argArray[dlist.size() - 2][i]);
				ib.setId("ib" + i);
				row.appendChild(lb);
				row.appendChild(ib);
				rows.appendChild(row);
			}
		} else if (type.shortValue() == JXKH_MultiDept.JL) {
			for (int i = 0; i < dlist.size(); i++) {
				Jxkh_AwardDept d = (Jxkh_AwardDept) dlist.get(i);
				Row row = new Row();
				Label lb = new Label(d.getName());
				strDept += d.getDeptId() + ",";
				Intbox ib = new Intbox();
				ib.setValue(argArray[dlist.size() - 2][i]);
				ib.setId("ib" + i);
				row.appendChild(lb);
				row.appendChild(ib);
				rows.appendChild(row);
			}
		} else if (type.shortValue() == JXKH_MultiDept.SP) {
			for (int i = 0; i < dlist.size(); i++) {
				Jxkh_VideoDept d = (Jxkh_VideoDept) dlist.get(i);
				Row row = new Row();
				Label lb = new Label(d.getName());
				strDept += d.getDeptId() + ",";
				Intbox ib = new Intbox();
				ib.setValue(argArray[dlist.size() - 2][i]);
				ib.setId("ib" + i);
				row.appendChild(lb);
				row.appendChild(ib);
				rows.appendChild(row);
			}
		} else if (type.shortValue() == JXKH_MultiDept.XM) {
			for (int i = 0; i < dlist.size(); i++) {
				Jxkh_ProjectDept d = (Jxkh_ProjectDept) dlist.get(i);
				Row row = new Row();
				Label lb = new Label(d.getName());
				strDept += d.getDeptId() + ",";
				Intbox ib = new Intbox();
				ib.setValue(argArray[dlist.size() - 2][i]);
				ib.setId("ib" + i);
				row.appendChild(lb);
				row.appendChild(ib);
				rows.appendChild(row);
			}
		} else if (type.shortValue() == JXKH_MultiDept.ZL) {
			for (int i = 0; i < dlist.size(); i++) {
				Jxkh_PatentDept d = (Jxkh_PatentDept) dlist.get(i);
				Row row = new Row();
				Label lb = new Label(d.getName());
				strDept += d.getDeptId() + ",";
				Intbox ib = new Intbox();
				ib.setValue(argArray[dlist.size() - 2][i]);
				ib.setId("ib" + i);
				row.appendChild(lb);
				row.appendChild(ib);
				rows.appendChild(row);
			}
		} else if (type.shortValue() == JXKH_MultiDept.CG) {
			for (int i = 0; i < dlist.size(); i++) {
				Jxkh_FruitDept d = (Jxkh_FruitDept) dlist.get(i);
				Row row = new Row();
				Label lb = new Label(d.getName());
				strDept += d.getDeptId() + ",";
				Intbox ib = new Intbox();
				ib.setValue(argArray[dlist.size() - 2][i]);
				ib.setId("ib" + i);
				row.appendChild(lb);
				row.appendChild(ib);
				rows.appendChild(row);
			}
		} else if (type.shortValue() == JXKH_MultiDept.HY) {
			for (int i = 0; i < dlist.size(); i++) {
				JXKH_MeetingDept d = (JXKH_MeetingDept) dlist.get(i);
				Row row = new Row();
				Label lb = new Label(d.getName());
				strDept += d.getDepId() + ",";
				Intbox ib = new Intbox();
				ib.setValue(argArray[dlist.size() - 2][i]);
				ib.setId("ib" + i);
				row.appendChild(lb);
				row.appendChild(ib);
				rows.appendChild(row);
			}
		} else if (type.shortValue() == JXKH_MultiDept.BG) {
			for (int i = 0; i < dlist.size(); i++) {
				Jxkh_ReportDept d = (Jxkh_ReportDept) dlist.get(i);
				Row row = new Row();
				Label lb = new Label(d.getName());
				strDept += d.getDeptId() + ",";
				Intbox ib = new Intbox();
				ib.setValue(argArray[dlist.size() - 2][i]);
				ib.setId("ib" + i);
				row.appendChild(lb);
				row.appendChild(ib);
				rows.appendChild(row);
			}
		}
	}

	public void onClick$submit() {
		for (int i = 0; i < rows.getChildren().size(); i++) {
			Intbox ib = (Intbox) this.getFellow("ib" + i);
			strRate += ib.getValue() + ":";
		}
		strRate = strRate.substring(0, strRate.length() - 1);
		strDept = strDept.substring(0, strDept.length() - 1);
		Events.postEvent(Events.ON_CHANGE, this, null);
	}

	public void onClick$close() {
		this.detach();
	}
}