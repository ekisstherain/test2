package com.gdie.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.time.DateUtils;

import com.gdie.task.BackUpTableTask;

/**
 * 数据库备份监听器
 * 
 * @author HHP
 * 
 */
public class BackUpDataBaseListener implements ServletContextListener {
	/**
	 * 每天的毫秒数
	 */
	public static final long PERIOD_DAY = DateUtils.MILLIS_PER_DAY;
	/**
	 * 一周内的毫秒数
	 */
	public static final long PERIOD_WEEK = PERIOD_DAY * 7;
	/**
	 * 无延迟
	 */
	public static final long NO_DELAY = 0;
	/**
	 * 定时器
	 */
	private Timer timer;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		timer.cancel(); // 定时器销毁
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// 定义定时器
		timer = new Timer("数据库表备份", true);
		// 启动备份任务,每月(4个星期)执行一次
		// timer.schedule(new BackUpTableTask(), getExecuteTime());
		timer.schedule(new BackUpTableTask(), NO_DELAY, PERIOD_DAY); // 每天备份一次
	}

	/**
	 * 获取每天定时执行时间
	 * 
	 * @return
	 */
	public Date getExecuteTime() {
		// 设置执行时间
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);// 每天
		// 定制每天的21:09:00执行，
		calendar.set(year, month, day, 00, 00, 00);
		Date date = calendar.getTime();
		return date;

	}

}
