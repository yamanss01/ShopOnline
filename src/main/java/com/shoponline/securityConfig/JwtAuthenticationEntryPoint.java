package com.shoponline.securityConfig;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String encodedRedirectURL = ((HttpServletResponse) response).encodeRedirectURL(
				request.getContextPath() + "/signin");
		response.setHeader("Location", encodedRedirectURL);
		response.sendRedirect(encodedRedirectURL);
		
	}

}
