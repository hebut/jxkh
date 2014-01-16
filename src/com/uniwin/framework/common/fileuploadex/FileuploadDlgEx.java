package com.uniwin.framework.common.fileuploadex;
import java.util.Iterator;
import java.util.List;

import org.zkoss.util.media.Media;
import org.zkoss.zk.au.AuScript;
import org.zkoss.zk.ui.ext.client.Updatable;
import org.zkoss.zul.Window;


@SuppressWarnings("deprecation")
public class FileuploadDlgEx extends Window{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Media[] _result;
	private boolean isCancel=true;
	/** Returns the result.
	 * @return an array of media (length >= 1), or null if nothing.
	 */
	public ImageUploadPackage getResult() {
		ImageUploadPackage iUP=new ImageUploadPackage(this._result,this.isCancel);
		return iUP;
		//return _result;
	}

	public void onCancel() {
		this.isCancel=true;
		onClose();
	}
	public void onClose() {
		response("endUpload", new AuScript(null, "zkau.endUpload()"));
		detach();
	}

	/** Sets the result.
	 */
	public void setResult(Media[] result) {
		_result = result;
	}

	public static Media[] parseResult(List<?> result) {
		if (result != null) {
			//we have to filter items that user doesn't specify any file
			for (Iterator<?> it = result.iterator(); it.hasNext();) {
				final Media media = (Media)it.next();
				if (media != null && media.inMemory() && media.isBinary()) {
					final String nm = media.getName();
					if (nm == null || nm.length() == 0) {
						final byte[] bs = media.getByteData();
						if (bs == null || bs.length == 0)
							it.remove(); //Upload is pressed without specifying a file
					}
				}
			}

			if (!result.isEmpty())
				return (Media[])result.toArray(new Media[result.size()]);
		}
		return null;
	}

	//-- ComponentCtrl --//
	protected Object newExtraCtrl() {
		return new ExtraCtrl();
	}
	/** A utility class to implement {@link #getExtraCtrl}.
	 * It is used only by component developers.
	 */
	protected class ExtraCtrl extends Window.ExtraCtrl implements Updatable {
		//-- Updatable --//
		/** Updates the result from the client.
		 * Callback by the system only. Don't invoke it directly.
		 *
		 * @param result a list of media instances, or null
		 */
		public void setResult(Object result) {
			FileuploadDlgEx.this.isCancel=false;
			FileuploadDlgEx.this.setResult(parseResult((List<?>)result));
		}
	}
}
