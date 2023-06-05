package com.ungdungso.model;
public class Email {
	public static final String smtpServer = "smtp server";
	public static final String userName = "vhgiang15@gmail.com";
	public static final String pass = "tbnunkhkwohdtkac";
	public static final String emailFrom = "vhgiang15@gmail.com";
    private String emailTo;
    private String emailCC;
    private  String emailSubject;
    private  String emailText;
	public Email(String emailTo, String emailCC, String emailSubject, String emailText) {
		super();
		this.emailTo = emailTo;
		this.emailCC = emailCC;
		this.emailSubject = emailSubject;
		this.emailText = emailText;
	}
	public Email() {
		super();
	}
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getEmailCC() {
		return emailCC;
	}
	public void setEmailCC(String emailCC) {
		this.emailCC = emailCC;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getEmailText() {
		return emailText;
	}
	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}
	

}
