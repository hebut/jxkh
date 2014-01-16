package org.iti.projectmanage.science.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
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
import org.apache.log4j.Logger;
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
 * <li>��������: ʵ��������Ŀ���ڱ���Ķ��ƹ���
 * <li>��Ȩ: Copyright (c) ��Ϣ�����о���
 */
public class ZMiddReportWindow extends Window implements AfterCompose{	
	private static final long serialVersionUID = -1397177361099476338L;
	private Logger  log  = Logger.getLogger(ZMiddReportWindow.class);
	
	GhXm xm = (GhXm) Executions.getCurrent().getArg().get("zProgram");		
	private WkTUser user;	
	private XmService xmService;
	private JsxmService jsxmService;
	private CgService cgService;
	private HylwService hylwService;
	private QklwService qklwService;
	private RjzzService rjzzService;
	private FmzlService fmzlService;
	//��Ŀ��Ϣ
	private Checkbox proName,proSource,proMan,proNum;//��Ŀ���ơ���Ŀ��Դ����Ŀ�����ˡ���Ŀ���
	private Checkbox lxTime,beginTime,endTime,proFund;//����ʱ�䡢��ʼʱ�䡢����ʱ�䡢��Ŀ����
	private Checkbox infoWriter,proMember,subjectClass,proProgress;//��Ϣ��д�ˡ���Ŀ���Ա��ѧ�Ʒ��ࡢ��Ŀ��չ
	private Checkbox proBank,meContrib,meResearch,meTask;//��Ŀ���𡢱��˹��������������о����򡢸��������
	private Checkbox proInterNum,proTarget;//��Ŀ�ڲ���š���Ŀָ��
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
			proName.setChecked(true);proSource.setChecked(true);proMan.setChecked(true);proNum.setChecked(true);
			lxTime.setChecked(true);beginTime.setChecked(true);endTime.setChecked(true);proFund.setChecked(true);
			infoWriter.setChecked(true);proMember.setChecked(true);subjectClass.setChecked(true);proProgress.setChecked(true);
			proBank.setChecked(true);meContrib.setChecked(true);meResearch.setChecked(true);meTask.setChecked(true);
			proInterNum.setChecked(true);proTarget.setChecked(true);
			
//			hxProStatist.setChecked(true);zxProStatist.setChecked(true);
			nationalProStatist.setChecked(true);provincProStatist.setChecked(true);
			nationalFruitStatist.setChecked(true);provincFruitStatist.setChecked(true);
			conferePaperStatist.setChecked(true);journalPaperStatist.setChecked(true);softwareCopyrightStatist.setChecked(true);invenPatentStatist.setChecked(true);
		}else{
			proName.setChecked(false);proSource.setChecked(false);proMan.setChecked(false);proNum.setChecked(false);
			lxTime.setChecked(false);beginTime.setChecked(false);endTime.setChecked(false);proFund.setChecked(false);
			infoWriter.setChecked(false);proMember.setChecked(false);subjectClass.setChecked(false);proProgress.setChecked(false);
			proBank.setChecked(false);meContrib.setChecked(false);meResearch.setChecked(false);meTask.setChecked(false);
			proInterNum.setChecked(false);proTarget.setChecked(false);
			
//			hxProStatist.setChecked(false);zxProStatist.setChecked(false);
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
			List list1 = xmService.findByKuidAndTypeAndKyclassAndXmid("2",xm.getKyId());
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
				if(proNum.isChecked()){
					titlelist.add("��Ŀ���");
					namelist.add(xm.getKyNumber());
				}
				
				if(lxTime.isChecked()){
					titlelist.add("����ʱ��");
					namelist.add(xm.getKyLxsj());
				}
				if(beginTime.isChecked()){
					titlelist.add("��ʼʱ��");
					namelist.add(xm.getKyKssj());
				}
				if(endTime.isChecked()){
					titlelist.add("����ʱ��");
					namelist.add(xm.getKyJssj());
				}
				if(proFund.isChecked()){
					titlelist.add("��Ŀ����(��Ԫ)");
					namelist.add(xm.getKyJf()!=null?xm.getKyJf().toString():"");
				}	
				
				if(infoWriter.isChecked()){
					titlelist.add("��Ϣ��д��");
					namelist.add(xm.getUser().getKuName());
				}	
				if(proMember.isChecked()){
					titlelist.add("��Ŀ����Ա");
					namelist.add(xm.getKyProstaffs());
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
			
				GhJsxm jsxm=jsxmService.findByXmidAndKuidAndType(xm.getKyId(),user.getKuId(),GhJsxm.KYXM);
				if(meContrib.isChecked()){
					titlelist.add("���˹�������");
					String con="";
					
					if(jsxm!=null&&jsxm.getKyGx()!=null){
						con=jsxm.getKyGx()+"";						
					}else{
						con="";
					}
					namelist.add(con);
				}
				if(meResearch.isChecked()){
					titlelist.add("�о�����");
					String fx="";
					if(jsxm!=null&&jsxm.getYjId()!=0&&!jsxm.equals("")){
						fx=jsxm.getYjfx().getGyName();
					}else{
						fx="";
					}
					namelist.add(fx);
				}
				
				if(meTask.isChecked()){
					titlelist.add("���˳е����������");
					String task="";
					if(jsxm.getKyCdrw() == null || jsxm.getKyCdrw().equals("") || jsxm.getKyCdrw().trim().equalsIgnoreCase("-1")){
						task="";
					}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "1")){
						task="����";
					}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "2")){
						task="�μ�";
					}else if(jsxm.getKyCdrw().trim().equalsIgnoreCase( "3")){
						task="����";
					}
					namelist.add(task);
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
				
				if(proTarget.isChecked()){
					titlelist.add("��Ŀָ��");
					namelist.add(xm.getKyTarget());
				}
//				if(zxProStatist.isChecked()){
//					titlelist.add("������Ŀ��");
//					List zxProlist = xmService.findCountByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"2");
//					int totalZxSize = 0;
//			        if (zxProlist.size() > 0){            	
//			        	totalZxSize = ((Long) zxProlist.get(0)).intValue();
//			        }
//					namelist.add(totalZxSize);
//				}
//				
//				if(hxProStatist.isChecked()){
//					titlelist.add("������Ŀ��");
//					List zxProlist = xmService.findCountByKuidAndTypeAndKyclass(user.getKuId(), GhJsxm.KYXM,"1");
//					int totalZxSize = 0;
//			        if (zxProlist.size() > 0){            	
//			        	totalZxSize = ((Long) zxProlist.get(0)).intValue();
//			        }
//					namelist.add(totalZxSize);
//				}
				
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