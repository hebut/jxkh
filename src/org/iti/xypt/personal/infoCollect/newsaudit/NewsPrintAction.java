package org.iti.xypt.personal.infoCollect.newsaudit;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.service.NewsService;

import com.uniwin.common.util.EncodeUtil;



public class NewsPrintAction extends Action {

	NewsService newsService;
	
	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String action=(String) request.getParameter("action");
		if(action.equalsIgnoreCase("newsprint")){			
			return executeNewsPrint(mapping,form,request,response);
		}

		return null;
	}

	private ActionForward executeNewsPrint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long  infoid=EncodeUtil.decodeByDES(request.getParameter("infoid"));
		Long disid=EncodeUtil.decodeByDES(request.getParameter("disid"));
		WkTInfo info=(WkTInfo)newsService.getWkTInfo(infoid);
		WkTDistribute dis=(WkTDistribute)newsService.getWktDistribute(disid);
		List infocnt=(List)newsService.getInfocnt(infoid);
	
		request.setAttribute("info", info);
		request.setAttribute("infocnt", infocnt);
		request.setAttribute("dis", dis);
		return mapping.findForward("newsprint");
	}
	
}
