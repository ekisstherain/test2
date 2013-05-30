package com.gdie.mail;

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

public class MainTest {
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

	private String mail_subject = "this is the subject of this test mail";

	private String mail_body = "this is the mail_body of this test mail";

	private String personalName = "我的邮件";

	public MainTest() {
	}

	/**
	 * 此段代码用来发送普通电子邮件
	 */
	public void send() throws Exception {
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
			message.setText(mail_body); // 设置邮件正文
			message.setHeader(mail_head_name, mail_head_value); // 设置邮件标题
			message.setSentDate(new Date()); // 设置邮件发送日期
			Address address = new InternetAddress(mail_from, personalName);
			message.setFrom(address); // 设置邮件发送者的地址
			Address toAddress = new InternetAddress(mail_to); // 设置邮件接收方的地址
			message.addRecipient(Message.RecipientType.TO, toAddress);
			Transport.send(message); // 发送邮件
			System.out.println("send ok!");
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

	public static void main(String[] args) {
		MainTest sendmail = new MainTest();
		try {
			sendmail.send();
		} catch (Exception ex) {
		}
	}

}