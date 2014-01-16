package org.iti.jxkh.score.leader;

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
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class ShowWindow extends BaseWindow {
	private static final long serialVersionUID = 7709604284024931540L;
	private AuditResultService auditResultService;
	private AuditConfigService auditConfigService;
	private YearListbox yearlist;
	private Listbox listbox;
	private float base;

	public void initShow() {
		yearlist.initYearListbox("");
		listbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				JXKH_AuditResult arg = (JXKH_AuditResult) arg1;
				arg0.setValue(arg);
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");
				WkTUser user = (WkTUser) auditConfigService.loadById(WkTUser.class, arg.getKuId());
				Listcell c1 = new Listcell(user.getKuName());
				Listcell c2 = new Listcell(user.getKuLid());
				Listcell c3 = new Listcell();
				if (user.getKuSex().equals("1")) {
					c3.setLabel("男");
				} else {
					c3.setLabel("女");
				}
				Listcell c4 = new Listcell(user.getDept().getKdName());
				Listcell c5 = new Listcell();
				Listcell c6 = new Listcell();
				if (arg.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_GOOD) {
					c5.setLabel("优秀");
					c6.setLabel("1.3");
				} else if (arg.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_PASS) {
					c5.setLabel("合格");
					c6.setLabel("1.0");
				} else if (arg.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_FAIL) {
					c5.setLabel("不合格");
					c6.setLabel("0.0");
				}
				Listcell c7 = new Listcell(base+"");
				Listcell c8 = new Listcell(arg.getArMoney() + "");
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

	public void onClick$submit() throws InterruptedException {
		if(yearlist.getSelectedIndex() == 0) {
			Messagebox.show("请选择年份！");
			return;
		}
		JXKH_AuditConfig ac = auditConfigService.findByYear(yearlist.getSelectedItem().getLabel());
		if(ac == null) {
			Messagebox.show("还没设置参数，请先设置参数！");
			return;
		}
		int goodNum = 0, passNum = 0;//优秀档次人数，合格档次人数
		List<JXKH_AuditResult> arlist = auditResultService.findLeaderByYear(yearlist.getSelectedItem().getLabel());
	
		for (JXKH_AuditResult ar : arlist) {
			if (ar.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_GOOD) {
				goodNum++;
			} else if (ar.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_PASS) {
				passNum++;
			}
		}
		float total = (ac.getAcBase() * 1.6f + ac.getAcBase2() * 1.6f) * auditConfigService.findLeader();
		base = total / (1.3f * goodNum + passNum);//基数
		for (JXKH_AuditResult ar : arlist) {
			if (ar.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_GOOD) {
				ar.setArMoney(base * 1.3f);
			} else if (ar.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_PASS) {
				ar.setArMoney(base);
			} else if (ar.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_FAIL) {
				ar.setArMoney(0f);
			}
			auditConfigService.update(ar);
		}
		listbox.setModel(new ListModelList(arlist));
	}

	public void initWindow() {
	}
	
	public void onClick$export() throws WriteException, IOException {
//		if(JXKH_AuditResult.getSelectedCount()==0)
//		{
//			try {  
//		         Messagebox.show("提示请选择要导出的数据","提示", Messagebox.OK,  
//		                 Messagebox.EXCLAMATION);  
//		     } catch (InterruptedException e) {  
//		         // ignore  
//		     }  
//			 return;
//		}
//		else
//		{
			int i=0;
			 ArrayList<JXKH_AuditResult>  expertlist=new ArrayList<JXKH_AuditResult>();
			@SuppressWarnings("unchecked")
			Iterator<Listitem> it=listbox.getItems().iterator();
		     while(it.hasNext()){
		    	 Listitem item=(Listitem)it.next();
		    	 JXKH_AuditResult p=(JXKH_AuditResult) item.getValue();
		    	 expertlist.add(i, p);
		    	 i++;
		     }
		        Date now=new Date();
		    	String fileName = ConvertUtil.convertDateString(now)+"院领导评分结果"+".xls";
		    	String Title = "院领导评分结果";
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
				for(int j=0;j<expertlist.size();j++){
					JXKH_AuditResult award=(JXKH_AuditResult)expertlist.get(j);		
					WkTUser user = (WkTUser) auditConfigService.loadById(WkTUser.class, award.getKuId());
					    c[j][0]=j+1+"";
					    c[j][1]=user.getKuName();
						c[j][2]=user.getKuLid();
						String xingbie;
						if (user.getKuSex().equals("1")) {
							xingbie="男";
						} else {
							xingbie="女";
						}
						c[j][3]=xingbie;
						c[j][4]=user.getDept().getKdName();
						String jibie = null;
						String xishu = null;
						if (award.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_GOOD) {
							jibie="优秀";
							xishu="1.3";
						} else if (award.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_PASS) {
							jibie="合格";
							xishu="1.0";
						} else if (award.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_FAIL) {
							jibie="不合格";
							xishu="0.0";
						}
					    c[j][5]=jibie;
						c[j][6]=xishu;
						c[j][7]=base+"";
						c[j][8]=award.getArMoney() + "";
					}
				ExportExcel ee=new ExportExcel();
				ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
//	}
}
