package org.iti.jxkh.business.video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.write.WriteException;

import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.business.meeting.DownloadWindow;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_VideoDept;
import org.iti.jxkh.entity.Jxkh_VideoFile;
import org.iti.jxkh.entity.Jxkh_VideoMember;
import org.iti.jxkh.service.JxkhVideoService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.iti.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class PersonWin extends Window implements AfterCompose {

	/**
	 * @author ZhangyuGuang
	 */
	private static final long serialVersionUID = -7461839605136552258L;
	private Listbox videoListbox;
	private JxkhVideoService jxkhVideoService;
	private List<Jxkh_Video> reportList = new ArrayList<Jxkh_Video>();
	private WkTUser user;
	private Set<Jxkh_VideoFile> filesList;
	private Textbox name;
	private YearListbox year;
	private String nameSearch, yearSearch;
	private Listbox auditState;
	private Integer auditStateSearch;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		user = (WkTUser) Sessions.getCurrent().getAttribute("user");// ��ȡ��ǰ��¼�û�
		videoListbox.setItemRenderer(new VideoRenderer());
		year.initYearListbox("");
		initWindow();
		String[] s = { "-��ѡ��-", "��д��","�����","���������", "����ͨ��", "���Ų�ͨ��","ҵ����ݻ�ͨ��", "ҵ���ͨ��", "ҵ��첻ͨ��",
		"�鵵" };
		List<String> lwjbList = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			lwjbList.add(s[i]);
		}
		auditState.setModel(new ListModelList(lwjbList));
		auditState.setSelectedIndex(0);
	}

	public void initWindow() {
		reportList = jxkhVideoService.findVideoByKuLid("", null, null,
				user.getKuLid());
		videoListbox.setModel(new ListModelList(reportList));
	}

	public class VideoRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			if (data == null)
				return;
			final Jxkh_Video video = (Jxkh_Video) data;
			item.setValue(video);
			Listcell c0 = new Listcell();
			Listcell c1 = new Listcell(item.getIndex() + 1 + "");
			Listcell c2 = new Listcell(video.getName().length() <= 12?
					video.getName():video.getName().substring(0, 12) + "...");
			c2.setTooltiptext("����鿴Ӱ��ר��Ƭ��Ϣ");
			c2.setStyle("color:blue");
			if (user.getKuLid().equals(video.getSubmitId())) {
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
									"�����Ѿ����ͨ������ҵ����Ѿ����ͨ������ֻ�ܲ鿴����Ȩ�ٱ༭ ��", "��ʾ",
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
						initWindow();
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
						initWindow();
					}
				});
			}
			Listcell c3 = new Listcell(video.getType());
			Listcell c4 = new Listcell();
			if (video.getjxYear() != null) {
				c4 = new Listcell(video.getjxYear());
			} else {
				c4 = new Listcell("");
			}
			Listcell c5 = new Listcell();
			c5.setTooltiptext("����");
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

					filesList = video.getVideoFile();
					win.setFiles(filesList);
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
			c7.setTooltiptext("����鿴��˽��");
			if (video.getState() == null || video.getState() == 0) {
				c7.setLabel("�����");
				c7.setStyle("color:red");
			} else {
				switch (video.getState()) {
				case 0:
					break;
				case 1:
					c7.setLabel("����ͨ��");
					c7.setStyle("color:red");
					break;
				case 2:
					c7.setLabel("���������");
					c7.setStyle("color:red");
					break;
				case 3:
					c7.setLabel("���Ų�ͨ��");
					c7.setStyle("color:red");
					break;
				case 4:
					c7.setLabel("ҵ���ͨ��");
					c7.setStyle("color:red");
					break;
				case 5:
					c7.setLabel("ҵ��첻ͨ��");
					c7.setStyle("color:red");
					break;
				case 6:
					c7.setLabel("�鵵");
					c7.setStyle("color:red");
					break;
				case 7:
					c7.setLabel("ҵ����ݻ�ͨ��");
					c7.setStyle("color:red");
					break;
				case 8:
					c7.setLabel("��д��");
					c7.setStyle("color:red");
					break;
				case 9:
					c7.setLabel("���������");
					c7.setStyle("color:red");
					break;
				}
			}
			// �����������¼�
			c7.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					AdviceWin w = (AdviceWin) Executions.createComponents(
							"/admin/personal/businessdata/video/advice.zul",
							null, null);
					try {
						w.setMeeting(video);
						w.initWindow();
						w.doModal();
					} catch (SuspendNotAllowedException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			item.appendChild(c0);
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

	// ������Ӱ�ť���������Ӱ�ӽ���
	public void onClick$add() {
		AddVideoWin win = (AddVideoWin) Executions.createComponents(
				"/admin/personal/businessdata/video/addvideo.zul", null, null);
		try {
			win.initWindow();
			win.addEventListener(Events.ON_CHANGE, new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					initWindow();
				}
			});
			win.doModal();

		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// ɾ���ǵ���ȷ��ɾ����
	public void onClick$del() throws InterruptedException {
		if (videoListbox.getSelectedItems() == null
				|| videoListbox.getSelectedItems().size() <= 0) {
			try {
				Messagebox.show("��ѡ��Ҫɾ���ı��棡", "��ʾ", Messagebox.OK,
						Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Messagebox.show("ȷ��ɾ����?", "ȷ��", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							Iterator<?> items = videoListbox
									.getSelectedItems().iterator();
							while (items.hasNext()) {
								Listitem item = (Listitem) items.next();
								Jxkh_Video video = (Jxkh_Video) item.getValue();
								if (!user.getKuLid()
										.equals(video.getSubmitId())) {
									try {
										Messagebox.show("���Ǳ����ύ�Ľ�������ɾ����", "��ʾ",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								if (video.getState() == Jxkh_Video.DEPT_PASS
										|| video.getState() == Jxkh_Video.First_Dept_Pass
										|| video.getState() == Jxkh_Video.BUSINESS_PASS || video.getState() == 7
										|| video.getState() == Jxkh_Video.SAVE_RECORD) {
									try {
										Messagebox.show(
												"���Ż�ҵ������ͨ����鵵��Ӱ�Ӳ���ɾ����", "��ʾ",
												Messagebox.OK,
												Messagebox.INFORMATION);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									return;
								}
								jxkhVideoService.delete(video);
							}
							try {
								Messagebox.show("����ɾ���ɹ���", "��ʾ", Messagebox.OK,
										Messagebox.INFORMATION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							initWindow();
						}
					}
				});
	}

	public void onClick$query() {
		nameSearch = null;
		auditStateSearch = null;
		yearSearch = null;
		nameSearch = name.getValue();
		if (auditState.getSelectedItem().getValue().equals("-��ѡ��-")) {
			auditStateSearch = null;
		} else if (auditState.getSelectedItem().getValue().equals("�����")) {
			auditStateSearch = 0;
		} else if (auditState.getSelectedItem().getValue().equals("����ͨ��")) {
			auditStateSearch = 1;
		} else if (auditState.getSelectedItem().getValue().equals("���������")) {
			auditStateSearch = 2;
		} else if (auditState.getSelectedItem().getValue().equals("���Ų�ͨ��")) {
			auditStateSearch = 3;
		} else if (auditState.getSelectedItem().getValue().equals("ҵ���ͨ��")) {
			auditStateSearch = 4;
		} else if (auditState.getSelectedItem().getValue().equals("ҵ��첻ͨ��")) {
			auditStateSearch = 5;
		} else if (auditState.getSelectedItem().getValue().equals("�鵵")) {
			auditStateSearch = 6;
		}else if(auditState.getSelectedItem().getValue().equals("ҵ����ݻ�ͨ��")) {
			auditStateSearch = 7;
		}else if(auditState.getSelectedItem().getValue().equals("��д��")) {
			auditStateSearch = 8;
		}
		if (year.getSelectedIndex() != 0 && year.getSelectedItem() != null)
			yearSearch = year.getSelectedItem().getValue() + "";

		List<Jxkh_Video> reportList = jxkhVideoService.findVideoByKuLid(
				nameSearch, auditStateSearch, yearSearch, user.getKuLid());
		videoListbox.setModel(new ListModelList(reportList));
	}

	public void onClick$reset() {
		name.setValue("");
		auditState.getItemAtIndex(0).setSelected(true);
		year.getItemAtIndex(0).setSelected(true);
	}

	// ����
	public void onClick$expert() throws WriteException, IOException {
		if (videoListbox.getSelectedCount() == 0) {
			try {
				Messagebox.show("��ʾ��ѡ��Ҫ����������", "��ʾ", Messagebox.OK,
						Messagebox.EXCLAMATION);
			} catch (InterruptedException e) {
				// ignore
			}
			return;
		} else {
			int i = 0;
			ArrayList<Jxkh_Video> expertlist = new ArrayList<Jxkh_Video>();
			@SuppressWarnings("unchecked")
			Set<Listitem> sel = videoListbox.getSelectedItems();
			Iterator<Listitem> it = sel.iterator();
			while (it.hasNext()) {
				Listitem item = (Listitem) it.next();
				Jxkh_Video p = (Jxkh_Video) item.getValue();
				expertlist.add(i, p);
				i++;
			}
			Date now = new Date();
			String fileName = ConvertUtil.convertDateString(now)
					+ "yingshixinxi" + ".xls";
			String Title = "Ӱ�����";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("Ӱ������");
			titlelist.add("�����");
			titlelist.add("Ժ�ڲ���");
			titlelist.add("Ժ�ⲿ��");
			titlelist.add("��ʾ�쵼");
			titlelist.add("Ӱ������");
			titlelist.add("����ʱ��");
			titlelist.add("����ý�弶��");
			titlelist.add("����ʱ��");
			titlelist.add("����ʱ�������ӣ�");
			titlelist.add("����ý��");
			titlelist.add("��Ϣ��д��");
			String c[][] = new String[expertlist.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < expertlist.size(); j++) {
				Jxkh_Video video = (Jxkh_Video) expertlist.get(j);
				List<Jxkh_VideoMember> mlist = video.getVideoMember();
				String member = "";
				c[j][0] = j + 1 + "";
				c[j][1] = video.getName();
				if (mlist.size() != 0) {
					for (int m = 0; m < mlist.size(); m++) {
						Jxkh_VideoMember mem = (Jxkh_VideoMember) mlist.get(m);
						member += mem.getName() + "��";
					}
					c[j][2] = member.substring(0, member.length() - 1);
				}
				List<Jxkh_VideoDept> dlist = video.getVideoDept();
				String dept = "";
				if (dlist.size() != 0) {
					for (int m = 0; m < dlist.size(); m++) {
						Jxkh_VideoDept de = (Jxkh_VideoDept) dlist.get(m);
						dept += de.getName() + "��";
					}
					c[j][3] = dept.substring(0, dept.length() - 1);
				}
				c[j][4] = video.getCoCompany();
				c[j][5] = video.getLeader().getKbName();
				c[j][6] = video.getType();
				c[j][7] = video.getShootTime();
				c[j][8] = video.getRank().getKbName();
				c[j][9] = video.getPlayTime();
				c[j][10] = video.getLongTime().toString();
				c[j][11] = video.getMedia();
				c[j][12] = video.getSubmitName();
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, expertlist.size(), c);
		}
	}
}
