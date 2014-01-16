package com.uniwin.asm.personal.ui.data.teacherinfo.qtqk;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.entity.Jxkh_HonourFile;
import org.iti.jxkh.service.RychService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

public class DownFileWindow extends Window implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Listbox fileList;
	private Toolbarbutton download;
	private Toolbarbutton close;
	List pathList = new ArrayList();
	Jxkh_Honour ch;
	RychService rychService;
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);		
	}
	/**
	 * ��ʼ��ҳ��
	 * @param ch
	 */
	public void initWindow(Jxkh_Honour ch) {
		this.ch = ch;
		pathList.clear();
		List filelt = new ArrayList();
		filelt.clear();
		List<Jxkh_HonourFile> filelist = rychService.findFileByHonour(ch);
		if(filelist.size() != 0) {
			for(int i=0;i<filelist.size();i++) {
				Jxkh_HonourFile file = filelist.get(i);
				filelt.add(file.getFileName());
				pathList.add(file.getFilePath());
			}
		}
		fileList.setModel(new ListModelList(filelt));		
	}
	/**
	 * �����ļ�
	 */
	public void onClic$download() {
		int index = fileList.getSelectedIndex();		
		if(!pathList.get(index).toString().equals("") || pathList.get(index) != null) {
			String filepath = pathList.get(index).toString().trim();
			/**
			 * �����ļ�
			 */
			String reportPath =filepath;
				File reportFile = new File(reportPath);
				try 
				{
					if(!reportFile.exists())
					{
						Messagebox.show("�ļ��Ѿ���ɾ����", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
						return;
					}
					Filedownload.save(reportFile,null);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		}else {
			try {
				Messagebox.show("���������Ѳ����ڴ��ļ����޷����أ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
	}
	/**
	 * �ر�ҳ��
	 */
	public void onClick$close() {
		this.detach();
	}
	

}
