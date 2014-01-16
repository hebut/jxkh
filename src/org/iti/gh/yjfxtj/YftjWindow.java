package org.iti.gh.yjfxtj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.iti.gh.entity.GhUseryjfx;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.UseryjfxService;
import org.iti.gh.service.YjfxService;
import org.iti.xypt.entity.Student;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zkmax.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class YftjWindow extends BaseWindow {
	Listbox yjfztj;
	UseryjfxService useryjfxService;
	UserService userService;
	YjfxService yjfxService;
	@Override
	public void initShow() {
		yjfztj.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				 GhYjfx yjfx=(GhYjfx)arg1;
				 Listcell c1 = new Listcell(yjfx.getGyName());
				 List userlist=useryjfxService.findByGyid(yjfx.getGyId());
				 Listcell c2 = new Listcell(setLabelValue(userlist));
				 arg0.appendChild(c1);
				 arg0.appendChild(c2);
				}});
	}
	@Override
	public void initWindow() {
		yjfztj.setModel(new ListModelList(yjfxService.findByKdid(getXyUserRole().getKdId())));
	}
	public String setLabelValue(List ulist){
		StringBuffer lb=new StringBuffer("");
		for(int i=0;i<ulist.size();i++){
			GhUseryjfx ghyjfx=(GhUseryjfx)ulist.get(i);
			WkTUser u=(WkTUser)userService.get(WkTUser.class, ghyjfx.getId().getKuId());
			lb.append(u.getKuName());
			if(i<ulist.size()-1){
				lb.append(",");
			}
		}
		return lb.toString();
		
	}
	public void onClick$Export() throws WriteException, IOException{

		try {
			Date d=new Date();
			String filename="研究方向汇总表"+d.getTime()+".xls";
			OutputStream stream = new FileOutputStream(new File(filename));
			WritableWorkbook wbook = Workbook.createWorkbook(stream); // 建立excel文件
			WritableSheet wsheet = wbook.createSheet("教师研究方向汇总", 0); // 工作表名称
			wsheet.setColumnView(1,30);wsheet.setColumnView(2,40);
			// 设置Excel字体
			WritableFont wfont = new WritableFont(WritableFont.ARIAL,
					10, WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			// 自定义标题格式
			WritableCellFormat wcf = null;
			WritableFont wf = new WritableFont(WritableFont.TIMES, 12,WritableFont.BOLD, false);// 最后一个为是否italic
			wcf = new WritableCellFormat(wf);
			// 对齐方式
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			jxl.write.Label tlabel;
			
		  tlabel = new jxl.write.Label(0, 0, getXyUserRole().getDept().getKdName().trim()+"教师研究方向汇总表");
			
			wsheet.mergeCells(0, 0, 4, 0);
			tlabel.setCellFormat(wcf);
			wsheet.addCell(tlabel);
			WritableCellFormat titleFormat = new WritableCellFormat(wfont);
			titleFormat.setAlignment(Alignment.CENTRE);
			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			List userList=yjfxService.findByKdid(getXyUserRole().getKdId());
			Label excelTitle1 = new Label(0, 1, "序号", titleFormat);
			wsheet.addCell(excelTitle1);
			for (int j = 0; j < yjfztj.getListhead().getChildren().size(); j++) {
				Listheader l = (Listheader) yjfztj.getListhead().getChildren().get(j);
				String s = l.getLabel();
				Label excelTitle = new Label(j+1, 1, s, titleFormat);
				wsheet.addCell(excelTitle);
			}
			
			
			for (int i = 0; i < userList.size(); i++) {
				GhYjfx ghyjfx=(GhYjfx)userList.get(i);
				Integer xuhao = i + 1;
				Label content1 = new Label(0, i + 2, xuhao.toString(),titleFormat);
				Label content2 = new Label(1, i + 2, ghyjfx.getGyName(),titleFormat);
				 List userlist=useryjfxService.findByGyid(ghyjfx.getGyId());
				Label content3 = new Label(2, i + 2, setLabelValue(userlist),
						titleFormat);
				
				wsheet.addCell(content1);
				wsheet.addCell(content2);
				wsheet.addCell(content3);
				
			}
			wbook.write(); // 写入文件
			Filedownload.save(new File(filename), "application/vnd.ms-excel");
			wbook.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	
	}
}
