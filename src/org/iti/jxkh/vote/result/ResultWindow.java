package org.iti.jxkh.vote.result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_VoteConfig;
import org.iti.jxkh.entity.JXKH_VoteResult;
import org.iti.jxkh.service.VoteConfigService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Space;

import com.uniwin.framework.entity.WkTUser;

public class ResultWindow extends BaseWindow {

	private static final long serialVersionUID = 5134573690743253474L;
	private YearListbox yearlist;
	private Listbox votelist, memberlist;
	private Button print, export,clear;	
	private VoteConfigService voteConfigService;
	@SuppressWarnings("unused")
	private WkTUser user;
	List<JXKH_VoteResult> rs=new ArrayList<JXKH_VoteResult>();

	public void initShow() {
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");
		yearlist.initYearListbox("");
		yearlist.getItemAtIndex(0).setSelected(true);
		votelist.setItemRenderer(new ListitemRenderer() {//投票结果列表渲染器

			public void render(Listitem arg0, Object arg1) throws Exception {
				final JXKH_VoteConfig vc = (JXKH_VoteConfig) arg1;
				Listcell cell = new Listcell();
				Image img = new Image("/images/collection/tablechild.png");
				Label lb = new Label(vc.getVcName());
				Space space1 = new Space();
				Space space2 = new Space();
				cell.appendChild(space1);
				cell.appendChild(img);
				cell.appendChild(space2);
				cell.appendChild(lb);
				arg0.appendChild(cell);
				arg0.setValue(arg1);
				arg0.addEventListener(Events.ON_CLICK, new EventListener() {

					public void onEvent(Event arg0) throws Exception {
						memberlist.setVisible(false);
						memberlist.setVisible(true);
						export.setDisabled(false);
						clear.setDisabled(false);
						
						/**********************************************************/
						
				        rs=voteConfigService.findResultByVcId(vc.getVcId());
				        memberlist.setModel(new ListModelList(rs));	
						/*-----------changed---------------*/
						clear.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {
								for(JXKH_VoteResult result:rs){
									voteConfigService.delete(result);
								}
								rs.clear();
								memberlist.setModel(new ListModelList(rs));	
								Messagebox.show("投票结果已清空！");
							}
						});
						
						/***********************************************************/
						print.setDisabled(false);
						/*JxYear jy = (JxYear)yearlist.getSelectedItem().getValue();
						String year = jy.getYears();*/
						/*-----------------changed----------------*/
						String year = yearlist.getSelectYear();
						/*-----------------changed----------------*/
						print.setHref("/print.action?n=vote&year=" + year);
					}
				});
			}
		});
//<<<<<<< ResultWindow.java
//		JxYear jy = (JxYear)yearlist.getSelectedItem().getValue();
//		String year = jy.getYears();
//		List<JXKH_VoteConfig> vlist = voteConfigService.findConfigByKuId(user.getKuId().toString(), year);
//		votelist.setModel(new ListModelList(vlist));
//=======
//>>>>>>> 1.9
		memberlist.setItemRenderer(new ListitemRenderer() {//人员结果列表渲染器

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
				Listcell c5 = new Listcell(res.getVrNumber() + "");
				arg0.setValue(arg1);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
			}
		});
		initWindow();
	}

	public void onSelect$yearlist() {
		initWindow();
	}

	public void initWindow(){//change
		String year=yearlist.getSelectYear();
		List<JXKH_VoteConfig>vlist=voteConfigService.findConfigByYear(year);
		votelist.setModel(new ListModelList(vlist));
	}
	public void onClick$export() throws WriteException, IOException {//导出按钮先暂时不用看
		// if(memberlist.getSelectedCount()==0)
		// {
		// try {
		// Messagebox.show("提示请选择要导出的数据","提示", Messagebox.OK,
		// Messagebox.EXCLAMATION);
		// } catch (InterruptedException e) {
		// // ignore
		// }
		// return;
		// }
		// else
		// {
		int i = 0;
		ArrayList<JXKH_VoteResult> expertlist = new ArrayList<JXKH_VoteResult>();
		@SuppressWarnings("unchecked")
		Iterator<Listitem> it = memberlist.getItems().iterator();
		// Iterator<Listitem> it=sel.iterator();
		while (it.hasNext()) {
			Listitem item = (Listitem) it.next();
			JXKH_VoteResult p = (JXKH_VoteResult) item.getValue();
			expertlist.add(i, p);
			i++;
		}
		/*JxYear jy = (JxYear)yearlist.getSelectedItem().getValue();
		String year = jy.getYears();*/
		/*-----------------changed----------------*/
		String year = yearlist.getSelectYear();
		/*-----------------changed----------------*/
		String fileName = year + "管理人员考核结果" + ".xls";
		String Title = "管理人员考核结果";
		List<String> titlelist = new ArrayList<String>();
		titlelist.add("序号");
		titlelist.add("部门");
		titlelist.add("职工号");
		titlelist.add("姓名");
		titlelist.add("性别");
		titlelist.add("所得分数");
		String c[][] = new String[expertlist.size()][titlelist.size()];
		// 从SQL中读数据
		for (int j = 0; j < expertlist.size(); j++) {
			JXKH_VoteResult award = (JXKH_VoteResult) expertlist.get(j);
			String xingbie = "";
			c[j][0] = j + 1 + "";
			c[j][1] = award.getUser().getDept().getKdName();
			c[j][2] = award.getUser().getKuLid();
			c[j][3] = award.getUser().getKuName();
			if (award.getUser().getKuSex().equals("1")) {
				xingbie = "男";
			} else {
				xingbie = "女";
			}
			c[j][4] = xingbie;
			c[j][5] = award.getVrNumber() + "";
		}
		// for(int j=0;j<expertlist.size();j++){
		// JXKH_VoteConfig award=(JXKH_VoteConfig)expertlist.get(j);
		// String xingbie = "";
		// c[j][0]=j+1+"";
		// c[j][1]=((JXKH_VoteResult)award.getVcObject()).getUser().getDept().getKdName();
		// c[j][2]=((JXKH_VoteResult)award.getVcObject()).getUser().getKuLid();
		// c[j][3]=((JXKH_VoteResult)award.getVcObject()).getUser().getKuName();
		//
		// if (((JXKH_VoteResult)award.getVcObject()).getUser().getKuSex().equals("1")) {
		// xingbie="男";
		// } else {
		// xingbie="女";
		// }
		// c[j][4]=xingbie;
		// c[j][5]=((JXKH_VoteResult)award.getVcObject()).getVrNumber() + "";
		// }
		ExportExcel ee = new ExportExcel();
		ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
	}
	//
	// }
}
