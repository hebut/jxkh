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
 * <li>功能描述: 实现纵向项目结项报告的订制功能
 * <li>版权: Copyright (c) 信息技术研究所
 */
public class ZResultReportWindow extends Window implements AfterCompose{	
	private static final long serialVersionUID = -1397177361099476338L;
	private Logger  log  = Logger.getLogger(ZResultReportWindow.class);
	GhXm xm = (GhXm) Executions.getCurrent().getArg().get("zProgram");
	
	private WkTUser user;
	
	private XmService xmService;
	private CgService cgService;
	private HylwService hylwService;
	private QklwService qklwService;
	private RjzzService rjzzService;
	private FmzlService fmzlService;
	
	private Checkbox proName,proSource,proMan,proNum;//项目名称、项目来源、项目负责人、项目编号
	private Checkbox contractNum,finishUnit,setFinishTime,realFinishTime;//合同编号、完成单位、规定完成时间、实际完成时间
	private Checkbox grants,writeuser,conFinishCondition,fruit;//资助金额、信息填写人、完成情况、成果及成效
	private Checkbox identtime,level;//鉴定时间、鉴定水平
	//项目统计
	private Checkbox zxProStatist,nationalProStatist,provincProStatist;
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
			proName.setChecked(true);proSource.setChecked(true);proMan.setChecked(true);proNum.setChecked(true);
			contractNum.setChecked(true);finishUnit.setChecked(true);setFinishTime.setChecked(true);realFinishTime.setChecked(true);
			grants.setChecked(true);writeuser.setChecked(true);conFinishCondition.setChecked(true);fruit.setChecked(true);
			identtime.setChecked(true);level.setChecked(true);
			
//			hxProStatist.setChecked(true);
			zxProStatist.setChecked(true);
			nationalProStatist.setChecked(true);provincProStatist.setChecked(true);
			nationalFruitStatist.setChecked(true);provincFruitStatist.setChecked(true);
			conferePaperStatist.setChecked(true);journalPaperStatist.setChecked(true);softwareCopyrightStatist.setChecked(true);invenPatentStatist.setChecked(true);
		}else{
			proName.setChecked(false);proSource.setChecked(false);proMan.setChecked(false);proNum.setChecked(false);
			contractNum.setChecked(false);finishUnit.setChecked(false);setFinishTime.setChecked(false);realFinishTime.setChecked(false);
			grants.setChecked(false);writeuser.setChecked(false);conFinishCondition.setChecked(false);fruit.setChecked(false);
			identtime.setChecked(false);level.setChecked(false);
			
//			hxProStatist.setChecked(false);
			zxProStatist.setChecked(false);
			nationalProStatist.setChecked(false);provincProStatist.setChecked(false);
			nationalFruitStatist.setChecked(false);provincFruitStatist.setChecked(false);
			conferePaperStatist.setChecked(false);journalPaperStatist.setChecked(false);softwareCopyrightStatist.setChecked(false);invenPatentStatist.setChecked(false);
		}
	}
	/**
	* <li>功能描述：选择或不选择全部属性。
	 * @param null
	 * @return null
	 * @author bobo
	 */
	public void onClick$allCheck(){
		proName.setChecked(false);proSource.setChecked(false);proMan.setChecked(false);proNum.setChecked(false);
		contractNum.setChecked(false);finishUnit.setChecked(false);setFinishTime.setChecked(false);realFinishTime.setChecked(false);
		grants.setChecked(false);writeuser.setChecked(false);conFinishCondition.setChecked(false);fruit.setChecked(false);
		identtime.setChecked(false);level.setChecked(false);
	}
	
	/**
	* <li>功能描述：将选择的结项报告属性，导出到.xml文档中。
	 * @param null
	 * @return null
	 * @author bobo
	 */
	public void onClick$buildResultReport() throws WriteException{
		if (xm != null) {
			List list1 = xmService.findByKuidAndTypeAndKyclassAndXmid("2",xm.getKyId());
			 if(list1.size()==0){
				 try {
						Messagebox.show("空数据，导出错误！", "错误", Messagebox.OK,Messagebox.INFORMATION);
					} catch (InterruptedException e) {
						log.error("当前系统时间："+ new Date().toLocaleString()
				                    + " "+ "科研项目接口"+ e.getMessage());
						e.printStackTrace();
					}
			 }else{
				Date now=new Date();
				String xmc="";
				if(xm.getKyMc().trim().length()>11){
					xmc=xm.getKyMc().substring(0, 10);
				}		    	
		    	String fileName = xmc+"结项报告"+DateUtil.getDateString(now)+".xls";
		    	String Title = xm.getKyMc()+"结项报告";
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
				if(proNum.isChecked()){
					titlelist.add("项目编号");
					namelist.add(xm.getKyNumber());
				}
				
				if(contractNum.isChecked()){
					titlelist.add("合同编号");
					namelist.add(xm.getKyContraNum());
				}
				if(finishUnit.isChecked()){
					titlelist.add("主要完成单位");
					namelist.add(xm.getKyFinishUnit());
				}
				if(setFinishTime.isChecked()){
					titlelist.add("规定完成时间");
					namelist.add(xm.getKySetFinishTime());
				}
				if(realFinishTime.isChecked()){
					titlelist.add("实际完成时间");
					namelist.add(xm.getKyRealFinishTime());
				}	
			
				if(grants.isChecked()){
					titlelist.add("资助金额");
					namelist.add(xm.getKyGrants()!=null?xm.getKyGrants().toString():"");
				}	
				if(writeuser.isChecked()){
					titlelist.add("信息填写人");
					namelist.add(xm.getUser().getKuName());
				}					
				if(conFinishCondition.isChecked()){
					titlelist.add("内容完成情况");
					namelist.add(xm.getKyContentFinishConditon());
				}		
				if(fruit.isChecked()){
					titlelist.add("成果及成效");	
					namelist.add(xm.getKyFruit());
				}
				
				if(identtime.isChecked()){
					titlelist.add("项目鉴定时间");
					namelist.add(xm.getKyIdenttime());
				}
			
				if(level.isChecked()){
					titlelist.add("项目鉴定水平");					
					namelist.add(xm.getKyLevel());
				}
				
				if(zxProStatist.isChecked()){
				titlelist.add("纵向项目数");
				List zxProlist = xmService.findCountByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"2");
				int totalZxSize = 0;
		        if (zxProlist.size() > 0){            	
		        	totalZxSize = ((Long) zxProlist.get(0)).intValue();
		        }
				namelist.add(totalZxSize);
			}
//			
//			if(hxProStatist.isChecked()){
//				titlelist.add("横向项目数");
//				List zxProlist = xmService.findCountByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"1");
//				int totalZxSize = 0;
//		        if (zxProlist.size() > 0){            	
//		        	totalZxSize = ((Long) zxProlist.get(0)).intValue();
//		        }
//				namelist.add(totalZxSize);
//			}
			
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
		                    + " "+ "纵向科研项目结项报告导出"+ e.getMessage());
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