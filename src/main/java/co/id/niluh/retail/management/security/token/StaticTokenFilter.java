package co.id.niluh.retail.management.security.token;

import co.id.niluh.retail.management.config.prop.ConfigProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StaticTokenFilter implements Filter {

	public final static String AUTHORIZATION_HEADER = "x-static-token";

	private ConfigProperties prop;
	private String[] authorize;

	public StaticTokenFilter(ConfigProperties prop) {
		this.prop = prop;
	}

	@Override
	public void init(FilterConfig filterConfig) {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String token = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
		if (!filter(httpServletRequest, authorize)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			if (token != null && token.equals(prop)) {
				filterChain.doFilter(servletRequest, servletResponse);
			} else {
				HttpServletResponse response = (HttpServletResponse) servletResponse;
				response.setHeader("Content-Type", "application/json");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write(new ObjectMapper().writeValueAsString("Akses tidak diizinkan"));
				response.getWriter().flush();
				response.getWriter().close();
			}
		}

	}

	public StaticTokenFilter authorize(String... matches) {
		authorize = matches;
		return this;
	}

	private boolean filter(HttpServletRequest request, String[] filter) {
		String url = request.getServletPath();
		return PatternMatchUtils.simpleMatch(filter, url);

	}
}
