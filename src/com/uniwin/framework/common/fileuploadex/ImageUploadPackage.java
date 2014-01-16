package com.uniwin.framework.common.fileuploadex;
import org.zkoss.util.media.Media;


public class ImageUploadPackage {
	private Media[] media;
	private boolean isCancel;
	public ImageUploadPackage(Media[] Media,boolean IsCancel)
	{
		this.media=Media;
		this.isCancel=IsCancel;
	}
	public void setPackage(Media[] Media,boolean IsCancel)
	{
		this.media=Media;
		this.isCancel=IsCancel;
	}
	public Media getMedia0()
	{
		if(this.media==null)
			return null;
		return this.media[0];
	}
	public Media[] getMediaAll()
	{
		if(this.media==null)
			return null;
		return this.media;
	}
	public boolean getIsCancel()
	{
		return this.isCancel;
	}
}
