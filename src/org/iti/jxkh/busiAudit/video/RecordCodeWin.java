package org.iti.jxkh.busiAudit.video;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoFile;
import org.iti.jxkh.service.JxkhVideoService;
import org.iti.jxkh.waterprint.PDFWaterMark;
import org.iti.jxkh.waterprint.Watermark;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.itextpdf.text.DocumentException;

public class RecordCodeWin extends Window implements AfterCompose{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8986184830140231615L;
	private Textbox record;
	private Jxkh_Video video;
	private JxkhVideoService jxkhVideoService;
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	}
	public void initWindow(){
		if(video.getRecordCode()==null || video.getRecordCode().equals("")){
			
		}else{
			record.setValue(video.getRecordCode());
		}
//		if(video.getRecordCode()!=null || !video.getRecordCode().equals("")){
//			record.setValue(video.getRecordCode());
//		}
	}
	public void onClick$save(){
		if(video.getState()==4){
			Set<?> files = video.getVideoFile();
			Object[] file = files.toArray();
			String filePath = "";
			String filePath2 = "";
			for (int i = 0; i < file.length; i++) {
				Jxkh_VideoFile f = (Jxkh_VideoFile) file[i];
				filePath=Executions.getCurrent().getDesktop().getWebApp().getRealPath("/upload/")+  f.getPath();
				filePath2=filePath.substring(0, filePath.lastIndexOf(File.separator));
				File awardFile = new File(filePath);
	//			ͼƬ��ˮӡ
				Watermark w = new Watermark();
				w.markImage(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/admin/waterprint/water.png"), awardFile.getPath(), awardFile.getPath(),
						-30);
	//			pdf��ˮӡ
				if (awardFile.isFile()) {
					int lastlength = Integer.parseInt(String.valueOf(awardFile.getName().length()));
					String dds = awardFile.getName().substring(awardFile.getName().lastIndexOf(".") + 1, lastlength);
					if (dds.toLowerCase().equalsIgnoreCase("pdf")) {
						try {
							new PDFWaterMark().createPDFWaterMark(awardFile.toString(),filePath2+ "/_" + awardFile.getName(), Executions.getCurrent().getDesktop().getWebApp().getRealPath("/admin/waterprint/water.png"), "�ӱ�ʡ��ѧ�����鱨�о�Ժ");
						} catch (IOException e) {
							e.printStackTrace();
						} catch (DocumentException e) {
							e.printStackTrace();
						}
						if(awardFile.exists())
							awardFile.delete();
							File filenew=new File(filePath2+ "/_" + awardFile.getName());
							filenew.renameTo(new File(awardFile.getPath()));
					}
				}
			}
	}
		video.setRecordCode(record.getValue());
		video.setState(Jxkh_Video.SAVE_RECORD);
		try{
			jxkhVideoService.saveOrUpdate(video);
			Messagebox.show("�����ű���ɹ���", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);	
		}catch(Exception e){
			e.printStackTrace();
			try {
				Messagebox.show("�����ű���ʧ�ܣ������ԣ�", "��ʾ", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.onClose();
	}
	
	public Jxkh_Video getVideo() {
		return video;
	}
	public void setVideo(Jxkh_Video video) {
		this.video = video;
	}
	/**
	 * <li>�����������رյ�ǰ���ڡ�
	 */
	public void onClick$close(){
		this.onClose();
	}
	
}

