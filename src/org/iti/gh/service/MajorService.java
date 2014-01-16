package org.iti.gh.service;

import java.util.List;

import org.python.antlr.PythonParser.list_for_return;

import com.uniwin.basehs.service.BaseService;

public interface MajorService extends BaseService {

	public List findFirstmajor(); 
	
	public List findByPtid(String pzyid);
	
	public List findByZyid(String zyid);
}
