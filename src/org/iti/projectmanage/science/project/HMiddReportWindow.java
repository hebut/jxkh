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
 * <li>��Ŀ����: Kygl���й���
 * <li>��������: ʵ�ֺ�����Ŀ���ڱ���Ķ��ƹ���
 * <li>��Ȩ: Copyright (c) ��Ϣ�����о���
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
	
	private Checkbox proName,proSource,proMan;//��Ŀ���ơ���Ŀ��Դ����Ŀ������
	private Checkbox beginTime,endTime,proFund;//��ʼʱ�䡢����ʱ�䡢��Ŀ����
	private Checkbox infoWriter,subjectClass,proProgress;//��Ϣ��д�ˡ�ѧ�Ʒ��ࡢ��Ŀ��չ
	private Checkbox proBank;//��Ŀ���𡢱��˹��������������о����򡢸��������
	private Checkbox proInterNum;//��Ŀ�ڲ���š���Ŀָ��
	
	private Checkbox contractNum,contraTypeListbox,commitCom,commitComplace,acceptCom,acceptComPlace,identtime,level,conFinishCondition,fruit;
	//��Ŀͳ��
	private Checkbox hxProStatist,zxProStatist,nationalProStatist,provincProStatist;
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
	* <li>������������ѡ������ڱ������ԣ�������.xml�ĵ��С�
	 * @param null
	 * @return null
	 * @author bobo
	 */
	public void onClick$buildMiddReport() throws WriteException{
		if (xm != null) {		
			List list1 = xmService.findByKuidAndTypeAndKyclassAndXmid("1",xm.getKyId());
			if(list1.size()==0){
				try {
					Messagebox.show("�����ݣ���������", "����", Messagebox.OK,Messagebox.INFORMATION);
				} catch (InterruptedException e) {
					log.error("��ǰϵͳʱ�䣺"+ new Date().toLocaleString()
			                    + " "+ "������Ŀ�ӿ�"+ e.getMessage());
					e.printStackTrace();
				}
			    return;
			}else{
				Date now=new Date();
				String xmc="";
				if(xm.getKyMc().trim().length()>11){
					xmc=xm.getKyMc().substring(0, 10);
				}		    	
		    	String fileName = xmc+"���ڱ���"+DateUtil.getDateString(now)+".xls";
		    	String Title =xm.getKyMc()+"���ڱ���";
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
				if(proBank.isChecked()){
					titlelist.add("��Ŀ����");
					String bank="";
					if(xm.getKyScale() == null || xm.getKyScale().equals("") || xm.getKyScale().trim() == "-1"){
						bank="";
					}else{ if(xm.getKyScale().trim().equalsIgnoreCase( "1")){
						bank="���ʺ�����Ŀ";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "2")){
						bank="���Ҽ���Ŀ";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "3")){
						bank="����ί��ʡ������Ŀ";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "4")){
						bank="��������Ŀ";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "5")){
						bank="ί�м�������Ŀ";
					}else if(xm.getKyClass().trim().equalsIgnoreCase( "6")){
						bank="ѧУ������Ŀ";
					}else {
						bank="����";
					}}
					namelist.add(bank);
				}
				if(infoWriter.isChecked()){
					titlelist.add("��Ϣ��д��");
					namelist.add(xm.getUser().getKuName());
				}	
				if(contractNum.isChecked()){
					titlelist.add("��ͬ���");
					namelist.add(xm.getKyContraNum());
				}	
				if(contraTypeListbox.isChecked()){
					titlelist.add("��ͬ����");
					String bank="";
					if(xm.getContractType()== null || xm.getContractType().equals("") || xm.getContractType().trim() == "-1"){
						bank="";
					}else{ if(xm.getContractType().trim().equalsIgnoreCase( "1")){
								bank="��������";
							}else if(xm.getContractType().trim().equalsIgnoreCase( "2")){
								bank="����ת��";
							}else if(xm.getContractType().trim().equalsIgnoreCase( "3")){
								bank="��������";
							}else if(xm.getContractType().trim().equalsIgnoreCase( "4")){
								bank="������ѯ";
							}else {
								bank="����";
							}
					}
					namelist.add(bank);
				}	
				if(proInterNum.isChecked()){
					titlelist.add("��Ŀ�ڲ����");
					String inNum="";
					if(xm.getInternalNum()==null||xm.getInternalNum().equals("")){
						inNum="";
					}else{
						inNum=xm.getInternalNum().toString();
					}
					namelist.add(inNum);
				}
				if(commitCom.isChecked()){
					titlelist.add("ί�е�λ");
					namelist.add(xm.getCommitCom());
				}
				if(commitComplace.isChecked()){
					titlelist.add("ί�е�λ��ַ");
					namelist.add(xm.getCommitComPlace());
				}
				if(acceptCom.isChecked()){
					titlelist.add("���е�λ");
					namelist.add(xm.getAcceptCom());
				}
				if(acceptComPlace.isChecked()){
					titlelist.add("���е�λ��ַ");
					namelist.add(xm.getAcceptComPlace());
				}
				if(proFund.isChecked()){
					titlelist.add("��Ŀ����(��Ԫ)");
					namelist.add(xm.getKyJf()!=null?xm.getKyJf().toString():"");
				}	
				
				if(beginTime.isChecked()){
					titlelist.add("�ƻ���ʼ");
					namelist.add(xm.getKyKssj());
				}
				if(endTime.isChecked()){
					titlelist.add("�ƻ�����");
					namelist.add(xm.getKyJssj());
				}
				
						
				if(subjectClass.isChecked()){
					titlelist.add("ѧ�Ʒ���");
					String subClass="";
					if(xm.getKySubjetype() == null ||xm.getKySubjetype().equals("") || xm.getKySubjetype().trim().equalsIgnoreCase("-1")){
						subClass="";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "1")){
						subClass="��Ȼ��ѧ";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "2")){
						subClass="����ѧ";
					}else if(xm.getKySubjetype().equalsIgnoreCase( "3")){
						subClass="����";
					}
					namelist.add(subClass);
				}		
				if(proProgress.isChecked()){
					titlelist.add("��Ŀ��չ");
					String progress="";
					if(xm.getKyProgress() == null ||xm.getKyProgress().equals("") || xm.getKyProgress().trim() ==""){
						progress="";
					}else if(xm.getKyProgress().trim().equalsIgnoreCase( "1")){
						progress="������";
					}else if(xm.getKyProgress().trim().equalsIgnoreCase( "2")){
						progress="�����";
					}else if(xm.getKyProgress().trim().equalsIgnoreCase( "3")){
						progress="����";
					}	
					namelist.add(progress);
				}
				
				if(identtime.isChecked()){
					titlelist.add("����ʱ��");
					namelist.add(xm.getKyIdenttime());
				}
				if(level.isChecked()){
					titlelist.add("����ˮƽ");
					namelist.add(xm.getKyLevel());
				}
				if(conFinishCondition.isChecked()){
					titlelist.add("�з�����������");
					namelist.add(xm.getKyContentFinishConditon());
				}
				
				if(fruit.isChecked()){
					titlelist.add("ȡ�õĳɹ�����Ч");
					namelist.add(xm.getKyFruit());
				}
							
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
		                    + " "+ "���������Ŀ���ڱ��浼��"+ e.getMessage());
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