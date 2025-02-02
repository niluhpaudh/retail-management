package co.id.niluh.retail.management.security.token.security.token;

import co.id.niluh.retail.management.exception.AuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {

	private TokenProvider tokenProvider;

	public TokenFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String token = resolveToken(httpServletRequest);
		try {
			if (StringUtils.hasText(token)) {
				SecurityContextHolder.getContext().setAuthentication(this.tokenProvider.getAuthenticationToken(token));
			}
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (AuthenticationException e) {
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			response.setHeader("Content-Type", "application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(new ObjectMapper()
					.writeValueAsString("Error: "+ e.getMessage()));
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(TokenConfiguration.AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken)) {
			return bearerToken;
		}
		return null;
	}
}
