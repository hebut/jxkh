package org.iti.gh.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkmax.zul.Filedownload;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportExcel {
	/**
	 * 
	 * @param fileName
	 *            ����Excelʱ�ļ���
	 * @param Title
	 *            �����ļ��Ĵ���⣨�磬2010���＾ѧ��2007����ѧִ�мƻ���
	 * @param titlelist
	 *            Excel�����б�(�ַ�������ļ���)
	 * @param rowNum
	 *            ����������
	 * @param String
	 *            c[][] ÿһ�ж�Ӧ������(������)
	 * @author FengXinhong 2010-7-31
	 * @throws WriteException
	 * @throws IOException
	 */
	public void exportExcel(String fileName, String Title, List titlelist, Integer rowNum, String c[][]) throws WriteException, IOException {
		// ������ʽ
		WritableCellFormat wcf = null;
		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);// ���һ��Ϊ�Ƿ�italic,������
		wcf = new WritableCellFormat(wf);
		// ���뷽ʽ
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		// �Զ����ͷ��ʽ
		WritableCellFormat wc = null;
		WritableFont w = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
		wc = new WritableCellFormat(w);
		wc.setAlignment(Alignment.LEFT);
		// �Զ��������и�ʽ
		WritableCellFormat swf = null;
		WritableFont ff = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
		swf = new WritableCellFormat(ff);
		swf.setAlignment(Alignment.LEFT);
		WritableWorkbook workbook;
		OutputStream ops = new FileOutputStream(fileName);
		workbook = Workbook.createWorkbook(ops);
		WritableSheet sheet = workbook.createSheet(fileName, 0);
		Label tlabel = new Label(0, 0, Title);
		sheet.mergeCells(0, 0, titlelist.size(), 0);// �����
		tlabel.setCellFormat(wcf);
		sheet.addCell(tlabel);
		Label tl;
		for (int i = 0; i < titlelist.size(); i++) {
			tl = new Label(i, 1, (String) titlelist.get(i));
			tl.setCellFormat(wc);
			sheet.addCell(tl);
		}
		for (int j = 0; j < rowNum; j++) {
			for (int k = 0; k < titlelist.size(); k++) {
				Label clabel = new Label(k, j + 2, (String) c[j][k]);
				clabel.setCellFormat(swf);
				sheet.addCell(clabel);
			}
		}
		workbook.write();
		Filedownload.save(new File(fileName), null);
		workbook.close();
	}

	public void exportTaskExcel(String fileName, String Title, List titlelist, Integer rowNum, String c[][]) throws WriteException, IOException {
		// ������ʽ
		WritableCellFormat wcf = null;
		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);// ���һ��Ϊ�Ƿ�italic,������
		wcf = new WritableCellFormat(wf);
		// ���뷽ʽ
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		// �Զ����ͷ��ʽ
		WritableCellFormat wc = null;
		WritableFont w = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
		wc = new WritableCellFormat(w);
		wc.setAlignment(Alignment.CENTRE);
		// �Զ��������и�ʽ
		WritableCellFormat swf = null;
		WritableFont ff = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
		swf = new WritableCellFormat(ff);
		swf.setAlignment(Alignment.CENTRE);
		swf.setVerticalAlignment(VerticalAlignment.CENTRE);
		WritableWorkbook workbook;
		OutputStream ops = new FileOutputStream(fileName);
		workbook = Workbook.createWorkbook(ops);
		WritableSheet sheet = workbook.createSheet(fileName, 0);
		Label tlabel = new Label(0, 0, Title);
		sheet.mergeCells(0, 0, titlelist.size(), 0);// �����
		tlabel.setCellFormat(wcf);
		sheet.addCell(tlabel);
		Label tl;
		for (int i = 0; i < titlelist.size(); i++) {
			tl = new Label(i, 1, (String) titlelist.get(i));
			tl.setCellFormat(wc);
			sheet.addCell(tl);
		}
		for (int j = 0; j < rowNum; j++) {
			for (int k = 0; k < titlelist.size(); k++) {
				Label clabel = new Label(k, j + 2, (String) c[j][k]);
				clabel.setCellFormat(swf);
				sheet.addCell(clabel);
			}
		}
		//����ϰ࿪ʼ
		int sumstate = 0;
		int sign = 0;// ��¼�ϰ����������ݿ��еĺϰ����
		int start, end;// �����ͬ�ϰ�Ŀ�ʼ�ͽ���
		int num = 0;// ��¼��ͬ�ĺϰ����
		for (int i = 0; i < rowNum; i++) {
			sumstate = Integer.valueOf((String) c[i][14]);
			if (i == 0) {// ��һ��
				sign = sumstate;
				num = 1;
				start = 0;
			} else {// ������
				if (sign == sumstate) {// �ϰ������ͬ,����ִ��
					num = num + 1;
					if (i == rowNum - 1) {
						sign = sumstate;
						end = i;
						if (num == 1) {
							// û�кϰֻ࣬�е���
							c[end - num + 1][14] = "����";
							num = 1;
						} else {
							c[end - num + 1][14] = "�ϰ�";
							for (int j = end - num + 2; j <= end; j++) {
								c[j][14] = "";
								c[j][15] = "";
								c[j][16] = "";
								c[j][17] = "";
							}
							sheet.mergeCells(14, end - num + 3, 14, end + 2);
							sheet.mergeCells(15, end - num + 3, 15, end + 2);
							sheet.mergeCells(16, end - num + 3, 16, end + 2);
							sheet.mergeCells(17, end - num + 3, 17, end + 2);
							num = 1;
						}
					}
				} else {// �ϰ������ͬ���ϲ���Ԫ��
					sign = sumstate;
					end = i - 1;
					if (num == 1) {
						// û�кϰֻ࣬�е���
						c[end - num + 1][14] = "����";
						num = 1;
						//�±��¼�(�������һ��)
						if(i==rowNum-1){
							c[end - num + 2][14] = "����";
						}
						
						
						
						
						
					} else {
						c[end - num + 1][14] = "�ϰ�";
						for (int j = end - num + 2; j <= end; j++) {
							c[j][14] = "";
							c[j][15] = "";
							c[j][16] = "";
							c[j][17] = "";
						}
						sheet.mergeCells(14, end - num + 3, 14, end + 2);
						sheet.mergeCells(15, end - num + 3, 15, end + 2);
						sheet.mergeCells(16, end - num + 3, 16, end + 2);
						sheet.mergeCells(17, end - num + 3, 17, end + 2);
						num = 1;
					}
				}
			}
		}
		//���Ͻ����ϰ�
		for (int j = 0; j < rowNum; j++) {
			for (int k = 0; k < titlelist.size(); k++) {
				Label clabel = new Label(k, j + 2, (String) c[j][k]);
				clabel.setCellFormat(swf);
				sheet.addCell(clabel);
			}
		}
		workbook.write();
		Filedownload.save(new File(fileName), null);
		workbook.close();
	}

	// �����ϰ������������
	public void exportSumstateTaskExcel(String fileName, String Title, List titlelist, Integer rowNum, String c[][]) throws WriteException, IOException {
		// ������ʽ
		WritableCellFormat wcf = null;
		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);// ���һ��Ϊ�Ƿ�italic,������
		wcf = new WritableCellFormat(wf);
		// ���뷽ʽ
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		// �Զ����ͷ��ʽ
		WritableCellFormat wc = null;
		WritableFont w = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
		wc = new WritableCellFormat(w);
		wc.setAlignment(Alignment.CENTRE);
		// �Զ��������и�ʽ
		WritableCellFormat swf = null;
		WritableFont ff = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
		swf = new WritableCellFormat(ff);
		swf.setAlignment(Alignment.CENTRE);
		swf.setVerticalAlignment(VerticalAlignment.CENTRE);
		WritableWorkbook workbook;
		OutputStream ops = new FileOutputStream(fileName);
		workbook = Workbook.createWorkbook(ops);
		WritableSheet sheet = workbook.createSheet(fileName, 0);
		Label tlabel = new Label(0, 0, Title);
		sheet.mergeCells(0, 0, titlelist.size(), 0);// �����
		tlabel.setCellFormat(wcf);
		sheet.addCell(tlabel);
		Label tl;
		for (int i = 0; i < titlelist.size(); i++) {
			tl = new Label(i, 1, (String) titlelist.get(i));
			tl.setCellFormat(wc);
			sheet.addCell(tl);
		}
		for (int j = 0; j < rowNum; j++) {
			for (int k = 0; k < titlelist.size(); k++) {
				Label clabel = new Label(k, j + 2, (String) c[j][k]);
				clabel.setCellFormat(swf);
				sheet.addCell(clabel);
			}
		}
		int sumstate = 0;
		int sign = 0;// ��¼�ϰ����������ݿ��еĺϰ����
		int start, end;// �����ͬ�ϰ�Ŀ�ʼ�ͽ���
		int num = 0;// ��¼��ͬ�ĺϰ����
		//�ϰ࿪ʼ
		for (int i = 0; i < rowNum; i++) {
			sumstate = Integer.valueOf((String) c[i][14]);
			if (i == 0) {// ��һ��
				sign = sumstate;
				num = 1;
				start = 0;
			} else {// ������
				if (sign == sumstate) {// �ϰ������ͬ,����ִ��
					num = num + 1;
					if (i == rowNum - 1) {
						sign = sumstate;
						end = i;
						if (num == 1) {
							// û�кϰֻ࣬�е���
							c[end - num + 1][14] = "����";
							num = 1;
						} else {
							c[end - num + 1][14] = "�ϰ�";
							for (int j = end - num + 2; j <= end; j++) {
								c[j][14] = "";
							}
							sheet.mergeCells(14, end - num + 3, 14, end + 2);
							num = 1;
						}
					}
				} else {// �ϰ������ͬ���ϲ���Ԫ��
					sign = sumstate;
					end = i - 1;
					if (num == 1) {
						// û�кϰֻ࣬�е���
						c[end - num + 1][14] = "����";
						num = 1;
					} else {
						c[end - num + 1][14] = "�ϰ�";
						for (int j = end - num + 2; j <= end; j++) {
							c[j][14] = "";
						}
						sheet.mergeCells(14, end - num + 3, 14, end + 2);
						num = 1;
					}
				}
			}
		}
		for (int j = 0; j < rowNum; j++) {
			for (int k = 0; k < titlelist.size(); k++) {
				Label clabel = new Label(k, j + 2, (String) c[j][k]);
				clabel.setCellFormat(swf);
				sheet.addCell(clabel);
			}
		}
		workbook.write();
		Filedownload.save(new File(fileName), null);
		workbook.close();
	}

	// �����ܹ�����
	public void exportWorkExcel(String fileName, String Title, Integer rowNum, String c[][]) throws WriteException, IOException {
		// ������ʽ
		WritableCellFormat wcf = null;
		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);// ���һ��Ϊ�Ƿ�italic,������
		wcf = new WritableCellFormat(wf);
		// ���뷽ʽ
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		// �Զ����ͷ��ʽ
		WritableCellFormat wc = null;
		WritableFont w = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
		wc = new WritableCellFormat(w);
		wc.setAlignment(Alignment.CENTRE);
		/*
		 * //�¼� //Label tlabel = new Label(0, 0,Title); WritableSheet sheet1 = null; sheet1.mergeCells(0, 0, 0, 2);//�ϲ���ͷ sheet1.mergeCells(1, 0, 1, 2); sheet1.mergeCells(2, 0, 2, 2); sheet1.mergeCells(3, 0, 3, 2); sheet1.mergeCells(3, 0, 3, 2);
		 */
		// �Զ��������и�ʽ
		WritableCellFormat swf = null;
		WritableFont ff = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
		swf = new WritableCellFormat(ff);
		swf.setAlignment(Alignment.CENTRE);
		WritableWorkbook workbook;
		OutputStream ops = new FileOutputStream(fileName);
		workbook = Workbook.createWorkbook(ops);
		WritableSheet sheet = workbook.createSheet(fileName, 0);
		/*
		 * Label tlabel = new Label(0, 0,Title); sheet.mergeCells(0, 0, titlelist.size(), 0);//����� tlabel.setCellFormat(wcf); sheet.addCell(tlabel);
		 */
		// ��ͷ����
		Label tlabel1 = new Label(0, 0, "��λ");
		sheet.mergeCells(0, 0, 0, 2);
		tlabel1.setCellFormat(wcf);
		sheet.addCell(tlabel1);
		Label tlabel2 = new Label(1, 0, "ְ����");
		sheet.mergeCells(1, 0, 1, 2);
		tlabel2.setCellFormat(wcf);
		sheet.addCell(tlabel2);
		Label tlabel3 = new Label(2, 0, "����");
		sheet.mergeCells(2, 0, 2, 2);
		tlabel3.setCellFormat(wcf);
		sheet.addCell(tlabel3);
		Label tlabel4 = new Label(3, 0, "ְ��");
		sheet.mergeCells(3, 0, 3, 2);
		tlabel4.setCellFormat(wcf);
		sheet.addCell(tlabel4);
		Label tlabel5 = new Label(4, 0, "��ʱ");
		sheet.mergeCells(4, 0, 9, 0);
		tlabel5.setCellFormat(wcf);
		sheet.addCell(tlabel5);
		Label tlabel6 = new Label(4, 1, "���ݷ�Ƹ��ʱ");
		sheet.mergeCells(4, 1, 4, 2);
		tlabel6.setCellFormat(wcf);
		sheet.addCell(tlabel6);
		Label tlabel7 = new Label(5, 1, "˫���ʱ");
		sheet.mergeCells(5, 1, 5, 2);
		tlabel7.setCellFormat(wcf);
		sheet.addCell(tlabel7);
		Label tlabel8 = new Label(6, 1, "������ͨ��ʱ�ϼ�");
		sheet.mergeCells(6, 1, 6, 2);
		tlabel8.setCellFormat(wcf);
		sheet.addCell(tlabel8);
		Label tlabel9 = new Label(7, 1, "ǰ�������ʱ��");
		sheet.mergeCells(7, 1, 9, 1);
		tlabel9.setCellFormat(wcf);
		sheet.addCell(tlabel9);
		Label tlabel10 = new Label(7, 2, "У�ſ�ʱ");
		sheet.mergeCells(7, 2, 7, 2);
		tlabel10.setCellFormat(wcf);
		sheet.addCell(tlabel10);
		Label tlabel11 = new Label(8, 2, "ʡ�ſ�ʱ");
		sheet.mergeCells(8, 2, 8, 2);
		tlabel11.setCellFormat(wcf);
		sheet.addCell(tlabel11);
		Label tlabel12 = new Label(9, 2, "���ſ�ʱ");
		sheet.mergeCells(9, 2, 9, 2);
		tlabel12.setCellFormat(wcf);
		sheet.addCell(tlabel12);
		for (int j = 0; j < rowNum; j++) {
			for (int k = 0; k < 10; k++) {
				Label clabel = new Label(k, j + 3, (String) c[j][k]);
				clabel.setCellFormat(swf);
				sheet.addCell(clabel);
			}
		}
		workbook.write();
		Filedownload.save(new File(fileName), null);
		workbook.close();
	}
	
	public void exportkyqkExcel(String fileName, String Title, List titlehxlist1,List titlezxlist1,List titlelist2,List namehxlist,List namezxlist) throws WriteException, IOException {
		// ������ʽ
		WritableCellFormat wcf = null;
		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);// ���һ��Ϊ�Ƿ�italic,������
		wcf = new WritableCellFormat(wf);
		// ���뷽ʽ
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		// �Զ����ͷ��ʽ
		WritableCellFormat wc = null;
		WritableFont w = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
		wc = new WritableCellFormat(w);
		wc.setAlignment(Alignment.CENTRE);
		// �Զ��������и�ʽ
		WritableCellFormat swf = null;
		WritableFont ff = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
		swf = new WritableCellFormat(ff);
		swf.setAlignment(Alignment.CENTRE);
		WritableWorkbook workbook;
		OutputStream ops = new FileOutputStream(fileName);
		workbook = Workbook.createWorkbook(ops);
		WritableSheet sheet = workbook.createSheet(fileName, 0);
		Label tlabel = new Label(0, 0, Title);
		sheet.mergeCells(0, 0, 34, 0);// �����
		tlabel.setCellFormat(wcf);
		sheet.addCell(tlabel);
		String type=(String) Sessions.getCurrent().getAttribute("type");
		if(type.equals("0"))
		{
			initHxProject(wc,swf,sheet,titlehxlist1,titlelist2,namehxlist);
			initZxProject(wc,swf,sheet,titlezxlist1,titlelist2,namezxlist);
		}
		else if(type.equals("1"))
		{
			initHxProject(wc,swf,sheet,titlehxlist1,titlelist2,namehxlist);
			initZxProject(wc,swf,sheet,titlezxlist1,titlelist2,namezxlist);
		}
		else if (type.equals("2"))
		{
			initHxProject(wc,swf,sheet,titlehxlist1,titlelist2,namehxlist);
		}
		else if(type.equals("3"))
		{
			initZxProject(wc,swf,sheet,titlezxlist1,titlelist2,namezxlist);
		}	
		workbook.write();
		Filedownload.save(new File(fileName), null);
		workbook.close();
	}
	
	
	
	
	public void exportjyqkExcel(String fileName, String Title, List titlezxlist1,List titlelist2,List namezxlist) throws WriteException, IOException {
		// ������ʽ
		WritableCellFormat wcf = null;
		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);// ���һ��Ϊ�Ƿ�italic,������
		wcf = new WritableCellFormat(wf);
		// ���뷽ʽ
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		// �Զ����ͷ��ʽ
		WritableCellFormat wc = null;
		WritableFont w = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
		wc = new WritableCellFormat(w);
		wc.setAlignment(Alignment.CENTRE);
		// �Զ��������и�ʽ
		WritableCellFormat swf = null;
		WritableFont ff = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
		swf = new WritableCellFormat(ff);
		swf.setAlignment(Alignment.CENTRE);
		WritableWorkbook workbook;
		OutputStream ops = new FileOutputStream(fileName);
		workbook = Workbook.createWorkbook(ops);
		WritableSheet sheet = workbook.createSheet(fileName, 0);
		Label tlabel = new Label(0, 0, Title);
		sheet.mergeCells(0, 0, 34, 0);// �����
		tlabel.setCellFormat(wcf);
		sheet.addCell(tlabel);
		String type=(String) Sessions.getCurrent().getAttribute("type");
//		if(type.equals("0"))
//		{
//			initHxProject(wc,swf,sheet,titlehxlist1,titlelist2,namehxlist);
//			initZxProject(wc,swf,sheet,titlezxlist1,titlelist2,namezxlist);
//		}
//		else if(type.equals("1"))
//		{
//			initHxProject(wc,swf,sheet,titlehxlist1,titlelist2,namehxlist);
//			initZxProject(wc,swf,sheet,titlezxlist1,titlelist2,namezxlist);
//		}
//		else if (type.equals("2"))
//		{
//			initHxProject(wc,swf,sheet,titlehxlist1,titlelist2,namehxlist);
//		}
//		else if(type.equals("3"))
//		{
			initZxProject(wc,swf,sheet,titlezxlist1,titlelist2,namezxlist);
//		}	
		workbook.write();
		Filedownload.save(new File(fileName), null);
		workbook.close();
	}
	
	
	
	
public void initHxProject(WritableCellFormat wc,WritableCellFormat swf,WritableSheet sheet ,List titlehxlist1,List titlelist2,List namehxlist) throws RowsExceededException, WriteException
{
	 //�������ݱ�ͷ
	   String[] size={"1","3","3","4","6","1","3","3","3","3","2","2"};
       ArrayList slist=new ArrayList();
       for(int s=0;s<size.length;s++)
       {
       	slist.add(size[s]);
       }
    int s=0;
    Label tl;
	tl = new Label(0, 1, (String) titlehxlist1.get(0));
	sheet.mergeCells(0,1,1,1);
	tl.setCellFormat(wc);
	sheet.addCell(tl);
	for (int i = 1; i < titlehxlist1.size(); i++) {
		s += Integer.parseInt(slist.get(i-1).toString());
		int order=Integer.parseInt(slist.get(i).toString());
		tl = new Label(s, 2, (String) titlehxlist1.get(i));
		tl.setCellFormat(wc);
		sheet.mergeCells(s,2,s+order-1,2);
		sheet.addCell(tl);
	}
	
	for (int j = 0; j < titlelist2.size(); j++) {
		tl = new Label(j+1, 3, (String) titlelist2.get(j));
		tl.setCellFormat(wc);
		sheet.addCell(tl);
	}
	//��������
	for (int q = 0; q < namehxlist.size(); q++) {
		tl = new Label(q+1, 4, (String) namehxlist.get(q));
		tl.setCellFormat(swf);
		sheet.addCell(tl);
	}	
}
public void initZxProject(WritableCellFormat wc,WritableCellFormat swf,WritableSheet sheet ,List titlezxlist1,List titlelist2,List namezxlist) throws RowsExceededException, WriteException
{
	String[] size={"1","3","3","4","6","1","3","3","3","3","2","2"};
    ArrayList slist=new ArrayList();
    for(int s=0;s<size.length;s++)
    {
    	slist.add(size[s]);
    }
 int s=0;
 Label tl;
//�������ݱ�ͷ
 int m=0;
	tl = new Label(0, 5, (String) titlezxlist1.get(0));
	sheet.mergeCells(0,5,1,5);
	tl.setCellFormat(wc);
	sheet.addCell(tl);
	for (int i = 1; i < titlezxlist1.size(); i++) {
		m+= Integer.parseInt(slist.get(i-1).toString());
		int order=Integer.parseInt(slist.get(i).toString());
		tl = new Label(m, 6, (String) titlezxlist1.get(i));
		tl.setCellFormat(wc);
		sheet.mergeCells(m,6,m+order-1,6);
		sheet.addCell(tl);
	}
	for (int n = 0; n < titlelist2.size(); n++) {
		tl = new Label(n+1, 7, (String) titlelist2.get(n));
		tl.setCellFormat(wc);
		sheet.addCell(tl);
	}
//��������
	for (int q = 0; q < namezxlist.size(); q++) {
		tl = new Label(q+1, 8, (String) namezxlist.get(q));
		tl.setCellFormat(swf);
		sheet.addCell(tl);
	}	
}

public void exportjyqkExcel(String fileName, String Title, List titlehxlist1,List titlezxlist1,List titlelist2,List namehxlist,List namezxlist) throws WriteException, IOException {
	// ������ʽ
	WritableCellFormat wcf = null;
	WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);// ���һ��Ϊ�Ƿ�italic,������
	wcf = new WritableCellFormat(wf);
	// ���뷽ʽ
	wcf.setAlignment(Alignment.CENTRE);
	wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
	// �Զ����ͷ��ʽ
	WritableCellFormat wc = null;
	WritableFont w = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
	wc = new WritableCellFormat(w);
	wc.setAlignment(Alignment.CENTRE);
	// �Զ��������и�ʽ
	WritableCellFormat swf = null;
	WritableFont ff = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
	swf = new WritableCellFormat(ff);
	swf.setAlignment(Alignment.CENTRE);
	WritableWorkbook workbook;
	OutputStream ops = new FileOutputStream(fileName);
	workbook = Workbook.createWorkbook(ops);
	WritableSheet sheet = workbook.createSheet(fileName, 0);
	Label tlabel = new Label(0, 0, Title);
	sheet.mergeCells(0, 0, 22, 0);// �����
	tlabel.setCellFormat(wcf);
	sheet.addCell(tlabel);
	String type=(String) Sessions.getCurrent().getAttribute("type");
	if(type.equals("0"))
	{
		initHxjyProject(wc,swf,sheet,titlehxlist1,titlelist2,namehxlist);
		initZxjyProject(wc,swf,sheet,titlezxlist1,titlelist2,namezxlist);
	}
	else if(type.equals("1"))
	{
		initHxjyProject(wc,swf,sheet,titlehxlist1,titlelist2,namehxlist);
		initZxjyProject(wc,swf,sheet,titlezxlist1,titlelist2,namezxlist);
	}
	else if (type.equals("2"))
	{
		initHxjyProject(wc,swf,sheet,titlehxlist1,titlelist2,namehxlist);
	}
	else if(type.equals("3"))
	{
		initZxjyProject(wc,swf,sheet,titlezxlist1,titlelist2,namezxlist);
	}	
	workbook.write();
	Filedownload.save(new File(fileName), null);
	workbook.close();
}

public void initHxjyProject(WritableCellFormat wc,WritableCellFormat swf,WritableSheet sheet ,List titlehxlist1,List titlelist2,List namehxlist) throws RowsExceededException, WriteException
{
	 //�������ݱ�ͷ
	   String[] size={"1","3","3","4","6","1","1","3"};
       ArrayList slist=new ArrayList();
       for(int s=0;s<size.length;s++)
       {
       	slist.add(size[s]);
       }
    int s=0;
    Label tl;
	tl = new Label(0, 1, (String) titlehxlist1.get(0));
	sheet.mergeCells(0,1,1,1);
	tl.setCellFormat(wc);
	sheet.addCell(tl);
	for (int i = 1; i < titlehxlist1.size(); i++) {
		s += Integer.parseInt(slist.get(i-1).toString());
		int order=Integer.parseInt(slist.get(i).toString());
		tl = new Label(s, 2, (String) titlehxlist1.get(i));
		tl.setCellFormat(wc);
		sheet.mergeCells(s,2,s+order-1,2);
		sheet.addCell(tl);
	}
	
	for (int j = 0; j < titlelist2.size(); j++) {
		tl = new Label(j+1, 3, (String) titlelist2.get(j));
		tl.setCellFormat(wc);
		sheet.addCell(tl);
	}
	//��������
	for (int q = 0; q < namehxlist.size(); q++) {
		tl = new Label(q+1, 4, (String) namehxlist.get(q));
		tl.setCellFormat(swf);
		sheet.addCell(tl);
	}	
}



public void initZxjyProject(WritableCellFormat wc,WritableCellFormat swf,WritableSheet sheet ,List titlezxlist1,List titlelist2,List namezxlist) throws RowsExceededException, WriteException
{
	String[] size={"1","3","3","4","6","1","1","3"};
    ArrayList slist=new ArrayList();
    for(int s=0;s<size.length;s++)
    {
    	slist.add(size[s]);
    }
 int s=0;
 Label tl;
//�������ݱ�ͷ
 int m=0;
	tl = new Label(0, 5, (String) titlezxlist1.get(0));
	sheet.mergeCells(0,5,1,5);
	tl.setCellFormat(wc);
	sheet.addCell(tl);
	for (int i = 1; i < titlezxlist1.size(); i++) {
		m+= Integer.parseInt(slist.get(i-1).toString());
		int order=Integer.parseInt(slist.get(i).toString());
		tl = new Label(m, 6, (String) titlezxlist1.get(i));
		tl.setCellFormat(wc);
		sheet.mergeCells(m,6,m+order-1,6);
		sheet.addCell(tl);
	}
	for (int n = 0; n < titlelist2.size(); n++) {
		tl = new Label(n+1, 7, (String) titlelist2.get(n));
		tl.setCellFormat(wc);
		sheet.addCell(tl);
	}
//��������
	for (int q = 0; q < namezxlist.size(); q++) {
		tl = new Label(q+1, 8, (String) namezxlist.get(q));
		tl.setCellFormat(swf);
		sheet.addCell(tl);
	}	
}



}
