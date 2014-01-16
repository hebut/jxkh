package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhYjfx;

import com.uniwin.basehs.service.BaseService;

public interface JsxmService extends BaseService {
	
	/**
	 * <li>����������������Ŀid���û�id����Ŀ���ࣨ�������Ŀ��������Ŀ�ȣ�
	 * @param xmId ��Ŀid
	 * @param kuId �û�id
	 * @param Type ��Ŀ���ࣨ�涨��1���������Ŀ��2���������Ŀ��
	 * @return
	 */
	public GhJsxm findByXmidAndKuidAndType(Long xmId,Long kuId,Integer Type);

	/**������Ŀid������Ŀ���ࣨ�������Ŀ��������Ŀ�ȣ�
	 * <li>����������
	 * @param xmid
	 * @param Type
	 * @return
	 */
	public List findByXmidAndtype(Long xmid,Integer Type);
	/**
	 * 
	 * @param yjfx 
	 * @return ��ʦ�о�����
	 */
	public GhYjfx getYjfx(Long yjfx);
}
