package com.ungdungso.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.ungdungso.service.AccountService;


@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	AccountService accountService;
    
	@Override
	public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, Authentication authentication)
			throws IOException, jakarta.servlet.ServletException {
		User userDetails=(User) authentication.getPrincipal();
		String tempString=userDetails.getAuthorities().toString();
        String redirectURL = request.getContextPath();       
        if (tempString.equals("[ROLE_ADMIN]")) {
            redirectURL = "admin-dashboard"; 
            //redirectURL = "admin-dashboard";
        } else if (tempString.equals("[ROLE_USER]")) {
            redirectURL = "index";
        }  
        response.sendRedirect(redirectURL);		
	}

}
