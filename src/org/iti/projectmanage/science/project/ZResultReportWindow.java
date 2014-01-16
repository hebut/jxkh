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
 * <li>��Ŀ����: Kygl���й���
 * <li>��������: ʵ��������Ŀ�����Ķ��ƹ���
 * <li>��Ȩ: Copyright (c) ��Ϣ�����о���
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
	
	private Checkbox proName,proSource,proMan,proNum;//��Ŀ���ơ���Ŀ��Դ����Ŀ�����ˡ���Ŀ���
	private Checkbox contractNum,finishUnit,setFinishTime,realFinishTime;//��ͬ��š���ɵ�λ���涨���ʱ�䡢ʵ�����ʱ��
	private Checkbox grants,writeuser,conFinishCondition,fruit;//��������Ϣ��д�ˡ����������ɹ�����Ч
	private Checkbox identtime,level;//����ʱ�䡢����ˮƽ
	//��Ŀͳ��
	private Checkbox zxProStatist,nationalProStatist,provincProStatist;
	//��ͳ��
	private Checkbox nationalFruitStatist,provincFruitStatist;
	//�ɹ�ͳ��
	private Checkbox conferePaperStatist,journalPaperStatist,softwareCopyrightStatist,invenPatentStatist;
	private Checkbox allCheck;	
		
	public void afterCompose(){
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");//��ȡ��ǰ��¼�û�		
	}
	/**
	* <li>����������ѡ���ѡ��ȫ�����ԡ�
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
	* <li>����������ѡ���ѡ��ȫ�����ԡ�
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
	* <li>������������ѡ��Ľ�������ԣ�������.xml�ĵ��С�
	 * @param null
	 * @return null
	 * @author bobo
	 */
	public void onClick$buildResultReport() throws WriteException{
		if (xm != null) {
			List list1 = xmService.findByKuidAndTypeAndKyclassAndXmid("2",xm.getKyId());
			 if(list1.size()==0){
				 try {
						Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
					} catch (InterruptedException e) {
						log.error("��ǰϵͳʱ�䣺"+ new Date().toLocaleString()
				                    + " "+ "������Ŀ�ӿ�"+ e.getMessage());
						e.printStackTrace();
					}
			 }else{
				Date now=new Date();
				String xmc="";
				if(xm.getKyMc().trim().length()>11){
					xmc=xm.getKyMc().substring(0, 10);
				}		    	
		    	String fileName = xmc+"�����"+DateUtil.getDateString(now)+".xls";
		    	String Title = xm.getKyMc()+"�����";
		    	WritableWorkbook workbook;
				List titlelist=new ArrayList();
				List namelist = new ArrayList();
				titlelist.add("���");
				namelist.add("0");
				if(proName.isChecked()){
					titlelist.add("��Ŀ����");
					namelist.add(xm.getKyMc());
				}
				
				if(proSource.isChecked()){
					titlelist.add("��Ŀ��Դ");
					namelist.add(xm.getKyLy());
				}
				
				if(proMan.isChecked()){
					titlelist.add("��Ŀ������");
					namelist.add(xm.getKyProman());
				}
				if(proNum.isChecked()){
					titlelist.add("��Ŀ���");
					namelist.add(xm.getKyNumber());
				}
				
				if(contractNum.isChecked()){
					titlelist.add("��ͬ���");
					namelist.add(xm.getKyContraNum());
				}
				if(finishUnit.isChecked()){
					titlelist.add("��Ҫ��ɵ�λ");
					namelist.add(xm.getKyFinishUnit());
				}
				if(setFinishTime.isChecked()){
					titlelist.add("�涨���ʱ��");
					namelist.add(xm.getKySetFinishTime());
				}
				if(realFinishTime.isChecked()){
					titlelist.add("ʵ�����ʱ��");
					namelist.add(xm.getKyRealFinishTime());
				}	
			
				if(grants.isChecked()){
					titlelist.add("�������");
					namelist.add(xm.getKyGrants()!=null?xm.getKyGrants().toString():"");
				}	
				if(writeuser.isChecked()){
					titlelist.add("��Ϣ��д��");
					namelist.add(xm.getUser().getKuName());
				}					
				if(conFinishCondition.isChecked()){
					titlelist.add("����������");
					namelist.add(xm.getKyContentFinishConditon());
				}		
				if(fruit.isChecked()){
					titlelist.add("�ɹ�����Ч");	
					namelist.add(xm.getKyFruit());
				}
				
				if(identtime.isChecked()){
					titlelist.add("��Ŀ����ʱ��");
					namelist.add(xm.getKyIdenttime());
				}
			
				if(level.isChecked()){
					titlelist.add("��Ŀ����ˮƽ");					
					namelist.add(xm.getKyLevel());
				}
				
				if(zxProStatist.isChecked()){
				titlelist.add("������Ŀ��");
				List zxProlist = xmService.findCountByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"2");
				int totalZxSize = 0;
		        if (zxProlist.size() > 0){            	
		        	totalZxSize = ((Long) zxProlist.get(0)).intValue();
		        }
				namelist.add(totalZxSize);
			}
//			
//			if(hxProStatist.isChecked()){
//				titlelist.add("������Ŀ��");
//				List zxProlist = xmService.findCountByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"1");
//				int totalZxSize = 0;
//		        if (zxProlist.size() > 0){            	
//		        	totalZxSize = ((Long) zxProlist.get(0)).intValue();
//		        }
//				namelist.add(totalZxSize);
//			}
			
			if(nationalProStatist.isChecked()){
				titlelist.add("���Ҽ���Ŀ��");
				List nationlist = xmService.findCountByKuidAndTypeAndKyScale(user.getKuId(), GhJsxm.KYXM,"2");
				int totalZxSize = 0;
		        if (nationlist.size() > 0){            	
		        	totalZxSize = ((Long) nationlist.get(0)).intValue();
		        }
				namelist.add(totalZxSize);
			}
			if(provincProStatist.isChecked()){
				titlelist.add("ʡ������Ŀ��");
				List nationlist = xmService.findCountByKuidAndTypeAndKyScale(user.getKuId(), GhJsxm.KYXM,"3");
				int totalZxSize = 0;
		        if (nationlist.size() > 0){            	
		        	totalZxSize = ((Long) nationlist.get(0)).intValue();
		        }
				namelist.add(totalZxSize);
			}
			if(nationalFruitStatist.isChecked()){
				titlelist.add("���Ҽ�����");
				List nationlist = cgService.findCountByKuidAndCgkyAndHjky(user.getKuId(),GhCg.CG_KY,GhJsxm.HjKY,Short.valueOf("1"));
				int totalZxSize = 0;
		        if (nationlist.size() > 0){            	
		        	totalZxSize = ((Long) nationlist.get(0)).intValue();
		        }
				namelist.add(totalZxSize);
			}
			
			if(provincFruitStatist.isChecked()){
				titlelist.add("ʡ��������");
				List nationlist = cgService.findCountByKuidAndCgkyAndHjky(user.getKuId(),GhCg.CG_KY,GhJsxm.HjKY,Short.valueOf("2"));
				int totalZxSize = 0;
		        if (nationlist.size() > 0){            	
		        	totalZxSize = ((Long) nationlist.get(0)).intValue();
		        }
				namelist.add(totalZxSize);
			}

			if(conferePaperStatist.isChecked()){
				titlelist.add("����������");
				List list3 = hylwService.findByKuidAndType(user.getKuId(), GhHylw.KYLW, GhJslw.KYHY);
				namelist.add(list3.size());
			}
			if(journalPaperStatist.isChecked()){
				titlelist.add("�ڿ�������");
				List list4 = qklwService.findByKuidAndType(user.getKuId(), GhQklw.KYLW, GhJslw.KYQK);
				namelist.add(list4.size());
			}
			if(journalPaperStatist.isChecked()){
				titlelist.add("�������Ȩ��");
				List list5 = rjzzService.findByKuid(user.getKuId());
				namelist.add(list5.size());
			}
			if(invenPatentStatist.isChecked()){
				titlelist.add("����ר����");
				List list6= fmzlService.findByKuid(user.getKuId());
				namelist.add(list6.size());
			}
				//��namelist��������titlelist���ж�Ӧ
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
					log.error("��ǰϵͳʱ�䣺"+ new Date().toLocaleString()
		                    + " "+ "���������Ŀ����浼��"+ e.getMessage());
				    e.printStackTrace();
					e.printStackTrace();
				}
				}
		}
	}
	/**
	* <li>�����������رյ�ǰ���ڡ�
	 * @param null
	 * @return null
	 * @author bobo
	 */
	public void onClick$close(){
		this.detach();
	}
	
}