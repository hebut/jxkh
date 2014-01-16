package org.iti.jxkh.indicator.businessdept;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.Jxkh_IndicatorHistory;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class HistoryStatisticWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private Toolbarbutton search;
	private YearListbox year;
	//private Listbox data;
	private Listitem indicator;
	private List<Jxkh_IndicatorHistory> iHistory = new ArrayList<Jxkh_IndicatorHistory>();
	
	BusinessIndicatorService businessIndicatorService;
	
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);	
		initListbox();
	}
	
	public void initListbox() {
		year.initYearListbox("");
		//year.initYearlist();
	}
	
	public void onClick$search() {
		iHistory.clear();
		indicator.getChildren().clear();
		//JxYear yearEntity = (JxYear)year.getSelectedItem().getValue();
		String yearString = year.getSelectYear();
		loadIndicatorHistory(yearString);		
	}
	
	public void loadIndicatorHistory(String yearString) {
		
		//һ��ָ����ʷ
		List<Jxkh_IndicatorHistory> first = new ArrayList<Jxkh_IndicatorHistory>();
		first = businessIndicatorService.findHistoryByTimeAndPid(yearString, 0L);
		if(first.size() == 0) {
			for(int k=Integer.valueOf(yearString).intValue()-1;k>=2002;k--) {
				List<Jxkh_IndicatorHistory> yearlist = businessIndicatorService.findHistoryByTimeAndPid(String.valueOf(k), 0L);
				if(yearlist.size() != 0) {
					first = yearlist;
					break;
				}
			}
			if(first.size() == 0 ) {
				try {
					Messagebox.show("�޵���ָ����ʷ��¼!");
				} catch (InterruptedException e) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
					e.printStackTrace();
				}
				indicator.setVisible(false);				
			}else {
				loadHistory(first,yearString);
			}
		}else {
			loadHistory(first,yearString);
			/**
			 * ��һ����ʾ���
			 *//*
			*//**��һ��Listbox*//*
			indicator.setVisible(true);
			Listcell c1 = new Listcell();
			c1.setLabel(yearString);
			this.indicator.appendChild(c1);
			Listcell c2 = new Listcell();
			*//**
			 * �ڶ�����ʾָ��ľ������
			 *//*
			for(int i=0;i<first.size();i++) {
				Jxkh_IndicatorHistory firstIndicator = first.get(i);
				*//**�ڶ���lisbox*//*
				Listbox indicatorListbox = new Listbox();
				indicatorListbox.setSclass("new_listbox");
				indicatorListbox.setStyle("boder:0");
				
				//��ͷ
				Listhead head = new Listhead();
				Listheader header1 = new Listheader();
				header1.setWidth("20%");			
				Listheader header2 = new Listheader();
				header2.setWidth("15%");
				Listheader header3 = new Listheader();
				header3.setWidth("65%");
				head.appendChild(header1);
				head.appendChild(header2);	
				head.appendChild(header3);			
				
				//���������
				Listitem listitem = new Listitem();
				Listcell ci1 = new Listcell();
				ci1.setLabel(firstIndicator.getKbName());			
				Listcell ci2 = new Listcell();
				ci2.setLabel(firstIndicator.getKbScore().toString()+"��/"+firstIndicator.getKbMeasure());
				Listcell ci3 = new Listcell();
				*//**������Listbox*//*
				List<Jxkh_IndicatorHistory> indicatorList3 = new ArrayList<Jxkh_IndicatorHistory>();
				indicatorList3.clear();
				Listbox indicatorListbox3 = new Listbox();	
				indicatorListbox3.setItemRenderer(new ListitemRenderer() {
					public void render(Listitem arg0, Object arg1) throws Exception {
						Jxkh_IndicatorHistory inh = (Jxkh_IndicatorHistory)arg1;
						arg0.setValue(inh);
						Listcell c31 = new Listcell(inh.getKbName());
						Listcell c32 = new Listcell(inh.getKbIndex()+"");
						arg0.appendChild(c31);
						arg0.appendChild(c32);
					}
				});
				//indicatorListbox3 = new Listbox();
				indicatorListbox3.setSclass("new_listbox");
				indicatorListbox3.setStyle("boder:0");
				
				//��ͷ
				Listhead head3 = new Listhead();
				Listheader header31 = new Listheader();
				header31.setWidth("70%");
				Listheader header32 = new Listheader();
				header32.setWidth("30%");
				head3.appendChild(header31);
				head3.appendChild(header32);
				
				//��������
//				indicatorListbox3.setItemRenderer(new ListitemRenderer() {
//					public void render(Listitem arg0, Object arg1) throws Exception {
//						Jxkh_IndicatorHistory inh = (Jxkh_IndicatorHistory)arg1;
//						arg0.setValue(inh);
//						Listcell c31 = new Listcell(inh.getKbName());
//						Listcell c32 = new Listcell(inh.getKbIndex()+"");
//						arg0.appendChild(c31);
//						arg0.appendChild(c32);
//					}
//				});
				//����ָ����ʷ
				List<Jxkh_IndicatorHistory> second = businessIndicatorService.findHistoryByTimeAndPid(yearString,firstIndicator.getKbId()); 
				if(second.size() != 0) {
					for(int j=0;j<second.size();j++) {
						Jxkh_IndicatorHistory secondIndicator = second.get(j);
						//����ָ����ʷ
						List<Jxkh_IndicatorHistory> third = businessIndicatorService.findHistoryByTimeAndPid(yearString,secondIndicator.getKbId());
						indicatorList3.addAll(third);						
					}
					indicatorListbox3.setModel(new ListModelList(indicatorList3));
				}
				
				*//**������Listbox*//*
				indicatorListbox3.appendChild(head3);
				
				//��������listbox��ci2��
				ci3.appendChild(indicatorListbox3);			
				
				
				*//**�ڶ���lisbox*//*
				indicatorListbox.appendChild(head);
				listitem.appendChild(ci1);
				listitem.appendChild(ci2);
				listitem.appendChild(ci3);
				indicatorListbox.appendChild(listitem);			
				//���ڶ���listbox��c2��
				c2.appendChild(indicatorListbox);			
			}
			*//**��һ��Listbox*//*
			this.indicator.appendChild(c2);	*/
		}		
	}
	
	private void loadHistory(List<Jxkh_IndicatorHistory> first,String yearString) {
		//�����һ��ָ��
		iHistory.addAll(first);
		/**
		 * ��һ����ʾ���
		 */
		/**��һ��Listbox*/
		indicator.setVisible(true);
		Listcell c1 = new Listcell();
		c1.setLabel(yearString);
		this.indicator.appendChild(c1);
		Listcell c2 = new Listcell();
		/**
		 * �ڶ�����ʾָ��ľ������
		 */
		for(int i=0;i<first.size();i++) {
			Jxkh_IndicatorHistory firstIndicator = first.get(i);
			/**�ڶ���lisbox*/
			Listbox indicatorListbox = new Listbox();
			indicatorListbox.setSclass("new_listbox");
			indicatorListbox.setStyle("boder:0");
			
			//��ͷ
			Listhead head = new Listhead();
			Listheader header1 = new Listheader();
			header1.setWidth("20%");			
			Listheader header2 = new Listheader();
			header2.setWidth("15%");
			Listheader header3 = new Listheader();
			header3.setWidth("65%");
			head.appendChild(header1);
			head.appendChild(header2);	
			head.appendChild(header3);			
			
			//���������
			Listitem listitem = new Listitem();
			Listcell ci1 = new Listcell();
			ci1.setLabel(firstIndicator.getKbName());			
			Listcell ci2 = new Listcell();
			ci2.setLabel(firstIndicator.getKbScore().toString()+"��/"+firstIndicator.getKbMeasure());
			Listcell ci3 = new Listcell();
			/**������Listbox*/
			List<Jxkh_IndicatorHistory> indicatorList3 = new ArrayList<Jxkh_IndicatorHistory>();
			indicatorList3.clear();
			Listbox indicatorListbox3 = new Listbox();	
			indicatorListbox3.setItemRenderer(new ListitemRenderer() {
				public void render(Listitem arg0, Object arg1) throws Exception {
					Jxkh_IndicatorHistory inh = (Jxkh_IndicatorHistory)arg1;
					arg0.setValue(inh);
					Listcell c31 = new Listcell(inh.getKbName());
					Listcell c32 = new Listcell(inh.getKbIndex()+"");
					arg0.appendChild(c31);
					arg0.appendChild(c32);
				}
			});
			//indicatorListbox3 = new Listbox();
			indicatorListbox3.setSclass("new_listbox");
			indicatorListbox3.setStyle("boder:0");
			
			//��ͷ
			Listhead head3 = new Listhead();
			Listheader header31 = new Listheader();
			header31.setWidth("70%");
			Listheader header32 = new Listheader();
			header32.setWidth("30%");
			head3.appendChild(header31);
			head3.appendChild(header32);
			
			//��������
//			indicatorListbox3.setItemRenderer(new ListitemRenderer() {
//				public void render(Listitem arg0, Object arg1) throws Exception {
//					Jxkh_IndicatorHistory inh = (Jxkh_IndicatorHistory)arg1;
//					arg0.setValue(inh);
//					Listcell c31 = new Listcell(inh.getKbName());
//					Listcell c32 = new Listcell(inh.getKbIndex()+"");
//					arg0.appendChild(c31);
//					arg0.appendChild(c32);
//				}
//			});
			//����ָ����ʷ
			List<Jxkh_IndicatorHistory> second = businessIndicatorService.findHistoryByTimeAndPid(yearString,firstIndicator.getKbId()); 
			if(second.size() != 0) {
				//����ڶ���ָ����ʷ
				iHistory.addAll(second);
				for(int j=0;j<second.size();j++) {
					Jxkh_IndicatorHistory secondIndicator = second.get(j);
					//����ָ����ʷ
					List<Jxkh_IndicatorHistory> third = businessIndicatorService.findHistoryByTimeAndPid(yearString,secondIndicator.getKbId());
					//�����������ָ����ʷ
					iHistory.addAll(third);
					indicatorList3.addAll(third);						
				}
				indicatorListbox3.setModel(new ListModelList(indicatorList3));
			}
			
			/**������Listbox*/
			indicatorListbox3.appendChild(head3);
			
			//��������listbox��ci2��
			ci3.appendChild(indicatorListbox3);			
			
			
			/**�ڶ���lisbox*/
			indicatorListbox.appendChild(head);
			listitem.appendChild(ci1);
			listitem.appendChild(ci2);
			listitem.appendChild(ci3);
			indicatorListbox.appendChild(listitem);			
			//���ڶ���listbox��c2��
			c2.appendChild(indicatorListbox);			
		}
		/**��һ��Listbox*/
		this.indicator.appendChild(c2);	
	}
	
	public void loadPresentIndicator(String yearString) {
		/**
		 * ��һ����ʾ���
		 */
		/**��һ��Listbox*/
		Listcell c1 = new Listcell();
		c1.setLabel(yearString);
		this.indicator.appendChild(c1);
		Listcell c2 = new Listcell();
		/**
		 * �ڶ�����ʾָ��ľ������
		 */
		//һ��ָ����ʷ
		List<Jxkh_IndicatorHistory> first = businessIndicatorService.findHistoryByTimeAndPid(yearString,0L);
		if(first.size() == 0) {
			try {
				Messagebox.show("�޵���ָ����ʷ��¼!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}		
		for(int i=0;i<first.size();i++) {
			Jxkh_IndicatorHistory firstIndicator = first.get(i);
			/**�ڶ���lisbox*/
			Listbox indicatorListbox = new Listbox();
			indicatorListbox.setSclass("new_listbox");
			indicatorListbox.setStyle("boder:0");
			
			//��ͷ
			Listhead head = new Listhead();
			Listheader header1 = new Listheader();
			header1.setWidth("20%");			
			Listheader header2 = new Listheader();
			header2.setWidth("15%");
			Listheader header3 = new Listheader();
			header1.setWidth("65%");
			head.appendChild(header1);
			head.appendChild(header2);	
			head.appendChild(header3);	
			
			//���������
			Listitem listitem = new Listitem();
			Listcell ci1 = new Listcell();
			ci1.setLabel(firstIndicator.getKbScore().toString()+"��/"+firstIndicator.getKbMeasure());
			
			Listcell ci2 = new Listcell();
			/**������Listbox*/
			Listbox indicatorListbox3 = new Listbox();
			indicatorListbox3.setSclass("new_listbox");
			indicatorListbox3.setStyle("boder:0");
			
			//��ͷ
			Listhead head3 = new Listhead();
			Listheader header31 = new Listheader();
			header31.setWidth("70%");
			Listheader header32 = new Listheader();
			header32.setWidth("30%");
			head3.appendChild(header31);
			head3.appendChild(header32);
			
			//��������
			indicatorListbox3.setItemRenderer(new ListitemRenderer() {
				public void render(Listitem arg0, Object arg1) throws Exception {
					Jxkh_IndicatorHistory inh = (Jxkh_IndicatorHistory)arg1;
					arg0.setValue(inh);
					Listcell c31 = new Listcell(inh.getKbName());
					Listcell c32 = new Listcell(inh.getKbIndex()+"");
					arg0.appendChild(c31);
					arg0.appendChild(c32);
				}
			});
			//����ָ����ʷ
			List<Jxkh_IndicatorHistory> second = businessIndicatorService.findHistoryByTimeAndPid(yearString,firstIndicator.getKbId()); 
			if(second.size() != 0) {
				for(int j=0;j<second.size();j++) {
					Jxkh_IndicatorHistory secondIndicator = second.get(j);
					//����ָ����ʷ
					List<Jxkh_IndicatorHistory> third = businessIndicatorService.findHistoryByTimeAndPid(yearString,secondIndicator.getKbId());
					indicatorListbox3.setModel(new ListModelList(third));
				}
			}
			
			/**������Listbox*/
			indicatorListbox3.appendChild(head3);
			//��������listbox��ci2��
			ci2.appendChild(indicatorListbox3);			
			
			
			/**�ڶ���lisbox*/
			indicatorListbox.appendChild(head);
			listitem.appendChild(ci1);
			listitem.appendChild(ci2);
			indicatorListbox.appendChild(listitem);			
			//���ڶ���listbox��c2��
			c2.appendChild(indicatorListbox);			
		}
		/**��һ��Listbox*/
		this.indicator.appendChild(c2);	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onClick$export() throws WriteException, IOException {
		String nowYear = "";
		//JxYear yearEntity = (JxYear)year.getSelectedItem().getValue();
		String yearString = year.getSelectYear();
		nowYear = yearString;
		List<Jxkh_IndicatorHistory> list3 = new ArrayList<Jxkh_IndicatorHistory>();
		if(iHistory.size() != 0) {
			list3.addAll(iHistory);
		}else {
			//һ��ָ����ʷ
//			List<Jxkh_IndicatorHistory> first = new ArrayList<Jxkh_IndicatorHistory>();			
			List<Jxkh_IndicatorHistory> f = businessIndicatorService.findHistoryByTimeAndPid(yearString, 0L);
			List<Jxkh_IndicatorHistory> yearlist = new ArrayList<Jxkh_IndicatorHistory>();			
			if(f.size() == 0) {
				for(int k=Integer.valueOf(yearString).intValue()-1;k>=2002;k--) {
					yearlist = businessIndicatorService.findHistoryByTimeAndPid(String.valueOf(k), 0L);
					//�ж��Ƿ��и�������ͬ����ʷ��¼������о�����forѭ��
					if(yearlist.size() != 0) {
						f = yearlist;
						yearString = k+"";
						break;
					}
				}
				//�ж��Ƿ��и�������ͬ����ʷ��¼
				if(f.size() == 0) {
					try {
						Messagebox.show("�޵���ָ����ʷ��¼!");
					} catch (InterruptedException e) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
						e.printStackTrace();
					}
				}else {
					list3=getHistoryList(yearString);
				}				
				
			}else {
				list3=getHistoryList(yearString);
			}
		}
		
		
		//list3 =this.list;
		if(list3 == null || list3.size() == 0){
			 try {
	 				Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
	 			} catch (InterruptedException e) {
	 				e.printStackTrace();
	 			}
	 			  return;
		}else {
			@SuppressWarnings("unused")
			String state = "";
			String fileName = nowYear+"��ָ����ʷ.xls";
			String Title = nowYear+"��ָ����ʷ";
			@SuppressWarnings("unused")
			WritableWorkbook workbook;
			List titlelist=new ArrayList();
			titlelist.add("���");
			titlelist.add("ָ������");
			titlelist.add("������ָ��");				
			titlelist.add("ָ���ֵ");//���磺120/ƪ
			titlelist.add("ָ��Ȩ��");
			titlelist.add("ָ�����ʱ��");
			String c[][]=new String[list3.size()][titlelist.size()];
			//��SQL�ж�����
			for(int j=0;j<list3.size();j++){
				Jxkh_IndicatorHistory jb = (Jxkh_IndicatorHistory)list3.get(j);				
			    c[j][0]=j+1+"";
				c[j][1]=jb.getKbName();
				Jxkh_IndicatorHistory pjb = new Jxkh_IndicatorHistory();
				List<Jxkh_IndicatorHistory> plist = businessIndicatorService.findByid(jb.getKbPid());
				if(plist.size() != 0) {
					pjb = plist.get(0);
				}
			    c[j][2]=pjb.getKbName();
				c[j][3]=jb.getKbScore()+"/"+jb.getKbMeasure();
				c[j][4]=jb.getKbIndex()+"";
				c[j][5]=jb.getKbTime();
			}
	         ExportExcel ee=new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list3.size(), c);
			
		}
	}
	
	private List<Jxkh_IndicatorHistory> getHistoryList(String yearString) {
		List<Jxkh_IndicatorHistory> inlist = businessIndicatorService.findHistoryByTimeAndPid(yearString, null);
		return inlist;
	}

}
