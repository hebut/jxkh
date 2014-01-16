package org.iti.jxkh.tools;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.common.util.MessageBoxshow;
import org.iti.gh.ui.listbox.YearListbox;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.write.WriteException;
import org.iti.jxkh.entity.JXKH_JFRESULT;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;
import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTDept;
public class DepartAlgWindow extends Window implements AfterCompose{
	/**
	 * @author WXY
	 */
	private static final long serialVersionUID = -5467313524029368330L;
	private YearListbox yearlist;//����б�
	private List<JXKH_JFRESULT> jxkhList = new ArrayList<JXKH_JFRESULT>();//list
	private Listbox rankResultListbox,deptListbox;
	private AuditConfigService  auditConfigService ;
	private List<WkTDept>deptList= new  ArrayList<WkTDept>();
	private AuditResultService auditResultService;
	private Map<Long, double[]> dMap = new HashMap<Long, double[]>();
	
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
	    yearlist.initYearListbox("");
	    deptListbox.setItemRenderer(new DeptRenderer());
	    deptList=auditResultService.findWorkDept();
	    deptListbox.setModel(new ListModelList(deptList));
	}
	public List<JXKH_JFRESULT> sortList(List<JXKH_JFRESULT>list){//����
		Collections.sort(list, new Comparator<JXKH_JFRESULT>(){

			@Override
			public int compare(JXKH_JFRESULT o1, JXKH_JFRESULT o2) {
				double first=o1.getTotalJf();
				double second = o2.getTotalJf();
				return first==second?0:(first>second?-1:1);
			}
			
		});
		return list;
	}
	public class  RankResultRenderer implements ListitemRenderer{//��������б���Ⱦ��
		@Override
		public void render(Listitem item, Object data) throws Exception {
			
			if(data==null)return ;
			final JXKH_JFRESULT totaljf=(JXKH_JFRESULT)data;
			item.setValue(totaljf);
			Listcell c0 = new Listcell(item.getIndex() + 1 + "");	//���
			Listcell c1 = new Listcell(totaljf.getName());//ҵ��������
			Listcell c2 = new Listcell();//��һ��Ȼ���
            c2.setLabel(String.valueOf(totaljf.getLastYearJf()));		
            Listcell c3=new Listcell();//����Ȼ���
            c3.setLabel(String.valueOf(totaljf.getYearJf()));
            
            //������˾�����
            Listcell c5=new Listcell();
            c5.setLabel(String.valueOf(totaljf. getRjJf()));
            //���������
            Listcell c6=new Listcell();
            c6.setLabel(String.valueOf(totaljf. getZzlJf()));
            
            Listcell c4=new Listcell();//ҵ��ָ��
            c4.setLabel(String.valueOf(totaljf.getTotalJf()));
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c4);
		}
	}
	public  class DeptRenderer implements ListitemRenderer {//ҵ������Ⱦ��
		public void render(Listitem item, Object data) throws Exception {
			if(data==null) return ;
			final WkTDept name=(WkTDept) data;
			item.setValue(name);
			Listcell c0=new Listcell(name.getKdName());
			Listcell c1=new Listcell();
			 Decimalbox a=new Decimalbox();
			 a.setId(name.getKdId()+"1");
			 a.setParent(c1);
		    Listcell c2=new Listcell();
		      Decimalbox b=new Decimalbox();
		      b.setId(name.getKdId()+"2");
			  b.setParent(c2);
			Listcell c3=new Listcell();
			Decimalbox c=new Decimalbox();
			  c.setId(name.getKdId()+"3");
			  c.setParent(c3);
			item.appendChild(c0);
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c3);

		}
		
	}
	public void onClick$export()throws WriteException, IOException{//���������ť
		int i=0;
		ArrayList<JXKH_JFRESULT> exportlist=new ArrayList<JXKH_JFRESULT>();
		@SuppressWarnings("unchecked")
		Iterator<Listitem> it=rankResultListbox.getItems().iterator();
		while(it.hasNext()){
			Listitem item= (Listitem)it.next();
			JXKH_JFRESULT p=(JXKH_JFRESULT)item.getValue();
			exportlist.add(i,p);
			i++;
		}
		Date now=new Date ();
		String fileName=ConvertUtil.convertDateString(now)+"ҵ���ż�Ч�ֽܷ��"+".xls";
		String Title="ҵ���ż�Ч�ֽܷ��";
		List<String>titlelist=new ArrayList<String>();
		titlelist.add("����");
		titlelist.add("����");
		titlelist.add("��һ��Ȼ���");
		titlelist.add("����Ȼ���");
		titlelist.add("������˾�����");
		titlelist.add("����������ʻ���");
		titlelist.add("����");
		titlelist.add("ҵ��ָ��"); 
		titlelist.add("���");
		String c[][]=new String[exportlist.size()][titlelist.size()];
		//��SOL�ж�ȡ����
		for(int j=0;j<exportlist.size();j++){
			JXKH_JFRESULT result=(JXKH_JFRESULT)exportlist.get(j);
			c[j][0]=j+1+"";
			c[j][1]=result.getName();
			c[j][2]=String.valueOf(result.getLastYearJf());
			c[j][3]=String.valueOf(result.getYearJf());
			c[j][4]=String.valueOf(result.getRjJf());
			c[j][5]=String.valueOf(result.getZzlJf());
			c[j][6]=String.valueOf(result.getPeoNum());
			c[j][7]=String.valueOf(result.getTotalJf());
			c[j][8]=String.valueOf(result.getYear())+"";
		}
		ExportExcel ee=new ExportExcel();
		ee.exportExcel(fileName, Title, titlelist, exportlist.size(), c);
	}
	public  void onClick$compute()throws InterruptedException{//������
		jxkhList.clear();
		double avg1=0.0d,avg2=0.0d,avg3=0.0d;//����ƽ��ֵ
		double sqr1=0.0d,sqr2=0.0d,sqr3=0.0d;//������
		for(WkTDept d:deptList){
			double[] dArray=new double[5];
			for(int i=0;i<3;i++){
				Decimalbox db=  (Decimalbox) deptListbox.getFellow(d.getKdId()+String.valueOf(i+1));
				if(db.getValue()==null){
					throw new WrongValueException(db,"����û����д���ݣ�");
				}
				dArray[i]=db.getValue().doubleValue();
			}
			dArray[3]=100*(dArray[1]-dArray[0])/dArray[0];//dArray[3]�洢������
			dArray[4]=dArray[1]/dArray[2];//dArray[4]�洢�˾�����
			avg1+=dArray[1];
            avg2+=dArray[3];
            avg3+=dArray[4];
            sqr1+=(dArray[1]*dArray[1]);
            sqr2+=(dArray[3]*dArray[3]);
            sqr3+=(dArray[4]*dArray[4]);
			dMap.put(d.getKdId(), dArray);
		}
		
		avg1=avg1/deptList.size();//����Ȼ���ƽ��ֵ(û����)
		avg2=avg2/deptList.size();//������ƽ��ֵ
		avg3=avg3/deptList.size();//�˾�����ƽ��ֵ
		
		sqr1=(Math.sqrt(sqr1/deptList.size()));//����Ȼ��ַ���(������)
		sqr2=(Math.sqrt(sqr2/deptList.size()));//�����ʷ���
		sqr3=(Math.sqrt(sqr3/deptList.size()));//�˾����ַ���
		double[] indicators1=new double[deptList.size()];//����Ȼ��ֽ��
		double[] indicators2=new double[deptList.size()];//�����ʽ��
		double[] indicators3=new double[deptList.size()];//�˾����ֽ��
		indicators1=compute(deptList,avg1,sqr1,1);
		indicators2=compute(deptList,avg2,sqr2,3);
		indicators3=compute(deptList,avg3,sqr3,4);
		
		double[] finalResult=new double[deptList.size()];
		for(int i=0;i<deptList.size();i++){
			finalResult[i]=(indicators1[i]+indicators2[i]+indicators3[i]);
		}
		for(int i=0;i<deptList.size();i++){
			JXKH_JFRESULT jxkh=new JXKH_JFRESULT();
			double[] a=new double[5];
			a=dMap.get(deptList.get(i).getKdId());
			Long p=deptList.get(i).getKdId();
			jxkh.setKdId(p);//����ID
			String dept=(auditResultService.findDepname(p)).getKdName();//ҵ��������
			jxkh.setName(dept);
			jxkh.setLastYearJf(df(a[0]));
			jxkh.setYearJf(df(a[1]));
			jxkh.setRjJf(df(indicators2[i]));//�˾�����
			jxkh.setZzlJf(df(indicators2[i]));//�����ʻ���
			jxkh.setPeoNum(a[2]);
			jxkh.setTotalJf(df(finalResult[i]));
			String g1=yearlist.getSelectYear();
			jxkh.setYear(String.valueOf(g1));//��ȡ���
			jxkhList.add(jxkh);
		}
		sortList(jxkhList);//����
		rankResultListbox.setItemRenderer(new RankResultRenderer());
		rankResultListbox.setModel(new ListModelList(jxkhList));
		
	}
	public double[] compute(List<WkTDept> list,double pj,double fc,int j){//С����
	         double[] result=new double[list.size()];//�洢���ս��
		     double[] b=new double[list.size()];//�洢����Ԥ������
        	 double[] a=new double[5];
        	 for(int i=0;i<list.size();i++){
             Long d=(list.get(i)).getKdId();
     		 a=dMap.get(d);
     		 b[i]=(a[j]-pj)/fc;//����Ԥ����
         }
        	
        	 double Max=b[0];
        	 double Min=b[0];
		for(int i=0;i<list.size();i++){
			if(Max<b[i])Max=b[i];
			if(Min>b[i])Min=b[i];
		}
		for(int i=0;i<list.size();i++){
			result[i]=100*((b[i]-Min)/(Max-Min));//�޸������������Ա�˵÷�
		}
		return result;
	}
	public void onClick$saveResult() throws InterruptedException{//��������ť
		    if(yearlist.getSelectedItem()==null || yearlist.getSelectedItem().getLabel().equals("-��ѡ��-")){
		    	throw new WrongValueException(yearlist,"����û��ѡ����ݣ�");
		    }else{
			List<JXKH_JFRESULT>resultList=new ArrayList<JXKH_JFRESULT>();
			resultList=auditConfigService.findJfByYear(String.valueOf(yearlist.getSelectYear()));
			if(resultList.size()>0){
				for(int p=0;p<resultList.size();p++){
					auditConfigService.delete(resultList.get(p));
				}
			}
			for(int j=0;j<jxkhList.size();j++){
				jxkhList.get(j).setYear(yearlist.getSelectYear());
				auditConfigService.save(jxkhList.get(j));
			}
		 MessageBoxshow.showInfo("��ӳɹ���");
		 Events.postEvent(Events.ON_CHANGE, this, null);
		    }
	}
	public double df (double p){//��ȡС�������λ
		double a1;
		DecimalFormat df = new DecimalFormat("#.##");   
		a1=Double.parseDouble(df.format(p)); 
		return a1;
	}
}
