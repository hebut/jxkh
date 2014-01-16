package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.asm.personal.entity.DocTree;
import com.uniwin.basehs.service.BaseService;

public interface DocTreeService extends BaseService {
	public DocTree getRoot(Long dtkuid);
	public List<DocTree> getChildernDocTree(Long dtpid);
}
