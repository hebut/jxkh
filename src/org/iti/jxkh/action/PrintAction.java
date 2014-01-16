package org.iti.jxkh.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.iti.jxkh.entity.JXKH_HULWMember;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.JXKH_QKLWDept;
import org.iti.jxkh.entity.JXKH_QKLWMember;
import org.iti.jxkh.entity.JXKH_VoteResult;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_AwardDept;
import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_FruitDept;
import org.iti.jxkh.entity.Jxkh_FruitMember;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_PatentDept;
import org.iti.jxkh.entity.Jxkh_PatentInventor;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ProjectMember;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.entity.Jxkh_ReportMember;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.entity.Jxkh_VideoMember;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.jxkh.entity.Jxkh_Writer;
import org.iti.jxkh.entity.Jxkh_Writing;
import org.iti.jxkh.entity.Jxkh_WritingDept;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhFruitService;
import org.iti.jxkh.service.JxkhHylwService;
import org.iti.jxkh.service.JxkhProjectService;
import org.iti.jxkh.service.JxkhQklwService;
import org.iti.jxkh.service.JxkhReportService;
import org.iti.jxkh.service.JxkhVideoService;
import org.iti.jxkh.service.VoteResultService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iti.common.util.ConvertUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.uniwin.framework.entity.WkTUser;

@Controller("printAction")
@Scope("prototype")
public class PrintAction extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = -7031297946742422353L;
	private HttpServletRequest request;
	private VoteResultService voteResultService;
	private JxkhAwardService jxkhAwardService;
	private JxkhProjectService jxkhProjectService;
	private JxkhFruitService jxkhFruitService;
	private JxkhReportService jxkhReportService;
	private JxkhVideoService jxkhVideoService;
	private JXKHMeetingService jxkhMeetingService;
	private JxkhHylwService jxkhHylwService;
	private JxkhQklwService jxkhQklwService;

	public JxkhProjectService getJxkhProjectService() {
		return jxkhProjectService;
	}

	@Resource(name = "jxkhProjectService")
	public void setJxkhProjectService(JxkhProjectService jxkhProjectService) {
		this.jxkhProjectService = jxkhProjectService;
	}

	public String print() {
		System.out.println("aaaaa");
		String ret = "success";
		if ("vote".equals(request.getParameter("n"))) {
			List<JXKH_VoteResult> vrlist = voteResultService
					.findResultByYear(request.getParameter("year"));
			request.setAttribute("vrlist", vrlist);
			ret = "vote";
		} else if ("award".equals(request.getParameter("n"))) {
			request.setAttribute(
					"award",
					voteResultService.loadById(Jxkh_Award.class,
							Long.parseLong(request.getParameter("id") + "")));
			String memberName = "";
			String deptName = "";
			Long id = Long.parseLong(request.getParameter("id"));
			Jxkh_Award aw = (Jxkh_Award) voteResultService.loadById(
					Jxkh_Award.class, id);
			System.out.print(aw.getbAdvice()+"bbbbbbbbbbbbbbbbbbbbbbb~~~~~~~~~~~~~~~~~~~~``");
			System.out.print(aw.getDep1Reason()+"dddddddddddddddddd~~~~~~~~~~~~~~~~~~~~~~~~");
			List<Jxkh_AwardMember> awardMemberList = jxkhAwardService
					.findAwardMemberByAwardId(aw);
			if (awardMemberList != null && awardMemberList.size() != 0) {
				for (int i = 0; i < awardMemberList.size(); i++) {
					Jxkh_AwardMember mem = (Jxkh_AwardMember) awardMemberList
							.get(i);
					memberName += mem.getName() + "、";
				}
			}
			request.setAttribute("awardmember",
					memberName.substring(0, memberName.length() - 1));

			List<Jxkh_AwardDept> awardDeptList = jxkhAwardService
					.findAwardDeptByAwardId(aw);
			if (awardDeptList != null && awardDeptList.size() != 0) {
				for (int i = 0; i < awardDeptList.size(); i++) {
					Jxkh_AwardDept de = (Jxkh_AwardDept) awardDeptList.get(i);
					deptName += de.getName() + "、";
				}
			}
			request.setAttribute("awarddept",
					deptName.substring(0, deptName.length() - 1));
			ret = "award";
		} else if ("fruit".equals(request.getParameter("n"))) {
			request.setAttribute(
					"fruit",
					voteResultService.loadById(Jxkh_Fruit.class,
							Long.parseLong(request.getParameter("id") + "")));
			String memberName = "";
			String deptName = "";
			Long id = Long.parseLong(request.getParameter("id"));
			Jxkh_Fruit fr = (Jxkh_Fruit) voteResultService.loadById(
					Jxkh_Fruit.class, id);

			List<Jxkh_FruitMember> fruitMemberList = jxkhFruitService
					.findFruitMemberByFruitId(fr);
			if (fruitMemberList != null && fruitMemberList.size() != 0) {
				for (int i = 0; i < fruitMemberList.size(); i++) {
					Jxkh_FruitMember mem = (Jxkh_FruitMember) fruitMemberList
							.get(i);
					memberName += mem.getName() + "、";
				}
			}
			request.setAttribute("fruitmember",
					memberName.substring(0, memberName.length() - 1));

			List<Jxkh_FruitDept> fruitDeptList = jxkhFruitService
					.findFruitDeptByFruitId(fr);
			if (fruitDeptList != null && fruitDeptList.size() != 0) {
				for (int i = 0; i < fruitDeptList.size(); i++) {
					Jxkh_FruitDept de = (Jxkh_FruitDept) fruitDeptList.get(i);
					deptName += de.getName() + "、";
				}
			}
			request.setAttribute("fruitdept",
					deptName.substring(0, deptName.length() - 1));
			ret = "fruit";
		} else if ("report".equals(request.getParameter("n"))) {
			request.setAttribute(
					"report",
					voteResultService.loadById(Jxkh_Report.class,
							Long.parseLong(request.getParameter("id") + "")));
			String memberName = "";
			String deptName = "";
			Long id = Long.parseLong(request.getParameter("id"));
			Jxkh_Report re = (Jxkh_Report) voteResultService.loadById(
					Jxkh_Report.class, id);

			List<Jxkh_ReportMember> reportMemberList = jxkhReportService
					.findReportMemberByReportId(re);
			if (reportMemberList != null && reportMemberList.size() != 0) {
				for (int i = 0; i < reportMemberList.size(); i++) {
					Jxkh_ReportMember mem = (Jxkh_ReportMember) reportMemberList
							.get(i);
					memberName += mem.getName() + "、";
				}
			}
			request.setAttribute("reportmember",
					memberName.substring(0, memberName.length() - 1));

			List<Jxkh_ReportDept> reportDeptList = jxkhReportService
					.findReportDeptByReportId(re);
			if (reportDeptList != null && reportDeptList.size() != 0) {
				for (int i = 0; i < reportDeptList.size(); i++) {
					Jxkh_ReportDept de = (Jxkh_ReportDept) reportDeptList
							.get(i);
					deptName += de.getName() + "、";
				}
			}
			request.setAttribute("reportdept",
					deptName.substring(0, deptName.length() - 1));
			ret = "report";
		} else if ("video".equals(request.getParameter("n"))) {
			request.setAttribute(
					"video",
					voteResultService.loadById(Jxkh_Video.class,
							Long.parseLong(request.getParameter("id") + "")));
			String memberName = "";
			String deptName = "";
			Long id = Long.parseLong(request.getParameter("id"));
			Jxkh_Video vi = (Jxkh_Video) voteResultService.loadById(
					Jxkh_Video.class, id);

			List<Jxkh_VideoMember> videoMemberList = jxkhVideoService
					.findVideoMemberByVideoId(vi);
			if (videoMemberList != null && videoMemberList.size() != 0) {
				for (int i = 0; i < videoMemberList.size(); i++) {
					Jxkh_VideoMember mem = (Jxkh_VideoMember) videoMemberList
							.get(i);
					memberName += mem.getName() + "、";
				}
			}
			request.setAttribute("videomember",
					memberName.substring(0, memberName.length() - 1));

			List<Jxkh_VideoDept> videoDeptList = jxkhVideoService
					.findVideoDeptrByVideoId(vi);
			if (videoDeptList != null && videoDeptList.size() != 0) {
				for (int i = 0; i < videoDeptList.size(); i++) {
					Jxkh_VideoDept de = (Jxkh_VideoDept) videoDeptList.get(i);
					deptName += de.getName() + "、";
				}
			}
			request.setAttribute("videodept",
					deptName.substring(0, deptName.length() - 1));
			ret = "video";
		} else if ("writing".equals(request.getParameter("n"))) {
			request.setAttribute(
					"writing",
					voteResultService.loadById(Jxkh_Writing.class,
							Long.parseLong(request.getParameter("id") + "")));
			Long id = Long.parseLong(request.getParameter("id"));
			Jxkh_Writing wr = (Jxkh_Writing) voteResultService.loadById(
					Jxkh_Writing.class, id);
			String memberName = "";
			List<Jxkh_Writer> projectMemberList = jxkhProjectService
					.findWritingMember(wr);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				for (int i = 0; i < projectMemberList.size(); i++) {
					Jxkh_Writer mem = (Jxkh_Writer) projectMemberList.get(i);
					memberName += mem.getName() + "、";
				}
			}
			request.setAttribute("writingmember",
					memberName.substring(0, memberName.length() - 1));

			String deptName = "";
			List<Jxkh_WritingDept> projectDeptList = jxkhProjectService
					.findWritingDept(wr);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				for (int i = 0; i < projectDeptList.size(); i++) {
					Jxkh_WritingDept dept = (Jxkh_WritingDept) projectDeptList
							.get(i);
					deptName += dept.getName() + "、";
				}
			}
			request.setAttribute("writingdept",
					deptName.substring(0, deptName.length() - 1));
			if (wr.getCooState() == Jxkh_Writing.YES) {
				request.setAttribute("writingcoo", wr.getCooCompany());
			} else {
				request.setAttribute("writingcoo", "无");
			}
			if (wr.getIfPublish() == Jxkh_Writing.YES) {
				request.setAttribute("writingyn", "是");
			} else {
				request.setAttribute("writingyn", "否");
			}
			WkTUser infoWriter = jxkhProjectService
					.findWktUserByMemberUserId(wr.getInfoWriter());
			request.setAttribute("writingw", infoWriter.getKuName());
			ret = "writing";
		} else if ("journal".equals(request.getParameter("n"))) {
			request.setAttribute(
					"journal",
					voteResultService.loadById(JXKH_QKLW.class,
							Long.parseLong(request.getParameter("id") + "")));
			Long id = Long.parseLong(request.getParameter("id"));
			JXKH_QKLW wr = (JXKH_QKLW) voteResultService.loadById(
					JXKH_QKLW.class, id);
			String memberName = "";
			List<JXKH_QKLWMember> projectMemberList = jxkhQklwService
					.findAwardMemberByAwardId(wr);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				for (int i = 0; i < projectMemberList.size(); i++) {
					JXKH_QKLWMember mem = (JXKH_QKLWMember) projectMemberList
							.get(i);
					memberName += mem.getName() + "、";
				}
			}
			request.setAttribute("writingmember",
					memberName.substring(0, memberName.length() - 1));

			String deptName = "";
			List<JXKH_QKLWDept> projectDeptList = jxkhQklwService
					.findMeetingDeptByMeetingId(wr);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				for (int i = 0; i < projectDeptList.size(); i++) {
					JXKH_QKLWDept dept = (JXKH_QKLWDept) projectDeptList.get(i);
					deptName += dept.getName() + "、";
				}
			}
			request.setAttribute("writingdept",
					deptName.substring(0, deptName.length() - 1));
			if (wr.getIfUnion() == Jxkh_Writing.YES) {
				request.setAttribute("writingcoo", wr.getLwCoDep());
			} else {
				request.setAttribute("writingcoo", "无");
			}
			ret = "journal";
		} else if ("meetArtical".equals(request.getParameter("n"))) {
			request.setAttribute(
					"meetArtical",
					voteResultService.loadById(JXKH_HYLW.class,
							Long.parseLong(request.getParameter("id") + "")));
			Long id = Long.parseLong(request.getParameter("id"));
			JXKH_HYLW wr = (JXKH_HYLW) voteResultService.loadById(
					JXKH_HYLW.class, id);
			String memberName = "";
			List<JXKH_HULWMember> projectMemberList = jxkhHylwService
					.findAwardMemberByAwardId(wr);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				for (int i = 0; i < projectMemberList.size(); i++) {
					JXKH_HULWMember mem = (JXKH_HULWMember) projectMemberList
							.get(i);
					memberName += mem.getName() + "、";
				}
			}
			request.setAttribute("writingmember",
					memberName.substring(0, memberName.length() - 1));

			String deptName = "";
			List<JXKH_HYLWDept> projectDeptList = jxkhHylwService
					.findMeetingDeptByMeetingId(wr);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				for (int i = 0; i < projectDeptList.size(); i++) {
					JXKH_HYLWDept dept = (JXKH_HYLWDept) projectDeptList.get(i);
					deptName += dept.getName() + "、";
				}
			}
			request.setAttribute("writingdept",
					deptName.substring(0, deptName.length() - 1));
			if (wr.getIfUnion() == JXKH_HYLW.YES) {
				request.setAttribute("writingcoo", wr.getLwCoDep());
			} else {
				request.setAttribute("writingcoo", "无");
			}
			ret = "meetArtical";
		} else if ("meeting".equals(request.getParameter("n"))) {
			request.setAttribute(
					"meeting",
					voteResultService.loadById(JXKH_MEETING.class,
							Long.parseLong(request.getParameter("id") + "")));
			Long id = Long.parseLong(request.getParameter("id"));
			JXKH_MEETING wr = (JXKH_MEETING) voteResultService.loadById(
					JXKH_MEETING.class, id);

			String deptName = "";
			List<JXKH_MeetingDept> projectDeptList = jxkhMeetingService
					.findWritingDept(wr);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				for (int i = 0; i < projectDeptList.size(); i++) {
					JXKH_MeetingDept dept = (JXKH_MeetingDept) projectDeptList
							.get(i);
					deptName += dept.getName() + "、";
				}
			}
			request.setAttribute("meetingdept",
					deptName.substring(0, deptName.length() - 1));
			if (wr.getIfUnion() == Jxkh_Writing.YES) {
				request.setAttribute("meetingcoo", wr.getMtCoDep());
			} else {
				request.setAttribute("meetingcoo", "无");
			}
			ret = "meeting";
		} else if ("user".equals(request.getParameter("n"))) {
			request.setAttribute(
					"user",
					voteResultService.loadById(WkTUser.class,
							Long.parseLong(request.getParameter("id") + "")));
			WkTUser u = (WkTUser) voteResultService.loadById(WkTUser.class,
					Long.parseLong(request.getParameter("id")));
			String[] nation = { "-请选择-", "汉族", "蒙古族", "回族", "藏族", "维吾尔组", "苗族",
					"彝族", "壮族", "布依族", "朝鲜族", "满族", "侗族", "瑶族", "白族", "土家族",
					"哈尼族", "哈萨克族", "傣族", "黎族", "僳僳族", "佤族", "畲族", "高山族", "拉祜族",
					"水族", "东乡族", "纳西族", "景颇族", "柯尔克孜族", "土族", "达斡尔族", "仫佬族",
					"羌族", "布朗族", "撒拉族", "毛难族", "仡佬族", "锡伯族", "阿昌族", "塔吉克族",
					"普米族", "怒族", "乌孜别克族", "俄罗斯族", "鄂温克族", "德昂族", "保安族", "裕固族",
					"京族", "塔塔尔族", "独龙族", "鄂伦春族", "赫哲族", "门巴族", "基诺族", "珞巴族" };
			String[] marry = { "-请选择-", "未婚", "已婚", "离异", "再婚" };
			String[] political = { "-请选择-", "中国共产党党员", "中国共产党预备党员",
					"中国共产主义青年团团员", "中国国民党革命委员会会员", "中国民主同盟盟员", "中国民主建国会会员",
					"中国民主促进会会员", "中国农工民主党党员", "中国致公党党员", "九三学社社员",
					"台湾民主自治同盟盟员", "无党派民主人士", "群众" };
			String[] languagelist = { "-请选择-", "CET-4", "CET-6", "PETS-1",
					"PETS-2", "PETS-3", "PETS-4", "PETS-5", "TOEIC", "BEC-1",
					"BEC-2", "BEC-3" };
			String kuSex;
			if (u.getKuSex().equals(WkTUser.SEX_MAN)) {
				kuSex = "男";
			} else {
				kuSex = "女";
			}
			request.setAttribute("sex", kuSex);
			String kuNation;
			if (u.getKuNation().equals("0")) {
				kuNation = "";
			} else {
				kuNation = nation[Integer.valueOf(u.getKuNation()).intValue()];
			}
			request.setAttribute("nation", kuNation);
			String kuMarry;
			if (u.getKuMarstatus().equals("0")) {
				kuMarry = "";
			} else {
				kuMarry = marry[Integer.valueOf(u.getKuMarstatus()).intValue()];
			}
			request.setAttribute("marry", kuMarry);
			String kuPolity, inTime = "";
			if (u.getKuPolitical().equals("0")) {
				kuPolity = "";
			} else {
				kuPolity = political[Integer.valueOf(u.getKuPolitical()).intValue()];
			}
			if (u.getKuPartytime() != null || !"".equals(u.getKuPartytime())) {
				inTime = u.getKuPartytime();
			}
			request.setAttribute("polity", kuPolity);
			request.setAttribute("partyTime", inTime);
			String language;
			if (u.getLanguage().equals("0")) {
				language = "";
			} else {
				language = languagelist[Integer.valueOf(u.getLanguage())
						.intValue()];
			}
			request.setAttribute("language", language);
			String image = "";
			if (u.getKuPath() != null || !"".equals(u.getKuPath())) {
				image = u.getKuPath();
//				image = this.request.getRealPath("F:/workspace3.0/metadata/plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/jxkh"+u.getKuPath());
//				image = Executions.getCurrent().getDesktop().getWebApp().getRealPath(u.getKuPath()) ;
			}
			request.setAttribute("image", image);
			String kuUsedName = "";
			if (u.getKuUsedname() != null || !"".equals(u.getKuUsedname())) {
				kuUsedName = u.getKuUsedname();
			}
			request.setAttribute("usedname", kuUsedName);
			String birthday = "";
			if (u.getKuBirthday() != null || !"".equals(u.getKuBirthday())) {
				birthday = u.getKuBirthday();
			}
			request.setAttribute("birthday", birthday);
			String nativeplace = "";
			if (u.getKuNativeplace() != null
					|| !"".equals(u.getKuNativeplace())) {
				nativeplace = u.getKuNativeplace();
			}
			request.setAttribute("nativeplace", nativeplace);
			String staffId = "";
			if (u.getStaffId() != null || !"".equals(u.getStaffId())) {
				staffId = u.getStaffId();
			}
			request.setAttribute("staffid", staffId);
			String identy = "";
			if (u.getKuIdentity() != null || !"".equals(u.getKuIdentity())) {
				identy = u.getKuIdentity();
			}
			request.setAttribute("identy", identy);
			String kuDept = "";
			if (u.getDept() != null) {
				kuDept = u.getDept().getKdName();
			}
			request.setAttribute("dept", kuDept);
			String phone = "";
			if (u.getKuPhone() != null || !"".equals(u.getKuPhone())) {
				phone = u.getKuPhone();
			}
			request.setAttribute("phone", phone);
			String email = "";
			if (u.getKuEmail() != null || !"".equals(u.getKuEmail())) {
				email = u.getKuEmail();
			}
			request.setAttribute("email", email);
			String workPhone = "";
			if (u.getKuWorktel() != null || !"".equals(u.getKuWorktel())) {
				workPhone = u.getKuWorktel();
			}
			request.setAttribute("workphone", workPhone);
			String info = "";
			if (u.getKuIntro() != null || !"".equals(u.getKuIntro())) {
				info = u.getKuIntro();
			}
			request.setAttribute("info", info);
			ret = "user";
		} else if ("patent".equals(request.getParameter("n"))) {
			request.setAttribute(
					"patent",
					voteResultService.loadById(Jxkh_Patent.class,
							Long.parseLong(request.getParameter("id") + "")));
			Long id = Long.parseLong(request.getParameter("id"));
			Jxkh_Patent wr = (Jxkh_Patent) voteResultService.loadById(
					Jxkh_Patent.class, id);
			String memberName = "";

			List<Jxkh_PatentInventor> projectMemberList = jxkhProjectService
					.findPatentMember(wr);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				for (int i = 0; i < projectMemberList.size(); i++) {
					Jxkh_PatentInventor mem = (Jxkh_PatentInventor) projectMemberList
							.get(i);
					memberName += mem.getName() + "、";
				}
			}
			request.setAttribute("patentmember",
					memberName.substring(0, memberName.length() - 1));

			String deptName = "";

			List<Jxkh_PatentDept> projectDeptList = jxkhProjectService
					.findPatentDept(wr);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				for (int i = 0; i < projectDeptList.size(); i++) {
					Jxkh_PatentDept dept = (Jxkh_PatentDept) projectDeptList
							.get(i);
					deptName += dept.getName() + "、";
				}
			}
			request.setAttribute("patentdept",
					deptName.substring(0, deptName.length() - 1));
			if (wr.getCooState() == Jxkh_Patent.YES) {
				request.setAttribute("patentcoo", wr.getCooCompany());
			} else {
				request.setAttribute("patentcoo", "无");
			}
			WkTUser infoWriter = jxkhProjectService
					.findWktUserByMemberUserId(wr.getInfoWriter());
			request.setAttribute("patentw", infoWriter.getKuName());
			ret = "patent";
		} else if ("zp".equals(request.getParameter("n"))) {
			request.setAttribute(
					"zp",
					voteResultService.loadById(Jxkh_Project.class,
							Long.parseLong(request.getParameter("id") + "")));
			Long id = Long.parseLong(request.getParameter("id"));
			Jxkh_Project wr = (Jxkh_Project) voteResultService.loadById(
					Jxkh_Project.class, id);
			String memberName = "";

			List<Jxkh_ProjectMember> projectMemberList = jxkhProjectService
					.findProjectMember(wr);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				for (int i = 0; i < projectMemberList.size(); i++) {
					Jxkh_ProjectMember mem = (Jxkh_ProjectMember) projectMemberList
							.get(i);
					memberName += mem.getName() + "、";
				}
			}
			request.setAttribute("zpmember",
					memberName.substring(0, memberName.length() - 1));

			String deptName = "";

			List<Jxkh_ProjectDept> projectDeptList = jxkhProjectService
					.findProjectDept(wr);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				for (int i = 0; i < projectDeptList.size(); i++) {
					Jxkh_ProjectDept dept = (Jxkh_ProjectDept) projectDeptList
							.get(i);
					deptName += dept.getName() + "、";
				}
			}
			request.setAttribute("zpdept",
					deptName.substring(0, deptName.length() - 1));
			if (wr.getCooState() == Jxkh_Project.YES) {
				request.setAttribute("zpcoo", wr.getCooCompany());
			} else {
				request.setAttribute("zpcoo", "无");
			}
			WkTUser infoWriter = jxkhProjectService
					.findWktUserByMemberUserId(wr.getInfoWriter());
			request.setAttribute("zpw", infoWriter.getKuName());
			String subnum, outnum, innum;
			String year = ConvertUtil.convertDateString(new Date()).substring(
					0, 4);
			List outNum = jxkhProjectService.sumOut(wr, year);
			List inNum = jxkhProjectService.sumIn(wr, year);
			if (outNum.size() == 0 || outNum.get(0) == null) {
				outnum = "0.0";
			} else {
				outnum = outNum.get(0).toString();
			}
			if (inNum.size() == 0 || inNum.get(0) == null) {
				innum = "0.0";
			} else {
				innum = inNum.get(0).toString();
			}
			subnum = String.valueOf(Float.parseFloat(innum)
					- Float.parseFloat(outnum));
			request.setAttribute("zpfund", subnum);

			if (wr.getIfHumanities() == Jxkh_Project.YES) {
				request.setAttribute("zphum", "是");
			} else {
				request.setAttribute("zphum", "否");
			}
			if (wr.getIfSoftScience() == Jxkh_Project.YES) {
				request.setAttribute("zpsoft", "是");
			} else {
				request.setAttribute("zpsoft", "否");
			}

			ret = "zp";
		} else if ("hp".equals(request.getParameter("n"))) {
			request.setAttribute(
					"zp",
					voteResultService.loadById(Jxkh_Project.class,
							Long.parseLong(request.getParameter("id") + "")));
			Long id = Long.parseLong(request.getParameter("id"));
			Jxkh_Project wr = (Jxkh_Project) voteResultService.loadById(
					Jxkh_Project.class, id);
			String memberName = "";

			List<Jxkh_ProjectMember> projectMemberList = jxkhProjectService
					.findProjectMember(wr);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				for (int i = 0; i < projectMemberList.size(); i++) {
					Jxkh_ProjectMember mem = (Jxkh_ProjectMember) projectMemberList
							.get(i);
					memberName += mem.getName() + "、";
				}
			}
			request.setAttribute("zpmember",
					memberName.substring(0, memberName.length() - 1));

			String deptName = "";

			List<Jxkh_ProjectDept> projectDeptList = jxkhProjectService
					.findProjectDept(wr);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				for (int i = 0; i < projectDeptList.size(); i++) {
					Jxkh_ProjectDept dept = (Jxkh_ProjectDept) projectDeptList
							.get(i);
					deptName += dept.getName() + "、";
				}
			}
			request.setAttribute("zpdept",
					deptName.substring(0, deptName.length() - 1));
			if (wr.getCooState() == Jxkh_Project.YES) {
				request.setAttribute("zpcoo", wr.getCooCompany());
			} else {
				request.setAttribute("zpcoo", "无");
			}
			WkTUser infoWriter = jxkhProjectService
					.findWktUserByMemberUserId(wr.getInfoWriter());
			request.setAttribute("zpw", infoWriter.getKuName());
			String subnum, outnum, innum;
			String year = ConvertUtil.convertDateString(new Date()).substring(
					0, 4);
			List outNum = jxkhProjectService.sumOut(wr, year);
			List inNum = jxkhProjectService.sumIn(wr, year);
			if (outNum.size() == 0 || outNum.get(0) == null) {
				outnum = "0.0";
			} else {
				outnum = outNum.get(0).toString();
			}
			if (inNum.size() == 0 || inNum.get(0) == null) {
				innum = "0.0";
			} else {
				innum = inNum.get(0).toString();
			}
			subnum = String.valueOf(Float.parseFloat(innum)
					- Float.parseFloat(outnum));
			request.setAttribute("zpfund", subnum);
			if (wr.getIfEntruster() == Jxkh_Project.YES) {
				request.setAttribute("zpifentr", "是");
				request.setAttribute("zpentr", "受托方");
			} else {
				request.setAttribute("zpifentr", "否");
				request.setAttribute("zpentr", "委托方");
			}
			ret = "hp";
		} else if ("cp".equals(request.getParameter("n"))) {
			request.setAttribute(
					"zp",
					voteResultService.loadById(Jxkh_Project.class,
							Long.parseLong(request.getParameter("id") + "")));
			Long id = Long.parseLong(request.getParameter("id"));
			Jxkh_Project wr = (Jxkh_Project) voteResultService.loadById(
					Jxkh_Project.class, id);
			String memberName = "";

			List<Jxkh_ProjectMember> projectMemberList = jxkhProjectService
					.findProjectMember(wr);
			if (projectMemberList != null && projectMemberList.size() != 0) {
				for (int i = 0; i < projectMemberList.size(); i++) {
					Jxkh_ProjectMember mem = (Jxkh_ProjectMember) projectMemberList
							.get(i);
					memberName += mem.getName() + "、";
				}
			}
			request.setAttribute("zpmember",
					memberName.substring(0, memberName.length() - 1));

			String deptName = "";

			List<Jxkh_ProjectDept> projectDeptList = jxkhProjectService
					.findProjectDept(wr);
			if (projectDeptList != null && projectDeptList.size() != 0) {
				for (int i = 0; i < projectDeptList.size(); i++) {
					Jxkh_ProjectDept dept = (Jxkh_ProjectDept) projectDeptList
							.get(i);
					deptName += dept.getName() + "、";
				}
			}
			request.setAttribute("zpdept",
					deptName.substring(0, deptName.length() - 1));
			if (wr.getCooState() == Jxkh_Project.YES) {
				request.setAttribute("zpcoo", wr.getCooCompany());
			} else {
				request.setAttribute("zpcoo", "无");
			}
			WkTUser infoWriter = jxkhProjectService
					.findWktUserByMemberUserId(wr.getInfoWriter());
			request.setAttribute("zpw", infoWriter.getKuName());
			String subnum, outnum, innum;
			String year = ConvertUtil.convertDateString(new Date()).substring(
					0, 4);
			List outNum = jxkhProjectService.sumOut(wr, year);
			List inNum = jxkhProjectService.sumIn(wr, year);
			if (outNum.size() == 0 || outNum.get(0) == null) {
				outnum = "0.0";
			} else {
				outnum = outNum.get(0).toString();
			}
			if (inNum.size() == 0 || inNum.get(0) == null) {
				innum = "0.0";
			} else {
				innum = inNum.get(0).toString();
			}
			subnum = String.valueOf(Float.parseFloat(innum)
					- Float.parseFloat(outnum));
			request.setAttribute("zpfund", subnum);
			ret = "cp";
		}
		return ret;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public VoteResultService getVoteResultService() {
		return voteResultService;
	}

	@Resource(name = "voteResultService")
	public void setVoteResultService(VoteResultService voteResultService) {
		this.voteResultService = voteResultService;
	}

	public JxkhAwardService getJxkhAwardService() {
		return jxkhAwardService;
	}

	@Resource(name = "jxkhAwardService")
	public void setJxkhAwardService(JxkhAwardService jxkhAwardService) {
		this.jxkhAwardService = jxkhAwardService;
	}

	public JxkhFruitService getJxkhFruitService() {
		return jxkhFruitService;
	}

	@Resource(name = "jxkhFruitService")
	public void setJxkhFruitService(JxkhFruitService jxkhFruitService) {
		this.jxkhFruitService = jxkhFruitService;
	}

	public JxkhReportService getJxkhReportService() {
		return jxkhReportService;
	}

	@Resource(name = "jxkhReportService")
	public void setJxkhReportService(JxkhReportService jxkhReportService) {
		this.jxkhReportService = jxkhReportService;
	}

	public JxkhVideoService getJxkhVideoService() {
		return jxkhVideoService;
	}

	@Resource(name = "jxkhVideoService")
	public void setJxkhVideoService(JxkhVideoService jxkhVideoService) {
		this.jxkhVideoService = jxkhVideoService;
	}

	public JXKHMeetingService getJxkhMeetingService() {
		return jxkhMeetingService;
	}

	@Resource(name = "jxkhMeetingService")
	public void setJxkhMeetingService(JXKHMeetingService jxkhMeetingService) {
		this.jxkhMeetingService = jxkhMeetingService;
	}

	public JxkhHylwService getJxkhHylwService() {
		return jxkhHylwService;
	}

	@Resource(name = "jxkhHylwService")
	public void setJxkhHylwService(JxkhHylwService jxkhHylwService) {
		this.jxkhHylwService = jxkhHylwService;
	}

	public JxkhQklwService getJxkhQklwService() {
		return jxkhQklwService;
	}

	@Resource(name = "jxkhQklwService")
	public void setJxkhQklwService(JxkhQklwService jxkhQklwService) {
		this.jxkhQklwService = jxkhQklwService;
	}

}
