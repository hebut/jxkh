package org.iti.jxkh.score.work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import jxl.write.WriteException;
import org.iti.gh.common.util.ExportExcel;
import org.iti.gh.ui.listbox.YearListbox;
import org.iti.jxkh.entity.JXKH_AppraisalMember;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.entity.JXKH_MultiDept;
import org.iti.jxkh.entity.JXKH_PointNumber;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.AuditConfigService;
import org.iti.jxkh.service.AuditResultService;
import org.iti.jxkh.service.BusinessIndicatorService;
import org.iti.jxkh.service.JxkhAwardService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.iti.common.util.PropertiesLoader;
import com.uniwin.framework.entity.WkTDept;

public class SortWindow extends BaseWindow {

	private static final long serialVersionUID = -2803419586964832849L;
	private Row listrow;
	private YearListbox yearlist;
	private Listbox listbox;
	private AuditConfigService auditConfigService;
	private AuditResultService auditResultService;
	private BusinessIndicatorService businessIndicatorService;
	private String year;
	private Float[][] array;
	private Float[] TmpArray;
	private Label yearLb, baseLb;
    private JxkhAwardService jxkhAwardService;
	private List<JXKH_AppraisalMember> checkedMember3=new ArrayList<JXKH_AppraisalMember>();
	private List<JXKH_AppraisalMember> checkedMember2=new ArrayList<JXKH_AppraisalMember>();
	private List<JXKH_AppraisalMember> checkedMember1=new ArrayList<JXKH_AppraisalMember>();
	public void initShow() {
		yearlist.initYearListbox("");
		listbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {//list��Ⱦ
				JXKH_AuditResult res = (JXKH_AuditResult) arg1;//�ؼ��ľ��Ǳ�JXKH_AuditResult
				WkTDept dept = (WkTDept) auditConfigService.loadById(WkTDept.class, res.getKdId());
				Listcell c0 = new Listcell(arg0.getIndex() + 1 + "");//���
				Listcell c1 = new Listcell();//��������
				if(dept.getKdName() != null){
					c1.setLabel(dept.getKdName());
				}
				int num = auditConfigService.findCheckPeoByDeptId(dept.getKdId());//
				Listcell c2 = new Listcell(num + "");//��Ա��Ŀ(�μӿ��˵���Ա����Ŀ)
				Listcell c3 = new Listcell();
				Listcell c4 = new Listcell();
				if(res.getArScore() != null){
					c3.setLabel(new DecimalFormat("#.00").format(res.getArScore()) + "");//���Ż���
					c4.setLabel(new DecimalFormat("#.00").format(res.getArScore() / num) + "");//�˾�����
				}
				/*BigDecimal bd = new BigDecimal(res.getArRate() * 100);
				Listcell c5 = new Listcell(bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");//ͬ��������
				c5.setVisible(false);
				Listcell c6 = new Listcell();
				Listcell c7 = new Listcell();//ϵ��
				if(res.getArLevel() != null && !res.getArLevel().toString().equals("")){
					c6.setLabel(res.getArLevel() + "");//��λ
					
					if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_ONE) {
						c7.setLabel("1.0");
					} else if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_TWO) {
						c7.setLabel("1.3");
					} else if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_THREE) {
						c7.setLabel("1.6");
					}
				}*/
				
				
				arg0.appendChild(c0);
				arg0.appendChild(c1);
				arg0.appendChild(c2);
				arg0.appendChild(c3);
				arg0.appendChild(c4);
				/*arg0.appendChild(c5);
				arg0.appendChild(c6);
				arg0.appendChild(c7);*/
				/*arg0.appendChild(c8);
				arg0.appendChild(c9);
				arg0.appendChild(c10);*/
			}
		});
	}

	public void onSelect$yearlist() {
		String year = yearlist.getSelectYear();
		List<JXKH_AuditResult> list = auditResultService.findDept(year);//������ݲ��Ҳ���
		if (list.size() != 0) {
			listbox.setModel(new ListModelList(auditResultService.findDept(year)));
			/*JXKH_AuditConfig config = auditConfigService.findByYear(year);
			if (config == null) {
				try {
					Messagebox.show("�����Բ�����δ���ã��������ã�");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return;
			}*/
			yearLb.setValue(year);//�������
			//baseLb.setValue(config.getAcBase() + "");//���û������
			listrow.setVisible(true);
			listbox.setVisible(true);
		}
	}
	public void initWindow() {
	}

	public void computeDetail() {
		List<JXKH_AuditResult> arList = auditResultService.findDeptByYear(year);//��ѯ���ŵ÷�
		for (JXKH_AuditResult ar : arList) {
			WkTDept dep = (WkTDept) auditResultService.loadById(WkTDept.class, ar.getKdId());
			WkTDept d = (WkTDept) this.auditConfigService.loadById(WkTDept.class, ar.getKdId());
			String key = "sci" + "-" + "ei" + "-" + "istp" + "-" + "core" + "-" + "normal" + "-" + "nation" + "-" + "province" + "-" + "zz" + "-" + "bz" + "-" + "hb" + "-" + "nbqk" + "-" + "nation1" + "-" + "nation2" + "-" + "province1" + "-"
					+ "province2" + "-" + "province3" + "-" + "city1" + "-" + "city2" + "-" + "city3" + "-" + "videonation" + "-" + "videoprovince" + "-" + "goveragree" + "-" + "tleaderagree" + "-" + "tchuagree" + "-" + "centertv" + "-" + "hbtv" + "-"
					+ "nationstudy" + "-" + "nationwork" + "-" + "provincestudy" + "-" + "provincework" + "-" + "citystudy" + "-" + "hproject" + "-" + "foreigninvent" + "-" + "invent" + "-" + "applied" + "-" + "software" + "-" + "nationys" + "-"
					+ "provinceys" + "-" + "normalys" + "-" + "nationys_" + "-" + "provinceys_" + "-" + "nationmeeting" + "-" + "provincemeeting" + "-" + "interprovince" + "-" + "majormeeting" + "-" + "reportgov" + "-" + "reportting" + "-"
					+ "reportscience";

			// ����
			float sci = 0, ei = 0, istp = 0, core = 0, normal = 0, nation = 0, province = 0;
			// ����
			float zz = 0, bzyz = 0, hb = 0, nbqk = 0;
			// ����
			float nation1 = 0, nation2 = 0, province1 = 0, province2 = 0, province3 = 0, city1 = 0, city2 = 0, city3 = 0;
			// Ӱ��
			float videonation = 0, videoprovince = 0, goveragree = 0, tleaderagree = 0, tchuagree = 0, centertv = 0, hbtv = 0;
			// ��Ŀ
			float nationstudy = 0, nationwork = 0, provincestudy = 0, provincework = 0, citystudy = 0, hproject = 0;
			// ר��
			float foreigninvent = 0, invent = 0, applied = 0, software = 0;
			// �ɹ�
			float nationys = 0, provinceys = 0, normalys = 0, nationys_1 = 0, provinceys_1 = 0;
			// ����
			float nationmeeting = 0, provincemeeting = 0, interprovince = 0, majormeeting = 0;
			// ����
			float reportgov = 0, reportting = 0, reportscience = 0;

			int sci_ = 0, ei_ = 0, istp_ = 0, core_ = 0, normal_ = 0, nation_ = 0, province_ = 0;
			int zz_ = 0, bzyz_ = 0, hb_ = 0, nbqk_ = 0;
			int nation1_ = 0, nation2_ = 0, province1_ = 0, province2_ = 0, province3_ = 0, city1_ = 0, city2_ = 0, city3_ = 0;
			int videonation_ = 0, videoprovince_ = 0, goveragree_ = 0, tleaderagree_ = 0, tchuagree_ = 0, centertv_ = 0, hbtv_ = 0;
			int nationstudy_ = 0, nationwork_ = 0, provincestudy_ = 0, provincework_ = 0, citystudy_ = 0, hproject_ = 0;
			int foreigninvent_ = 0, invent_ = 0, applied_ = 0, software_ = 0;
			int nationys_ = 0, provinceys_ = 0, normalys_ = 0, nationys_1_ = 0, provinceys_1_ = 0;
			int nationmeeting_ = 0, provincemeeting_ = 0, interprovince_ = 0, majormeeting_ = 0;
			int reportgov_ = 0, reportting_ = 0, reportscience_ = 0;

			Properties property = PropertiesLoader.loader("title", "title.properties");
			// ��Ŀ
			//System.out.print(dep.getKdName());//
			//System.out.println(dep.getKdNumber());//���ű�ţ����ײ����ڵĳ�Ա�������أ���
			nationstudy_ = auditResultService.countDeptProNum(dep.getKdNumber(), year, property.getProperty("nationstudy").toString().trim());
			nationwork_ = auditResultService.countDeptProNum(dep.getKdNumber(), year, property.getProperty("nationwork").toString().trim());
			provincestudy_ = auditResultService.countDeptProNum(dep.getKdNumber(), year, property.getProperty("provincestudy").toString().trim());
			provincework_ = auditResultService.countDeptProNum(dep.getKdNumber(), year, property.getProperty("provincework").toString().trim());
			citystudy_ = auditResultService.countDeptProNum(dep.getKdNumber(), year, property.getProperty("citystudy").toString().trim());
			hproject_ = auditResultService.countDeptProNum(dep.getKdNumber(), year, property.getProperty("hproject").toString().trim());
			nationstudy = auditResultService.countDeptProType(dep.getKdNumber(), year, property.getProperty("nationstudy").toString().trim());
			nationwork = auditResultService.countDeptProType(dep.getKdNumber(), year, property.getProperty("nationwork").toString().trim());
			provincestudy = auditResultService.countDeptProType(dep.getKdNumber(), year, property.getProperty("provincestudy").toString().trim());
			provincework = auditResultService.countDeptProType(dep.getKdNumber(), year, property.getProperty("provincework").toString().trim());
			citystudy = auditResultService.countDeptProType(dep.getKdNumber(), year, property.getProperty("citystudy").toString().trim());
			hproject = auditResultService.countDeptProType(dep.getKdNumber(), year, property.getProperty("hproject").toString().trim());
			// ����
			zz_ = auditResultService.countDeptWriNum(dep.getKdNumber(), year, property.getProperty("zz"));
			bzyz_ = auditResultService.countDeptWriNum(dep.getKdNumber(), year, property.getProperty("bz")) + auditResultService.countDeptWriNum(dep.getKdNumber(), year, property.getProperty("yz"));
			hb_ = auditResultService.countDeptWriNum(dep.getKdNumber(), year, property.getProperty("hb"));
			nbqk_ = auditResultService.countDeptWriNum(dep.getKdNumber(), year, property.getProperty("nbqk"));
			zz = auditResultService.countDeptWriType(dep.getKdNumber(), year, property.getProperty("zz"));
			bzyz = auditResultService.countDeptWriType(dep.getKdNumber(), year, property.getProperty("bz")) + auditResultService.countDeptWriType(dep.getKdNumber(), year, property.getProperty("yz"));
			hb = auditResultService.countDeptWriType(dep.getKdNumber(), year, property.getProperty("hb"));
			nbqk = auditResultService.countDeptWriType(dep.getKdNumber(), year, property.getProperty("nbqk"));
			// ר��
			foreigninvent_ = auditResultService.countDeptPatNum(dep.getKdNumber(), year, property.getProperty("foreigninvent"));
			invent_ = auditResultService.countDeptPatNum(dep.getKdNumber(), year, property.getProperty("invent"));
			applied_ = auditResultService.countDeptPatNum(dep.getKdNumber(), year, property.getProperty("applied"));
			software_ = auditResultService.countDeptPatNum(dep.getKdNumber(), year, property.getProperty("software"));
			foreigninvent = auditResultService.countDeptPatType(dep.getKdNumber(), year, property.getProperty("foreigninvent"));
			invent = auditResultService.countDeptPatType(dep.getKdNumber(), year, property.getProperty("invent"));
			applied = auditResultService.countDeptPatType(dep.getKdNumber(), year, property.getProperty("applied"));
			software = auditResultService.countDeptPatType(dep.getKdNumber(), year, property.getProperty("software"));
			// ����
			nationmeeting_ = auditResultService.countDeptMeeNum(dep.getKdNumber(), year, property.getProperty("nationmeeting"));
			provincemeeting_ = auditResultService.countDeptMeeNum(dep.getKdNumber(), year, property.getProperty("provincemeeting"));
			interprovince_ = auditResultService.countDeptMeeNum(dep.getKdNumber(), year, property.getProperty("interprovince"));
			majormeeting_ = auditResultService.countDeptMeeNum(dep.getKdNumber(), year, property.getProperty("majormeeting"));
			nationmeeting = auditResultService.countDeptMeeType(dep.getKdNumber(), year, property.getProperty("nationmeeting"));
			provincemeeting = auditResultService.countDeptMeeType(dep.getKdNumber(), year, property.getProperty("provincemeeting"));
			interprovince = auditResultService.countDeptMeeType(dep.getKdNumber(), year, property.getProperty("interprovince"));
			majormeeting = auditResultService.countDeptMeeType(dep.getKdNumber(), year, property.getProperty("majormeeting"));

			// ������
			Jxkh_BusinessIndicator bi_sci = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("sci"));
			Object[] obi_sci1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.QKLW, bi_sci.getKbId());
			Object[] obi_sci2 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.HYLW, bi_sci.getKbId());
			sci_ = (Integer) obi_sci1[0] + (Integer) obi_sci2[0];
			sci = (Float) obi_sci1[1] + (Float) obi_sci2[1];

			Jxkh_BusinessIndicator obi_ei = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("ei"));
			Object[] obi_ei1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.QKLW, obi_ei.getKbId());
			Object[] obi_ei2 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.HYLW, obi_ei.getKbId());
			ei_ = (Integer) obi_ei1[0] + (Integer) obi_ei2[0];
			ei = (Float) obi_ei1[1] + (Float) obi_ei2[1];

			Jxkh_BusinessIndicator bi_istp = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("istp"));
			Object[] obi_istp1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.QKLW, bi_istp.getKbId());
			Object[] obi_istp2 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.HYLW, bi_istp.getKbId());
			istp_ = (Integer) obi_istp1[0] + (Integer) obi_istp2[0];
			istp = (Float) obi_istp1[1] + (Float) obi_istp2[1];

			Jxkh_BusinessIndicator bi_core = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("core"));
			Jxkh_BusinessIndicator bi_international = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("international"));
			Object[] obi_core1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.QKLW, bi_core.getKbId());
			Object[] obi_international1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.HYLW, bi_international.getKbId());
			core_ = (Integer) obi_core1[0] + (Integer) obi_international1[0];
			core = (Float) obi_core1[1] + (Float) obi_international1[1];

			Jxkh_BusinessIndicator bi_normal = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("normal"));
			Object[] obi_normal1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.QKLW, bi_normal.getKbId());
			normal_ = (Integer) obi_normal1[0];
			normal = (Float) obi_normal1[1];

			Jxkh_BusinessIndicator bi_nation = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("nation"));
			Object[] obi_nation1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.HYLW, bi_nation.getKbId());
			nation_ = (Integer) obi_nation1[0];
			nation = (Float) obi_nation1[1];

			Jxkh_BusinessIndicator bi_province = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("province"));
			Object[] obi_province1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.HYLW, bi_province.getKbId());
			province_ = (Integer) obi_province1[0];
			province = (Float) obi_province1[1];

			// ����
			Jxkh_BusinessIndicator bi_nation1 = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("nation1"));
			Object[] obi_nation11 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.JL, bi_nation1.getKbId());
			nation1_ = (Integer) obi_nation11[0];
			nation1 = (Float) obi_nation11[1];

			Jxkh_BusinessIndicator bi_nation2 = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("nation2"));
			Object[] onation21 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.JL, bi_nation2.getKbId());
			nation2_ = (Integer) onation21[0];
			nation2 = (Float) onation21[1];

			Jxkh_BusinessIndicator bi_province1 = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("province1"));
			Object[] oprovince11 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.JL, bi_province1.getKbId());
			province1_ = (Integer) oprovince11[0];
			province1 = (Float) oprovince11[1];

			Jxkh_BusinessIndicator bi_province2 = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("province2"));
			Object[] oprovince21 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.JL, bi_province2.getKbId());
			province2_ = (Integer) oprovince21[0];
			province2 = (Float) oprovince21[1];

			Jxkh_BusinessIndicator bi_province3 = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("province3"));
			Object[] oprovince31 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.JL, bi_province3.getKbId());
			province3_ = (Integer) oprovince31[0];
			province3 = (Float) oprovince31[1];

			Jxkh_BusinessIndicator bi_city1 = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("city1"));
			Object[] ocity11 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.JL, bi_city1.getKbId());
			city1_ = (Integer) ocity11[0];
			city1 = (Float) ocity11[1];

			Jxkh_BusinessIndicator bi_city2 = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("city2"));
			Object[] ocity21 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.JL, bi_city2.getKbId());
			city2_ = (Integer) ocity21[0];
			city2 = (Float) ocity21[1];

			Jxkh_BusinessIndicator bi_city3 = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("city3"));
			Object[] ocity31 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.JL, bi_city3.getKbId());
			city3_ = (Integer) ocity31[0];
			city3 = (Float) ocity31[1];

			Jxkh_BusinessIndicator bi_videonation = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("videonation"));
			Object[] ovideonation1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.SP, bi_videonation.getKbId());
			videonation_ = (Integer) ovideonation1[0];
			videonation = (Float) ovideonation1[1];

			Jxkh_BusinessIndicator bi_videoprovince = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("videoprovince"));
			Object[] ovideoprovince1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.SP, bi_videoprovince.getKbId());
			videoprovince_ = (Integer) ovideoprovince1[0];
			videoprovince = (Float) ovideoprovince1[1];

			Jxkh_BusinessIndicator bi_goveragree = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("goveragree"));
			Object[] ogoveragree1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.SP, bi_goveragree.getKbId());
			goveragree_ = (Integer) ogoveragree1[0];
			goveragree = (Float) ogoveragree1[1];

			Jxkh_BusinessIndicator bi_tleaderagree = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("tleaderagree"));
			Object[] otleaderagree1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.SP, bi_tleaderagree.getKbId());
			tleaderagree_ = (Integer) otleaderagree1[0];
			tleaderagree = (Float) otleaderagree1[1];

			Jxkh_BusinessIndicator bi_tchuagree = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("tchuagree"));
			Object[] otchuagree1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.SP, bi_tchuagree.getKbId());
			tchuagree_ = (Integer) otchuagree1[0];
			tchuagree = (Float) otchuagree1[1];

			Jxkh_BusinessIndicator bi_centertv = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("centertv"));
			Object[] ocentertv1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.SP, bi_centertv.getKbId());
			centertv_ = (Integer) ocentertv1[0];
			centertv = (Float) ocentertv1[1];

			Jxkh_BusinessIndicator bi_hbtv = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("hbtv"));
			Object[] ohbtv1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.SP, bi_hbtv.getKbId());
			hbtv_ = (Integer) ohbtv1[0];
			hbtv = (Float) ohbtv1[1];
			// �ɹ�
			Jxkh_BusinessIndicator bi_nationys = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("nationys"));
			Object[] onationys1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.CG, bi_nationys.getKbId());
			nationys_ = (Integer) onationys1[0];
			nationys = (Float) onationys1[1];

			Jxkh_BusinessIndicator bi_provinceys = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("provinceys"));
			Object[] oprovinceys1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.CG, bi_provinceys.getKbId());
			provinceys_ = (Integer) oprovinceys1[0];
			provinceys = (Float) oprovinceys1[1];

			Jxkh_BusinessIndicator bi_normalys = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("normalys"));
			Object[] onormalys1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.CG, bi_normalys.getKbId());
			normalys_ = (Integer) onormalys1[0];
			normalys = (Float) onormalys1[1];

			Jxkh_BusinessIndicator bi_nationys_ = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("nationys_"));
			Object[] onationys_1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.CG, bi_nationys_.getKbId());
			nationys_1_ = (Integer) onationys_1[0];
			nationys_1 = (Float) onationys_1[1];

			Jxkh_BusinessIndicator bi_provinceys_ = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("provinceys_"));
			Object[] oprovinceys_1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.CG, bi_provinceys_.getKbId());
			provinceys_1_ = (Integer) oprovinceys_1[0];
			provinceys_1 = (Float) oprovinceys_1[1];

			Jxkh_BusinessIndicator bi_reportgov = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("reportgov"));
			Object[] oreportgov1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.BG, bi_reportgov.getKbId());
			reportgov_ = (Integer) oreportgov1[0];
			reportgov = (Float) oreportgov1[1];

			Jxkh_BusinessIndicator bi_reportting = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("reportting"));
			Object[] oreportting1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.BG, bi_reportting.getKbId());
			reportting_ = (Integer) oreportting1[0];
			reportting = (Float) oreportting1[1];

			Jxkh_BusinessIndicator bi_reportscience = (Jxkh_BusinessIndicator) businessIndicatorService.getEntityByName(property.getProperty("reportscience"));
			Object[] oreportscience1 = this.auditResultService.findBiSumScore(d.getKdNumber(), year, JXKH_MultiDept.BG, bi_reportscience.getKbId());
			reportscience_ = (Integer) oreportscience1[0];
			reportscience = (Float) oreportscience1[1];
			// ����
			String valueshow = sci_ + "-" + ei_ + "-" + istp_ + "-" + core_ + "-" + normal_ + "-" + nation_ + "-" + province_ + "-" + zz_ + "-" + bzyz_ + "-" + hb_ + "-" + nbqk_ + "-" + nation1_ + "-" + nation2_ + "-" + province1_ + "-" + province2_ + "-"
					+ province3_ + "-" + city1_ + "-" + city2_ + "-" + city3_ + "-" + videonation_ + "-" + videoprovince_ + "-" + goveragree_ + "-" + tleaderagree_ + "-" + tchuagree_ + "-" + centertv_ + "-" + hbtv_ + "-" + nationstudy_ + "-" + nationwork_
					+ "-" + provincestudy_ + "-" + provincework_ + "-" + citystudy_ + "-" + hproject_ + "-" + foreigninvent_ + "-" + invent_ + "-" + applied_ + "-" + software_ + "-" + nationys_ + "-" + provinceys_ + "-" + normalys_ + "-" + nationys_1_
					+ "-" + provinceys_1_ + "-" + nationmeeting_ + "-" + provincemeeting_ + "-" + interprovince_ + "-" + majormeeting_ + "-" + reportgov_ + "-" + reportting_ + "-" + reportscience_;

			// ��ֵ
			String exp = sci + "-" + ei + "-" + istp + "-" + core + "-" + normal + "-" + nation + "-" + province + "-" + zz + "-" + bzyz + "-" + hb + "-" + nbqk + "-" + nation1 + "-" + nation2 + "-" + province1 + "-" + province2 + "-" + province3 + "-"
					+ city1 + "-" + city2 + "-" + city3 + "-" + videonation + "-" + videoprovince + "-" + goveragree + "-" + tleaderagree + "-" + tchuagree + "-" + centertv + "-" + hbtv + "-" + nationstudy + "-" + nationwork + "-" + provincestudy + "-"
					+ provincework + "-" + citystudy + "-" + hproject + "-" + foreigninvent + "-" + invent + "-" + applied + "-" + software + "-" + nationys + "-" + provinceys + "-" + normalys + "-" + nationys_1 + "-" + provinceys_1 + "-" + nationmeeting
					+ "-" + provincemeeting + "-" + interprovince + "-" + majormeeting + "-" + reportgov + "-" + reportting + "-" + reportscience;
			ar.setArKeys(key);
			ar.setArValues(valueshow);
			ar.setArExpand(exp);
			auditResultService.update(ar);
		}

	}

	public void onClick$compute() throws InterruptedException {
		String path = "d://save.perperties";
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		Properties log = new Properties();
		year = yearlist.getSelectYear();
		JXKH_AuditConfig config = auditConfigService.findByYear(year);
		if (config == null) {
			Messagebox.show("�����Բ�����δ���ã��������ã�");
			return;
		}
		int flag = 0;
		List<String> list = new ArrayList<String>();
		List<JXKH_AppraisalMember> setMem = jxkhAwardService.findAllAppraisalMember();
		for(JXKH_AppraisalMember m:setMem){
			list.add(m.getDept());
		}
		List<WkTDept> deptlist = jxkhAwardService.findDeptAll();
		for(WkTDept d:deptlist){
			if(!list.contains(d.getKdName())){
				flag = flag + 1;
			}
		}
		
		if(flag>0){
			Messagebox.show("����������Ƿ���ȫ���趨������Ա");
			return;
		}
		auditResultService.deleteAll(auditResultService.findDeptByYear(year + ""));
		auditResultService.deleteAll(auditResultService.findWorkDeptByYear(year + ""));
		// ���е�ҵ�����б�
		List<WkTDept> dlist = auditResultService.findWorkDept();
		array = new Float[dlist.size()][9];
		TmpArray = new Float[dlist.size()];

		float sumScore = 0f/* ҵ�����ܻ��� */, sumAver = 0f/* ҵ������ƽ���� */, sumRate = 0f;
		float sumScoreSq = 0f, sumAverSq = 0f, sumRateSq = 0f;
		int index = 0;
		for (WkTDept dept : dlist) {
			float res = 0f;
			float sumHYLW = 0/* ������������ */, sumQKLW = 0/* �ڿ��������� */, sumJL = 0, sumSP = 0, sumCG = 0, sumBG = 0;
			String kdnumber = dept.getKdNumber();
			Float pro = auditResultService.countDeptPro(dept.getKdNumber(), year);// �ò�����Ŀ�ܵ÷�
			Float wri = auditResultService.countDeptWri(dept.getKdNumber(), year);// �ò��������ܵ÷�
			Float pat = auditResultService.countDeptPat(dept.getKdNumber(), year);// �ò���ר���ܵ÷�
			Float mee = auditResultService.countDeptMee(dept.getKdNumber(), year);// �ò��Ż����ܵ÷�
			// �ò��Ż��������ܵ÷�
			sumHYLW = this.auditResultService.findSumScoreByKdNumberYearAndState(kdnumber, year, JXKH_MultiDept.HYLW);
			// �ò����ڿ������ܵ÷�
			sumQKLW = this.auditResultService.findSumScoreByKdNumberYearAndState(kdnumber, year, JXKH_MultiDept.QKLW);
			// �ò��Ž����ܵ÷�
			sumJL = this.auditResultService.findSumScoreByKdNumberYearAndState(kdnumber, year, JXKH_MultiDept.JL);
			// �ò���Ӱ��ר��Ƭ�ܵ÷�
			sumSP = this.auditResultService.findSumScoreByKdNumberYearAndState(kdnumber, year, JXKH_MultiDept.SP);
			// �ò��ųɹ��ܵ÷�
			sumCG = this.auditResultService.findSumScoreByKdNumberYearAndState(kdnumber, year, JXKH_MultiDept.CG);
			// �ò��ű����ܵ÷�
			sumBG = this.auditResultService.findSumScoreByKdNumberYearAndState(kdnumber, year, JXKH_MultiDept.BG);
			res = pro + wri + pat + mee + sumHYLW + sumQKLW + sumJL + sumSP + sumCG + sumBG;
			// int num = auditConfigService.findDeptMember(dept.getKdId());
			/*************************changed****************************/
			// �����������������ڱ����Ƹ
			//int num = auditConfigService.findDeptAllMember(dept.getKdId());
			int num = auditConfigService.findCheckPeoByDeptId(dept.getKdId());//�μӿ��˵���Ա��Ŀ
			/*******************************************************/
			// �б��1λ��Ų����ܻ���
			array[index][0] = res;
			// �б��2λ��Ų����˾�����-1
			array[index][1] = (res / (float) num) - 1;
			// �ò�����һ��Ĳ��ŵ÷�������Ȳ����ǣ�
			JXKH_AuditResult ar = auditResultService.findDepartment((Integer.parseInt(year) - 1) + "", dept.getKdId());
			if (ar != null) {
				// �б��3λ��Ÿò���ͬ��������
				if (ar.getNewArScore() != null)
					array[index][2] = (res - ar.getNewArScore()) / ar.getNewArScore();
				else if (ar.getArScore() != null && !ar.getArScore().toString().equals("0.0")) {
					array[index][2] = (res - ar.getArScore()) / ar.getArScore();
				} else {
					array[index][2] = 1f;
				}
			} else {
				array[index][2] = 1f;
			}
			// ���в��ŵ��ܻ����ܺ�
			sumScore += array[index][0];
			// ���в����˾������ܺ�
			sumAver += array[index][1];
			// ���в���ͬ���������ܺ�
			sumRate += array[index][2];
			// ���в��ŵ��ܻ��ֵ�ƽ���ܺ�
			sumScoreSq += array[index][0] * array[index][0];
			// ���в����˾�����ƽ���ܺ�
			sumAverSq += array[index][1] * array[index][1];
			// ���в���ͬ��������ƽ���ܺ�
			sumRateSq += array[index][2] * array[index][2];

			JXKH_AuditResult result = new JXKH_AuditResult();
			result.setKdId(dept.getKdId());
			result.setArType(JXKH_AuditResult.AR_DEPT);
			result.setArYear(year + "");
			result.setArScore(res);
			result.setArRate(array[index][2]);
			auditResultService.save(result);
			index++;
		}
		computeDetail();// ��������
		float xScore = sumScore / dlist.size();
		float xAver = sumAver / dlist.size();
		float xRate = sumRate / dlist.size();
		double sScore = Math.sqrt(sumScoreSq / dlist.size());
		double sAver = Math.sqrt(sumAverSq / dlist.size());
		double sRate = Math.sqrt(sumRateSq / dlist.size());
		float maxScore = Float.MIN_VALUE, minScore = Float.MAX_VALUE;
		float maxAver = Float.MIN_VALUE, minAver = Float.MAX_VALUE;
		float maxRate = Float.MIN_VALUE, minRate = Float.MAX_VALUE;
		for (int i = 0; i < dlist.size(); i++) {
			array[i][3] = (float) ((array[i][0] - xScore) / sScore);
			array[i][4] = (float) ((array[i][1] - xAver) / sAver);
			array[i][5] = (float) ((array[i][2] - xRate) / sRate);
			if (maxScore < array[i][3]) {
				maxScore = array[i][3];
			}
			if (maxAver < array[i][4]) {
				maxAver = array[i][4];
			}
			if (maxRate < array[i][5]) {
				maxRate = array[i][5];
			}
			if (minScore > array[i][3]) {
				minScore = array[i][3];
			}
			if (minAver > array[i][4]) {
				minAver = array[i][4];
			}
			if (minRate > array[i][5]) {
				minRate = array[i][5];
			}
		}
		float maxYScore = Float.MIN_VALUE, maxYAver = Float.MIN_VALUE, maxYRate = Float.MIN_VALUE;
		for (int i = 0; i < dlist.size(); i++) {
			array[i][6] = (array[i][3] - minScore) / (maxScore - minScore);
			array[i][7] = (array[i][4] - minAver) / (maxAver - minAver);
			array[i][8] = (array[i][5] - minRate) / (maxRate - minRate);
			if (maxYScore < array[i][6]) {
				maxYScore = array[i][6];
			}
			if (maxYAver < array[i][7]) {
				maxYAver = array[i][7];
			}
			if (maxYRate < array[i][8]) {
				maxYRate = array[i][8];
			}
		}
		index = 0;
		for (WkTDept dept : dlist) {
			JXKH_AuditResult ar = auditResultService.findDepartment(year, dept.getKdId());
			Float a = (100 * array[index][6]) / maxYScore + (100 * array[index][7]) / maxYAver + (100 * array[index][8]) / maxYRate;
			if (a.isNaN()) {
				ar.setArIndex(0f);//���ñ��
			} else {
				ar.setArIndex((100 * array[index][6]) / maxYScore + (100 * array[index][7]) / maxYAver + (100 * array[index][8]) / maxYRate);
			}
			auditResultService.update(ar);
			index++;
		}
		List<Integer> numDept = new ArrayList<Integer>();
		List<JXKH_AuditResult> arlist = auditResultService.findDeptByYear(year + "");
		int num1 = 0, num2 = 0, num3 = 0, temp = 0;
		int num1_ = 0, num2_ = 0, num3_ = 0;
		/*****************************************************/
		List<JXKH_AppraisalMember>leaderList=jxkhAwardService.findpeoByDept("�ӱ�ʡ��ѧ�����鱨�о�Ժ");//service������
		int leader = leaderList.size();
		/*int leader = auditConfigService.findLeader();*///����Ժ�쵼����Ŀ��Ժ�쵼�Ƿ���Ҫָ������
		//System.out.print("leader:");
		//System.out.println(leader);
		/*********************************************************/
		float a = config.getAcBFloat();//�ϸ�һ������
		int anum = 0, bnum = 0;
		for (int i = 0; i < config.getThird(); i++) {//������
			arlist.get(i).setArLevel(JXKH_AuditResult.LEVEL_THREE);
	//	temp = auditConfigService.findDeptMember(arlist.get(i).getKdId());//���Ҳ����ڱ�����
	    temp = auditConfigService.findCheckPeoByDeptId(arlist.get(i).getKdId());//���Ҳμӿ��˵�����
			JXKH_PointNumber pn = auditResultService.findPointNumber(year, arlist.get(i).getKdId());
			if (pn != null) {
				anum = pn.getPnNumber();
				bnum = temp - anum;
			} else {
				anum = new BigDecimal(temp * a).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
				bnum = temp - anum;
			}
			num1 += temp;
			num1_ += anum;
			numDept.add(temp);
			auditResultService.update(arlist.get(i));
			TmpArray[i] = bnum * 1.6f + anum * 2f + 0.4f;//������important��
		}
		float base1 = (num1 - num1_) * 1.6f + num1_ * 2f + 0.4f * config.getThird() + (leader * 1.6f) / 2;//����Ǹ�����أ�
		for (int i = config.getThird(); i < config.getThird() + config.getSecond(); i++) {//������
			arlist.get(i).setArLevel(JXKH_AuditResult.LEVEL_TWO);
		//	temp = auditConfigService.findDeptMember(arlist.get(i).getKdId());
			temp = auditConfigService.findCheckPeoByDeptId(arlist.get(i).getKdId());//���Ҳμӿ��˵�����
			JXKH_PointNumber pn = auditResultService.findPointNumber(year, arlist.get(i).getKdId());//������仰Ҳ��֪���Ǹ����
			if (pn != null) {
				anum = pn.getPnNumber();
				bnum = temp - anum;
			} else {
				anum = new BigDecimal(temp * a).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
				bnum = temp - anum;
			}
			num2 += temp;
			num2_ += anum;
			numDept.add(temp);
			auditResultService.update(arlist.get(i));
			TmpArray[i] = bnum * 1.3f + anum * 1.6f + 0.3f;//����
		}
		float base2 = (num2 - num2_) * 1.3f + num2_ * 1.6f + 0.3f * config.getSecond();
		for (int i = config.getThird() + config.getSecond(); i < arlist.size(); i++) {//һ��
			arlist.get(i).setArLevel(JXKH_AuditResult.LEVEL_ONE);
			//temp = auditConfigService.findDeptMember(arlist.get(i).getKdId());
			temp = auditConfigService.findCheckPeoByDeptId(arlist.get(i).getKdId());//���Ҳμӿ��˵�����
			JXKH_PointNumber pn = auditResultService.findPointNumber(year, arlist.get(i).getKdId());
			if (pn != null) {
				anum = pn.getPnNumber();
				bnum = temp - anum;
			} else { 
				anum = new BigDecimal(temp * a).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
				bnum = temp - anum;
			}
			num3 += temp;
			num3_ += anum;
			numDept.add(temp);
			auditResultService.update(arlist.get(i));
			TmpArray[i] = bnum * 1.0f + anum * 1.3f + 0.3f;
		}
		float base3 = (num3 - num3_) * 1.0f + num3_ * 1.3f + 0.3f * config.getFirst();//Ϊbase����
		float base = config.getAcMoney() / (base1 + base2 + base3);//һ���������
		for(int i = 0;i < config.getThird();i++){//������
			Long p = arlist.get(i).getKdId();
			checkedMember3 = auditConfigService.findpeoByDept(p);
			for(int j = 0;j<checkedMember3.size();j++){
				JXKH_AuditResult res = new JXKH_AuditResult();
				res.setKuId(checkedMember3.get(j).getKuId());//������Աid
				res.setKdId(p);//���ò���id
				res.setArType(JXKH_AuditResult.AR_WORK);//��Ա����(ҵ����Ա���;�Ϊ0)
				res.setArYear(year + "");//���û������
				auditResultService.save(res);
			}
			arlist.get().setArMoney(base * TmpArray[i]); 
			auditResultService.update(arlist.get(i));
		}
		
	for (int i = config.getThird(); i < config.getThird() + config.getSecond(); i++) {//��������
			Long p = arlist.get(i).getKdId();
			checkedMember2 = auditConfigService.findpeoByDept(p);
			for(int j = 0;j<checkedMember2.size();j++){
				JXKH_AuditResult res = new JXKH_AuditResult();
				res.setKuId(checkedMember2.get(j).getKuId());//������Աid
				res.setKdId(p);//���ò���id
				//res.setArType(JXKH_AuditResult.AR_WORK);//��Ա����(ҵ����Ա���;�Ϊ0)
				res.setArYear(year + "");//���û������
				//res.setArMoney(1.3f * base);//���ò��Ž���
				//res.setArLevel(JXKH_AuditResult.LEVEL_TWO);//���õ���
				auditResultService.save(res);
			}
		
			arlist.get(i).setArMoney(base * TmpArray[i]);
			auditResultService.update(arlist.get(i));
			checkedMember2.clear();
		}
		for (int i = config.getThird() + config.getSecond(); i < arlist.size(); i++) {//һ������
			Long p = arlist.get(i).getKdId();
			checkedMember1 = auditConfigService.findpeoByDept(p);
			for(int j = 0;j<checkedMember1.size();j++){
				JXKH_AuditResult res = new JXKH_AuditResult();
				res.setKuId(checkedMember1.get(j).getKuId());//������Աid
				res.setKdId(p);//���ò���id
				res.setArType(JXKH_AuditResult.AR_WORK);//��Ա����(ҵ����Ա���;�Ϊ0)
				res.setArYear(year + "");//���û������
				res.setArMoney(base);//���ò��Ž���
				res.setArLevel(JXKH_AuditResult.LEVEL_ONE);//���õ���
				auditResultService.save(res);
			}
			arlist.get(i).setArMoney(base * TmpArray[i]);
			auditResultService.update(arlist.get(i));
			checkedMember1.clear();
		}
		
		auditConfigService.update(config);
		List<JXKH_AuditResult> arlists = auditResultService.findDept(year);
		
		listbox.setModel(new ListModelList(arlists));
		listrow.setVisible(true);
		yearLb.setValue(year);
		FileOutputStream outputFile = null;
		try {
			outputFile = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			log.store(outputFile, "log");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			outputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void onClick$export() throws WriteException, IOException {//������ť�����Բ��ÿ���
		year = yearlist.getSelectYear();
		List<JXKH_AuditResult> list3 = auditResultService.findDept(year);
		if (list3 == null || list3.size() == 0) {
			try {
				Messagebox.show("�����ݣ���������", "����", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		} else {
			String fileName = year + "���ż�Ч���˽��.xls";
			String Title = year + "���ż�Ч���˽��";
			List<String> titlelist = new ArrayList<String>();
			titlelist.add("���");
			titlelist.add("��������");
			titlelist.add("��Ա��Ŀ");
			titlelist.add("���Ż���");// ���磺120/ƪ
			titlelist.add("�˾�����");
			titlelist.add("ҵ���ܷ�");
			titlelist.add("ͬ��������");
			titlelist.add("���ڵ�λ");
			titlelist.add("ϵ��");
			titlelist.add("���ý���(Ԫ)");
			String c[][] = new String[list3.size()][titlelist.size()];
			// ��SQL�ж�����
			for (int j = 0; j < list3.size(); j++) {
				JXKH_AuditResult res = list3.get(j);
				WkTDept dept = (WkTDept) auditConfigService.loadById(WkTDept.class, res.getKdId());
				c[j][0] = j + 1 + "";
				c[j][1] = dept.getKdName();
				int num = auditConfigService.findDeptMember(dept.getKdId());
				c[j][2] = num + "";
				c[j][3] = new DecimalFormat("#.00").format(res.getArScore()) + "";
				c[j][4] = new DecimalFormat("#.00").format(res.getArScore() / num) + "";
				c[j][5] = new DecimalFormat("#.00").format(res.getArIndex()) + "";
				BigDecimal bd = new BigDecimal(res.getArRate());
				c[j][6] = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%";
				c[j][7] = res.getArLevel() + "";
				if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_ONE) {
					c[j][8] = "1.0";
				} else if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_TWO) {
					c[j][8] = "1.3";
				} else if (res.getArLevel().shortValue() == JXKH_AuditResult.LEVEL_THREE) {
					c[j][8] = "1.6";
				}
				c[j][9] = new DecimalFormat("#.00").format(res.getArMoney()) + "";
			}
			ExportExcel ee = new ExportExcel();
			ee.exportExcel(fileName, Title, titlelist, list3.size(), c);
		}
	}
}
