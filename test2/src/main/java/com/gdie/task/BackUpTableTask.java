package com.gdie.task;

import org.apache.log4j.Logger;

import com.gdie.common.MailUtil;
import com.gdie.common.SpringUtil;
import com.gdie.entity.History;
import com.gdie.service.IHistoryService;
import com.gdie.service.impl.HistoryService;

import java.util.TimerTask;

public class BackUpTableTask extends TimerTask {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BackUpTableTask.class);

	private static boolean isRunning = false;

	private IHistoryService historyService = SpringUtil.getBean(HistoryService.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (!isRunning) {
			isRunning = true;
			logger.debug("开始执行任务..."); // 开始任务
			// working add what you want to do
			MailUtil.sendEmail(TableBackUp.getTableBackUpSql());
			logger.debug("执行任务完成..."); // 任务完成
			// 创建日志
			History history = History.getInstance();
			history.setDetail("备份数据库!");
			history.setOperateUser("系统定时器");
			historyService.insert(history);
			isRunning = false;
		} else {
			logger.debug("上一次任务执行还未结束..."); // 上一次任务执行还未结束
		}
	}
}
