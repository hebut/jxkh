package org.iti.jxkh.busiAudit.video;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.service.JxkhVideoService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

public class BatchAuditWin extends Window implements AfterCompose{

	/**
	 *  @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -490535543664378451L;
	private List<Jxkh_Video> videoList=new ArrayList<Jxkh_Video>();
	private Radio pass,temp;
	private JxkhVideoService jxkhVideoService;
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		pass.setChecked(true);
	}
	public void onClick$submit(){
		Short state = null;		
		if (pass.isChecked()) {
			state = JXKH_QKLW.BUSINESS_PASS;
		}else if(temp.isChecked()) {
			state = JXKH_QKLW.BUSINESS_TEMP_PASS;
		}else {
			state = JXKH_QKLW.BUSINESS_NOT_PASS;
		}
		for(int i=0;i<videoList.size();i++){
			Jxkh_Video video=videoList.get(i);
			video.setState(state);
			jxkhVideoService.saveOrUpdate(video);
		}
		try{
			Messagebox.show("批量审核成功！", "提示", Messagebox.OK, Messagebox.INFORMATION);	
		}catch(Exception e){
			e.printStackTrace();
			try {
				Messagebox.show("批量审核失败，请重试！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		this.onClose();
	}

	public List<Jxkh_Video> getVideoList() {
		return videoList;
	}
	public void setVideoList(List<Jxkh_Video> videoList) {
		this.videoList = videoList;
	}
	/**
	 * <li>功能描述：关闭当前窗口。
	 */
	public void onClick$close(){
		this.onClose();
	}
}
