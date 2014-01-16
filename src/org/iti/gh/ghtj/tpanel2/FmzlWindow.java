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

import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhQt;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.QtService;
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


public class FmzlWindow extends Window implements AfterCompose {

	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short type=(Short)Executions.getCurrent().getArg().get("type");
	Listbox fm,qt;
	FmzlService fmzlService;
	QtService qtService;
	JslwService jslwService;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	public void initWindow(){
		if(type.shortValue()==(short)1){
			qt.setVisible(false);
			final List list=fmzlService.findSumKdId(kdid,GhFmzl.AUDIT_YES);
			fm.setModel(new ListModelList(list));
			fm.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					GhFmzl xm=(GhFmzl)arg1;
					Listcell c2=new Listcell(xm.getFmMc());
					Listcell c3=new Listcell(xm.getFmSj());
					Listcell c4=new Listcell(xm.getFmSqh());
					Listcell c5=new Listcell(xm.getFmInventor().trim());
//					String mc=(String)arg1;
					List fmlist=jslwService.findByLwidAndType(xm.getFmId(),GhJslw.FMZL);
//					GhFmzl xm=(GhFmzl)fmlist.get(0);
//					Listcell c2=new Listcell(xm.getFmMc());
//					Listcell c3=new Listcell(xm.getFmSj());
//					Listcell c4=new Listcell(xm.getFmSqh());
					String name=null;
					if(fmlist!=null&&fmlist.size()>0){
						for(int i=0;i<fmlist.size();i++){
							GhJslw fm=(GhJslw)fmlist.get(i);
							name=name+","+fm.getUser().getKuName();
						}
						name=name.split("null,")[1];
					}
//					Listcell c5=new Listcell(name);
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
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
						filename = "发明专利.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("发明专利", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						wsheet.setColumnView(3, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < fm.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) fm.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							GhFmzl xm=(GhFmzl)list.get(i);
							List fmlist=jslwService.findByLwidAndType(xm.getFmId(),GhJslw.FMZL);
//							GhFmzl xm=(GhFmzl)fmlist.get(0);
//							
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getFmMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getFmSj(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getFmSqh(),titleFormat);
							String name=null;
							for(int j=0;j<fmlist.size();j++){
								GhJslw fm=(GhJslw)fmlist.get(j);
								name=name+","+fm.getUser().getKuName();
							}
							name=name.split("null,")[1];
							Label content5 = new Label(4, i + 1, name, titleFormat);
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
			fm.setVisible(false);
			final List list=qtService.findByKdId(kdid);
			qt.setModel(new ListModelList(list));
			qt.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
//					GhQt xm=(GhQt)arg1;
//					Listcell c2=new Listcell(xm.getQtMc());
//					Listcell c3=new Listcell(xm.getQtSj());
//					Listcell c4=new Listcell(xm.getQtBz());
//					Listcell c5=new Listcell(xm.getUser().getKuName());
	
					String mc=(String)arg1;
					List qtlist=qtService.findByMc(mc);
					GhQt xm=(GhQt)qtlist.get(0);
					Listcell c2=new Listcell(xm.getQtMc());
					Listcell c3=new Listcell(xm.getQtSj());
					Listcell c4=new Listcell(xm.getQtBz());
					String name=null;
					for(int i=0;i<qtlist.size();i++){
						GhQt fm=(GhQt)qtlist.get(i);
						name=name+","+fm.getUser().getKuName();
					}
					name=name.split("null,")[1];
					Listcell c5=new Listcell(name);
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
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
						filename = "其他.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("其他", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						wsheet.setColumnView(3, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < qt.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) qt.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							String mc=(String)list.get(i);
							List fmlist=qtService.findByMc(mc);
							GhQt xm=(GhQt)fmlist.get(0);
							
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getQtMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getQtSj(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getQtBz(),titleFormat);
							String name=null;
							for(int j=0;j<fmlist.size();j++){
								GhQt fm=(GhQt)fmlist.get(j);
								name=name+","+fm.getUser().getKuName();
							}
							name=name.split("null,")[1];
							Label content5 = new Label(4, i + 1, name, titleFormat);
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
