package org.iti.projectmanage.science.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;
import org.iti.gh.common.util.DateUtil;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JsxmService;
import org.iti.gh.service.QklwService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.XmService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import com.uniwin.framework.entity.WkTUser;

/**
 * <li>项目名称: Kygl科研管理
 * <li>功能描述: 实现横向项目中期报告的订制功能
 * <li>版权: Copyright (c) 信息技术研究所
 */
public class HMiddReportWindow extends Window implements AfterCompose{	
	private static final long serialVersionUID = -1397177361099476338L;
	private Logger  log  = Logger.getLogger(HMiddReportWindow.class);
	GhXm xm = (GhXm) Executions.getCurrent().getArg().get("hProgram");
	
	private WkTUser user;
	
	private XmService xmService;
	private CgService cgService;
	private HylwService hylwService;
	private QklwService qklwService;
	private RjzzService rjzzService;
	private FmzlService fmzlService;
	
	private Checkbox proName,proSource,proMan;//项目名称、项目来源、项目负责人
	private Checkbox beginTime,endTime,proFund;//开始时间、结束时间、项目经费
	private Checkbox infoWriter,subjectClass,proProgress;//信息填写人、学科分类、项目进展
	private Checkbox proBank;//项目级别、本人贡献排名、本人研究方向、负责的任务
	private Checkbox proInterNum;//项目内部编号、项目指标
	
	private Checkbox contractNum,contraTypeListbox,commitCom,commitComplace,acceptCom,acceptComPlace,identtime,level,conFinishCondition,fruit;
	//项目统计
	private Checkbox hxProStatist,zxProStatist,nationalProStatist,provincProStatist;
	//获奖统计
	private Checkbox nationalFruitStatist,provincFruitStatist;
	//成果统计
	private Checkbox conferePaperStatist,journalPaperStatist,softwareCopyrightStatist,invenPatentStatist;
	private Checkbox allCheck;
			
	public void afterCompose(){
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");//获取当前登录用户	
		
	}
	
	/**
	* <li>功能描述：选择或不选择全部属性。
	 * @param null
	 * @return null
	 * @author bobo
	 */
	public void onCheck$allCheck(){
		if(allCheck.isChecked()){			
			proName.setChecked(true);proSource.setChecked(true);proMan.setChecked(true);
			beginTime.setChecked(true);endTime.setChecked(true);proFund.setChecked(true);
			infoWriter.setChecked(true);subjectClass.setChecked(true);proProgress.setChecked(true);
			proBank.setChecked(true);
			proInterNum.setChecked(true);
			contractNum.setChecked(true);contraTypeListbox.setChecked(true);commitCom.setChecked(true);
			commitComplace.setChecked(true);acceptCom.setChecked(true);acceptComPlace.setChecked(true);
			identtime.setChecked(true);level.setChecked(true);conFinishCondition.setChecked(true);fruit.setChecked(true);
			nationalProStatist.setChecked(true);provincProStatist.setChecked(true);
			nationalFruitStatist.setChecked(true);provincFruitStatist.setChecked(true);
			conferePaperStatist.setChecked(true);journalPaperStatist.setChecked(true);softwareCopyrightStatist.setChecked(true);invenPatentStatist.setChecked(true);
		
		}else{
			proName.setChecked(false);proSource.setChecked(false);proMan.setChecked(false);
			beginTime.setChecked(false);endTime.setChecked(false);proFund.setChecked(false);
			infoWriter.setChecked(false);subjectClass.setChecked(false);proProgress.setChecked(false);
			proBank.setChecked(false);
			proInterNum.setChecked(false);
			contractNum.setChecked(false);contraTypeListbox.setChecked(false);commitCom.setChecked(false);
			commitComplace.setChecked(false);acceptCom.setChecked(false);acceptComPlace.setChecked(false);
			identtime.setChecked(false);level.setChecked(false);conFinishCondition.setChecked(false);fruit.setChecked(false);
			nationalProStatist.setChecked(false);provincProStatist.setChecked(false);
			nationalFruitStatist.setChecked(false);provincFruitStatist.setChecked(false);
			conferePaperStatist.setChecked(false);journalPaperStatist.setChecked(false);softwareCopyrightStatist.setChecked(false);invenPatentStatist.setChecked(false);
		}
	}
	
	/**
	* <li>功能描述：将选择的中期报告属性，导出到.xml文档中。
	 * @param null
	 * @return null
	 * @author bobo
	 */
	public void onClick$buildMiddReport() throws WriteException{
		if (xm != null) {		
			List list1 = xmService.findByKuidAndTypeAndKyclassAndXmid("1",xm.getKyId());
			if(list1.size()==0){
				try {
					Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
				} catch (InterruptedException e) {
					log.error("当前系统时间："+ new Date().toLocaleString()
			                    + " "+ "科研项目接口"+ e.getMessage());
					e.printStackTrace();
				}
			    return;
			}else{
				Date now=new Date();
				String xmc="";
				if(xm.getKyMc().trim().length()>11){
					xmc=xm.getKyMc().substring(0, 10);
				}		    	
		    	String fileName = xmc+"中期报告"+DateUtil.getDateString(now)+".xls";
		    	String Title =xm.getKyMc()+"中期报告";
		    	WritableWorkbook workbook;
				List titlelist=new ArrayList();
				List namelist = new ArrayList();
				titlelist.add("序号");
				namelist.add("0");
				if(proName.isChecked()){
					titlelist.add("项目名称");
					namelist.add(xm.getKyMc());
				}
				
				if(proSource.isChecked()){
					titlelist.add("项目来源");
					namelist.add(xm.getKyLy());
				}
				
				if(proMan.isChecked()){
					titlelist.add("项目负责人");
					namelist.add(xm.getKyProman());
				}
				if(proBank.isChecked()){
					titlelist.add("项目级别");
					String bank="";
					if(xm.getKyScale() == null || xm.getKyScale().equals("") || xm.getKyScale().trim() == "-1"){
						bank="";
					}else{ if(xm.getKyScale().trim().equalsIgnoreCase( "1")){
						bank="国际合作项目";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "2")){
						bank="国家级项目";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "3")){
						bank="部（委、省）级项目";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "4")){
						bank="市厅级项目";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "5")){
						bank="委托及开发项目";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "6")){
						bank="学校基金项目";
					}else {
						bank="其他";
					}}
					namelist.add(bank);
				}
				if(infoWriter.isChecked()){
					titlelist.add("信息填写人");
					namelist.add(xm.getUser().getKuName());
				}	
				if(contractNum.isChecked()){
					titlelist.add("合同编号");
					namelist.add(xm.getKyContraNum());
				}	
				if(contraTypeListbox.isChecked()){
					titlelist.add("合同类型");
					String bank="";
					if(xm.getContractType()== null || xm.getContractType().equals("") || xm.getContractType().trim() == "-1"){
						bank="";
					}else{ if(xm.getContractType().trim().equalsIgnoreCase( "1")){
								bank="技术开发";
							}else if(xm.getContractType().trim().equalsIgnoreCase( "2")){
								bank="技术转让";
							}else if(xm.getContractType().trim().equalsIgnoreCase( "3")){
								bank="技术服务";
							}else if(xm.getContractType().trim().equalsIgnoreCase( "4")){
								bank="技术咨询";
							}else {
								bank="其他";
							}
					}
					namelist.add(bank);
				}	
				if(proInterNum.isChecked()){
					titlelist.add("项目内部编号");
					String inNum="";
					if(xm.getInternalNum()==null||xm.getInternalNum().equals("")){
						inNum="";
					}else{
						inNum=xm.getInternalNum().toString();
					}
					namelist.add(inNum);
				}
				if(commitCom.isChecked()){
					titlelist.add("委托单位");
					namelist.add(xm.getCommitCom());
				}
				if(commitComplace.isChecked()){
					titlelist.add("委托单位地址");
					namelist.add(xm.getCommitComPlace());
				}
				if(acceptCom.isChecked()){
					titlelist.add("受托单位");
					namelist.add(xm.getAcceptCom());
				}
				if(acceptComPlace.isChecked()){
					titlelist.add("受托单位地址");
					namelist.add(xm.getAcceptComPlace());
				}
				if(proFund.isChecked()){
					titlelist.add("项目经费(万元)");
					namelist.add(xm.getKyJf()!=null?xm.getKyJf().toString():"");
				}	
				
				if(beginTime.isChecked()){
					titlelist.add("计划开始");
					namelist.add(xm.getKyKssj());
				}
				if(endTime.isChecked()){
					titlelist.add("计划结束");
					namelist.add(xm.getKyJssj());
				}
				
						
				if(subjectClass.isChecked()){
					titlelist.add("学科分类");
					String subClass="";
					if(xm.getKySubjetype() == null ||xm.getKySubjetype().equals("") || xm.getKySubjetype().trim().equalsIgnoreCase("-1")){
						subClass="";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "1")){
						subClass="自然科学";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "2")){
						subClass="社会科学";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "3")){
						subClass="其他";
					}
					namelist.add(subClass);
				}		
				if(proProgress.isChecked()){
					titlelist.add("项目进展");
					String progress="";
					if(xm.getKyProgress() == null ||xm.getKyProgress().equals("") || xm.getKyProgress().trim() ==""){
						progress="";
					}else if(xm.getKyProgress().trim().equalsIgnoreCase( "1")){
						progress="申请中";
					}else if(xm.getKyProgress().trim().equalsIgnoreCase( "2")){
						progress="已完成";
					}else if(xm.getKyProgress().trim().equalsIgnoreCase( "3")){
						progress="在研";
					}	
					namelist.add(progress);
				}
				
				if(identtime.isChecked()){
					titlelist.add("鉴定时间");
					namelist.add(xm.getKyIdenttime());
				}
				if(level.isChecked()){
					titlelist.add("鉴定水平");
					namelist.add(xm.getKyLevel());
				}
				if(conFinishCondition.isChecked()){
					titlelist.add("研发内容完成情况");
					namelist.add(xm.getKyContentFinishConditon());
				}
				
				if(fruit.isChecked()){
					titlelist.add("取得的成果及成效");
					namelist.add(xm.getKyFruit());
				}
							
				if(nationalProStatist.isChecked()){
					titlelist.add("国家级项目数");
					List nationlist = xmService.findCountByKuidAndTypeAndKyScale(user.getKuId(), GhJsxm.KYXM,"2");
					int totalZxSize = 0;
			        if (nationlist.size() > 0){            	
			        	totalZxSize = ((Long) nationlist.get(0)).intValue();
			        }
					namelist.add(totalZxSize);
				}
				if(provincProStatist.isChecked()){
					titlelist.add("省部级项目数");
					List nationlist = xmService.findCountByKuidAndTypeAndKyScale(user.getKuId(), GhJsxm.KYXM,"3");
					int totalZxSize = 0;
			        if (nationlist.size() > 0){            	
			        	totalZxSize = ((Long) nationlist.get(0)).intValue();
			        }
					namelist.add(totalZxSize);
				}
				if(nationalFruitStatist.isChecked()){
					titlelist.add("国家级获奖数");
					List nationlist = cgService.findCountByKuidAndCgkyAndHjky(user.getKuId(),GhCg.CG_KY,GhJsxm.HjKY,Short.valueOf("1"));
					int totalZxSize = 0;
			        if (nationlist.size() > 0){            	
			        	totalZxSize = ((Long) nationlist.get(0)).intValue();
			        }
					namelist.add(totalZxSize);
				}
				
				if(provincFruitStatist.isChecked()){
					titlelist.add("省部级获奖数");
					List nationlist = cgService.findCountByKuidAndCgkyAndHjky(user.getKuId(),GhCg.CG_KY,GhJsxm.HjKY,Short.valueOf("2"));
					int totalZxSize = 0;
			        if (nationlist.size() > 0){            	
			        	totalZxSize = ((Long) nationlist.get(0)).intValue();
			        }
					namelist.add(totalZxSize);
				}
	
				if(conferePaperStatist.isChecked()){
					titlelist.add("会议论文数");
					List list3 = hylwService.findByKuidAndType(user.getKuId(), GhHylw.KYLW, GhJslw.KYHY);
					namelist.add(list3.size());
				}
				if(journalPaperStatist.isChecked()){
					titlelist.add("期刊论文数");
					List list4 = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
					namelist.add(list4.size());
				}
				if(journalPaperStatist.isChecked()){
					titlelist.add("软件著作权数");
					List list5 = rjzzService.findByKuid(user.getKuId());
					namelist.add(list5.size());
				}
				if(invenPatentStatist.isChecked()){
					titlelist.add("发明专利数");
					List list6= fmzlService.findByKuid(user.getKuId());
					namelist.add(list6.size());
				}
				//将namelist中数据与titlelist进行对应
				String c[][]=new String[list1.size()][titlelist.size()];
				for(int j=0;j<list1.size();j++){
					GhXm xm  =(GhXm)list1.get(j);
					c[j][0]=j+1+"";
					for(int k=0;k<titlelist.size();k++){
						 c[j][k]=namelist.get(k).toString();
					}				    
				}
		         ExportExcel ee=new ExportExcel();
				 try {
					ee.exportExcel(fileName, Title, titlelist, list1.size(), c);
				} catch (IOException e) {
					log.error("当前系统时间："+ new Date().toLocaleString()
		                    + " "+ "横向科研项目中期报告导出"+ e.getMessage());
				    e.printStackTrace();
					e.printStackTrace();
				}
			}
		}
	}
	/**
	* <li>功能描述：关闭当前窗口。
	 * @param null
	 * @return null
	 * @author bobo
	 */
	public void onClick$close(){
		this.detach();
	}
	
}