package org.iti.gh.ghtj.hzqkdc;

import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhRjzz;
import org.iti.xypt.ui.base.BaseWindow;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class RjzzxqWindow extends BaseWindow {
	Listbox rjzzlist,fmzllist;
	List rzlist=(List)Executions.getCurrent().getArg().get("rzlist");
	Short type=(Short)Executions.getCurrent().getArg().get("type");
	@Override
	public void initShow() {
		rjzzlist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhRjzz rjzz = (GhRjzz) arg1;

				//���
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//����
				Listcell c2 = new Listcell(rjzz.getRjName());

				//�״η���ʱ��
				Listcell c3 = new Listcell(rjzz.getRjFirtime());

				//�ǼǺ�
				Listcell c4 = new Listcell(rjzz.getRjRegisno());
				//�Ǽ�ʱ��
				Listcell c5 = new Listcell(rjzz.getRjTime());
				//�����ǼǺ�
				Listcell c6 = new Listcell(rjzz.getRjSoftno());
				Listcell c7 = new Listcell(rjzz.getUser().getKuName());
				

				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5); arg0.appendChild(c6);
				arg0.appendChild(c7); 
			}

		});
		fmzllist.setItemRenderer(new ListitemRenderer(){

			public void render(Listitem arg0, Object arg1) throws Exception {
				final GhFmzl fm = (GhFmzl) arg1;

				//���
				Listcell c1 = new Listcell(arg0.getIndex()+1+"");

				//����ר������
				Listcell c2 = new Listcell(fm.getFmMc());

				//��������
				Listcell c3 = new Listcell();
				if(fm.getFmTypes()!=null&&fm.getFmTypes().trim().equals("1")){
					c3.setLabel("����");
				}else if(fm.getFmTypes()!=null&&fm.getFmTypes().trim().equals("2")){
					c3.setLabel("ʵ������");
				}else if(fm.getFmTypes()!=null&&fm.getFmTypes().trim().equals("3")){
					c3.setLabel("������");
				}else{
					c3.setLabel("δ֪");
				}
				//��Ȩʱ��
				Listcell c4 = new Listcell(fm.getFmSj());
				//ר����Ȩ��
				Listcell c5 = new Listcell(fm.getFmSqh());
			   //������
				Listcell c6 = new Listcell(fm.getUser().getKuName());

				arg0.appendChild(c1); arg0.appendChild(c2); arg0.appendChild(c3);
				arg0.appendChild(c4); arg0.appendChild(c5);arg0.appendChild(c6);
			}

		});

	}

	@Override
	public void initWindow() {
		if(type==Short.parseShort("1")){
			rjzzlist.setModel(new ListModelList(rzlist));
			rjzzlist.setVisible(true);
			fmzllist.setVisible(false);
		}else if(type==Short.parseShort("2")){
			fmzllist.setModel(new ListModelList(rzlist));
			rjzzlist.setVisible(false);
			fmzllist.setVisible(true);
		}
	
	}

}
