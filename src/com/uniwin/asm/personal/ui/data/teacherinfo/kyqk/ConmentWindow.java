package com.uniwin.asm.personal.ui.data.teacherinfo.kyqk;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import org.iti.bysj.ui.base.InnerButton;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Configuration;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.uniwin.asm.personal.entity.QzMessage;
import com.uniwin.asm.personal.entity.QzSubject;
import com.uniwin.asm.personal.service.QzMessageService;
import com.uniwin.asm.personal.service.RelationService;
import com.uniwin.asm.personal.service.SubjectService;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTUser;

public class ConmentWindow extends Window implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Rows rows;
	String Path = "";

	public void afterCompose() {
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		LoadMessgegrid();
	}

	private void LoadMessgegrid() {
		Row firstRow = new Row();
		firstRow.setStyle("padding:1;border-width:medium 0px 1px;background-color:#E8F3FD");
		{
//			Vbox firstLeft = new Vbox();
//			firstLeft.setAlign("center");
//			{
//				Image image = new Image();
//				image.setSrc("/admin/image/default.jpg");
//				image.setWidth("100px");
//				image.setHeight("100px");
//				Label qzname = new Label("董永峰");
//				qzname.setStyle("font-family:'华文楷体';font-size:15px;color:red");
//				Label zctime = new Label("计算机科学与技术");
//				zctime.setStyle("color:gray");
//				firstLeft.appendChild(image);
//				firstLeft.appendChild(qzname);
//				firstLeft.appendChild(zctime);
//			}
//			Vbox firstRight = new Vbox();
//			{
//				Label fbtime = new Label("资料上传于：2011-05-08 13:12:22" );
//				fbtime.setStyle("color:gray");
//				Label sjtitle = new Label("主   题：网页信息抽取技术综述");
//				sjtitle.setStyle("font-family:'宋体';font-size:14px");
//				Hbox hbox = new Hbox();
//				hbox.setAlign("center");
//				{
//					Label extra = new Label("附件：");
//					extra.setStyle("font-family:'宋体';font-size:14px");
//					Listbox list=new Listbox();
//					list.setRows(1);
//					list.setHeight("20px");
//					list.setWidth("150px");
//					Button download = new Button("下载");
//					hbox.appendChild(extra);
//					hbox.appendChild(list);
//					hbox.appendChild(download);
//				}
//				Html contents = new Html();
//				contents.setContent("请大家下载查看");
//				firstRight.appendChild(fbtime);
//				firstRight.appendChild(new Separator());
//				firstRight.appendChild(sjtitle);
//				firstRight.appendChild(hbox);
//				firstRight.appendChild(new Separator());
//				firstRight.appendChild(contents);
//			}
//			firstRow.appendChild(firstLeft);
//			firstRow.appendChild(firstRight);
//		}
//		rows.appendChild(firstRow);
				Row row = new Row();
				row.setStyle("padding:0;border-width:medium 0px 1px;background-color:#FFFFFF");
				// 用户信息
				Vbox mainLeft = new Vbox();
				mainLeft.setAlign("center");
				{
					String path = this.getDesktop().getWebApp().getRealPath("/admin/image/default.jpg");
					File file = new File(path);
					Image image = new Image();
					image.setWidth("100px");
					image.setHeight("100px");
					image.setSrc("/admin/image/default.jpg");
					Label name = new Label();
					Label dept = new Label();
					name.setValue("赵啦");
					name.setStyle("font-family:'宋体';font-size:15px;color:red");
					dept.setValue("计算机技术");
					dept.setStyle("color:gray");
					mainLeft.appendChild(image);
					mainLeft.appendChild(name);
					mainLeft.appendChild(dept);
				}
				row.appendChild(mainLeft);
				// 回复的消息信息
				Hbox mainRight = new Hbox();
				{
				Vbox vbox = new Vbox();
					vbox.setWidth("550px");
					{
						Hbox uphbox = new Hbox();						
						{
							Label time = new Label("评论于：2011-05-10 19:12:11");
							time.setStyle("color:gray");
							time.setWidth("400px");
						InnerButton delete = new InnerButton("删除");
							delete.setStyle("color:red");
							Space space = new Space();
							uphbox.appendChild(time);
							uphbox.appendChild(space);
						}
						Hbox downhbox = new Hbox();
						downhbox.setAlign("center");
						{
						}
						Html contents = new Html();
						contents.setContent("而二哥发电公司热个染色");
						vbox.appendChild(uphbox);
						vbox.appendChild(new Separator());
						vbox.appendChild(downhbox);
						vbox.appendChild(new Separator());
						vbox.appendChild(contents);
					}
					mainRight.appendChild(vbox);
				}
				row.appendChild(mainRight);
			rows.appendChild(row);
		Row lastRow = new Row();
		lastRow.setStyle("background-color:#FFFFFF");
		lastRow.setSpans("2,0");
		Vbox vbox = new Vbox();
		final FCKeditor editor = new FCKeditor();
		editor.setHeight("260px");
		editor.setToolbarSet("Basic");
		vbox.setWidth("100%");
		{
			Separator separator = new Separator();
			separator.setBar(true);
			Grid grid = new Grid();
			{
				Columns columns = new Columns();
				{
					Column leftColumn = new Column();
					leftColumn.setWidth("10%");
					leftColumn.setAlign("center");
					Column rightColumn = new Column();
					columns.appendChild(leftColumn);
					columns.appendChild(rightColumn);
				}
				grid.appendChild(columns);
				Rows rows = new Rows();
				{
					Row upRow = new Row();
					{
						Label text = new Label("发表内容");
						upRow.appendChild(text);
						upRow.appendChild(editor);
					}
//					Row middleRow = new Row();
//					middleRow.setStyle("background-color:#FFFFFF");
//					{
//						Label text = new Label("上传附件");
//						Hbox hbox = new Hbox();
//						{
//							final Label filename = new Label();
//							final Button upload = new Button("上传");
//							final Button delete = new Button("删除");
//							upload.setVisible(true);
//							delete.setVisible(false);
//							upload.addEventListener(Events.ON_CLICK, new EventListener() {
//								public void onEvent(Event arg0) throws Exception {
//									//upload(filename, upload, delete);
//								}
//							});
//							delete.addEventListener(Events.ON_CLICK, new EventListener() {
//								public void onEvent(Event arg0) throws Exception {
//									//delete(filename, upload, delete);
//								}
//							});
//							hbox.appendChild(filename);
//							hbox.appendChild(upload);
//							hbox.appendChild(delete);
//						}
//						middleRow.appendChild(text);
//						middleRow.appendChild(hbox);
//					}
					Row downRow = new Row();
					downRow.setSpans("2,0");
					{
						Hbox hbox = new Hbox();
						{
							Button submit = new Button("发表评论");   
							Button close = new Button("关闭页面");
							submit.addEventListener(Events.ON_CLICK, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									//submit(editor);
								}
							});
							close.addEventListener(Events.ON_CLICK, new EventListener() {
								public void onEvent(Event arg0) throws Exception {
									//close();
								}
							});
							hbox.appendChild(submit);
							hbox.appendChild(close);
						}
						downRow.appendChild(hbox);
					}
					rows.appendChild(upRow);
//					rows.appendChild(middleRow);
					rows.appendChild(downRow);
				}
				grid.appendChild(rows);
			}
			vbox.appendChild(separator);
			vbox.appendChild(grid);
		}
		lastRow.appendChild(vbox);
		rows.appendChild(lastRow);
	}
	}

}
