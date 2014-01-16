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
	private YearListbox yearlist;//年度列表
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
	public List<JXKH_JFRESULT> sortList(List<JXKH_JFRESULT>list){//排序
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
	public class  RankResultRenderer implements ListitemRenderer{//排名结果列表渲染器
		@Override
		public void render(Listitem item, Object data) throws Exception {
			
			if(data==null)return ;
			final JXKH_JFRESULT totaljf=(JXKH_JFRESULT)data;
			item.setValue(totaljf);
			Listcell c0 = new Listcell(item.getIndex() + 1 + "");	//序号
			Listcell c1 = new Listcell(totaljf.getName());//业务部门名称
			Listcell c2 = new Listcell();//上一年度积分
            c2.setLabel(String.valueOf(totaljf.getLastYearJf()));		
            Listcell c3=new Listcell();//本年度积分
            c3.setLabel(String.valueOf(totaljf.getYearJf()));
            
            //本年度人均积分
            Listcell c5=new Listcell();
            c5.setLabel(String.valueOf(totaljf. getRjJf()));
            //年度增长率
            Listcell c6=new Listcell();
            c6.setLabel(String.valueOf(totaljf. getZzlJf()));
            
            Listcell c4=new Listcell();//业绩指数
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
	public  class DeptRenderer implements ListitemRenderer {//业务部门渲染器
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
	public void onClick$export()throws WriteException, IOException{//导出结果按钮
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
		String fileName=ConvertUtil.convertDateString(now)+"业务部门绩效总分结果"+".xls";
		String Title="业务部门绩效总分结果";
		List<String>titlelist=new ArrayList<String>();
		titlelist.add("排名");
		titlelist.add("名称");
		titlelist.add("上一年度积分");
		titlelist.add("本年度积分");
		titlelist.add("本年度人均积分");
		titlelist.add("本年度增长率积分");
		titlelist.add("人数");
		titlelist.add("业绩指标"); 
		titlelist.add("年份");
		String c[][]=new String[exportlist.size()][titlelist.size()];
		//从SOL中读取数据
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
	public  void onClick$compute()throws InterruptedException{//计算结果
		jxkhList.clear();
		double avg1=0.0d,avg2=0.0d,avg3=0.0d;//处理平均值
		double sqr1=0.0d,sqr2=0.0d,sqr3=0.0d;//处理方差
		for(WkTDept d:deptList){
			double[] dArray=new double[5];
			for(int i=0;i<3;i++){
				Decimalbox db=  (Decimalbox) deptListbox.getFellow(d.getKdId()+String.valueOf(i+1));
				if(db.getValue()==null){
					throw new WrongValueException(db,"您还没有填写数据！");
				}
				dArray[i]=db.getValue().doubleValue();
			}
			dArray[3]=100*(dArray[1]-dArray[0])/dArray[0];//dArray[3]存储增长率
			dArray[4]=dArray[1]/dArray[2];//dArray[4]存储人均积分
			avg1+=dArray[1];
            avg2+=dArray[3];
            avg3+=dArray[4];
            sqr1+=(dArray[1]*dArray[1]);
            sqr2+=(dArray[3]*dArray[3]);
            sqr3+=(dArray[4]*dArray[4]);
			dMap.put(d.getKdId(), dArray);
		}
		
		avg1=avg1/deptList.size();//本年度积分平均值(没问题)
		avg2=avg2/deptList.size();//增长率平均值
		avg3=avg3/deptList.size();//人均积分平均值
		
		sqr1=(Math.sqrt(sqr1/deptList.size()));//本年度积分方差(有问题)
		sqr2=(Math.sqrt(sqr2/deptList.size()));//增长率方差
		sqr3=(Math.sqrt(sqr3/deptList.size()));//人均积分方差
		double[] indicators1=new double[deptList.size()];//本年度积分结果
		double[] indicators2=new double[deptList.size()];//增长率结果
		double[] indicators3=new double[deptList.size()];//人均积分结果
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
			jxkh.setKdId(p);//设置ID
			String dept=(auditResultService.findDepname(p)).getKdName();//业务部门名称
			jxkh.setName(dept);
			jxkh.setLastYearJf(df(a[0]));
			jxkh.setYearJf(df(a[1]));
			jxkh.setRjJf(df(indicators2[i]));//人均积分
			jxkh.setZzlJf(df(indicators2[i]));//增长率积分
			jxkh.setPeoNum(a[2]);
			jxkh.setTotalJf(df(finalResult[i]));
			String g1=yearlist.getSelectYear();
			jxkh.setYear(String.valueOf(g1));//获取年份
			jxkhList.add(jxkh);
		}
		sortList(jxkhList);//排序
		rankResultListbox.setItemRenderer(new RankResultRenderer());
		rankResultListbox.setModel(new ListModelList(jxkhList));
		
	}
	public double[] compute(List<WkTDept> list,double pj,double fc,int j){//小计算
	         double[] result=new double[list.size()];//存储最终结果
		     double[] b=new double[list.size()];//存储数据预处理结果
        	 double[] a=new double[5];
        	 for(int i=0;i<list.size();i++){
             Long d=(list.get(i)).getKdId();
     		 a=dMap.get(d);
     		 b[i]=(a[j]-pj)/fc;//数据预处理
         }
        	
        	 double Max=b[0];
        	 double Min=b[0];
		for(int i=0;i<list.size();i++){
			if(Max<b[i])Max=b[i];
			if(Min>b[i])Min=b[i];
		}
		for(int i=0;i<list.size();i++){
			result[i]=100*((b[i]-Min)/(Max-Min));//无刚量化处理和相对标杆得分
		}
		return result;
	}
	public void onClick$saveResult() throws InterruptedException{//保存结果按钮
		    if(yearlist.getSelectedItem()==null || yearlist.getSelectedItem().getLabel().equals("-请选择-")){
		    	throw new WrongValueException(yearlist,"您还没有选择年份！");
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
		 MessageBoxshow.showInfo("添加成功！");
		 Events.postEvent(Events.ON_CHANGE, this, null);
		    }
	}
	public double df (double p){//截取小数点后两位
		double a1;
		DecimalFormat df = new DecimalFormat("#.##");   
		a1=Double.parseDouble(df.format(p)); 
		return a1;
	}
}
