package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.asm.personal.entity.DocList;
import com.uniwin.basehs.service.BaseService;

public interface DocListService extends BaseService {
	public List<DocList> findByDtid(Long dtid);
	public List<DocList> findByDtnameAndKuid(String name,Long kuid);
	
}
