package org.iti.jxkh.vote.participate;

import java.util.Calendar;
import java.util.List;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.entity.JXKH_VoteConfig;
import org.iti.jxkh.entity.JXKH_VoteResult;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.VoteConfigService;
import org.iti.jxkh.service.VoteResultService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;

public class ParticipateWindow extends BaseWindow {

	private static final long serialVersionUID = 5134573690743253474L;
	private Listbox votelist, memberlist;
	private YearListbox yearlist;
	private Label level1, level2, level3;
	private VoteConfigService voteConfigService;
	private VoteResultService voteResultService;
	private AuditConfigService auditConfigService;
	private JXKH_AuditConfig ac;
	private JXKH_VoteConfig vc;
	private WkTUser user;
	private Button submit;

	public void initShow() {
		yearlist.initYearListbox("");
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		yearlist.initYearListbox("");
		yearlist.getItemAtIndex(0).setSelected(true);
		votelist.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				final JXKH_VoteConfig config = (JXKH_VoteConfig) arg1;
				arg0.setLabel(config.getVcName());
				arg0.setValue(arg1);
			}
		});
		memberlist.setItemRenderer(new ListitemRenderer() {

			public void render(Listitem arg0, Object arg1) throws Exception {
				JXKH_VoteResult res = (JXKH_VoteResult) arg1;
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				Listcell c1 = new Listcell(res.getUser().getDept().getKdName());
				Listcell c2 = new Listcell(res.getUser().getKuLid());
				Listcell c3 = new Listcell(res.getUser().getKuName());
				Listcell c4 = new Listcell();
				if (res.getUser().getKuSex().equals("1")) {
					c4.setLabel("男");
				} else {
					c4.setLabel("女");
				}
				Listcell c5 = new Listcell();
				if (res.getVrRecord() == null || res.getVrRecord().indexOf("-" + user.getKuId() + ",") == -1) {
					Radiogroup group = new Radiogroup();
					Radio r1 = new Radio("三档");
					Radio r2 = new Radio("二档");
					Radio r3 = new Radio("一档");
					Radio r4 = new Radio("弃权");
					r3.setSelected(true);
					group.setId("group" + arg0.getIndex());
					group.appendChild(r1);
					group.appendChild(r2);
					group.appendChild(r3);
					group.appendChild(r4);
					c5.appendChild(group);
					submit.setVisible(true);
				} else {
					Label lb = new Label(res.getVrRecord().charAt(res.getVrRecord().indexOf(user.getKuId() + "") - 2) + "档");
					c5.appendChild(lb);
					submit.setVisible(false);//当投票完成后，“提交投票结果”按钮不可见20120713
				}
				arg0.setValue(arg1);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
	}

	public void onSelect$votelist() {
		memberlist.setVisible(false);
		memberlist.setVisible(true);
		JXKH_VoteConfig config = (JXKH_VoteConfig) votelist.getSelectedItem().getValue();
		memberlist.setModel(new ListModelList(config.getVcObject()));
		initTitle();
		vc = config;
	}

	public void initTitle() {
		String[] vlist = vc.getVcWeight().split("-");
		int arg1 = (int) (memberlist.getItemCount() * (Float.parseFloat(vlist[2]) / 100));
		int arg2 = (int) (memberlist.getItemCount() * (Float.parseFloat(vlist[1]) / 100));
		int arg3 = memberlist.getItemCount() - arg1 - arg2;
		level1.setValue(arg1 + "");
		level2.setValue(arg2 + "");
		level3.setValue(arg3 + "");
	}

	public void initWindow() {
		/*JxYear jy = (JxYear)yearlist.getSelectedItem().getValue();
		String year = jy.getYears();*/
		/*-----------------changed----------------*/
		String year = yearlist.getSelectYear();
		/*-----------------changed----------------*/
		List<JXKH_VoteConfig> vlist = voteConfigService.findConfigByKuId(user.getKuId().toString(), year);
		votelist.setModel(new ListModelList(vlist));
		if (vlist.size() > 0) {
			vc = vlist.get(0);
			memberlist.setModel(new ListModelList(vc.getVcObject()));
			initTitle();
		}
	}

	public void onClick$submit() {
		if (memberlist.getItemCount() == 0) {
			try {
				Messagebox.show("当前无投票对象！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		String start=vc.getVcStart();
		String end=vc.getVcEnd();
		//获取当前的时间
		/*-------------------changed--------------*/
		String nowTime = com.iti.common.util.TimeConvert.format_5.format(
				Calendar.getInstance().getTime());
		/*-------------------changed--------------*/
		if(nowTime.compareTo(start)<0||nowTime.compareTo(end)>0)
		{
			try {
				Messagebox.show("当前不在投票时间范围内！投票时间为"+vc.getVcStart()+" —— "+vc.getVcEnd(), "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		//ac = auditConfigService.findByYear(yearlist.getSelectedItem().getLabel());
		/*-------------------changed--------------*/
		ac = auditConfigService.findByYear(yearlist.getSelectYear());
		/*-------------------changed--------------*/
		String[] alist = ac.getAcMWeight().split("-");
		int second = 0, three = 0;
		for (int i = 0; i < memberlist.getItemCount(); i++) {
			Radiogroup group = (Radiogroup) this.getFellowIfAny("group" + i);
			if (group.getSelectedIndex() == 0) {
				three++;
			} else if (group.getSelectedIndex() == 1) {
				second++;
			}
		}
		if (three > Integer.parseInt(level3.getValue())) {
			try {
				Messagebox.show("三档的总人数过多，请修改后再提交！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if (second + three > Integer.parseInt(level2.getValue()) + Integer.parseInt(level3.getValue())) {
			try {
				Messagebox.show("二档和三档的总人数过多，请修改后再提交！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		List<WkTRole> rlist = voteResultService.findMyRole(user.getKuId());
		WkTRole role = rlist.get(0);
		Object[] objArray = memberlist.getItems().toArray();
		for (int i = 0; i < objArray.length; i++) {
			Listitem item = (Listitem) objArray[i];
			JXKH_VoteResult vr = (JXKH_VoteResult) item.getValue();
			String str = "";
			Integer num = 0;
			Radiogroup group = (Radiogroup) this.getFellowIfAny("group" + i);
			if (role.getKrName().equals("院长")) {
				str += "1-" + (3 - group.getSelectedIndex()) + "-" + user.getKuId() + ",";
				num = Integer.parseInt(alist[0]) * (3 - group.getSelectedIndex());
			} else if (role.getKrName().equals("副院长")) {
				str += "2-" + (3 - group.getSelectedIndex()) + "-" + user.getKuId() + ",";
				num = Integer.parseInt(alist[1]) * (3 - group.getSelectedIndex());
			} else if (role.getKrName().endsWith("负责人")) {
				str += "3-" + (3 - group.getSelectedIndex()) + "-" + user.getKuId() + ",";
				num = Integer.parseInt(alist[2]) * (3 - group.getSelectedIndex());
			} else {
				str += "4-" + (3 - group.getSelectedIndex()) + "-" + user.getKuId() + ",";
				num = Integer.parseInt(alist[3]) * (3 - group.getSelectedIndex());
			}
			vr.setVrNumber(vr.getVrNumber() + num);
			vr.setVrRecord(vr.getVrRecord() + str);
			voteResultService.update(vr);
		}
		memberlist.setModel(new ListModelList(vc.getVcObject()));
	}

	public void onSelect$yearlist() {
		initWindow();
	}
}