package com.uniwin.framework.ui.login;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.iti.bysj.service.BatchService;
import org.iti.bysj.service.BsStudentService;
import org.iti.bysj.service.BsTeacherService;
import org.iti.bysj.service.GpunitService;
import org.iti.bysj.service.PhaseService;
import org.iti.cqgl.entity.CqCqcs;
import org.iti.cqgl.service.CqcsService;
import org.iti.cqgl.service.KyhdcqService;
import org.iti.cqgl.service.NstudentService;
import org.iti.cqgl.service.ZccqService;
import org.iti.jxgl.entity.JxCalendar;
import org.iti.jxgl.service.CalendarService;

import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class NoticeAction extends Action {
	private PhaseService bsphaseService;
	private GpunitService gpunitService;
	private BatchService batchService;
	private UserService userService;
	private BsTeacherService bsteacherService;
	private BsStudentService bsStudentService;
	
	private CqcsService cqcsService;
	private CalendarService calendarService;
	private ZccqService zccqService;
	private KyhdcqService kyhdcqService;
	private NstudentService nstudentService;
	
	
	public NstudentService getNstudentService() {
		return nstudentService;
	}

	public void setNstudentService(NstudentService nstudentService) {
		this.nstudentService = nstudentService;
	}

	public ZccqService getZccqService() {
		return zccqService;
	}

	public void setZccqService(ZccqService zccqService) {
		this.zccqService = zccqService;
	}

	public KyhdcqService getKyhdcqService() {
		return kyhdcqService;
	}

	public void setKyhdcqService(KyhdcqService kyhdcqService) {
		this.kyhdcqService = kyhdcqService;
	}

	public CalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	public CqcsService getCqcsService() {
		return cqcsService;
	}

	public void setCqcsService(CqcsService cqcsService) {
		this.cqcsService = cqcsService;
	}

	public BsTeacherService getBsteacherService() {
		return bsteacherService;
	}

	public void setBsteacherService(BsTeacherService bsteacherService) {
		this.bsteacherService = bsteacherService;
	}

	public BsStudentService getBsStudentService() {
		return bsStudentService;
	}

	public void setBsStudentService(BsStudentService bsStudentService) {
		this.bsStudentService = bsStudentService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public PhaseService getBsphaseService() {
		return bsphaseService;
	}

	public void setBsphaseService(PhaseService bsphaseService) {
		this.bsphaseService = bsphaseService;
	}

	public GpunitService getGpunitService() {
		return gpunitService;
	}

	public void setGpunitService(GpunitService gpunitService) {
		this.gpunitService = gpunitService;
	}

	public BatchService getBatchService() {
		return batchService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String action=(String) request.getParameter("action");
		if(action.equalsIgnoreCase("notice")){
			return executeNotice(mapping,request,response);
		}
		return super.execute(mapping, form, request, response);
	}

	private ActionForward executeNotice(ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		Long kuid= Long.parseLong(request.getParameter("kuid"));
		WkTUser user=(WkTUser)userService.get(WkTUser.class, kuid);
		Integer havencqcs=0,weizccqcs=0;
		Integer havenkycs=0,weikhcs=0;
		Integer zcdays=0,khdays=0;
		List plist=bsphaseService.findByKuidAndNow(kuid,  new Date().getTime());
		List tlist=bsteacherService.findByKuid(user.getKuId());
		List slist=bsStudentService.findByKuid(user.getKuId());
		boolean joinbs=false;
		if((tlist==null||tlist.size()==0)&&(slist==null||slist.size()==0)){
			joinbs=false;
		}else{
			joinbs=true;
		}
		String []YearTerm =calendarService.getNowYearTerm();
	 
		JxCalendar calendar=calendarService.findByCyearAndTermAndKdid(YearTerm[0], Short.parseShort(YearTerm[1]),user.getKdId()!=0L?user.getDept().getKdSchid():0L);
		boolean flag=true;
		if(calendar!=null){
			Calendar c = Calendar.getInstance();
			c.setTime(calendar.getStarttime());
			c.add(Calendar.DATE, Integer.parseInt(calendar.getWeeks())*7);
			if(!nstudentService.CheckIf(calendar.getId(), user.getKuId())){
			CqCqcs cqcs=cqcsService.findByXlid(calendar.getId());
			if(cqcs!=null){
				   zcdays=ConvertUtil.countDays(ConvertUtil.convertString(new Date()),ConvertUtil.convertString(cqcs.getZcendDate()!=null?cqcs.getZcendDate():c.getTime()));//早操出勤天数
				   khdays=ConvertUtil.countDays(ConvertUtil.convertString(new Date()),ConvertUtil.convertString(cqcs.getEndDate()!=null?cqcs.getEndDate():c.getTime()));//课活出勤天数
				   havencqcs=zccqService.findByKuidAndXlid(kuid,calendar.getId());//已经参加的早操次数
				   weizccqcs=cqcs.getCcZczscs().intValue()-havencqcs;//还差的早操出勤次数
				   //已经参加的活动次数
				   havenkycs= kyhdcqService.findByKuIdAndXlId(kuid, calendar.getId())==null?0:kyhdcqService.findByKuIdAndXlId(kuid, calendar.getId()).size();
				   weikhcs=cqcs.getCcKyhdzscs().intValue()-havenkycs;
				   request.setAttribute("zccqcs", havencqcs);//已出操次数
				   request.setAttribute("weizccqcs", weizccqcs);//未出操次数
//				   request.setAttribute("khcqcs", havenkycs);//已参加课活次数
//				   request.setAttribute("weikhcs", weikhcs);//未参加课活次数
				   request.setAttribute("zcdays", zcdays);
//				   request.setAttribute("khdays", khdays);
			}else{
				  request.setAttribute("zccqcs", null);//已出操次数
				   request.setAttribute("weizccqcs", null);//未出操次数
//				   request.setAttribute("khcqcs", null);//已参加课活次数
//				   request.setAttribute("weikhcs", null);//未参加课活次数
				   request.setAttribute("zcdays", null);
//				   request.setAttribute("khdays", null);
			}
			}
			flag= nstudentService.CheckIf(calendar.getId(), user.getKuId());
		} 
		request.setAttribute("flag",flag);
		request.setAttribute("joinbs",joinbs );
		request.setAttribute("user", user);
		request.setAttribute("plist", plist);
		return mapping.findForward("notice");
	}
}
