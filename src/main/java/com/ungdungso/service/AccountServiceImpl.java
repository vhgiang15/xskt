package com.ungdungso.service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ungdungso.model.Account;
import com.ungdungso.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;
	

	public List<Account> getAll() {
		return (List<Account>) accountRepository.findAll();
	}

	@SuppressWarnings("deprecation")
	public Account getByUser(String userNam) {
		//return accountRepository.findOne(userNam);
		return accountRepository.getById(userNam);
	}

	public void deteleByUserName(String userName) {
		Account account = accountRepository.findById(userName).get();
		account.setAvailable("Disable");
		accountRepository.save(account);
	}

	public boolean isAdmin(String userName) {
		if (accountRepository.getPermission(userName).equals("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistUserName(String userName) {
		if(accountRepository.queryGetUserName(userName)!=null)
		{
			return true;	
		}
		return false;
	}

	@Override
	public boolean isExistUserMail(String mail) {
		if(accountRepository.queryGetMail(mail)!=null)
		{
			return true;	
		}
		return false;
	}

	@Override
	public boolean isExistUserPhone(String phone) {
		if(accountRepository.queryGetPhone(phone)!=null)
		{
			return true;	
		}
		return false;
	}

	@Override
	public void addAccount(Account account) {
		accountRepository.save(account);
		
	}

	@Override
	public boolean isEnableUser(String userName) {
		Account account=accountRepository.findById(userName).get();
		
		return (account.getAvailable().equals("enable"));
	}

	@Override
	public List<Account> getByUserOrPhone(String userName, String phone, int page) {
		if(userName.equals("*")&&phone.equals("*")) {
			List<Account> list= accountRepository.querygetAll(page*5-5);
			return list;		
		}
		else {
			List<Account> list= accountRepository.querygetAccountByUserOrPhone(userName, phone, page*5-5);
			return list;
		}	
	}

	@Override
	public int countAllAccount() {
		return (int) accountRepository.count();
	}

	@Override
	public void saveAccount(Account account) {
		accountRepository.save(account);
		
	}

	@Override
	public Account getByEmail(String mail) {
		return accountRepository.queryGetByEmail(mail);
	}

	@Override
	public int totalUser() {
		// TODO Auto-generated method stub
		return accountRepository.queryTotlalUser();
	}

	@Override
	public int countUserEnable() {
		// TODO Auto-generated method stub
		return accountRepository.querycountUserEnable();
	}

	
	
	


}
