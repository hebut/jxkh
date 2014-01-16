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

import org.iti.gh.entity.GhPx;
import org.iti.gh.entity.GhSk;
import org.iti.gh.entity.GhZs;
import org.iti.gh.service.PxService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.ZsService;
import org.zkoss.zk.ui.Components;
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

public class PxskWindow extends Window implements AfterCompose {

	Listbox px,sk,zs;
	Long kdid;
	Integer type;
	public Long getKdid() {
		return kdid;
	}
	public void setKdid(Long kdid) {
		this.kdid = kdid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	PxService pxService;
	SkService skService;
	ZsService zsService;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	public void initWindow(){
		if(type==1){
			sk.setVisible(false);
			zs.setVisible(false);
			final List list=pxService.findByKdidAndState(kdid, GhPx.AUDIT_YES);
			px.setModel(new ListModelList(list));
			px.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhPx xm=(GhPx)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(xm.getPxMc());
					Listcell c3=new Listcell(xm.getPxDw());
					Listcell c4=new Listcell(xm.getPxJx());
//					Listcell c5=new Listcell(xm.getPxBrzy());
//					Listcell c6=new Listcell(xm.getUser().getKuName());
//					
//					arg0.appendChild(c1);arg0.appendChild(c6);
//					arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
//					arg0.appendChild(c5);
//					String mc=(String)arg1;
					List pxlist=pxService.findByMc(xm.getPxMc());
//					GhPx xm=(GhPx)pxlist.get(0);
//					Listcell c1=new Listcell(arg0.getIndex()+1+"");
//					Listcell c2=new Listcell(xm.getPxMc());
//					Listcell c3=new Listcell(xm.getPxDw());
//					Listcell c4=new Listcell(xm.getPxJx());
					String name=null;
					for(int i=0;i<pxlist.size();i++){
						GhPx px=(GhPx)pxlist.get(i);
						name=name+","+px.getUser().getKuName();
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
						filename = "培训情况.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("培训", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						wsheet.setColumnView(2, 30);
						wsheet.setColumnView(3, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < px.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) px.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							String mc=(String)list.get(i);
							List fmlist=pxService.findByMc(mc);
							GhPx xm=(GhPx)fmlist.get(0);
							
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getPxMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getPxDw(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getPxJx(),titleFormat);
							String name=null;
							for(int j=0;j<fmlist.size();j++){
								GhPx fm=(GhPx)fmlist.get(j);
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
		}else if(type==2){
			px.setVisible(false);
			zs.setVisible(false);
			final List list=skService.findByKdId(kdid);
			sk.setModel(new ListModelList(list));
			sk.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
//					GhSk xm=(GhSk)arg1;
//					Listcell c1=new Listcell(arg0.getIndex()+1+"");
//					Listcell c2=new Listcell(xm.getUser().getKuName());
//					Listcell c3=new Listcell(xm.getSkMc());
//					Listcell c4=new Listcell(xm.getSkZy());
//					Listcell c5=new Listcell(xm.getSkXs());
//					
//					arg0.appendChild(c1);
//					arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
//					arg0.appendChild(c5);
					String mc=(String)arg1;
					List sklist=skService.findByMc(mc);
					GhSk xm=(GhSk)sklist.get(0);
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(xm.getSkMc());
					Listcell c3=new Listcell(xm.getSkZy());
					Listcell c4=new Listcell(xm.getSkXs());
					String name=null;
					for(int i=0;i<sklist.size();i++){
						GhSk sk=(GhSk)sklist.get(i);
						name=name+","+sk.getUser().getKuName();
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
						filename = "授课情况.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("授课", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < sk.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) sk.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							String mc=(String)list.get(i);
							List fmlist=skService.findByMc(mc);
							GhSk xm=(GhSk)fmlist.get(0);
							
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getSkMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getSkZy(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getSkXs(),titleFormat);
							String name=null;
							for(int j=0;j<fmlist.size();j++){
								GhSk fm=(GhSk)fmlist.get(j);
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
		}else{
			px.setVisible(false);
			sk.setVisible(false);
			final List list=zsService.findByKdId(kdid);
			zs.setModel(new ListModelList(list));
			zs.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhZs xm=(GhZs)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(xm.getUser().getKuName());
					Listcell c3=new Listcell(xm.getYear()+"");
					Listcell c4=new Listcell(xm.getNum()+"");
					Listcell c5=new Listcell(xm.getName()+"");
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);arg0.appendChild(c5);
//					arg0.appendChild(c6);arg0.appendChild(c7);arg0.appendChild(c8);arg0.appendChild(c9);arg0.appendChild(c10);
//					arg0.appendChild(c11);arg0.appendChild(c12);
				}
			});
			Caption c=new Caption();
			Button daochu = new Button();
			daochu.setLabel("导出Excel ");
			daochu.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					String filename;
					try {
						filename = "招生情况.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("招生", 0); // 工作表名称
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < zs.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) zs.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							GhZs xm=(GhZs)list.get(i);
							
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getUser().getKuName(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getYear()+"",titleFormat);
							Label content4 = new Label(3, i + 1, xm.getNum()+"",titleFormat);
							Label content5 = new Label(4, i + 1, xm.getName()+"" ,titleFormat);
//							Label content6 = new Label(5, i + 1, xm.getYear2009()+"" ,titleFormat);
//							Label content7 = new Label(6, i + 1, xm.getYear2010()+"" ,titleFormat);
//							Label content8 = new Label(7, i + 1, xm.getYear2011()+"" ,titleFormat);
//							Label content9 = new Label(8, i + 1, xm.getYear2012()+"" ,titleFormat);
//							Label content10 = new Label(9, i + 1, xm.getYear2013()+"" ,titleFormat);
//							Label content11 = new Label(10, i + 1, xm.getYear2014()+"" ,titleFormat);
//							Label content12 = new Label(11, i + 1, xm.getYear2015()+"" ,titleFormat);
//							
							
							wsheet.addCell(content1);
							wsheet.addCell(content2);
							wsheet.addCell(content3);
							wsheet.addCell(content4);
							wsheet.addCell(content5);
//							wsheet.addCell(content6);
//							wsheet.addCell(content7);
//							wsheet.addCell(content8);
//							wsheet.addCell(content9);
//							wsheet.addCell(content10);
//							wsheet.addCell(content11);
//							wsheet.addCell(content12);
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
