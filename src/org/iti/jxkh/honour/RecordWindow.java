package org.iti.jxkh.honour;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.iti.jxkh.entity.Jxkh_AwardFile;
import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.entity.Jxkh_HonourFile;
import org.iti.jxkh.service.RychService;
import org.iti.jxkh.waterprint.PDFWaterMark;
import org.iti.jxkh.waterprint.Watermark;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.itextpdf.text.DocumentException;

public class RecordWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Textbox record;
	
	private Jxkh_Honour honour;
	private RychService rychService;
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	
	public void initWindow(Jxkh_Honour honour) {
		this.honour = honour;
		if(honour.getRecordId() != null) {
			record.setValue(honour.getRecordId());
		}
	}
	
	public void onClick$save() {
		if(record.getValue() == null || "".equals(record.getValue())) {
			try {
				Messagebox.show("档案号不能为空！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {				
				e.printStackTrace();
				try {
					Messagebox.show("档案号保存失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			return;
		}
		honour.setRecordId(record.getValue().trim());
		honour.setState(Jxkh_Honour.RECORD_YES);
		rychService.update(honour);
		putwater();
		Events.postEvent(Events.ON_CHANGE, this, null);
		this.detach();
	}
	
	public void onClick$close() {
		this.detach();
	}
	
	private void putwater() {
		Set<?> files = honour.getFile();
		Object[] file = files.toArray();
		String filePath = "";
		String filePath2 = "";
		for (int i = 0; i < file.length; i++) {
			Jxkh_HonourFile f = (Jxkh_HonourFile) file[i];
			filePath=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/")+  f.getFilePath();
			filePath2=filePath.substring(0, filePath.lastIndexOf(File.separator));
			File honourFile = new File(filePath);
//			图片加水印
			if(honourFile.getName().endsWith("jpg") || honourFile.getName().endsWith("png") || honourFile.getName().endsWith("gif") || honourFile.getName().endsWith("bmp")) {
				Watermark w = new Watermark();
				w.markImage(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/admin/waterprint/water.png"), honourFile.getPath(), honourFile.getPath(),
						-60);
			}
//			pdf加水印
			else if(honourFile.getName().endsWith("pdf")) {
				if (honourFile.isFile()) {
					int lastlength = Integer.parseInt(String.valueOf(honourFile.getName().length()));
					String dds = honourFile.getName().substring(honourFile.getName().lastIndexOf(".") + 1, lastlength);
					if (dds.toLowerCase().equalsIgnoreCase("pdf")) {
						try {
							new PDFWaterMark().createPDFWaterMark(honourFile.toString(),filePath2+ "/_" + honourFile.getName(), Executions.getCurrent().getDesktop().getWebApp().getRealPath("/admin/waterprint/water.png"), "河北省科学技术情报研究院");
						} catch (IOException e) {
							e.printStackTrace();
						} catch (DocumentException e) {
							e.printStackTrace();
						}
						if(honourFile.exists())
							honourFile.delete();
							File filenew=new File(filePath2+ "/_" + honourFile.getName());
							filenew.renameTo(new File(honourFile.getPath()));
					}
				}
			}			
		}
	}

}
