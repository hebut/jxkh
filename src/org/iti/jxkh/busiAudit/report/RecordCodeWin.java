package org.iti.jxkh.busiAudit.report;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportFile;
import org.iti.jxkh.service.JxkhReportService;
import org.iti.jxkh.waterprint.PDFWaterMark;
import org.iti.jxkh.waterprint.Watermark;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.itextpdf.text.DocumentException;

public class RecordCodeWin extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3231795741911179563L;
	private Textbox record;
	private Jxkh_Report report;
	private JxkhReportService jxkhReportService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initWindow() {
		if (report.getRecordCode() == null || report.getRecordCode().equals("")) {

		} else {
			record.setValue(report.getRecordCode());
		}
		// if(report.getRecordCode()!=null ||
		// !report.getRecordCode().equals("")){
		// record.setValue(report.getRecordCode());
		// }
	}

	public void onClick$save() {
		if (report.getState() == 4) {
			Set<?> files = report.getReportFile();
			Object[] file = files.toArray();
			String filePath = "";
			String filePath2 = "";
			for (int i = 0; i < file.length; i++) {
				Jxkh_ReportFile f = (Jxkh_ReportFile) file[i];
				filePath = Executions.getCurrent().getDesktop().getWebApp()
						.getRealPath("/upload/")
						+ f.getPath();
				filePath2 = filePath.substring(0,
						filePath.lastIndexOf(File.separator));
				File awardFile = new File(filePath);
				// 图片加水印
				Watermark w = new Watermark();
				w.markImage(Executions.getCurrent().getDesktop().getWebApp()
						.getRealPath("/admin/waterprint/water.png"),
						awardFile.getPath(), awardFile.getPath(), -30);
				// pdf加水印
				if (awardFile.isFile()) {
					int lastlength = Integer.parseInt(String.valueOf(awardFile
							.getName().length()));
					String dds = awardFile.getName().substring(
							awardFile.getName().lastIndexOf(".") + 1,
							lastlength);
					if (dds.toLowerCase().equalsIgnoreCase("pdf")) {
						try {
							new PDFWaterMark()
									.createPDFWaterMark(
											awardFile.toString(),
											filePath2 + "/_"
													+ awardFile.getName(),
											Executions
													.getCurrent()
													.getDesktop()
													.getWebApp()
													.getRealPath(
															"/admin/waterprint/water.png"),
											"河北省科学技术情报研究院");
						} catch (IOException e) {
							e.printStackTrace();
						} catch (DocumentException e) {
							e.printStackTrace();
						}
						if (awardFile.exists())
							awardFile.delete();
						File filenew = new File(filePath2 + "/_"
								+ awardFile.getName());
						filenew.renameTo(new File(awardFile.getPath()));
					}
				}
			}
		}
		report.setRecordCode(record.getValue());
		report.setState(Jxkh_Fruit.SAVE_RECORD);
		try {
			jxkhReportService.saveOrUpdate(report);
			Messagebox.show("档案号保存成功！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("档案号保存失败，请重试！", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.onClose();
	}

	public Jxkh_Report getReport() {
		return report;
	}

	public void setReport(Jxkh_Report report) {
		this.report = report;
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}

}
