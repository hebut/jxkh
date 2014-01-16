package com.uniwin.framework.ui.dept;

/**
 * <li>��֯�ṹ����������ģ��
 * @author DaLei
 * @2010-3-17
 */
import java.util.List;
import org.zkoss.zul.AbstractTreeModel;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

@SuppressWarnings("rawtypes")
public class DepartmentTreeModel extends AbstractTreeModel {
	private static final long serialVersionUID = 449664256724523681L;
	DepartmentService departmentService;
	String kdtype;

	public DepartmentTreeModel(Object root, DepartmentService departmentService,String kdtype) {
		super(root);
		this.departmentService = departmentService;
		this.kdtype = kdtype;
	}

	public Object getChild(Object parent, int index) {
		if (parent instanceof WkTDept) {
			WkTDept d = (WkTDept) parent;
			if(d.getKdLevel().intValue()==1&&kdtype.equals(WkTDept.XUEKE)){
				return departmentService.getChildDepartment(d.getKdId(),WkTDept.DANWEI).get(index);
			}else{
				return departmentService.getChildDepartment(d.getKdId(),kdtype).get(index);
			}
			
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent instanceof WkTDept) {
			WkTDept d = (WkTDept) parent;
			if(d.getKdLevel().intValue()==1&&kdtype.equals(WkTDept.XUEKE)){
				return departmentService.getChildDepartment(d.getKdId(),WkTDept.DANWEI).size();
			}else{
				return departmentService.getChildDepartment(d.getKdId(),kdtype).size();
			}
			
		} else if (parent instanceof List) {
			List clist = (List) parent;
			return clist.size();
		}
		return 0;
	}

	// �ж�ĳ�ڵ��Ƿ�ΪҶ�ӽڵ�
	public boolean isLeaf(Object node) {
		if (node instanceof WkTDept) {
			WkTDept d = (WkTDept) node;
			if(d.getKdLevel().intValue()==1&&kdtype.equals(WkTDept.XUEKE)){
				return departmentService.getChildDepartment(d.getKdId(),WkTDept.DANWEI).size() > 0 ? false : true;
			}else{
				return departmentService.getChildDepartment(d.getKdId(),kdtype).size() > 0 ? false : true;
			}
			
		} else if (node instanceof List) {
			List clist = (List) node;
			return clist.size() > 0 ? false : true;
		}
		return true;
	}
}
