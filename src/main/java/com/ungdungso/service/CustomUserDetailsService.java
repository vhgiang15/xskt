package com.ungdungso.service;
import com.ungdungso.model.Account;
import java.util.List; 
import java.util.ArrayList; 
import com.ungdungso.repository.AccountRepository;
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    	System.out.println("......................");
        //User user = accountRepository.findByEmail(usernameOrEmail);
    	Account account= accountRepository.findById(usernameOrEmail).get();
        if (account != null) {           
        	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();  
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(account.getPermission());  
            authorities.add(authority);
            return new org.springframework.security.core.userdetails.User(account.getUserName()
                    ,account.getPass(),authorities);
                   
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }
}