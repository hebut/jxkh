package com.uniwin.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.format.Border;
import jxl.format.Colour;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class JExcelUtils {
	/**
	 * 32. * ����Excel�ļ� 33. * @param path �ļ�·�� 34. * @param sheetName ���������� 35. * @param dataTitles ���ݱ��� 36.
	 */
	public void createExcelFile(String path, String sheetName, String[] dataTitles) {
		WritableWorkbook workbook;
		try {
			OutputStream os = new FileOutputStream(path);
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet(sheetName, 0); // ��ӵ�һ��������
			initialSheetSetting(sheet);
			Label label;
			for (int i = 0; i < dataTitles.length; i++) {
				// Label(�к�,�к�,����,���)
				label = new Label(i, 0, dataTitles[i], getTitleCellFormat());
				sheet.addCell(label);
			}
			// ����һ��
			insertRowData(sheet, 1, new String[] { "200201001", "����", "100", "60", "100", "260" }, getDataCellFormat(CellType.STRING_FORMULA));
			// һ��һ��������
			label = new Label(0, 2, "200201002", getDataCellFormat(CellType.STRING_FORMULA));
			sheet.addCell(label);
			label = new Label(1, 2, "����", getDataCellFormat(CellType.STRING_FORMULA));
			sheet.addCell(label);
			insertOneCellData(sheet, 2, 2, 70.5, getDataCellFormat(CellType.NUMBER));
			insertOneCellData(sheet, 3, 2, 90.523, getDataCellFormat(CellType.NUMBER));
			insertOneCellData(sheet, 4, 2, 60.5, getDataCellFormat(CellType.NUMBER));
			insertFormula(sheet, 5, 2, "C3+D3+E3", getDataCellFormat(CellType.NUMBER_FORMULA));
			// ��������
			mergeCellsAndInsertData(sheet, 0, 3, 5, 3, new Date(), getDataCellFormat(CellType.DATE));
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 80. * ��ʼ��������� 81. * @param sheet 82.
	 */
	public void initialSheetSetting(WritableSheet sheet) {
		try {
			// sheet.getSettings().setProtected(true); //����xls�ı�������Ԫ��Ϊֻ����
			sheet.getSettings().setDefaultColumnWidth(10); // �����е�Ĭ�Ͽ��
			// sheet.setRowView(2,false);//�и��Զ���չ
			// setRowView(int row, int height);--�и�
			// setColumnView(int col,int width); --�п�
			sheet.setColumnView(0, 20);// ���õ�һ�п��
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���빫ʽ
	 * 
	 * @param sheet
	 * @param col
	 * @param row
	 * @param formula
	 * @param format
	 */
	public void insertFormula(WritableSheet sheet, Integer col, Integer row, String formula, WritableCellFormat format) {
		try {
			Formula f = new Formula(col, row, formula, format);
			sheet.addCell(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 114. * ����һ������ 115. * @param sheet ������ 116. * @param row �к� 117. * @param content ���� 118. * @param format ��� 119.
	 */
	public void insertRowData(WritableSheet sheet, Integer row, String[] dataArr, WritableCellFormat format) {
		try {
			Label label;
			for (int i = 0; i < dataArr.length; i++) {
				label = new Label(i, row, dataArr[i], format);
				sheet.addCell(label);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 133. * ���뵥Ԫ������ 134. * @param sheet 135. * @param col 136. * @param row 137. * @param data 138.
	 */
	public void insertOneCellData(WritableSheet sheet, Integer col, Integer row, Object data, WritableCellFormat format) {
		try {
			if (data instanceof Double) {
				jxl.write.Number labelNF = new jxl.write.Number(col, row, (Double) data, format);
				sheet.addCell(labelNF);
			} else if (data instanceof Boolean) {
				jxl.write.Boolean labelB = new jxl.write.Boolean(col, row, (Boolean) data, format);
				sheet.addCell(labelB);
			} else if (data instanceof Date) {
				jxl.write.DateTime labelDT = new jxl.write.DateTime(col, row, (Date) data, format);
				sheet.addCell(labelDT);
				setCellComments(labelDT, "���Ǹ������������˵����");
			} else {
				Label label = new Label(col, row, data.toString(), format);
				sheet.addCell(label);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ϲ���Ԫ�񣬲���������
	 * 
	 * @param sheet
	 * @param col_start
	 * @param row_start
	 * @param col_end
	 * @param row_end
	 * @param data
	 * @param format
	 */
	public void mergeCellsAndInsertData(WritableSheet sheet, Integer col_start, Integer row_start, Integer col_end, Integer row_end, Object data, WritableCellFormat format) {
		try {
			sheet.mergeCells(col_start, row_start, col_end, row_end);// ���Ͻǵ����½�
			insertOneCellData(sheet, col_start, row_start, data, format);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 182. * ����Ԫ���ע�� 183. * @param label 184. * @param comments 185.
	 */
	public void setCellComments(Object label, String comments) {
		WritableCellFeatures cellFeatures = new WritableCellFeatures();
		cellFeatures.setComment(comments);
		if (label instanceof jxl.write.Number) {
			jxl.write.Number num = (jxl.write.Number) label;
			num.setCellFeatures(cellFeatures);
		} else if (label instanceof jxl.write.Boolean) {
			jxl.write.Boolean bool = (jxl.write.Boolean) label;
			bool.setCellFeatures(cellFeatures);
		} else if (label instanceof jxl.write.DateTime) {
			jxl.write.DateTime dt = (jxl.write.DateTime) label;
			dt.setCellFeatures(cellFeatures);
		} else {
			Label _label = (Label) label;
			_label.setCellFeatures(cellFeatures);
		}
	}

	/**
	 * 205. * ��ȡexcel 206. * @param inputFile 207. * @param inputFileSheetIndex 208. * @throws Exception 209.
	 */
	public ArrayList<String> readDataFromExcel(File inputFile, int inputFileSheetIndex) {
		ArrayList<String> list = new ArrayList<String>();
		Workbook book = null;
		Cell cell = null;
		WorkbookSettings setting = new WorkbookSettings();
		java.util.Locale locale = new java.util.Locale("zh", "CN");
		setting.setLocale(locale);
		setting.setEncoding("ISO-8859-1");
		try {
			book = Workbook.getWorkbook(inputFile, setting);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = book.getSheet(inputFileSheetIndex);
		for (int rowIndex = 0; rowIndex < sheet.getRows(); rowIndex++) {// ��
			for (int colIndex = 0; colIndex < sheet.getColumns(); colIndex++) {// ��
				cell = sheet.getCell(colIndex, rowIndex);
				// System.out.println(cell.getContents());
				list.add(cell.getContents());
			}
		}
		book.close();
		return list;
	}

	/**
	 * �õ����ݱ�ͷ��ʽ
	 * 
	 * @return
	 */
	public WritableCellFormat getTitleCellFormat() {
		WritableCellFormat wcf = null;
		try {
			// ������ʽ
			WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD, false);// ���һ��Ϊ�Ƿ�italic
			wf.setColour(Colour.RED);
			wcf = new WritableCellFormat(wf);
			// ���뷽ʽ
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			// �߿�
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ����ɫ
			wcf.setBackground(Colour.GREY_25_PERCENT);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcf;
	}

	/**
	 * 263. * �õ����ݸ�ʽ 264. * @return 265.
	 */
	public WritableCellFormat getDataCellFormat(CellType type) {
		WritableCellFormat wcf = null;
		try {
			// ������ʽ
			if (type == CellType.NUMBER || type == CellType.NUMBER_FORMULA) {// ����
				NumberFormat nf = new NumberFormat("#.00");
				wcf = new WritableCellFormat(nf);
			} else if (type == CellType.DATE || type == CellType.DATE_FORMULA) {// ����
				jxl.write.DateFormat df = new jxl.write.DateFormat("yyyy-MM-dd hh:mm:ss");
				wcf = new jxl.write.WritableCellFormat(df);
			} else {
				WritableFont wf = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD, false);// ���һ��Ϊ�Ƿ�italic
				wcf = new WritableCellFormat(wf);
			}
			// ���뷽ʽ
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			// �߿�
			wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
			wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
			// ����ɫ
			wcf.setBackground(Colour.WHITE);
			wcf.setWrap(true);// �Զ�����
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcf;
	}

	/**
	 * ���ļ�����
	 * 
	 * @param exePath
	 * @param filePath
	 */
	public void openExcel(String exePath, String filePath) {
		Runtime r = Runtime.getRuntime();
		String cmd[] = { exePath, filePath };
		try {
			r.exec(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// public static void main(String[] args){
	// String[] titles = {"ѧ��","����","����","��ѧ","Ӣ��","�ܷ�"};
	// JExcelUtils jxl = new JExcelUtils();
	// String filePath = "E:/test.xls";
	// jxl.createExcelFile(filePath,"�ɼ���",titles);
	// jxl.readDataFromExcel(new File(filePath),0);
	// jxl.openExcel("C:/Program Files/Microsoft Office/OFFICE11/EXCEL.EXE",filePath);
	// '}
}