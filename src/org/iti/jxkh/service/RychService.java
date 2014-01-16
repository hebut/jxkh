package org.iti.jxkh.service;

import java.util.List;
import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.entity.Jxkh_HonourFile;

import com.uniwin.basehs.service.BaseService;

public interface RychService extends BaseService {
	//根据KUID找该老师的荣誉称号实体
    public List  FindByKuid(long kuid);
    /**
     * 根据荣誉称号找出其所有的附件
     * @param Jxkh_Honour honour
     * @author WeifangWang
     */
    public List<Jxkh_HonourFile> findFileByHonour(Jxkh_Honour honour);
    /**
     * 根据荣誉的附件名称找到附件的路径
     * @param String fileName
     * @author WeifangWang
     */
    public List<Jxkh_HonourFile> findFileByFilename(String fileName);
    /**
     * 根据审核状态查找荣誉
     * @param state
     * @return
     */
    public List<Jxkh_Honour> findHonourByState(short state);
    
    public List<Jxkh_Honour> findAllHonour();
    /**
     * 根据查询条件查找符合条件的荣誉称号
     * @param name
     * @param index
     * @return
     */
    public List<Jxkh_Honour> findHonourByCondition(String name,int index);
}
