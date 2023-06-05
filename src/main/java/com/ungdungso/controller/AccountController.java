package com.ungdungso.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ungdungso.model.Account;
import com.ungdungso.model.Email;
import com.ungdungso.service.AccountService;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Controller
public class AccountController {
	@Autowired
	AccountService accountService;

	@RequestMapping("/admin-user-manage")
	public ModelAndView userManage() {
		ModelAndView model= new ModelAndView("admin/user-manage");
		return model;
	}
	
	
	@RequestMapping("/xskt/admin-search-user")
	public ModelAndView searchUser(@RequestParam(value = "username", defaultValue = "*") String username,
			@RequestParam(value = "phone", defaultValue = "*") String phone,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		ModelAndView model = new ModelAndView("admin/user-result-search");
		List<Account> list = accountService.getByUserOrPhone(username, phone, page);
		int totalAccount = accountService.countAllAccount();
		int totalPage = totalAccount / 5;
		if (totalAccount % 5 != 0) {
			totalPage++;
		}
		if (!username.equals("*") || !phone.equals("*")) {
			totalPage = 1;
		}
		model.addObject("listAccount", list);
		model.addObject("totalPage", totalPage);
		model.addObject("indexPage", page);
		return model;

	}

	@RequestMapping("/xskt/admin-delete-user")
	public ModelAndView deleteUser(@RequestParam("userName") String username) {
		ModelAndView model = new ModelAndView("admin/alert");
		if (accountService.isAdmin(username)) {
			model.addObject("show", "show");
			model.addObject("message", "User Admin không được quyền xóa");
			return model;
		} else {
			accountService.deteleByUserName(username);
			model.addObject("show", "show");
			model.addObject("message", "Xóa thất bại. Đã chuyển user sang trạng thái Disable");
		}
		return model;
	}

	
	@RequestMapping("/xskt/admin-change-permission")
	public void changePermission(@RequestParam("user") String username) {
		Account account = accountService.getByUser(username);
		if(account.getPermission().equals("ROLE_ADMIN")) {
			account.setPermission("ROLE_USER");		
		} else {account.setPermission("ROLE_ADMIN");} 
		accountService.saveAccount(account);   
	}
	
	@RequestMapping("/xskt/admin-change-status")
	public void changeStatus(@RequestParam("user") String username) {
		Account account = accountService.getByUser(username);
		if(account.getAvailable().equals("enable")) {
			account.setAvailable("disable");	
		} else {account.setAvailable("enable");} 
		accountService.saveAccount(account);   
	}
	
	
	
	// Chức năng đổi mật khẩu áp dụng cho tất cả các user
	@RequestMapping("/change-pass" )
	public ModelAndView changePass(@RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass,
			@RequestParam("conPass") String conPass, Authentication authentication,HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView("admin/alert");
		User userDetails = (User) authentication.getPrincipal();
		String user = userDetails.getUsername();
		Account account = accountService.getByUser(user);
		if (!BCrypt.checkpw(oldPass,account.getPass())) {
			model.addObject("show", "show");
			model.addObject("message", "Mật khẩu cũ không đúng");
			return model;
		}
		if (!newPass.equals(conPass)) {
			model.addObject("show", "show");
			model.addObject("message", "Mật khẩu xác nhận không trùng khớp");
			return model;
		}

		else {
			String newhashPass = BCrypt.hashpw(newPass, BCrypt.gensalt(12));
			account.setPass(newhashPass);
			accountService.saveAccount(account);
			model.addObject("show", "show");
			model.addObject("message", "Thay đổi mật khẩu thành công");
			return model;
		}
	}

	//Chức năng reset pass áp dụng cho user có quyền Admin
	@RequestMapping("/xskt/admin-reset-pass")
	public void resetPass(@RequestParam("userName") String username)
			throws MessagingException, UnsupportedEncodingException {
		Account account = accountService.getByUser(username);
		String pass = RandomStringUtils.randomAlphanumeric(8);
		String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt(12));
		account.setPass(hashPass);
		accountService.saveAccount(account);
		Email emailObject = new Email();
		emailObject.setEmailSubject("Thông tin mật khẩu mới");
		emailObject.setEmailText("Mật khẩu mới của user "+account.getUserName()+" là: " + pass + " .Vui lòng thực hiện đôi mật khẩu");
		emailObject.setEmailTo(account.getMail());

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		props.put("mail.smtp.port", "587"); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Email.userName, Email.pass);
			}
		};
		Session session = Session.getInstance(props, auth);
		MimeMessage msg = new MimeMessage(session);
		// set message headers
		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");

		msg.setFrom(new InternetAddress(Email.emailFrom, "NoReply-JD"));
		msg.setReplyTo(InternetAddress.parse(Email.emailFrom, false));
		msg.setSubject(emailObject.getEmailSubject(), "UTF-8");
		msg.setText(emailObject.getEmailText(), "UTF-8");
		msg.setSentDate(new Date());
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailObject.getEmailTo(), false));
		Transport.send(msg);
	}

	//Chức năng reset Pass áp dụng được cho tất cả các user, chỉ reset pass cho chính mình
	@RequestMapping("/reset-pass")
	public ModelAndView resetPassUser(@RequestParam("email") String email)
			throws MessagingException, UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("client/alert");
		System.out.println(email);
		if (accountService.isExistUserMail(email)) {
			Account account = accountService.getByEmail(email);
			if(account.getAvailable().equalsIgnoreCase("disable"))
			{
				model.addObject("show", "show");
				model.addObject("message", "User bị khóa. Vui lòng liên hệ Admin");
				return model;
				
			}
			String pass = RandomStringUtils.randomAlphanumeric(8);
			String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt(12));
			account.setPass(hashPass);
			accountService.saveAccount(account);
			Email emailObject = new Email();
			emailObject.setEmailSubject("Thông tin mật khẩu mới");
			emailObject.setEmailText("Mật khẩu mới của user "+account.getUserName()+" là: " + pass + " .Vui lòng thực hiện đôi mật khẩu");
			emailObject.setEmailTo(account.getMail());

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
			props.put("mail.smtp.port", "587"); // TLS Port
			props.put("mail.smtp.auth", "true"); // enable authentication
			props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS
			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Email.userName, Email.pass);
				}
			};
			Session session = Session.getInstance(props, auth);
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(Email.emailFrom, "NoReply-JD"));
			msg.setReplyTo(InternetAddress.parse(Email.emailFrom, false));
			msg.setSubject(emailObject.getEmailSubject(), "UTF-8");
			msg.setText(emailObject.getEmailText(), "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailObject.getEmailTo(), false));
			Transport.send(msg);
			model.addObject("show", "show");
			model.addObject("message", "Mật khẩu mới đã gửi vào email");
			return model;
		} else {
			model.addObject("show", "show");
			model.addObject("message", "Email không tồn tại");
			return model;
		}
	}

}
