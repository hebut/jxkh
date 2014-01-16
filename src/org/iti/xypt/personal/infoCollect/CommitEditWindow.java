package org.iti.xypt.personal.infoCollect;
/**
 * ������鿴
 */
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;



public class CommitEditWindow extends Window implements AfterCompose {

	
	private static final long serialVersionUID = -2413959345167806957L;
    //�༭����İ�ť���
	Button edit;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		edit.addEventListener(Events.ON_CLICK,new EventListener() {
			public void onEvent(Event event) throws Exception {
				Window w=(Window) Executions.createComponents("/admin/content/task/newtask.zul", null,null);
				w.doHighlighted();
			}
		});
	}
}
