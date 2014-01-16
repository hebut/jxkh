/**
 * 
 */
package org.iti.bysj.ui.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iti.bysj.entity.BsGpunit;
import org.iti.bysj.entity.BsUserDudao;
import org.iti.bysj.service.CheckService;
import org.iti.bysj.service.GpunitService;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.XyUserRoleService;

import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

/**
 * @author DaLei
 * @version $Id: LeaderBysjWindow.java,v 1.1 2011/08/31 07:03:09 ljb Exp $
 */
public abstract class LeaderBysjWindow   extends BysjWindow{
	
	DepartmentService departmentService;
	GpunitService gpunitService;
	CheckService checkService;
	XyUserRoleService xyUserRoleService;
	
	List firstCol;
    Map secondColMap=new HashMap();
	
	public void initArgs() {
		firstCol=new ArrayList();
		List templist;
		XyUserrole xyUserRole = getXyUserRole();
		if("教学督导".equals(xyUserRole.getRole().getKrName())){
			templist = checkService.findKdidByKuidAndKdid(xyUserRole.getId().getKuId(),
					getGprocess().getKdId());
			
			for(int i=0;i<templist.size();i++){
				BsUserDudao dudao = (BsUserDudao)templist.get(i);
				
				WkTDept fdept=(WkTDept) checkService.get(WkTDept.class, 
								dudao.getId().getKdId());
				List gpunitList=gpunitService.findByPkdidAndBgid(fdept.getKdId(),
						getGprocess().getBgId());
				if(gpunitList.size()>0){
					firstCol.add(fdept);
					secondColMap.put(fdept, gpunitList);
				}			
			}
		}else{
			
			WkTDept dept=(WkTDept)gpunitService.get(WkTDept.class,getXyUserRole().getKdId());
			if(dept.getKdLevel()==1){
				//学校级别领导
				templist=departmentService.getChildDepartment(dept.getKdId(),WkTDept.DANWEI);
				for(int i=0;i<templist.size();i++){
					WkTDept fdept=(WkTDept)templist.get(i);
					List gpunitList=gpunitService.findByPkdidAndBgid(fdept.getKdId(),  getGprocess().getBgId());
					if(gpunitList.size()>0){
						firstCol.add(fdept);
						secondColMap.put(fdept, gpunitList);
					}			
				}
			}else if(dept.getKdLevel()==2){
				//学院级别领导
				templist=new ArrayList();
				templist.add(dept);		   
				for(int i=0;i<templist.size();i++){
					WkTDept fdept=(WkTDept)templist.get(i);
					List gpunitList=gpunitService.findByPkdidAndBgid(fdept.getKdId(),  getGprocess().getBgId());
					if(gpunitList.size()>0){
						firstCol.add(fdept);
						secondColMap.put(fdept, gpunitList);
					}			
				}
			}else{//系级别
				
				BsGpunit bsg=gpunitService.findByKdidAndGpid(dept.getKdId(), getGprocess().getBgId());
				List gpunitList = new ArrayList();
				gpunitList.add(bsg);
				if(bsg!=null){
					firstCol.add(dept.getPdept());
					secondColMap.put(dept.getPdept(), gpunitList);
				}
			}
		}
		
	}
	@Override
	public String isAble() {
		Date d=new Date();
		if(d.getTime()<getGprocess().getBgStart()){
			return"该毕设过程尚未开始";
		}
		return null;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public GpunitService getGpunitService() {
		return gpunitService;
	}

	public void setGpunitService(GpunitService gpunitService) {
		this.gpunitService = gpunitService;
	}

	public List getFirstCol() {
		return firstCol;
	}

	public void setFirstCol(List firstCol) {
		this.firstCol = firstCol;
	}

	public Map getSecondColMap() {
		return secondColMap;
	}

	public void setSecondColMap(Map secondColMap) {
		this.secondColMap = secondColMap;
	}

	public CheckService getCheckService() {
		return checkService;
	}

	public void setCheckService(CheckService checkService) {
		this.checkService = checkService;
	}

	public XyUserRoleService getXyUserRoleService() {
		return xyUserRoleService;
	}

	public void setXyUserRoleService(XyUserRoleService xyUserRoleService) {
		this.xyUserRoleService = xyUserRoleService;
	} 
}
