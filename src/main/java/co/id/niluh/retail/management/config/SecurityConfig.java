package co.id.niluh.retail.management.config;


import co.id.niluh.retail.management.config.prop.ConfigProperties;
import co.id.niluh.retail.management.security.token.OpenApiTokenFilter;
import co.id.niluh.retail.management.security.token.StaticTokenFilter;
import co.id.niluh.retail.management.security.token.security.OpenApiService;
import co.id.niluh.retail.management.security.token.security.token.TokenStoreService;
import co.id.niluh.retail.management.security.token.security.token.TokenConfiguration;
import co.id.niluh.retail.management.security.token.security.token.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//				.authorizeRequests()
//				.antMatchers(
//						"/swagger-ui.html",
//						"/swagger-resources/**",
//						"/v2/api-docs",
//						"/webjars/**",
//						"/api/open/**"
//				).permitAll()  // Allow public access to Swagger and open APIs
//				.anyRequest().authenticated()  // Secure all other endpoints
//				.and()
//				.csrf().disable(); // Optional: Disable CSRF for simplicity (use only if applicable)
//	}

	@Autowired
	private ConfigProperties configProperties;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	@Autowired
	private TokenStoreService tokenStoreService;
	@Autowired
	private OpenApiService openApiService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.antMatchers("/app/**/*.{js,html}")
				.antMatchers("/v2/api-docs", "/configuration/ui",  "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**")
				.antMatchers("**/*.html", "**/*.htm", "**/*.gif", "**/*.css", "**/*.js", "**/*.png", "**/*.jpg", "**/*.jpeg")
				.antMatchers("/api/open/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.headers().frameOptions().disable()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.antMatcher("/api/**").authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.apply(securityConfigurationAdapter());

	}

	private TokenConfiguration securityConfigurationAdapter() {
		return new TokenConfiguration(new TokenProvider(tokenStoreService));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean(name="staticFilterBean")
	public FilterRegistrationBean staticFilterBean() {
		FilterRegistrationBean filter = new FilterRegistrationBean<>();
		filter.setFilter(staticFilter());
		filter.setName("staticFilter");
		filter.setOrder(1);
		return filter;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean(name="openFilterBean")
	public FilterRegistrationBean openFilterBean() {
		FilterRegistrationBean filter = new FilterRegistrationBean<>();
		filter.setFilter(openApiTokenFilter());
		filter.setName("openApiTokenFilter");
		filter.setOrder(2);
		return filter;
	}

	private StaticTokenFilter staticFilter() {
		return new StaticTokenFilter(configProperties).authorize("/api/account/*", "/api/un-auth/**");
	}

	private OpenApiTokenFilter openApiTokenFilter() {
		return new OpenApiTokenFilter(openApiService).authorize("/api/open/**");
	}

}
