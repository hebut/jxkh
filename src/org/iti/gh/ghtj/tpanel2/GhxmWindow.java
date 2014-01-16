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

import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.XmService;
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

public class GhxmWindow extends Window implements AfterCompose {
	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
	String progress=(String)Executions.getCurrent().getArg().get("progress");
	Listbox kjxm,jydq,kydq;
	JsxmService jsxmService;
	
	XmService xmService;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);

	}
	public void initWindow(){
		final List list=xmService.findSumKdId(kdid, lx,progress,GhXm.AUDIT_YES);
		if(lx.shortValue()==GhXm.JYXM.shortValue()&&progress.equals(GhXm.PROGRESS_DONE)){
			kjxm.setVisible(false);
			kydq.setVisible(false);
			jydq.setModel(new ListModelList(list));
			jydq.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhXm xm=(GhXm)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(xm.getKyMc());
					Listcell c4=new Listcell(xm.getKyKssj());
					Listcell c5=new Listcell(xm.getKyJssj());
					Listcell c6=new Listcell(xm.getKyJf()+"");
					String mz=null;
					List jsxmlist=jsxmService.findByXmidAndtype(xm.getKyId(),GhJsxm.JYXM);
					for(int i=0;i<jsxmlist.size();i++){
						GhJsxm jsxm=(GhJsxm)jsxmlist.get(i);
						mz=mz+","+jsxm.getUser().getKuName()+"("+(jsxm.getKyGx()==null||jsxm.getKyGx()==-1?0:jsxm.getKyGx())+")";
					}
					Listcell c7=new Listcell(mz.split("null,")[1]);
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c4);
					arg0.appendChild(c5);arg0.appendChild(c6);arg0.appendChild(c7);
				}
			});
			
			Caption c=new Caption();
			Button daochu = new Button();
			daochu.setLabel("导出Excel ");
			daochu.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					String filename;
					try {
						filename = "已完成的教研项目.xls";
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("已完成的教研项目", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < jydq.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) jydq.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							GhXm xm=(GhXm)list.get(i);
							List fmlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.JYXM);
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getKyMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getKyKssj(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getKyJssj(),titleFormat);
							Label content5 = new Label(4, i + 1, xm.getKyJf()+"",titleFormat);
							String name=null;
							for(int j=0;j<fmlist.size();j++){
								GhJsxm fm=(GhJsxm)fmlist.get(j);
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
		}else if(lx.shortValue()==GhXm.KYXM.shortValue()&&progress.equals(GhXm.PROGRESS_DONE)){
			jydq.setVisible(false);
			kjxm.setVisible(false);
			kydq.setModel(new ListModelList(list));
			kydq.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhXm xm=(GhXm)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(xm.getKyMc());
					Listcell c3=new Listcell(xm.getKyLy());
					Listcell c4=new Listcell(xm.getKyKssj());
					Listcell c5=new Listcell(xm.getKyJssj());
					Listcell c6=new Listcell(xm.getKyJf()+"");
					String mz=null;
					List fmlist=jsxmService.findByXmidAndtype(xm.getKyId(),GhJsxm.KYXM);
					for(int i=0;i<fmlist.size();i++){
						GhJsxm lw=(GhJsxm)fmlist.get(i);
						mz=mz+","+lw.getUser().getKuName()+"("+lw.getKyGx()+")";
					}
					Listcell c7=new Listcell(mz.split("null,")[1]);
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
					arg0.appendChild(c5);arg0.appendChild(c6);arg0.appendChild(c7);
				}
			});
			
			Caption c=new Caption();
			Button daochu = new Button();
			daochu.setLabel("导出Excel ");
			daochu.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					String filename;
					try {
						filename = "成果.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("成果", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						wsheet.setColumnView(2, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < kydq.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) kydq.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							GhXm xm=(GhXm)list.get(i);
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getKyMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getKyLy(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getKyKssj(),titleFormat);
							Label content5 = new Label(4, i + 1, xm.getKyJssj(),titleFormat);
							Label content6 = new Label(5, i + 1, xm.getKyJf()+"",titleFormat);
							String mz=null;
							List fmlist=jsxmService.findByXmidAndtype(xm.getKyId(),GhJsxm.KYXM);
							for(int j=0;j<fmlist.size();j++){
								GhJsxm lw=(GhJsxm)fmlist.get(j);
								mz=mz+","+lw.getUser().getKuName()+"("+lw.getKyGx()+")";
							}
							Label content7 = new Label(6, i + 1, mz.split("null,")[1],titleFormat);
							wsheet.addCell(content1);
							wsheet.addCell(content2);
							wsheet.addCell(content3);
							wsheet.addCell(content4);
							wsheet.addCell(content5);
							wsheet.addCell(content6);
							wsheet.addCell(content7);
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
		}else if(lx.shortValue()==GhXm.KYXM.shortValue()&&progress.equals(GhXm.PROGRESS_DO)){
			jydq.setVisible(false);
			kydq.setVisible(false);
			kjxm.setModel(new ListModelList(list));
			kjxm.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhXm xm=(GhXm)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(xm.getKyMc());
					Listcell c3=new Listcell(xm.getKyLy());
					Listcell c4=new Listcell(xm.getKyKssj());
					Listcell c5=new Listcell(xm.getKyJssj());
					Listcell c6=new Listcell(xm.getKyJf()+"");
					String mz="";
					List xmlist=jsxmService.findByXmidAndtype(xm.getKyId(),GhJsxm.KYXM);
//					xmlist.addAll(jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.JYXM));
					for(int i=0;i<xmlist.size();i++){
						GhJsxm jsxm=(GhJsxm)xmlist.get(i);
						mz=mz+jsxm.getUser().getKuName()+"("+(jsxm.getKyGx()==null||jsxm.getKyGx()==-1?"":jsxm.getKyGx())+")";
					}
					Listcell c7=new Listcell(mz);
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
					arg0.appendChild(c5);arg0.appendChild(c6);arg0.appendChild(c7);
				}
			});
			Caption c=new Caption();
			Button daochu = new Button();
			daochu.setLabel("导出Excel ");
			daochu.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					String filename;
					try {
						filename = "成果.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("成果", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						wsheet.setColumnView(2, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);

						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < kjxm.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) kjxm.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							GhXm xm=(GhXm)list.get(i);
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getKyMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getKyLy(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getKyKssj(),titleFormat);
							Label content5 = new Label(4, i + 1, xm.getKyJssj(),titleFormat);
							Label content6 = new Label(5, i + 1, xm.getKyJf()+"",titleFormat);
							String name=null;
							List fmlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.KYXM);
							for(int j=0;j<fmlist.size();j++){
								GhJsxm jsxm=(GhJsxm)fmlist.get(j);
								name=name+","+jsxm.getUser().getKuName()+"("+(jsxm.getKyGx()==null||jsxm.getKyGx()==-1?"":jsxm.getKyGx())+")";
							}
							Label content7 = new Label(6, i + 1, name.split("null,")[1],titleFormat);
							wsheet.addCell(content1);
							wsheet.addCell(content2);
							wsheet.addCell(content3);
							wsheet.addCell(content4);
							wsheet.addCell(content5);
							wsheet.addCell(content6);
							wsheet.addCell(content7);
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
		}else if(lx.shortValue()==GhXm.JYXM.shortValue()&&progress.equals(GhXm.PROGRESS_DO)){
			jydq.setVisible(false);
			kydq.setVisible(false);
			kjxm.setModel(new ListModelList(list));
			kjxm.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhXm xm=(GhXm)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(xm.getKyMc());
					Listcell c3=new Listcell(xm.getKyLy());
					Listcell c4=new Listcell(xm.getKyKssj());
					Listcell c5=new Listcell(xm.getKyJssj());
					Listcell c6=new Listcell(xm.getKyJf()+"");
					String mz="";
					List xmlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.JYXM);
					for(int i=0;i<xmlist.size();i++){
						GhJsxm jsxm=(GhJsxm)xmlist.get(i);
						mz=mz+jsxm.getUser().getKuName()+"("+(jsxm.getKyGx()==null||jsxm.getKyGx().toString().equals("-1")?"":jsxm.getKyGx())+")";
					}
					Listcell c7=new Listcell(mz);
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
					arg0.appendChild(c5);arg0.appendChild(c6);arg0.appendChild(c7);
				}
			});
			Caption c=new Caption();
			Button daochu = new Button();
			daochu.setLabel("导出Excel ");
			daochu.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					String filename;
					try {
						filename = "成果.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("成果", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						wsheet.setColumnView(2, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);

						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < kjxm.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) kjxm.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							GhXm xm=(GhXm)list.get(i);
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getKyMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getKyLy(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getKyKssj(),titleFormat);
							Label content5 = new Label(4, i + 1, xm.getKyJssj(),titleFormat);
							Label content6 = new Label(5, i + 1, xm.getKyJf()+"",titleFormat);
							String name=null;
							List fmlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.JYXM);
							for(int j=0;j<fmlist.size();j++){
								GhJsxm lw=(GhJsxm)fmlist.get(j);
								name=name+","+lw.getUser().getKuName()+"("+(lw.getKyGx()==null||lw.getKyGx().toString().equals("-1")?"":lw.getKyGx())+")";
							}
							Label content7 = new Label(6, i + 1, name.split("null,")[1],titleFormat);
							wsheet.addCell(content1);
							wsheet.addCell(content2);
							wsheet.addCell(content3);
							wsheet.addCell(content4);
							wsheet.addCell(content5);
							wsheet.addCell(content6);
							wsheet.addCell(content7);
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
