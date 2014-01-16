package org.iti.bysj.ui.listbox;

import java.util.List;

import org.iti.xypt.entity.XyClass;
import org.iti.xypt.service.XyClassService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.uniwin.framework.entity.WkTDept;

/**
 * ��������ϵ���꼶�󣬼ȿ�����ʾ�༶�б�
 * @author Administrator
 *
 */
public class XyClassList extends Listbox implements AfterCompose {

	/**
	 * ��ǰ����ϵ
	 */
	private WkTDept dept;
	
	/**
	 * ��ǰ�����꼶
	 */
	private Integer grade;
	
	/**
	 * ��Ҫѡ�еİ༶
	 */
	XyClass xyClass;
	
	XyClassService xyClassService;
	
	public void afterCompose() {
		 Components.wireVariables(this, this);
		 Components.addForwards(this, this);
		 
		 this.setItemRenderer(new ListitemRenderer(){
			public void render(Listitem item, Object data) throws Exception {
				 XyClass cl=(XyClass)data;
				 item.setValue(cl);
				 item.setLabel(cl.getClName());
				 if(xyClass!=null&&xyClass.getClId().intValue()==cl.getClId().intValue()){
					 item.setSelected(true);
				 }
			}			 
		 });
	}
	
	public void loadList(){
		List clist=xyClassService.findByKdidAndGrade(getDept().getKdId(), getGrade());
		this.setModel(new ListModelList(clist));
	}

	public WkTDept getDept() {
		return dept;
	}

	public void setDept(WkTDept dept) {
		this.dept = dept;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	public XyClass getSelectClass(){
		if(this.getModel().getSize()==0){
			return null;
		}else if(this.getSelectedItem()==null){
			return (XyClass) this.getModel().getElementAt(0);
		}else{
			return (XyClass) this.getSelectedItem().getValue();
		}
	}

	public XyClass getXyClass() {
		return xyClass;
	}

	public void setXyClass(XyClass xyClass) {
		this.xyClass = xyClass;
	}
 
}
