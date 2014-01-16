package org.iti.jxkh.waterprint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.iti.common.util.UploadUtil;

public class PDFWaterMark {
	public PdfStamper getPdfStamper(PdfReader _pdfReader, String _savePath) throws FileNotFoundException, DocumentException, IOException {
		return new PdfStamper(_pdfReader, new FileOutputStream(_savePath));
	}

	/*************************************************
	 * ʵ����PdfReader
	 * 
	 * @author Amaryllis
	 * @param _filePath
	 * @return
	 * @throws IOException
	 ************************************************/
	public PdfReader getPdfReader(String _filePath) throws IOException {
		return new PdfReader(_filePath);
	}

	/**
	 * ʵ����ͼƬ��ˮӡ��
	 * 
	 * @author Amaryllis
	 * @param _waterMark
	 * @return
	 * @throws BadElementException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public Image getImage(String _waterMark) throws BadElementException, MalformedURLException, IOException {
		return Image.getInstance(_waterMark);
	}

	public void printSlopesEffects(PdfStamper pdfStamper, Image image, String _waterMarkName, int _pageCount) throws DocumentException, IOException {
		PdfContentByte under;
		BaseFont base = BaseFont.createFont("c://windows/fonts/SIMHEI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); // ����������ʽ
		// int j = IsChineseOrEnglish.getLength(_waterMarkName);//��ȡˮӡ���ֵĳ���(�ֽ��볤��)
		int _j = _waterMarkName.length();// ��ȡˮӡ���ֵĳ���
		int j = _j;
		char c = 0;
		int rise = 0;
		for (int i = 1; i < _pageCount; i++) {
			// under = pdfStamper.getUnderContent(i);
			under = pdfStamper.getOverContent(i);
			// ���ͼƬ
			under.addImage(image);
			under.beginText();
			// under.setColorFill(Color.GRAY);
			under.setFontAndSize(base, 18);
			// ����ˮӡ����������б ��ʼ
			System.out.println(j);
			if (j >= 15) {
				rise = 630;
				under.setTextMatrix(150, -100);
				for (int k = 0; k < _j; k++) {
					under.setTextRise(rise);
					c = _waterMarkName.charAt(k);
					under.showText(c + "");
					rise -= 20;
				}
			} else {
				rise = 660;
				under.setTextMatrix(350, -150);
				for (int k = 0; k < _j; k++) {
					under.setTextRise(rise);
					c = _waterMarkName.charAt(k);
					under.showText(c + "");
					rise -= 18;
				}
			}
			// �������ý���
			under.endText();
			// ��һ��Բ
			// under.ellipse(250, 450, 350, 550);
			// under.setLineWidth(1f);
			// under.stroke();
		}
	}

	/*************************************************
	 * ��ӡˮӡ��ʽһ:��ͼƬ��������ʽ(PDF)
	 * 
	 * @author Amaryllis
	 * @param _filePath
	 * @param _savePath
	 * @param _waterMark
	 * @param waterMarkName
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 *************************************************/
	public boolean createPDFWaterMark(String _filePath, String _savePath, String _waterMark, String waterMarkName) throws IOException, DocumentException {
		PdfReader pdfReader = getPdfReader(_filePath);
		int pageCount = pdfReader.getNumberOfPages() + 1;// ��ȡpdfҳ��
		PdfStamper pdfStamper = getPdfStamper(pdfReader, _savePath);
		// ���ð�ȫ����(����)
		// pdfStamper.setEncryption(userPassWord.getBytes(), ownerPassWord
		// .getBytes(), permission, false);
		Image image = getImage(_waterMark);
		image.setAbsolutePosition(400, 300);
		image.scalePercent(100);
		// ��ÿҳ����ˮӡ
		printSlopesEffects(pdfStamper, image, waterMarkName, pageCount);
		pdfStamper.close();
		return true;
	}

	public static void main(String[] args) {
		File file = new File("d://");
		File[] dd = file.listFiles();
		for (int i = 0; i < dd.length; i++) {
			File _file = new File(String.valueOf(dd[i]));
			if (_file.isFile()) {
				int lastlength = Integer.parseInt(String.valueOf(_file.getName().length()));
				String dds = _file.getName().substring(_file.getName().lastIndexOf(".") + 1, lastlength);
//				int length_v = Integer.parseInt(String.valueOf(_file.getPath().length()));
//				String ddss = _file.getPath().substring(_file.getName().lastIndexOf("/") + 1, length_v);
				if (dds.toLowerCase().equalsIgnoreCase("pdf")) {
					try {
					
						new PDFWaterMark().createPDFWaterMark(dd[i].toString(), "d://_" + _file.getName(), "C://hebut.png", "�������Ϳ���");
//						new PDFWaterMark().createPDFWaterMark(dd[i].toString(), "d://water/" + _file.getName(), "C://hebut.png", "�������Ϳ���");
						System.out.println("��" + (i + 1) + "��PDF�ļ�" + _file.getName() + "����ˮӡ�ɹ�");
					} catch (IOException e) {
						e.printStackTrace();
					} catch (DocumentException e) {
						e.printStackTrace();
					}
					if(_file.exists())
					_file.delete();
					File filenew=new File("d://_" + _file.getName());
					filenew.renameTo(new File("d://" + _file.getName()));
				}
			}
		}
	}
}
