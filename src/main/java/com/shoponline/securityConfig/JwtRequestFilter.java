package com.shoponline.securityConfig;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.shoponline.service.UserDetailServiceImpl;
import com.shoponline.util.JwtCookieUtil;
import com.shoponline.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * This filter is intercept to all the incoming requests & provide access to 
 * api's as per the user authorities
 * 
 * @author yaman
 *
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	/**
	 * this service class handles operation linked to Jwt Utils
	 */
	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * This service class handles operation linked to User
	 */
	@Autowired
	private UserDetailServiceImpl userDetailService;

	/**
	 * This service class handle operation linked to store and delete jwt token to browser cookie
	 */
	@Autowired
	private JwtCookieUtil jwtCookieUtil;

	String[] unsecuredRequests = new String[] { "/", "/home", "/about", "/contact-us", "/signin", "/register",
			"/registerNewUser", "/men/{menuId}", "/women/{menuId}", "/kids/{menuId}", "/home&living/{menuId}", "/cart",
			"/category/{categoryId}", "/product/{productId}", "/authenticate", "/registerNewUser" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String username = null;
		Optional<Cookie> tokenCookie = jwtCookieUtil.getTokenCookieByName(request, JwtCookieUtil.ID_TOKEN_COOKIE_NAME);
		// System.out.println(tokenCookie.getValue());

		if (tokenCookie.isPresent()) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + tokenCookie.get().getValue());
			try {
				username = jwtUtil.getUsernameFromToken(tokenCookie.get().getValue());
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userDetailService.loadUserByUsername(username);

			if (jwtUtil.validateToken(tokenCookie.get().getValue(), userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);

	}

}
