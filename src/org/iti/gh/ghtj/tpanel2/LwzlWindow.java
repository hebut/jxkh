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

 
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhJszz;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.JszzService;
import org.iti.gh.service.LwzlService;
import org.iti.gh.service.QklwService;
import org.iti.gh.service.ZzService;
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

public class LwzlWindow extends Window implements AfterCompose {

	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
//	LwzlService lwzlService;
	JslwService jslwService;
	Listbox lw,zz,jc;
	HylwService hylwService;
	QklwService qklwService;
	ZzService zzService;
	JszzService jszzService;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	public void initWindow(){
		if(lx.shortValue()==GhZz.JC.shortValue()){
			lw.setVisible(false);
			zz.setVisible(false);
			final List list=zzService.findSumKdId(kdid, lx,GhZz.AUDIT_YES);
			jc.setModel(new ListModelList(list));
			jc.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhZz lwzl=(GhZz)arg1;					
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(lwzl.getZzMc());
					Listcell c3=new Listcell(lwzl.getZzPublitime());
					Listcell c4=new Listcell(lwzl.getZzKw());
					List lwlist=jszzService.findByLwidAndType(lwzl.getZzId(),GhJszz.JC);
					String mz=null;
					for(int i=0;i<lwlist.size();i++){
						GhJszz lw=(GhJszz)lwlist.get(i);
						mz=mz+","+lw.getUser().getKuName().trim();;
					}
					Listcell c5=new Listcell(mz.split("null,")[1]);
					arg0.appendChild(c1);arg0.appendChild(c2);
					arg0.appendChild(c3);arg0.appendChild(c4);
					arg0.appendChild(c5);
//					arg0.appendChild(c6);arg0.appendChild(c7);
				}
			});
			
			Caption c=new Caption();
			Button daochu = new Button();
			daochu.setLabel("导出Excel ");
			daochu.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					String filename;
					try {
						filename = "教材情况.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("教材情况", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						wsheet.setColumnView(3, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
					
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < jc.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) jc.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							GhZz lwzl=(GhZz)list.get(i);	
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, lwzl.getZzMc(),titleFormat);
							Label content3 = new Label(2, i + 1, lwzl.getZzPublitime(),titleFormat);
							Label content4 = new Label(3, i + 1, lwzl.getZzKw(),titleFormat);
							List lwlist=jszzService.findByLwidAndType(lwzl.getZzId(),GhJszz.JC);
							String name=null;
							for(int j=0;j<lwlist.size();j++){
								GhJszz lw=(GhJszz)lwlist.get(j);
								name=name+","+lw.getUser().getKuName();
							}
							Listcell c5=new Listcell(name.split("null,")[1]);
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
			
		}else if(lx.shortValue()==GhZz.ZZ.shortValue()){
			lw.setVisible(false);
			jc.setVisible(false);
			final List list=zzService.findSumKdId(kdid, lx,GhZz.AUDIT_YES);
			zz.setModel(new ListModelList(list));
			zz.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhZz lwzl=(GhZz)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(lwzl.getZzMc());
					Listcell c3=new Listcell(lwzl.getZzPublitime());
					Listcell c4=new Listcell(lwzl.getZzKw());
					List wlist=jszzService.findByLwidAndType(lwzl.getZzId(),GhJszz.ZZ);
					String mz=null;
					for(int i=0;i<wlist.size();i++){
						GhJszz lw=(GhJszz)wlist.get(i);
						mz=mz+","+lw.getUser().getKuName();
					}
					Listcell c5=new Listcell(mz.split("null,")[1]);
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
						filename = "发表著作情况.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("著作", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						wsheet.setColumnView(3, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < zz.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) zz.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							GhZz mc=(GhZz)list.get(i);
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, mc.getZzMc(),titleFormat);
							Label content3 = new Label(2, i + 1, mc.getZzPublitime(),titleFormat);
							Label content4 = new Label(3, i + 1, mc.getZzKw(),titleFormat);
							List wlist=jszzService.findByLwidAndType(mc.getZzId(),GhJszz.ZZ);
							String mz=null;
							for(int j=0;j<wlist.size();j++){
								GhJszz lw=(GhJszz)wlist.get(j);
								mz=mz+","+lw.getUser().getKuName();
							}
							Label content5 = new Label(4, i + 1, mz.split("null,")[1],titleFormat);
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
		}else if(lx.shortValue()==GhHylw.KYLW||lx.shortValue()==GhHylw.JYLW){
			zz.setVisible(false);
			jc.setVisible(false);
			final List list=hylwService.findSumKdId(kdid, lx,GhHylw.AUDIT_YES);
			lw.setModel(new ListModelList(list));
			lw.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					GhHylw lwzl=(GhHylw)arg1;
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					Listcell c2=new Listcell(lwzl.getLwMc());
					Listcell c3=new Listcell(lwzl.getLwFbsj());
					Listcell c4=new Listcell(lwzl.getLwKw());
//					String mz=null;
//					for(int i=0;i<lwlist.size();i++){
//						GhLwzl lw=(GhLwzl)lwlist.get(i);
//						mz=mz+","+lw.getUser().getKuName();
//					}
//					Listcell c5=new Listcell(mz.split("null,")[1]);
					Listcell c5=new Listcell(lwzl.getLwAll());
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
						filename = "发表论文情况.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("论文", 0); // 工作表名称
						wsheet.setColumnView(1, 40);
						wsheet.setColumnView(3, 40);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < lw.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) lw.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							GhHylw xm=(GhHylw)list.get(i);
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getLwMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getLwFbsj(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getLwKw(),titleFormat);
//							String name=null;
//							for(int j=0;j<fmlist.size();j++){
//								GhLwzl fm=(GhLwzl)fmlist.get(j);
//								name=name+","+fm.getUser().getKuName();
//							}
							Label content5 = new Label(4, i + 1, xm.getLwAll(),titleFormat);
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
