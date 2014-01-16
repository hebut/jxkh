package org.iti.jxkh.business.statistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.audit.project.CPWindow;
import org.iti.jxkh.audit.project.HPWindow;
import org.iti.jxkh.audit.project.ZPWindow;
import org.iti.jxkh.business.artical.journal.AddJournalWindow;
import org.iti.jxkh.business.artical.meetArtical.AddMeetArticalWindow;
import org.iti.jxkh.business.award.AddAwardWin;
import org.iti.jxkh.business.fruit.AddFruitWin;
import org.iti.jxkh.business.meeting.AddMeetingWindow;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.business.patent.AddPatentWindow;
import org.iti.jxkh.business.project.AddCPWindow;
import org.iti.jxkh.business.project.AddHPWindow;
import org.iti.jxkh.business.project.AddZPWindow;
import org.iti.jxkh.business.project.AdviceWindow;
import org.iti.jxkh.business.report.AddReportWin;
import org.iti.jxkh.business.video.AddVideoWin;
import org.iti.jxkh.business.writing.AddWritingWindow;
import org.iti.jxkh.entity.JXKH_HULWMember;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWFile;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MeetingFile;
import org.iti.jxkh.entity.JXKH_MeetingMember;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.JXKH_QKLWFile;
import org.iti.jxkh.entity.JXKH_QKLWMember;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_AwardFile;
import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_FruitFile;
import org.iti.jxkh.entity.Jxkh_FruitMember;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_PatentInventor;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectMember;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportFile;
import org.iti.jxkh.entity.Jxkh_ReportMember;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoFile;
import org.iti.jxkh.entity.Jxkh_VideoMember;
import org.iti.jxkh.entity.Jxkh_Writer;
import org.iti.jxkh.entity.Jxkh_Writing;
import org.iti.jxkh.service.JXKHMeetingService;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.jxkh.service.JxkhFruitService;
import org.iti.jxkh.service.JxkhHylwService;
import org.iti.jxkh.service.JxkhProjectService;
import org.iti.jxkh.service.JxkhQklwService;
import org.iti.jxkh.service.JxkhReportService;
import org.iti.jxkh.service.JxkhVideoService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Tab;

import com.uniwin.framework.entity.WkTUser;

public class StatisticWin extends Window implements AfterCompose{

	/**
	 * @author WXY 
	 */
	private static final long serialVersionUID = -8670179305219591096L;
	
	private Tab zxtab,hxtab,zstab,cgtab,jltab,bgtab;//tab页
	private Tab cqtab,hytab,ystab,qktab,hylwtab,zztab;
	
	WkTUser user;
	private YearListbox yearlist;
	private Listbox auditState;
	private Listbox zxListbox,hxListbox,zsListbox,fruitListbox,awardListbox,reportListbox;
	private Listbox cqListbox,meetingListbox,videoListbox,journalListbox,hylwListbox,zzListbox;
	private JxkhProjectService jxkhProjectService;
	private int state;//查询区分位
	private String nameSearch,yearSearch;
	private Integer auditStateSearch;
	
	private List<Jxkh_Fruit> fruitList = new ArrayList<Jxkh_Fruit>();//鉴定成果
	private Set<Jxkh_FruitFile> filesListFruit;
	private JxkhFruitService jxkhFruitService;
	
	private JxkhAwardService jxkhAwardService;//科技奖励
	private List<Jxkh_Award> awardList = new ArrayList<Jxkh_Award>();
	private Set<Jxkh_AwardFile> filesListAward;
	
	private JxkhReportService jxkhReportService;//报告
	private List<Jxkh_Report> reportList = new ArrayList<Jxkh_Report>();
	private Set<Jxkh_ReportFile> filesListReport;
	
	private Paging meetingPaging;//学术会议
	private JXKHMeetingService jxkhMeetingService;
	private List<JXKH_MEETING> meetingList = new ArrayList<JXKH_MEETING>();
	private Set<JXKH_MeetingFile> filesListMeeting;
	
	private JxkhVideoService jxkhVideoService;//影视专题片
	private List<Jxkh_Video> videoList = new ArrayList<Jxkh_Video>();
	private Set<Jxkh_VideoFile> filesListVideo;
	
	private Paging qklwwPaging;//期刊论文
	private JxkhQklwService jxkhQklwService;
	private List<JXKH_QKLW> qklwList = new ArrayList<JXKH_QKLW>();
	private List<JXKH_QKLW> meetingmList = new ArrayList<JXKH_QKLW>();
	private Set<JXKH_QKLWFile> filesListQKLW;
	
	private Paging hylwPaging;//会议论文
	private JxkhHylwService jxkhHylwService;
	private List<JXKH_HYLW> hylwList = new ArrayList<JXKH_HYLW>();
	private Set<JXKH_HYLWFile> filesListHYLW;
	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// 获取当前登录用户
		yearlist.initYearListbox("");//时间列表渲染
		String[] s = { "-请选择-", "填写中","待审核","部门审核中", "部门通过", "部门不通过","业务办暂缓通过", "业务办通过", "业务办不通过",
		"归档" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));//审核状态
		auditState.setSelectedIndex(0);
		
		zxtab.addEventListener(Events.ON_CLICK, new EventListener() {//纵向项目
			public void onEvent(Event arg0) throws Exception {
				List<Jxkh_Project> zProjectList = jxkhProjectService.findZPBymemberId(user.getKuLid());
				zxListbox.setItemRenderer(new ZProjectRenderer());
				zxListbox.setModel(new ListModelList(zProjectList));
				state=1;
			}
		});
		hxtab.addEventListener(Events.ON_CLICK, new EventListener() {//横向向项目
			public void onEvent(Event arg0) throws Exception {
				List<Jxkh_Project> hProjectList = jxkhProjectService.findHPBymemberId(user.getKuLid());
				hxListbox.setItemRenderer(new HProjectRenderer());
				hxListbox.setModel(new ListModelList(hProjectList));
				state=2;
			}
		});
		zstab.addEventListener(Events.ON_CLICK, new EventListener() {//院内自设项目
			public void onEvent(Event arg0) throws Exception {
				List<Jxkh_Project> cProjectList = jxkhProjectService.findCPBymemberId(user.getKuLid());
				zsListbox.setItemRenderer(new CProjectRenderer());
				zsListbox.setModel(new ListModelList(cProjectList));
				state=3;
			}
		});
		cgtab.addEventListener(Events.ON_CLICK, new EventListener() {//鉴定成果
			public void onEvent(Event arg0) throws Exception {
				fruitListbox.setItemRenderer(new FruitRenderer());
				fruitList = jxkhFruitService.findFruitByKuLid("", null, null,user.getKuLid());
				fruitListbox.setModel(new ListModelList(fruitList));
				state=4;
			}
		});
		jltab.addEventListener(Events.ON_CLICK, new EventListener() {//科技奖励
			public void onEvent(Event arg0) throws Exception {
				awardListbox.setItemRenderer(new AwardRenderer() );
				awardList = jxkhAwardService.findAwardByKuLid("", null, null,user.getKuLid());
				awardListbox.setModel(new ListModelList(awardList));
				state=5;
			}
		});
		bgtab.addEventListener(Events.ON_CLICK, new EventListener() {//报告
			public void onEvent(Event arg0) throws Exception {
				reportListbox.setItemRenderer(new ReportRenderer());
				reportList = jxkhReportService.findReportByKuLid(user.getKuLid());
				reportListbox.setModel(new ListModelList(reportList));
				state=6;
			}
		});
		cqtab.addEventListener(Events.ON_CLICK, new EventListener() {//知识产权
			public void onEvent(Event arg0) throws Exception {
				cqListbox.setItemRenderer(new ZscqRenderer());
				List<Jxkh_Patent> patentList = jxkhProjectService.findPatentBymemberId(user.getKuLid());
				cqListbox.setModel(new ListModelList(patentList));
				state=7;
			}
		});
		hytab.addEventListener(Events.ON_CLICK, new EventListener() {//学术会议
			public void onEvent(Event arg0) throws Exception {
				meetingListbox.setItemRenderer(new MeetingRenderer());
				meetingPaging.addEventListener("onPaging", new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						meetingList = jxkhMeetingService.findMeetingByKuLidAndPaging(
								"", null, null, user.getKuLid(),
								meetingPaging.getActivePage(),
								meetingPaging.getPageSize());
						meetingListbox.setModel(new ListModelList(meetingList));
					}
				});
				int totalNum = jxkhMeetingService.findMeetingByKuLid("", null, null,
						user.getKuLid());
				meetingPaging.setTotalSize(totalNum);
				meetingList = jxkhMeetingService.findMeetingByKuLidAndPaging("", null,
						null, user.getKuLid(), meetingPaging.getActivePage(),
						meetingPaging.getPageSize());
				meetingListbox.setModel(new ListModelList(meetingList));
				state=8;
			}
		});
		ystab.addEventListener(Events.ON_CLICK, new EventListener() {//影视专题片
			public void onEvent(Event arg0) throws Exception {
				videoListbox.setItemRenderer(new VideoRenderer());
				videoList = jxkhVideoService.findVideoByKuLid("", null, null,user.getKuLid());
				videoListbox.setModel(new ListModelList(videoList));
				state=9;
			}
		});
		qktab.addEventListener(Events.ON_CLICK, new EventListener() {//期刊论文
			public void onEvent(Event arg0) throws Exception {
				journalListbox.setItemRenderer(new qklwRenderer());
				qklwwPaging.addEventListener("onPaging", new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						qklwList = jxkhQklwService.findMeetingByKuLidAndPaging("",null, null, user.getKuLid(),qklwwPaging.getActivePage(),qklwwPaging.getPageSize());
						journalListbox.setModel(new ListModelList(qklwList));
					}
				});
				int totalNum = jxkhQklwService.findMeetingByKuLid("", null, null,
						user.getKuLid());
				qklwwPaging.setTotalSize(totalNum);
				qklwList = jxkhQklwService.findMeetingByKuLidAndPaging("", null, null,user.getKuLid(), qklwwPaging.getActivePage(),qklwwPaging.getPageSize());
				journalListbox.setModel(new ListModelList(qklwList));
				state=10;
			}
		});
		hylwtab.addEventListener(Events.ON_CLICK, new EventListener() {//会议论文
			public void onEvent(Event arg0) throws Exception {
				hylwListbox.setItemRenderer(new HylwRenderer() );
				hylwPaging.addEventListener("onPaging", new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						hylwList = jxkhHylwService.findMeetingByKuLidAndpPaging("",
								null, null, user.getKuLid(), hylwPaging.getActivePage(),
								hylwPaging.getPageSize());
						hylwListbox.setModel(new ListModelList(hylwList));
					}
				});
				int totalNum = jxkhHylwService.findMeetingByKuLid("", null, null,
						user.getKuLid());
				hylwPaging.setTotalSize(totalNum);
				hylwList = jxkhHylwService.findMeetingByKuLidAndpPaging("", null,
						null, user.getKuLid(), hylwPaging.getActivePage(),
						hylwPaging.getPageSize());
				hylwListbox.setModel(new ListModelList(hylwList));
				state=11;
			}
		});
     	zztab.addEventListener(Events.ON_CLICK, new EventListener() {//著作
			public void onEvent(Event arg0) throws Exception {
				zzListbox.setItemRenderer(new zzRenderer());
				List<Jxkh_Writing> writingList = jxkhProjectService.findWritingBymemberId(user.getKuLid());
				zzListbox.setModel(new ListModelList(writingList));
				state=12;
			}
		});
	}

		public class ZProjectRenderer implements ListitemRenderer {//纵向项目
			public void render(Listitem item, Object data) throws Exception {
				if (data == null)
					return;
				final Jxkh_Project project = (Jxkh_Project) data;
				item.setValue(project);
				Listcell c0 = new Listcell(item.getIndex() + 1 + "");
				Listcell c1 = new Listcell(project.getName());
				c1.setStyle("color:blue");

				/*if (user.getKuLid().equals(project.getInfoWriter())) {
					if (project.getState() == Jxkh_Project.NOT_AUDIT
							|| project.getState() == Jxkh_Project.DEPT_NOT_PASS
							|| project.getState() == Jxkh_Project.BUSINESS_NOT_PASS) {

						c1.setTooltiptext("点击编辑纵向项目信息");
						c1.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {

								AddZPWindow w = (AddZPWindow) Executions
										.createComponents(
												"/admin/personal/businessdata/project/addZP.zul",
												null, null);
								try {
									w.setTitle("编辑项目信息");
									w.setProject(project);
//									w.initShow();
									w.initWindow();
									w.doModal();
								} catch (SuspendNotAllowedException e) {
									e.printStackTrace();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							//	initShow();
							}
						});

					} else {

						c1.setTooltiptext("点击查看纵向项目信息");
						c1.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {

								ZPWindow w = (ZPWindow) Executions
										.createComponents(
												"/admin/personal/businessdata/project/showZP.zul",
												null, null);
								try {
									w.setTitle("查看项目信息");
									w.setProject(project);
									w.setDept("dept");
									w.initShow();
									w.initWindow();
									w.doModal();
								} catch (SuspendNotAllowedException e) {
									e.printStackTrace();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							//	initShow();
							}
						});
					}
				} else {

					c1.setTooltiptext("点击查看纵向项目信息");
					c1.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							ZPWindow w = (ZPWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/project/showZP.zul",
											null, null);
							try {
								w.setTitle("查看项目信息");
								w.setProject(project);
								w.initShow();
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						//	initShow();
						}
					});
				}*/
				Listcell c2 = new Listcell(project.getRank().getKbName());
				Listcell c3 = new Listcell(project.getjxYear());
				Listcell c4 = new Listcell(project.getBeginDate());
				Listcell c5 = new Listcell();
				Image download = new Image("/css/default/images/button/down.gif");
				download.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow win = (DownloadWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/meeting/download.zul",
										null, null);
						win.setFiles(project.getProjectFile());
						win.setFlag("zp");
						win.initWindow();
						win.doModal();
					}
				});
				c5.appendChild(download);
				Listcell c6 = new Listcell();
				if(project.getScore() != null) {
					BigDecimal bd = new BigDecimal(project.getScore());
					bd.setScale(1, RoundingMode.HALF_UP);
					c6.setLabel(Float.valueOf(bd.floatValue()).toString());
				}else {
					c6.setLabel("0");
				}
				Listcell c7 = new Listcell("");
				List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
				for (int j = 0; j < mlist.size(); j++) {
					Jxkh_ProjectMember m = mlist.get(j);
					if (user.getKuName().equals(m.getName())) {
						if (m.getScore() != null && !m.getScore().equals("")) {
							BigDecimal b = new BigDecimal(m.getScore());
							b.setScale(1, RoundingMode.HALF_UP);
							c7.setLabel(Float.valueOf(b.floatValue()).toString());
						}
					}
				}
				String strC8;
				switch (project.getState()) {
				case Jxkh_Project.NOT_AUDIT:
					strC8 = "待审核";
					break;
				case Jxkh_Project.DEPT_PASS:
					strC8 = "部门通过";
					break;
				case Jxkh_Project.First_Dept_Pass:
					strC8 = "部门审核中";
					break;
				case Jxkh_Project.DEPT_NOT_PASS:
					strC8 = "部门不通过";
					break;
				case Jxkh_Project.BUSINESS_PASS:
					strC8 = "业务办通过";
					break;
				case Jxkh_Project.BUSINESS_NOT_PASS:
					strC8 = "业务办不通过";
					break;
				case 7:
					strC8 = "业务办暂缓通过";
					break;
				case 8:
					strC8 = "填写中";
					break;
				case 9:
					strC8 = "部门审核中";
					break;
				default:
					strC8 = "归档";
					break;
				}
				Listcell c8 = new Listcell(strC8);
				c8.setStyle("color:red");
				item.appendChild(c0);
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
				item.appendChild(c8);
			}
		}
		public class HProjectRenderer implements ListitemRenderer {//横向项目
			public void render(Listitem item, Object data) throws Exception {
				if (data == null)
					return;
				final Jxkh_Project project = (Jxkh_Project) data;
				item.setValue(project);
				Listcell c0 = new Listcell(item.getIndex() + 1 + "");
				Listcell c1 = new Listcell(project.getName());
				c1.setStyle("color:blue");

			/*	if (user.getKuLid().equals(project.getInfoWriter())) {
					if (project.getState() == Jxkh_Project.NOT_AUDIT
							|| project.getState() == Jxkh_Project.DEPT_NOT_PASS
							|| project.getState() == Jxkh_Project.BUSINESS_NOT_PASS) {

						c1.setTooltiptext("点击编辑横向项目信息");
						c1.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {

								AddHPWindow w = (AddHPWindow) Executions
										.createComponents(
												"/admin/personal/businessdata/project/addHP.zul",
												null, null);
								try {
									w.setTitle("编辑横向项目信息");
									w.setProject(project);
									w.initShow();
									w.initWindow();
									w.doModal();

								} catch (SuspendNotAllowedException e) {
									e.printStackTrace();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								//initShow();
							}
						});
					} else {
						c1.setTooltiptext("点击查看横向项目信息");
						c1.addEventListener(Events.ON_CLICK, new EventListener() {
							public void onEvent(Event arg0) throws Exception {

								HPWindow w = (HPWindow) Executions
										.createComponents(
												"/admin/personal/businessdata/project/showHP.zul",
												null, null);
								try {
									w.setTitle("查看横向项目信息");
									w.setProject(project);
									w.setDept("dept");
									w.initShow();
									w.initWindow();
									w.doModal();

								} catch (SuspendNotAllowedException e) {
									e.printStackTrace();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							//	initShow();

							}
						});
					}
				} else {

					c1.setTooltiptext("点击查看横向项目信息");
					c1.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							HPWindow w = (HPWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/project/showHP.zul",
											null, null);
							try {
								w.setTitle("查看横向项目信息");
								w.setProject(project);
								w.initShow();
								w.initWindow();
								w.doModal();

							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						//	initShow();

						}
					});

				}*/

				Listcell c2 = new Listcell(project.getRank().getKbName());
				Listcell c3 = new Listcell(project.getjxYear());
				Listcell c4 = new Listcell(project.getBeginDate());
				Listcell c5 = new Listcell();
				Image download = new Image("/css/default/images/button/down.gif");
				download.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow win = (DownloadWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/meeting/download.zul",
										null, null);
						win.setFiles(project.getProjectFile());
						win.setFlag("hp");
						win.initWindow();
						win.doModal();
					}
				});
				c5.appendChild(download);
				Listcell c6 = new Listcell();
				if(project.getScore() != null) {
					BigDecimal bd = new BigDecimal(project.getScore());
					bd.setScale(1, RoundingMode.HALF_UP);
					c6.setLabel(Float.valueOf(bd.floatValue()).toString());
				}else {
					c6.setLabel("0");
				}
				Listcell c7 = new Listcell("");
				List<Jxkh_ProjectMember> mlist = jxkhProjectService.findProjectMember(project);
				for (int j = 0; j < mlist.size(); j++) {
					Jxkh_ProjectMember m = mlist.get(j);
					if (user.getKuName().equals(m.getName())) {
						if (m.getScore() != null && !m.getScore().equals("")) {
							BigDecimal bd = new BigDecimal(m.getScore());
							bd.setScale(1, RoundingMode.HALF_UP);
							c7.setLabel(Float.valueOf(bd.floatValue()).toString());						
						}
					}
				}
				String strC8;
				switch (project.getState()) {
				case Jxkh_Project.NOT_AUDIT:
					strC8 = "待审核";
					break;
				case Jxkh_Project.DEPT_PASS:
					strC8 = "部门通过";
					break;
				case Jxkh_Project.First_Dept_Pass:
					strC8 = "部门审核中";
					break;
				case Jxkh_Project.DEPT_NOT_PASS:
					strC8 = "部门不通过";
					break;
				case Jxkh_Project.BUSINESS_PASS:
					strC8 = "业务办通过";
					break;
				case Jxkh_Project.BUSINESS_NOT_PASS:
					strC8 = "业务办不通过";
					break;
				case 7:
					strC8 = "业务办暂缓通过";
					break;
				case 8:
					strC8 = "填写中";
					break;
				case 9:
					strC8 = "部门审核中";
					break;
				default:
					strC8 = "归档";
					break;
				}
				Listcell c8 = new Listcell(strC8);
				c8.setStyle("color:red");
				/*c8.setTooltiptext("点击查看审核结果");
				c8.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						AdviceWindow pa = (AdviceWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/project/advice.zul",
										null, null);
						pa.setMeeting(project);
						pa.initWindow();
						pa.doModal();
					}
				});*/
				item.appendChild(c0);
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
				item.appendChild(c8);
			}
		}
		public class CProjectRenderer implements ListitemRenderer {//院内自设项目
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Project project = (Jxkh_Project) data;
			item.setValue(project);
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(project.getName());
			c2.setStyle("color:blue");

			/*if (user.getKuLid().equals(project.getInfoWriter())) {
				if (project.getState() == Jxkh_Project.NOT_AUDIT
						|| project.getState() == Jxkh_Project.DEPT_NOT_PASS
						|| project.getState() == Jxkh_Project.BUSINESS_NOT_PASS) {

					c2.setTooltiptext("点击编辑自设项目信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							AddCPWindow w = (AddCPWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/project/addCP.zul",
											null, null);
							try {
								w.setTitle("编辑自设项目信息");
								w.setProject(project);
								w.initShow();
								w.initWindow();
								w.doModal();

							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						//	initShow();

						}
					});

				} else {

					c2.setTooltiptext("点击查看自设项目信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							CPWindow w = (CPWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/project/showCP.zul",
											null, null);
							try {
								w.setTitle("查看自设项目信息");
								w.setProject(project);
								w.setDept("dept");
								w.initShow();
								w.initWindow();
								w.doModal();

							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						//	initShow();
						}
					});
				}
			} else {

				c2.setTooltiptext("点击查看自设项目信息");
				c2.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {

						CPWindow w = (CPWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/project/showCP.zul",
										null, null);
						try {
							w.setTitle("查看项目信息");
							w.setProject(project);
							w.initShow();
							w.initWindow();
							w.doModal();

						} catch (SuspendNotAllowedException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					//	initShow();

					}
				});
			}*/
			Listcell c4 = new Listcell(project.getHeader());
			Listcell c3 = new Listcell(project.getjxYear());
			Listcell c5 = new Listcell(project.getBeginDate());
			Listcell c6 = new Listcell();
			Image download = new Image("/css/default/images/button/down.gif");
			download.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					DownloadWindow win = (DownloadWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/download.zul",
									null, null);
					win.setFiles(project.getProjectFile());
					win.setFlag("cp");
					win.initWindow();
					win.doModal();
				}
			});
			c6.appendChild(download);
			String strC8;
			switch (project.getState()) {
			case Jxkh_Project.NOT_AUDIT:
				strC8 = "待审核";
				break;
			case Jxkh_Project.DEPT_PASS:
				strC8 = "部门通过";
				break;
			case Jxkh_Project.First_Dept_Pass:
				strC8 = "部门审核中";
				break;
			case Jxkh_Project.DEPT_NOT_PASS:
				strC8 = "部门不通过";
				break;
			case Jxkh_Project.BUSINESS_PASS:
				strC8 = "业务办通过";
				break;
			case Jxkh_Project.BUSINESS_NOT_PASS:
				strC8 = "业务办不通过";
				break;
			case 7:
				strC8 = "业务办暂缓通过";
				break;
			case 8:
				strC8 = "填写中";
				break;
			case 9:
				strC8 = "部门审核中";
				break;
			default:
				strC8 = "归档";
				break;
			}
			Listcell c8 = new Listcell(strC8);
			c8.setStyle("color:red");
			/*c8.setTooltiptext("点击查看审核结果");
			c8.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					AdviceWindow pa = (AdviceWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/project/advice.zul",
									null, null);
					pa.setMeeting(project);
					pa.initWindow();
					pa.doModal();
				}
			});*/
			item.appendChild(c1);
			item.appendChild(c2);
			item.appendChild(c4);
			item.appendChild(c3);
			item.appendChild(c5);
			item.appendChild(c6);
			item.appendChild(c8);
		}
	}
		public class FruitRenderer implements ListitemRenderer {// 成果列表渲染器
			public void render(Listitem item, Object data) throws Exception {
				if (data == null)
					return;
				final Jxkh_Fruit fruit = (Jxkh_Fruit) data;
				item.setValue(fruit);
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(fruit.getName());
				c2.setStyle("color:blue");
				/*if (user.getKuLid().equals(fruit.getSubmitId())) {
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event event) throws Exception {
							Listitem item = (Listitem) event.getTarget()
									.getParent();
							Jxkh_Fruit fruit = (Jxkh_Fruit) item.getValue();
							if (fruit.getState() == JXKH_MEETING.WRITING || fruit.getState() == Jxkh_Fruit.NOT_AUDIT
									|| fruit.getState() == Jxkh_Fruit.DEPT_NOT_PASS
									|| fruit.getState() == Jxkh_Fruit.BUSINESS_NOT_PASS) {

							} else {
								Messagebox.show(
										"部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示",
										Messagebox.OK, Messagebox.ERROR);
							}
							AddFruitWin w = (AddFruitWin) Executions
									.createComponents(
											"/admin/personal/businessdata/fruit/addfruit.zul",
											null, null);
							try {
								w.setFruit(fruit);
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						//	initWindow();
						}
					});
				} else {
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event event) throws Exception {
							Listitem item = (Listitem) event.getTarget()
									.getParent();
							Jxkh_Fruit fruit = (Jxkh_Fruit) item.getValue();
							AddFruitWin w = (AddFruitWin) Executions
									.createComponents(
											"/admin/personal/businessdata/fruit/addfruit.zul",
											null, null);
							try {
								w.setFruit(fruit);
								w.setDetail("Detail");
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						//	initWindow();
						}
					});
				}*/
				Listcell c3 = new Listcell();
				if (fruit.getAppraiseRank() != null)
					c3 = new Listcell(fruit.getAppraiseRank().getKbName());
				Listcell c4 = new Listcell(fruit.getjxYear());

				Listcell c5 = new Listcell();
				c5.setTooltiptext("下载文档");
				Toolbarbutton downlowd = new Toolbarbutton();
				downlowd.setImage("/css/default/images/button/down.gif");
				downlowd.setParent(c5);
				downlowd.setHeight("20px");
				downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow win = (DownloadWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/meeting/download.zul",
										null, null);

						filesListFruit = fruit.getFruitFile();
						win.setFiles(filesListFruit);
						win.setFlag("FRUIT");
						win.initWindow();
						win.doModal();
					}
				});
				Listcell c6 = new Listcell(fruit.getScore() == null ? "0" : fruit
						.getScore().toString());
				Listcell c7 = new Listcell("");
				List<Jxkh_FruitMember> mlist = fruit.getFruitMember();
				for (int j = 0; j < mlist.size(); j++) {
					Jxkh_FruitMember m = mlist.get(j);
					if (user.getKuName().equals(m.getName())) {
						if (m.getScore() != null && !m.getScore().equals("")) {
							c7.setLabel(m.getScore() + "");
						}
					}
				}

				Listcell c8 = new Listcell();
				if (fruit.getState() == null || fruit.getState() == 0) {
					c8.setLabel("待审核");
					c8.setStyle("color:red");
				} else {
					switch (fruit.getState()) {
					case 0:
						break;
					case 1:
						c8.setLabel("部门通过");
						c8.setStyle("color:red");
						break;
					case 2:
						c8.setLabel("部门审核中");
						c8.setStyle("color:red");
						break;
					case 3:
						c8.setLabel("部门不通过");
						c8.setStyle("color:red");
						break;
					case 4:
						c8.setLabel("业务办通过");
						c8.setStyle("color:red");
						break;
					case 5:
						c8.setLabel("业务办不通过");
						c8.setStyle("color:red");
						break;
					case 6:
						c8.setLabel("归档");
						c8.setStyle("color:red");
						break;
					case 7:
						c8.setLabel("业务办暂缓通过");
						c8.setStyle("color:red");
						break;
					case 8:
						c8.setLabel("填写中");
						c8.setStyle("color:red");
						break;
					case 9:
						c8.setLabel("部门审核中");
						c8.setStyle("color:red");
						break;
					}
				}
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
				item.appendChild(c8);
			}
		}
		public class AwardRenderer implements ListitemRenderer {// 奖励列表渲染器
			@Override
			public void render(Listitem item, Object data) throws Exception {
				if (data == null)
					return;
				final Jxkh_Award award = (Jxkh_Award) data;
				item.setValue(award);
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(award.getName());
				c2.setStyle("color:blue");
				/*if (user.getKuLid().equals(award.getSubmitId())) {
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event event) throws Exception {
							Listitem item = (Listitem) event.getTarget()
									.getParent();
							Jxkh_Award award = (Jxkh_Award) item.getValue();
							if (award.getState() == JXKH_MEETING.WRITING || award.getState() == Jxkh_Award.NOT_AUDIT
									|| award.getState() == Jxkh_Award.DEPT_NOT_PASS
									|| award.getState() == Jxkh_Award.BUSINESS_NOT_PASS) {
							} else {

								Messagebox.show(
										"部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示",
										Messagebox.OK, Messagebox.ERROR);
							}
							AddAwardWin w = (AddAwardWin) Executions
									.createComponents(
											"/admin/personal/businessdata/award/addaward.zul",
											null, null);
							try {
								w.setAward(award);
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//initWindow();
						}
					});

				} else {
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event event) throws Exception {
							Listitem item = (Listitem) event.getTarget()
									.getParent();
							Jxkh_Award award = (Jxkh_Award) item.getValue();
							AddAwardWin w = (AddAwardWin) Executions
									.createComponents(
											"/admin/personal/businessdata/award/addaward.zul",
											null, null);
							try {
								w.setAward(award);
								w.setDetail("Detail");
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
				}*/
				Listcell c3 = new Listcell();
				if (award.getRank() != null) {
					c3 = new Listcell(award.getRank().getKbName());
				} else {
					c3 = new Listcell("");
				}
				Listcell c4 = new Listcell(award.getjxYear());

				Listcell c5 = new Listcell();
				c5.setTooltiptext("下载文档");
				Toolbarbutton downlowd = new Toolbarbutton();
				downlowd.setImage("/css/default/images/button/down.gif");
				downlowd.setParent(c5);
				downlowd.setHeight("20px");
				downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow win = (DownloadWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/meeting/download.zul",
										null, null);

						filesListAward = award.getAwardFile();
						win.setFiles(filesListAward);
						win.setFlag("AWARD");
						win.initWindow();
						win.doModal();
					}
				});
				Listcell c6 = new Listcell(award.getScore() == null ? "" : award
						.getScore().toString());
				Listcell c7 = new Listcell("");
				List<Jxkh_AwardMember> mlist = award.getAwardMember();
				for (int j = 0; j < mlist.size(); j++) {
					Jxkh_AwardMember m = mlist.get(j);
					if (user.getKuName().equals(m.getName())) {
						if (m.getScore() != null && !m.getScore().equals("")) {
							c7.setLabel(m.getScore() + "");
						}
					}
				}
				Listcell c8 = new Listcell();
				if (award.getState() == null || award.getState() == 0) {
					c8.setLabel("待审核");
					c8.setStyle("color:red");
				} else {
					switch (award.getState()) {
					case 0:
						break;
					case 1:
						c8.setLabel("部门通过");
						c8.setStyle("color:red");
						break;
					case 2:
						c8.setLabel("部门审核中");
						c8.setStyle("color:red");
						break;
					case 3:
						c8.setLabel("部门不通过");
						c8.setStyle("color:red");
						break;
					case 4:
						c8.setLabel("业务办通过");
						c8.setStyle("color:red");
						break;
					case 5:
						c8.setLabel("业务办不通过");
						c8.setStyle("color:red");
						break;
					case 6:
						c8.setLabel("归档");
						c8.setStyle("color:red");
						break;
					case 7:
						c8.setLabel("业务办暂缓通过");
						c8.setStyle("color:red");
						break;
					case 8:
						c8.setLabel("填写中");
						c8.setStyle("color:red");
						break;
					case 9:
						c8.setLabel("部门审核中");
						c8.setStyle("color:red");
						break;
					}
				}
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
				item.appendChild(c8);
			}
		}
		public class ReportRenderer implements ListitemRenderer {//报告列表渲染器

			@Override
			public void render(Listitem item, Object data) throws Exception {
				if (data == null)
					return;
				final Jxkh_Report report = (Jxkh_Report) data;
				item.setValue(report);
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(report.getName());
				c2.setStyle("color:blue");
			/*	if (user.getKuLid().equals(report.getSubmitId())) {
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event event) throws Exception {
							Listitem item = (Listitem) event.getTarget()
									.getParent();
							Jxkh_Report report = (Jxkh_Report) item.getValue();
							if (report.getState() == Jxkh_Report.NOT_AUDIT
									|| report.getState() == Jxkh_Report.DEPT_NOT_PASS || report.getState() == JXKH_MEETING.WRITING 
									|| report.getState() == Jxkh_Report.BUSINESS_NOT_PASS) {

							} else {
								Messagebox.show(
										"部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示",
										Messagebox.OK, Messagebox.ERROR);
							}
							AddReportWin w = (AddReportWin) Executions
									.createComponents(
											"/admin/personal/businessdata/report/addreport.zul",
											null, null);
							try {
								w.setReport(report);
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						//	initWindow();
						}
					});

				} else {
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event event) throws Exception {
							Listitem item = (Listitem) event.getTarget()
									.getParent();
							Jxkh_Report report = (Jxkh_Report) item.getValue();
							AddReportWin w = (AddReportWin) Executions
									.createComponents(
											"/admin/personal/businessdata/report/addreport.zul",
											null, null);
							try {
								w.setReport(report);
								w.setDetail("Detail");
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//initWindow();
						}
					});

				}*/
				Listcell c3 = new Listcell(report.getType());
				Listcell c4 = new Listcell(report.getjxYear());
				Listcell c5 = new Listcell();
				c5.setTooltiptext("下载文档");
				Toolbarbutton downlowd = new Toolbarbutton();
				downlowd.setImage("/css/default/images/button/down.gif");
				downlowd.setParent(c5);
				downlowd.setHeight("20px");
				downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow win = (DownloadWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/meeting/download.zul",
										null, null);

						filesListReport = report.getReportFile();
						win.setFiles(filesListReport);
						win.setFlag("REPORT");
						win.initWindow();
						win.doModal();
					}
				});
				Listcell c6 = new Listcell(report.getScore() == null ? "" : report
						.getScore().toString());
				Listcell c7 = new Listcell("");
				List<Jxkh_ReportMember> mlist = report.getReportMember();
				for (int j = 0; j < mlist.size(); j++) {
					Jxkh_ReportMember m = mlist.get(j);
					if (user.getKuName().equals(m.getName())) {
						if (m.getScore() != null && !m.getScore().equals("")) {
							c7.setLabel(m.getScore() + "");
						}
					}
				}
				Listcell c8 = new Listcell();
				if (report.getState() == null || report.getState() == 0) {
					c8.setLabel("待审核");
					c8.setStyle("color:red");
				} else {
					switch (report.getState()) {
					case 0:
						c8.setLabel("未审核");
						c8.setStyle("color:red");
						break;
					case 1:
						c8.setLabel("部门通过");
						c8.setStyle("color:red");
						break;
					case 2:
						c8.setLabel("部门审核中");
						c8.setStyle("color:red");
						break;
					case 3:
						c8.setLabel("部门不通过");
						c8.setStyle("color:red");
						break;
					case 4:
						c8.setLabel("业务办通过");
						c8.setStyle("color:red");
						break;
					case 5:
						c8.setLabel("业务办不通过");
						c8.setStyle("color:red");
						break;
					case 6:
						c8.setLabel("归档");
						c8.setStyle("color:red");
						break;
					case 7:
						c8.setLabel("业务办暂缓通过");
						c8.setStyle("color:red");
						break;
					case 8:
						c8.setLabel("填写中");
						c8.setStyle("color:red");
						break;
					case 9:
						c8.setLabel("部门审核中");
						c8.setStyle("color:red");
						break;
					}
				}
			
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
				item.appendChild(c8);
			}
		}
		public class ZscqRenderer implements ListitemRenderer {//知识产权列表渲染器
			public void render(Listitem item, Object data) throws Exception {
				if (data == null)
					return;
				final Jxkh_Patent project = (Jxkh_Patent) data;
				item.setValue(project);
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(project.getName());
				c2.setStyle("color:blue");
				/*if (user.getKuLid().equals(project.getInfoWriter())) {
					c2.setTooltiptext("点击编辑专利(软件)信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							if (project.getState() == JXKH_MEETING.WRITING || project.getState() == Jxkh_Patent.NOT_AUDIT
									|| project.getState() == Jxkh_Patent.DEPT_NOT_PASS
									|| project.getState() == Jxkh_Patent.BUSINESS_NOT_PASS) {
							} else {
								Messagebox.show(
										"部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示",
										Messagebox.OK, Messagebox.ERROR);
							}
							AddPatentWindow w = (AddPatentWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/patent/addPatent.zul",
											null, null);
							try {
								w.setTitle("编辑专利(软件)信息");
								w.setProject(project);
								if (project.getState() == Jxkh_Patent.First_Dept_Pass
										|| project.getState() == Jxkh_Patent.BUSINESS_PASS
										|| project.getState() == Jxkh_Patent.DEPT_PASS
										|| project.getState() == Jxkh_Patent.SAVE_RECORD)
									w.setDetail("Detail");
								w.initShow();
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//initShow();

						}
					});

				} else {
					c2.setTooltiptext("点击查看专利(软件)信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {

							AddPatentWindow w = (AddPatentWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/patent/addPatent.zul",
											null, null);
							try {
								w.setTitle("查看专利(软件)信息");
								w.setProject(project);
								w.setDetail("Detail");
								w.initShow();
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//initShow();
						}
					});

				}*/

				Listcell c3 = new Listcell(project.getSort().getKbName());
				Listcell c4 = new Listcell(project.getjxYear());

				Listcell c5 = new Listcell();
				Image download = new Image("/css/default/images/button/down.gif");
				download.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow win = (DownloadWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/meeting/download.zul",
										null, null);
						win.setFiles(project.getPatentFile());
						win.setFlag("patent");
						win.initWindow();
						win.doModal();
					}
				});
				c5.appendChild(download);
				Listcell c6 = new Listcell(project.getScore() == null ? ""
						: project.getScore().toString());
				Listcell c7 = new Listcell("");
				List<Jxkh_PatentInventor> mlist = jxkhProjectService.findPatentMember(project);
				for (int j = 0; j < mlist.size(); j++) {
					Jxkh_PatentInventor m = mlist.get(j);
					if (user.getKuName().equals(m.getName())) {
						if (m.getScore() != null && !m.getScore().equals("")) {
							c7.setLabel(m.getScore() + "");
						}
					}
				}
				String strC8 = "";
				switch (project.getState()) {
				case Jxkh_Patent.NOT_AUDIT:
					strC8 = "待审核";
					break;
				case Jxkh_Patent.DEPT_PASS:
					strC8 = "部门通过";
					break;
				case Jxkh_Patent.First_Dept_Pass:
					strC8 = "部门审核中";
					break;
				case Jxkh_Patent.DEPT_NOT_PASS:
					strC8 = "部门不通过";
					break;
				case Jxkh_Patent.BUSINESS_PASS:
					strC8 = "业务办通过";
					break;
				case Jxkh_Patent.BUSINESS_NOT_PASS:
					strC8 = "业务办不通过";
					break;
				case JXKH_MEETING.SAVE_RECORD:
					strC8 = "归档";
					break;	
				case 7:
					strC8 = "业务办暂缓通过";
					break;
				case 8:
					strC8 = "填写中";
					break;
				case 9:
					strC8 = "部门审核中";
					break;
				}
				Listcell c8 = new Listcell(strC8);
				c8.setStyle("color:red");
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
				item.appendChild(c8);
			}
		}
		public class MeetingRenderer implements ListitemRenderer {//学术会议列表渲染器

			public void render(Listitem item, Object data) throws Exception {
				if (data == null)
					return;
				final JXKH_MEETING meeting = (JXKH_MEETING) data;
				item.setValue(meeting);
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(meeting.getMtName());
				c2.setStyle("color:blue");
				//c2.addEventListener(Events.ON_CLICK, new EditListenerxshy());
				Listcell c3 = new Listcell("");
				if (meeting.getMtDegree() == null) {
					c3.setLabel("");
				} else {
					c3.setLabel(meeting.getMtDegree().getKbName());
				}
				Listcell c4 = new Listcell(meeting.getjxYear());
				Listcell c5 = new Listcell();
				c5.setTooltiptext("下载文档");
				Toolbarbutton downlowd = new Toolbarbutton();
				downlowd.setImage("/css/default/images/button/down.gif");
				downlowd.setParent(c5);
				downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow win = (DownloadWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/meeting/download.zul",
										null, null);

						filesListMeeting = jxkhMeetingService
								.findMeetingFilesByMeetingId(meeting);
						win.setFiles(filesListMeeting);
						win.setFlag("M");
						win.initWindow();
						win.doModal();
					}
				});
				Listcell c6 = new Listcell(meeting.getScore() == null ? ""
						: meeting.getScore().toString());

				Listcell c7 = new Listcell("");
				List<JXKH_MeetingMember> mlist = jxkhMeetingService
						.findMeetingMemberByMeetingId(meeting);
				for (int j = 0; j < mlist.size(); j++) {
					JXKH_MeetingMember m = mlist.get(j);
					if (user.getKuName().equals(m.getName())) {
						if (m.getScore() != null && !m.getScore().equals("")) {
							c7.setLabel(m.getScore() + "");
						}
					}
				}

				Listcell c8 = new Listcell();
				if (meeting.getMtState() == null) {
					c8.setLabel("待审核");
					c8.setStyle("color:red");
				} else {
					switch (meeting.getMtState()) {
					case 0:
						c8.setLabel("待审核");
						c8.setStyle("color:red");
						break;
					case 1:
						c8.setLabel("部门通过");
						c8.setStyle("color:red");
						break;
					case 2:
						c8.setLabel("部门审核中");
						c8.setStyle("color:red");
						break;
					case 3:
						c8.setLabel("部门不通过");
						c8.setStyle("color:red");
						break;
					case 4:
						c8.setLabel("业务办通过");
						c8.setStyle("color:red");
						break;
					case 5:
						c8.setLabel("业务办不通过");
						c8.setStyle("color:red");
						break;
					case 6:
						c8.setLabel("归档");
						c8.setStyle("color:red");
						break;
					case 7:
						c8.setLabel("业务办暂缓通过");
						c8.setStyle("color:red");
						break;
					case 8:
						c8.setLabel("填写中");
						c8.setStyle("color:red");
						break;
					case 9:
						c8.setLabel("部门审核中");
						c8.setStyle("color:red");
						break;
					}
				}
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
				item.appendChild(c8);
			}
		}
		public class VideoRenderer implements ListitemRenderer {//影视专题片列表渲染器
			public void render(Listitem item, Object data) throws Exception {
				if (data == null)
					return;
				final Jxkh_Video video = (Jxkh_Video) data;
				item.setValue(video);
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(video.getName());
				c2.setStyle("color:blue");
				/*if (user.getKuLid().equals(video.getSubmitId())) {
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event event) throws Exception {
							Listitem item = (Listitem) event.getTarget()
									.getParent();
							Jxkh_Video video = (Jxkh_Video) item.getValue();
							if (video.getState() == Jxkh_Video.NOT_AUDIT
									|| video.getState() == Jxkh_Video.DEPT_NOT_PASS || video.getState() == JXKH_MEETING.WRITING
									|| video.getState() == Jxkh_Video.BUSINESS_NOT_PASS) {
							} else {
								Messagebox.show(
										"部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示",
										Messagebox.OK, Messagebox.ERROR);
							}
							AddVideoWin w = (AddVideoWin) Executions
									.createComponents(
											"/admin/personal/businessdata/video/addvideo.zul",
											null, null);
							try {
								w.setVideo(video);
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						//	initWindow();
						}
					});

				} else {
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event event) throws Exception {
							Listitem item = (Listitem) event.getTarget()
									.getParent();
							Jxkh_Video video = (Jxkh_Video) item.getValue();
							AddVideoWin w = (AddVideoWin) Executions
									.createComponents(
											"/admin/personal/businessdata/video/addvideo.zul",
											null, null);
							try {
								w.setVideo(video);
								w.setDetail("Detail");
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						//	initWindow();
						}
					});
				}*/
				Listcell c3 = new Listcell(video.getType());
				Listcell c4 = new Listcell();
				if (video.getjxYear() != null) {
					c4 = new Listcell(video.getjxYear());
				} else {
					c4 = new Listcell("");
				}
				Listcell c5 = new Listcell();
				c5.setTooltiptext("附件");
				Toolbarbutton downlowd = new Toolbarbutton();
				downlowd.setImage("/css/default/images/button/down.gif");
				downlowd.setParent(c5);
				downlowd.setHeight("20px");
				downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow win = (DownloadWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/meeting/download.zul",
										null, null);

						filesListVideo = video.getVideoFile();
						win.setFiles(filesListVideo);
						win.setFlag("VIDEO");
						win.initWindow();
						win.doModal();
					}
				});
				Listcell c6 = new Listcell(video.getScore() == null ? "" : video
						.getScore().toString());
				Listcell c8 = new Listcell("");
				List<Jxkh_VideoMember> mlist = video.getVideoMember();
				for (int j = 0; j < mlist.size(); j++) {
					Jxkh_VideoMember m = mlist.get(j);
					if (user.getKuName().equals(m.getName())) {
						if (m.getScore() != null && !m.getScore().equals("")) {
							c8.setLabel(m.getScore() + "");
						}
					}
				}
				Listcell c7 = new Listcell();
				if (video.getState() == null || video.getState() == 0) {
					c7.setLabel("待审核");
					c7.setStyle("color:red");
				} else {
					switch (video.getState()) {
					case 0:
						break;
					case 1:
						c7.setLabel("部门通过");
						c7.setStyle("color:red");
						break;
					case 2:
						c7.setLabel("部门审核中");
						c7.setStyle("color:red");
						break;
					case 3:
						c7.setLabel("部门不通过");
						c7.setStyle("color:red");
						break;
					case 4:
						c7.setLabel("业务办通过");
						c7.setStyle("color:red");
						break;
					case 5:
						c7.setLabel("业务办不通过");
						c7.setStyle("color:red");
						break;
					case 6:
						c7.setLabel("归档");
						c7.setStyle("color:red");
						break;
					case 7:
						c7.setLabel("业务办暂缓通过");
						c7.setStyle("color:red");
						break;
					case 8:
						c7.setLabel("填写中");
						c7.setStyle("color:red");
						break;
					case 9:
						c7.setLabel("部门审核中");
						c7.setStyle("color:red");
						break;
					}
				}
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c8);
				item.appendChild(c7);
			}
		}
		public class qklwRenderer implements ListitemRenderer {//期刊论文列表渲染器
			public void render(Listitem item, Object data) throws Exception {
				if (data == null)
					return;
				final JXKH_QKLW meeting = (JXKH_QKLW) data;
				item.setValue(meeting);
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(meeting.getLwName());
				c2.setStyle("color:blue");
				//c2.addEventListener(Events.ON_CLICK, new EditListenerqklw());
				Listcell c3 = new Listcell();
				if (meeting.getQkGrade() == null)
					c3.setLabel("");
				else
					c3.setLabel(meeting.getQkGrade().getKbName());
				Listcell c4 = new Listcell(meeting.getjxYear());
				Listcell c5 = new Listcell();
				c5.setTooltiptext("附件");
				Toolbarbutton downlowd = new Toolbarbutton();
				downlowd.setImage("/css/default/images/button/down.gif");
				downlowd.setParent(c5);
				downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow win = (DownloadWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/meeting/download.zul",
										null, null);
						filesListQKLW = jxkhQklwService
								.findMeetingFilesByMeetingId(meeting);
						win.setFiles(filesListQKLW);
						win.setFlag("QKLW");
						win.initWindow();
						win.doModal();
					}
				});
				Listcell c6 = new Listcell();
				if(meeting.getScore() != null) {
					BigDecimal bd = new BigDecimal(meeting.getScore().floatValue());
					c6.setLabel(Float.valueOf(bd.setScale(1, RoundingMode.HALF_UP).floatValue()).toString());
				}else {
					c6.setLabel("0");
				}
				Listcell c7 = new Listcell("");
				List<JXKH_QKLWMember> mlist = jxkhQklwService
						.findAwardMemberByAwardId(meeting);
				for (int j = 0; j < mlist.size(); j++) {
					JXKH_QKLWMember m = mlist.get(j);
					if (user.getKuName().equals(m.getName())) {
						if (m.getScore() != null && !m.getScore().equals("")) {
							BigDecimal bd = new BigDecimal(m.getScore().floatValue());
							c7.setLabel(Float.valueOf(bd.setScale(1, RoundingMode.HALF_UP).floatValue()).toString());
						}
					}
				}
				Listcell c8 = new Listcell();
				if (meeting.getLwState() == null) {
					c8.setLabel("待审核");
					c8.setStyle("color:red");
				} else {
					switch (meeting.getLwState()) {
					case 0:
						c8.setLabel("待审核");
						c8.setStyle("color:red");
						break;
					case 1:
						c8.setLabel("部门通过");
						c8.setStyle("color:red");
						break;
					case 2:
						c8.setLabel("部门审核中");
						c8.setStyle("color:red");
						break;
					case 3:
						c8.setLabel("部门不通过");
						c8.setStyle("color:red");
						break;
					case 4:
						c8.setLabel("业务办通过");
						c8.setStyle("color:red");
						break;
					case 5:
						c8.setLabel("业务办不通过");
						c8.setStyle("color:red");
						break;
					case 6:
						c8.setLabel("归档");
						c8.setStyle("color:red");
						break;
					case 7:
						c8.setLabel("业务办暂缓通过");
						c8.setStyle("color:red");
						break;
					case 8:
						c8.setLabel("填写中");
						c8.setStyle("color:red");
						break;				
					}
				}
				// 弹出审核意见事件
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
				item.appendChild(c8);
			}
		}
		public class HylwRenderer implements ListitemRenderer {//会议论文列表渲染器
			public void render(Listitem item, Object data) throws Exception {
				if (data == null)
					return;
				final JXKH_HYLW meeting = (JXKH_HYLW) data;
				item.setValue(meeting);
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(meeting.getLwName());
				c2.setStyle("color:blue");
			//	c2.addEventListener(Events.ON_CLICK, new EditListenerhylw());
				Listcell c3 = new Listcell();
				if (meeting.getLwGrade() == null) {
					c3.setLabel("");
				} else {
					c3.setLabel(meeting.getLwGrade().getKbName());
				}
				Listcell c8 = new Listcell(meeting.getjxYear());
				Listcell c4 = new Listcell();
				c4.setTooltiptext("下载文档");
				Toolbarbutton downlowd = new Toolbarbutton();
				downlowd.setImage("/css/default/images/button/down.gif");
				downlowd.setParent(c4);
				downlowd.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow win = (DownloadWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/meeting/download.zul",
										null, null);
						filesListHYLW = jxkhHylwService
								.findMeetingFilesByMeetingId(meeting);
						win.setFiles(filesListHYLW);
						win.setFlag("HYLW");
						win.initWindow();
						win.doModal();
					}
				});
				Listcell c6 = new Listcell("");
				List<JXKH_HULWMember> mlist = jxkhHylwService
						.findAwardMemberByAwardId(meeting);
				for (int j = 0; j < mlist.size(); j++) {
					JXKH_HULWMember m = mlist.get(j);
					if (user.getKuName().equals(m.getName())) {
						if (m.getScore() != null && !m.getScore().equals("")) {
							c6.setLabel(m.getScore() + "");
						}
					}
				}
				Listcell c5 = new Listcell(meeting.getScore() == null ? ""
						: meeting.getScore().toString());
				Listcell c7 = new Listcell();
				if (meeting.getLwState() == null) {
					c7.setLabel("待审核");
					c7.setStyle("color:red");
				} else {
					switch (meeting.getLwState()) {
					case 0:
						c7.setLabel("待审核");
						c7.setStyle("color:red");
						break;
					case 1:
						c7.setLabel("部门通过");
						c7.setStyle("color:red");
						break;
					case 2:
						c7.setLabel("部门审核中");
						c7.setStyle("color:red");
						break;
					case 3:
						c7.setLabel("部门不通过");
						c7.setStyle("color:red");
						break;
					case 4:
						c7.setLabel("业务办通过");
						c7.setStyle("color:red");
						break;
					case 5:
						c7.setLabel("业务办不通过");
						c7.setStyle("color:red");
						break;
					case 6:
						c7.setLabel("归档");
						c7.setStyle("color:red");
						break;
					case 7:
						c7.setLabel("业务办暂缓通过");
						c7.setStyle("color:red");
						break;
					case 8:
						c7.setLabel("填写中");
						c7.setStyle("color:red");
						break;
					case 9:
						c8.setLabel("部门审核中");
						c8.setStyle("color:red");
						break;
					}
				}
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c8);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
			}
		}
		public class zzRenderer implements ListitemRenderer {//著作列表渲染器
			@Override
			public void render(Listitem item, Object data) throws Exception {
				if (data == null)
					return;
				final Jxkh_Writing project = (Jxkh_Writing) data;
				item.setValue(project);
				Listcell c1 = new Listcell(item.getIndex() + 1 + "");
				Listcell c2 = new Listcell(project.getName());
				c2.setStyle("color:blue");
				/*if (user.getKuLid().equals(project.getInfoWriter())) {
					c2.setTooltiptext("点击编辑著作信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							if (project.getState() == Jxkh_Writing.NOT_AUDIT
									|| project.getState() == Jxkh_Writing.DEPT_NOT_PASS || project.getState() == JXKH_MEETING.WRITING
									|| project.getState() == Jxkh_Writing.BUSINESS_NOT_PASS) {
							} else {
								Messagebox.show(
										"部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示",
										Messagebox.OK, Messagebox.ERROR);
							}
							AddWritingWindow w = (AddWritingWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/writing/addWriting.zul",
											null, null);
							try {
								w.setTitle("编辑著作信息");
								w.setProject(project);
								if (project.getState() == Jxkh_Patent.First_Dept_Pass
										|| project.getState() == Jxkh_Patent.BUSINESS_PASS
										|| project.getState() == Jxkh_Patent.DEPT_PASS || project.getState()== JXKH_MEETING.BUSINESS_TEMP_PASS
										|| project.getState() == Jxkh_Patent.SAVE_RECORD)
									w.setDetail("Detail");
								w.initShow();
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//initShow();
						}
					});

				} else {
					c2.setTooltiptext("点击查看著作信息");
					c2.addEventListener(Events.ON_CLICK, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							AddWritingWindow w = (AddWritingWindow) Executions
									.createComponents(
											"/admin/personal/businessdata/writing/addWriting.zul",
											null, null);
							try {
								w.setTitle("查看著作信息");
								w.setProject(project);
								w.setDetail("Detail");
								w.initShow();
								w.initWindow();
								w.doModal();
							} catch (SuspendNotAllowedException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//initShow();
						}
					});
				}*/
				Listcell c3 = new Listcell(project.getSort().getKbName());
				Listcell c4 = new Listcell(project.getjxYear());
				Listcell c5 = new Listcell();
				Image download = new Image("/css/default/images/button/down.gif");
				download.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						DownloadWindow win = (DownloadWindow) Executions
								.createComponents(
										"/admin/personal/businessdata/meeting/download.zul",
										null, null);
						win.setFiles(project.getWritingFile());
						win.setFlag("writing");
						win.initWindow();
						win.doModal();
					}
				});
				c5.appendChild(download);
				Listcell c6 = new Listcell(project.getScore() == null ? "0"
						: project.getScore().toString());
				Listcell c7 = new Listcell("");
				List<Jxkh_Writer> mlist = jxkhProjectService.findWritingMember(project);
				for (int j = 0; j < mlist.size(); j++) {
					Jxkh_Writer m = mlist.get(j);
					if (user.getKuName().equals(m.getName())) {
						if (m.getScore() != null && !m.getScore().equals("")) {
							c7.setLabel(m.getScore() + "");
						}
					}
				}
				String strC8;
				switch (project.getState()) {
				case Jxkh_Writing.NOT_AUDIT:
					strC8 = "待审核";
					break;
				case Jxkh_Writing.DEPT_PASS:
					strC8 = "部门通过";
					break;
				case Jxkh_Writing.First_Dept_Pass:
					strC8 = "部门审核中";
					break;
				case Jxkh_Writing.DEPT_NOT_PASS:
					strC8 = "部门不通过";
					break;
				case Jxkh_Writing.BUSINESS_PASS:
					strC8 = "业务办通过";
					break;
				case Jxkh_Writing.BUSINESS_NOT_PASS:
					strC8 = "业务办不通过";
					break;
				case 7:
					strC8 = "业务办暂缓通过";
					break;
				case 8:
					strC8 = "填写中";
					break;
				case 9:
					strC8 = "部门审核中";
					break;
				default:
					strC8 = "归档";
					break;
				}
				Listcell c8 = new Listcell(strC8);
				c8.setStyle("color:red");
				item.appendChild(c1);
				item.appendChild(c2);
				item.appendChild(c3);
				item.appendChild(c4);
				item.appendChild(c5);
				item.appendChild(c6);
				item.appendChild(c7);
				item.appendChild(c8);
			}
		}
		public class EditListenerqklw implements EventListener {//监听期刊论文名称列的单击事件
			@Override
			public void onEvent(Event event) throws Exception {
				Listitem item = (Listitem) event.getTarget().getParent();
				JXKH_QKLW meeting = (JXKH_QKLW) item.getValue();
				if (user.getKuLid().equals(meeting.getWriterId())) {
					if (meeting.getLwState() == null || meeting.getLwState() == JXKH_MEETING.WRITING || meeting.getLwState() == 0
							|| meeting.getLwState() == 3
							|| meeting.getLwState() == 5) {
					} else {
						Messagebox.show("部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示",
								Messagebox.OK, Messagebox.ERROR);
					}
					AddJournalWindow w = (AddJournalWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/artical/journal/addJournal.zul",
									null, null);
					w.setMeeting(meeting);
					w.initWindow();
					w.addEventListener(Events.ON_CHANGE, new EventListener() {
						public void onEvent(Event arg0) throws Exception {
							//initWindow();
						}
					});
					w.doModal();				
				} else {
					AddJournalWindow w = (AddJournalWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/artical/journal/addJournal.zul",
									null, null);
					w.setMeeting(meeting);
					w.setDetail("Detail");
					w.initWindow();
					w.doModal();
				}
			}
		}
		public class EditListenerxshy implements EventListener {//监听学术会议名称列的单击事件
			@Override
			public void onEvent(Event event) throws Exception {
				Listitem item = (Listitem) event.getTarget().getParent();
				JXKH_MEETING meeting = (JXKH_MEETING) item.getValue();
				if (user.getKuLid().equals(meeting.getWriterId())) {
					if (meeting.getMtState() == null || meeting.getMtState() == 0
							|| meeting.getMtState() == 3
							|| meeting.getMtState() == 5 || meeting.getMtState()== JXKH_MEETING.WRITING) {
					} else {

						Messagebox.show("部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示",
								Messagebox.OK, Messagebox.ERROR);
					}
					AddMeetingWindow w = (AddMeetingWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/addmeeting.zul",
									null, null);
					w.setMeeting(meeting);
					w.initWindow();
					w.doModal();
					//initWindow();
				} else {
					AddMeetingWindow w = (AddMeetingWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/meeting/addmeeting.zul",
									null, null);
					try {
						w.setMeeting(meeting);
						w.setDetail("Detail");
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	   	public class EditListenerhylw implements EventListener {//会议论文名称列的单击事件
			public void onEvent(Event event) throws Exception {
				Listitem item = (Listitem) event.getTarget().getParent();
				JXKH_HYLW meeting = (JXKH_HYLW) item.getValue();
				if (user.getKuLid().equals(meeting.getWriterId())) {
					if (meeting.getLwState() == null || meeting.getLwState() == JXKH_MEETING.WRITING || meeting.getLwState() == 0
							|| meeting.getLwState() == 3
							|| meeting.getLwState() == 5) {
					} else {
						Messagebox.show("部门已经审核通过或者业务办已经审核通过，您只能查看，无权再编辑 ！", "提示",
								Messagebox.OK, Messagebox.ERROR);
					}
					AddMeetArticalWindow w = (AddMeetArticalWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/artical/meetartical/addMeetarti.zul",
									null, null);
					w.setMeeting(meeting);
					w.initWindow();
					w.addEventListener(Events.ON_CHANGE, new EventListener() {

						public void onEvent(Event arg0) throws Exception {
							//initWindow();
						}
					});
					w.doModal();
				} else {
					AddMeetArticalWindow w = (AddMeetArticalWindow) Executions
							.createComponents(
									"/admin/personal/businessdata/artical/meetartical/addMeetarti.zul",
									null, null);
					try {
						w.setMeeting(meeting);
						w.setDetail("Detail");
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		
		
		public void onClick$search(){//查询按钮
			int i=state;
			nameSearch = null;
			auditStateSearch = null;
			yearSearch = null;
			if (auditState.getSelectedItem().getValue().equals("-请选择-")) {
				auditStateSearch = null;
			} else if (auditState.getSelectedItem().getValue().equals("待审核")) {
				auditStateSearch = 0;
			} else if (auditState.getSelectedItem().getValue().equals("部门通过")) {
				auditStateSearch = 1;
			} else if (auditState.getSelectedItem().getValue().equals("部门审核中")) {
				auditStateSearch = 2;
			} else if (auditState.getSelectedItem().getValue().equals("部门不通过")) {
				auditStateSearch = 3;
			} else if (auditState.getSelectedItem().getValue().equals("业务办通过")) {
				auditStateSearch = 4;
			} else if (auditState.getSelectedItem().getValue().equals("业务办不通过")) {
				auditStateSearch = 5;
			} else if (auditState.getSelectedItem().getValue().equals("归档")) {
				auditStateSearch = 6;
			}else if(auditState.getSelectedItem().getValue().equals("业务办暂缓通过")) {
				auditStateSearch = 7;
			}else if(auditState.getSelectedItem().getValue().equals("填写中")) {
				auditStateSearch = 8;
			}
			if (yearlist.getSelectedIndex() != 0 && yearlist.getSelectedItem() != null)
				yearSearch = yearlist.getSelectedItem().getValue() + "";

			if(i==1){//纵向项目
				List<Jxkh_Project> meetingList = jxkhProjectService.findZPByCondition(
						nameSearch, auditStateSearch, yearSearch, user.getKuLid());
				zxListbox.setModel(new ListModelList(meetingList));
			}else
			if(i==2){//横向项目
				List<Jxkh_Project> meetingList = jxkhProjectService.findHPByCondition(
						nameSearch, auditStateSearch, yearSearch, user.getKuLid());
				hxListbox.setModel(new ListModelList(meetingList));
			}else
			if(i==3){//院内自设项目
				List<Jxkh_Project> meetingList = jxkhProjectService.findZPByCondition(
						nameSearch, auditStateSearch, yearSearch, user.getKuLid());
				zsListbox.setModel(new ListModelList(meetingList));
			}
			else if(i==4){//鉴定成果
				List<Jxkh_Fruit> fruitList = jxkhFruitService.findFruitByKuLid(
						nameSearch, auditStateSearch, yearSearch, user.getKuLid());
				fruitListbox.setModel(new ListModelList(fruitList));
			}
			else if(i==5){//科技奖励
				List<Jxkh_Award> awardList = jxkhAwardService.findAwardByKuLid(nameSearch, auditStateSearch, yearSearch, user.getKuLid());
				awardListbox.setModel(new ListModelList(awardList));
			}
			else if(i==6){//报告
				List<Jxkh_Report> reportList = jxkhReportService.findReportListByCondition(nameSearch,auditStateSearch,yearSearch, user.getKuLid());
				reportListbox.setModel(new ListModelList(reportList));
			}
			else if(i==7){//知识产权
				List<Jxkh_Patent> patentList = jxkhProjectService.findPatentByCondition(nameSearch, auditStateSearch,yearSearch, user.getKuLid());
				cqListbox.setModel(new ListModelList(patentList));
			}
			else if(i==8){//学术会议
				meetingPaging.addEventListener("onPaging", new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						meetingList = jxkhMeetingService.findMeetingByKuLidAndPaging(
								nameSearch, auditStateSearch, yearSearch,
								user.getKuLid(), meetingPaging.getActivePage(),
								meetingPaging.getPageSize());
						meetingListbox.setModel(new ListModelList(meetingList));
					}
				});
				int totalNum = jxkhMeetingService.findMeetingByKuLid(nameSearch,
						auditStateSearch, yearSearch, user.getKuLid());
				meetingPaging.setTotalSize(totalNum);
				meetingList = jxkhMeetingService.findMeetingByKuLidAndPaging(
						nameSearch, auditStateSearch, yearSearch, user.getKuLid(),
						meetingPaging.getActivePage(), meetingPaging.getPageSize());
				meetingListbox.setModel(new ListModelList(meetingList));
			}
			else if(i==9){//影视专题片
				List<Jxkh_Video> reportList = jxkhVideoService.findVideoByKuLid(nameSearch, auditStateSearch, yearSearch, user.getKuLid());
				videoListbox.setModel(new ListModelList(reportList));
			}
			else if(i==10){//期刊论文
				qklwwPaging.addEventListener("onPaging", new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						meetingmList=jxkhQklwService.findQklwByKuLidAndPaging(nameSearch, auditStateSearch, yearSearch, user.getKuLid(), qklwwPaging.getActivePage(),
								qklwwPaging.getPageSize());
						journalListbox.setModel(new ListModelList(meetingmList));
					}
				});
				int totalNum = jxkhQklwService.findQklwByKuLid(nameSearch, auditStateSearch, yearSearch, user.getKuLid());
				qklwwPaging.setTotalSize(totalNum);
				meetingmList=jxkhQklwService.findQklwByKuLidAndPaging(nameSearch, auditStateSearch, yearSearch, user.getKuLid(), qklwwPaging.getActivePage(),
						qklwwPaging.getPageSize());
				journalListbox.setModel(new ListModelList(meetingmList));
			}
			else if(i==11){//会议论文
				hylwPaging.addEventListener("onPaging", new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						hylwList= jxkhHylwService.findHylwByKuLidAndPaging(nameSearch, auditStateSearch, yearSearch, user.getKuLid(), hylwPaging.getActivePage(), hylwPaging.getPageSize());
						hylwListbox.setModel(new ListModelList(hylwList));
					}
				});
				int totalNum = jxkhHylwService.findHylwByKuLid(nameSearch, auditStateSearch, yearSearch, user.getKuLid());
				hylwPaging.setTotalSize(totalNum);
				hylwList= jxkhHylwService.findHylwByKuLidAndPaging(nameSearch, auditStateSearch, yearSearch, user.getKuLid(), hylwPaging.getActivePage(), hylwPaging.getPageSize());
				hylwListbox.setModel(new ListModelList(hylwList));
			}
			else if(i==12){//著作
				List<Jxkh_Writing> meetingList = jxkhProjectService
						.findWritingByCondition(nameSearch, auditStateSearch,
								yearSearch, user.getKuLid());
				zzListbox.setModel(new ListModelList(meetingList));
			}
		}
}
