package org.iti.jxgl.service;

import java.util.List;

import org.iti.jxgl.entity.JxCoursetype;
import org.iti.jxgl.entity.JxviewCoursetype;

import com.uniwin.basehs.service.BaseService;

public interface CoursetypeService extends BaseService {
	
	/**
	 * <li>�������������һ�����⣨�γ̴��ࣩ�����ƺ���Ӧ�ı��룬��Ҫ���ڳ�ʼ����ѯ����
	 * 
	 * @return List
	 * @author wx
	 * @2010-7-9
	 */
	public List getCoursetypeFirst();

	/**
	 * <li>�������������ݶ�����������ƣ����Ҷ��������Ӧ��ID��ӳ���ϵ��
	 * 
	 * @param name
	 *            ������������
	 * @return String
	 * @author wx
	 * @2010-7-9
	 */
	public String FindCtidbyName(String name);

	/**
	 * <li>��������������һ�������������Ķ��������б�
	 * 
	 * @param firsttype
	 *            һ��������
	 * @return List
	 * @author wx
	 * @2010-7-9
	 */
	public List FindsecondtypebyFirst(String firsttype);

	/**
	 * <li>������������������Ų���ĳһ�����
	 * 
	 * @param Jctid
	 * @return һ���γ�������
	 * @author FengXinhong 2010-7-20
	 */

    public JxCoursetype findByJctid(String Jctid);
	/**
	 * <li>������������������Ų���ĳһ�����ĺ���
	 * @param Jctid
	 * @return  һ���γ�������
	 * @author FengXinhong
	 * 2010-7-20
	 */
    public List getChildByJctFid(String jctFid);
	/**
	 * <li>�����������õ��µ�jctid
	 * @param 
	 * @return  һ������Jctid+1
	 * @author ����
	 * 2010-8-11
	 */
    public String getNewJctid();
	/**
	 * <li>��������������
	 * @param һ�������ţ�������������
	 * @return  �����򷵻�true�����򷵻�false
	 * @author ����
	 * 2010-8-11
	 */
    public boolean CheckDuplicat(String jctFid,String jctName);

	/**
	 * <li>��������:���excel��Ŀγ̴���Ϳγ�С�����ݿ����Ƿ����
	 * @param һ���������ƣ�������������
	 * @return  �������򷵻ر������ƣ����򷵻�null
	 * @author ����
	 * 2010-8-11
	 */
    public String[] CheckExit(String firstname,String secondName);
    public List checkScheExcel();
	/**
	 * <li>��������:ɾ��jctFid��������еĿγ����
	 * @param һ������id
	 * @return  void
	 * @author ����
	 * 2010-8-11
	 */
    public void DelTypeByFirst(String jctFid);
    /**
	 * <li>��������������
	 * @param �����ţ���������
	 * @return  ������������
	 * @author ����
	 * 2010-10-25
	 */
    public String CheckDubiaoti(String jctFid,String jctName);
    /**
	 * <li>����������������������
	 * @param ���ͺ�
	 * @return  �����򷵻�true�����򷵻�false
	 * @author ����
	 * 2010-8-11
	 */
    public String findprename(String jcid);
}
