package co.id.niluh.retail.management.security.token;

import co.id.niluh.retail.management.common.Function;
import co.id.niluh.retail.management.entity.auth.Privilege;
import co.id.niluh.retail.management.enumz.ErrorCodes;
import co.id.niluh.retail.management.security.token.security.OpenApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OpenApiTokenFilter extends GenericFilterBean {

	public final static String AUTHORIZATION_HEADER = "x-password";
	public final static String USER_HEADER = "x-user";
	private String[] authorize;

	private OpenApiService securityOpenApiService;

	public OpenApiTokenFilter(OpenApiService securityOpenApiService) {
		this.securityOpenApiService = securityOpenApiService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		String token = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
		String user = httpServletRequest.getHeader(USER_HEADER);
		String path = ((HttpServletRequest) servletRequest).getServletPath();
		if (!filter(httpServletRequest, authorize)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			try {
				String remoteAddr = Function.getIpAddress(httpServletRequest);
				SecurityContextHolder.getContext().setAuthentication(
						this.securityOpenApiService.doAuthorization(token, remoteAddr, user)
				);
				List< Privilege > privileges = this.securityOpenApiService.privileges(user);
				Privilege foundPrivilege = null;
				for (Privilege privilege : privileges) {
					if (path.equals(privilege.getDescription())) {
						foundPrivilege = privilege;
						break;  // Stop searching after finding the first match
					}
				}
				if (foundPrivilege == null) {
					throw new InternalAuthenticationServiceException(ErrorCodes.FORBIDDEN_ACCESS.getDefaultMessage());
				}
				// Proceed with the chain
				filterChain.doFilter(servletRequest, servletResponse);

			} catch (InternalAuthenticationServiceException e) {
				// Check if the response is already committed
				if (!httpServletResponse.isCommitted()) {
					httpServletResponse.setContentType("application/json");
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

					// Write error response
					httpServletResponse.getWriter().write(
							new ObjectMapper().writeValueAsString(ErrorCodes.BAD_CREDENTIAL.getDefaultMessage())
					);
					httpServletResponse.getWriter().flush();
				} else {
					// Log the issue for debugging if response is already committed
					System.err.println("Response already committed. Unable to send error response.");
				}
			}
		}
	}


	public OpenApiTokenFilter authorize(String... matches) {
		authorize = matches;
		return this;
	}

	private boolean filter(HttpServletRequest request, String[] filter) {
		String url = request.getServletPath();
		return PatternMatchUtils.simpleMatch(filter, url);

	}
}
