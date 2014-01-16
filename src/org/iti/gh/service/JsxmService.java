package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhYjfx;

import com.uniwin.basehs.service.BaseService;

public interface JsxmService extends BaseService {
	
	/**
	 * <li>功能描述：根据项目id，用户id和项目种类（如科研项目，教研项目等）
	 * @param xmId 项目id
	 * @param kuId 用户id
	 * @param Type 项目种类（规定：1代表科研项目，2代表教研项目）
	 * @return
	 */
	public GhJsxm findByXmidAndKuidAndType(Long xmId,Long kuId,Integer Type);

	/**根据项目id，和项目种类（如科研项目，教研项目等）
	 * <li>功能描述：
	 * @param xmid
	 * @param Type
	 * @return
	 */
	public List findByXmidAndtype(Long xmid,Integer Type);
	/**
	 * 
	 * @param yjfx 
	 * @return 教师研究方向
	 */
	public GhYjfx getYjfx(Long yjfx);
}
