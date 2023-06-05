package com.ungdungso.service;

import java.util.List;
import com.ungdungso.model.Account;


public interface AccountService {
	public List<Account> getAll();
	public Account getByUser(String userNam);
	public Account getByEmail(String mail);
	public List<Account> getByUserOrPhone(String userName, String phone, int page);
	public void deteleByUserName(String userName);
	public void addAccount(Account account);
	public int countAllAccount();
	public void saveAccount(Account account);
	public boolean isAdmin(String userName);
	public boolean isExistUserName(String userName);
	public boolean isExistUserMail(String mail);
	public boolean isExistUserPhone(String phone);
	public boolean isEnableUser(String userName);
	public int totalUser();
	public int countUserEnable();
}
