package com.uniwin.asm.personal.ui.data;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJsps;
import org.iti.gh.entity.GhJszz;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.entity.GhSk;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JslwService;
import org.iti.gh.service.JspsService;
import org.iti.gh.service.JszzService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QklwService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.SkService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.ZzService;
import org.iti.jxkh.service.RychService;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.entity.Title;
import org.iti.xypt.service.TeacherService;
import org.iti.xypt.service.XyptTitleService;

import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;

public class ReportAction extends Action 
{

	private UserService userService;
	private TeacherService teacherService;	
	private XmService xmService;
	private CgService cgService;
	private HylwService hylwService;
	private QklwService qklwService;
	private RjzzService rjzzService;
	private FmzlService fmzlService;
	private ZzService zzService;
	private JszzService jszzService;
	private SkService skService;
	private RychService rychService;
	private XyptTitleService xypttitleService;
	private PxService pxService;
	private JslwService jslwService;
	private JspsService jspsService;
	
	private Map reportMap;
	private String test;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String action=(String) request.getParameter("action");
		if(action.equalsIgnoreCase("indicator")){
			return executeIndicator(mapping,request,response);
		}
		return super.execute(mapping, form, request, response);
	}
	
	private ActionForward executeIndicator(ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		reportMap = (Map)request.getSession().getAttribute("reportMap");
		GregorianCalendar calendar=new GregorianCalendar(); 
		int curYear = calendar.get(calendar.YEAR);//当前年份
		Long kuid= Long.parseLong(request.getParameter("kuid"));
		WkTUser user=(WkTUser)userService.get(WkTUser.class, kuid);
		Teacher teacher = teacherService.findBykuid(user.getKuId());
		List titleList = xypttitleService.findBytid(teacher.getThTitle());
		Title teacherTitle = (Title)titleList.get(0);
		GhJsps ps = jspsService.getByKuidYear(kuid, curYear);
		//最高学历
		String[] Educational = { " ", "博士毕业", "博士结业", "博士肄业", "双硕士",
				"硕士毕业", "硕士结业", "硕士肄业", "相当硕士毕业", "研究生班毕业", "研究生班结业", "研究生班肄业",
				"双本科", "本科毕业", "本科结业", "本科肄业", "相当本科毕业", "双大专", "大专毕业", "大专结业",
				"相当大专毕业", "中专毕业", "中专结业" };
		String education=null;
		if (user.getKuEducational() == null || user.getKuEducational() == ""||user.getKuEducational().trim().equalsIgnoreCase("0")) {
			education=" ";
		} else {
			education = Educational[Integer.valueOf(user.getKuEducational())];
		}
		//学位
		String[] xuewei = { " ", "名誉博士", "博士", "硕士", "双硕士", "学士", "无" };
		String hDegree=null;
		if (user.getKuEducational() == null || user.getKuEducational() == ""||user.getKuXuewei().trim().equalsIgnoreCase("0")) {
			hDegree=" ";
		} else {
			hDegree = xuewei[Integer.valueOf(user.getKuXuewei())];
		}
		//修业年限
		String[] year = { "", "两年", "两年半", "三年", "四年", "五年" };
		String hEduYear = "";
		if (teacher.getThSupeyear() != null && !"".equals(teacher.getThSupeyear())) {
			hEduYear = year[(Integer.valueOf(teacher.getThSupeyear()))];
		}
		List allSkList = skService.findByKuid(user.getKuId());//授课情况
		int total = 0;//总学时
		long average = 0L;//年均学时数
		int startYear = curYear;
		int endYear = 0;
		if(null!=teacher.getThEmplTime()&&!"".equals(teacher.getThEmplTime())){
			endYear = Integer.valueOf(teacher.getThEmplTime().substring(0, 4));
		}
		for(int i=0;i<allSkList.size();i++)
		{
			GhSk gs = (GhSk)allSkList.get(i);
			String hours = 0+"";
			if(null!=gs.getThWork()&&!"".equals(gs.getThWork()))
			{
				hours = gs.getThWork();
			}
			total = total + Integer.valueOf(hours);
			if(null==gs.getSkYear()||"".equals(gs.getSkYear()))
			{
			}else if(null!=gs.getSkYear()&&gs.getSkYear().length()>4){
				int curThYear = Integer.valueOf(gs.getSkYear().substring(0, 4));
				if(curThYear>endYear)
					endYear = curThYear;
				if(curThYear<startYear)
					startYear = curThYear;
			}
			
		}
		if(total>0)
		{
			average = total/(endYear - startYear + 1);
		}
		List skList = new ArrayList();
		if(null!=reportMap.get("skIds")&&!"".equals(reportMap.get("skIds"))){
			skList = skService.findBySkIds(reportMap.get("skIds").toString());//授课情况
		}
			
		List zdxsList = pxService.findByKuid(user.getKuId());//指导学生 待定
		
		List<GhCg> cglist = new ArrayList();
		if(null!=reportMap.get("cgIds")&&!"".equals(reportMap.get("cgIds")))
		{
			cglist = cgService.findByKyIds(reportMap.get("cgIds").toString());//获奖成果
		}
		List<GhXm> xmlist = new ArrayList();
		if(null!=reportMap.get("xmIds")&&!"".equals(reportMap.get("xmIds")))
		{
			xmlist = xmService.findByKyIdsType(reportMap.get("xmIds").toString());//科研项目
		}
		List<GhRjzz> softAuthlist = new ArrayList();
		if(null!=reportMap.get("rjzzIds")&&!"".equals(reportMap.get("rjzzIds")))
		{
			softAuthlist = rjzzService.findByRjIds(reportMap.get("rjzzIds").toString());//软件著作权
		}
		List<GhQklw> magaPapelist = new ArrayList();
		if(null!=reportMap.get("magaIds")&&!"".equals(reportMap.get("magaIds")))
		{
			magaPapelist = qklwService.findByLwIds(reportMap.get("magaIds").toString());//期刊论文
		}
		List<GhHylw> hylwList = new ArrayList();
		if(null!=reportMap.get("mepaIds")&&!"".equals(reportMap.get("mepaIds")))
		{
			hylwList = hylwService.findByLwIds(reportMap.get("mepaIds").toString());//会议论文
		}
		List<GhZz> pubtealist = new ArrayList();
		if(null!=reportMap.get("zzIds")&&!"".equals(reportMap.get("zzIds")))
		{
			pubtealist = zzService.findByZzIds(reportMap.get("zzIds").toString());//教材
		}
		//任职资历(年)
		String qualifications = "";
		if(null!=teacher.getThEnter()&&!"".equals(teacher.getThEnter()))
		{
			int enterYear = Integer.valueOf(teacher.getThEnter().substring(0, 4));
			qualifications = curYear-enterYear+1+"";
		}
		List rychList = rychService.FindByKuid(user.getKuId());//荣誉称号
		
		Map<String,String> countMap = new HashMap<String,String>();//存放统计结果
		
		//获奖项目统计
		int allGjjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.GJJ, 0);
		int allSbjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.SBJ, 0);
		int allStjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.STJ, 0);
		int firGjjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.GJJ, 1);
		int firSbjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.SBJ, 1);
		int firStjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.STJ, 1);
		int secGjjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.GJJ, 2);
		int secSbjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.SBJ, 2);
		int secStjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.STJ, 2);
		int thiGjjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.GJJ, 3);
		int thiSbjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.SBJ, 3);
		int thiStjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.STJ, 3);
		int fouGjjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.GJJ, 4);
		int fouSbjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.SBJ, 4);
		int fouStjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.STJ, 4);
		int fivGjjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.GJJ, 5);
		int fivSbjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.SBJ, 5);
		int fivStjHjCount = xmService.getHjCountByKuidGx(kuid, GhXm.STJ, 5);
		countMap.put("firGjjHjCount", firGjjHjCount+"");
		countMap.put("firSbjHjCount",firSbjHjCount+"");
		countMap.put("firStjHjCount", firStjHjCount+"");
		countMap.put("secGjjHjCount", secGjjHjCount+"");
		countMap.put("secSbjHjCount", secSbjHjCount+"");
		countMap.put("secStjHjCount",secStjHjCount+"" );
		countMap.put("thiGjjHjCount",thiGjjHjCount+"");
		countMap.put("thiSbjHjCount", thiSbjHjCount+"");
		countMap.put("thiStjHjCount", thiStjHjCount+"");
		countMap.put("fouGjjHjCount", fouGjjHjCount+"");
		countMap.put("fouSbjHjCount", fouSbjHjCount+"");
		countMap.put("fouStjHjCount", fouStjHjCount+"");
		countMap.put("fivGjjHjCount", fivGjjHjCount+"");
		countMap.put("fivSbjHjCount", fivSbjHjCount+"");
		countMap.put("fivStjHjCount", fivStjHjCount+"");
		countMap.put("elseGjjHjCount", allGjjHjCount-firGjjHjCount-secGjjHjCount-thiGjjHjCount-fouGjjHjCount-fivGjjHjCount+"");
		countMap.put("elseSbjHjCount", allSbjHjCount-firGjjHjCount-secSbjHjCount-thiSbjHjCount-fouSbjHjCount-fivSbjHjCount+"");
		countMap.put("elseStjHjCount", allStjHjCount-firStjHjCount-secStjHjCount-thiStjHjCount-fouStjHjCount-fivStjHjCount+"");
		//项目统计
		int allGjjCount = xmService.getCountByKuidGx(kuid, GhXm.GJJ, 0);
		int allSbjCount = xmService.getCountByKuidGx(kuid, GhXm.SBJ, 0);
		int allStjCount = xmService.getCountByKuidGx(kuid, GhXm.STJ, 0);
		int firGjjCount = xmService.getCountByKuidGx(kuid, GhXm.GJJ, 1);
		int firSbjCount = xmService.getCountByKuidGx(kuid, GhXm.SBJ, 1);
		int firStjCount = xmService.getCountByKuidGx(kuid, GhXm.STJ, 1);
		int secGjjCount = xmService.getCountByKuidGx(kuid, GhXm.GJJ, 2);
		int secSbjCount = xmService.getCountByKuidGx(kuid, GhXm.SBJ, 2);
		int secStjCount = xmService.getCountByKuidGx(kuid, GhXm.STJ, 2);
		int thiGjjCount = xmService.getCountByKuidGx(kuid, GhXm.GJJ, 3);
		int thiSbjCount = xmService.getCountByKuidGx(kuid, GhXm.SBJ, 3);
		int thiStjCount = xmService.getCountByKuidGx(kuid, GhXm.STJ, 3);
		int fouGjjCount = xmService.getCountByKuidGx(kuid, GhXm.GJJ, 4);
		int fouSbjCount = xmService.getCountByKuidGx(kuid, GhXm.SBJ, 4);
		int fouStjCount = xmService.getCountByKuidGx(kuid, GhXm.STJ, 4);
		int fivGjjCount = xmService.getCountByKuidGx(kuid, GhXm.GJJ, 5);
		int fivSbjCount = xmService.getCountByKuidGx(kuid, GhXm.SBJ, 5);
		int fivStjCount = xmService.getCountByKuidGx(kuid, GhXm.STJ, 5);
		countMap.put("firGjjCount", firGjjCount+"");
		countMap.put("firSbjCount",firSbjCount+"");
		countMap.put("firStjCount", firStjCount+"");
		countMap.put("secGjjCount", secGjjCount+"");
		countMap.put("secSbjCount", secSbjCount+"");
		countMap.put("secStjCount",secStjCount+"" );
		countMap.put("thiGjjCount",thiGjjCount+"");
		countMap.put("thiSbjCount", thiSbjCount+"");
		countMap.put("thiStjCount", thiStjCount+"");
		countMap.put("fouGjjCount", fouGjjCount+"");
		countMap.put("fouSbjCount", fouSbjCount+"");
		countMap.put("fouStjCount", fouStjCount+"");
		countMap.put("fivGjjCount", fivGjjCount+"");
		countMap.put("fivSbjCount", fivSbjCount+"");
		countMap.put("fivStjCount", fivStjCount+"");
		countMap.put("elseGjjCount", allGjjCount-firGjjCount-secGjjCount-thiGjjCount-fouGjjCount-fivGjjCount+"");
		countMap.put("elseSbjCount", allSbjCount-firGjjCount-secSbjCount-thiSbjCount-fouSbjCount-fivSbjCount+"");
		countMap.put("elseStjCount", allStjCount-firStjCount-secStjCount-thiStjCount-fouStjCount-fivStjCount+"");
		
		//发明专利
		int allFmzlCount = fmzlService.getCountBykuidSelfs(kuid, 0);
		int firFmzlCount = fmzlService.getCountBykuidSelfs(kuid, 1);
		int secFmzlCount = fmzlService.getCountBykuidSelfs(kuid, 2);
		int thiFmzlCount = fmzlService.getCountBykuidSelfs(kuid, 3);
		int fouFmzlCount = fmzlService.getCountBykuidSelfs(kuid, 4);
		int fivFmzlCount = fmzlService.getCountBykuidSelfs(kuid, 5);
		countMap.put("firFmzlCount", firFmzlCount+"");
		countMap.put("secFmzlCount", secFmzlCount+"");
		countMap.put("thiFmzlCount", thiFmzlCount+"");
		countMap.put("fouFmzlCount", fouFmzlCount+"");
		countMap.put("fivFmzlCount", fivFmzlCount+"");
		countMap.put("elseFmzlCount",allFmzlCount-firFmzlCount-secFmzlCount-thiFmzlCount-fouFmzlCount-fivFmzlCount+"");
		
		//教材统计
		List<Long> zbjcList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.JC, GhJszz.chiefEditor);//主编的教材
		String zbjcCount = zbjcList!=null?zbjcList.get(0)+"":"";
		countMap.put("zbjcCount", zbjcCount);
		List<Long> fzbjcList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.JC, GhJszz.DeputyEditor);//副主编的教材
		String fzbjcCount = fzbjcList!=null?fzbjcList.get(0)+"":"";
		countMap.put("fzbjcCount", fzbjcCount);
		List<Long> cbjcList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.JC, GhJszz.editor);//参编的教材
		String cbjcCount = cbjcList!=null?cbjcList.get(0)+"":"";
		countMap.put("cbjcCount", cbjcCount);
		int jcWords = jszzService.getWordsByKuidType(user.getKuId(), GhJszz.JC);
		countMap.put("jcWords", jcWords+"");
		
		//论著统计统计
		List<Long> firstzzList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.ZZ, GhJszz.chiefEditor);//
		int firstzzCount = firstzzList!=null?((Long)firstzzList.get(0)).intValue():0;
		countMap.put("firstzzCount", firstzzCount+"");
		List<Long> secondzzList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.ZZ, GhJszz.DeputyEditor);//
		int secondzzCount = secondzzList!=null?((Long)secondzzList.get(0)).intValue():0;
		countMap.put("secondzzCount", secondzzCount+"");
		List<Long> thirdzzList = jszzService.findCountByKuidAndType(user.getKuId(), GhJszz.ZZ, GhJszz.editor);//
		int thirdzzCount = thirdzzList!=null?((Long)thirdzzList.get(0)).intValue():0;
		countMap.put("thirdzzCount", thirdzzCount+"");
		int zzWords = jszzService.getWordsByKuidType(user.getKuId(), GhJszz.ZZ);
		countMap.put("zzWords", zzWords+"");
		
		//期刊论文统计
		List<Long> allQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_NO, GhQklw.allAuthor);
		int allQkCount = allQkList!=null?((Long)allQkList.get(0)).intValue():0;
		List<Long> fQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_NO, GhQklw.firstAuthor);
		int fQkCount = fQkList!=null?((Long)fQkList.get(0)).intValue():0;
		countMap.put("fQkCount", fQkCount+"");
		List<Long> sQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_NO, GhQklw.secondAuthor);
		int sQkCount = sQkList!=null?((Long)sQkList.get(0)).intValue():0;
		countMap.put("sQkCount", sQkCount+"");
		List<Long> tQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_NO, GhQklw.thirdAuthor);
		int tQkCount = tQkList!=null?((Long)tQkList.get(0)).intValue():0;
		countMap.put("tQkCount", tQkCount+"");
		countMap.put("elseQkCount", allQkCount-fQkCount-sQkCount-tQkCount+"");//其他
		
		String indexes = "'"+GhQklw.SCI+"','"+GhQklw.EI+"','"+GhQklw.ISTP+"'";
		List<Long> allMCenterQkList = qklwService.findCountByKuidRecordSelfs(user.getKuId(),indexes, GhQklw.allAuthor);
		int allMCenterQkCount = allMCenterQkList!=null?((Long)allMCenterQkList.get(0)).intValue():0;
		List<Long> fMCenterQkList = qklwService.findCountByKuidRecordSelfs(user.getKuId(), indexes, GhQklw.firstAuthor);
		int fMCenterQkCount = fMCenterQkList!=null?((Long)fMCenterQkList.get(0)).intValue():0;
		countMap.put("fMCenterQkCount", fMCenterQkCount+"");
		List<Long> sMCenterQkList = qklwService.findCountByKuidRecordSelfs(user.getKuId(), indexes, GhQklw.secondAuthor);
		int sMCenterQkCount = sMCenterQkList!=null?((Long)sMCenterQkList.get(0)).intValue():0;
		countMap.put("sMCenterQkCount", sMCenterQkCount+"");
		List<Long> tMCenterQkList = qklwService.findCountByKuidRecordSelfs(user.getKuId(), indexes, GhQklw.thirdAuthor);
		int tMCenterQkCount = tMCenterQkList!=null?((Long)tMCenterQkList.get(0)).intValue():0;
		countMap.put("tMCenterQkCount", tMCenterQkCount+"");
		int elseMCenterQkCount = allMCenterQkCount-fMCenterQkCount-sMCenterQkCount-tMCenterQkCount;
		countMap.put("elseMCenterQkCount", elseMCenterQkCount+"");
		
		
		List<Long> allCenterQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_YES, GhQklw.allAuthor);
		int allCenterQkCount = allCenterQkList!=null?((Long)allCenterQkList.get(0)).intValue():0;
		List<Long> fCenterQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_YES, GhQklw.firstAuthor);
		int fCenterQkCount = fCenterQkList!=null?((Long)fCenterQkList.get(0)).intValue():0;
		countMap.put("fCenterQkCount", fCenterQkCount-fMCenterQkCount+"");
		List<Long> sCenterQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_YES, GhQklw.secondAuthor);
		int sCenterQkCount = sCenterQkList!=null?((Long)sCenterQkList.get(0)).intValue():0;
		countMap.put("sCenterQkCount", sCenterQkCount-sMCenterQkCount+"");
		List<Long> tCenterQkList = qklwService.findCountByKuidCenterSelfs(user.getKuId(), GhQklw.LWHX_YES, GhQklw.thirdAuthor);
		int tCenterQkCount = tCenterQkList!=null?((Long)tCenterQkList.get(0)).intValue():0;
		countMap.put("tCenterQkCount", tCenterQkCount-tMCenterQkCount+"");
		countMap.put("elseCenterQkCount", allCenterQkCount-fCenterQkCount-sCenterQkCount-tCenterQkCount-elseMCenterQkCount+"");
		
		int lwWords = qklwService.getWordsByKuidIsCenter(kuid, GhQklw.LWHX_NO);
		int centerLwWords = qklwService.getWordsByKuidRecord(kuid, indexes);
		int mCenterLwWords = qklwService.getWordsByKuidIsCenter(kuid, GhQklw.LWHX_YES);
		int elseCenterLwWords = centerLwWords - mCenterLwWords;
		countMap.put("lwWords", lwWords+"");
		countMap.put("mCenterLwWords", mCenterLwWords+"");
		countMap.put("elseCenterLwWords", elseCenterLwWords+"");
		
		request.setAttribute("countMap", countMap);
		request.setAttribute("wktUser", user);
		request.setAttribute("teacher", teacher);
		request.setAttribute("ghJsps", ps);
		request.setAttribute("teacherTitle", teacherTitle.getTiName());
		request.setAttribute("education", education);
		request.setAttribute("hDegree", hDegree);
		request.setAttribute("hEduYear", hEduYear);
		request.setAttribute("skList", skList);
		request.setAttribute("total", total+"");
		request.setAttribute("average", average+"");
		request.setAttribute("zdxsList", zdxsList);
		request.setAttribute("cglist", cglist);
		request.setAttribute("xmlist", xmlist);
		request.setAttribute("softAuthlist", softAuthlist);
		request.setAttribute("magaPapelist", magaPapelist);
		request.setAttribute("hylwList", hylwList);
		request.setAttribute("pubtealist", pubtealist);
		request.setAttribute("qualifications", qualifications);
		request.setAttribute("rychList", rychList);
		return mapping.findForward("indicator");
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	public XmService getXmService() {
		return xmService;
	}
	public void setXmService(XmService xmService) {
		this.xmService = xmService;
	}
	public CgService getCgService() {
		return cgService;
	}
	public void setCgService(CgService cgService) {
		this.cgService = cgService;
	}
	public HylwService getHylwService() {
		return hylwService;
	}
	public void setHylwService(HylwService hylwService) {
		this.hylwService = hylwService;
	}
	public QklwService getQklwService() {
		return qklwService;
	}
	public void setQklwService(QklwService qklwService) {
		this.qklwService = qklwService;
	}
	public RjzzService getRjzzService() {
		return rjzzService;
	}
	public void setRjzzService(RjzzService rjzzService) {
		this.rjzzService = rjzzService;
	}
	public FmzlService getFmzlService() {
		return fmzlService;
	}
	public void setFmzlService(FmzlService fmzlService) {
		this.fmzlService = fmzlService;
	}
	public ZzService getZzService() {
		return zzService;
	}
	public void setZzService(ZzService zzService) {
		this.zzService = zzService;
	}
	public SkService getSkService() {
		return skService;
	}
	public void setSkService(SkService skService) {
		this.skService = skService;
	}
	public RychService getRychService() {
		return rychService;
	}
	public void setRychService(RychService rychService) {
		this.rychService = rychService;
	}
	public XyptTitleService getXypttitleService() {
		return xypttitleService;
	}
	public void setXypttitleService(XyptTitleService xypttitleService) {
		this.xypttitleService = xypttitleService;
	}
	public PxService getPxService() {
		return pxService;
	}
	public void setPxService(PxService pxService) {
		this.pxService = pxService;
	}
	public JslwService getJslwService() {
		return jslwService;
	}
	public void setJslwService(JslwService jslwService) {
		this.jslwService = jslwService;
	}

	public JszzService getJszzService() {
		return jszzService;
	}

	public void setJszzService(JszzService jszzService) {
		this.jszzService = jszzService;
	}

	public JspsService getJspsService() {
		return jspsService;
	}

	public void setJspsService(JspsService jspsService) {
		this.jspsService = jspsService;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
	
}
