package org.iti.gh.ghtj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.iti.gh.entity.GhQkdc;
import org.iti.gh.service.QkdcService;
import org.iti.xypt.entity.XyUserrole;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event; 
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Toolbarbutton;

import com.uniwin.framework.entity.WkTUser;

public class GhqkdctjTPanel extends Tabpanel implements AfterCompose  {
	XyUserrole xyuserrole=(XyUserrole)Executions.getCurrent().getArg().get("xyuserrole");
	
	Toolbarbutton a11,a12,a13,a14,a15,a16,a17;//科研项目国家级
	Toolbarbutton a21,a22,a23,a24,a25,a26,a27;//科研项目省部级
	Toolbarbutton a31,a32,a33,a34,a35,a36,a37;//科研项目横向
	Toolbarbutton a41,a42,a43,a44,a45,a46,a47;//科研项目其他
	
	Toolbarbutton a51,a52,a53,a54,a55,a56,a57;//科研成果论文
	Toolbarbutton a61,a62,a63,a64,a65,a66,a67;//科研成果著作/专利
	
	Toolbarbutton a71,a72,a73,a74,a75,a76,a77;//科研奖励国家级
	Toolbarbutton a81,a82,a83,a84,a85,a86,a87;//科研奖励省部级
	Toolbarbutton a91,a92,a93,a94,a95,a96,a97;//科研奖励其他
	
	Toolbarbutton b11,b12,b13,b14,b15,b16,b17;//交流与合作国际会议
	Toolbarbutton b21,b22,b23,b24,b25,b26,b27;//交流与合作国内会议
	Toolbarbutton b31,b32,b33,b34,b35,b36,b37;//交流与合作互访
	Toolbarbutton b41,b42,b43,b44,b45,b46,b47;//交流与合作其他
	
	Toolbarbutton b51,b52,b53,b54,b55,b56,b57;//教学成果国家级奖励
	Toolbarbutton b61,b62,b63,b64,b65,b66,b67;//教学成果省部级奖励（等级/数量）
	
	Toolbarbutton b71,b72,b73,b74,b75,b76,b77;//教学成果出版教材（数量）
	Toolbarbutton b81,b82,b83,b84,b85,b86,b87;//教学成果其他
	
//	WkTUser user;
	QkdcService qkdcService;

	Map<Toolbarbutton,List> ughmap=new HashMap();
	InnerListener inlistener=new InnerListener();
	//科研项目国家
	int[] kygj10=new int[6],kygj1115=new int[6],kygj11=new int[6],kygj12=new int[6],kygj13=new int[6],kygj14=new int[6],kygj15=new int[6];
	//科研项目省部
	int[] kysb10=new int[6],kysb1115=new int[6],kysb11=new int[6],kysb12=new int[6],kysb13=new int[6],kysb14=new int[6],kysb15=new int[6];
	
	//科研成果论文
	int[] kylw10=new int[4],kylw1115=new int[4],kylw11=new int[4],kylw12=new int[4],kylw13=new int[4],kylw14=new int[4],kylw15=new int[4];
	
	//科研成果著作/专利
	int[] kyzz10=new int[2],kyzz1115=new int[2],kyzz11=new int[2],kyzz12=new int[2],kyzz13=new int[2],kyzz14=new int[2],kyzz15=new int[2];
	
	//科研项目奖励国家级别
	int[] kyjlgj10=new int[4],kyjlgj1115=new int[4],kyjlgj11=new int[4],kyjlgj12=new int[4],kyjlgj13=new int[4],kyjlgj14=new int[4],kyjlgj15=new int[4];
	
	//科研项目奖励省部级别
	int[] kyjlsb10=new int[4],kyjlsb1115=new int[4],kyjlsb11=new int[4],kyjlsb12=new int[4],kyjlsb13=new int[4],kyjlsb14=new int[4],kyjlsb15=new int[4];
	
	//科研交流合作国际会议
	int[] kygjhy10=new int[3],kygjhy1115=new int[3],kygjhy11=new int[3],kygjhy12=new int[3],kygjhy13=new int[3],kygjhy14=new int[3],kygjhy15=new int[3];
	
	//科研交流合作国内会议
	int[] kygnhy10=new int[3],kygnhy1115=new int[3],kygnhy11=new int[3],kygnhy12=new int[3],kygnhy13=new int[3],kygnhy14=new int[3],kygnhy15=new int[3];
	
	//科研交流互访次数
	int[] kyhf10=new int[2],kyhf1115=new int[2],kyhf11=new int[2],kyhf12=new int[2],kyhf13=new int[2],kyhf14=new int[2],kyhf15=new int[2];
	
	//教学项目奖励国家级别
	int[] jxjlgj10=new int[4],jxjlgj1115=new int[4],jxjlgj11=new int[4],jxjlgj12=new int[4],jxjlgj13=new int[4],jxjlgj14=new int[4],jxjlgj15=new int[4];
	
	//教学项目奖励省部级别
	int[] jxjlsb10=new int[4],jxjlsb1115=new int[4],jxjlsb11=new int[4],jxjlsb12=new int[4],jxjlsb13=new int[4],jxjlsb14=new int[4],jxjlsb15=new int[4];
	
	String xmlbts="科研项目类别：1.自然基金 2.支撑计划 3.863项目 4.973项目 5.其他。（例如：国家级自然基金两项，支撑计划1项即为1/2,2/2）";
	String fblw="论文(SCI/EI/ISIP/核心)：逐项填写篇数，没有请填写0（例如：0/1/1/3) ";
	String hj="等级填写中文，（例如：一等奖一项，二等奖两项即填写一/1,二/2) ";
	String hy="交流与合作：（例如参加过一次国际会议即填写0/0/1） ";
	Map<Toolbarbutton,String> hearMap=new HashMap();
	
	public void afterCompose() { 
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
//		user=(WkTUser) Sessions.getCurrent().getAttribute("user");
		initShow();  
	}
	public void onClick$exportQkdt() throws FileNotFoundException, IOException,
			InterruptedException {
		String fpath = this.exportExcel();
		java.io.InputStream is = this.getDesktop().getWebApp()
				.getResourceAsStream(fpath);
		if (is != null) {
			Filedownload.save(is, "text/html", "情况调查表统计.xls");
		} else {
			Messagebox
					.show("服务器不存在此文件 ", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	} 
	private void initShow() {
		List kuidlist=qkdcService.getKuidByKdid(xyuserrole.getKdId()); 
		for(int i=0;i<kuidlist.size();i++){
			Long kuid=(Long)kuidlist.get(i);
			List qklist=qkdcService.findByKuid(kuid);
			System.out.println(kuid+"="+qklist.size());  
			if(qklist.size()==7){ 
				GhQkdc qk10=(GhQkdc)qklist.get(0);
				GhQkdc qk1115=(GhQkdc)qklist.get(1);
				GhQkdc qk11=(GhQkdc)qklist.get(2);
				GhQkdc qk12=(GhQkdc)qklist.get(3);
				GhQkdc qk13=(GhQkdc)qklist.get(4);
				GhQkdc qk14=(GhQkdc)qklist.get(5);
				GhQkdc qk15=(GhQkdc)qklist.get(6);
				//计算国家级别
				if(calculateAky(kygj10, qk10.getKyxmGj())){
					addMap(a11, new QkdcItem(kuid,qk10.getKyxmGj()));
				}
				if(calculateAky(kygj1115, qk1115.getKyxmGj())){
					addMap(a12, new QkdcItem(kuid,qk1115.getKyxmGj()));
				}
				if(calculateAky(kygj11, qk11.getKyxmGj())){
					addMap(a13, new QkdcItem(kuid,qk11.getKyxmGj()));
				}
				if(calculateAky(kygj12, qk12.getKyxmGj())){
					addMap(a14, new QkdcItem(kuid,qk12.getKyxmGj()));
				}
				if(calculateAky(kygj13, qk13.getKyxmGj())){
					addMap(a15, new QkdcItem(kuid,qk13.getKyxmGj()));
				}
				if(calculateAky(kygj14, qk14.getKyxmGj())){
					addMap(a16, new QkdcItem(kuid,qk14.getKyxmGj()));
				}
				if(calculateAky(kygj15, qk15.getKyxmGj())){
					addMap(a17, new QkdcItem(kuid,qk15.getKyxmGj()));
				}
				
				//计算省部级别
				if(calculateAky(kysb10, qk10.getKyxmSb())){
					addMap(a21, new QkdcItem(kuid,qk10.getKyxmSb()));
				}
				if(calculateAky(kysb1115, qk1115.getKyxmSb())){
					addMap(a22, new QkdcItem(kuid,qk1115.getKyxmSb()));
				}
				if(calculateAky(kysb11, qk11.getKyxmSb())){
					addMap(a23, new QkdcItem(kuid,qk11.getKyxmSb()));
				}
				if(calculateAky(kysb12, qk12.getKyxmSb())){
					addMap(a24, new QkdcItem(kuid,qk12.getKyxmSb()));
				}
				if(calculateAky(kysb13, qk13.getKyxmSb())){
					addMap(a25, new QkdcItem(kuid,qk13.getKyxmSb()));
				}
				if(calculateAky(kysb14, qk14.getKyxmSb())){
					addMap(a26, new QkdcItem(kuid,qk14.getKyxmSb()));
				}
				if(calculateAky(kysb15, qk15.getKyxmSb())){
					addMap(a27, new QkdcItem(kuid,qk15.getKyxmSb()));
				}
				//计算 科研项目横向项目数
				if(addCount(a31,qk10.getKyxmHx())){
					addMap(a31, new QkdcItem(kuid,qk10.getKyxmHx()));
				}
				if(addCount(a32,qk1115.getKyxmHx())){
					addMap(a32, new QkdcItem(kuid,qk1115.getKyxmHx()));
				}
				if(addCount(a33,qk11.getKyxmHx())){
					addMap(a33, new QkdcItem(kuid,qk11.getKyxmHx()));
				}
				if(addCount(a34,qk12.getKyxmHx())){
					addMap(a34, new QkdcItem(kuid,qk12.getKyxmHx()));
				}
				if(addCount(a35,qk13.getKyxmHx())){
					addMap(a35, new QkdcItem(kuid,qk13.getKyxmHx()));
				}
				if(addCount(a36,qk14.getKyxmHx())){
					addMap(a36, new QkdcItem(kuid,qk14.getKyxmHx()));
				}
				if(addCount(a37,qk15.getKyxmHx())){
					addMap(a37, new QkdcItem(kuid,qk15.getKyxmHx()));
				}
				//计算 科研项目其他项目数
				if(addCount(a41,qk10.getKyxmQt())){
					addMap(a41, new QkdcItem(kuid,qk10.getKyxmQt()));
				}
				if(addCount(a42,qk1115.getKyxmQt())){
					addMap(a42, new QkdcItem(kuid,qk1115.getKyxmQt()));
				}
				if(addCount(a43,qk11.getKyxmQt())){
					addMap(a43, new QkdcItem(kuid,qk11.getKyxmQt()));
				}
				if(addCount(a44,qk12.getKyxmQt())){
					addMap(a44, new QkdcItem(kuid,qk12.getKyxmQt()));
				}
				if(addCount(a45,qk13.getKyxmQt())){
					addMap(a45, new QkdcItem(kuid,qk13.getKyxmQt()));
				}
				if(addCount(a46,qk14.getKyxmQt())){
					addMap(a46, new QkdcItem(kuid,qk14.getKyxmQt()));
				}
				if(addCount(a47,qk15.getKyxmQt())){
					addMap(a47, new QkdcItem(kuid,qk15.getKyxmQt()));
				}
				//计算论文 
				if(calculate(kylw10, qk10.getKycgLw())){
					addMap(a51,  new QkdcItem(kuid,qk10.getKycgLw()));
				}
				if(calculate(kylw1115, qk1115.getKycgLw())){
					addMap(a52,  new QkdcItem(kuid,qk1115.getKycgLw()));
				}
				if(calculate(kylw11, qk11.getKycgLw())){
					addMap(a53,  new QkdcItem(kuid,qk11.getKycgLw()));
				}
				if(calculate(kylw12, qk12.getKycgLw())){
					addMap(a54,  new QkdcItem(kuid,qk12.getKycgLw()));
				}
				if(calculate(kylw13, qk13.getKycgLw())){
					addMap(a55,  new QkdcItem(kuid,qk13.getKycgLw()));
				}
				if(calculate(kylw14, qk14.getKycgLw())){
					addMap(a56,  new QkdcItem(kuid,qk14.getKycgLw()));
				}
				if(calculate(kylw15, qk15.getKycgLw())){
					addMap(a57,  new QkdcItem(kuid,qk15.getKycgLw()));
				}
				//计算专著/专利数目
				if(calculate(kyzz10, qk10.getKycgZl())){
					addMap(a61,  new QkdcItem(kuid,qk10.getKycgZl()));
				}
				if(calculate(kyzz1115, qk1115.getKycgZl())){
					addMap(a62,  new QkdcItem(kuid,qk1115.getKycgZl()));
				}
				if(calculate(kyzz11, qk11.getKycgZl())){
					addMap(a63,  new QkdcItem(kuid,qk11.getKycgZl()));
				}
				if(calculate(kyzz12, qk12.getKycgZl())){
					addMap(a64,  new QkdcItem(kuid,qk12.getKycgZl()));
				}
				if(calculate(kyzz13, qk13.getKycgZl())){
					addMap(a65,  new QkdcItem(kuid,qk13.getKycgZl()));
				}
				if(calculate(kyzz14, qk14.getKycgZl())){
					addMap(a66,  new QkdcItem(kuid,qk14.getKycgZl()));
				}
				if(calculate(kyzz15, qk15.getKycgZl())){
					addMap(a67,  new QkdcItem(kuid,qk15.getKycgZl()));
				}
				
				//科研项目国家级奖励
				if(calculateJL(kyjlgj10, qk10.getKyjlGj())){
					addMap(a71,  new QkdcItem(kuid,qk10.getKyjlGj()));
				}
				if(calculateJL(kyjlgj1115, qk1115.getKyjlGj())){
					addMap(a72,  new QkdcItem(kuid,qk1115.getKyjlGj()));
				}
				if(calculateJL(kyjlgj11, qk11.getKyjlGj())){
					addMap(a73,  new QkdcItem(kuid,qk11.getKyjlGj()));
				}
				if(calculateJL(kyjlgj12, qk12.getKyjlGj())){
					addMap(a74,  new QkdcItem(kuid,qk12.getKyjlGj()));
				}
				if(calculateJL(kyjlgj13, qk13.getKyjlGj())){
					addMap(a75,  new QkdcItem(kuid,qk13.getKyjlGj()));
				}
				if(calculateJL(kyjlgj14, qk14.getKyjlGj())){
					addMap(a76,  new QkdcItem(kuid,qk14.getKyjlGj()));
				}
				if(calculateJL(kyjlgj15, qk15.getKyjlGj())){
					addMap(a77,  new QkdcItem(kuid,qk15.getKyjlGj()));
				}
				
				//科研项目省部级奖励
				if(calculateJL(kyjlsb10, qk10.getKyjlSb())){
					addMap(a81,  new QkdcItem(kuid,qk10.getKyjlSb()));
				}
				if(calculateJL(kyjlsb1115, qk1115.getKyjlSb())){
					addMap(a82,  new QkdcItem(kuid,qk1115.getKyjlSb()));
				}
				if(calculateJL(kyjlsb11, qk11.getKyjlSb())){
					addMap(a83,  new QkdcItem(kuid,qk11.getKyjlSb()));
				}
				if(calculateJL(kyjlsb12, qk12.getKyjlSb())){
					addMap(a84,  new QkdcItem(kuid,qk12.getKyjlSb()));
				}
				if(calculateJL(kyjlsb13, qk13.getKyjlSb())){
					addMap(a85,  new QkdcItem(kuid,qk13.getKyjlSb()));
				}
				if(calculateJL(kyjlsb14, qk14.getKyjlSb())){
					addMap(a86,  new QkdcItem(kuid,qk14.getKyjlSb()));
				}
				if(calculateJL(kyjlsb15, qk15.getKyjlSb())){
					addMap(a87,  new QkdcItem(kuid,qk15.getKyjlSb()));
				}
				
				//计算 科研项目奖励其他项目数
				if(addCount(a91,qk10.getKyjlQt())){
					addMap(a91, new QkdcItem(kuid,qk10.getKyjlQt()));
				}
				if(addCount(a92,qk1115.getKyjlQt())){
					addMap(a92, new QkdcItem(kuid,qk1115.getKyjlQt()));
				}
				if(addCount(a93,qk11.getKyjlQt())){
					addMap(a93, new QkdcItem(kuid,qk11.getKyjlQt()));
				}
				if(addCount(a94,qk12.getKyjlQt())){
					addMap(a94, new QkdcItem(kuid,qk12.getKyjlQt()));
				}
				if(addCount(a95,qk13.getKyjlQt())){
					addMap(a95, new QkdcItem(kuid,qk13.getKyjlQt()));
				}
				if(addCount(a96,qk14.getKyjlQt())){
					addMap(a96, new QkdcItem(kuid,qk14.getKyjlQt()));
				}
				if(addCount(a97,qk15.getKyjlQt())){
					addMap(a97, new QkdcItem(kuid,qk15.getKyjlQt()));
				}
				//计算国际会议
				if(calculate(kygjhy10, qk10.getKyhzGj())){
					addMap(b11, new QkdcItem(kuid, qk10.getKyhzGj()));
				}
				if(calculate(kygjhy1115, qk1115.getKyhzGj())){
					addMap(b12, new QkdcItem(kuid, qk1115.getKyhzGj()));
				}
				if(calculate(kygjhy11, qk11.getKyhzGj())){
					addMap(b13, new QkdcItem(kuid, qk11.getKyhzGj()));
				}
				if(calculate(kygjhy12, qk12.getKyhzGj())){
					addMap(b14, new QkdcItem(kuid, qk12.getKyhzGj()));
				}
				if(calculate(kygjhy13, qk13.getKyhzGj())){
					addMap(b15, new QkdcItem(kuid, qk13.getKyhzGj()));
				}
				if(calculate(kygjhy14, qk14.getKyhzGj())){
					addMap(b16, new QkdcItem(kuid, qk14.getKyhzGj()));
				}
				if(calculate(kygjhy15, qk15.getKyhzGj())){
					addMap(b17, new QkdcItem(kuid, qk15.getKyhzGj()));
				}
				
				//计算国内会议
				if(calculate(kygnhy10, qk10.getKyhzGn())){
					addMap(b21, new QkdcItem(kuid, qk10.getKyhzGn()));
				}
				if(calculate(kygnhy1115, qk1115.getKyhzGn())){
					addMap(b22, new QkdcItem(kuid, qk1115.getKyhzGn()));
				}
				if(calculate(kygnhy11, qk11.getKyhzGn())){
					addMap(b23, new QkdcItem(kuid, qk11.getKyhzGn()));
				}
				if(calculate(kygnhy12, qk12.getKyhzGn())){
					addMap(b24, new QkdcItem(kuid, qk12.getKyhzGn()));
				}
				if(calculate(kygnhy13, qk13.getKyhzGn())){
					addMap(b25, new QkdcItem(kuid, qk13.getKyhzGn()));
				}
				if(calculate(kygnhy14, qk14.getKyhzGn())){
					addMap(b26, new QkdcItem(kuid, qk14.getKyhzGn()));
				}
				if(calculate(kygnhy15, qk15.getKyhzGn())){
					addMap(b27, new QkdcItem(kuid, qk15.getKyhzGn()));
				}
				
				//计算科研互访
				if(calculate(kyhf10, qk10.getKyhzHf())){
					addMap(b31, new QkdcItem(kuid, qk10.getKyhzHf()));
				}
				if(calculate(kyhf1115, qk1115.getKyhzHf())){
					addMap(b32, new QkdcItem(kuid, qk1115.getKyhzHf()));
				}
				if(calculate(kyhf11, qk11.getKyhzHf())){
					addMap(b33, new QkdcItem(kuid, qk11.getKyhzHf()));
				}
				if(calculate(kyhf12, qk12.getKyhzHf())){
					addMap(b34, new QkdcItem(kuid, qk12.getKyhzHf()));
				}
				if(calculate(kyhf13, qk13.getKyhzHf())){
					addMap(b35, new QkdcItem(kuid, qk13.getKyhzHf()));
				}
				if(calculate(kyhf14, qk14.getKyhzHf())){
					addMap(b36, new QkdcItem(kuid, qk14.getKyhzHf()));
				}
				if(calculate(kyhf15, qk15.getKyhzHf())){
					addMap(b37, new QkdcItem(kuid, qk15.getKyhzHf()));
				}
				
				//计算科研交流其他次数 
				if(addCount(b41,qk10.getKyhzQt())){
					addMap(b41, new QkdcItem(kuid,qk10.getKyhzQt()));
				}
				if(addCount(b42,qk1115.getKyhzQt())){
					addMap(b42, new QkdcItem(kuid,qk1115.getKyhzQt()));
				}
				if(addCount(b43,qk11.getKyhzQt())){
					addMap(b43, new QkdcItem(kuid,qk11.getKyhzQt()));
				}
				if(addCount(b44,qk12.getKyhzQt())){
					addMap(b44, new QkdcItem(kuid,qk12.getKyhzQt()));
				}
				if(addCount(b45,qk13.getKyhzQt())){
					addMap(b45, new QkdcItem(kuid,qk13.getKyhzQt()));
				}
				if(addCount(b46,qk14.getKyhzQt())){
					addMap(b46, new QkdcItem(kuid,qk14.getKyhzQt()));
				}
				if(addCount(b47,qk15.getKyhzQt())){
					addMap(b47, new QkdcItem(kuid,qk15.getKyhzQt()));
				}
				
				//计算教学成果国家级奖励 
				if(calculateJL(jxjlgj10, qk10.getJxcgGj())){
					addMap(b51,  new QkdcItem(kuid,qk10.getJxcgGj()));
				}
				if(calculateJL(jxjlgj1115, qk1115.getJxcgGj())){
					addMap(b52,  new QkdcItem(kuid,qk1115.getJxcgGj()));
				}
				if(calculateJL(jxjlgj11, qk11.getJxcgGj())){
					addMap(b53,  new QkdcItem(kuid,qk11.getJxcgGj()));
				}
				if(calculateJL(jxjlgj12, qk12.getJxcgGj())){
					addMap(b54,  new QkdcItem(kuid,qk12.getJxcgGj()));
				}
				if(calculateJL(jxjlgj13, qk13.getJxcgGj())){
					addMap(b55,  new QkdcItem(kuid,qk13.getJxcgGj()));
				}
				if(calculateJL(jxjlgj14, qk14.getJxcgGj())){
					addMap(b56,  new QkdcItem(kuid,qk14.getJxcgGj()));
				}
				if(calculateJL(jxjlgj15, qk15.getJxcgGj())){
					addMap(b57,  new QkdcItem(kuid,qk15.getJxcgGj()));
				}
								
				//科研项目省部级奖励
				if(calculateJL(jxjlsb10, qk10.getJxcgSb())){
					addMap(b61,  new QkdcItem(kuid,qk10.getJxcgSb()));
				} 
				System.out.println(kuid+"=="+ qk1115.getJxcgSb());
				if(calculateJL(jxjlsb1115, qk1115.getJxcgSb())){
					addMap(b62,  new QkdcItem(kuid,qk1115.getJxcgSb()));
				}
				if(calculateJL(jxjlsb11, qk11.getJxcgSb())){
					addMap(b63,  new QkdcItem(kuid,qk11.getJxcgSb()));
				}
				if(calculateJL(jxjlsb12, qk12.getJxcgSb())){
					addMap(b64,  new QkdcItem(kuid,qk12.getJxcgSb()));
				}
				if(calculateJL(jxjlsb13, qk13.getJxcgSb())){
					addMap(b65,  new QkdcItem(kuid,qk13.getJxcgSb()));
				}
				if(calculateJL(jxjlsb14, qk14.getJxcgSb())){
					addMap(b66,  new QkdcItem(kuid,qk14.getJxcgSb()));
				}
				if(calculateJL(jxjlsb15, qk15.getJxcgSb())){
					addMap(b67,  new QkdcItem(kuid,qk15.getJxcgSb()));
				}
				
				//计算出版教材数目
				if(addCount(b71,qk10.getJxcgCb())){
					addMap(b71, new QkdcItem(kuid,qk10.getJxcgCb()));
				}
				if(addCount(b72,qk1115.getJxcgCb())){
					addMap(b72, new QkdcItem(kuid,qk1115.getJxcgCb()));
				}
				if(addCount(b73,qk11.getJxcgCb())){
					addMap(b73, new QkdcItem(kuid,qk11.getJxcgCb()));
				}
				if(addCount(b74,qk12.getJxcgCb())){
					addMap(b74, new QkdcItem(kuid,qk12.getJxcgCb()));
				}
				if(addCount(b75,qk13.getJxcgCb())){
					addMap(b75, new QkdcItem(kuid,qk13.getJxcgCb()));
				}
				if(addCount(b76,qk14.getJxcgCb())){
					addMap(b76, new QkdcItem(kuid,qk14.getJxcgCb()));
				}
				if(addCount(b77,qk15.getJxcgCb())){
					addMap(b77, new QkdcItem(kuid,qk15.getJxcgCb()));
				}
				
				//计算教学成果其他项目数目
				if(addCount(b81,qk10.getJxcgQt())){
					addMap(b81, new QkdcItem(kuid,qk10.getJxcgQt()));
				}
				if(addCount(b82,qk1115.getJxcgQt())){
					addMap(b82, new QkdcItem(kuid,qk1115.getJxcgQt()));
				}
				if(addCount(b83,qk11.getJxcgQt())){
					addMap(b83, new QkdcItem(kuid,qk11.getJxcgQt()));
				}
				if(addCount(b84,qk12.getJxcgQt())){
					addMap(b84, new QkdcItem(kuid,qk12.getJxcgQt()));
				}
				if(addCount(b85,qk13.getJxcgQt())){
					addMap(b85, new QkdcItem(kuid,qk13.getJxcgQt()));
				}
				if(addCount(b86,qk14.getJxcgQt())){
					addMap(b86, new QkdcItem(kuid,qk14.getJxcgQt()));
				}
				if(addCount(b87,qk15.getJxcgQt())){
					addMap(b87, new QkdcItem(kuid,qk15.getJxcgQt()));
				}
			}else{
				System.out.println(xyuserrole.getUser().getKuName()+"情况调查列不全");
			}
		}
		
		a11.setLabel(getKycg(kygj10));a12.setLabel(getKycg(kygj1115));a13.setLabel(getKycg(kygj11));
		a14.setLabel(getKycg(kygj12));a15.setLabel(getKycg(kygj13));a16.setLabel(getKycg(kygj14));a17.setLabel(getKycg(kygj15));
		
		a21.setLabel(getKycg(kysb10));a22.setLabel(getKycg(kysb1115));a23.setLabel(getKycg(kysb11));
		a24.setLabel(getKycg(kysb12));a25.setLabel(getKycg(kysb13));a26.setLabel(getKycg(kysb14));a27.setLabel(getKycg(kysb15));
		
		a51.setLabel(getIntege(kylw10)); a52.setLabel(getIntege(kylw1115)); a53.setLabel(getIntege(kylw11)); 
		a54.setLabel(getIntege(kylw12)); a55.setLabel(getIntege(kylw13)); a56.setLabel(getIntege(kylw14)); a57.setLabel(getIntege(kylw15)); 
		
		a61.setLabel(getIntege(kyzz10)); a62.setLabel(getIntege(kyzz1115)); a63.setLabel(getIntege(kyzz11)); 
		a64.setLabel(getIntege(kyzz12)); a65.setLabel(getIntege(kyzz13)); a66.setLabel(getIntege(kyzz14)); a67.setLabel(getIntege(kyzz15)); 
		
		a71.setLabel(getKyJl(kyjlgj10));a72.setLabel(getKyJl(kyjlgj1115));a73.setLabel(getKyJl(kyjlgj11));
		a74.setLabel(getKyJl(kyjlgj12));a75.setLabel(getKyJl(kyjlgj13));a76.setLabel(getKyJl(kyjlgj14));a77.setLabel(getKyJl(kyjlgj15));
		
		a81.setLabel(getKyJl(kyjlsb10));a82.setLabel(getKyJl(kyjlsb1115));a83.setLabel(getKyJl(kyjlsb11));
		a84.setLabel(getKyJl(kyjlsb12));a85.setLabel(getKyJl(kyjlsb13));a86.setLabel(getKyJl(kyjlsb14));a87.setLabel(getKyJl(kyjlsb15));
		
		b11.setLabel(getIntege(kygjhy10));b12.setLabel(getIntege(kygjhy1115));b13.setLabel(getIntege(kygjhy11));
		b14.setLabel(getIntege(kygjhy12));b15.setLabel(getIntege(kygjhy13));b16.setLabel(getIntege(kygjhy14));b17.setLabel(getIntege(kygjhy15));
		
		b21.setLabel(getIntege(kygnhy10));b22.setLabel(getIntege(kygnhy1115));b23.setLabel(getIntege(kygnhy11));
		b24.setLabel(getIntege(kygnhy12));b25.setLabel(getIntege(kygnhy13));b26.setLabel(getIntege(kygnhy14));b27.setLabel(getIntege(kygnhy15));
		
		b31.setLabel(getIntege(kyhf10));b32.setLabel(getIntege(kyhf1115));b33.setLabel(getIntege(kyhf11));
		b34.setLabel(getIntege(kyhf12));b35.setLabel(getIntege(kyhf13));b36.setLabel(getIntege(kyhf14));b37.setLabel(getIntege(kyhf15));
		
		b51.setLabel(getKyJl(jxjlgj10));b52.setLabel(getKyJl(jxjlgj1115));b53.setLabel(getKyJl(jxjlgj11));
		b54.setLabel(getKyJl(jxjlgj12));b55.setLabel(getKyJl(jxjlgj13));b56.setLabel(getKyJl(jxjlgj14));b57.setLabel(getKyJl(jxjlgj15));
		
		b61.setLabel(getKyJl(jxjlsb10));b62.setLabel(getKyJl(jxjlsb1115));b63.setLabel(getKyJl(jxjlsb11));
		b64.setLabel(getKyJl(jxjlsb12));b65.setLabel(getKyJl(jxjlsb13));b66.setLabel(getKyJl(jxjlsb14));b67.setLabel(getKyJl(jxjlsb15));
		
		addListener(a11);addListener(a12);addListener(a13);addListener(a14);addListener(a15);addListener(a16);addListener(a17);
		
		addListener(a21);addListener(a22);addListener(a23);addListener(a24);addListener(a25);addListener(a26);addListener(a27);
		
		addListener(a31);addListener(a32);addListener(a33);addListener(a34);addListener(a35);addListener(a36);addListener(a37);
		addListener(a41);addListener(a42);addListener(a43);addListener(a44);addListener(a45);addListener(a46);addListener(a47);
		addListener(a51);addListener(a52);addListener(a53);addListener(a54);addListener(a55);addListener(a56);addListener(a57);
		addListener(a61);addListener(a62);addListener(a63);addListener(a64);addListener(a65);addListener(a66);addListener(a67);
		addListener(a71);addListener(a72);addListener(a73);addListener(a74);addListener(a75);addListener(a76);addListener(a77);
		addListener(a81);addListener(a82);addListener(a83);addListener(a84);addListener(a85);addListener(a86);addListener(a87);
		addListener(a91);addListener(a92);addListener(a93);addListener(a94);addListener(a95);addListener(a96);addListener(a97);
		
		addListener(b11);addListener(b12);addListener(b13);addListener(b14);addListener(b15);addListener(b16);addListener(b17);
		addListener(b21);addListener(b22);addListener(b23);addListener(b24);addListener(b25);addListener(b26);addListener(b27);
		addListener(b31);addListener(b32);addListener(b33);addListener(b34);addListener(b35);addListener(b36);addListener(b37);
		addListener(b41);addListener(b42);addListener(b43);addListener(b44);addListener(b45);addListener(b46);addListener(b47);
		addListener(b51);addListener(b52);addListener(b53);addListener(b54);addListener(b55);addListener(b56);addListener(b57);
		addListener(b61);addListener(b62);addListener(b63);addListener(b64);addListener(b65);addListener(b66);addListener(b67);
		addListener(b71);addListener(b72);addListener(b73);addListener(b74);addListener(b75);addListener(b76);addListener(b77);
		addListener(b81);addListener(b82);addListener(b83);addListener(b84);addListener(b85);addListener(b86);addListener(b87);
		 
		//添加提示及表头
		
		String header1="国家级（类别/项数)";
		addOpenTitle(a11,xmlbts,header1);addOpenTitle(a12,xmlbts,header1);addOpenTitle(a13,xmlbts,header1);
		addOpenTitle(a14,xmlbts,header1);addOpenTitle(a15,xmlbts,header1);addOpenTitle(a16,xmlbts,header1);addOpenTitle(a17,xmlbts,header1);
		
		String header2="省部级（类别/项数)"; 
		addOpenTitle(a21,xmlbts,header2);addOpenTitle(a22,xmlbts,header2);addOpenTitle(a23,xmlbts,header2);
		addOpenTitle(a24,xmlbts,header2);addOpenTitle(a25,xmlbts,header2);addOpenTitle(a26,xmlbts,header2);addOpenTitle(a27,xmlbts,header2);
		
		String header3="横向（项数）"; 
		addOpenTitle(a31,null,header3);addOpenTitle(a32,null,header3);addOpenTitle(a33,null,header3);
		addOpenTitle(a34,null,header3);addOpenTitle(a35,null,header3);addOpenTitle(a36,null,header3);addOpenTitle(a37,null,header3);
		
		String header4="其他（项数）"; 
		addOpenTitle(a41,null,header4);addOpenTitle(a42,null,header4);addOpenTitle(a43,null,header4);
		addOpenTitle(a44,null,header4);addOpenTitle(a45,null,header4);addOpenTitle(a46,null,header4);addOpenTitle(a47,null,header4);
		
		String header5="论文 （SCI/EI/ISTP/核心）（篇数）"; 
		addOpenTitle(a51,fblw,header5);addOpenTitle(a52,fblw,header5);addOpenTitle(a53,fblw,header5);
		addOpenTitle(a54,fblw,header5);addOpenTitle(a55,fblw,header5);addOpenTitle(a56,fblw,header5);addOpenTitle(a57,fblw,header5);
		
		String header6="著作/专利"; 
		addOpenTitle(a61,null,header6);addOpenTitle(a62,null,header6);addOpenTitle(a63,null,header6);
		addOpenTitle(a64,null,header6);addOpenTitle(a65,null,header6);addOpenTitle(a66,null,header6);addOpenTitle(a67,null,header6);
		
		String header7="国家级（等级/项数)"; 
		addOpenTitle(a71,hj,header7);addOpenTitle(a72,hj,header7);addOpenTitle(a73,hj,header7);
		addOpenTitle(a74,hj,header7);addOpenTitle(a75,hj,header7);addOpenTitle(a76,hj,header7);addOpenTitle(a77,hj,header7);
		
		String header8="省部级（等级/项数)"; 
		addOpenTitle(a81,hj,header8);addOpenTitle(a82,hj,header8);addOpenTitle(a83,hj,header8);
		addOpenTitle(a84,hj,header8);addOpenTitle(a85,hj,header8);addOpenTitle(a86,hj,header8);addOpenTitle(a87,hj,header8);
		
		String header9="其他（项数）"; 
		addOpenTitle(a91,null,header9);addOpenTitle(a92,null,header9);addOpenTitle(a93,null,header9);
		addOpenTitle(a94,null,header9);addOpenTitle(a95,null,header9);addOpenTitle(a96,null,header9);addOpenTitle(a97,null,header9);
		
		String header10="国际会议（主办/承办/参加）"; 
		addOpenTitle(b11,hy,header10);addOpenTitle(b12,hy,header10);addOpenTitle(b13,hy,header10);
		addOpenTitle(b14,hy,header10);addOpenTitle(b15,hy,header10);addOpenTitle(b16,hy,header10);addOpenTitle(b17,hy,header10);
		
		String header11="国内会议（主办/承办/参加）"; 
		addOpenTitle(b21,hy,header11);addOpenTitle(b22,hy,header11);addOpenTitle(b23,hy,header11);
		addOpenTitle(b24,hy,header11);addOpenTitle(b25,hy,header11);addOpenTitle(b26,hy,header11);addOpenTitle(b27,hy,header11);
		
		String header12="互访（次数/人数）"; 
		addOpenTitle(b31,null,header12);addOpenTitle(b32,null,header12);addOpenTitle(b33,null,header12);
		addOpenTitle(b34,null,header12);addOpenTitle(b35,null,header12);addOpenTitle(b36,null,header12);addOpenTitle(b37,null,header12);
		
		String header13="其他"; 
		addOpenTitle(b41,null,header13);addOpenTitle(b42,null,header13);addOpenTitle(b43,null,header13);
		addOpenTitle(b44,null,header13);addOpenTitle(b45,null,header13);addOpenTitle(b46,null,header13);addOpenTitle(b47,null,header13);
		
		String header14="国家级奖励（等级/数量）"; 
		addOpenTitle(b51,hj,header14);addOpenTitle(b52,hj,header14);addOpenTitle(b53,hj,header14);
		addOpenTitle(b54,hj,header14);addOpenTitle(b55,hj,header14);addOpenTitle(b56,hj,header14);addOpenTitle(b57,hj,header14);
		
		String header15="省部级奖励（等级/数量）"; 
		addOpenTitle(b61,hj,header15);addOpenTitle(b62,hj,header15);addOpenTitle(b63,hj,header15);
		addOpenTitle(b64,hj,header15);addOpenTitle(b65,hj,header15);addOpenTitle(b66,hj,header15);addOpenTitle(b67,hj,header15);
		
		String header16="出版教材（数量）"; 
		addOpenTitle(b71,null,header16);addOpenTitle(b72,null,header16);addOpenTitle(b73,null,header16);
		addOpenTitle(b74,null,header16);addOpenTitle(b75,null,header16);addOpenTitle(b76,null,header16);addOpenTitle(b77,null,header16);
		
		String header17="其他"; 
		addOpenTitle(b81,null,header17);addOpenTitle(b82,null,header17);addOpenTitle(b83,null,header17);
		addOpenTitle(b84,null,header17);addOpenTitle(b85,null,header17);addOpenTitle(b86,null,header17);addOpenTitle(b87,null,header17);
		
	}
	
	public void onClick$exportExcel() throws FileNotFoundException, IOException, InterruptedException{
		exportExcel();
	}
	
	public String exportExcel() throws FileNotFoundException, IOException, InterruptedException{
		
		//将excel中数据导入
		POIFSFileSystem fs;
		HSSFWorkbook  wb;
		String filePath = this.getDesktop().getWebApp().getRealPath("/upload/info/plan/");
		fs = new POIFSFileSystem(new FileInputStream(filePath+"\\sample.xls"));
		wb = new HSSFWorkbook(fs);   
		HSSFSheet sheet = wb.getSheetAt(0);  
		Short index_2010=3;
		Short index_1115=4;
		Short index_2011=5;
		Short index_2012=6;
		Short index_2013=7;
		Short index_2014=8;
		Short index_2015=9; 
//		HSSFRow  testRow=sheet.getRow(2);//科研项目国家级
//		for(short i=0;i<testRow.getLastCellNum();i++){
//			HSSFCell ky=testRow.getCell(i);
//			System.out.println(ky.getCellType()==HSSFCell.CELL_TYPE_NUMERIC?ky.getNumericCellValue():ky.getStringCellValue());
//		}
		
		HSSFRow  row=sheet.getRow(3);//科研项目国家级
		HSSFCell c2010=row.getCell(index_2010);
		HSSFCell c1115=row.getCell(index_1115);
		HSSFCell c2011=row.getCell(index_2011);
		HSSFCell c2012=row.getCell(index_2012);
		HSSFCell c2013=row.getCell(index_2013);
		HSSFCell c2014=row.getCell(index_2014);
		HSSFCell c2015=row.getCell(index_2015);
		
		setCell(c2010,a11,row,index_2010);
		setCell(c1115,a12,row,index_1115);
		setCell(c2011,a13,row,index_2011);
		setCell(c2012,a14,row,index_2012);
		setCell(c2013,a15,row,index_2013);
		setCell(c2014,a16,row,index_2014);
		setCell(c2015,a17,row,index_2015);
		
		    row=sheet.getRow(4); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015); 
			setCell(c2010,a21,row,index_2010);
			setCell(c1115,a22,row,index_1115);
			setCell(c2011,a23,row,index_2011);
			setCell(c2012,a24,row,index_2012);
			setCell(c2013,a25,row,index_2013);
			setCell(c2014,a26,row,index_2014);
			setCell(c2015,a27,row,index_2015);

			row=sheet.getRow(5); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,a31,row,index_2010);
			setCell(c1115,a32,row,index_1115);
			setCell(c2011,a33,row,index_2011);
			setCell(c2012,a34,row,index_2012);
			setCell(c2013,a35,row,index_2013);
			setCell(c2014,a36,row,index_2014);
			setCell(c2015,a37,row,index_2015);
			
			row=sheet.getRow(6); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,a41,row,index_2010);
			setCell(c1115,a42,row,index_1115);
			setCell(c2011,a43,row,index_2011);
			setCell(c2012,a44,row,index_2012);
			setCell(c2013,a45,row,index_2013);
			setCell(c2014,a46,row,index_2014);
			setCell(c2015,a47,row,index_2015);
			
			row=sheet.getRow(7); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,a51,row,index_2010);
			setCell(c1115,a52,row,index_1115);
			setCell(c2011,a53,row,index_2011);
			setCell(c2012,a54,row,index_2012);
			setCell(c2013,a55,row,index_2013);
			setCell(c2014,a56,row,index_2014);
			setCell(c2015,a57,row,index_2015);
			
			row=sheet.getRow(8); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,a61,row,index_2010);
			setCell(c1115,a62,row,index_1115);
			setCell(c2011,a63,row,index_2011);
			setCell(c2012,a64,row,index_2012);
			setCell(c2013,a65,row,index_2013);
			setCell(c2014,a66,row,index_2014);
			setCell(c2015,a67,row,index_2015);
			
			row=sheet.getRow(9); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,a71,row,index_2010);
			setCell(c1115,a72,row,index_1115);
			setCell(c2011,a73,row,index_2011);
			setCell(c2012,a74,row,index_2012);
			setCell(c2013,a75,row,index_2013);
			setCell(c2014,a76,row,index_2014);
			setCell(c2015,a77,row,index_2015);
			
			row=sheet.getRow(10); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,a81,row,index_2010);
			setCell(c1115,a82,row,index_1115);
			setCell(c2011,a83,row,index_2011);
			setCell(c2012,a84,row,index_2012);
			setCell(c2013,a85,row,index_2013);
			setCell(c2014,a86,row,index_2014);
			setCell(c2015,a87,row,index_2015);
			
			row=sheet.getRow(11); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,a91,row,index_2010);
			setCell(c1115,a92,row,index_1115);
			setCell(c2011,a93,row,index_2011);
			setCell(c2012,a94,row,index_2012);
			setCell(c2013,a95,row,index_2013);
			setCell(c2014,a96,row,index_2014);
			setCell(c2015,a97,row,index_2015);
			
			row=sheet.getRow(12); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,b11,row,index_2010);
			setCell(c1115,b12,row,index_1115);
			setCell(c2011,b13,row,index_2011);
			setCell(c2012,b14,row,index_2012);
			setCell(c2013,b15,row,index_2013);
			setCell(c2014,b16,row,index_2014);
			setCell(c2015,b17,row,index_2015);
			
			row=sheet.getRow(13); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,b21,row,index_2010);
			setCell(c1115,b22,row,index_1115);
			setCell(c2011,b23,row,index_2011);
			setCell(c2012,b24,row,index_2012);
			setCell(c2013,b25,row,index_2013);
			setCell(c2014,b26,row,index_2014);
			setCell(c2015,b27,row,index_2015);
			
			row=sheet.getRow(14); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,b31,row,index_2010);
			setCell(c1115,b32,row,index_1115);
			setCell(c2011,b33,row,index_2011);
			setCell(c2012,b34,row,index_2012);
			setCell(c2013,b35,row,index_2013);
			setCell(c2014,b36,row,index_2014);
			setCell(c2015,b37,row,index_2015);
			
			row=sheet.getRow(15); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,b41,row,index_2010);
			setCell(c1115,b42,row,index_1115);
			setCell(c2011,b43,row,index_2011);
			setCell(c2012,b44,row,index_2012);
			setCell(c2013,b45,row,index_2013);
			setCell(c2014,b46,row,index_2014);
			setCell(c2015,b47,row,index_2015);
			
			row=sheet.getRow(16); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,b51,row,index_2010);
			setCell(c1115,b52,row,index_1115);
			setCell(c2011,b53,row,index_2011);
			setCell(c2012,b54,row,index_2012);
			setCell(c2013,b55,row,index_2013);
			setCell(c2014,b56,row,index_2014);
			setCell(c2015,b57,row,index_2015);
			
			row=sheet.getRow(17); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,b61,row,index_2010);
			setCell(c1115,b62,row,index_1115);
			setCell(c2011,b63,row,index_2011);
			setCell(c2012,b64,row,index_2012);
			setCell(c2013,b65,row,index_2013);
			setCell(c2014,b66,row,index_2014);
			setCell(c2015,b67,row,index_2015);
			
			row=sheet.getRow(18); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,b71,row,index_2010);
			setCell(c1115,b72,row,index_1115);
			setCell(c2011,b73,row,index_2011);
			setCell(c2012,b74,row,index_2012);
			setCell(c2013,b75,row,index_2013);
			setCell(c2014,b76,row,index_2014);
			setCell(c2015,b77,row,index_2015);
			
			row=sheet.getRow(19); 
			c2010=row.getCell(index_2010);
			c1115=row.getCell(index_1115);
			c2011=row.getCell(index_2011);
			c2012=row.getCell(index_2012);
			c2013=row.getCell(index_2013);
			c2014=row.getCell(index_2014);
			c2015=row.getCell(index_2015);
			setCell(c2010,b81,row,index_2010);
			setCell(c1115,b82,row,index_1115);
			setCell(c2011,b83,row,index_2011);
			setCell(c2012,b84,row,index_2012);
			setCell(c2013,b85,row,index_2013);
			setCell(c2014,b86,row,index_2014);
			setCell(c2015,b87,row,index_2015);
			
		 
		
		
		Date d=new Date();
		String resultPath=filePath+"\\expTemp\\"+d.getTime()+".xls"; 
		File resFile=new File(resultPath);
		FileOutputStream fileOut= new FileOutputStream(resFile);  
		wb.write(fileOut);   
		fileOut.flush();
		fileOut.close();   
		return "/upload/info/plan/expTemp/"+d.getTime()+".xls";
	}
	
	private void setCell(HSSFCell cell,Toolbarbutton tb,HSSFRow  row,Short index){
		if(tb.getLabel().length()==0){
			return;
		}
		if(cell==null){
			System.out.println("cell=null");
			System.out.println(row.getRowNum()+"| "+index);
			cell=row.createCell(index, HSSFCell.CELL_TYPE_STRING);	 
		}else{ 
			 
		}
		cell.setCellValue(tb.getLabel()); 
	}
	
	private void addOpenTitle(Toolbarbutton tb,String tooltip,String header){
		if(tooltip!=null)
		tb.setTooltiptext(tooltip);
		hearMap.put(tb, header);
	}
	
	private void addListener(Toolbarbutton tb){
		tb.addEventListener(Events.ON_CLICK, inlistener);
	}
	
	/**
	 * 返回科研奖励
	 * @param a
	 */
	private String getKyJl(int[] a){
		StringBuffer sb=new StringBuffer("");
		if(a[1]>0){
			sb.append("一/"+a[1]);
		}
		if(a[2]>0){
			if(sb.toString().length()>0){
				sb.append(",");
			}
			sb.append("二/"+a[2]); 
		}
		if(a[3]>0){
			if(sb.toString().length()>0){
				sb.append(",");
			}
			sb.append("三/"+a[3]); 
		}
		if(a[0]>0){
			if(sb.toString().length()>0){
				sb.append(",");
			}
			sb.append("其他/"+a[0]); 
		}
		return sb.toString();
	}
	
	/**
	 * 获得以/分开的数目表示
	 * @param a
	 * @return
	 */
	private String getIntege(int[] a){
		StringBuffer sb=new StringBuffer("");
		boolean hasnumber=false;
		for(int i=0;i<a.length;i++){
			if(a[i]>0){
				hasnumber=true;
			}
			sb.append(a[i]);
			if(i<a.length-1){
				sb.append("/");
			}
		}
		return hasnumber?sb.toString():"";
	}
	
	/**
	 * 获得科研成果，如国家级、省部级等奖项
	 * @param a
	 * @return
	 */
	private String getKycg(int[] a){
		StringBuffer sb=new StringBuffer("");
		if(a[0]>0){ 
			sb.append("1/"+a[0]); 
		}
		if(a[1]>0){
			if(sb.toString().length()>0){
				sb.append(",");
			}
			sb.append("2/"+a[1]);
		}
		if(a[2]>0){
			if(sb.toString().length()>0){
				sb.append(",");
			}
			sb.append("3/"+a[2]); 
		}
		if(a[3]>0){
			if(sb.toString().length()>0){
				sb.append(",");
			}
			sb.append("4/"+a[3]); 
		}
		if(a[4]>0){
			if(sb.toString().length()>0){
				sb.append(",");
			}
			sb.append("5/"+a[4]); 
		}
		if(a[5]>0){
			if(sb.toString().length()>0){
				sb.append(",");
			}
			sb.append("未知/"+a[5]); 
		}
		return sb.toString(); 
	}
	
	/**
	 * 计算奖励，包括科研，教学成果
	 * @param a
	 * @param value
	 * @return
	 */
	private boolean calculateJL(int[] a,String value){
		if(value==null||value.equalsIgnoreCase("")||value.equalsIgnoreCase("0")){
			return false;
		}
		String[] vs=value.split(","); 
		for(int i=0;i<vs.length;i++){
			String v=vs[i];
			int index=0;
			if(v.indexOf("/")>0){
			 String lb=v.substring(0, v.indexOf("/"));
			 String sm=v.substring(v.indexOf("/")+1); 
			 if(lb.equalsIgnoreCase("三")){
				index=3;
			 }else if(lb.equalsIgnoreCase("二")){
				index=2;
			 }else if(lb.equalsIgnoreCase("一")){
				index=1;
			 }
			 if(Integer.valueOf(sm).intValue()==0){
				return false;
			 }
			 a[index]=a[index]+Integer.valueOf(sm);
			}else{
			     a[index]=a[index]+Integer.valueOf(v);
			}
		}
		return true;
	}
	
	/**
	 * 计算某些数目和，如横向项目数目等
	 * @param tb 计算结果对应显示的链接
	 * @param count 数目字符串
	 * @return
	 */
	private boolean addCount(Toolbarbutton tb,String value){
		if(value==null||value.equalsIgnoreCase("")||value.equalsIgnoreCase("0")){
			return false;
		}
		String nowvalue=tb.getLabel();
		if(nowvalue==null||nowvalue.trim().length()==0){
		    try {
				tb.setLabel(Integer.valueOf(value)+"");
			} catch (Exception e) {
				tb.setLabel("1");
			}
		}else{
		    try {
				tb.setLabel(Integer.valueOf(nowvalue)+Integer.valueOf(value)+"");
			} catch (Exception e) {
				tb.setLabel(Integer.valueOf(nowvalue)+1+"");
			}
		}
		return true;
	}
	
	private void addMap(Toolbarbutton tb,QkdcItem qitem){
		List list=ughmap.get(tb);
		if(list==null){
			list=new ArrayList();
		}
		list.add(qitem);
		ughmap.put(tb, list);
	}
	
	/**
	 * 计算以"/"隔开的东西
	 * @param a
	 * @param value
	 * @return
	 */
	private boolean calculate(int[] a,String value){
		if(value==null||value.equalsIgnoreCase("")||value.equalsIgnoreCase("0")){
			return false;
		} 
		String[] vs=value.split("/");
		if(vs.length!=a.length){
			System.out.println(value+" 不合格");
			return false;
		}
		boolean res=false;
		for(int i=0;i<vs.length;i++){
			if(Integer.valueOf(vs[i]).intValue()>0){
				res=true;
			}
		   a[i]=a[i]+Integer.valueOf(vs[i]);
		}
		if(res)
		return true;
		return false;
	}
	
	/**
	 * 计算科研情况中的项目类国家或者省部级
	 * @param a
	 * @param value
	 * @return
	 */
	private boolean calculateAky(int[] a,String value){
		boolean res=false;
		if(value==null||value.equalsIgnoreCase("")||value.equalsIgnoreCase("0")){
			return res;
		}
		res=true;
		String[] vs=value.split(",");
		for(int i=0;i<vs.length;i++){
			String v=vs[i];
			if( v.indexOf("/")>0){
			  String lb=v.substring(0, v.indexOf("/"));
			  String sm=v.substring(v.indexOf("/")+1);
			  a[Integer.valueOf(lb)-1]=a[Integer.valueOf(lb)-1]+Integer.valueOf(sm);
			}else{
				int r=0;
				try {
					 r=Integer.valueOf(v);
				} catch (Exception e) {
					 r=1;
				} 
			    a[5]=a[5]+Integer.valueOf(v);
			}
		}
		return res;
	}
	
	class InnerListener implements EventListener{

		public void onEvent(Event arg0) throws Exception { 
			Toolbarbutton tb=(Toolbarbutton) arg0.getTarget();
			List itemlist=ughmap.get(arg0.getTarget());
			if(itemlist==null||itemlist.size()==0){
				System.out.println("没数据");
				return;
			}
			
			QkdcDetailWindow qdw=(QkdcDetailWindow)Executions.getCurrent().createComponents("/admin/xkgl/ghtj/xkjstj/tpanel_detail.zul", null, null);
			qdw.doHighlighted();
			qdw.initWindow(itemlist,tb.getTooltiptext(),hearMap.get(arg0.getTarget()),tb.getLabel());
		}
		
	}
}
