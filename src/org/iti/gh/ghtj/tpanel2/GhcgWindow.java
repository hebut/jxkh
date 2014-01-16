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

import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.service.CgService;
import org.iti.gh.service.JsxmService;
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

public class GhcgWindow extends Window implements AfterCompose {

	Long kdid=(Long)Executions.getCurrent().getArg().get("kdid");
	Short lx=(Short)Executions.getCurrent().getArg().get("lx");
	Listbox kjcg;
	JsxmService jsxmService;
	
	CgService cgService;;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	public void initWindow(){
		final List list=cgService.findSumKdId(kdid, lx,GhCg.AUDIT_YES);
		kjcg.setModel(new ListModelList(list));
		if(lx.shortValue()==GhCg.CG_KY){
		kjcg.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem arg0,Object arg1){
				Listcell c1=new Listcell(arg0.getIndex()+1+"");
				GhCg xm=(GhCg)arg1;
				String name=null;
				List jsxmlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.HjKY);
				for(int i=0;i<jsxmlist.size();i++){
					GhJsxm jsxm=(GhJsxm)jsxmlist.get(i);
					name=name+","+jsxm.getUser().getKuName();
				}
				Listcell c2=new Listcell(xm.getKyMc());
				Listcell c3=new Listcell(xm.getKyLy());
				Listcell c4=new Listcell(xm.getKySj());
				Listcell c5=new Listcell(xm.getKyDj());
//				Listcell c6=new Listcell(xm.getKyPrizeper());
				Listcell c6=new Listcell(name.split("null,")[1]);
				arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
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
					filename = "获奖科研成果.xls";
					
					OutputStream stream = new FileOutputStream(new File(filename));
					WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
					WritableSheet wsheet = wbook.createSheet("获奖科研成果", 0); // 工作表名称
					wsheet.setColumnView(1, 30);
					wsheet.setColumnView(2, 30);
					wsheet.setColumnView(4, 30);
					// 设置Excel字体
					WritableFont wfont = new WritableFont(WritableFont.ARIAL,
							10, WritableFont.NO_BOLD, false,
							jxl.format.UnderlineStyle.NO_UNDERLINE,
							jxl.format.Colour.BLACK);
					
					WritableCellFormat titleFormat = new WritableCellFormat(wfont);
					titleFormat.setWrap(true);
					for (int j = 0; j < kjcg.getListhead().getChildren().size(); j++) {
						Listheader l = (Listheader) kjcg.getListhead().getChildren().get(j);
						String s = l.getLabel();
						Label excelTitle = new Label(j, 0, s, titleFormat);
						wsheet.addCell(excelTitle);
					}
					for (int i = 0; i < list.size(); i++) {
						GhCg xm=(GhCg)list.get(i);
						
						Integer xuhao = i + 1;
						Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
						Label content2 = new Label(1, i + 1, xm.getKyMc(),titleFormat);
						Label content3 = new Label(2, i + 1, xm.getKyLy(),titleFormat);
						Label content4 = new Label(3, i + 1, xm.getKySj(),titleFormat);
						Label content5 = new Label(4, i + 1, xm.getKyDj(),titleFormat);
						String name=null;
						List jsxmlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.HjKY);
						for(int j=0;j<jsxmlist.size();j++){
							GhJsxm jsxm=(GhJsxm)jsxmlist.get(j);
							name=name+","+jsxm.getUser().getKuName();
						}

						Label content6 = new Label(5, i + 1,name.split("null,")[1]);
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
		}else if(lx.shortValue()==GhCg.CG_JY){
			kjcg.setItemRenderer(new ListitemRenderer(){
				public void render(Listitem arg0,Object arg1){
					Listcell c1=new Listcell(arg0.getIndex()+1+"");
					GhCg xm=(GhCg)arg1;
					String name=null;
					List jsxmlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.HJJY);
					for(int i=0;i<jsxmlist.size();i++){
						GhJsxm jsxm=(GhJsxm)jsxmlist.get(i);
						name=name+","+jsxm.getUser().getKuName();
					}
					Listcell c2=new Listcell(xm.getKyMc());
					Listcell c3=new Listcell(xm.getKyLy());
					Listcell c4=new Listcell(xm.getKySj());
					Listcell c5=new Listcell(xm.getKyDj());
//					Listcell c6=new Listcell(xm.getKyPrizeper());
					Listcell c6=new Listcell(name.split("null,")[1]);
					arg0.appendChild(c1);arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
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
						filename = "获奖教研成果.xls";
						
						OutputStream stream = new FileOutputStream(new File(filename));
						WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
						WritableSheet wsheet = wbook.createSheet("获奖教研成果", 0); // 工作表名称
						wsheet.setColumnView(1, 30);
						wsheet.setColumnView(2, 30);
						wsheet.setColumnView(4, 30);
						// 设置Excel字体
						WritableFont wfont = new WritableFont(WritableFont.ARIAL,
								10, WritableFont.NO_BOLD, false,
								jxl.format.UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK);
						
						WritableCellFormat titleFormat = new WritableCellFormat(wfont);
						titleFormat.setWrap(true);
						for (int j = 0; j < kjcg.getListhead().getChildren().size(); j++) {
							Listheader l = (Listheader) kjcg.getListhead().getChildren().get(j);
							String s = l.getLabel();
							Label excelTitle = new Label(j, 0, s, titleFormat);
							wsheet.addCell(excelTitle);
						}
						for (int i = 0; i < list.size(); i++) {
							GhCg xm=(GhCg)list.get(i);
							
							Integer xuhao = i + 1;
							Label content1 = new Label(0, i + 1, xuhao.toString(),titleFormat);
							Label content2 = new Label(1, i + 1, xm.getKyMc(),titleFormat);
							Label content3 = new Label(2, i + 1, xm.getKyLy(),titleFormat);
							Label content4 = new Label(3, i + 1, xm.getKySj(),titleFormat);
							Label content5 = new Label(4, i + 1, xm.getKyDj(),titleFormat);
							String name=null;
							List jsxmlist=jsxmService.findByXmidAndtype(xm.getKyId(), GhJsxm.HJJY);
							for(int j=0;j<jsxmlist.size();j++){
								GhJsxm jsxm=(GhJsxm)jsxmlist.get(j);
								name=name+","+jsxm.getUser().getKuName();
							}

							Label content6 = new Label(5, i + 1,name.split("null,")[1]);
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
		}
	}
}
