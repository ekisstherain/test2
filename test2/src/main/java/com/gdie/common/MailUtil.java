package com.gdie.common;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MailUtil.class);

	// 邮箱服务器
	private String host = "smtp.126.com";
	// 这个是你的邮箱用户名
	private String username = "account498482873";
	// 你的邮箱密码
	private String password = "498482873";

	private String mail_head_name = "this is head of this mail";

	private String mail_head_value = "this is head of this mail";

	private String mail_to = "498482873@qq.com";

	private String mail_from = "account498482873@126.com";

	private String mail_subject = "数据库备份邮件(" + new Date().toLocaleString() + ")";

	private String personalName = "数据库备份邮件";

	public MailUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 此段代码用来发送普通电子邮件
	 */
	public void send(String content) throws Exception {
		try {
			Properties props = new Properties(); // 获取系统环境
			Authenticator auth = new Email_Autherticator(); // 进行邮件服务器用户认证
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, auth);
			// 设置session,和邮件服务器进行通讯。
			MimeMessage message = new MimeMessage(session);
			// message.setContent("foobar, "application/x-foobar"); // 设置邮件格式
			message.setSubject(mail_subject); // 设置邮件主题
			message.setText(content); // 设置邮件正文
			message.setHeader(mail_head_name, mail_head_value); // 设置邮件标题
			message.setSentDate(new Date()); // 设置邮件发送日期
			Address address = new InternetAddress(mail_from, personalName);
			message.setFrom(address); // 设置邮件发送者的地址
			Address toAddress = new InternetAddress(mail_to); // 设置邮件接收方的地址
			message.addRecipient(Message.RecipientType.TO, toAddress);
			Transport.send(message); // 发送邮件
			logger.debug("send ok!");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * 用来进行服务器对用户的认证
	 */
	public class Email_Autherticator extends Authenticator {
		public Email_Autherticator() {
			super();
		}

		public Email_Autherticator(String user, String pwd) {
			super();
			username = user;
			password = pwd;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param content
	 */
	public static void sendEmail(String content) {
		try {
			new MailUtil().send(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MailUtil sendmail = new MailUtil();
		try {
			sendmail.send("hahahaha");
		} catch (Exception ex) {
		}
	}
}
