package org.iti.gh.common.util;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;

import com.uniwin.basehs.util.BeanFactory;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

public class Department {

	DepartmentService departmentService;

	public Department() {
		this.departmentService = (DepartmentService) BeanFactory.getBean("departmentService");
	}

	/**
	 * 功能：通过用户的kdid找到用户所在的学院编号（即学院的kdid）
	 * 
	 * @param kdid
	 * @return
	 */

	public Long getCollegeByid(Long kdid) {

		WkTDept dept = departmentService.findByDid(kdid);
		Long childtemp = kdid;

		Long temp = dept.getKdPid();
		Long ptemp = temp;
		int count = 0;
		while (ptemp != 0) {
			count++;
			childtemp = temp;
			temp = departmentService.findByDid(temp).getKdPid();
			if (temp == 0 && count == 1) {
				childtemp = kdid;
				break;
			} else {
				ptemp = departmentService.findByDid(temp).getKdPid();
			}
		}
		return childtemp;
	}
}
