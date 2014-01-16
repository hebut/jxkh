package org.iti.jxkh.business.award;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.service.JxkhFruitService;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ChooseFruitWin extends Window implements AfterCompose{

	/**
	 * @author ZhangyuGuang 
	 */
	private static final long serialVersionUID = 8835650511563492055L;
	private Textbox name;
	private Listbox fruitListbox;
	private JxkhFruitService jxkhFruitService;
	private Jxkh_Fruit fruit;

	@Override
	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		fruitListbox.setItemRenderer(new FruitRenderer());
		initListbox();
		
	}
	
	public void initListbox(){
		List<Jxkh_Fruit> fruitList=jxkhFruitService.findFruitByKuLid(name.getValue(), null, null, null);
		fruitListbox.setModel(new ListModelList(fruitList));
	}
	
	public class FruitRenderer implements ListitemRenderer {

		@Override
		public void render(Listitem item, Object data) throws Exception {
			Jxkh_Fruit fruit=(Jxkh_Fruit)data;
			item.setValue(fruit);
			Listcell c0=new Listcell("");
			Listcell c1=new Listcell(item.getIndex()+1+"");
			Listcell c2=new Listcell(fruit.getName());
			Listcell c3=new Listcell("");
			if(fruit.getAppraiseRank()!=null)
				c3=new Listcell(fruit.getAppraiseRank().getKbName());
			Listcell c4=new Listcell("");
			if(fruit.getAcceptRank()!=null)
				c4=new Listcell(fruit.getAcceptRank().getKbName());
			item.appendChild(c0);item.appendChild(c1);item.appendChild(c2);
			item.appendChild(c3);item.appendChild(c4);
		}
	}
	
	public void onClick$choose(){
		if(fruitListbox.getSelectedItems()==null||fruitListbox.getSelectedItems().size()<=0){
			try {
				Messagebox.show("请选择成果！", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		Events.postEvent(Events.ON_CHANGE, this, null);
		Listitem it=fruitListbox.getSelectedItem();
		fruit=(Jxkh_Fruit)it.getValue();	
	}
	public void onClick$query(){
		initListbox();
	}
	
	public void onClick$close() {
		this.detach();
	}
	
	public Jxkh_Fruit getFruit(){
		return fruit;
	}
	
	public void setFruit(Jxkh_Fruit fruit){
		this.fruit=fruit;
	}
	
	

}
