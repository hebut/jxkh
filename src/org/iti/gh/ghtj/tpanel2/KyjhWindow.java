package org.iti.gh.ghtj.tpanel2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhKyjh;
import org.iti.gh.service.KyjhService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkmax.zul.Filedownload;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

public class KyjhWindow extends Window implements AfterCompose {

	long kdid;
	KyjhService kyjhService;
//	Listbox kyjh;
	Grid jh;
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);

	}
	public void initWindow(){
		final List list=kyjhService.findByKdId(kdid);
//		kyjh.setModel(new ListModelList(list));
//		kyjh.setItemRenderer(new ListitemRenderer(){
//			public void render(Listitem arg0,Object arg1){
//				GhKyjh xm=(GhKyjh)arg1;
//				Listcell c1=new Listcell(arg0.getIndex()+1+"");
//				Listcell c6=new Listcell(xm.getUser().getKuName());
//				Listcell c2=new Listcell(xm.getJhNr());
//				Listcell c3=new Listcell(xm.getJhGjwt());
//				Listcell c4=new Listcell(xm.getJhYjcg());
//				Listcell c5=new Listcell(xm.getJhKysx());
//				arg0.appendChild(c1);arg0.appendChild(c6);
//				arg0.appendChild(c2);arg0.appendChild(c3);arg0.appendChild(c4);
//				arg0.appendChild(c5);
//			}
//		});
		jh.setModel(new ListModelList(list));
		
		jh.setRowRenderer(new RowRenderer(){
			int i=1;
			public void render(Row arg0, Object arg1) throws Exception {
				GhKyjh xm=(GhKyjh)arg1;
				Label c1=new Label(i+"");
				Label c6=new Label(xm.getUser().getKuName());
				Hbox h2=new Hbox();
				Label c2=new Label(xm.getJhNr());
				h2.appendChild(c2);
				Hbox h3=new Hbox();
				Label c3=new Label(xm.getJhGjwt());
				h3.appendChild(c3);
				Hbox h4=new Hbox();
				Label c4=new Label(xm.getJhYjcg());
				h4.appendChild(c4);
				Hbox h5=new Hbox();
				Label c5=new Label(xm.getJhKysx());
				h5.appendChild(c5);
				arg0.appendChild(c1);arg0.appendChild(c6);
				arg0.appendChild(h2);arg0.appendChild(h3);arg0.appendChild(h4);
				arg0.appendChild(h5);
				i++;
			}
		});
		
		Caption c=new Caption();
		Button daochu = new Button();
		daochu.setLabel("导出Excel ");
		daochu.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				String filename;
				try {
					filename = "科研计划.xls";
					
					OutputStream stream = new FileOutputStream(new File(filename));
					WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
					WritableSheet wsheet = wbook.createSheet("科研计划", 0); // 工作表名称
					wsheet.setColumnView(2, 50);//列   ， 宽
					wsheet.setColumnView(3, 30);//列   ， 宽
					wsheet.setColumnView(4, 30);//列   ， 宽
					wsheet.setColumnView(5, 50);//列   ， 宽
					// 设置Excel字体
					WritableFont wfont = new WritableFont(WritableFont.ARIAL,
							10, WritableFont.NO_BOLD, false,
							jxl.format.UnderlineStyle.NO_UNDERLINE,
							jxl.format.Colour.BLACK);
					
					WritableCellFormat titleFormat = new WritableCellFormat(wfont);
					titleFormat.setWrap(true);
					titleFormat.setShrinkToFit(true);
					titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//					titleFormat.setAlignment(Alignment.CENTRE);
					
					for (int j = 0; j < jh.getColumns().getChildren().size(); j++) {
						Column l = (Column) jh.getColumns().getChildren().get(j);
						String s = l.getLabel();
						jxl.write.Label excelTitle = new jxl.write.Label(j, 0, s, titleFormat);
						wsheet.addCell(excelTitle);
					}
					for (int i = 0; i < list.size(); i++) {
						GhKyjh xm=(GhKyjh)list.get(i);
						
						Integer xuhao = i + 1;
						jxl.write.Label content1 = new jxl.write.Label(0, i + 1, xuhao.toString(),titleFormat);
						jxl.write.Label content2 = new jxl.write.Label(1, i + 1, xm.getUser().getKuName(),titleFormat);
						jxl.write.Label content3 = new jxl.write.Label(2, i + 1, xm.getJhNr(),titleFormat);
						jxl.write.Label content4 = new jxl.write.Label(3, i + 1, xm.getJhGjwt(),titleFormat);
						jxl.write.Label content5 = new jxl.write.Label(4, i + 1, xm.getJhYjcg(),titleFormat);
						jxl.write.Label content6 = new jxl.write.Label(5, i + 1, xm.getJhKysx()+"",titleFormat);
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
	public long getKdid() {
		return kdid;
	}
	public void setKdid(long kdid) {
		this.kdid = kdid;
	}

}
