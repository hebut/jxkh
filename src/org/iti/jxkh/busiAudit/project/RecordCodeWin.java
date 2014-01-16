package org.iti.jxkh.busiAudit.project;

import java.io.File;
import java.io.IOException;

import org.iti.jxkh.entity.Jxkh_Project;
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
import com.iti.common.util.UploadUtil;

public class RecordCodeWin extends Window implements AfterCompose {

	/**
	 * @author ZhangYuguang
	 */
	private static final long serialVersionUID = 1983995014077054035L;
	private Textbox record;
	private Jxkh_Project award;
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
		award.setRecordCode(record.getValue());

		if (award.getState() == Jxkh_Project.BUSINESS_PASS) {

			// 附件1
			String filePath = "";
			try {
				filePath = UploadUtil.getRealPath("/jxkh/project/1/"
						+ award.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			File fruitFile = new File(filePath);
			if (fruitFile.exists()) {
				File[] fileArray = fruitFile.listFiles();
				for (File file : fileArray) {
					// 图片加水印
					Watermark w = new Watermark();
					w.markImage(
							Executions.getCurrent().getDesktop().getWebApp()
									.getRealPath("/admin/waterprint/water.png"),
							file.getPath(), file.getPath(), -30);
					// pdf加水印
					if (file.isFile()) {
						int lastlength = Integer.parseInt(String.valueOf(file
								.getName().length()));
						String dds = file.getName()
								.substring(file.getName().lastIndexOf(".") + 1,
										lastlength);
						if (dds.toLowerCase().equalsIgnoreCase("pdf")) {
							try {
								new PDFWaterMark()
										.createPDFWaterMark(
												file.toString(),
												filePath + "/_"
														+ file.getName(),
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
							if (file.exists())
								file.delete();
							File filenew = new File(filePath + "/_"
									+ file.getName());
							filenew.renameTo(new File(file.getPath()));
						}
					}
				}
			}
			// 附件2
			String filePath2 = "";
			try {
				filePath2 = UploadUtil.getRealPath("/jxkh/project/2/"
						+ award.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			File fruitFile2 = new File(filePath2);
			if (fruitFile2.exists()) {
				File[] fileArray = fruitFile2.listFiles();
				for (File file : fileArray) {
					Watermark w = new Watermark();
					// w.markImage();
					w.markImage(
							Executions.getCurrent().getDesktop().getWebApp()
									.getRealPath("/admin/waterprint/water.png"),
							file.getPath(), file.getPath(), -30);
					// pdf加水印
					if (file.isFile()) {
						int lastlength = Integer.parseInt(String.valueOf(file
								.getName().length()));
						String dds = file.getName()
								.substring(file.getName().lastIndexOf(".") + 1,
										lastlength);
						if (dds.toLowerCase().equalsIgnoreCase("pdf")) {
							try {
								new PDFWaterMark()
										.createPDFWaterMark(
												file.toString(),
												filePath2 + "/_"
														+ file.getName(),
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
							if (file.exists())
								file.delete();
							File filenew = new File(filePath2 + "/_"
									+ file.getName());
							filenew.renameTo(new File(file.getPath()));
						}
					}
				}
			}
			// 附件3
			String filePath3 = "";
			try {
				filePath3 = UploadUtil.getRealPath("/jxkh/project/3/"
						+ award.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			File fruitFile3 = new File(filePath3);
			if (fruitFile3.exists()) {
				File[] fileArray = fruitFile3.listFiles();
				for (File file : fileArray) {
					Watermark w = new Watermark();
					// w.markImage();
					w.markImage(
							Executions.getCurrent().getDesktop().getWebApp()
									.getRealPath("/admin/waterprint/water.png"),
							file.getPath(), file.getPath(), -30);
					// pdf加水印
					if (file.isFile()) {
						int lastlength = Integer.parseInt(String.valueOf(file
								.getName().length()));
						String dds = file.getName()
								.substring(file.getName().lastIndexOf(".") + 1,
										lastlength);
						if (dds.toLowerCase().equalsIgnoreCase("pdf")) {
							try {
								new PDFWaterMark()
										.createPDFWaterMark(
												file.toString(),
												filePath3 + "/_"
														+ file.getName(),
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
							if (file.exists())
								file.delete();
							File filenew = new File(filePath3 + "/_"
									+ file.getName());
							filenew.renameTo(new File(file.getPath()));
						}
					}
				}
			}
			// 附件4
			String filePath4 = "";
			try {
				filePath4 = UploadUtil.getRealPath("/jxkh/project/4/"
						+ award.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			File fruitFile4 = new File(filePath4);
			if (fruitFile4.exists()) {
				File[] fileArray = fruitFile4.listFiles();
				for (File file : fileArray) {
					Watermark w = new Watermark();
					// w.markImage();
					w.markImage(
							Executions.getCurrent().getDesktop().getWebApp()
									.getRealPath("/admin/waterprint/water.png"),
							file.getPath(), file.getPath(), -30);
					// pdf加水印
					if (file.isFile()) {
						int lastlength = Integer.parseInt(String.valueOf(file
								.getName().length()));
						String dds = file.getName()
								.substring(file.getName().lastIndexOf(".") + 1,
										lastlength);
						if (dds.toLowerCase().equalsIgnoreCase("pdf")) {
							try {
								new PDFWaterMark()
										.createPDFWaterMark(
												file.toString(),
												filePath4 + "/_"
														+ file.getName(),
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
							if (file.exists())
								file.delete();
							File filenew = new File(filePath4 + "/_"
									+ file.getName());
							filenew.renameTo(new File(file.getPath()));
						}
					}
				}
			}
			// 附件5
			String filePath5 = "";
			try {
				filePath5 = UploadUtil.getRealPath("/jxkh/project/5/"
						+ award.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			File fruitFile5 = new File(filePath5);
			if (fruitFile5.exists()) {
				File[] fileArray = fruitFile5.listFiles();
				for (File file : fileArray) {
					Watermark w = new Watermark();
					// w.markImage();
					w.markImage(
							Executions.getCurrent().getDesktop().getWebApp()
									.getRealPath("/admin/waterprint/water.png"),
							file.getPath(), file.getPath(), -30);
					// pdf加水印
					if (file.isFile()) {
						int lastlength = Integer.parseInt(String.valueOf(file
								.getName().length()));
						String dds = file.getName()
								.substring(file.getName().lastIndexOf(".") + 1,
										lastlength);
						if (dds.toLowerCase().equalsIgnoreCase("pdf")) {
							try {
								new PDFWaterMark()
										.createPDFWaterMark(
												file.toString(),
												filePath5 + "/_"
														+ file.getName(),
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
							if (file.exists())
								file.delete();
							File filenew = new File(filePath5 + "/_"
									+ file.getName());
							filenew.renameTo(new File(file.getPath()));
						}
					}
				}
			}

			award.setState(Jxkh_Project.SAVE_RECORD);
		}
		try {
			jxkhProjectService.saveOrUpdate(award);
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

	public Jxkh_Project getAward() {
		return award;
	}

	public void setAward(Jxkh_Project award) {
		this.award = award;
	}

	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close() {
		this.onClose();
	}

}
