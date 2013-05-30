package com.gdie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdie.dao.HistoryMapper;
import com.gdie.entity.History;
import com.gdie.service.IHistoryService;

@Service
public class HistoryService extends BaseService<History, String> implements IHistoryService {
	private HistoryMapper historyMapper;

	@Autowired
	public void setHistoryMapper(HistoryMapper historyMapper) {
		this.historyMapper = historyMapper;
		this.mapper = historyMapper;
	}
}
