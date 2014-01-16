package org.iti.jxkh.busiAudit.article.meetartical;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWFile;
import org.iti.jxkh.service.JxkhProjectService;
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
	private static final long serialVersionUID = 2270239512098826592L;
	private Textbox record;
	private JXKH_HYLW award;
	private JxkhProjectService jxkhProjectService;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}

	public void initShow() {
		if (award.getRecordCode() == null || award.getRecordCode().equals("")) {

		} else {
			record.setValue(award.getRecordCode());
		}
	}

	public void onClick$save() {

		// ���ļ���ˮӡ
		if (award.getLwState() == 4) {
			String filePath = "";
			String filePath2 = "";
			Set<?> filesList = award.getFiles();
			Object[] file1 = filesList.toArray();
			for (int j = 0; j < file1.length; j++) {
				JXKH_HYLWFile f = (JXKH_HYLWFile) file1[j];
				filePath = Executions.getCurrent().getDesktop().getWebApp()
						.getRealPath("/upload/")
						+ f.getPath();
				File file = new File(filePath);
				filePath2 = filePath.substring(0,
						filePath.lastIndexOf(File.separator));
				if (file.isFile()) {
					int lastlength = Integer.parseInt(String.valueOf(file
							.getName().length()));
					String dds = file.getName().substring(
							file.getName().lastIndexOf(".") + 1, lastlength);
					/** ��ͼƬ�ļ���ˮӡ */
					if (dds.toLowerCase().equalsIgnoreCase("jpg")
							|| dds.toLowerCase().equalsIgnoreCase("bmp")
							|| dds.toLowerCase().equalsIgnoreCase("gpeg")
							|| dds.toLowerCase().equalsIgnoreCase("png")
							|| dds.toLowerCase().equalsIgnoreCase("gif")) {
						Watermark w = new Watermark();
						w.markImage(
								Executions
										.getCurrent()
										.getDesktop()
										.getWebApp()
										.getRealPath(
												"/admin/waterprint/water.png"),
								file.getPath(), file.getPath(), -30);
					}
					/** ��.pdf�ļ���ˮӡ */
					if (dds.toLowerCase().equalsIgnoreCase("pdf")) {
						try {
							new PDFWaterMark()
									.createPDFWaterMark(
											file.toString(),
											filePath2 + "/_" + file.getName(),
											Executions
													.getCurrent()
													.getDesktop()
													.getWebApp()
													.getRealPath(
															"/admin/waterprint/water.png"),
											"�ӱ�ʡ��ѧ�����鱨�о�Ժ");
						} catch (IOException e) {
							e.printStackTrace();
						} catch (DocumentException e) {
							e.printStackTrace();
						}
						if (file.exists())
							file.delete();
						File filenew = new File(filePath2 + "/_"
								+ file.getName());
						filenew.renameTo(new File(file.getPath()));
					}
				}
			}
		}

		award.setRecordCode(record.getValue());
		award.setLwState(JXKH_HYLW.SAVE_RECORD);
		try {
			jxkhProjectService.saveOrUpdate(award);
			Messagebox.show("�����ű���ɹ���", "��ʾ", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show("�����ű���ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.onClose();
	}

	public JXKH_HYLW getAward() {
		return award;
	}

	public void setAward(JXKH_HYLW award) {
		this.award = award;
	}

	/**
	 * <li>�����������رյ�ǰ���ڡ�
	 */
	public void onClick$close() {
		this.onClose();
	}

}
