package com.shoponline.controller;

import com.shoponline.exception.CredentialValidationException;
import com.shoponline.exception.InvalidAuthStringException;
import com.shoponline.model.User;
import com.shoponline.service.UserService;
import com.shoponline.util.JwtCookieUtil;
import com.shoponline.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.shoponline.model.JwtRequest;
import com.shoponline.model.JwtResponse;
import com.shoponline.service.UserDetailServiceImpl;

import java.util.Set;

/**
 * This controller handles the operation linked with authentication of user
 * 
 * @author yaman
 *
 */
@RestController
@CrossOrigin
public class JwtController {

	private Logger log = LoggerFactory.getLogger(JwtController.class.getName());

	/**
	 * this service method handle operation related to JWT token based
	 * authentication
	 */
	@Autowired
	private UserDetailServiceImpl jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	/**
	 * This api when called returns view of login Page.
	 *
	 * @return
	 */
	@RequestMapping("/signin")
	public ModelAndView login() {
		log.info("login: api Started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("login.html");
		modelView.addObject("title", "Login - Online Shopping");
		modelView.addObject("jwtRequest", new JwtRequest());
		log.info("login: api Stopped");
		return modelView;
	}
	/**
	 * This api when called authenticates the user by JWT token & returns view of
	 * JwtResponse token.
	 *
	 * @param jwtRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JwtResponse authenticateUser(@RequestBody JwtRequest jwtRequest) throws Exception {

		log.info("createJwtToken: api Started");
		if (jwtRequest.getUserName() == null || jwtRequest.getUserName().trim().equals("")) {
			throw new InvalidAuthStringException();
		}
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getUserPassword()));
		} catch (BadCredentialsException e) {
			throw new CredentialValidationException("incorrect credentails");
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		}


		return createJwtToken(jwtRequest.getUserName());
	}
	public JwtResponse createJwtToken(String userName) throws Exception {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		try {
			UserDetails userDetails = jwtService.loadUserByUsername(userName);
			String newGeneratedToken = jwtUtil.generateToken(userDetails);
			JwtCookieUtil.addTokenCookies(attr, newGeneratedToken, "true");
			User user = userService.findUserByUserName(userName);

			Set<SimpleGrantedAuthority> authorities = (Set<SimpleGrantedAuthority>) userDetails.getAuthorities();

			String role = authorities.stream().findFirst().toString();
			log.info("Role : " + role);
			return new JwtResponse(user, newGeneratedToken);
		} catch (Exception ex) {
			log.error("User = {} failed auth", userName, ex);
			JwtCookieUtil.clearTokenCookies(attr);
			throw new CredentialValidationException("incorrect credentails");
		}
	}
}