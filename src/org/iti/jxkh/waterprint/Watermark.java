package org.iti.jxkh.waterprint;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.iti.common.util.UploadUtil;

public class Watermark {
	String iconPath = "c:/hebut.png";
	String tempPath = "c:/temp/";
	String[] extension = { "png", "jpg", "gif", "bmp" };

	public Watermark() {
	}

	public void markImage() {
		try {
			decompressFile("c:/1.zip", tempPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("开始压缩");
		try {
			compressFloderChangeToZip(tempPath, "C:/", "1_.zip");
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = new File(tempPath);
		deleteFile(file);
		System.out.println("完成");
	}

	public void markImage(String iconPath, String srcImgPath, String targerPath, Integer degree) {
		OutputStream os = null;
		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = buffImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // 设置对线段的锯齿状边缘处理
			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			if (null != degree) {
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}
			ImageIcon imgIcon = new ImageIcon(iconPath);
			Image img = imgIcon.getImage();
			float alpha = 0.2f; // 透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			g.drawImage(img, 150, 300, null); // 表示水印图片的位置
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
//			int i =targerPath.lastIndexOf("/");
//			String filePath =targerPath.substring(0, i);
//			 File fruitFile = new File(filePath);
//			 if(!fruitFile.exists()){
//			 fruitFile.mkdirs();
//			 }
			os = new FileOutputStream(targerPath);
			ImageIO.write(buffImg, "JPG", os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					this.deleteFile(files[i]);
				}
			}
			file.delete();
		}
	}

	private void decompressFile(String zipFilePath, String releasePath) throws IOException {
		ZipFile zipFile = new ZipFile(zipFilePath);
		@SuppressWarnings("unchecked")
		Enumeration<ZipEntry> enumeration = zipFile.getEntries();
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		ZipEntry zipEntry = null;
		String zipEntryNameStr = "";
		String[] zipEntryNameArray = null;
		while (enumeration.hasMoreElements()) {
			zipEntry = enumeration.nextElement();
			zipEntryNameStr = zipEntry.getName();
			System.out.println("正在解压" + zipEntryNameStr);
			boolean flag = true;
			for (int i = 0; i < extension.length; i++) {
				if (zipEntryNameStr.toLowerCase().trim().endsWith(extension[i].trim())) {
					flag = false;
					break;
				}
			}
			if (flag) {
				continue;
			}
			zipEntryNameArray = zipEntryNameStr.split("/");
			String path = releasePath;
			File root = new File(releasePath);
			if (!root.exists()) {
				root.mkdir();
			}
			for (int i = 0; i < zipEntryNameArray.length; i++) {
				if (i < zipEntryNameArray.length - 1) {
					path = path + File.separator + zipEntryNameArray[i];
					new File(path).mkdir();
				} else {
					if (zipEntryNameStr.endsWith(File.separator)) {
						new File(releasePath + zipEntryNameStr).mkdir();
					} else {
						inputStream = zipFile.getInputStream(zipEntry);
						fileOutputStream = new FileOutputStream(new File(releasePath + "tmp"));
						byte[] buf = new byte[1024];
						int len;
						while ((len = inputStream.read(buf)) > 0) {
							fileOutputStream.write(buf, 0, len);
						}
						inputStream.close();
						fileOutputStream.close();
						System.out.println("正在为" + zipEntryNameStr + "加水印");
						markImage(iconPath, releasePath + "tmp", releasePath + zipEntryNameStr, -30);
						File file = new File(releasePath + "tmp");
						file.delete();
						System.out.println(zipEntryNameStr + "已加上水印");
					}
				}
			}
		}
		zipFile.close();
	}

	private boolean compressFloderChangeToZip(String compressedFilePath, String zipFileRootPath, String zipFileName) throws IOException {
		File compressedFile = new File(compressedFilePath);
		if ("".equalsIgnoreCase(zipFileName)) {
			zipFileName = "." + compressedFilePath.replaceFirst(".*\\.", "");
		}
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileRootPath + zipFileName));
		String base = "";
		boolean result = compressFloderChangeToZip(compressedFile, zipOutputStream, base);
		zipOutputStream.close();
		return result;
	}

	private boolean compressFloderChangeToZip(File compressedFile, ZipOutputStream zipOutputStream, String base) throws IOException {
		FileInputStream fileInputStream = null;
		try {
			if (compressedFile.isDirectory()) {
				File[] childrenCompressedFileList = compressedFile.listFiles();
				base = base.length() == 0 ? "" : base + File.separator;
				for (int i = 0; i < childrenCompressedFileList.length; i++) {
					compressFloderChangeToZip(childrenCompressedFileList[i], zipOutputStream, base + childrenCompressedFileList[i].getName());
				}
			} else {
				if ("".equalsIgnoreCase(base)) {
					base = compressedFile.getName();
				}
				zipOutputStream.putNextEntry(new ZipEntry(base));
				fileInputStream = new FileInputStream(compressedFile);
				int b;
				while ((b = fileInputStream.read()) != -1) {
					zipOutputStream.write(b);
				}
				fileInputStream.close();
			}
			return true;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		}
	}
}
