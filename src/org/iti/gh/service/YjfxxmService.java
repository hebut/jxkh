package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhYjfxxm;

import com.uniwin.basehs.service.BaseService;

public interface YjfxxmService extends BaseService {
	List<GhYjfxxm>  findByGyId(Long gyid);

}
