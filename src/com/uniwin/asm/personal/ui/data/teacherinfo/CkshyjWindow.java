package com.uniwin.asm.personal.ui.data.teacherinfo;

import org.iti.gh.entity.GhCg;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhHylw;
import org.iti.gh.entity.GhJlhz;
import org.iti.gh.entity.GhJxbg;
import org.iti.gh.entity.GhPkpy;
import org.iti.gh.entity.GhPx;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.entity.GhXm;
import org.iti.gh.entity.GhXshy;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.CgService;
import org.iti.gh.service.FmzlService;
import org.iti.gh.service.HylwService;
import org.iti.gh.service.JlhzService;
import org.iti.gh.service.JszzService;
import org.iti.gh.service.JxbgService;
import org.iti.gh.service.PkpyService;
import org.iti.gh.service.PxService;
import org.iti.gh.service.QklwService;
import org.iti.gh.service.RjzzService;
import org.iti.gh.service.XmService;
import org.iti.gh.service.XshyService;
import org.iti.gh.service.ZzService;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;

public class CkshyjWindow extends BaseWindow {

	Integer type = (Integer) Executions.getCurrent().getArg().get("type");
	Long id = (Long) Executions.getCurrent().getArg().get("id");
	Label sxmc, xmmc, reason, audituser;
	XmService xmService;
	CgService cgService;
	FmzlService fmzlService;
	RjzzService rjzzService;
	PkpyService pkpyService;
	PxService pxService;
	XshyService xshyService;
	JxbgService jxbgService;
	JlhzService jlhzService;
	ZzService zzService;
	JszzService jszzService;
	HylwService hylwService;
	QklwService qklwService;

	@Override
	public void initShow() {

	}

	@Override
	public void initWindow() {
		switch (type) {
		case 1:
			sxmc.setValue("科研项目名称");
			GhXm xm = (GhXm) xmService.get(GhXm.class, id);
			xmmc.setValue(xm.getKyMc());
			reason.setValue(xm.getReason() != null ? xm.getReason() : "");
			audituser.setValue(xm.getAuditUser().getKuName());
			break;
		case 2:
			sxmc.setValue("获奖成果名称");
			GhCg cg = (GhCg) cgService.get(GhCg.class, id);
			xmmc.setValue(cg.getKyMc());
			reason.setValue(cg.getReason() != null ? cg.getReason() : "");
			audituser.setValue(cg.getAuditUser().getKuName());
			break;
		case 3:
			sxmc.setValue("会议论文名称");
			GhHylw lwzl=(GhHylw)hylwService.get(GhHylw.class, id);
			xmmc.setValue(lwzl.getLwMc());
			reason.setValue(lwzl.getAuditReason() != null ? lwzl.getAuditReason() : "");
			audituser.setValue(lwzl.getAuditUser().getKuName());
			break;
		case 4:
			sxmc.setValue("期刊论文名称");
			GhQklw qklw=(GhQklw)qklwService.get(GhQklw.class, id);
			xmmc.setValue(qklw.getLwMc());
			reason.setValue(qklw.getAuditReason() != null ? qklw.getAuditReason() : "");
			audituser.setValue(qklw.getAuditUser().getKuName());
			break;
		case 5:
			sxmc.setValue("学术专著名称");
			GhZz xszz=(GhZz)zzService.get(GhZz.class, id);
			xmmc.setValue(xszz.getZzMc());
			reason.setValue(xszz.getAuditReason() != null ? xszz.getAuditReason() : "");
			audituser.setValue(xszz.getAuditUser().getKuName());
			break;
		case 6:
			sxmc.setValue("软件著作名称");
			GhRjzz rjzz=(GhRjzz)rjzzService.get(GhRjzz.class, id);
			xmmc.setValue(rjzz.getRjName());
			reason.setValue(rjzz.getReason() != null ? rjzz.getReason() : "");
			audituser.setValue(rjzz.getAuditUser().getKuName());
			break;
		case 7:
			sxmc.setValue("发明专利名称");
			GhFmzl fmzl=(GhFmzl)fmzlService.get(GhFmzl.class, id);
			xmmc.setValue(fmzl.getFmMc());
			reason.setValue(fmzl.getReason() != null ? fmzl.getReason() : "");
			audituser.setValue(fmzl.getAuditUser().getKuName());
			break;
		case 10:	
			sxmc.setValue("教研项目名称");
			GhXm jyxm = (GhXm) xmService.get(GhXm.class, id);
			xmmc.setValue(jyxm.getKyMc());
			reason.setValue(jyxm.getReason() != null ? jyxm.getReason() : "");
			audituser.setValue(jyxm.getAuditUser().getKuName());
			break;
		case 11:	
			sxmc.setValue("获奖成果名称");
			GhCg jycg = (GhCg) cgService.get(GhCg.class, id);
			xmmc.setValue(jycg.getKyMc());
			reason.setValue(jycg.getReason() != null ? jycg.getReason() : "");
			audituser.setValue(jycg.getAuditUser().getKuName());
			break;
		case 12:	
			sxmc.setValue("教研项目名称");
			GhPkpy pkpy = (GhPkpy) pkpyService.get(GhPkpy.class, id);
			xmmc.setValue(pkpy.getPkName());
			reason.setValue(pkpy.getReason() != null ? pkpy.getReason() : "");
			audituser.setValue(pkpy.getAuditUser().getKuName());
			break;
		case 13:	
			sxmc.setValue("会议论文名称");
			GhHylw Lwzl = (GhHylw) hylwService.get(GhHylw.class, id);
			xmmc.setValue(Lwzl.getLwMc());
			reason.setValue(Lwzl.getAuditReason() != null ? Lwzl.getAuditReason() : "");
			audituser.setValue(Lwzl.getAuditUser().getKuName());
			break;
		case 14:	
			sxmc.setValue("期刊论文名称");
			GhQklw qkLw = (GhQklw) qklwService.get(GhQklw.class, id);
			xmmc.setValue(qkLw.getLwMc());
			reason.setValue(qkLw.getAuditReason() != null ? qkLw.getAuditReason() : "");
			audituser.setValue(qkLw.getAuditUser().getKuName());
			break;
		case 15:	
			sxmc.setValue("教材名称");
			GhZz jc= (GhZz) zzService.get(GhZz.class, id);
			xmmc.setValue(jc.getZzMc());
			reason.setValue(jc.getAuditReason() != null ? jc.getAuditReason() : "");
			audituser.setValue(jc.getAuditUser().getKuName());
			break;
		case 16:	
			sxmc.setValue("培训项目名称");
			GhPx px= (GhPx) pxService.get(GhPx.class, id);
			xmmc.setValue(px.getPxMc());
			reason.setValue(px.getReason() != null ? px.getReason() : "");
			audituser.setValue(px.getAuditUser().getKuName());
			break;
		case 19:
			//学术交流会议
			sxmc.setValue("会议名称");
			GhXshy xs= (GhXshy) xshyService.get(GhXshy.class, id);
			xmmc.setValue(xs.getHyMc());
			reason.setValue(xs.getReason() != null ? xs.getReason() : "");
			audituser.setValue(xs.getAuditUser().getKuName());
			break;
		case 20:
			//讲学报告
			sxmc.setValue("报告名称");
			GhJxbg bg= (GhJxbg) jxbgService.get(GhJxbg.class, id);
			xmmc.setValue(bg.getJxBgmc());
			reason.setValue(bg.getReason() != null ? bg.getReason() : "");
			audituser.setValue(bg.getAuditUser().getKuName());
			break;
		case 21:
			//交流合作
			sxmc.setValue("交流合作会议名称");
			GhJlhz jl= (GhJlhz) jlhzService.get(GhJlhz.class, id);
			xmmc.setValue(jl.getHzMc());
			reason.setValue(jl.getReason() != null ? jl.getReason() : "");
			audituser.setValue(jl.getAuditUser().getKuName());
			break;
			
		}
	}
	public void onClick$close(){
		this.detach();
	}
}
