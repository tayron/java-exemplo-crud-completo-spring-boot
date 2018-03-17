package br.com.projeto.login.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MySuccessAccessHandler implements AuthenticationSuccessHandler {
	
	private static Logger LOGGER = LoggerFactory.getLogger(MySuccessAccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		if (authentication != null) {
			LOGGER.debug("User '" + authentication.getName() + "' authenticated successfully.");
		}

		String target = authentication.getAuthorities().toArray()[0].toString().toLowerCase();
		response.sendRedirect(request.getContextPath() +  target);
	}

}
