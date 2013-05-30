package com.gdie.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdie.entity.History;
import com.gdie.service.IHistoryService;
import com.gdie.vo.Order;
import com.gdie.vo.PageData;

@RequestMapping("/history")
@Controller
public class HistoryController extends BaseController {
	@Autowired
	private IHistoryService historyService;

	@RequestMapping("/getHistoryList")
	@ResponseBody
	public PageData<History> getHistoryList(String pageIndex) {
		int index = 1;
		if (!StringUtils.isEmpty(pageIndex)) {
			try {
				index = Integer.parseInt(pageIndex);
			} catch (Exception ex) {
				index = 1;
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", Order.desc("operate_time"));
		PageData<History> pageData = historyService.query(map, index);
		return pageData;
	}
}
