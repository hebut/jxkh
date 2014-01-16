package com.iti.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.zkoss.zk.ui.WrongValueException;

public class ZipUtils
{

    private static final int BUFFER = 8192;

    /**
     * <li>功能描述：从指定路径中读取文件名称
     * 
     * @param filePath
     *            指定文件的存放路径
     * @return
     */
    public static String getFileName(String filePath)
    {
        int index = filePath.indexOf(".");
        return filePath.substring(0, index);
    }

    /**
     * <li>功能描述：根据指定路径获取要压缩的文件
     * 
     * @param filePath
     *            指定要压缩文件的存放路径
     */
    public static void zip(String sourceFilePath)
    {
        File fileDir = new File(sourceFilePath);
        if (fileDir.exists())
        {
            doZip(fileDir);
        } else
        {
            throw new WrongValueException("不能找到指定文件！");
        }
    }

    /**
     * <li>功能描述：根据指定路径获取要解压的文件
     * 
     * @param filePath
     *            指定要解压文件的存放路径
     */
    public static void unZip(String zipFilePath)
    {
        File fileDir = new File(zipFilePath);
        if (fileDir.exists())
        {
            doUnZip(fileDir);
        } else
        {
            throw new WrongValueException("不能找到指定文件！");
        }
    }

    /**
     * <li>功能描述：压缩指定的文件
     * 
     * @param file
     *            指定要压缩的文件
     */
    public static void doZip(File file)
    {
        List<File> fileList = new ArrayList<File>();
        List<File> allFiles = (ArrayList<File>) searchFiles(file.getPath(),
                fileList);

        Object[] fileArray = allFiles.toArray();

        BufferedInputStream in = null;
        FileInputStream fis = null;
        ZipOutputStream zos = null;
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file.getParent() + File.separator
                    + file.getName() + ".zip");
            zos = new ZipOutputStream(new BufferedOutputStream(fos, BUFFER));

            zos.setLevel(9);

            byte[] data = new byte[BUFFER];

            for (int i = 0; i < fileArray.length; i++)
            {
                // 设置压缩文件入口entry,为被读取的文件创建压缩条目
                File tempFile = new File(fileArray[i].toString());
                String rootStr = file.getPath();
                String entryStr = null;
                // entry以相对路径的形式设置。
                // 分别处理单个文件/目录的entry
                if (rootStr.equals(tempFile.getPath()))
                {
                    entryStr = file.getName() + "/" + tempFile.getName();
                } else
                {
                    entryStr = file.getName()
                            + "/"
                            + tempFile.getPath().substring(
                                    (rootStr + File.separator).length());
                }

                ZipEntry entry = new ZipEntry(entryStr);
                zos.putNextEntry(entry);

                fis = new FileInputStream(tempFile);
                in = new BufferedInputStream(fis, BUFFER);

                int count;
                while ((count = in.read(data, 0, BUFFER)) != -1)
                {
                    zos.write(data, 0, count);
                }
            }

        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
                if (zos != null)
                {
                    zos.close();
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * <li>功能描述：压缩指定的文件列表，并以指定的名称保存到指定的目录下
     * 
     * @param filePath 指定存储路径
     * @param fileList 欲压缩的文件列表
     * @param zipName 压缩文件名
     */
    public static void doZipFiles(String filePath, List<File> fileList, String zipName)
    {
        Object[] fileArray = fileList.toArray();

        BufferedInputStream in = null;
        FileInputStream fis = null;
        ZipOutputStream zos = null;
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(filePath + File.separator + zipName + ".zip");
            zos = new ZipOutputStream(new BufferedOutputStream(fos, BUFFER));

            zos.setLevel(9);

            byte[] data = new byte[BUFFER];

            for (int i = 0; i < fileArray.length; i++)
            {
                // 设置压缩文件入口entry,为被读取的文件创建压缩条目
                File tempFile = new File(fileArray[i].toString());
                String entryStr = null;
                // entry以相对路径的形式设置。
                // 分别处理单个文件/目录的entry
                entryStr = zipName + File.separator + tempFile.getName();
//                if (filePath.equals(tempFile.getPath()))
//                {
//                    entryStr = zipName + "/" + tempFile.getName();
//                } else
//                {
//                    entryStr = zipName + "/" + tempFile.getPath().substring((filePath + File.separator).length());
//                }
                ZipEntry entry = new ZipEntry(entryStr);
                zos.putNextEntry(entry);

                fis = new FileInputStream(tempFile);
                in = new BufferedInputStream(fis, BUFFER);

                int count;
                while ((count = in.read(data, 0, BUFFER)) != -1)
                {
                    zos.write(data, 0, count);
                }
            }

        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
                if (zos != null)
                {
                    zos.close();
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * <li>功能描述：解压指定的压缩文件
     * 
     * @param file
     *            指定要解压的文件
     */
    public static void doUnZip(File zipFile)
    {
        String targetPath = zipFile.getParentFile().getPath();
        try
        {
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry entry = null;

            try
            {
                while ((entry = (ZipEntry) zis.getNextEntry()) != null)
                {
                    String zipPath = entry.getName();
                    if (entry.isDirectory())
                    {
                        File zipFolder = new File(targetPath + File.separator
                                + zipPath);
                        if (!zipFolder.exists())
                        {
                            zipFolder.mkdirs();
                        }

                    } else
                    {
                        File file = new File(targetPath + File.separator
                                + zipPath);
                        if (!file.exists())
                        {
                            File pathDir = file.getParentFile();
                            pathDir.mkdirs();
                            file.createNewFile();
                        }
                        FileOutputStream fos = new FileOutputStream(file);
                        int bread;
                        while ((bread = zis.read()) != -1)
                        {
                            fos.write(bread);
                        }
                        fos.close();
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    if (zis != null)
                    {
                        zis.close();
                    }
                    if (fis != null)
                    {
                        fis.close();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            }

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * <li>功能描述：查看指定路径中是否存在与解压后同名的文件
     * 
     * @param zipPath
     *            指定要解压文件的路径
     * @return
     */
    public static Boolean folderIsExists(String zipPath)
    {
        File zipFile = new File(zipPath);
        if (zipFile.exists())
        {
            String targetPath = zipFile.getParentFile().getPath();
            try
            {
                FileInputStream fis = new FileInputStream(zipFile);
                ZipInputStream zis = new ZipInputStream(fis);
                ZipEntry entry = (ZipEntry) zis.getNextEntry();
                String entryPath = entry.getName();
                String cssFolderName = entryPath.substring(0, entryPath
                        .indexOf("/"));
                File cssFolder = new File(targetPath + "\\" + cssFolderName);
                if (cssFolder.exists())
                {
                    return true;
                } else
                {
                    return false;
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        } else
        {
            throw new WrongValueException("不能找到指定文件！");
        }
        return null;
    }

    /**
     * <li>功能描述：获取指定文件夹中的所有文件
     * 
     * @param sourceFilePath
     *            指定文件夹的路径
     * @param fileList
     * @return fileList
     */
    public static List<File> searchFiles(String sourceFilePath,
            List<File> fileList)
    {
        File fileDir = new File(sourceFilePath);
        if (fileDir.isDirectory())
        {
            File[] subfiles = fileDir.listFiles();
            for (int i = 0; i < subfiles.length; i++)
            {
                searchFiles(subfiles[i].getPath(), fileList);
            }
        } else
        {
            fileList.add(fileDir);
        }

        return fileList;
    }

    public static String newDir(File file, String entryName)
    {

        String rootDir = getFileName(file.getPath());
        int index = entryName.lastIndexOf("\\");
        if (index != -1)
        {

            String path = entryName.substring(0, index);
            new File(rootDir + file.separator + path).mkdirs();

        } else
        {
            new File(rootDir).mkdirs();
        }
        return entryName;

    }
}
