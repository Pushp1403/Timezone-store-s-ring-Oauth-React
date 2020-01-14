package com.toptal.pushpendra.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.toptal.pushpendra.services.IUserDetailService;
import com.toptal.pushpendra.utilities.Constants;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class AppJwtAuthorizationFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(AppJwtAuthorizationFilter.class);

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil tokenUtils;

	@Autowired
	private IUserDetailService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader(tokenHeader);
		String username = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith(Constants.JWT_TOKEN_BEARER)) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = tokenUtils.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				logger.error(Constants.JWT_TOKEN_UNABLE_TO_GET_USERNAME, e);
			} catch (ExpiredJwtException e) {
				logger.error(Constants.JWT_TOKEN_EXPIRED, e);
			}

		} else {
			logger.warn(Constants.JWT_TOKEN_DOES_NOT_START_WITH_BEARER_STRING);
		}

		logger.debug("JWT_TOKEN_USERNAME VALUE '{}'", username);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = null;
			try {
				userDetails = this.userDetailService.loadUserByUsername(username);
			} catch (Exception e) {
				logger.debug(Constants.USER_NOT_EXIST);
			}
			boolean hasActiveSesion = userDetailService.hasActiveSesion(username, jwtToken);

			if (tokenUtils.validateToken(jwtToken, userDetails) && hasActiveSesion) {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(token);
			}
		}
		filterChain.doFilter(request, response);
	}

}
