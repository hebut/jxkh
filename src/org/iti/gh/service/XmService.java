package org.iti.gh.service;

import java.util.List;
import org.iti.gh.entity.GhXm;
import com.uniwin.basehs.service.BaseService;

public interface XmService extends BaseService {

	/**
	 * �����û��ı�������������е��ѻ�ÿ��гɹ�/Ŀǰ�е���Ŀ/����ɽ���/���ڽ��н������
	 * @param kuId
	 * @return
	 */
	List findByKuid(Long kuId,Short lx);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ���л��߽������
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid,Short lx,Short state);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ���л��߽��������ͬ����Ŀ�ϲ�
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid,Short lx,String progress,Short state);
	/**
	 * ������Ŀ���������������е��ѻ�ÿ��гɹ�/Ŀǰ�е���Ŀ/����ɽ���/���ڽ��н������
	 * @param kuId
	 * @return
	 */
	List findByMc(String name,Short lx);
	
	/**
	 * <li>����������������Ŀ������������
	 *  
	 * @param kyid  ��Ŀ�������
	 *
	 * @return ��Ŀ�б�
	 */
	List findByKyid(Long kyid);
	/**
	 * �Ǳ����ĵ�������Ŀ
	 * @param kuId
	 * @param lx
	 * @param lwType
	 * @param jsxmType
	 * @param lwid
	 * @return
	 */
	List findByKuidAndLwidNotZz(Long kuId,Short lx,Short lwType, Integer jsxmType,Long lwid);

	/**
	 * <li>������������ѯĳһ���û�����Ĳ��Ǹ��û���ӵ���Ŀ����
	 * @return
	 */
	List findAllname(Long kuId,String kuname,Short lx,Integer type);
	/**
	 * <li>������������ѯĳһ���û�����Ĳ��Ǹ��û���ӵ�δ����Ŀ����
	 * @param kuId
	 * @param kuname
	 * @param lx
	 * @param hj
	 * @return
	 */
	List findAllXmname(Long kuid,String kuname,Integer lx);
	
	/**
	 * <li>���������������û��ı�������������е��ѻ�ÿ��гɹ�/Ŀǰ�е���Ŀ/����ɽ���/���ڽ��н������
	 * @param kuid
	 * @param type
	 * @return
	 */
	List findByKuidAndType(Long kuid,Integer type);
	public List findCountByKuidAndType(Long kuid,Integer type);
	
	/**
	 * <li>���������������û��ı�������������е��ѻ�ÿ��гɹ�/Ŀǰ�е���Ŀ/����ɽ���/���ڽ��н������
	 * @param kuid �û�ID
	 * @param type �ѻ�ÿ��гɹ�/Ŀǰ�е���Ŀ/����ɽ���/���ڽ��н������
	 * @param proType 1-����2-����
	 * @return
	 */
	List findByKuidAndTypeAndKyclass(Long kuid,Integer type,String kyClass);
	
	/**
	 * <li>���������������û��ı�������������е��ѻ�ÿ��гɹ�/Ŀǰ�е���Ŀ/����ɽ���/���ڽ��н������
	 * @param kuid �û�ID
	 * @param type �ѻ�ÿ��гɹ�/Ŀǰ�е���Ŀ/����ɽ���/���ڽ��н������
	 * @param proType 1-����2-����
	 * @param xmid ��ĿID
	 * @return
	 */
	List findByKuidAndTypeAndKyclassAndXmid(String kyClass,Long xmid);
	/**
	 * ���ݵ�λid����Ŀ���ͣ�������Ŀ�������״̬��ѯĳһ��λ�������
	 * @param kdid
	 * @param lx
	 * @param State
	 * @return
	 */
	
	public List findByKdidAndLxAndTypeAndState(Long kdid,Short lx,Short State);
	
	 /**
	  * <li>�������ơ�������Ŀ��Դ�ж���Ŀ�Ƿ�Ψһ
	  * @param xm
	  * @param lx
	  * @param ly
	  * @return
	  */
	public boolean CheckOnlyOne(String xm, Short lx,String ly,String fzr);
	/**
	 * <li>�������ơ�������Ŀ��Դ��ѯ��Ŀif return true��˵��û����xm�����ơ����ͣ�����/���У�����Դ��ͬ��
	 * ����false��˵����
	 * @param xm
	 * @param mc
	 * @param lx
	 * @param ly
	 * @return
	 */
	public boolean findByNameAndLxAndLy(GhXm xm,String mc, Short lx,String ly,String fzr);
	/**
	 * 
	 * @param mc
	 * @param lx
	 * @param ly
	 * @param fzr
	 * @return
	 */
	public GhXm findByNameAndLyAndLxAndFzr(String mc, Short lx,String ly,String fzr);
	/**
	 * ������������δ������ϵ����Ŀ
	 * @param mc
	 * @param lx
	 * @param ly
	 * @param fzr
	 * @return
	 */
	public List findByMcAndLyAndFzr(Long kuid,String mc,Short lx,String ly,String fzr,Integer type);
	
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @param type
	 * @param jb
	 * @return
	 */
	public List findByKdidAndYearAndKuidAndTypeAndJb(Long kdid,String year,Long kuid,Integer type,String jb,String hx);
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @param type
	 * @param jb
	 * @param hx
	 * @return
	 */
	public List findQtByKdidAndYearAndKuid(Long kdid,String year,Long kuid,Integer type);
	
	/**
     * <li>������������ȡ���в���ҳ��
     * @param pageNum ��ǰҳ��
     * @param pageSize ÿҳ����
     */
    public List<GhXm> getListPageByKuidAndTypeAndKyclass(Long kuid,Integer type,String kyClass,int pageNum, int pageSize) ;
    
    /**
     * <li>������������ȡ���в���ҳ��
     * @param pageNum ��ǰҳ��
     * @param pageSize ÿҳ����
     */
    public List<GhXm> getListPageByKuidAndType(Long kuid,Integer type,int pageNum, int pageSize);

    
    /**
	 * <li>���������������û��ı�š�����ͳ�������е��ѻ�ÿ��гɹ�/Ŀǰ�е���Ŀ/����ɽ���/���ڽ��н������
	 * @param kuid �û�ID
	 * @param type �ѻ�ÿ��гɹ�/Ŀǰ�е���Ŀ/����ɽ���/���ڽ��н������
	 * @param proType 1-����2-����
	 * @return
	 */
	public List findCountByKuidAndTypeAndKyclass(Long kuid,Integer type,String kyClass);
	
	 /**
	 * <li>���������������û��ı�š����͡�����ͳ�������е��ѻ�ÿ��гɹ�/Ŀǰ�е���Ŀ/����ɽ���/���ڽ��н������
	 * @param kuid �û�ID
	 * @param type �ѻ�ÿ��гɹ�/Ŀǰ�е���Ŀ/����ɽ���/���ڽ��н������
	 * @param proType 2-���Ҽ���3-ʡ����
	 * @return
	 */
	public List findCountByKuidAndTypeAndKyScale(Long kuid,Integer type,String kyScale);
	
   /**
    * @param pageNum ��ǰҳ��
    * @param pageSize  ÿҳ����
    * @return  ��Ա��Ŀ�б��ҳ
    */
    public List findByKuidAndType(Long kuid, Integer type,int pageNum, int pageSize ) ;
	public List findXmByKuidAndType(Long kuid,Integer type);
	
	/**
	 * �����û�ID����Ŀ����͹���������ѯ����Ŀ�ļ�¼����
	 * @param kuid
	 * @param kyScale
	 * @param gx
	 * @return
	 */
	public int getHjCountByKuidGx(Long kuid,String kyScale, int gx);

	/**
	 * �����û�ID����Ŀ����͹���������ѯ��Ŀ��¼����
	 * @param kuid
	 * @param kyScale
	 * @param gx
	 * @return
	 */
	public int getCountByKuidGx(Long kuid,String kyScale, int gx);
	
	/**
	 * ������ĿID��ѯ�б�
	 * @param ids
	 * @param lx
	 * @return
	 */
	public List<GhXm> findByKyIdsType(String ids);
}
