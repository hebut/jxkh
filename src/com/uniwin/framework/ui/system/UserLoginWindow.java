package com.uniwin.framework.ui.system;

/**
 * <li>�û���¼���,��Ӧҳ��admin/alogin.zul
 * @author DaLei
 * @2010-3-15
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.uniwin.common.util.EncodeUtil;
import com.uniwin.framework.entity.WkTSite;
import com.uniwin.framework.service.WebsiteService;

public class UserLoginWindow extends Window implements AfterCompose {
	
	Textbox j_username, j_password;
	Datebox endtime;
	
	Button resetButton, resignButton;
	WebsiteService websiteService;
	

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		List list =websiteService.findAll(WkTSite.class);
		WkTSite sit=(WkTSite) list.get(0);
		if(sit.getKsEndtime()!=null&&sit.getKsEndtime().trim().length()!=0){
			endtime.setValue(new Date(EncodeUtil.decodeByDES(sit.getKsEndtime())));
		}
		
	}
	private static String getMacOnWindow() {
        String s = "";
        try {
            String s1 = "ipconfig /all";
            Process process = Runtime.getRuntime().exec(s1);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String nextLine;
            for (String line = bufferedreader.readLine(); line != null; line = nextLine) {
                nextLine = bufferedreader.readLine();
                if (line.indexOf("Physical Address") <= 0) {
                    continue;
                }
                int i = line.indexOf("Physical Address") + 36;
                s = line.substring(i);
                break;
            }
            bufferedreader.close();
            process.waitFor();
        } catch (Exception exception) {
            s = "";
        }
        return s.trim();
    }



	public void onClick$resignButton() throws Exception {
		if (j_username.getValue().length() == 0) {
			return;
		}
		//if (cpa.getValue().equalsIgnoreCase(cpatb.getValue())) {
		String lid=EncodeUtil.encodeByDES(j_username.getValue().toString().trim());
		String password = EncodeUtil.encodeByDES(j_password.getValue().toString().trim());
		String et=EncodeUtil.encodeByDES(endtime.getValue().getTime()).trim();
		List ulist = websiteService.findByLidAndPassword(lid,password);
		if (ulist == null || ulist.size() == 0) {
				throw new WrongValueException(j_username, "�û������������");
		}else{
			String mac=EncodeUtil.encodeByDES(getMacOnWindow().trim());
			List slist=websiteService.findByLidAndPasswordAndMac(lid, password, mac);
			if(slist.size()!=0){
				WkTSite sit=(WkTSite) slist.get(0);
				sit.setKsEndtime(et);
				websiteService.update(sit);
				Messagebox.show("���û���ע�� ", "��ʾ", Messagebox.OK,Messagebox.INFORMATION);
			}else{
				WkTSite site=(WkTSite) ulist.get(0);
				site.setKsMac(mac);
				site.setKsEndtime(et);
				websiteService.update(site);
				Messagebox.show("ע��ɹ� ", "��ʾ", Messagebox.OK,Messagebox.INFORMATION);
			}
		}
		//}
	}

	// �����¼�
	public void onClick$resetButton() {
		j_username.setRawValue(null);
		j_password.setRawValue(null);
		//cpatb.setRawValue(null);
		//cpa.randomValue();
	}

}
