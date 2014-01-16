package org.iti.jxkh.score.manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.entity.JXKH_VoteResult;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.iti.jxkh.service.VoteResultService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.entity.WkTUser;

public class ScoreWindow extends BaseWindow {

	private static final long serialVersionUID = -2803419586964832849L;
	private YearListbox yearlist;
	private Listbox listbox;//yearlist
	private VoteResultService voteResultService;
	private AuditConfigService auditConfigService;
	private AuditResultService auditResultService;
	private JXKH_AuditConfig ac;

	public void onClick$compute() throws InterruptedException {
		String year = yearlist.getSelectYear();
		
		auditResultService.deleteAll(auditResultService.findManageByYear(year));
		
		ac = auditConfigService.findByYear(year);
		if(ac == null) {
			Messagebox.show("当年参数还未设置，请先设置参数！");
			return;
		}
		List<JXKH_AuditResult> arlist = new ArrayList<JXKH_AuditResult>();
		List<JXKH_VoteResult> vrlist = voteResultService.findResultByYear(year);
		if(vrlist.size() == 0 ) {
			try {
				Messagebox.show("无当年数据！");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		JXKH_VoteResult vr = vrlist.get(0);
		String[] array = vr.getConfig().getVcWeight().split("-");
		int num = vrlist.size();
		float base = 0f;
		int int1 = (int) ((Float.parseFloat(array[0]) / 100) * num);
		int int2 = (int) ((Float.parseFloat(array[1]) / 100) * num);
		for (int i = 0; i < int1; i++) {
			JXKH_AuditResult ar = new JXKH_AuditResult();
			ar.setKuId(vrlist.get(i).getKuId());
			ar.setArYear(year);
			ar.setArType(JXKH_AuditResult.AR_MANAGE);
			ar.setArScore((float) vrlist.get(i).getVrNumber());
			ar.setArLevel(JXKH_AuditResult.LEVEL_THREE);
			ar.setKdId(vrlist.get(i).getUser().getDept().getKdId());
			base += 1.6f;
			arlist.add(ar);
			auditConfigService.save(ar);
		}
		for (int i = int1; i < int1 + int2; i++) {
			JXKH_AuditResult ar = new JXKH_AuditResult();
			ar.setKuId(vrlist.get(i).getKuId());
			ar.setArYear(year);
			ar.setArType(JXKH_AuditResult.AR_MANAGE);
			ar.setArScore((float) vrlist.get(i).getVrNumber());
			ar.setArLevel(JXKH_AuditResult.LEVEL_TWO);
			ar.setKdId(vrlist.get(i).getUser().getDept().getKdId());
			base += 1.3f;
			arlist.add(ar);
			auditConfigService.save(ar);
		}
		for (int i = int1 + int2; i < vrlist.size(); i++) {
			JXKH_AuditResult ar = new JXKH_AuditResult();
			ar.setKuId(vrlist.get(i).getKuId());
			ar.setArYear(year);
//			ar.setArYear(yearlist.getSelectedItem().getLabel());
			ar.setArType(JXKH_AuditResult.AR_MANAGE);
			ar.setArScore((float) vrlist.get(i).getVrNumber());
			ar.setArLevel(JXKH_AuditResult.LEVEL_ONE);
			ar.setKdId(vrlist.get(i).getUser().getDept().getKdId());
			base += 1.0f;
			arlist.add(ar);
			auditConfigService.save(ar);
		}
		arlist = auditResultService.findManageByYear(year);
		for (JXKH_AuditResult res : arlist) {
			for (WkTRole role : voteResultService.findMyRole(res.getKuId())) {
				System.out.println("user:" + res.getKuId() + "role:" + role.getKrName());
				if (role.getKrName().equals("部门负责人")) {
					if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_ONE) {
						res.setArLevel(JXKH_AuditResult.LEVEL_TWO);
						base += 0.3;
					} else if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_TWO) {
						res.setArLevel(JXKH_AuditResult.LEVEL_THREE);
						base += 0.3;
					} else if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_THREE) {
						res.setArLevel(JXKH_AuditResult.LEVEL_FOUR);
						base += 0.4;
					}
					auditConfigService.update(res);
				}
			}
		}
		base = ac.getAcMoney2() / (base + (1.6f * auditConfigService.findLeader()) / 2);
		ac.setAcBase2(base);
		auditConfigService.update(ac);
		listbox.setModel(new ListModelList(arlist));
	}

	public void initShow() {
		yearlist.initYearListbox("");
		listbox.setItemRenderer(new ListitemRenderer() {//渲染

			public void render(Listitem arg0, Object arg1) throws Exception {
				JXKH_AuditResult arg = (JXKH_AuditResult) arg1;
				WkTUser user = (WkTUser) auditConfigService.loadById(WkTUser.class, arg.getKuId());
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");//序号
				Listcell c1 = new Listcell(user.getKuName());//名称
				Listcell c2 = new Listcell(user.getKuLid());//职工号
				Listcell c3 = new Listcell();//性别
				if (user.getKuSex().equals("1")) {
					c3.setLabel("男");
				} else {
					c3.setLabel("女");
				}
				Listcell c4 = new Listcell(user.getDept().getKdName());//所在部门
				Listcell c5 = new Listcell(arg.getArLevel() + "档");//所在档次
				Listcell c6 = new Listcell();//系数
				float money = 0f;
				if (arg.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_ONE) {
					money = ac.getAcBase2() * 1.0f;
					c6.setLabel(1.0 + "");
				} else if (arg.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_TWO) {
					money = ac.getAcBase2() * 1.3f;
					c6.setLabel(1.3 + "");
				} else if (arg.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_THREE) {
					money = ac.getAcBase2() * 1.6f;
					c6.setLabel(1.6 + "");
				} else if (arg.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_FOUR) {
					money = ac.getAcBase2() * 2.0f;
					c6.setLabel(2.0 + "");
				}
				Listcell c7 = new Listcell(ac.getAcBase2() + "");//基数额度
				Listcell c8 = new Listcell();//所分奖金
				c8.setLabel(money + "");
				arg.setArMoney(money);
				auditConfigService.update(arg);
				arg0.setValue(arg1);
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);
				arg0.appendChild(c8);
			}
		});
	}

	public void initWindow() {
	}
	public void onClick$expor1() throws WriteException, IOException {//导出不用看
			int i=0;
			 ArrayList<JXKH_AuditResult>  expertlist=new ArrayList<JXKH_AuditResult>();
			@SuppressWarnings("unchecked")
			Iterator<Listitem> it=listbox.getItems().iterator();
			System.out.println(it);
		     while(it.hasNext()){
		    	 Listitem item=(Listitem)it.next();
		    	 System.out.println(item.getValue());
		    	 JXKH_AuditResult p=(JXKH_AuditResult) item.getValue();
		    	 expertlist.add(i, p);
		    	 i++;
		     }
		        Date now=new Date();
		    	String fileName = ConvertUtil.convertDateString(now)+"管理人员评分结果"+".xls";
		    	String Title = "管理人员评分结果";
		    	List<String> titlelist=new ArrayList<String>();
				titlelist.add("序号");
				titlelist.add("姓名");
				titlelist.add("职工号");
				titlelist.add("性别");
				titlelist.add("所在部门");
				titlelist.add("所在档次");
				titlelist.add("系数");
				titlelist.add("基数额度");
				titlelist.add("所分奖金");
				String c[][]=new String[expertlist.size()][titlelist.size()];
				//从SQL中读数据
				System.out.println(expertlist);
				for(int j=0;j<expertlist.size();j++){
					JXKH_AuditResult award=(JXKH_AuditResult)expertlist.get(j);		
					WkTUser user = (WkTUser) auditConfigService.loadById(WkTUser.class, award.getKuId());
					    c[j][0] = j + 1 + "";
					    c[j][1] = user.getKuName();
						c[j][2] = user.getKuLid();
						String xingbie;
						if (user.getKuSex().equals("1")) {
							xingbie = "男";
						} else {
							xingbie = "女";
						}
						c[j][3] = xingbie;
						c[j][4] = user.getDept().getKdName();
						float money = 0f;
						String xishu = null;
						if (award.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_ONE) {
							money = ac.getAcBase2() * 1.0f;
							xishu = 1.0 + "";
						} else if (award.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_TWO) {
							money = ac.getAcBase2() * 1.3f;
							xishu = 1.3 + "";
						} else if (award.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_THREE) {
							money = ac.getAcBase2() * 1.6f;
							xishu = 1.6 + "";
						} else if (award.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_FOUR) {
							money = ac.getAcBase2() * 2.0f;
							xishu = 2.0 + "";
						}
					    c[j][5] = award.getArLevel() + "档";
						c[j][6] = xishu;
						c[j][7] = ac.getAcBase2() + "";
						c[j][8] = money + "";
					}
				ExportExcel ee = new ExportExcel();
				ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
}
