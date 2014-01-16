package org.iti.jxkh.service;

import java.util.List;
import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.entity.Jxkh_HonourFile;

import com.uniwin.basehs.service.BaseService;

public interface RychService extends BaseService {
	//����KUID�Ҹ���ʦ�������ƺ�ʵ��
    public List  FindByKuid(long kuid);
    /**
     * ���������ƺ��ҳ������еĸ���
     * @param Jxkh_Honour honour
     * @author WeifangWang
     */
    public List<Jxkh_HonourFile> findFileByHonour(Jxkh_Honour honour);
    /**
     * ���������ĸ��������ҵ�������·��
     * @param String fileName
     * @author WeifangWang
     */
    public List<Jxkh_HonourFile> findFileByFilename(String fileName);
    /**
     * �������״̬��������
     * @param state
     * @return
     */
    public List<Jxkh_Honour> findHonourByState(short state);
    
    public List<Jxkh_Honour> findAllHonour();
    /**
     * ���ݲ�ѯ�������ҷ��������������ƺ�
     * @param name
     * @param index
     * @return
     */
    public List<Jxkh_Honour> findHonourByCondition(String name,int index);
}
