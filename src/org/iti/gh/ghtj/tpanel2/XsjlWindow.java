package org.iti.gh.ghtj.tpanel2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.iti.gh.entity.GhJlhz;
import org.iti.gh.entity.GhJxbg;
import org.iti.gh.entity.GhXshy;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.XshyService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkmax.zul.Filedownload;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

public class XsjlWindow extends Window implements AfterCompose {

	Listbox xshy,jxbg,jlhz;
	
	Short cj=(Short)Executions.getCurrent().getArg().get("cj");
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Integer type=(Integer)Executions.getCurrent().getArg().get("type");
	Short state=(Short)Executions.getCurrent().getArg().get("state");
	JlhzService jlhzService;
	JxbgService jxbgService;
	XshyService xshyService;
	
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	public void initWindow(){
		if(type==1){
			jxbg.setVisible(false);
			jlhz.setVisible(false);
			final List list=xshyService.findSumKdId(kdid, cj,state);
			xshy.setModel(new ListModelList(list));
			xshy.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
//					GhXshy lwzl=(GhXshy)arg1;
//					Listcell c1=new Listcell(arg0.getIndex()+1+"");
//					Listcell c2=new Listcell(lwzl.getHyMc());
//					Listcell c3=new Listcell(lwzl.getHySj());
//					Listcell c4=new Listcell(lwzl.getHyZrs()+"人");
//					Listcell c5=new Listcell(lwzl.getHyJwrs()+"人");
//					Listcell c6=new Listcell(lwzl.getUser().getKuName());
//					arg0.appendChild(c1);arg0.appendChild(c2);
//					arg0.appendChild(c3);arg0.appendChild(c4);
//					arg0.appendChild(c5);arg0.appendChild(c6);
					String mc=(String)arg1;
					List xslist=xshyService.findByMc(mc, cj,state);
					GhXshy lwzl=(GhXshy)xslist.get(0);
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(lwzl.getHyMc());
					Listcell c3=new Listcell(lwzl.getHySj());
					Listcell c4=new Listcell(lwzl.getHyZrs()+"人");
					Listcell c5=new Listcell(lwzl.getHyJwrs()+"人");
					String name=null;
					for(int i=0;i<xslist.size();i++){
						GhXshy xs=(GhXshy)xslist.get(i);
						name=name+","+xs.getUser().getKuName();
					}
					Listcell c6=new Listcell(name.split("null,")[1]);
					arg0.appendChild(c1);arg0.appendChild(c2);
					arg0.appendChild(c3);arg0.appendChild(c4);
					arg0.appendChild(c5);arg0.appendChild(c6);
				}
			});
			
			Caption c=new Caption();
			Button daochu = new Button();
			daochu.setLabel("导出Excel ");
			daochu.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					String filename;
					try {
						filename = "学术会议情况.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("学术会议", 0); // 工作表名称
						wsheet.setColumnView(1, 40);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < xshy.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) xshy.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							String mc=(String)list.get(i);
							List fmlist=xshyService.findByMc(mc, cj,state);
							GhXshy xm=(GhXshy)fmlist.get(0);
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getHyMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getHySj(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getHyZrs()+"",titleFormat);
							Label content5 = new Label(4, i + 1, xm.getHyJwrs()+"",titleFormat);
							String name=null;
							for(int j=0;j<fmlist.size();j++){
								GhXshy fm=(GhXshy)fmlist.get(j);
								name=name+","+fm.getUser().getKuName();
							}
							Label content6 = new Label(5, i + 1, name.split("null,")[1],titleFormat);
							wsheet.addCell(content1);
							wsheet.addCell(content2);
							wsheet.addCell(content3);
							wsheet.addCell(content4);
							wsheet.addCell(content5);
							wsheet.addCell(content6);
						}
						wbook.write(); // 写入文件
						Filedownload.save(new File(filename), null);
						wbook.close();
						stream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			this.appendChild(c);
			this.getCaption().appendChild(daochu);
		}else if(type==2){
			xshy.setVisible(false);
			jlhz.setVisible(false);
			final List list=jxbgService.findByKdIdAndCj(kdid, cj,state);
			jxbg.setModel(new ListModelList(list));
			jxbg.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhJxbg lwzl=(GhJxbg)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(lwzl.getJxJxmc());
					Listcell c3=new Listcell(lwzl.getJxHymc());
					Listcell c4=new Listcell(lwzl.getJxSj());
					Listcell c5=new Listcell(lwzl.getJxBgmc());
					Listcell c6=new Listcell(lwzl.getUser().getKuName());
					arg0.appendChild(c1);arg0.appendChild(c2);
					arg0.appendChild(c3);arg0.appendChild(c4);
					arg0.appendChild(c5);arg0.appendChild(c6);
				}
			});
			
			Caption c=new Caption();
			Button daochu = new Button();
			daochu.setLabel("导出Excel ");
			daochu.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					String filename;
					try {
						filename = "教学报告情况.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("教学报告", 0); // 工作表名称
						wsheet.setColumnView(1, 20);
						wsheet.setColumnView(2, 30);
						wsheet.setColumnView(4, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < jxbg.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) jxbg.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							GhJxbg xm=(GhJxbg)list.get(i);
							
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getJxJxmc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getJxHymc(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getJxSj(),titleFormat);
							Label content5 = new Label(4, i + 1, xm.getJxBgmc(),titleFormat);
							Label content6 = new Label(5, i + 1, xm.getUser().getKuName(),titleFormat);
							wsheet.addCell(content1);
							wsheet.addCell(content2);
							wsheet.addCell(content3);
							wsheet.addCell(content4);
							wsheet.addCell(content5);
							wsheet.addCell(content6);
						}
						wbook.write(); // 写入文件
						Filedownload.save(new File(filename), null);
						wbook.close();
						stream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			this.appendChild(c);
			this.getCaption().appendChild(daochu);
			
		}else{
			xshy.setVisible(false);
			jxbg.setVisible(false);
			final List list=jlhzService.findByKdId(kdid, cj,state);
			jlhz.setModel(new ListModelList(list));
			jlhz.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
//					GhJlhz lwzl=(GhJlhz)arg1;
//					Listcell c1=new Listcell(arg0.getIndex()+1+"");
//					Listcell c2=new Listcell(lwzl.getHzMc());
//					Listcell c3=new Listcell(lwzl.getHzSj());
//					Listcell c4=new Listcell(lwzl.getHzDx());
//					Listcell c5=new Listcell(lwzl.getUser().getKuName());
//					arg0.appendChild(c1);arg0.appendChild(c2);
//					arg0.appendChild(c3);arg0.appendChild(c4);
//					arg0.appendChild(c5);
					String mc=(String)arg1;
					List hzlist=jlhzService.findByMc(mc, cj,state);
					GhJlhz lwzl=(GhJlhz)hzlist.get(0);
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(lwzl.getHzMc());
					Listcell c3=new Listcell(lwzl.getHzSj());
					Listcell c4=new Listcell(lwzl.getHzDx());
					String name=null;
					for(int i=0;i<hzlist.size();i++){
						GhJlhz xs=(GhJlhz)hzlist.get(i);
						name=name+","+xs.getUser().getKuName();
					}
					Listcell c5=new Listcell(name.split("null,")[1]);
					arg0.appendChild(c1);arg0.appendChild(c2);
					arg0.appendChild(c3);arg0.appendChild(c4);
					arg0.appendChild(c5);
				}
			});
			Caption c=new Caption();
			Button daochu = new Button();
			daochu.setLabel("导出Excel ");
			daochu.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					String filename;
					try {
						filename = "交流合作情况.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("交流合作", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						wsheet.setColumnView(2, 20);
						wsheet.setColumnView(3, 40);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < jlhz.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) jlhz.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							String mc=(String)list.get(i);
							List fmlist=jlhzService.findByMc(mc, cj,state);
							GhJlhz xm=(GhJlhz)fmlist.get(0);
							
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getHzMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getHzSj(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getHzDx(),titleFormat);
							String name=null;
							for(int j=0;j<fmlist.size();j++){
								GhJlhz fm=(GhJlhz)fmlist.get(j);
								name=name+","+fm.getUser().getKuName();
							}
							Label content5 = new Label(4, i + 1, name.split("null,")[1],titleFormat);
							wsheet.addCell(content1);
							wsheet.addCell(content2);
							wsheet.addCell(content3);
							wsheet.addCell(content4);
							wsheet.addCell(content5);
						}
						wbook.write(); // 写入文件
						Filedownload.save(new File(filename), null);
						wbook.close();
						stream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			this.appendChild(c);
			this.getCaption().appendChild(daochu);
		}
	}

}
