package org.iti.bysj.ui.base;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Window;
import org.zkoss.zul.Hbox;

public class ErrorWindow extends Window implements AfterCompose {

	Label errorMessage;
	Panel errorPanel;
	
	public void afterCompose() {
		Components.wireVariables(this, this);		
		errorPanel=new Panel();
		errorPanel.setFramable(true);		
		this.appendChild(errorPanel);
		Panelchildren panelchildren =new Panelchildren();
		errorPanel.appendChild(panelchildren);		
		Hbox fbox=new Hbox();
		fbox.setWidth("100%");
		fbox.setPack("center");
		panelchildren.appendChild(fbox);
		
		Hbox sbox=new Hbox();
		fbox.appendChild(sbox);
		errorMessage=new Label();
		sbox.appendChild(errorMessage);
	}

	public void setErrorMessage(String errorMessage){
		this.errorMessage.setValue(errorMessage);
	}

	@Override
	public void setTitle(String title) {
		errorPanel.setTitle(title);
	}

}
