package com.gdie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdie.dao.StatisticsMapper;
import com.gdie.entity.Statistics;
import com.gdie.service.IStatisticsService;

@Service
public class StatisticsService extends BaseService<Statistics, String> implements IStatisticsService {
	private StatisticsMapper statisticsMapper;

	@Autowired
	public void setUserMapper(StatisticsMapper statisticsMapper) {
		this.statisticsMapper = statisticsMapper;
		this.mapper = statisticsMapper;
	}

}
