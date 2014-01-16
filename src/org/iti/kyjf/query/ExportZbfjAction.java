package org.iti.kyjf.query;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.iti.kyjf.entity.Kyzb;
import org.iti.kyjf.entity.Zbdeptype;
import org.iti.kyjf.service.ZbService;
import org.iti.kyjf.service.ZbdeptypeService;
import org.iti.kyjf.service.ZbteacherService;

import com.uniwin.common.util.EncodeUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.service.DepartmentService;

public class ExportZbfjAction extends Action {
	ZbteacherService zbteacherService;
	DepartmentService departmentService;
	ZbdeptypeService zbdeptypeService;
	ZbService zbService;
	

	public ZbdeptypeService getZbdeptypeService() {
		return zbdeptypeService;
	}

	public void setZbdeptypeService(ZbdeptypeService zbdeptypeService) {
		this.zbdeptypeService = zbdeptypeService;
	}

	public ZbService getZbService() {
		return zbService;
	}

	public void setZbService(ZbService zbService) {
		this.zbService = zbService;
	}

	public ZbteacherService getZbteacherService() {
		return zbteacherService;
	}

	public void setZbteacherService(ZbteacherService zbteacherService) {
		this.zbteacherService = zbteacherService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String action=(String) request.getParameter("action");
		if(action.equalsIgnoreCase("export")){
			return executeExport(mapping, form, request, response);
		}
		return null;
	}

	private ActionForward executeExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
//		 Integer year=Integer.parseInt(EncodeUtil.decodeByDES(request.getParameter("year"))+"");
		Integer year=Integer.parseInt(request.getParameter("year"));
//		 Long kdid=EncodeUtil.decodeByDES(request.getParameter("kdid"));
		 Long kdid=Long.parseLong(request.getParameter("kdid"));
		 Integer pagesize=15;
			if(request.getParameter("pagesize")==null){
				pagesize=15;
			}else{
				pagesize=Integer.valueOf(request.getParameter("pagesize"));
			}
		 WkTDept dept=(WkTDept)departmentService.get(WkTDept.class, kdid);
		 List tlist=zbteacherService.findByYear(year,kdid,"","");
		 Kyzb zb=zbService.findByYear(year, kdid);
		 Zbdeptype zbdeptype=zbdeptypeService.queryByYearAndKdid(year, kdid);
		 request.setAttribute("zb",zb);
		 request.setAttribute("zbdeptype",zbdeptype);
		 request.setAttribute("dept", dept);//ѧԺ
		 request.setAttribute("year",year);
		 request.setAttribute("tlist", tlist);
		 request.setAttribute("pagesize", pagesize);
		 return mapping.findForward("export");
		
	}
	
}
